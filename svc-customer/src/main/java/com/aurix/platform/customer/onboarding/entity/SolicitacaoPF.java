package com.aurix.platform.customer.onboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "solicitacoes_pf", schema = "aurix")
public class SolicitacaoPF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "solicitacao_id", nullable = false, unique = true)
    private Long solicitacaoId;
    @Column(name = "tenant_id", length = 64)
    private String tenantId;
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @NotBlank
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "ocupacao", length = 100)
    private String ocupacao;
    @Column(name = "renda_declarada", precision = 15, scale = 2)
    private BigDecimal rendaDeclarada;
    @Column(name = "pep")
    private Boolean pep;
    @Column(name = "score_bureau")
    private Integer scoreBureau;
    @Column(name = "resultado_kyc", length = 50)
    private String resultadoKyc;
    @Column(name = "conta_limitada_ate_kyc")
    private Boolean contaLimitadaAteKyc;


    @java.lang.SuppressWarnings("all")
    public static class SolicitacaoPFBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Long solicitacaoId;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String cpf;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataNascimento;
        @java.lang.SuppressWarnings("all")
        private String ocupacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal rendaDeclarada;
        @java.lang.SuppressWarnings("all")
        private Boolean pep;
        @java.lang.SuppressWarnings("all")
        private Integer scoreBureau;
        @java.lang.SuppressWarnings("all")
        private String resultadoKyc;
        @java.lang.SuppressWarnings("all")
        private Boolean contaLimitadaAteKyc;

        @java.lang.SuppressWarnings("all")
        SolicitacaoPFBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder solicitacaoId(final Long solicitacaoId) {
            this.solicitacaoId = solicitacaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder cpf(final String cpf) {
            this.cpf = cpf;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder dataNascimento(final LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder ocupacao(final String ocupacao) {
            this.ocupacao = ocupacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder rendaDeclarada(final BigDecimal rendaDeclarada) {
            this.rendaDeclarada = rendaDeclarada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder pep(final Boolean pep) {
            this.pep = pep;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder scoreBureau(final Integer scoreBureau) {
            this.scoreBureau = scoreBureau;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder resultadoKyc(final String resultadoKyc) {
            this.resultadoKyc = resultadoKyc;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF.SolicitacaoPFBuilder contaLimitadaAteKyc(final Boolean contaLimitadaAteKyc) {
            this.contaLimitadaAteKyc = contaLimitadaAteKyc;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPF build() {
            return new SolicitacaoPF(this.id, this.solicitacaoId, this.tenantId, this.cpf, this.nome, this.dataNascimento, this.ocupacao, this.rendaDeclarada, this.pep, this.scoreBureau, this.resultadoKyc, this.contaLimitadaAteKyc);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoPF.SolicitacaoPFBuilder(id=" + this.id + ", solicitacaoId=" + this.solicitacaoId + ", tenantId=" + this.tenantId + ", cpf=" + this.cpf + ", nome=" + this.nome + ", dataNascimento=" + this.dataNascimento + ", ocupacao=" + this.ocupacao + ", rendaDeclarada=" + this.rendaDeclarada + ", pep=" + this.pep + ", scoreBureau=" + this.scoreBureau + ", resultadoKyc=" + this.resultadoKyc + ", contaLimitadaAteKyc=" + this.contaLimitadaAteKyc + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SolicitacaoPF.SolicitacaoPFBuilder builder() {
        return new SolicitacaoPF.SolicitacaoPFBuilder();
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
    public String getTenantId() {
        return this.tenantId;
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
    public Boolean getPep() {
        return this.pep;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreBureau() {
        return this.scoreBureau;
    }

    @java.lang.SuppressWarnings("all")
    public String getResultadoKyc() {
        return this.resultadoKyc;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getContaLimitadaAteKyc() {
        return this.contaLimitadaAteKyc;
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
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
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
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @java.lang.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      