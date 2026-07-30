package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "conciliacoes_bancarias", schema = "aurix")
public class ConciliacaoBancaria extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoConciliacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConciliacao tipoConciliacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConciliacao status = StatusConciliacao.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataReferencia;
    @Column
    private LocalDateTime dataInicioProcessamento;
    @Column
    private LocalDateTime dataFimProcessamento;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoSistema;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoExtrato;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorDivergencia;
    @Column
    private Integer quantidadeMovimentosSistema;
    @Column
    private Integer quantidadeMovimentosExtrato;
    @Column
    private Integer quantidadeConciliados;
    @Column
    private Integer quantidadeDivergencias;
    @Column
    private Integer quantidadePendentes;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_conciliacao", columnDefinition = "JSONB")
    private String detalhesConciliacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "divergencias_encontradas", columnDefinition = "JSONB")
    private String divergenciasEncontradas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "movimentos_nao_conciliados", columnDefinition = "JSONB")
    private String movimentosNaoConciliados;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private String arquivoExtrato;
    @Column
    private String codigoBacen;
    @Column
    private String codigoSPI;
    @Column
    private String codigoSTR;
    @Column
    private String usuarioProcessamento;


    public enum TipoConciliacao {
        DIARIA, MENSAL, BACEN, SPI, STR, INTERNO, EXTERNO;
    }


    public enum StatusConciliacao {
        PENDENTE, PROCESSANDO, CONCLUIDA, DIVERGENCIA, FALHADA, CANCELADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoConciliacao() {
        return this.codigoConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public TipoConciliacao getTipoConciliacao() {
        return this.tipoConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConciliacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicioProcessamento() {
        return this.dataInicioProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFimProcessamento() {
        return this.dataFimProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoSistema() {
        return this.saldoSistema;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoExtrato() {
        return this.saldoExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDivergencia() {
        return this.valorDivergencia;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeMovimentosSistema() {
        return this.quantidadeMovimentosSistema;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeMovimentosExtrato() {
        return this.quantidadeMovimentosExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeConciliados() {
        return this.quantidadeConciliados;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeDivergencias() {
        return this.quantidadeDivergencias;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadePendentes() {
        return this.quantidadePendentes;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesConciliacao() {
        return this.detalhesConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDivergenciasEncontradas() {
        return this.divergenciasEncontradas;
    }

    @java.lang.SuppressWarnings("all")
    public String getMovimentosNaoConciliados() {
        return this.movimentosNaoConciliados;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getProcessamentoAutomatico() {
        return this.processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public String getArquivoExtrato() {
        return this.arquivoExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBacen() {
        return this.codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSPI() {
        return this.codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSTR() {
        return this.codigoSTR;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioProcessamento() {
        return this.usuarioProcessamento;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoConciliacao(final String codigoConciliacao) {
        this.codigoConciliacao = codigoConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoConciliacao(final TipoConciliacao tipoConciliacao) {
        this.tipoConciliacao = tipoConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConciliacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataReferencia(final LocalDateTime dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioProcessamento(final LocalDateTime dataInicioProcessamento) {
        this.dataInicioProcessamento = dataInicioProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFimProcessamento(final LocalDateTime dataFimProcessamento) {
        this.dataFimProcessamento = dataFimProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoSistema(final BigDecimal saldoSistema) {
        this.saldoSistema = saldoSistema;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoExtrato(final BigDecimal saldoExtrato) {
        this.saldoExtrato = saldoExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDivergencia(final BigDecimal valorDivergencia) {
        this.valorDivergencia = valorDivergencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeMovimentosSistema(final Integer quantidadeMovimentosSistema) {
        this.quantidadeMovimentosSistema = quantidadeMovimentosSistema;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeMovimentosExtrato(final Integer quantidadeMovimentosExtrato) {
        this.quantidadeMovimentosExtrato = quantidadeMovimentosExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeConciliados(final Integer quantidadeConciliados) {
        this.quantidadeConciliados = quantidadeConciliados;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeDivergencias(final Integer quantidadeDivergencias) {
        this.quantidadeDivergencias = quantidadeDivergencias;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadePendentes(final Integer quantidadePendentes) {
        this.quantidadePendentes = quantidadePendentes;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesConciliacao(final String detalhesConciliacao) {
        this.detalhesConciliacao = detalhesConciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDivergenciasEncontradas(final String divergenciasEncontradas) {
        this.divergenciasEncontradas = divergenciasEncontradas;
    }

    @java.lang.SuppressWarnings("all")
    public void setMovimentosNaoConciliados(final String movimentosNaoConciliados) {
        this.movimentosNaoConciliados = movimentosNaoConciliados;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessamentoAutomatico(final Boolean processamentoAutomatico) {
        this.processamentoAutomatico = processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public void setArquivoExtrato(final String arquivoExtrato) {
        this.arquivoExtrato = arquivoExtrato;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBacen(final String codigoBacen) {
        this.codigoBacen = codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSPI(final String codigoSPI) {
        this.codigoSPI = codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSTR(final String codigoSTR) {
        this.codigoSTR = codigoSTR;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioProcessamento(final String usuarioProcessamento) {
        this.usuarioProcessamento = usuarioProcessamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ConciliacaoBancaria(id=" + this.getId() + ", codigoConciliacao=" + this.getCodigoConciliacao() + ", conta=" + this.getConta() + ", tipoConciliacao=" + this.getTipoConciliacao() + ", status=" + this.getStatus() + ", dataReferencia=" + this.getDataReferencia() + ", dataInicioProcessamento=" + this.getDataInicioProcessamento() + ", dataFimProcessamento=" + this.getDataFimProcessamento() + ", saldoSistema=" + this.getSaldoSistema() + ", saldoExtrato=" + this.getSaldoExtrato() + ", valorDivergencia=" + this.getValorDivergencia() + ", quantidadeMovimentosSistema=" + this.getQuantidadeMovimentosSistema() + ", quantidadeMovimentosExtrato=" + this.getQuantidadeMovimentosExtrato() + ", quantidadeConciliados=" + this.getQuantidadeConciliados() + ", quantidadeDivergencias=" + this.getQuantidadeDivergencias() + ", quantidadePendentes=" + this.getQuantidadePendentes() + ", observacoes=" + this.getObservacoes() + ", detalhesConciliacao=" + this.getDetalhesConciliacao() + ", divergenciasEncontradas=" + this.getDivergenciasEncontradas() + ", movimentosNaoConciliados=" + this.getMovimentosNaoConciliados() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", arquivoExtrato=" + this.getArquivoExtrato() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ", usuarioProcessamento=" + this.getUsuarioProcessamento() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ConciliacaoBancaria() {
    }

    @java.lang.SuppressWarnings("all")
    public ConciliacaoBancaria(final Long id, final String codigoConciliacao, final Conta conta, final TipoConciliacao tipoConciliacao, final StatusConciliacao status, final LocalDateTime dataReferencia, final LocalDateTime dataInicioProcessamento, final LocalDateTime dataFimProcessamento, final BigDecimal saldoSistema, final BigDecimal saldoExtrato, final BigDecimal valorDivergencia, final Integer quantidadeMovimentosSistema, final Integer quantidadeMovimentosExtrato, final Integer quantidadeConciliados, final Integer quantidadeDivergencias, final Integer quantidadePendentes, final String observacoes, final String detalhesConciliacao, final String divergenciasEncontradas, final String movimentosNaoConciliados, final Boolean processamentoAutomatico, final String arquivoExtrato, final String codigoBacen, final String codigoSPI, final String codigoSTR, final String usuarioProcessamento) {
        this.setId(id);
        this.codigoConciliacao = codigoConciliacao;
        this.conta = conta;
        this.tipoConciliacao = tipoConciliacao;
        this.status = status;
        this.dataReferencia = dataReferencia;
        this.dataInicioProcessamento = dataInicioProcessamento;
        this.dataFimProcessamento = dataFimProcessamento;
        this.saldoSistema = saldoSistema;
        this.saldoExtrato = saldoExtrato;
        this.valorDivergencia = valorDivergencia;
        this.quantidadeMovimentosSistema = quantidadeMovimentosSistema;
        this.quantidadeMovimentosExtrato = quantidadeMovimentosExtrato;
        this.quantidadeConciliados = quantidadeConciliados;
        this.quantidadeDivergencias = quantidadeDivergencias;
        this.quantidadePendentes = quantidadePendentes;
        this.observacoes = observacoes;
        this.detalhesConciliacao = detalhesConciliacao;
        this.divergenciasEncontradas = divergenciasEncontradas;
        this.movimentosNaoConciliados = movimentosNaoConciliados;
        this.processamentoAutomatico = processamentoAutomatico;
        this.arquivoExtrato = arquivoExtrato;
        this.codigoBacen = codigoBacen;
        this.codigoSPI = codigoSPI;
        this.codigoSTR = codigoSTR;
        this.usuarioProcessamento = usuarioProcessamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ConciliacaoBancaria)) return false;
        final ConciliacaoBancaria other = (ConciliacaoBancaria) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeMovimentosSistema = this.getQuantidadeMovimentosSistema();
        final java.lang.Object other$quantidadeMovimentosSistema = other.getQuantidadeMovimentosSistema();
        if (this$quantidadeMovimentosSistema == null ? other$quantidadeMovimentosSistema != null : !this$quantidadeMovimentosSistema.equals(other$quantidadeMovimentosSistema)) return false;
        final java.lang.Object this$quantidadeMovimentosExtrato = this.getQuantidadeMovimentosExtrato();
        final java.lang.Object other$quantidadeMovimentosExtrato = other.getQuantidadeMovimentosExtrato();
        if (this$quantidadeMovimentosExtrato == null ? other$quantidadeMovimentosExtrato != null : !this$quantidadeMovimentosExtrato.equals(other$quantidadeMovimentosExtrato)) return false;
        final java.lang.Object this$quantidadeConciliados = this.getQuantidadeConciliados();
        final java.lang.Object other$quantidadeConciliados = other.getQuantidadeConciliados();
        if (this$quantidadeConciliados == null ? other$quantidadeConciliados != null : !this$quantidadeConciliados.equals(other$quantidadeConciliados)) return false;
        final java.lang.Object this$quantidadeDivergencias = this.getQuantidadeDivergencias();
        final java.lang.Object other$quantidadeDivergencias = other.getQuantidadeDivergencias();
        if (this$quantidadeDivergencias == null ? other$quantidadeDivergencias != null : !this$quantidadeDivergencias.equals(other$quantidadeDivergencias)) return false;
        final java.lang.Object this$quantidadePendentes = this.getQuantidadePendentes();
        final java.lang.Object other$quantidadePendentes = other.getQuantidadePendentes();
        if (this$quantidadePendentes == null ? other$quantidadePendentes != null : !this$quantidadePendentes.equals(other$quantidadePendentes)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$codigoConciliacao = this.getCodigoConciliacao();
        final java.lang.Object other$codigoConciliacao = other.getCodigoConciliacao();
        if (this$codigoConciliacao == null ? other$codigoConciliacao != null : !this$codigoConciliacao.equals(other$codigoConciliacao)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tipoConciliacao = this.getTipoConciliacao();
        final java.lang.Object other$tipoConciliacao = other.getTipoConciliacao();
        if (this$tipoConciliacao == null ? other$tipoConciliacao != null : !this$tipoConciliacao.equals(other$tipoConciliacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$dataInicioProcessamento = this.getDataInicioProcessamento();
        final java.lang.Object other$dataInicioProcessamento = other.getDataInicioProcessamento();
        if (this$dataInicioProcessamento == null ? other$dataInicioProcessamento != null : !this$dataInicioProcessamento.equals(other$dataInicioProcessamento)) return false;
        final java.lang.Object this$dataFimProcessamento = this.getDataFimProcessamento();
        final java.lang.Object other$dataFimProcessamento = other.getDataFimProcessamento();
        if (this$dataFimProcessamento == null ? other$dataFimProcessamento != null : !this$dataFimProcessamento.equals(other$dataFimProcessamento)) return false;
        final java.lang.Object this$saldoSistema = this.getSaldoSistema();
        final java.lang.Object other$saldoSistema = other.getSaldoSistema();
        if (this$saldoSistema == null ? other$saldoSistema != null : !this$saldoSistema.equals(other$saldoSistema)) return false;
        final java.lang.Object this$saldoExtrato = this.getSaldoExtrato();
        final java.lang.Object other$saldoExtrato = other.getSaldoExtrato();
        if (this$saldoExtrato == null ? other$saldoExtrato != null : !this$saldoExtrato.equals(other$saldoExtrato)) return false;
        final java.lang.Object this$valorDivergencia = this.getValorDivergencia();
        final java.lang.Object other$valorDivergencia = other.getValorDivergencia();
        if (this$valorDivergencia == null ? other$valorDivergencia != null : !this$valorDivergencia.equals(other$valorDivergencia)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesConciliacao = this.getDetalhesConciliacao();
        final java.lang.Object other$detalhesConciliacao = other.getDetalhesConciliacao();
        if (this$detalhesConciliacao == null ? other$detalhesConciliacao != null : !this$detalhesConciliacao.equals(other$detalhesConciliacao)) return false;
        final java.lang.Object this$divergenciasEncontradas = this.getDivergenciasEncontradas();
        final java.lang.Object other$divergenciasEncontradas = other.getDivergenciasEncontradas();
        if (this$divergenciasEncontradas == null ? other$divergenciasEncontradas != null : !this$divergenciasEncontradas.equals(other$divergenciasEncontradas)) return false;
        final java.lang.Object this$movimentosNaoConciliados = this.getMovimentosNaoConciliados();
        final java.lang.Object other$movimentosNaoConciliados = other.getMovimentosNaoConciliados();
        if (this$movimentosNaoConciliados == null ? other$movimentosNaoConciliados != null : !this$movimentosNaoConciliados.equals(other$movimentosNaoConciliados)) return false;
        final java.lang.Object this$arquivoExtrato = this.getArquivoExtrato();
        final java.lang.Object other$arquivoExtrato = other.getArquivoExtrato();
        if (this$arquivoExtrato == null ? other$arquivoExtrato != null : !this$arquivoExtrato.equals(other$arquivoExtrato)) return false;
        final java.lang.Object this$codigoBacen = this.getCodigoBacen();
        final java.lang.Object other$codigoBacen = other.getCodigoBacen();
        if (this$codigoBacen == null ? other$codigoBacen != null : !this$codigoBacen.equals(other$codigoBacen)) return false;
        final java.lang.Object this$codigoSPI = this.getCodigoSPI();
        final java.lang.Object other$codigoSPI = other.getCodigoSPI();
        if (this$codigoSPI == null ? other$codigoSPI != null : !this$codigoSPI.equals(other$codigoSPI)) return false;
        final java.lang.Object this$codigoSTR = this.getCodigoSTR();
        final java.lang.Object other$codigoSTR = other.getCodigoSTR();
        if (this$codigoSTR == null ? other$codigoSTR != null : !this$codigoSTR.equals(other$codigoSTR)) return false;
        final java.lang.Object this$usuarioProcessamento = this.getUsuarioProcessamento();
        final java.lang.Object other$usuarioProcessamento = other.getUsuarioProcessamento();
        if (this$usuarioProcessamento == null ? other$usuarioProcessamento != null : !this$usuarioProcessamento.equals(other$usuarioProcessamento)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ConciliacaoBancaria;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeMovimentosSistema = this.getQuantidadeMovimentosSistema();
        result = result * PRIME + ($quantidadeMovimentosSistema == null ? 43 : $quantidadeMovimentosSistema.hashCode());
        final java.lang.Object $quantidadeMovimentosExtrato = this.getQuantidadeMovimentosExtrato();
        result = result * PRIME + ($quantidadeMovimentosExtrato == null ? 43 : $quantidadeMovimentosExtrato.hashCode());
        final java.lang.Object $quantidadeConciliados = this.getQuantidadeConciliados();
        result = result * PRIME + ($quantidadeConciliados == null ? 43 : $quantidadeConciliados.hashCode());
        final java.lang.Object $quantidadeDivergencias = this.getQuantidadeDivergencias();
        result = result * PRIME + ($quantidadeDivergencias == null ? 43 : $quantidadeDivergencias.hashCode());
        final java.lang.Object $quantidadePendentes = this.getQuantidadePendentes();
        result = result * PRIME + ($quantidadePendentes == null ? 43 : $quantidadePendentes.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $codigoConciliacao = this.getCodigoConciliacao();
        result = result * PRIME + ($codigoConciliacao == null ? 43 : $codigoConciliacao.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tipoConciliacao = this.getTipoConciliacao();
        result = result * PRIME + ($tipoConciliacao == null ? 43 : $tipoConciliacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $dataInicioProcessamento = this.getDataInicioProcessamento();
        result = result * PRIME + ($dataInicioProcessamento == null ? 43 : $dataInicioProcessamento.hashCode());
        final java.lang.Object $dataFimProcessamento = this.getDataFimProcessamento();
        result = result * PRIME + ($dataFimProcessamento == null ? 43 : $dataFimProcessamento.hashCode());
        final java.lang.Object $saldoSistema = this.getSaldoSistema();
        result = result * PRIME + ($saldoSistema == null ? 43 : $saldoSistema.hashCode());
        final java.lang.Object $saldoExtrato = this.getSaldoExtrato();
        result = result * PRIME + ($saldoExtrato == null ? 43 : $saldoExtrato.hashCode());
        final java.lang.Object $valorDivergencia = this.getValorDivergencia();
        result = result * PRIME + ($valorDivergencia == null ? 43 : $valorDivergencia.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesConciliacao = this.getDetalhesConciliacao();
        result = result * PRIME + ($detalhesConciliacao == null ? 43 : $detalhesConciliacao.hashCode());
        final java.lang.Object $divergenciasEncontradas = this.getDivergenciasEncontradas();
        result = result * PRIME + ($divergenciasEncontradas == null ? 43 : $divergenciasEncontradas.hashCode());
        final java.lang.Object $movimentosNaoConciliados = this.getMovimentosNaoConciliados();
        result = result * PRIME + ($movimentosNaoConciliados == null ? 43 : $movimentosNaoConciliados.hashCode());
        final java.lang.Object $arquivoExtrato = this.getArquivoExtrato();
        result = result * PRIME + ($arquivoExtrato == null ? 43 : $arquivoExtrato.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $codigoSPI = this.getCodigoSPI();
        result = result * PRIME + ($codigoSPI == null ? 43 : $codigoSPI.hashCode());
        final java.lang.Object $codigoSTR = this.getCodigoSTR();
        result = result * PRIME + ($codigoSTR == null ? 43 : $codigoSTR.hashCode());
        final java.lang.Object $usuarioProcessamento = this.getUsuarioProcessamento();
        result = result * PRIME + ($usuarioProcessamento == null ? 43 : $usuarioProcessamento.hashCode());
        return result;
    }
}
