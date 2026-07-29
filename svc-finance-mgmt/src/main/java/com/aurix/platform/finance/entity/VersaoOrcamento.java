package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma versão de orçamento
 * 
 * Gerencia controle de versões e histórico de orçamentos
 */
@Entity
@Table(name = "versoes_orcamento", schema = "aurix")
public class VersaoOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;
    @Column(name = "numero_versao", nullable = false)
    private Integer numeroVersao;
    @Column(name = "descricao_versao", length = 500)
    private String descricaoVersao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_versao", nullable = false)
    private StatusVersao statusVersao;
    @Column(name = "data_versao", nullable = false)
    private LocalDateTime dataVersao;
    @Column(name = "criado_por", length = 100, nullable = false)
    private String criadoPor;
    @Column(name = "aprovado_por", length = 100)
    private String aprovadoPor;
    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;
    @Column(name = "motivo_alteracao", length = 1000)
    private String motivoAlteracao;
    @Column(name = "alteracoes_realizadas", length = 2000)
    private String alteracoesRealizadas;
    @Column(name = "impacto_financeiro", precision = 15, scale = 2)
    private java.math.BigDecimal impactoFinanceiro;
    @Column(name = "percentual_alteracao", precision = 8, scale = 4)
    private java.math.BigDecimal percentualAlteracao;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    @Column(name = "versao", nullable = false)
    @Version
    private Long versao;


    /**
     * Status da versão
     */
    public enum StatusVersao {
        RASCUNHO,  // Rascunho
        APROVACAO,  // Em aprovação
        APROVADA,  // Aprovada
        REJEITADA,  // Rejeitada
        ATIVA,  // Ativa
        ARQUIVADA // Arquivada
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class VersaoOrcamentoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Orcamento orcamento;
        @java.lang.SuppressWarnings("all")
        private Integer numeroVersao;
        @java.lang.SuppressWarnings("all")
        private String descricaoVersao;
        @java.lang.SuppressWarnings("all")
        private StatusVersao statusVersao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataVersao;
        @java.lang.SuppressWarnings("all")
        private String criadoPor;
        @java.lang.SuppressWarnings("all")
        private String aprovadoPor;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAprovacao;
        @java.lang.SuppressWarnings("all")
        private String motivoAlteracao;
        @java.lang.SuppressWarnings("all")
        private String alteracoesRealizadas;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal impactoFinanceiro;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal percentualAlteracao;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        VersaoOrcamentoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder orcamento(final Orcamento orcamento) {
            this.orcamento = orcamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder numeroVersao(final Integer numeroVersao) {
            this.numeroVersao = numeroVersao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder descricaoVersao(final String descricaoVersao) {
            this.descricaoVersao = descricaoVersao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder statusVersao(final StatusVersao statusVersao) {
            this.statusVersao = statusVersao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder dataVersao(final LocalDateTime dataVersao) {
            this.dataVersao = dataVersao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder criadoPor(final String criadoPor) {
            this.criadoPor = criadoPor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder aprovadoPor(final String aprovadoPor) {
            this.aprovadoPor = aprovadoPor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder dataAprovacao(final LocalDateTime dataAprovacao) {
            this.dataAprovacao = dataAprovacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder motivoAlteracao(final String motivoAlteracao) {
            this.motivoAlteracao = motivoAlteracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder alteracoesRealizadas(final String alteracoesRealizadas) {
            this.alteracoesRealizadas = alteracoesRealizadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder impactoFinanceiro(final java.math.BigDecimal impactoFinanceiro) {
            this.impactoFinanceiro = impactoFinanceiro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder percentualAlteracao(final java.math.BigDecimal percentualAlteracao) {
            this.percentualAlteracao = percentualAlteracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento.VersaoOrcamentoBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public VersaoOrcamento build() {
            return new VersaoOrcamento(this.id, this.orcamento, this.numeroVersao, this.descricaoVersao, this.statusVersao, this.dataVersao, this.criadoPor, this.aprovadoPor, this.dataAprovacao, this.motivoAlteracao, this.alteracoesRealizadas, this.impactoFinanceiro, this.percentualAlteracao, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "VersaoOrcamento.VersaoOrcamentoBuilder(id=" + this.id + ", orcamento=" + this.orcamento + ", numeroVersao=" + this.numeroVersao + ", descricaoVersao=" + this.descricaoVersao + ", statusVersao=" + this.statusVersao + ", dataVersao=" + this.dataVersao + ", criadoPor=" + this.criadoPor + ", aprovadoPor=" + this.aprovadoPor + ", dataAprovacao=" + this.dataAprovacao + ", motivoAlteracao=" + this.motivoAlteracao + ", alteracoesRealizadas=" + this.alteracoesRealizadas + ", impactoFinanceiro=" + this.impactoFinanceiro + ", percentualAlteracao=" + this.percentualAlteracao + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static VersaoOrcamento.VersaoOrcamentoBuilder builder() {
        return new VersaoOrcamento.VersaoOrcamentoBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public Orcamento getOrcamento() {
        return this.orcamento;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNumeroVersao() {
        return this.numeroVersao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoVersao() {
        return this.descricaoVersao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusVersao getStatusVersao() {
        return this.statusVersao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVersao() {
        return this.dataVersao;
    }

    @java.lang.SuppressWarnings("all")
    public String getCriadoPor() {
        return this.criadoPor;
    }

    @java.lang.SuppressWarnings("all")
    public String getAprovadoPor() {
        return this.aprovadoPor;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoAlteracao() {
        return this.motivoAlteracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getAlteracoesRealizadas() {
        return this.alteracoesRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public java.math.BigDecimal getImpactoFinanceiro() {
        return this.impactoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public java.math.BigDecimal getPercentualAlteracao() {
        return this.percentualAlteracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
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
    public Long getVersao() {
        return this.versao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setOrcamento(final Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroVersao(final Integer numeroVersao) {
        this.numeroVersao = numeroVersao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoVersao(final String descricaoVersao) {
        this.descricaoVersao = descricaoVersao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatusVersao(final StatusVersao statusVersao) {
        this.statusVersao = statusVersao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVersao(final LocalDateTime dataVersao) {
        this.dataVersao = dataVersao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCriadoPor(final String criadoPor) {
        this.criadoPor = criadoPor;
    }

    @java.lang.SuppressWarnings("all")
    public void setAprovadoPor(final String aprovadoPor) {
        this.aprovadoPor = aprovadoPor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoAlteracao(final String motivoAlteracao) {
        this.motivoAlteracao = motivoAlteracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setAlteracoesRealizadas(final String alteracoesRealizadas) {
        this.alteracoesRealizadas = alteracoesRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setImpactoFinanceiro(final java.math.BigDecimal impactoFinanceiro) {
        this.impactoFinanceiro = impactoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualAlteracao(final java.math.BigDecimal percentualAlteracao) {
        this.percentualAlteracao = percentualAlteracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
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
    public void setVersao(final Long versao) {
        this.versao = versao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof VersaoOrcamento)) return false;
        final VersaoOrcamento other = (VersaoOrcamento) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$numeroVersao = this.getNumeroVersao();
        final java.lang.Object other$numeroVersao = other.getNumeroVersao();
        if (this$numeroVersao == null ? other$numeroVersao != null : !this$numeroVersao.equals(other$numeroVersao)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$orcamento = this.getOrcamento();
        final java.lang.Object other$orcamento = other.getOrcamento();
        if (this$orcamento == null ? other$orcamento != null : !this$orcamento.equals(other$orcamento)) return false;
        final java.lang.Object this$descricaoVersao = this.getDescricaoVersao();
        final java.lang.Object other$descricaoVersao = other.getDescricaoVersao();
        if (this$descricaoVersao == null ? other$descricaoVersao != null : !this$descricaoVersao.equals(other$descricaoVersao)) return false;
        final java.lang.Object this$statusVersao = this.getStatusVersao();
        final java.lang.Object other$statusVersao = other.getStatusVersao();
        if (this$statusVersao == null ? other$statusVersao != null : !this$statusVersao.equals(other$statusVersao)) return false;
        final java.lang.Object this$dataVersao = this.getDataVersao();
        final java.lang.Object other$dataVersao = other.getDataVersao();
        if (this$dataVersao == null ? other$dataVersao != null : !this$dataVersao.equals(other$dataVersao)) return false;
        final java.lang.Object this$criadoPor = this.getCriadoPor();
        final java.lang.Object other$criadoPor = other.getCriadoPor();
        if (this$criadoPor == null ? other$criadoPor != null : !this$criadoPor.equals(other$criadoPor)) return false;
        final java.lang.Object this$aprovadoPor = this.getAprovadoPor();
        final java.lang.Object other$aprovadoPor = other.getAprovadoPor();
        if (this$aprovadoPor == null ? other$aprovadoPor != null : !this$aprovadoPor.equals(other$aprovadoPor)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        final java.lang.Object this$motivoAlteracao = this.getMotivoAlteracao();
        final java.lang.Object other$motivoAlteracao = other.getMotivoAlteracao();
        if (this$motivoAlteracao == null ? other$motivoAlteracao != null : !this$motivoAlteracao.equals(other$motivoAlteracao)) return false;
        final java.lang.Object this$alteracoesRealizadas = this.getAlteracoesRealizadas();
        final java.lang.Object other$alteracoesRealizadas = other.getAlteracoesRealizadas();
        if (this$alteracoesRealizadas == null ? other$alteracoesRealizadas != null : !this$alteracoesRealizadas.equals(other$alteracoesRealizadas)) return false;
        final java.lang.Object this$impactoFinanceiro = this.getImpactoFinanceiro();
        final java.lang.Object other$impactoFinanceiro = other.getImpactoFinanceiro();
        if (this$impactoFinanceiro == null ? other$impactoFinanceiro != null : !this$impactoFinanceiro.equals(other$impactoFinanceiro)) return false;
        final java.lang.Object this$percentualAlteracao = this.getPercentualAlteracao();
        final java.lang.Object other$percentualAlteracao = other.getPercentualAlteracao();
        if (this$percentualAlteracao == null ? other$percentualAlteracao != null : !this$percentualAlteracao.equals(other$percentualAlteracao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof VersaoOrcamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $numeroVersao = this.getNumeroVersao();
        result = result * PRIME + ($numeroVersao == null ? 43 : $numeroVersao.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $orcamento = this.getOrcamento();
        result = result * PRIME + ($orcamento == null ? 43 : $orcamento.hashCode());
        final java.lang.Object $descricaoVersao = this.getDescricaoVersao();
        result = result * PRIME + ($descricaoVersao == null ? 43 : $descricaoVersao.hashCode());
        final java.lang.Object $statusVersao = this.getStatusVersao();
        result = result * PRIME + ($statusVersao == null ? 43 : $statusVersao.hashCode());
        final java.lang.Object $dataVersao = this.getDataVersao();
        result = result * PRIME + ($dataVersao == null ? 43 : $dataVersao.hashCode());
        final java.lang.Object $criadoPor = this.getCriadoPor();
        result = result * PRIME + ($criadoPor == null ? 43 : $criadoPor.hashCode());
        final java.lang.Object $aprovadoPor = this.getAprovadoPor();
        result = result * PRIME + ($aprovadoPor == null ? 43 : $aprovadoPor.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        final java.lang.Object $motivoAlteracao = this.getMotivoAlteracao();
        result = result * PRIME + ($motivoAlteracao == null ? 43 : $motivoAlteracao.hashCode());
        final java.lang.Object $alteracoesRealizadas = this.getAlteracoesRealizadas();
        result = result * PRIME + ($alteracoesRealizadas == null ? 43 : $alteracoesRealizadas.hashCode());
        final java.lang.Object $impactoFinanceiro = this.getImpactoFinanceiro();
        result = result * PRIME + ($impactoFinanceiro == null ? 43 : $impactoFinanceiro.hashCode());
        final java.lang.Object $percentualAlteracao = this.getPercentualAlteracao();
        result = result * PRIME + ($percentualAlteracao == null ? 43 : $percentualAlteracao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "VersaoOrcamento(id=" + this.getId() + ", orcamento=" + this.getOrcamento() + ", numeroVersao=" + this.getNumeroVersao() + ", descricaoVersao=" + this.getDescricaoVersao() + ", statusVersao=" + this.getStatusVersao() + ", dataVersao=" + this.getDataVersao() + ", criadoPor=" + this.getCriadoPor() + ", aprovadoPor=" + this.getAprovadoPor() + ", dataAprovacao=" + this.getDataAprovacao() + ", motivoAlteracao=" + this.getMotivoAlteracao() + ", alteracoesRealizadas=" + this.getAlteracoesRealizadas() + ", impactoFinanceiro=" + this.getImpactoFinanceiro() + ", percentualAlteracao=" + this.getPercentualAlteracao() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public VersaoOrcamento() {
    }

    @java.lang.SuppressWarnings("all")
    public VersaoOrcamento(final Long id, final Orcamento orcamento, final Integer numeroVersao, final String descricaoVersao, final StatusVersao statusVersao, final LocalDateTime dataVersao, final String criadoPor, final String aprovadoPor, final LocalDateTime dataAprovacao, final String motivoAlteracao, final String alteracoesRealizadas, final java.math.BigDecimal impactoFinanceiro, final java.math.BigDecimal percentualAlteracao, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.orcamento = orcamento;
        this.numeroVersao = numeroVersao;
        this.descricaoVersao = descricaoVersao;
        this.statusVersao = statusVersao;
        this.dataVersao = dataVersao;
        this.criadoPor = criadoPor;
        this.aprovadoPor = aprovadoPor;
        this.dataAprovacao = dataAprovacao;
        this.motivoAlteracao = motivoAlteracao;
        this.alteracoesRealizadas = alteracoesRealizadas;
        this.impactoFinanceiro = impactoFinanceiro;
        this.percentualAlteracao = percentualAlteracao;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
