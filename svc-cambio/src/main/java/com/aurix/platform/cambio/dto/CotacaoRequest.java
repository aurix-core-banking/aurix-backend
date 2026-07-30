package com.aurix.platform.cambio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CotacaoRequest {

    @NotBlank
    private String moeda;

    @NotNull
    private BigDecimal taxaCompra;

    @NotNull
    private BigDecimal taxaVenda;

    private String fonte;

    public CotacaoRequest() {}

    public CotacaoRequest(String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda, String fonte) {
        this.moeda = moeda;
        this.taxaCompra = taxaCompra;
        this.taxaVenda = taxaVenda;
        this.fonte = fonte;
    }

    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }
    public BigDecimal getTaxaCompra() { return taxaCompra; }
    public void setTaxaCompra(BigDecimal taxaCompra) { this.taxaCompra = taxaCompra; }
    public BigDecimal getTaxaVenda() { return taxaVenda; }
    public void setTaxaVenda(BigDecimal taxaVenda) { this.taxaVenda = taxaVenda; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
}
