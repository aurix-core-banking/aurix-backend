package com.aurix.platform.customer.onboarding.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "empresas", schema = "aurix")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "solicitacao_id", nullable = false, unique = true)
    private Long solicitacaoId;
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;
    @Column(name = "razao_social", nullable = false, length = 255)
    private String razaoSocial;
    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;
    @Column(name = "cnae_principal", length = 20)
    private String cnaePrincipal;
    @Column(name = "cnae_secundarios", columnDefinition = "TEXT")
    private String cnaeSecundarios;
    @Column(name = "endereco", columnDefinition = "TEXT")
    private String endereco;
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_cadastral", length = 10)
    private SituacaoCNPJ situacaoCadastral;
    @Column(name = "data_situacao")
    private LocalDate dataSituacao;
    @Column(name = "regime_tributario", length = 50)
    private String regimeTributario;
    @Column(name = "dados_abertos", columnDefinition = "TEXT")
    private String dadosAbertos;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    public static class EmpresaBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Long solicitacaoId;
        @java.lang.SuppressWarnings("all")
        private String cnpj;
        @java.lang.SuppressWarnings("all")
        private String razaoSocial;
        @java.lang.SuppressWarnings("all")
        private String nomeFantasia;
        @java.lang.SuppressWarnings("all")
        private String cnaePrincipal;
        @java.lang.SuppressWarnings("all")
        private String cnaeSecundarios;
        @java.lang.SuppressWarnings("all")
        private String endereco;
        @java.lang.SuppressWarnings("all")
        private SituacaoCNPJ situacaoCadastral;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataSituacao;
        @java.lang.SuppressWarnings("all")
        private String regimeTributario;
        @java.lang.SuppressWarnings("all")
        private String dadosAbertos;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        EmpresaBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder solicitacaoId(final Long solicitacaoId) {
            this.solicitacaoId = solicitacaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder cnpj(final String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder razaoSocial(final String razaoSocial) {
            this.razaoSocial = razaoSocial;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder nomeFantasia(final String nomeFantasia) {
            this.nomeFantasia = nomeFantasia;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder cnaePrincipal(final String cnaePrincipal) {
            this.cnaePrincipal = cnaePrincipal;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder cnaeSecundarios(final String cnaeSecundarios) {
            this.cnaeSecundarios = cnaeSecundarios;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder endereco(final String endereco) {
            this.endereco = endereco;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder situacaoCadastral(final SituacaoCNPJ situacaoCadastral) {
            this.situacaoCadastral = situacaoCadastral;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder dataSituacao(final LocalDate dataSituacao) {
            this.dataSituacao = dataSituacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder regimeTributario(final String regimeTributario) {
            this.regimeTributario = regimeTributario;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder dadosAbertos(final String dadosAbertos) {
            this.dadosAbertos = dadosAbertos;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa.EmpresaBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Empresa build() {
            return new Empresa(this.id, this.solicitacaoId, this.cnpj, this.razaoSocial, this.nomeFantasia, this.cnaePrincipal, this.cnaeSecundarios, this.endereco, this.situacaoCadastral, this.dataSituacao, this.regimeTributario, this.dadosAbertos, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Empresa.EmpresaBuilder(id=" + this.id + ", solicitacaoId=" + this.solicitacaoId + ", cnpj=" + this.cnpj + ", razaoSocial=" + this.razaoSocial + ", nomeFantasia=" + this.nomeFantasia + ", cnaePrincipal=" + this.cnaePrincipal + ", cnaeSecundarios=" + this.cnaeSecundarios + ", endereco=" + this.endereco + ", situacaoCadastral=" + this.situacaoCadastral + ", dataSituacao=" + this.dataSituacao + ", regimeTributario=" + this.regimeTributario + ", dadosAbertos=" + this.dadosAbertos + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Empresa.EmpresaBuilder builder() {
        return new Empresa.EmpresaBuilder();
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
    public String getCnaePrincipal() {
        return this.cnaePrincipal;
    }

    @java.lang.SuppressWarnings("all")
    public String getCnaeSecundarios() {
        return this.cnaeSecundarios;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndereco() {
        return this.endereco;
    }

    @java.lang.SuppressWarnings("all")
    public SituacaoCNPJ getSituacaoCadastral() {
        return this.situacaoCadastral;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataSituacao() {
        return this.dataSituacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegimeTributario() {
        return this.regimeTributario;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAbertos() {
        return this.dadosAbertos;
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
    public void setCnaePrincipal(final String cnaePrincipal) {
        this.cnaePrincipal = cnaePrincipal;
    }

    @java.lang.SuppressWarnings("all")
    public void setCnaeSecundarios(final String cnaeSecundarios) {
        this.cnaeSecundarios = cnaeSecundarios;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    @java.lang.SuppressWarnings("all")
    public void setSituacaoCadastral(final SituacaoCNPJ situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSituacao(final LocalDate dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegimeTributario(final String regimeTributario) {
        this.regimeTributario = regimeTributario;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAbertos(final String dadosAbertos) {
        this.dadosAbertos = dadosAbertos;
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
    public Empresa() {
    }

    @java.lang.SuppressWarnings("all")
    public Empresa(final Long id, final Long solicitacaoId, final String cnpj, final String razaoSocial, final String nomeFantasia, final String cnaePrincipal, final String cnaeSecundarios, final String endereco, final SituacaoCNPJ situacaoCadastral, final LocalDate dataSituacao, final String regimeTributario, final String dadosAbertos, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.solicitacaoId = solicitacaoId;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnaePrincipal = cnaePrincipal;
        this.cnaeSecundarios = cnaeSecundarios;
        this.endereco = endereco;
        this.situacaoCadastral = situacaoCadastral;
        this.dataSituacao = dataSituacao;
        this.regimeTributario = regimeTributario;
        this.dadosAbertos = dadosAbertos;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
