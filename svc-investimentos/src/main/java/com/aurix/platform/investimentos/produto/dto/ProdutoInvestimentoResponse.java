package com.aurix.platform.investimentos.produto.dto;

import com.aurix.platform.investimentos.indice.entity.TipoIndice;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProdutoInvestimentoResponse(
    Long id,
    String tenantId,
    String nome,
    String descricao,
    String tipo,
    String tipoRenda,
    BigDecimal taxaRendimento,
    BigDecimal taxaAdm,
    TipoIndice indexador,
    BigDecimal percentualIndexador,
    BigDecimal valorMinimo,
    Integer prazoMinimoDias,
    LocalDate dataVencimento,
    Integer carenciaDias,
    Boolean ativo,
    LocalDateTime dataCriacao
) {}
