package com.aurix.platform.compliance.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "anonimizacoes_dados", schema = "aurix")
public class AnonimizacaoDados extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoAnonimizacao;
    @Column(name = "cliente_id")
    private Long clienteId;
    @Column(name = "cpf_cnpj", length = 14)
    private String cpfCnpj;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAnonimizacao tipoAnonimizacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnonimizacao status = StatusAnonimizacao.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tabelas_afetadas", columnDefinition = "JSONB")
    private String tabelasAfetadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "campos_anonimizados", columnDefinition = "JSONB")
    private String camposAnonimizados;
    @Column
    private Integer totalRegistrosProcessados = 0;
    @Column
    private Integer totalRegistrosAnonimizados = 0;
    @Column(name = "metodo_anonimizacao", length = 100)
    private String metodoAnonimizacao;
    @Column(name = "algoritmo_hash", length = 100)
    private String algoritmoHash;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "erros_processamento", columnDefinition = "JSONB")
    private String errosProcessamento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;


    public enum TipoAnonimizacao {
        COMPLETA, PARCIAL, PSEUDONIMIZACAO, HASH, MASCARAMENTO;
    }


    public enum StatusAnonimizacao {
        PENDENTE, PROCESSANDO, CONCLUIDO, ERRO, CANCELADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoAnonimizacao() {
        return this.codigoAnonimizacao;
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
    public TipoAnonimizacao getTipoAnonimizacao() {
        return this.tipoAnonimizacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusAnonimizacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public String getTabelasAfetadas() {
        return this.tabelasAfetadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getCamposAnonimizados() {
        return this.camposAnonimizados;
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
    public String getMetodoAnonimizacao() {
        return this.metodoAnonimizacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getAlgoritmoHash() {
        return this.algoritmoHash;
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
    public void setCodigoAnonimizacao(final String codigoAnonimizacao) {
        this.codigoAnonimizacao = codigoAnonimizacao;
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
    public void setTipoAnonimizacao(final TipoAnonimizacao tipoAnonimizacao) {
        this.tipoAnonimizacao = tipoAnonimizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAnonimizacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConclusao(final LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTabelasAfetadas(final String tabelasAfetadas) {
        this.tabelasAfetadas = tabelasAfetadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setCamposAnonimizados(final String camposAnonimizados) {
        this.camposAnonimizados = camposAnonimizados;
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
    public void setMetodoAnonimizacao(final String metodoAnonimizacao) {
        this.metodoAnonimizacao = metodoAnonimizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setAlgoritmoHash(final String algoritmoHash) {
        this.algoritmoHash = algoritmoHash;
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
        return "AnonimizacaoDados(id=" + this.getId() + ", codigoAnonimizacao=" + this.getCodigoAnonimizacao() + ", clienteId=" + this.getClienteId() + ", cpfCnpj=" + this.getCpfCnpj() + ", tipoAnonimizacao=" + this.getTipoAnonimizacao() + ", status=" + this.getStatus() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", tabelasAfetadas=" + this.getTabelasAfetadas() + ", camposAnonimizados=" + this.getCamposAnonimizados() + ", totalRegistrosProcessados=" + this.getTotalRegistrosProcessados() + ", totalRegistrosAnonimizados=" + this.getTotalRegistrosAnonimizados() + ", metodoAnonimizacao=" + this.getMetodoAnonimizacao() + ", algoritmoHash=" + this.getAlgoritmoHash() + ", observacoes=" + this.getObservacoes() + ", errosProcessamento=" + this.getErrosProcessamento() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AnonimizacaoDados() {
    }

    @java.lang.SuppressWarnings("all")
    public AnonimizacaoDados(final Long id, final String codigoAnonimizacao, final Long clienteId, final String cpfCnpj, final TipoAnonimizacao tipoAnonimizacao, final StatusAnonimizacao status, final LocalDateTime dataSolicitacao, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final String tabelasAfetadas, final String camposAnonimizados, final Integer totalRegistrosProcessados, final Integer totalRegistrosAnonimizados, final String metodoAnonimizacao, final String algoritmoHash, final String observacoes, final String errosProcessamento, final String metadata) {
        this.setId(id);
        this.codigoAnonimizacao = codigoAnonimizacao;
        this.clienteId = clienteId;
        this.cpfCnpj = cpfCnpj;
        this.tipoAnonimizacao = tipoAnonimizacao;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.tabelasAfetadas = tabelasAfetadas;
        this.camposAnonimizados = camposAnonimizados;
        this.totalRegistrosProcessados = totalRegistrosProcessados;
        this.totalRegistrosAnonimizados = totalRegistrosAnonimizados;
        this.metodoAnonimizacao = metodoAnonimizacao;
        this.algoritmoHash = algoritmoHash;
        this.observacoes = observacoes;
        this.errosProcessamento = errosProcessamento;
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AnonimizacaoDados)) return false;
        final AnonimizacaoDados other = (AnonimizacaoDados) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$totalRegistrosProcessados = this.getTotalRegistrosProcessados();
        final java.lang.Object other$totalRegistrosProcessados = other.getTotalRegistrosProcessados();
        if (this$totalRegistrosProcessados == null ? other$totalRegistrosProcessados != null : !this$totalRegistrosProcessados.equals(other$totalRegistrosProcessados)) return false;
        final java.lang.Object this$totalRegistrosAnonimizados = this.getTotalRegistrosAnonimizados();
        final java.lang.Object other$totalRegistrosAnonimizados = other.getTotalRegistrosAnonimizados();
        if (this$totalRegistrosAnonimizados == null ? other$totalRegistrosAnonimizados != null : !this$totalRegistrosAnonimizados.equals(other$totalRegistrosAnonimizados)) return false;
        final java.lang.Object this$codigoAnonimizacao = this.getCodigoAnonimizacao();
        final java.lang.Object other$codigoAnonimizacao = other.getCodigoAnonimizacao();
        if (this$codigoAnonimizacao == null ? other$codigoAnonimizacao != null : !this$codigoAnonimizacao.equals(other$codigoAnonimizacao)) return false;
        final java.lang.Object this$cpfCnpj = this.getCpfCnpj();
        final java.lang.Object other$cpfCnpj = other.getCpfCnpj();
        if (this$cpfCnpj == null ? other$cpfCnpj != null : !this$cpfCnpj.equals(other$cpfCnpj)) return false;
        final java.lang.Object this$tipoAnonimizacao = this.getTipoAnonimizacao();
        final java.lang.Object other$tipoAnonimizacao = other.getTipoAnonimizacao();
        if (this$tipoAnonimizacao == null ? other$tipoAnonimizacao != null : !this$tipoAnonimizacao.equals(other$tipoAnonimizacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataSolicitacao = this.getDataSolicitacao();
        final java.lang.Object other$dataSolicitacao = other.getDataSolicitacao();
        if (this$dataSolicitacao == null ? other$dataSolicitacao != null : !this$dataSolicitacao.equals(other$dataSolicitacao)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$tabelasAfetadas = this.getTabelasAfetadas();
        final java.lang.Object other$tabelasAfetadas = other.getTabelasAfetadas();
        if (this$tabelasAfetadas == null ? other$tabelasAfetadas != null : !this$tabelasAfetadas.equals(other$tabelasAfetadas)) return false;
        final java.lang.Object this$camposAnonimizados = this.getCamposAnonimizados();
        final java.lang.Object other$camposAnonimizados = other.getCamposAnonimizados();
        if (this$camposAnonimizados == null ? other$camposAnonimizados != null : !this$camposAnonimizados.equals(other$camposAnonimizados)) return false;
        final java.lang.Object this$metodoAnonimizacao = this.getMetodoAnonimizacao();
        final java.lang.Object other$metodoAnonimizacao = other.getMetodoAnonimizacao();
        if (this$metodoAnonimizacao == null ? other$metodoAnonimizacao != null : !this$metodoAnonimizacao.equals(other$metodoAnonimizacao)) return false;
        final java.lang.Object this$algoritmoHash = this.getAlgoritmoHash();
        final java.lang.Object other$algoritmoHash = other.getAlgoritmoHash();
        if (this$algoritmoHash == null ? other$algoritmoHash != null : !this$algoritmoHash.equals(other$algoritmoHash)) return false;
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
        return other instanceof AnonimizacaoDados;
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
        final java.lang.Object $totalRegistrosProcessados = this.getTotalRegistrosProcessados();
        result = result * PRIME + ($totalRegistrosProcessados == null ? 43 : $totalRegistrosProcessados.hashCode());
        final java.lang.Object $totalRegistrosAnonimizados = this.getTotalRegistrosAnonimizados();
        result = result * PRIME + ($totalRegistrosAnonimizados == null ? 43 : $totalRegistrosAnonimizados.hashCode());
        final java.lang.Object $codigoAnonimizacao = this.getCodigoAnonimizacao();
        result = result * PRIME + ($codigoAnonimizacao == null ? 43 : $codigoAnonimizacao.hashCode());
        final java.lang.Object $cpfCnpj = this.getCpfCnpj();
        result = result * PRIME + ($cpfCnpj == null ? 43 : $cpfCnpj.hashCode());
        final java.lang.Object $tipoAnonimizacao = this.getTipoAnonimizacao();
        result = result * PRIME + ($tipoAnonimizacao == null ? 43 : $tipoAnonimizacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataSolicitacao = this.getDataSolicitacao();
        result = result * PRIME + ($dataSolicitacao == null ? 43 : $dataSolicitacao.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $tabelasAfetadas = this.getTabelasAfetadas();
        result = result * PRIME + ($tabelasAfetadas == null ? 43 : $tabelasAfetadas.hashCode());
        final java.lang.Object $camposAnonimizados = this.getCamposAnonimizados();
        result = result * PRIME + ($camposAnonimizados == null ? 43 : $camposAnonimizados.hashCode());
        final java.lang.Object $metodoAnonimizacao = this.getMetodoAnonimizacao();
        result = result * PRIME + ($metodoAnonimizacao == null ? 43 : $metodoAnonimizacao.hashCode());
        final java.lang.Object $algoritmoHash = this.getAlgoritmoHash();
        result = result * PRIME + ($algoritmoHash == null ? 43 : $algoritmoHash.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $errosProcessamento = this.getErrosProcessamento();
        result = result * PRIME + ($errosProcessamento == null ? 43 : $errosProcessamento.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        return result;
    }
}
