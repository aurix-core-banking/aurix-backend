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
 * Entidade que representa a rentabilidade
 * 
 * Gerencia análise de rentabilidade por produto, cliente, canal e segmento
 */
@Entity
@Table(name = "rentabilidade", schema = "aurix")
public class Rentabilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_rentabilidade", unique = true, nullable = false, length = 50)
    private String codigoRentabilidade;
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_analise", nullable = false)
    private TipoAnalise tipoAnalise;
    @Column(name = "produto_id")
    private Long produtoId;
    @Column(name = "cliente_id")
    private Long clienteId;
    @Column(name = "canal_id")
    private Long canalId;
    @Column(name = "segmento_id")
    private Long segmentoId;
    @Column(name = "receita_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal receitaTotal;
    @Column(name = "custo_direto", precision = 15, scale = 2, nullable = false)
    private BigDecimal custoDireto;
    @Column(name = "custo_indireto", precision = 15, scale = 2, nullable = false)
    private BigDecimal custoIndireto;
    @Column(name = "custo_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal custoTotal;
    @Column(name = "margem_bruta", precision = 15, scale = 2)
    private BigDecimal margemBruta;
    @Column(name = "margem_liquida", precision = 15, scale = 2)
    private BigDecimal margemLiquida;
    @Column(name = "percentual_margem_bruta", precision = 8, scale = 4)
    private BigDecimal percentualMargemBruta;
    @Column(name = "percentual_margem_liquida", precision = 8, scale = 4)
    private BigDecimal percentualMargemLiquida;
    @Column(name = "roa", precision = 8, scale = 4)
    private BigDecimal roa; // Return on Assets
    @Column(name = "roe", precision = 8, scale = 4)
    private BigDecimal roe; // Return on Equity
    @Column(name = "roi", precision = 8, scale = 4)
    private BigDecimal roi; // Return on Investment
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;
    @Column(name = "competencia", length = 7, nullable = false)
    private String competencia;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusRentabilidade status;
    @Column(name = "metodologia_calculo", length = 200)
    private String metodologiaCalculo;
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
     * Tipo de análise de rentabilidade
     */
    public enum TipoAnalise {
        PRODUTO,  // Rentabilidade por produto
        CLIENTE,  // Rentabilidade por cliente
        CANAL,  // Rentabilidade por canal
        SEGMENTO,  // Rentabilidade por segmento
        UNIDADE_NEGOCIO,  // Rentabilidade por unidade de negócio
        CONSOLIDADA // Análise consolidada
        ;
    }


    /**
     * Status da rentabilidade
     */
    public enum StatusRentabilidade {
        CALCULADA,  // Calculada
        APURADA,  // Apurada
        VALIDADA,  // Validada
        APROVADA,  // Aprovada
        CANCELADA // Cancelada
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class RentabilidadeBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoRentabilidade;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoAnalise tipoAnalise;
        @java.lang.SuppressWarnings("all")
        private Long produtoId;
        @java.lang.SuppressWarnings("all")
        private Long clienteId;
        @java.lang.SuppressWarnings("all")
        private Long canalId;
        @java.lang.SuppressWarnings("all")
        private Long segmentoId;
        @java.lang.SuppressWarnings("all")
        private BigDecimal receitaTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal custoDireto;
        @java.lang.SuppressWarnings("all")
        private BigDecimal custoIndireto;
        @java.lang.SuppressWarnings("all")
        private BigDecimal custoTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal margemBruta;
        @java.lang.SuppressWarnings("all")
        private BigDecimal margemLiquida;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualMargemBruta;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualMargemLiquida;
        @java.lang.SuppressWarnings("all")
        private BigDecimal roa;
        @java.lang.SuppressWarnings("all")
        private BigDecimal roe;
        @java.lang.SuppressWarnings("all")
        private BigDecimal roi;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private String competencia;
        @java.lang.SuppressWarnings("all")
        private StatusRentabilidade status;
        @java.lang.SuppressWarnings("all")
        private String metodologiaCalculo;
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
        RentabilidadeBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder codigoRentabilidade(final String codigoRentabilidade) {
            this.codigoRentabilidade = codigoRentabilidade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder tipoAnalise(final TipoAnalise tipoAnalise) {
            this.tipoAnalise = tipoAnalise;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder produtoId(final Long produtoId) {
            this.produtoId = produtoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder clienteId(final Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder canalId(final Long canalId) {
            this.canalId = canalId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder segmentoId(final Long segmentoId) {
            this.segmentoId = segmentoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder receitaTotal(final BigDecimal receitaTotal) {
            this.receitaTotal = receitaTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder custoDireto(final BigDecimal custoDireto) {
            this.custoDireto = custoDireto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder custoIndireto(final BigDecimal custoIndireto) {
            this.custoIndireto = custoIndireto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder custoTotal(final BigDecimal custoTotal) {
            this.custoTotal = custoTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder margemBruta(final BigDecimal margemBruta) {
            this.margemBruta = margemBruta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder margemLiquida(final BigDecimal margemLiquida) {
            this.margemLiquida = margemLiquida;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder percentualMargemBruta(final BigDecimal percentualMargemBruta) {
            this.percentualMargemBruta = percentualMargemBruta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder percentualMargemLiquida(final BigDecimal percentualMargemLiquida) {
            this.percentualMargemLiquida = percentualMargemLiquida;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder roa(final BigDecimal roa) {
            this.roa = roa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder roe(final BigDecimal roe) {
            this.roe = roe;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder roi(final BigDecimal roi) {
            this.roi = roi;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder competencia(final String competencia) {
            this.competencia = competencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder status(final StatusRentabilidade status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder metodologiaCalculo(final String metodologiaCalculo) {
            this.metodologiaCalculo = metodologiaCalculo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Rentabilidade.RentabilidadeBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Rentabilidade build() {
            return new Rentabilidade(this.id, this.codigoRentabilidade, this.descricao, this.tipoAnalise, this.produtoId, this.clienteId, this.canalId, this.segmentoId, this.receitaTotal, this.custoDireto, this.custoIndireto, this.custoTotal, this.margemBruta, this.margemLiquida, this.percentualMargemBruta, this.percentualMargemLiquida, this.roa, this.roe, this.roi, this.dataReferencia, this.competencia, this.status, this.metodologiaCalculo, this.responsavel, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Rentabilidade.RentabilidadeBuilder(id=" + this.id + ", codigoRentabilidade=" + this.codigoRentabilidade + ", descricao=" + this.descricao + ", tipoAnalise=" + this.tipoAnalise + ", produtoId=" + this.produtoId + ", clienteId=" + this.clienteId + ", canalId=" + this.canalId + ", segmentoId=" + this.segmentoId + ", receitaTotal=" + this.receitaTotal + ", custoDireto=" + this.custoDireto + ", custoIndireto=" + this.custoIndireto + ", custoTotal=" + this.custoTotal + ", margemBruta=" + this.margemBruta + ", margemLiquida=" + this.margemLiquida + ", percentualMargemBruta=" + this.percentualMargemBruta + ", percentualMargemLiquida=" + this.percentualMargemLiquida + ", roa=" + this.roa + ", roe=" + this.roe + ", roi=" + this.roi + ", dataReferencia=" + this.dataReferencia + ", competencia=" + this.competencia + ", status=" + this.status + ", metodologiaCalculo=" + this.metodologiaCalculo + ", responsavel=" + this.responsavel + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Rentabilidade.RentabilidadeBuilder builder() {
        return new Rentabilidade.RentabilidadeBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoRentabilidade() {
        return this.codigoRentabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoAnalise getTipoAnalise() {
        return this.tipoAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public Long getProdutoId() {
        return this.produtoId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getCanalId() {
        return this.canalId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getSegmentoId() {
        return this.segmentoId;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getReceitaTotal() {
        return this.receitaTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCustoDireto() {
        return this.custoDireto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCustoIndireto() {
        return this.custoIndireto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCustoTotal() {
        return this.custoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMargemBruta() {
        return this.margemBruta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getMargemLiquida() {
        return this.margemLiquida;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualMargemBruta() {
        return this.percentualMargemBruta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualMargemLiquida() {
        return this.percentualMargemLiquida;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRoa() {
        return this.roa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRoe() {
        return this.roe;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRoi() {
        return this.roi;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getCompetencia() {
        return this.competencia;
    }

    @java.lang.SuppressWarnings("all")
    public StatusRentabilidade getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetodologiaCalculo() {
        return this.metodologiaCalculo;
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
    public void setCodigoRentabilidade(final String codigoRentabilidade) {
        this.codigoRentabilidade = codigoRentabilidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAnalise(final TipoAnalise tipoAnalise) {
        this.tipoAnalise = tipoAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutoId(final Long produtoId) {
        this.produtoId = produtoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setCanalId(final Long canalId) {
        this.canalId = canalId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSegmentoId(final Long segmentoId) {
        this.segmentoId = segmentoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setReceitaTotal(final BigDecimal receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setCustoDireto(final BigDecimal custoDireto) {
        this.custoDireto = custoDireto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCustoIndireto(final BigDecimal custoIndireto) {
        this.custoIndireto = custoIndireto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCustoTotal(final BigDecimal custoTotal) {
        this.custoTotal = custoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setMargemBruta(final BigDecimal margemBruta) {
        this.margemBruta = margemBruta;
    }

    @java.lang.SuppressWarnings("all")
    public void setMargemLiquida(final BigDecimal margemLiquida) {
        this.margemLiquida = margemLiquida;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualMargemBruta(final BigDecimal percentualMargemBruta) {
        this.percentualMargemBruta = percentualMargemBruta;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualMargemLiquida(final BigDecimal percentualMargemLiquida) {
        this.percentualMargemLiquida = percentualMargemLiquida;
    }

    @java.lang.SuppressWarnings("all")
    public void setRoa(final BigDecimal roa) {
        this.roa = roa;
    }

    @java.lang.SuppressWarnings("all")
    public void setRoe(final BigDecimal roe) {
        this.roe = roe;
    }

    @java.lang.SuppressWarnings("all")
    public void setRoi(final BigDecimal roi) {
        this.roi = roi;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataReferencia(final LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setCompetencia(final String competencia) {
        this.competencia = competencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRentabilidade status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetodologiaCalculo(final String metodologiaCalculo) {
        this.metodologiaCalculo = metodologiaCalculo;
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
        if (!(o instanceof Rentabilidade)) return false;
        final Rentabilidade other = (Rentabilidade) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$produtoId = this.getProdutoId();
        final java.lang.Object other$produtoId = other.getProdutoId();
        if (this$produtoId == null ? other$produtoId != null : !this$produtoId.equals(other$produtoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$canalId = this.getCanalId();
        final java.lang.Object other$canalId = other.getCanalId();
        if (this$canalId == null ? other$canalId != null : !this$canalId.equals(other$canalId)) return false;
        final java.lang.Object this$segmentoId = this.getSegmentoId();
        final java.lang.Object other$segmentoId = other.getSegmentoId();
        if (this$segmentoId == null ? other$segmentoId != null : !this$segmentoId.equals(other$segmentoId)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoRentabilidade = this.getCodigoRentabilidade();
        final java.lang.Object other$codigoRentabilidade = other.getCodigoRentabilidade();
        if (this$codigoRentabilidade == null ? other$codigoRentabilidade != null : !this$codigoRentabilidade.equals(other$codigoRentabilidade)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoAnalise = this.getTipoAnalise();
        final java.lang.Object other$tipoAnalise = other.getTipoAnalise();
        if (this$tipoAnalise == null ? other$tipoAnalise != null : !this$tipoAnalise.equals(other$tipoAnalise)) return false;
        final java.lang.Object this$receitaTotal = this.getReceitaTotal();
        final java.lang.Object other$receitaTotal = other.getReceitaTotal();
        if (this$receitaTotal == null ? other$receitaTotal != null : !this$receitaTotal.equals(other$receitaTotal)) return false;
        final java.lang.Object this$custoDireto = this.getCustoDireto();
        final java.lang.Object other$custoDireto = other.getCustoDireto();
        if (this$custoDireto == null ? other$custoDireto != null : !this$custoDireto.equals(other$custoDireto)) return false;
        final java.lang.Object this$custoIndireto = this.getCustoIndireto();
        final java.lang.Object other$custoIndireto = other.getCustoIndireto();
        if (this$custoIndireto == null ? other$custoIndireto != null : !this$custoIndireto.equals(other$custoIndireto)) return false;
        final java.lang.Object this$custoTotal = this.getCustoTotal();
        final java.lang.Object other$custoTotal = other.getCustoTotal();
        if (this$custoTotal == null ? other$custoTotal != null : !this$custoTotal.equals(other$custoTotal)) return false;
        final java.lang.Object this$margemBruta = this.getMargemBruta();
        final java.lang.Object other$margemBruta = other.getMargemBruta();
        if (this$margemBruta == null ? other$margemBruta != null : !this$margemBruta.equals(other$margemBruta)) return false;
        final java.lang.Object this$margemLiquida = this.getMargemLiquida();
        final java.lang.Object other$margemLiquida = other.getMargemLiquida();
        if (this$margemLiquida == null ? other$margemLiquida != null : !this$margemLiquida.equals(other$margemLiquida)) return false;
        final java.lang.Object this$percentualMargemBruta = this.getPercentualMargemBruta();
        final java.lang.Object other$percentualMargemBruta = other.getPercentualMargemBruta();
        if (this$percentualMargemBruta == null ? other$percentualMargemBruta != null : !this$percentualMargemBruta.equals(other$percentualMargemBruta)) return false;
        final java.lang.Object this$percentualMargemLiquida = this.getPercentualMargemLiquida();
        final java.lang.Object other$percentualMargemLiquida = other.getPercentualMargemLiquida();
        if (this$percentualMargemLiquida == null ? other$percentualMargemLiquida != null : !this$percentualMargemLiquida.equals(other$percentualMargemLiquida)) return false;
        final java.lang.Object this$roa = this.getRoa();
        final java.lang.Object other$roa = other.getRoa();
        if (this$roa == null ? other$roa != null : !this$roa.equals(other$roa)) return false;
        final java.lang.Object this$roe = this.getRoe();
        final java.lang.Object other$roe = other.getRoe();
        if (this$roe == null ? other$roe != null : !this$roe.equals(other$roe)) return false;
        final java.lang.Object this$roi = this.getRoi();
        final java.lang.Object other$roi = other.getRoi();
        if (this$roi == null ? other$roi != null : !this$roi.equals(other$roi)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$competencia = this.getCompetencia();
        final java.lang.Object other$competencia = other.getCompetencia();
        if (this$competencia == null ? other$competencia != null : !this$competencia.equals(other$competencia)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$metodologiaCalculo = this.getMetodologiaCalculo();
        final java.lang.Object other$metodologiaCalculo = other.getMetodologiaCalculo();
        if (this$metodologiaCalculo == null ? other$metodologiaCalculo != null : !this$metodologiaCalculo.equals(other$metodologiaCalculo)) return false;
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
        return other instanceof Rentabilidade;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $produtoId = this.getProdutoId();
        result = result * PRIME + ($produtoId == null ? 43 : $produtoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $canalId = this.getCanalId();
        result = result * PRIME + ($canalId == null ? 43 : $canalId.hashCode());
        final java.lang.Object $segmentoId = this.getSegmentoId();
        result = result * PRIME + ($segmentoId == null ? 43 : $segmentoId.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoRentabilidade = this.getCodigoRentabilidade();
        result = result * PRIME + ($codigoRentabilidade == null ? 43 : $codigoRentabilidade.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoAnalise = this.getTipoAnalise();
        result = result * PRIME + ($tipoAnalise == null ? 43 : $tipoAnalise.hashCode());
        final java.lang.Object $receitaTotal = this.getReceitaTotal();
        result = result * PRIME + ($receitaTotal == null ? 43 : $receitaTotal.hashCode());
        final java.lang.Object $custoDireto = this.getCustoDireto();
        result = result * PRIME + ($custoDireto == null ? 43 : $custoDireto.hashCode());
        final java.lang.Object $custoIndireto = this.getCustoIndireto();
        result = result * PRIME + ($custoIndireto == null ? 43 : $custoIndireto.hashCode());
        final java.lang.Object $custoTotal = this.getCustoTotal();
        result = result * PRIME + ($custoTotal == null ? 43 : $custoTotal.hashCode());
        final java.lang.Object $margemBruta = this.getMargemBruta();
        result = result * PRIME + ($margemBruta == null ? 43 : $margemBruta.hashCode());
        final java.lang.Object $margemLiquida = this.getMargemLiquida();
        result = result * PRIME + ($margemLiquida == null ? 43 : $margemLiquida.hashCode());
        final java.lang.Object $percentualMargemBruta = this.getPercentualMargemBruta();
        result = result * PRIME + ($percentualMargemBruta == null ? 43 : $percentualMargemBruta.hashCode());
        final java.lang.Object $percentualMargemLiquida = this.getPercentualMargemLiquida();
        result = result * PRIME + ($percentualMargemLiquida == null ? 43 : $percentualMargemLiquida.hashCode());
        final java.lang.Object $roa = this.getRoa();
        result = result * PRIME + ($roa == null ? 43 : $roa.hashCode());
        final java.lang.Object $roe = this.getRoe();
        result = result * PRIME + ($roe == null ? 43 : $roe.hashCode());
        final java.lang.Object $roi = this.getRoi();
        result = result * PRIME + ($roi == null ? 43 : $roi.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $competencia = this.getCompetencia();
        result = result * PRIME + ($competencia == null ? 43 : $competencia.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $metodologiaCalculo = this.getMetodologiaCalculo();
        result = result * PRIME + ($metodologiaCalculo == null ? 43 : $metodologiaCalculo.hashCode());
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
        return "Rentabilidade(id=" + this.getId() + ", codigoRentabilidade=" + this.getCodigoRentabilidade() + ", descricao=" + this.getDescricao() + ", tipoAnalise=" + this.getTipoAnalise() + ", produtoId=" + this.getProdutoId() + ", clienteId=" + this.getClienteId() + ", canalId=" + this.getCanalId() + ", segmentoId=" + this.getSegmentoId() + ", receitaTotal=" + this.getReceitaTotal() + ", custoDireto=" + this.getCustoDireto() + ", custoIndireto=" + this.getCustoIndireto() + ", custoTotal=" + this.getCustoTotal() + ", margemBruta=" + this.getMargemBruta() + ", margemLiquida=" + this.getMargemLiquida() + ", percentualMargemBruta=" + this.getPercentualMargemBruta() + ", percentualMargemLiquida=" + this.getPercentualMargemLiquida() + ", roa=" + this.getRoa() + ", roe=" + this.getRoe() + ", roi=" + this.getRoi() + ", dataReferencia=" + this.getDataReferencia() + ", competencia=" + this.getCompetencia() + ", status=" + this.getStatus() + ", metodologiaCalculo=" + this.getMetodologiaCalculo() + ", responsavel=" + this.getResponsavel() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Rentabilidade() {
    }

    @java.lang.SuppressWarnings("all")
    public Rentabilidade(final Long id, final String codigoRentabilidade, final String descricao, final TipoAnalise tipoAnalise, final Long produtoId, final Long clienteId, final Long canalId, final Long segmentoId, final BigDecimal receitaTotal, final BigDecimal custoDireto, final BigDecimal custoIndireto, final BigDecimal custoTotal, final BigDecimal margemBruta, final BigDecimal margemLiquida, final BigDecimal percentualMargemBruta, final BigDecimal percentualMargemLiquida, final BigDecimal roa, final BigDecimal roe, final BigDecimal roi, final LocalDate dataReferencia, final String competencia, final StatusRentabilidade status, final String metodologiaCalculo, final String responsavel, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoRentabilidade = codigoRentabilidade;
        this.descricao = descricao;
        this.tipoAnalise = tipoAnalise;
        this.produtoId = produtoId;
        this.clienteId = clienteId;
        this.canalId = canalId;
        this.segmentoId = segmentoId;
        this.receitaTotal = receitaTotal;
        this.custoDireto = custoDireto;
        this.custoIndireto = custoIndireto;
        this.custoTotal = custoTotal;
        this.margemBruta = margemBruta;
        this.margemLiquida = margemLiquida;
        this.percentualMargemBruta = percentualMargemBruta;
        this.percentualMargemLiquida = percentualMargemLiquida;
        this.roa = roa;
        this.roe = roe;
        this.roi = roi;
        this.dataReferencia = dataReferencia;
        this.competencia = competencia;
        this.status = status;
        this.metodologiaCalculo = metodologiaCalculo;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
