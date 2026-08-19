package com.aurix.platform.credit.seguro.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CoberturaResponse(
    Long seguroId,
    Long contratoId,
    String statusSeguro,
    List<String> coberturasAtivas,
    LocalDate dataInicio,
    LocalDate dataFim,
    int carenciaDias,
    boolean dentroCarencia,
    BigDecimal saldoDevedorAtual,
    BigDecimal taxaMensal
) {}
