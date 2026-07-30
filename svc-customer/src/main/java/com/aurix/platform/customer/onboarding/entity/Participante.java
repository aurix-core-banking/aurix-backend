package com.aurix.platform.customer.onboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "participantes", schema = "aurix")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "solicitacao_id", nullable = false)
    private Long solicitacaoId;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoParticipante tipo;
    @Pattern(regexp = "\\d{11}")
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "telefone", length = 20)
    private String telefone;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;
    @Column(name = "qualificacao", length = 100)
    private String qualificacao;
    @Column(name = "percentual_participacao", precision = 5, scale = 2)
    private BigDecimal percentualParticipacao;
    @Column(name = "validado", nullable = false)
    private boolean validado;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    public static class ParticipanteBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Long solicitacaoId;
        @java.lang.SuppressWarnings("all")
        private TipoParticipante tipo;
        @java.lang.SuppressWarnings("all")
        private String cpf;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String email;
        @java.lang.SuppressWarnings("all")
        private String telefone;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataNascimento;
        @java.lang.SuppressWarnings("all")
        private String nacionalidade;
        @java.lang.SuppressWarnings("all")
        private String qualificacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualParticipacao;
        @java.lang.SuppressWarnings("all")
        private boolean validado;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        ParticipanteBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder solicitacaoId(final Long solicitacaoId) {
            this.solicitacaoId = solicitacaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder tipo(final TipoParticipante tipo) {
            this.tipo = tipo;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder cpf(final String cpf) {
            this.cpf = cpf;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder telefone(final String telefone) {
            this.telefone = telefone;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder dataNascimento(final LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder nacionalidade(final String nacionalidade) {
            this.nacionalidade = nacionalidade;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder qualificacao(final String qualificacao) {
            this.qualificacao = qualificacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder percentualParticipacao(final BigDecimal percentualParticipacao) {
            this.percentualParticipacao = percentualParticipacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder validado(final boolean validado) {
            this.validado = validado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante.ParticipanteBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Participante build() {
            return new Participante(this.id, this.solicitacaoId, this.tipo, this.cpf, this.nome, this.email, this.telefone, this.dataNascimento, this.nacionalidade, this.qualificacao, this.percentualParticipacao, this.validado, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Participante.ParticipanteBuilder(id=" + this.id + ", solicitacaoId=" + this.solicitacaoId + ", tipo=" + this.tipo + ", cpf=" + this.cpf + ", nome=" + this.nome + ", email=" + this.email + ", telefone=" + this.telefone + ", dataNascimento=" + this.dataNascimento + ", nacionalidade=" + this.nacionalidade + ", qualificacao=" + this.qualificacao + ", percentualParticipacao=" + this.percentualParticipacao + ", validado=" + this.validado + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Participante.ParticipanteBuilder builder() {
        return new Participante.ParticipanteBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public Long getSolicitacaoId() {
        return this.solicitacaoId;
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
    public boolean isValidado() {
        return this.validado;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setSolicitacaoId(final Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
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

    @java.lang.SuppressWarnings("all")
    public void setValidado(final boolean validado) {
        this.validado = validado;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Participante() {
    }

    @java.lang.SuppressWarnings("all")
    public Participante(final Long id, final Long solicitacaoId, final TipoParticipante tipo, final String cpf, final String nome, final String email, final String telefone, final LocalDate dataNascimento, final String nacionalidade, final String qualificacao, final BigDecimal percentualParticipacao, final boolean validado, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.solicitacaoId = solicitacaoId;
        this.tipo = tipo;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.qualificacao = qualificacao;
        this.percentualParticipacao = percentualParticipacao;
        this.validado = validado;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
