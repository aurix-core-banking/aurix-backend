package com.aurix.platform.credit.financiamento.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaPagaEvent(
    Long parcelaId,
    Long contratoId,
    int numero,
    BigDecimal valorPago,
    LocalDate dataPagamento,
    String tenantId
) {}
