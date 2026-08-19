package com.aurix.platform.credit.renegociacao.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RenegociacaoParcelaResponse(
    Long id,
    int numero,
    LocalDate dataVencimento,
    BigDecimal valorParcela,
    BigDecimal valorAmortizacao,
    BigDecimal valorJuros,
    BigDecimal valorSaldoDevedor,
    LocalDate dataPagamento,
    String status
) {}
