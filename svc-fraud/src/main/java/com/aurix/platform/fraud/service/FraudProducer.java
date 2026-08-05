package com.aurix.platform.fraud.service;

import com.aurix.platform.fraud.entity.BloqueioPreventivo;
import com.aurix.platform.fraud.entity.OcorrenciaFraude;
import com.aurix.platform.fraud.entity.ScoreTransacao;
import com.aurix.platform.shared.event.OcorrenciaFraudEvent;
import com.aurix.platform.shared.event.ScoreAlteradoEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.event.TransacaoBloqueadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudProducer {
    private static final Logger log = LoggerFactory.getLogger(FraudProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FraudProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void transacaoBloqueada(ScoreTransacao score, BloqueioPreventivo bloqueio) {
        TransacaoBloqueadaEvent event = TransacaoBloqueadaEvent.bloqueada(
                score.getClienteId(),
                score.getTransacaoRef(),
                score.getScore(),
                score.getRisco(),
                bloqueio.getId());
        kafkaTemplate.send(Topics.FRAUD_TRANSACAO_BLOQUEADA,
                String.valueOf(score.getClienteId()), event);
        log.info("Evento transacao bloqueada enviado: clienteId={}", score.getClienteId());
    }

    public void ocorrenciaCriada(OcorrenciaFraude ocorrencia) {
        OcorrenciaFraudEvent event = OcorrenciaFraudEvent.criada(
                ocorrencia.getClienteId(),
                ocorrencia.getId(),
                ocorrencia.getTipo(),
                ocorrencia.getStatus());
        kafkaTemplate.send(Topics.FRAUD_OCORRENCIA_CRIADA,
                String.valueOf(ocorrencia.getClienteId()), event);
        log.info("Evento ocorrencia criada enviado: clienteId={}", ocorrencia.getClienteId());
    }

    public void scoreAlterado(Long clienteId, String transacaoRef, Integer score, String risco) {
        ScoreAlteradoEvent event = ScoreAlteradoEvent.alterado(
                clienteId, transacaoRef, score, risco);
        kafkaTemplate.send(Topics.FRAUD_SCORE_ALTERADO,
                String.valueOf(clienteId), event);
        log.info("Evento score alterado enviado: clienteId={}", clienteId);
    }
}
