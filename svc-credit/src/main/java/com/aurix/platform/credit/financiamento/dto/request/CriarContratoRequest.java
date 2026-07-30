package com.aurix.platform.credit.financiamento.dto.request;

import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.entity.TipoFinanciamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CriarContratoRequest {

    @NotNull
    private Long clienteId;

    private Long contaCorrenteId;

    @NotNull
    private TipoFinanciamento tipo;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorFinanciado;

    private BigDecimal valorEntrada = BigDecimal.ZERO;

    @Min(1)
    private int prazoMeses;

    @NotNull
    private SistemaAmortizacao sistemaAmortizacao;

    private BemRequest bem;

    private GarantiaRequest garantia;

    public CriarContratoRequest() {}

    public CriarContratoRequest(Long clienteId, Long contaCorrenteId, TipoFinanciamento tipo, BigDecimal valorFinanciado, BigDecimal valorEntrada, int prazoMeses, SistemaAmortizacao sistemaAmortizacao, BemRequest bem, GarantiaRequest garantia) {
        this.clienteId = clienteId;
        this.contaCorrenteId = contaCorrenteId;
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.valorEntrada = valorEntrada;
        this.prazoMeses = prazoMeses;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.bem = bem;
        this.garantia = garantia;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public TipoFinanciamento getTipo() { return tipo; }
    public void setTipo(TipoFinanciamento tipo) { this.tipo = tipo; }
    public BigDecimal getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public BigDecimal getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(BigDecimal valorEntrada) { this.valorEntrada = valorEntrada; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public SistemaAmortizacao getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(SistemaAmortizacao sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public BemRequest getBem() { return bem; }
    public void setBem(BemRequest bem) { this.bem = bem; }
    public GarantiaRequest getGarantia() { return garantia; }
    public void setGarantia(GarantiaRequest garantia) { this.garantia = garantia; }
}
