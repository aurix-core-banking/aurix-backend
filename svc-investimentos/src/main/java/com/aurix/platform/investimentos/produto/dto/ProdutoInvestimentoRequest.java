package com.aurix.platform.investimentos.produto.dto;

import com.aurix.platform.investimentos.produto.entity.TipoProdutoInvestimento;
import com.aurix.platform.investimentos.produto.entity.TipoRenda;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoInvestimentoRequest(
    @NotBlank String tenantId,
    @NotBlank String nome,
    String descricao,
    @NotNull TipoProdutoInvestimento tipo,
    @NotNull TipoRenda tipoRenda,
    @NotNull @DecimalMin("0.0") BigDecimal taxaRendimento,
    @DecimalMin("0.0") BigDecimal taxaAdm,
    @NotNull @DecimalMin("0.01") BigDecimal valorMinimo,
    @NotNull @Min(1) Integer prazoMinimoDias,
    LocalDate dataVencimento,
    Integer carenciaDias
) {}
