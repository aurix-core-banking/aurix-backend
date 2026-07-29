package com.aurix.platform.banking.poupanca.service;

import com.aurix.platform.banking.poupanca.client.BacenClient;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.banking.poupanca.entity.MovimentacaoPoupanca;
import com.aurix.platform.banking.poupanca.entity.MovimentacaoPoupanca.TipoMovimentacao;
import com.aurix.platform.banking.poupanca.event.RendimentoEvent;
import com.aurix.platform.banking.poupanca.repository.ContaPoupancaRepository;
import com.aurix.platform.banking.poupanca.repository.MovimentacaoPoupancaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AniversarioService {

    private static final Logger log = LoggerFactory.getLogger(AniversarioService.class);

    private final ContaPoupancaRepository contaPoupancaRepository;
    private final MovimentacaoPoupancaRepository movimentacaoPoupancaRepository;
    private final BacenClient bacenClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AniversarioService self;

    public AniversarioService(ContaPoupancaRepository contaPoupancaRepository,
                              MovimentacaoPoupancaRepository movimentacaoPoupancaRepository,
                              BacenClient bacenClient,
                              KafkaTemplate<String, Object> kafkaTemplate,
                              @Lazy AniversarioService self) {
        this.contaPoupancaRepository = contaPoupancaRepository;
        this.movimentacaoPoupancaRepository = movimentacaoPoupancaRepository;
        this.bacenClient = bacenClient;
        this.kafkaTemplate = kafkaTemplate;
        this.self = self;
    }

    public int processarAniversarios() {
        int hoje = LocalDate.now().getDayOfMonth();
        List<ContaPoupanca> contas = contaPoupancaRepository.findContasParaAniversario(hoje);
        for (ContaPoupanca conta : contas) {
            try {
                self.processarConta(conta);
            } catch (Exception e) {
                log.error("Erro ao processar aniversario da conta {}: {}", conta.getId(), e.getMessage());
            }
        }
        log.info("Aniversario processado para {} contas no dia {}", contas.size(), hoje);
        return contas.size();
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void executarProcessamentoDiario() {
        processarAniversarios();
    }

    @Transactional
    @Retryable(maxRetries = 3, delay = 100, multiplier = 2)
    public void processarConta(ContaPoupanca conta) {
        String dataStr = LocalDate.now().toString();
        BigDecimal tr = bacenClient.buscarTrDiaria(dataStr);
        BigDecimal rendimento = conta.getSaldo().multiply(tr).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);

        if (rendimento.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        BigDecimal saldoAnterior = conta.getSaldo();
        conta.setSaldo(saldoAnterior.add(rendimento));
        conta.setUltimoAniversario(LocalDate.now());
        contaPoupancaRepository.save(conta);

        MovimentacaoPoupanca mov = new MovimentacaoPoupanca();
        mov.setContaPoupancaId(conta.getId());
        mov.setTipo(TipoMovimentacao.RENDIMENTO_TR);
        mov.setValor(rendimento);
        mov.setSaldoAnterior(saldoAnterior);
        mov.setSaldoPosterior(conta.getSaldo());
        mov.setDescricao("Rendimento TR: " + tr + "%");
        mov.setTenantId(conta.getTenantId());
        movimentacaoPoupancaRepository.save(mov);

        try {
            kafkaTemplate.send(Topics.POUPANCA_RENDIMENTO_CREDITADO, new RendimentoEvent(
                conta.getId(), rendimento, tr, conta.getSaldo(), LocalDate.now(), conta.getTenantId()));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento rendimento: {}", e.getMessage());
        }

        log.info("Rendimento creditado: conta={}, tr={}, rendimento={}", conta.getId(), tr, rendimento);
    }

    public BigDecimal estimarProximoRendimento() {
        String dataStr = LocalDate.now().toString();
        try {
            return bacenClient.buscarTrDiaria(dataStr);
        } catch (Exception e) {
            log.warn("Nao foi possivel buscar TR para estimativa: {}", e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}
