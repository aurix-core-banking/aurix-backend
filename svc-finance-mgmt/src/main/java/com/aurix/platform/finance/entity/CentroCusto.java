package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um centro de custo
 * 
 * Gerencia a estrutura hierárquica de centros de custo para rateio e análise
 */
@Entity
@Table(name = "centros_custo", schema = "aurix")
public class CentroCusto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_centro", unique = true, nullable = false, length = 50)
    private String codigoCentro;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_pai_id")
    private CentroCusto centroPai;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_centro", nullable = false)
    private TipoCentro tipoCentro;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCentro status;
    @Column(name = "nivel_hierarquia", nullable = false)
    private Integer nivelHierarquia;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "orcamento_anual", precision = 15, scale = 2)
    private BigDecimal orcamentoAnual;
    @Column(name = "orcamento_mensal", precision = 15, scale = 2)
    private BigDecimal orcamentoMensal;
    @Column(name = "realizado_ano", precision = 15, scale = 2)
    private BigDecimal realizadoAno;
    @Column(name = "realizado_mes", precision = 15, scale = 2)
    private BigDecimal realizadoMes;
    @Column(name = "variacao_ano", precision = 15, scale = 2)
    private BigDecimal variacaoAno;
    @Column(name = "variacao_mes", precision = 15, scale = 2)
    private BigDecimal variacaoMes;
    @Column(name = "percentual_variacao_ano", precision = 8, scale = 4)
    private BigDecimal percentualVariacaoAno;
    @Column(name = "percentual_variacao_mes", precision = 8, scale = 4)
    private BigDecimal percentualVariacaoMes;
    @Column(name = "base_rateio", length = 100)
    private String baseRateio;
    @Column(name = "percentual_rateio", precision = 8, scale = 6)
    private BigDecimal percentualRateio;
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
     * Tipo de centro de custo
     */
    public enum TipoCentro {
        DIRETORIA,  // Diretoria
        DEPARTAMENTO,  // Departamento
        SETOR,  // Setor
        UNIDADE,  // Unidade
        PROJETO,  // Projeto
        ATIVIDADE,  // Atividade
        PRODUTO,  // Produto
        CANAL,  // Canal
        SEGMENTO // Segmento
        ;
    }


    /**
     * Status do centro de custo
     */
    public enum StatusCentro {
        ATIVO,  // Ativo
        INATIVO,  // Inativo
        SUSPENSO,  // Suspenso
        FECHADO // Fechado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class CentroCustoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoCentro;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private CentroCusto centroPai;
        @java.lang.SuppressWarnings("all")
        private TipoCentro tipoCentro;
        @java.lang.SuppressWarnings("all")
        private StatusCentro status;
        @java.lang.SuppressWarnings("all")
        private Integer nivelHierarquia;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private BigDecimal orcamentoAnual;
        @java.lang.SuppressWarnings("all")
        private BigDecimal orcamentoMensal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoAno;
        @java.lang.SuppressWarnings("all")
        private BigDecimal realizadoMes;
        @java.lang.SuppressWarnings("all")
        private BigDecimal variacaoAno;
        @java.lang.SuppressWarnings("all")
        private BigDecimal variacaoMes;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualVariacaoAno;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualVariacaoMes;
        @java.lang.SuppressWarnings("all")
        private String baseRateio;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualRateio;
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
        CentroCustoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder codigoCentro(final String codigoCentro) {
            this.codigoCentro = codigoCentro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder centroPai(final CentroCusto centroPai) {
            this.centroPai = centroPai;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder tipoCentro(final TipoCentro tipoCentro) {
            this.tipoCentro = tipoCentro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder status(final StatusCentro status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder nivelHierarquia(final Integer nivelHierarquia) {
            this.nivelHierarquia = nivelHierarquia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder orcamentoAnual(final BigDecimal orcamentoAnual) {
            this.orcamentoAnual = orcamentoAnual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder orcamentoMensal(final BigDecimal orcamentoMensal) {
            this.orcamentoMensal = orcamentoMensal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder realizadoAno(final BigDecimal realizadoAno) {
            this.realizadoAno = realizadoAno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder realizadoMes(final BigDecimal realizadoMes) {
            this.realizadoMes = realizadoMes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder variacaoAno(final BigDecimal variacaoAno) {
            this.variacaoAno = variacaoAno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder variacaoMes(final BigDecimal variacaoMes) {
            this.variacaoMes = variacaoMes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder percentualVariacaoAno(final BigDecimal percentualVariacaoAno) {
            this.percentualVariacaoAno = percentualVariacaoAno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder percentualVariacaoMes(final BigDecimal percentualVariacaoMes) {
            this.percentualVariacaoMes = percentualVariacaoMes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder baseRateio(final String baseRateio) {
            this.baseRateio = baseRateio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder percentualRateio(final BigDecimal percentualRateio) {
            this.percentualRateio = percentualRateio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public CentroCusto.CentroCustoBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public CentroCusto build() {
            return new CentroCusto(this.id, this.codigoCentro, this.nome, this.descricao, this.centroPai, this.tipoCentro, this.status, this.nivelHierarquia, this.responsavel, this.orcamentoAnual, this.orcamentoMensal, this.realizadoAno, this.realizadoMes, this.variacaoAno, this.variacaoMes, this.percentualVariacaoAno, this.percentualVariacaoMes, this.baseRateio, this.percentualRateio, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "CentroCusto.CentroCustoBuilder(id=" + this.id + ", codigoCentro=" + this.codigoCentro + ", nome=" + this.nome + ", descricao=" + this.descricao + ", centroPai=" + this.centroPai + ", tipoCentro=" + this.tipoCentro + ", status=" + this.status + ", nivelHierarquia=" + this.nivelHierarquia + ", responsavel=" + this.responsavel + ", orcamentoAnual=" + this.orcamentoAnual + ", orcamentoMensal=" + this.orcamentoMensal + ", realizadoAno=" + this.realizadoAno + ", realizadoMes=" + this.realizadoMes + ", variacaoAno=" + this.variacaoAno + ", variacaoMes=" + this.variacaoMes + ", percentualVariacaoAno=" + this.percentualVariacaoAno + ", percentualVariacaoMes=" + this.percentualVariacaoMes + ", baseRateio=" + this.baseRateio + ", percentualRateio=" + this.percentualRateio + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static CentroCusto.CentroCustoBuilder builder() {
        return new CentroCusto.CentroCustoBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoCentro() {
        return this.codigoCentro;
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
    public CentroCusto getCentroPai() {
        return this.centroPai;
    }

    @java.lang.SuppressWarnings("all")
    public TipoCentro getTipoCentro() {
        return this.tipoCentro;
    }

    @java.lang.SuppressWarnings("all")
    public StatusCentro getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivelHierarquia() {
        return this.nivelHierarquia;
    }

    @java.lang.SuppressWarnings("all")
    public String getResponsavel() {
        return this.responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getOrcamentoAnual() {
        return this.orcamentoAnual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getOrcamentoMensal() {
        return this.orcamentoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoAno() {
        return this.realizadoAno;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRealizadoMes() {
        return this.realizadoMes;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getVariacaoAno() {
        return this.variacaoAno;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getVariacaoMes() {
        return this.variacaoMes;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualVariacaoAno() {
        return this.percentualVariacaoAno;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualVariacaoMes() {
        return this.percentualVariacaoMes;
    }

    @java.lang.SuppressWarnings("all")
    public String getBaseRateio() {
        return this.baseRateio;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualRateio() {
        return this.percentualRateio;
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
    public void setCodigoCentro(final String codigoCentro) {
        this.codigoCentro = codigoCentro;
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
    public void setCentroPai(final CentroCusto centroPai) {
        this.centroPai = centroPai;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoCentro(final TipoCentro tipoCentro) {
        this.tipoCentro = tipoCentro;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusCentro status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelHierarquia(final Integer nivelHierarquia) {
        this.nivelHierarquia = nivelHierarquia;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponsavel(final String responsavel) {
        this.responsavel = responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setOrcamentoAnual(final BigDecimal orcamentoAnual) {
        this.orcamentoAnual = orcamentoAnual;
    }

    @java.lang.SuppressWarnings("all")
    public void setOrcamentoMensal(final BigDecimal orcamentoMensal) {
        this.orcamentoMensal = orcamentoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoAno(final BigDecimal realizadoAno) {
        this.realizadoAno = realizadoAno;
    }

    @java.lang.SuppressWarnings("all")
    public void setRealizadoMes(final BigDecimal realizadoMes) {
        this.realizadoMes = realizadoMes;
    }

    @java.lang.SuppressWarnings("all")
    public void setVariacaoAno(final BigDecimal variacaoAno) {
        this.variacaoAno = variacaoAno;
    }

    @java.lang.SuppressWarnings("all")
    public void setVariacaoMes(final BigDecimal variacaoMes) {
        this.variacaoMes = variacaoMes;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualVariacaoAno(final BigDecimal percentualVariacaoAno) {
        this.percentualVariacaoAno = percentualVariacaoAno;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualVariacaoMes(final BigDecimal percentualVariacaoMes) {
        this.percentualVariacaoMes = percentualVariacaoMes;
    }

    @java.lang.SuppressWarnings("all")
    public void setBaseRateio(final String baseRateio) {
        this.baseRateio = baseRateio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualRateio(final BigDecimal percentualRateio) {
        this.percentualRateio = percentualRateio;
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
        if (!(o instanceof CentroCusto)) return false;
        final CentroCusto other = (CentroCusto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nivelHierarquia = this.getNivelHierarquia();
        final java.lang.Object other$nivelHierarquia = other.getNivelHierarquia();
        if (this$nivelHierarquia == null ? other$nivelHierarquia != null : !this$nivelHierarquia.equals(other$nivelHierarquia)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoCentro = this.getCodigoCentro();
        final java.lang.Object other$codigoCentro = other.getCodigoCentro();
        if (this$codigoCentro == null ? other$codigoCentro != null : !this$codigoCentro.equals(other$codigoCentro)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$centroPai = this.getCentroPai();
        final java.lang.Object other$centroPai = other.getCentroPai();
        if (this$centroPai == null ? other$centroPai != null : !this$centroPai.equals(other$centroPai)) return false;
        final java.lang.Object this$tipoCentro = this.getTipoCentro();
        final java.lang.Object other$tipoCentro = other.getTipoCentro();
        if (this$tipoCentro == null ? other$tipoCentro != null : !this$tipoCentro.equals(other$tipoCentro)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$responsavel = this.getResponsavel();
        final java.lang.Object other$responsavel = other.getResponsavel();
        if (this$responsavel == null ? other$responsavel != null : !this$responsavel.equals(other$responsavel)) return false;
        final java.lang.Object this$orcamentoAnual = this.getOrcamentoAnual();
        final java.lang.Object other$orcamentoAnual = other.getOrcamentoAnual();
        if (this$orcamentoAnual == null ? other$orcamentoAnual != null : !this$orcamentoAnual.equals(other$orcamentoAnual)) return false;
        final java.lang.Object this$orcamentoMensal = this.getOrcamentoMensal();
        final java.lang.Object other$orcamentoMensal = other.getOrcamentoMensal();
        if (this$orcamentoMensal == null ? other$orcamentoMensal != null : !this$orcamentoMensal.equals(other$orcamentoMensal)) return false;
        final java.lang.Object this$realizadoAno = this.getRealizadoAno();
        final java.lang.Object other$realizadoAno = other.getRealizadoAno();
        if (this$realizadoAno == null ? other$realizadoAno != null : !this$realizadoAno.equals(other$realizadoAno)) return false;
        final java.lang.Object this$realizadoMes = this.getRealizadoMes();
        final java.lang.Object other$realizadoMes = other.getRealizadoMes();
        if (this$realizadoMes == null ? other$realizadoMes != null : !this$realizadoMes.equals(other$realizadoMes)) return false;
        final java.lang.Object this$variacaoAno = this.getVariacaoAno();
        final java.lang.Object other$variacaoAno = other.getVariacaoAno();
        if (this$variacaoAno == null ? other$variacaoAno != null : !this$variacaoAno.equals(other$variacaoAno)) return false;
        final java.lang.Object this$variacaoMes = this.getVariacaoMes();
        final java.lang.Object other$variacaoMes = other.getVariacaoMes();
        if (this$variacaoMes == null ? other$variacaoMes != null : !this$variacaoMes.equals(other$variacaoMes)) return false;
        final java.lang.Object this$percentualVariacaoAno = this.getPercentualVariacaoAno();
        final java.lang.Object other$percentualVariacaoAno = other.getPercentualVariacaoAno();
        if (this$percentualVariacaoAno == null ? other$percentualVariacaoAno != null : !this$percentualVariacaoAno.equals(other$percentualVariacaoAno)) return false;
        final java.lang.Object this$percentualVariacaoMes = this.getPercentualVariacaoMes();
        final java.lang.Object other$percentualVariacaoMes = other.getPercentualVariacaoMes();
        if (this$percentualVariacaoMes == null ? other$percentualVariacaoMes != null : !this$percentualVariacaoMes.equals(other$percentualVariacaoMes)) return false;
        final java.lang.Object this$baseRateio = this.getBaseRateio();
        final java.lang.Object other$baseRateio = other.getBaseRateio();
        if (this$baseRateio == null ? other$baseRateio != null : !this$baseRateio.equals(other$baseRateio)) return false;
        final java.lang.Object this$percentualRateio = this.getPercentualRateio();
        final java.lang.Object other$percentualRateio = other.getPercentualRateio();
        if (this$percentualRateio == null ? other$percentualRateio != null : !this$percentualRateio.equals(other$percentualRateio)) return false;
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
        return other instanceof CentroCusto;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $nivelHierarquia = this.getNivelHierarquia();
        result = result * PRIME + ($nivelHierarquia == null ? 43 : $nivelHierarquia.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoCentro = this.getCodigoCentro();
        result = result * PRIME + ($codigoCentro == null ? 43 : $codigoCentro.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $centroPai = this.getCentroPai();
        result = result * PRIME + ($centroPai == null ? 43 : $centroPai.hashCode());
        final java.lang.Object $tipoCentro = this.getTipoCentro();
        result = result * PRIME + ($tipoCentro == null ? 43 : $tipoCentro.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $orcamentoAnual = this.getOrcamentoAnual();
        result = result * PRIME + ($orcamentoAnual == null ? 43 : $orcamentoAnual.hashCode());
        final java.lang.Object $orcamentoMensal = this.getOrcamentoMensal();
        result = result * PRIME + ($orcamentoMensal == null ? 43 : $orcamentoMensal.hashCode());
        final java.lang.Object $realizadoAno = this.getRealizadoAno();
        result = result * PRIME + ($realizadoAno == null ? 43 : $realizadoAno.hashCode());
        final java.lang.Object $realizadoMes = this.getRealizadoMes();
        result = result * PRIME + ($realizadoMes == null ? 43 : $realizadoMes.hashCode());
        final java.lang.Object $variacaoAno = this.getVariacaoAno();
        result = result * PRIME + ($variacaoAno == null ? 43 : $variacaoAno.hashCode());
        final java.lang.Object $variacaoMes = this.getVariacaoMes();
        result = result * PRIME + ($variacaoMes == null ? 43 : $variacaoMes.hashCode());
        final java.lang.Object $percentualVariacaoAno = this.getPercentualVariacaoAno();
        result = result * PRIME + ($percentualVariacaoAno == null ? 43 : $percentualVariacaoAno.hashCode());
        final java.lang.Object $percentualVariacaoMes = this.getPercentualVariacaoMes();
        result = result * PRIME + ($percentualVariacaoMes == null ? 43 : $percentualVariacaoMes.hashCode());
        final java.lang.Object $baseRateio = this.getBaseRateio();
        result = result * PRIME + ($baseRateio == null ? 43 : $baseRateio.hashCode());
        final java.lang.Object $percentualRateio = this.getPercentualRateio();
        result = result * PRIME + ($percentualRateio == null ? 43 : $percentualRateio.hashCode());
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
        return "CentroCusto(id=" + this.getId() + ", codigoCentro=" + this.getCodigoCentro() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", centroPai=" + this.getCentroPai() + ", tipoCentro=" + this.getTipoCentro() + ", status=" + this.getStatus() + ", nivelHierarquia=" + this.getNivelHierarquia() + ", responsavel=" + this.getResponsavel() + ", orcamentoAnual=" + this.getOrcamentoAnual() + ", orcamentoMensal=" + this.getOrcamentoMensal() + ", realizadoAno=" + this.getRealizadoAno() + ", realizadoMes=" + this.getRealizadoMes() + ", variacaoAno=" + this.getVariacaoAno() + ", variacaoMes=" + this.getVariacaoMes() + ", percentualVariacaoAno=" + this.getPercentualVariacaoAno() + ", percentualVariacaoMes=" + this.getPercentualVariacaoMes() + ", baseRateio=" + this.getBaseRateio() + ", percentualRateio=" + this.getPercentualRateio() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public CentroCusto() {
    }

    @java.lang.SuppressWarnings("all")
    public CentroCusto(final Long id, final String codigoCentro, final String nome, final String descricao, final CentroCusto centroPai, final TipoCentro tipoCentro, final StatusCentro status, final Integer nivelHierarquia, final String responsavel, final BigDecimal orcamentoAnual, final BigDecimal orcamentoMensal, final BigDecimal realizadoAno, final BigDecimal realizadoMes, final BigDecimal variacaoAno, final BigDecimal variacaoMes, final BigDecimal percentualVariacaoAno, final BigDecimal percentualVariacaoMes, final String baseRateio, final BigDecimal percentualRateio, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoCentro = codigoCentro;
        this.nome = nome;
        this.descricao = descricao;
        this.centroPai = centroPai;
        this.tipoCentro = tipoCentro;
        this.status = status;
        this.nivelHierarquia = nivelHierarquia;
        this.responsavel = responsavel;
        this.orcamentoAnual = orcamentoAnual;
        this.orcamentoMensal = orcamentoMensal;
        this.realizadoAno = realizadoAno;
        this.realizadoMes = realizadoMes;
        this.variacaoAno = variacaoAno;
        this.variacaoMes = variacaoMes;
        this.percentualVariacaoAno = percentualVariacaoAno;
        this.percentualVariacaoMes = percentualVariacaoMes;
        this.baseRateio = baseRateio;
        this.percentualRateio = percentualRateio;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
