package com.aurix.platform.shared.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

/**
 * Publisher de eventos para comunicação assíncrona entre módulos.
 */
@Component
public class EventPublisher {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventPublisher.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // ========== EVENTOS DE CONTA ==========
    /**
     * Publica evento de conta criada.
     *
     * @param event Evento de conta
     */
    public void publicarContaCriada(final ContaEvent event) {
        publishEvent(Topics.CONTA_CRIADA, event);
    }

    /**
     * Publica evento de conta atualizada.
     *
     * @param event Evento de conta
     */
    public void publicarContaAtualizada(final ContaEvent event) {
        publishEvent(Topics.CONTA_ATUALIZADA, event);
    }

    /**
     * Publica evento de conta bloqueada.
     *
     * @param event Evento de conta
     */
    public void publicarContaBloqueada(final ContaEvent event) {
        publishEvent(Topics.CONTA_BLOQUEADA, event);
    }

    // ========== EVENTOS DE TRANSAÇÃO ==========
    /**
     * Publica evento de transação realizada.
     *
     * @param event Evento de transação
     */
    public void publicarTransacaoRealizada(final TransacaoEvent event) {
        publishEvent(Topics.TRANSACAO_REALIZADA, event);
    }

    /**
     * Publica evento de transação liquidada.
     *
     * @param event Evento de transação
     */
    public void publicarTransacaoLiquidada(final TransacaoEvent event) {
        publishEvent(Topics.TRANSACAO_LIQUIDADA, event);
    }

    /**
     * Publica evento de transação conciliada.
     *
     * @param event Evento de transação
     */
    public void publicarTransacaoConciliada(final TransacaoEvent event) {
        publishEvent(Topics.TRANSACAO_CONCILIADA, event);
    }

    // ========== EVENTOS DE LIQUIDEZ ==========
    /**
     * Publica evento de liquidez processada.
     *
     * @param event Evento de liquidez
     */
    public void publicarLiquidezProcessada(final LiquidezEvent event) {
        publishEvent(Topics.LIQUIDEZ_PROCESSADA, event);
    }

    /**
     * Publica evento de liquidez rejeitada.
     *
     * @param event Evento de liquidez
     */
    public void publicarLiquidezRejeitada(final LiquidezEvent event) {
        publishEvent(Topics.LIQUIDEZ_REJEITADA, event);
    }

    // ========== EVENTOS DE IMPOSTO ==========
    /**
     * Publica evento de imposto calculado.
     *
     * @param event Evento de imposto
     */
    public void publicarImpostoCalculado(final ImpostoEvent event) {
        publishEvent(Topics.IMPOSTO_CALCULADO, event);
    }

    /**
     * Publica evento de imposto registrado.
     *
     * @param event Evento de imposto
     */
    public void publicarImpostoRegistrado(final ImpostoEvent event) {
        publishEvent(Topics.IMPOSTO_REGISTRADO, event);
    }

    // ========== MÉTODO GENÉRICO ==========
    /**
     * Publica evento genérico.
     *
     * @param topic Tópico para publicação
     * @param event Evento a ser publicado
     */
    private void publishEvent(final String topic, final BaseEvent event) {
        try {
            log.info("Publicando evento: Topic={}, EventType={}, EventId={}", topic, event.getEventType(), event.getEventId());
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event.getEventId(), event);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Evento publicado com sucesso: Topic={}, EventId={}, " + "Offset={}", topic, event.getEventId(), result.getRecordMetadata().offset());
                } else {
                    log.error("Erro ao publicar evento: Topic={}, EventId={}, " + "Error={}", topic, event.getEventId(), ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Erro ao publicar evento no Kafka: Topic={}, EventId={}, " + "Error={}", topic, event.getEventId(), e.getMessage());
        }
    }

    @java.lang.SuppressWarnings("all")
    public EventPublisher(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
