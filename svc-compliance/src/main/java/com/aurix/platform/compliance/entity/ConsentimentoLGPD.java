package com.aurix.platform.compliance.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "consentimentos_lgpd", schema = "aurix")
public class ConsentimentoLGPD extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoConsentimento;
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConsentimento tipoConsentimento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsentimento status = StatusConsentimento.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;
    @Column
    private LocalDateTime dataConsentimento;
    @Column
    private LocalDateTime dataExpiracao;
    @Column(columnDefinition = "TEXT")
    private String descricaoFinalidade;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_coletados", columnDefinition = "JSONB")
    private String dadosColetados;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "finalidades", columnDefinition = "JSONB")
    private String finalidades;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "compartilhamentos", columnDefinition = "JSONB")
    private String compartilhamentos;
    @Column
    private Boolean consentimentoEspecifico = false;
    @Column
    private Boolean consentimentoInformado = false;
    @Column
    private Boolean consentimentoLivre = false;
    @Column
    private Boolean consentimentoIndubitavel = false;
    @Column(length = 500)
    private String ipAddress;
    @Column(length = 1000)
    private String userAgent;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;


    public enum TipoConsentimento {
        COLETA_DADOS, PROCESSAMENTO_DADOS, COMPARTILHAMENTO_DADOS, TRANSFERENCIA_INTERNACIONAL, MARKETING_DIRETO, PESQUISA, OUTROS;
    }


    public enum StatusConsentimento {
        PENDENTE, CONCEDIDO, NEGADO, REVOGADO, EXPIRADO, CANCELADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoConsentimento() {
        return this.codigoConsentimento;
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
    public TipoConsentimento getTipoConsentimento() {
        return this.tipoConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConsentimento getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConsentimento() {
        return this.dataConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoFinalidade() {
        return this.descricaoFinalidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosColetados() {
        return this.dadosColetados;
    }

    @java.lang.SuppressWarnings("all")
    public String getFinalidades() {
        return this.finalidades;
    }

    @java.lang.SuppressWarnings("all")
    public String getCompartilhamentos() {
        return this.compartilhamentos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getConsentimentoEspecifico() {
        return this.consentimentoEspecifico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getConsentimentoInformado() {
        return this.consentimentoInformado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getConsentimentoLivre() {
        return this.consentimentoLivre;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getConsentimentoIndubitavel() {
        return this.consentimentoIndubitavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getIpAddress() {
        return this.ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
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
    public void setCodigoConsentimento(final String codigoConsentimento) {
        this.codigoConsentimento = codigoConsentimento;
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
    public void setTipoConsentimento(final TipoConsentimento tipoConsentimento) {
        this.tipoConsentimento = tipoConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConsentimento status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConsentimento(final LocalDateTime dataConsentimento) {
        this.dataConsentimento = dataConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoFinalidade(final String descricaoFinalidade) {
        this.descricaoFinalidade = descricaoFinalidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosColetados(final String dadosColetados) {
        this.dadosColetados = dadosColetados;
    }

    @java.lang.SuppressWarnings("all")
    public void setFinalidades(final String finalidades) {
        this.finalidades = finalidades;
    }

    @java.lang.SuppressWarnings("all")
    public void setCompartilhamentos(final String compartilhamentos) {
        this.compartilhamentos = compartilhamentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setConsentimentoEspecifico(final Boolean consentimentoEspecifico) {
        this.consentimentoEspecifico = consentimentoEspecifico;
    }

    @java.lang.SuppressWarnings("all")
    public void setConsentimentoInformado(final Boolean consentimentoInformado) {
        this.consentimentoInformado = consentimentoInformado;
    }

    @java.lang.SuppressWarnings("all")
    public void setConsentimentoLivre(final Boolean consentimentoLivre) {
        this.consentimentoLivre = consentimentoLivre;
    }

    @java.lang.SuppressWarnings("all")
    public void setConsentimentoIndubitavel(final Boolean consentimentoIndubitavel) {
        this.consentimentoIndubitavel = consentimentoIndubitavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
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
        return "ConsentimentoLGPD(id=" + this.getId() + ", codigoConsentimento=" + this.getCodigoConsentimento() + ", clienteId=" + this.getClienteId() + ", cpfCnpj=" + this.getCpfCnpj() + ", tipoConsentimento=" + this.getTipoConsentimento() + ", status=" + this.getStatus() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataConsentimento=" + this.getDataConsentimento() + ", dataExpiracao=" + this.getDataExpiracao() + ", descricaoFinalidade=" + this.getDescricaoFinalidade() + ", dadosColetados=" + this.getDadosColetados() + ", finalidades=" + this.getFinalidades() + ", compartilhamentos=" + this.getCompartilhamentos() + ", consentimentoEspecifico=" + this.getConsentimentoEspecifico() + ", consentimentoInformado=" + this.getConsentimentoInformado() + ", consentimentoLivre=" + this.getConsentimentoLivre() + ", consentimentoIndubitavel=" + this.getConsentimentoIndubitavel() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoLGPD() {
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoLGPD(final Long id, final String codigoConsentimento, final Long clienteId, final String cpfCnpj, final TipoConsentimento tipoConsentimento, final StatusConsentimento status, final LocalDateTime dataSolicitacao, final LocalDateTime dataConsentimento, final LocalDateTime dataExpiracao, final String descricaoFinalidade, final String dadosColetados, final String finalidades, final String compartilhamentos, final Boolean consentimentoEspecifico, final Boolean consentimentoInformado, final Boolean consentimentoLivre, final Boolean consentimentoIndubitavel, final String ipAddress, final String userAgent, final String observacoes, final String metadata) {
        this.setId(id);
        this.codigoConsentimento = codigoConsentimento;
        this.clienteId = clienteId;
        this.cpfCnpj = cpfCnpj;
        this.tipoConsentimento = tipoConsentimento;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataConsentimento = dataConsentimento;
        this.dataExpiracao = dataExpiracao;
        this.descricaoFinalidade = descricaoFinalidade;
        this.dadosColetados = dadosColetados;
        this.finalidades = finalidades;
        this.compartilhamentos = compartilhamentos;
        this.consentimentoEspecifico = consentimentoEspecifico;
        this.consentimentoInformado = consentimentoInformado;
        this.consentimentoLivre = consentimentoLivre;
        this.consentimentoIndubitavel = consentimentoIndubitavel;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.observacoes = observacoes;
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ConsentimentoLGPD)) return false;
        final ConsentimentoLGPD other = (ConsentimentoLGPD) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$consentimentoEspecifico = this.getConsentimentoEspecifico();
        final java.lang.Object other$consentimentoEspecifico = other.getConsentimentoEspecifico();
        if (this$consentimentoEspecifico == null ? other$consentimentoEspecifico != null : !this$consentimentoEspecifico.equals(other$consentimentoEspecifico)) return false;
        final java.lang.Object this$consentimentoInformado = this.getConsentimentoInformado();
        final java.lang.Object other$consentimentoInformado = other.getConsentimentoInformado();
        if (this$consentimentoInformado == null ? other$consentimentoInformado != null : !this$consentimentoInformado.equals(other$consentimentoInformado)) return false;
        final java.lang.Object this$consentimentoLivre = this.getConsentimentoLivre();
        final java.lang.Object other$consentimentoLivre = other.getConsentimentoLivre();
        if (this$consentimentoLivre == null ? other$consentimentoLivre != null : !this$consentimentoLivre.equals(other$consentimentoLivre)) return false;
        final java.lang.Object this$consentimentoIndubitavel = this.getConsentimentoIndubitavel();
        final java.lang.Object other$consentimentoIndubitavel = other.getConsentimentoIndubitavel();
        if (this$consentimentoIndubitavel == null ? other$consentimentoIndubitavel != null : !this$consentimentoIndubitavel.equals(other$consentimentoIndubitavel)) return false;
        final java.lang.Object this$codigoConsentimento = this.getCodigoConsentimento();
        final java.lang.Object other$codigoConsentimento = other.getCodigoConsentimento();
        if (this$codigoConsentimento == null ? other$codigoConsentimento != null : !this$codigoConsentimento.equals(other$codigoConsentimento)) return false;
        final java.lang.Object this$cpfCnpj = this.getCpfCnpj();
        final java.lang.Object other$cpfCnpj = other.getCpfCnpj();
        if (this$cpfCnpj == null ? other$cpfCnpj != null : !this$cpfCnpj.equals(other$cpfCnpj)) return false;
        final java.lang.Object this$tipoConsentimento = this.getTipoConsentimento();
        final java.lang.Object other$tipoConsentimento = other.getTipoConsentimento();
        if (this$tipoConsentimento == null ? other$tipoConsentimento != null : !this$tipoConsentimento.equals(other$tipoConsentimento)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataSolicitacao = this.getDataSolicitacao();
        final java.lang.Object other$dataSolicitacao = other.getDataSolicitacao();
        if (this$dataSolicitacao == null ? other$dataSolicitacao != null : !this$dataSolicitacao.equals(other$dataSolicitacao)) return false;
        final java.lang.Object this$dataConsentimento = this.getDataConsentimento();
        final java.lang.Object other$dataConsentimento = other.getDataConsentimento();
        if (this$dataConsentimento == null ? other$dataConsentimento != null : !this$dataConsentimento.equals(other$dataConsentimento)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$descricaoFinalidade = this.getDescricaoFinalidade();
        final java.lang.Object other$descricaoFinalidade = other.getDescricaoFinalidade();
        if (this$descricaoFinalidade == null ? other$descricaoFinalidade != null : !this$descricaoFinalidade.equals(other$descricaoFinalidade)) return false;
        final java.lang.Object this$dadosColetados = this.getDadosColetados();
        final java.lang.Object other$dadosColetados = other.getDadosColetados();
        if (this$dadosColetados == null ? other$dadosColetados != null : !this$dadosColetados.equals(other$dadosColetados)) return false;
        final java.lang.Object this$finalidades = this.getFinalidades();
        final java.lang.Object other$finalidades = other.getFinalidades();
        if (this$finalidades == null ? other$finalidades != null : !this$finalidades.equals(other$finalidades)) return false;
        final java.lang.Object this$compartilhamentos = this.getCompartilhamentos();
        final java.lang.Object other$compartilhamentos = other.getCompartilhamentos();
        if (this$compartilhamentos == null ? other$compartilhamentos != null : !this$compartilhamentos.equals(other$compartilhamentos)) return false;
        final java.lang.Object this$ipAddress = this.getIpAddress();
        final java.lang.Object other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
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
        return other instanceof ConsentimentoLGPD;
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
        final java.lang.Object $consentimentoEspecifico = this.getConsentimentoEspecifico();
        result = result * PRIME + ($consentimentoEspecifico == null ? 43 : $consentimentoEspecifico.hashCode());
        final java.lang.Object $consentimentoInformado = this.getConsentimentoInformado();
        result = result * PRIME + ($consentimentoInformado == null ? 43 : $consentimentoInformado.hashCode());
        final java.lang.Object $consentimentoLivre = this.getConsentimentoLivre();
        result = result * PRIME + ($consentimentoLivre == null ? 43 : $consentimentoLivre.hashCode());
        final java.lang.Object $consentimentoIndubitavel = this.getConsentimentoIndubitavel();
        result = result * PRIME + ($consentimentoIndubitavel == null ? 43 : $consentimentoIndubitavel.hashCode());
        final java.lang.Object $codigoConsentimento = this.getCodigoConsentimento();
        result = result * PRIME + ($codigoConsentimento == null ? 43 : $codigoConsentimento.hashCode());
        final java.lang.Object $cpfCnpj = this.getCpfCnpj();
        result = result * PRIME + ($cpfCnpj == null ? 43 : $cpfCnpj.hashCode());
        final java.lang.Object $tipoConsentimento = this.getTipoConsentimento();
        result = result * PRIME + ($tipoConsentimento == null ? 43 : $tipoConsentimento.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataSolicitacao = this.getDataSolicitacao();
        result = result * PRIME + ($dataSolicitacao == null ? 43 : $dataSolicitacao.hashCode());
        final java.lang.Object $dataConsentimento = this.getDataConsentimento();
        result = result * PRIME + ($dataConsentimento == null ? 43 : $dataConsentimento.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $descricaoFinalidade = this.getDescricaoFinalidade();
        result = result * PRIME + ($descricaoFinalidade == null ? 43 : $descricaoFinalidade.hashCode());
        final java.lang.Object $dadosColetados = this.getDadosColetados();
        result = result * PRIME + ($dadosColetados == null ? 43 : $dadosColetados.hashCode());
        final java.lang.Object $finalidades = this.getFinalidades();
        result = result * PRIME + ($finalidades == null ? 43 : $finalidades.hashCode());
        final java.lang.Object $compartilhamentos = this.getCompartilhamentos();
        result = result * PRIME + ($compartilhamentos == null ? 43 : $compartilhamentos.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        return result;
    }
}
