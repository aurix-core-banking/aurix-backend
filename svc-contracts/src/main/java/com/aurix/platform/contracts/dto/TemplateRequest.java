package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.Contrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TemplateRequest(
    @NotBlank(message = "Código do template é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    String codigo,

    @NotBlank(message = "Nome do template é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    String nome,

    @NotNull(message = "Tipo de contrato é obrigatório")
    Contrato.TipoContrato tipoContrato,

    @NotBlank(message = "Corpo do template é obrigatório")
    String corpoTexto
) {
}
