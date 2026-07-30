package com.aurix.platform.banking.salario.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreditoDiretoRequest {
    @NotNull
    private Long empresaId;
    @NotBlank
    private String cpfFuncionario;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorLiquido;
    private String descontos;

    public CreditoDiretoRequest() {}
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long v) { this.empresaId = v; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String v) { this.cpfFuncionario = v; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal v) { this.valorLiquido = v; }
    public String getDescontos() { return descontos; }
    public void setDescontos(String v) { this.descontos = v; }
}
