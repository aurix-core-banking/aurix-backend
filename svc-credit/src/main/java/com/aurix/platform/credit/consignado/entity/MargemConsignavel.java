package com.aurix.platform.credit.consignado.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "margens_consignaveis")
public class MargemConsignavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 20)
    private String fonteMargem;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal margemTotal;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal margemDisponivel;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal margemUtilizada;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public MargemConsignavel() {}

    public MargemConsignavel(Long clienteId, String fonteMargem, BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, LocalDateTime dataAtualizacao, String tenantId) {
        this.clienteId = clienteId;
        this.fonteMargem = fonteMargem;
        this.margemTotal = margemTotal;
        this.margemDisponivel = margemDisponivel;
        this.margemUtilizada = margemUtilizada;
        this.dataAtualizacao = dataAtualizacao;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getFonteMargem() { return fonteMargem; }
    public void setFonteMargem(String fonteMargem) { this.fonteMargem = fonteMargem; }
    public BigDecimal getMargemTotal() { return margemTotal; }
    public void setMargemTotal(BigDecimal margemTotal) { this.margemTotal = margemTotal; }
    public BigDecimal getMargemDisponivel() { return margemDisponivel; }
    public void setMargemDisponivel(BigDecimal margemDisponivel) { this.margemDisponivel = margemDisponivel; }
    public BigDecimal getMargemUtilizada() { return margemUtilizada; }
    public void setMargemUtilizada(BigDecimal margemUtilizada) { this.margemUtilizada = margemUtilizada; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
