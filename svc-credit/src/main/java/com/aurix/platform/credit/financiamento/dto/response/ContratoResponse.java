package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ContratoResponse {

    private Long id;
    private Long clienteId;
    private String tipo;
    private BigDecimal valorFinanciado;
    private BigDecimal valorEntrada;
    private BigDecimal taxaJuros;
    private int prazoMeses;
    private String sistemaAmortizacao;
    private BigDecimal valorParcela;
    private BigDecimal saldoDevedor;
    private LocalDate dataContratacao;
    private LocalDate dataPrimeiraParcela;
    private String status;
    private List<BemResponse> bens;
    private List<GarantiaResponse> garantias;
    private LocalDateTime dataCriacao;

    public ContratoResponse() {}

    public ContratoResponse(Long id, Long clienteId, String tipo, BigDecimal valorFinanciado, BigDecimal valorEntrada, BigDecimal taxaJuros, int prazoMeses, String sistemaAmortizacao, BigDecimal valorParcela, BigDecimal saldoDevedor, LocalDate dataContratacao, LocalDate dataPrimeiraParcela, String status, List<BemResponse> bens, List<GarantiaResponse> garantias, LocalDateTime dataCriacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.valorEntrada = valorEntrada;
        this.taxaJuros = taxaJuros;
        this.prazoMeses = prazoMeses;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.valorParcela = valorParcela;
        this.saldoDevedor = saldoDevedor;
        this.dataContratacao = dataContratacao;
        this.dataPrimeiraParcela = dataPrimeiraParcela;
        this.status = status;
        this.bens = bens;
        this.garantias = garantias;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public BigDecimal getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(BigDecimal valorEntrada) { this.valorEntrada = valorEntrada; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public String getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(String sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public BigDecimal getSaldoDevedor() { return saldoDevedor; }
    public void setSaldoDevedor(BigDecimal saldoDevedor) { this.saldoDevedor = saldoDevedor; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public LocalDate getDataPrimeiraParcela() { return dataPrimeiraParcela; }
    public void setDataPrimeiraParcela(LocalDate dataPrimeiraParcela) { this.dataPrimeiraParcela = dataPrimeiraParcela; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<BemResponse> getBens() { return bens; }
    public void setBens(List<BemResponse> bens) { this.bens = bens; }
    public List<GarantiaResponse> getGarantias() { return garantias; }
    public void setGarantias(List<GarantiaResponse> garantias) { this.garantias = garantias; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
