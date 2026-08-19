package com.aurix.platform.investimentos.portfolio.dto;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioResponse(
    Long clienteId,
    BigDecimal valorTotalAplicado,
    BigDecimal valorTotalBruto,
    BigDecimal valorTotalLiquido,
    BigDecimal rendimentoTotal,
    Integer totalAplicacoes,
    List<ItemPortfolio> aplicacoes
) {
    public record ItemPortfolio(
        Long aplicacaoId,
        Long produtoId,
        String produtoNome,
        String produtoTipo,
        BigDecimal valorAplicado,
        BigDecimal valorBruto,
        BigDecimal valorLiquido,
        String status,
        java.time.LocalDate dataAplicacao,
        java.time.LocalDate dataVencimento
    ) {}
}
