package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AjustarLimiteRequest {

    @NotNull
    @DecimalMin("1")
    private BigDecimal novoLimite;

    public BigDecimal getNovoLimite() { return novoLimite; }
    public void setNovoLimite(BigDecimal novoLimite) { this.novoLimite = novoLimite; }
}
