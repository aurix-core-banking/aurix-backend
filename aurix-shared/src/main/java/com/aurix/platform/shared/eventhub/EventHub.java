package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Event Hub centralizado para roteamento inteligente de eventos.
 *
 * Gerencia todos os eventos da plataforma Aurix com roteamento,
 * transformação e validação.
 */
@Service
public class EventHub {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventHub.class);
    /**
     * Template Kafka para publicação de eventos.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * Roteador de eventos para determinar destinos.
     */
    private final EventRouter eventRouter;
    /**
     * Transformador de eventos para conversões.
     */
    private final EventTransformer eventTransformer;
    /**
     * Validador de eventos para verificações.
     */
    private final EventValidator eventValidator;
    /**
     * Fila de eventos falhados para reprocessamento.
     */
    private final DeadLetterQueue deadLetterQueue;
    /**
     * Agenda publicações futuras (publishEventWithDelay) sem bloquear uma thread
     * durante a espera.
     */
    private final ScheduledExecutorService delayScheduler = Executors.newSingleThreadScheduledExecutor();
    /**
     * Base para backoff exponencial.
     */
    private static final int BACKOFF_BASE = 2;

    /**
     * Publica evento no Event Hub.
     *
     * @param event Evento a ser publicado
     * @return CompletableFuture para acompanhamento
     */
    public CompletableFuture<Void> publishEvent(final BaseEvent event) {
        log.info("Event Hub: Publicando evento - Tipo={}, ID={}, Source={}", event.getEventType(), event.getEventId(), event.getSource());
        try {
            // 1. Validar evento
            if (!eventValidator.validate(event)) {
                log.error("Event Hub: Evento inválido rejeitado - {}", event.getEventId());
                deadLetterQueue.sendToDLQ(event, "EVENT_VALIDATION_FAILED");
                return CompletableFuture.completedFuture(null);
            }
            // 2. Transformar evento se necessário
            BaseEvent transformedEvent = eventTransformer.transform(event);
            // 3. Determinar rotas do evento
            String[] routes = eventRouter.getRoutes(transformedEvent);
            // 4. Publicar em todas as rotas
            @SuppressWarnings("unchecked")
            CompletableFuture<Void>[] futures = new CompletableFuture[routes.length];
            for (int i = 0; i < routes.length; i++) {
                String route = routes[i];
                futures[i] = publishToRoute(transformedEvent, route);
            }
            // 5. Aguardar todas as publicações
            return CompletableFuture.allOf(futures).thenRun(() -> log.info("Event Hub: Evento {} publicado " + "com sucesso em {} rotas", event.getEventId(), routes.length)).exceptionally(throwable -> {
                log.error("Event Hub: Erro ao publicar evento {}: {}", event.getEventId(), throwable.getMessage());
                deadLetterQueue.sendToDLQ(event, "PUBLISH_FAILED: " + throwable.getMessage());
                return null;
            });
        } catch (Exception e) {
            log.error("Event Hub: Erro crítico ao processar evento {}: {}", event.getEventId(), e.getMessage());
            deadLetterQueue.sendToDLQ(event, "CRITICAL_ERROR: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    /**
     * Publica evento em rota específica.
     *
     * @param event Evento a publicar
     * @param route Rota/Tópico de destino
     * @return CompletableFuture para acompanhamento
     */
    private CompletableFuture<Void> publishToRoute(final BaseEvent event, final String route) {
        return CompletableFuture.runAsync(() -> {
            try {
                String topic = route;
                String key = event.getEventId();
                log.debug("Event Hub: Publicando evento {} na rota {}", event.getEventId(), route);
                CompletableFuture<org.springframework.kafka.support.SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.debug("Event Hub: Evento {} publicado com sucesso " + "na rota {} (offset: {})", event.getEventId(), route, result.getRecordMetadata().offset());
                    } else {
                        log.error("Event Hub: Erro ao publicar evento {} na rota {}: {}", event.getEventId(), route, ex.getMessage());
                        deadLetterQueue.sendToDLQ(event, "ROUTE_PUBLISH_FAILED: " + ex.getMessage());
                    }
                });
            } catch (Exception e) {
                log.error("Event Hub: Erro ao publicar evento {} na rota {}: {}", event.getEventId(), route, e.getMessage());
                deadLetterQueue.sendToDLQ(event, "ROUTE_ERROR: " + e.getMessage());
            }
        });
    }

    /**
     * Publica evento com prioridade.
     *
     * @param event    Evento a publicar
     * @param priority Prioridade desejada
     * @return CompletableFuture para acompanhamento
     */
    public CompletableFuture<Void> publishEventWithPriority(final BaseEvent event, final EventPriority priority) {
        log.info("Event Hub: Publicando evento com prioridade - Tipo={}, ID={}, Prioridade={}", event.getEventType(), event.getEventId(), priority);
        // Adicionar metadados de prioridade
        Map<String, Object> metadata = event.getMetadata();
        metadata.put("priority", priority.name());
        metadata.put("priorityTimestamp", LocalDateTime.now());
        return publishEvent(event);
    }

    /**
     * Publica evento com delay.
     *
     * @param event        Evento a publicar
     * @param delaySeconds Tempo de espera em segundos
     * @return CompletableFuture para acompanhamento
     */
    public CompletableFuture<Void> publishEventWithDelay(final BaseEvent event, final long delaySeconds) {
        log.info("Event Hub: Publicando evento com delay - Tipo={}, ID={}, Delay={}s", event.getEventType(), event.getEventId(), delaySeconds);
        // Adicionar metadados de delay
        Map<String, Object> metadata = event.getMetadata();
        metadata.put("delaySeconds", delaySeconds);
        metadata.put("scheduledTime", LocalDateTime.now().plusSeconds(delaySeconds));
        // Agenda a publicação para depois do delay sem bloquear nenhuma thread
        // esperando (a versão anterior usava Thread.sleep, prendendo uma thread do
        // pool compartilhado pela duração inteira do delay).
        CompletableFuture<Void> result = new CompletableFuture<>();
        delayScheduler.schedule(() -> publishEvent(event).whenComplete((v, ex) -> {
            if (ex != null) {
                result.completeExceptionally(ex);
            } else {
                result.complete(null);
            }
        }), delaySeconds, TimeUnit.SECONDS);
        return result;
    }

    /**
     * Publica evento com retry automático.
     *
     * @param event      Evento a publicar
     * @param maxRetries Máximo de tentativas
     * @return CompletableFuture para acompanhamento
     */
    public CompletableFuture<Void> publishEventWithRetry(final BaseEvent event, final int maxRetries) {
        log.info("Event Hub: Publicando evento com retry - Tipo={}, ID={}, MaxRetries={}", event.getEventType(), event.getEventId(), maxRetries);
        return publishEventWithRetryInternal(event, maxRetries, 0);
    }

    /**
     * Implementação interna de retry.
     *
     * @param event        Evento a publicar
     * @param maxRetries   Máximo de tentativas
     * @param currentRetry Tentativa atual
     * @return CompletableFuture para acompanhamento
     */
    private CompletableFuture<Void> publishEventWithRetryInternal(final BaseEvent event, final int maxRetries, final int currentRetry) {
        return publishEvent(event).thenCompose(result -> {
            if (currentRetry < maxRetries) {
                log.warn("Event Hub: Tentativa {} de {} para evento {}", currentRetry + 1, maxRetries, event.getEventId());
                // Delay exponencial entre tentativas, sem bloquear uma thread esperando
                long delaySeconds = (long) Math.pow(BACKOFF_BASE, currentRetry);
                CompletableFuture<Void> delayed = new CompletableFuture<>();
                delayScheduler.schedule(() -> delayed.complete(null), delaySeconds, TimeUnit.SECONDS);
                return delayed.thenCompose(v -> publishEventWithRetryInternal(event, maxRetries, currentRetry + 1));
            } else {
                log.error("Event Hub: Máximo de tentativas atingido para evento {}", event.getEventId());
                deadLetterQueue.sendToDLQ(event, "MAX_RETRIES_EXCEEDED");
                return CompletableFuture.completedFuture(null);
            }
        });
    }

    /**
     * Obtém estatísticas do Event Hub.
     *
     * @return Mapa com estatísticas
     */
    public Map<String, Object> getStatistics() {
        return Map.of("totalEventsProcessed", eventRouter.getTotalEventsProcessed(), "totalRoutes", eventRouter.getTotalRoutes(), "totalTransformations", eventTransformer.getTotalTransformations(), "totalValidations", eventValidator.getTotalValidations(), "totalDLQEvents", deadLetterQueue.getTotalDLQEvents(), "timestamp", LocalDateTime.now());
    }


    /**
     * Enum para prioridades de evento.
     */
    public enum EventPriority {
        /**
         * Prioridade baixa.
         */
        LOW, /**
         * Prioridade normal.
         */
        NORMAL, /**
         * Prioridade alta.
         */
        HIGH, /**
         * Prioridade crítica.
         */
        CRITICAL;
    }

    /**
     * Creates a new {@code EventHub} instance.
     *
     * @param kafkaTemplate Template Kafka para publicação de eventos.
     * @param eventRouter Roteador de eventos para determinar destinos.
     * @param eventTransformer Transformador de eventos para conversões.
     * @param eventValidator Validador de eventos para verificações.
     * @param deadLetterQueue Fila de eventos falhados para reprocessamento.
     */
    @java.lang.SuppressWarnings("all")
    public EventHub(final KafkaTemplate<String, Object> kafkaTemplate, final EventRouter eventRouter, final EventTransformer eventTransformer, final EventValidator eventValidator, final DeadLetterQueue deadLetterQueue) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventRouter = eventRouter;
        this.eventTransformer = eventTransformer;
        this.eventValidator = eventValidator;
        this.deadLetterQueue = deadLetterQueue;
    }
}
