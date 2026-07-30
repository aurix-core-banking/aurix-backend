package com.aurix.platform.credit.financiamento.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BemRequest {

    @NotBlank
    private String tipo;

    @NotBlank
    private String descricao;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorAvaliacao;

    private String chassi;

    private String placa;

    private String matriculaRGI;

    public BemRequest() {}

    public BemRequest(String tipo, String descricao, BigDecimal valorAvaliacao, String chassi, String placa, String matriculaRGI) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorAvaliacao = valorAvaliacao;
        this.chassi = chassi;
        this.placa = placa;
        this.matriculaRGI = matriculaRGI;
    }

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
}
