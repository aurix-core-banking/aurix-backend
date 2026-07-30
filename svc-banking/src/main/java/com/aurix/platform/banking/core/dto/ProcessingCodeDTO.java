package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import java.math.BigDecimal;

public class ProcessingCodeDTO {
    private Long id;
    private String code;
    private String description;
    private String paymentType;
    private boolean active;
    private int priority;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String processingRules;

    public static ProcessingCodeDTO fromEntity(ProcessingCode entity) {
        ProcessingCodeDTO dto = new ProcessingCodeDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setPaymentType(entity.getPaymentType());
        dto.setActive(entity.isActive());
        dto.setPriority(entity.getPriority());
        dto.setMinAmount(entity.getMinAmount());
        dto.setMaxAmount(entity.getMaxAmount());
        dto.setProcessingRules(entity.getProcessingRules());
        return dto;
    }

    public ProcessingCode toEntity() {
        ProcessingCode entity = new ProcessingCode();
        entity.setCode(this.code);
        entity.setDescription(this.description);
        entity.setPaymentType(this.paymentType);
        entity.setActive(this.active);
        entity.setPriority(this.priority);
        entity.setMinAmount(this.minAmount);
        entity.setMaxAmount(this.maxAmount);
        entity.setProcessingRules(this.processingRules);
        return entity;
    }

    @java.lang.SuppressWarnings("all")
    public ProcessingCodeDTO() {}

    @java.lang.SuppressWarnings("all")
    public Long getId() { return this.id; }

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
    public void setId(final Long id) { this.id = id; }

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
