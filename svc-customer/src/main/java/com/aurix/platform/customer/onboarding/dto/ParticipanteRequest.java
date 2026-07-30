package com.aurix.platform.customer.onboarding.dto;

import com.aurix.platform.customer.onboarding.entity.TipoParticipante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ParticipanteRequest {
    @NotNull
    private TipoParticipante tipo;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    @NotBlank
    private String nome;
    private String email;
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
    @Past
    private LocalDate dataNascimento;
    private String nacionalidade;
    private String qualificacao;
    private BigDecimal percentualParticipacao;

    @java.lang.SuppressWarnings("all")
    public ParticipanteRequest() {
    }

    @java.lang.SuppressWarnings("all")
    public TipoParticipante getTipo() {
        return this.tipo;
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
    public String getNacionalidade() {
        return this.nacionalidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getQualificacao() {
        return this.qualificacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualParticipacao() {
        return this.percentualParticipacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipo(final TipoParticipante tipo) {
        this.tipo = tipo;
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
    public void setNacionalidade(final String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setQualificacao(final String qualificacao) {
        this.qualificacao = qualificacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualParticipacao(final BigDecimal percentualParticipacao) {
        this.percentualParticipacao = percentualParticipacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ParticipanteRequest)) return false;
        final ParticipanteRequest other = (ParticipanteRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$tipo = this.getTipo();
        final java.lang.Object other$tipo = other.getTipo();
        if (this$tipo == null ? other$tipo != null : !this$tipo.equals(other$tipo)) return false;
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
        final java.lang.Object this$nacionalidade = this.getNacionalidade();
        final java.lang.Object other$nacionalidade = other.getNacionalidade();
        if (this$nacionalidade == null ? other$nacionalidade != null : !this$nacionalidade.equals(other$nacionalidade)) return false;
        final java.lang.Object this$qualificacao = this.getQualificacao();
        final java.lang.Object other$qualificacao = other.getQualificacao();
        if (this$qualificacao == null ? other$qualificacao != null : !this$qualificacao.equals(other$qualificacao)) return false;
        final java.lang.Object this$percentualParticipacao = this.getPercentualParticipacao();
        final java.lang.Object other$percentualParticipacao = other.getPercentualParticipacao();
        if (this$percentualParticipacao == null ? other$percentualParticipacao != null : !this$percentualParticipacao.equals(other$percentualParticipacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ParticipanteRequest;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $tipo = this.getTipo();
        result = result * PRIME + ($tipo == null ? 43 : $tipo.hashCode());
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
        final java.lang.Object $nacionalidade = this.getNacionalidade();
        result = result * PRIME + ($nacionalidade == null ? 43 : $nacionalidade.hashCode());
        final java.lang.Object $qualificacao = this.getQualificacao();
        result = result * PRIME + ($qualificacao == null ? 43 : $qualificacao.hashCode());
        final java.lang.Object $percentualParticipacao = this.getPercentualParticipacao();
        result = result * PRIME + ($percentualParticipacao == null ? 43 : $percentualParticipacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ParticipanteRequest(tipo=" + this.getTipo() + ", cpf=" + this.getCpf() + ", nome=" + this.getNome() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", dataNascimento=" + this.getDataNascimento() + ", nacionalidade=" + this.getNacionalidade() + ", qualificacao=" + this.getQualificacao() + ", percentualParticipacao=" + this.getPercentualParticipacao() + ")";
    }
}
