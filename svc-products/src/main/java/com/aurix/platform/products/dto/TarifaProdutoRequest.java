package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.TarifaProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TarifaProdutoRequest(
    @NotBlank(message = "Código da tarifa é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    String codigo,

    @NotBlank(message = "Descrição da tarifa é obrigatória")
    @Size(max = 300, message = "Descrição deve ter no máximo 300 caracteres")
    String descricao,

    @NotNull(message = "Tipo da tarifa é obrigatório")
    TarifaProduto.TipoTarifa tipoTarifa,

    @NotNull(message = "Periodicidade é obrigatória")
    TarifaProduto.Periodicidade periodicidade,

    BigDecimal valorFixo,

    BigDecimal percentual,

    LocalDate vigenciaInicio,

    LocalDate vigenciaFim,

    Boolean obrigatoria
) {
}
