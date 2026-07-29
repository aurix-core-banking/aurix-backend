package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;

public class TaxasResponse {

    private BigDecimal taxaSAC;
    private BigDecimal taxaPrice;
    private BigDecimal taxaSACRE;
    private BigDecimal cetTaxa;

    public TaxasResponse() {}

    public TaxasResponse(BigDecimal taxaSAC, BigDecimal taxaPrice, BigDecimal taxaSACRE, BigDecimal cetTaxa) {
        this.taxaSAC = taxaSAC;
        this.taxaPrice = taxaPrice;
        this.taxaSACRE = taxaSACRE;
        this.cetTaxa = cetTaxa;
    }

    public BigDecimal getTaxaSAC() { return taxaSAC; }
    public void setTaxaSAC(BigDecimal taxaSAC) { this.taxaSAC = taxaSAC; }
    public BigDecimal getTaxaPrice() { return taxaPrice; }
    public void setTaxaPrice(BigDecimal taxaPrice) { this.taxaPrice = taxaPrice; }
    public BigDecimal getTaxaSACRE() { return taxaSACRE; }
    public void setTaxaSACRE(BigDecimal taxaSACRE) { this.taxaSACRE = taxaSACRE; }
    public BigDecimal getCetTaxa() { return cetTaxa; }
    public void setCetTaxa(BigDecimal cetTaxa) { this.cetTaxa = cetTaxa; }
}
