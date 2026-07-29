package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.ProdutoFinanceiro;
import java.math.BigDecimal;

public class ProdutoFinanceiroDTO {
    private Long id;
    private String codigoProduto;
    private String nomeProduto;
    private String descricao;
    private ProdutoFinanceiro.TipoProduto tipoProduto;
    private ProdutoFinanceiro.CategoriaProduto categoriaProduto;
    private BigDecimal valorMinimoAplicacao;
    private BigDecimal valorMaximoAplicacao;
    private BigDecimal taxaRemuneracao;

    public static ProdutoFinanceiroDTO fromEntity(ProdutoFinanceiro e) {
        if (e == null) return null;
        ProdutoFinanceiroDTO dto = new ProdutoFinanceiroDTO();
        dto.setId(e.getId());
        dto.setCodigoProduto(e.getCodigoProduto());
        dto.setNomeProduto(e.getNomeProduto());
        dto.setDescricao(e.getDescricao());
        dto.setTipoProduto(e.getTipoProduto());
        dto.setCategoriaProduto(e.getCategoriaProduto());
        dto.setValorMinimoAplicacao(e.getValorMinimoAplicacao());
        dto.setValorMaximoAplicacao(e.getValorMaximoAplicacao());
        dto.setTaxaRemuneracao(e.getTaxaRemuneracao());
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoProduto() {
        return this.codigoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeProduto() {
        return this.nomeProduto;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro.TipoProduto getTipoProduto() {
        return this.tipoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro.CategoriaProduto getCategoriaProduto() {
        return this.categoriaProduto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMinimoAplicacao() {
        return this.valorMinimoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMaximoAplicacao() {
        return this.valorMaximoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracao() {
        return this.taxaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoProduto(final String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeProduto(final String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoProduto(final ProdutoFinanceiro.TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaProduto(final ProdutoFinanceiro.CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMinimoAplicacao(final BigDecimal valorMinimoAplicacao) {
        this.valorMinimoAplicacao = valorMinimoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMaximoAplicacao(final BigDecimal valorMaximoAplicacao) {
        this.valorMaximoAplicacao = valorMaximoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracao(final BigDecimal taxaRemuneracao) {
        this.taxaRemuneracao = taxaRemuneracao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProdutoFinanceiroDTO)) return false;
        final ProdutoFinanceiroDTO other = (ProdutoFinanceiroDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$codigoProduto = this.getCodigoProduto();
        final java.lang.Object other$codigoProduto = other.getCodigoProduto();
        if (this$codigoProduto == null ? other$codigoProduto != null : !this$codigoProduto.equals(other$codigoProduto)) return false;
        final java.lang.Object this$nomeProduto = this.getNomeProduto();
        final java.lang.Object other$nomeProduto = other.getNomeProduto();
        if (this$nomeProduto == null ? other$nomeProduto != null : !this$nomeProduto.equals(other$nomeProduto)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoProduto = this.getTipoProduto();
        final java.lang.Object other$tipoProduto = other.getTipoProduto();
        if (this$tipoProduto == null ? other$tipoProduto != null : !this$tipoProduto.equals(other$tipoProduto)) return false;
        final java.lang.Object this$categoriaProduto = this.getCategoriaProduto();
        final java.lang.Object other$categoriaProduto = other.getCategoriaProduto();
        if (this$categoriaProduto == null ? other$categoriaProduto != null : !this$categoriaProduto.equals(other$categoriaProduto)) return false;
        final java.lang.Object this$valorMinimoAplicacao = this.getValorMinimoAplicacao();
        final java.lang.Object other$valorMinimoAplicacao = other.getValorMinimoAplicacao();
        if (this$valorMinimoAplicacao == null ? other$valorMinimoAplicacao != null : !this$valorMinimoAplicacao.equals(other$valorMinimoAplicacao)) return false;
        final java.lang.Object this$valorMaximoAplicacao = this.getValorMaximoAplicacao();
        final java.lang.Object other$valorMaximoAplicacao = other.getValorMaximoAplicacao();
        if (this$valorMaximoAplicacao == null ? other$valorMaximoAplicacao != null : !this$valorMaximoAplicacao.equals(other$valorMaximoAplicacao)) return false;
        final java.lang.Object this$taxaRemuneracao = this.getTaxaRemuneracao();
        final java.lang.Object other$taxaRemuneracao = other.getTaxaRemuneracao();
        if (this$taxaRemuneracao == null ? other$taxaRemuneracao != null : !this$taxaRemuneracao.equals(other$taxaRemuneracao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ProdutoFinanceiroDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $codigoProduto = this.getCodigoProduto();
        result = result * PRIME + ($codigoProduto == null ? 43 : $codigoProduto.hashCode());
        final java.lang.Object $nomeProduto = this.getNomeProduto();
        result = result * PRIME + ($nomeProduto == null ? 43 : $nomeProduto.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoProduto = this.getTipoProduto();
        result = result * PRIME + ($tipoProduto == null ? 43 : $tipoProduto.hashCode());
        final java.lang.Object $categoriaProduto = this.getCategoriaProduto();
        result = result * PRIME + ($categoriaProduto == null ? 43 : $categoriaProduto.hashCode());
        final java.lang.Object $valorMinimoAplicacao = this.getValorMinimoAplicacao();
        result = result * PRIME + ($valorMinimoAplicacao == null ? 43 : $valorMinimoAplicacao.hashCode());
        final java.lang.Object $valorMaximoAplicacao = this.getValorMaximoAplicacao();
        result = result * PRIME + ($valorMaximoAplicacao == null ? 43 : $valorMaximoAplicacao.hashCode());
        final java.lang.Object $taxaRemuneracao = this.getTaxaRemuneracao();
        result = result * PRIME + ($taxaRemuneracao == null ? 43 : $taxaRemuneracao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ProdutoFinanceiroDTO(id=" + this.getId() + ", codigoProduto=" + this.getCodigoProduto() + ", nomeProduto=" + this.getNomeProduto() + ", descricao=" + this.getDescricao() + ", tipoProduto=" + this.getTipoProduto() + ", categoriaProduto=" + this.getCategoriaProduto() + ", valorMinimoAplicacao=" + this.getValorMinimoAplicacao() + ", valorMaximoAplicacao=" + this.getValorMaximoAplicacao() + ", taxaRemuneracao=" + this.getTaxaRemuneracao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiroDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiroDTO(final Long id, final String codigoProduto, final String nomeProduto, final String descricao, final ProdutoFinanceiro.TipoProduto tipoProduto, final ProdutoFinanceiro.CategoriaProduto categoriaProduto, final BigDecimal valorMinimoAplicacao, final BigDecimal valorMaximoAplicacao, final BigDecimal taxaRemuneracao) {
        this.id = id;
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.tipoProduto = tipoProduto;
        this.categoriaProduto = categoriaProduto;
        this.valorMinimoAplicacao = valorMinimoAplicacao;
        this.valorMaximoAplicacao = valorMaximoAplicacao;
        this.taxaRemuneracao = taxaRemuneracao;
    }
}
