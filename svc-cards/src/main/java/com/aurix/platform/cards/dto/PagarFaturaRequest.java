package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class PagarFaturaRequest {

    @DecimalMin("0.01")
    private BigDecimal valorPagamento;

    public BigDecimal getValorPagamento() { return valorPagamento; }
    public void setValorPagamento(BigDecimal valorPagamento) { this.valorPagamento = valorPagamento; }
}
