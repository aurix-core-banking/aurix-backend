package com.aurix.platform.investimentos.aplicacao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AplicacaoResponse(
    Long id,
    String tenantId,
    Long clienteId,
    Long produtoId,
    String produtoTipo,
    BigDecimal valorAplicado,
    BigDecimal valorBruto,
    BigDecimal valorLiquido,
    BigDecimal iof,
    BigDecimal ir,
    BigDecimal taxaRendimento,
    LocalDate dataAplicacao,
    LocalDate dataVencimento,
    LocalDate dataResgate,
    String status,
    LocalDateTime dataCriacao
) {}
