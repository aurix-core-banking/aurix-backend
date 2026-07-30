package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmitirCartaoRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    private Long contaId;

    @NotBlank
    private String nomePortador;

    @NotNull
    private String tipo;

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getNomePortador() { return nomePortador; }
    public void setNomePortador(String nomePortador) { this.nomePortador = nomePortador; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
