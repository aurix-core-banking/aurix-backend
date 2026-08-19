package com.aurix.platform.seguros.apolice.dto;

import com.aurix.platform.seguros.produto.entity.TipoCobertura;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ApoliceRequest(
    @NotNull String tenantId,
    @NotNull Long clienteId,
    @NotNull Long produtoId,
    @NotNull @DecimalMin("0.01") BigDecimal valorSegurado,
    @NotNull TipoCobertura cobertura,
    @NotNull @Min(1) Integer idadeSegurado,
    @NotBlank String uf,
    @NotBlank String sexo,
    String profissao,
    @NotNull Boolean renovacaoAutomatica
) {}
