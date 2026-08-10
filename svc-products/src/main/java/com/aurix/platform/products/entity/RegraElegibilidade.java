package com.aurix.platform.products.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "regras_elegibilidade", schema = "aurix")
public class RegraElegibilidade extends BaseEntity {

    public enum TipoRegra {
        RENDA_MINIMA, IDADE_MINIMA, IDADE_MAXIMA, SCORE_MINIMO, SEGMENTO, TIPO_PESSOA, NAO_NEGATIVADO
    }

    public enum Comparador {
        MAIOR_IGUAL, MENOR_IGUAL, IGUAL, DIFERENTE
    }

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_regra", nullable = false, length = 30)
    private TipoRegra tipoRegra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Comparador comparador;

    @Column(name = "valor_numerico", precision = 19, scale = 4)
    private BigDecimal valorNumerico;

    @Column(length = 50)
    private String valorTexto;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativa = Boolean.TRUE;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public TipoRegra getTipoRegra() {
        return tipoRegra;
    }

    public void setTipoRegra(TipoRegra tipoRegra) {
        this.tipoRegra = tipoRegra;
    }

    public Comparador getComparador() {
        return comparador;
    }

    public void setComparador(Comparador comparador) {
        this.comparador = comparador;
    }

    public BigDecimal getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(BigDecimal valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public String getValorTexto() {
        return valorTexto;
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}
