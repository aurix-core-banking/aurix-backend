package com.aurix.platform.credit.seguro.dto.request;

import jakarta.validation.constraints.NotNull;

public class ContratarSeguroRequest {

    @NotNull(message = "ID do contrato é obrigatório")
    private Long contratoId;

    private String[] coberturas;

    public ContratarSeguroRequest() {}

    public ContratarSeguroRequest(Long contratoId, String[] coberturas) {
        this.contratoId = contratoId;
        this.coberturas = coberturas;
    }

    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public String[] getCoberturas() { return coberturas; }
    public void setCoberturas(String[] coberturas) { this.coberturas = coberturas; }
}
