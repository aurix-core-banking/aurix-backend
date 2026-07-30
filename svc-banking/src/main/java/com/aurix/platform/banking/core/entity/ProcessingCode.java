package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "processing_codes", schema = "aurix")
public class ProcessingCode extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "payment_type", nullable = false, length = 30)
    private String paymentType;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "min_amount", precision = 19, scale = 2)
    private BigDecimal minAmount;

    @Column(name = "max_amount", precision = 19, scale = 2)
    private BigDecimal maxAmount;

    @Column(name = "processing_rules", length = 2000)
    private String processingRules;

    @java.lang.SuppressWarnings("all")
    public ProcessingCode() {}

    @java.lang.SuppressWarnings("all")
    public String getCode() { return this.code; }

    @java.lang.SuppressWarnings("all")
    public String getDescription() { return this.description; }

    @java.lang.SuppressWarnings("all")
    public String getPaymentType() { return this.paymentType; }

    @java.lang.SuppressWarnings("all")
    public boolean isActive() { return this.active; }

    @java.lang.SuppressWarnings("all")
    public int getPriority() { return this.priority; }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMinAmount() { return this.minAmount; }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMaxAmount() { return this.maxAmount; }

    @java.lang.SuppressWarnings("all")
    public String getProcessingRules() { return this.processingRules; }

    @java.lang.SuppressWarnings("all")
    public void setCode(final String code) { this.code = code; }

    @java.lang.SuppressWarnings("all")
    public void setDescription(final String description) { this.description = description; }

    @java.lang.SuppressWarnings("all")
    public void setPaymentType(final String paymentType) { this.paymentType = paymentType; }

    @java.lang.SuppressWarnings("all")
    public void setActive(final boolean active) { this.active = active; }

    @java.lang.SuppressWarnings("all")
    public void setPriority(final int priority) { this.priority = priority; }

    @java.lang.SuppressWarnings("all")
    public void setMinAmount(final BigDecimal minAmount) { this.minAmount = minAmount; }

    @java.lang.SuppressWarnings("all")
    public void setMaxAmount(final BigDecimal maxAmount) { this.maxAmount = maxAmount; }

    @java.lang.SuppressWarnings("all")
    public void setProcessingRules(final String processingRules) { this.processingRules = processingRules; }
}
