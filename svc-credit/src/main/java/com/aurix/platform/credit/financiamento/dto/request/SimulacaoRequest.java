package com.aurix.platform.credit.financiamento.dto.request;

import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.entity.TipoFinanciamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SimulacaoRequest {

    @NotNull
    private TipoFinanciamento tipo;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorFinanciado;

    @Min(1)
    private int prazoMeses;

    private BigDecimal taxaJuros;

    private SistemaAmortizacao sistemaAmortizacao;

    public SimulacaoRequest() {}

    public SimulacaoRequest(TipoFinanciamento tipo, BigDecimal valorFinanciado, int prazoMeses, BigDecimal taxaJuros, SistemaAmortizacao sistemaAmortizacao) {
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.prazoMeses = prazoMeses;
        this.taxaJuros = taxaJuros;
        this.sistemaAmortizacao = sistemaAmortizacao;
    }

    public TipoFinanciamento getTipo() { return tipo; }
    public void setTipo(TipoFinanciamento tipo) { this.tipo = tipo; }
    public BigDecimal getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public SistemaAmortizacao getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(SistemaAmortizacao sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
}
