package com.aurix.platform.credit.financiamento.event;

import java.time.LocalDate;

public record GarantiaRegistradaEvent(
    Long garantiaId,
    Long contratoId,
    Long bemId,
    String tipo,
    String orgaoRegistro,
    LocalDate dataRegistro,
    String tenantId
) {}
