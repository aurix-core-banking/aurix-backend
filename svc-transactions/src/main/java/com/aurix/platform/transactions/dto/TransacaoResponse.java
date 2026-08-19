package com.aurix.platform.transactions.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponse {

    private Long id;
    private String codigoTransacao;
    private Long contaOrigemId;
    private String contaOrigemNumero;
    private Long contaDestinoId;
    private String contaDestinoNumero;
    private String tipoTransacao;
    private BigDecimal valor;
    private String descricao;
    private String status;
    private LocalDateTime dataTransacao;
    private LocalDateTime dataProcessamento;
    private BigDecimal saldoAnteriorOrigem;
    private BigDecimal saldoPosteriorOrigem;
    private BigDecimal saldoAnteriorDestino;
    private BigDecimal saldoPosteriorDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public String getContaOrigemNumero() {
        return contaOrigemNumero;
    }

    public void setContaOrigemNumero(String contaOrigemNumero) {
        this.contaOrigemNumero = contaOrigemNumero;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getContaDestinoNumero() {
        return contaDestinoNumero;
    }

    public void setContaDestinoNumero(String contaDestinoNumero) {
        this.contaDestinoNumero = contaDestinoNumero;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public BigDecimal getSaldoAnteriorOrigem() {
        return saldoAnteriorOrigem;
    }

    public void setSaldoAnteriorOrigem(BigDecimal saldoAnteriorOrigem) {
        this.saldoAnteriorOrigem = saldoAnteriorOrigem;
    }

    public BigDecimal getSaldoPosteriorOrigem() {
        return saldoPosteriorOrigem;
    }

    public void setSaldoPosteriorOrigem(BigDecimal saldoPosteriorOrigem) {
        this.saldoPosteriorOrigem = saldoPosteriorOrigem;
    }

    public BigDecimal getSaldoAnteriorDestino() {
        return saldoAnteriorDestino;
    }

    public void setSaldoAnteriorDestino(BigDecimal saldoAnteriorDestino) {
        this.saldoAnteriorDestino = saldoAnteriorDestino;
    }

    public BigDecimal getSaldoPosteriorDestino() {
        return saldoPosteriorDestino;
    }

    public void setSaldoPosteriorDestino(BigDecimal saldoPosteriorDestino) {
        this.saldoPosteriorDestino = saldoPosteriorDestino;
    }
}
