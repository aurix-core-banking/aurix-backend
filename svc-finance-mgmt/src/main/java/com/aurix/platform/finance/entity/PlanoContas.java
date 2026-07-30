package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o Plano de Contas do banco
 * 
 * Define a estrutura contábil com todas as contas utilizadas
 * no razão geral, seguindo padrões COSIF e BACEN.
 */
@Entity
@Table(name = "plano_contas", schema = "aurix")
public class PlanoContas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoConta tipoConta;
    @Enumerated(EnumType.STRING)
    @Column(name = "natureza", nullable = false)
    private NaturezaConta natureza;
    @Column(name = "nivel", nullable = false)
    private Integer nivel;
    @Column(name = "conta_pai_id")
    private Long contaPaiId;
    @Column(name = "conta_raiz", nullable = false)
    private Boolean contaRaiz;
    @Column(name = "conta_analitica", nullable = false)
    private Boolean contaAnalitica;
    @Column(name = "conta_sintetica", nullable = false)
    private Boolean contaSintetica;
    @Column(name = "saldo_inicial", precision = 15, scale = 2)
    private BigDecimal saldoInicial;
    @Column(name = "saldo_atual", precision = 15, scale = 2)
    private BigDecimal saldoAtual;
    @Column(name = "moeda", length = 3, nullable = false)
    private String moeda;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConta status;
    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    @Column(name = "centro_custo", length = 50)
    private String centroCusto;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
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
     * Tipo de conta contábil
     */
    public enum TipoConta {
        ATIVO,  // Ativo
        PASSIVO,  // Passivo
        PATRIMONIO_LIQUIDO,  // Patrimônio Líquido
        RECEITA,  // Receita
        DESPESA,  // Despesa
        CUSTO,  // Custo
        RESULTADO,  // Resultado
        COMPENSACAO // Compensação
        ;
    }


    /**
     * Natureza da conta (Débito ou Crédito)
     */
    public enum NaturezaConta {
        DEBITO,  // Conta de débito
        CREDITO // Conta de crédito
        ;
    }


    /**
     * Status da conta
     */
    public enum StatusConta {
        ATIVA,  // Conta ativa
        INATIVA,  // Conta inativa
        BLOQUEADA,  // Conta bloqueada
        FECHADA // Conta fechada
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class PlanoContasBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigo;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoConta tipoConta;
        @java.lang.SuppressWarnings("all")
        private NaturezaConta natureza;
        @java.lang.SuppressWarnings("all")
        private Integer nivel;
        @java.lang.SuppressWarnings("all")
        private Long contaPaiId;
        @java.lang.SuppressWarnings("all")
        private Boolean contaRaiz;
        @java.lang.SuppressWarnings("all")
        private Boolean contaAnalitica;
        @java.lang.SuppressWarnings("all")
        private Boolean contaSintetica;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoInicial;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoAtual;
        @java.lang.SuppressWarnings("all")
        private String moeda;
        @java.lang.SuppressWarnings("all")
        private StatusConta status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAbertura;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataFechamento;
        @java.lang.SuppressWarnings("all")
        private String centroCusto;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
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
        PlanoContasBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder codigo(final String codigo) {
            this.codigo = codigo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder tipoConta(final TipoConta tipoConta) {
            this.tipoConta = tipoConta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder natureza(final NaturezaConta natureza) {
            this.natureza = natureza;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder nivel(final Integer nivel) {
            this.nivel = nivel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder contaPaiId(final Long contaPaiId) {
            this.contaPaiId = contaPaiId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder contaRaiz(final Boolean contaRaiz) {
            this.contaRaiz = contaRaiz;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder contaAnalitica(final Boolean contaAnalitica) {
            this.contaAnalitica = contaAnalitica;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder contaSintetica(final Boolean contaSintetica) {
            this.contaSintetica = contaSintetica;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder saldoInicial(final BigDecimal saldoInicial) {
            this.saldoInicial = saldoInicial;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder saldoAtual(final BigDecimal saldoAtual) {
            this.saldoAtual = saldoAtual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder moeda(final String moeda) {
            this.moeda = moeda;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder status(final StatusConta status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder dataAbertura(final LocalDateTime dataAbertura) {
            this.dataAbertura = dataAbertura;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder dataFechamento(final LocalDateTime dataFechamento) {
            this.dataFechamento = dataFechamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder centroCusto(final String centroCusto) {
            this.centroCusto = centroCusto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanoContas.PlanoContasBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PlanoContas build() {
            return new PlanoContas(this.id, this.codigo, this.nome, this.descricao, this.tipoConta, this.natureza, this.nivel, this.contaPaiId, this.contaRaiz, this.contaAnalitica, this.contaSintetica, this.saldoInicial, this.saldoAtual, this.moeda, this.status, this.dataAbertura, this.dataFechamento, this.centroCusto, this.responsavel, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "PlanoContas.PlanoContasBuilder(id=" + this.id + ", codigo=" + this.codigo + ", nome=" + this.nome + ", descricao=" + this.descricao + ", tipoConta=" + this.tipoConta + ", natureza=" + this.natureza + ", nivel=" + this.nivel + ", contaPaiId=" + this.contaPaiId + ", contaRaiz=" + this.contaRaiz + ", contaAnalitica=" + this.contaAnalitica + ", contaSintetica=" + this.contaSintetica + ", saldoInicial=" + this.saldoInicial + ", saldoAtual=" + this.saldoAtual + ", moeda=" + this.moeda + ", status=" + this.status + ", dataAbertura=" + this.dataAbertura + ", dataFechamento=" + this.dataFechamento + ", centroCusto=" + this.centroCusto + ", responsavel=" + this.responsavel + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static PlanoContas.PlanoContasBuilder builder() {
        return new PlanoContas.PlanoContasBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigo() {
        return this.codigo;
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
    public TipoConta getTipoConta() {
        return this.tipoConta;
    }

    @java.lang.SuppressWarnings("all")
    public NaturezaConta getNatureza() {
        return this.natureza;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivel() {
        return this.nivel;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaPaiId() {
        return this.contaPaiId;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getContaRaiz() {
        return this.contaRaiz;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getContaAnalitica() {
        return this.contaAnalitica;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getContaSintetica() {
        return this.contaSintetica;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoInicial() {
        return this.saldoInicial;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoAtual() {
        return this.saldoAtual;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoeda() {
        return this.moeda;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConta getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAbertura() {
        return this.dataAbertura;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFechamento() {
        return this.dataFechamento;
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
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
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
    public void setTipoConta(final TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    @java.lang.SuppressWarnings("all")
    public void setNatureza(final NaturezaConta natureza) {
        this.natureza = natureza;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivel(final Integer nivel) {
        this.nivel = nivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaPaiId(final Long contaPaiId) {
        this.contaPaiId = contaPaiId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaRaiz(final Boolean contaRaiz) {
        this.contaRaiz = contaRaiz;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaAnalitica(final Boolean contaAnalitica) {
        this.contaAnalitica = contaAnalitica;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaSintetica(final Boolean contaSintetica) {
        this.contaSintetica = contaSintetica;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoInicial(final BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoAtual(final BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoeda(final String moeda) {
        this.moeda = moeda;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConta status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAbertura(final LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFechamento(final LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
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
        if (!(o instanceof PlanoContas)) return false;
        final PlanoContas other = (PlanoContas) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nivel = this.getNivel();
        final java.lang.Object other$nivel = other.getNivel();
        if (this$nivel == null ? other$nivel != null : !this$nivel.equals(other$nivel)) return false;
        final java.lang.Object this$contaPaiId = this.getContaPaiId();
        final java.lang.Object other$contaPaiId = other.getContaPaiId();
        if (this$contaPaiId == null ? other$contaPaiId != null : !this$contaPaiId.equals(other$contaPaiId)) return false;
        final java.lang.Object this$contaRaiz = this.getContaRaiz();
        final java.lang.Object other$contaRaiz = other.getContaRaiz();
        if (this$contaRaiz == null ? other$contaRaiz != null : !this$contaRaiz.equals(other$contaRaiz)) return false;
        final java.lang.Object this$contaAnalitica = this.getContaAnalitica();
        final java.lang.Object other$contaAnalitica = other.getContaAnalitica();
        if (this$contaAnalitica == null ? other$contaAnalitica != null : !this$contaAnalitica.equals(other$contaAnalitica)) return false;
        final java.lang.Object this$contaSintetica = this.getContaSintetica();
        final java.lang.Object other$contaSintetica = other.getContaSintetica();
        if (this$contaSintetica == null ? other$contaSintetica != null : !this$contaSintetica.equals(other$contaSintetica)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigo = this.getCodigo();
        final java.lang.Object other$codigo = other.getCodigo();
        if (this$codigo == null ? other$codigo != null : !this$codigo.equals(other$codigo)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoConta = this.getTipoConta();
        final java.lang.Object other$tipoConta = other.getTipoConta();
        if (this$tipoConta == null ? other$tipoConta != null : !this$tipoConta.equals(other$tipoConta)) return false;
        final java.lang.Object this$natureza = this.getNatureza();
        final java.lang.Object other$natureza = other.getNatureza();
        if (this$natureza == null ? other$natureza != null : !this$natureza.equals(other$natureza)) return false;
        final java.lang.Object this$saldoInicial = this.getSaldoInicial();
        final java.lang.Object other$saldoInicial = other.getSaldoInicial();
        if (this$saldoInicial == null ? other$saldoInicial != null : !this$saldoInicial.equals(other$saldoInicial)) return false;
        final java.lang.Object this$saldoAtual = this.getSaldoAtual();
        final java.lang.Object other$saldoAtual = other.getSaldoAtual();
        if (this$saldoAtual == null ? other$saldoAtual != null : !this$saldoAtual.equals(other$saldoAtual)) return false;
        final java.lang.Object this$moeda = this.getMoeda();
        final java.lang.Object other$moeda = other.getMoeda();
        if (this$moeda == null ? other$moeda != null : !this$moeda.equals(other$moeda)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataAbertura = this.getDataAbertura();
        final java.lang.Object other$dataAbertura = other.getDataAbertura();
        if (this$dataAbertura == null ? other$dataAbertura != null : !this$dataAbertura.equals(other$dataAbertura)) return false;
        final java.lang.Object this$dataFechamento = this.getDataFechamento();
        final java.lang.Object other$dataFechamento = other.getDataFechamento();
        if (this$dataFechamento == null ? other$dataFechamento != null : !this$dataFechamento.equals(other$dataFechamento)) return false;
        final java.lang.Object this$centroCusto = this.getCentroCusto();
        final java.lang.Object other$centroCusto = other.getCentroCusto();
        if (this$centroCusto == null ? other$centroCusto != null : !this$centroCusto.equals(other$centroCusto)) return false;
        final java.lang.Object this$responsavel = this.getResponsavel();
        final java.lang.Object other$responsavel = other.getResponsavel();
        if (this$responsavel == null ? other$responsavel != null : !this$responsavel.equals(other$responsavel)) return false;
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
        return other instanceof PlanoContas;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $nivel = this.getNivel();
        result = result * PRIME + ($nivel == null ? 43 : $nivel.hashCode());
        final java.lang.Object $contaPaiId = this.getContaPaiId();
        result = result * PRIME + ($contaPaiId == null ? 43 : $contaPaiId.hashCode());
        final java.lang.Object $contaRaiz = this.getContaRaiz();
        result = result * PRIME + ($contaRaiz == null ? 43 : $contaRaiz.hashCode());
        final java.lang.Object $contaAnalitica = this.getContaAnalitica();
        result = result * PRIME + ($contaAnalitica == null ? 43 : $contaAnalitica.hashCode());
        final java.lang.Object $contaSintetica = this.getContaSintetica();
        result = result * PRIME + ($contaSintetica == null ? 43 : $contaSintetica.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigo = this.getCodigo();
        result = result * PRIME + ($codigo == null ? 43 : $codigo.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoConta = this.getTipoConta();
        result = result * PRIME + ($tipoConta == null ? 43 : $tipoConta.hashCode());
        final java.lang.Object $natureza = this.getNatureza();
        result = result * PRIME + ($natureza == null ? 43 : $natureza.hashCode());
        final java.lang.Object $saldoInicial = this.getSaldoInicial();
        result = result * PRIME + ($saldoInicial == null ? 43 : $saldoInicial.hashCode());
        final java.lang.Object $saldoAtual = this.getSaldoAtual();
        result = result * PRIME + ($saldoAtual == null ? 43 : $saldoAtual.hashCode());
        final java.lang.Object $moeda = this.getMoeda();
        result = result * PRIME + ($moeda == null ? 43 : $moeda.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataAbertura = this.getDataAbertura();
        result = result * PRIME + ($dataAbertura == null ? 43 : $dataAbertura.hashCode());
        final java.lang.Object $dataFechamento = this.getDataFechamento();
        result = result * PRIME + ($dataFechamento == null ? 43 : $dataFechamento.hashCode());
        final java.lang.Object $centroCusto = this.getCentroCusto();
        result = result * PRIME + ($centroCusto == null ? 43 : $centroCusto.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
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
        return "PlanoContas(id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoConta=" + this.getTipoConta() + ", natureza=" + this.getNatureza() + ", nivel=" + this.getNivel() + ", contaPaiId=" + this.getContaPaiId() + ", contaRaiz=" + this.getContaRaiz() + ", contaAnalitica=" + this.getContaAnalitica() + ", contaSintetica=" + this.getContaSintetica() + ", saldoInicial=" + this.getSaldoInicial() + ", saldoAtual=" + this.getSaldoAtual() + ", moeda=" + this.getMoeda() + ", status=" + this.getStatus() + ", dataAbertura=" + this.getDataAbertura() + ", dataFechamento=" + this.getDataFechamento() + ", centroCusto=" + this.getCentroCusto() + ", responsavel=" + this.getResponsavel() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PlanoContas() {
    }

    @java.lang.SuppressWarnings("all")
    public PlanoContas(final Long id, final String codigo, final String nome, final String descricao, final TipoConta tipoConta, final NaturezaConta natureza, final Integer nivel, final Long contaPaiId, final Boolean contaRaiz, final Boolean contaAnalitica, final Boolean contaSintetica, final BigDecimal saldoInicial, final BigDecimal saldoAtual, final String moeda, final StatusConta status, final LocalDateTime dataAbertura, final LocalDateTime dataFechamento, final String centroCusto, final String responsavel, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoConta = tipoConta;
        this.natureza = natureza;
        this.nivel = nivel;
        this.contaPaiId = contaPaiId;
        this.contaRaiz = contaRaiz;
        this.contaAnalitica = contaAnalitica;
        this.contaSintetica = contaSintetica;
        this.saldoInicial = saldoInicial;
        this.saldoAtual = saldoAtual;
        this.moeda = moeda;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.centroCusto = centroCusto;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
