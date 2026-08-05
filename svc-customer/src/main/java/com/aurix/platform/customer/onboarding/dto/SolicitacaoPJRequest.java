package com.aurix.platform.customer.onboarding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SolicitacaoPJRequest {
    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    private String nomeFantasia;
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
    private String endereco;

    @java.lang.SuppressWarnings("all")
    public SolicitacaoPJRequest() {
    }

    @java.lang.SuppressWarnings("all")
    public String getCnpj() {
        return this.cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    @java.lang.SuppressWarnings("all")
    public String getTelefone() {
        return this.telefone;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndereco() {
        return this.endereco;
    }

    @java.lang.SuppressWarnings("all")
    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public void setRazaoSocial(final String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeFantasia(final String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    @java.lang.SuppressWarnings("all")
    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoPJRequest)) return false;
        final SolicitacaoPJRequest other = (SolicitacaoPJRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$cnpj = this.getCnpj();
        final java.lang.Object other$cnpj = other.getCnpj();
        if (this$cnpj == null ? other$cnpj != null : !this$cnpj.equals(other$cnpj)) return false;
        final java.lang.Object this$razaoSocial = this.getRazaoSocial();
        final java.lang.Object other$razaoSocial = other.getRazaoSocial();
        if (this$razaoSocial == null ? other$razaoSocial != null : !this$razaoSocial.equals(other$razaoSocial)) return false;
        final java.lang.Object this$nomeFantasia = this.getNomeFantasia();
        final java.lang.Object other$nomeFantasia = other.getNomeFantasia();
        if (this$nomeFantasia == null ? other$nomeFantasia != null : !this$nomeFantasia.equals(other$nomeFantasia)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$telefone = this.getTelefone();
        final java.lang.Object other$telefone = other.getTelefone();
        if (this$telefone == null ? other$telefone != null : !this$telefone.equals(other$telefone)) return false;
        final java.lang.Object this$endereco = this.getEndereco();
        final java.lang.Object other$endereco = other.getEndereco();
        if (this$endereco == null ? other$endereco != null : !this$endereco.equals(other$endereco)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SolicitacaoPJRequest;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $cnpj = this.getCnpj();
        result = result * PRIME + ($cnpj == null ? 43 : $cnpj.hashCode());
        final java.lang.Object $razaoSocial = this.getRazaoSocial();
        result = result * PRIME + ($razaoSocial == null ? 43 : $razaoSocial.hashCode());
        final java.lang.Object $nomeFantasia = this.getNomeFantasia();
        result = result * PRIME + ($nomeFantasia == null ? 43 : $nomeFantasia.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $telefone = this.getTelefone();
        result = result * PRIME + ($telefone == null ? 43 : $telefone.hashCode());
        final java.lang.Object $endereco = this.getEndereco();
        result = result * PRIME + ($endereco == null ? 43 : $endereco.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SolicitacaoPJRequest(cnpj=" + this.getCnpj() + ", razaoSocial=" + this.getRazaoSocial() + ", nomeFantasia=" + this.getNomeFantasia() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", endereco=" + this.getEndereco() + ")";
    }
}
