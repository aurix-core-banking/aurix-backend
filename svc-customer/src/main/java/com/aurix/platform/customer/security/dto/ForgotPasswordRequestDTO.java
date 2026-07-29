package com.aurix.platform.customer.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequestDTO {
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ForgotPasswordRequestDTO)) return false;
        final ForgotPasswordRequestDTO other = (ForgotPasswordRequestDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ForgotPasswordRequestDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ForgotPasswordRequestDTO(email=" + this.getEmail() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ForgotPasswordRequestDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public ForgotPasswordRequestDTO(final String email) {
        this.email = email;
    }
}
