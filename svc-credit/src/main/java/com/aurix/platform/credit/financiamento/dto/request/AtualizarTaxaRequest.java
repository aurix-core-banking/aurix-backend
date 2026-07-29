package com.aurix.platform.credit.financiamento.dto.request;

import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import java.math.BigDecimal;

public class AtualizarTaxaRequest {

    private SistemaAmortizacao sistemaAmortizacao;

    private BigDecimal taxa;

    public AtualizarTaxaRequest() {}

    public AtualizarTaxaRequest(SistemaAmortizacao sistemaAmortizacao, BigDecimal taxa) {
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.taxa = taxa;
    }

    public SistemaAmortizacao getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(SistemaAmortizacao sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public BigDecimal getTaxa() { return taxa; }
    public void setTaxa(BigDecimal taxa) { this.taxa = taxa; }
}
