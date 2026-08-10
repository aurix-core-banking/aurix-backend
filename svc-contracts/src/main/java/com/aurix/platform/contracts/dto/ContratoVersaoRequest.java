package com.aurix.platform.contracts.dto;

import jakarta.validation.constraints.NotBlank;

public record ContratoVersaoRequest(
    @NotBlank(message = "Motivo da alteração é obrigatório")
    String motivoAlteracao
) {
}
