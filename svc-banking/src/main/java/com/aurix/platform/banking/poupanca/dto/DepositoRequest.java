package com.aurix.platform.banking.poupanca.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositoRequest {

    @NotNull
    private Long contaPoupancaId;

    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "50000.00", message = "Valor maximo para deposito: 50000")
    private BigDecimal valor;

    public Long getContaPoupancaId() { return contaPoupancaId; }
    public void setContaPoupancaId(Long contaPoupancaId) { this.contaPoupancaId = contaPoupancaId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
