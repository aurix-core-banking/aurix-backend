package com.aurix.platform.cambio.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoLiquidadoEvent(
    Long contratoId,
    Long clienteId,
    BigDecimal valorLiquidado,
    LocalDate dataLiquidacao,
    String tenantId
) {}
