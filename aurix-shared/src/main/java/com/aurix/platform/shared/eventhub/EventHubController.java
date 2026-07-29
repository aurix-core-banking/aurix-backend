package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller para APIs do Event Hub.
 *
 * Expõe endpoints para gerenciar o Event Hub centralizado.
 */
@RestController
@RequestMapping("/api/eventhub")
@Tag(name = "Event Hub", description = "APIs para Event Hub centralizado")
public class EventHubController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventHubController.class);
    /**
     * Event Hub centralizado para publicação de eventos.
     */
    private final EventHub eventHub;
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
     * Armazenamento persistente de eventos.
     */
    private final EventStore eventStore;

    // ========== PUBLICAÇÃO DE EVENTOS ==========
    /**
     * Publica evento no Event Hub.
     *
     * @param event Evento a ser publicado
     * @return ResponseEntity com status da operação
     */
    @PostMapping("/events/publish")
    @Operation(summary = "Publicar evento", description = "Publica evento no Event Hub " + "com roteamento inteligente")
    public ResponseEntity<Map<String, Object>> publishEvent(@RequestBody final BaseEvent event) {
        log.info("Publicando evento via API: Tipo={}, ID={}", event.getEventType(), event.getEventId());
        try {
            // Publicar evento
            eventHub.publishEvent(event);
            // Armazenar no Event Store
            eventStore.storeEvent(event);
            Map<String, Object> response = Map.of("status", "SUCCESS", "eventId", event.getEventId(), "eventType", event.getEventType(), "timestamp", LocalDateTime.now(), "message", "Evento publicado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao publicar evento via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "eventId", event.getEventId(), "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Publica evento com prioridade.
     *
     * @param event    Evento a publicar
     * @param priority Prioridade desejada
     * @return ResponseEntity com status da operação
     */
    @PostMapping("/events/publish/priority")
    @Operation(summary = "Publicar evento com prioridade", description = "Publica evento no Event Hub " + "com prioridade específica")
    public ResponseEntity<Map<String, Object>> publishEventWithPriority(@RequestBody final BaseEvent event, @RequestParam final EventHub.EventPriority priority) {
        log.info("Publicando evento com prioridade via API: " + "Tipo={}, ID={}, Prioridade={}", event.getEventType(), event.getEventId(), priority);
        try {
            // Publicar evento com prioridade
            eventHub.publishEventWithPriority(event, priority);
            // Armazenar no Event Store
            eventStore.storeEvent(event);
            Map<String, Object> response = Map.of("status", "SUCCESS", "eventId", event.getEventId(), "eventType", event.getEventType(), "priority", priority.name(), "timestamp", LocalDateTime.now(), "message", "Evento publicado com prioridade");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao publicar evento com prioridade via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "eventId", event.getEventId(), "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Publica evento com retry.
     *
     * @param event      Evento a publicar
     * @param maxRetries Máximo de tentativas
     * @return ResponseEntity com status da operação
     */
    @PostMapping("/events/publish/retry")
    @Operation(summary = "Publicar evento with retry", description = "Publica evento no Event Hub com retry automático")
    public ResponseEntity<Map<String, Object>> publishEventWithRetry(@RequestBody final BaseEvent event, @RequestParam(defaultValue = "3") final int maxRetries) {
        log.info("Publicando evento com retry via API: " + "Tipo={}, ID={}, MaxRetries={}", event.getEventType(), event.getEventId(), maxRetries);
        try {
            // Publicar evento com retry
            eventHub.publishEventWithRetry(event, maxRetries);
            Map<String, Object> response = Map.of("status", "SUCCESS", "eventId", event.getEventId(), "eventType", event.getEventType(), "maxRetries", maxRetries, "timestamp", LocalDateTime.now(), "message", "Evento publicado com retry");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao publicar evento com retry via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "eventId", event.getEventId(), "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== CONSULTA DE EVENTOS ==========
    /**
     * Busca eventos por tipo.
     *
     * @param eventType Tipo do evento
     * @return Lista de eventos encontrados
     */
    @GetMapping("/events/type/{eventType}")
    @Operation(summary = "Buscar eventos por tipo", description = "Busca eventos armazenados por tipo")
    public ResponseEntity<List<EventStore.StoredEvent>> getEventsByType(@PathVariable final String eventType) {
        log.info("Buscando eventos por tipo via API: {}", eventType);
        try {
            List<EventStore.StoredEvent> events = eventStore.getEventsByType(eventType);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Erro ao buscar eventos por tipo via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Busca evento por ID.
     *
     * @param eventId ID do evento
     * @return Evento encontrado ou 404
     */
    @GetMapping("/events/{eventId}")
    @Operation(summary = "Buscar evento por ID", description = "Busca evento específico por ID")
    public ResponseEntity<EventStore.StoredEvent> getEventById(@PathVariable final String eventId) {
        log.info("Buscando evento por ID via API: {}", eventId);
        try {
            Optional<EventStore.StoredEvent> event = eventStore.getEventById(eventId);
            if (event.isPresent()) {
                return ResponseEntity.ok(event.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar evento por ID via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Busca eventos por período.
     *
     * @param startTime Início do período
     * @param endTime   Fim do período
     * @return Lista de eventos encontrados
     */
    @GetMapping("/events/period")
    @Operation(summary = "Buscar eventos por período", description = "Busca eventos em um período específico")
    public ResponseEntity<List<EventStore.StoredEvent>> getEventsByPeriod(@RequestParam final LocalDateTime startTime, @RequestParam final LocalDateTime endTime) {
        log.info("Buscando eventos por período via API: {} a {}", startTime, endTime);
        try {
            List<EventStore.StoredEvent> events = eventStore.getEventsByPeriod(startTime, endTime);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Erro ao buscar eventos por período via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Busca eventos por correlation ID.
     *
     * @param correlationId ID de correlação
     * @return Lista de eventos encontrados
     */
    @GetMapping("/events/correlation/{correlationId}")
    @Operation(summary = "Buscar eventos por correlation ID", description = "Busca eventos relacionados por correlation ID")
    public ResponseEntity<List<EventStore.StoredEvent>> getEventsByCorrelationId(@PathVariable final String correlationId) {
        log.info("Buscando eventos por correlation ID via API: {}", correlationId);
        try {
            List<EventStore.StoredEvent> events = eventStore.getEventsByCorrelationId(correlationId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Erro ao buscar eventos por correlation ID via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ========== REPLAY DE EVENTOS ==========
    /**
     * Replay de eventos por tipo.
     *
     * @param eventType Tipo para replay
     * @return ResponseEntity com status do replay
     */
    @PostMapping("/events/replay/type/{eventType}")
    @Operation(summary = "Replay de eventos por tipo", description = "Replay de eventos armazenados por tipo")
    public ResponseEntity<Map<String, Object>> replayEventsByType(@PathVariable final String eventType) {
        log.info("Replay de eventos por tipo via API: {}", eventType);
        try {
            List<BaseEvent> events = eventStore.replayEventsByType(eventType);
            Map<String, Object> response = Map.of("status", "SUCCESS", "eventType", eventType, "eventsCount", events.size(), "timestamp", LocalDateTime.now(), "message", "Replay de eventos iniciado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao fazer replay de eventos por tipo via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "eventType", eventType, "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Replay de eventos por período.
     *
     * @param startTime Início para replay
     * @param endTime   Fim para replay
     * @return ResponseEntity com status do replay
     */
    @PostMapping("/events/replay/period")
    @Operation(summary = "Replay de eventos por período", description = "Replay de eventos em um período específico")
    public ResponseEntity<Map<String, Object>> replayEventsByPeriod(@RequestParam final LocalDateTime startTime, @RequestParam final LocalDateTime endTime) {
        log.info("Replay de eventos por período via API: {} a {}", startTime, endTime);
        try {
            List<BaseEvent> events = eventStore.replayEventsByPeriod(startTime, endTime);
            Map<String, Object> response = Map.of("status", "SUCCESS", "startTime", startTime, "endTime", endTime, "eventsCount", events.size(), "timestamp", LocalDateTime.now(), "message", "Replay de eventos iniciado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao fazer replay de eventos por período via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "startTime", startTime, "endTime", endTime, "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== ESTATÍSTICAS ==========
    /**
     * Obtém estatísticas do Event Hub.
     *
     * @return ResponseEntity com estatísticas completas
     */
    @GetMapping("/statistics")
    @Operation(summary = "Estatísticas do Event Hub", description = "Retorna estatísticas completas do Event Hub")
    public ResponseEntity<Map<String, Object>> getEventHubStatistics() {
        log.info("Obtendo estatísticas do Event Hub via API");
        try {
            Map<String, Object> statistics = Map.of("eventHub", eventHub.getStatistics(), "router", eventRouter.getRoutingStatistics(), "transformer", eventTransformer.getTransformationStatistics(), "validator", eventValidator.getValidationStatistics(), "dlq", deadLetterQueue.getDLQStatistics(), "eventStore", eventStore.getEventStoreStatistics(), "timestamp", LocalDateTime.now());
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas do Event Hub via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtém estatísticas do Event Router.
     *
     * @return ResponseEntity com estatísticas de roteamento
     */
    @GetMapping("/statistics/router")
    @Operation(summary = "Estatísticas do Event Router", description = "Retorna estatísticas do roteamento de eventos")
    public ResponseEntity<Map<String, Object>> getRouterStatistics() {
        log.info("Obtendo estatísticas do Event Router via API");
        try {
            Map<String, Object> statistics = eventRouter.getRoutingStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas do Event Router via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtém estatísticas do Event Store.
     *
     * @return ResponseEntity com estatísticas de armazenamento
     */
    @GetMapping("/statistics/eventstore")
    @Operation(summary = "Estatísticas do Event Store", description = "Retorna estatísticas do armazenamento de eventos")
    public ResponseEntity<Map<String, Object>> getEventStoreStatistics() {
        log.info("Obtendo estatísticas do Event Store via API");
        try {
            Map<String, Object> statistics = eventStore.getEventStoreStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error("Erro ao obter estatísticas do Event Store via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ========== GESTÃO DE ROTAS ==========
    /**
     * Obtém todas as regras de roteamento.
     *
     * @return Mapa com regras de roteamento
     */
    @GetMapping("/routes")
    @Operation(summary = "Regras de roteamento", description = "Retorna todas as regras de roteamento de eventos")
    public ResponseEntity<Map<String, List<String>>> getAllRoutingRules() {
        log.info("Obtendo regras de roteamento via API");
        try {
            Map<String, List<String>> rules = eventRouter.getAllRoutingRules();
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            log.error("Erro ao obter regras de roteamento via API: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Adiciona nova regra de roteamento.
     *
     * @param eventType Tipo do evento
     * @param routes    Lista de rotas/tópicos
     * @return ResponseEntity com status da operação
     */
    @PostMapping("/routes")
    @Operation(summary = "Adicionar regra de roteamento", description = "Adiciona nova regra de roteamento de eventos")
    public ResponseEntity<Map<String, Object>> addRoutingRule(@RequestParam final String eventType, @RequestParam final List<String> routes) {
        log.info("Adicionando regra de roteamento via API: {} -> {}", eventType, routes);
        try {
            eventRouter.addRoutingRule(eventType, routes);
            Map<String, Object> response = Map.of("status", "SUCCESS", "eventType", eventType, "routes", routes, "timestamp", LocalDateTime.now(), "message", "Regra de roteamento adicionada com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao adicionar regra de roteamento via API: {}", e.getMessage());
            Map<String, Object> response = Map.of("status", "ERROR", "eventType", eventType, "error", e.getMessage(), "timestamp", LocalDateTime.now());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Creates a new {@code EventHubController} instance.
     *
     * @param eventHub Event Hub centralizado para publicação de eventos.
     * @param eventRouter Roteador de eventos para determinar destinos.
     * @param eventTransformer Transformador de eventos para conversões.
     * @param eventValidator Validador de eventos para verificações.
     * @param deadLetterQueue Fila de eventos falhados para reprocessamento.
     * @param eventStore Armazenamento persistente de eventos.
     */
    @java.lang.SuppressWarnings("all")
    public EventHubController(final EventHub eventHub, final EventRouter eventRouter, final EventTransformer eventTransformer, final EventValidator eventValidator, final DeadLetterQueue deadLetterQueue, final EventStore eventStore) {
        this.eventHub = eventHub;
        this.eventRouter = eventRouter;
        this.eventTransformer = eventTransformer;
        this.eventValidator = eventValidator;
        this.deadLetterQueue = deadLetterQueue;
        this.eventStore = eventStore;
    }
}
