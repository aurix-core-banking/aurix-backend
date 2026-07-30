package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um item de orçamento
 * 
 * Detalha as receitas e despesas do orçamento
 */
@Entity
@Table(name = "itens_orcamento", schema = "aurix")
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;
    @Column(name = "codigo_item", nullable = false, length = 50)
    private String codigoItem;
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false)
    private TipoItem tipoItem;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaItem categoria;
    @Column(name = "centro_custo", length = 100)
    private String centroCusto;
    @Column(name = "conta_contabil", length = 50)
    private String contaContabil;
    @Column(name = "valor_orcado", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorOrcado;
    @Column(name = "valor_realizado", precision = 15, scale = 2)
    private BigDecimal valorRealizado;
    @Column(name = "variacao_valor", precision = 15, scale = 2)
    private BigDecimal variacaoValor;
    @Column(name = "percentual_variacao", precision = 8, scale = 4)
    private BigDecimal percentualVariacao;
    @Column(name = "meta_mensal", precision = 15, scale = 2)
    private BigDecimal metaMensal;
    @Column(name = "realizado_mes_1", precision = 15, scale = 2)
    private BigDecimal realizadoMes1;
    @Column(name = "realizado_mes_2", precision = 15, scale = 2)
    private BigDecimal realizadoMes2;
    @Column(name = "realizado_mes_3", precision = 15, scale = 2)
    private BigDecimal realizadoMes3;
    @Column(name = "realizado_mes_4", precision = 15, scale = 2)
    private BigDecimal realizadoMes4;
    @Column(name = "realizado_mes_5", precision = 15, scale = 2)
    private BigDecimal realizadoMes5;
    @Column(name = "realizado_mes_6", precision = 15, scale = 2)
    private BigDecimal realizadoMes6;
    @Column(name = "realizado_mes_7", precision = 15, scale = 2)
    private BigDecimal realizadoMes7;
    @Column(name = "realizado_mes_8", precision = 15, scale = 2)
    private BigDecimal realizadoMes8;
    @Column(name = "realizado_mes_9", precision = 15, scale = 2)
    private BigDecimal realizadoMes9;
    @Column(name = "realizado_mes_10", precision = 15, scale = 2)
    private BigDecimal realizadoMes10;
    @Column(name = "realizado_mes_11", precision = 15, scale = 2)
    private BigDecimal realizadoMes11;
    @Column(name = "realizado_mes_12", precision = 15, scale = 2)
    private BigDecimal realizadoMes12;
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
     * Tipo do item de orçamento
     */
    public enum TipoItem {
        RECEITA,  // Receita
        DESPESA,  // Despesa
        INVESTIMENTO,  // Investimento
        CAPITAL,  // Capital
        RESERVA // Reserva
        ;
    }


    /**
     * Categoria do item
     */
    public enum CategoriaItem {
        OPERACIONAL,  // Operacional
        ADMINISTRATIVO,  // Administrativo
        COMERCIAL,  // Comercial
        FINANCEIRO,  // Financeiro
        INVESTIMENTO,  // Investimento
        TECNOLOGIA,  // Tecnologia
        RH,  // Recursos Humanos
        INFRAESTRUTURA,  // Infraestrutura
        OUTROS // Outros
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class ItemOrcamentoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Orcamento orcamento;
        @java.lang.SuppressWarnings("all")
        private String codigoItem;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoItem tipoItem;
        @java.lang.SuppressWarnings("all")
        private CategoriaItem categoria;
        @java.lang.SuppressWarnings("all")
        private String centroCusto;
        @java.lang.SuppressWarnings("all")
        private String contaContabil;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorOrcado;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorRealizado;
        @java.lang.SuppressWarnings("all")
        private BigDecimal variacaoValor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualVariacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal metaMensal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes1;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes2;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes3;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes4;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes5;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes6;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes7;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes8;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes9;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes10;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes11;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes12;
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
        ItemOrcamentoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder orcamento(final Orcamento orcamento) {
            this.orcamento = orcamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder codigoItem(final String codigoItem) {
            this.codigoItem = codigoItem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder tipoItem(final TipoItem tipoItem) {
            this.tipoItem = tipoItem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder categoria(final CategoriaItem categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder centroCusto(final String centroCusto) {
            this.centroCusto = centroCusto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder contaContabil(final String contaContabil) {
            this.contaContabil = contaContabil;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder valorOrcado(final BigDecimal valorOrcado) {
            this.valorOrcado = valorOrcado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder valorRealizado(final BigDecimal valorRealizado) {
            this.valorRealizado = valorRealizado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder variacaoValor(final BigDecimal variacaoValor) {
            this.variacaoValor = variacaoValor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder percentualVariacao(final BigDecimal percentualVariacao) {
            this.percentualVariacao = percentualVariacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder metaMensal(final BigDecimal metaMensal) {
            this.metaMensal = metaMensal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes1(final BigDecimal realizadoMes1) {
            this.realizadoMes1 = realizadoMes1;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes2(final BigDecimal realizadoMes2) {
            this.realizadoMes2 = realizadoMes2;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes3(final BigDecimal realizadoMes3) {
            this.realizadoMes3 = realizadoMes3;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes4(final BigDecimal realizadoMes4) {
            this.realizadoMes4 = realizadoMes4;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes5(final BigDecimal realizadoMes5) {
            this.realizadoMes5 = realizadoMes5;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes6(final BigDecimal realizadoMes6) {
            this.realizadoMes6 = realizadoMes6;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes7(final BigDecimal realizadoMes7) {
            this.realizadoMes7 = realizadoMes7;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes8(final BigDecimal realizadoMes8) {
            this.realizadoMes8 = realizadoMes8;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes9(final BigDecimal realizadoMes9) {
            this.realizadoMes9 = realizadoMes9;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes10(final BigDecimal realizadoMes10) {
            this.realizadoMes10 = realizadoMes10;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes11(final BigDecimal realizadoMes11) {
            this.realizadoMes11 = realizadoMes11;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder realizadoMes12(final BigDecimal realizadoMes12) {
            this.realizadoMes12 = realizadoMes12;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemOrcamento.ItemOrcamentoBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ItemOrcamento build() {
            return new ItemOrcamento(this.id, this.orcamento, this.codigoItem, this.descricao, this.tipoItem, this.categoria, this.centroCusto, this.contaContabil, this.valorOrcado, this.valorRealizado, this.variacaoValor, this.percentualVariacao, this.metaMensal, this.realizadoMes1, this.realizadoMes2, this.realizadoMes3, this.realizadoMes4, this.realizadoMes5, this.realizadoMes6, this.realizadoMes7, this.realizadoMes8, this.realizadoMes9, this.realizadoMes10, this.realizadoMes11, this.realizadoMes12, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ItemOrcamento.ItemOrcamentoBuilder(id=" + this.id + ", orcamento=" + this.orcamento + ", codigoItem=" + this.codigoItem + ", descricao=" + this.descricao + ", tipoItem=" + this.tipoItem + ", categoria=" + this.categoria + ", centroCusto=" + this.centroCusto + ", contaContabil=" + this.contaContabil + ", valorOrcado=" + this.valorOrcado + ", valorRealizado=" + this.valorRealizado + ", variacaoValor=" + this.variacaoValor + ", percentualVariacao=" + this.percentualVariacao + ", metaMensal=" + this.metaMensal + ", realizadoMes1=" + this.realizadoMes1 + ", realizadoMes2=" + this.realizadoMes2 + ", realizadoMes3=" + this.realizadoMes3 + ", realizadoMes4=" + this.realizadoMes4 + ", realizadoMes5=" + this.realizadoMes5 + ", realizadoMes6=" + this.realizadoMes6 + ", realizadoMes7=" + this.realizadoMes7 + ", realizadoMes8=" + this.realizadoMes8 + ", realizadoMes9=" + this.realizadoMes9 + ", realizadoMes10=" + this.realizadoMes10 + ", realizadoMes11=" + this.realizadoMes11 + ", realizadoMes12=" + this.realizadoMes12 + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ItemOrcamento.ItemOrcamentoBuilder builder() {
        return new ItemOrcamento.ItemOrcamentoBuilder();
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
    public String getCodigoItem() {
        return this.codigoItem;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoItem getTipoItem() {
        return this.tipoItem;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaItem getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getCentroCusto() {
        return this.centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaContabil() {
        return this.contaContabil;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOrcado() {
        return this.valorOrcado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorRealizado() {
        return this.valorRealizado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getVariacaoValor() {
        return this.variacaoValor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualVariacao() {
        return this.percentualVariacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMetaMensal() {
        return this.metaMensal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes1() {
        return this.realizadoMes1;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes2() {
        return this.realizadoMes2;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes3() {
        return this.realizadoMes3;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes4() {
        return this.realizadoMes4;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes5() {
        return this.realizadoMes5;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes6() {
        return this.realizadoMes6;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes7() {
        return this.realizadoMes7;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes8() {
        return this.realizadoMes8;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes9() {
        return this.realizadoMes9;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes10() {
        return this.realizadoMes10;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes11() {
        return this.realizadoMes11;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes12() {
        return this.realizadoMes12;
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
    public void setCodigoItem(final String codigoItem) {
        this.codigoItem = codigoItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoItem(final TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaItem categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setCentroCusto(final String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaContabil(final String contaContabil) {
        this.contaContabil = contaContabil;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOrcado(final BigDecimal valorOrcado) {
        this.valorOrcado = valorOrcado;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorRealizado(final BigDecimal valorRealizado) {
        this.valorRealizado = valorRealizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setVariacaoValor(final BigDecimal variacaoValor) {
        this.variacaoValor = variacaoValor;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualVariacao(final BigDecimal percentualVariacao) {
        this.percentualVariacao = percentualVariacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetaMensal(final BigDecimal metaMensal) {
        this.metaMensal = metaMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes1(final BigDecimal realizadoMes1) {
        this.realizadoMes1 = realizadoMes1;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes2(final BigDecimal realizadoMes2) {
        this.realizadoMes2 = realizadoMes2;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes3(final BigDecimal realizadoMes3) {
        this.realizadoMes3 = realizadoMes3;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes4(final BigDecimal realizadoMes4) {
        this.realizadoMes4 = realizadoMes4;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes5(final BigDecimal realizadoMes5) {
        this.realizadoMes5 = realizadoMes5;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes6(final BigDecimal realizadoMes6) {
        this.realizadoMes6 = realizadoMes6;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes7(final BigDecimal realizadoMes7) {
        this.realizadoMes7 = realizadoMes7;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes8(final BigDecimal realizadoMes8) {
        this.realizadoMes8 = realizadoMes8;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes9(final BigDecimal realizadoMes9) {
        this.realizadoMes9 = realizadoMes9;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes10(final BigDecimal realizadoMes10) {
        this.realizadoMes10 = realizadoMes10;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes11(final BigDecimal realizadoMes11) {
        this.realizadoMes11 = realizadoMes11;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes12(final BigDecimal realizadoMes12) {
        this.realizadoMes12 = realizadoMes12;
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
        if (!(o instanceof ItemOrcamento)) return false;
        final ItemOrcamento other = (ItemOrcamento) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$orcamento = this.getOrcamento();
        final java.lang.Object other$orcamento = other.getOrcamento();
        if (this$orcamento == null ? other$orcamento != null : !this$orcamento.equals(other$orcamento)) return false;
        final java.lang.Object this$codigoItem = this.getCodigoItem();
        final java.lang.Object other$codigoItem = other.getCodigoItem();
        if (this$codigoItem == null ? other$codigoItem != null : !this$codigoItem.equals(other$codigoItem)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoItem = this.getTipoItem();
        final java.lang.Object other$tipoItem = other.getTipoItem();
        if (this$tipoItem == null ? other$tipoItem != null : !this$tipoItem.equals(other$tipoItem)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$centroCusto = this.getCentroCusto();
        final java.lang.Object other$centroCusto = other.getCentroCusto();
        if (this$centroCusto == null ? other$centroCusto != null : !this$centroCusto.equals(other$centroCusto)) return false;
        final java.lang.Object this$contaContabil = this.getContaContabil();
        final java.lang.Object other$contaContabil = other.getContaContabil();
        if (this$contaContabil == null ? other$contaContabil != null : !this$contaContabil.equals(other$contaContabil)) return false;
        final java.lang.Object this$valorOrcado = this.getValorOrcado();
        final java.lang.Object other$valorOrcado = other.getValorOrcado();
        if (this$valorOrcado == null ? other$valorOrcado != null : !this$valorOrcado.equals(other$valorOrcado)) return false;
        final java.lang.Object this$valorRealizado = this.getValorRealizado();
        final java.lang.Object other$valorRealizado = other.getValorRealizado();
        if (this$valorRealizado == null ? other$valorRealizado != null : !this$valorRealizado.equals(other$valorRealizado)) return false;
        final java.lang.Object this$variacaoValor = this.getVariacaoValor();
        final java.lang.Object other$variacaoValor = other.getVariacaoValor();
        if (this$variacaoValor == null ? other$variacaoValor != null : !this$variacaoValor.equals(other$variacaoValor)) return false;
        final java.lang.Object this$percentualVariacao = this.getPercentualVariacao();
        final java.lang.Object other$percentualVariacao = other.getPercentualVariacao();
        if (this$percentualVariacao == null ? other$percentualVariacao != null : !this$percentualVariacao.equals(other$percentualVariacao)) return false;
        final java.lang.Object this$metaMensal = this.getMetaMensal();
        final java.lang.Object other$metaMensal = other.getMetaMensal();
        if (this$metaMensal == null ? other$metaMensal != null : !this$metaMensal.equals(other$metaMensal)) return false;
        final java.lang.Object this$realizadoMes1 = this.getRealizadoMes1();
        final java.lang.Object other$realizadoMes1 = other.getRealizadoMes1();
        if (this$realizadoMes1 == null ? other$realizadoMes1 != null : !this$realizadoMes1.equals(other$realizadoMes1)) return false;
        final java.lang.Object this$realizadoMes2 = this.getRealizadoMes2();
        final java.lang.Object other$realizadoMes2 = other.getRealizadoMes2();
        if (this$realizadoMes2 == null ? other$realizadoMes2 != null : !this$realizadoMes2.equals(other$realizadoMes2)) return false;
        final java.lang.Object this$realizadoMes3 = this.getRealizadoMes3();
        final java.lang.Object other$realizadoMes3 = other.getRealizadoMes3();
        if (this$realizadoMes3 == null ? other$realizadoMes3 != null : !this$realizadoMes3.equals(other$realizadoMes3)) return false;
        final java.lang.Object this$realizadoMes4 = this.getRealizadoMes4();
        final java.lang.Object other$realizadoMes4 = other.getRealizadoMes4();
        if (this$realizadoMes4 == null ? other$realizadoMes4 != null : !this$realizadoMes4.equals(other$realizadoMes4)) return false;
        final java.lang.Object this$realizadoMes5 = this.getRealizadoMes5();
        final java.lang.Object other$realizadoMes5 = other.getRealizadoMes5();
        if (this$realizadoMes5 == null ? other$realizadoMes5 != null : !this$realizadoMes5.equals(other$realizadoMes5)) return false;
        final java.lang.Object this$realizadoMes6 = this.getRealizadoMes6();
        final java.lang.Object other$realizadoMes6 = other.getRealizadoMes6();
        if (this$realizadoMes6 == null ? other$realizadoMes6 != null : !this$realizadoMes6.equals(other$realizadoMes6)) return false;
        final java.lang.Object this$realizadoMes7 = this.getRealizadoMes7();
        final java.lang.Object other$realizadoMes7 = other.getRealizadoMes7();
        if (this$realizadoMes7 == null ? other$realizadoMes7 != null : !this$realizadoMes7.equals(other$realizadoMes7)) return false;
        final java.lang.Object this$realizadoMes8 = this.getRealizadoMes8();
        final java.lang.Object other$realizadoMes8 = other.getRealizadoMes8();
        if (this$realizadoMes8 == null ? other$realizadoMes8 != null : !this$realizadoMes8.equals(other$realizadoMes8)) return false;
        final java.lang.Object this$realizadoMes9 = this.getRealizadoMes9();
        final java.lang.Object other$realizadoMes9 = other.getRealizadoMes9();
        if (this$realizadoMes9 == null ? other$realizadoMes9 != null : !this$realizadoMes9.equals(other$realizadoMes9)) return false;
        final java.lang.Object this$realizadoMes10 = this.getRealizadoMes10();
        final java.lang.Object other$realizadoMes10 = other.getRealizadoMes10();
        if (this$realizadoMes10 == null ? other$realizadoMes10 != null : !this$realizadoMes10.equals(other$realizadoMes10)) return false;
        final java.lang.Object this$realizadoMes11 = this.getRealizadoMes11();
        final java.lang.Object other$realizadoMes11 = other.getRealizadoMes11();
        if (this$realizadoMes11 == null ? other$realizadoMes11 != null : !this$realizadoMes11.equals(other$realizadoMes11)) return false;
        final java.lang.Object this$realizadoMes12 = this.getRealizadoMes12();
        final java.lang.Object other$realizadoMes12 = other.getRealizadoMes12();
        if (this$realizadoMes12 == null ? other$realizadoMes12 != null : !this$realizadoMes12.equals(other$realizadoMes12)) return false;
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
        return other instanceof ItemOrcamento;
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
        final java.lang.Object $orcamento = this.getOrcamento();
        result = result * PRIME + ($orcamento == null ? 43 : $orcamento.hashCode());
        final java.lang.Object $codigoItem = this.getCodigoItem();
        result = result * PRIME + ($codigoItem == null ? 43 : $codigoItem.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoItem = this.getTipoItem();
        result = result * PRIME + ($tipoItem == null ? 43 : $tipoItem.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $centroCusto = this.getCentroCusto();
        result = result * PRIME + ($centroCusto == null ? 43 : $centroCusto.hashCode());
        final java.lang.Object $contaContabil = this.getContaContabil();
        result = result * PRIME + ($contaContabil == null ? 43 : $contaContabil.hashCode());
        final java.lang.Object $valorOrcado = this.getValorOrcado();
        result = result * PRIME + ($valorOrcado == null ? 43 : $valorOrcado.hashCode());
        final java.lang.Object $valorRealizado = this.getValorRealizado();
        result = result * PRIME + ($valorRealizado == null ? 43 : $valorRealizado.hashCode());
        final java.lang.Object $variacaoValor = this.getVariacaoValor();
        result = result * PRIME + ($variacaoValor == null ? 43 : $variacaoValor.hashCode());
        final java.lang.Object $percentualVariacao = this.getPercentualVariacao();
        result = result * PRIME + ($percentualVariacao == null ? 43 : $percentualVariacao.hashCode());
        final java.lang.Object $metaMensal = this.getMetaMensal();
        result = result * PRIME + ($metaMensal == null ? 43 : $metaMensal.hashCode());
        final java.lang.Object $realizadoMes1 = this.getRealizadoMes1();
        result = result * PRIME + ($realizadoMes1 == null ? 43 : $realizadoMes1.hashCode());
        final java.lang.Object $realizadoMes2 = this.getRealizadoMes2();
        result = result * PRIME + ($realizadoMes2 == null ? 43 : $realizadoMes2.hashCode());
        final java.lang.Object $realizadoMes3 = this.getRealizadoMes3();
        result = result * PRIME + ($realizadoMes3 == null ? 43 : $realizadoMes3.hashCode());
        final java.lang.Object $realizadoMes4 = this.getRealizadoMes4();
        result = result * PRIME + ($realizadoMes4 == null ? 43 : $realizadoMes4.hashCode());
        final java.lang.Object $realizadoMes5 = this.getRealizadoMes5();
        result = result * PRIME + ($realizadoMes5 == null ? 43 : $realizadoMes5.hashCode());
        final java.lang.Object $realizadoMes6 = this.getRealizadoMes6();
        result = result * PRIME + ($realizadoMes6 == null ? 43 : $realizadoMes6.hashCode());
        final java.lang.Object $realizadoMes7 = this.getRealizadoMes7();
        result = result * PRIME + ($realizadoMes7 == null ? 43 : $realizadoMes7.hashCode());
        final java.lang.Object $realizadoMes8 = this.getRealizadoMes8();
        result = result * PRIME + ($realizadoMes8 == null ? 43 : $realizadoMes8.hashCode());
        final java.lang.Object $realizadoMes9 = this.getRealizadoMes9();
        result = result * PRIME + ($realizadoMes9 == null ? 43 : $realizadoMes9.hashCode());
        final java.lang.Object $realizadoMes10 = this.getRealizadoMes10();
        result = result * PRIME + ($realizadoMes10 == null ? 43 : $realizadoMes10.hashCode());
        final java.lang.Object $realizadoMes11 = this.getRealizadoMes11();
        result = result * PRIME + ($realizadoMes11 == null ? 43 : $realizadoMes11.hashCode());
        final java.lang.Object $realizadoMes12 = this.getRealizadoMes12();
        result = result * PRIME + ($realizadoMes12 == null ? 43 : $realizadoMes12.hashCode());
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
        return "ItemOrcamento(id=" + this.getId() + ", orcamento=" + this.getOrcamento() + ", codigoItem=" + this.getCodigoItem() + ", descricao=" + this.getDescricao() + ", tipoItem=" + this.getTipoItem() + ", categoria=" + this.getCategoria() + ", centroCusto=" + this.getCentroCusto() + ", contaContabil=" + this.getContaContabil() + ", valorOrcado=" + this.getValorOrcado() + ", valorRealizado=" + this.getValorRealizado() + ", variacaoValor=" + this.getVariacaoValor() + ", percentualVariacao=" + this.getPercentualVariacao() + ", metaMensal=" + this.getMetaMensal() + ", realizadoMes1=" + this.getRealizadoMes1() + ", realizadoMes2=" + this.getRealizadoMes2() + ", realizadoMes3=" + this.getRealizadoMes3() + ", realizadoMes4=" + this.getRealizadoMes4() + ", realizadoMes5=" + this.getRealizadoMes5() + ", realizadoMes6=" + this.getRealizadoMes6() + ", realizadoMes7=" + this.getRealizadoMes7() + ", realizadoMes8=" + this.getRealizadoMes8() + ", realizadoMes9=" + this.getRealizadoMes9() + ", realizadoMes10=" + this.getRealizadoMes10() + ", realizadoMes11=" + this.getRealizadoMes11() + ", realizadoMes12=" + this.getRealizadoMes12() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ItemOrcamento() {
    }

    @java.lang.SuppressWarnings("all")
    public ItemOrcamento(final Long id, final Orcamento orcamento, final String codigoItem, final String descricao, final TipoItem tipoItem, final CategoriaItem categoria, final String centroCusto, final String contaContabil, final BigDecimal valorOrcado, final BigDecimal valorRealizado, final BigDecimal variacaoValor, final BigDecimal percentualVariacao, final BigDecimal metaMensal, final BigDecimal realizadoMes1, final BigDecimal realizadoMes2, final BigDecimal realizadoMes3, final BigDecimal realizadoMes4, final BigDecimal realizadoMes5, final BigDecimal realizadoMes6, final BigDecimal realizadoMes7, final BigDecimal realizadoMes8, final BigDecimal realizadoMes9, final BigDecimal realizadoMes10, final BigDecimal realizadoMes11, final BigDecimal realizadoMes12, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.orcamento = orcamento;
        this.codigoItem = codigoItem;
        this.descricao = descricao;
        this.tipoItem = tipoItem;
        this.categoria = categoria;
        this.centroCusto = centroCusto;
        this.contaContabil = contaContabil;
        this.valorOrcado = valorOrcado;
        this.valorRealizado = valorRealizado;
        this.variacaoValor = variacaoValor;
        this.percentualVariacao = percentualVariacao;
        this.metaMensal = metaMensal;
        this.realizadoMes1 = realizadoMes1;
        this.realizadoMes2 = realizadoMes2;
        this.realizadoMes3 = realizadoMes3;
        this.realizadoMes4 = realizadoMes4;
        this.realizadoMes5 = realizadoMes5;
        this.realizadoMes6 = realizadoMes6;
        this.realizadoMes7 = realizadoMes7;
        this.realizadoMes8 = realizadoMes8;
        this.realizadoMes9 = realizadoMes9;
        this.realizadoMes10 = realizadoMes10;
        this.realizadoMes11 = realizadoMes11;
        this.realizadoMes12 = realizadoMes12;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
