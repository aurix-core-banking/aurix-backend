package com.aurix.platform.customer.security.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mfa_configs", schema = "aurix")
public class MfaConfig extends BaseEntity {
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMfa tipoMfa;
    @Column(nullable = false)
    private Boolean ativo = false;
    @Column(length = 200)
    private String valorConfigurado;
    @Column(name = "codigo_backup", length = 100)
    private String codigoBackup;
    @Column
    private LocalDateTime dataConfiguracao;
    @Column
    private LocalDateTime dataUltimoUso;
    @Column
    private Integer tentativasFalhas = 0;
    @Column
    private Boolean bloqueado = false;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadata;


    public enum TipoMfa {
        SMS, EMAIL, APP_AUTHENTICATOR, BIOMETRIA, TOKEN_HARDWARE, BACKUP_CODE;
    }

@java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public TipoMfa getTipoMfa() {
        return this.tipoMfa;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public String getValorConfigurado() {
        return this.valorConfigurado;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBackup() {
        return this.codigoBackup;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConfiguracao() {
        return this.dataConfiguracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataUltimoUso() {
        return this.dataUltimoUso;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasFalhas() {
        return this.tentativasFalhas;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBloqueado() {
        return this.bloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
    }

@java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoMfa(final TipoMfa tipoMfa) {
        this.tipoMfa = tipoMfa;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorConfigurado(final String valorConfigurado) {
        this.valorConfigurado = valorConfigurado;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBackup(final String codigoBackup) {
        this.codigoBackup = codigoBackup;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConfiguracao(final LocalDateTime dataConfiguracao) {
        this.dataConfiguracao = dataConfiguracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUltimoUso(final LocalDateTime dataUltimoUso) {
        this.dataUltimoUso = dataUltimoUso;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasFalhas(final Integer tentativasFalhas) {
        this.tentativasFalhas = tentativasFalhas;
    }

    @java.lang.SuppressWarnings("all")
    public void setBloqueado(final Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "MfaConfig(id=" + this.getId() + ", usuarioId=" + this.getUsuarioId() + ", tipoMfa=" + this.getTipoMfa() + ", ativo=" + this.getAtivo() + ", valorConfigurado=" + this.getValorConfigurado() + ", codigoBackup=" + this.getCodigoBackup() + ", dataConfiguracao=" + this.getDataConfiguracao() + ", dataUltimoUso=" + this.getDataUltimoUso() + ", tentativasFalhas=" + this.getTentativasFalhas() + ", bloqueado=" + this.getBloqueado() + ", metadata=" + this.getMetadata() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public MfaConfig() {
    }

    @java.lang.SuppressWarnings("all")
    public MfaConfig(final Long id, final Long usuarioId, final TipoMfa tipoMfa, final Boolean ativo, final String valorConfigurado, final String codigoBackup, final LocalDateTime dataConfiguracao, final LocalDateTime dataUltimoUso, final Integer tentativasFalhas, final Boolean bloqueado, final String metadata) {
        this.setId(id);
        this.usuarioId = usuarioId;
        this.tipoMfa = tipoMfa;
        this.ativo = ativo;
        this.valorConfigurado = valorConfigurado;
        this.codigoBackup = codigoBackup;
        this.dataConfiguracao = dataConfiguracao;
        this.dataUltimoUso = dataUltimoUso;
        this.tentativasFalhas = tentativasFalhas;
        this.bloqueado = bloqueado;
        this.metadata = metadata;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MfaConfig)) return false;
        final MfaConfig other = (MfaConfig) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$tentativasFalhas = this.getTentativasFalhas();
        final java.lang.Object other$tentativasFalhas = other.getTentativasFalhas();
        if (this$tentativasFalhas == null ? other$tentativasFalhas != null : !this$tentativasFalhas.equals(other$tentativasFalhas)) return false;
        final java.lang.Object this$bloqueado = this.getBloqueado();
        final java.lang.Object other$bloqueado = other.getBloqueado();
        if (this$bloqueado == null ? other$bloqueado != null : !this$bloqueado.equals(other$bloqueado)) return false;
        final java.lang.Object this$tipoMfa = this.getTipoMfa();
        final java.lang.Object other$tipoMfa = other.getTipoMfa();
        if (this$tipoMfa == null ? other$tipoMfa != null : !this$tipoMfa.equals(other$tipoMfa)) return false;
        final java.lang.Object this$valorConfigurado = this.getValorConfigurado();
        final java.lang.Object other$valorConfigurado = other.getValorConfigurado();
        if (this$valorConfigurado == null ? other$valorConfigurado != null : !this$valorConfigurado.equals(other$valorConfigurado)) return false;
        final java.lang.Object this$codigoBackup = this.getCodigoBackup();
        final java.lang.Object other$codigoBackup = other.getCodigoBackup();
        if (this$codigoBackup == null ? other$codigoBackup != null : !this$codigoBackup.equals(other$codigoBackup)) return false;
        final java.lang.Object this$dataConfiguracao = this.getDataConfiguracao();
        final java.lang.Object other$dataConfiguracao = other.getDataConfiguracao();
        if (this$dataConfiguracao == null ? other$dataConfiguracao != null : !this$dataConfiguracao.equals(other$dataConfiguracao)) return false;
        final java.lang.Object this$dataUltimoUso = this.getDataUltimoUso();
        final java.lang.Object other$dataUltimoUso = other.getDataUltimoUso();
        if (this$dataUltimoUso == null ? other$dataUltimoUso != null : !this$dataUltimoUso.equals(other$dataUltimoUso)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof MfaConfig;
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
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $tentativasFalhas = this.getTentativasFalhas();
        result = result * PRIME + ($tentativasFalhas == null ? 43 : $tentativasFalhas.hashCode());
        final java.lang.Object $bloqueado = this.getBloqueado();
        result = result * PRIME + ($bloqueado == null ? 43 : $bloqueado.hashCode());
        final java.lang.Object $tipoMfa = this.getTipoMfa();
        result = result * PRIME + ($tipoMfa == null ? 43 : $tipoMfa.hashCode());
        final java.lang.Object $valorConfigurado = this.getValorConfigurado();
        result = result * PRIME + ($valorConfigurado == null ? 43 : $valorConfigurado.hashCode());
        final java.lang.Object $codigoBackup = this.getCodigoBackup();
        result = result * PRIME + ($codigoBackup == null ? 43 : $codigoBackup.hashCode());
        final java.lang.Object $dataConfiguracao = this.getDataConfiguracao();
        result = result * PRIME + ($dataConfiguracao == null ? 43 : $dataConfiguracao.hashCode());
        final java.lang.Object $dataUltimoUso = this.getDataUltimoUso();
        result = result * PRIME + ($dataUltimoUso == null ? 43 : $dataUltimoUso.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        return result;
    }
}
