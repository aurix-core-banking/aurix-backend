package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.FilaNotificacao;
import com.aurix.platform.shared.event.NotificacaoEnviadaEvent;
import com.aurix.platform.shared.event.NotificacaoFalhouEvent;
import com.aurix.platform.shared.event.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {
    private static final Logger log = LoggerFactory.getLogger(NotificacaoProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public NotificacaoProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notificacaoEnviada(FilaNotificacao notificacao) {
        try {
            NotificacaoEnviadaEvent event = NotificacaoEnviadaEvent.enviada(
                    notificacao.getId(), notificacao.getClienteId(),
                    notificacao.getCanal(), notificacao.getTemplateCodigo(),
                    notificacao.getStatus());
            kafkaTemplate.send(Topics.NOTIFICATION_NOTIFICACAO_ENVIADA,
                    String.valueOf(notificacao.getClienteId()), event);
        } catch (Exception e) {
            log.error("Erro ao enviar evento notificacao.enviada", e);
        }
    }

    public void notificacaoFalhou(FilaNotificacao notificacao, String motivo) {
        try {
            NotificacaoFalhouEvent event = NotificacaoFalhouEvent.falhou(
                    notificacao.getId(), notificacao.getClienteId(),
                    notificacao.getCanal(), motivo);
            kafkaTemplate.send(Topics.NOTIFICATION_NOTIFICACAO_FALHOU,
                    String.valueOf(notificacao.getClienteId()), event);
        } catch (Exception e) {
            log.error("Erro ao enviar evento notificacao.falhou", e);
        }
    }
}
