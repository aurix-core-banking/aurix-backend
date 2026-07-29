package com.aurix.platform.banking.pricing.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma simulação de tarifas
 * 
 * Gerencia simulações e comparações de tarifas
 */
@Entity
@Table(name = "simulacoes_tarifas", schema = "aurix")
public class SimulacaoTarifas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_simulacao", unique = true, nullable = false, length = 50)
    private String numeroSimulacao;
    @Column(name = "nome_simulacao", length = 200)
    private String nomeSimulacao;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_simulacao", nullable = false)
    private TipoSimulacao tipoSimulacao;
    @Column(name = "cliente_id", length = 50)
    private String clienteId;
    @Column(name = "segmento_cliente", length = 100)
    private String segmentoCliente;
    @Column(name = "produto", length = 100)
    private String produto;
    @Column(name = "canal", length = 100)
    private String canal;
    @Column(name = "regiao", length = 100)
    private String regiao;
    @Column(name = "volume_operacoes")
    private Integer volumeOperacoes;
    @Column(name = "valor_total_operacoes", precision = 15, scale = 2)
    private BigDecimal valorTotalOperacoes;
    @Column(name = "periodo_simulacao")
    private Integer periodoSimulacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_periodo", nullable = false)
    private UnidadePeriodo unidadePeriodo;
    @Column(name = "tarifa_atual", precision = 15, scale = 2)
    private BigDecimal tarifaAtual;
    @Column(name = "tarifa_simulada", precision = 15, scale = 2)
    private BigDecimal tarifaSimulada;
    @Column(name = "diferenca_valor", precision = 15, scale = 2)
    private BigDecimal diferencaValor;
    @Column(name = "diferenca_percentual", precision = 8, scale = 4)
    private BigDecimal diferencaPercentual;
    @Column(name = "economia_total", precision = 15, scale = 2)
    private BigDecimal economiaTotal;
    @Column(name = "economia_percentual", precision = 8, scale = 4)
    private BigDecimal economiaPercentual;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cenarios_simulados", columnDefinition = "jsonb")
    private String cenariosSimulados;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "resultados_detalhados", columnDefinition = "jsonb")
    private String resultadosDetalhados;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "recomendacoes", columnDefinition = "jsonb")
    private String recomendacoes;
    @Column(name = "status_simulacao", nullable = false)
    private String statusSimulacao;
    @Column(name = "data_simulacao", nullable = false)
    private LocalDateTime dataSimulacao;
    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;
    @Column(name = "usuario_simulacao", length = 100)
    private String usuarioSimulacao;
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
     * Tipo de simulação
     */
    public enum TipoSimulacao {
        COMPARATIVA,  // Simulação comparativa
        PROJETIVA,  // Simulação projetiva
        CENARIO,  // Simulação de cenário
        PERSONALIZADA,  // Simulação personalizada
        PROMOCIONAL,  // Simulação promocional
        CORPORATIVA // Simulação corporativa
        ;
    }


    /**
     * Unidade de período
     */
    public enum UnidadePeriodo {
        DIAS,  // Dias
        SEMANAS,  // Semanas
        MESES,  // Meses
        TRIMESTRES,  // Trimestres
        SEMESTRES,  // Semestres
        ANOS // Anos
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class SimulacaoTarifasBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String numeroSimulacao;
        @java.lang.SuppressWarnings("all")
        private String nomeSimulacao;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoSimulacao tipoSimulacao;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String segmentoCliente;
        @java.lang.SuppressWarnings("all")
        private String produto;
        @java.lang.SuppressWarnings("all")
        private String canal;
        @java.lang.SuppressWarnings("all")
        private String regiao;
        @java.lang.SuppressWarnings("all")
        private Integer volumeOperacoes;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotalOperacoes;
        @java.lang.SuppressWarnings("all")
        private Integer periodoSimulacao;
        @java.lang.SuppressWarnings("all")
        private UnidadePeriodo unidadePeriodo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal tarifaAtual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal tarifaSimulada;
        @java.lang.SuppressWarnings("all")
        private BigDecimal diferencaValor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal diferencaPercentual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal economiaTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal economiaPercentual;
        @java.lang.SuppressWarnings("all")
        private String cenariosSimulados;
        @java.lang.SuppressWarnings("all")
        private String resultadosDetalhados;
        @java.lang.SuppressWarnings("all")
        private String recomendacoes;
        @java.lang.SuppressWarnings("all")
        private String statusSimulacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataSimulacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private String usuarioSimulacao;
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
        SimulacaoTarifasBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder numeroSimulacao(final String numeroSimulacao) {
            this.numeroSimulacao = numeroSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder nomeSimulacao(final String nomeSimulacao) {
            this.nomeSimulacao = nomeSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder tipoSimulacao(final TipoSimulacao tipoSimulacao) {
            this.tipoSimulacao = tipoSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder segmentoCliente(final String segmentoCliente) {
            this.segmentoCliente = segmentoCliente;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder produto(final String produto) {
            this.produto = produto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder canal(final String canal) {
            this.canal = canal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder regiao(final String regiao) {
            this.regiao = regiao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder volumeOperacoes(final Integer volumeOperacoes) {
            this.volumeOperacoes = volumeOperacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder valorTotalOperacoes(final BigDecimal valorTotalOperacoes) {
            this.valorTotalOperacoes = valorTotalOperacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder periodoSimulacao(final Integer periodoSimulacao) {
            this.periodoSimulacao = periodoSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder unidadePeriodo(final UnidadePeriodo unidadePeriodo) {
            this.unidadePeriodo = unidadePeriodo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder tarifaAtual(final BigDecimal tarifaAtual) {
            this.tarifaAtual = tarifaAtual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder tarifaSimulada(final BigDecimal tarifaSimulada) {
            this.tarifaSimulada = tarifaSimulada;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder diferencaValor(final BigDecimal diferencaValor) {
            this.diferencaValor = diferencaValor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder diferencaPercentual(final BigDecimal diferencaPercentual) {
            this.diferencaPercentual = diferencaPercentual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder economiaTotal(final BigDecimal economiaTotal) {
            this.economiaTotal = economiaTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder economiaPercentual(final BigDecimal economiaPercentual) {
            this.economiaPercentual = economiaPercentual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder cenariosSimulados(final String cenariosSimulados) {
            this.cenariosSimulados = cenariosSimulados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder resultadosDetalhados(final String resultadosDetalhados) {
            this.resultadosDetalhados = resultadosDetalhados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder recomendacoes(final String recomendacoes) {
            this.recomendacoes = recomendacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder statusSimulacao(final String statusSimulacao) {
            this.statusSimulacao = statusSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder dataSimulacao(final LocalDateTime dataSimulacao) {
            this.dataSimulacao = dataSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder usuarioSimulacao(final String usuarioSimulacao) {
            this.usuarioSimulacao = usuarioSimulacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas.SimulacaoTarifasBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SimulacaoTarifas build() {
            return new SimulacaoTarifas(this.id, this.numeroSimulacao, this.nomeSimulacao, this.descricao, this.tipoSimulacao, this.clienteId, this.segmentoCliente, this.produto, this.canal, this.regiao, this.volumeOperacoes, this.valorTotalOperacoes, this.periodoSimulacao, this.unidadePeriodo, this.tarifaAtual, this.tarifaSimulada, this.diferencaValor, this.diferencaPercentual, this.economiaTotal, this.economiaPercentual, this.cenariosSimulados, this.resultadosDetalhados, this.recomendacoes, this.statusSimulacao, this.dataSimulacao, this.dataExpiracao, this.usuarioSimulacao, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SimulacaoTarifas.SimulacaoTarifasBuilder(id=" + this.id + ", numeroSimulacao=" + this.numeroSimulacao + ", nomeSimulacao=" + this.nomeSimulacao + ", descricao=" + this.descricao + ", tipoSimulacao=" + this.tipoSimulacao + ", clienteId=" + this.clienteId + ", segmentoCliente=" + this.segmentoCliente + ", produto=" + this.produto + ", canal=" + this.canal + ", regiao=" + this.regiao + ", volumeOperacoes=" + this.volumeOperacoes + ", valorTotalOperacoes=" + this.valorTotalOperacoes + ", periodoSimulacao=" + this.periodoSimulacao + ", unidadePeriodo=" + this.unidadePeriodo + ", tarifaAtual=" + this.tarifaAtual + ", tarifaSimulada=" + this.tarifaSimulada + ", diferencaValor=" + this.diferencaValor + ", diferencaPercentual=" + this.diferencaPercentual + ", economiaTotal=" + this.economiaTotal + ", economiaPercentual=" + this.economiaPercentual + ", cenariosSimulados=" + this.cenariosSimulados + ", resultadosDetalhados=" + this.resultadosDetalhados + ", recomendacoes=" + this.recomendacoes + ", statusSimulacao=" + this.statusSimulacao + ", dataSimulacao=" + this.dataSimulacao + ", dataExpiracao=" + this.dataExpiracao + ", usuarioSimulacao=" + this.usuarioSimulacao + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SimulacaoTarifas.SimulacaoTarifasBuilder builder() {
        return new SimulacaoTarifas.SimulacaoTarifasBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroSimulacao() {
        return this.numeroSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeSimulacao() {
        return this.nomeSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoSimulacao getTipoSimulacao() {
        return this.tipoSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getSegmentoCliente() {
        return this.segmentoCliente;
    }

    @java.lang.SuppressWarnings("all")
    public String getProduto() {
        return this.produto;
    }

    @java.lang.SuppressWarnings("all")
    public String getCanal() {
        return this.canal;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegiao() {
        return this.regiao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getVolumeOperacoes() {
        return this.volumeOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotalOperacoes() {
        return this.valorTotalOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPeriodoSimulacao() {
        return this.periodoSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public UnidadePeriodo getUnidadePeriodo() {
        return this.unidadePeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTarifaAtual() {
        return this.tarifaAtual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTarifaSimulada() {
        return this.tarifaSimulada;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDiferencaValor() {
        return this.diferencaValor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDiferencaPercentual() {
        return this.diferencaPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEconomiaTotal() {
        return this.economiaTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEconomiaPercentual() {
        return this.economiaPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public String getCenariosSimulados() {
        return this.cenariosSimulados;
    }

    @java.lang.SuppressWarnings("all")
    public String getResultadosDetalhados() {
        return this.resultadosDetalhados;
    }

    @java.lang.SuppressWarnings("all")
    public String getRecomendacoes() {
        return this.recomendacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatusSimulacao() {
        return this.statusSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSimulacao() {
        return this.dataSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioSimulacao() {
        return this.usuarioSimulacao;
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
    public void setNumeroSimulacao(final String numeroSimulacao) {
        this.numeroSimulacao = numeroSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeSimulacao(final String nomeSimulacao) {
        this.nomeSimulacao = nomeSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoSimulacao(final TipoSimulacao tipoSimulacao) {
        this.tipoSimulacao = tipoSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSegmentoCliente(final String segmentoCliente) {
        this.segmentoCliente = segmentoCliente;
    }

    @java.lang.SuppressWarnings("all")
    public void setProduto(final String produto) {
        this.produto = produto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCanal(final String canal) {
        this.canal = canal;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegiao(final String regiao) {
        this.regiao = regiao;
    }

    @java.lang.SuppressWarnings("all")
    public void setVolumeOperacoes(final Integer volumeOperacoes) {
        this.volumeOperacoes = volumeOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotalOperacoes(final BigDecimal valorTotalOperacoes) {
        this.valorTotalOperacoes = valorTotalOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodoSimulacao(final Integer periodoSimulacao) {
        this.periodoSimulacao = periodoSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUnidadePeriodo(final UnidadePeriodo unidadePeriodo) {
        this.unidadePeriodo = unidadePeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public void setTarifaAtual(final BigDecimal tarifaAtual) {
        this.tarifaAtual = tarifaAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setTarifaSimulada(final BigDecimal tarifaSimulada) {
        this.tarifaSimulada = tarifaSimulada;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiferencaValor(final BigDecimal diferencaValor) {
        this.diferencaValor = diferencaValor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiferencaPercentual(final BigDecimal diferencaPercentual) {
        this.diferencaPercentual = diferencaPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public void setEconomiaTotal(final BigDecimal economiaTotal) {
        this.economiaTotal = economiaTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setEconomiaPercentual(final BigDecimal economiaPercentual) {
        this.economiaPercentual = economiaPercentual;
    }

    @java.lang.SuppressWarnings("all")
    public void setCenariosSimulados(final String cenariosSimulados) {
        this.cenariosSimulados = cenariosSimulados;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultadosDetalhados(final String resultadosDetalhados) {
        this.resultadosDetalhados = resultadosDetalhados;
    }

    @java.lang.SuppressWarnings("all")
    public void setRecomendacoes(final String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatusSimulacao(final String statusSimulacao) {
        this.statusSimulacao = statusSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSimulacao(final LocalDateTime dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioSimulacao(final String usuarioSimulacao) {
        this.usuarioSimulacao = usuarioSimulacao;
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
        if (!(o instanceof SimulacaoTarifas)) return false;
        final SimulacaoTarifas other = (SimulacaoTarifas) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$volumeOperacoes = this.getVolumeOperacoes();
        final java.lang.Object other$volumeOperacoes = other.getVolumeOperacoes();
        if (this$volumeOperacoes == null ? other$volumeOperacoes != null : !this$volumeOperacoes.equals(other$volumeOperacoes)) return false;
        final java.lang.Object this$periodoSimulacao = this.getPeriodoSimulacao();
        final java.lang.Object other$periodoSimulacao = other.getPeriodoSimulacao();
        if (this$periodoSimulacao == null ? other$periodoSimulacao != null : !this$periodoSimulacao.equals(other$periodoSimulacao)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$numeroSimulacao = this.getNumeroSimulacao();
        final java.lang.Object other$numeroSimulacao = other.getNumeroSimulacao();
        if (this$numeroSimulacao == null ? other$numeroSimulacao != null : !this$numeroSimulacao.equals(other$numeroSimulacao)) return false;
        final java.lang.Object this$nomeSimulacao = this.getNomeSimulacao();
        final java.lang.Object other$nomeSimulacao = other.getNomeSimulacao();
        if (this$nomeSimulacao == null ? other$nomeSimulacao != null : !this$nomeSimulacao.equals(other$nomeSimulacao)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoSimulacao = this.getTipoSimulacao();
        final java.lang.Object other$tipoSimulacao = other.getTipoSimulacao();
        if (this$tipoSimulacao == null ? other$tipoSimulacao != null : !this$tipoSimulacao.equals(other$tipoSimulacao)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$segmentoCliente = this.getSegmentoCliente();
        final java.lang.Object other$segmentoCliente = other.getSegmentoCliente();
        if (this$segmentoCliente == null ? other$segmentoCliente != null : !this$segmentoCliente.equals(other$segmentoCliente)) return false;
        final java.lang.Object this$produto = this.getProduto();
        final java.lang.Object other$produto = other.getProduto();
        if (this$produto == null ? other$produto != null : !this$produto.equals(other$produto)) return false;
        final java.lang.Object this$canal = this.getCanal();
        final java.lang.Object other$canal = other.getCanal();
        if (this$canal == null ? other$canal != null : !this$canal.equals(other$canal)) return false;
        final java.lang.Object this$regiao = this.getRegiao();
        final java.lang.Object other$regiao = other.getRegiao();
        if (this$regiao == null ? other$regiao != null : !this$regiao.equals(other$regiao)) return false;
        final java.lang.Object this$valorTotalOperacoes = this.getValorTotalOperacoes();
        final java.lang.Object other$valorTotalOperacoes = other.getValorTotalOperacoes();
        if (this$valorTotalOperacoes == null ? other$valorTotalOperacoes != null : !this$valorTotalOperacoes.equals(other$valorTotalOperacoes)) return false;
        final java.lang.Object this$unidadePeriodo = this.getUnidadePeriodo();
        final java.lang.Object other$unidadePeriodo = other.getUnidadePeriodo();
        if (this$unidadePeriodo == null ? other$unidadePeriodo != null : !this$unidadePeriodo.equals(other$unidadePeriodo)) return false;
        final java.lang.Object this$tarifaAtual = this.getTarifaAtual();
        final java.lang.Object other$tarifaAtual = other.getTarifaAtual();
        if (this$tarifaAtual == null ? other$tarifaAtual != null : !this$tarifaAtual.equals(other$tarifaAtual)) return false;
        final java.lang.Object this$tarifaSimulada = this.getTarifaSimulada();
        final java.lang.Object other$tarifaSimulada = other.getTarifaSimulada();
        if (this$tarifaSimulada == null ? other$tarifaSimulada != null : !this$tarifaSimulada.equals(other$tarifaSimulada)) return false;
        final java.lang.Object this$diferencaValor = this.getDiferencaValor();
        final java.lang.Object other$diferencaValor = other.getDiferencaValor();
        if (this$diferencaValor == null ? other$diferencaValor != null : !this$diferencaValor.equals(other$diferencaValor)) return false;
        final java.lang.Object this$diferencaPercentual = this.getDiferencaPercentual();
        final java.lang.Object other$diferencaPercentual = other.getDiferencaPercentual();
        if (this$diferencaPercentual == null ? other$diferencaPercentual != null : !this$diferencaPercentual.equals(other$diferencaPercentual)) return false;
        final java.lang.Object this$economiaTotal = this.getEconomiaTotal();
        final java.lang.Object other$economiaTotal = other.getEconomiaTotal();
        if (this$economiaTotal == null ? other$economiaTotal != null : !this$economiaTotal.equals(other$economiaTotal)) return false;
        final java.lang.Object this$economiaPercentual = this.getEconomiaPercentual();
        final java.lang.Object other$economiaPercentual = other.getEconomiaPercentual();
        if (this$economiaPercentual == null ? other$economiaPercentual != null : !this$economiaPercentual.equals(other$economiaPercentual)) return false;
        final java.lang.Object this$cenariosSimulados = this.getCenariosSimulados();
        final java.lang.Object other$cenariosSimulados = other.getCenariosSimulados();
        if (this$cenariosSimulados == null ? other$cenariosSimulados != null : !this$cenariosSimulados.equals(other$cenariosSimulados)) return false;
        final java.lang.Object this$resultadosDetalhados = this.getResultadosDetalhados();
        final java.lang.Object other$resultadosDetalhados = other.getResultadosDetalhados();
        if (this$resultadosDetalhados == null ? other$resultadosDetalhados != null : !this$resultadosDetalhados.equals(other$resultadosDetalhados)) return false;
        final java.lang.Object this$recomendacoes = this.getRecomendacoes();
        final java.lang.Object other$recomendacoes = other.getRecomendacoes();
        if (this$recomendacoes == null ? other$recomendacoes != null : !this$recomendacoes.equals(other$recomendacoes)) return false;
        final java.lang.Object this$statusSimulacao = this.getStatusSimulacao();
        final java.lang.Object other$statusSimulacao = other.getStatusSimulacao();
        if (this$statusSimulacao == null ? other$statusSimulacao != null : !this$statusSimulacao.equals(other$statusSimulacao)) return false;
        final java.lang.Object this$dataSimulacao = this.getDataSimulacao();
        final java.lang.Object other$dataSimulacao = other.getDataSimulacao();
        if (this$dataSimulacao == null ? other$dataSimulacao != null : !this$dataSimulacao.equals(other$dataSimulacao)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$usuarioSimulacao = this.getUsuarioSimulacao();
        final java.lang.Object other$usuarioSimulacao = other.getUsuarioSimulacao();
        if (this$usuarioSimulacao == null ? other$usuarioSimulacao != null : !this$usuarioSimulacao.equals(other$usuarioSimulacao)) return false;
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
        return other instanceof SimulacaoTarifas;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $volumeOperacoes = this.getVolumeOperacoes();
        result = result * PRIME + ($volumeOperacoes == null ? 43 : $volumeOperacoes.hashCode());
        final java.lang.Object $periodoSimulacao = this.getPeriodoSimulacao();
        result = result * PRIME + ($periodoSimulacao == null ? 43 : $periodoSimulacao.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $numeroSimulacao = this.getNumeroSimulacao();
        result = result * PRIME + ($numeroSimulacao == null ? 43 : $numeroSimulacao.hashCode());
        final java.lang.Object $nomeSimulacao = this.getNomeSimulacao();
        result = result * PRIME + ($nomeSimulacao == null ? 43 : $nomeSimulacao.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoSimulacao = this.getTipoSimulacao();
        result = result * PRIME + ($tipoSimulacao == null ? 43 : $tipoSimulacao.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $segmentoCliente = this.getSegmentoCliente();
        result = result * PRIME + ($segmentoCliente == null ? 43 : $segmentoCliente.hashCode());
        final java.lang.Object $produto = this.getProduto();
        result = result * PRIME + ($produto == null ? 43 : $produto.hashCode());
        final java.lang.Object $canal = this.getCanal();
        result = result * PRIME + ($canal == null ? 43 : $canal.hashCode());
        final java.lang.Object $regiao = this.getRegiao();
        result = result * PRIME + ($regiao == null ? 43 : $regiao.hashCode());
        final java.lang.Object $valorTotalOperacoes = this.getValorTotalOperacoes();
        result = result * PRIME + ($valorTotalOperacoes == null ? 43 : $valorTotalOperacoes.hashCode());
        final java.lang.Object $unidadePeriodo = this.getUnidadePeriodo();
        result = result * PRIME + ($unidadePeriodo == null ? 43 : $unidadePeriodo.hashCode());
        final java.lang.Object $tarifaAtual = this.getTarifaAtual();
        result = result * PRIME + ($tarifaAtual == null ? 43 : $tarifaAtual.hashCode());
        final java.lang.Object $tarifaSimulada = this.getTarifaSimulada();
        result = result * PRIME + ($tarifaSimulada == null ? 43 : $tarifaSimulada.hashCode());
        final java.lang.Object $diferencaValor = this.getDiferencaValor();
        result = result * PRIME + ($diferencaValor == null ? 43 : $diferencaValor.hashCode());
        final java.lang.Object $diferencaPercentual = this.getDiferencaPercentual();
        result = result * PRIME + ($diferencaPercentual == null ? 43 : $diferencaPercentual.hashCode());
        final java.lang.Object $economiaTotal = this.getEconomiaTotal();
        result = result * PRIME + ($economiaTotal == null ? 43 : $economiaTotal.hashCode());
        final java.lang.Object $economiaPercentual = this.getEconomiaPercentual();
        result = result * PRIME + ($economiaPercentual == null ? 43 : $economiaPercentual.hashCode());
        final java.lang.Object $cenariosSimulados = this.getCenariosSimulados();
        result = result * PRIME + ($cenariosSimulados == null ? 43 : $cenariosSimulados.hashCode());
        final java.lang.Object $resultadosDetalhados = this.getResultadosDetalhados();
        result = result * PRIME + ($resultadosDetalhados == null ? 43 : $resultadosDetalhados.hashCode());
        final java.lang.Object $recomendacoes = this.getRecomendacoes();
        result = result * PRIME + ($recomendacoes == null ? 43 : $recomendacoes.hashCode());
        final java.lang.Object $statusSimulacao = this.getStatusSimulacao();
        result = result * PRIME + ($statusSimulacao == null ? 43 : $statusSimulacao.hashCode());
        final java.lang.Object $dataSimulacao = this.getDataSimulacao();
        result = result * PRIME + ($dataSimulacao == null ? 43 : $dataSimulacao.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $usuarioSimulacao = this.getUsuarioSimulacao();
        result = result * PRIME + ($usuarioSimulacao == null ? 43 : $usuarioSimulacao.hashCode());
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
        return "SimulacaoTarifas(id=" + this.getId() + ", numeroSimulacao=" + this.getNumeroSimulacao() + ", nomeSimulacao=" + this.getNomeSimulacao() + ", descricao=" + this.getDescricao() + ", tipoSimulacao=" + this.getTipoSimulacao() + ", clienteId=" + this.getClienteId() + ", segmentoCliente=" + this.getSegmentoCliente() + ", produto=" + this.getProduto() + ", canal=" + this.getCanal() + ", regiao=" + this.getRegiao() + ", volumeOperacoes=" + this.getVolumeOperacoes() + ", valorTotalOperacoes=" + this.getValorTotalOperacoes() + ", periodoSimulacao=" + this.getPeriodoSimulacao() + ", unidadePeriodo=" + this.getUnidadePeriodo() + ", tarifaAtual=" + this.getTarifaAtual() + ", tarifaSimulada=" + this.getTarifaSimulada() + ", diferencaValor=" + this.getDiferencaValor() + ", diferencaPercentual=" + this.getDiferencaPercentual() + ", economiaTotal=" + this.getEconomiaTotal() + ", economiaPercentual=" + this.getEconomiaPercentual() + ", cenariosSimulados=" + this.getCenariosSimulados() + ", resultadosDetalhados=" + this.getResultadosDetalhados() + ", recomendacoes=" + this.getRecomendacoes() + ", statusSimulacao=" + this.getStatusSimulacao() + ", dataSimulacao=" + this.getDataSimulacao() + ", dataExpiracao=" + this.getDataExpiracao() + ", usuarioSimulacao=" + this.getUsuarioSimulacao() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SimulacaoTarifas() {
    }

    @java.lang.SuppressWarnings("all")
    public SimulacaoTarifas(final Long id, final String numeroSimulacao, final String nomeSimulacao, final String descricao, final TipoSimulacao tipoSimulacao, final String clienteId, final String segmentoCliente, final String produto, final String canal, final String regiao, final Integer volumeOperacoes, final BigDecimal valorTotalOperacoes, final Integer periodoSimulacao, final UnidadePeriodo unidadePeriodo, final BigDecimal tarifaAtual, final BigDecimal tarifaSimulada, final BigDecimal diferencaValor, final BigDecimal diferencaPercentual, final BigDecimal economiaTotal, final BigDecimal economiaPercentual, final String cenariosSimulados, final String resultadosDetalhados, final String recomendacoes, final String statusSimulacao, final LocalDateTime dataSimulacao, final LocalDateTime dataExpiracao, final String usuarioSimulacao, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.numeroSimulacao = numeroSimulacao;
        this.nomeSimulacao = nomeSimulacao;
        this.descricao = descricao;
        this.tipoSimulacao = tipoSimulacao;
        this.clienteId = clienteId;
        this.segmentoCliente = segmentoCliente;
        this.produto = produto;
        this.canal = canal;
        this.regiao = regiao;
        this.volumeOperacoes = volumeOperacoes;
        this.valorTotalOperacoes = valorTotalOperacoes;
        this.periodoSimulacao = periodoSimulacao;
        this.unidadePeriodo = unidadePeriodo;
        this.tarifaAtual = tarifaAtual;
        this.tarifaSimulada = tarifaSimulada;
        this.diferencaValor = diferencaValor;
        this.diferencaPercentual = diferencaPercentual;
        this.economiaTotal = economiaTotal;
        this.economiaPercentual = economiaPercentual;
        this.cenariosSimulados = cenariosSimulados;
        this.resultadosDetalhados = resultadosDetalhados;
        this.recomendacoes = recomendacoes;
        this.statusSimulacao = statusSimulacao;
        this.dataSimulacao = dataSimulacao;
        this.dataExpiracao = dataExpiracao;
        this.usuarioSimulacao = usuarioSimulacao;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
