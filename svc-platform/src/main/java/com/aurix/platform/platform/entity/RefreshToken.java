package com.aurix.platform.platform.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens", schema = "aurix")
public class RefreshToken extends BaseEntity {
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(nullable = false)
    private Boolean revogado = false;

    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public String getToken() {
        return this.token;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getExpiraEm() {
        return this.expiraEm;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRevogado() {
        return this.revogado;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public void setToken(final String token) {
        this.token = token;
    }

    @java.lang.SuppressWarnings("all")
    public void setExpiraEm(final LocalDateTime expiraEm) {
        this.expiraEm = expiraEm;
    }

    @java.lang.SuppressWarnings("all")
    public void setRevogado(final Boolean revogado) {
        this.revogado = revogado;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "RefreshToken(id=" + this.getId() + ", usuarioId=" + this.getUsuarioId() + ", token=" + this.getToken() + ", expiraEm=" + this.getExpiraEm() + ", revogado=" + this.getRevogado() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RefreshToken() {
    }

    @java.lang.SuppressWarnings("all")
    public RefreshToken(final Long usuarioId, final String token, final LocalDateTime expiraEm) {
        this.usuarioId = usuarioId;
        this.token = token;
        this.expiraEm = expiraEm;
        this.revogado = false;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RefreshToken)) return false;
        final RefreshToken other = (RefreshToken) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        final java.lang.Object this$expiraEm = this.getExpiraEm();
        final java.lang.Object other$expiraEm = other.getExpiraEm();
        if (this$expiraEm == null ? other$expiraEm != null : !this$expiraEm.equals(other$expiraEm)) return false;
        final java.lang.Object this$revogado = this.getRevogado();
        final java.lang.Object other$revogado = other.getRevogado();
        if (this$revogado == null ? other$revogado != null : !this$revogado.equals(other$revogado)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof RefreshToken;
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
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        final java.lang.Object $expiraEm = this.getExpiraEm();
        result = result * PRIME + ($expiraEm == null ? 43 : $expiraEm.hashCode());
        final java.lang.Object $revogado = this.getRevogado();
        result = result * PRIME + ($revogado == null ? 43 : $revogado.hashCode());
        return result;
    }
}
