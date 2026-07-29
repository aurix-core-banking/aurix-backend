package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.OutboxEvent;
import com.aurix.platform.banking.core.repository.OutboxEventRepository;
import com.aurix.platform.shared.event.BaseEvent;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Publicador de eventos que utiliza o padrão Outbox para garantir a entrega.
 */
@Primary
@Component
@Service
public class OutboxEventPublisher extends EventPublisher {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OutboxEventPublisher.class);
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    /**
     * Construtor com injeção de dependências.
     *
     * @param kafkaTemplate         Template do Kafka.
     * @param outboxEventRepository Repositório do Outbox.
     * @param objectMapper          Mapper para serialização JSON.
     */
    public OutboxEventPublisher(final KafkaTemplate<String, Object> kafkaTemplate, final OutboxEventRepository outboxEventRepository, final ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publicarContaCriada(final ContaEvent event) {
        saveToOutbox("CONTA", event.getEventId(), Topics.CONTA_CRIADA, event);
    }

    @Override
    public void publicarContaAtualizada(final ContaEvent event) {
        // Antes não estava sobrescrito aqui, então caía no EventPublisher.publishEvent
        // direto (sem outbox) — inconsistente com os outros dois eventos "ao vivo".
        saveToOutbox("CONTA", event.getEventId(), Topics.CONTA_ATUALIZADA, event);
    }

    @Override
    public void publicarTransacaoRealizada(final TransacaoEvent event) {
        saveToOutbox("TRANSACAO", event.getEventId(), Topics.TRANSACAO_REALIZADA, event);
    }

    /**
     * Salva um evento na tabela de outbox.
     *
     * @param aggregateType Tipo do agregado (ex: CONTA, TRANSACAO).
     * @param aggregateId   ID do agregado.
     * @param eventType     Tipo do evento.
     * @param event         Dados do evento.
     */
    private void saveToOutbox(final String aggregateType, final String aggregateId, final String eventType, final BaseEvent event) {
        try {
            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setAggregateType(aggregateType);
            outboxEvent.setAggregateId(aggregateId);
            outboxEvent.setEventType(eventType);
            outboxEvent.setPayload(objectMapper.writeValueAsString(event));
            outboxEventRepository.save(outboxEvent);
            log.info("Evento salvo no Outbox: {} - {}", eventType, aggregateId);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar evento para o Outbox", e);
            throw new RuntimeException("Erro ao serializar evento", e);
        }
    }
}
