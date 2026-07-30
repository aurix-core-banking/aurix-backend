package com.aurix.platform.banking.core.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events", schema = "aurix")
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String aggregateType;
    @Column(nullable = false)
    private String aggregateId;
    @Column(nullable = false)
    private String eventType;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB", nullable = false)
    private String payload;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private LocalDateTime processedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;


    public enum Status {
        PENDING, PROCESSED, FAILED;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getAggregateType() {
        return this.aggregateType;
    }

    @java.lang.SuppressWarnings("all")
    public String getAggregateId() {
        return this.aggregateId;
    }

    @java.lang.SuppressWarnings("all")
    public String getEventType() {
        return this.eventType;
    }

    @java.lang.SuppressWarnings("all")
    public String getPayload() {
        return this.payload;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getProcessedAt() {
        return this.processedAt;
    }

    @java.lang.SuppressWarnings("all")
    public Status getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setAggregateType(final String aggregateType) {
        this.aggregateType = aggregateType;
    }

    @java.lang.SuppressWarnings("all")
    public void setAggregateId(final String aggregateId) {
        this.aggregateId = aggregateId;
    }

    @java.lang.SuppressWarnings("all")
    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    @java.lang.SuppressWarnings("all")
    public void setPayload(final String payload) {
        this.payload = payload;
    }

    @java.lang.SuppressWarnings("all")
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessedAt(final LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final Status status) {
        this.status = status;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof OutboxEvent)) return false;
        final OutboxEvent other = (OutboxEvent) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$aggregateType = this.getAggregateType();
        final java.lang.Object other$aggregateType = other.getAggregateType();
        if (this$aggregateType == null ? other$aggregateType != null : !this$aggregateType.equals(other$aggregateType)) return false;
        final java.lang.Object this$aggregateId = this.getAggregateId();
        final java.lang.Object other$aggregateId = other.getAggregateId();
        if (this$aggregateId == null ? other$aggregateId != null : !this$aggregateId.equals(other$aggregateId)) return false;
        final java.lang.Object this$eventType = this.getEventType();
        final java.lang.Object other$eventType = other.getEventType();
        if (this$eventType == null ? other$eventType != null : !this$eventType.equals(other$eventType)) return false;
        final java.lang.Object this$payload = this.getPayload();
        final java.lang.Object other$payload = other.getPayload();
        if (this$payload == null ? other$payload != null : !this$payload.equals(other$payload)) return false;
        final java.lang.Object this$createdAt = this.getCreatedAt();
        final java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final java.lang.Object this$processedAt = this.getProcessedAt();
        final java.lang.Object other$processedAt = other.getProcessedAt();
        if (this$processedAt == null ? other$processedAt != null : !this$processedAt.equals(other$processedAt)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof OutboxEvent;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $aggregateType = this.getAggregateType();
        result = result * PRIME + ($aggregateType == null ? 43 : $aggregateType.hashCode());
        final java.lang.Object $aggregateId = this.getAggregateId();
        result = result * PRIME + ($aggregateId == null ? 43 : $aggregateId.hashCode());
        final java.lang.Object $eventType = this.getEventType();
        result = result * PRIME + ($eventType == null ? 43 : $eventType.hashCode());
        final java.lang.Object $payload = this.getPayload();
        result = result * PRIME + ($payload == null ? 43 : $payload.hashCode());
        final java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final java.lang.Object $processedAt = this.getProcessedAt();
        result = result * PRIME + ($processedAt == null ? 43 : $processedAt.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "OutboxEvent(id=" + this.getId() + ", aggregateType=" + this.getAggregateType() + ", aggregateId=" + this.getAggregateId() + ", eventType=" + this.getEventType() + ", payload=" + this.getPayload() + ", createdAt=" + this.getCreatedAt() + ", processedAt=" + this.getProcessedAt() + ", status=" + this.getStatus() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public OutboxEvent() {
    }
}
