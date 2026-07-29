package com.aurix.platform.cambio.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa relatórios do BACEN
 * 
 * Gerencia relatórios regulatórios obrigatórios e opcionais
 */
@Entity
@Table(name = "relatorios_bacen", schema = "aurix")
public class RelatorioBacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_relatorio", nullable = false, length = 20)
    private String codigoRelatorio;
    @Column(name = "nome_relatorio", nullable = false, length = 200)
    private String nomeRelatorio;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relatorio", nullable = false)
    private TipoRelatorio tipoRelatorio;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaRelatorio categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusRelatorio status;
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
    @Column(name = "data_envio")
    private LocalDate dataEnvio;
    @Column(name = "periodicidade", length = 50)
    private String periodicidade;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "arquivo_gerado", length = 500)
    private String arquivoGerado;
    @Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;
    @Column(name = "hash_arquivo", length = 100)
    private String hashArquivo;
    @Column(name = "protocolo_bacen", length = 100)
    private String protocoloBacen;
    @Column(name = "numero_sequencial")
    private Integer numeroSequencial;
    @Column(name = "versao_formato", length = 20)
    private String versaoFormato;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @Column(name = "erros_validacao", length = 2000)
    private String errosValidacao;
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
     * Tipo de relatório
     */
    public enum TipoRelatorio {
        OBRIGATORIO,  // Relatório obrigatório
        OPCIONAL,  // Relatório opcional
        EMERGENCIAL,  // Relatório emergencial
        PERIODICO,  // Relatório periódico
        EVENTUAL // Relatório eventual
        ;
    }


    /**
     * Categoria do relatório
     */
    public enum CategoriaRelatorio {
        CAMBIO,  // Câmbio
        CREDITO,  // Crédito
        DEPOSITO,  // Depósito
        LIQUIDEZ,  // Liquidez
        RISCO,  // Risco
        TARIFA,  // Tarifa
        PIX,  // PIX
        OPEN_BANKING,  // Open Banking
        LGPD,  // LGPD
        E_FINANCEIRA,  // E-Financeira (Receita Federal)
        SCR_CCS,  // Central de Riscos / CCS
        SPED,  // SPED (ECD, ECF, EFD-Reinf)
        BACEN_JUD,  // BACEN Jud (bloqueios judiciais)
        OUTROS // Outros
        ;
    }


    /**
     * Status do relatório
     */
    public enum StatusRelatorio {
        PENDENTE,  // Pendente de geração
        GERANDO,  // Em geração
        GERADO,  // Gerado
        VALIDANDO,  // Em validação
        VALIDADO,  // Validado
        ENVIANDO,  // Enviando
        ENVIADO,  // Enviado
        RECEBIDO,  // Recebido pelo BACEN
        REJEITADO,  // Rejeitado pelo BACEN
        CORRIGINDO,  // Corrigindo
        CORRIGIDO // Corrigido
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class RelatorioBacenBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoRelatorio;
        @java.lang.SuppressWarnings("all")
        private String nomeRelatorio;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoRelatorio tipoRelatorio;
        @java.lang.SuppressWarnings("all")
        private CategoriaRelatorio categoria;
        @java.lang.SuppressWarnings("all")
        private StatusRelatorio status;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataReferencia;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataVencimento;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataEnvio;
        @java.lang.SuppressWarnings("all")
        private String periodicidade;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private String arquivoGerado;
        @java.lang.SuppressWarnings("all")
        private Long tamanhoArquivo;
        @java.lang.SuppressWarnings("all")
        private String hashArquivo;
        @java.lang.SuppressWarnings("all")
        private String protocoloBacen;
        @java.lang.SuppressWarnings("all")
        private Integer numeroSequencial;
        @java.lang.SuppressWarnings("all")
        private String versaoFormato;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String errosValidacao;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        RelatorioBacenBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder codigoRelatorio(final String codigoRelatorio) {
            this.codigoRelatorio = codigoRelatorio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder nomeRelatorio(final String nomeRelatorio) {
            this.nomeRelatorio = nomeRelatorio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder tipoRelatorio(final TipoRelatorio tipoRelatorio) {
            this.tipoRelatorio = tipoRelatorio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder categoria(final CategoriaRelatorio categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder status(final StatusRelatorio status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder dataReferencia(final LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder dataVencimento(final LocalDate dataVencimento) {
            this.dataVencimento = dataVencimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder dataEnvio(final LocalDate dataEnvio) {
            this.dataEnvio = dataEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder periodicidade(final String periodicidade) {
            this.periodicidade = periodicidade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder responsavel(final String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder arquivoGerado(final String arquivoGerado) {
            this.arquivoGerado = arquivoGerado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder tamanhoArquivo(final Long tamanhoArquivo) {
            this.tamanhoArquivo = tamanhoArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder hashArquivo(final String hashArquivo) {
            this.hashArquivo = hashArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder protocoloBacen(final String protocoloBacen) {
            this.protocoloBacen = protocoloBacen;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder numeroSequencial(final Integer numeroSequencial) {
            this.numeroSequencial = numeroSequencial;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder versaoFormato(final String versaoFormato) {
            this.versaoFormato = versaoFormato;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder errosValidacao(final String errosValidacao) {
            this.errosValidacao = errosValidacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public RelatorioBacen.RelatorioBacenBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public RelatorioBacen build() {
            return new RelatorioBacen(this.id, this.codigoRelatorio, this.nomeRelatorio, this.descricao, this.tipoRelatorio, this.categoria, this.status, this.dataReferencia, this.dataVencimento, this.dataEnvio, this.periodicidade, this.responsavel, this.arquivoGerado, this.tamanhoArquivo, this.hashArquivo, this.protocoloBacen, this.numeroSequencial, this.versaoFormato, this.observacoes, this.errosValidacao, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "RelatorioBacen.RelatorioBacenBuilder(id=" + this.id + ", codigoRelatorio=" + this.codigoRelatorio + ", nomeRelatorio=" + this.nomeRelatorio + ", descricao=" + this.descricao + ", tipoRelatorio=" + this.tipoRelatorio + ", categoria=" + this.categoria + ", status=" + this.status + ", dataReferencia=" + this.dataReferencia + ", dataVencimento=" + this.dataVencimento + ", dataEnvio=" + this.dataEnvio + ", periodicidade=" + this.periodicidade + ", responsavel=" + this.responsavel + ", arquivoGerado=" + this.arquivoGerado + ", tamanhoArquivo=" + this.tamanhoArquivo + ", hashArquivo=" + this.hashArquivo + ", protocoloBacen=" + this.protocoloBacen + ", numeroSequencial=" + this.numeroSequencial + ", versaoFormato=" + this.versaoFormato + ", observacoes=" + this.observacoes + ", errosValidacao=" + this.errosValidacao + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static RelatorioBacen.RelatorioBacenBuilder builder() {
        return new RelatorioBacen.RelatorioBacenBuilder();
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
    public String getNomeRelatorio() {
        return this.nomeRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoRelatorio getTipoRelatorio() {
        return this.tipoRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaRelatorio getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public StatusRelatorio getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataEnvio() {
        return this.dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public String getPeriodicidade() {
        return this.periodicidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getResponsavel() {
        return this.responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getArquivoGerado() {
        return this.arquivoGerado;
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
    public String getProtocoloBacen() {
        return this.protocoloBacen;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNumeroSequencial() {
        return this.numeroSequencial;
    }

    @java.lang.SuppressWarnings("all")
    public String getVersaoFormato() {
        return this.versaoFormato;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getErrosValidacao() {
        return this.errosValidacao;
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
    public void setNomeRelatorio(final String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoRelatorio(final TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaRelatorio categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRelatorio status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataReferencia(final LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEnvio(final LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodicidade(final String periodicidade) {
        this.periodicidade = periodicidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponsavel(final String responsavel) {
        this.responsavel = responsavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setArquivoGerado(final String arquivoGerado) {
        this.arquivoGerado = arquivoGerado;
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
    public void setProtocoloBacen(final String protocoloBacen) {
        this.protocoloBacen = protocoloBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroSequencial(final Integer numeroSequencial) {
        this.numeroSequencial = numeroSequencial;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoFormato(final String versaoFormato) {
        this.versaoFormato = versaoFormato;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setErrosValidacao(final String errosValidacao) {
        this.errosValidacao = errosValidacao;
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
        if (!(o instanceof RelatorioBacen)) return false;
        final RelatorioBacen other = (RelatorioBacen) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$numeroSequencial = this.getNumeroSequencial();
        final java.lang.Object other$numeroSequencial = other.getNumeroSequencial();
        if (this$numeroSequencial == null ? other$numeroSequencial != null : !this$numeroSequencial.equals(other$numeroSequencial)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoRelatorio = this.getCodigoRelatorio();
        final java.lang.Object other$codigoRelatorio = other.getCodigoRelatorio();
        if (this$codigoRelatorio == null ? other$codigoRelatorio != null : !this$codigoRelatorio.equals(other$codigoRelatorio)) return false;
        final java.lang.Object this$nomeRelatorio = this.getNomeRelatorio();
        final java.lang.Object other$nomeRelatorio = other.getNomeRelatorio();
        if (this$nomeRelatorio == null ? other$nomeRelatorio != null : !this$nomeRelatorio.equals(other$nomeRelatorio)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoRelatorio = this.getTipoRelatorio();
        final java.lang.Object other$tipoRelatorio = other.getTipoRelatorio();
        if (this$tipoRelatorio == null ? other$tipoRelatorio != null : !this$tipoRelatorio.equals(other$tipoRelatorio)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataEnvio = this.getDataEnvio();
        final java.lang.Object other$dataEnvio = other.getDataEnvio();
        if (this$dataEnvio == null ? other$dataEnvio != null : !this$dataEnvio.equals(other$dataEnvio)) return false;
        final java.lang.Object this$periodicidade = this.getPeriodicidade();
        final java.lang.Object other$periodicidade = other.getPeriodicidade();
        if (this$periodicidade == null ? other$periodicidade != null : !this$periodicidade.equals(other$periodicidade)) return false;
        final java.lang.Object this$responsavel = this.getResponsavel();
        final java.lang.Object other$responsavel = other.getResponsavel();
        if (this$responsavel == null ? other$responsavel != null : !this$responsavel.equals(other$responsavel)) return false;
        final java.lang.Object this$arquivoGerado = this.getArquivoGerado();
        final java.lang.Object other$arquivoGerado = other.getArquivoGerado();
        if (this$arquivoGerado == null ? other$arquivoGerado != null : !this$arquivoGerado.equals(other$arquivoGerado)) return false;
        final java.lang.Object this$hashArquivo = this.getHashArquivo();
        final java.lang.Object other$hashArquivo = other.getHashArquivo();
        if (this$hashArquivo == null ? other$hashArquivo != null : !this$hashArquivo.equals(other$hashArquivo)) return false;
        final java.lang.Object this$protocoloBacen = this.getProtocoloBacen();
        final java.lang.Object other$protocoloBacen = other.getProtocoloBacen();
        if (this$protocoloBacen == null ? other$protocoloBacen != null : !this$protocoloBacen.equals(other$protocoloBacen)) return false;
        final java.lang.Object this$versaoFormato = this.getVersaoFormato();
        final java.lang.Object other$versaoFormato = other.getVersaoFormato();
        if (this$versaoFormato == null ? other$versaoFormato != null : !this$versaoFormato.equals(other$versaoFormato)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$errosValidacao = this.getErrosValidacao();
        final java.lang.Object other$errosValidacao = other.getErrosValidacao();
        if (this$errosValidacao == null ? other$errosValidacao != null : !this$errosValidacao.equals(other$errosValidacao)) return false;
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
        return other instanceof RelatorioBacen;
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
        final java.lang.Object $numeroSequencial = this.getNumeroSequencial();
        result = result * PRIME + ($numeroSequencial == null ? 43 : $numeroSequencial.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoRelatorio = this.getCodigoRelatorio();
        result = result * PRIME + ($codigoRelatorio == null ? 43 : $codigoRelatorio.hashCode());
        final java.lang.Object $nomeRelatorio = this.getNomeRelatorio();
        result = result * PRIME + ($nomeRelatorio == null ? 43 : $nomeRelatorio.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoRelatorio = this.getTipoRelatorio();
        result = result * PRIME + ($tipoRelatorio == null ? 43 : $tipoRelatorio.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataEnvio = this.getDataEnvio();
        result = result * PRIME + ($dataEnvio == null ? 43 : $dataEnvio.hashCode());
        final java.lang.Object $periodicidade = this.getPeriodicidade();
        result = result * PRIME + ($periodicidade == null ? 43 : $periodicidade.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $arquivoGerado = this.getArquivoGerado();
        result = result * PRIME + ($arquivoGerado == null ? 43 : $arquivoGerado.hashCode());
        final java.lang.Object $hashArquivo = this.getHashArquivo();
        result = result * PRIME + ($hashArquivo == null ? 43 : $hashArquivo.hashCode());
        final java.lang.Object $protocoloBacen = this.getProtocoloBacen();
        result = result * PRIME + ($protocoloBacen == null ? 43 : $protocoloBacen.hashCode());
        final java.lang.Object $versaoFormato = this.getVersaoFormato();
        result = result * PRIME + ($versaoFormato == null ? 43 : $versaoFormato.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $errosValidacao = this.getErrosValidacao();
        result = result * PRIME + ($errosValidacao == null ? 43 : $errosValidacao.hashCode());
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
        return "RelatorioBacen(id=" + this.getId() + ", codigoRelatorio=" + this.getCodigoRelatorio() + ", nomeRelatorio=" + this.getNomeRelatorio() + ", descricao=" + this.getDescricao() + ", tipoRelatorio=" + this.getTipoRelatorio() + ", categoria=" + this.getCategoria() + ", status=" + this.getStatus() + ", dataReferencia=" + this.getDataReferencia() + ", dataVencimento=" + this.getDataVencimento() + ", dataEnvio=" + this.getDataEnvio() + ", periodicidade=" + this.getPeriodicidade() + ", responsavel=" + this.getResponsavel() + ", arquivoGerado=" + this.getArquivoGerado() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", hashArquivo=" + this.getHashArquivo() + ", protocoloBacen=" + this.getProtocoloBacen() + ", numeroSequencial=" + this.getNumeroSequencial() + ", versaoFormato=" + this.getVersaoFormato() + ", observacoes=" + this.getObservacoes() + ", errosValidacao=" + this.getErrosValidacao() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RelatorioBacen() {
    }

    @java.lang.SuppressWarnings("all")
    public RelatorioBacen(final Long id, final String codigoRelatorio, final String nomeRelatorio, final String descricao, final TipoRelatorio tipoRelatorio, final CategoriaRelatorio categoria, final StatusRelatorio status, final LocalDate dataReferencia, final LocalDate dataVencimento, final LocalDate dataEnvio, final String periodicidade, final String responsavel, final String arquivoGerado, final Long tamanhoArquivo, final String hashArquivo, final String protocoloBacen, final Integer numeroSequencial, final String versaoFormato, final String observacoes, final String errosValidacao, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoRelatorio = codigoRelatorio;
        this.nomeRelatorio = nomeRelatorio;
        this.descricao = descricao;
        this.tipoRelatorio = tipoRelatorio;
        this.categoria = categoria;
        this.status = status;
        this.dataReferencia = dataReferencia;
        this.dataVencimento = dataVencimento;
        this.dataEnvio = dataEnvio;
        this.periodicidade = periodicidade;
        this.responsavel = responsavel;
        this.arquivoGerado = arquivoGerado;
        this.tamanhoArquivo = tamanhoArquivo;
        this.hashArquivo = hashArquivo;
        this.protocoloBacen = protocoloBacen;
        this.numeroSequencial = numeroSequencial;
        this.versaoFormato = versaoFormato;
        this.observacoes = observacoes;
        this.errosValidacao = errosValidacao;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
