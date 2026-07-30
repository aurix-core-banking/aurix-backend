package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "faturas", schema = "aurix")
public class Fatura extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoFatura;
    @Column(name = "cartao_id", nullable = false)
    private Long cartaoId;
    @Column(nullable = false)
    private Integer mesReferencia;
    @Column(nullable = false)
    private Integer anoReferencia;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFatura status = StatusFatura.ABERTA;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorTotal = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorPago = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorPendente = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorMinimo = BigDecimal.ZERO;
    @Column(nullable = false)
    private LocalDate dataVencimento;
    @Column
    private LocalDate dataPagamento;
    @Column
    private LocalDateTime dataFechamento;
    @Column
    private LocalDateTime dataGeracao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "transacoes", columnDefinition = "JSONB")
    private String transacoes;
    @Column(length = 1000)
    private String observacoes;
    @Column
    private LocalDate dataVencimentoOriginal;
    @Column
    private LocalDate dataPagamentoEfetivo;
    @Column
    private String codigoTransacao;


    public enum StatusFatura {
        ABERTA, FECHADA, PAGA, VENCIDA, CANCELADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoFatura() {
        return this.codigoFatura;
    }

    @java.lang.SuppressWarnings("all")
    public Long getCartaoId() {
        return this.cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMesReferencia() {
        return this.mesReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getAnoReferencia() {
        return this.anoReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public StatusFatura getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorPago() {
        return this.valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorPendente() {
        return this.valorPendente;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMinimo() {
        return this.valorMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataPagamento() {
        return this.dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFechamento() {
        return this.dataFechamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getTransacoes() {
        return this.transacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoFatura(final String codigoFatura) {
        this.codigoFatura = codigoFatura;
    }

    @java.lang.SuppressWarnings("all")
    public void setCartaoId(final Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setMesReferencia(final Integer mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setAnoReferencia(final Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusFatura status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorPago(final BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorPendente(final BigDecimal valorPendente) {
        this.valorPendente = valorPendente;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMinimo(final BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamento(final LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFechamento(final LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacoes(final String transacoes) {
        this.transacoes = transacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataVencimentoOriginal() {
        return this.dataVencimentoOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataPagamentoEfetivo() {
        return this.dataPagamentoEfetivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimentoOriginal(final LocalDate dataVencimentoOriginal) {
        this.dataVencimentoOriginal = dataVencimentoOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamentoEfetivo(final LocalDate dataPagamentoEfetivo) {
        this.dataPagamentoEfetivo = dataPagamentoEfetivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Fatura(id=" + this.getId() + ", codigoFatura=" + this.getCodigoFatura() + ", cartaoId=" + this.getCartaoId() + ", mesReferencia=" + this.getMesReferencia() + ", anoReferencia=" + this.getAnoReferencia() + ", status=" + this.getStatus() + ", valorTotal=" + this.getValorTotal() + ", valorPago=" + this.getValorPago() + ", valorPendente=" + this.getValorPendente() + ", valorMinimo=" + this.getValorMinimo() + ", dataVencimento=" + this.getDataVencimento() + ", dataPagamento=" + this.getDataPagamento() + ", dataFechamento=" + this.getDataFechamento() + ", dataGeracao=" + this.getDataGeracao() + ", transacoes=" + this.getTransacoes() + ", observacoes=" + this.getObservacoes() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Fatura() {
    }

    @java.lang.SuppressWarnings("all")
    public Fatura(final Long id, final String codigoFatura, final Long cartaoId, final Integer mesReferencia, final Integer anoReferencia, final StatusFatura status, final BigDecimal valorTotal, final BigDecimal valorPago, final BigDecimal valorPendente, final BigDecimal valorMinimo, final LocalDate dataVencimento, final LocalDate dataPagamento, final LocalDateTime dataFechamento, final LocalDateTime dataGeracao, final String transacoes, final String observacoes) {
        this.setId(id);
        this.codigoFatura = codigoFatura;
        this.cartaoId = cartaoId;
        this.mesReferencia = mesReferencia;
        this.anoReferencia = anoReferencia;
        this.status = status;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.valorPendente = valorPendente;
        this.valorMinimo = valorMinimo;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.dataFechamento = dataFechamento;
        this.dataGeracao = dataGeracao;
        this.transacoes = transacoes;
        this.observacoes = observacoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Fatura)) return false;
        final Fatura other = (Fatura) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$cartaoId = this.getCartaoId();
        final java.lang.Object other$cartaoId = other.getCartaoId();
        if (this$cartaoId == null ? other$cartaoId != null : !this$cartaoId.equals(other$cartaoId)) return false;
        final java.lang.Object this$mesReferencia = this.getMesReferencia();
        final java.lang.Object other$mesReferencia = other.getMesReferencia();
        if (this$mesReferencia == null ? other$mesReferencia != null : !this$mesReferencia.equals(other$mesReferencia)) return false;
        final java.lang.Object this$anoReferencia = this.getAnoReferencia();
        final java.lang.Object other$anoReferencia = other.getAnoReferencia();
        if (this$anoReferencia == null ? other$anoReferencia != null : !this$anoReferencia.equals(other$anoReferencia)) return false;
        final java.lang.Object this$codigoFatura = this.getCodigoFatura();
        final java.lang.Object other$codigoFatura = other.getCodigoFatura();
        if (this$codigoFatura == null ? other$codigoFatura != null : !this$codigoFatura.equals(other$codigoFatura)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$valorPago = this.getValorPago();
        final java.lang.Object other$valorPago = other.getValorPago();
        if (this$valorPago == null ? other$valorPago != null : !this$valorPago.equals(other$valorPago)) return false;
        final java.lang.Object this$valorPendente = this.getValorPendente();
        final java.lang.Object other$valorPendente = other.getValorPendente();
        if (this$valorPendente == null ? other$valorPendente != null : !this$valorPendente.equals(other$valorPendente)) return false;
        final java.lang.Object this$valorMinimo = this.getValorMinimo();
        final java.lang.Object other$valorMinimo = other.getValorMinimo();
        if (this$valorMinimo == null ? other$valorMinimo != null : !this$valorMinimo.equals(other$valorMinimo)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataPagamento = this.getDataPagamento();
        final java.lang.Object other$dataPagamento = other.getDataPagamento();
        if (this$dataPagamento == null ? other$dataPagamento != null : !this$dataPagamento.equals(other$dataPagamento)) return false;
        final java.lang.Object this$dataFechamento = this.getDataFechamento();
        final java.lang.Object other$dataFechamento = other.getDataFechamento();
        if (this$dataFechamento == null ? other$dataFechamento != null : !this$dataFechamento.equals(other$dataFechamento)) return false;
        final java.lang.Object this$dataGeracao = this.getDataGeracao();
        final java.lang.Object other$dataGeracao = other.getDataGeracao();
        if (this$dataGeracao == null ? other$dataGeracao != null : !this$dataGeracao.equals(other$dataGeracao)) return false;
        final java.lang.Object this$transacoes = this.getTransacoes();
        final java.lang.Object other$transacoes = other.getTransacoes();
        if (this$transacoes == null ? other$transacoes != null : !this$transacoes.equals(other$transacoes)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Fatura;
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
        final java.lang.Object $mesReferencia = this.getMesReferencia();
        result = result * PRIME + ($mesReferencia == null ? 43 : $mesReferencia.hashCode());
        final java.lang.Object $anoReferencia = this.getAnoReferencia();
        result = result * PRIME + ($anoReferencia == null ? 43 : $anoReferencia.hashCode());
        final java.lang.Object $codigoFatura = this.getCodigoFatura();
        result = result * PRIME + ($codigoFatura == null ? 43 : $codigoFatura.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $valorPago = this.getValorPago();
        result = result * PRIME + ($valorPago == null ? 43 : $valorPago.hashCode());
        final java.lang.Object $valorPendente = this.getValorPendente();
        result = result * PRIME + ($valorPendente == null ? 43 : $valorPendente.hashCode());
        final java.lang.Object $valorMinimo = this.getValorMinimo();
        result = result * PRIME + ($valorMinimo == null ? 43 : $valorMinimo.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataPagamento = this.getDataPagamento();
        result = result * PRIME + ($dataPagamento == null ? 43 : $dataPagamento.hashCode());
        final java.lang.Object $dataFechamento = this.getDataFechamento();
        result = result * PRIME + ($dataFechamento == null ? 43 : $dataFechamento.hashCode());
        final java.lang.Object $dataGeracao = this.getDataGeracao();
        result = result * PRIME + ($dataGeracao == null ? 43 : $dataGeracao.hashCode());
        final java.lang.Object $transacoes = this.getTransacoes();
        result = result * PRIME + ($transacoes == null ? 43 : $transacoes.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        return result;
    }
}
