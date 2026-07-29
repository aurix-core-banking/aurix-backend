package com.aurix.platform.cambio.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AtualizarLimiteRequest {

    @NotNull
    private BigDecimal limiteRemessaMensal;

    @NotNull
    private BigDecimal limiteRemessaAnual;

    public AtualizarLimiteRequest() {}

    public AtualizarLimiteRequest(BigDecimal limiteRemessaMensal, BigDecimal limiteRemessaAnual) {
        this.limiteRemessaMensal = limiteRemessaMensal;
        this.limiteRemessaAnual = limiteRemessaAnual;
    }

    public BigDecimal getLimiteRemessaMensal() { return limiteRemessaMensal; }
    public void setLimiteRemessaMensal(BigDecimal limiteRemessaMensal) { this.limiteRemessaMensal = limiteRemessaMensal; }
    public BigDecimal getLimiteRemessaAnual() { return limiteRemessaAnual; }
    public void setLimiteRemessaAnual(BigDecimal limiteRemessaAnual) { this.limiteRemessaAnual = limiteRemessaAnual; }
}
