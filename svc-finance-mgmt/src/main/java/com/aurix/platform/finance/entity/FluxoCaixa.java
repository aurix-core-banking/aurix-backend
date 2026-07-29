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
 * Entidade que representa o fluxo de caixa
 * 
 * Gerencia projeções e movimentações de caixa
 */
@Entity
@Table(name = "fluxo_caixa", schema = "aurix")
public class FluxoCaixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;
    @Column(name = "saldo_inicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoInicial;
    @Column(name = "entradas_previstas", precision = 15, scale = 2)
    private BigDecimal entradasPrevistas;
    @Column(name = "entradas_realizadas", precision = 15, scale = 2)
    private BigDecimal entradasRealizadas;
    @Column(name = "saidas_previstas", precision = 15, scale = 2)
    private BigDecimal saidasPrevistas;
    @Column(name = "saidas_realizadas", precision = 15, scale = 2)
    private BigDecimal saidasRealizadas;
    @Column(name = "saldo_final_previsto", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoFinalPrevisto;
    @Column(name = "saldo_final_realizado", precision = 15, scale = 2)
    private BigDecimal saldoFinalRealizado;
    @Column(name = "diferenca", precision = 15, scale = 2)
    private BigDecimal diferenca;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_fluxo", nullable = false)
    private TipoFluxo tipoFluxo;
    @Column(name = "conta_bancaria", length = 100)
    private String contaBancaria;
    @Column(name = "moeda", length = 3, nullable = false)
    private String moeda;
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
     * Tipo de fluxo de caixa
     */
    public enum TipoFluxo {
        PROJETADO,  // Projetado
        REALIZADO,  // Realizado
        CONSOLIDADO,  // Consolidado
        DIARIO,  // Diário
        SEMANAL,  // Semanal
        MENSAL // Mensal
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class FluxoCaixaBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoInicial;
        @java.lang.SuppressWarnings("all")
        private BigDecimal entradasPrevistas;
        @java.lang.SuppressWarnings("all")
        private BigDecimal entradasRealizadas;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saidasPrevistas;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saidasRealizadas;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoFinalPrevisto;
        @java.lang.SuppressWarnings("all")
        private BigDecimal saldoFinalRealizado;
        @java.lang.SuppressWarnings("all")
        private BigDecimal diferenca;
        @java.lang.SuppressWarnings("all")
        private TipoFluxo tipoFluxo;
        @java.lang.SuppressWarnings("all")
        private String contaBancaria;
        @java.lang.SuppressWarnings("all")
        private String moeda;
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
        FluxoCaixaBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder saldoInicial(final BigDecimal saldoInicial) {
            this.saldoInicial = saldoInicial;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder entradasPrevistas(final BigDecimal entradasPrevistas) {
            this.entradasPrevistas = entradasPrevistas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder entradasRealizadas(final BigDecimal entradasRealizadas) {
            this.entradasRealizadas = entradasRealizadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder saidasPrevistas(final BigDecimal saidasPrevistas) {
            this.saidasPrevistas = saidasPrevistas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder saidasRealizadas(final BigDecimal saidasRealizadas) {
            this.saidasRealizadas = saidasRealizadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder saldoFinalPrevisto(final BigDecimal saldoFinalPrevisto) {
            this.saldoFinalPrevisto = saldoFinalPrevisto;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder saldoFinalRealizado(final BigDecimal saldoFinalRealizado) {
            this.saldoFinalRealizado = saldoFinalRealizado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder diferenca(final BigDecimal diferenca) {
            this.diferenca = diferenca;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder tipoFluxo(final TipoFluxo tipoFluxo) {
            this.tipoFluxo = tipoFluxo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder contaBancaria(final String contaBancaria) {
            this.contaBancaria = contaBancaria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder moeda(final String moeda) {
            this.moeda = moeda;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public FluxoCaixa.FluxoCaixaBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FluxoCaixa build() {
            return new FluxoCaixa(this.id, this.dataReferencia, this.saldoInicial, this.entradasPrevistas, this.entradasRealizadas, this.saidasPrevistas, this.saidasRealizadas, this.saldoFinalPrevisto, this.saldoFinalRealizado, this.diferenca, this.tipoFluxo, this.contaBancaria, this.moeda, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "FluxoCaixa.FluxoCaixaBuilder(id=" + this.id + ", dataReferencia=" + this.dataReferencia + ", saldoInicial=" + this.saldoInicial + ", entradasPrevistas=" + this.entradasPrevistas + ", entradasRealizadas=" + this.entradasRealizadas + ", saidasPrevistas=" + this.saidasPrevistas + ", saidasRealizadas=" + this.saidasRealizadas + ", saldoFinalPrevisto=" + this.saldoFinalPrevisto + ", saldoFinalRealizado=" + this.saldoFinalRealizado + ", diferenca=" + this.diferenca + ", tipoFluxo=" + this.tipoFluxo + ", contaBancaria=" + this.contaBancaria + ", moeda=" + this.moeda + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static FluxoCaixa.FluxoCaixaBuilder builder() {
        return new FluxoCaixa.FluxoCaixaBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoInicial() {
        return this.saldoInicial;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEntradasPrevistas() {
        return this.entradasPrevistas;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEntradasRealizadas() {
        return this.entradasRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaidasPrevistas() {
        return this.saidasPrevistas;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaidasRealizadas() {
        return this.saidasRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoFinalPrevisto() {
        return this.saldoFinalPrevisto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoFinalRealizado() {
        return this.saldoFinalRealizado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDiferenca() {
        return this.diferenca;
    }

    @java.lang.SuppressWarnings("all")
    public TipoFluxo getTipoFluxo() {
        return this.tipoFluxo;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaBancaria() {
        return this.contaBancaria;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoeda() {
        return this.moeda;
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
    public void setDataReferencia(final LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoInicial(final BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    @java.lang.SuppressWarnings("all")
    public void setEntradasPrevistas(final BigDecimal entradasPrevistas) {
        this.entradasPrevistas = entradasPrevistas;
    }

    @java.lang.SuppressWarnings("all")
    public void setEntradasRealizadas(final BigDecimal entradasRealizadas) {
        this.entradasRealizadas = entradasRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaidasPrevistas(final BigDecimal saidasPrevistas) {
        this.saidasPrevistas = saidasPrevistas;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaidasRealizadas(final BigDecimal saidasRealizadas) {
        this.saidasRealizadas = saidasRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoFinalPrevisto(final BigDecimal saldoFinalPrevisto) {
        this.saldoFinalPrevisto = saldoFinalPrevisto;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoFinalRealizado(final BigDecimal saldoFinalRealizado) {
        this.saldoFinalRealizado = saldoFinalRealizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiferenca(final BigDecimal diferenca) {
        this.diferenca = diferenca;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoFluxo(final TipoFluxo tipoFluxo) {
        this.tipoFluxo = tipoFluxo;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaBancaria(final String contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoeda(final String moeda) {
        this.moeda = moeda;
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
        if (!(o instanceof FluxoCaixa)) return false;
        final FluxoCaixa other = (FluxoCaixa) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$saldoInicial = this.getSaldoInicial();
        final java.lang.Object other$saldoInicial = other.getSaldoInicial();
        if (this$saldoInicial == null ? other$saldoInicial != null : !this$saldoInicial.equals(other$saldoInicial)) return false;
        final java.lang.Object this$entradasPrevistas = this.getEntradasPrevistas();
        final java.lang.Object other$entradasPrevistas = other.getEntradasPrevistas();
        if (this$entradasPrevistas == null ? other$entradasPrevistas != null : !this$entradasPrevistas.equals(other$entradasPrevistas)) return false;
        final java.lang.Object this$entradasRealizadas = this.getEntradasRealizadas();
        final java.lang.Object other$entradasRealizadas = other.getEntradasRealizadas();
        if (this$entradasRealizadas == null ? other$entradasRealizadas != null : !this$entradasRealizadas.equals(other$entradasRealizadas)) return false;
        final java.lang.Object this$saidasPrevistas = this.getSaidasPrevistas();
        final java.lang.Object other$saidasPrevistas = other.getSaidasPrevistas();
        if (this$saidasPrevistas == null ? other$saidasPrevistas != null : !this$saidasPrevistas.equals(other$saidasPrevistas)) return false;
        final java.lang.Object this$saidasRealizadas = this.getSaidasRealizadas();
        final java.lang.Object other$saidasRealizadas = other.getSaidasRealizadas();
        if (this$saidasRealizadas == null ? other$saidasRealizadas != null : !this$saidasRealizadas.equals(other$saidasRealizadas)) return false;
        final java.lang.Object this$saldoFinalPrevisto = this.getSaldoFinalPrevisto();
        final java.lang.Object other$saldoFinalPrevisto = other.getSaldoFinalPrevisto();
        if (this$saldoFinalPrevisto == null ? other$saldoFinalPrevisto != null : !this$saldoFinalPrevisto.equals(other$saldoFinalPrevisto)) return false;
        final java.lang.Object this$saldoFinalRealizado = this.getSaldoFinalRealizado();
        final java.lang.Object other$saldoFinalRealizado = other.getSaldoFinalRealizado();
        if (this$saldoFinalRealizado == null ? other$saldoFinalRealizado != null : !this$saldoFinalRealizado.equals(other$saldoFinalRealizado)) return false;
        final java.lang.Object this$diferenca = this.getDiferenca();
        final java.lang.Object other$diferenca = other.getDiferenca();
        if (this$diferenca == null ? other$diferenca != null : !this$diferenca.equals(other$diferenca)) return false;
        final java.lang.Object this$tipoFluxo = this.getTipoFluxo();
        final java.lang.Object other$tipoFluxo = other.getTipoFluxo();
        if (this$tipoFluxo == null ? other$tipoFluxo != null : !this$tipoFluxo.equals(other$tipoFluxo)) return false;
        final java.lang.Object this$contaBancaria = this.getContaBancaria();
        final java.lang.Object other$contaBancaria = other.getContaBancaria();
        if (this$contaBancaria == null ? other$contaBancaria != null : !this$contaBancaria.equals(other$contaBancaria)) return false;
        final java.lang.Object this$moeda = this.getMoeda();
        final java.lang.Object other$moeda = other.getMoeda();
        if (this$moeda == null ? other$moeda != null : !this$moeda.equals(other$moeda)) return false;
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
        return other instanceof FluxoCaixa;
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
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $saldoInicial = this.getSaldoInicial();
        result = result * PRIME + ($saldoInicial == null ? 43 : $saldoInicial.hashCode());
        final java.lang.Object $entradasPrevistas = this.getEntradasPrevistas();
        result = result * PRIME + ($entradasPrevistas == null ? 43 : $entradasPrevistas.hashCode());
        final java.lang.Object $entradasRealizadas = this.getEntradasRealizadas();
        result = result * PRIME + ($entradasRealizadas == null ? 43 : $entradasRealizadas.hashCode());
        final java.lang.Object $saidasPrevistas = this.getSaidasPrevistas();
        result = result * PRIME + ($saidasPrevistas == null ? 43 : $saidasPrevistas.hashCode());
        final java.lang.Object $saidasRealizadas = this.getSaidasRealizadas();
        result = result * PRIME + ($saidasRealizadas == null ? 43 : $saidasRealizadas.hashCode());
        final java.lang.Object $saldoFinalPrevisto = this.getSaldoFinalPrevisto();
        result = result * PRIME + ($saldoFinalPrevisto == null ? 43 : $saldoFinalPrevisto.hashCode());
        final java.lang.Object $saldoFinalRealizado = this.getSaldoFinalRealizado();
        result = result * PRIME + ($saldoFinalRealizado == null ? 43 : $saldoFinalRealizado.hashCode());
        final java.lang.Object $diferenca = this.getDiferenca();
        result = result * PRIME + ($diferenca == null ? 43 : $diferenca.hashCode());
        final java.lang.Object $tipoFluxo = this.getTipoFluxo();
        result = result * PRIME + ($tipoFluxo == null ? 43 : $tipoFluxo.hashCode());
        final java.lang.Object $contaBancaria = this.getContaBancaria();
        result = result * PRIME + ($contaBancaria == null ? 43 : $contaBancaria.hashCode());
        final java.lang.Object $moeda = this.getMoeda();
        result = result * PRIME + ($moeda == null ? 43 : $moeda.hashCode());
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
        return "FluxoCaixa(id=" + this.getId() + ", dataReferencia=" + this.getDataReferencia() + ", saldoInicial=" + this.getSaldoInicial() + ", entradasPrevistas=" + this.getEntradasPrevistas() + ", entradasRealizadas=" + this.getEntradasRealizadas() + ", saidasPrevistas=" + this.getSaidasPrevistas() + ", saidasRealizadas=" + this.getSaidasRealizadas() + ", saldoFinalPrevisto=" + this.getSaldoFinalPrevisto() + ", saldoFinalRealizado=" + this.getSaldoFinalRealizado() + ", diferenca=" + this.getDiferenca() + ", tipoFluxo=" + this.getTipoFluxo() + ", contaBancaria=" + this.getContaBancaria() + ", moeda=" + this.getMoeda() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public FluxoCaixa() {
    }

    @java.lang.SuppressWarnings("all")
    public FluxoCaixa(final Long id, final LocalDate dataReferencia, final BigDecimal saldoInicial, final BigDecimal entradasPrevistas, final BigDecimal entradasRealizadas, final BigDecimal saidasPrevistas, final BigDecimal saidasRealizadas, final BigDecimal saldoFinalPrevisto, final BigDecimal saldoFinalRealizado, final BigDecimal diferenca, final TipoFluxo tipoFluxo, final String contaBancaria, final String moeda, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.dataReferencia = dataReferencia;
        this.saldoInicial = saldoInicial;
        this.entradasPrevistas = entradasPrevistas;
        this.entradasRealizadas = entradasRealizadas;
        this.saidasPrevistas = saidasPrevistas;
        this.saidasRealizadas = saidasRealizadas;
        this.saldoFinalPrevisto = saldoFinalPrevisto;
        this.saldoFinalRealizado = saldoFinalRealizado;
        this.diferenca = diferenca;
        this.tipoFluxo = tipoFluxo;
        this.contaBancaria = contaBancaria;
        this.moeda = moeda;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
