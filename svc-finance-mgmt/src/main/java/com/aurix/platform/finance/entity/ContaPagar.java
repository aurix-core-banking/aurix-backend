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
 * Entidade que representa uma conta a pagar
 * 
 * Gerencia todas as obrigações financeiras do banco
 */
@Entity
@Table(name = "contas_pagar", schema = "aurix")
public class ContaPagar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_documento", unique = true, nullable = false, length = 50)
    private String numeroDocumento;
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;
    @Column(name = "valor_original", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorOriginal;
    @Column(name = "valor_pago", precision = 15, scale = 2)
    private BigDecimal valorPago;
    @Column(name = "valor_juros", precision = 15, scale = 2)
    private BigDecimal valorJuros;
    @Column(name = "valor_multa", precision = 15, scale = 2)
    private BigDecimal valorMulta;
    @Column(name = "valor_desconto", precision = 15, scale = 2)
    private BigDecimal valorDesconto;
    @Column(name = "valor_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorTotal;
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConta status;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;
    @Column(name = "categoria", length = 100)
    private String categoria;
    @Column(name = "centro_custo", length = 100)
    private String centroCusto;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @Column(name = "anexos", length = 500)
    private String anexos;
    @Column(name = "usuario_criacao", length = 100)
    private String usuarioCriacao;
    @Column(name = "usuario_aprovacao", length = 100)
    private String usuarioAprovacao;
    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;
    @Column(name = "moeda", length = 3, nullable = false)
    private String moeda;
    @Column(name = "taxa_cambio", precision = 10, scale = 6)
    private BigDecimal taxaCambio;
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
     * Status da conta a pagar
     */
    public enum StatusConta {
        PENDENTE,  // Pendente de pagamento
        APROVADA,  // Aprovada para pagamento
        PAGA,  // Paga
        VENCIDA,  // Vencida
        CANCELADA,  // Cancelada
        PARCELADA,  // Parcelada
        REJEITADA // Rejeitada
        ;
    }


    /**
     * Tipo de conta a pagar
     */
    public enum TipoConta {
        FORNECEDOR,  // Fornecedor
        EMPREGADO,  // Empregado (salários, benefícios)
        GOVERNO,  // Governo (impostos, taxas)
        BANCO,  // Bancário (empréstimos, financiamentos)
        SERVICO,  // Serviços
        MATERIAL,  // Materiais e suprimentos
        OUTROS // Outros
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class ContaPagarBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String numeroDocumento;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private Fornecedor fornecedor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorOriginal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorPago;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorJuros;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorMulta;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorDesconto;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotal;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataVencimento;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataPagamento;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataEmissao;
        @java.lang.SuppressWarnings("all")
        private StatusConta status;
        @java.lang.SuppressWarnings("all")
        private TipoConta tipoConta;
        @java.lang.SuppressWarnings("all")
        private String categoria;
        @java.lang.SuppressWarnings("all")
        private String centroCusto;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String anexos;
        @java.lang.SuppressWarnings("all")
        private String usuarioCriacao;
        @java.lang.SuppressWarnings("all")
        private String usuarioAprovacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAprovacao;
        @java.lang.SuppressWarnings("all")
        private String moeda;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaCambio;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        ContaPagarBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder numeroDocumento(final String numeroDocumento) {
            this.numeroDocumento = numeroDocumento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder fornecedor(final Fornecedor fornecedor) {
            this.fornecedor = fornecedor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorOriginal(final BigDecimal valorOriginal) {
            this.valorOriginal = valorOriginal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorPago(final BigDecimal valorPago) {
            this.valorPago = valorPago;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorJuros(final BigDecimal valorJuros) {
            this.valorJuros = valorJuros;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorMulta(final BigDecimal valorMulta) {
            this.valorMulta = valorMulta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorDesconto(final BigDecimal valorDesconto) {
            this.valorDesconto = valorDesconto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder valorTotal(final BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataVencimento(final LocalDate dataVencimento) {
            this.dataVencimento = dataVencimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataPagamento(final LocalDate dataPagamento) {
            this.dataPagamento = dataPagamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataEmissao(final LocalDate dataEmissao) {
            this.dataEmissao = dataEmissao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder status(final StatusConta status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder tipoConta(final TipoConta tipoConta) {
            this.tipoConta = tipoConta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder categoria(final String categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder centroCusto(final String centroCusto) {
            this.centroCusto = centroCusto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder anexos(final String anexos) {
            this.anexos = anexos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder usuarioCriacao(final String usuarioCriacao) {
            this.usuarioCriacao = usuarioCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder usuarioAprovacao(final String usuarioAprovacao) {
            this.usuarioAprovacao = usuarioAprovacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataAprovacao(final LocalDateTime dataAprovacao) {
            this.dataAprovacao = dataAprovacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder moeda(final String moeda) {
            this.moeda = moeda;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder taxaCambio(final BigDecimal taxaCambio) {
            this.taxaCambio = taxaCambio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ContaPagar.ContaPagarBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ContaPagar build() {
            return new ContaPagar(this.id, this.numeroDocumento, this.descricao, this.fornecedor, this.valorOriginal, this.valorPago, this.valorJuros, this.valorMulta, this.valorDesconto, this.valorTotal, this.dataVencimento, this.dataPagamento, this.dataEmissao, this.status, this.tipoConta, this.categoria, this.centroCusto, this.observacoes, this.anexos, this.usuarioCriacao, this.usuarioAprovacao, this.dataAprovacao, this.moeda, this.taxaCambio, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ContaPagar.ContaPagarBuilder(id=" + this.id + ", numeroDocumento=" + this.numeroDocumento + ", descricao=" + this.descricao + ", fornecedor=" + this.fornecedor + ", valorOriginal=" + this.valorOriginal + ", valorPago=" + this.valorPago + ", valorJuros=" + this.valorJuros + ", valorMulta=" + this.valorMulta + ", valorDesconto=" + this.valorDesconto + ", valorTotal=" + this.valorTotal + ", dataVencimento=" + this.dataVencimento + ", dataPagamento=" + this.dataPagamento + ", dataEmissao=" + this.dataEmissao + ", status=" + this.status + ", tipoConta=" + this.tipoConta + ", categoria=" + this.categoria + ", centroCusto=" + this.centroCusto + ", observacoes=" + this.observacoes + ", anexos=" + this.anexos + ", usuarioCriacao=" + this.usuarioCriacao + ", usuarioAprovacao=" + this.usuarioAprovacao + ", dataAprovacao=" + this.dataAprovacao + ", moeda=" + this.moeda + ", taxaCambio=" + this.taxaCambio + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ContaPagar.ContaPagarBuilder builder() {
        return new ContaPagar.ContaPagarBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorPago() {
        return this.valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorJuros() {
        return this.valorJuros;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMulta() {
        return this.valorMulta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataPagamento() {
        return this.dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConta getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public TipoConta getTipoConta() {
        return this.tipoConta;
    }

    @java.lang.SuppressWarnings("all")
    public String getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getCentroCusto() {
        return this.centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getAnexos() {
        return this.anexos;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioCriacao() {
        return this.usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAprovacao() {
        return this.usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoeda() {
        return this.moeda;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaCambio() {
        return this.taxaCambio;
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
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setFornecedor(final Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorPago(final BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorJuros(final BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMulta(final BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDesconto(final BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamento(final LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEmissao(final LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConta status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoConta(final TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setCentroCusto(final String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setAnexos(final String anexos) {
        this.anexos = anexos;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioCriacao(final String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAprovacao(final String usuarioAprovacao) {
        this.usuarioAprovacao = usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoeda(final String moeda) {
        this.moeda = moeda;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaCambio(final BigDecimal taxaCambio) {
        this.taxaCambio = taxaCambio;
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
        if (!(o instanceof ContaPagar)) return false;
        final ContaPagar other = (ContaPagar) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$numeroDocumento = this.getNumeroDocumento();
        final java.lang.Object other$numeroDocumento = other.getNumeroDocumento();
        if (this$numeroDocumento == null ? other$numeroDocumento != null : !this$numeroDocumento.equals(other$numeroDocumento)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$fornecedor = this.getFornecedor();
        final java.lang.Object other$fornecedor = other.getFornecedor();
        if (this$fornecedor == null ? other$fornecedor != null : !this$fornecedor.equals(other$fornecedor)) return false;
        final java.lang.Object this$valorOriginal = this.getValorOriginal();
        final java.lang.Object other$valorOriginal = other.getValorOriginal();
        if (this$valorOriginal == null ? other$valorOriginal != null : !this$valorOriginal.equals(other$valorOriginal)) return false;
        final java.lang.Object this$valorPago = this.getValorPago();
        final java.lang.Object other$valorPago = other.getValorPago();
        if (this$valorPago == null ? other$valorPago != null : !this$valorPago.equals(other$valorPago)) return false;
        final java.lang.Object this$valorJuros = this.getValorJuros();
        final java.lang.Object other$valorJuros = other.getValorJuros();
        if (this$valorJuros == null ? other$valorJuros != null : !this$valorJuros.equals(other$valorJuros)) return false;
        final java.lang.Object this$valorMulta = this.getValorMulta();
        final java.lang.Object other$valorMulta = other.getValorMulta();
        if (this$valorMulta == null ? other$valorMulta != null : !this$valorMulta.equals(other$valorMulta)) return false;
        final java.lang.Object this$valorDesconto = this.getValorDesconto();
        final java.lang.Object other$valorDesconto = other.getValorDesconto();
        if (this$valorDesconto == null ? other$valorDesconto != null : !this$valorDesconto.equals(other$valorDesconto)) return false;
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataPagamento = this.getDataPagamento();
        final java.lang.Object other$dataPagamento = other.getDataPagamento();
        if (this$dataPagamento == null ? other$dataPagamento != null : !this$dataPagamento.equals(other$dataPagamento)) return false;
        final java.lang.Object this$dataEmissao = this.getDataEmissao();
        final java.lang.Object other$dataEmissao = other.getDataEmissao();
        if (this$dataEmissao == null ? other$dataEmissao != null : !this$dataEmissao.equals(other$dataEmissao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$tipoConta = this.getTipoConta();
        final java.lang.Object other$tipoConta = other.getTipoConta();
        if (this$tipoConta == null ? other$tipoConta != null : !this$tipoConta.equals(other$tipoConta)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$centroCusto = this.getCentroCusto();
        final java.lang.Object other$centroCusto = other.getCentroCusto();
        if (this$centroCusto == null ? other$centroCusto != null : !this$centroCusto.equals(other$centroCusto)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$anexos = this.getAnexos();
        final java.lang.Object other$anexos = other.getAnexos();
        if (this$anexos == null ? other$anexos != null : !this$anexos.equals(other$anexos)) return false;
        final java.lang.Object this$usuarioCriacao = this.getUsuarioCriacao();
        final java.lang.Object other$usuarioCriacao = other.getUsuarioCriacao();
        if (this$usuarioCriacao == null ? other$usuarioCriacao != null : !this$usuarioCriacao.equals(other$usuarioCriacao)) return false;
        final java.lang.Object this$usuarioAprovacao = this.getUsuarioAprovacao();
        final java.lang.Object other$usuarioAprovacao = other.getUsuarioAprovacao();
        if (this$usuarioAprovacao == null ? other$usuarioAprovacao != null : !this$usuarioAprovacao.equals(other$usuarioAprovacao)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        final java.lang.Object this$moeda = this.getMoeda();
        final java.lang.Object other$moeda = other.getMoeda();
        if (this$moeda == null ? other$moeda != null : !this$moeda.equals(other$moeda)) return false;
        final java.lang.Object this$taxaCambio = this.getTaxaCambio();
        final java.lang.Object other$taxaCambio = other.getTaxaCambio();
        if (this$taxaCambio == null ? other$taxaCambio != null : !this$taxaCambio.equals(other$taxaCambio)) return false;
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
        return other instanceof ContaPagar;
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
        final java.lang.Object $numeroDocumento = this.getNumeroDocumento();
        result = result * PRIME + ($numeroDocumento == null ? 43 : $numeroDocumento.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $fornecedor = this.getFornecedor();
        result = result * PRIME + ($fornecedor == null ? 43 : $fornecedor.hashCode());
        final java.lang.Object $valorOriginal = this.getValorOriginal();
        result = result * PRIME + ($valorOriginal == null ? 43 : $valorOriginal.hashCode());
        final java.lang.Object $valorPago = this.getValorPago();
        result = result * PRIME + ($valorPago == null ? 43 : $valorPago.hashCode());
        final java.lang.Object $valorJuros = this.getValorJuros();
        result = result * PRIME + ($valorJuros == null ? 43 : $valorJuros.hashCode());
        final java.lang.Object $valorMulta = this.getValorMulta();
        result = result * PRIME + ($valorMulta == null ? 43 : $valorMulta.hashCode());
        final java.lang.Object $valorDesconto = this.getValorDesconto();
        result = result * PRIME + ($valorDesconto == null ? 43 : $valorDesconto.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataPagamento = this.getDataPagamento();
        result = result * PRIME + ($dataPagamento == null ? 43 : $dataPagamento.hashCode());
        final java.lang.Object $dataEmissao = this.getDataEmissao();
        result = result * PRIME + ($dataEmissao == null ? 43 : $dataEmissao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $tipoConta = this.getTipoConta();
        result = result * PRIME + ($tipoConta == null ? 43 : $tipoConta.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $centroCusto = this.getCentroCusto();
        result = result * PRIME + ($centroCusto == null ? 43 : $centroCusto.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $anexos = this.getAnexos();
        result = result * PRIME + ($anexos == null ? 43 : $anexos.hashCode());
        final java.lang.Object $usuarioCriacao = this.getUsuarioCriacao();
        result = result * PRIME + ($usuarioCriacao == null ? 43 : $usuarioCriacao.hashCode());
        final java.lang.Object $usuarioAprovacao = this.getUsuarioAprovacao();
        result = result * PRIME + ($usuarioAprovacao == null ? 43 : $usuarioAprovacao.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        final java.lang.Object $moeda = this.getMoeda();
        result = result * PRIME + ($moeda == null ? 43 : $moeda.hashCode());
        final java.lang.Object $taxaCambio = this.getTaxaCambio();
        result = result * PRIME + ($taxaCambio == null ? 43 : $taxaCambio.hashCode());
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
        return "ContaPagar(id=" + this.getId() + ", numeroDocumento=" + this.getNumeroDocumento() + ", descricao=" + this.getDescricao() + ", fornecedor=" + this.getFornecedor() + ", valorOriginal=" + this.getValorOriginal() + ", valorPago=" + this.getValorPago() + ", valorJuros=" + this.getValorJuros() + ", valorMulta=" + this.getValorMulta() + ", valorDesconto=" + this.getValorDesconto() + ", valorTotal=" + this.getValorTotal() + ", dataVencimento=" + this.getDataVencimento() + ", dataPagamento=" + this.getDataPagamento() + ", dataEmissao=" + this.getDataEmissao() + ", status=" + this.getStatus() + ", tipoConta=" + this.getTipoConta() + ", categoria=" + this.getCategoria() + ", centroCusto=" + this.getCentroCusto() + ", observacoes=" + this.getObservacoes() + ", anexos=" + this.getAnexos() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", usuarioAprovacao=" + this.getUsuarioAprovacao() + ", dataAprovacao=" + this.getDataAprovacao() + ", moeda=" + this.getMoeda() + ", taxaCambio=" + this.getTaxaCambio() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ContaPagar() {
    }

    @java.lang.SuppressWarnings("all")
    public ContaPagar(final Long id, final String numeroDocumento, final String descricao, final Fornecedor fornecedor, final BigDecimal valorOriginal, final BigDecimal valorPago, final BigDecimal valorJuros, final BigDecimal valorMulta, final BigDecimal valorDesconto, final BigDecimal valorTotal, final LocalDate dataVencimento, final LocalDate dataPagamento, final LocalDate dataEmissao, final StatusConta status, final TipoConta tipoConta, final String categoria, final String centroCusto, final String observacoes, final String anexos, final String usuarioCriacao, final String usuarioAprovacao, final LocalDateTime dataAprovacao, final String moeda, final BigDecimal taxaCambio, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.numeroDocumento = numeroDocumento;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
        this.valorOriginal = valorOriginal;
        this.valorPago = valorPago;
        this.valorJuros = valorJuros;
        this.valorMulta = valorMulta;
        this.valorDesconto = valorDesconto;
        this.valorTotal = valorTotal;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.dataEmissao = dataEmissao;
        this.status = status;
        this.tipoConta = tipoConta;
        this.categoria = categoria;
        this.centroCusto = centroCusto;
        this.observacoes = observacoes;
        this.anexos = anexos;
        this.usuarioCriacao = usuarioCriacao;
        this.usuarioAprovacao = usuarioAprovacao;
        this.dataAprovacao = dataAprovacao;
        this.moeda = moeda;
        this.taxaCambio = taxaCambio;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
