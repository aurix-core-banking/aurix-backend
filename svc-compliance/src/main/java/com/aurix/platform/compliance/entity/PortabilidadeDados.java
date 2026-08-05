package com.aurix.platform.compliance.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "portabilidade_dados", schema = "aurix")
public class PortabilidadeDados extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoPortabilidade;
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPortabilidade tipoPortabilidade;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPortabilidade status = StatusPortabilidade.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;
    @Column
    private LocalDateTime dataGeracao;
    @Column
    private LocalDateTime dataExpiracao;
    @Column(name = "formato_dados", length = 50)
    private String formatoDados = "JSON";
    @Column(name = "dados_exportados", columnDefinition = "TEXT")
    private String dadosExportados;
    @Column(name = "arquivo_gerado", length = 500)
    private String arquivoGerado;
    @Column
    private Long tamanhoArquivo;
    @Column(length = 100)
    private String hashArquivo;
    @Column(name = "destino_portabilidade", length = 200)
    private String destinoPortabilidade;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tipos_dados", columnDefinition = "JSONB")
    private String tiposDados;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;


    public enum TipoPortabilidade {
        EXPORTACAO_COMPLETA, EXPORTACAO_PARCIAL, TRANSFERENCIA_OUTRA_INSTITUICAO;
    }


    public enum StatusPortabilidade {
        PENDENTE, PROCESSANDO, GERADO, DISPONIVEL, BAIXADO, EXPIRADO, CANCELADO, ERRO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoPortabilidade() {
        return this.codigoPortabilidade;
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
    public TipoPortabilidade getTipoPortabilidade() {
        return this.tipoPortabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public StatusPortabilidade getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getFormatoDados() {
        return this.formatoDados;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosExportados() {
        return this.dadosExportados;
    }

    @java.lang.SuppressWarnings("all")
    public String getArquivoGerado() {
        return this.arquivoGerado;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTamanhoArquivo() {
        return this.tamanhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getHashArquivo() {
        return this.hashArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getDestinoPortabilidade() {
        return this.destinoPortabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getTiposDados() {
        return this.tiposDados;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoPortabilidade(final String codigoPortabilidade) {
        this.codigoPortabilidade = codigoPortabilidade;
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
    public void setTipoPortabilidade(final TipoPortabilidade tipoPortabilidade) {
        this.tipoPortabilidade = tipoPortabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusPortabilidade status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setFormatoDados(final String formatoDados) {
        this.formatoDados = formatoDados;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosExportados(final String dadosExportados) {
        this.dadosExportados = dadosExportados;
    }

    @java.lang.SuppressWarnings("all")
    public void setArquivoGerado(final String arquivoGerado) {
        this.arquivoGerado = arquivoGerado;
    }

    @java.lang.SuppressWarnings("all")
    public void setTamanhoArquivo(final Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setHashArquivo(final String hashArquivo) {
        this.hashArquivo = hashArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDestinoPortabilidade(final String destinoPortabilidade) {
        this.destinoPortabilidade = destinoPortabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setTiposDados(final String tiposDados) {
        this.tiposDados = tiposDados;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PortabilidadeDados(id=" + this.getId() + ", codigoPortabilidade=" + this.getCodigoPortabilidade() + ", clienteId=" + this.getClienteId() + ", cpfCnpj=" + this.getCpfCnpj() + ", tipoPortabilidade=" + this.getTipoPortabilidade() + ", status=" + this.getStatus() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataGeracao=" + this.getDataGeracao() + ", dataExpiracao=" + this.getDataExpiracao() + ", formatoDados=" + this.getFormatoDados() + ", dadosExportados=" + this.getDadosExportados() + ", arquivoGerado=" + this.getArquivoGerado() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", hashArquivo=" + this.getHashArquivo() + ", destinoPortabilidade=" + this.getDestinoPortabilidade() + ", tiposDados=" + this.getTiposDados() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PortabilidadeDados() {
    }

    @java.lang.SuppressWarnings("all")
    public PortabilidadeDados(final Long id, final String codigoPortabilidade, final Long clienteId, final String cpfCnpj, final TipoPortabilidade tipoPortabilidade, final StatusPortabilidade status, final LocalDateTime dataSolicitacao, final LocalDateTime dataGeracao, final LocalDateTime dataExpiracao, final String formatoDados, final String dadosExportados, final String arquivoGerado, final Long tamanhoArquivo, final String hashArquivo, final String destinoPortabilidade, final String tiposDados, final String observacoes, final String metadata) {
        this.setId(id);
        this.codigoPortabilidade = codigoPortabilidade;
        this.clienteId = clienteId;
        this.cpfCnpj = cpfCnpj;
        this.tipoPortabilidade = tipoPortabilidade;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataGeracao = dataGeracao;
        this.dataExpiracao = dataExpiracao;
        this.formatoDados = formatoDados;
        this.dadosExportados = dadosExportados;
        this.arquivoGerado = arquivoGerado;
        this.tamanhoArquivo = tamanhoArquivo;
        this.hashArquivo = hashArquivo;
        this.destinoPortabilidade = destinoPortabilidade;
        this.tiposDados = tiposDados;
        this.observacoes = observacoes;
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PortabilidadeDados)) return false;
        final PortabilidadeDados other = (PortabilidadeDados) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$codigoPortabilidade = this.getCodigoPortabilidade();
        final java.lang.Object other$codigoPortabilidade = other.getCodigoPortabilidade();
        if (this$codigoPortabilidade == null ? other$codigoPortabilidade != null : !this$codigoPortabilidade.equals(other$codigoPortabilidade)) return false;
        final java.lang.Object this$cpfCnpj = this.getCpfCnpj();
        final java.lang.Object other$cpfCnpj = other.getCpfCnpj();
        if (this$cpfCnpj == null ? other$cpfCnpj != null : !this$cpfCnpj.equals(other$cpfCnpj)) return false;
        final java.lang.Object this$tipoPortabilidade = this.getTipoPortabilidade();
        final java.lang.Object other$tipoPortabilidade = other.getTipoPortabilidade();
        if (this$tipoPortabilidade == null ? other$tipoPortabilidade != null : !this$tipoPortabilidade.equals(other$tipoPortabilidade)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataSolicitacao = this.getDataSolicitacao();
        final java.lang.Object other$dataSolicitacao = other.getDataSolicitacao();
        if (this$dataSolicitacao == null ? other$dataSolicitacao != null : !this$dataSolicitacao.equals(other$dataSolicitacao)) return false;
        final java.lang.Object this$dataGeracao = this.getDataGeracao();
        final java.lang.Object other$dataGeracao = other.getDataGeracao();
        if (this$dataGeracao == null ? other$dataGeracao != null : !this$dataGeracao.equals(other$dataGeracao)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$formatoDados = this.getFormatoDados();
        final java.lang.Object other$formatoDados = other.getFormatoDados();
        if (this$formatoDados == null ? other$formatoDados != null : !this$formatoDados.equals(other$formatoDados)) return false;
        final java.lang.Object this$dadosExportados = this.getDadosExportados();
        final java.lang.Object other$dadosExportados = other.getDadosExportados();
        if (this$dadosExportados == null ? other$dadosExportados != null : !this$dadosExportados.equals(other$dadosExportados)) return false;
        final java.lang.Object this$arquivoGerado = this.getArquivoGerado();
        final java.lang.Object other$arquivoGerado = other.getArquivoGerado();
        if (this$arquivoGerado == null ? other$arquivoGerado != null : !this$arquivoGerado.equals(other$arquivoGerado)) return false;
        final java.lang.Object this$hashArquivo = this.getHashArquivo();
        final java.lang.Object other$hashArquivo = other.getHashArquivo();
        if (this$hashArquivo == null ? other$hashArquivo != null : !this$hashArquivo.equals(other$hashArquivo)) return false;
        final java.lang.Object this$destinoPortabilidade = this.getDestinoPortabilidade();
        final java.lang.Object other$destinoPortabilidade = other.getDestinoPortabilidade();
        if (this$destinoPortabilidade == null ? other$destinoPortabilidade != null : !this$destinoPortabilidade.equals(other$destinoPortabilidade)) return false;
        final java.lang.Object this$tiposDados = this.getTiposDados();
        final java.lang.Object other$tiposDados = other.getTiposDados();
        if (this$tiposDados == null ? other$tiposDados != null : !this$tiposDados.equals(other$tiposDados)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PortabilidadeDados;
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
        final java.lang.Object $tamanhoArquivo = this.getTamanhoArquivo();
        result = result * PRIME + ($tamanhoArquivo == null ? 43 : $tamanhoArquivo.hashCode());
        final java.lang.Object $codigoPortabilidade = this.getCodigoPortabilidade();
        result = result * PRIME + ($codigoPortabilidade == null ? 43 : $codigoPortabilidade.hashCode());
        final java.lang.Object $cpfCnpj = this.getCpfCnpj();
        result = result * PRIME + ($cpfCnpj == null ? 43 : $cpfCnpj.hashCode());
        final java.lang.Object $tipoPortabilidade = this.getTipoPortabilidade();
        result = result * PRIME + ($tipoPortabilidade == null ? 43 : $tipoPortabilidade.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataSolicitacao = this.getDataSolicitacao();
        result = result * PRIME + ($dataSolicitacao == null ? 43 : $dataSolicitacao.hashCode());
        final java.lang.Object $dataGeracao = this.getDataGeracao();
        result = result * PRIME + ($dataGeracao == null ? 43 : $dataGeracao.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $formatoDados = this.getFormatoDados();
        result = result * PRIME + ($formatoDados == null ? 43 : $formatoDados.hashCode());
        final java.lang.Object $dadosExportados = this.getDadosExportados();
        result = result * PRIME + ($dadosExportados == null ? 43 : $dadosExportados.hashCode());
        final java.lang.Object $arquivoGerado = this.getArquivoGerado();
        result = result * PRIME + ($arquivoGerado == null ? 43 : $arquivoGerado.hashCode());
        final java.lang.Object $hashArquivo = this.getHashArquivo();
        result = result * PRIME + ($hashArquivo == null ? 43 : $hashArquivo.hashCode());
        final java.lang.Object $destinoPortabilidade = this.getDestinoPortabilidade();
        result = result * PRIME + ($destinoPortabilidade == null ? 43 : $destinoPortabilidade.hashCode());
        final java.lang.Object $tiposDados = this.getTiposDados();
        result = result * PRIME + ($tiposDados == null ? 43 : $tiposDados.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        return result;
    }
}
