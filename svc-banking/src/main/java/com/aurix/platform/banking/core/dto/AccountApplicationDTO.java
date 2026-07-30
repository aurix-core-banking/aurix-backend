package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.AccountApplication;
import java.time.LocalDateTime;

public class AccountApplicationDTO {
    private Long id;
    private Long clienteId;
    private String customerName;
    private String cpfCnpj;
    private String email;
    private String phone;
    private String accountType;
    private String status;
    private Long reviewerId;
    private String reviewNotes;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private Long accountId;

    public static AccountApplicationDTO fromEntity(AccountApplication entity) {
        AccountApplicationDTO dto = new AccountApplicationDTO();
        dto.setId(entity.getId());
        dto.setClienteId(entity.getClienteId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAccountType(entity.getAccountType());
        dto.setStatus(entity.getStatus().name());
        dto.setReviewerId(entity.getReviewerId());
        dto.setReviewNotes(entity.getReviewNotes());
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setReviewedAt(entity.getReviewedAt());
        dto.setAccountId(entity.getAccountId());
        return dto;
    }

    public AccountApplication toEntity() {
        AccountApplication entity = new AccountApplication();
        entity.setClienteId(this.clienteId);
        entity.setCustomerName(this.customerName);
        entity.setCpfCnpj(this.cpfCnpj);
        entity.setEmail(this.email);
        entity.setPhone(this.phone);
        entity.setAccountType(this.accountType);
        return entity;
    }

    @java.lang.SuppressWarnings("all")
    public AccountApplicationDTO() {}

    @java.lang.SuppressWarnings("all")
    public Long getId() { return this.id; }

    @java.lang.SuppressWarnings("all")
    public Long getClienteId() { return this.clienteId; }

    @java.lang.SuppressWarnings("all")
    public String getCustomerName() { return this.customerName; }

    @java.lang.SuppressWarnings("all")
    public String getCpfCnpj() { return this.cpfCnpj; }

    @java.lang.SuppressWarnings("all")
    public String getEmail() { return this.email; }

    @java.lang.SuppressWarnings("all")
    public String getPhone() { return this.phone; }

    @java.lang.SuppressWarnings("all")
    public String getAccountType() { return this.accountType; }

    @java.lang.SuppressWarnings("all")
    public String getStatus() { return this.status; }

    @java.lang.SuppressWarnings("all")
    public Long getReviewerId() { return this.reviewerId; }

    @java.lang.SuppressWarnings("all")
    public String getReviewNotes() { return this.reviewNotes; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getSubmittedAt() { return this.submittedAt; }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getReviewedAt() { return this.reviewedAt; }

    @java.lang.SuppressWarnings("all")
    public Long getAccountId() { return this.accountId; }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) { this.id = id; }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) { this.clienteId = clienteId; }

    @java.lang.SuppressWarnings("all")
    public void setCustomerName(final String customerName) { this.customerName = customerName; }

    @java.lang.SuppressWarnings("all")
    public void setCpfCnpj(final String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) { this.email = email; }

    @java.lang.SuppressWarnings("all")
    public void setPhone(final String phone) { this.phone = phone; }

    @java.lang.SuppressWarnings("all")
    public void setAccountType(final String accountType) { this.accountType = accountType; }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) { this.status = status; }

    @java.lang.SuppressWarnings("all")
    public void setReviewerId(final Long reviewerId) { this.reviewerId = reviewerId; }

    @java.lang.SuppressWarnings("all")
    public void setReviewNotes(final String reviewNotes) { this.reviewNotes = reviewNotes; }

    @java.lang.SuppressWarnings("all")
    public void setSubmittedAt(final LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    @java.lang.SuppressWarnings("all")
    public void setReviewedAt(final LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    @java.lang.SuppressWarnings("all")
    public void setAccountId(final Long accountId) { this.accountId = accountId; }
}
