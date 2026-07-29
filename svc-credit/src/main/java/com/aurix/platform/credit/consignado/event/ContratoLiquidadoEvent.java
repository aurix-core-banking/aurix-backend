package com.aurix.platform.credit.consignado.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoLiquidadoEvent(
    Long contratoId,
    Long clienteId,
    BigDecimal valorTotalPago,
    LocalDate dataLiquidacao,
    String tenantId
) {}
