package com.aurix.platform.customer.onboarding.dto;

import com.aurix.platform.customer.onboarding.entity.TipoPessoa;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SolicitacaoContaRequest {
    @NotNull
    private TipoPessoa tipoPessoa = TipoPessoa.FISICA;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    @NotBlank
    @Size(min = 2, max = 255)
    private String nome;
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
    private LocalDate dataNascimento;
    @Size(max = 100)
    private String ocupacao;
    @DecimalMin("0")
    private BigDecimal rendaDeclarada;
    private String endereco;

    @java.lang.SuppressWarnings("all")
    public SolicitacaoContaRequest() {
        this.tipoPessoa = TipoPessoa.FISICA;
    }

    @java.lang.SuppressWarnings("all")
    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    @java.lang.SuppressWarnings("all")
    public String getCpf() {
        return this.cpf;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
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
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getOcupacao() {
        return this.ocupacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRendaDeclarada() {
        return this.rendaDeclarada;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndereco() {
        return this.endereco;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoPessoa(final TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @java.lang.SuppressWarnings("all")
    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
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
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setOcupacao(final String ocupacao) {
        this.ocupacao = ocupacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRendaDeclarada(final BigDecimal rendaDeclarada) {
        this.rendaDeclarada = rendaDeclarada;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoContaRequest)) return false;
        final SolicitacaoContaRequest other = (SolicitacaoContaRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$tipoPessoa = this.getTipoPessoa();
        final java.lang.Object other$tipoPessoa = other.getTipoPessoa();
        if (this$tipoPessoa == null ? other$tipoPessoa != null : !this$tipoPessoa.equals(other$tipoPessoa)) return false;
        final java.lang.Object this$cpf = this.getCpf();
        final java.lang.Object other$cpf = other.getCpf();
        if (this$cpf == null ? other$cpf != null : !this$cpf.equals(other$cpf)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$telefone = this.getTelefone();
        final java.lang.Object other$telefone = other.getTelefone();
        if (this$telefone == null ? other$telefone != null : !this$telefone.equals(other$telefone)) return false;
        final java.lang.Object this$dataNascimento = this.getDataNascimento();
        final java.lang.Object other$dataNascimento = other.getDataNascimento();
        if (this$dataNascimento == null ? other$dataNascimento != null : !this$dataNascimento.equals(other$dataNascimento)) return false;
        final java.lang.Object this$ocupacao = this.getOcupacao();
        final java.lang.Object other$ocupacao = other.getOcupacao();
        if (this$ocupacao == null ? other$ocupacao != null : !this$ocupacao.equals(other$ocupacao)) return false;
        final java.lang.Object this$rendaDeclarada = this.getRendaDeclarada();
        final java.lang.Object other$rendaDeclarada = other.getRendaDeclarada();
        if (this$rendaDeclarada == null ? other$rendaDeclarada != null : !this$rendaDeclarada.equals(other$rendaDeclarada)) return false;
        final java.lang.Object this$endereco = this.getEndereco();
        final java.lang.Object other$endereco = other.getEndereco();
        if (this$endereco == null ? other$endereco != null : !this$endereco.equals(other$endereco)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SolicitacaoContaRequest;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $tipoPessoa = this.getTipoPessoa();
        result = result * PRIME + ($tipoPessoa == null ? 43 : $tipoPessoa.hashCode());
        final java.lang.Object $cpf = this.getCpf();
        result = result * PRIME + ($cpf == null ? 43 : $cpf.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $telefone = this.getTelefone();
        result = result * PRIME + ($telefone == null ? 43 : $telefone.hashCode());
        final java.lang.Object $dataNascimento = this.getDataNascimento();
        result = result * PRIME + ($dataNascimento == null ? 43 : $dataNascimento.hashCode());
        final java.lang.Object $ocupacao = this.getOcupacao();
        result = result * PRIME + ($ocupacao == null ? 43 : $ocupacao.hashCode());
        final java.lang.Object $rendaDeclarada = this.getRendaDeclarada();
        result = result * PRIME + ($rendaDeclarada == null ? 43 : $rendaDeclarada.hashCode());
        final java.lang.Object $endereco = this.getEndereco();
        result = result * PRIME + ($endereco == null ? 43 : $endereco.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SolicitacaoContaRequest(tipoPessoa=" + this.getTipoPessoa() + ", cpf=" + this.getCpf() + ", nome=" + this.getNome() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", dataNascimento=" + this.getDataNascimento() + ", ocupacao=" + this.getOcupacao() + ", rendaDeclarada=" + this.getRendaDeclarada() + ", endereco=" + this.getEndereco() + ")";
    }
}
