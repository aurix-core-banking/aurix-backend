package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.client.ContaCorrenteClient;
import com.aurix.platform.credit.financiamento.dto.request.PagarParcelaRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.credit.financiamento.dto.response.ParcelaResponse;
import com.aurix.platform.credit.financiamento.entity.ContratoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.ParcelaFinanciamento;
import com.aurix.platform.credit.financiamento.entity.StatusParcela;
import com.aurix.platform.credit.financiamento.event.ParcelaPagaEvent;
import com.aurix.platform.credit.financiamento.repository.ContratoFinanciamentoRepository;
import com.aurix.platform.credit.financiamento.repository.ParcelaFinanciamentoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("parcelaFinanciamentoService")
public class ParcelaService {

    private static final Logger log = LoggerFactory.getLogger(ParcelaService.class);

    private static final BigDecimal MULTA_PERCENTUAL = new BigDecimal("0.02");
    private static final BigDecimal JUROS_MORA_DIARIOS = new BigDecimal("0.00033");

    private final ParcelaFinanciamentoRepository parcelaRepository;
    private final ContratoFinanciamentoRepository contratoRepository;
    private final ContaCorrenteClient contaCorrenteClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ParcelaService(ParcelaFinanciamentoRepository parcelaRepository,
                          ContratoFinanciamentoRepository contratoRepository,
                          ContaCorrenteClient contaCorrenteClient,
                          KafkaTemplate<String, Object> kafkaTemplate) {
        this.parcelaRepository = parcelaRepository;
        this.contratoRepository = contratoRepository;
        this.contaCorrenteClient = contaCorrenteClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional(readOnly = true)
    public List<ParcelaResponse> listarParcelas(Long contratoId) {
        return parcelaRepository.findByContratoIdOrderByNumero(contratoId).stream()
            .map(this::toResponse).toList();
    }

    @Transactional
    public void pagarParcela(Long contratoId, PagarParcelaRequest request) {
        var parcela = parcelaRepository.findById(request.getParcelaId())
            .orElseThrow(() -> new IllegalArgumentException("Parcela não encontrada: " + request.getParcelaId()));

        if (parcela.getStatus() == StatusParcela.PAGA) {
            throw new IllegalStateException("Parcela já está paga: " + request.getParcelaId());
        }

        var contrato = contratoRepository.findById(contratoId)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + contratoId));

        var valorDevido = parcela.getValorParcela();
        if (parcela.getStatus() == StatusParcela.ATRASADA && parcela.getDataVencimento() != null) {
            var diasAtraso = ChronoUnit.DAYS.between(parcela.getDataVencimento(), LocalDate.now());
            if (diasAtraso > 0) {
                var multa = parcela.getValorParcela().multiply(MULTA_PERCENTUAL)
                    .setScale(2, RoundingMode.HALF_EVEN);
                var jurosMora = parcela.getValorParcela()
                    .multiply(JUROS_MORA_DIARIOS.multiply(BigDecimal.valueOf(diasAtraso)))
                    .setScale(2, RoundingMode.HALF_EVEN);
                valorDevido = valorDevido.add(multa).add(jurosMora);
            }
        }

        if (contrato.getContaCorrenteId() != null) {
            try {
                contaCorrenteClient.debitar(contrato.getContaCorrenteId(),
                    new ContaCorrenteClient.DebitoRequest(valorDevido, "Pagamento parcela " + parcela.getNumero()));
            } catch (Exception e) {
                log.warn("Erro ao debitar valor da conta corrente: {}", e.getMessage());
            }
        }

        parcela.setStatus(StatusParcela.PAGA);
        parcela.setDataPagamento(LocalDate.now());
        parcelaRepository.save(parcela);

        var novoSaldo = contrato.getSaldoDevedor().subtract(parcela.getValorParcela())
            .setScale(2, RoundingMode.HALF_EVEN);
        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            novoSaldo = BigDecimal.ZERO;
        }
        contrato.setSaldoDevedor(novoSaldo);
        contratoRepository.save(contrato);

        try {
            kafkaTemplate.send(Topics.FINANCIAMENTO_PARCELA_PAGA,
                new ParcelaPagaEvent(parcela.getId(), contratoId, parcela.getNumero(),
                    valorDevido, LocalDate.now(), contrato.getTenantId()));
        } catch (Exception e) {
            log.warn("Erro ao publicar ParcelaPagaEvent: {}", e.getMessage());
        }

        log.info("Parcela paga: id={}, contratoId={}, valor={}", parcela.getId(), contratoId, valorDevido);
    }

    private ParcelaResponse toResponse(ParcelaFinanciamento p) {
        return new ParcelaResponse(p.getId(), p.getContratoId(), p.getNumero(),
            p.getDataVencimento(), p.getValorParcela(), p.getValorAmortizacao(),
            p.getValorJuros(), p.getValorSaldoDevolver(), p.getDataPagamento(),
            p.getStatus().name());
    }
}
