package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.NotBlank;

public class ParceiroAdquirenteRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String tipoEndpoint;

    private String config;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipoEndpoint() { return tipoEndpoint; }
    public void setTipoEndpoint(String tipoEndpoint) { this.tipoEndpoint = tipoEndpoint; }
    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }
}
