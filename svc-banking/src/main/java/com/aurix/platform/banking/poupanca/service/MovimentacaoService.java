package com.aurix.platform.banking.poupanca.service;

import com.aurix.platform.banking.poupanca.client.ContaCorrenteClient;
import com.aurix.platform.banking.poupanca.client.TaxClient;
import com.aurix.platform.banking.poupanca.dto.DepositoRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.banking.poupanca.dto.ExtratoResponse;
import com.aurix.platform.banking.poupanca.dto.ExtratoResponse.MovimentacaoItem;
import com.aurix.platform.banking.poupanca.dto.SaqueRequest;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca.StatusConta;
import com.aurix.platform.banking.poupanca.entity.MovimentacaoPoupanca;
import com.aurix.platform.banking.poupanca.entity.MovimentacaoPoupanca.TipoMovimentacao;
import com.aurix.platform.banking.poupanca.event.MovimentacaoEvent;
import com.aurix.platform.banking.poupanca.repository.ContaPoupancaRepository;
import com.aurix.platform.banking.poupanca.repository.MovimentacaoPoupancaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovimentacaoService {

    private static final Logger log = LoggerFactory.getLogger(MovimentacaoService.class);

    private final ContaPoupancaRepository contaPoupancaRepository;
    private final MovimentacaoPoupancaRepository movimentacaoPoupancaRepository;
    private final ContaCorrenteClient contaCorrenteClient;
    private final TaxClient taxClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MovimentacaoService(ContaPoupancaRepository contaPoupancaRepository,
                               MovimentacaoPoupancaRepository movimentacaoPoupancaRepository,
                               ContaCorrenteClient contaCorrenteClient,
                               TaxClient taxClient,
                               KafkaTemplate<String, Object> kafkaTemplate) {
        this.contaPoupancaRepository = contaPoupancaRepository;
        this.movimentacaoPoupancaRepository = movimentacaoPoupancaRepository;
        this.contaCorrenteClient = contaCorrenteClient;
        this.taxClient = taxClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @ConcurrencyLimit(1)
    @Retryable(maxRetries = 3, delay = 100, multiplier = 2)
    public void depositar(DepositoRequest request) {
        ContaPoupanca conta = validarContaAtiva(request.getContaPoupancaId());
        BigDecimal valor = request.getValor();

        BigDecimal saldoAnterior = conta.getSaldo();
        conta.setSaldo(saldoAnterior.add(valor));
        contaPoupancaRepository.save(conta);

        MovimentacaoPoupanca mov = new MovimentacaoPoupanca();
        mov.setContaPoupancaId(conta.getId());
        mov.setTipo(TipoMovimentacao.DEPOSITO);
        mov.setValor(valor);
        mov.setSaldoAnterior(saldoAnterior);
        mov.setSaldoPosterior(conta.getSaldo());
        mov.setDescricao("Deposito em conta poupanca");
        mov.setTenantId(TenantContext.getTenantId());
        movimentacaoPoupancaRepository.save(mov);

        contaCorrenteClient.debitar(conta.getContaCorrenteId(),
            new ContaCorrenteClient.DebitoRequest(valor, "Deposito poupanca"));

        try {
            kafkaTemplate.send(Topics.POUPANCA_DEPOSITO_REALIZADO, new MovimentacaoEvent(
                conta.getId(), "DEPOSITO", valor, BigDecimal.ZERO, conta.getSaldo(),
                mov.getDataMovimentacao(), conta.getTenantId()));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento deposito: {}", e.getMessage());
        }

        log.info("Deposito poupanca realizado: conta={}, valor={}", conta.getId(), valor);
    }

    @ConcurrencyLimit(1)
    @Retryable(maxRetries = 3, delay = 100, multiplier = 2)
    public void sacar(SaqueRequest request) {
        ContaPoupanca conta = validarContaAtiva(request.getContaPoupancaId());
        BigDecimal valor = request.getValor();

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente na conta poupanca");
        }

        BigDecimal iof = BigDecimal.ZERO;
        long diasAplicacao = ChronoUnit.DAYS.between(conta.getUltimoAniversario(), LocalDate.now());
        if (diasAplicacao < 30) {
            iof = taxClient.calcularIof(new TaxClient.IofRequest(
                conta.getClienteId(), valor, conta.getUltimoAniversario(), LocalDate.now())).valorIof();
        }

        BigDecimal valorTotal = valor.add(iof);
        if (conta.getSaldo().compareTo(valorTotal) < 0) {
            throw new IllegalStateException("Saldo insuficiente para valor + IOF");
        }

        BigDecimal saldoAnterior = conta.getSaldo();

        contaCorrenteClient.creditar(conta.getContaCorrenteId(),
            new ContaCorrenteClient.CreditoRequest(valor, "Saque poupanca"));

        conta.setSaldo(saldoAnterior.subtract(valorTotal));
        contaPoupancaRepository.save(conta);

        MovimentacaoPoupanca mov = new MovimentacaoPoupanca();
        mov.setContaPoupancaId(conta.getId());
        mov.setTipo(TipoMovimentacao.SAQUE);
        mov.setValor(valor);
        mov.setSaldoAnterior(saldoAnterior);
        mov.setSaldoPosterior(conta.getSaldo());
        mov.setDescricao("Saque de conta poupanca" + (iof.compareTo(BigDecimal.ZERO) > 0 ? " (IOF: " + iof + ")" : ""));
        mov.setTenantId(TenantContext.getTenantId());
        movimentacaoPoupancaRepository.save(mov);

        try {
            kafkaTemplate.send(Topics.POUPANCA_SAQUE_REALIZADO, new MovimentacaoEvent(
                conta.getId(), "SAQUE", valor, iof, conta.getSaldo(),
                mov.getDataMovimentacao(), conta.getTenantId()));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento saque: {}", e.getMessage());
        }

        log.info("Saque poupanca realizado: conta={}, valor={}, iof={}", conta.getId(), valor, iof);
    }

    @Transactional(readOnly = true)
    public ExtratoResponse gerarExtrato(Long contaId, LocalDateTime inicio, LocalDateTime fim) {
        ContaPoupanca conta = contaPoupancaRepository.findById(contaId)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + contaId));

        List<MovimentacaoPoupanca> movs = movimentacaoPoupancaRepository
            .findByContaAndPeriodo(contaId, inicio, fim);

        ExtratoResponse r = new ExtratoResponse();
        r.setContaId(conta.getId());
        r.setNumeroConta(conta.getNumeroConta());
        r.setSaldoAtual(conta.getSaldo());
        r.setDataInicio(inicio);
        r.setDataFim(fim);

        BigDecimal[] rendimento = {BigDecimal.ZERO};
        List<MovimentacaoItem> itens = movs.stream().map(m -> {
            MovimentacaoItem item = new MovimentacaoItem();
            item.setData(m.getDataMovimentacao());
            item.setDescricao(m.getDescricao());
            item.setValor(m.getTipo() == TipoMovimentacao.SAQUE ? m.getValor().negate() : m.getValor());
            item.setSaldo(m.getSaldoPosterior());
            if (m.getTipo() == TipoMovimentacao.RENDIMENTO_TR) {
                rendimento[0] = rendimento[0].add(m.getValor());
            }
            return item;
        }).toList();
        r.setMovimentacoes(itens);
        r.setRendimentoPeriodo(rendimento[0]);

        return r;
    }

    private ContaPoupanca validarContaAtiva(Long contaId) {
        ContaPoupanca conta = contaPoupancaRepository.findById(contaId)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + contaId));
        if (conta.getStatus() != StatusConta.ATIVA) {
            throw new IllegalStateException("Conta poupanca nao esta ativa: " + conta.getStatus());
        }
        return conta;
    }
}
