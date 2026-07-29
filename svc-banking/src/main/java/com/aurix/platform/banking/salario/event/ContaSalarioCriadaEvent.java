package com.aurix.platform.banking.salario.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaSalarioCriadaEvent(
    Long contaSalarioId,
    Long clienteId,
    Long empresaId,
    LocalDate dataAdmissao,
    BigDecimal valorSalarioLiquido
) {}
