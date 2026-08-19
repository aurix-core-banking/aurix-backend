package com.aurix.platform.savings.service;

import com.aurix.platform.savings.dto.ContaPoupancaRequest;
import com.aurix.platform.savings.dto.ContaPoupancaResponse;
import com.aurix.platform.savings.dto.MovimentacaoRequest;
import com.aurix.platform.savings.dto.MovimentacaoResponse;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.TransacaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PoupancaService {

    private static final Logger log = LoggerFactory.getLogger(PoupancaService.class);
    private static final BigDecimal TAXA_POUPANCA_ANUAL = new BigDecimal("0.0617");
    private static final int DIAS_ANO = 365;

    private final ContaRepository contaRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PoupancaService(ContaRepository contaRepository,
                           KafkaTemplate<String, Object> kafkaTemplate) {
        this.contaRepository = contaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ContaPoupancaResponse criarConta(ContaPoupancaRequest request) {
        log.info("Criando conta poupanca para cliente: {}", request.getClienteId());
        String tenantId = TenantContext.getTenantId();

        Conta conta = new Conta();
        conta.setTenantId(tenantId);
        conta.setNumeroConta(TransacaoUtil.gerarCodigoPix());
        conta.setTipoConta(Conta.TipoConta.POUPANCA);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setLimiteCredito(BigDecimal.ZERO);
        conta.setLimiteUtilizado(BigDecimal.ZERO);
        conta.setStatus(Conta.StatusConta.ATIVA);

        Conta contaSalva = contaRepository.save(conta);
        log.info("Conta poupanca criada: {}", contaSalva.getNumeroConta());

        ContaPoupancaResponse resp = new ContaPoupancaResponse();
        resp.setId(contaSalva.getId());
        resp.setClienteId(request.getClienteId());
        resp.setNumeroConta(contaSalva.getNumeroConta());
        resp.setSaldo(BigDecimal.ZERO);
        resp.setAniversarioDia(request.getAniversarioDia() != null
            ? request.getAniversarioDia() : LocalDate.now().getDayOfMonth());
        resp.setDataAbertura(LocalDate.now());
        resp.setStatus(Conta.StatusConta.ATIVA.name());
        resp.setDataCriacao(contaSalva.getDataCriacao());
        return resp;
    }

    @Transactional(readOnly = true)
    public ContaPoupancaResponse buscarPorId(Long id) {
        log.info("Buscando poupanca ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        return toResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaPoupancaResponse> listarPorCliente(Long clienteId) {
        log.info("Listando poupancas do cliente: {}", clienteId);
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantIdAndClienteId(tenantId, clienteId).stream()
            .filter(c -> c.getTipoConta() == Conta.TipoConta.POUPANCA)
            .map(this::toResponse).collect(Collectors.toList());
    }

    public void bloquear(Long id) {
        log.info("Bloqueando poupanca ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        if (conta.getStatus() == Conta.StatusConta.FECHADA) {
            throw new IllegalStateException("Conta poupanca ja esta encerrada");
        }
        conta.setStatus(Conta.StatusConta.BLOQUEADA);
        contaRepository.save(conta);
    }

    public void encerrar(Long id) {
        log.info("Encerrando poupanca ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("Conta poupanca possui saldo — transfira antes de encerrar");
        }
        conta.setStatus(Conta.StatusConta.FECHADA);
        contaRepository.save(conta);
    }

    public MovimentacaoResponse depositar(Long id, MovimentacaoRequest request) {
        log.info("Depositando {} na poupanca ID: {}", request.getValor(), id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));

        BigDecimal saldoAnterior = conta.getSaldo();
        conta.setSaldo(saldoAnterior.add(request.getValor()));
        contaRepository.save(conta);

        MovimentacaoResponse resp = new MovimentacaoResponse();
        resp.setPoupancaId(id);
        resp.setTipoMovimentacao("DEPOSITO");
        resp.setValor(request.getValor());
        resp.setSaldoAnterior(saldoAnterior);
        resp.setSaldoAtual(conta.getSaldo());
        resp.setDescricao(request.getDescricao());
        resp.setDataMovimentacao(LocalDateTime.now());
        return resp;
    }

    public MovimentacaoResponse resgatar(Long id, MovimentacaoRequest request) {
        log.info("Resgatando {} da poupanca ID: {}", request.getValor(), id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));

        if (conta.getSaldo().compareTo(request.getValor()) < 0) {
            throw new IllegalStateException("Saldo insuficiente para resgate");
        }

        BigDecimal saldoAnterior = conta.getSaldo();
        conta.setSaldo(saldoAnterior.subtract(request.getValor()));
        contaRepository.save(conta);

        MovimentacaoResponse resp = new MovimentacaoResponse();
        resp.setPoupancaId(id);
        resp.setTipoMovimentacao("RESGATE");
        resp.setValor(request.getValor());
        resp.setSaldoAnterior(saldoAnterior);
        resp.setSaldoAtual(conta.getSaldo());
        resp.setDescricao(request.getDescricao());
        resp.setDataMovimentacao(LocalDateTime.now());
        return resp;
    }

    public void render(Long id) {
        log.info("Render poupanca ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));

        if (conta.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("Poupanca {} sem saldo para render", id);
            return;
        }

        BigDecimal rendimento = conta.getSaldo()
            .multiply(TAXA_POUPANCA_ANUAL)
            .divide(BigDecimal.valueOf(DIAS_ANO), 10, RoundingMode.HALF_UP);
        conta.setSaldo(conta.getSaldo().add(rendimento));
        contaRepository.save(conta);
        log.info("Rendimento aplicado: {} na poupanca {}", rendimento, id);
    }

    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(Long id) {
        log.info("Consultando saldo da poupanca ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        return conta.getSaldo();
    }

    private ContaPoupancaResponse toResponse(Conta conta) {
        ContaPoupancaResponse resp = new ContaPoupancaResponse();
        resp.setId(conta.getId());
        resp.setNumeroConta(conta.getNumeroConta());
        resp.setSaldo(conta.getSaldo());
        resp.setStatus(conta.getStatus() != null ? conta.getStatus().name() : null);
        resp.setDataCriacao(conta.getDataCriacao());
        return resp;
    }
}
