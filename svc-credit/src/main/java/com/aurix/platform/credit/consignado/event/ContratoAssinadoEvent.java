package com.aurix.platform.credit.consignado.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoAssinadoEvent(
    Long contratoId,
    Long clienteId,
    BigDecimal valorTotal,
    int prazoMeses,
    BigDecimal valorParcela,
    String fonteMargem,
    LocalDate dataContratacao,
    String tenantId
) {}
