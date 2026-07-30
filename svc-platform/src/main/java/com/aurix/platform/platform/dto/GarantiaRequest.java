package com.aurix.platform.platform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GarantiaRequest {

    @NotNull
    private Long contratoId;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long bemId;

    @NotBlank
    private String tipo;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;

    @NotNull
    private LocalDate dataVencimento;

    public GarantiaRequest() {}

    public GarantiaRequest(Long contratoId, Long clienteId, Long bemId, String tipo, BigDecimal valor, LocalDate dataVencimento) {
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.bemId = bemId;
        this.tipo = tipo;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long v) { this.contratoId = v; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long v) { this.clienteId = v; }
    public Long getBemId() { return bemId; }
    public void setBemId(Long v) { this.bemId = v; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate v) { this.dataVencimento = v; }
}
