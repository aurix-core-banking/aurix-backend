package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;

public record LinhaTabela(int numero, BigDecimal valorParcela, BigDecimal amortizacao, BigDecimal juros, BigDecimal saldoDevedor) {}
