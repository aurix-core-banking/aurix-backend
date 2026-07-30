package com.aurix.platform.credit.financiamento.dto.request;

import java.math.BigDecimal;

public class RenegociarRequest {

    private int novosPrazos;

    private BigDecimal novaTaxa;

    public RenegociarRequest() {}

    public RenegociarRequest(int novosPrazos, BigDecimal novaTaxa) {
        this.novosPrazos = novosPrazos;
        this.novaTaxa = novaTaxa;
    }

    public int getNovosPrazos() { return novosPrazos; }
    public void setNovosPrazos(int novosPrazos) { this.novosPrazos = novosPrazos; }
    public BigDecimal getNovaTaxa() { return novaTaxa; }
    public void setNovaTaxa(BigDecimal novaTaxa) { this.novaTaxa = novaTaxa; }
}
