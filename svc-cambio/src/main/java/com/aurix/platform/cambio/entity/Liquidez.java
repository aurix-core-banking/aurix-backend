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
 * Entidade que representa o controle de liquidez
 * 
 * Gerencia níveis de liquidez e compliance regulatório
 */
@Entity
@Table(name = "liquidez", schema = "aurix")
public class Liquidez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;
    @Column(name = "ativo_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal ativoTotal;
    @Column(name = "passivo_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal passivoTotal;
    @Column(name = "patrimonio_liquido", nullable = false, precision = 15, scale = 2)
    private BigDecimal patrimonioLiquido;
    @Column(name = "caixa_disponivel", precision = 15, scale = 2)
    private BigDecimal caixaDisponivel;
    @Column(name = "reservas_bancarias", precision = 15, scale = 2)
    private BigDecimal reservasBancarias;
    @Column(name = "titulos_publicos", precision = 15, scale = 2)
    private BigDecimal titulosPublicos;
    @Column(name = "depositos_bacen", precision = 15, scale = 2)
    private BigDecimal depositosBacen;
    @Column(name = "ativo_liquido", precision = 15, scale = 2)
    private BigDecimal ativoLiquido;
    @Column(name = "percentual_liquidez", precision = 8, scale = 4)
    private BigDecimal percentualLiquidez;
    @Column(name = "percentual_capital", precision = 8, scale = 4)
    private BigDecimal percentualCapital;
    @Column(name = "percentual_alavancagem", precision = 8, scale = 4)
    private BigDecimal percentualAlavancagem;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_liquidez", nullable = false)
    private StatusLiquidez statusLiquidez;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_capital", nullable = false)
    private StatusCapital statusCapital;
    @Column(name = "limite_minimo_liquidez", precision = 8, scale = 4)
    private BigDecimal limiteMinimoLiquidez;
    @Column(name = "limite_ideal_liquidez", precision = 8, scale = 4)
    private BigDecimal limiteIdealLiquidez;
    @Column(name = "limite_minimo_capital", precision = 8, scale = 4)
    private BigDecimal limiteMinimoCapital;
    @Column(name = "limite_ideal_capital", precision = 8, scale = 4)
    private BigDecimal limiteIdealCapital;
    @Column(name = "projecao_30_dias", precision = 15, scale = 2)
    private BigDecimal projecao30Dias;
    @Column(name = "projecao_90_dias", precision = 15, scale = 2)
    private BigDecimal projecao90Dias;
    @Column(name = "stress_test", precision = 8, scale = 4)
    private BigDecimal stressTest;
    @Column(name = "cenario_crise", precision = 8, scale = 4)
    private BigDecimal cenarioCrise;
    @Column(name = "alertas", length = 1000)
    private String alertas;
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
     * Status da liquidez
     */
    public enum StatusLiquidez {
        ADEQUADA,  // Liquidez adequada
        ATENCAO,  // Atenção - próximo do limite
        CRITICA,  // Crítica - abaixo do limite
        EMERGENCIA // Emergência - muito baixa
        ;
    }


    /**
     * Status do capital
     */
    public enum StatusCapital {
        ADEQUADO,  // Capital adequado
        ATENCAO,  // Atenção - próximo do limite
        INSUFICIENTE,  // Insuficiente - abaixo do limite
        CRITICO // Crítico - muito baixo
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class LiquidezBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private BigDecimal ativoTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal passivoTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal patrimonioLiquido;
        @java.lang.SuppressWarnings("all")
        private BigDecimal caixaDisponivel;
        @java.lang.SuppressWarnings("all")
        private BigDecimal reservasBancarias;
        @java.lang.SuppressWarnings("all")
        private BigDecimal titulosPublicos;
        @java.lang.SuppressWarnings("all")
        private BigDecimal depositosBacen;
        @java.lang.SuppressWarnings("all")
        private BigDecimal ativoLiquido;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualLiquidez;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualCapital;
        @java.lang.SuppressWarnings("all")
        private BigDecimal percentualAlavancagem;
        @java.lang.SuppressWarnings("all")
        private StatusLiquidez statusLiquidez;
        @java.lang.SuppressWarnings("all")
        private StatusCapital statusCapital;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteMinimoLiquidez;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteIdealLiquidez;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteMinimoCapital;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteIdealCapital;
        @java.lang.SuppressWarnings("all")
        private BigDecimal projecao30Dias;
        @java.lang.SuppressWarnings("all")
        private BigDecimal projecao90Dias;
        @java.lang.SuppressWarnings("all")
        private BigDecimal stressTest;
        @java.lang.SuppressWarnings("all")
        private BigDecimal cenarioCrise;
        @java.lang.SuppressWarnings("all")
        private String alertas;
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
        LiquidezBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder ativoTotal(final BigDecimal ativoTotal) {
            this.ativoTotal = ativoTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder passivoTotal(final BigDecimal passivoTotal) {
            this.passivoTotal = passivoTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder patrimonioLiquido(final BigDecimal patrimonioLiquido) {
            this.patrimonioLiquido = patrimonioLiquido;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder caixaDisponivel(final BigDecimal caixaDisponivel) {
            this.caixaDisponivel = caixaDisponivel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder reservasBancarias(final BigDecimal reservasBancarias) {
            this.reservasBancarias = reservasBancarias;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder titulosPublicos(final BigDecimal titulosPublicos) {
            this.titulosPublicos = titulosPublicos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder depositosBacen(final BigDecimal depositosBacen) {
            this.depositosBacen = depositosBacen;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder ativoLiquido(final BigDecimal ativoLiquido) {
            this.ativoLiquido = ativoLiquido;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder percentualLiquidez(final BigDecimal percentualLiquidez) {
            this.percentualLiquidez = percentualLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder percentualCapital(final BigDecimal percentualCapital) {
            this.percentualCapital = percentualCapital;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder percentualAlavancagem(final BigDecimal percentualAlavancagem) {
            this.percentualAlavancagem = percentualAlavancagem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder statusLiquidez(final StatusLiquidez statusLiquidez) {
            this.statusLiquidez = statusLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder statusCapital(final StatusCapital statusCapital) {
            this.statusCapital = statusCapital;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder limiteMinimoLiquidez(final BigDecimal limiteMinimoLiquidez) {
            this.limiteMinimoLiquidez = limiteMinimoLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder limiteIdealLiquidez(final BigDecimal limiteIdealLiquidez) {
            this.limiteIdealLiquidez = limiteIdealLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder limiteMinimoCapital(final BigDecimal limiteMinimoCapital) {
            this.limiteMinimoCapital = limiteMinimoCapital;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder limiteIdealCapital(final BigDecimal limiteIdealCapital) {
            this.limiteIdealCapital = limiteIdealCapital;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder projecao30Dias(final BigDecimal projecao30Dias) {
            this.projecao30Dias = projecao30Dias;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder projecao90Dias(final BigDecimal projecao90Dias) {
            this.projecao90Dias = projecao90Dias;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder stressTest(final BigDecimal stressTest) {
            this.stressTest = stressTest;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder cenarioCrise(final BigDecimal cenarioCrise) {
            this.cenarioCrise = cenarioCrise;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder alertas(final String alertas) {
            this.alertas = alertas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Liquidez build() {
            return new Liquidez(this.id, this.dataReferencia, this.ativoTotal, this.passivoTotal, this.patrimonioLiquido, this.caixaDisponivel, this.reservasBancarias, this.titulosPublicos, this.depositosBacen, this.ativoLiquido, this.percentualLiquidez, this.percentualCapital, this.percentualAlavancagem, this.statusLiquidez, this.statusCapital, this.limiteMinimoLiquidez, this.limiteIdealLiquidez, this.limiteMinimoCapital, this.limiteIdealCapital, this.projecao30Dias, this.projecao90Dias, this.stressTest, this.cenarioCrise, this.alertas, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Liquidez.LiquidezBuilder(id=" + this.id + ", dataReferencia=" + this.dataReferencia + ", ativoTotal=" + this.ativoTotal + ", passivoTotal=" + this.passivoTotal + ", patrimonioLiquido=" + this.patrimonioLiquido + ", caixaDisponivel=" + this.caixaDisponivel + ", reservasBancarias=" + this.reservasBancarias + ", titulosPublicos=" + this.titulosPublicos + ", depositosBacen=" + this.depositosBacen + ", ativoLiquido=" + this.ativoLiquido + ", percentualLiquidez=" + this.percentualLiquidez + ", percentualCapital=" + this.percentualCapital + ", percentualAlavancagem=" + this.percentualAlavancagem + ", statusLiquidez=" + this.statusLiquidez + ", statusCapital=" + this.statusCapital + ", limiteMinimoLiquidez=" + this.limiteMinimoLiquidez + ", limiteIdealLiquidez=" + this.limiteIdealLiquidez + ", limiteMinimoCapital=" + this.limiteMinimoCapital + ", limiteIdealCapital=" + this.limiteIdealCapital + ", projecao30Dias=" + this.projecao30Dias + ", projecao90Dias=" + this.projecao90Dias + ", stressTest=" + this.stressTest + ", cenarioCrise=" + this.cenarioCrise + ", alertas=" + this.alertas + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Liquidez.LiquidezBuilder builder() {
        return new Liquidez.LiquidezBuilder();
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
    public BigDecimal getAtivoTotal() {
        return this.ativoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPassivoTotal() {
        return this.passivoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPatrimonioLiquido() {
        return this.patrimonioLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCaixaDisponivel() {
        return this.caixaDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getReservasBancarias() {
        return this.reservasBancarias;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTitulosPublicos() {
        return this.titulosPublicos;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getDepositosBacen() {
        return this.depositosBacen;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getAtivoLiquido() {
        return this.ativoLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualLiquidez() {
        return this.percentualLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualCapital() {
        return this.percentualCapital;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualAlavancagem() {
        return this.percentualAlavancagem;
    }

    @java.lang.SuppressWarnings("all")
    public StatusLiquidez getStatusLiquidez() {
        return this.statusLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public StatusCapital getStatusCapital() {
        return this.statusCapital;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteMinimoLiquidez() {
        return this.limiteMinimoLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteIdealLiquidez() {
        return this.limiteIdealLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteMinimoCapital() {
        return this.limiteMinimoCapital;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteIdealCapital() {
        return this.limiteIdealCapital;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProjecao30Dias() {
        return this.projecao30Dias;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getProjecao90Dias() {
        return this.projecao90Dias;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getStressTest() {
        return this.stressTest;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCenarioCrise() {
        return this.cenarioCrise;
    }

    @java.lang.SuppressWarnings("all")
    public String getAlertas() {
        return this.alertas;
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
    public void setAtivoTotal(final BigDecimal ativoTotal) {
        this.ativoTotal = ativoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setPassivoTotal(final BigDecimal passivoTotal) {
        this.passivoTotal = passivoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setPatrimonioLiquido(final BigDecimal patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setCaixaDisponivel(final BigDecimal caixaDisponivel) {
        this.caixaDisponivel = caixaDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setReservasBancarias(final BigDecimal reservasBancarias) {
        this.reservasBancarias = reservasBancarias;
    }

    @java.lang.SuppressWarnings("all")
    public void setTitulosPublicos(final BigDecimal titulosPublicos) {
        this.titulosPublicos = titulosPublicos;
    }

    @java.lang.SuppressWarnings("all")
    public void setDepositosBacen(final BigDecimal depositosBacen) {
        this.depositosBacen = depositosBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivoLiquido(final BigDecimal ativoLiquido) {
        this.ativoLiquido = ativoLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualLiquidez(final BigDecimal percentualLiquidez) {
        this.percentualLiquidez = percentualLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualCapital(final BigDecimal percentualCapital) {
        this.percentualCapital = percentualCapital;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualAlavancagem(final BigDecimal percentualAlavancagem) {
        this.percentualAlavancagem = percentualAlavancagem;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatusLiquidez(final StatusLiquidez statusLiquidez) {
        this.statusLiquidez = statusLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatusCapital(final StatusCapital statusCapital) {
        this.statusCapital = statusCapital;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteMinimoLiquidez(final BigDecimal limiteMinimoLiquidez) {
        this.limiteMinimoLiquidez = limiteMinimoLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteIdealLiquidez(final BigDecimal limiteIdealLiquidez) {
        this.limiteIdealLiquidez = limiteIdealLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteMinimoCapital(final BigDecimal limiteMinimoCapital) {
        this.limiteMinimoCapital = limiteMinimoCapital;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteIdealCapital(final BigDecimal limiteIdealCapital) {
        this.limiteIdealCapital = limiteIdealCapital;
    }

    @java.lang.SuppressWarnings("all")
    public void setProjecao30Dias(final BigDecimal projecao30Dias) {
        this.projecao30Dias = projecao30Dias;
    }

    @java.lang.SuppressWarnings("all")
    public void setProjecao90Dias(final BigDecimal projecao90Dias) {
        this.projecao90Dias = projecao90Dias;
    }

    @java.lang.SuppressWarnings("all")
    public void setStressTest(final BigDecimal stressTest) {
        this.stressTest = stressTest;
    }

    @java.lang.SuppressWarnings("all")
    public void setCenarioCrise(final BigDecimal cenarioCrise) {
        this.cenarioCrise = cenarioCrise;
    }

    @java.lang.SuppressWarnings("all")
    public void setAlertas(final String alertas) {
        this.alertas = alertas;
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
        if (!(o instanceof Liquidez)) return false;
        final Liquidez other = (Liquidez) o;
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
        final java.lang.Object this$ativoTotal = this.getAtivoTotal();
        final java.lang.Object other$ativoTotal = other.getAtivoTotal();
        if (this$ativoTotal == null ? other$ativoTotal != null : !this$ativoTotal.equals(other$ativoTotal)) return false;
        final java.lang.Object this$passivoTotal = this.getPassivoTotal();
        final java.lang.Object other$passivoTotal = other.getPassivoTotal();
        if (this$passivoTotal == null ? other$passivoTotal != null : !this$passivoTotal.equals(other$passivoTotal)) return false;
        final java.lang.Object this$patrimonioLiquido = this.getPatrimonioLiquido();
        final java.lang.Object other$patrimonioLiquido = other.getPatrimonioLiquido();
        if (this$patrimonioLiquido == null ? other$patrimonioLiquido != null : !this$patrimonioLiquido.equals(other$patrimonioLiquido)) return false;
        final java.lang.Object this$caixaDisponivel = this.getCaixaDisponivel();
        final java.lang.Object other$caixaDisponivel = other.getCaixaDisponivel();
        if (this$caixaDisponivel == null ? other$caixaDisponivel != null : !this$caixaDisponivel.equals(other$caixaDisponivel)) return false;
        final java.lang.Object this$reservasBancarias = this.getReservasBancarias();
        final java.lang.Object other$reservasBancarias = other.getReservasBancarias();
        if (this$reservasBancarias == null ? other$reservasBancarias != null : !this$reservasBancarias.equals(other$reservasBancarias)) return false;
        final java.lang.Object this$titulosPublicos = this.getTitulosPublicos();
        final java.lang.Object other$titulosPublicos = other.getTitulosPublicos();
        if (this$titulosPublicos == null ? other$titulosPublicos != null : !this$titulosPublicos.equals(other$titulosPublicos)) return false;
        final java.lang.Object this$depositosBacen = this.getDepositosBacen();
        final java.lang.Object other$depositosBacen = other.getDepositosBacen();
        if (this$depositosBacen == null ? other$depositosBacen != null : !this$depositosBacen.equals(other$depositosBacen)) return false;
        final java.lang.Object this$ativoLiquido = this.getAtivoLiquido();
        final java.lang.Object other$ativoLiquido = other.getAtivoLiquido();
        if (this$ativoLiquido == null ? other$ativoLiquido != null : !this$ativoLiquido.equals(other$ativoLiquido)) return false;
        final java.lang.Object this$percentualLiquidez = this.getPercentualLiquidez();
        final java.lang.Object other$percentualLiquidez = other.getPercentualLiquidez();
        if (this$percentualLiquidez == null ? other$percentualLiquidez != null : !this$percentualLiquidez.equals(other$percentualLiquidez)) return false;
        final java.lang.Object this$percentualCapital = this.getPercentualCapital();
        final java.lang.Object other$percentualCapital = other.getPercentualCapital();
        if (this$percentualCapital == null ? other$percentualCapital != null : !this$percentualCapital.equals(other$percentualCapital)) return false;
        final java.lang.Object this$percentualAlavancagem = this.getPercentualAlavancagem();
        final java.lang.Object other$percentualAlavancagem = other.getPercentualAlavancagem();
        if (this$percentualAlavancagem == null ? other$percentualAlavancagem != null : !this$percentualAlavancagem.equals(other$percentualAlavancagem)) return false;
        final java.lang.Object this$statusLiquidez = this.getStatusLiquidez();
        final java.lang.Object other$statusLiquidez = other.getStatusLiquidez();
        if (this$statusLiquidez == null ? other$statusLiquidez != null : !this$statusLiquidez.equals(other$statusLiquidez)) return false;
        final java.lang.Object this$statusCapital = this.getStatusCapital();
        final java.lang.Object other$statusCapital = other.getStatusCapital();
        if (this$statusCapital == null ? other$statusCapital != null : !this$statusCapital.equals(other$statusCapital)) return false;
        final java.lang.Object this$limiteMinimoLiquidez = this.getLimiteMinimoLiquidez();
        final java.lang.Object other$limiteMinimoLiquidez = other.getLimiteMinimoLiquidez();
        if (this$limiteMinimoLiquidez == null ? other$limiteMinimoLiquidez != null : !this$limiteMinimoLiquidez.equals(other$limiteMinimoLiquidez)) return false;
        final java.lang.Object this$limiteIdealLiquidez = this.getLimiteIdealLiquidez();
        final java.lang.Object other$limiteIdealLiquidez = other.getLimiteIdealLiquidez();
        if (this$limiteIdealLiquidez == null ? other$limiteIdealLiquidez != null : !this$limiteIdealLiquidez.equals(other$limiteIdealLiquidez)) return false;
        final java.lang.Object this$limiteMinimoCapital = this.getLimiteMinimoCapital();
        final java.lang.Object other$limiteMinimoCapital = other.getLimiteMinimoCapital();
        if (this$limiteMinimoCapital == null ? other$limiteMinimoCapital != null : !this$limiteMinimoCapital.equals(other$limiteMinimoCapital)) return false;
        final java.lang.Object this$limiteIdealCapital = this.getLimiteIdealCapital();
        final java.lang.Object other$limiteIdealCapital = other.getLimiteIdealCapital();
        if (this$limiteIdealCapital == null ? other$limiteIdealCapital != null : !this$limiteIdealCapital.equals(other$limiteIdealCapital)) return false;
        final java.lang.Object this$projecao30Dias = this.getProjecao30Dias();
        final java.lang.Object other$projecao30Dias = other.getProjecao30Dias();
        if (this$projecao30Dias == null ? other$projecao30Dias != null : !this$projecao30Dias.equals(other$projecao30Dias)) return false;
        final java.lang.Object this$projecao90Dias = this.getProjecao90Dias();
        final java.lang.Object other$projecao90Dias = other.getProjecao90Dias();
        if (this$projecao90Dias == null ? other$projecao90Dias != null : !this$projecao90Dias.equals(other$projecao90Dias)) return false;
        final java.lang.Object this$stressTest = this.getStressTest();
        final java.lang.Object other$stressTest = other.getStressTest();
        if (this$stressTest == null ? other$stressTest != null : !this$stressTest.equals(other$stressTest)) return false;
        final java.lang.Object this$cenarioCrise = this.getCenarioCrise();
        final java.lang.Object other$cenarioCrise = other.getCenarioCrise();
        if (this$cenarioCrise == null ? other$cenarioCrise != null : !this$cenarioCrise.equals(other$cenarioCrise)) return false;
        final java.lang.Object this$alertas = this.getAlertas();
        final java.lang.Object other$alertas = other.getAlertas();
        if (this$alertas == null ? other$alertas != null : !this$alertas.equals(other$alertas)) return false;
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
        return other instanceof Liquidez;
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
        final java.lang.Object $ativoTotal = this.getAtivoTotal();
        result = result * PRIME + ($ativoTotal == null ? 43 : $ativoTotal.hashCode());
        final java.lang.Object $passivoTotal = this.getPassivoTotal();
        result = result * PRIME + ($passivoTotal == null ? 43 : $passivoTotal.hashCode());
        final java.lang.Object $patrimonioLiquido = this.getPatrimonioLiquido();
        result = result * PRIME + ($patrimonioLiquido == null ? 43 : $patrimonioLiquido.hashCode());
        final java.lang.Object $caixaDisponivel = this.getCaixaDisponivel();
        result = result * PRIME + ($caixaDisponivel == null ? 43 : $caixaDisponivel.hashCode());
        final java.lang.Object $reservasBancarias = this.getReservasBancarias();
        result = result * PRIME + ($reservasBancarias == null ? 43 : $reservasBancarias.hashCode());
        final java.lang.Object $titulosPublicos = this.getTitulosPublicos();
        result = result * PRIME + ($titulosPublicos == null ? 43 : $titulosPublicos.hashCode());
        final java.lang.Object $depositosBacen = this.getDepositosBacen();
        result = result * PRIME + ($depositosBacen == null ? 43 : $depositosBacen.hashCode());
        final java.lang.Object $ativoLiquido = this.getAtivoLiquido();
        result = result * PRIME + ($ativoLiquido == null ? 43 : $ativoLiquido.hashCode());
        final java.lang.Object $percentualLiquidez = this.getPercentualLiquidez();
        result = result * PRIME + ($percentualLiquidez == null ? 43 : $percentualLiquidez.hashCode());
        final java.lang.Object $percentualCapital = this.getPercentualCapital();
        result = result * PRIME + ($percentualCapital == null ? 43 : $percentualCapital.hashCode());
        final java.lang.Object $percentualAlavancagem = this.getPercentualAlavancagem();
        result = result * PRIME + ($percentualAlavancagem == null ? 43 : $percentualAlavancagem.hashCode());
        final java.lang.Object $statusLiquidez = this.getStatusLiquidez();
        result = result * PRIME + ($statusLiquidez == null ? 43 : $statusLiquidez.hashCode());
        final java.lang.Object $statusCapital = this.getStatusCapital();
        result = result * PRIME + ($statusCapital == null ? 43 : $statusCapital.hashCode());
        final java.lang.Object $limiteMinimoLiquidez = this.getLimiteMinimoLiquidez();
        result = result * PRIME + ($limiteMinimoLiquidez == null ? 43 : $limiteMinimoLiquidez.hashCode());
        final java.lang.Object $limiteIdealLiquidez = this.getLimiteIdealLiquidez();
        result = result * PRIME + ($limiteIdealLiquidez == null ? 43 : $limiteIdealLiquidez.hashCode());
        final java.lang.Object $limiteMinimoCapital = this.getLimiteMinimoCapital();
        result = result * PRIME + ($limiteMinimoCapital == null ? 43 : $limiteMinimoCapital.hashCode());
        final java.lang.Object $limiteIdealCapital = this.getLimiteIdealCapital();
        result = result * PRIME + ($limiteIdealCapital == null ? 43 : $limiteIdealCapital.hashCode());
        final java.lang.Object $projecao30Dias = this.getProjecao30Dias();
        result = result * PRIME + ($projecao30Dias == null ? 43 : $projecao30Dias.hashCode());
        final java.lang.Object $projecao90Dias = this.getProjecao90Dias();
        result = result * PRIME + ($projecao90Dias == null ? 43 : $projecao90Dias.hashCode());
        final java.lang.Object $stressTest = this.getStressTest();
        result = result * PRIME + ($stressTest == null ? 43 : $stressTest.hashCode());
        final java.lang.Object $cenarioCrise = this.getCenarioCrise();
        result = result * PRIME + ($cenarioCrise == null ? 43 : $cenarioCrise.hashCode());
        final java.lang.Object $alertas = this.getAlertas();
        result = result * PRIME + ($alertas == null ? 43 : $alertas.hashCode());
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
        return "Liquidez(id=" + this.getId() + ", dataReferencia=" + this.getDataReferencia() + ", ativoTotal=" + this.getAtivoTotal() + ", passivoTotal=" + this.getPassivoTotal() + ", patrimonioLiquido=" + this.getPatrimonioLiquido() + ", caixaDisponivel=" + this.getCaixaDisponivel() + ", reservasBancarias=" + this.getReservasBancarias() + ", titulosPublicos=" + this.getTitulosPublicos() + ", depositosBacen=" + this.getDepositosBacen() + ", ativoLiquido=" + this.getAtivoLiquido() + ", percentualLiquidez=" + this.getPercentualLiquidez() + ", percentualCapital=" + this.getPercentualCapital() + ", percentualAlavancagem=" + this.getPercentualAlavancagem() + ", statusLiquidez=" + this.getStatusLiquidez() + ", statusCapital=" + this.getStatusCapital() + ", limiteMinimoLiquidez=" + this.getLimiteMinimoLiquidez() + ", limiteIdealLiquidez=" + this.getLimiteIdealLiquidez() + ", limiteMinimoCapital=" + this.getLimiteMinimoCapital() + ", limiteIdealCapital=" + this.getLimiteIdealCapital() + ", projecao30Dias=" + this.getProjecao30Dias() + ", projecao90Dias=" + this.getProjecao90Dias() + ", stressTest=" + this.getStressTest() + ", cenarioCrise=" + this.getCenarioCrise() + ", alertas=" + this.getAlertas() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Liquidez() {
    }

    @java.lang.SuppressWarnings("all")
    public Liquidez(final Long id, final LocalDate dataReferencia, final BigDecimal ativoTotal, final BigDecimal passivoTotal, final BigDecimal patrimonioLiquido, final BigDecimal caixaDisponivel, final BigDecimal reservasBancarias, final BigDecimal titulosPublicos, final BigDecimal depositosBacen, final BigDecimal ativoLiquido, final BigDecimal percentualLiquidez, final BigDecimal percentualCapital, final BigDecimal percentualAlavancagem, final StatusLiquidez statusLiquidez, final StatusCapital statusCapital, final BigDecimal limiteMinimoLiquidez, final BigDecimal limiteIdealLiquidez, final BigDecimal limiteMinimoCapital, final BigDecimal limiteIdealCapital, final BigDecimal projecao30Dias, final BigDecimal projecao90Dias, final BigDecimal stressTest, final BigDecimal cenarioCrise, final String alertas, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.dataReferencia = dataReferencia;
        this.ativoTotal = ativoTotal;
        this.passivoTotal = passivoTotal;
        this.patrimonioLiquido = patrimonioLiquido;
        this.caixaDisponivel = caixaDisponivel;
        this.reservasBancarias = reservasBancarias;
        this.titulosPublicos = titulosPublicos;
        this.depositosBacen = depositosBacen;
        this.ativoLiquido = ativoLiquido;
        this.percentualLiquidez = percentualLiquidez;
        this.percentualCapital = percentualCapital;
        this.percentualAlavancagem = percentualAlavancagem;
        this.statusLiquidez = statusLiquidez;
        this.statusCapital = statusCapital;
        this.limiteMinimoLiquidez = limiteMinimoLiquidez;
        this.limiteIdealLiquidez = limiteIdealLiquidez;
        this.limiteMinimoCapital = limiteMinimoCapital;
        this.limiteIdealCapital = limiteIdealCapital;
        this.projecao30Dias = projecao30Dias;
        this.projecao90Dias = projecao90Dias;
        this.stressTest = stressTest;
        this.cenarioCrise = cenarioCrise;
        this.alertas = alertas;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
