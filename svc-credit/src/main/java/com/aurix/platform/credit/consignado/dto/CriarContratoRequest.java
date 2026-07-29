package com.aurix.platform.credit.consignado.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CriarContratoRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long contaSalarioId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorTotal;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal taxaJuros;

    @Min(1)
    private int prazoMeses;

    @NotBlank
    private String fonteMargem;

    public CriarContratoRequest() {}

    public CriarContratoRequest(Long clienteId, Long contaSalarioId, BigDecimal valorTotal, BigDecimal taxaJuros, int prazoMeses, String fonteMargem) {
        this.clienteId = clienteId;
        this.contaSalarioId = contaSalarioId;
        this.valorTotal = valorTotal;
        this.taxaJuros = taxaJuros;
        this.prazoMeses = prazoMeses;
        this.fonteMargem = fonteMargem;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long contaSalarioId) { this.contaSalarioId = contaSalarioId; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public String getFonteMargem() { return fonteMargem; }
    public void setFonteMargem(String fonteMargem) { this.fonteMargem = fonteMargem; }
}
