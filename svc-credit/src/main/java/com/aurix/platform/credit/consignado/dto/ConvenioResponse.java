package com.aurix.platform.credit.consignado.dto;

public class ConvenioResponse {

    private Long id;
    private String nome;
    private String tipo;
    private String codigoFonte;
    private boolean ativo;

    public ConvenioResponse() {}

    public ConvenioResponse(Long id, String nome, String tipo, String codigoFonte, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.codigoFonte = codigoFonte;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCodigoFonte() { return codigoFonte; }
    public void setCodigoFonte(String codigoFonte) { this.codigoFonte = codigoFonte; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
