package com.aurix.platform.credit.consignado.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_consignados")
public class ContratoConsignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long contaSalarioId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal taxaJuros;

    @Column(nullable = false)
    private int prazoMeses;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorParcela;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal margemUtilizada;

    @Column(nullable = false, length = 20)
    private String fonteMargem;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private LocalDate dataContratacao;

    @Column(nullable = false, length = 50)
    private String tenantId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public ContratoConsignado() {}

    public ContratoConsignado(Long clienteId, Long contaSalarioId, BigDecimal valorTotal, BigDecimal taxaJuros, int prazoMeses, BigDecimal valorParcela, BigDecimal margemUtilizada, String fonteMargem, String status, LocalDate dataContratacao, String tenantId) {
        this.clienteId = clienteId;
        this.contaSalarioId = contaSalarioId;
        this.valorTotal = valorTotal;
        this.taxaJuros = taxaJuros;
        this.prazoMeses = prazoMeses;
        this.valorParcela = valorParcela;
        this.margemUtilizada = margemUtilizada;
        this.fonteMargem = fonteMargem;
        this.status = status;
        this.dataContratacao = dataContratacao;
        this.tenantId = tenantId;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long contaSalarioId) { this.contaSalarioId = contaSalarioId; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public BigDecimal getMargemUtilizada() { return margemUtilizada; }
    public void setMargemUtilizada(BigDecimal margemUtilizada) { this.margemUtilizada = margemUtilizada; }
    public String getFonteMargem() { return fonteMargem; }
    public void setFonteMargem(String fonteMargem) { this.fonteMargem = fonteMargem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
