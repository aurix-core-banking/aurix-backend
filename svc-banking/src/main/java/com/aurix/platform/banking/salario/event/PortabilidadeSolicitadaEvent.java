package com.aurix.platform.banking.salario.event;

import java.math.BigDecimal;

public record PortabilidadeSolicitadaEvent(
    Long portabilidadeId,
    Long contaSalarioId,
    String codigoBancoDestino,
    BigDecimal valorPercentual
) {}
