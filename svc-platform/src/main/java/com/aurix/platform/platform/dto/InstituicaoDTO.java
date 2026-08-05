package com.aurix.platform.platform.dto;

import com.aurix.platform.platform.entity.DeploymentProfile;
import com.aurix.platform.platform.entity.Instituicao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class InstituicaoDTO {
    private Long id;
    @NotBlank
    private String tenantId;
    @NotBlank
    private String nome;
    @Pattern(regexp = "\\d{14}")
    private String cnpj;
    private String emailContato;
    private String telefoneContato;
    private Instituicao.PlanoType plano;
    private Instituicao.StatusInstituicao status;
    private DeploymentProfile deploymentProfile;
    private java.time.LocalDateTime dataCriacao;

    public static InstituicaoDTO from(Instituicao e) {
        if (e == null) return null;
        return InstituicaoDTO.builder().id(e.getId()).tenantId(e.getTenantId()).nome(e.getNome()).cnpj(e.getCnpj()).emailContato(e.getEmailContato()).telefoneContato(e.getTelefoneContato()).plano(e.getPlano()).status(e.getStatus()).deploymentProfile(e.getDeploymentProfile()).dataCriacao(e.getDataCriacao()).build();
    }


    @java.lang.SuppressWarnings("all")
    public static class InstituicaoDTOBuilder {
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
        private Instituicao.PlanoType plano;
        @java.lang.SuppressWarnings("all")
        private Instituicao.StatusInstituicao status;
        @java.lang.SuppressWarnings("all")
        private DeploymentProfile deploymentProfile;
        @java.lang.SuppressWarnings("all")
        private java.time.LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        InstituicaoDTOBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder cnpj(final String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder emailContato(final String emailContato) {
            this.emailContato = emailContato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder telefoneContato(final String telefoneContato) {
            this.telefoneContato = telefoneContato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder plano(final Instituicao.PlanoType plano) {
            this.plano = plano;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder status(final Instituicao.StatusInstituicao status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder deploymentProfile(final DeploymentProfile deploymentProfile) {
            this.deploymentProfile = deploymentProfile;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO.InstituicaoDTOBuilder dataCriacao(final java.time.LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public InstituicaoDTO build() {
            return new InstituicaoDTO(this.id, this.tenantId, this.nome, this.cnpj, this.emailContato, this.telefoneContato, this.plano, this.status, this.deploymentProfile, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "InstituicaoDTO.InstituicaoDTOBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", nome=" + this.nome + ", cnpj=" + this.cnpj + ", emailContato=" + this.emailContato + ", telefoneContato=" + this.telefoneContato + ", plano=" + this.plano + ", status=" + this.status + ", deploymentProfile=" + this.deploymentProfile + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static InstituicaoDTO.InstituicaoDTOBuilder builder() {
        return new InstituicaoDTO.InstituicaoDTOBuilder();
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
    public Instituicao.PlanoType getPlano() {
        return this.plano;
    }

    @java.lang.SuppressWarnings("all")
    public Instituicao.StatusInstituicao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public DeploymentProfile getDeploymentProfile() {
        return this.deploymentProfile;
    }

    @java.lang.SuppressWarnings("all")
    public java.time.LocalDateTime getDataCriacao() {
        return this.dataCriacao;
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
    public void setPlano(final Instituicao.PlanoType plano) {
        this.plano = plano;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final Instituicao.StatusInstituicao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeploymentProfile(final DeploymentProfile deploymentProfile) {
        this.deploymentProfile = deploymentProfile;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final java.time.LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof InstituicaoDTO)) return false;
        final InstituicaoDTO other = (InstituicaoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tenantId = this.getTenantId();
        final java.lang.Object other$tenantId = other.getTenantId();
        if (this$tenantId == null ? other$tenantId != null : !this$tenantId.equals(other$tenantId)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$cnpj = this.getCnpj();
        final java.lang.Object other$cnpj = other.getCnpj();
        if (this$cnpj == null ? other$cnpj != null : !this$cnpj.equals(other$cnpj)) return false;
        final java.lang.Object this$emailContato = this.getEmailContato();
        final java.lang.Object other$emailContato = other.getEmailContato();
        if (this$emailContato == null ? other$emailContato != null : !this$emailContato.equals(other$emailContato)) return false;
        final java.lang.Object this$telefoneContato = this.getTelefoneContato();
        final java.lang.Object other$telefoneContato = other.getTelefoneContato();
        if (this$telefoneContato == null ? other$telefoneContato != null : !this$telefoneContato.equals(other$telefoneContato)) return false;
        final java.lang.Object this$plano = this.getPlano();
        final java.lang.Object other$plano = other.getPlano();
        if (this$plano == null ? other$plano != null : !this$plano.equals(other$plano)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$deploymentProfile = this.getDeploymentProfile();
        final java.lang.Object other$deploymentProfile = other.getDeploymentProfile();
        if (this$deploymentProfile == null ? other$deploymentProfile != null : !this$deploymentProfile.equals(other$deploymentProfile)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof InstituicaoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tenantId = this.getTenantId();
        result = result * PRIME + ($tenantId == null ? 43 : $tenantId.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $cnpj = this.getCnpj();
        result = result * PRIME + ($cnpj == null ? 43 : $cnpj.hashCode());
        final java.lang.Object $emailContato = this.getEmailContato();
        result = result * PRIME + ($emailContato == null ? 43 : $emailContato.hashCode());
        final java.lang.Object $telefoneContato = this.getTelefoneContato();
        result = result * PRIME + ($telefoneContato == null ? 43 : $telefoneContato.hashCode());
        final java.lang.Object $plano = this.getPlano();
        result = result * PRIME + ($plano == null ? 43 : $plano.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $deploymentProfile = this.getDeploymentProfile();
        result = result * PRIME + ($deploymentProfile == null ? 43 : $deploymentProfile.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "InstituicaoDTO(id=" + this.getId() + ", tenantId=" + this.getTenantId() + ", nome=" + this.getNome() + ", cnpj=" + this.getCnpj() + ", emailContato=" + this.getEmailContato() + ", telefoneContato=" + this.getTelefoneContato() + ", plano=" + this.getPlano() + ", status=" + this.getStatus() + ", deploymentProfile=" + this.getDeploymentProfile() + ", dataCriacao=" + this.getDataCriacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public InstituicaoDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public InstituicaoDTO(final Long id, final String tenantId, final String nome, final String cnpj, final String emailContato, final String telefoneContato, final Instituicao.PlanoType plano, final Instituicao.StatusInstituicao status, final DeploymentProfile deploymentProfile, final java.time.LocalDateTime dataCriacao) {
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
    }
}
