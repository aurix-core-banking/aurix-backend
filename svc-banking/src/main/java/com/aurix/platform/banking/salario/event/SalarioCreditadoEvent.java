package com.aurix.platform.banking.salario.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalarioCreditadoEvent(
    Long contaSalarioId,
    BigDecimal valor,
    String tipo,
    Long empresaId,
    LocalDate dataReferencia
) {}
