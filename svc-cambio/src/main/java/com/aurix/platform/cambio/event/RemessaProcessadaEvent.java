package com.aurix.platform.cambio.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RemessaProcessadaEvent(
    Long remessaId,
    Long contratoId,
    Long clienteId,
    BigDecimal valor,
    String moeda,
    String status,
    String codigoSwift,
    LocalDateTime dataConfirmacao,
    String tenantId
) {}
