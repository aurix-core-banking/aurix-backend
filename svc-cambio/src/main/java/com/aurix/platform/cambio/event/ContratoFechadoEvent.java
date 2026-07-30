package com.aurix.platform.cambio.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoFechadoEvent(
    Long contratoId,
    Long clienteId,
    String tipo,
    String moedaOrigem,
    String moedaDestino,
    BigDecimal valorOrigem,
    BigDecimal valorDestino,
    BigDecimal taxaCambio,
    String registroBACEN,
    LocalDate dataContratacao,
    String tenantId
) {}
