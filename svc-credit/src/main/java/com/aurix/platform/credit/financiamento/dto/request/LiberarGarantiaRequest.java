package com.aurix.platform.credit.financiamento.dto.request;

import java.time.LocalDate;

public class LiberarGarantiaRequest {

    private LocalDate dataBaixa;

    public LiberarGarantiaRequest() {}

    public LiberarGarantiaRequest(LocalDate dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public LocalDate getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDate dataBaixa) { this.dataBaixa = dataBaixa; }
}
