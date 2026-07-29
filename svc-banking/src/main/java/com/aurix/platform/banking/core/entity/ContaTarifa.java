package com.aurix.platform.banking.core.entity;

import com.aurix.platform.banking.core.entity.PacoteTarifas;
import com.aurix.platform.banking.core.entity.Tarifa;
import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "conta_tarifas", schema = "aurix")
public class ContaTarifa extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_tarifas_id")
    private PacoteTarifas pacoteTarifas;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorAplicado;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorOriginal;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualDesconto = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorDesconto = BigDecimal.ZERO;
    @Column(nullable = false)
    private Integer quantidadeUtilizada = 0;
    @Column(nullable = false)
    private Integer quantidadeLimite = 0;
    @Column(nullable = false)
    private Boolean ativa = true;
    @Column
    private LocalDateTime dataInicioVigencia;
    @Column
    private LocalDateTime dataFimVigencia;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_especiais", columnDefinition = "JSONB")
    private String configuracoesEspeciais;

@java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public Tarifa getTarifa() {
        return this.tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public PacoteTarifas getPacoteTarifas() {
        return this.pacoteTarifas;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAplicado() {
        return this.valorAplicado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualDesconto() {
        return this.percentualDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeUtilizada() {
        return this.quantidadeUtilizada;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeLimite() {
        return this.quantidadeLimite;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtiva() {
        return this.ativa;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicioVigencia() {
        return this.dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFimVigencia() {
        return this.dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesEspeciais() {
        return this.configuracoesEspeciais;
    }

@java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTarifa(final Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setPacoteTarifas(final PacoteTarifas pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAplicado(final BigDecimal valorAplicado) {
        this.valorAplicado = valorAplicado;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualDesconto(final BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDesconto(final BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeUtilizada(final Integer quantidadeUtilizada) {
        this.quantidadeUtilizada = quantidadeUtilizada;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeLimite(final Integer quantidadeLimite) {
        this.quantidadeLimite = quantidadeLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtiva(final Boolean ativa) {
        this.ativa = ativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioVigencia(final LocalDateTime dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFimVigencia(final LocalDateTime dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesEspeciais(final String configuracoesEspeciais) {
        this.configuracoesEspeciais = configuracoesEspeciais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ContaTarifa(id=" + this.getId() + ", conta=" + this.getConta() + ", tarifa=" + this.getTarifa() + ", pacoteTarifas=" + this.getPacoteTarifas() + ", valorAplicado=" + this.getValorAplicado() + ", valorOriginal=" + this.getValorOriginal() + ", percentualDesconto=" + this.getPercentualDesconto() + ", valorDesconto=" + this.getValorDesconto() + ", quantidadeUtilizada=" + this.getQuantidadeUtilizada() + ", quantidadeLimite=" + this.getQuantidadeLimite() + ", ativa=" + this.getAtiva() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ContaTarifa() {
    }

    @java.lang.SuppressWarnings("all")
    public ContaTarifa(final Long id, final Conta conta, final Tarifa tarifa, final PacoteTarifas pacoteTarifas, final BigDecimal valorAplicado, final BigDecimal valorOriginal, final BigDecimal percentualDesconto, final BigDecimal valorDesconto, final Integer quantidadeUtilizada, final Integer quantidadeLimite, final Boolean ativa, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final String configuracoesEspeciais) {
        this.setId(id);
        this.conta = conta;
        this.tarifa = tarifa;
        this.pacoteTarifas = pacoteTarifas;
        this.valorAplicado = valorAplicado;
        this.valorOriginal = valorOriginal;
        this.percentualDesconto = percentualDesconto;
        this.valorDesconto = valorDesconto;
        this.quantidadeUtilizada = quantidadeUtilizada;
        this.quantidadeLimite = quantidadeLimite;
        this.ativa = ativa;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFimVigencia = dataFimVigencia;
        this.configuracoesEspeciais = configuracoesEspeciais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ContaTarifa)) return false;
        final ContaTarifa other = (ContaTarifa) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeUtilizada = this.getQuantidadeUtilizada();
        final java.lang.Object other$quantidadeUtilizada = other.getQuantidadeUtilizada();
        if (this$quantidadeUtilizada == null ? other$quantidadeUtilizada != null : !this$quantidadeUtilizada.equals(other$quantidadeUtilizada)) return false;
        final java.lang.Object this$quantidadeLimite = this.getQuantidadeLimite();
        final java.lang.Object other$quantidadeLimite = other.getQuantidadeLimite();
        if (this$quantidadeLimite == null ? other$quantidadeLimite != null : !this$quantidadeLimite.equals(other$quantidadeLimite)) return false;
        final java.lang.Object this$ativa = this.getAtiva();
        final java.lang.Object other$ativa = other.getAtiva();
        if (this$ativa == null ? other$ativa != null : !this$ativa.equals(other$ativa)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tarifa = this.getTarifa();
        final java.lang.Object other$tarifa = other.getTarifa();
        if (this$tarifa == null ? other$tarifa != null : !this$tarifa.equals(other$tarifa)) return false;
        final java.lang.Object this$pacoteTarifas = this.getPacoteTarifas();
        final java.lang.Object other$pacoteTarifas = other.getPacoteTarifas();
        if (this$pacoteTarifas == null ? other$pacoteTarifas != null : !this$pacoteTarifas.equals(other$pacoteTarifas)) return false;
        final java.lang.Object this$valorAplicado = this.getValorAplicado();
        final java.lang.Object other$valorAplicado = other.getValorAplicado();
        if (this$valorAplicado == null ? other$valorAplicado != null : !this$valorAplicado.equals(other$valorAplicado)) return false;
        final java.lang.Object this$valorOriginal = this.getValorOriginal();
        final java.lang.Object other$valorOriginal = other.getValorOriginal();
        if (this$valorOriginal == null ? other$valorOriginal != null : !this$valorOriginal.equals(other$valorOriginal)) return false;
        final java.lang.Object this$percentualDesconto = this.getPercentualDesconto();
        final java.lang.Object other$percentualDesconto = other.getPercentualDesconto();
        if (this$percentualDesconto == null ? other$percentualDesconto != null : !this$percentualDesconto.equals(other$percentualDesconto)) return false;
        final java.lang.Object this$valorDesconto = this.getValorDesconto();
        final java.lang.Object other$valorDesconto = other.getValorDesconto();
        if (this$valorDesconto == null ? other$valorDesconto != null : !this$valorDesconto.equals(other$valorDesconto)) return false;
        final java.lang.Object this$dataInicioVigencia = this.getDataInicioVigencia();
        final java.lang.Object other$dataInicioVigencia = other.getDataInicioVigencia();
        if (this$dataInicioVigencia == null ? other$dataInicioVigencia != null : !this$dataInicioVigencia.equals(other$dataInicioVigencia)) return false;
        final java.lang.Object this$dataFimVigencia = this.getDataFimVigencia();
        final java.lang.Object other$dataFimVigencia = other.getDataFimVigencia();
        if (this$dataFimVigencia == null ? other$dataFimVigencia != null : !this$dataFimVigencia.equals(other$dataFimVigencia)) return false;
        final java.lang.Object this$configuracoesEspeciais = this.getConfiguracoesEspeciais();
        final java.lang.Object other$configuracoesEspeciais = other.getConfiguracoesEspeciais();
        if (this$configuracoesEspeciais == null ? other$configuracoesEspeciais != null : !this$configuracoesEspeciais.equals(other$configuracoesEspeciais)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ContaTarifa;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeUtilizada = this.getQuantidadeUtilizada();
        result = result * PRIME + ($quantidadeUtilizada == null ? 43 : $quantidadeUtilizada.hashCode());
        final java.lang.Object $quantidadeLimite = this.getQuantidadeLimite();
        result = result * PRIME + ($quantidadeLimite == null ? 43 : $quantidadeLimite.hashCode());
        final java.lang.Object $ativa = this.getAtiva();
        result = result * PRIME + ($ativa == null ? 43 : $ativa.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tarifa = this.getTarifa();
        result = result * PRIME + ($tarifa == null ? 43 : $tarifa.hashCode());
        final java.lang.Object $pacoteTarifas = this.getPacoteTarifas();
        result = result * PRIME + ($pacoteTarifas == null ? 43 : $pacoteTarifas.hashCode());
        final java.lang.Object $valorAplicado = this.getValorAplicado();
        result = result * PRIME + ($valorAplicado == null ? 43 : $valorAplicado.hashCode());
        final java.lang.Object $valorOriginal = this.getValorOriginal();
        result = result * PRIME + ($valorOriginal == null ? 43 : $valorOriginal.hashCode());
        final java.lang.Object $percentualDesconto = this.getPercentualDesconto();
        result = result * PRIME + ($percentualDesconto == null ? 43 : $percentualDesconto.hashCode());
        final java.lang.Object $valorDesconto = this.getValorDesconto();
        result = result * PRIME + ($valorDesconto == null ? 43 : $valorDesconto.hashCode());
        final java.lang.Object $dataInicioVigencia = this.getDataInicioVigencia();
        result = result * PRIME + ($dataInicioVigencia == null ? 43 : $dataInicioVigencia.hashCode());
        final java.lang.Object $dataFimVigencia = this.getDataFimVigencia();
        result = result * PRIME + ($dataFimVigencia == null ? 43 : $dataFimVigencia.hashCode());
        final java.lang.Object $configuracoesEspeciais = this.getConfiguracoesEspeciais();
        result = result * PRIME + ($configuracoesEspeciais == null ? 43 : $configuracoesEspeciais.hashCode());
        return result;
    }
}
