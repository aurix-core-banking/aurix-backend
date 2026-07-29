package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;

public class BemResponse {

    private Long id;
    private String tipo;
    private String descricao;
    private BigDecimal valorAvaliacao;
    private String chassi;
    private String placa;
    private String matriculaRGI;
    private String registroGarantia;

    public BemResponse() {}

    public BemResponse(Long id, String tipo, String descricao, BigDecimal valorAvaliacao, String chassi, String placa, String matriculaRGI, String registroGarantia) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorAvaliacao = valorAvaliacao;
        this.chassi = chassi;
        this.placa = placa;
        this.matriculaRGI = matriculaRGI;
        this.registroGarantia = registroGarantia;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValorAvaliacao() { return valorAvaliacao; }
    public void setValorAvaliacao(BigDecimal valorAvaliacao) { this.valorAvaliacao = valorAvaliacao; }
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMatriculaRGI() { return matriculaRGI; }
    public void setMatriculaRGI(String matriculaRGI) { this.matriculaRGI = matriculaRGI; }
    public String getRegistroGarantia() { return registroGarantia; }
    public void setRegistroGarantia(String registroGarantia) { this.registroGarantia = registroGarantia; }
}
