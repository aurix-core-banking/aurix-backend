package com.aurix.platform.cards.dto;

import java.math.BigDecimal;

public class ProdutoCartaoResponse {

    private Long id;
    private String nome;
    private String bandeira;
    private String adquirente;
    private BigDecimal anuidade;
    private BigDecimal taxaJuros;
    private BigDecimal taxaMora;
    private BigDecimal limiteMinimo;
    private BigDecimal limiteMaximo;
    private String programaPontos;
    private Boolean ativo;
    private AuditMetaDTO auditoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public AuditMetaDTO getAuditoria() { return auditoria; }
    public void setAuditoria(AuditMetaDTO auditoria) { this.auditoria = auditoria; }
}
