package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContratoCambioResponse {

    private Long id;
    private Long clienteId;
    private String tipo;
    private String moedaOrigem;
    private String moedaDestino;
    private BigDecimal valorOrigem;
    private BigDecimal valorDestino;
    private BigDecimal taxaCambio;
    private LocalDate dataContratacao;
    private LocalDate dataLiquidacao;
    private String finalidade;
    private String status;
    private String registroBACEN;
    private LocalDateTime dataCriacao;

    public ContratoCambioResponse() {}

    public ContratoCambioResponse(Long id, Long clienteId, String tipo, String moedaOrigem, String moedaDestino, BigDecimal valorOrigem, BigDecimal valorDestino, BigDecimal taxaCambio, LocalDate dataContratacao, LocalDate dataLiquidacao, String finalidade, String status, String registroBACEN, LocalDateTime dataCriacao) {
        this.id = id;
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
        this.dataCriacao = dataCriacao;
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
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
