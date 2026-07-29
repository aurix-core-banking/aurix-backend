package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Dead Letter Queue para eventos falhados.
 *
 * Gerencia eventos que falharam no processamento para análise e
 * reprocessamento.
 */
@Component
public class DeadLetterQueue {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeadLetterQueue.class);
    /**
     * Template do Kafka para operações de DLQ.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * Contador de eventos enviados para DLQ.
     */
    private final AtomicLong totalDLQEvents = new AtomicLong(0);
    /**
     * Número padrão de tentativas de reprocessamento.
     */
    private static final int DEFAULT_MAX_RETRIES = 3;

    /**
     * Envia evento para Dead Letter Queue.
     *
     * @param event         Evento original
     * @param failureReason Razão da falha
     */
    public void sendToDLQ(final BaseEvent event, final String failureReason) {
        totalDLQEvents.incrementAndGet();
        log.warn("Enviando evento para DLQ: EventId={}, Reason={}", event.getEventId(), failureReason);
        try {
            // Criar evento DLQ
            DLQEvent dlqEvent = DLQEvent.builder().originalEvent(event).failureReason(failureReason).failureTimestamp(LocalDateTime.now()).retryCount(0).maxRetries(DEFAULT_MAX_RETRIES).build();
            // Adicionar metadados
            Map<String, Object> metadata = dlqEvent.getMetadata();
            metadata.put("dlqEvent", true);
            metadata.put("originalEventType", event.getEventType());
            metadata.put("originalEventId", event.getEventId());
            metadata.put("failureReason", failureReason);
            metadata.put("dlqTimestamp", LocalDateTime.now());
            // Publicar no tópico DLQ
            kafkaTemplate.send(Topics.DLQ, event.getEventId(), dlqEvent);
            log.info("Evento enviado para DLQ com sucesso: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Erro ao enviar evento para DLQ: {}", e.getMessage());
            // Em caso de erro crítico, salvar localmente ou em arquivo
            saveEventLocally(event, failureReason, e.getMessage());
        }
    }

    /**
     * Reprocessa evento da DLQ.
     *
     * @param dlqEvent Evento da DLQ para reprocessamento
     */
    public void reprocessEvent(final DLQEvent dlqEvent) {
        log.info("Reprocessando evento da DLQ: OriginalEventId={}, " + "RetryCount={}", dlqEvent.getOriginalEvent().getEventId(), dlqEvent.getRetryCount());
        try {
            // Incrementar contador de tentativas
            dlqEvent.setRetryCount(dlqEvent.getRetryCount() + 1);
            // Verificar se ainda pode tentar
            if (dlqEvent.getRetryCount() > dlqEvent.getMaxRetries()) {
                log.error("Máximo de tentativas atingido para evento: {}", dlqEvent.getOriginalEvent().getEventId());
                // Enviar para DLQ permanente
                sendToPermanentDLQ(dlqEvent);
                return;
            }
            // Adicionar metadados de reprocessamento
            Map<String, Object> metadata = dlqEvent.getMetadata();
            metadata.put("reprocessedAt", LocalDateTime.now());
            metadata.put("retryCount", dlqEvent.getRetryCount());
            metadata.put("maxRetries", dlqEvent.getMaxRetries());
            // Publicar evento original novamente
            kafkaTemplate.send(Topics.REPROCESS, dlqEvent.getOriginalEvent().getEventId(), dlqEvent.getOriginalEvent());
            log.info("Evento reprocessado com sucesso: {}", dlqEvent.getOriginalEvent().getEventId());
        } catch (Exception e) {
            log.error("Erro ao reprocessar evento da DLQ: {}", e.getMessage());
            // Enviar para DLQ permanente em caso de erro
            sendToPermanentDLQ(dlqEvent);
        }
    }

    /**
     * Envia evento para DLQ permanente.
     *
     * @param dlqEvent Evento que excedeu retentativas
     */
    private void sendToPermanentDLQ(final DLQEvent dlqEvent) {
        log.error("Enviando evento para DLQ permanente: {}", dlqEvent.getOriginalEvent().getEventId());
        try {
            // Adicionar metadados de DLQ permanente
            Map<String, Object> metadata = dlqEvent.getMetadata();
            metadata.put("permanentDLQ", true);
            metadata.put("permanentDLQTimestamp", LocalDateTime.now());
            metadata.put("finalRetryCount", dlqEvent.getRetryCount());
            // Publicar no tópico DLQ permanente
            kafkaTemplate.send(Topics.DLQ_PERMANENT, dlqEvent.getOriginalEvent().getEventId(), dlqEvent);
            log.error("Evento enviado para DLQ permanente: {}", dlqEvent.getOriginalEvent().getEventId());
        } catch (Exception e) {
            log.error("Erro crítico ao enviar para DLQ permanente: {}", e.getMessage());
            // Salvar localmente como último recurso
            saveEventLocally(dlqEvent.getOriginalEvent(), "PERMANENT_DLQ_FAILED", e.getMessage());
        }
    }

    /**
     * Salva evento localmente em caso de falha crítica.
     *
     * @param event         Evento original
     * @param failureReason Razão da falha
     * @param errorMessage  Mensagem de erro
     */
    private void saveEventLocally(final BaseEvent event, final String failureReason, final String errorMessage) {
        try {
            // Implementar salvamento local (arquivo, banco, etc.)
            log.error("Salvando evento localmente: EventId={}, Reason={}, " + "Error={}", event.getEventId(), failureReason, errorMessage);
        } catch (
        // Por enquanto, apenas log
        // Em produção, implementar salvamento em arquivo ou banco
        Exception e) {
            log.error("Erro crítico ao salvar evento localmente: {}", e.getMessage());
        }
    }

    /**
     * Obtém estatísticas da DLQ.
     *
     * @return Mapa com estatísticas
     */
    public Map<String, Object> getDLQStatistics() {
        return Map.of("totalDLQEvents", totalDLQEvents.get(), "timestamp", LocalDateTime.now());
    }

    /**
     * Obtém total de eventos na DLQ.
     *
     * @return Total de eventos
     */
    public long getTotalDLQEvents() {
        return totalDLQEvents.get();
    }

    /**
     * Limpa DLQ (usar com cuidado).
     */
    public void clearDLQ() {
        log.warn("Limpando DLQ - Esta operação deve ser usada com cuidado");
        totalDLQEvents.set(0);
    }


    /**
     * Classe para eventos da DLQ.
     */
    public static class DLQEvent {
        /**
         * Evento original.
         */
        private BaseEvent originalEvent;
        /**
         * Razão da falha.
         */
        private String failureReason;
        /**
         * Timestamp da falha.
         */
        private LocalDateTime failureTimestamp;
        /**
         * Contador de tentativas.
         */
        private int retryCount;
        /**
         * Máximo de tentativas permitidas.
         */
        private int maxRetries;
        /**
         * Metadados do evento DLQ.
         */
        private Map<String, Object> metadata;

        @java.lang.SuppressWarnings("all")
        private static Map<String, Object> $default$metadata() {
            return new java.util.HashMap<>();
        }


        @java.lang.SuppressWarnings("all")
        public static class DLQEventBuilder {
            @java.lang.SuppressWarnings("all")
            private BaseEvent originalEvent;
            @java.lang.SuppressWarnings("all")
            private String failureReason;
            @java.lang.SuppressWarnings("all")
            private LocalDateTime failureTimestamp;
            @java.lang.SuppressWarnings("all")
            private int retryCount;
            @java.lang.SuppressWarnings("all")
            private int maxRetries;
            @java.lang.SuppressWarnings("all")
            private boolean metadata$set;
            @java.lang.SuppressWarnings("all")
            private Map<String, Object> metadata$value;

            @java.lang.SuppressWarnings("all")
            DLQEventBuilder() {
            }

            /**
             * Evento original.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder originalEvent(final BaseEvent originalEvent) {
                this.originalEvent = originalEvent;
                return this;
            }

            /**
             * Razão da falha.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder failureReason(final String failureReason) {
                this.failureReason = failureReason;
                return this;
            }

            /**
             * Timestamp da falha.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder failureTimestamp(final LocalDateTime failureTimestamp) {
                this.failureTimestamp = failureTimestamp;
                return this;
            }

            /**
             * Contador de tentativas.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder retryCount(final int retryCount) {
                this.retryCount = retryCount;
                return this;
            }

            /**
             * Máximo de tentativas permitidas.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder maxRetries(final int maxRetries) {
                this.maxRetries = maxRetries;
                return this;
            }

            /**
             * Metadados do evento DLQ.
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent.DLQEventBuilder metadata(final Map<String, Object> metadata) {
                this.metadata$value = metadata;
                metadata$set = true;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public DeadLetterQueue.DLQEvent build() {
                Map<String, Object> metadata$value = this.metadata$value;
                if (!this.metadata$set) metadata$value = DLQEvent.$default$metadata();
                return new DeadLetterQueue.DLQEvent(this.originalEvent, this.failureReason, this.failureTimestamp, this.retryCount, this.maxRetries, metadata$value);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "DeadLetterQueue.DLQEvent.DLQEventBuilder(originalEvent=" + this.originalEvent + ", failureReason=" + this.failureReason + ", failureTimestamp=" + this.failureTimestamp + ", retryCount=" + this.retryCount + ", maxRetries=" + this.maxRetries + ", metadata$value=" + this.metadata$value + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static DeadLetterQueue.DLQEvent.DLQEventBuilder builder() {
            return new DeadLetterQueue.DLQEvent.DLQEventBuilder();
        }

        /**
         * Evento original.
         */
        @java.lang.SuppressWarnings("all")
        public BaseEvent getOriginalEvent() {
            return this.originalEvent;
        }

        /**
         * Razão da falha.
         */
        @java.lang.SuppressWarnings("all")
        public String getFailureReason() {
            return this.failureReason;
        }

        /**
         * Timestamp da falha.
         */
        @java.lang.SuppressWarnings("all")
        public LocalDateTime getFailureTimestamp() {
            return this.failureTimestamp;
        }

        /**
         * Contador de tentativas.
         */
        @java.lang.SuppressWarnings("all")
        public int getRetryCount() {
            return this.retryCount;
        }

        /**
         * Máximo de tentativas permitidas.
         */
        @java.lang.SuppressWarnings("all")
        public int getMaxRetries() {
            return this.maxRetries;
        }

        /**
         * Metadados do evento DLQ.
         */
        @java.lang.SuppressWarnings("all")
        public Map<String, Object> getMetadata() {
            return this.metadata;
        }

        /**
         * Evento original.
         */
        @java.lang.SuppressWarnings("all")
        public void setOriginalEvent(final BaseEvent originalEvent) {
            this.originalEvent = originalEvent;
        }

        /**
         * Razão da falha.
         */
        @java.lang.SuppressWarnings("all")
        public void setFailureReason(final String failureReason) {
            this.failureReason = failureReason;
        }

        /**
         * Timestamp da falha.
         */
        @java.lang.SuppressWarnings("all")
        public void setFailureTimestamp(final LocalDateTime failureTimestamp) {
            this.failureTimestamp = failureTimestamp;
        }

        /**
         * Contador de tentativas.
         */
        @java.lang.SuppressWarnings("all")
        public void setRetryCount(final int retryCount) {
            this.retryCount = retryCount;
        }

        /**
         * Máximo de tentativas permitidas.
         */
        @java.lang.SuppressWarnings("all")
        public void setMaxRetries(final int maxRetries) {
            this.maxRetries = maxRetries;
        }

        /**
         * Metadados do evento DLQ.
         */
        @java.lang.SuppressWarnings("all")
        public void setMetadata(final Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof DeadLetterQueue.DLQEvent)) return false;
            final DeadLetterQueue.DLQEvent other = (DeadLetterQueue.DLQEvent) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.getRetryCount() != other.getRetryCount()) return false;
            if (this.getMaxRetries() != other.getMaxRetries()) return false;
            final java.lang.Object this$originalEvent = this.getOriginalEvent();
            final java.lang.Object other$originalEvent = other.getOriginalEvent();
            if (this$originalEvent == null ? other$originalEvent != null : !this$originalEvent.equals(other$originalEvent)) return false;
            final java.lang.Object this$failureReason = this.getFailureReason();
            final java.lang.Object other$failureReason = other.getFailureReason();
            if (this$failureReason == null ? other$failureReason != null : !this$failureReason.equals(other$failureReason)) return false;
            final java.lang.Object this$failureTimestamp = this.getFailureTimestamp();
            final java.lang.Object other$failureTimestamp = other.getFailureTimestamp();
            if (this$failureTimestamp == null ? other$failureTimestamp != null : !this$failureTimestamp.equals(other$failureTimestamp)) return false;
            final java.lang.Object this$metadata = this.getMetadata();
            final java.lang.Object other$metadata = other.getMetadata();
            if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof DeadLetterQueue.DLQEvent;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + this.getRetryCount();
            result = result * PRIME + this.getMaxRetries();
            final java.lang.Object $originalEvent = this.getOriginalEvent();
            result = result * PRIME + ($originalEvent == null ? 43 : $originalEvent.hashCode());
            final java.lang.Object $failureReason = this.getFailureReason();
            result = result * PRIME + ($failureReason == null ? 43 : $failureReason.hashCode());
            final java.lang.Object $failureTimestamp = this.getFailureTimestamp();
            result = result * PRIME + ($failureTimestamp == null ? 43 : $failureTimestamp.hashCode());
            final java.lang.Object $metadata = this.getMetadata();
            result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "DeadLetterQueue.DLQEvent(originalEvent=" + this.getOriginalEvent() + ", failureReason=" + this.getFailureReason() + ", failureTimestamp=" + this.getFailureTimestamp() + ", retryCount=" + this.getRetryCount() + ", maxRetries=" + this.getMaxRetries() + ", metadata=" + this.getMetadata() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public DLQEvent() {
            this.metadata = DLQEvent.$default$metadata();
        }

        /**
         * Creates a new {@code DLQEvent} instance.
         *
         * @param originalEvent Evento original.
         * @param failureReason Razão da falha.
         * @param failureTimestamp Timestamp da falha.
         * @param retryCount Contador de tentativas.
         * @param maxRetries Máximo de tentativas permitidas.
         * @param metadata Metadados do evento DLQ.
         */
        @java.lang.SuppressWarnings("all")
        public DLQEvent(final BaseEvent originalEvent, final String failureReason, final LocalDateTime failureTimestamp, final int retryCount, final int maxRetries, final Map<String, Object> metadata) {
            this.originalEvent = originalEvent;
            this.failureReason = failureReason;
            this.failureTimestamp = failureTimestamp;
            this.retryCount = retryCount;
            this.maxRetries = maxRetries;
            this.metadata = metadata;
        }
    }

    /**
     * Creates a new {@code DeadLetterQueue} instance.
     *
     * @param kafkaTemplate Template do Kafka para operações de DLQ.
     */
    @java.lang.SuppressWarnings("all")
    public DeadLetterQueue(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
