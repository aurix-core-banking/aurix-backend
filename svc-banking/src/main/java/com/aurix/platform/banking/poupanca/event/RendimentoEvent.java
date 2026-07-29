package com.aurix.platform.banking.poupanca.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RendimentoEvent(
    Long contaId,
    BigDecimal valor,
    BigDecimal tr,
    BigDecimal saldoPosterior,
    LocalDate dataAniversario,
    String tenantId
) {}
