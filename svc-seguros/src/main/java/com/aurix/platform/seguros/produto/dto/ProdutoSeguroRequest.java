package com.aurix.platform.seguros.produto.dto;

import com.aurix.platform.seguros.produto.entity.TipoCobertura;
import com.aurix.platform.seguros.produto.entity.TipoSeguro;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoSeguroRequest(
    @NotBlank String tenantId,
    @NotBlank String nome,
    String descricao,
    @NotNull TipoSeguro tipo,
    @NotNull TipoCobertura coberturaPadrao,
    @NotNull @DecimalMin("0.0") BigDecimal taxaBase,
    @NotNull @DecimalMin("0.01") BigDecimal premioMinimo,
    @DecimalMin("0") BigDecimal carenciaMeses,
    @NotNull @Min(1) Integer prazoAnaliseDias,
    @NotNull @Min(1) Integer prazoPagamentoSinistroDias
) {}
