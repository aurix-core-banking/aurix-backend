package com.aurix.platform.cambio.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa a taxa SELIC
 * 
 * Armazena histórico e valores atuais da taxa básica de juros do BACEN
 */
@Entity
@Table(name = "taxa_selic", schema = "aurix")
public class TaxaSelic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_referencia", nullable = false, unique = true)
    private LocalDate dataReferencia;
    @Column(name = "valor_taxa", nullable = false, precision = 8, scale = 4)
    private BigDecimal valorTaxa;
    @Column(name = "valor_taxa_anualizada", precision = 8, scale = 4)
    private BigDecimal valorTaxaAnualizada;
    @Column(name = "codigo_serie_bacen", length = 10)
    private String codigoSerieBacen;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_taxa", nullable = false)
    private TipoTaxa tipoTaxa;
    @Column(name = "fonte_dados", length = 100)
    private String fonteDados;
    @Column(name = "data_atualizacao_bacen")
    private LocalDateTime dataAtualizacaoBacen;
    @Column(name = "proxima_reuniao_copom")
    private LocalDate proximaReuniaoCopom;
    @Column(name = "data_ultima_reuniao_copom")
    private LocalDate dataUltimaReuniaoCopom;
    @Column(name = "decisao_copom", length = 500)
    private String decisaoCopom;
    @Column(name = "justificativa_copom", length = 1000)
    private String justificativaCopom;
    @Column(name = "impacto_inflacao", precision = 8, scale = 4)
    private BigDecimal impactoInflacao;
    @Column(name = "impacto_crescimento", precision = 8, scale = 4)
    private BigDecimal impactoCrescimento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "previsao_proximos_meses", columnDefinition = "jsonb")
    private String previsaoProximosMeses;
    @Column(name = "tendencia", length = 20)
    private String tendencia;
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
     * Tipo de taxa SELIC
     */
    public enum TipoTaxa {
        SELIC_OVER,  // Taxa SELIC Over
        SELIC_META,  // Taxa SELIC Meta
        SELIC_EFETIVA,  // Taxa SELIC Efetiva
        SELIC_ANUALIZADA,  // Taxa SELIC Anualizada
        SELIC_PROJETADA // Taxa SELIC Projetada
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class TaxaSelicBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTaxa;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTaxaAnualizada;
        @java.lang.SuppressWarnings("all")
        private String codigoSerieBacen;
        @java.lang.SuppressWarnings("all")
        private TipoTaxa tipoTaxa;
        @java.lang.SuppressWarnings("all")
        private String fonteDados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacaoBacen;
        @java.lang.SuppressWarnings("all")
        private LocalDate proximaReuniaoCopom;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataUltimaReuniaoCopom;
        @java.lang.SuppressWarnings("all")
        private String decisaoCopom;
        @java.lang.SuppressWarnings("all")
        private String justificativaCopom;
        @java.lang.SuppressWarnings("all")
        private BigDecimal impactoInflacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal impactoCrescimento;
        @java.lang.SuppressWarnings("all")
        private String previsaoProximosMeses;
        @java.lang.SuppressWarnings("all")
        private String tendencia;
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
        TaxaSelicBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder valorTaxa(final BigDecimal valorTaxa) {
            this.valorTaxa = valorTaxa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder valorTaxaAnualizada(final BigDecimal valorTaxaAnualizada) {
            this.valorTaxaAnualizada = valorTaxaAnualizada;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder codigoSerieBacen(final String codigoSerieBacen) {
            this.codigoSerieBacen = codigoSerieBacen;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder tipoTaxa(final TipoTaxa tipoTaxa) {
            this.tipoTaxa = tipoTaxa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder fonteDados(final String fonteDados) {
            this.fonteDados = fonteDados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder dataAtualizacaoBacen(final LocalDateTime dataAtualizacaoBacen) {
            this.dataAtualizacaoBacen = dataAtualizacaoBacen;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder proximaReuniaoCopom(final LocalDate proximaReuniaoCopom) {
            this.proximaReuniaoCopom = proximaReuniaoCopom;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder dataUltimaReuniaoCopom(final LocalDate dataUltimaReuniaoCopom) {
            this.dataUltimaReuniaoCopom = dataUltimaReuniaoCopom;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder decisaoCopom(final String decisaoCopom) {
            this.decisaoCopom = decisaoCopom;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder justificativaCopom(final String justificativaCopom) {
            this.justificativaCopom = justificativaCopom;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder impactoInflacao(final BigDecimal impactoInflacao) {
            this.impactoInflacao = impactoInflacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder impactoCrescimento(final BigDecimal impactoCrescimento) {
            this.impactoCrescimento = impactoCrescimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder previsaoProximosMeses(final String previsaoProximosMeses) {
            this.previsaoProximosMeses = previsaoProximosMeses;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder tendencia(final String tendencia) {
            this.tendencia = tendencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TaxaSelic.TaxaSelicBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TaxaSelic build() {
            return new TaxaSelic(this.id, this.dataReferencia, this.valorTaxa, this.valorTaxaAnualizada, this.codigoSerieBacen, this.tipoTaxa, this.fonteDados, this.dataAtualizacaoBacen, this.proximaReuniaoCopom, this.dataUltimaReuniaoCopom, this.decisaoCopom, this.justificativaCopom, this.impactoInflacao, this.impactoCrescimento, this.previsaoProximosMeses, this.tendencia, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TaxaSelic.TaxaSelicBuilder(id=" + this.id + ", dataReferencia=" + this.dataReferencia + ", valorTaxa=" + this.valorTaxa + ", valorTaxaAnualizada=" + this.valorTaxaAnualizada + ", codigoSerieBacen=" + this.codigoSerieBacen + ", tipoTaxa=" + this.tipoTaxa + ", fonteDados=" + this.fonteDados + ", dataAtualizacaoBacen=" + this.dataAtualizacaoBacen + ", proximaReuniaoCopom=" + this.proximaReuniaoCopom + ", dataUltimaReuniaoCopom=" + this.dataUltimaReuniaoCopom + ", decisaoCopom=" + this.decisaoCopom + ", justificativaCopom=" + this.justificativaCopom + ", impactoInflacao=" + this.impactoInflacao + ", impactoCrescimento=" + this.impactoCrescimento + ", previsaoProximosMeses=" + this.previsaoProximosMeses + ", tendencia=" + this.tendencia + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TaxaSelic.TaxaSelicBuilder builder() {
        return new TaxaSelic.TaxaSelicBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTaxa() {
        return this.valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTaxaAnualizada() {
        return this.valorTaxaAnualizada;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSerieBacen() {
        return this.codigoSerieBacen;
    }

    @java.lang.SuppressWarnings("all")
    public TipoTaxa getTipoTaxa() {
        return this.tipoTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public String getFonteDados() {
        return this.fonteDados;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacaoBacen() {
        return this.dataAtualizacaoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getProximaReuniaoCopom() {
        return this.proximaReuniaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataUltimaReuniaoCopom() {
        return this.dataUltimaReuniaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public String getDecisaoCopom() {
        return this.decisaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativaCopom() {
        return this.justificativaCopom;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getImpactoInflacao() {
        return this.impactoInflacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getImpactoCrescimento() {
        return this.impactoCrescimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getPrevisaoProximosMeses() {
        return this.previsaoProximosMeses;
    }

    @java.lang.SuppressWarnings("all")
    public String getTendencia() {
        return this.tendencia;
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
    public void setDataReferencia(final LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTaxa(final BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTaxaAnualizada(final BigDecimal valorTaxaAnualizada) {
        this.valorTaxaAnualizada = valorTaxaAnualizada;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSerieBacen(final String codigoSerieBacen) {
        this.codigoSerieBacen = codigoSerieBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoTaxa(final TipoTaxa tipoTaxa) {
        this.tipoTaxa = tipoTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public void setFonteDados(final String fonteDados) {
        this.fonteDados = fonteDados;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacaoBacen(final LocalDateTime dataAtualizacaoBacen) {
        this.dataAtualizacaoBacen = dataAtualizacaoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setProximaReuniaoCopom(final LocalDate proximaReuniaoCopom) {
        this.proximaReuniaoCopom = proximaReuniaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUltimaReuniaoCopom(final LocalDate dataUltimaReuniaoCopom) {
        this.dataUltimaReuniaoCopom = dataUltimaReuniaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public void setDecisaoCopom(final String decisaoCopom) {
        this.decisaoCopom = decisaoCopom;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativaCopom(final String justificativaCopom) {
        this.justificativaCopom = justificativaCopom;
    }

    @java.lang.SuppressWarnings("all")
    public void setImpactoInflacao(final BigDecimal impactoInflacao) {
        this.impactoInflacao = impactoInflacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setImpactoCrescimento(final BigDecimal impactoCrescimento) {
        this.impactoCrescimento = impactoCrescimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrevisaoProximosMeses(final String previsaoProximosMeses) {
        this.previsaoProximosMeses = previsaoProximosMeses;
    }

    @java.lang.SuppressWarnings("all")
    public void setTendencia(final String tendencia) {
        this.tendencia = tendencia;
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
        if (!(o instanceof TaxaSelic)) return false;
        final TaxaSelic other = (TaxaSelic) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$valorTaxa = this.getValorTaxa();
        final java.lang.Object other$valorTaxa = other.getValorTaxa();
        if (this$valorTaxa == null ? other$valorTaxa != null : !this$valorTaxa.equals(other$valorTaxa)) return false;
        final java.lang.Object this$valorTaxaAnualizada = this.getValorTaxaAnualizada();
        final java.lang.Object other$valorTaxaAnualizada = other.getValorTaxaAnualizada();
        if (this$valorTaxaAnualizada == null ? other$valorTaxaAnualizada != null : !this$valorTaxaAnualizada.equals(other$valorTaxaAnualizada)) return false;
        final java.lang.Object this$codigoSerieBacen = this.getCodigoSerieBacen();
        final java.lang.Object other$codigoSerieBacen = other.getCodigoSerieBacen();
        if (this$codigoSerieBacen == null ? other$codigoSerieBacen != null : !this$codigoSerieBacen.equals(other$codigoSerieBacen)) return false;
        final java.lang.Object this$tipoTaxa = this.getTipoTaxa();
        final java.lang.Object other$tipoTaxa = other.getTipoTaxa();
        if (this$tipoTaxa == null ? other$tipoTaxa != null : !this$tipoTaxa.equals(other$tipoTaxa)) return false;
        final java.lang.Object this$fonteDados = this.getFonteDados();
        final java.lang.Object other$fonteDados = other.getFonteDados();
        if (this$fonteDados == null ? other$fonteDados != null : !this$fonteDados.equals(other$fonteDados)) return false;
        final java.lang.Object this$dataAtualizacaoBacen = this.getDataAtualizacaoBacen();
        final java.lang.Object other$dataAtualizacaoBacen = other.getDataAtualizacaoBacen();
        if (this$dataAtualizacaoBacen == null ? other$dataAtualizacaoBacen != null : !this$dataAtualizacaoBacen.equals(other$dataAtualizacaoBacen)) return false;
        final java.lang.Object this$proximaReuniaoCopom = this.getProximaReuniaoCopom();
        final java.lang.Object other$proximaReuniaoCopom = other.getProximaReuniaoCopom();
        if (this$proximaReuniaoCopom == null ? other$proximaReuniaoCopom != null : !this$proximaReuniaoCopom.equals(other$proximaReuniaoCopom)) return false;
        final java.lang.Object this$dataUltimaReuniaoCopom = this.getDataUltimaReuniaoCopom();
        final java.lang.Object other$dataUltimaReuniaoCopom = other.getDataUltimaReuniaoCopom();
        if (this$dataUltimaReuniaoCopom == null ? other$dataUltimaReuniaoCopom != null : !this$dataUltimaReuniaoCopom.equals(other$dataUltimaReuniaoCopom)) return false;
        final java.lang.Object this$decisaoCopom = this.getDecisaoCopom();
        final java.lang.Object other$decisaoCopom = other.getDecisaoCopom();
        if (this$decisaoCopom == null ? other$decisaoCopom != null : !this$decisaoCopom.equals(other$decisaoCopom)) return false;
        final java.lang.Object this$justificativaCopom = this.getJustificativaCopom();
        final java.lang.Object other$justificativaCopom = other.getJustificativaCopom();
        if (this$justificativaCopom == null ? other$justificativaCopom != null : !this$justificativaCopom.equals(other$justificativaCopom)) return false;
        final java.lang.Object this$impactoInflacao = this.getImpactoInflacao();
        final java.lang.Object other$impactoInflacao = other.getImpactoInflacao();
        if (this$impactoInflacao == null ? other$impactoInflacao != null : !this$impactoInflacao.equals(other$impactoInflacao)) return false;
        final java.lang.Object this$impactoCrescimento = this.getImpactoCrescimento();
        final java.lang.Object other$impactoCrescimento = other.getImpactoCrescimento();
        if (this$impactoCrescimento == null ? other$impactoCrescimento != null : !this$impactoCrescimento.equals(other$impactoCrescimento)) return false;
        final java.lang.Object this$previsaoProximosMeses = this.getPrevisaoProximosMeses();
        final java.lang.Object other$previsaoProximosMeses = other.getPrevisaoProximosMeses();
        if (this$previsaoProximosMeses == null ? other$previsaoProximosMeses != null : !this$previsaoProximosMeses.equals(other$previsaoProximosMeses)) return false;
        final java.lang.Object this$tendencia = this.getTendencia();
        final java.lang.Object other$tendencia = other.getTendencia();
        if (this$tendencia == null ? other$tendencia != null : !this$tendencia.equals(other$tendencia)) return false;
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
        return other instanceof TaxaSelic;
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
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $valorTaxa = this.getValorTaxa();
        result = result * PRIME + ($valorTaxa == null ? 43 : $valorTaxa.hashCode());
        final java.lang.Object $valorTaxaAnualizada = this.getValorTaxaAnualizada();
        result = result * PRIME + ($valorTaxaAnualizada == null ? 43 : $valorTaxaAnualizada.hashCode());
        final java.lang.Object $codigoSerieBacen = this.getCodigoSerieBacen();
        result = result * PRIME + ($codigoSerieBacen == null ? 43 : $codigoSerieBacen.hashCode());
        final java.lang.Object $tipoTaxa = this.getTipoTaxa();
        result = result * PRIME + ($tipoTaxa == null ? 43 : $tipoTaxa.hashCode());
        final java.lang.Object $fonteDados = this.getFonteDados();
        result = result * PRIME + ($fonteDados == null ? 43 : $fonteDados.hashCode());
        final java.lang.Object $dataAtualizacaoBacen = this.getDataAtualizacaoBacen();
        result = result * PRIME + ($dataAtualizacaoBacen == null ? 43 : $dataAtualizacaoBacen.hashCode());
        final java.lang.Object $proximaReuniaoCopom = this.getProximaReuniaoCopom();
        result = result * PRIME + ($proximaReuniaoCopom == null ? 43 : $proximaReuniaoCopom.hashCode());
        final java.lang.Object $dataUltimaReuniaoCopom = this.getDataUltimaReuniaoCopom();
        result = result * PRIME + ($dataUltimaReuniaoCopom == null ? 43 : $dataUltimaReuniaoCopom.hashCode());
        final java.lang.Object $decisaoCopom = this.getDecisaoCopom();
        result = result * PRIME + ($decisaoCopom == null ? 43 : $decisaoCopom.hashCode());
        final java.lang.Object $justificativaCopom = this.getJustificativaCopom();
        result = result * PRIME + ($justificativaCopom == null ? 43 : $justificativaCopom.hashCode());
        final java.lang.Object $impactoInflacao = this.getImpactoInflacao();
        result = result * PRIME + ($impactoInflacao == null ? 43 : $impactoInflacao.hashCode());
        final java.lang.Object $impactoCrescimento = this.getImpactoCrescimento();
        result = result * PRIME + ($impactoCrescimento == null ? 43 : $impactoCrescimento.hashCode());
        final java.lang.Object $previsaoProximosMeses = this.getPrevisaoProximosMeses();
        result = result * PRIME + ($previsaoProximosMeses == null ? 43 : $previsaoProximosMeses.hashCode());
        final java.lang.Object $tendencia = this.getTendencia();
        result = result * PRIME + ($tendencia == null ? 43 : $tendencia.hashCode());
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
        return "TaxaSelic(id=" + this.getId() + ", dataReferencia=" + this.getDataReferencia() + ", valorTaxa=" + this.getValorTaxa() + ", valorTaxaAnualizada=" + this.getValorTaxaAnualizada() + ", codigoSerieBacen=" + this.getCodigoSerieBacen() + ", tipoTaxa=" + this.getTipoTaxa() + ", fonteDados=" + this.getFonteDados() + ", dataAtualizacaoBacen=" + this.getDataAtualizacaoBacen() + ", proximaReuniaoCopom=" + this.getProximaReuniaoCopom() + ", dataUltimaReuniaoCopom=" + this.getDataUltimaReuniaoCopom() + ", decisaoCopom=" + this.getDecisaoCopom() + ", justificativaCopom=" + this.getJustificativaCopom() + ", impactoInflacao=" + this.getImpactoInflacao() + ", impactoCrescimento=" + this.getImpactoCrescimento() + ", previsaoProximosMeses=" + this.getPrevisaoProximosMeses() + ", tendencia=" + this.getTendencia() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TaxaSelic() {
    }

    @java.lang.SuppressWarnings("all")
    public TaxaSelic(final Long id, final LocalDate dataReferencia, final BigDecimal valorTaxa, final BigDecimal valorTaxaAnualizada, final String codigoSerieBacen, final TipoTaxa tipoTaxa, final String fonteDados, final LocalDateTime dataAtualizacaoBacen, final LocalDate proximaReuniaoCopom, final LocalDate dataUltimaReuniaoCopom, final String decisaoCopom, final String justificativaCopom, final BigDecimal impactoInflacao, final BigDecimal impactoCrescimento, final String previsaoProximosMeses, final String tendencia, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.dataReferencia = dataReferencia;
        this.valorTaxa = valorTaxa;
        this.valorTaxaAnualizada = valorTaxaAnualizada;
        this.codigoSerieBacen = codigoSerieBacen;
        this.tipoTaxa = tipoTaxa;
        this.fonteDados = fonteDados;
        this.dataAtualizacaoBacen = dataAtualizacaoBacen;
        this.proximaReuniaoCopom = proximaReuniaoCopom;
        this.dataUltimaReuniaoCopom = dataUltimaReuniaoCopom;
        this.decisaoCopom = decisaoCopom;
        this.justificativaCopom = justificativaCopom;
        this.impactoInflacao = impactoInflacao;
        this.impactoCrescimento = impactoCrescimento;
        this.previsaoProximosMeses = previsaoProximosMeses;
        this.tendencia = tendencia;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
