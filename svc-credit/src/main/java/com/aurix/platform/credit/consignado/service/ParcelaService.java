package com.aurix.platform.credit.consignado.service;

import com.aurix.platform.credit.consignado.client.ContaSalarioClient;
import com.aurix.platform.credit.consignado.client.ContaSalarioClient.DebitarParcelaRequest;
import com.aurix.platform.credit.consignado.dto.ParcelaResponse;
import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import com.aurix.platform.credit.consignado.entity.Parcela;
import com.aurix.platform.credit.consignado.event.ParcelaDebitadaEvent;
import com.aurix.platform.credit.consignado.repository.ParcelaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("parcelaConsignadoService")
public class ParcelaService {

    private static final Logger log = LoggerFactory.getLogger(ParcelaService.class);

    private final ParcelaRepository parcelaRepository;
    private final ContaSalarioClient contaSalarioClient;
    private final MargemService margemService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ParcelaService(ParcelaRepository parcelaRepository,
                          ContaSalarioClient contaSalarioClient,
                          MargemService margemService,
                          KafkaTemplate<String, Object> kafkaTemplate) {
        this.parcelaRepository = parcelaRepository;
        this.contaSalarioClient = contaSalarioClient;
        this.margemService = margemService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void gerarParcelas(ContratoConsignado contrato) {
        BigDecimal valorParcela = contrato.getValorParcela();
        for (int i = 1; i <= contrato.getPrazoMeses(); i++) {
            var parcela = new Parcela(
                contrato.getId(),
                i,
                valorParcela,
                contrato.getDataContratacao().plusMonths(i),
                null,
                "PENDENTE",
                contrato.getTenantId()
            );
            parcelaRepository.save(parcela);
        }
        log.info("Parcelas geradas: contratoId={}, quantidade={}", contrato.getId(), contrato.getPrazoMeses());
    }

    @Transactional(readOnly = true)
    public List<ParcelaResponse> listarParcelas(Long contratoId) {
        return parcelaRepository.findByContratoId(contratoId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public void processarParcelasVencidas() {
        List<Parcela> vencidas = parcelaRepository
            .findByStatusAndDataVencimentoBefore("PENDENTE", LocalDate.now());
        for (Parcela parcela : vencidas) {
            try {
                var request = new DebitarParcelaRequest(
                    parcela.getContratoId(), null, parcela.getValor(),
                    "parcela-" + parcela.getId());
                contaSalarioClient.debitarParcela(request);
                parcela.setStatus("PAGA");
                parcela.setDataPagamento(LocalDate.now());
                parcelaRepository.save(parcela);
                try {
                    kafkaTemplate.send(Topics.CONSIGNADO_PARCELA_DEBITADA, new ParcelaDebitadaEvent(
                        parcela.getId(), parcela.getContratoId(), parcela.getNumero(),
                        parcela.getValor(), LocalDate.now(), "DEFAULT"));
                } catch (Exception e) {
                    log.warn("Erro ao publicar ParcelaDebitadaEvent: parcelaId={}", parcela.getId());
                }
                log.info("Parcela debitada: id={}, contratoId={}", parcela.getId(), parcela.getContratoId());
            } catch (Exception e) {
                parcela.setStatus("ATRASADA");
                parcelaRepository.save(parcela);
                log.error("Falha ao debitar parcela: id={}, contratoId={}", parcela.getId(), parcela.getContratoId(), e);
            }
        }
    }

    private ParcelaResponse toResponse(Parcela p) {
        return new ParcelaResponse(p.getId(), p.getContratoId(), p.getNumero(),
            p.getValor(), p.getDataVencimento(), p.getDataPagamento(), p.getStatus());
    }
}
