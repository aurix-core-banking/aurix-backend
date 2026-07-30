package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.PaymentRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentRequestDTO {
    private Long id;
    private String token;
    private BigDecimal amount;
    private String description;
    private Long requesterId;
    private String requesterAccountNumber;
    private Long payerId;
    private String payerAccountNumber;
    private String status;
    private LocalDateTime expiresAt;
    private LocalDateTime paidAt;
    private Long transactionId;

    public static PaymentRequestDTO fromEntity(PaymentRequest entity) {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setId(entity.getId());
        dto.setToken(entity.getToken());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setRequesterId(entity.getRequesterId());
        dto.setRequesterAccountNumber(entity.getRequesterAccountNumber());
        dto.setPayerId(entity.getPayerId());
        dto.setPayerAccountNumber(entity.getPayerAccountNumber());
        dto.setStatus(entity.getStatus().name());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setPaidAt(entity.getPaidAt());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }

    public PaymentRequest toEntity() {
        PaymentRequest entity = new PaymentRequest();
        entity.setToken(this.token);
        entity.setAmount(this.amount);
        entity.setDescription(this.description);
        entity.setRequesterId(this.requesterId);
        entity.setRequesterAccountNumber(this.requesterAccountNumber);
        entity.setExpiresAt(this.expiresAt);
        return entity;
    }

    @java.lang.SuppressWarnings("all")
    public PaymentRequestDTO() {}

    @java.lang.SuppressWarnings("all")
    public Long getId() { return this.id; }

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
    public String getStatus() { return this.status; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getExpiresAt() { return this.expiresAt; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPaidAt() { return this.paidAt; }

    @java.lang.SuppressWarnings("all")
    public Long getTransactionId() { return this.transactionId; }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) { this.id = id; }

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
    public void setStatus(final String status) { this.status = status; }

    @java.lang.SuppressWarnings("all")
    public void setExpiresAt(final LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    @java.lang.SuppressWarnings("all")
    public void setPaidAt(final LocalDateTime paidAt) { this.paidAt = paidAt; }

    @java.lang.SuppressWarnings("all")
    public void setTransactionId(final Long transactionId) { this.transactionId = transactionId; }
}
