package com.aurix.platform.payments.pix.entity;

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
        PENDING, PROCESSED, FAILED
    }

    public Long getId() {
        return this.id;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public String getEventType() {
        return this.eventType;
    }

    public String getPayload() {
        return this.payload;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return this.processedAt;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setAggregateType(final String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public void setAggregateId(final String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    public void setProcessedAt(final LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
}
