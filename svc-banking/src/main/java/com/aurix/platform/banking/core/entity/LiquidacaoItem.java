package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "liquidacao_itens", schema = "aurix")
public class LiquidacaoItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquidacao_id", nullable = false)
    private Liquidacao liquidacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimento tipoMovimento;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorMovimento;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoAnterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoPosterior;
    @Column
    private LocalDateTime dataMovimento;
    @Column(length = 1000)
    private String descricaoMovimento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_movimento", columnDefinition = "JSONB")
    private String detalhesMovimento;
    @Column
    private Boolean processado = false;
    @Column
    private String codigoMovimento;
    @Column
    private String codigoContraparte;


    public enum TipoMovimento {
        DEBITO, CREDITO, BLOQUEIO, DESBLOQUEIO, RESERVA, LIBERACAO_RESERVA;
    }

@java.lang.SuppressWarnings("all")
    public Liquidacao getLiquidacao() {
        return this.liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public TipoMovimento getTipoMovimento() {
        return this.tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMovimento() {
        return this.valorMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoAnterior() {
        return this.saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoPosterior() {
        return this.saldoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataMovimento() {
        return this.dataMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoMovimento() {
        return this.descricaoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesMovimento() {
        return this.detalhesMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getProcessado() {
        return this.processado;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoMovimento() {
        return this.codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoContraparte() {
        return this.codigoContraparte;
    }

@java.lang.SuppressWarnings("all")
    public void setLiquidacao(final Liquidacao liquidacao) {
        this.liquidacao = liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoMovimento(final TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMovimento(final BigDecimal valorMovimento) {
        this.valorMovimento = valorMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoAnterior(final BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoPosterior(final BigDecimal saldoPosterior) {
        this.saldoPosterior = saldoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataMovimento(final LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoMovimento(final String descricaoMovimento) {
        this.descricaoMovimento = descricaoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesMovimento(final String detalhesMovimento) {
        this.detalhesMovimento = detalhesMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessado(final Boolean processado) {
        this.processado = processado;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoMovimento(final String codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoContraparte(final String codigoContraparte) {
        this.codigoContraparte = codigoContraparte;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LiquidacaoItem(id=" + this.getId() + ", liquidacao=" + this.getLiquidacao() + ", conta=" + this.getConta() + ", tipoMovimento=" + this.getTipoMovimento() + ", valorMovimento=" + this.getValorMovimento() + ", saldoAnterior=" + this.getSaldoAnterior() + ", saldoPosterior=" + this.getSaldoPosterior() + ", dataMovimento=" + this.getDataMovimento() + ", descricaoMovimento=" + this.getDescricaoMovimento() + ", detalhesMovimento=" + this.getDetalhesMovimento() + ", processado=" + this.getProcessado() + ", codigoMovimento=" + this.getCodigoMovimento() + ", codigoContraparte=" + this.getCodigoContraparte() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LiquidacaoItem() {
    }

    @java.lang.SuppressWarnings("all")
    public LiquidacaoItem(final Long id, final Liquidacao liquidacao, final Conta conta, final TipoMovimento tipoMovimento, final BigDecimal valorMovimento, final BigDecimal saldoAnterior, final BigDecimal saldoPosterior, final LocalDateTime dataMovimento, final String descricaoMovimento, final String detalhesMovimento, final Boolean processado, final String codigoMovimento, final String codigoContraparte) {
        this.setId(id);
        this.liquidacao = liquidacao;
        this.conta = conta;
        this.tipoMovimento = tipoMovimento;
        this.valorMovimento = valorMovimento;
        this.saldoAnterior = saldoAnterior;
        this.saldoPosterior = saldoPosterior;
        this.dataMovimento = dataMovimento;
        this.descricaoMovimento = descricaoMovimento;
        this.detalhesMovimento = detalhesMovimento;
        this.processado = processado;
        this.codigoMovimento = codigoMovimento;
        this.codigoContraparte = codigoContraparte;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LiquidacaoItem)) return false;
        final LiquidacaoItem other = (LiquidacaoItem) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$processado = this.getProcessado();
        final java.lang.Object other$processado = other.getProcessado();
        if (this$processado == null ? other$processado != null : !this$processado.equals(other$processado)) return false;
        final java.lang.Object this$liquidacao = this.getLiquidacao();
        final java.lang.Object other$liquidacao = other.getLiquidacao();
        if (this$liquidacao == null ? other$liquidacao != null : !this$liquidacao.equals(other$liquidacao)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tipoMovimento = this.getTipoMovimento();
        final java.lang.Object other$tipoMovimento = other.getTipoMovimento();
        if (this$tipoMovimento == null ? other$tipoMovimento != null : !this$tipoMovimento.equals(other$tipoMovimento)) return false;
        final java.lang.Object this$valorMovimento = this.getValorMovimento();
        final java.lang.Object other$valorMovimento = other.getValorMovimento();
        if (this$valorMovimento == null ? other$valorMovimento != null : !this$valorMovimento.equals(other$valorMovimento)) return false;
        final java.lang.Object this$saldoAnterior = this.getSaldoAnterior();
        final java.lang.Object other$saldoAnterior = other.getSaldoAnterior();
        if (this$saldoAnterior == null ? other$saldoAnterior != null : !this$saldoAnterior.equals(other$saldoAnterior)) return false;
        final java.lang.Object this$saldoPosterior = this.getSaldoPosterior();
        final java.lang.Object other$saldoPosterior = other.getSaldoPosterior();
        if (this$saldoPosterior == null ? other$saldoPosterior != null : !this$saldoPosterior.equals(other$saldoPosterior)) return false;
        final java.lang.Object this$dataMovimento = this.getDataMovimento();
        final java.lang.Object other$dataMovimento = other.getDataMovimento();
        if (this$dataMovimento == null ? other$dataMovimento != null : !this$dataMovimento.equals(other$dataMovimento)) return false;
        final java.lang.Object this$descricaoMovimento = this.getDescricaoMovimento();
        final java.lang.Object other$descricaoMovimento = other.getDescricaoMovimento();
        if (this$descricaoMovimento == null ? other$descricaoMovimento != null : !this$descricaoMovimento.equals(other$descricaoMovimento)) return false;
        final java.lang.Object this$detalhesMovimento = this.getDetalhesMovimento();
        final java.lang.Object other$detalhesMovimento = other.getDetalhesMovimento();
        if (this$detalhesMovimento == null ? other$detalhesMovimento != null : !this$detalhesMovimento.equals(other$detalhesMovimento)) return false;
        final java.lang.Object this$codigoMovimento = this.getCodigoMovimento();
        final java.lang.Object other$codigoMovimento = other.getCodigoMovimento();
        if (this$codigoMovimento == null ? other$codigoMovimento != null : !this$codigoMovimento.equals(other$codigoMovimento)) return false;
        final java.lang.Object this$codigoContraparte = this.getCodigoContraparte();
        final java.lang.Object other$codigoContraparte = other.getCodigoContraparte();
        if (this$codigoContraparte == null ? other$codigoContraparte != null : !this$codigoContraparte.equals(other$codigoContraparte)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LiquidacaoItem;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $processado = this.getProcessado();
        result = result * PRIME + ($processado == null ? 43 : $processado.hashCode());
        final java.lang.Object $liquidacao = this.getLiquidacao();
        result = result * PRIME + ($liquidacao == null ? 43 : $liquidacao.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tipoMovimento = this.getTipoMovimento();
        result = result * PRIME + ($tipoMovimento == null ? 43 : $tipoMovimento.hashCode());
        final java.lang.Object $valorMovimento = this.getValorMovimento();
        result = result * PRIME + ($valorMovimento == null ? 43 : $valorMovimento.hashCode());
        final java.lang.Object $saldoAnterior = this.getSaldoAnterior();
        result = result * PRIME + ($saldoAnterior == null ? 43 : $saldoAnterior.hashCode());
        final java.lang.Object $saldoPosterior = this.getSaldoPosterior();
        result = result * PRIME + ($saldoPosterior == null ? 43 : $saldoPosterior.hashCode());
        final java.lang.Object $dataMovimento = this.getDataMovimento();
        result = result * PRIME + ($dataMovimento == null ? 43 : $dataMovimento.hashCode());
        final java.lang.Object $descricaoMovimento = this.getDescricaoMovimento();
        result = result * PRIME + ($descricaoMovimento == null ? 43 : $descricaoMovimento.hashCode());
        final java.lang.Object $detalhesMovimento = this.getDetalhesMovimento();
        result = result * PRIME + ($detalhesMovimento == null ? 43 : $detalhesMovimento.hashCode());
        final java.lang.Object $codigoMovimento = this.getCodigoMovimento();
        result = result * PRIME + ($codigoMovimento == null ? 43 : $codigoMovimento.hashCode());
        final java.lang.Object $codigoContraparte = this.getCodigoContraparte();
        result = result * PRIME + ($codigoContraparte == null ? 43 : $codigoContraparte.hashCode());
        return result;
    }
}
