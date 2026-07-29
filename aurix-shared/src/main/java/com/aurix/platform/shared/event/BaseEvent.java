package com.aurix.platform.shared.event;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe base para todos os eventos da plataforma Aurix.
 */
public class BaseEvent {
    /**
     * Identificador único do evento.
     */
    private String eventId;
    /**
     * Tipo do evento (ex: CONTA_CRIADA).
     */
    private String eventType;
    /**
     * Origem do evento.
     */
    private String source;
    /**
     * Timestamp de ocorrência.
     */
    private LocalDateTime timestamp;
    /**
     * ID de correlação para rastreamento.
     */
    private String correlationId;
    /**
     * Metadados suplementares.
     */
    private Map<String, Object> metadata;

    /**
     * Construtor base para eventos.
     *
     * @param eventType Tipo do evento
     * @param source    Origem do evento
     */
    protected BaseEvent(final String eventType, final String source) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.eventType = eventType;
        this.source = source;
        this.timestamp = LocalDateTime.now();
        this.correlationId = java.util.UUID.randomUUID().toString();
    }


    @java.lang.SuppressWarnings("all")
    public static class BaseEventBuilder {
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
        private Map<String, Object> metadata;

        @java.lang.SuppressWarnings("all")
        BaseEventBuilder() {
        }

        /**
         * Identificador único do evento.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder eventId(final String eventId) {
            this.eventId = eventId;
            return this;
        }

        /**
         * Tipo do evento (ex: CONTA_CRIADA).
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder eventType(final String eventType) {
            this.eventType = eventType;
            return this;
        }

        /**
         * Origem do evento.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder source(final String source) {
            this.source = source;
            return this;
        }

        /**
         * Timestamp de ocorrência.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder timestamp(final LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * ID de correlação para rastreamento.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder correlationId(final String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        /**
         * Metadados suplementares.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent.BaseEventBuilder metadata(final Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public BaseEvent build() {
            return new BaseEvent(this.eventId, this.eventType, this.source, this.timestamp, this.correlationId, this.metadata);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "BaseEvent.BaseEventBuilder(eventId=" + this.eventId + ", eventType=" + this.eventType + ", source=" + this.source + ", timestamp=" + this.timestamp + ", correlationId=" + this.correlationId + ", metadata=" + this.metadata + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static BaseEvent.BaseEventBuilder builder() {
        return new BaseEvent.BaseEventBuilder();
    }

    /**
     * Identificador único do evento.
     */
    @java.lang.SuppressWarnings("all")
    public String getEventId() {
        return this.eventId;
    }

    /**
     * Tipo do evento (ex: CONTA_CRIADA).
     */
    @java.lang.SuppressWarnings("all")
    public String getEventType() {
        return this.eventType;
    }

    /**
     * Origem do evento.
     */
    @java.lang.SuppressWarnings("all")
    public String getSource() {
        return this.source;
    }

    /**
     * Timestamp de ocorrência.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * ID de correlação para rastreamento.
     */
    @java.lang.SuppressWarnings("all")
    public String getCorrelationId() {
        return this.correlationId;
    }

    /**
     * Metadados suplementares.
     */
    @java.lang.SuppressWarnings("all")
    public Map<String, Object> getMetadata() {
        return this.metadata;
    }

    /**
     * Identificador único do evento.
     */
    @java.lang.SuppressWarnings("all")
    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    /**
     * Tipo do evento (ex: CONTA_CRIADA).
     */
    @java.lang.SuppressWarnings("all")
    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    /**
     * Origem do evento.
     */
    @java.lang.SuppressWarnings("all")
    public void setSource(final String source) {
        this.source = source;
    }

    /**
     * Timestamp de ocorrência.
     */
    @java.lang.SuppressWarnings("all")
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * ID de correlação para rastreamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Metadados suplementares.
     */
    @java.lang.SuppressWarnings("all")
    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseEvent)) return false;
        final BaseEvent other = (BaseEvent) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof BaseEvent;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
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
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "BaseEvent(eventId=" + this.getEventId() + ", eventType=" + this.getEventType() + ", source=" + this.getSource() + ", timestamp=" + this.getTimestamp() + ", correlationId=" + this.getCorrelationId() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public BaseEvent() {
    }

    /**
     * Creates a new {@code BaseEvent} instance.
     *
     * @param eventId Identificador único do evento.
     * @param eventType Tipo do evento (ex: CONTA_CRIADA).
     * @param source Origem do evento.
     * @param timestamp Timestamp de ocorrência.
     * @param correlationId ID de correlação para rastreamento.
     * @param metadata Metadados suplementares.
     */
    @java.lang.SuppressWarnings("all")
    public BaseEvent(final String eventId, final String eventType, final String source, final LocalDateTime timestamp, final String correlationId, final Map<String, Object> metadata) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.source = source;
        this.timestamp = timestamp;
        this.correlationId = correlationId;
        this.metadata = metadata;
    }
}
