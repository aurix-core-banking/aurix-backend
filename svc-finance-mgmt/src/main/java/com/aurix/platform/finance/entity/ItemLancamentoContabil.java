package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um item de lançamento contábil
 * 
 * Cada lançamento contábil possui itens que seguem o princípio
 * da partida dobrada (débito = crédito).
 */
@Entity
@Table(name = "itens_lancamento_contabil", schema = "aurix")
public class ItemLancamentoContabil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_id", nullable = false)
    private LancamentoContabil lancamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private PlanoContas conta;
    @Column(name = "valor", precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    @Column(name = "natureza", nullable = false)
    private NaturezaItem natureza;
    @Column(name = "historico", length = 500)
    private String historico;
    @Column(name = "centro_custo", length = 50)
    private String centroCusto;
    @Column(name = "projeto", length = 100)
    private String projeto;
    @Column(name = "cliente_id")
    private Long clienteId;
    @Column(name = "conta_cliente_id")
    private Long contaClienteId;
    @Column(name = "documento", length = 100)
    private String documento;
    @Column(name = "referencia", length = 100)
    private String referencia;
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
     * Natureza do item (Débito ou Crédito)
     */
    public enum NaturezaItem {
        DEBITO,  // Item de débito
        CREDITO // Item de crédito
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class ItemLancamentoContabilBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private LancamentoContabil lancamento;
        @java.lang.SuppressWarnings("all")
        private PlanoContas conta;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valor;
        @java.lang.SuppressWarnings("all")
        private NaturezaItem natureza;
        @java.lang.SuppressWarnings("all")
        private String historico;
        @java.lang.SuppressWarnings("all")
        private String centroCusto;
        @java.lang.SuppressWarnings("all")
        private String projeto;
        @java.lang.SuppressWarnings("all")
        private Long clienteId;
        @java.lang.SuppressWarnings("all")
        private Long contaClienteId;
        @java.lang.SuppressWarnings("all")
        private String documento;
        @java.lang.SuppressWarnings("all")
        private String referencia;
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
        ItemLancamentoContabilBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder lancamento(final LancamentoContabil lancamento) {
            this.lancamento = lancamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder conta(final PlanoContas conta) {
            this.conta = conta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder valor(final BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder natureza(final NaturezaItem natureza) {
            this.natureza = natureza;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder historico(final String historico) {
            this.historico = historico;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder centroCusto(final String centroCusto) {
            this.centroCusto = centroCusto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder projeto(final String projeto) {
            this.projeto = projeto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder clienteId(final Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder contaClienteId(final Long contaClienteId) {
            this.contaClienteId = contaClienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder documento(final String documento) {
            this.documento = documento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder referencia(final String referencia) {
            this.referencia = referencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil.ItemLancamentoContabilBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ItemLancamentoContabil build() {
            return new ItemLancamentoContabil(this.id, this.lancamento, this.conta, this.valor, this.natureza, this.historico, this.centroCusto, this.projeto, this.clienteId, this.contaClienteId, this.documento, this.referencia, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ItemLancamentoContabil.ItemLancamentoContabilBuilder(id=" + this.id + ", lancamento=" + this.lancamento + ", conta=" + this.conta + ", valor=" + this.valor + ", natureza=" + this.natureza + ", historico=" + this.historico + ", centroCusto=" + this.centroCusto + ", projeto=" + this.projeto + ", clienteId=" + this.clienteId + ", contaClienteId=" + this.contaClienteId + ", documento=" + this.documento + ", referencia=" + this.referencia + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ItemLancamentoContabil.ItemLancamentoContabilBuilder builder() {
        return new ItemLancamentoContabil.ItemLancamentoContabilBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public LancamentoContabil getLancamento() {
        return this.lancamento;
    }

    @java.lang.SuppressWarnings("all")
    public PlanoContas getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public NaturezaItem getNatureza() {
        return this.natureza;
    }

    @java.lang.SuppressWarnings("all")
    public String getHistorico() {
        return this.historico;
    }

    @java.lang.SuppressWarnings("all")
    public String getCentroCusto() {
        return this.centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public String getProjeto() {
        return this.projeto;
    }

    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaClienteId() {
        return this.contaClienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDocumento() {
        return this.documento;
    }

    @java.lang.SuppressWarnings("all")
    public String getReferencia() {
        return this.referencia;
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
    public void setLancamento(final LancamentoContabil lancamento) {
        this.lancamento = lancamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final PlanoContas conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setNatureza(final NaturezaItem natureza) {
        this.natureza = natureza;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistorico(final String historico) {
        this.historico = historico;
    }

    @java.lang.SuppressWarnings("all")
    public void setCentroCusto(final String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @java.lang.SuppressWarnings("all")
    public void setProjeto(final String projeto) {
        this.projeto = projeto;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaClienteId(final Long contaClienteId) {
        this.contaClienteId = contaClienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumento(final String documento) {
        this.documento = documento;
    }

    @java.lang.SuppressWarnings("all")
    public void setReferencia(final String referencia) {
        this.referencia = referencia;
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
        if (!(o instanceof ItemLancamentoContabil)) return false;
        final ItemLancamentoContabil other = (ItemLancamentoContabil) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$contaClienteId = this.getContaClienteId();
        final java.lang.Object other$contaClienteId = other.getContaClienteId();
        if (this$contaClienteId == null ? other$contaClienteId != null : !this$contaClienteId.equals(other$contaClienteId)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$lancamento = this.getLancamento();
        final java.lang.Object other$lancamento = other.getLancamento();
        if (this$lancamento == null ? other$lancamento != null : !this$lancamento.equals(other$lancamento)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$natureza = this.getNatureza();
        final java.lang.Object other$natureza = other.getNatureza();
        if (this$natureza == null ? other$natureza != null : !this$natureza.equals(other$natureza)) return false;
        final java.lang.Object this$historico = this.getHistorico();
        final java.lang.Object other$historico = other.getHistorico();
        if (this$historico == null ? other$historico != null : !this$historico.equals(other$historico)) return false;
        final java.lang.Object this$centroCusto = this.getCentroCusto();
        final java.lang.Object other$centroCusto = other.getCentroCusto();
        if (this$centroCusto == null ? other$centroCusto != null : !this$centroCusto.equals(other$centroCusto)) return false;
        final java.lang.Object this$projeto = this.getProjeto();
        final java.lang.Object other$projeto = other.getProjeto();
        if (this$projeto == null ? other$projeto != null : !this$projeto.equals(other$projeto)) return false;
        final java.lang.Object this$documento = this.getDocumento();
        final java.lang.Object other$documento = other.getDocumento();
        if (this$documento == null ? other$documento != null : !this$documento.equals(other$documento)) return false;
        final java.lang.Object this$referencia = this.getReferencia();
        final java.lang.Object other$referencia = other.getReferencia();
        if (this$referencia == null ? other$referencia != null : !this$referencia.equals(other$referencia)) return false;
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
        return other instanceof ItemLancamentoContabil;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $contaClienteId = this.getContaClienteId();
        result = result * PRIME + ($contaClienteId == null ? 43 : $contaClienteId.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $lancamento = this.getLancamento();
        result = result * PRIME + ($lancamento == null ? 43 : $lancamento.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $natureza = this.getNatureza();
        result = result * PRIME + ($natureza == null ? 43 : $natureza.hashCode());
        final java.lang.Object $historico = this.getHistorico();
        result = result * PRIME + ($historico == null ? 43 : $historico.hashCode());
        final java.lang.Object $centroCusto = this.getCentroCusto();
        result = result * PRIME + ($centroCusto == null ? 43 : $centroCusto.hashCode());
        final java.lang.Object $projeto = this.getProjeto();
        result = result * PRIME + ($projeto == null ? 43 : $projeto.hashCode());
        final java.lang.Object $documento = this.getDocumento();
        result = result * PRIME + ($documento == null ? 43 : $documento.hashCode());
        final java.lang.Object $referencia = this.getReferencia();
        result = result * PRIME + ($referencia == null ? 43 : $referencia.hashCode());
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
        return "ItemLancamentoContabil(id=" + this.getId() + ", lancamento=" + this.getLancamento() + ", conta=" + this.getConta() + ", valor=" + this.getValor() + ", natureza=" + this.getNatureza() + ", historico=" + this.getHistorico() + ", centroCusto=" + this.getCentroCusto() + ", projeto=" + this.getProjeto() + ", clienteId=" + this.getClienteId() + ", contaClienteId=" + this.getContaClienteId() + ", documento=" + this.getDocumento() + ", referencia=" + this.getReferencia() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ItemLancamentoContabil() {
    }

    @java.lang.SuppressWarnings("all")
    public ItemLancamentoContabil(final Long id, final LancamentoContabil lancamento, final PlanoContas conta, final BigDecimal valor, final NaturezaItem natureza, final String historico, final String centroCusto, final String projeto, final Long clienteId, final Long contaClienteId, final String documento, final String referencia, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.lancamento = lancamento;
        this.conta = conta;
        this.valor = valor;
        this.natureza = natureza;
        this.historico = historico;
        this.centroCusto = centroCusto;
        this.projeto = projeto;
        this.clienteId = clienteId;
        this.contaClienteId = contaClienteId;
        this.documento = documento;
        this.referencia = referencia;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
