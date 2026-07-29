package com.aurix.platform.cambio.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CotacaoAtualizadaEvent(
    Long cotacaoId,
    String moeda,
    BigDecimal taxaCompra,
    BigDecimal taxaVenda,
    LocalDateTime dataCotacao,
    String fonte,
    String tenantId
) {}
