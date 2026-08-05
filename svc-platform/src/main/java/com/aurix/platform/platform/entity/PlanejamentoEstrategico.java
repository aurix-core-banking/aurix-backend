package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o planejamento estratégico
 * 
 * Gerencia objetivos estratégicos, metas e iniciativas
 */
@Entity
@Table(name = "planejamento_estrategico", schema = "aurix")
public class PlanejamentoEstrategico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_objetivo", unique = true, nullable = false, length = 50)
    private String codigoObjetivo;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_objetivo", nullable = false)
    private TipoObjetivo tipoObjetivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaObjetivo categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusObjetivo status;
    @Column(name = "prioridade", nullable = false)
    private Integer prioridade;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
    @Column(name = "percentual_conclusao", precision = 8, scale = 4)
    private java.math.BigDecimal percentualConclusao;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "equipe", length = 500)
    private String equipe;
    @Column(name = "recursos_necessarios", length = 1000)
    private String recursosNecessarios;
    @Column(name = "riscos", length = 1000)
    private String riscos;
    @Column(name = "indicadores_sucesso", length = 1000)
    private String indicadoresSucesso;
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
     * Tipo de objetivo estratégico
     */
    public enum TipoObjetivo {
        ESTRATEGICO,  // Estratégico
        TATICO,  // Tático
        OPERACIONAL,  // Operacional
        PROJETO,  // Projeto
        INICIATIVA // Iniciativa
        ;
    }


    /**
     * Categoria do objetivo
     */
    public enum CategoriaObjetivo {
        FINANCEIRO,  // Financeiro
        COMERCIAL,  // Comercial
        OPERACIONAL,  // Operacional
        TECNOLOGIA,  // Tecnologia
        RECURSOS_HUMANOS,  // Recursos Humanos
        QUALIDADE,  // Qualidade
        CRESCIMENTO,  // Crescimento
        INOVACAO,  // Inovação
        SUSTENTABILIDADE,  // Sustentabilidade
        OUTROS // Outros
        ;
    }


    /**
     * Status do objetivo
     */
    public enum StatusObjetivo {
        PLANEJADO,  // Planejado
        EM_ANDAMENTO,  // Em andamento
        CONCLUIDO,  // Concluído
        SUSPENSO,  // Suspenso
        CANCELADO,  // Cancelado
        ATRASADO // Atrasado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class PlanejamentoEstrategicoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoObjetivo;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoObjetivo tipoObjetivo;
        @java.lang.SuppressWarnings("all")
        private CategoriaObjetivo categoria;
        @java.lang.SuppressWarnings("all")
        private StatusObjetivo status;
        @java.lang.SuppressWarnings("all")
        private Integer prioridade;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataInicio;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataFim;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataConclusao;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal percentualConclusao;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private String equipe;
        @java.lang.SuppressWarnings("all")
        private String recursosNecessarios;
        @java.lang.SuppressWarnings("all")
        private String riscos;
        @java.lang.SuppressWarnings("all")
        private String indicadoresSucesso;
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
        PlanejamentoEstrategicoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder codigoObjetivo(final String codigoObjetivo) {
            this.codigoObjetivo = codigoObjetivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder tipoObjetivo(final TipoObjetivo tipoObjetivo) {
            this.tipoObjetivo = tipoObjetivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder categoria(final CategoriaObjetivo categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder status(final StatusObjetivo status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder prioridade(final Integer prioridade) {
            this.prioridade = prioridade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataInicio(final LocalDate dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataFim(final LocalDate dataFim) {
            this.dataFim = dataFim;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataConclusao(final LocalDate dataConclusao) {
            this.dataConclusao = dataConclusao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder percentualConclusao(final java.math.BigDecimal percentualConclusao) {
            this.percentualConclusao = percentualConclusao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder equipe(final String equipe) {
            this.equipe = equipe;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder recursosNecessarios(final String recursosNecessarios) {
            this.recursosNecessarios = recursosNecessarios;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder riscos(final String riscos) {
            this.riscos = riscos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder indicadoresSucesso(final String indicadoresSucesso) {
            this.indicadoresSucesso = indicadoresSucesso;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico build() {
            return new PlanejamentoEstrategico(this.id, this.codigoObjetivo, this.nome, this.descricao, this.tipoObjetivo, this.categoria, this.status, this.prioridade, this.dataInicio, this.dataFim, this.dataConclusao, this.percentualConclusao, this.responsavel, this.equipe, this.recursosNecessarios, this.riscos, this.indicadoresSucesso, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder(id=" + this.id + ", codigoObjetivo=" + this.codigoObjetivo + ", nome=" + this.nome + ", descricao=" + this.descricao + ", tipoObjetivo=" + this.tipoObjetivo + ", categoria=" + this.categoria + ", status=" + this.status + ", prioridade=" + this.prioridade + ", dataInicio=" + this.dataInicio + ", dataFim=" + this.dataFim + ", dataConclusao=" + this.dataConclusao + ", percentualConclusao=" + this.percentualConclusao + ", responsavel=" + this.responsavel + ", equipe=" + this.equipe + ", recursosNecessarios=" + this.recursosNecessarios + ", riscos=" + this.riscos + ", indicadoresSucesso=" + this.indicadoresSucesso + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder builder() {
        return new PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoObjetivo() {
        return this.codigoObjetivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoObjetivo getTipoObjetivo() {
        return this.tipoObjetivo;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaObjetivo getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public StatusObjetivo getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrioridade() {
        return this.prioridade;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataFim() {
        return this.dataFim;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataConclusao() {
        return this.dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public java.math.BigDecimal getPercentualConclusao() {
        return this.percentualConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public String getResponsavel() {
        return this.responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getEquipe() {
        return this.equipe;
    }

    @java.lang.SuppressWarnings("all")
    public String getRecursosNecessarios() {
        return this.recursosNecessarios;
    }

    @java.lang.SuppressWarnings("all")
    public String getRiscos() {
        return this.riscos;
    }

    @java.lang.SuppressWarnings("all")
    public String getIndicadoresSucesso() {
        return this.indicadoresSucesso;
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
    public void setCodigoObjetivo(final String codigoObjetivo) {
        this.codigoObjetivo = codigoObjetivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoObjetivo(final TipoObjetivo tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaObjetivo categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusObjetivo status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrioridade(final Integer prioridade) {
        this.prioridade = prioridade;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFim(final LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConclusao(final LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualConclusao(final java.math.BigDecimal percentualConclusao) {
        this.percentualConclusao = percentualConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponsavel(final String responsavel) {
        this.responsavel = responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setEquipe(final String equipe) {
        this.equipe = equipe;
    }

    @java.lang.SuppressWarnings("all")
    public void setRecursosNecessarios(final String recursosNecessarios) {
        this.recursosNecessarios = recursosNecessarios;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiscos(final String riscos) {
        this.riscos = riscos;
    }

    @java.lang.SuppressWarnings("all")
    public void setIndicadoresSucesso(final String indicadoresSucesso) {
        this.indicadoresSucesso = indicadoresSucesso;
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
        if (!(o instanceof PlanejamentoEstrategico)) return false;
        final PlanejamentoEstrategico other = (PlanejamentoEstrategico) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$prioridade = this.getPrioridade();
        final java.lang.Object other$prioridade = other.getPrioridade();
        if (this$prioridade == null ? other$prioridade != null : !this$prioridade.equals(other$prioridade)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoObjetivo = this.getCodigoObjetivo();
        final java.lang.Object other$codigoObjetivo = other.getCodigoObjetivo();
        if (this$codigoObjetivo == null ? other$codigoObjetivo != null : !this$codigoObjetivo.equals(other$codigoObjetivo)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoObjetivo = this.getTipoObjetivo();
        final java.lang.Object other$tipoObjetivo = other.getTipoObjetivo();
        if (this$tipoObjetivo == null ? other$tipoObjetivo != null : !this$tipoObjetivo.equals(other$tipoObjetivo)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataInicio = this.getDataInicio();
        final java.lang.Object other$dataInicio = other.getDataInicio();
        if (this$dataInicio == null ? other$dataInicio != null : !this$dataInicio.equals(other$dataInicio)) return false;
        final java.lang.Object this$dataFim = this.getDataFim();
        final java.lang.Object other$dataFim = other.getDataFim();
        if (this$dataFim == null ? other$dataFim != null : !this$dataFim.equals(other$dataFim)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$percentualConclusao = this.getPercentualConclusao();
        final java.lang.Object other$percentualConclusao = other.getPercentualConclusao();
        if (this$percentualConclusao == null ? other$percentualConclusao != null : !this$percentualConclusao.equals(other$percentualConclusao)) return false;
        final java.lang.Object this$responsavel = this.getResponsavel();
        final java.lang.Object other$responsavel = other.getResponsavel();
        if (this$responsavel == null ? other$responsavel != null : !this$responsavel.equals(other$responsavel)) return false;
        final java.lang.Object this$equipe = this.getEquipe();
        final java.lang.Object other$equipe = other.getEquipe();
        if (this$equipe == null ? other$equipe != null : !this$equipe.equals(other$equipe)) return false;
        final java.lang.Object this$recursosNecessarios = this.getRecursosNecessarios();
        final java.lang.Object other$recursosNecessarios = other.getRecursosNecessarios();
        if (this$recursosNecessarios == null ? other$recursosNecessarios != null : !this$recursosNecessarios.equals(other$recursosNecessarios)) return false;
        final java.lang.Object this$riscos = this.getRiscos();
        final java.lang.Object other$riscos = other.getRiscos();
        if (this$riscos == null ? other$riscos != null : !this$riscos.equals(other$riscos)) return false;
        final java.lang.Object this$indicadoresSucesso = this.getIndicadoresSucesso();
        final java.lang.Object other$indicadoresSucesso = other.getIndicadoresSucesso();
        if (this$indicadoresSucesso == null ? other$indicadoresSucesso != null : !this$indicadoresSucesso.equals(other$indicadoresSucesso)) return false;
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
        return other instanceof PlanejamentoEstrategico;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $prioridade = this.getPrioridade();
        result = result * PRIME + ($prioridade == null ? 43 : $prioridade.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoObjetivo = this.getCodigoObjetivo();
        result = result * PRIME + ($codigoObjetivo == null ? 43 : $codigoObjetivo.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoObjetivo = this.getTipoObjetivo();
        result = result * PRIME + ($tipoObjetivo == null ? 43 : $tipoObjetivo.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataInicio = this.getDataInicio();
        result = result * PRIME + ($dataInicio == null ? 43 : $dataInicio.hashCode());
        final java.lang.Object $dataFim = this.getDataFim();
        result = result * PRIME + ($dataFim == null ? 43 : $dataFim.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $percentualConclusao = this.getPercentualConclusao();
        result = result * PRIME + ($percentualConclusao == null ? 43 : $percentualConclusao.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $equipe = this.getEquipe();
        result = result * PRIME + ($equipe == null ? 43 : $equipe.hashCode());
        final java.lang.Object $recursosNecessarios = this.getRecursosNecessarios();
        result = result * PRIME + ($recursosNecessarios == null ? 43 : $recursosNecessarios.hashCode());
        final java.lang.Object $riscos = this.getRiscos();
        result = result * PRIME + ($riscos == null ? 43 : $riscos.hashCode());
        final java.lang.Object $indicadoresSucesso = this.getIndicadoresSucesso();
        result = result * PRIME + ($indicadoresSucesso == null ? 43 : $indicadoresSucesso.hashCode());
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
        return "PlanejamentoEstrategico(id=" + this.getId() + ", codigoObjetivo=" + this.getCodigoObjetivo() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoObjetivo=" + this.getTipoObjetivo() + ", categoria=" + this.getCategoria() + ", status=" + this.getStatus() + ", prioridade=" + this.getPrioridade() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", dataConclusao=" + this.getDataConclusao() + ", percentualConclusao=" + this.getPercentualConclusao() + ", responsavel=" + this.getResponsavel() + ", equipe=" + this.getEquipe() + ", recursosNecessarios=" + this.getRecursosNecessarios() + ", riscos=" + this.getRiscos() + ", indicadoresSucesso=" + this.getIndicadoresSucesso() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PlanejamentoEstrategico() {
    }

    @java.lang.SuppressWarnings("all")
    public PlanejamentoEstrategico(final Long id, final String codigoObjetivo, final String nome, final String descricao, final TipoObjetivo tipoObjetivo, final CategoriaObjetivo categoria, final StatusObjetivo status, final Integer prioridade, final LocalDate dataInicio, final LocalDate dataFim, final LocalDate dataConclusao, final java.math.BigDecimal percentualConclusao, final String responsavel, final String equipe, final String recursosNecessarios, final String riscos, final String indicadoresSucesso, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoObjetivo = codigoObjetivo;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoObjetivo = tipoObjetivo;
        this.categoria = categoria;
        this.status = status;
        this.prioridade = prioridade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataConclusao = dataConclusao;
        this.percentualConclusao = percentualConclusao;
        this.responsavel = responsavel;
        this.equipe = equipe;
        this.recursosNecessarios = recursosNecessarios;
        this.riscos = riscos;
        this.indicadoresSucesso = indicadoresSucesso;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
