package com.aurix.platform.cambio.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ClienteCambioRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private BigDecimal limiteRemessaMensal;

    @NotNull
    private BigDecimal limiteRemessaAnual;

    private String categoriasAutorizadas;

    public ClienteCambioRequest() {}

    public ClienteCambioRequest(Long clienteId, BigDecimal limiteRemessaMensal, BigDecimal limiteRemessaAnual, String categoriasAutorizadas) {
        this.clienteId = clienteId;
        this.limiteRemessaMensal = limiteRemessaMensal;
        this.limiteRemessaAnual = limiteRemessaAnual;
        this.categoriasAutorizadas = categoriasAutorizadas;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getLimiteRemessaMensal() { return limiteRemessaMensal; }
    public void setLimiteRemessaMensal(BigDecimal limiteRemessaMensal) { this.limiteRemessaMensal = limiteRemessaMensal; }
    public BigDecimal getLimiteRemessaAnual() { return limiteRemessaAnual; }
    public void setLimiteRemessaAnual(BigDecimal limiteRemessaAnual) { this.limiteRemessaAnual = limiteRemessaAnual; }
    public String getCategoriasAutorizadas() { return categoriasAutorizadas; }
    public void setCategoriasAutorizadas(String categoriasAutorizadas) { this.categoriasAutorizadas = categoriasAutorizadas; }
}
