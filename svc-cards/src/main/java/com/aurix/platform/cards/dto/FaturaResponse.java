package com.aurix.platform.cards.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturaResponse {

    private Long id;
    private String codigoFatura;
    private Long cartaoId;
    private Integer mesReferencia;
    private Integer anoReferencia;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private BigDecimal valorPendente;
    private BigDecimal valorMinimo;
    private String status;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoFatura() { return codigoFatura; }
    public void setCodigoFatura(String codigoFatura) { this.codigoFatura = codigoFatura; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public Integer getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(Integer mesReferencia) { this.mesReferencia = mesReferencia; }
    public Integer getAnoReferencia() { return anoReferencia; }
    public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }
    public BigDecimal getValorPendente() { return valorPendente; }
    public void setValorPendente(BigDecimal valorPendente) { this.valorPendente = valorPendente; }
    public BigDecimal getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(BigDecimal valorMinimo) { this.valorMinimo = valorMinimo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
}
