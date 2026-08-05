package com.aurix.platform.platform.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens", schema = "aurix")
public class PasswordResetToken extends BaseEntity {
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(nullable = false)
    private Boolean utilizado = false;

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
    public Boolean getUtilizado() {
        return this.utilizado;
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
    public void setUtilizado(final Boolean utilizado) {
        this.utilizado = utilizado;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PasswordResetToken(id=" + this.getId() + ", usuarioId=" + this.getUsuarioId() + ", token=" + this.getToken() + ", expiraEm=" + this.getExpiraEm() + ", utilizado=" + this.getUtilizado() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PasswordResetToken() {
    }

    @java.lang.SuppressWarnings("all")
    public PasswordResetToken(final Long usuarioId, final String token, final LocalDateTime expiraEm) {
        this.usuarioId = usuarioId;
        this.token = token;
        this.expiraEm = expiraEm;
        this.utilizado = false;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PasswordResetToken)) return false;
        final PasswordResetToken other = (PasswordResetToken) o;
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
        final java.lang.Object this$utilizado = this.getUtilizado();
        final java.lang.Object other$utilizado = other.getUtilizado();
        if (this$utilizado == null ? other$utilizado != null : !this$utilizado.equals(other$utilizado)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PasswordResetToken;
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
        final java.lang.Object $utilizado = this.getUtilizado();
        result = result * PRIME + ($utilizado == null ? 43 : $utilizado.hashCode());
        return result;
    }
}
