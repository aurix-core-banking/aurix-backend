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
 * Entidade que representa os relatórios específicos do IFRS 9
 * 
 * Armazena informações sobre relatórios gerados para conformidade
 * com o IFRS 9, incluindo classificações, ECL e hedge accounting.
 */
@Entity
@Table(name = "relatorios_ifrs9", schema = "aurix")
public class RelatorioIFRS9 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_relatorio", unique = true, nullable = false, length = 50)
    private String codigoRelatorio;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relatorio", nullable = false)
    private TipoRelatorioIFRS9 tipoRelatorio;
    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;
    @Column(name = "periodo_fim", nullable = false)
    private LocalDate periodoFim;
    @Enumerated(EnumType.STRING)
    @Column(name = "formato", nullable = false)
    private FormatoRelatorio formato;
    @Column(name = "caminho_arquivo", length = 500)
    private String caminhoArquivo;
    @Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;
    @Column(name = "hash_arquivo", length = 64)
    private String hashArquivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusRelatorio status;
    @Column(name = "total_instrumentos")
    private Long totalInstrumentos;
    @Column(name = "valor_total_exposicao", precision = 15, scale = 2)
    private BigDecimal valorTotalExposicao;
    @Column(name = "valor_total_ecl", precision = 15, scale = 2)
    private BigDecimal valorTotalECL;
    @Column(name = "valor_total_provisao", precision = 15, scale = 2)
    private BigDecimal valorTotalProvisao;
    @Column(name = "instrumentos_estagio1")
    private Long instrumentosEstagio1;
    @Column(name = "instrumentos_estagio2")
    private Long instrumentosEstagio2;
    @Column(name = "instrumentos_estagio3")
    private Long instrumentosEstagio3;
    @Column(name = "valor_ecl_estagio1", precision = 15, scale = 2)
    private BigDecimal valorECLEstagio1;
    @Column(name = "valor_ecl_estagio2", precision = 15, scale = 2)
    private BigDecimal valorECLEstagio2;
    @Column(name = "valor_ecl_estagio3", precision = 15, scale = 2)
    private BigDecimal valorECLEstagio3;
    @Column(name = "hedges_ativos")
    private Long hedgesAtivos;
    @Column(name = "valor_hedge_total", precision = 15, scale = 2)
    private BigDecimal valorHedgeTotal;
    @Column(name = "efetividade_media", precision = 8, scale = 6)
    private BigDecimal efetividadeMedia;
    @Column(name = "usuario_geracao", length = 100)
    private String usuarioGeracao;
    @Column(name = "data_geracao", nullable = false)
    private LocalDateTime dataGeracao;
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    @Column(name = "tempo_processamento")
    private Long tempoProcessamento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros_geracao", columnDefinition = "jsonb")
    private String parametrosGeracao;
    @Column(name = "resumo_executivo", length = 2000)
    private String resumoExecutivo;
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
     * Tipo de relatório IFRS 9
     */
    public enum TipoRelatorioIFRS9 {
        CLASSIFICACAO_MENSURACAO,  // Classificação e mensuração
        ECL_DETALHADO,  // ECL detalhado
        ECL_CONSOLIDADO,  // ECL consolidado
        HEDGE_ACCOUNTING,  // Hedge accounting
        PROVISAO_IMPAIRMENT,  // Provisão para impairment
        RECLASSIFICACAO,  // Reclassificação de instrumentos
        DISCLOSURE_NOTES,  // Notas explicativas
        AUDITORIA_IFRS9,  // Relatório de auditoria
        COMPLIANCE_REPORT,  // Relatório de conformidade
        DASHBOARD_EXECUTIVO // Dashboard executivo
        ;
    }


    /**
     * Formato do relatório
     */
    public enum FormatoRelatorio {
        PDF,  // PDF
        EXCEL,  // Excel
        CSV,  // CSV
        XML,  // XML
        JSON,  // JSON
        HTML // HTML
        ;
    }


    /**
     * Status do relatório
     */
    public enum StatusRelatorio {
        PENDENTE,  // Pendente de geração
        GERANDO,  // Em processo de geração
        CONCLUIDO,  // Concluído
        ERRO,  // Erro na geração
        CANCELADO,  // Cancelado
        EXPIRADO // Expirado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class RelatorioIFRS9Builder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoRelatorio;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoRelatorioIFRS9 tipoRelatorio;
        @java.lang.SuppressWarnings("all")
        private LocalDate periodoInicio;
        @java.lang.SuppressWarnings("all")
        private LocalDate periodoFim;
        @java.lang.SuppressWarnings("all")
        private FormatoRelatorio formato;
        @java.lang.SuppressWarnings("all")
        private String caminhoArquivo;
        @java.lang.SuppressWarnings("all")
        private Long tamanhoArquivo;
        @java.lang.SuppressWarnings("all")
        private String hashArquivo;
        @java.lang.SuppressWarnings("all")
        private StatusRelatorio status;
        @java.lang.SuppressWarnings("all")
        private Long totalInstrumentos;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotalExposicao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotalECL;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotalProvisao;
        @java.lang.SuppressWarnings("all")
        private Long instrumentosEstagio1;
        @java.lang.SuppressWarnings("all")
        private Long instrumentosEstagio2;
        @java.lang.SuppressWarnings("all")
        private Long instrumentosEstagio3;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorECLEstagio1;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorECLEstagio2;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorECLEstagio3;
        @java.lang.SuppressWarnings("all")
        private Long hedgesAtivos;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorHedgeTotal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal efetividadeMedia;
        @java.lang.SuppressWarnings("all")
        private String usuarioGeracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataGeracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataConclusao;
        @java.lang.SuppressWarnings("all")
        private Long tempoProcessamento;
        @java.lang.SuppressWarnings("all")
        private String parametrosGeracao;
        @java.lang.SuppressWarnings("all")
        private String resumoExecutivo;
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
        RelatorioIFRS9Builder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder codigoRelatorio(final String codigoRelatorio) {
            this.codigoRelatorio = codigoRelatorio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder tipoRelatorio(final TipoRelatorioIFRS9 tipoRelatorio) {
            this.tipoRelatorio = tipoRelatorio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder periodoInicio(final LocalDate periodoInicio) {
            this.periodoInicio = periodoInicio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder periodoFim(final LocalDate periodoFim) {
            this.periodoFim = periodoFim;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder formato(final FormatoRelatorio formato) {
            this.formato = formato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder caminhoArquivo(final String caminhoArquivo) {
            this.caminhoArquivo = caminhoArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder tamanhoArquivo(final Long tamanhoArquivo) {
            this.tamanhoArquivo = tamanhoArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder hashArquivo(final String hashArquivo) {
            this.hashArquivo = hashArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder status(final StatusRelatorio status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder totalInstrumentos(final Long totalInstrumentos) {
            this.totalInstrumentos = totalInstrumentos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorTotalExposicao(final BigDecimal valorTotalExposicao) {
            this.valorTotalExposicao = valorTotalExposicao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorTotalECL(final BigDecimal valorTotalECL) {
            this.valorTotalECL = valorTotalECL;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorTotalProvisao(final BigDecimal valorTotalProvisao) {
            this.valorTotalProvisao = valorTotalProvisao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder instrumentosEstagio1(final Long instrumentosEstagio1) {
            this.instrumentosEstagio1 = instrumentosEstagio1;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder instrumentosEstagio2(final Long instrumentosEstagio2) {
            this.instrumentosEstagio2 = instrumentosEstagio2;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder instrumentosEstagio3(final Long instrumentosEstagio3) {
            this.instrumentosEstagio3 = instrumentosEstagio3;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorECLEstagio1(final BigDecimal valorECLEstagio1) {
            this.valorECLEstagio1 = valorECLEstagio1;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorECLEstagio2(final BigDecimal valorECLEstagio2) {
            this.valorECLEstagio2 = valorECLEstagio2;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorECLEstagio3(final BigDecimal valorECLEstagio3) {
            this.valorECLEstagio3 = valorECLEstagio3;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder hedgesAtivos(final Long hedgesAtivos) {
            this.hedgesAtivos = hedgesAtivos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder valorHedgeTotal(final BigDecimal valorHedgeTotal) {
            this.valorHedgeTotal = valorHedgeTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder efetividadeMedia(final BigDecimal efetividadeMedia) {
            this.efetividadeMedia = efetividadeMedia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder usuarioGeracao(final String usuarioGeracao) {
            this.usuarioGeracao = usuarioGeracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder dataGeracao(final LocalDateTime dataGeracao) {
            this.dataGeracao = dataGeracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder dataConclusao(final LocalDateTime dataConclusao) {
            this.dataConclusao = dataConclusao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder tempoProcessamento(final Long tempoProcessamento) {
            this.tempoProcessamento = tempoProcessamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder parametrosGeracao(final String parametrosGeracao) {
            this.parametrosGeracao = parametrosGeracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder resumoExecutivo(final String resumoExecutivo) {
            this.resumoExecutivo = resumoExecutivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9.RelatorioIFRS9Builder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public RelatorioIFRS9 build() {
            return new RelatorioIFRS9(this.id, this.codigoRelatorio, this.nome, this.descricao, this.tipoRelatorio, this.periodoInicio, this.periodoFim, this.formato, this.caminhoArquivo, this.tamanhoArquivo, this.hashArquivo, this.status, this.totalInstrumentos, this.valorTotalExposicao, this.valorTotalECL, this.valorTotalProvisao, this.instrumentosEstagio1, this.instrumentosEstagio2, this.instrumentosEstagio3, this.valorECLEstagio1, this.valorECLEstagio2, this.valorECLEstagio3, this.hedgesAtivos, this.valorHedgeTotal, this.efetividadeMedia, this.usuarioGeracao, this.dataGeracao, this.dataConclusao, this.tempoProcessamento, this.parametrosGeracao, this.resumoExecutivo, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "RelatorioIFRS9.RelatorioIFRS9Builder(id=" + this.id + ", codigoRelatorio=" + this.codigoRelatorio + ", nome=" + this.nome + ", descricao=" + this.descricao + ", tipoRelatorio=" + this.tipoRelatorio + ", periodoInicio=" + this.periodoInicio + ", periodoFim=" + this.periodoFim + ", formato=" + this.formato + ", caminhoArquivo=" + this.caminhoArquivo + ", tamanhoArquivo=" + this.tamanhoArquivo + ", hashArquivo=" + this.hashArquivo + ", status=" + this.status + ", totalInstrumentos=" + this.totalInstrumentos + ", valorTotalExposicao=" + this.valorTotalExposicao + ", valorTotalECL=" + this.valorTotalECL + ", valorTotalProvisao=" + this.valorTotalProvisao + ", instrumentosEstagio1=" + this.instrumentosEstagio1 + ", instrumentosEstagio2=" + this.instrumentosEstagio2 + ", instrumentosEstagio3=" + this.instrumentosEstagio3 + ", valorECLEstagio1=" + this.valorECLEstagio1 + ", valorECLEstagio2=" + this.valorECLEstagio2 + ", valorECLEstagio3=" + this.valorECLEstagio3 + ", hedgesAtivos=" + this.hedgesAtivos + ", valorHedgeTotal=" + this.valorHedgeTotal + ", efetividadeMedia=" + this.efetividadeMedia + ", usuarioGeracao=" + this.usuarioGeracao + ", dataGeracao=" + this.dataGeracao + ", dataConclusao=" + this.dataConclusao + ", tempoProcessamento=" + this.tempoProcessamento + ", parametrosGeracao=" + this.parametrosGeracao + ", resumoExecutivo=" + this.resumoExecutivo + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static RelatorioIFRS9.RelatorioIFRS9Builder builder() {
        return new RelatorioIFRS9.RelatorioIFRS9Builder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoRelatorio() {
        return this.codigoRelatorio;
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
    public TipoRelatorioIFRS9 getTipoRelatorio() {
        return this.tipoRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getPeriodoInicio() {
        return this.periodoInicio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getPeriodoFim() {
        return this.periodoFim;
    }

    @java.lang.SuppressWarnings("all")
    public FormatoRelatorio getFormato() {
        return this.formato;
    }

    @java.lang.SuppressWarnings("all")
    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTamanhoArquivo() {
        return this.tamanhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getHashArquivo() {
        return this.hashArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public StatusRelatorio getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTotalInstrumentos() {
        return this.totalInstrumentos;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotalExposicao() {
        return this.valorTotalExposicao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotalECL() {
        return this.valorTotalECL;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotalProvisao() {
        return this.valorTotalProvisao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getInstrumentosEstagio1() {
        return this.instrumentosEstagio1;
    }

    @java.lang.SuppressWarnings("all")
    public Long getInstrumentosEstagio2() {
        return this.instrumentosEstagio2;
    }

    @java.lang.SuppressWarnings("all")
    public Long getInstrumentosEstagio3() {
        return this.instrumentosEstagio3;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorECLEstagio1() {
        return this.valorECLEstagio1;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorECLEstagio2() {
        return this.valorECLEstagio2;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorECLEstagio3() {
        return this.valorECLEstagio3;
    }

    @java.lang.SuppressWarnings("all")
    public Long getHedgesAtivos() {
        return this.hedgesAtivos;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorHedgeTotal() {
        return this.valorHedgeTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getEfetividadeMedia() {
        return this.efetividadeMedia;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioGeracao() {
        return this.usuarioGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTempoProcessamento() {
        return this.tempoProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getParametrosGeracao() {
        return this.parametrosGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getResumoExecutivo() {
        return this.resumoExecutivo;
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
    public void setCodigoRelatorio(final String codigoRelatorio) {
        this.codigoRelatorio = codigoRelatorio;
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
    public void setTipoRelatorio(final TipoRelatorioIFRS9 tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodoInicio(final LocalDate periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodoFim(final LocalDate periodoFim) {
        this.periodoFim = periodoFim;
    }

    @java.lang.SuppressWarnings("all")
    public void setFormato(final FormatoRelatorio formato) {
        this.formato = formato;
    }

    @java.lang.SuppressWarnings("all")
    public void setCaminhoArquivo(final String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setTamanhoArquivo(final Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setHashArquivo(final String hashArquivo) {
        this.hashArquivo = hashArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRelatorio status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalInstrumentos(final Long totalInstrumentos) {
        this.totalInstrumentos = totalInstrumentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotalExposicao(final BigDecimal valorTotalExposicao) {
        this.valorTotalExposicao = valorTotalExposicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotalECL(final BigDecimal valorTotalECL) {
        this.valorTotalECL = valorTotalECL;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotalProvisao(final BigDecimal valorTotalProvisao) {
        this.valorTotalProvisao = valorTotalProvisao;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentosEstagio1(final Long instrumentosEstagio1) {
        this.instrumentosEstagio1 = instrumentosEstagio1;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentosEstagio2(final Long instrumentosEstagio2) {
        this.instrumentosEstagio2 = instrumentosEstagio2;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrumentosEstagio3(final Long instrumentosEstagio3) {
        this.instrumentosEstagio3 = instrumentosEstagio3;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorECLEstagio1(final BigDecimal valorECLEstagio1) {
        this.valorECLEstagio1 = valorECLEstagio1;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorECLEstagio2(final BigDecimal valorECLEstagio2) {
        this.valorECLEstagio2 = valorECLEstagio2;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorECLEstagio3(final BigDecimal valorECLEstagio3) {
        this.valorECLEstagio3 = valorECLEstagio3;
    }

    @java.lang.SuppressWarnings("all")
    public void setHedgesAtivos(final Long hedgesAtivos) {
        this.hedgesAtivos = hedgesAtivos;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorHedgeTotal(final BigDecimal valorHedgeTotal) {
        this.valorHedgeTotal = valorHedgeTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setEfetividadeMedia(final BigDecimal efetividadeMedia) {
        this.efetividadeMedia = efetividadeMedia;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioGeracao(final String usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConclusao(final LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTempoProcessamento(final Long tempoProcessamento) {
        this.tempoProcessamento = tempoProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setParametrosGeracao(final String parametrosGeracao) {
        this.parametrosGeracao = parametrosGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResumoExecutivo(final String resumoExecutivo) {
        this.resumoExecutivo = resumoExecutivo;
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
        if (!(o instanceof RelatorioIFRS9)) return false;
        final RelatorioIFRS9 other = (RelatorioIFRS9) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$totalInstrumentos = this.getTotalInstrumentos();
        final java.lang.Object other$totalInstrumentos = other.getTotalInstrumentos();
        if (this$totalInstrumentos == null ? other$totalInstrumentos != null : !this$totalInstrumentos.equals(other$totalInstrumentos)) return false;
        final java.lang.Object this$instrumentosEstagio1 = this.getInstrumentosEstagio1();
        final java.lang.Object other$instrumentosEstagio1 = other.getInstrumentosEstagio1();
        if (this$instrumentosEstagio1 == null ? other$instrumentosEstagio1 != null : !this$instrumentosEstagio1.equals(other$instrumentosEstagio1)) return false;
        final java.lang.Object this$instrumentosEstagio2 = this.getInstrumentosEstagio2();
        final java.lang.Object other$instrumentosEstagio2 = other.getInstrumentosEstagio2();
        if (this$instrumentosEstagio2 == null ? other$instrumentosEstagio2 != null : !this$instrumentosEstagio2.equals(other$instrumentosEstagio2)) return false;
        final java.lang.Object this$instrumentosEstagio3 = this.getInstrumentosEstagio3();
        final java.lang.Object other$instrumentosEstagio3 = other.getInstrumentosEstagio3();
        if (this$instrumentosEstagio3 == null ? other$instrumentosEstagio3 != null : !this$instrumentosEstagio3.equals(other$instrumentosEstagio3)) return false;
        final java.lang.Object this$hedgesAtivos = this.getHedgesAtivos();
        final java.lang.Object other$hedgesAtivos = other.getHedgesAtivos();
        if (this$hedgesAtivos == null ? other$hedgesAtivos != null : !this$hedgesAtivos.equals(other$hedgesAtivos)) return false;
        final java.lang.Object this$tempoProcessamento = this.getTempoProcessamento();
        final java.lang.Object other$tempoProcessamento = other.getTempoProcessamento();
        if (this$tempoProcessamento == null ? other$tempoProcessamento != null : !this$tempoProcessamento.equals(other$tempoProcessamento)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoRelatorio = this.getCodigoRelatorio();
        final java.lang.Object other$codigoRelatorio = other.getCodigoRelatorio();
        if (this$codigoRelatorio == null ? other$codigoRelatorio != null : !this$codigoRelatorio.equals(other$codigoRelatorio)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoRelatorio = this.getTipoRelatorio();
        final java.lang.Object other$tipoRelatorio = other.getTipoRelatorio();
        if (this$tipoRelatorio == null ? other$tipoRelatorio != null : !this$tipoRelatorio.equals(other$tipoRelatorio)) return false;
        final java.lang.Object this$periodoInicio = this.getPeriodoInicio();
        final java.lang.Object other$periodoInicio = other.getPeriodoInicio();
        if (this$periodoInicio == null ? other$periodoInicio != null : !this$periodoInicio.equals(other$periodoInicio)) return false;
        final java.lang.Object this$periodoFim = this.getPeriodoFim();
        final java.lang.Object other$periodoFim = other.getPeriodoFim();
        if (this$periodoFim == null ? other$periodoFim != null : !this$periodoFim.equals(other$periodoFim)) return false;
        final java.lang.Object this$formato = this.getFormato();
        final java.lang.Object other$formato = other.getFormato();
        if (this$formato == null ? other$formato != null : !this$formato.equals(other$formato)) return false;
        final java.lang.Object this$caminhoArquivo = this.getCaminhoArquivo();
        final java.lang.Object other$caminhoArquivo = other.getCaminhoArquivo();
        if (this$caminhoArquivo == null ? other$caminhoArquivo != null : !this$caminhoArquivo.equals(other$caminhoArquivo)) return false;
        final java.lang.Object this$hashArquivo = this.getHashArquivo();
        final java.lang.Object other$hashArquivo = other.getHashArquivo();
        if (this$hashArquivo == null ? other$hashArquivo != null : !this$hashArquivo.equals(other$hashArquivo)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorTotalExposicao = this.getValorTotalExposicao();
        final java.lang.Object other$valorTotalExposicao = other.getValorTotalExposicao();
        if (this$valorTotalExposicao == null ? other$valorTotalExposicao != null : !this$valorTotalExposicao.equals(other$valorTotalExposicao)) return false;
        final java.lang.Object this$valorTotalECL = this.getValorTotalECL();
        final java.lang.Object other$valorTotalECL = other.getValorTotalECL();
        if (this$valorTotalECL == null ? other$valorTotalECL != null : !this$valorTotalECL.equals(other$valorTotalECL)) return false;
        final java.lang.Object this$valorTotalProvisao = this.getValorTotalProvisao();
        final java.lang.Object other$valorTotalProvisao = other.getValorTotalProvisao();
        if (this$valorTotalProvisao == null ? other$valorTotalProvisao != null : !this$valorTotalProvisao.equals(other$valorTotalProvisao)) return false;
        final java.lang.Object this$valorECLEstagio1 = this.getValorECLEstagio1();
        final java.lang.Object other$valorECLEstagio1 = other.getValorECLEstagio1();
        if (this$valorECLEstagio1 == null ? other$valorECLEstagio1 != null : !this$valorECLEstagio1.equals(other$valorECLEstagio1)) return false;
        final java.lang.Object this$valorECLEstagio2 = this.getValorECLEstagio2();
        final java.lang.Object other$valorECLEstagio2 = other.getValorECLEstagio2();
        if (this$valorECLEstagio2 == null ? other$valorECLEstagio2 != null : !this$valorECLEstagio2.equals(other$valorECLEstagio2)) return false;
        final java.lang.Object this$valorECLEstagio3 = this.getValorECLEstagio3();
        final java.lang.Object other$valorECLEstagio3 = other.getValorECLEstagio3();
        if (this$valorECLEstagio3 == null ? other$valorECLEstagio3 != null : !this$valorECLEstagio3.equals(other$valorECLEstagio3)) return false;
        final java.lang.Object this$valorHedgeTotal = this.getValorHedgeTotal();
        final java.lang.Object other$valorHedgeTotal = other.getValorHedgeTotal();
        if (this$valorHedgeTotal == null ? other$valorHedgeTotal != null : !this$valorHedgeTotal.equals(other$valorHedgeTotal)) return false;
        final java.lang.Object this$efetividadeMedia = this.getEfetividadeMedia();
        final java.lang.Object other$efetividadeMedia = other.getEfetividadeMedia();
        if (this$efetividadeMedia == null ? other$efetividadeMedia != null : !this$efetividadeMedia.equals(other$efetividadeMedia)) return false;
        final java.lang.Object this$usuarioGeracao = this.getUsuarioGeracao();
        final java.lang.Object other$usuarioGeracao = other.getUsuarioGeracao();
        if (this$usuarioGeracao == null ? other$usuarioGeracao != null : !this$usuarioGeracao.equals(other$usuarioGeracao)) return false;
        final java.lang.Object this$dataGeracao = this.getDataGeracao();
        final java.lang.Object other$dataGeracao = other.getDataGeracao();
        if (this$dataGeracao == null ? other$dataGeracao != null : !this$dataGeracao.equals(other$dataGeracao)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$parametrosGeracao = this.getParametrosGeracao();
        final java.lang.Object other$parametrosGeracao = other.getParametrosGeracao();
        if (this$parametrosGeracao == null ? other$parametrosGeracao != null : !this$parametrosGeracao.equals(other$parametrosGeracao)) return false;
        final java.lang.Object this$resumoExecutivo = this.getResumoExecutivo();
        final java.lang.Object other$resumoExecutivo = other.getResumoExecutivo();
        if (this$resumoExecutivo == null ? other$resumoExecutivo != null : !this$resumoExecutivo.equals(other$resumoExecutivo)) return false;
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
        return other instanceof RelatorioIFRS9;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tamanhoArquivo = this.getTamanhoArquivo();
        result = result * PRIME + ($tamanhoArquivo == null ? 43 : $tamanhoArquivo.hashCode());
        final java.lang.Object $totalInstrumentos = this.getTotalInstrumentos();
        result = result * PRIME + ($totalInstrumentos == null ? 43 : $totalInstrumentos.hashCode());
        final java.lang.Object $instrumentosEstagio1 = this.getInstrumentosEstagio1();
        result = result * PRIME + ($instrumentosEstagio1 == null ? 43 : $instrumentosEstagio1.hashCode());
        final java.lang.Object $instrumentosEstagio2 = this.getInstrumentosEstagio2();
        result = result * PRIME + ($instrumentosEstagio2 == null ? 43 : $instrumentosEstagio2.hashCode());
        final java.lang.Object $instrumentosEstagio3 = this.getInstrumentosEstagio3();
        result = result * PRIME + ($instrumentosEstagio3 == null ? 43 : $instrumentosEstagio3.hashCode());
        final java.lang.Object $hedgesAtivos = this.getHedgesAtivos();
        result = result * PRIME + ($hedgesAtivos == null ? 43 : $hedgesAtivos.hashCode());
        final java.lang.Object $tempoProcessamento = this.getTempoProcessamento();
        result = result * PRIME + ($tempoProcessamento == null ? 43 : $tempoProcessamento.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoRelatorio = this.getCodigoRelatorio();
        result = result * PRIME + ($codigoRelatorio == null ? 43 : $codigoRelatorio.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoRelatorio = this.getTipoRelatorio();
        result = result * PRIME + ($tipoRelatorio == null ? 43 : $tipoRelatorio.hashCode());
        final java.lang.Object $periodoInicio = this.getPeriodoInicio();
        result = result * PRIME + ($periodoInicio == null ? 43 : $periodoInicio.hashCode());
        final java.lang.Object $periodoFim = this.getPeriodoFim();
        result = result * PRIME + ($periodoFim == null ? 43 : $periodoFim.hashCode());
        final java.lang.Object $formato = this.getFormato();
        result = result * PRIME + ($formato == null ? 43 : $formato.hashCode());
        final java.lang.Object $caminhoArquivo = this.getCaminhoArquivo();
        result = result * PRIME + ($caminhoArquivo == null ? 43 : $caminhoArquivo.hashCode());
        final java.lang.Object $hashArquivo = this.getHashArquivo();
        result = result * PRIME + ($hashArquivo == null ? 43 : $hashArquivo.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorTotalExposicao = this.getValorTotalExposicao();
        result = result * PRIME + ($valorTotalExposicao == null ? 43 : $valorTotalExposicao.hashCode());
        final java.lang.Object $valorTotalECL = this.getValorTotalECL();
        result = result * PRIME + ($valorTotalECL == null ? 43 : $valorTotalECL.hashCode());
        final java.lang.Object $valorTotalProvisao = this.getValorTotalProvisao();
        result = result * PRIME + ($valorTotalProvisao == null ? 43 : $valorTotalProvisao.hashCode());
        final java.lang.Object $valorECLEstagio1 = this.getValorECLEstagio1();
        result = result * PRIME + ($valorECLEstagio1 == null ? 43 : $valorECLEstagio1.hashCode());
        final java.lang.Object $valorECLEstagio2 = this.getValorECLEstagio2();
        result = result * PRIME + ($valorECLEstagio2 == null ? 43 : $valorECLEstagio2.hashCode());
        final java.lang.Object $valorECLEstagio3 = this.getValorECLEstagio3();
        result = result * PRIME + ($valorECLEstagio3 == null ? 43 : $valorECLEstagio3.hashCode());
        final java.lang.Object $valorHedgeTotal = this.getValorHedgeTotal();
        result = result * PRIME + ($valorHedgeTotal == null ? 43 : $valorHedgeTotal.hashCode());
        final java.lang.Object $efetividadeMedia = this.getEfetividadeMedia();
        result = result * PRIME + ($efetividadeMedia == null ? 43 : $efetividadeMedia.hashCode());
        final java.lang.Object $usuarioGeracao = this.getUsuarioGeracao();
        result = result * PRIME + ($usuarioGeracao == null ? 43 : $usuarioGeracao.hashCode());
        final java.lang.Object $dataGeracao = this.getDataGeracao();
        result = result * PRIME + ($dataGeracao == null ? 43 : $dataGeracao.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $parametrosGeracao = this.getParametrosGeracao();
        result = result * PRIME + ($parametrosGeracao == null ? 43 : $parametrosGeracao.hashCode());
        final java.lang.Object $resumoExecutivo = this.getResumoExecutivo();
        result = result * PRIME + ($resumoExecutivo == null ? 43 : $resumoExecutivo.hashCode());
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
        return "RelatorioIFRS9(id=" + this.getId() + ", codigoRelatorio=" + this.getCodigoRelatorio() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoRelatorio=" + this.getTipoRelatorio() + ", periodoInicio=" + this.getPeriodoInicio() + ", periodoFim=" + this.getPeriodoFim() + ", formato=" + this.getFormato() + ", caminhoArquivo=" + this.getCaminhoArquivo() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", hashArquivo=" + this.getHashArquivo() + ", status=" + this.getStatus() + ", totalInstrumentos=" + this.getTotalInstrumentos() + ", valorTotalExposicao=" + this.getValorTotalExposicao() + ", valorTotalECL=" + this.getValorTotalECL() + ", valorTotalProvisao=" + this.getValorTotalProvisao() + ", instrumentosEstagio1=" + this.getInstrumentosEstagio1() + ", instrumentosEstagio2=" + this.getInstrumentosEstagio2() + ", instrumentosEstagio3=" + this.getInstrumentosEstagio3() + ", valorECLEstagio1=" + this.getValorECLEstagio1() + ", valorECLEstagio2=" + this.getValorECLEstagio2() + ", valorECLEstagio3=" + this.getValorECLEstagio3() + ", hedgesAtivos=" + this.getHedgesAtivos() + ", valorHedgeTotal=" + this.getValorHedgeTotal() + ", efetividadeMedia=" + this.getEfetividadeMedia() + ", usuarioGeracao=" + this.getUsuarioGeracao() + ", dataGeracao=" + this.getDataGeracao() + ", dataConclusao=" + this.getDataConclusao() + ", tempoProcessamento=" + this.getTempoProcessamento() + ", parametrosGeracao=" + this.getParametrosGeracao() + ", resumoExecutivo=" + this.getResumoExecutivo() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RelatorioIFRS9() {
    }

    @java.lang.SuppressWarnings("all")
    public RelatorioIFRS9(final Long id, final String codigoRelatorio, final String nome, final String descricao, final TipoRelatorioIFRS9 tipoRelatorio, final LocalDate periodoInicio, final LocalDate periodoFim, final FormatoRelatorio formato, final String caminhoArquivo, final Long tamanhoArquivo, final String hashArquivo, final StatusRelatorio status, final Long totalInstrumentos, final BigDecimal valorTotalExposicao, final BigDecimal valorTotalECL, final BigDecimal valorTotalProvisao, final Long instrumentosEstagio1, final Long instrumentosEstagio2, final Long instrumentosEstagio3, final BigDecimal valorECLEstagio1, final BigDecimal valorECLEstagio2, final BigDecimal valorECLEstagio3, final Long hedgesAtivos, final BigDecimal valorHedgeTotal, final BigDecimal efetividadeMedia, final String usuarioGeracao, final LocalDateTime dataGeracao, final LocalDateTime dataConclusao, final Long tempoProcessamento, final String parametrosGeracao, final String resumoExecutivo, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoRelatorio = codigoRelatorio;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoRelatorio = tipoRelatorio;
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.formato = formato;
        this.caminhoArquivo = caminhoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.hashArquivo = hashArquivo;
        this.status = status;
        this.totalInstrumentos = totalInstrumentos;
        this.valorTotalExposicao = valorTotalExposicao;
        this.valorTotalECL = valorTotalECL;
        this.valorTotalProvisao = valorTotalProvisao;
        this.instrumentosEstagio1 = instrumentosEstagio1;
        this.instrumentosEstagio2 = instrumentosEstagio2;
        this.instrumentosEstagio3 = instrumentosEstagio3;
        this.valorECLEstagio1 = valorECLEstagio1;
        this.valorECLEstagio2 = valorECLEstagio2;
        this.valorECLEstagio3 = valorECLEstagio3;
        this.hedgesAtivos = hedgesAtivos;
        this.valorHedgeTotal = valorHedgeTotal;
        this.efetividadeMedia = efetividadeMedia;
        this.usuarioGeracao = usuarioGeracao;
        this.dataGeracao = dataGeracao;
        this.dataConclusao = dataConclusao;
        this.tempoProcessamento = tempoProcessamento;
        this.parametrosGeracao = parametrosGeracao;
        this.resumoExecutivo = resumoExecutivo;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
