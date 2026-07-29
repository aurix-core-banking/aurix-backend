package com.aurix.platform.cards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoCartaoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String bandeira;

    @NotBlank
    private String adquirente;

    @NotNull
    @DecimalMin("0")
    private BigDecimal anuidade;

    @NotNull
    @DecimalMin("0")
    private BigDecimal taxaJuros;

    @NotNull
    @DecimalMin("0")
    private BigDecimal taxaMora;

    @NotNull
    @DecimalMin("0")
    private BigDecimal limiteMinimo;

    @NotNull
    @DecimalMin("0")
    private BigDecimal limiteMaximo;

    private String programaPontos;

    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getBandeira() { return bandeira; }
    public void setBandeira(String bandeira) { this.bandeira = bandeira; }
    public String getAdquirente() { return adquirente; }
    public void setAdquirente(String adquirente) { this.adquirente = adquirente; }
    public BigDecimal getAnuidade() { return anuidade; }
    public void setAnuidade(BigDecimal anuidade) { this.anuidade = anuidade; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public BigDecimal getTaxaMora() { return taxaMora; }
    public void setTaxaMora(BigDecimal taxaMora) { this.taxaMora = taxaMora; }
    public BigDecimal getLimiteMinimo() { return limiteMinimo; }
    public void setLimiteMinimo(BigDecimal limiteMinimo) { this.limiteMinimo = limiteMinimo; }
    public BigDecimal getLimiteMaximo() { return limiteMaximo; }
    public void setLimiteMaximo(BigDecimal limiteMaximo) { this.limiteMaximo = limiteMaximo; }
    public String getProgramaPontos() { return programaPontos; }
    public void setProgramaPontos(String programaPontos) { this.programaPontos = programaPontos; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
