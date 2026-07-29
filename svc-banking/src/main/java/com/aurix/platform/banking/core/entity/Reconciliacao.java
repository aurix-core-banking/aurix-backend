package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "reconciliacoes", schema = "aurix")
public class Reconciliacao extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoReconciliacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoReconciliacao tipoReconciliacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReconciliacao status = StatusReconciliacao.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataReferencia;
    @Column
    private LocalDateTime dataInicioProcessamento;
    @Column
    private LocalDateTime dataFimProcessamento;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorTotalProcessado;
    @Column
    private Integer quantidadeTransacoes;
    @Column
    private Integer quantidadeSucesso;
    @Column
    private Integer quantidadeFalha;
    @Column
    private Integer quantidadePendente;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorDivergencia;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_reconciliacao", columnDefinition = "JSONB")
    private String detalhesReconciliacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "erros_encontrados", columnDefinition = "JSONB")
    private String errosEncontrados;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private String codigoBacen;
    @Column
    private String arquivoOrigem;


    public enum TipoReconciliacao {
        PIX_DIARIA, TED_DIARIA, DOC_DIARIA, BOLETO_DIARIO, CARTAO_DIARIO, INVESTIMENTO_DIARIO, BACEN_DIARIA, BACEN_MENSAL, INTERNO_DIARIO, INTERNO_MENSAL, OUTROS;
    }


    public enum StatusReconciliacao {
        PENDENTE, PROCESSANDO, CONCLUIDA, FALHADA, DIVERGENCIA, CANCELADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoReconciliacao() {
        return this.codigoReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoReconciliacao getTipoReconciliacao() {
        return this.tipoReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusReconciliacao getStatus() {
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
    public BigDecimal getValorTotalProcessado() {
        return this.valorTotalProcessado;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeTransacoes() {
        return this.quantidadeTransacoes;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeSucesso() {
        return this.quantidadeSucesso;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeFalha() {
        return this.quantidadeFalha;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadePendente() {
        return this.quantidadePendente;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDivergencia() {
        return this.valorDivergencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesReconciliacao() {
        return this.detalhesReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getErrosEncontrados() {
        return this.errosEncontrados;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getProcessamentoAutomatico() {
        return this.processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBacen() {
        return this.codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public String getArquivoOrigem() {
        return this.arquivoOrigem;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoReconciliacao(final String codigoReconciliacao) {
        this.codigoReconciliacao = codigoReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoReconciliacao(final TipoReconciliacao tipoReconciliacao) {
        this.tipoReconciliacao = tipoReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusReconciliacao status) {
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
    public void setValorTotalProcessado(final BigDecimal valorTotalProcessado) {
        this.valorTotalProcessado = valorTotalProcessado;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeTransacoes(final Integer quantidadeTransacoes) {
        this.quantidadeTransacoes = quantidadeTransacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeSucesso(final Integer quantidadeSucesso) {
        this.quantidadeSucesso = quantidadeSucesso;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeFalha(final Integer quantidadeFalha) {
        this.quantidadeFalha = quantidadeFalha;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadePendente(final Integer quantidadePendente) {
        this.quantidadePendente = quantidadePendente;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDivergencia(final BigDecimal valorDivergencia) {
        this.valorDivergencia = valorDivergencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesReconciliacao(final String detalhesReconciliacao) {
        this.detalhesReconciliacao = detalhesReconciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setErrosEncontrados(final String errosEncontrados) {
        this.errosEncontrados = errosEncontrados;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessamentoAutomatico(final Boolean processamentoAutomatico) {
        this.processamentoAutomatico = processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBacen(final String codigoBacen) {
        this.codigoBacen = codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setArquivoOrigem(final String arquivoOrigem) {
        this.arquivoOrigem = arquivoOrigem;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Reconciliacao(id=" + this.getId() + ", codigoReconciliacao=" + this.getCodigoReconciliacao() + ", tipoReconciliacao=" + this.getTipoReconciliacao() + ", status=" + this.getStatus() + ", dataReferencia=" + this.getDataReferencia() + ", dataInicioProcessamento=" + this.getDataInicioProcessamento() + ", dataFimProcessamento=" + this.getDataFimProcessamento() + ", valorTotalProcessado=" + this.getValorTotalProcessado() + ", quantidadeTransacoes=" + this.getQuantidadeTransacoes() + ", quantidadeSucesso=" + this.getQuantidadeSucesso() + ", quantidadeFalha=" + this.getQuantidadeFalha() + ", quantidadePendente=" + this.getQuantidadePendente() + ", valorDivergencia=" + this.getValorDivergencia() + ", observacoes=" + this.getObservacoes() + ", detalhesReconciliacao=" + this.getDetalhesReconciliacao() + ", errosEncontrados=" + this.getErrosEncontrados() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", codigoBacen=" + this.getCodigoBacen() + ", arquivoOrigem=" + this.getArquivoOrigem() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Reconciliacao() {
    }

    @java.lang.SuppressWarnings("all")
    public Reconciliacao(final Long id, final String codigoReconciliacao, final TipoReconciliacao tipoReconciliacao, final StatusReconciliacao status, final LocalDateTime dataReferencia, final LocalDateTime dataInicioProcessamento, final LocalDateTime dataFimProcessamento, final BigDecimal valorTotalProcessado, final Integer quantidadeTransacoes, final Integer quantidadeSucesso, final Integer quantidadeFalha, final Integer quantidadePendente, final BigDecimal valorDivergencia, final String observacoes, final String detalhesReconciliacao, final String errosEncontrados, final Boolean processamentoAutomatico, final String codigoBacen, final String arquivoOrigem) {
        this.setId(id);
        this.codigoReconciliacao = codigoReconciliacao;
        this.tipoReconciliacao = tipoReconciliacao;
        this.status = status;
        this.dataReferencia = dataReferencia;
        this.dataInicioProcessamento = dataInicioProcessamento;
        this.dataFimProcessamento = dataFimProcessamento;
        this.valorTotalProcessado = valorTotalProcessado;
        this.quantidadeTransacoes = quantidadeTransacoes;
        this.quantidadeSucesso = quantidadeSucesso;
        this.quantidadeFalha = quantidadeFalha;
        this.quantidadePendente = quantidadePendente;
        this.valorDivergencia = valorDivergencia;
        this.observacoes = observacoes;
        this.detalhesReconciliacao = detalhesReconciliacao;
        this.errosEncontrados = errosEncontrados;
        this.processamentoAutomatico = processamentoAutomatico;
        this.codigoBacen = codigoBacen;
        this.arquivoOrigem = arquivoOrigem;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Reconciliacao)) return false;
        final Reconciliacao other = (Reconciliacao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeTransacoes = this.getQuantidadeTransacoes();
        final java.lang.Object other$quantidadeTransacoes = other.getQuantidadeTransacoes();
        if (this$quantidadeTransacoes == null ? other$quantidadeTransacoes != null : !this$quantidadeTransacoes.equals(other$quantidadeTransacoes)) return false;
        final java.lang.Object this$quantidadeSucesso = this.getQuantidadeSucesso();
        final java.lang.Object other$quantidadeSucesso = other.getQuantidadeSucesso();
        if (this$quantidadeSucesso == null ? other$quantidadeSucesso != null : !this$quantidadeSucesso.equals(other$quantidadeSucesso)) return false;
        final java.lang.Object this$quantidadeFalha = this.getQuantidadeFalha();
        final java.lang.Object other$quantidadeFalha = other.getQuantidadeFalha();
        if (this$quantidadeFalha == null ? other$quantidadeFalha != null : !this$quantidadeFalha.equals(other$quantidadeFalha)) return false;
        final java.lang.Object this$quantidadePendente = this.getQuantidadePendente();
        final java.lang.Object other$quantidadePendente = other.getQuantidadePendente();
        if (this$quantidadePendente == null ? other$quantidadePendente != null : !this$quantidadePendente.equals(other$quantidadePendente)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$codigoReconciliacao = this.getCodigoReconciliacao();
        final java.lang.Object other$codigoReconciliacao = other.getCodigoReconciliacao();
        if (this$codigoReconciliacao == null ? other$codigoReconciliacao != null : !this$codigoReconciliacao.equals(other$codigoReconciliacao)) return false;
        final java.lang.Object this$tipoReconciliacao = this.getTipoReconciliacao();
        final java.lang.Object other$tipoReconciliacao = other.getTipoReconciliacao();
        if (this$tipoReconciliacao == null ? other$tipoReconciliacao != null : !this$tipoReconciliacao.equals(other$tipoReconciliacao)) return false;
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
        final java.lang.Object this$valorTotalProcessado = this.getValorTotalProcessado();
        final java.lang.Object other$valorTotalProcessado = other.getValorTotalProcessado();
        if (this$valorTotalProcessado == null ? other$valorTotalProcessado != null : !this$valorTotalProcessado.equals(other$valorTotalProcessado)) return false;
        final java.lang.Object this$valorDivergencia = this.getValorDivergencia();
        final java.lang.Object other$valorDivergencia = other.getValorDivergencia();
        if (this$valorDivergencia == null ? other$valorDivergencia != null : !this$valorDivergencia.equals(other$valorDivergencia)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesReconciliacao = this.getDetalhesReconciliacao();
        final java.lang.Object other$detalhesReconciliacao = other.getDetalhesReconciliacao();
        if (this$detalhesReconciliacao == null ? other$detalhesReconciliacao != null : !this$detalhesReconciliacao.equals(other$detalhesReconciliacao)) return false;
        final java.lang.Object this$errosEncontrados = this.getErrosEncontrados();
        final java.lang.Object other$errosEncontrados = other.getErrosEncontrados();
        if (this$errosEncontrados == null ? other$errosEncontrados != null : !this$errosEncontrados.equals(other$errosEncontrados)) return false;
        final java.lang.Object this$codigoBacen = this.getCodigoBacen();
        final java.lang.Object other$codigoBacen = other.getCodigoBacen();
        if (this$codigoBacen == null ? other$codigoBacen != null : !this$codigoBacen.equals(other$codigoBacen)) return false;
        final java.lang.Object this$arquivoOrigem = this.getArquivoOrigem();
        final java.lang.Object other$arquivoOrigem = other.getArquivoOrigem();
        if (this$arquivoOrigem == null ? other$arquivoOrigem != null : !this$arquivoOrigem.equals(other$arquivoOrigem)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Reconciliacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeTransacoes = this.getQuantidadeTransacoes();
        result = result * PRIME + ($quantidadeTransacoes == null ? 43 : $quantidadeTransacoes.hashCode());
        final java.lang.Object $quantidadeSucesso = this.getQuantidadeSucesso();
        result = result * PRIME + ($quantidadeSucesso == null ? 43 : $quantidadeSucesso.hashCode());
        final java.lang.Object $quantidadeFalha = this.getQuantidadeFalha();
        result = result * PRIME + ($quantidadeFalha == null ? 43 : $quantidadeFalha.hashCode());
        final java.lang.Object $quantidadePendente = this.getQuantidadePendente();
        result = result * PRIME + ($quantidadePendente == null ? 43 : $quantidadePendente.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $codigoReconciliacao = this.getCodigoReconciliacao();
        result = result * PRIME + ($codigoReconciliacao == null ? 43 : $codigoReconciliacao.hashCode());
        final java.lang.Object $tipoReconciliacao = this.getTipoReconciliacao();
        result = result * PRIME + ($tipoReconciliacao == null ? 43 : $tipoReconciliacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $dataInicioProcessamento = this.getDataInicioProcessamento();
        result = result * PRIME + ($dataInicioProcessamento == null ? 43 : $dataInicioProcessamento.hashCode());
        final java.lang.Object $dataFimProcessamento = this.getDataFimProcessamento();
        result = result * PRIME + ($dataFimProcessamento == null ? 43 : $dataFimProcessamento.hashCode());
        final java.lang.Object $valorTotalProcessado = this.getValorTotalProcessado();
        result = result * PRIME + ($valorTotalProcessado == null ? 43 : $valorTotalProcessado.hashCode());
        final java.lang.Object $valorDivergencia = this.getValorDivergencia();
        result = result * PRIME + ($valorDivergencia == null ? 43 : $valorDivergencia.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesReconciliacao = this.getDetalhesReconciliacao();
        result = result * PRIME + ($detalhesReconciliacao == null ? 43 : $detalhesReconciliacao.hashCode());
        final java.lang.Object $errosEncontrados = this.getErrosEncontrados();
        result = result * PRIME + ($errosEncontrados == null ? 43 : $errosEncontrados.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $arquivoOrigem = this.getArquivoOrigem();
        result = result * PRIME + ($arquivoOrigem == null ? 43 : $arquivoOrigem.hashCode());
        return result;
    }
}
