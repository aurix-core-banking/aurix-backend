package com.aurix.platform.customer.security.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mfa_tokens", schema = "aurix")
public class MfaToken extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoToken;
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Column(name = "sessao_id", length = 100)
    private String sessaoId;
    @Column(nullable = false, length = 10)
    private String codigo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMfa tipoMfa;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusToken status = StatusToken.PENDENTE;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column
    private LocalDateTime dataValidacao;
    @Column
    private LocalDateTime dataExpiracao;
    @Column
    private Integer tentativasValidacao = 0;
    @Column
    private Integer maxTentativas = 3;
    @Column(length = 500)
    private String destinatario;
    @Column(length = 1000)
    private String observacoes;


    public enum TipoMfa {
        SMS, EMAIL, APP_AUTHENTICATOR, BIOMETRIA, TOKEN_HARDWARE, BACKUP_CODE;
    }


    public enum StatusToken {
        PENDENTE, VALIDADO, EXPIRADO, INVALIDADO, BLOQUEADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoToken() {
        return this.codigoToken;
    }

    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public String getSessaoId() {
        return this.sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigo() {
        return this.codigo;
    }

    @java.lang.SuppressWarnings("all")
    public TipoMfa getTipoMfa() {
        return this.tipoMfa;
    }

    @java.lang.SuppressWarnings("all")
    public StatusToken getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataValidacao() {
        return this.dataValidacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasValidacao() {
        return this.tentativasValidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMaxTentativas() {
        return this.maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public String getDestinatario() {
        return this.destinatario;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoToken(final String codigoToken) {
        this.codigoToken = codigoToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSessaoId(final String sessaoId) {
        this.sessaoId = sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoMfa(final TipoMfa tipoMfa) {
        this.tipoMfa = tipoMfa;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusToken status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataValidacao(final LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasValidacao(final Integer tentativasValidacao) {
        this.tentativasValidacao = tentativasValidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMaxTentativas(final Integer maxTentativas) {
        this.maxTentativas = maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public void setDestinatario(final String destinatario) {
        this.destinatario = destinatario;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "MfaToken(id=" + this.getId() + ", codigoToken=" + this.getCodigoToken() + ", usuarioId=" + this.getUsuarioId() + ", sessaoId=" + this.getSessaoId() + ", codigo=" + this.getCodigo() + ", tipoMfa=" + this.getTipoMfa() + ", status=" + this.getStatus() + ", dataCriacao=" + this.getDataCriacao() + ", dataValidacao=" + this.getDataValidacao() + ", dataExpiracao=" + this.getDataExpiracao() + ", tentativasValidacao=" + this.getTentativasValidacao() + ", maxTentativas=" + this.getMaxTentativas() + ", destinatario=" + this.getDestinatario() + ", observacoes=" + this.getObservacoes() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public MfaToken() {
    }

    @java.lang.SuppressWarnings("all")
    public MfaToken(final Long id, final String codigoToken, final Long usuarioId, final String sessaoId, final String codigo, final TipoMfa tipoMfa, final StatusToken status, final LocalDateTime dataCriacao, final LocalDateTime dataValidacao, final LocalDateTime dataExpiracao, final Integer tentativasValidacao, final Integer maxTentativas, final String destinatario, final String observacoes) {
        this.setId(id);
        this.codigoToken = codigoToken;
        this.usuarioId = usuarioId;
        this.sessaoId = sessaoId;
        this.codigo = codigo;
        this.tipoMfa = tipoMfa;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataValidacao = dataValidacao;
        this.dataExpiracao = dataExpiracao;
        this.tentativasValidacao = tentativasValidacao;
        this.maxTentativas = maxTentativas;
        this.destinatario = destinatario;
        this.observacoes = observacoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MfaToken)) return false;
        final MfaToken other = (MfaToken) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$tentativasValidacao = this.getTentativasValidacao();
        final java.lang.Object other$tentativasValidacao = other.getTentativasValidacao();
        if (this$tentativasValidacao == null ? other$tentativasValidacao != null : !this$tentativasValidacao.equals(other$tentativasValidacao)) return false;
        final java.lang.Object this$maxTentativas = this.getMaxTentativas();
        final java.lang.Object other$maxTentativas = other.getMaxTentativas();
        if (this$maxTentativas == null ? other$maxTentativas != null : !this$maxTentativas.equals(other$maxTentativas)) return false;
        final java.lang.Object this$codigoToken = this.getCodigoToken();
        final java.lang.Object other$codigoToken = other.getCodigoToken();
        if (this$codigoToken == null ? other$codigoToken != null : !this$codigoToken.equals(other$codigoToken)) return false;
        final java.lang.Object this$sessaoId = this.getSessaoId();
        final java.lang.Object other$sessaoId = other.getSessaoId();
        if (this$sessaoId == null ? other$sessaoId != null : !this$sessaoId.equals(other$sessaoId)) return false;
        final java.lang.Object this$codigo = this.getCodigo();
        final java.lang.Object other$codigo = other.getCodigo();
        if (this$codigo == null ? other$codigo != null : !this$codigo.equals(other$codigo)) return false;
        final java.lang.Object this$tipoMfa = this.getTipoMfa();
        final java.lang.Object other$tipoMfa = other.getTipoMfa();
        if (this$tipoMfa == null ? other$tipoMfa != null : !this$tipoMfa.equals(other$tipoMfa)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataValidacao = this.getDataValidacao();
        final java.lang.Object other$dataValidacao = other.getDataValidacao();
        if (this$dataValidacao == null ? other$dataValidacao != null : !this$dataValidacao.equals(other$dataValidacao)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$destinatario = this.getDestinatario();
        final java.lang.Object other$destinatario = other.getDestinatario();
        if (this$destinatario == null ? other$destinatario != null : !this$destinatario.equals(other$destinatario)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof MfaToken;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $tentativasValidacao = this.getTentativasValidacao();
        result = result * PRIME + ($tentativasValidacao == null ? 43 : $tentativasValidacao.hashCode());
        final java.lang.Object $maxTentativas = this.getMaxTentativas();
        result = result * PRIME + ($maxTentativas == null ? 43 : $maxTentativas.hashCode());
        final java.lang.Object $codigoToken = this.getCodigoToken();
        result = result * PRIME + ($codigoToken == null ? 43 : $codigoToken.hashCode());
        final java.lang.Object $sessaoId = this.getSessaoId();
        result = result * PRIME + ($sessaoId == null ? 43 : $sessaoId.hashCode());
        final java.lang.Object $codigo = this.getCodigo();
        result = result * PRIME + ($codigo == null ? 43 : $codigo.hashCode());
        final java.lang.Object $tipoMfa = this.getTipoMfa();
        result = result * PRIME + ($tipoMfa == null ? 43 : $tipoMfa.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataValidacao = this.getDataValidacao();
        result = result * PRIME + ($dataValidacao == null ? 43 : $dataValidacao.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $destinatario = this.getDestinatario();
        result = result * PRIME + ($destinatario == null ? 43 : $destinatario.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        return result;
    }
}
