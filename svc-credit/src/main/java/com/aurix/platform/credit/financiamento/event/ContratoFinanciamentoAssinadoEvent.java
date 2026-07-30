package com.aurix.platform.credit.financiamento.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoFinanciamentoAssinadoEvent(
    Long id,
    Long clienteId,
    Long contaCorrenteId,
    String tipo,
    BigDecimal valorFinanciado,
    int prazoMeses,
    BigDecimal taxaJuros,
    LocalDate dataContratacao,
    String tenantId
) {}
