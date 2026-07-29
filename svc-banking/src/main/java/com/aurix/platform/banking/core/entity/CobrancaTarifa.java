package com.aurix.platform.banking.core.entity;

import com.aurix.platform.banking.core.entity.Tarifa;
import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "cobrancas_tarifas", schema = "aurix")
public class CobrancaTarifa extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoCobranca;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorCobrado;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorOriginal;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualDesconto = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorDesconto = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCobranca status = StatusCobranca.PENDENTE;
    @Column
    private LocalDateTime dataCobranca;
    @Column
    private LocalDateTime dataVencimento;
    @Column
    private LocalDateTime dataPagamento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_cobranca", columnDefinition = "JSONB")
    private String detalhesCobranca;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;


    public enum StatusCobranca {
        PENDENTE, COBRADA, PAGA, CANCELADA, ESTORNADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoCobranca() {
        return this.codigoCobranca;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public Tarifa getTarifa() {
        return this.tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public Transacao getTransacao() {
        return this.transacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorCobrado() {
        return this.valorCobrado;
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
    public StatusCobranca getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCobranca() {
        return this.dataCobranca;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPagamento() {
        return this.dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesCobranca() {
        return this.detalhesCobranca;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoCobranca(final String codigoCobranca) {
        this.codigoCobranca = codigoCobranca;
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
    public void setTransacao(final Transacao transacao) {
        this.transacao = transacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorCobrado(final BigDecimal valorCobrado) {
        this.valorCobrado = valorCobrado;
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
    public void setStatus(final StatusCobranca status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCobranca(final LocalDateTime dataCobranca) {
        this.dataCobranca = dataCobranca;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamento(final LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesCobranca(final String detalhesCobranca) {
        this.detalhesCobranca = detalhesCobranca;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "CobrancaTarifa(id=" + this.getId() + ", codigoCobranca=" + this.getCodigoCobranca() + ", conta=" + this.getConta() + ", tarifa=" + this.getTarifa() + ", transacao=" + this.getTransacao() + ", valorCobrado=" + this.getValorCobrado() + ", valorOriginal=" + this.getValorOriginal() + ", percentualDesconto=" + this.getPercentualDesconto() + ", valorDesconto=" + this.getValorDesconto() + ", status=" + this.getStatus() + ", dataCobranca=" + this.getDataCobranca() + ", dataVencimento=" + this.getDataVencimento() + ", dataPagamento=" + this.getDataPagamento() + ", detalhesCobranca=" + this.getDetalhesCobranca() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public CobrancaTarifa() {
    }

    @java.lang.SuppressWarnings("all")
    public CobrancaTarifa(final Long id, final String codigoCobranca, final Conta conta, final Tarifa tarifa, final Transacao transacao, final BigDecimal valorCobrado, final BigDecimal valorOriginal, final BigDecimal percentualDesconto, final BigDecimal valorDesconto, final StatusCobranca status, final LocalDateTime dataCobranca, final LocalDateTime dataVencimento, final LocalDateTime dataPagamento, final String detalhesCobranca, final String regrasAplicadas) {
        this.setId(id);
        this.codigoCobranca = codigoCobranca;
        this.conta = conta;
        this.tarifa = tarifa;
        this.transacao = transacao;
        this.valorCobrado = valorCobrado;
        this.valorOriginal = valorOriginal;
        this.percentualDesconto = percentualDesconto;
        this.valorDesconto = valorDesconto;
        this.status = status;
        this.dataCobranca = dataCobranca;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.detalhesCobranca = detalhesCobranca;
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CobrancaTarifa)) return false;
        final CobrancaTarifa other = (CobrancaTarifa) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$codigoCobranca = this.getCodigoCobranca();
        final java.lang.Object other$codigoCobranca = other.getCodigoCobranca();
        if (this$codigoCobranca == null ? other$codigoCobranca != null : !this$codigoCobranca.equals(other$codigoCobranca)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tarifa = this.getTarifa();
        final java.lang.Object other$tarifa = other.getTarifa();
        if (this$tarifa == null ? other$tarifa != null : !this$tarifa.equals(other$tarifa)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$valorCobrado = this.getValorCobrado();
        final java.lang.Object other$valorCobrado = other.getValorCobrado();
        if (this$valorCobrado == null ? other$valorCobrado != null : !this$valorCobrado.equals(other$valorCobrado)) return false;
        final java.lang.Object this$valorOriginal = this.getValorOriginal();
        final java.lang.Object other$valorOriginal = other.getValorOriginal();
        if (this$valorOriginal == null ? other$valorOriginal != null : !this$valorOriginal.equals(other$valorOriginal)) return false;
        final java.lang.Object this$percentualDesconto = this.getPercentualDesconto();
        final java.lang.Object other$percentualDesconto = other.getPercentualDesconto();
        if (this$percentualDesconto == null ? other$percentualDesconto != null : !this$percentualDesconto.equals(other$percentualDesconto)) return false;
        final java.lang.Object this$valorDesconto = this.getValorDesconto();
        final java.lang.Object other$valorDesconto = other.getValorDesconto();
        if (this$valorDesconto == null ? other$valorDesconto != null : !this$valorDesconto.equals(other$valorDesconto)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataCobranca = this.getDataCobranca();
        final java.lang.Object other$dataCobranca = other.getDataCobranca();
        if (this$dataCobranca == null ? other$dataCobranca != null : !this$dataCobranca.equals(other$dataCobranca)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataPagamento = this.getDataPagamento();
        final java.lang.Object other$dataPagamento = other.getDataPagamento();
        if (this$dataPagamento == null ? other$dataPagamento != null : !this$dataPagamento.equals(other$dataPagamento)) return false;
        final java.lang.Object this$detalhesCobranca = this.getDetalhesCobranca();
        final java.lang.Object other$detalhesCobranca = other.getDetalhesCobranca();
        if (this$detalhesCobranca == null ? other$detalhesCobranca != null : !this$detalhesCobranca.equals(other$detalhesCobranca)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof CobrancaTarifa;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $codigoCobranca = this.getCodigoCobranca();
        result = result * PRIME + ($codigoCobranca == null ? 43 : $codigoCobranca.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tarifa = this.getTarifa();
        result = result * PRIME + ($tarifa == null ? 43 : $tarifa.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $valorCobrado = this.getValorCobrado();
        result = result * PRIME + ($valorCobrado == null ? 43 : $valorCobrado.hashCode());
        final java.lang.Object $valorOriginal = this.getValorOriginal();
        result = result * PRIME + ($valorOriginal == null ? 43 : $valorOriginal.hashCode());
        final java.lang.Object $percentualDesconto = this.getPercentualDesconto();
        result = result * PRIME + ($percentualDesconto == null ? 43 : $percentualDesconto.hashCode());
        final java.lang.Object $valorDesconto = this.getValorDesconto();
        result = result * PRIME + ($valorDesconto == null ? 43 : $valorDesconto.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataCobranca = this.getDataCobranca();
        result = result * PRIME + ($dataCobranca == null ? 43 : $dataCobranca.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataPagamento = this.getDataPagamento();
        result = result * PRIME + ($dataPagamento == null ? 43 : $dataPagamento.hashCode());
        final java.lang.Object $detalhesCobranca = this.getDetalhesCobranca();
        result = result * PRIME + ($detalhesCobranca == null ? 43 : $detalhesCobranca.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        return result;
    }
}
