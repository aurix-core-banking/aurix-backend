package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.OutboxEvent;
import com.aurix.platform.banking.core.repository.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável por ler eventos da tabela de outbox e encaminhá-los
 * para os tópicos correspondentes no Kafka.
 */
@Service
public class OutboxRelay {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OutboxRelay.class);
    /**
     * Intervalo de processamento do outbox (5 segundos).
     */
    private static final long DELAY_PROCESSAMENT = 5000;
    /**
     * Repositório de eventos do outbox.
     */
    private final OutboxEventRepository outboxEventRepository;
    /**
     * Template do Kafka para envio de mensagens.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Construtor com injeção de dependências.
     *
     * @param outboxEventRepository Repositório do Outbox.
     * @param kafkaTemplate         Template do Kafka.
     */
    public OutboxRelay(final OutboxEventRepository outboxEventRepository, final KafkaTemplate<String, Object> kafkaTemplate) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Processa eventos pendentes no outbox periodicamente.
     */
    @Scheduled(fixedDelay = DELAY_PROCESSAMENT)
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatusOrderByCreatedAtAsc(OutboxEvent.Status.PENDING);
        if (pendingEvents.isEmpty()) {
            return;
        }
        log.info("Processando {} eventos pendentes no Outbox", pendingEvents.size());
        for (final OutboxEvent event : pendingEvents) {
            try {
                // eventType já é o nome completo do tópico (ver Topics, ADR-0001).
                // Antes este código recalculava "aggregateType.toLowerCase() + '.' +
                // eventType.toLowerCase()", o que nunca correspondia ao tópico real
                // escutado pelo consumidor — os eventos do outbox nunca chegavam ao
                // destino certo, mesmo antes desta correção.
                String topic = event.getEventType();
                kafkaTemplate.send(topic, event.getAggregateId(), event.getPayload());
                // Marcar como processado
                event.setStatus(OutboxEvent.Status.PROCESSED);
                event.setProcessedAt(LocalDateTime.now());
                outboxEventRepository.save(event);
                log.info("Evento Outbox {} publicado com sucesso no tópico {}" + " (RoutingKey: {})", event.getId(), topic, topic);
            } catch (Exception e) {
                log.error("Erro ao processar evento Outbox {}: {}", event.getId(), e.getMessage());
                event.setStatus(OutboxEvent.Status.FAILED);
                outboxEventRepository.save(event);
            }
        }
    }
}
