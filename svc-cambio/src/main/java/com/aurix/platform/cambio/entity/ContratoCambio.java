package com.aurix.platform.cambio.entity;

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
@Table(name = "contratos_cambio")
public class ContratoCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 10)
    private String moedaOrigem;

    @Column(nullable = false, length = 10)
    private String moedaDestino;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal valorOrigem;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal valorDestino;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal taxaCambio;

    @Column(nullable = false)
    private LocalDate dataContratacao;

    @Column
    private LocalDate dataLiquidacao;

    @Column(nullable = false, length = 100)
    private String finalidade;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 50)
    private String registroBACEN;

    @Column(nullable = false, length = 50)
    private String tenantId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public ContratoCambio() {}

    public ContratoCambio(Long clienteId, String tipo, String moedaOrigem, String moedaDestino, BigDecimal valorOrigem, BigDecimal valorDestino, BigDecimal taxaCambio, LocalDate dataContratacao, LocalDate dataLiquidacao, String finalidade, String status, String registroBACEN, String tenantId) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.valorOrigem = valorOrigem;
        this.valorDestino = valorDestino;
        this.taxaCambio = taxaCambio;
        this.dataContratacao = dataContratacao;
        this.dataLiquidacao = dataLiquidacao;
        this.finalidade = finalidade;
        this.status = status;
        this.registroBACEN = registroBACEN;
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
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMoedaOrigem() { return moedaOrigem; }
    public void setMoedaOrigem(String moedaOrigem) { this.moedaOrigem = moedaOrigem; }
    public String getMoedaDestino() { return moedaDestino; }
    public void setMoedaDestino(String moedaDestino) { this.moedaDestino = moedaDestino; }
    public BigDecimal getValorOrigem() { return valorOrigem; }
    public void setValorOrigem(BigDecimal valorOrigem) { this.valorOrigem = valorOrigem; }
    public BigDecimal getValorDestino() { return valorDestino; }
    public void setValorDestino(BigDecimal valorDestino) { this.valorDestino = valorDestino; }
    public BigDecimal getTaxaCambio() { return taxaCambio; }
    public void setTaxaCambio(BigDecimal taxaCambio) { this.taxaCambio = taxaCambio; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public LocalDate getDataLiquidacao() { return dataLiquidacao; }
    public void setDataLiquidacao(LocalDate dataLiquidacao) { this.dataLiquidacao = dataLiquidacao; }
    public String getFinalidade() { return finalidade; }
    public void setFinalidade(String finalidade) { this.finalidade = finalidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRegistroBACEN() { return registroBACEN; }
    public void setRegistroBACEN(String registroBACEN) { this.registroBACEN = registroBACEN; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
