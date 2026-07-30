package com.aurix.platform.banking.salario.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PortabilidadeRequest {
    @NotNull
    private Long contaSalarioId;
    @NotBlank
    private String codigoBancoDestino;
    @NotBlank
    private String agenciaDestino;
    @NotBlank
    private String contaDestino;
    @DecimalMin("0.01")
    @DecimalMax("100.00")
    private BigDecimal valorPercentual = new BigDecimal("100.00");

    public PortabilidadeRequest() {}
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long v) { this.contaSalarioId = v; }
    public String getCodigoBancoDestino() { return codigoBancoDestino; }
    public void setCodigoBancoDestino(String v) { this.codigoBancoDestino = v; }
    public String getAgenciaDestino() { return agenciaDestino; }
    public void setAgenciaDestino(String v) { this.agenciaDestino = v; }
    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String v) { this.contaDestino = v; }
    public BigDecimal getValorPercentual() { return valorPercentual; }
    public void setValorPercentual(BigDecimal v) { this.valorPercentual = v; }
}
