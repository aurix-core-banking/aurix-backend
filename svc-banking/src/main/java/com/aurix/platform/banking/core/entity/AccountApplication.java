package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_applications", schema = "aurix")
public class AccountApplication extends BaseEntity {

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "customer_name", nullable = false, length = 200)
    private String customerName;

    @Column(name = "cpf_cnpj", nullable = false, length = 20)
    private String cpfCnpj;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "account_type", nullable = false, length = 30)
    private String accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private AccountApplicationStatus status = AccountApplicationStatus.DRAFT;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "review_notes", length = 1000)
    private String reviewNotes;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "account_id")
    private Long accountId;

    public enum AccountApplicationStatus {
        DRAFT,
        PENDING_DOCUMENTS,
        SUBMITTED,
        UNDER_REVIEW,
        APPROVED,
        REJECTED
    }

    @java.lang.SuppressWarnings("all")
    public AccountApplication() {}

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
    public AccountApplicationStatus getStatus() { return this.status; }

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
    public void setStatus(final AccountApplicationStatus status) { this.status = status; }

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
