package com.aurix.platform.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para requisição de login.
 */
public class LoginRequestDTO {
    /**
     * Email do usuário.
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    /**
     * Senha do usuário.
     */
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    /**
     * Email do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Senha do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getSenha() {
        return this.senha;
    }

    /**
     * Email do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Senha do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setSenha(final String senha) {
        this.senha = senha;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginRequestDTO)) return false;
        final LoginRequestDTO other = (LoginRequestDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$senha = this.getSenha();
        final java.lang.Object other$senha = other.getSenha();
        if (this$senha == null ? other$senha != null : !this$senha.equals(other$senha)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LoginRequestDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $senha = this.getSenha();
        result = result * PRIME + ($senha == null ? 43 : $senha.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LoginRequestDTO(email=" + this.getEmail() + ", senha=" + this.getSenha() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LoginRequestDTO() {
    }

    /**
     * Creates a new {@code LoginRequestDTO} instance.
     *
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public LoginRequestDTO(final String email, final String senha) {
        this.email = email;
        this.senha = senha;
    }
}
