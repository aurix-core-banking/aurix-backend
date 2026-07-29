package com.aurix.platform.credit.financiamento.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SimulacaoRealizadaEvent(
    Long id,
    Long clienteId,
    String tipo,
    BigDecimal valorFinanciado,
    int prazoMeses,
    String sistemaAmortizacao,
    LocalDateTime dataSimulacao,
    String tenantId
) {}
