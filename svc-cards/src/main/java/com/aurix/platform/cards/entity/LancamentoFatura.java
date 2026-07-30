package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamentos_fatura", schema = "aurix")
public class LancamentoFatura extends BaseEntity {
    @Column(name = "fatura_id", nullable = false)
    private Long faturaId;
    @Column(nullable = false, length = 255)
    private String descricao;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valor = BigDecimal.ZERO;
    @Column(nullable = false)
    private LocalDateTime dataLancamento;
    @Column(length = 100)
    private String categoria;

@java.lang.SuppressWarnings("all")
    public Long getFaturaId() {
        return this.faturaId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLancamento() {
        return this.dataLancamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getCategoria() {
        return this.categoria;
    }

@java.lang.SuppressWarnings("all")
    public void setFaturaId(final Long faturaId) {
        this.faturaId = faturaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLancamento(final LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LancamentoFatura(id=" + this.getId() + ", faturaId=" + this.getFaturaId() + ", descricao=" + this.getDescricao() + ", valor=" + this.getValor() + ", dataLancamento=" + this.getDataLancamento() + ", categoria=" + this.getCategoria() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LancamentoFatura() {
    }

    @java.lang.SuppressWarnings("all")
    public LancamentoFatura(final Long id, final Long faturaId, final String descricao, final BigDecimal valor, final LocalDateTime dataLancamento, final String categoria) {
        this.setId(id);
        this.faturaId = faturaId;
        this.descricao = descricao;
        this.valor = valor;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LancamentoFatura)) return false;
        final LancamentoFatura other = (LancamentoFatura) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$faturaId = this.getFaturaId();
        final java.lang.Object other$faturaId = other.getFaturaId();
        if (this$faturaId == null ? other$faturaId != null : !this$faturaId.equals(other$faturaId)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$dataLancamento = this.getDataLancamento();
        final java.lang.Object other$dataLancamento = other.getDataLancamento();
        if (this$dataLancamento == null ? other$dataLancamento != null : !this$dataLancamento.equals(other$dataLancamento)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LancamentoFatura;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $faturaId = this.getFaturaId();
        result = result * PRIME + ($faturaId == null ? 43 : $faturaId.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $dataLancamento = this.getDataLancamento();
        result = result * PRIME + ($dataLancamento == null ? 43 : $dataLancamento.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        return result;
    }
}
