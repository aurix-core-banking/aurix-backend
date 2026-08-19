package com.aurix.platform.investimentos.portfolio.dto;

import java.math.BigDecimal;

public record RentabilidadeResponse(
    Long clienteId,
    BigDecimal rendimentoBrutoTotal,
    BigDecimal iofTotal,
    BigDecimal irTotal,
    BigDecimal rendimentoLiquidoTotal,
    BigDecimal rentabilidadeAnual,
    java.time.LocalDate dataReferencia
) {}
