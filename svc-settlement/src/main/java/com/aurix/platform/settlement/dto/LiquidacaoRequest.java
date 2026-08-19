package com.aurix.platform.settlement.dto;

import jakarta.validation.constraints.NotNull;

public class LiquidacaoRequest {

    @NotNull(message = "ID da transacao e obrigatorio")
    private Long transacaoId;

    @NotNull(message = "Tipo de liquidacao e obrigatorio")
    private String tipoLiquidacao;

    private String contaOrigem;
    private String contaDestino;

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
}
