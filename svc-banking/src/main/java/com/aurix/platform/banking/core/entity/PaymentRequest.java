package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_requests", schema = "aurix")
public class PaymentRequest extends BaseEntity {

    @Column(name = "token", nullable = false, unique = true, length = 64)
    private String token;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "requester_id", nullable = false)
    private Long requesterId;

    @Column(name = "requester_account_number", length = 20)
    private String requesterAccountNumber;

    @Column(name = "payer_id")
    private Long payerId;

    @Column(name = "payer_account_number", length = 20)
    private String payerAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentRequestStatus status = PaymentRequestStatus.PENDING;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "transaction_id")
    private Long transactionId;

    public PaymentRequest() {}

    public enum PaymentRequestStatus {
        PENDING, PAID, EXPIRED, CANCELLED
    }

    @java.lang.SuppressWarnings("all")
    public String getToken() { return this.token; }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getAmount() { return this.amount; }

    @java.lang.SuppressWarnings("all")
    public String getDescription() { return this.description; }

    @java.lang.SuppressWarnings("all")
    public Long getRequesterId() { return this.requesterId; }

    @java.lang.SuppressWarnings("all")
    public String getRequesterAccountNumber() { return this.requesterAccountNumber; }

    @java.lang.SuppressWarnings("all")
    public Long getPayerId() { return this.payerId; }

    @java.lang.SuppressWarnings("all")
    public String getPayerAccountNumber() { return this.payerAccountNumber; }

    @java.lang.SuppressWarnings("all")
    public PaymentRequestStatus getStatus() { return this.status; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getExpiresAt() { return this.expiresAt; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPaidAt() { return this.paidAt; }

    @java.lang.SuppressWarnings("all")
    public Long getTransactionId() { return this.transactionId; }

    @java.lang.SuppressWarnings("all")
    public void setToken(final String token) { this.token = token; }

    @java.lang.SuppressWarnings("all")
    public void setAmount(final BigDecimal amount) { this.amount = amount; }

    @java.lang.SuppressWarnings("all")
    public void setDescription(final String description) { this.description = description; }

    @java.lang.SuppressWarnings("all")
    public void setRequesterId(final Long requesterId) { this.requesterId = requesterId; }

    @java.lang.SuppressWarnings("all")
    public void setRequesterAccountNumber(final String requesterAccountNumber) { this.requesterAccountNumber = requesterAccountNumber; }

    @java.lang.SuppressWarnings("all")
    public void setPayerId(final Long payerId) { this.payerId = payerId; }

    @java.lang.SuppressWarnings("all")
    public void setPayerAccountNumber(final String payerAccountNumber) { this.payerAccountNumber = payerAccountNumber; }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final PaymentRequestStatus status) { this.status = status; }

    @java.lang.SuppressWarnings("all")
    public void setExpiresAt(final LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    @java.lang.SuppressWarnings("all")
    public void setPaidAt(final LocalDateTime paidAt) { this.paidAt = paidAt; }

    @java.lang.SuppressWarnings("all")
    public void setTransactionId(final Long transactionId) { this.transactionId = transactionId; }
}
