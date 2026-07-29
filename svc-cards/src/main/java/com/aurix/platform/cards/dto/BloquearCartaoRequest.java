package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.NotBlank;

public class BloquearCartaoRequest {

    @NotBlank
    private String motivo;

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
