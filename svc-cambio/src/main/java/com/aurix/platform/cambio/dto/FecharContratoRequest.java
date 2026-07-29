package com.aurix.platform.cambio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FecharContratoRequest {

    @NotNull
    private Long clienteId;

    @NotBlank
    private String tipo;

    @NotBlank
    private String moedaOrigem;

    @NotBlank
    private String moedaDestino;

    @NotNull
    private BigDecimal valorOrigem;

    @NotNull
    private BigDecimal taxaCambio;

    private String finalidade;

    public FecharContratoRequest() {}

    public FecharContratoRequest(Long clienteId, String tipo, String moedaOrigem, String moedaDestino, BigDecimal valorOrigem, BigDecimal taxaCambio, String finalidade) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.valorOrigem = valorOrigem;
        this.taxaCambio = taxaCambio;
        this.finalidade = finalidade;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMoedaOrigem() { return moedaOrigem; }
    public void setMoedaOrigem(String moedaOrigem) { this.moedaOrigem = moedaOrigem; }
    public String getMoedaDestino() { return moedaDestino; }
    public void setMoedaDestino(String moedaDestino) { this.moedaDestino = moedaDestino; }
    public BigDecimal getValorOrigem() { return valorOrigem; }
    public void setValorOrigem(BigDecimal valorOrigem) { this.valorOrigem = valorOrigem; }
    public BigDecimal getTaxaCambio() { return taxaCambio; }
    public void setTaxaCambio(BigDecimal taxaCambio) { this.taxaCambio = taxaCambio; }
    public String getFinalidade() { return finalidade; }
    public void setFinalidade(String finalidade) { this.finalidade = finalidade; }
}
