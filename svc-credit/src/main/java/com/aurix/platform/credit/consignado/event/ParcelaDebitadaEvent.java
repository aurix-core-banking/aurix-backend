package com.aurix.platform.credit.consignado.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaDebitadaEvent(
    Long parcelaId,
    Long contratoId,
    int numero,
    BigDecimal valor,
    LocalDate dataPagamento,
    String tenantId
) {}
