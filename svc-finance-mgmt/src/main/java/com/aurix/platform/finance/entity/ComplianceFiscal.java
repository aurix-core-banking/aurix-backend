package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o compliance fiscal
 * 
 * Gerencia obrigações fiscais e prazos de cumprimento
 */
@Entity
@Table(name = "compliance_fiscal", schema = "aurix")
public class ComplianceFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_obrigacao", unique = true, nullable = false, length = 50)
    private String codigoObrigacao;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_obrigacao", nullable = false)
    private TipoObrigacao tipoObrigacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaObrigacao categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCompliance status;
    @Column(name = "competencia", length = 7, nullable = false)
    private String competencia;
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;
    @Column(name = "data_cumprimento")
    private LocalDate dataCumprimento;
    @Column(name = "prazo_antecedencia")
    private Integer prazoAntecedencia;
    @Enumerated(EnumType.STRING)
    @Column(name = "frequencia", nullable = false)
    private FrequenciaCompliance frequencia;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "sistema_origem", length = 100)
    private String sistemaOrigem;
    @Column(name = "documento_gerado", length = 200)
    private String documentoGerado;
    @Column(name = "protocolo_envio", length = 100)
    private String protocoloEnvio;
    @Column(name = "valor_multa", precision = 15, scale = 2)
    private java.math.BigDecimal valorMulta;
    @Column(name = "valor_juros", precision = 15, scale = 2)
    private java.math.BigDecimal valorJuros;
    @Column(name = "valor_total", precision = 15, scale = 2)
    private java.math.BigDecimal valorTotal;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @Column(name = "alertas_enviados")
    private Integer alertasEnviados;
    @Column(name = "data_ultimo_alerta")
    private LocalDateTime dataUltimoAlerta;
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
     * Tipo de obrigação
     */
    public enum TipoObrigacao {
        DECLARACAO,  // Declaração
        PAGAMENTO,  // Pagamento
        ENTREGA,  // Entrega de documento
        INFORMATIVO,  // Informativo
        RETENCAO,  // Retenção
        OUTROS // Outros
        ;
    }


    /**
     * Categoria da obrigação
     */
    public enum CategoriaObrigacao {
        IR_CSLL,  // IR/CSLL
        PIS_COFINS,  // PIS/COFINS
        IOF,  // IOF
        FGTS,  // FGTS
        INSS,  // INSS
        SPED,  // SPED
        EFD,  // EFD
        ECD,  // ECD
        DIRF,  // DIRF
        RAIS,  // RAIS
        CAGED,  // CAGED
        OUTROS // Outros
        ;
    }


    /**
     * Status do compliance
     */
    public enum StatusCompliance {
        PENDENTE,  // Pendente
        EM_ANDAMENTO,  // Em andamento
        CUMPRIDO,  // Cumprido
        VENCIDO,  // Vencido
        CANCELADO,  // Cancelado
        ISENTO // Isento
        ;
    }


    /**
     * Frequência da obrigação
     */
    public enum FrequenciaCompliance {
        DIARIA,  // Diária
        SEMANAL,  // Semanal
        MENSAL,  // Mensal
        BIMESTRAL,  // Bimestral
        TRIMESTRAL,  // Trimestral
        SEMESTRAL,  // Semestral
        ANUAL,  // Anual
        EVENTUAL // Eventual
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class ComplianceFiscalBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoObrigacao;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoObrigacao tipoObrigacao;
        @java.lang.SuppressWarnings("all")
        private CategoriaObrigacao categoria;
        @java.lang.SuppressWarnings("all")
        private StatusCompliance status;
        @java.lang.SuppressWarnings("all")
        private String competencia;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataVencimento;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataCumprimento;
        @java.lang.SuppressWarnings("all")
        private Integer prazoAntecedencia;
        @java.lang.SuppressWarnings("all")
        private FrequenciaCompliance frequencia;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private String sistemaOrigem;
        @java.lang.SuppressWarnings("all")
        private String documentoGerado;
        @java.lang.SuppressWarnings("all")
        private String protocoloEnvio;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valorMulta;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valorJuros;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valorTotal;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private Integer alertasEnviados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataUltimoAlerta;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        ComplianceFiscalBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder codigoObrigacao(final String codigoObrigacao) {
            this.codigoObrigacao = codigoObrigacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder tipoObrigacao(final TipoObrigacao tipoObrigacao) {
            this.tipoObrigacao = tipoObrigacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder categoria(final CategoriaObrigacao categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder status(final StatusCompliance status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder competencia(final String competencia) {
            this.competencia = competencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder dataVencimento(final LocalDate dataVencimento) {
            this.dataVencimento = dataVencimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder dataCumprimento(final LocalDate dataCumprimento) {
            this.dataCumprimento = dataCumprimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder prazoAntecedencia(final Integer prazoAntecedencia) {
            this.prazoAntecedencia = prazoAntecedencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder frequencia(final FrequenciaCompliance frequencia) {
            this.frequencia = frequencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder sistemaOrigem(final String sistemaOrigem) {
            this.sistemaOrigem = sistemaOrigem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder documentoGerado(final String documentoGerado) {
            this.documentoGerado = documentoGerado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder protocoloEnvio(final String protocoloEnvio) {
            this.protocoloEnvio = protocoloEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder valorMulta(final java.math.BigDecimal valorMulta) {
            this.valorMulta = valorMulta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder valorJuros(final java.math.BigDecimal valorJuros) {
            this.valorJuros = valorJuros;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder valorTotal(final java.math.BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder alertasEnviados(final Integer alertasEnviados) {
            this.alertasEnviados = alertasEnviados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder dataUltimoAlerta(final LocalDateTime dataUltimoAlerta) {
            this.dataUltimoAlerta = dataUltimoAlerta;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal.ComplianceFiscalBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ComplianceFiscal build() {
            return new ComplianceFiscal(this.id, this.codigoObrigacao, this.nome, this.descricao, this.tipoObrigacao, this.categoria, this.status, this.competencia, this.dataVencimento, this.dataCumprimento, this.prazoAntecedencia, this.frequencia, this.responsavel, this.sistemaOrigem, this.documentoGerado, this.protocoloEnvio, this.valorMulta, this.valorJuros, this.valorTotal, this.observacoes, this.alertasEnviados, this.dataUltimoAlerta, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ComplianceFiscal.ComplianceFiscalBuilder(id=" + this.id + ", codigoObrigacao=" + this.codigoObrigacao + ", nome=" + this.nome + ", descricao=" + this.descricao + ", tipoObrigacao=" + this.tipoObrigacao + ", categoria=" + this.categoria + ", status=" + this.status + ", competencia=" + this.competencia + ", dataVencimento=" + this.dataVencimento + ", dataCumprimento=" + this.dataCumprimento + ", prazoAntecedencia=" + this.prazoAntecedencia + ", frequencia=" + this.frequencia + ", responsavel=" + this.responsavel + ", sistemaOrigem=" + this.sistemaOrigem + ", documentoGerado=" + this.documentoGerado + ", protocoloEnvio=" + this.protocoloEnvio + ", valorMulta=" + this.valorMulta + ", valorJuros=" + this.valorJuros + ", valorTotal=" + this.valorTotal + ", observacoes=" + this.observacoes + ", alertasEnviados=" + this.alertasEnviados + ", dataUltimoAlerta=" + this.dataUltimoAlerta + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ComplianceFiscal.ComplianceFiscalBuilder builder() {
        return new ComplianceFiscal.ComplianceFiscalBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoObrigacao() {
        return this.codigoObrigacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ode());
        final java.lang.Object $dataCumprimento = this.getDataCumprimento();
        result = result * PRIME + ($dataCumprimento == null ? 43 : $dataCumprimento.hashCode());
        final java.lang.Object $frequencia = this.getFrequencia();
        result = result * PRIME + ($frequencia == null ? 43 : $frequencia.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $documentoGerado = this.getDocumentoGerado();
        result = result * PRIME + ($documentoGerado == null ? 43 : $documentoGerado.hashCode());
        final java.lang.Object $protocoloEnvio = this.getProtocoloEnvio();
        result = result * PRIME + ($protocoloEnvio == null ? 43 : $protocoloEnvio.hashCode());
        final java.lang.Object $valorMulta = this.getValorMulta();
        result = result * PRIME + ($valorMulta == null ? 43 : $valorMulta.hashCode());
        final java.lang.Object $valorJuros = this.getValorJuros();
        result = result * PRIME + ($valorJuros == null ? 43 : $valorJuros.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $dataUltimoAlerta = this.getDataUltimoAlerta();
        result = result * PRIME + ($dataUltimoAlerta == null ? 43 : $dataUltimoAlerta.hashCode());
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
        return "ComplianceFiscal(id=" + this.getId() + ", codigoObrigacao=" + this.getCodigoObrigacao() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoObrigacao=" + this.getTipoObrigacao() + ", categoria=" + this.getCategoria() + ", status=" + this.getStatus() + ", competencia=" + this.getCompetencia() + ", dataVencimento=" + this.getDataVencimento() + ", dataCumprimento=" + this.getDataCumprimento() + ", prazoAntecedencia=" + this.getPrazoAntecedencia() + ", frequencia=" + this.getFrequencia() + ", responsavel=" + this.getResponsavel() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", documentoGerado=" + this.getDocumentoGerado() + ", protocoloEnvio=" + this.getProtocoloEnvio() + ", valorMulta=" + this.getValorMulta() + ", valorJuros=" + this.getValorJuros() + ", valorTotal=" + this.getValorTotal() + ", observacoes=" + this.getObservacoes() + ", alertasEnviados=" + this.getAlertasEnviados() + ", dataUltimoAlerta=" + this.getDataUltimoAlerta() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ComplianceFiscal() {
    }

    @java.lang.SuppressWarnings("all")
    public ComplianceFiscal(final Long id, final String codigoObrigacao, final String nome, final String descricao, final TipoObrigacao tipoObrigacao, final CategoriaObrigacao categoria, final StatusCompliance status, final String competencia, final LocalDate dataVencimento, final LocalDate dataCumprimento, final Integer prazoAntecedencia, final FrequenciaCompliance frequencia, final String responsavel, final String sistemaOrigem, final String documentoGerado, final String protocoloEnvio, final java.math.BigDecimal valorMulta, final java.math.BigDecimal valorJuros, final java.math.BigDecimal valorTotal, final String observacoes, final Integer alertasEnviados, final LocalDateTime dataUltimoAlerta, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoObrigacao = codigoObrigacao;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoObrigacao = tipoObrigacao;
        this.categoria = categoria;
        this.status = status;
        this.competencia = competencia;
        this.dataVencimento = dataVencimento;
        this.dataCumprimento = dataCumprimento;
        this.prazoAntecedencia = prazoAntecedencia;
        this.frequencia = frequencia;
        this.responsavel = responsavel;
        this.sistemaOrigem = sistemaOrigem;
        this.documentoGerado = documentoGerado;
        this.protocoloEnvio = protocoloEnvio;
        this.valorMulta = valorMulta;
        this.valorJuros = valorJuros;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.alertasEnviados = alertasEnviados;
        this.dataUltimoAlerta = dataUltimoAlerta;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
