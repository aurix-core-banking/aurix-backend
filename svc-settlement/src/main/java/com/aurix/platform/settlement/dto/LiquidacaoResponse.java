package com.aurix.platform.settlement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LiquidacaoResponse {

    private Long id;
    private String codigoLiquidacao;
    private Long transacaoId;
    private String tipoLiquidacao;
    private String status;
    private BigDecimal valorLiquidacao;
    private BigDecimal valorTaxa;
    private BigDecimal valorIOF;
    private BigDecimal valorTotal;
    private String contaOrigem;
    private String contaDestino;
    private String codigoRetorno;
    private String mensagemRetorno;
    private LocalDateTime dataLiquidacao;
    private LocalDateTime dataProcessamento;
    private LocalDateTime dataConfirmacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoLiquidacao() {
        return codigoLiquidacao;
    }

    public void setCodigoLiquidacao(String codigoLiquidacao) {
        this.codigoLiquidacao = codigoLiquidacao;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    public String getTipoLiquidacao() {
        return tipoLiquidacao;
    }

    public void setTipoLiquidacao(String tipoLiquidacao) {
        this.tipoLiquidacao = tipoLiquidacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorLiquidacao() {
        return valorLiquidacao;
    }

    public void setValorLiquidacao(BigDecimal valorLiquidacao) {
        this.valorLiquidacao = valorLiquidacao;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public BigDecimal getValorIOF() {
        return valorIOF;
    }

    public void setValorIOF(BigDecimal valorIOF) {
        this.valorIOF = valorIOF;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public void setCodigoRetorno(String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    public LocalDateTime getDataLiquidacao() {
        return dataLiquidacao;
    }

    public void setDataLiquidacao(LocalDateTime dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }
}
