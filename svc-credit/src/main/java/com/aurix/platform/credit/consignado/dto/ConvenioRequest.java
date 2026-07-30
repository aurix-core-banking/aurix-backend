package com.aurix.platform.credit.consignado.dto;

import jakarta.validation.constraints.NotBlank;

public class ConvenioRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String tipo;

    private String codigoFonte;

    private boolean ativo;

    public ConvenioRequest() {}

    public ConvenioRequest(String nome, String tipo, String codigoFonte, boolean ativo) {
        this.nome = nome;
        this.tipo = tipo;
        this.codigoFonte = codigoFonte;
        this.ativo = ativo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCodigoFonte() { return codigoFonte; }
    public void setCodigoFonte(String codigoFonte) { this.codigoFonte = codigoFonte; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
