package com.aurix.platform.seguros.sinistro.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SinistroRequest(
    @NotNull String tenantId,
    @NotNull Long apoliceId,
    @NotNull Long clienteId,
    @NotNull Long produtoId,
    @NotNull String produtoTipo,
    @NotNull String descricaoEvento,
    @NotNull LocalDate dataEvento,
    BigDecimal valorSolicitado
) {}
