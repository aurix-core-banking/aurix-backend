package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pacote_tarifa_itens", schema = "aurix")
public class PacoteTarifaItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_tarifas_id", nullable = false)
    private PacoteTarifas pacoteTarifas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;
    @Column(nullable = false)
    private Integer quantidadeInclusa = 0;
    @Column(nullable = false)
    private Integer quantidadeGratuita = 0;
    @Column(precision = 19, scale = 4)
    private BigDecimal descontoPercentual = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal descontoValor = BigDecimal.ZERO;
    @Column(nullable = false)
    private Boolean ativo = true;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_especiais", columnDefinition = "JSONB")
    private String regrasEspeciais;

@java.lang.SuppressWarnings("all")
    public PacoteTarifas getPacoteTarifas() {
        return this.pacoteTarifas;
    }

    @java.lang.SuppressWarnings("all")
    public Tarifa getTarifa() {
        return this.tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeInclusa() {
        return this.quantidadeInclusa;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeGratuita() {
        return this.quantidadeGratuita;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDescontoPercentual() {
        return this.descontoPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDescontoValor() {
        return this.descontoValor;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasEspeciais() {
        return this.regrasEspeciais;
    }

@java.lang.SuppressWarnings("all")
    public void setPacoteTarifas(final PacoteTarifas pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;
    }

    @java.lang.SuppressWarnings("all")
    public void setTarifa(final Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeInclusa(final Integer quantidadeInclusa) {
        this.quantidadeInclusa = quantidadeInclusa;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeGratuita(final Integer quantidadeGratuita) {
        this.quantidadeGratuita = quantidadeGratuita;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescontoPercentual(final BigDecimal descontoPercentual) {
        this.descontoPercentual = descontoPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescontoValor(final BigDecimal descontoValor) {
        this.descontoValor = descontoValor;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasEspeciais(final String regrasEspeciais) {
        this.regrasEspeciais = regrasEspeciais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PacoteTarifaItem(id=" + this.getId() + ", pacoteTarifas=" + this.getPacoteTarifas() + ", tarifa=" + this.getTarifa() + ", quantidadeInclusa=" + this.getQuantidadeInclusa() + ", quantidadeGratuita=" + this.getQuantidadeGratuita() + ", descontoPercentual=" + this.getDescontoPercentual() + ", descontoValor=" + this.getDescontoValor() + ", ativo=" + this.getAtivo() + ", regrasEspeciais=" + this.getRegrasEspeciais() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PacoteTarifaItem() {
    }

    @java.lang.SuppressWarnings("all")
    public PacoteTarifaItem(final Long id, final PacoteTarifas pacoteTarifas, final Tarifa tarifa, final Integer quantidadeInclusa, final Integer quantidadeGratuita, final BigDecimal descontoPercentual, final BigDecimal descontoValor, final Boolean ativo, final String regrasEspeciais) {
        this.setId(id);
        this.pacoteTarifas = pacoteTarifas;
        this.tarifa = tarifa;
        this.quantidadeInclusa = quantidadeInclusa;
        this.quantidadeGratuita = quantidadeGratuita;
        this.descontoPercentual = descontoPercentual;
        this.descontoValor = descontoValor;
        this.ativo = ativo;
        this.regrasEspeciais = regrasEspeciais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PacoteTarifaItem)) return false;
        final PacoteTarifaItem other = (PacoteTarifaItem) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeInclusa = this.getQuantidadeInclusa();
        final java.lang.Object other$quantidadeInclusa = other.getQuantidadeInclusa();
        if (this$quantidadeInclusa == null ? other$quantidadeInclusa != null : !this$quantidadeInclusa.equals(other$quantidadeInclusa)) return false;
        final java.lang.Object this$quantidadeGratuita = this.getQuantidadeGratuita();
        final java.lang.Object other$quantidadeGratuita = other.getQuantidadeGratuita();
        if (this$quantidadeGratuita == null ? other$quantidadeGratuita != null : !this$quantidadeGratuita.equals(other$quantidadeGratuita)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$pacoteTarifas = this.getPacoteTarifas();
        final java.lang.Object other$pacoteTarifas = other.getPacoteTarifas();
        if (this$pacoteTarifas == null ? other$pacoteTarifas != null : !this$pacoteTarifas.equals(other$pacoteTarifas)) return false;
        final java.lang.Object this$tarifa = this.getTarifa();
        final java.lang.Object other$tarifa = other.getTarifa();
        if (this$tarifa == null ? other$tarifa != null : !this$tarifa.equals(other$tarifa)) return false;
        final java.lang.Object this$descontoPercentual = this.getDescontoPercentual();
        final java.lang.Object other$descontoPercentual = other.getDescontoPercentual();
        if (this$descontoPercentual == null ? other$descontoPercentual != null : !this$descontoPercentual.equals(other$descontoPercentual)) return false;
        final java.lang.Object this$descontoValor = this.getDescontoValor();
        final java.lang.Object other$descontoValor = other.getDescontoValor();
        if (this$descontoValor == null ? other$descontoValor != null : !this$descontoValor.equals(other$descontoValor)) return false;
        final java.lang.Object this$regrasEspeciais = this.getRegrasEspeciais();
        final java.lang.Object other$regrasEspeciais = other.getRegrasEspeciais();
        if (this$regrasEspeciais == null ? other$regrasEspeciais != null : !this$regrasEspeciais.equals(other$regrasEspeciais)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PacoteTarifaItem;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeInclusa = this.getQuantidadeInclusa();
        result = result * PRIME + ($quantidadeInclusa == null ? 43 : $quantidadeInclusa.hashCode());
        final java.lang.Object $quantidadeGratuita = this.getQuantidadeGratuita();
        result = result * PRIME + ($quantidadeGratuita == null ? 43 : $quantidadeGratuita.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $pacoteTarifas = this.getPacoteTarifas();
        result = result * PRIME + ($pacoteTarifas == null ? 43 : $pacoteTarifas.hashCode());
        final java.lang.Object $tarifa = this.getTarifa();
        result = result * PRIME + ($tarifa == null ? 43 : $tarifa.hashCode());
        final java.lang.Object $descontoPercentual = this.getDescontoPercentual();
        result = result * PRIME + ($descontoPercentual == null ? 43 : $descontoPercentual.hashCode());
        final java.lang.Object $descontoValor = this.getDescontoValor();
        result = result * PRIME + ($descontoValor == null ? 43 : $descontoValor.hashCode());
        final java.lang.Object $regrasEspeciais = this.getRegrasEspeciais();
        result = result * PRIME + ($regrasEspeciais == null ? 43 : $regrasEspeciais.hashCode());
        return result;
    }
}
