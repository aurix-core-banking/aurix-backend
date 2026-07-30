package com.aurix.platform.credit.financiamento.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GarantiaRequest {

    @NotBlank
    private String tipo;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;

    private String orgaoRegistro;

    public GarantiaRequest() {}

    public GarantiaRequest(String tipo, BigDecimal valor, String orgaoRegistro) {
        this.tipo = tipo;
        this.valor = valor;
        this.orgaoRegistro = orgaoRegistro;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getOrgaoRegistro() { return orgaoRegistro; }
    public void setOrgaoRegistro(String orgaoRegistro) { this.orgaoRegistro = orgaoRegistro; }
}
