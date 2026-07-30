package com.aurix.platform.banking.integration.webhook;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_events", schema = "aurix")
public class WebhookEvent extends BaseEntity {

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "source", length = 200)
    private String source;

    @Lob
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "target_url", nullable = false, length = 512)
    private String targetUrl;

    @Column(name = "target_api_key", length = 256)
    private String targetApiKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private WebhookEventStatus status = WebhookEventStatus.PENDING;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "max_attempts", nullable = false)
    private int maxAttempts = 5;

    @Column(name = "last_attempt_at")
    private LocalDateTime lastAttemptAt;

    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "response_body", length = 2000)
    private String responseBody;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "dispatched_at")
    private LocalDateTime dispatchedAt;

    public enum WebhookEventStatus {
        PENDING, DELIVERED, FAILED, EXHAUSTED
    }

    @java.lang.SuppressWarnings("all")
    public WebhookEvent() {}

    @java.lang.SuppressWarnings("all")
    public String getEventType() { return this.eventType; }

    @java.lang.SuppressWarnings("all")
    public String getSource() { return this.source; }

    @java.lang.SuppressWarnings("all")
    public String getPayload() { return this.payload; }

    @java.lang.SuppressWarnings("all")
    public String getTargetUrl() { return this.targetUrl; }

    @java.lang.SuppressWarnings("all")
    public String getTargetApiKey() { return this.targetApiKey; }

    @java.lang.SuppressWarnings("all")
    public WebhookEventStatus getStatus() { return this.status; }

    @java.lang.SuppressWarnings("all")
    public int getAttempts() { return this.attempts; }

    @java.lang.SuppressWarnings("all")
    public int getMaxAttempts() { return this.maxAttempts; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getLastAttemptAt() { return this.lastAttemptAt; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getNextRetryAt() { return this.nextRetryAt; }

    @java.lang.SuppressWarnings("all")
    public Integer getResponseCode() { return this.responseCode; }

    @java.lang.SuppressWarnings("all")
    public String getResponseBody() { return this.responseBody; }

    @java.lang.SuppressWarnings("all")
    public String getErrorMessage() { return this.errorMessage; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDispatchedAt() { return this.dispatchedAt; }

    @java.lang.SuppressWarnings("all")
    public void setEventType(final String eventType) { this.eventType = eventType; }

    @java.lang.SuppressWarnings("all")
    public void setSource(final String source) { this.source = source; }

    @java.lang.SuppressWarnings("all")
    public void setPayload(final String payload) { this.payload = payload; }

    @java.lang.SuppressWarnings("all")
    public void setTargetUrl(final String targetUrl) { this.targetUrl = targetUrl; }

    @java.lang.SuppressWarnings("all")
    public void setTargetApiKey(final String targetApiKey) { this.targetApiKey = targetApiKey; }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final WebhookEventStatus status) { this.status = status; }

    @java.lang.SuppressWarnings("all")
    public void setAttempts(final int attempts) { this.attempts = attempts; }

    @java.lang.SuppressWarnings("all")
    public void setMaxAttempts(final int maxAttempts) { this.maxAttempts = maxAttempts; }

    @java.lang.SuppressWarnings("all")
    public void setLastAttemptAt(final LocalDateTime lastAttemptAt) { this.lastAttemptAt = lastAttemptAt; }

    @java.lang.SuppressWarnings("all")
    public void setNextRetryAt(final LocalDateTime nextRetryAt) { this.nextRetryAt = nextRetryAt; }

    @java.lang.SuppressWarnings("all")
    public void setResponseCode(final Integer responseCode) { this.responseCode = responseCode; }

    @java.lang.SuppressWarnings("all")
    public void setResponseBody(final String responseBody) { this.responseBody = responseBody; }

    @java.lang.SuppressWarnings("all")
    public void setErrorMessage(final String errorMessage) { this.errorMessage = errorMessage; }

    @java.lang.SuppressWarnings("all")
    public void setDispatchedAt(final LocalDateTime dispatchedAt) { this.dispatchedAt = dispatchedAt; }
}
