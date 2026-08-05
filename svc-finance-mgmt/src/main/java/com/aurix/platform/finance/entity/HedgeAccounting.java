package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o Hedge Accounting conforme IFRS 9
 * 
 * Gerencia os instrumentos de hedge e sua contabilização,
 * incluindo hedge de valor justo, fluxo de caixa e investimento líquido.
 */
@Entity
@Table(name = "hedge_accounting", schema = "aurix")
public class HedgeAccounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_hedge", unique = true, nullable = false, length = 50)
    private String codigoHedge;
    @Column(name = "nome_hedge", nullable = false, length = 200)
    private String nomeHedge;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_hedge", nullable = false)
    private TipoHedge tipoHedge;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_hedge", nullable = false)
    private CategoriaHedge categoriaHedge;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumento_hedgeado_id", nullable = false)
    private InstrumentoFinanceiro instrumentoHedgeado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumento_hedge_id", nullable = false)
    private InstrumentoFinanceiro instrumentoHedge;
    @Column(name = "valor_exposicao", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorExposicao;
    @Column(name = "valor_hedge", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorHedge;
    @Column(name = "proporcao_hedge", precision = 8, scale = 6)
    private BigDecimal proporcaoHedge;
    @Column(name = "efetividade_hedge", precision = 8, scale = 6)
    private BigDecimal efetividadeHedge;
    @Column(name = "limite_efetividade_min", precision = 8, scale = 6)
    private BigDecimal limiteEfetividadeMin;
    @Column(name = "limite_efetividade_max", precision = 8, scale = 6)
    private BigDecimal limiteEfetividadeMax;
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;
    @Column(name = "data_terminacao")
    private LocalDateTime dataTerminacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusHedge status;
    @Column(name = "metodologia_avaliacao", length = 100)
    private String metodologiaAvaliacao;
    @Column(name = "frequencia_avaliacao", length = 50)
    private String frequenciaAvaliacao;
    @Column(name = "data_ultima_avaliacao")
    private LocalDateTime dataUltimaAvaliacao;
    @Column(name = "resultado_hedge", precision = 15, scale = 2)
    private BigDecimal resultadoHedge;
    @Column(name = "resultado_nao_efetivo", precision = 15, scale = 2)
    private BigDecimal resultadoNaoEfetivo;
    @Column(name = "conta_hedge_id")
    private Long contaHedgeId;
    @Column(name = "conta_resultado_hedge_id")
    private Long contaResultadoHedgeId;
    @Column(name = "conta_nao_efetivo_id")
    private Long contaNaoEfetivoId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "documentacao", columnDefinition = "jsonb")
    private String documentacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros_hedge", columnDefinition = "jsonb")
    private String parametrosHedge;
    @Column(name = "risco_hedgeado", length = 100)
    private String riscoHedgeado;
    @Column(name = "instrumento_derivativo", length = 100)
    private String instrumentoDerivativo;
    @Column(name = "valor_nocional", precision = 15, scale = 2)
    private BigDecimal valorNocional;
    @Column(name = "moeda_hedge", length = 3)
    private String moedaHedge;
    @Column(name = "taxa_hedge", precision = 8, scale = 4)
    private BigDecimal taxaHedge;
    @Column(name = "usuario_responsavel", length = 100)
    private String usuarioResponsavel;
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
     * Tipo de hedge
     */
    public enum TipoHedge {
        VALOR_JUSTO,  // Hedge de valor justo
        FLUXO_CAIXA,  // Hedge de fluxo de caixa
        INVESTIMENTO_LIQUIDO // Hedge de investimento líquido
        ;
    }


    /**
     * Categoria do hedge
     */
    public enum CategoriaHedge {
        HEDGE_PERFEITO,  // Hedge perfeito
        HEDGE_ALTAMENTE_EFETIVO,  // Hedge altamente efetivo
        HEDGE_EFETIVO,  // Hedge efetivo
        HEDGE_NAO_EFETIVO // Hedge não efetivo
        ;
    }


    /**
     * Status do hedge
     */
    public enum StatusHedge {
        ATIVO,  // Ativo
        SUSPENSO,  // Suspenso
        TERMINADO,  // Terminado
        CANCELADO,  // Cancelado
        REESTRUTURADO,  // Reestruturado
        NAO_EFETIVO // Não efetivo
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class HedgeAccountingBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoHedge;
        @java.lang.SuppressWarnings("all")
        private String nomeHedge;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoHedge tipoHedge;
        @java.lang.SuppressWarnings("all")
        private CategoriaHedge categoriaHedge;
        @java.lang.SuppressWarnings("all")
        private InstrumentoFinanceiro instrumentoHedgeado;
        @java.lang.SuppressWarnings("all")
        private InstrumentoFinanceiro instrumentoHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorExposicao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal proporcaoHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal efetividadeHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteEfetividadeMin;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteEfetividadeMax;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataInicio;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataVencimento;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataTerminacao;
        @java.lang.SuppressWarnings("all")
        private StatusHedge status;
        @java.lang.SuppressWarnings("all")
        private String metodologiaAvaliacao;
        @java.lang.SuppressWarnings("all")
        private String frequenciaAvaliacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataUltimaAvaliacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal resultadoHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal resultadoNaoEfetivo;
        @java.lang.SuppressWarnings("all")
        private Long contaHedgeId;
        @java.lang.SuppressWarnings("all")
        private Long contaResultadoHedgeId;
        @java.lang.SuppressWarnings("all")
        private Long contaNaoEfetivoId;
        @java.lang.SuppressWarnings("all")
        private String documentacao;
        @java.lang.SuppressWarnings("all")
        private String parametrosHedge;
        @java.lang.SuppressWarnings("all")
        private String riscoHedgeado;
        @java.lang.SuppressWarnings("all")
        private String instrumentoDerivativo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorNocional;
        @java.lang.SuppressWarnings("all")
        private String moedaHedge;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaHedge;
        @java.lang.SuppressWarnings("all")
        private String usuarioResponsavel;
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
        HedgeAccountingBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder codigoHedge(final String codigoHedge) {
            this.codigoHedge = codigoHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder nomeHedge(final String nomeHedge) {
            this.nomeHedge = nomeHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder tipoHedge(final TipoHedge tipoHedge) {
            this.tipoHedge = tipoHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder categoriaHedge(final CategoriaHedge categoriaHedge) {
            this.categoriaHedge = categoriaHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder instrumentoHedgeado(final InstrumentoFinanceiro instrumentoHedgeado) {
            this.instrumentoHedgeado = instrumentoHedgeado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder instrumentoHedge(final InstrumentoFinanceiro instrumentoHedge) {
            this.instrumentoHedge = instrumentoHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder valorExposicao(final BigDecimal valorExposicao) {
            this.valorExposicao = valorExposicao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder valorHedge(final BigDecimal valorHedge) {
            this.valorHedge = valorHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder proporcaoHedge(final BigDecimal proporcaoHedge) {
            this.proporcaoHedge = proporcaoHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder efetividadeHedge(final BigDecimal efetividadeHedge) {
            this.efetividadeHedge = efetividadeHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder limiteEfetividadeMin(final BigDecimal limiteEfetividadeMin) {
            this.limiteEfetividadeMin = limiteEfetividadeMin;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder limiteEfetividadeMax(final BigDecimal limiteEfetividadeMax) {
            this.limiteEfetividadeMax = limiteEfetividadeMax;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataInicio(final LocalDateTime dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataVencimento(final LocalDateTime dataVencimento) {
            this.dataVencimento = dataVencimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataTerminacao(final LocalDateTime dataTerminacao) {
            this.dataTerminacao = dataTerminacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder status(final StatusHedge status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder metodologiaAvaliacao(final String metodologiaAvaliacao) {
            this.metodologiaAvaliacao = metodologiaAvaliacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder frequenciaAvaliacao(final String frequenciaAvaliacao) {
            this.frequenciaAvaliacao = frequenciaAvaliacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataUltimaAvaliacao(final LocalDateTime dataUltimaAvaliacao) {
            this.dataUltimaAvaliacao = dataUltimaAvaliacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder resultadoHedge(final BigDecimal resultadoHedge) {
            this.resultadoHedge = resultadoHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder resultadoNaoEfetivo(final BigDecimal resultadoNaoEfetivo) {
            this.resultadoNaoEfetivo = resultadoNaoEfetivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder contaHedgeId(final Long contaHedgeId) {
            this.contaHedgeId = contaHedgeId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder contaResultadoHedgeId(final Long contaResultadoHedgeId) {
            this.contaResultadoHedgeId = contaResultadoHedgeId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder contaNaoEfetivoId(final Long contaNaoEfetivoId) {
            this.contaNaoEfetivoId = contaNaoEfetivoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder documentacao(final String documentacao) {
            this.documentacao = documentacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder parametrosHedge(final String parametrosHedge) {
            this.parametrosHedge = parametrosHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder riscoHedgeado(final String riscoHedgeado) {
            this.riscoHedgeado = riscoHedgeado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder instrumentoDerivativo(final String instrumentoDerivativo) {
            this.instrumentoDerivativo = instrumentoDerivativo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder valorNocional(final BigDecimal valorNocional) {
            this.valorNocional = valorNocional;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder moedaHedge(final String moedaHedge) {
            this.moedaHedge = moedaHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder taxaHedge(final BigDecimal taxaHedge) {
            this.taxaHedge = taxaHedge;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder usuarioResponsavel(final String usuarioResponsavel) {
            this.usuarioResponsavel = usuarioResponsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public HedgeAccounting.HedgeAccountingBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public HedgeAccounting build() {
            return new HedgeAccounting(this.id, this.codigoHedge, this.nomeHedge, this.descricao, this.tipoHedge, this.categoriaHedge, this.instrumentoHedgeado, this.instrumentoHedge, this.valorExposicao, this.valorHedge, this.proporcaoHedge, this.efetividadeHedge, this.limiteEfetividadeMin, this.limiteEfetividadeMax, this.dataInicio, this.dataVencimento, this.dataTerminacao, this.status, this.metodologiaAvaliacao, this.frequenciaAvaliacao, this.dataUltimaAvaliacao, this.resultadoHedge, this.resultadoNaoEfetivo, this.contaHedgeId, this.contaResultadoHedgeId, this.contaNaoEfetivoId, this.documentacao, this.parametrosHedge, this.riscoHedgeado, this.instrumentoDerivativo, this.valorNocional, this.moedaHedge, this.taxaHedge, this.usuarioResponsavel, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "HedgeAccounting.HedgeAccountingBuilder(id=" + this.id + ", codigoHedge=" + this.codigoHedge + ", nomeHedge=" + this.nomeHedge + ", descricao=" + this.descricao + ", tipoHedge=" + this.tipoHedge + ", categoriaHedge=" + this.categoriaHedge + ", instrumentoHedgeado=" + this.instrumentoHedgeado + ", instrumentoHedge=" + this.instrumentoHedge + ", valorExposicao=" + this.valorExposicao + ", valorHedge=" + this.valorHedge + ", proporcaoHedge=" + this.proporcaoHedge + ", efetividadeHedge=" + this.efetividadeHedge + ", limiteEfetividadeMin=" + this.limiteEfetividadeMin + ", limiteEfetividadeMax=" + this.limiteEfetividadeMax + ", dataInicio=" + this.dataInicio + ", dataVencimento=" + this.dataVencimento + ", dataTerminacao=" + this.dataTerminacao + ", status=" + this.status + ", metodologiaAvaliacao=" + this.metodologiaAvaliacao + ", frequenciaAvaliacao=" + this.frequenciaAvaliacao + ", dataUltimaAvaliacao=" + this.dataUltimaAvaliacao + ", resultadoHedge=" + this.resultadoHedge + ", resultadoNaoEfetivo=" + this.resultadoNaoEfetivo + ", contaHedgeId=" + this.contaHedgeId + ", contaResultadoHedgeId=" + this.contaResultadoHedgeId + ", contaNaoEfetivoId=" + this.contaNaoEfetivoId + ", documentacao=" + this.documentacao + ", parametrosHedge=" + this.parametrosHedge + ", riscoHedgeado=" + this.riscoHedgeado + ", instrumentoDerivativo=" + this.instrumentoDerivativo + ", valorNocional=" + this.valorNocional + ", moedaHedge=" + this.moedaHedge + ", taxaHedge=" + this.taxaHedge + ", usuarioResponsavel=" + this.usuarioResponsavel + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static HedgeAccounting.HedgeAccountingBuilder builder() {
        return new HedgeAccounting.HedgeAccountingBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoHedge() {
        return this.codigoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeHedge() {
        return this.nomeHedge;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoHedge getTipoHedge() {
        return this.tipoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaHedge getCategoriaHedge() {
        return this.categoriaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public InstrumentoFinanceiro getInstrumentoHedgeado() {
        return this.instrumentoHedgeado;
    }

    @java.lang.SuppressWarnings("all")
    public InstrumentoFinanceiro getInstrumentoHedge() {
        return this.instrumentoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorExposicao() {
        return this.valorExposicao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorHedge() {
        return this.valorHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProporcaoHedge() {
        return this.proporcaoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEfetividadeHedge() {
        return this.efetividadeHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteEfetividadeMin() {
        return this.limiteEfetividadeMin;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteEfetividadeMax() {
        return this.limiteEfetividadeMax;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTerminacao() {
        return this.dataTerminacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusHedge getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetodologiaAvaliacao() {
        return this.metodologiaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getFrequenciaAvaliacao() {
        return this.frequenciaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataUltimaAvaliacao() {
        return this.dataUltimaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getResultadoHedge() {
        return this.resultadoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getResultadoNaoEfetivo() {
        return this.resultadoNaoEfetivo;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaHedgeId() {
        return this.contaHedgeId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaResultadoHedgeId() {
        return this.contaResultadoHedgeId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaNaoEfetivoId() {
        return this.contaNaoEfetivoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDocumentacao() {
        return this.documentacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getParametrosHedge() {
        return this.parametrosHedge;
    }

    @java.lang.SuppressWarnings("all")
    public String getRiscoHedgeado() {
        return this.riscoHedgeado;
    }

    @java.lang.SuppressWarnings("all")
    public String getInstrumentoDerivativo() {
        return this.instrumentoDerivativo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorNocional() {
        return this.valorNocional;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoedaHedge() {
        return this.moedaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaHedge() {
        return this.taxaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioResponsavel() {
        return this.usuarioResponsavel;
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
    public void setCodigoHedge(final String codigoHedge) {
        this.codigoHedge = codigoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeHedge(final String nomeHedge) {
        this.nomeHedge = nomeHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoHedge(final TipoHedge tipoHedge) {
        this.tipoHedge = tipoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaHedge(final CategoriaHedge categoriaHedge) {
        this.categoriaHedge = categoriaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentoHedgeado(final InstrumentoFinanceiro instrumentoHedgeado) {
        this.instrumentoHedgeado = instrumentoHedgeado;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentoHedge(final InstrumentoFinanceiro instrumentoHedge) {
        this.instrumentoHedge = instrumentoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorExposicao(final BigDecimal valorExposicao) {
        this.valorExposicao = valorExposicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorHedge(final BigDecimal valorHedge) {
        this.valorHedge = valorHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setProporcaoHedge(final BigDecimal proporcaoHedge) {
        this.proporcaoHedge = proporcaoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setEfetividadeHedge(final BigDecimal efetividadeHedge) {
        this.efetividadeHedge = efetividadeHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteEfetividadeMin(final BigDecimal limiteEfetividadeMin) {
        this.limiteEfetividadeMin = limiteEfetividadeMin;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteEfetividadeMax(final BigDecimal limiteEfetividadeMax) {
        this.limiteEfetividadeMax = limiteEfetividadeMax;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTerminacao(final LocalDateTime dataTerminacao) {
        this.dataTerminacao = dataTerminacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusHedge status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetodologiaAvaliacao(final String metodologiaAvaliacao) {
        this.metodologiaAvaliacao = metodologiaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setFrequenciaAvaliacao(final String frequenciaAvaliacao) {
        this.frequenciaAvaliacao = frequenciaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUltimaAvaliacao(final LocalDateTime dataUltimaAvaliacao) {
        this.dataUltimaAvaliacao = dataUltimaAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultadoHedge(final BigDecimal resultadoHedge) {
        this.resultadoHedge = resultadoHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultadoNaoEfetivo(final BigDecimal resultadoNaoEfetivo) {
        this.resultadoNaoEfetivo = resultadoNaoEfetivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaHedgeId(final Long contaHedgeId) {
        this.contaHedgeId = contaHedgeId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaResultadoHedgeId(final Long contaResultadoHedgeId) {
        this.contaResultadoHedgeId = contaResultadoHedgeId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaNaoEfetivoId(final Long contaNaoEfetivoId) {
        this.contaNaoEfetivoId = contaNaoEfetivoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentacao(final String documentacao) {
        this.documentacao = documentacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setParametrosHedge(final String parametrosHedge) {
        this.parametrosHedge = parametrosHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiscoHedgeado(final String riscoHedgeado) {
        this.riscoHedgeado = riscoHedgeado;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentoDerivativo(final String instrumentoDerivativo) {
        this.instrumentoDerivativo = instrumentoDerivativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorNocional(final BigDecimal valorNocional) {
        this.valorNocional = valorNocional;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoedaHedge(final String moedaHedge) {
        this.moedaHedge = moedaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaHedge(final BigDecimal taxaHedge) {
        this.taxaHedge = taxaHedge;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioResponsavel(final String usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
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
        if (!(o instanceof HedgeAccounting)) return false;
        final HedgeAccounting other = (HedgeAccounting) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaHedgeId = this.getContaHedgeId();
        final java.lang.Object other$contaHedgeId = other.getContaHedgeId();
        if (this$contaHedgeId == null ? other$contaHedgeId != null : !this$contaHedgeId.equals(other$contaHedgeId)) return false;
        final java.lang.Object this$contaResultadoHedgeId = this.getContaResultadoHedgeId();
        final java.lang.Object other$contaResultadoHedgeId = other.getContaResultadoHedgeId();
        if (this$contaResultadoHedgeId == null ? other$contaResultadoHedgeId != null : !this$contaResultadoHedgeId.equals(other$contaResultadoHedgeId)) return false;
        final java.lang.Object this$contaNaoEfetivoId = this.getContaNaoEfetivoId();
        final java.lang.Object other$contaNaoEfetivoId = other.getContaNaoEfetivoId();
        if (this$contaNaoEfetivoId == null ? other$contaNaoEfetivoId != null : !this$contaNaoEfetivoId.equals(other$contaNaoEfetivoId)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoHedge = this.getCodigoHedge();
        final java.lang.Object other$codigoHedge = other.getCodigoHedge();
        if (this$codigoHedge == null ? other$codigoHedge != null : !this$codigoHedge.equals(other$codigoHedge)) return false;
        final java.lang.Object this$nomeHedge = this.getNomeHedge();
        final java.lang.Object other$nomeHedge = other.getNomeHedge();
        if (this$nomeHedge == null ? other$nomeHedge != null : !this$nomeHedge.equals(other$nomeHedge)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoHedge = this.getTipoHedge();
        final java.lang.Object other$tipoHedge = other.getTipoHedge();
        if (this$tipoHedge == null ? other$tipoHedge != null : !this$tipoHedge.equals(other$tipoHedge)) return false;
        final java.lang.Object this$categoriaHedge = this.getCategoriaHedge();
        final java.lang.Object other$categoriaHedge = other.getCategoriaHedge();
        if (this$categoriaHedge == null ? other$categoriaHedge != null : !this$categoriaHedge.equals(other$categoriaHedge)) return false;
        final java.lang.Object this$instrumentoHedgeado = this.getInstrumentoHedgeado();
        final java.lang.Object other$instrumentoHedgeado = other.getInstrumentoHedgeado();
        if (this$instrumentoHedgeado == null ? other$instrumentoHedgeado != null : !this$instrumentoHedgeado.equals(other$instrumentoHedgeado)) return false;
        final java.lang.Object this$instrumentoHedge = this.getInstrumentoHedge();
        final java.lang.Object other$instrumentoHedge = other.getInstrumentoHedge();
        if (this$instrumentoHedge == null ? other$instrumentoHedge != null : !this$instrumentoHedge.equals(other$instrumentoHedge)) return false;
        final java.lang.Object this$valorExposicao = this.getValorExposicao();
        final java.lang.Object other$valorExposicao = other.getValorExposicao();
        if (this$valorExposicao == null ? other$valorExposicao != null : !this$valorExposicao.equals(other$valorExposicao)) return false;
        final java.lang.Object this$valorHedge = this.getValorHedge();
        final java.lang.Object other$valorHedge = other.getValorHedge();
        if (this$valorHedge == null ? other$valorHedge != null : !this$valorHedge.equals(other$valorHedge)) return false;
        final java.lang.Object this$proporcaoHedge = this.getProporcaoHedge();
        final java.lang.Object other$proporcaoHedge = other.getProporcaoHedge();
        if (this$proporcaoHedge == null ? other$proporcaoHedge != null : !this$proporcaoHedge.equals(other$proporcaoHedge)) return false;
        final java.lang.Object this$efetividadeHedge = this.getEfetividadeHedge();
        final java.lang.Object other$efetividadeHedge = other.getEfetividadeHedge();
        if (this$efetividadeHedge == null ? other$efetividadeHedge != null : !this$efetividadeHedge.equals(other$efetividadeHedge)) return false;
        final java.lang.Object this$limiteEfetividadeMin = this.getLimiteEfetividadeMin();
        final java.lang.Object other$limiteEfetividadeMin = other.getLimiteEfetividadeMin();
        if (this$limiteEfetividadeMin == null ? other$limiteEfetividadeMin != null : !this$limiteEfetividadeMin.equals(other$limiteEfetividadeMin)) return false;
        final java.lang.Object this$limiteEfetividadeMax = this.getLimiteEfetividadeMax();
        final java.lang.Object other$limiteEfetividadeMax = other.getLimiteEfetividadeMax();
        if (this$limiteEfetividadeMax == null ? other$limiteEfetividadeMax != null : !this$limiteEfetividadeMax.equals(other$limiteEfetividadeMax)) return false;
        final java.lang.Object this$dataInicio = this.getDataInicio();
        final java.lang.Object other$dataInicio = other.getDataInicio();
        if (this$dataInicio == null ? other$dataInicio != null : !this$dataInicio.equals(other$dataInicio)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataTerminacao = this.getDataTerminacao();
        final java.lang.Object other$dataTerminacao = other.getDataTerminacao();
        if (this$dataTerminacao == null ? other$dataTerminacao != null : !this$dataTerminacao.equals(other$dataTerminacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$metodologiaAvaliacao = this.getMetodologiaAvaliacao();
        final java.lang.Object other$metodologiaAvaliacao = other.getMetodologiaAvaliacao();
        if (this$metodologiaAvaliacao == null ? other$metodologiaAvaliacao != null : !this$metodologiaAvaliacao.equals(other$metodologiaAvaliacao)) return false;
        final java.lang.Object this$frequenciaAvaliacao = this.getFrequenciaAvaliacao();
        final java.lang.Object other$frequenciaAvaliacao = other.getFrequenciaAvaliacao();
        if (this$frequenciaAvaliacao == null ? other$frequenciaAvaliacao != null : !this$frequenciaAvaliacao.equals(other$frequenciaAvaliacao)) return false;
        final java.lang.Object this$dataUltimaAvaliacao = this.getDataUltimaAvaliacao();
        final java.lang.Object other$dataUltimaAvaliacao = other.getDataUltimaAvaliacao();
        if (this$dataUltimaAvaliacao == null ? other$dataUltimaAvaliacao != null : !this$dataUltimaAvaliacao.equals(other$dataUltimaAvaliacao)) return false;
        final java.lang.Object this$resultadoHedge = this.getResultadoHedge();
        final java.lang.Object other$resultadoHedge = other.getResultadoHedge();
        if (this$resultadoHedge == null ? other$resultadoHedge != null : !this$resultadoHedge.equals(other$resultadoHedge)) return false;
        final java.lang.Object this$resultadoNaoEfetivo = this.getResultadoNaoEfetivo();
        final java.lang.Object other$resultadoNaoEfetivo = other.getResultadoNaoEfetivo();
        if (this$resultadoNaoEfetivo == null ? other$resultadoNaoEfetivo != null : !this$resultadoNaoEfetivo.equals(other$resultadoNaoEfetivo)) return false;
        final java.lang.Object this$documentacao = this.getDocumentacao();
        final java.lang.Object other$documentacao = other.getDocumentacao();
        if (this$documentacao == null ? other$documentacao != null : !this$documentacao.equals(other$documentacao)) return false;
        final java.lang.Object this$parametrosHedge = this.getParametrosHedge();
        final java.lang.Object other$parametrosHedge = other.getParametrosHedge();
        if (this$parametrosHedge == null ? other$parametrosHedge != null : !this$parametrosHedge.equals(other$parametrosHedge)) return false;
        final java.lang.Object this$riscoHedgeado = this.getRiscoHedgeado();
        final java.lang.Object other$riscoHedgeado = other.getRiscoHedgeado();
        if (this$riscoHedgeado == null ? other$riscoHedgeado != null : !this$riscoHedgeado.equals(other$riscoHedgeado)) return false;
        final java.lang.Object this$instrumentoDerivativo = this.getInstrumentoDerivativo();
        final java.lang.Object other$instrumentoDerivativo = other.getInstrumentoDerivativo();
        if (this$instrumentoDerivativo == null ? other$instrumentoDerivativo != null : !this$instrumentoDerivativo.equals(other$instrumentoDerivativo)) return false;
        final java.lang.Object this$valorNocional = this.getValorNocional();
        final java.lang.Object other$valorNocional = other.getValorNocional();
        if (this$valorNocional == null ? other$valorNocional != null : !this$valorNocional.equals(other$valorNocional)) return false;
        final java.lang.Object this$moedaHedge = this.getMoedaHedge();
        final java.lang.Object other$moedaHedge = other.getMoedaHedge();
        if (this$moedaHedge == null ? other$moedaHedge != null : !this$moedaHedge.equals(other$moedaHedge)) return false;
        final java.lang.Object this$taxaHedge = this.getTaxaHedge();
        final java.lang.Object other$taxaHedge = other.getTaxaHedge();
        if (this$taxaHedge == null ? other$taxaHedge != null : !this$taxaHedge.equals(other$taxaHedge)) return false;
        final java.lang.Object this$usuarioResponsavel = this.getUsuarioResponsavel();
        final java.lang.Object other$usuarioResponsavel = other.getUsuarioResponsavel();
        if (this$usuarioResponsavel == null ? other$usuarioResponsavel != null : !this$usuarioResponsavel.equals(other$usuarioResponsavel)) return false;
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
        return other instanceof HedgeAccounting;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaHedgeId = this.getContaHedgeId();
        result = result * PRIME + ($contaHedgeId == null ? 43 : $contaHedgeId.hashCode());
        final java.lang.Object $contaResultadoHedgeId = this.getContaResultadoHedgeId();
        result = result * PRIME + ($contaResultadoHedgeId == null ? 43 : $contaResultadoHedgeId.hashCode());
        final java.lang.Object $contaNaoEfetivoId = this.getContaNaoEfetivoId();
        result = result * PRIME + ($contaNaoEfetivoId == null ? 43 : $contaNaoEfetivoId.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoHedge = this.getCodigoHedge();
        result = result * PRIME + ($codigoHedge == null ? 43 : $codigoHedge.hashCode());
        final java.lang.Object $nomeHedge = this.getNomeHedge();
        result = result * PRIME + ($nomeHedge == null ? 43 : $nomeHedge.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoHedge = this.getTipoHedge();
        result = result * PRIME + ($tipoHedge == null ? 43 : $tipoHedge.hashCode());
        final java.lang.Object $categoriaHedge = this.getCategoriaHedge();
        result = result * PRIME + ($categoriaHedge == null ? 43 : $categoriaHedge.hashCode());
        final java.lang.Object $instrumentoHedgeado = this.getInstrumentoHedgeado();
        result = result * PRIME + ($instrumentoHedgeado == null ? 43 : $instrumentoHedgeado.hashCode());
        final java.lang.Object $instrumentoHedge = this.getInstrumentoHedge();
        result = result * PRIME + ($instrumentoHedge == null ? 43 : $instrumentoHedge.hashCode());
        final java.lang.Object $valorExposicao = this.getValorExposicao();
        result = result * PRIME + ($valorExposicao == null ? 43 : $valorExposicao.hashCode());
        final java.lang.Object $valorHedge = this.getValorHedge();
        result = result * PRIME + ($valorHedge == null ? 43 : $valorHedge.hashCode());
        final java.lang.Object $proporcaoHedge = this.getProporcaoHedge();
        result = result * PRIME + ($proporcaoHedge == null ? 43 : $proporcaoHedge.hashCode());
        final java.lang.Object $efetividadeHedge = this.getEfetividadeHedge();
        result = result * PRIME + ($efetividadeHedge == null ? 43 : $efetividadeHedge.hashCode());
        final java.lang.Object $limiteEfetividadeMin = this.getLimiteEfetividadeMin();
        result = result * PRIME + ($limiteEfetividadeMin == null ? 43 : $limiteEfetividadeMin.hashCode());
        final java.lang.Object $limiteEfetividadeMax = this.getLimiteEfetividadeMax();
        result = result * PRIME + ($limiteEfetividadeMax == null ? 43 : $limiteEfetividadeMax.hashCode());
        final java.lang.Object $dataInicio = this.getDataInicio();
        result = result * PRIME + ($dataInicio == null ? 43 : $dataInicio.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataTerminacao = this.getDataTerminacao();
        result = result * PRIME + ($dataTerminacao == null ? 43 : $dataTerminacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $metodologiaAvaliacao = this.getMetodologiaAvaliacao();
        result = result * PRIME + ($metodologiaAvaliacao == null ? 43 : $metodologiaAvaliacao.hashCode());
        final java.lang.Object $frequenciaAvaliacao = this.getFrequenciaAvaliacao();
        result = result * PRIME + ($frequenciaAvaliacao == null ? 43 : $frequenciaAvaliacao.hashCode());
        final java.lang.Object $dataUltimaAvaliacao = this.getDataUltimaAvaliacao();
        result = result * PRIME + ($dataUltimaAvaliacao == null ? 43 : $dataUltimaAvaliacao.hashCode());
        final java.lang.Object $resultadoHedge = this.getResultadoHedge();
        result = result * PRIME + ($resultadoHedge == null ? 43 : $resultadoHedge.hashCode());
        final java.lang.Object $resultadoNaoEfetivo = this.getResultadoNaoEfetivo();
        result = result * PRIME + ($resultadoNaoEfetivo == null ? 43 : $resultadoNaoEfetivo.hashCode());
        final java.lang.Object $documentacao = this.getDocumentacao();
        result = result * PRIME + ($documentacao == null ? 43 : $documentacao.hashCode());
        final java.lang.Object $parametrosHedge = this.getParametrosHedge();
        result = result * PRIME + ($parametrosHedge == null ? 43 : $parametrosHedge.hashCode());
        final java.lang.Object $riscoHedgeado = this.getRiscoHedgeado();
        result = result * PRIME + ($riscoHedgeado == null ? 43 : $riscoHedgeado.hashCode());
        final java.lang.Object $instrumentoDerivativo = this.getInstrumentoDerivativo();
        result = result * PRIME + ($instrumentoDerivativo == null ? 43 : $instrumentoDerivativo.hashCode());
        final java.lang.Object $valorNocional = this.getValorNocional();
        result = result * PRIME + ($valorNocional == null ? 43 : $valorNocional.hashCode());
        final java.lang.Object $moedaHedge = this.getMoedaHedge();
        result = result * PRIME + ($moedaHedge == null ? 43 : $moedaHedge.hashCode());
        final java.lang.Object $taxaHedge = this.getTaxaHedge();
        result = result * PRIME + ($taxaHedge == null ? 43 : $taxaHedge.hashCode());
        final java.lang.Object $usuarioResponsavel = this.getUsuarioResponsavel();
        result = result * PRIME + ($usuarioResponsavel == null ? 43 : $usuarioResponsavel.hashCode());
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
        return "HedgeAccounting(id=" + this.getId() + ", codigoHedge=" + this.getCodigoHedge() + ", nomeHedge=" + this.getNomeHedge() + ", descricao=" + this.getDescricao() + ", tipoHedge=" + this.getTipoHedge() + ", categoriaHedge=" + this.getCategoriaHedge() + ", instrumentoHedgeado=" + this.getInstrumentoHedgeado() + ", instrumentoHedge=" + this.getInstrumentoHedge() + ", valorExposicao=" + this.getValorExposicao() + ", valorHedge=" + this.getValorHedge() + ", proporcaoHedge=" + this.getProporcaoHedge() + ", efetividadeHedge=" + this.getEfetividadeHedge() + ", limiteEfetividadeMin=" + this.getLimiteEfetividadeMin() + ", limiteEfetividadeMax=" + this.getLimiteEfetividadeMax() + ", dataInicio=" + this.getDataInicio() + ", dataVencimento=" + this.getDataVencimento() + ", dataTerminacao=" + this.getDataTerminacao() + ", status=" + this.getStatus() + ", metodologiaAvaliacao=" + this.getMetodologiaAvaliacao() + ", frequenciaAvaliacao=" + this.getFrequenciaAvaliacao() + ", dataUltimaAvaliacao=" + this.getDataUltimaAvaliacao() + ", resultadoHedge=" + this.getResultadoHedge() + ", resultadoNaoEfetivo=" + this.getResultadoNaoEfetivo() + ", contaHedgeId=" + this.getContaHedgeId() + ", contaResultadoHedgeId=" + this.getContaResultadoHedgeId() + ", contaNaoEfetivoId=" + this.getContaNaoEfetivoId() + ", documentacao=" + this.getDocumentacao() + ", parametrosHedge=" + this.getParametrosHedge() + ", riscoHedgeado=" + this.getRiscoHedgeado() + ", instrumentoDerivativo=" + this.getInstrumentoDerivativo() + ", valorNocional=" + this.getValorNocional() + ", moedaHedge=" + this.getMoedaHedge() + ", taxaHedge=" + this.getTaxaHedge() + ", usuarioResponsavel=" + this.getUsuarioResponsavel() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public HedgeAccounting() {
    }

    @java.lang.SuppressWarnings("all")
    public HedgeAccounting(final Long id, final String codigoHedge, final String nomeHedge, final String descricao, final TipoHedge tipoHedge, final CategoriaHedge categoriaHedge, final InstrumentoFinanceiro instrumentoHedgeado, final InstrumentoFinanceiro instrumentoHedge, final BigDecimal valorExposicao, final BigDecimal valorHedge, final BigDecimal proporcaoHedge, final BigDecimal efetividadeHedge, final BigDecimal limiteEfetividadeMin, final BigDecimal limiteEfetividadeMax, final LocalDateTime dataInicio, final LocalDateTime dataVencimento, final LocalDateTime dataTerminacao, final StatusHedge status, final String metodologiaAvaliacao, final String frequenciaAvaliacao, final LocalDateTime dataUltimaAvaliacao, final BigDecimal resultadoHedge, final BigDecimal resultadoNaoEfetivo, final Long contaHedgeId, final Long contaResultadoHedgeId, final Long contaNaoEfetivoId, final String documentacao, final String parametrosHedge, final String riscoHedgeado, final String instrumentoDerivativo, final BigDecimal valorNocional, final String moedaHedge, final BigDecimal taxaHedge, final String usuarioResponsavel, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoHedge = codigoHedge;
        this.nomeHedge = nomeHedge;
        this.descricao = descricao;
        this.tipoHedge = tipoHedge;
        this.categoriaHedge = categoriaHedge;
        this.instrumentoHedgeado = instrumentoHedgeado;
        this.instrumentoHedge = instrumentoHedge;
        this.valorExposicao = valorExposicao;
        this.valorHedge = valorHedge;
        this.proporcaoHedge = proporcaoHedge;
        this.efetividadeHedge = efetividadeHedge;
        this.limiteEfetividadeMin = limiteEfetividadeMin;
        this.limiteEfetividadeMax = limiteEfetividadeMax;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataVencimento;
        this.dataTerminacao = dataTerminacao;
        this.status = status;
        this.metodologiaAvaliacao = metodologiaAvaliacao;
        this.frequenciaAvaliacao = frequenciaAvaliacao;
        this.dataUltimaAvaliacao = dataUltimaAvaliacao;
        this.resultadoHedge = resultadoHedge;
        this.resultadoNaoEfetivo = resultadoNaoEfetivo;
        this.contaHedgeId = contaHedgeId;
        this.contaResultadoHedgeId = contaResultadoHedgeId;
        this.contaNaoEfetivoId = contaNaoEfetivoId;
        this.documentacao = documentacao;
        this.parametrosHedge = parametrosHedge;
        this.riscoHedgeado = riscoHedgeado;
        this.instrumentoDerivativo = instrumentoDerivativo;
        this.valorNocional = valorNocional;
        this.moedaHedge = moedaHedge;
        this.taxaHedge = taxaHedge;
        this.usuarioResponsavel = usuarioResponsavel;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
