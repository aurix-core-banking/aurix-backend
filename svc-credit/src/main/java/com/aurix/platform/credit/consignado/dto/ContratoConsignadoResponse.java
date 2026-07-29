package com.aurix.platform.credit.consignado.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContratoConsignadoResponse {

    private Long id;
    private Long clienteId;
    private Long contaSalarioId;
    private BigDecimal valorTotal;
    private BigDecimal taxaJuros;
    private int prazoMeses;
    private BigDecimal valorParcela;
    private BigDecimal margemUtilizada;
    private String fonteMargem;
    private String status;
    private LocalDate dataContratacao;
    private String tenantId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ContratoConsignadoResponse() {}

    public ContratoConsignadoResponse(Long id, Long clienteId, Long contaSalarioId, BigDecimal valorTotal, BigDecimal taxaJuros, int prazoMeses, BigDecimal valorParcela, BigDecimal margemUtilizada, String fonteMargem, String status, LocalDate dataContratacao, String tenantId, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
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
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
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
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
