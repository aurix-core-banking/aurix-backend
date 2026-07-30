package com.aurix.platform.credit.consignado.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RenegociarRequest {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal novoValor;

    @Min(1)
    private int novoPrazoMeses;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal novaTaxaJuros;

    public RenegociarRequest() {}

    public RenegociarRequest(BigDecimal novoValor, int novoPrazoMeses, BigDecimal novaTaxaJuros) {
        this.novoValor = novoValor;
        this.novoPrazoMeses = novoPrazoMeses;
        this.novaTaxaJuros = novaTaxaJuros;
    }

    public BigDecimal getNovoValor() { return novoValor; }
    public void setNovoValor(BigDecimal novoValor) { this.novoValor = novoValor; }
    public int getNovoPrazoMeses() { return novoPrazoMeses; }
    public void setNovoPrazoMeses(int novoPrazoMeses) { this.novoPrazoMeses = novoPrazoMeses; }
    public BigDecimal getNovaTaxaJuros() { return novaTaxaJuros; }
    public void setNovaTaxaJuros(BigDecimal novaTaxaJuros) { this.novaTaxaJuros = novaTaxaJuros; }
}
