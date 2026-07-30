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
 * Entidade que representa o saldo de uma conta contábil
 * 
 * Mantém o saldo atualizado de cada conta do plano de contas,
 * permitindo consultas rápidas e relatórios contábeis.
 */
@Entity
@Table(name = "saldos_contas", schema = "aurix")
public class SaldoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private PlanoContas conta;
    @Column(name = "data_saldo", nullable = false)
    private LocalDate dataSaldo;
    @Column(name = "saldo_anterior", precision = 15, scale = 2)
    private BigDecimal saldoAnterior;
    @Column(name = "debitos_periodo", precision = 15, scale = 2)
    private BigDecimal debitosPeriodo;
    @Column(name = "creditos_periodo", precision = 15, scale = 2)
    private BigDecimal creditosPeriodo;
    @Column(name = "saldo_atual", precision = 15, scale = 2)
    private BigDecimal saldoAtual;
    @Column(name = "saldo_devedor", precision = 15, scale = 2)
    private BigDecimal saldoDevedor;
    @Column(name = "saldo_credor", precision = 15, scale = 2)
    private BigDecimal saldoCredor;
    @Column(name = "movimentacao_debito", precision = 15, scale = 2)
    private BigDecimal movimentacaoDebito;
    @Column(name = "movimentacao_credito", precision = 15, scale = 2)
    private BigDecimal movimentacaoCredito;
    @Column(name = "quantidade_lancamentos")
    private Long quantidadeLancamentos;
    @Column(name = "moeda", length = 3, nullable = false)
    private String moeda;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSaldo status;
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    @Column(name = "usuario_fechamento", length = 100)
    private String usuarioFechamento;
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
     * Status do saldo
     */
    public enum StatusSaldo {
        ABERTO,  // Saldo aberto
        FECHADO,  // Saldo fechado
        CONCILIADO,  // Saldo conciliado
        AJUSTADO // Saldo ajustado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class SaldoContaBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private PlanoContas conta;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataSaldo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoAnterior;
        @java.lang.SuppressWarnings("all")
        private BigDecimal debitosPeriodo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal creditosPeriodo;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoAtual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoDevedor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoCredor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal movimentacaoDebito;
        @java.lang.SuppressWarnings("all")
        private BigDecimal movimentacaoCredito;
        @java.lang.SuppressWarnings("all")
        private Long quantidadeLancamentos;
        @java.lang.SuppressWarnings("all")
        private String moeda;
        @java.lang.SuppressWarnings("all")
        private StatusSaldo status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataFechamento;
        @java.lang.SuppressWarnings("all")
        private String usuarioFechamento;
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
        SaldoContaBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder conta(final PlanoContas conta) {
            this.conta = conta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder dataSaldo(final LocalDate dataSaldo) {
            this.dataSaldo = dataSaldo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder saldoAnterior(final BigDecimal saldoAnterior) {
            this.saldoAnterior = saldoAnterior;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder debitosPeriodo(final BigDecimal debitosPeriodo) {
            this.debitosPeriodo = debitosPeriodo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder creditosPeriodo(final BigDecimal creditosPeriodo) {
            this.creditosPeriodo = creditosPeriodo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder saldoAtual(final BigDecimal saldoAtual) {
            this.saldoAtual = saldoAtual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder saldoDevedor(final BigDecimal saldoDevedor) {
            this.saldoDevedor = saldoDevedor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder saldoCredor(final BigDecimal saldoCredor) {
            this.saldoCredor = saldoCredor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder movimentacaoDebito(final BigDecimal movimentacaoDebito) {
            this.movimentacaoDebito = movimentacaoDebito;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder movimentacaoCredito(final BigDecimal movimentacaoCredito) {
            this.movimentacaoCredito = movimentacaoCredito;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder quantidadeLancamentos(final Long quantidadeLancamentos) {
            this.quantidadeLancamentos = quantidadeLancamentos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder moeda(final String moeda) {
            this.moeda = moeda;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder status(final StatusSaldo status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder dataFechamento(final LocalDateTime dataFechamento) {
            this.dataFechamento = dataFechamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder usuarioFechamento(final String usuarioFechamento) {
            this.usuarioFechamento = usuarioFechamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SaldoConta.SaldoContaBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SaldoConta build() {
            return new SaldoConta(this.id, this.conta, this.dataSaldo, this.saldoAnterior, this.debitosPeriodo, this.creditosPeriodo, this.saldoAtual, this.saldoDevedor, this.saldoCredor, this.movimentacaoDebito, this.movimentacaoCredito, this.quantidadeLancamentos, this.moeda, this.status, this.dataFechamento, this.usuarioFechamento, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SaldoConta.SaldoContaBuilder(id=" + this.id + ", conta=" + this.conta + ", dataSaldo=" + this.dataSaldo + ", saldoAnterior=" + this.saldoAnterior + ", debitosPeriodo=" + this.debitosPeriodo + ", creditosPeriodo=" + this.creditosPeriodo + ", saldoAtual=" + this.saldoAtual + ", saldoDevedor=" + this.saldoDevedor + ", saldoCredor=" + this.saldoCredor + ", movimentacaoDebito=" + this.movimentacaoDebito + ", movimentacaoCredito=" + this.movimentacaoCredito + ", quantidadeLancamentos=" + this.quantidadeLancamentos + ", moeda=" + this.moeda + ", status=" + this.status + ", dataFechamento=" + this.dataFechamento + ", usuarioFechamento=" + this.usuarioFechamento + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SaldoConta.SaldoContaBuilder builder() {
        return new SaldoConta.SaldoContaBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public PlanoContas getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataSaldo() {
        return this.dataSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoAnterior() {
        return this.saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDebitosPeriodo() {
        return this.debitosPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCreditosPeriodo() {
        return this.creditosPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoAtual() {
        return this.saldoAtual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoDevedor() {
        return this.saldoDevedor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoCredor() {
        return this.saldoCredor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMovimentacaoDebito() {
        return this.movimentacaoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMovimentacaoCredito() {
        return this.movimentacaoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public Long getQuantidadeLancamentos() {
        return this.quantidadeLancamentos;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoeda() {
        return this.moeda;
    }

    @java.lang.SuppressWarnings("all")
    public StatusSaldo getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFechamento() {
        return this.dataFechamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioFechamento() {
        return this.usuarioFechamento;
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
    public void setConta(final PlanoContas conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSaldo(final LocalDate dataSaldo) {
        this.dataSaldo = dataSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoAnterior(final BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setDebitosPeriodo(final BigDecimal debitosPeriodo) {
        this.debitosPeriodo = debitosPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public void setCreditosPeriodo(final BigDecimal creditosPeriodo) {
        this.creditosPeriodo = creditosPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoAtual(final BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoDevedor(final BigDecimal saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoCredor(final BigDecimal saldoCredor) {
        this.saldoCredor = saldoCredor;
    }

    @java.lang.SuppressWarnings("all")
    public void setMovimentacaoDebito(final BigDecimal movimentacaoDebito) {
        this.movimentacaoDebito = movimentacaoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setMovimentacaoCredito(final BigDecimal movimentacaoCredito) {
        this.movimentacaoCredito = movimentacaoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeLancamentos(final Long quantidadeLancamentos) {
        this.quantidadeLancamentos = quantidadeLancamentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoeda(final String moeda) {
        this.moeda = moeda;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSaldo status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFechamento(final LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioFechamento(final String usuarioFechamento) {
        this.usuarioFechamento = usuarioFechamento;
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
        if (!(o instanceof SaldoConta)) return false;
        final SaldoConta other = (SaldoConta) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeLancamentos = this.getQuantidadeLancamentos();
        final java.lang.Object other$quantidadeLancamentos = other.getQuantidadeLancamentos();
        if (this$quantidadeLancamentos == null ? other$quantidadeLancamentos != null : !this$quantidadeLancamentos.equals(other$quantidadeLancamentos)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$dataSaldo = this.getDataSaldo();
        final java.lang.Object other$dataSaldo = other.getDataSaldo();
        if (this$dataSaldo == null ? other$dataSaldo != null : !this$dataSaldo.equals(other$dataSaldo)) return false;
        final java.lang.Object this$saldoAnterior = this.getSaldoAnterior();
        final java.lang.Object other$saldoAnterior = other.getSaldoAnterior();
        if (this$saldoAnterior == null ? other$saldoAnterior != null : !this$saldoAnterior.equals(other$saldoAnterior)) return false;
        final java.lang.Object this$debitosPeriodo = this.getDebitosPeriodo();
        final java.lang.Object other$debitosPeriodo = other.getDebitosPeriodo();
        if (this$debitosPeriodo == null ? other$debitosPeriodo != null : !this$debitosPeriodo.equals(other$debitosPeriodo)) return false;
        final java.lang.Object this$creditosPeriodo = this.getCreditosPeriodo();
        final java.lang.Object other$creditosPeriodo = other.getCreditosPeriodo();
        if (this$creditosPeriodo == null ? other$creditosPeriodo != null : !this$creditosPeriodo.equals(other$creditosPeriodo)) return false;
        final java.lang.Object this$saldoAtual = this.getSaldoAtual();
        final java.lang.Object other$saldoAtual = other.getSaldoAtual();
        if (this$saldoAtual == null ? other$saldoAtual != null : !this$saldoAtual.equals(other$saldoAtual)) return false;
        final java.lang.Object this$saldoDevedor = this.getSaldoDevedor();
        final java.lang.Object other$saldoDevedor = other.getSaldoDevedor();
        if (this$saldoDevedor == null ? other$saldoDevedor != null : !this$saldoDevedor.equals(other$saldoDevedor)) return false;
        final java.lang.Object this$saldoCredor = this.getSaldoCredor();
        final java.lang.Object other$saldoCredor = other.getSaldoCredor();
        if (this$saldoCredor == null ? other$saldoCredor != null : !this$saldoCredor.equals(other$saldoCredor)) return false;
        final java.lang.Object this$movimentacaoDebito = this.getMovimentacaoDebito();
        final java.lang.Object other$movimentacaoDebito = other.getMovimentacaoDebito();
        if (this$movimentacaoDebito == null ? other$movimentacaoDebito != null : !this$movimentacaoDebito.equals(other$movimentacaoDebito)) return false;
        final java.lang.Object this$movimentacaoCredito = this.getMovimentacaoCredito();
        final java.lang.Object other$movimentacaoCredito = other.getMovimentacaoCredito();
        if (this$movimentacaoCredito == null ? other$movimentacaoCredito != null : !this$movimentacaoCredito.equals(other$movimentacaoCredito)) return false;
        final java.lang.Object this$moeda = this.getMoeda();
        final java.lang.Object other$moeda = other.getMoeda();
        if (this$moeda == null ? other$moeda != null : !this$moeda.equals(other$moeda)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataFechamento = this.getDataFechamento();
        final java.lang.Object other$dataFechamento = other.getDataFechamento();
        if (this$dataFechamento == null ? other$dataFechamento != null : !this$dataFechamento.equals(other$dataFechamento)) return false;
        final java.lang.Object this$usuarioFechamento = this.getUsuarioFechamento();
        final java.lang.Object other$usuarioFechamento = other.getUsuarioFechamento();
        if (this$usuarioFechamento == null ? other$usuarioFechamento != null : !this$usuarioFechamento.equals(other$usuarioFechamento)) return false;
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
        return other instanceof SaldoConta;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeLancamentos = this.getQuantidadeLancamentos();
        result = result * PRIME + ($quantidadeLancamentos == null ? 43 : $quantidadeLancamentos.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $dataSaldo = this.getDataSaldo();
        result = result * PRIME + ($dataSaldo == null ? 43 : $dataSaldo.hashCode());
        final java.lang.Object $saldoAnterior = this.getSaldoAnterior();
        result = result * PRIME + ($saldoAnterior == null ? 43 : $saldoAnterior.hashCode());
        final java.lang.Object $debitosPeriodo = this.getDebitosPeriodo();
        result = result * PRIME + ($debitosPeriodo == null ? 43 : $debitosPeriodo.hashCode());
        final java.lang.Object $creditosPeriodo = this.getCreditosPeriodo();
        result = result * PRIME + ($creditosPeriodo == null ? 43 : $creditosPeriodo.hashCode());
        final java.lang.Object $saldoAtual = this.getSaldoAtual();
        result = result * PRIME + ($saldoAtual == null ? 43 : $saldoAtual.hashCode());
        final java.lang.Object $saldoDevedor = this.getSaldoDevedor();
        result = result * PRIME + ($saldoDevedor == null ? 43 : $saldoDevedor.hashCode());
        final java.lang.Object $saldoCredor = this.getSaldoCredor();
        result = result * PRIME + ($saldoCredor == null ? 43 : $saldoCredor.hashCode());
        final java.lang.Object $movimentacaoDebito = this.getMovimentacaoDebito();
        result = result * PRIME + ($movimentacaoDebito == null ? 43 : $movimentacaoDebito.hashCode());
        final java.lang.Object $movimentacaoCredito = this.getMovimentacaoCredito();
        result = result * PRIME + ($movimentacaoCredito == null ? 43 : $movimentacaoCredito.hashCode());
        final java.lang.Object $moeda = this.getMoeda();
        result = result * PRIME + ($moeda == null ? 43 : $moeda.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataFechamento = this.getDataFechamento();
        result = result * PRIME + ($dataFechamento == null ? 43 : $dataFechamento.hashCode());
        final java.lang.Object $usuarioFechamento = this.getUsuarioFechamento();
        result = result * PRIME + ($usuarioFechamento == null ? 43 : $usuarioFechamento.hashCode());
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
        return "SaldoConta(id=" + this.getId() + ", conta=" + this.getConta() + ", dataSaldo=" + this.getDataSaldo() + ", saldoAnterior=" + this.getSaldoAnterior() + ", debitosPeriodo=" + this.getDebitosPeriodo() + ", creditosPeriodo=" + this.getCreditosPeriodo() + ", saldoAtual=" + this.getSaldoAtual() + ", saldoDevedor=" + this.getSaldoDevedor() + ", saldoCredor=" + this.getSaldoCredor() + ", movimentacaoDebito=" + this.getMovimentacaoDebito() + ", movimentacaoCredito=" + this.getMovimentacaoCredito() + ", quantidadeLancamentos=" + this.getQuantidadeLancamentos() + ", moeda=" + this.getMoeda() + ", status=" + this.getStatus() + ", dataFechamento=" + this.getDataFechamento() + ", usuarioFechamento=" + this.getUsuarioFechamento() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SaldoConta() {
    }

    @java.lang.SuppressWarnings("all")
    public SaldoConta(final Long id, final PlanoContas conta, final LocalDate dataSaldo, final BigDecimal saldoAnterior, final BigDecimal debitosPeriodo, final BigDecimal creditosPeriodo, final BigDecimal saldoAtual, final BigDecimal saldoDevedor, final BigDecimal saldoCredor, final BigDecimal movimentacaoDebito, final BigDecimal movimentacaoCredito, final Long quantidadeLancamentos, final String moeda, final StatusSaldo status, final LocalDateTime dataFechamento, final String usuarioFechamento, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.conta = conta;
        this.dataSaldo = dataSaldo;
        this.saldoAnterior = saldoAnterior;
        this.debitosPeriodo = debitosPeriodo;
        this.creditosPeriodo = creditosPeriodo;
        this.saldoAtual = saldoAtual;
        this.saldoDevedor = saldoDevedor;
        this.saldoCredor = saldoCredor;
        this.movimentacaoDebito = movimentacaoDebito;
        this.movimentacaoCredito = movimentacaoCredito;
        this.quantidadeLancamentos = quantidadeLancamentos;
        this.moeda = moeda;
        this.status = status;
        this.dataFechamento = dataFechamento;
        this.usuarioFechamento = usuarioFechamento;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
