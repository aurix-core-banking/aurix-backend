package com.aurix.platform.platform.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequestDTO {
    @NotBlank(message = "Token é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    private String novaSenha;

    @java.lang.SuppressWarnings("all")
    public String getToken() {
        return this.token;
    }

    @java.lang.SuppressWarnings("all")
    public String getNovaSenha() {
        return this.novaSenha;
    }

    @java.lang.SuppressWarnings("all")
    public void setToken(final String token) {
        this.token = token;
    }

    @java.lang.SuppressWarnings("all")
    public void setNovaSenha(final String novaSenha) {
        this.novaSenha = novaSenha;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ResetPasswordRequestDTO)) return false;
        final ResetPasswordRequestDTO other = (ResetPasswordRequestDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        final java.lang.Object this$novaSenha = this.getNovaSenha();
        final java.lang.Object other$novaSenha = other.getNovaSenha();
        if (this$novaSenha == null ? other$novaSenha != null : !this$novaSenha.equals(other$novaSenha)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ResetPasswordRequestDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        final java.lang.Object $novaSenha = this.getNovaSenha();
        result = result * PRIME + ($novaSenha == null ? 43 : $novaSenha.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ResetPasswordRequestDTO(token=" + this.getToken() + ", novaSenha=" + this.getNovaSenha() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ResetPasswordRequestDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public ResetPasswordRequestDTO(final String token, final String novaSenha) {
        this.token = token;
        this.novaSenha = novaSenha;
    }
}
