package com.aurix.platform.credit.consignado.event;

import java.math.BigDecimal;

public record MargemAtualizadaEvent(
    Long clienteId,
    String fonteMargem,
    BigDecimal margemTotal,
    BigDecimal margemDisponivel,
    BigDecimal margemUtilizada,
    String tenantId
) {}
