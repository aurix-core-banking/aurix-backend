package com.aurix.platform.credit.consignado.dto;

public class LiquidarRequest {

    private String observacao;

    public LiquidarRequest() {}

    public LiquidarRequest(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
