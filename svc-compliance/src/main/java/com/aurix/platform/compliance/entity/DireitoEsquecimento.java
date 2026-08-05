package com.aurix.platform.compliance.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "direitos_esquecimento", schema = "aurix")
public class DireitoEsquecimento extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoSolicitacao;
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDireito tipoDireito;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProcessamento status = StatusProcessamento.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;
    @Column
    private LocalDateTime dataInicioProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column(columnDefinition = "TEXT")
    private String justificativaSolicitacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_identificados", columnDefinition = "JSONB")
    private String dadosIdentificados;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sistemas_afetados", columnDefinition = "JSONB")
    private String sistemasAfetados;
    @Column
    private Integer totalRegistrosIdentificados = 0;
    @Column
    private Integer totalRegistrosProcessados = 0;
    @Column
    private Integer totalRegistrosAnonimizados = 0;
    @Column
    private Integer totalRegistrosExcluidos = 0;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "erros_processamento", columnDefinition = "JSONB")
    private String errosProcessamento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;


    public enum TipoDireito {
        EXCLUSAO_COMPLETA, ANONIMIZACAO, BLOQUEIO_PROCESSAMENTO, REVISAO_DADOS;
    }


    public enum StatusProcessamento {
        PENDENTE, EM_ANALISE, PROCESSANDO, CONCLUIDO, REJEITADO, CANCELADO, ERRO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoSolicitacao() {
        return this.codigoSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getCpfCnpj() {
        return this.cpfCnpj;
    }

    @java.lang.SuppressWarnings("all")
    public TipoDireito getTipoDireito() {
        return this.tipoDireito;
    }

    @java.lang.SuppressWarnings("all")
    public StatusProcessamento getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicioProcessamento() {
        return this.dataInicioProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativaSolicitacao() {
        return this.justificativaSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosIdentificados() {
        return this.dadosIdentificados;
    }

    @java.lang.SuppressWarnings("all")
    public String getSistemasAfetados() {
        return this.sistemasAfetados;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTotalRegistrosIdentificados() {
        return this.totalRegistrosIdentificados;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTotalRegistrosProcessados() {
        return this.totalRegistrosProcessados;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTotalRegistrosAnonimizados() {
        return this.totalRegistrosAnonimizados;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTotalRegistrosExcluidos() {
        return this.totalRegistrosExcluidos;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getErrosProcessamento() {
        return this.errosProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoSolicitacao(final String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoDireito(final TipoDireito tipoDireito) {
        this.tipoDireito = tipoDireito;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusProcessamento status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioProcessamento(final LocalDateTime dataInicioProcessamento) {
        this.dataInicioProcessamento = dataInicioProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConclusao(final LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativaSolicitacao(final String justificativaSolicitacao) {
        this.justificativaSolicitacao = justificativaSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosIdentificados(final String dadosIdentificados) {
        this.dadosIdentificados = dadosIdentificados;
    }

    @java.lang.SuppressWarnings("all")
    public void setSistemasAfetados(final String sistemasAfetados) {
        this.sistemasAfetados = sistemasAfetados;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalRegistrosIdentificados(final Integer totalRegistrosIdentificados) {
        this.totalRegistrosIdentificados = totalRegistrosIdentificados;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalRegistrosProcessados(final Integer totalRegistrosProcessados) {
        this.totalRegistrosProcessados = totalRegistrosProcessados;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalRegistrosAnonimizados(final Integer totalRegistrosAnonimizados) {
        this.totalRegistrosAnonimizados = totalRegistrosAnonimizados;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalRegistrosExcluidos(final Integer totalRegistrosExcluidos) {
        this.totalRegistrosExcluidos = totalRegistrosExcluidos;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setErrosProcessamento(final String errosProcessamento) {
        this.errosProcessamento = errosProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "DireitoEsquecimento(id=" + this.getId() + ", codigoSolicitacao=" + this.getCodigoSolicitacao() + ", clienteId=" + this.getClienteId() + ", cpfCnpj=" + this.getCpfCnpj() + ", tipoDireito=" + this.getTipoDireito() + ", status=" + this.getStatus() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataInicioProcessamento=" + this.getDataInicioProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", justificativaSolicitacao=" + this.getJustificativaSolicitacao() + ", dadosIdentificados=" + this.getDadosIdentificados() + ", sistemasAfetados=" + this.getSistemasAfetados() + ", totalRegistrosIdentificados=" + this.getTotalRegistrosIdentificados() + ", totalRegistrosProcessados=" + this.getTotalRegistrosProcessados() + ", totalRegistrosAnonimizados=" + this.getTotalRegistrosAnonimizados() + ", totalRegistrosExcluidos=" + this.getTotalRegistrosExcluidos() + ", observacoes=" + this.getObservacoes() + ", errosProcessamento=" + this.getErrosProcessamento() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public DireitoEsquecimento() {
    }

    @java.lang.SuppressWarnings("all")
    public DireitoEsquecimento(final Long id, final String codigoSolicitacao, final Long clienteId, final String cpfCnpj, final TipoDireito tipoDireito, final StatusProcessamento status, final LocalDateTime dataSolicitacao, final LocalDateTime dataInicioProcessamento, final LocalDateTime dataConclusao, final String justificativaSolicitacao, final String dadosIdentificados, final String sistemasAfetados, final Integer totalRegistrosIdentificados, final Integer totalRegistrosProcessados, final Integer totalRegistrosAnonimizados, final Integer totalRegistrosExcluidos, final String observacoes, final String errosProcessamento, final String metadata) {
        this.setId(id);
        this.codigoSolicitacao = codigoSolicitacao;
        this.clienteId = clienteId;
        this.cpfCnpj = cpfCnpj;
        this.tipoDireito = tipoDireito;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataInicioProcessamento = dataInicioProcessamento;
        this.dataConclusao = dataConclusao;
        this.justificativaSolicitacao = justificativaSolicitacao;
        this.dadosIdentificados = dadosIdentificados;
        this.sistemasAfetados = sistemasAfetados;
        this.totalRegistrosIdentificados = totalRegistrosIdentificados;
        this.totalRegistrosProcessados = totalRegistrosProcessados;
        this.totalRegistrosAnonimizados = totalRegistrosAnonimizados;
        this.totalRegistrosExcluidos = totalRegistrosExcluidos;
        this.observacoes = observacoes;
        this.errosProcessamento = errosProcessamento;
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof DireitoEsquecimento)) return false;
        final DireitoEsquecimento other = (DireitoEsquecimento) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$totalRegistrosIdentificados = this.getTotalRegistrosIdentificados();
        final java.lang.Object other$totalRegistrosIdentificados = other.getTotalRegistrosIdentificados();
        if (this$totalRegistrosIdentificados == null ? other$totalRegistrosIdentificados != null : !this$totalRegistrosIdentificados.equals(other$totalRegistrosIdentificados)) return false;
        final java.lang.Object this$totalRegistrosProcessados = this.getTotalRegistrosProcessados();
        final java.lang.Object other$totalRegistrosProcessados = other.getTotalRegistrosProcessados();
        if (this$totalRegistrosProcessados == null ? other$totalRegistrosProcessados != null : !this$totalRegistrosProcessados.equals(other$totalRegistrosProcessados)) return false;
        final java.lang.Object this$totalRegistrosAnonimizados = this.getTotalRegistrosAnonimizados();
        final java.lang.Object other$totalRegistrosAnonimizados = other.getTotalRegistrosAnonimizados();
        if (this$totalRegistrosAnonimizados == null ? other$totalRegistrosAnonimizados != null : !this$totalRegistrosAnonimizados.equals(other$totalRegistrosAnonimizados)) return false;
        final java.lang.Object this$totalRegistrosExcluidos = this.getTotalRegistrosExcluidos();
        final java.lang.Object other$totalRegistrosExcluidos = other.getTotalRegistrosExcluidos();
        if (this$totalRegistrosExcluidos == null ? other$totalRegistrosExcluidos != null : !this$totalRegistrosExcluidos.equals(other$totalRegistrosExcluidos)) return false;
        final java.lang.Object this$codigoSolicitacao = this.getCodigoSolicitacao();
        final java.lang.Object other$codigoSolicitacao = other.getCodigoSolicitacao();
        if (this$codigoSolicitacao == null ? other$codigoSolicitacao != null : !this$codigoSolicitacao.equals(other$codigoSolicitacao)) return false;
        final java.lang.Object this$cpfCnpj = this.getCpfCnpj();
        final java.lang.Object other$cpfCnpj = other.getCpfCnpj();
        if (this$cpfCnpj == null ? other$cpfCnpj != null : !this$cpfCnpj.equals(other$cpfCnpj)) return false;
        final java.lang.Object this$tipoDireito = this.getTipoDireito();
        final java.lang.Object other$tipoDireito = other.getTipoDireito();
        if (this$tipoDireito == null ? other$tipoDireito != null : !this$tipoDireito.equals(other$tipoDireito)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataSolicitacao = this.getDataSolicitacao();
        final java.lang.Object other$dataSolicitacao = other.getDataSolicitacao();
        if (this$dataSolicitacao == null ? other$dataSolicitacao != null : !this$dataSolicitacao.equals(other$dataSolicitacao)) return false;
        final java.lang.Object this$dataInicioProcessamento = this.getDataInicioProcessamento();
        final java.lang.Object other$dataInicioProcessamento = other.getDataInicioProcessamento();
        if (this$dataInicioProcessamento == null ? other$dataInicioProcessamento != null : !this$dataInicioProcessamento.equals(other$dataInicioProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$justificativaSolicitacao = this.getJustificativaSolicitacao();
        final java.lang.Object other$justificativaSolicitacao = other.getJustificativaSolicitacao();
        if (this$justificativaSolicitacao == null ? other$justificativaSolicitacao != null : !this$justificativaSolicitacao.equals(other$justificativaSolicitacao)) return false;
        final java.lang.Object this$dadosIdentificados = this.getDadosIdentificados();
        final java.lang.Object other$dadosIdentificados = other.getDadosIdentificados();
        if (this$dadosIdentificados == null ? other$dadosIdentificados != null : !this$dadosIdentificados.equals(other$dadosIdentificados)) return false;
        final java.lang.Object this$sistemasAfetados = this.getSistemasAfetados();
        final java.lang.Object other$sistemasAfetados = other.getSistemasAfetados();
        if (this$sistemasAfetados == null ? other$sistemasAfetados != null : !this$sistemasAfetados.equals(other$sistemasAfetados)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$errosProcessamento = this.getErrosProcessamento();
        final java.lang.Object other$errosProcessamento = other.getErrosProcessamento();
        if (this$errosProcessamento == null ? other$errosProcessamento != null : !this$errosProcessamento.equals(other$errosProcessamento)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof DireitoEsquecimento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $totalRegistrosIdentificados = this.getTotalRegistrosIdentificados();
        result = result * PRIME + ($totalRegistrosIdentificados == null ? 43 : $totalRegistrosIdentificados.hashCode());
        final java.lang.Object $totalRegistrosProcessados = this.getTotalRegistrosProcessados();
        result = result * PRIME + ($totalRegistrosProcessados == null ? 43 : $totalRegistrosProcessados.hashCode());
        final java.lang.Object $totalRegistrosAnonimizados = this.getTotalRegistrosAnonimizados();
        result = result * PRIME + ($totalRegistrosAnonimizados == null ? 43 : $totalRegistrosAnonimizados.hashCode());
        final java.lang.Object $totalRegistrosExcluidos = this.getTotalRegistrosExcluidos();
        result = result * PRIME + ($totalRegistrosExcluidos == null ? 43 : $totalRegistrosExcluidos.hashCode());
        final java.lang.Object $codigoSolicitacao = this.getCodigoSolicitacao();
        result = result * PRIME + ($codigoSolicitacao == null ? 43 : $codigoSolicitacao.hashCode());
        final java.lang.Object $cpfCnpj = this.getCpfCnpj();
        result = result * PRIME + ($cpfCnpj == null ? 43 : $cpfCnpj.hashCode());
        final java.lang.Object $tipoDireito = this.getTipoDireito();
        result = result * PRIME + ($tipoDireito == null ? 43 : $tipoDireito.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataSolicitacao = this.getDataSolicitacao();
        result = result * PRIME + ($dataSolicitacao == null ? 43 : $dataSolicitacao.hashCode());
        final java.lang.Object $dataInicioProcessamento = this.getDataInicioProcessamento();
        result = result * PRIME + ($dataInicioProcessamento == null ? 43 : $dataInicioProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $justificativaSolicitacao = this.getJustificativaSolicitacao();
        result = result * PRIME + ($justificativaSolicitacao == null ? 43 : $justificativaSolicitacao.hashCode());
        final java.lang.Object $dadosIdentificados = this.getDadosIdentificados();
        result = result * PRIME + ($dadosIdentificados == null ? 43 : $dadosIdentificados.hashCode());
        final java.lang.Object $sistemasAfetados = this.getSistemasAfetados();
        result = result * PRIME + ($sistemasAfetados == null ? 43 : $sistemasAfetados.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $errosProcessamento = this.getErrosProcessamento();
        result = result * PRIME + ($errosProcessamento == null ? 43 : $errosProcessamento.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        return result;
    }
}
