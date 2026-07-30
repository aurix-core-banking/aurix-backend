package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos_debito", schema = "aurix")
public class AgendamentoDebito extends BaseEntity {
    @Column(name = "conta_id", nullable = false)
    private Long contaId;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;
    @Column(name = "data_debito", nullable = false)
    private LocalDate dataDebito;
    @Column(length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAgendamento status = StatusAgendamento.AGENDADO;
    @Column(name = "boleto_id")
    private Long boletoId;
    @Column(nullable = false)
    private Boolean recorrente = false;
    @Column(name = "periodicidade", length = 20)
    private String periodicidade;
    @Column(name = "data_execucao")
    private LocalDateTime dataExecucao;
    @Column(name = "transacao_id")
    private Long transacaoId;


    public enum StatusAgendamento {
        AGENDADO, EXECUTADO, CANCELADO, FALHOU;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataDebito() {
        return this.dataDebito;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusAgendamento getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Long getBoletoId() {
        return this.boletoId;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRecorrente() {
        return this.recorrente;
    }

    @java.lang.SuppressWarnings("all")
    public String getPeriodicidade() {
        return this.periodicidade;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExecucao() {
        return this.dataExecucao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataDebito(final LocalDate dataDebito) {
        this.dataDebito = dataDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAgendamento status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setBoletoId(final Long boletoId) {
        this.boletoId = boletoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setRecorrente(final Boolean recorrente) {
        this.recorrente = recorrente;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodicidade(final String periodicidade) {
        this.periodicidade = periodicidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExecucao(final LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AgendamentoDebito(contaId=" + this.getContaId() + ", valor=" + this.getValor() + ", dataDebito=" + this.getDataDebito() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", boletoId=" + this.getBoletoId() + ", recorrente=" + this.getRecorrente() + ", periodicidade=" + this.getPeriodicidade() + ", dataExecucao=" + this.getDataExecucao() + ", transacaoId=" + this.getTransacaoId() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AgendamentoDebito() {
    }

    @java.lang.SuppressWarnings("all")
    public AgendamentoDebito(final Long contaId, final BigDecimal valor, final LocalDate dataDebito, final String descricao, final StatusAgendamento status, final Long boletoId, final Boolean recorrente, final String periodicidade, final LocalDateTime dataExecucao, final Long transacaoId) {
        this.contaId = contaId;
        this.valor = valor;
        this.dataDebito = dataDebito;
        this.descricao = descricao;
        this.status = status;
        this.boletoId = boletoId;
        this.recorrente = recorrente;
        this.periodicidade = periodicidade;
        this.dataExecucao = dataExecucao;
        this.transacaoId = transacaoId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AgendamentoDebito)) return false;
        final AgendamentoDebito other = (AgendamentoDebito) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$boletoId = this.getBoletoId();
        final java.lang.Object other$boletoId = other.getBoletoId();
        if (this$boletoId == null ? other$boletoId != null : !this$boletoId.equals(other$boletoId)) return false;
        final java.lang.Object this$recorrente = this.getRecorrente();
        final java.lang.Object other$recorrente = other.getRecorrente();
        if (this$recorrente == null ? other$recorrente != null : !this$recorrente.equals(other$recorrente)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$dataDebito = this.getDataDebito();
        final java.lang.Object other$dataDebito = other.getDataDebito();
        if (this$dataDebito == null ? other$dataDebito != null : !this$dataDebito.equals(other$dataDebito)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$periodicidade = this.getPeriodicidade();
        final java.lang.Object other$periodicidade = other.getPeriodicidade();
        if (this$periodicidade == null ? other$periodicidade != null : !this$periodicidade.equals(other$periodicidade)) return false;
        final java.lang.Object this$dataExecucao = this.getDataExecucao();
        final java.lang.Object other$dataExecucao = other.getDataExecucao();
        if (this$dataExecucao == null ? other$dataExecucao != null : !this$dataExecucao.equals(other$dataExecucao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AgendamentoDebito;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $boletoId = this.getBoletoId();
        result = result * PRIME + ($boletoId == null ? 43 : $boletoId.hashCode());
        final java.lang.Object $recorrente = this.getRecorrente();
        result = result * PRIME + ($recorrente == null ? 43 : $recorrente.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $dataDebito = this.getDataDebito();
        result = result * PRIME + ($dataDebito == null ? 43 : $dataDebito.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $periodicidade = this.getPeriodicidade();
        result = result * PRIME + ($periodicidade == null ? 43 : $periodicidade.hashCode());
        final java.lang.Object $dataExecucao = this.getDataExecucao();
        result = result * PRIME + ($dataExecucao == null ? 43 : $dataExecucao.hashCode());
        return result;
    }
}
