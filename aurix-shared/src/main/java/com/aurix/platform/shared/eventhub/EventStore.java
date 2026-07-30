package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Event Store para persistir eventos.
 *
 * Armazena todos os eventos da plataforma para auditoria, replay e análise.
 */
@Component
public class EventStore {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventStore.class);
    private final StoredEventJpaRepository repository;
    private final ObjectMapper objectMapper;
    private static final int RECENT_EVENTS_LIMIT = 10;

    /**
     * Armazena evento no Event Store.
     *
     * @param event Evento a ser armazenado
     */
    public void storeEvent(final BaseEvent event) {
        log.debug("Armazenando evento no Event Store: EventId={}, Type={}", event.getEventId(), event.getEventType());
        try {
            StoredEventEntity entity = new StoredEventEntity();
            entity.setEventId(event.getEventId());
            entity.setEventType(event.getEventType());
            entity.setSource(event.getSource());
            entity.setTimestamp(event.getTimestamp());
            entity.setCorrelationId(event.getCorrelationId());
            entity.setMetadataJson(objectMapper.writeValueAsString(
                event.getMetadata() != null ? event.getMetadata() : new HashMap<>()));
            entity.setPayload(objectMapper.writeValueAsString(event));
            entity.setStoredAt(LocalDateTime.now());
            entity.setVersion(1L);
            repository.save(entity);
            log.debug("Evento armazenado com sucesso: {}", event.getEventId());
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar evento para o Event Store: {}", e.getMessage());
        }
    }

    /**
     * Busca eventos por tipo.
     *
     * @param eventType Tipo do evento
     * @return Lista de eventos encontrados
     */
    public List<StoredEvent> getEventsByType(final String eventType) {
        log.debug("Buscando eventos por tipo: {}", eventType);
        return repository.findByEventTypeOrderByTimestampAsc(eventType).stream()
            .map(this::toStoredEvent)
            .collect(Collectors.toList());
    }

    /**
     * Busca evento por ID.
     *
     * @param eventId ID do evento
     * @return Evento encontrado ou Optional vazio
     */
    public Optional<StoredEvent> getEventById(final String eventId) {
        log.debug("Buscando evento por ID: {}", eventId);
        return repository.findByEventId(eventId).stream()
            .findFirst()
            .map(this::toStoredEvent);
    }

    /**
     * Busca eventos por período.
     *
     * @param startTime Início do período
     * @param endTime   Fim do período
     * @return Lista de eventos encontrados
     */
    public List<StoredEvent> getEventsByPeriod(final LocalDateTime startTime, final LocalDateTime endTime) {
        log.debug("Buscando eventos por período: {} a {}", startTime, endTime);
        return repository.findByTimestampBetweenOrderByTimestampAsc(startTime, endTime).stream()
            .map(this::toStoredEvent)
            .collect(Collectors.toList());
    }

    /**
     * Busca eventos por source.
     *
     * @param source Fonte do evento
     * @return Lista de eventos encontrados
     */
    public List<StoredEvent> getEventsBySource(final String source) {
        log.debug("Buscando eventos por source: {}", source);
        return repository.findBySourceOrderByTimestampAsc(source).stream()
            .map(this::toStoredEvent)
            .collect(Collectors.toList());
    }

    /**
     * Busca eventos por correlation ID.
     *
     * @param correlationId ID de correlação
     * @return Lista de eventos encontrados
     */
    public List<StoredEvent> getEventsByCorrelationId(final String correlationId) {
        log.debug("Buscando eventos por correlation ID: {}", correlationId);
        return repository.findByCorrelationIdOrderByTimestampAsc(correlationId).stream()
            .map(this::toStoredEvent)
            .collect(Collectors.toList());
    }

    /**
     * Replay de eventos por tipo.
     *
     * @param eventType Tipo para replay
     * @return Lista de eventos originais
     */
    public List<BaseEvent> replayEventsByType(final String eventType) {
        log.info("Replay de eventos por tipo: {}", eventType);
        return getEventsByType(eventType).stream()
            .map(StoredEvent::getEventData)
            .collect(Collectors.toList());
    }

    /**
     * Replay de eventos por período.
     *
     * @param startTime Início para replay
     * @param endTime   Fim para replay
     * @return Lista de eventos originais
     */
    public List<BaseEvent> replayEventsByPeriod(final LocalDateTime startTime, final LocalDateTime endTime) {
        log.info("Replay de eventos por período: {} a {}", startTime, endTime);
        return getEventsByPeriod(startTime, endTime).stream()
            .map(StoredEvent::getEventData)
            .collect(Collectors.toList());
    }

    private StoredEvent toStoredEvent(StoredEventEntity entity) {
        try {
            BaseEvent eventData = objectMapper.readValue(entity.getPayload(), BaseEvent.class);
            Map<String, Object> metadata = entity.getMetadataJson() != null
                ? objectMapper.readValue(entity.getMetadataJson(), Map.class)
                : new HashMap<>();
            return StoredEvent.builder()
                .eventId(entity.getEventId())
                .eventType(entity.getEventType())
                .source(entity.getSource())
                .timestamp(entity.getTimestamp())
                .correlationId(entity.getCorrelationId())
                .metadata(metadata)
                .eventData(eventData)
                .storedAt(entity.getStoredAt())
                .version(entity.getVersion())
                .build();
        } catch (JsonProcessingException e) {
            log.error("Erro ao desserializar evento do Event Store: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Obtém estatísticas do Event Store.
     *
     * @return Mapa com estatísticas
     */
    public Map<String, Object> getEventStoreStatistics() {
        Map<String, Object> stats = new HashMap<>();
        long total = repository.count();
        stats.put("totalEventsStored", total);
        stats.put("totalEventTypes", repository.findAll().stream()
            .map(StoredEventEntity::getEventType)
            .distinct()
            .count());
        Map<String, Long> eventsByType = repository.findAll().stream()
            .collect(Collectors.groupingBy(StoredEventEntity::getEventType, Collectors.counting()));
        stats.put("eventsByType", eventsByType);
        List<StoredEvent> recentEvents = repository.findAll().stream()
            .sorted(Comparator.comparing(StoredEventEntity::getStoredAt).reversed())
            .limit(RECENT_EVENTS_LIMIT)
            .map(this::toStoredEvent)
            .collect(Collectors.toList());
        stats.put("recentEvents", recentEvents);
        stats.put("lastUpdated", LocalDateTime.now());
        return stats;
    }

    /**
     * Limpa Event Store (usar com cuidado).
     */
    public void clearEventStore() {
        log.warn("Limpando Event Store - Esta operação deve ser usada com cuidado");
        repository.deleteAll();
    }

    /**
     * Obtém total de eventos armazenados.
     *
     * @return Total de eventos
     */
    public long getTotalEventsStored() {
        return repository.count();
    }


    /**
     * Classe para eventos armazenados.
     */
    public static class StoredEvent {
        /**
         * ID do evento.
         */
        private String eventId;
        /**
         * Tipo do evento.
         */
        private String eventType;
        /**
         * Fonte do evento.
         */
        private String source;
        /**
         * Timestamp original do evento.
         */
        private LocalDateTime timestamp;
        /**
         * ID de correlação.
         */
        private String correlationId;
        /**
         * Metadados do evento.
         */
        private Map<String, Object> metadata;
        /**
         * Dados originais do evento.
         */
        private BaseEvent eventData;
        /**
         * Timestamp do armazenamento.
         */
        private LocalDateTime storedAt;
        /**
         * Versão do esquema de persistência.
         */
        private Long version;

        @java.lang.SuppressWarnings("all")
        private static Map<String, Object> $default$metadata() {
            return new HashMap<>();
        }

        @java.lang.SuppressWarnings("all")
        private static Long $default$version() {
            return 1L;
        }


        @java.lang.SuppressWarnings("all")
        public static class StoredEventBuilder {
            @java.lang.SuppressWarnings("all")
            private String eventId;
            @java.lang.SuppressWarnings("all")
            private String eventType;
            @java.lang.SuppressWarnings("all")
            private String source;
            @java.lang.SuppressWarnings("all")
            private LocalDateTime timestamp;
            @java.lang.SuppressWarnings("all")
            private String correlationId;
            @java.lang.SuppressWarnings("all")
            private boolean metadata$set;
            @java.lang.SuppressWarnings("all")
            private Map<String, Object> metadata$value;
            @java.lang.SuppressWarnings("all")
            private BaseEvent eventData;
            @java.lang.SuppressWarnings("all")
            private LocalDateTime storedAt;
            @java.lang.SuppressWarnings("all")
            private boolean version$set;
            @java.lang.SuppressWarnings("all")
            private Long version$value;

            @java.lang.SuppressWarnings("all")
            StoredEventBuilder() {
            }

            /**
             * ID do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder eventId(final String eventId) {
                this.eventId = eventId;
                return this;
            }

            /**
             * Tipo do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder eventType(final String eventType) {
                this.eventType = eventType;
                return this;
            }

            /**
             * Fonte do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder source(final String source) {
                this.source = source;
                return this;
            }

            /**
             * Timestamp original do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder timestamp(final LocalDateTime timestamp) {
                this.timestamp = timestamp;
                return this;
            }

            /**
             * ID de correlação.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder correlationId(final String correlationId) {
                this.correlationId = correlationId;
                return this;
            }

            /**
             * Metadados do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder metadata(final Map<String, Object> metadata) {
                this.metadata$value = metadata;
                metadata$set = true;
                return this;
            }

            /**
             * Dados originais do evento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder eventData(final BaseEvent eventData) {
                this.eventData = eventData;
                return this;
            }

            /**
             * Timestamp do armazenamento.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder storedAt(final LocalDateTime storedAt) {
                this.storedAt = storedAt;
                return this;
            }

            /**
             * Versão do esquema de persistência.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent.StoredEventBuilder version(final Long version) {
                this.version$value = version;
                version$set = true;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public EventStore.StoredEvent build() {
                Map<String, Object> metadata$value = this.metadata$value;
                if (!this.metadata$set) metadata$value = StoredEvent.$default$metadata();
                Long version$value = this.version$value;
                if (!this.version$set) version$value = StoredEvent.$default$version();
                return new EventStore.StoredEvent(this.eventId, this.eventType, this.source, this.timestamp, this.correlationId, metadata$value, this.eventData, this.storedAt, version$value);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "EventStore.StoredEvent.StoredEventBuilder(eventId=" + this.eventId + ", eventType=" + this.eventType + ", source=" + this.source + ", timestamp=" + this.timestamp + ", correlationId=" + this.correlationId + ", metadata$value=" + this.metadata$value + ", eventData=" + this.eventData + ", storedAt=" + this.storedAt + ", version$value=" + this.version$value + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static EventStore.StoredEvent.StoredEventBuilder builder() {
            return new EventStore.StoredEvent.StoredEventBuilder();
        }

        /**
         * ID do evento.
         */
        @java.lang.SuppressWarnings("all")
        public String getEventId() {
            return this.eventId;
        }

        /**
         * Tipo do evento.
         */
        @java.lang.SuppressWarnings("all")
        public String getEventType() {
            return this.eventType;
        }

        /**
         * Fonte do evento.
         */
        @java.lang.SuppressWarnings("all")
        public String getSource() {
            return this.source;
        }

        /**
         * Timestamp original do evento.
         */
        @java.lang.SuppressWarnings("all")
        public LocalDateTime getTimestamp() {
            return this.timestamp;
        }

        /**
         * ID de correlação.
         */
        @java.lang.SuppressWarnings("all")
        public String getCorrelationId() {
            return this.correlationId;
        }

        /**
         * Metadados do evento.
         */
        @java.lang.SuppressWarnings("all")
        public Map<String, Object> getMetadata() {
            return this.metadata;
        }

        /**
         * Dados originais do evento.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent getEventData() {
            return this.eventData;
        }

        /**
         * Timestamp do armazenamento.
         */
        @java.lang.SuppressWarnings("all")
        public LocalDateTime getStoredAt() {
            return this.storedAt;
        }

        /**
         * Versão do esquema de persistência.
         */
        @java.lang.SuppressWarnings("all")
        public Long getVersion() {
            return this.version;
        }

        /**
         * ID do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setEventId(final String eventId) {
            this.eventId = eventId;
        }

        /**
         * Tipo do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setEventType(final String eventType) {
            this.eventType = eventType;
        }

        /**
         * Fonte do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setSource(final String source) {
            this.source = source;
        }

        /**
         * Timestamp original do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setTimestamp(final LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        /**
         * ID de correlação.
         */
        @java.lang.SuppressWarnings("all")
        public void setCorrelationId(final String correlationId) {
            this.correlationId = correlationId;
        }

        /**
         * Metadados do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setMetadata(final Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        /**
         * Dados originais do evento.
         */
        @java.lang.SuppressWarnings("all")
        public void setEventData(final BaseEvent eventData) {
            this.eventData = eventData;
        }

        /**
         * Timestamp do armazenamento.
         */
        @java.lang.SuppressWarnings("all")
        public void setStoredAt(final LocalDateTime storedAt) {
            this.storedAt = storedAt;
        }

        /**
         * Versão do esquema de persistência.
         */
        @java.lang.SuppressWarnings("all")
        public void setVersion(final Long version) {
            this.version = version;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof EventStore.StoredEvent)) return false;
            final EventStore.StoredEvent other = (EventStore.StoredEvent) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$version = this.getVersion();
            final java.lang.Object other$version = other.getVersion();
            if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
            final java.lang.Object this$eventId = this.getEventId();
            final java.lang.Object other$eventId = other.getEventId();
            if (this$eventId == null ? other$eventId != null : !this$eventId.equals(other$eventId)) return false;
            final java.lang.Object this$eventType = this.getEventType();
            final java.lang.Object other$eventType = other.getEventType();
            if (this$eventType == null ? other$eventType != null : !this$eventType.equals(other$eventType)) return false;
            final java.lang.Object this$source = this.getSource();
            final java.lang.Object other$source = other.getSource();
            if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
            final java.lang.Object this$timestamp = this.getTimestamp();
            final java.lang.Object other$timestamp = other.getTimestamp();
            if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
            final java.lang.Object this$correlationId = this.getCorrelationId();
            final java.lang.Object other$correlationId = other.getCorrelationId();
            if (this$correlationId == null ? other$correlationId != null : !this$correlationId.equals(other$correlationId)) return false;
            final java.lang.Object this$metadata = this.getMetadata();
            final java.lang.Object other$metadata = other.getMetadata();
            if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
            final java.lang.Object this$eventData = this.getEventData();
            final java.lang.Object other$eventData = other.getEventData();
            if (this$eventData == null ? other$eventData != null : !this$eventData.equals(other$eventData)) return false;
            final java.lang.Object this$storedAt = this.getStoredAt();
            final java.lang.Object other$storedAt = other.getStoredAt();
            if (this$storedAt == null ? other$storedAt != null : !this$storedAt.equals(other$storedAt)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof EventStore.StoredEvent;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $version = this.getVersion();
            result = result * PRIME + ($version == null ? 43 : $version.hashCode());
            final java.lang.Object $eventId = this.getEventId();
            result = result * PRIME + ($eventId == null ? 43 : $eventId.hashCode());
            final java.lang.Object $eventType = this.getEventType();
            result = result * PRIME + ($eventType == null ? 43 : $eventType.hashCode());
            final java.lang.Object $source = this.getSource();
            result = result * PRIME + ($source == null ? 43 : $source.hashCode());
            final java.lang.Object $timestamp = this.getTimestamp();
            result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
            final java.lang.Object $correlationId = this.getCorrelationId();
            result = result * PRIME + ($correlationId == null ? 43 : $correlationId.hashCode());
            final java.lang.Object $metadata = this.getMetadata();
            result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
            final java.lang.Object $eventData = this.getEventData();
            result = result * PRIME + ($eventData == null ? 43 : $eventData.hashCode());
            final java.lang.Object $storedAt = this.getStoredAt();
            result = result * PRIME + ($storedAt == null ? 43 : $storedAt.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "EventStore.StoredEvent(eventId=" + this.getEventId() + ", eventType=" + this.getEventType() + ", source=" + this.getSource() + ", timestamp=" + this.getTimestamp() + ", correlationId=" + this.getCorrelationId() + ", metadata=" + this.getMetadata() + ", eventData=" + this.getEventData() + ", storedAt=" + this.getStoredAt() + ", version=" + this.getVersion() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public StoredEvent() {
            this.metadata = StoredEvent.$default$metadata();
            this.version = StoredEvent.$default$version();
        }

        /**
         * Creates a new {@code StoredEvent} instance.
         *
         * @param eventId ID do evento.
         * @param eventType Tipo do evento.
         * @param source Fonte do evento.
         * @param timestamp Timestamp original do evento.
         * @param correlationId ID de correlação.
         * @param metadata Metadados do evento.
         * @param eventData Dados originais do evento.
         * @param storedAt Timestamp do armazenamento.
         * @param version Versão do esquema de persistência.
         */
        @java.lang.SuppressWarnings("all")
        public StoredEvent(final String eventId, final String eventType, final String source, final LocalDateTime timestamp, final String correlationId, final Map<String, Object> metadata, final BaseEvent eventData, final LocalDateTime storedAt, final Long version) {
            this.eventId = eventId;
            this.eventType = eventType;
            this.source = source;
            this.timestamp = timestamp;
            this.correlationId = correlationId;
            this.metadata = metadata;
            this.eventData = eventData;
            this.storedAt = storedAt;
            this.version = version;
        }
    }

    @java.lang.SuppressWarnings("all")
    public EventStore(final StoredEventJpaRepository repository, final ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }
}
