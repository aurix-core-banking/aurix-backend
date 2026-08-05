package com.aurix.platform.customer.kyc.service;

import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import com.aurix.platform.shared.event.KycAprovadoEvent;
import com.aurix.platform.shared.event.KycRejeitadoEvent;
import com.aurix.platform.shared.event.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KycProducer {
    private static final Logger log = LoggerFactory.getLogger(KycProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KycProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void kycAprovado(SolicitacaoKYC solicitacao) {
        KycAprovadoEvent event = KycAprovadoEvent.aprovado(
                solicitacao.getClienteId(), solicitacao.getId(), solicitacao.getScoreRisco());
        kafkaTemplate.send(Topics.KYC_SOLICITACAO_APROVADA,
                String.valueOf(solicitacao.getClienteId()), event);
        log.info("Evento KYC_APROVADO enviado para {}: clienteId={}",
                Topics.KYC_SOLICITACAO_APROVADA, solicitacao.getClienteId());
    }

    public void kycRejeitado(SolicitacaoKYC solicitacao, String motivo) {
        KycRejeitadoEvent event = KycRejeitadoEvent.rejeitado(
                solicitacao.getClienteId(), solicitacao.getId(), motivo);
        kafkaTemplate.send(Topics.KYC_SOLICITACAO_REJEITADA,
                String.valueOf(solicitacao.getClienteId()), event);
        log.info("Evento KYC_REJEITADO enviado para {}: clienteId={}",
                Topics.KYC_SOLICITACAO_REJEITADA, solicitacao.getClienteId());
    }
}
