package com.aurix.platform.banking.poupanca.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoEvent(
    Long contaId,
    String tipo,
    BigDecimal valor,
    BigDecimal iof,
    BigDecimal saldoPosterior,
    LocalDateTime dataMovimentacao,
    String tenantId
) {}
