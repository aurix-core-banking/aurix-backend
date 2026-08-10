package com.aurix.platform.products.dto;

import java.math.BigDecimal;

public record PerfilClienteRequest(
    BigDecimal rendaMensal,
    Integer idade,
    Integer score,
    String segmento,
    String tipoPessoa,
    Boolean negativado
) {
}
