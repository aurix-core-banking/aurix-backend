package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.AssinaturaContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AssinanteRequest(
    @NotNull(message = "Tipo do assinante é obrigatório")
    AssinaturaContrato.AssinanteTipo assinanteTipo,

    @NotBlank(message = "Documento do assinante é obrigatório")
    @Size(max = 14, message = "Documento deve ter no máximo 14 caracteres")
    String assinanteDocumento,

    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    String assinanteNome
) {
}
