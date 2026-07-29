package com.aurix.platform.cards.dto;

import java.math.BigDecimal;

public class LimiteCartaoResponse {

    private BigDecimal limiteTotal;
    private BigDecimal limiteDisponivel;
    private BigDecimal limiteUtilizado;

    public BigDecimal getLimiteTotal() { return limiteTotal; }
    public void setLimiteTotal(BigDecimal limiteTotal) { this.limiteTotal = limiteTotal; }
    public BigDecimal getLimiteDisponivel() { return limiteDisponivel; }
    public void setLimiteDisponivel(BigDecimal limiteDisponivel) { this.limiteDisponivel = limiteDisponivel; }
    public BigDecimal getLimiteUtilizado() { return limiteUtilizado; }
    public void setLimiteUtilizado(BigDecimal limiteUtilizado) { this.limiteUtilizado = limiteUtilizado; }
}
