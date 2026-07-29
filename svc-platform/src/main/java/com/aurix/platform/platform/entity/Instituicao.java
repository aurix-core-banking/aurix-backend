package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "instituicoes", schema = "aurix", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"}), @UniqueConstraint(columnNames = {"cnpj"})})
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "tenant_id", nullable = false, unique = true, length = 64)
    private String tenantId;
    @NotBlank
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    @Pattern(regexp = "\\d{14}")
    @Column(name = "cnpj", unique = true, length = 14)
    private String cnpj;
    @Column(name = "email_contato", length = 255)
    private String emailContato;
    @Column(name = "telefone_contato", length = 20)
    private String telefoneContato;
    @Enumerated(EnumType.STRING)
    @Column(name = "plano", length = 20)
    private PlanoType plano;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusInstituicao status;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "tenancy", column = @Column(name = "dp_tenancy")), @AttributeOverride(name = "cloud", column = @Column(name = "dp_cloud")), @AttributeOverride(name = "topology", column = @Column(name = "dp_topology"))})
    private DeploymentProfile deploymentProfile;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    public enum PlanoType {
        STARTER, GROWTH, ENTERPRISE;
    }


    public enum StatusInstituicao {
        PENDENTE, ATIVO, SUSPENSO;
    }


    @java.lang.SuppressWarnings("all")
    public static class InstituicaoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String cnpj;
        @java.lang.SuppressWarnings("all")
        private String emailContato;
        @java.lang.SuppressWarnings("all")
        private String telefoneContato;
        @java.lang.SuppressWarnings("all")
        private PlanoType plano;
        @java.lang.SuppressWarnings("all")
        private StatusInstituicao status;
        @java.lang.SuppressWarnings("all")
        private DeploymentProfile deploymentProfile;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        InstituicaoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder cnpj(final String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder emailContato(final String emailContato) {
            this.emailContato = emailContato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder telefoneContato(final String telefoneContato) {
            this.telefoneContato = telefoneContato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder plano(final PlanoType plano) {
            this.plano = plano;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder status(final StatusInstituicao status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder deploymentProfile(final DeploymentProfile deploymentProfile) {
            this.deploymentProfile = deploymentProfile;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Instituicao.InstituicaoBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Instituicao build() {
            return new Instituicao(this.id, this.tenantId, this.nome, this.cnpj, this.emailContato, this.telefoneContato, this.plano, this.status, this.deploymentProfile, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Instituicao.InstituicaoBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", nome=" + this.nome + ", cnpj=" + this.cnpj + ", emailContato=" + this.emailContato + ", telefoneContato=" + this.telefoneContato + ", plano=" + this.plano + ", status=" + this.status + ", deploymentProfile=" + this.deploymentProfile + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Instituicao.InstituicaoBuilder builder() {
        return new Instituicao.InstituicaoBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    @java.lang.SuppressWarnings("all")
    public String getCnpj() {
        return this.cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public String getEmailContato() {
        return this.emailContato;
    }

    @java.lang.SuppressWarnings("all")
    public String getTelefoneContato() {
        return this.telefoneContato;
    }

    @java.lang.SuppressWarnings("all")
    public PlanoType getPlano() {
        return this.plano;
    }

    @java.lang.SuppressWarnings("all")
    public StatusInstituicao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public DeploymentProfile getDeploymentProfile() {
        return this.deploymentProfile;
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
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    @java.lang.SuppressWarnings("all")
    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmailContato(final String emailContato) {
        this.emailContato = emailContato;
    }

    @java.lang.SuppressWarnings("all")
    public void setTelefoneContato(final String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    @java.lang.SuppressWarnings("all")
    public void setPlano(final PlanoType plano) {
        this.plano = plano;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusInstituicao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeploymentProfile(final DeploymentProfile deploymentProfile) {
        this.deploymentProfile = deploymentProfile;
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
    public Instituicao() {
    }

    @java.lang.SuppressWarnings("all")
    public Instituicao(final Long id, final String tenantId, final String nome, final String cnpj, final String emailContato, final String telefoneContato, final PlanoType plano, final StatusInstituicao status, final DeploymentProfile deploymentProfile, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.nome = nome;
        this.cnpj = cnpj;
        this.emailContato = emailContato;
        this.telefoneContato = telefoneContato;
        this.plano = plano;
        this.status = status;
        this.deploymentProfile = deploymentProfile;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
