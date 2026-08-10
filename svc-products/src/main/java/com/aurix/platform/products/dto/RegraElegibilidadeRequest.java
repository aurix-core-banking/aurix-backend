package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.RegraElegibilidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegraElegibilidadeRequest(
    @NotNull(message = "Tipo da regra é obrigatório")
    RegraElegibilidade.TipoRegra tipoRegra,

    @NotNull(message = "Comparador é obrigatório")
    RegraElegibilidade.Comparador comparador,

    BigDecimal valorNumerico,

    String valorTexto,

    String descricao
) {
}
