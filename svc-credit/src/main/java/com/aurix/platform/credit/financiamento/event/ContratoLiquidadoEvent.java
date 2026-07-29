package com.aurix.platform.credit.financiamento.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoLiquidadoEvent(
    Long contratoId,
    Long clienteId,
    BigDecimal valorQuitado,
    LocalDate dataLiquidacao,
    String tenantId
) {}
