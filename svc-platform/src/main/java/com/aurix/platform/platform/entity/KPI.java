package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um KPI (Key Performance Indicator)
 * 
 * Gerencia indicadores de performance e metas
 */
@Entity
@Table(name = "kpis", schema = "aurix")
public class KPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_kpi", unique = true, nullable = false, length = 50)
    private String codigoKpi;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_kpi", nullable = false)
    private TipoKPI tipoKpi;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaKPI categoria;
    @Column(name = "unidade_medida", length = 50)
    private String unidadeMedida;
    @Column(name = "formula_calculo", length = 1000)
    private String formulaCalculo;
    @Column(name = "meta_anual", precision = 15, scale = 4)
    private BigDecimal metaAnual;
    @Column(name = "meta_mensal", precision = 15, scale = 4)
    private BigDecimal metaMensal;
    @Column(name = "valor_atual", precision = 15, scale = 4)
    private BigDecimal valorAtual;
    @Column(name = "valor_anterior", precision = 15, scale = 4)
    private BigDecimal valorAnterior;
    @Column(name = "percentual_cumprimento", precision = 8, scale = 4)
    private BigDecimal percentualCumprimento;
    @Column(name = "tendencia", precision = 8, scale = 4)
    private BigDecimal tendencia;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_meta")
    private StatusMeta statusMeta;
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;
    @Column(name = "centro_custo", length = 100)
    private String centroCusto;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "frequencia_atualizacao", length = 50)
    private String frequenciaAtualizacao;
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
     * Tipo de KPI
     */
    public enum TipoKPI {
        FINANCEIRO,  // Financeiro
        OPERACIONAL,  // Operacional
        COMERCIAL,  // Comercial
        QUALIDADE,  // Qualidade
        SATISFACAO,  // Satisfação
        CRESCIMENTO,  // Crescimento
        EFICIENCIA,  // Eficiência
        OUTROS // Outros
        ;
    }


    /**
     * Categoria do KPI
     */
    public enum CategoriaKPI {
        RECEITA,  // Receita
        CUSTO,  // Custo
        MARGEM,  // Margem
        PRODUTIVIDADE,  // Produtividade
        QUALIDADE,  // Qualidade
        ATENDIMENTO,  // Atendimento
        CRESCIMENTO,  // Crescimento
        OUTROS // Outros
        ;
    }


    /**
     * Status da meta
     */
    public enum StatusMeta {
        ACIMA_META,  // Acima da meta
        NA_META,  // Na meta
        ABAIXO_META,  // Abaixo da meta
        CRITICO,  // Crítico
        SEM_META // Sem meta definida
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class KPIBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoKpi;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoKPI tipoKpi;
        @java.lang.SuppressWarnings("all")
        private CategoriaKPI categoria;
        @java.lang.SuppressWarnings("all")
        private String unidadeMedida;
        @java.lang.SuppressWarnings("all")
        private String formulaCalculo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal metaAnual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal metaMensal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorAtual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorAnterior;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualCumprimento;
        @java.lang.SuppressWarnings("all")
        private BigDecimal tendencia;
        @java.lang.SuppressWarnings("all")
        private StatusMeta statusMeta;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private String centroCusto;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private String frequenciaAtualizacao;
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
        KPIBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder codigoKpi(final String codigoKpi) {
            this.codigoKpi = codigoKpi;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder tipoKpi(final TipoKPI tipoKpi) {
            this.tipoKpi = tipoKpi;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder categoria(final CategoriaKPI categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder unidadeMedida(final String unidadeMedida) {
            this.unidadeMedida = unidadeMedida;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder formulaCalculo(final String formulaCalculo) {
            this.formulaCalculo = formulaCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder metaAnual(final BigDecimal metaAnual) {
            this.metaAnual = metaAnual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder metaMensal(final BigDecimal metaMensal) {
            this.metaMensal = metaMensal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder valorAtual(final BigDecimal valorAtual) {
            this.valorAtual = valorAtual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder valorAnterior(final BigDecimal valorAnterior) {
            this.valorAnterior = valorAnterior;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder percentualCumprimento(final BigDecimal percentualCumprimento) {
            this.percentualCumprimento = percentualCumprimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder tendencia(final BigDecimal tendencia) {
            this.tendencia = tendencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder statusMeta(final StatusMeta statusMeta) {
            this.statusMeta = statusMeta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder centroCusto(final String centroCusto) {
            this.centroCusto = centroCusto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder frequenciaAtualizacao(final String frequenciaAtualizacao) {
            this.frequenciaAtualizacao = frequenciaAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public KPI.KPIBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public KPI build() {
            return new KPI(this.id, this.codigoKpi, this.nome, this.descricao, this.tipoKpi, this.categoria, this.unidadeMedida, this.formulaCalculo, this.metaAnual, this.metaMensal, this.valorAtual, this.valorAnterior, this.percentualCumprimento, this.tendencia, this.statusMeta, this.dataReferencia, this.centroCusto, this.responsavel, this.frequenciaAtualizacao, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "KPI.KPIBuilder(id=" + this.id + ", codigoKpi=" + this.codigoKpi + ", nome=" + this.nome + ", descricao=" + this.descricao + ", tipoKpi=" + this.tipoKpi + ", categoria=" + this.categoria + ", unidadeMedida=" + this.unidadeMedida + ", formulaCalculo=" + this.formulaCalculo + ", metaAnual=" + this.metaAnual + ", metaMensal=" + this.metaMensal + ", valorAtual=" + this.valorAtual + ", valorAnterior=" + this.valorAnterior + ", percentualCumprimento=" + this.percentualCumprimento + ", tendencia=" + this.tendencia + ", statusMeta=" + this.statusMeta + ", dataReferencia=" + this.dataReferencia + ", centroCusto=" + this.centroCusto + ", responsavel=" + this.responsavel + ", frequenciaAtualizacao=" + this.frequenciaAtualizacao + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static KPI.KPIBuilder builder() {
        return new KPI.KPIBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoKpi() {
        return this.codigoKpi;
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
    public TipoKPI getTipoKpi() {
        return this.tipoKpi;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaKPI getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getUnidadeMedida() {
        return this.unidadeMedida;
    }

    @java.lang.SuppressWarnings("all")
    public String getFormulaCalculo() {
        return this.formulaCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMetaAnual() {
        return this.metaAnual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMetaMensal() {
        return this.metaMensal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAtual() {
        return this.valorAtual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAnterior() {
        return this.valorAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualCumprimento() {
        return this.percentualCumprimento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTendencia() {
        return this.tendencia;
    }

    @java.lang.SuppressWarnings("all")
    public StatusMeta getStatusMeta() {
        return this.statusMeta;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getCentroCusto() {
        return this.centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public String getResponsavel() {
        return this.responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getFrequenciaAtualizacao() {
        return this.frequenciaAtualizacao;
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
    public void setCodigoKpi(final String codigoKpi) {
        this.codigoKpi = codigoKpi;
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
    public void setTipoKpi(final TipoKPI tipoKpi) {
        this.tipoKpi = tipoKpi;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaKPI categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setUnidadeMedida(final String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @java.lang.SuppressWarnings("all")
    public void setFormulaCalculo(final String formulaCalculo) {
        this.formulaCalculo = formulaCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetaAnual(final BigDecimal metaAnual) {
        this.metaAnual = metaAnual;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetaMensal(final BigDecimal metaMensal) {
        this.metaMensal = metaMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAtual(final BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAnterior(final BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualCumprimento(final BigDecimal percentualCumprimento) {
        this.percentualCumprimento = percentualCumprimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setTendencia(final BigDecimal tendencia) {
        this.tendencia = tendencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatusMeta(final StatusMeta statusMeta) {
        this.statusMeta = statusMeta;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataReferencia(final LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setCentroCusto(final String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponsavel(final String responsavel) {
        this.responsavel = responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setFrequenciaAtualizacao(final String frequenciaAtualizacao) {
        this.frequenciaAtualizacao = frequenciaAtualizacao;
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
        if (!(o instanceof KPI)) return false;
        final KPI other = (KPI) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoKpi = this.getCodigoKpi();
        final java.lang.Object other$codigoKpi = other.getCodigoKpi();
        if (this$codigoKpi == null ? other$codigoKpi != null : !this$codigoKpi.equals(other$codigoKpi)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoKpi = this.getTipoKpi();
        final java.lang.Object other$tipoKpi = other.getTipoKpi();
        if (this$tipoKpi == null ? other$tipoKpi != null : !this$tipoKpi.equals(other$tipoKpi)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$unidadeMedida = this.getUnidadeMedida();
        final java.lang.Object other$unidadeMedida = other.getUnidadeMedida();
        if (this$unidadeMedida == null ? other$unidadeMedida != null : !this$unidadeMedida.equals(other$unidadeMedida)) return false;
        final java.lang.Object this$formulaCalculo = this.getFormulaCalculo();
        final java.lang.Object other$formulaCalculo = other.getFormulaCalculo();
        if (this$formulaCalculo == null ? other$formulaCalculo != null : !this$formulaCalculo.equals(other$formulaCalculo)) return false;
        final java.lang.Object this$metaAnual = this.getMetaAnual();
        final java.lang.Object other$metaAnual = other.getMetaAnual();
        if (this$metaAnual == null ? other$metaAnual != null : !this$metaAnual.equals(other$metaAnual)) return false;
        final java.lang.Object this$metaMensal = this.getMetaMensal();
        final java.lang.Object other$metaMensal = other.getMetaMensal();
        if (this$metaMensal == null ? other$metaMensal != null : !this$metaMensal.equals(other$metaMensal)) return false;
        final java.lang.Object this$valorAtual = this.getValorAtual();
        final java.lang.Object other$valorAtual = other.getValorAtual();
        if (this$valorAtual == null ? other$valorAtual != null : !this$valorAtual.equals(other$valorAtual)) return false;
        final java.lang.Object this$valorAnterior = this.getValorAnterior();
        final java.lang.Object other$valorAnterior = other.getValorAnterior();
        if (this$valorAnterior == null ? other$valorAnterior != null : !this$valorAnterior.equals(other$valorAnterior)) return false;
        final java.lang.Object this$percentualCumprimento = this.getPercentualCumprimento();
        final java.lang.Object other$percentualCumprimento = other.getPercentualCumprimento();
        if (this$percentualCumprimento == null ? other$percentualCumprimento != null : !this$percentualCumprimento.equals(other$percentualCumprimento)) return false;
        final java.lang.Object this$tendencia = this.getTendencia();
        final java.lang.Object other$tendencia = other.getTendencia();
        if (this$tendencia == null ? other$tendencia != null : !this$tendencia.equals(other$tendencia)) return false;
        final java.lang.Object this$statusMeta = this.getStatusMeta();
        final java.lang.Object other$statusMeta = other.getStatusMeta();
        if (this$statusMeta == null ? other$statusMeta != null : !this$statusMeta.equals(other$statusMeta)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$centroCusto = this.getCentroCusto();
        final java.lang.Object other$centroCusto = other.getCentroCusto();
        if (this$centroCusto == null ? other$centroCusto != null : !this$centroCusto.equals(other$centroCusto)) return false;
        final java.lang.Object this$responsavel = this.getResponsavel();
        final java.lang.Object other$responsavel = other.getResponsavel();
        if (this$responsavel == null ? other$responsavel != null : !this$responsavel.equals(other$responsavel)) return false;
        final java.lang.Object this$frequenciaAtualizacao = this.getFrequenciaAtualizacao();
        final java.lang.Object other$frequenciaAtualizacao = other.getFrequenciaAtualizacao();
        if (this$frequenciaAtualizacao == null ? other$frequenciaAtualizacao != null : !this$frequenciaAtualizacao.equals(other$frequenciaAtualizacao)) return false;
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
        return other instanceof KPI;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoKpi = this.getCodigoKpi();
        result = result * PRIME + ($codigoKpi == null ? 43 : $codigoKpi.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoKpi = this.getTipoKpi();
        result = result * PRIME + ($tipoKpi == null ? 43 : $tipoKpi.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $unidadeMedida = this.getUnidadeMedida();
        result = result * PRIME + ($unidadeMedida == null ? 43 : $unidadeMedida.hashCode());
        final java.lang.Object $formulaCalculo = this.getFormulaCalculo();
        result = result * PRIME + ($formulaCalculo == null ? 43 : $formulaCalculo.hashCode());
        final java.lang.Object $metaAnual = this.getMetaAnual();
        result = result * PRIME + ($metaAnual == null ? 43 : $metaAnual.hashCode());
        final java.lang.Object $metaMensal = this.getMetaMensal();
        result = result * PRIME + ($metaMensal == null ? 43 : $metaMensal.hashCode());
        final java.lang.Object $valorAtual = this.getValorAtual();
        result = result * PRIME + ($valorAtual == null ? 43 : $valorAtual.hashCode());
        final java.lang.Object $valorAnterior = this.getValorAnterior();
        result = result * PRIME + ($valorAnterior == null ? 43 : $valorAnterior.hashCode());
        final java.lang.Object $percentualCumprimento = this.getPercentualCumprimento();
        result = result * PRIME + ($percentualCumprimento == null ? 43 : $percentualCumprimento.hashCode());
        final java.lang.Object $tendencia = this.getTendencia();
        result = result * PRIME + ($tendencia == null ? 43 : $tendencia.hashCode());
        final java.lang.Object $statusMeta = this.getStatusMeta();
        result = result * PRIME + ($statusMeta == null ? 43 : $statusMeta.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $centroCusto = this.getCentroCusto();
        result = result * PRIME + ($centroCusto == null ? 43 : $centroCusto.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $frequenciaAtualizacao = this.getFrequenciaAtualizacao();
        result = result * PRIME + ($frequenciaAtualizacao == null ? 43 : $frequenciaAtualizacao.hashCode());
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
        return "KPI(id=" + this.getId() + ", codigoKpi=" + this.getCodigoKpi() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoKpi=" + this.getTipoKpi() + ", categoria=" + this.getCategoria() + ", unidadeMedida=" + this.getUnidadeMedida() + ", formulaCalculo=" + this.getFormulaCalculo() + ", metaAnual=" + this.getMetaAnual() + ", metaMensal=" + this.getMetaMensal() + ", valorAtual=" + this.getValorAtual() + ", valorAnterior=" + this.getValorAnterior() + ", percentualCumprimento=" + this.getPercentualCumprimento() + ", tendencia=" + this.getTendencia() + ", statusMeta=" + this.getStatusMeta() + ", dataReferencia=" + this.getDataReferencia() + ", centroCusto=" + this.getCentroCusto() + ", responsavel=" + this.getResponsavel() + ", frequenciaAtualizacao=" + this.getFrequenciaAtualizacao() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public KPI() {
    }

    @java.lang.SuppressWarnings("all")
    public KPI(final Long id, final String codigoKpi, final String nome, final String descricao, final TipoKPI tipoKpi, final CategoriaKPI categoria, final String unidadeMedida, final String formulaCalculo, final BigDecimal metaAnual, final BigDecimal metaMensal, final BigDecimal valorAtual, final BigDecimal valorAnterior, final BigDecimal percentualCumprimento, final BigDecimal tendencia, final StatusMeta statusMeta, final LocalDate dataReferencia, final String centroCusto, final String responsavel, final String frequenciaAtualizacao, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoKpi = codigoKpi;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoKpi = tipoKpi;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.formulaCalculo = formulaCalculo;
        this.metaAnual = metaAnual;
        this.metaMensal = metaMensal;
        this.valorAtual = valorAtual;
        this.valorAnterior = valorAnterior;
        this.percentualCumprimento = percentualCumprimento;
        this.tendencia = tendencia;
        this.statusMeta = statusMeta;
        this.dataReferencia = dataReferencia;
        this.centroCusto = centroCusto;
        this.responsavel = responsavel;
        this.frequenciaAtualizacao = frequenciaAtualizacao;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
