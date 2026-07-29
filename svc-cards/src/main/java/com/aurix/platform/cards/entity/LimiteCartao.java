package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limites_cartao", schema = "aurix")
public class LimiteCartao extends BaseEntity {
    @Column(name = "cartao_id", nullable = false)
    private Long cartaoId;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteTotal = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteDisponivel = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteUtilizado = BigDecimal.ZERO;
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

@java.lang.SuppressWarnings("all")
    public Long getCartaoId() {
        return this.cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteTotal() {
        return this.limiteTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteDisponivel() {
        return this.limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteUtilizado() {
        return this.limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

@java.lang.SuppressWarnings("all")
    public void setCartaoId(final Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteTotal(final BigDecimal limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteDisponivel(final BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteUtilizado(final BigDecimal limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LimiteCartao(id=" + this.getId() + ", cartaoId=" + this.getCartaoId() + ", limiteTotal=" + this.getLimiteTotal() + ", limiteDisponivel=" + this.getLimiteDisponivel() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LimiteCartao() {
    }

    @java.lang.SuppressWarnings("all")
    public LimiteCartao(final Long id, final Long cartaoId, final BigDecimal limiteTotal, final BigDecimal limiteDisponivel, final BigDecimal limiteUtilizado, final LocalDateTime dataAtualizacao) {
        this.setId(id);
        this.cartaoId = cartaoId;
        this.limiteTotal = limiteTotal;
        this.limiteDisponivel = limiteDisponivel;
        this.limiteUtilizado = limiteUtilizado;
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LimiteCartao)) return false;
        final LimiteCartao other = (LimiteCartao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$cartaoId = this.getCartaoId();
        final java.lang.Object other$cartaoId = other.getCartaoId();
        if (this$cartaoId == null ? other$cartaoId != null : !this$cartaoId.equals(other$cartaoId)) return false;
        final java.lang.Object this$limiteTotal = this.getLimiteTotal();
        final java.lang.Object other$limiteTotal = other.getLimiteTotal();
        if (this$limiteTotal == null ? other$limiteTotal != null : !this$limiteTotal.equals(other$limiteTotal)) return false;
        final java.lang.Object this$limiteDisponivel = this.getLimiteDisponivel();
        final java.lang.Object other$limiteDisponivel = other.getLimiteDisponivel();
        if (this$limiteDisponivel == null ? other$limiteDisponivel != null : !this$limiteDisponivel.equals(other$limiteDisponivel)) return false;
        final java.lang.Object this$limiteUtilizado = this.getLimiteUtilizado();
        final java.lang.Object other$limiteUtilizado = other.getLimiteUtilizado();
        if (this$limiteUtilizado == null ? other$limiteUtilizado != null : !this$limiteUtilizado.equals(other$limiteUtilizado)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LimiteCartao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $cartaoId = this.getCartaoId();
        result = result * PRIME + ($cartaoId == null ? 43 : $cartaoId.hashCode());
        final java.lang.Object $limiteTotal = this.getLimiteTotal();
        result = result * PRIME + ($limiteTotal == null ? 43 : $limiteTotal.hashCode());
        final java.lang.Object $limiteDisponivel = this.getLimiteDisponivel();
        result = result * PRIME + ($limiteDisponivel == null ? 43 : $limiteDisponivel.hashCode());
        final java.lang.Object $limiteUtilizado = this.getLimiteUtilizado();
        result = result * PRIME + ($limiteUtilizado == null ? 43 : $limiteUtilizado.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }
}
