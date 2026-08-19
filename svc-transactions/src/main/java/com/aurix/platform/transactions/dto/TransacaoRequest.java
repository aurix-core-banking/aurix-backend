package com.aurix.platform.transactions.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransacaoRequest {

    private Long contaOrigemId;
    private Long contaDestinoId;

    @NotNull(message = "Valor e obrigatorio")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    private String tipoTransacao;
    private String descricao;
    private String dadosPix;
    private String dadosTed;

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDadosPix() {
        return dadosPix;
    }

    public void setDadosPix(String dadosPix) {
        this.dadosPix = dadosPix;
    }

    public String getDadosTed() {
        return dadosTed;
    }

    public void setDadosTed(String dadosTed) {
        this.dadosTed = dadosTed;
    }
}
