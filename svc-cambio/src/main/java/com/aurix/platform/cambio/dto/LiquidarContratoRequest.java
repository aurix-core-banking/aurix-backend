package com.aurix.platform.cambio.dto;

public class LiquidarContratoRequest {

    private String observacao;

    public LiquidarContratoRequest() {}

    public LiquidarContratoRequest(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
