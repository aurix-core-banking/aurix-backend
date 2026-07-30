package com.aurix.platform.shared.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_leg", schema = "aurix")
public class TransactionLeg extends BaseEntity {

    public enum LegType { SOURCE, DESTINATION }

    public enum LegStatus { PENDING, RESERVED, SETTLED, FAILED, CANCELLED }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transacao transaction;

    @Column(name = "leg_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LegType legType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Conta account;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "BRL";

    @Column(name = "converted_amount", precision = 19, scale = 4)
    private BigDecimal convertedAmount;

    @Column(name = "exchange_rate", precision = 19, scale = 8)
    private BigDecimal exchangeRate;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "leg_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LegStatus legStatus = LegStatus.PENDING;

    @Column(name = "share_percentage", precision = 5, scale = 2)
    private BigDecimal sharePercentage;

    @Column(name = "order_index")
    private Integer orderIndex;

    public Transacao getTransaction() { return transaction; }
    public void setTransaction(Transacao transaction) { this.transaction = transaction; }

    public LegType getLegType() { return legType; }
    public void setLegType(LegType legType) { this.legType = legType; }

    public Conta getAccount() { return account; }
    public void setAccount(Conta account) { this.account = account; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getConvertedAmount() { return convertedAmount; }
    public void setConvertedAmount(BigDecimal convertedAmount) { this.convertedAmount = convertedAmount; }

    public BigDecimal getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(BigDecimal exchangeRate) { this.exchangeRate = exchangeRate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LegStatus getLegStatus() { return legStatus; }
    public void setLegStatus(LegStatus legStatus) { this.legStatus = legStatus; }

    public BigDecimal getSharePercentage() { return sharePercentage; }
    public void setSharePercentage(BigDecimal sharePercentage) { this.sharePercentage = sharePercentage; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}
