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
 * Entidade que representa o spread bancário
 * 
 * Gerencia cálculo e controle do spread entre captação e aplicação
 */
@Entity
@Table(name = "spread_bancario", schema = "aurix")
public class SpreadBancario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_calculo", nullable = false)
    private LocalDate dataCalculo;
    @Column(name = "taxa_selic", nullable = false, precision = 8, scale = 4)
    private BigDecimal taxaSelic;
    @Column(name = "taxa_captacao", nullable = false, precision = 8, scale = 4)
    private BigDecimal taxaCaptacao;
    @Column(name = "taxa_aplicacao", nullable = false, precision = 8, scale = 4)
    private BigDecimal taxaAplicacao;
    @Column(name = "spread_bruto", nullable = false, precision = 8, scale = 4)
    private BigDecimal spreadBruto;
    @Column(name = "custo_financeiro", precision = 8, scale = 4)
    private BigDecimal custoFinanceiro;
    @Column(name = "custo_operacional", precision = 8, scale = 4)
    private BigDecimal custoOperacional;
    @Column(name = "provisao_credito", precision = 8, scale = 4)
    private BigDecimal provisaoCredito;
    @Column(name = "margem_lucro", precision = 8, scale = 4)
    private BigDecimal margemLucro;
    @Column(name = "spread_liquido", precision = 8, scale = 4)
    private BigDecimal spreadLiquido;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false)
    private TipoOperacao tipoOperacao;
    @Column(name = "categoria_cliente", length = 50)
    private String categoriaCliente;
    @Column(name = "segmento_mercado", length = 50)
    private String segmentoMercado;
    @Column(name = "prazo_dias")
    private Integer prazoDias;
    @Column(name = "valor_operacao", precision = 15, scale = 2)
    private BigDecimal valorOperacao;
    @Column(name = "competitividade", precision = 8, scale = 4)
    private BigDecimal competitividade;
    @Column(name = "benchmark_mercado", precision = 8, scale = 4)
    private BigDecimal benchmarkMercado;
    @Column(name = "posicao_relativa", precision = 8, scale = 4)
    private BigDecimal posicaoRelativa;
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
     * Tipo de operação
     */
    public enum TipoOperacao {
        EMPRESTIMO_PESSOAL,  // Empréstimo pessoal
        EMPRESTIMO_CONSIGNADO,  // Empréstimo consignado
        EMPRESTIMO_IMOVEL,  // Empréstimo imóvel
        EMPRESTIMO_VEICULO,  // Empréstimo veículo
        CARTAO_CREDITO,  // Cartão de crédito
        CHEQUE_ESPECIAL,  // Cheque especial
        CONTA_CORRENTE,  // Conta corrente
        POUPANCA,  // Poupança
        CDB,  // CDB
        LCI,  // LCI
        LCA,  // LCA
        OUTROS // Outros
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class SpreadBancarioBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataCalculo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaSelic;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaCaptacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaAplicacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal spreadBruto;
        @java.lang.SuppressWarnings("all")
        private BigDecimal custoFinanceiro;
        @java.lang.SuppressWarnings("all")
        private BigDecimal custoOperacional;
        @java.lang.SuppressWarnings("all")
        private BigDecimal provisaoCredito;
        @java.lang.SuppressWarnings("all")
        private BigDecimal margemLucro;
        @java.lang.SuppressWarnings("all")
        private BigDecimal spreadLiquido;
        @java.lang.SuppressWarnings("all")
        private TipoOperacao tipoOperacao;
        @java.lang.SuppressWarnings("all")
        private String categoriaCliente;
        @java.lang.SuppressWarnings("all")
        private String segmentoMercado;
        @java.lang.SuppressWarnings("all")
        private Integer prazoDias;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorOperacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal competitividade;
        @java.lang.SuppressWarnings("all")
        private BigDecimal benchmarkMercado;
        @java.lang.SuppressWarnings("all")
        private BigDecimal posicaoRelativa;
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
        SpreadBancarioBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder dataCalculo(final LocalDate dataCalculo) {
            this.dataCalculo = dataCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder taxaSelic(final BigDecimal taxaSelic) {
            this.taxaSelic = taxaSelic;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder taxaCaptacao(final BigDecimal taxaCaptacao) {
            this.taxaCaptacao = taxaCaptacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder taxaAplicacao(final BigDecimal taxaAplicacao) {
            this.taxaAplicacao = taxaAplicacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder spreadBruto(final BigDecimal spreadBruto) {
            this.spreadBruto = spreadBruto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder custoFinanceiro(final BigDecimal custoFinanceiro) {
            this.custoFinanceiro = custoFinanceiro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder custoOperacional(final BigDecimal custoOperacional) {
            this.custoOperacional = custoOperacional;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder provisaoCredito(final BigDecimal provisaoCredito) {
            this.provisaoCredito = provisaoCredito;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder margemLucro(final BigDecimal margemLucro) {
            this.margemLucro = margemLucro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder spreadLiquido(final BigDecimal spreadLiquido) {
            this.spreadLiquido = spreadLiquido;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder tipoOperacao(final TipoOperacao tipoOperacao) {
            this.tipoOperacao = tipoOperacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder categoriaCliente(final String categoriaCliente) {
            this.categoriaCliente = categoriaCliente;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder segmentoMercado(final String segmentoMercado) {
            this.segmentoMercado = segmentoMercado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder prazoDias(final Integer prazoDias) {
            this.prazoDias = prazoDias;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder valorOperacao(final BigDecimal valorOperacao) {
            this.valorOperacao = valorOperacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder competitividade(final BigDecimal competitividade) {
            this.competitividade = competitividade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder benchmarkMercado(final BigDecimal benchmarkMercado) {
            this.benchmarkMercado = benchmarkMercado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder posicaoRelativa(final BigDecimal posicaoRelativa) {
            this.posicaoRelativa = posicaoRelativa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SpreadBancario.SpreadBancarioBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SpreadBancario build() {
            return new SpreadBancario(this.id, this.dataCalculo, this.taxaSelic, this.taxaCaptacao, this.taxaAplicacao, this.spreadBruto, this.custoFinanceiro, this.custoOperacional, this.provisaoCredito, this.margemLucro, this.spreadLiquido, this.tipoOperacao, this.categoriaCliente, this.segmentoMercado, this.prazoDias, this.valorOperacao, this.competitividade, this.benchmarkMercado, this.posicaoRelativa, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpreadBancario.SpreadBancarioBuilder(id=" + this.id + ", dataCalculo=" + this.dataCalculo + ", taxaSelic=" + this.taxaSelic + ", taxaCaptacao=" + this.taxaCaptacao + ", taxaAplicacao=" + this.taxaAplicacao + ", spreadBruto=" + this.spreadBruto + ", custoFinanceiro=" + this.custoFinanceiro + ", custoOperacional=" + this.custoOperacional + ", provisaoCredito=" + this.provisaoCredito + ", margemLucro=" + this.margemLucro + ", spreadLiquido=" + this.spreadLiquido + ", tipoOperacao=" + this.tipoOperacao + ", categoriaCliente=" + this.categoriaCliente + ", segmentoMercado=" + this.segmentoMercado + ", prazoDias=" + this.prazoDias + ", valorOperacao=" + this.valorOperacao + ", competitividade=" + this.competitividade + ", benchmarkMercado=" + this.benchmarkMercado + ", posicaoRelativa=" + this.posicaoRelativa + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SpreadBancario.SpreadBancarioBuilder builder() {
        return new SpreadBancario.SpreadBancarioBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataCalculo() {
        return this.dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaSelic() {
        return this.taxaSelic;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaCaptacao() {
        return this.taxaCaptacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaAplicacao() {
        return this.taxaAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSpreadBruto() {
        return this.spreadBruto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCustoFinanceiro() {
        return this.custoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCustoOperacional() {
        return this.custoOperacional;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProvisaoCredito() {
        return this.provisaoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMargemLucro() {
        return this.margemLucro;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSpreadLiquido() {
        return this.spreadLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public TipoOperacao getTipoOperacao() {
        return this.tipoOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getCategoriaCliente() {
        return this.categoriaCliente;
    }

    @java.lang.SuppressWarnings("all")
    public String getSegmentoMercado() {
        return this.segmentoMercado;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoDias() {
        return this.prazoDias;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOperacao() {
        return this.valorOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCompetitividade() {
        return this.competitividade;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getBenchmarkMercado() {
        return this.benchmarkMercado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPosicaoRelativa() {
        return this.posicaoRelativa;
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
    public void setDataCalculo(final LocalDate dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaSelic(final BigDecimal taxaSelic) {
        this.taxaSelic = taxaSelic;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaCaptacao(final BigDecimal taxaCaptacao) {
        this.taxaCaptacao = taxaCaptacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaAplicacao(final BigDecimal taxaAplicacao) {
        this.taxaAplicacao = taxaAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setSpreadBruto(final BigDecimal spreadBruto) {
        this.spreadBruto = spreadBruto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCustoFinanceiro(final BigDecimal custoFinanceiro) {
        this.custoFinanceiro = custoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setCustoOperacional(final BigDecimal custoOperacional) {
        this.custoOperacional = custoOperacional;
    }

    @java.lang.SuppressWarnings("all")
    public void setProvisaoCredito(final BigDecimal provisaoCredito) {
        this.provisaoCredito = provisaoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setMargemLucro(final BigDecimal margemLucro) {
        this.margemLucro = margemLucro;
    }

    @java.lang.SuppressWarnings("all")
    public void setSpreadLiquido(final BigDecimal spreadLiquido) {
        this.spreadLiquido = spreadLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoOperacao(final TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaCliente(final String categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }

    @java.lang.SuppressWarnings("all")
    public void setSegmentoMercado(final String segmentoMercado) {
        this.segmentoMercado = segmentoMercado;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoDias(final Integer prazoDias) {
        this.prazoDias = prazoDias;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOperacao(final BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCompetitividade(final BigDecimal competitividade) {
        this.competitividade = competitividade;
    }

    @java.lang.SuppressWarnings("all")
    public void setBenchmarkMercado(final BigDecimal benchmarkMercado) {
        this.benchmarkMercado = benchmarkMercado;
    }

    @java.lang.SuppressWarnings("all")
    public void setPosicaoRelativa(final BigDecimal posicaoRelativa) {
        this.posicaoRelativa = posicaoRelativa;
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
        if (!(o instanceof SpreadBancario)) return false;
        final SpreadBancario other = (SpreadBancario) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$prazoDias = this.getPrazoDias();
        final java.lang.Object other$prazoDias = other.getPrazoDias();
        if (this$prazoDias == null ? other$prazoDias != null : !this$prazoDias.equals(other$prazoDias)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$dataCalculo = this.getDataCalculo();
        final java.lang.Object other$dataCalculo = other.getDataCalculo();
        if (this$dataCalculo == null ? other$dataCalculo != null : !this$dataCalculo.equals(other$dataCalculo)) return false;
        final java.lang.Object this$taxaSelic = this.getTaxaSelic();
        final java.lang.Object other$taxaSelic = other.getTaxaSelic();
        if (this$taxaSelic == null ? other$taxaSelic != null : !this$taxaSelic.equals(other$taxaSelic)) return false;
        final java.lang.Object this$taxaCaptacao = this.getTaxaCaptacao();
        final java.lang.Object other$taxaCaptacao = other.getTaxaCaptacao();
        if (this$taxaCaptacao == null ? other$taxaCaptacao != null : !this$taxaCaptacao.equals(other$taxaCaptacao)) return false;
        final java.lang.Object this$taxaAplicacao = this.getTaxaAplicacao();
        final java.lang.Object other$taxaAplicacao = other.getTaxaAplicacao();
        if (this$taxaAplicacao == null ? other$taxaAplicacao != null : !this$taxaAplicacao.equals(other$taxaAplicacao)) return false;
        final java.lang.Object this$spreadBruto = this.getSpreadBruto();
        final java.lang.Object other$spreadBruto = other.getSpreadBruto();
        if (this$spreadBruto == null ? other$spreadBruto != null : !this$spreadBruto.equals(other$spreadBruto)) return false;
        final java.lang.Object this$custoFinanceiro = this.getCustoFinanceiro();
        final java.lang.Object other$custoFinanceiro = other.getCustoFinanceiro();
        if (this$custoFinanceiro == null ? other$custoFinanceiro != null : !this$custoFinanceiro.equals(other$custoFinanceiro)) return false;
        final java.lang.Object this$custoOperacional = this.getCustoOperacional();
        final java.lang.Object other$custoOperacional = other.getCustoOperacional();
        if (this$custoOperacional == null ? other$custoOperacional != null : !this$custoOperacional.equals(other$custoOperacional)) return false;
        final java.lang.Object this$provisaoCredito = this.getProvisaoCredito();
        final java.lang.Object other$provisaoCredito = other.getProvisaoCredito();
        if (this$provisaoCredito == null ? other$provisaoCredito != null : !this$provisaoCredito.equals(other$provisaoCredito)) return false;
        final java.lang.Object this$margemLucro = this.getMargemLucro();
        final java.lang.Object other$margemLucro = other.getMargemLucro();
        if (this$margemLucro == null ? other$margemLucro != null : !this$margemLucro.equals(other$margemLucro)) return false;
        final java.lang.Object this$spreadLiquido = this.getSpreadLiquido();
        final java.lang.Object other$spreadLiquido = other.getSpreadLiquido();
        if (this$spreadLiquido == null ? other$spreadLiquido != null : !this$spreadLiquido.equals(other$spreadLiquido)) return false;
        final java.lang.Object this$tipoOperacao = this.getTipoOperacao();
        final java.lang.Object other$tipoOperacao = other.getTipoOperacao();
        if (this$tipoOperacao == null ? other$tipoOperacao != null : !this$tipoOperacao.equals(other$tipoOperacao)) return false;
        final java.lang.Object this$categoriaCliente = this.getCategoriaCliente();
        final java.lang.Object other$categoriaCliente = other.getCategoriaCliente();
        if (this$categoriaCliente == null ? other$categoriaCliente != null : !this$categoriaCliente.equals(other$categoriaCliente)) return false;
        final java.lang.Object this$segmentoMercado = this.getSegmentoMercado();
        final java.lang.Object other$segmentoMercado = other.getSegmentoMercado();
        if (this$segmentoMercado == null ? other$segmentoMercado != null : !this$segmentoMercado.equals(other$segmentoMercado)) return false;
        final java.lang.Object this$valorOperacao = this.getValorOperacao();
        final java.lang.Object other$valorOperacao = other.getValorOperacao();
        if (this$valorOperacao == null ? other$valorOperacao != null : !this$valorOperacao.equals(other$valorOperacao)) return false;
        final java.lang.Object this$competitividade = this.getCompetitividade();
        final java.lang.Object other$competitividade = other.getCompetitividade();
        if (this$competitividade == null ? other$competitividade != null : !this$competitividade.equals(other$competitividade)) return false;
        final java.lang.Object this$benchmarkMercado = this.getBenchmarkMercado();
        final java.lang.Object other$benchmarkMercado = other.getBenchmarkMercado();
        if (this$benchmarkMercado == null ? other$benchmarkMercado != null : !this$benchmarkMercado.equals(other$benchmarkMercado)) return false;
        final java.lang.Object this$posicaoRelativa = this.getPosicaoRelativa();
        final java.lang.Object other$posicaoRelativa = other.getPosicaoRelativa();
        if (this$posicaoRelativa == null ? other$posicaoRelativa != null : !this$posicaoRelativa.equals(other$posicaoRelativa)) return false;
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
        return other instanceof SpreadBancario;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $prazoDias = this.getPrazoDias();
        result = result * PRIME + ($prazoDias == null ? 43 : $prazoDias.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $dataCalculo = this.getDataCalculo();
        result = result * PRIME + ($dataCalculo == null ? 43 : $dataCalculo.hashCode());
        final java.lang.Object $taxaSelic = this.getTaxaSelic();
        result = result * PRIME + ($taxaSelic == null ? 43 : $taxaSelic.hashCode());
        final java.lang.Object $taxaCaptacao = this.getTaxaCaptacao();
        result = result * PRIME + ($taxaCaptacao == null ? 43 : $taxaCaptacao.hashCode());
        final java.lang.Object $taxaAplicacao = this.getTaxaAplicacao();
        result = result * PRIME + ($taxaAplicacao == null ? 43 : $taxaAplicacao.hashCode());
        final java.lang.Object $spreadBruto = this.getSpreadBruto();
        result = result * PRIME + ($spreadBruto == null ? 43 : $spreadBruto.hashCode());
        final java.lang.Object $custoFinanceiro = this.getCustoFinanceiro();
        result = result * PRIME + ($custoFinanceiro == null ? 43 : $custoFinanceiro.hashCode());
        final java.lang.Object $custoOperacional = this.getCustoOperacional();
        result = result * PRIME + ($custoOperacional == null ? 43 : $custoOperacional.hashCode());
        final java.lang.Object $provisaoCredito = this.getProvisaoCredito();
        result = result * PRIME + ($provisaoCredito == null ? 43 : $provisaoCredito.hashCode());
        final java.lang.Object $margemLucro = this.getMargemLucro();
        result = result * PRIME + ($margemLucro == null ? 43 : $margemLucro.hashCode());
        final java.lang.Object $spreadLiquido = this.getSpreadLiquido();
        result = result * PRIME + ($spreadLiquido == null ? 43 : $spreadLiquido.hashCode());
        final java.lang.Object $tipoOperacao = this.getTipoOperacao();
        result = result * PRIME + ($tipoOperacao == null ? 43 : $tipoOperacao.hashCode());
        final java.lang.Object $categoriaCliente = this.getCategoriaCliente();
        result = result * PRIME + ($categoriaCliente == null ? 43 : $categoriaCliente.hashCode());
        final java.lang.Object $segmentoMercado = this.getSegmentoMercado();
        result = result * PRIME + ($segmentoMercado == null ? 43 : $segmentoMercado.hashCode());
        final java.lang.Object $valorOperacao = this.getValorOperacao();
        result = result * PRIME + ($valorOperacao == null ? 43 : $valorOperacao.hashCode());
        final java.lang.Object $competitividade = this.getCompetitividade();
        result = result * PRIME + ($competitividade == null ? 43 : $competitividade.hashCode());
        final java.lang.Object $benchmarkMercado = this.getBenchmarkMercado();
        result = result * PRIME + ($benchmarkMercado == null ? 43 : $benchmarkMercado.hashCode());
        final java.lang.Object $posicaoRelativa = this.getPosicaoRelativa();
        result = result * PRIME + ($posicaoRelativa == null ? 43 : $posicaoRelativa.hashCode());
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
        return "SpreadBancario(id=" + this.getId() + ", dataCalculo=" + this.getDataCalculo() + ", taxaSelic=" + this.getTaxaSelic() + ", taxaCaptacao=" + this.getTaxaCaptacao() + ", taxaAplicacao=" + this.getTaxaAplicacao() + ", spreadBruto=" + this.getSpreadBruto() + ", custoFinanceiro=" + this.getCustoFinanceiro() + ", custoOperacional=" + this.getCustoOperacional() + ", provisaoCredito=" + this.getProvisaoCredito() + ", margemLucro=" + this.getMargemLucro() + ", spreadLiquido=" + this.getSpreadLiquido() + ", tipoOperacao=" + this.getTipoOperacao() + ", categoriaCliente=" + this.getCategoriaCliente() + ", segmentoMercado=" + this.getSegmentoMercado() + ", prazoDias=" + this.getPrazoDias() + ", valorOperacao=" + this.getValorOperacao() + ", competitividade=" + this.getCompetitividade() + ", benchmarkMercado=" + this.getBenchmarkMercado() + ", posicaoRelativa=" + this.getPosicaoRelativa() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SpreadBancario() {
    }

    @java.lang.SuppressWarnings("all")
    public SpreadBancario(final Long id, final LocalDate dataCalculo, final BigDecimal taxaSelic, final BigDecimal taxaCaptacao, final BigDecimal taxaAplicacao, final BigDecimal spreadBruto, final BigDecimal custoFinanceiro, final BigDecimal custoOperacional, final BigDecimal provisaoCredito, final BigDecimal margemLucro, final BigDecimal spreadLiquido, final TipoOperacao tipoOperacao, final String categoriaCliente, final String segmentoMercado, final Integer prazoDias, final BigDecimal valorOperacao, final BigDecimal competitividade, final BigDecimal benchmarkMercado, final BigDecimal posicaoRelativa, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.dataCalculo = dataCalculo;
        this.taxaSelic = taxaSelic;
        this.taxaCaptacao = taxaCaptacao;
        this.taxaAplicacao = taxaAplicacao;
        this.spreadBruto = spreadBruto;
        this.custoFinanceiro = custoFinanceiro;
        this.custoOperacional = custoOperacional;
        this.provisaoCredito = provisaoCredito;
        this.margemLucro = margemLucro;
        this.spreadLiquido = spreadLiquido;
        this.tipoOperacao = tipoOperacao;
        this.categoriaCliente = categoriaCliente;
        this.segmentoMercado = segmentoMercado;
        this.prazoDias = prazoDias;
        this.valorOperacao = valorOperacao;
        this.competitividade = competitividade;
        this.benchmarkMercado = benchmarkMercado;
        this.posicaoRelativa = posicaoRelativa;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
