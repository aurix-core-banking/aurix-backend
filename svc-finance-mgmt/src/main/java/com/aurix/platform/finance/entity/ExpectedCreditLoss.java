package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o cálculo de Expected Credit Loss (ECL) conforme IFRS 9
 * 
 * Registra os cálculos de perda esperada de crédito para cada instrumento
 * financeiro, incluindo PD, LGD, EAD e o ECL resultante.
 */
@Entity
@Table(name = "expected_credit_loss", schema = "aurix")
public class ExpectedCreditLoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumento_id", nullable = false)
    private InstrumentoFinanceiro instrumento;
    @Column(name = "data_calculo", nullable = false)
    private LocalDate dataCalculo;
    @Enumerated(EnumType.STRING)
    @Column(name = "estagio", nullable = false)
    private EstagioDeterioracao estagio;
    @Column(name = "probability_default", precision = 8, scale = 6, nullable = false)
    private BigDecimal probabilityDefault; // PD
    @Column(name = "loss_given_default", precision = 8, scale = 6, nullable = false)
    private BigDecimal lossGivenDefault; // LGD
    @Column(name = "exposure_at_default", precision = 15, scale = 2, nullable = false)
    private BigDecimal exposureAtDefault; // EAD
    @Column(name = "expected_credit_loss", precision = 15, scale = 2, nullable = false)
    private BigDecimal expectedCreditLoss; // ECL = PD × LGD × EAD
    @Column(name = "provisao_12_meses", precision = 15, scale = 2)
    private BigDecimal provisao12Meses;
    @Column(name = "provisao_vida_util", precision = 15, scale = 2)
    private BigDecimal provisaoVidaUtil;
    @Column(name = "provisao_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal provisaoTotal;
    @Column(name = "metodologia_calculo", length = 100)
    private String metodologiaCalculo;
    @Column(name = "modelo_utilizado", length = 100)
    private String modeloUtilizado;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros_modelo", columnDefinition = "jsonb")
    private String parametrosModelo;
    @Column(name = "cenario_base", length = 50)
    private String cenarioBase;
    @Column(name = "cenario_otimista", length = 50)
    private String cenarioOtimista;
    @Column(name = "cenario_pessimista", length = 50)
    private String cenarioPessimista;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "peso_cenarios", columnDefinition = "jsonb")
    private String pesoCenarios;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "indicadores_deterioracao", columnDefinition = "jsonb")
    private String indicadoresDeterioracao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "historico_pagamentos", columnDefinition = "jsonb")
    private String historicoPagamentos;
    @Column(name = "score_credito")
    private Integer scoreCredito;
    @Column(name = "rating_interno", length = 10)
    private String ratingInterno;
    @Column(name = "rating_externo", length = 10)
    private String ratingExterno;
    @Column(name = "garantias_valor", precision = 15, scale = 2)
    private BigDecimal garantiasValor;
    @Column(name = "garantias_tipo", length = 100)
    private String garantiasTipo;
    @Column(name = "data_ultima_atualizacao", nullable = false)
    private LocalDateTime dataUltimaAtualizacao;
    @Column(name = "usuario_calculo", length = 100)
    private String usuarioCalculo;
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
     * Estágio de deterioração do crédito
     */
    public enum EstagioDeterioracao {
        ESTAGIO_1,  // Estágio 1 - 12 meses ECL
        ESTAGIO_2,  // Estágio 2 - Vida útil ECL (não deteriorado)
        ESTAGIO_3 // Estágio 3 - Vida útil ECL (deteriorado)
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class ExpectedCreditLossBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private InstrumentoFinanceiro instrumento;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataCalculo;
        @java.lang.SuppressWarnings("all")
        private EstagioDeterioracao estagio;
        @java.lang.SuppressWarnings("all")
        private BigDecimal probabilityDefault;
        @java.lang.SuppressWarnings("all")
        private BigDecimal lossGivenDefault;
        @java.lang.SuppressWarnings("all")
        private BigDecimal exposureAtDefault;
        @java.lang.SuppressWarnings("all")
        private BigDecimal expectedCreditLoss;
        @java.lang.SuppressWarnings("all")
        private BigDecimal provisao12Meses;
        @java.lang.SuppressWarnings("all")
        private BigDecimal provisaoVidaUtil;
        @java.lang.SuppressWarnings("all")
        private BigDecimal provisaoTotal;
        @java.lang.SuppressWarnings("all")
        private String metodologiaCalculo;
        @java.lang.SuppressWarnings("all")
        private String modeloUtilizado;
        @java.lang.SuppressWarnings("all")
        private String parametrosModelo;
        @java.lang.SuppressWarnings("all")
        private String cenarioBase;
        @java.lang.SuppressWarnings("all")
        private String cenarioOtimista;
        @java.lang.SuppressWarnings("all")
        private String cenarioPessimista;
        @java.lang.SuppressWarnings("all")
        private String pesoCenarios;
        @java.lang.SuppressWarnings("all")
        private String indicadoresDeterioracao;
        @java.lang.SuppressWarnings("all")
        private String historicoPagamentos;
        @java.lang.SuppressWarnings("all")
        private Integer scoreCredito;
        @java.lang.SuppressWarnings("all")
        private String ratingInterno;
        @java.lang.SuppressWarnings("all")
        private String ratingExterno;
        @java.lang.SuppressWarnings("all")
        private BigDecimal garantiasValor;
        @java.lang.SuppressWarnings("all")
        private String garantiasTipo;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataUltimaAtualizacao;
        @java.lang.SuppressWarnings("all")
        private String usuarioCalculo;
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
        ExpectedCreditLossBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder instrumento(final InstrumentoFinanceiro instrumento) {
            this.instrumento = instrumento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder dataCalculo(final LocalDate dataCalculo) {
            this.dataCalculo = dataCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder estagio(final EstagioDeterioracao estagio) {
            this.estagio = estagio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder probabilityDefault(final BigDecimal probabilityDefault) {
            this.probabilityDefault = probabilityDefault;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder lossGivenDefault(final BigDecimal lossGivenDefault) {
            this.lossGivenDefault = lossGivenDefault;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder exposureAtDefault(final BigDecimal exposureAtDefault) {
            this.exposureAtDefault = exposureAtDefault;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder expectedCreditLoss(final BigDecimal expectedCreditLoss) {
            this.expectedCreditLoss = expectedCreditLoss;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder provisao12Meses(final BigDecimal provisao12Meses) {
            this.provisao12Meses = provisao12Meses;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder provisaoVidaUtil(final BigDecimal provisaoVidaUtil) {
            this.provisaoVidaUtil = provisaoVidaUtil;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder provisaoTotal(final BigDecimal provisaoTotal) {
            this.provisaoTotal = provisaoTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder metodologiaCalculo(final String metodologiaCalculo) {
            this.metodologiaCalculo = metodologiaCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder modeloUtilizado(final String modeloUtilizado) {
            this.modeloUtilizado = modeloUtilizado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder parametrosModelo(final String parametrosModelo) {
            this.parametrosModelo = parametrosModelo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder cenarioBase(final String cenarioBase) {
            this.cenarioBase = cenarioBase;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder cenarioOtimista(final String cenarioOtimista) {
            this.cenarioOtimista = cenarioOtimista;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder cenarioPessimista(final String cenarioPessimista) {
            this.cenarioPessimista = cenarioPessimista;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder pesoCenarios(final String pesoCenarios) {
            this.pesoCenarios = pesoCenarios;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder indicadoresDeterioracao(final String indicadoresDeterioracao) {
            this.indicadoresDeterioracao = indicadoresDeterioracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder historicoPagamentos(final String historicoPagamentos) {
            this.historicoPagamentos = historicoPagamentos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder scoreCredito(final Integer scoreCredito) {
            this.scoreCredito = scoreCredito;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder ratingInterno(final String ratingInterno) {
            this.ratingInterno = ratingInterno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder ratingExterno(final String ratingExterno) {
            this.ratingExterno = ratingExterno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder garantiasValor(final BigDecimal garantiasValor) {
            this.garantiasValor = garantiasValor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder garantiasTipo(final String garantiasTipo) {
            this.garantiasTipo = garantiasTipo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder dataUltimaAtualizacao(final LocalDateTime dataUltimaAtualizacao) {
            this.dataUltimaAtualizacao = dataUltimaAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder usuarioCalculo(final String usuarioCalculo) {
            this.usuarioCalculo = usuarioCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss.ExpectedCreditLossBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ExpectedCreditLoss build() {
            return new ExpectedCreditLoss(this.id, this.instrumento, this.dataCalculo, this.estagio, this.probabilityDefault, this.lossGivenDefault, this.exposureAtDefault, this.expectedCreditLoss, this.provisao12Meses, this.provisaoVidaUtil, this.provisaoTotal, this.metodologiaCalculo, this.modeloUtilizado, this.parametrosModelo, this.cenarioBase, this.cenarioOtimista, this.cenarioPessimista, this.pesoCenarios, this.indicadoresDeterioracao, this.historicoPagamentos, this.scoreCredito, this.ratingInterno, this.ratingExterno, this.garantiasValor, this.garantiasTipo, this.dataUltimaAtualizacao, this.usuarioCalculo, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ExpectedCreditLoss.ExpectedCreditLossBuilder(id=" + this.id + ", instrumento=" + this.instrumento + ", dataCalculo=" + this.dataCalculo + ", estagio=" + this.estagio + ", probabilityDefault=" + this.probabilityDefault + ", lossGivenDefault=" + this.lossGivenDefault + ", exposureAtDefault=" + this.exposureAtDefault + ", expectedCreditLoss=" + this.expectedCreditLoss + ", provisao12Meses=" + this.provisao12Meses + ", provisaoVidaUtil=" + this.provisaoVidaUtil + ", provisaoTotal=" + this.provisaoTotal + ", metodologiaCalculo=" + this.metodologiaCalculo + ", modeloUtilizado=" + this.modeloUtilizado + ", parametrosModelo=" + this.parametrosModelo + ", cenarioBase=" + this.cenarioBase + ", cenarioOtimista=" + this.cenarioOtimista + ", cenarioPessimista=" + this.cenarioPessimista + ", pesoCenarios=" + this.pesoCenarios + ", indicadoresDeterioracao=" + this.indicadoresDeterioracao + ", historicoPagamentos=" + this.historicoPagamentos + ", scoreCredito=" + this.scoreCredito + ", ratingInterno=" + this.ratingInterno + ", ratingExterno=" + this.ratingExterno + ", garantiasValor=" + this.garantiasValor + ", garantiasTipo=" + this.garantiasTipo + ", dataUltimaAtualizacao=" + this.dataUltimaAtualizacao + ", usuarioCalculo=" + this.usuarioCalculo + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ExpectedCreditLoss.ExpectedCreditLossBuilder builder() {
        return new ExpectedCreditLoss.ExpectedCreditLossBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public InstrumentoFinanceiro getInstrumento() {
        return this.instrumento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataCalculo() {
        return this.dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public EstagioDeterioracao getEstagio() {
        return this.estagio;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProbabilityDefault() {
        return this.probabilityDefault;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLossGivenDefault() {
        return this.lossGivenDefault;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getExposureAtDefault() {
        return this.exposureAtDefault;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getExpectedCreditLoss() {
        return this.expectedCreditLoss;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProvisao12Meses() {
        return this.provisao12Meses;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProvisaoVidaUtil() {
        return this.provisaoVidaUtil;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProvisaoTotal() {
        return this.provisaoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetodologiaCalculo() {
        return this.metodologiaCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public String getModeloUtilizado() {
        return this.modeloUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public String getParametrosModelo() {
        return this.parametrosModelo;
    }

    @java.lang.SuppressWarnings("all")
    public String getCenarioBase() {
        return this.cenarioBase;
    }

    @java.lang.SuppressWarnings("all")
    public String getCenarioOtimista() {
        return this.cenarioOtimista;
    }

    @java.lang.SuppressWarnings("all")
    public String getCenarioPessimista() {
        return this.cenarioPessimista;
    }

    @java.lang.SuppressWarnings("all")
    public String getPesoCenarios() {
        return this.pesoCenarios;
    }

    @java.lang.SuppressWarnings("all")
    public String getIndicadoresDeterioracao() {
        return this.indicadoresDeterioracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getHistoricoPagamentos() {
        return this.historicoPagamentos;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreCredito() {
        return this.scoreCredito;
    }

    @java.lang.SuppressWarnings("all")
    public String getRatingInterno() {
        return this.ratingInterno;
    }

    @java.lang.SuppressWarnings("all")
    public String getRatingExterno() {
        return this.ratingExterno;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getGarantiasValor() {
        return this.garantiasValor;
    }

    @java.lang.SuppressWarnings("all")
    public String getGarantiasTipo() {
        return this.garantiasTipo;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataUltimaAtualizacao() {
        return this.dataUltimaAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioCalculo() {
        return this.usuarioCalculo;
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
    public void setInstrumento(final InstrumentoFinanceiro instrumento) {
        this.instrumento = instrumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCalculo(final LocalDate dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setEstagio(final EstagioDeterioracao estagio) {
        this.estagio = estagio;
    }

    @java.lang.SuppressWarnings("all")
    public void setProbabilityDefault(final BigDecimal probabilityDefault) {
        this.probabilityDefault = probabilityDefault;
    }

    @java.lang.SuppressWarnings("all")
    public void setLossGivenDefault(final BigDecimal lossGivenDefault) {
        this.lossGivenDefault = lossGivenDefault;
    }

    @java.lang.SuppressWarnings("all")
    public void setExposureAtDefault(final BigDecimal exposureAtDefault) {
        this.exposureAtDefault = exposureAtDefault;
    }

    @java.lang.SuppressWarnings("all")
    public void setExpectedCreditLoss(final BigDecimal expectedCreditLoss) {
        this.expectedCreditLoss = expectedCreditLoss;
    }

    @java.lang.SuppressWarnings("all")
    public void setProvisao12Meses(final BigDecimal provisao12Meses) {
        this.provisao12Meses = provisao12Meses;
    }

    @java.lang.SuppressWarnings("all")
    public void setProvisaoVidaUtil(final BigDecimal provisaoVidaUtil) {
        this.provisaoVidaUtil = provisaoVidaUtil;
    }

    @java.lang.SuppressWarnings("all")
    public void setProvisaoTotal(final BigDecimal provisaoTotal) {
        this.provisaoTotal = provisaoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetodologiaCalculo(final String metodologiaCalculo) {
        this.metodologiaCalculo = metodologiaCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setModeloUtilizado(final String modeloUtilizado) {
        this.modeloUtilizado = modeloUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setParametrosModelo(final String parametrosModelo) {
        this.parametrosModelo = parametrosModelo;
    }

    @java.lang.SuppressWarnings("all")
    public void setCenarioBase(final String cenarioBase) {
        this.cenarioBase = cenarioBase;
    }

    @java.lang.SuppressWarnings("all")
    public void setCenarioOtimista(final String cenarioOtimista) {
        this.cenarioOtimista = cenarioOtimista;
    }

    @java.lang.SuppressWarnings("all")
    public void setCenarioPessimista(final String cenarioPessimista) {
        this.cenarioPessimista = cenarioPessimista;
    }

    @java.lang.SuppressWarnings("all")
    public void setPesoCenarios(final String pesoCenarios) {
        this.pesoCenarios = pesoCenarios;
    }

    @java.lang.SuppressWarnings("all")
    public void setIndicadoresDeterioracao(final String indicadoresDeterioracao) {
        this.indicadoresDeterioracao = indicadoresDeterioracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistoricoPagamentos(final String historicoPagamentos) {
        this.historicoPagamentos = historicoPagamentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreCredito(final Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setRatingInterno(final String ratingInterno) {
        this.ratingInterno = ratingInterno;
    }

    @java.lang.SuppressWarnings("all")
    public void setRatingExterno(final String ratingExterno) {
        this.ratingExterno = ratingExterno;
    }

    @java.lang.SuppressWarnings("all")
    public void setGarantiasValor(final BigDecimal garantiasValor) {
        this.garantiasValor = garantiasValor;
    }

    @java.lang.SuppressWarnings("all")
    public void setGarantiasTipo(final String garantiasTipo) {
        this.garantiasTipo = garantiasTipo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUltimaAtualizacao(final LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioCalculo(final String usuarioCalculo) {
        this.usuarioCalculo = usuarioCalculo;
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
        if (!(o instanceof ExpectedCreditLoss)) return false;
        final ExpectedCreditLoss other = (ExpectedCreditLoss) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$scoreCredito = this.getScoreCredito();
        final java.lang.Object other$scoreCredito = other.getScoreCredito();
        if (this$scoreCredito == null ? other$scoreCredito != null : !this$scoreCredito.equals(other$scoreCredito)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$instrumento = this.getInstrumento();
        final java.lang.Object other$instrumento = other.getInstrumento();
        if (this$instrumento == null ? other$instrumento != null : !this$instrumento.equals(other$instrumento)) return false;
        final java.lang.Object this$dataCalculo = this.getDataCalculo();
        final java.lang.Object other$dataCalculo = other.getDataCalculo();
        if (this$dataCalculo == null ? other$dataCalculo != null : !this$dataCalculo.equals(other$dataCalculo)) return false;
        final java.lang.Object this$estagio = this.getEstagio();
        final java.lang.Object other$estagio = other.getEstagio();
        if (this$estagio == null ? other$estagio != null : !this$estagio.equals(other$estagio)) return false;
        final java.lang.Object this$probabilityDefault = this.getProbabilityDefault();
        final java.lang.Object other$probabilityDefault = other.getProbabilityDefault();
        if (this$probabilityDefault == null ? other$probabilityDefault != null : !this$probabilityDefault.equals(other$probabilityDefault)) return false;
        final java.lang.Object this$lossGivenDefault = this.getLossGivenDefault();
        final java.lang.Object other$lossGivenDefault = other.getLossGivenDefault();
        if (this$lossGivenDefault == null ? other$lossGivenDefault != null : !this$lossGivenDefault.equals(other$lossGivenDefault)) return false;
        final java.lang.Object this$exposureAtDefault = this.getExposureAtDefault();
        final java.lang.Object other$exposureAtDefault = other.getExposureAtDefault();
        if (this$exposureAtDefault == null ? other$exposureAtDefault != null : !this$exposureAtDefault.equals(other$exposureAtDefault)) return false;
        final java.lang.Object this$expectedCreditLoss = this.getExpectedCreditLoss();
        final java.lang.Object other$expectedCreditLoss = other.getExpectedCreditLoss();
        if (this$expectedCreditLoss == null ? other$expectedCreditLoss != null : !this$expectedCreditLoss.equals(other$expectedCreditLoss)) return false;
        final java.lang.Object this$provisao12Meses = this.getProvisao12Meses();
        final java.lang.Object other$provisao12Meses = other.getProvisao12Meses();
        if (this$provisao12Meses == null ? other$provisao12Meses != null : !this$provisao12Meses.equals(other$provisao12Meses)) return false;
        final java.lang.Object this$provisaoVidaUtil = this.getProvisaoVidaUtil();
        final java.lang.Object other$provisaoVidaUtil = other.getProvisaoVidaUtil();
        if (this$provisaoVidaUtil == null ? other$provisaoVidaUtil != null : !this$provisaoVidaUtil.equals(other$provisaoVidaUtil)) return false;
        final java.lang.Object this$provisaoTotal = this.getProvisaoTotal();
        final java.lang.Object other$provisaoTotal = other.getProvisaoTotal();
        if (this$provisaoTotal == null ? other$provisaoTotal != null : !this$provisaoTotal.equals(other$provisaoTotal)) return false;
        final java.lang.Object this$metodologiaCalculo = this.getMetodologiaCalculo();
        final java.lang.Object other$metodologiaCalculo = other.getMetodologiaCalculo();
        if (this$metodologiaCalculo == null ? other$metodologiaCalculo != null : !this$metodologiaCalculo.equals(other$metodologiaCalculo)) return false;
        final java.lang.Object this$modeloUtilizado = this.getModeloUtilizado();
        final java.lang.Object other$modeloUtilizado = other.getModeloUtilizado();
        if (this$modeloUtilizado == null ? other$modeloUtilizado != null : !this$modeloUtilizado.equals(other$modeloUtilizado)) return false;
        final java.lang.Object this$parametrosModelo = this.getParametrosModelo();
        final java.lang.Object other$parametrosModelo = other.getParametrosModelo();
        if (this$parametrosModelo == null ? other$parametrosModelo != null : !this$parametrosModelo.equals(other$parametrosModelo)) return false;
        final java.lang.Object this$cenarioBase = this.getCenarioBase();
        final java.lang.Object other$cenarioBase = other.getCenarioBase();
        if (this$cenarioBase == null ? other$cenarioBase != null : !this$cenarioBase.equals(other$cenarioBase)) return false;
        final java.lang.Object this$cenarioOtimista = this.getCenarioOtimista();
        final java.lang.Object other$cenarioOtimista = other.getCenarioOtimista();
        if (this$cenarioOtimista == null ? other$cenarioOtimista != null : !this$cenarioOtimista.equals(other$cenarioOtimista)) return false;
        final java.lang.Object this$cenarioPessimista = this.getCenarioPessimista();
        final java.lang.Object other$cenarioPessimista = other.getCenarioPessimista();
        if (this$cenarioPessimista == null ? other$cenarioPessimista != null : !this$cenarioPessimista.equals(other$cenarioPessimista)) return false;
        final java.lang.Object this$pesoCenarios = this.getPesoCenarios();
        final java.lang.Object other$pesoCenarios = other.getPesoCenarios();
        if (this$pesoCenarios == null ? other$pesoCenarios != null : !this$pesoCenarios.equals(other$pesoCenarios)) return false;
        final java.lang.Object this$indicadoresDeterioracao = this.getIndicadoresDeterioracao();
        final java.lang.Object other$indicadoresDeterioracao = other.getIndicadoresDeterioracao();
        if (this$indicadoresDeterioracao == null ? other$indicadoresDeterioracao != null : !this$indicadoresDeterioracao.equals(other$indicadoresDeterioracao)) return false;
        final java.lang.Object this$historicoPagamentos = this.getHistoricoPagamentos();
        final java.lang.Object other$historicoPagamentos = other.getHistoricoPagamentos();
        if (this$historicoPagamentos == null ? other$historicoPagamentos != null : !this$historicoPagamentos.equals(other$historicoPagamentos)) return false;
        final java.lang.Object this$ratingInterno = this.getRatingInterno();
        final java.lang.Object other$ratingInterno = other.getRatingInterno();
        if (this$ratingInterno == null ? other$ratingInterno != null : !this$ratingInterno.equals(other$ratingInterno)) return false;
        final java.lang.Object this$ratingExterno = this.getRatingExterno();
        final java.lang.Object other$ratingExterno = other.getRatingExterno();
        if (this$ratingExterno == null ? other$ratingExterno != null : !this$ratingExterno.equals(other$ratingExterno)) return false;
        final java.lang.Object this$garantiasValor = this.getGarantiasValor();
        final java.lang.Object other$garantiasValor = other.getGarantiasValor();
        if (this$garantiasValor == null ? other$garantiasValor != null : !this$garantiasValor.equals(other$garantiasValor)) return false;
        final java.lang.Object this$garantiasTipo = this.getGarantiasTipo();
        final java.lang.Object other$garantiasTipo = other.getGarantiasTipo();
        if (this$garantiasTipo == null ? other$garantiasTipo != null : !this$garantiasTipo.equals(other$garantiasTipo)) return false;
        final java.lang.Object this$dataUltimaAtualizacao = this.getDataUltimaAtualizacao();
        final java.lang.Object other$dataUltimaAtualizacao = other.getDataUltimaAtualizacao();
        if (this$dataUltimaAtualizacao == null ? other$dataUltimaAtualizacao != null : !this$dataUltimaAtualizacao.equals(other$dataUltimaAtualizacao)) return false;
        final java.lang.Object this$usuarioCalculo = this.getUsuarioCalculo();
        final java.lang.Object other$usuarioCalculo = other.getUsuarioCalculo();
        if (this$usuarioCalculo == null ? other$usuarioCalculo != null : !this$usuarioCalculo.equals(other$usuarioCalculo)) return false;
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
        return other instanceof ExpectedCreditLoss;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $scoreCredito = this.getScoreCredito();
        result = result * PRIME + ($scoreCredito == null ? 43 : $scoreCredito.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $instrumento = this.getInstrumento();
        result = result * PRIME + ($instrumento == null ? 43 : $instrumento.hashCode());
        final java.lang.Object $dataCalculo = this.getDataCalculo();
        result = result * PRIME + ($dataCalculo == null ? 43 : $dataCalculo.hashCode());
        final java.lang.Object $estagio = this.getEstagio();
        result = result * PRIME + ($estagio == null ? 43 : $estagio.hashCode());
        final java.lang.Object $probabilityDefault = this.getProbabilityDefault();
        result = result * PRIME + ($probabilityDefault == null ? 43 : $probabilityDefault.hashCode());
        final java.lang.Object $lossGivenDefault = this.getLossGivenDefault();
        result = result * PRIME + ($lossGivenDefault == null ? 43 : $lossGivenDefault.hashCode());
        final java.lang.Object $exposureAtDefault = this.getExposureAtDefault();
        result = result * PRIME + ($exposureAtDefault == null ? 43 : $exposureAtDefault.hashCode());
        final java.lang.Object $expectedCreditLoss = this.getExpectedCreditLoss();
        result = result * PRIME + ($expectedCreditLoss == null ? 43 : $expectedCreditLoss.hashCode());
        final java.lang.Object $provisao12Meses = this.getProvisao12Meses();
        result = result * PRIME + ($provisao12Meses == null ? 43 : $provisao12Meses.hashCode());
        final java.lang.Object $provisaoVidaUtil = this.getProvisaoVidaUtil();
        result = result * PRIME + ($provisaoVidaUtil == null ? 43 : $provisaoVidaUtil.hashCode());
        final java.lang.Object $provisaoTotal = this.getProvisaoTotal();
        result = result * PRIME + ($provisaoTotal == null ? 43 : $provisaoTotal.hashCode());
        final java.lang.Object $metodologiaCalculo = this.getMetodologiaCalculo();
        result = result * PRIME + ($metodologiaCalculo == null ? 43 : $metodologiaCalculo.hashCode());
        final java.lang.Object $modeloUtilizado = this.getModeloUtilizado();
        result = result * PRIME + ($modeloUtilizado == null ? 43 : $modeloUtilizado.hashCode());
        final java.lang.Object $parametrosModelo = this.getParametrosModelo();
        result = result * PRIME + ($parametrosModelo == null ? 43 : $parametrosModelo.hashCode());
        final java.lang.Object $cenarioBase = this.getCenarioBase();
        result = result * PRIME + ($cenarioBase == null ? 43 : $cenarioBase.hashCode());
        final java.lang.Object $cenarioOtimista = this.getCenarioOtimista();
        result = result * PRIME + ($cenarioOtimista == null ? 43 : $cenarioOtimista.hashCode());
        final java.lang.Object $cenarioPessimista = this.getCenarioPessimista();
        result = result * PRIME + ($cenarioPessimista == null ? 43 : $cenarioPessimista.hashCode());
        final java.lang.Object $pesoCenarios = this.getPesoCenarios();
        result = result * PRIME + ($pesoCenarios == null ? 43 : $pesoCenarios.hashCode());
        final java.lang.Object $indicadoresDeterioracao = this.getIndicadoresDeterioracao();
        result = result * PRIME + ($indicadoresDeterioracao == null ? 43 : $indicadoresDeterioracao.hashCode());
        final java.lang.Object $historicoPagamentos = this.getHistoricoPagamentos();
        result = result * PRIME + ($historicoPagamentos == null ? 43 : $historicoPagamentos.hashCode());
        final java.lang.Object $ratingInterno = this.getRatingInterno();
        result = result * PRIME + ($ratingInterno == null ? 43 : $ratingInterno.hashCode());
        final java.lang.Object $ratingExterno = this.getRatingExterno();
        result = result * PRIME + ($ratingExterno == null ? 43 : $ratingExterno.hashCode());
        final java.lang.Object $garantiasValor = this.getGarantiasValor();
        result = result * PRIME + ($garantiasValor == null ? 43 : $garantiasValor.hashCode());
        final java.lang.Object $garantiasTipo = this.getGarantiasTipo();
        result = result * PRIME + ($garantiasTipo == null ? 43 : $garantiasTipo.hashCode());
        final java.lang.Object $dataUltimaAtualizacao = this.getDataUltimaAtualizacao();
        result = result * PRIME + ($dataUltimaAtualizacao == null ? 43 : $dataUltimaAtualizacao.hashCode());
        final java.lang.Object $usuarioCalculo = this.getUsuarioCalculo();
        result = result * PRIME + ($usuarioCalculo == null ? 43 : $usuarioCalculo.hashCode());
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
        return "ExpectedCreditLoss(id=" + this.getId() + ", instrumento=" + this.getInstrumento() + ", dataCalculo=" + this.getDataCalculo() + ", estagio=" + this.getEstagio() + ", probabilityDefault=" + this.getProbabilityDefault() + ", lossGivenDefault=" + this.getLossGivenDefault() + ", exposureAtDefault=" + this.getExposureAtDefault() + ", expectedCreditLoss=" + this.getExpectedCreditLoss() + ", provisao12Meses=" + this.getProvisao12Meses() + ", provisaoVidaUtil=" + this.getProvisaoVidaUtil() + ", provisaoTotal=" + this.getProvisaoTotal() + ", metodologiaCalculo=" + this.getMetodologiaCalculo() + ", modeloUtilizado=" + this.getModeloUtilizado() + ", parametrosModelo=" + this.getParametrosModelo() + ", cenarioBase=" + this.getCenarioBase() + ", cenarioOtimista=" + this.getCenarioOtimista() + ", cenarioPessimista=" + this.getCenarioPessimista() + ", pesoCenarios=" + this.getPesoCenarios() + ", indicadoresDeterioracao=" + this.getIndicadoresDeterioracao() + ", historicoPagamentos=" + this.getHistoricoPagamentos() + ", scoreCredito=" + this.getScoreCredito() + ", ratingInterno=" + this.getRatingInterno() + ", ratingExterno=" + this.getRatingExterno() + ", garantiasValor=" + this.getGarantiasValor() + ", garantiasTipo=" + this.getGarantiasTipo() + ", dataUltimaAtualizacao=" + this.getDataUltimaAtualizacao() + ", usuarioCalculo=" + this.getUsuarioCalculo() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ExpectedCreditLoss() {
    }

    @java.lang.SuppressWarnings("all")
    public ExpectedCreditLoss(final Long id, final InstrumentoFinanceiro instrumento, final LocalDate dataCalculo, final EstagioDeterioracao estagio, final BigDecimal probabilityDefault, final BigDecimal lossGivenDefault, final BigDecimal exposureAtDefault, final BigDecimal expectedCreditLoss, final BigDecimal provisao12Meses, final BigDecimal provisaoVidaUtil, final BigDecimal provisaoTotal, final String metodologiaCalculo, final String modeloUtilizado, final String parametrosModelo, final String cenarioBase, final String cenarioOtimista, final String cenarioPessimista, final String pesoCenarios, final String indicadoresDeterioracao, final String historicoPagamentos, final Integer scoreCredito, final String ratingInterno, final String ratingExterno, final BigDecimal garantiasValor, final String garantiasTipo, final LocalDateTime dataUltimaAtualizacao, final String usuarioCalculo, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.instrumento = instrumento;
        this.dataCalculo = dataCalculo;
        this.estagio = estagio;
        this.probabilityDefault = probabilityDefault;
        this.lossGivenDefault = lossGivenDefault;
        this.exposureAtDefault = exposureAtDefault;
        this.expectedCreditLoss = expectedCreditLoss;
        this.provisao12Meses = provisao12Meses;
        this.provisaoVidaUtil = provisaoVidaUtil;
        this.provisaoTotal = provisaoTotal;
        this.metodologiaCalculo = metodologiaCalculo;
        this.modeloUtilizado = modeloUtilizado;
        this.parametrosModelo = parametrosModelo;
        this.cenarioBase = cenarioBase;
        this.cenarioOtimista = cenarioOtimista;
        this.cenarioPessimista = cenarioPessimista;
        this.pesoCenarios = pesoCenarios;
        this.indicadoresDeterioracao = indicadoresDeterioracao;
        this.historicoPagamentos = historicoPagamentos;
        this.scoreCredito = scoreCredito;
        this.ratingInterno = ratingInterno;
        this.ratingExterno = ratingExterno;
        this.garantiasValor = garantiasValor;
        this.garantiasTipo = garantiasTipo;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.usuarioCalculo = usuarioCalculo;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
