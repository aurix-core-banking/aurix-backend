package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.Contrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoRequest(
    @NotBlank(message = "Número do contrato é obrigatório")
    @Size(max = 50, message = "Número do contrato deve ter no máximo 50 caracteres")
    String numeroContrato,

    Long produtoId,

    @Size(max = 50, message = "Código do produto deve ter no máximo 50 caracteres")
    String produtoCodigo,

    @NotNull(message = "Cliente é obrigatório")
    Long clienteId,

    @Size(max = 14, message = "Documento deve ter no máximo 14 caracteres")
    String clienteDocumento,

    @NotNull(message = "Tipo do contrato é obrigatório")
    Contrato.TipoContrato tipoContrato,

    BigDecimal valor,

    Integer prazoMeses,

    BigDecimal valorParcela,

    BigDecimal taxaJuros,

    LocalDate dataVigenciaInicio,

    LocalDate dataVigenciaFim,

    String termosTexto,

    String dadosJson
) {
}
