package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AutorizarTransacaoRequest {

    @NotNull
    private Long cartaoId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;

    @NotBlank
    private String estabelecimento;

    @NotNull
    private String modo;

    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(String estabelecimento) { this.estabelecimento = estabelecimento; }
    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
}
