package com.aurix.platform.investimentos.aplicacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AplicacaoRequest(
    @NotNull String tenantId,
    @NotNull Long clienteId,
    @NotNull Long produtoId,
    @NotNull @DecimalMin("0.01") BigDecimal valor,
    @NotNull Long contaCorrenteId
) {}
