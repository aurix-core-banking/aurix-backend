package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos_cartao", schema = "aurix")
public class ProdutoCartao extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 20)
    private String bandeira;
    @Column(nullable = false, length = 20)
    private String adquirente;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal anuidade = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal taxaJuros = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal taxaMora = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteMinimo = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteMaximo = BigDecimal.ZERO;
    @Column(length = 200)
    private String programaPontos;
    @Column(nullable = false)
    private Boolean ativo = true;


    public enum Bandeira {
        VISA, MASTERCARD, ELO, PRIVATE_LABEL;
    }


    public enum Adquirente {
        REDE, STONE, GETNET, OWN_ACQUIRER;
    }

@java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    @java.lang.SuppressWarnings("all")
    public String getBandeira() {
        return this.bandeira;
    }

    @java.lang.SuppressWarnings("all")
    public String getAdquirente() {
        return this.adquirente;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getAnuidade() {
        return this.anuidade;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaJuros() {
        return this.taxaJuros;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaMora() {
        return this.taxaMora;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteMinimo() {
        return this.limiteMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteMaximo() {
        return this.limiteMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public String getProgramaPontos() {
        return this.programaPontos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

@java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    @java.lang.SuppressWarnings("all")
    public void setBandeira(final String bandeira) {
        this.bandeira = bandeira;
    }

    @java.lang.SuppressWarnings("all")
    public void setAdquirente(final String adquirente) {
        this.adquirente = adquirente;
    }

    @java.lang.SuppressWarnings("all")
    public void setAnuidade(final BigDecimal anuidade) {
        this.anuidade = anuidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaJuros(final BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaMora(final BigDecimal taxaMora) {
        this.taxaMora = taxaMora;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteMinimo(final BigDecimal limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteMaximo(final BigDecimal limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public void setProgramaPontos(final String programaPontos) {
        this.programaPontos = programaPontos;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ProdutoCartao(id=" + this.getId() + ", nome=" + this.getNome() + ", bandeira=" + this.getBandeira() + ", adquirente=" + this.getAdquirente() + ", anuidade=" + this.getAnuidade() + ", taxaJuros=" + this.getTaxaJuros() + ", taxaMora=" + this.getTaxaMora() + ", limiteMinimo=" + this.getLimiteMinimo() + ", limiteMaximo=" + this.getLimiteMaximo() + ", programaPontos=" + this.getProgramaPontos() + ", ativo=" + this.getAtivo() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCartao() {
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCartao(final Long id, final String nome, final String bandeira, final String adquirente, final BigDecimal anuidade, final BigDecimal taxaJuros, final BigDecimal taxaMora, final BigDecimal limiteMinimo, final BigDecimal limiteMaximo, final String programaPontos, final Boolean ativo) {
        this.setId(id);
        this.nome = nome;
        this.bandeira = bandeira;
        this.adquirente = adquirente;
        this.anuidade = anuidade;
        this.taxaJuros = taxaJuros;
        this.taxaMora = taxaMora;
        this.limiteMinimo = limiteMinimo;
        this.limiteMaximo = limiteMaximo;
        this.programaPontos = programaPontos;
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProdutoCartao)) return false;
        final ProdutoCartao other = (ProdutoCartao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$bandeira = this.getBandeira();
        final java.lang.Object other$bandeira = other.getBandeira();
        if (this$bandeira == null ? other$bandeira != null : !this$bandeira.equals(other$bandeira)) return false;
        final java.lang.Object this$adquirente = this.getAdquirente();
        final java.lang.Object other$adquirente = other.getAdquirente();
        if (this$adquirente == null ? other$adquirente != null : !this$adquirente.equals(other$adquirente)) return false;
        final java.lang.Object this$anuidade = this.getAnuidade();
        final java.lang.Object other$anuidade = other.getAnuidade();
        if (this$anuidade == null ? other$anuidade != null : !this$anuidade.equals(other$anuidade)) return false;
        final java.lang.Object this$taxaJuros = this.getTaxaJuros();
        final java.lang.Object other$taxaJuros = other.getTaxaJuros();
        if (this$taxaJuros == null ? other$taxaJuros != null : !this$taxaJuros.equals(other$taxaJuros)) return false;
        final java.lang.Object this$taxaMora = this.getTaxaMora();
        final java.lang.Object other$taxaMora = other.getTaxaMora();
        if (this$taxaMora == null ? other$taxaMora != null : !this$taxaMora.equals(other$taxaMora)) return false;
        final java.lang.Object this$limiteMinimo = this.getLimiteMinimo();
        final java.lang.Object other$limiteMinimo = other.getLimiteMinimo();
        if (this$limiteMinimo == null ? other$limiteMinimo != null : !this$limiteMinimo.equals(other$limiteMinimo)) return false;
        final java.lang.Object this$limiteMaximo = this.getLimiteMaximo();
        final java.lang.Object other$limiteMaximo = other.getLimiteMaximo();
        if (this$limiteMaximo == null ? other$limiteMaximo != null : !this$limiteMaximo.equals(other$limiteMaximo)) return false;
        final java.lang.Object this$programaPontos = this.getProgramaPontos();
        final java.lang.Object other$programaPontos = other.getProgramaPontos();
        if (this$programaPontos == null ? other$programaPontos != null : !this$programaPontos.equals(other$programaPontos)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ProdutoCartao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $bandeira = this.getBandeira();
        result = result * PRIME + ($bandeira == null ? 43 : $bandeira.hashCode());
        final java.lang.Object $adquirente = this.getAdquirente();
        result = result * PRIME + ($adquirente == null ? 43 : $adquirente.hashCode());
        final java.lang.Object $anuidade = this.getAnuidade();
        result = result * PRIME + ($anuidade == null ? 43 : $anuidade.hashCode());
        final java.lang.Object $taxaJuros = this.getTaxaJuros();
        result = result * PRIME + ($taxaJuros == null ? 43 : $taxaJuros.hashCode());
        final java.lang.Object $taxaMora = this.getTaxaMora();
        result = result * PRIME + ($taxaMora == null ? 43 : $taxaMora.hashCode());
        final java.lang.Object $limiteMinimo = this.getLimiteMinimo();
        result = result * PRIME + ($limiteMinimo == null ? 43 : $limiteMinimo.hashCode());
        final java.lang.Object $limiteMaximo = this.getLimiteMaximo();
        result = result * PRIME + ($limiteMaximo == null ? 43 : $limiteMaximo.hashCode());
        final java.lang.Object $programaPontos = this.getProgramaPontos();
        result = result * PRIME + ($programaPontos == null ? 43 : $programaPontos.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        return result;
    }
}
