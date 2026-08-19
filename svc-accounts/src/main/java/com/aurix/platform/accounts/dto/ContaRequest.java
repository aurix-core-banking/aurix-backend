package com.aurix.platform.accounts.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ContaRequest {

    @NotNull(message = "ID do cliente e obrigatorio")
    private Long clienteId;

    private String tipoConta;
    private BigDecimal saldoInicial;
    private BigDecimal limiteCredito;
    private String dadosExtras;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getDadosExtras() {
        return dadosExtras;
    }

    public void setDadosExtras(String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }
}
