package com.aurix.platform.seguros.produto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoSeguroResponse(
    Long id,
    String tenantId,
    String nome,
    String descricao,
    String tipo,
    String coberturaPadrao,
    BigDecimal taxaBase,
    BigDecimal premioMinimo,
    BigDecimal carenciaMeses,
    Integer prazoAnaliseDias,
    Integer prazoPagamentoSinistroDias,
    Boolean ativo,
    LocalDateTime dataCriacao
) {}
