package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um arquivo SPED
 * 
 * Gerencia a geração e envio de arquivos SPED (Sistema Público de Escrituração Digital)
 */
@Entity
@Table(name = "sped", schema = "aurix")
public class SPED {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_sped", unique = true, nullable = false, length = 50)
    private String codigoSped;
    @Column(name = "nome_arquivo", nullable = false, length = 200)
    private String nomeArquivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sped", nullable = false)
    private TipoSPED tipoSped;
    @Column(name = "competencia", length = 7, nullable = false)
    private String competencia;
    @Column(name = "ano", nullable = false)
    private Integer ano;
    @Column(name = "mes", nullable = false)
    private Integer mes;
    @Column(name = "versao_leiaute", length = 10, nullable = false)
    private String versaoLeiaute;
    @Column(name = "codigo_versao", length = 10, nullable = false)
    private String codigoVersao;
    @Enumerated(EnumType.STRING)
    @Column(name = "ambiente", nullable = false)
    private AmbienteSPED ambiente;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSPED status;
    @Column(name = "data_inicio_periodo", nullable = false)
    private LocalDate dataInicioPeriodo;
    @Column(name = "data_fim_periodo", nullable = false)
    private LocalDate dataFimPeriodo;
    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;
    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;
    @Column(name = "data_retorno")
    private LocalDateTime dataRetorno;
    @Column(name = "protocolo_envio", length = 100)
    private String protocoloEnvio;
    @Column(name = "numero_recibo", length = 100)
    private String numeroRecibo;
    @Column(name = "caminho_arquivo", length = 500)
    private String caminhoArquivo;
    @Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;
    @Column(name = "hash_arquivo", length = 64)
    private String hashArquivo;
    @Column(name = "total_registros")
    private Long totalRegistros;
    @Column(name = "valor_total", precision = 15, scale = 2)
    private java.math.BigDecimal valorTotal;
    @Column(name = "mensagem_retorno", length = 1000)
    private String mensagemRetorno;
    @Column(name = "codigo_erro", length = 50)
    private String codigoErro;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @Column(name = "usuario_geracao", length = 100)
    private String usuarioGeracao;
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
     * Tipo de SPED
     */
    public enum TipoSPED {
        EFD,  // Escrituração Fiscal Digital
        ECD,  // Escrituração Contábil Digital
        EFD_PIS_COFINS,  // EFD PIS/COFINS
        EFD_ICMS_IPI,  // EFD ICMS/IPI
        EFD_REINF,  // EFD REINF
        OUTROS // Outros tipos
        ;
    }


    /**
     * Ambiente do SPED
     */
    public enum AmbienteSPED {
        PRODUCAO,  // Produção
        HOMOLOGACAO,  // Homologação
        TESTE // Teste
        ;
    }


    /**
     * Status do SPED
     */
    public enum StatusSPED {
        RASCUNHO,  // Em elaboração
        GERADO,  // Arquivo gerado
        ENVIADO,  // Enviado para Receita
        PROCESSADO,  // Processado pela Receita
        ACEITO,  // Aceito pela Receita
        REJEITADO,  // Rejeitado pela Receita
        ERRO,  // Erro na geração/envio
        CANCELADO // Cancelado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class SPEDBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoSped;
        @java.lang.SuppressWarnings("all")
        private String nomeArquivo;
        @java.lang.SuppressWarnings("all")
        private TipoSPED tipoSped;
        @java.lang.SuppressWarnings("all")
        private String competencia;
        @java.lang.SuppressWarnings("all")
        private Integer ano;
        @java.lang.SuppressWarnings("all")
        private Integer mes;
        @java.lang.SuppressWarnings("all")
        private String versaoLeiaute;
        @java.lang.SuppressWarnings("all")
        private String codigoVersao;
        @java.lang.SuppressWarnings("all")
        private AmbienteSPED ambiente;
        @java.lang.SuppressWarnings("all")
        private StatusSPED status;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataInicioPeriodo;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataFimPeriodo;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataGeracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataEnvio;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataRetorno;
        @java.lang.SuppressWarnings("all")
        private String protocoloEnvio;
        @java.lang.SuppressWarnings("all")
        private String numeroRecibo;
        @java.lang.SuppressWarnings("all")
        private String caminhoArquivo;
        @java.lang.SuppressWarnings("all")
        private Long tamanhoArquivo;
        @java.lang.SuppressWarnings("all")
        private String hashArquivo;
        @java.lang.SuppressWarnings("all")
        private Long totalRegistros;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valorTotal;
        @java.lang.SuppressWarnings("all")
        private String mensagemRetorno;
        @java.lang.SuppressWarnings("all")
        private String codigoErro;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String usuarioGeracao;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        SPEDBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder codigoSped(final String codigoSped) {
            this.codigoSped = codigoSped;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder nomeArquivo(final String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder tipoSped(final TipoSPED tipoSped) {
            this.tipoSped = tipoSped;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder competencia(final String competencia) {
            this.competencia = competencia;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder ano(final Integer ano) {
            this.ano = ano;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder mes(final Integer mes) {
            this.mes = mes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder versaoLeiaute(final String versaoLeiaute) {
            this.versaoLeiaute = versaoLeiaute;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder codigoVersao(final String codigoVersao) {
            this.codigoVersao = codigoVersao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder ambiente(final AmbienteSPED ambiente) {
            this.ambiente = ambiente;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder status(final StatusSPED status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataInicioPeriodo(final LocalDate dataInicioPeriodo) {
            this.dataInicioPeriodo = dataInicioPeriodo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataFimPeriodo(final LocalDate dataFimPeriodo) {
            this.dataFimPeriodo = dataFimPeriodo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataGeracao(final LocalDateTime dataGeracao) {
            this.dataGeracao = dataGeracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataEnvio(final LocalDateTime dataEnvio) {
            this.dataEnvio = dataEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataRetorno(final LocalDateTime dataRetorno) {
            this.dataRetorno = dataRetorno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder protocoloEnvio(final String protocoloEnvio) {
            this.protocoloEnvio = protocoloEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder numeroRecibo(final String numeroRecibo) {
            this.numeroRecibo = numeroRecibo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder caminhoArquivo(final String caminhoArquivo) {
            this.caminhoArquivo = caminhoArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder tamanhoArquivo(final Long tamanhoArquivo) {
            this.tamanhoArquivo = tamanhoArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder hashArquivo(final String hashArquivo) {
            this.hashArquivo = hashArquivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder totalRegistros(final Long totalRegistros) {
            this.totalRegistros = totalRegistros;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder valorTotal(final java.math.BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder mensagemRetorno(final String mensagemRetorno) {
            this.mensagemRetorno = mensagemRetorno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder codigoErro(final String codigoErro) {
            this.codigoErro = codigoErro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder usuarioGeracao(final String usuarioGeracao) {
            this.usuarioGeracao = usuarioGeracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SPED.SPEDBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SPED build() {
            return new SPED(this.id, this.codigoSped, this.nomeArquivo, this.tipoSped, this.competencia, this.ano, this.mes, this.versaoLeiaute, this.codigoVersao, this.ambiente, this.status, this.dataInicioPeriodo, this.dataFimPeriodo, this.dataGeracao, this.dataEnvio, this.dataRetorno, this.protocoloEnvio, this.numeroRecibo, this.caminhoArquivo, this.tamanhoArquivo, this.hashArquivo, this.totalRegistros, this.valorTotal, this.mensagemRetorno, this.codigoErro, this.observacoes, this.usuarioGeracao, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SPED.SPEDBuilder(id=" + this.id + ", codigoSped=" + this.codigoSped + ", nomeArquivo=" + this.nomeArquivo + ", tipoSped=" + this.tipoSped + ", competencia=" + this.competencia + ", ano=" + this.ano + ", mes=" + this.mes + ", versaoLeiaute=" + this.versaoLeiaute + ", codigoVersao=" + this.codigoVersao + ", ambiente=" + this.ambiente + ", status=" + this.status + ", dataInicioPeriodo=" + this.dataInicioPeriodo + ", dataFimPeriodo=" + this.dataFimPeriodo + ", dataGeracao=" + this.dataGeracao + ", dataEnvio=" + this.dataEnvio + ", dataRetorno=" + this.dataRetorno + ", protocoloEnvio=" + this.protocoloEnvio + ", numeroRecibo=" + this.numeroRecibo + ", caminhoArquivo=" + this.caminhoArquivo + ", tamanhoArquivo=" + this.tamanhoArquivo + ", hashArquivo=" + this.hashArquivo + ", totalRegistros=" + this.totalRegistros + ", valorTotal=" + this.valorTotal + ", mensagemRetorno=" + this.mensagemRetorno + ", codigoErro=" + this.codigoErro + ", observacoes=" + this.observacoes + ", usuarioGeracao=" + this.usuarioGeracao + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SPED.SPEDBuilder builder() {
        return new SPED.SPEDBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSped() {
        return this.codigoSped;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeArquivo() {
        return this.nomeArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public TipoSPED getTipoSped() {
        return this.tipoSped;
    }

    @java.lang.SuppressWarnings("all")
    public String getCompetencia() {
        return this.competencia;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getAno() {
        return this.ano;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMes() {
        return this.mes;
    }

    @java.lang.SuppressWarnings("all")
    public String getVersaoLeiaute() {
        return this.versaoLeiaute;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoVersao() {
        return this.codigoVersao;
    }

    @java.lang.SuppressWarnings("all")
    public AmbienteSPED getAmbiente() {
        return this.ambiente;
    }

    @java.lang.SuppressWarnings("all")
    public StatusSPED getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataInicioPeriodo() {
        return this.dataInicioPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataFimPeriodo() {
        return this.dataFimPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRetorno() {
        return this.dataRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public String getProtocoloEnvio() {
        return this.protocoloEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroRecibo() {
        return this.numeroRecibo;
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
    public Long getTotalRegistros() {
        return this.totalRegistros;
    }

    @java.lang.SuppressWarnings("all")
    public java.math.BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public String getMensagemRetorno() {
        return this.mensagemRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoErro() {
        return this.codigoErro;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioGeracao() {
        return this.usuarioGeracao;
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
    public void setCodigoSped(final String codigoSped) {
        this.codigoSped = codigoSped;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeArquivo(final String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoSped(final TipoSPED tipoSped) {
        this.tipoSped = tipoSped;
    }

    @java.lang.SuppressWarnings("all")
    public void setCompetencia(final String competencia) {
        this.competencia = competencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setAno(final Integer ano) {
        this.ano = ano;
    }

    @java.lang.SuppressWarnings("all")
    public void setMes(final Integer mes) {
        this.mes = mes;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoLeiaute(final String versaoLeiaute) {
        this.versaoLeiaute = versaoLeiaute;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoVersao(final String codigoVersao) {
        this.codigoVersao = codigoVersao;
    }

    @java.lang.SuppressWarnings("all")
    public void setAmbiente(final AmbienteSPED ambiente) {
        this.ambiente = ambiente;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSPED status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioPeriodo(final LocalDate dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFimPeriodo(final LocalDate dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEnvio(final LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRetorno(final LocalDateTime dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public void setProtocoloEnvio(final String protocoloEnvio) {
        this.protocoloEnvio = protocoloEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroRecibo(final String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
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
    public void setTotalRegistros(final Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final java.math.BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setMensagemRetorno(final String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoErro(final String codigoErro) {
        this.codigoErro = codigoErro;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioGeracao(final String usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
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
        if (!(o instanceof SPED)) return false;
        final SPED other = (SPED) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$ano = this.getAno();
        final java.lang.Object other$ano = other.getAno();
        if (this$ano == null ? other$ano != null : !this$ano.equals(other$ano)) return false;
        final java.lang.Object this$mes = this.getMes();
        final java.lang.Object other$mes = other.getMes();
        if (this$mes == null ? other$mes != null : !this$mes.equals(other$mes)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$totalRegistros = this.getTotalRegistros();
        final java.lang.Object other$totalRegistros = other.getTotalRegistros();
        if (this$totalRegistros == null ? other$totalRegistros != null : !this$totalRegistros.equals(other$totalRegistros)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$codigoSped = this.getCodigoSped();
        final java.lang.Object other$codigoSped = other.getCodigoSped();
        if (this$codigoSped == null ? other$codigoSped != null : !this$codigoSped.equals(other$codigoSped)) return false;
        final java.lang.Object this$nomeArquivo = this.getNomeArquivo();
        final java.lang.Object other$nomeArquivo = other.getNomeArquivo();
        if (this$nomeArquivo == null ? other$nomeArquivo != null : !this$nomeArquivo.equals(other$nomeArquivo)) return false;
        final java.lang.Object this$tipoSped = this.getTipoSped();
        final java.lang.Object other$tipoSped = other.getTipoSped();
        if (this$tipoSped == null ? other$tipoSped != null : !this$tipoSped.equals(other$tipoSped)) return false;
        final java.lang.Object this$competencia = this.getCompetencia();
        final java.lang.Object other$competencia = other.getCompetencia();
        if (this$competencia == null ? other$competencia != null : !this$competencia.equals(other$competencia)) return false;
        final java.lang.Object this$versaoLeiaute = this.getVersaoLeiaute();
        final java.lang.Object other$versaoLeiaute = other.getVersaoLeiaute();
        if (this$versaoLeiaute == null ? other$versaoLeiaute != null : !this$versaoLeiaute.equals(other$versaoLeiaute)) return false;
        final java.lang.Object this$codigoVersao = this.getCodigoVersao();
        final java.lang.Object other$codigoVersao = other.getCodigoVersao();
        if (this$codigoVersao == null ? other$codigoVersao != null : !this$codigoVersao.equals(other$codigoVersao)) return false;
        final java.lang.Object this$ambiente = this.getAmbiente();
        final java.lang.Object other$ambiente = other.getAmbiente();
        if (this$ambiente == null ? other$ambiente != null : !this$ambiente.equals(other$ambiente)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataInicioPeriodo = this.getDataInicioPeriodo();
        final java.lang.Object other$dataInicioPeriodo = other.getDataInicioPeriodo();
        if (this$dataInicioPeriodo == null ? other$dataInicioPeriodo != null : !this$dataInicioPeriodo.equals(other$dataInicioPeriodo)) return false;
        final java.lang.Object this$dataFimPeriodo = this.getDataFimPeriodo();
        final java.lang.Object other$dataFimPeriodo = other.getDataFimPeriodo();
        if (this$dataFimPeriodo == null ? other$dataFimPeriodo != null : !this$dataFimPeriodo.equals(other$dataFimPeriodo)) return false;
        final java.lang.Object this$dataGeracao = this.getDataGeracao();
        final java.lang.Object other$dataGeracao = other.getDataGeracao();
        if (this$dataGeracao == null ? other$dataGeracao != null : !this$dataGeracao.equals(other$dataGeracao)) return false;
        final java.lang.Object this$dataEnvio = this.getDataEnvio();
        final java.lang.Object other$dataEnvio = other.getDataEnvio();
        if (this$dataEnvio == null ? other$dataEnvio != null : !this$dataEnvio.equals(other$dataEnvio)) return false;
        final java.lang.Object this$dataRetorno = this.getDataRetorno();
        final java.lang.Object other$dataRetorno = other.getDataRetorno();
        if (this$dataRetorno == null ? other$dataRetorno != null : !this$dataRetorno.equals(other$dataRetorno)) return false;
        final java.lang.Object this$protocoloEnvio = this.getProtocoloEnvio();
        final java.lang.Object other$protocoloEnvio = other.getProtocoloEnvio();
        if (this$protocoloEnvio == null ? other$protocoloEnvio != null : !this$protocoloEnvio.equals(other$protocoloEnvio)) return false;
        final java.lang.Object this$numeroRecibo = this.getNumeroRecibo();
        final java.lang.Object other$numeroRecibo = other.getNumeroRecibo();
        if (this$numeroRecibo == null ? other$numeroRecibo != null : !this$numeroRecibo.equals(other$numeroRecibo)) return false;
        final java.lang.Object this$caminhoArquivo = this.getCaminhoArquivo();
        final java.lang.Object other$caminhoArquivo = other.getCaminhoArquivo();
        if (this$caminhoArquivo == null ? other$caminhoArquivo != null : !this$caminhoArquivo.equals(other$caminhoArquivo)) return false;
        final java.lang.Object this$hashArquivo = this.getHashArquivo();
        final java.lang.Object other$hashArquivo = other.getHashArquivo();
        if (this$hashArquivo == null ? other$hashArquivo != null : !this$hashArquivo.equals(other$hashArquivo)) return false;
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$mensagemRetorno = this.getMensagemRetorno();
        final java.lang.Object other$mensagemRetorno = other.getMensagemRetorno();
        if (this$mensagemRetorno == null ? other$mensagemRetorno != null : !this$mensagemRetorno.equals(other$mensagemRetorno)) return false;
        final java.lang.Object this$codigoErro = this.getCodigoErro();
        final java.lang.Object other$codigoErro = other.getCodigoErro();
        if (this$codigoErro == null ? other$codigoErro != null : !this$codigoErro.equals(other$codigoErro)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$usuarioGeracao = this.getUsuarioGeracao();
        final java.lang.Object other$usuarioGeracao = other.getUsuarioGeracao();
        if (this$usuarioGeracao == null ? other$usuarioGeracao != null : !this$usuarioGeracao.equals(other$usuarioGeracao)) return false;
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
        return other instanceof SPED;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $ano = this.getAno();
        result = result * PRIME + ($ano == null ? 43 : $ano.hashCode());
        final java.lang.Object $mes = this.getMes();
        result = result * PRIME + ($mes == null ? 43 : $mes.hashCode());
        final java.lang.Object $tamanhoArquivo = this.getTamanhoArquivo();
        result = result * PRIME + ($tamanhoArquivo == null ? 43 : $tamanhoArquivo.hashCode());
        final java.lang.Object $totalRegistros = this.getTotalRegistros();
        result = result * PRIME + ($totalRegistros == null ? 43 : $totalRegistros.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoSped = this.getCodigoSped();
        result = result * PRIME + ($codigoSped == null ? 43 : $codigoSped.hashCode());
        final java.lang.Object $nomeArquivo = this.getNomeArquivo();
        result = result * PRIME + ($nomeArquivo == null ? 43 : $nomeArquivo.hashCode());
        final java.lang.Object $tipoSped = this.getTipoSped();
        result = result * PRIME + ($tipoSped == null ? 43 : $tipoSped.hashCode());
        final java.lang.Object $competencia = this.getCompetencia();
        result = result * PRIME + ($competencia == null ? 43 : $competencia.hashCode());
        final java.lang.Object $versaoLeiaute = this.getVersaoLeiaute();
        result = result * PRIME + ($versaoLeiaute == null ? 43 : $versaoLeiaute.hashCode());
        final java.lang.Object $codigoVersao = this.getCodigoVersao();
        result = result * PRIME + ($codigoVersao == null ? 43 : $codigoVersao.hashCode());
        final java.lang.Object $ambiente = this.getAmbiente();
        result = result * PRIME + ($ambiente == null ? 43 : $ambiente.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataInicioPeriodo = this.getDataInicioPeriodo();
        result = result * PRIME + ($dataInicioPeriodo == null ? 43 : $dataInicioPeriodo.hashCode());
        final java.lang.Object $dataFimPeriodo = this.getDataFimPeriodo();
        result = result * PRIME + ($dataFimPeriodo == null ? 43 : $dataFimPeriodo.hashCode());
        final java.lang.Object $dataGeracao = this.getDataGeracao();
        result = result * PRIME + ($dataGeracao == null ? 43 : $dataGeracao.hashCode());
        final java.lang.Object $dataEnvio = this.getDataEnvio();
        result = result * PRIME + ($dataEnvio == null ? 43 : $dataEnvio.hashCode());
        final java.lang.Object $dataRetorno = this.getDataRetorno();
        result = result * PRIME + ($dataRetorno == null ? 43 : $dataRetorno.hashCode());
        final java.lang.Object $protocoloEnvio = this.getProtocoloEnvio();
        result = result * PRIME + ($protocoloEnvio == null ? 43 : $protocoloEnvio.hashCode());
        final java.lang.Object $numeroRecibo = this.getNumeroRecibo();
        result = result * PRIME + ($numeroRecibo == null ? 43 : $numeroRecibo.hashCode());
        final java.lang.Object $caminhoArquivo = this.getCaminhoArquivo();
        result = result * PRIME + ($caminhoArquivo == null ? 43 : $caminhoArquivo.hashCode());
        final java.lang.Object $hashArquivo = this.getHashArquivo();
        result = result * PRIME + ($hashArquivo == null ? 43 : $hashArquivo.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $mensagemRetorno = this.getMensagemRetorno();
        result = result * PRIME + ($mensagemRetorno == null ? 43 : $mensagemRetorno.hashCode());
        final java.lang.Object $codigoErro = this.getCodigoErro();
        result = result * PRIME + ($codigoErro == null ? 43 : $codigoErro.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $usuarioGeracao = this.getUsuarioGeracao();
        result = result * PRIME + ($usuarioGeracao == null ? 43 : $usuarioGeracao.hashCode());
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
        return "SPED(id=" + this.getId() + ", codigoSped=" + this.getCodigoSped() + ", nomeArquivo=" + this.getNomeArquivo() + ", tipoSped=" + this.getTipoSped() + ", competencia=" + this.getCompetencia() + ", ano=" + this.getAno() + ", mes=" + this.getMes() + ", versaoLeiaute=" + this.getVersaoLeiaute() + ", codigoVersao=" + this.getCodigoVersao() + ", ambiente=" + this.getAmbiente() + ", status=" + this.getStatus() + ", dataInicioPeriodo=" + this.getDataInicioPeriodo() + ", dataFimPeriodo=" + this.getDataFimPeriodo() + ", dataGeracao=" + this.getDataGeracao() + ", dataEnvio=" + this.getDataEnvio() + ", dataRetorno=" + this.getDataRetorno() + ", protocoloEnvio=" + this.getProtocoloEnvio() + ", numeroRecibo=" + this.getNumeroRecibo() + ", caminhoArquivo=" + this.getCaminhoArquivo() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", hashArquivo=" + this.getHashArquivo() + ", totalRegistros=" + this.getTotalRegistros() + ", valorTotal=" + this.getValorTotal() + ", mensagemRetorno=" + this.getMensagemRetorno() + ", codigoErro=" + this.getCodigoErro() + ", observacoes=" + this.getObservacoes() + ", usuarioGeracao=" + this.getUsuarioGeracao() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SPED() {
    }

    @java.lang.SuppressWarnings("all")
    public SPED(final Long id, final String codigoSped, final String nomeArquivo, final TipoSPED tipoSped, final String competencia, final Integer ano, final Integer mes, final String versaoLeiaute, final String codigoVersao, final AmbienteSPED ambiente, final StatusSPED status, final LocalDate dataInicioPeriodo, final LocalDate dataFimPeriodo, final LocalDateTime dataGeracao, final LocalDateTime dataEnvio, final LocalDateTime dataRetorno, final String protocoloEnvio, final String numeroRecibo, final String caminhoArquivo, final Long tamanhoArquivo, final String hashArquivo, final Long totalRegistros, final java.math.BigDecimal valorTotal, final String mensagemRetorno, final String codigoErro, final String observacoes, final String usuarioGeracao, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoSped = codigoSped;
        this.nomeArquivo = nomeArquivo;
        this.tipoSped = tipoSped;
        this.competencia = competencia;
        this.ano = ano;
        this.mes = mes;
        this.versaoLeiaute = versaoLeiaute;
        this.codigoVersao = codigoVersao;
        this.ambiente = ambiente;
        this.status = status;
        this.dataInicioPeriodo = dataInicioPeriodo;
        this.dataFimPeriodo = dataFimPeriodo;
        this.dataGeracao = dataGeracao;
        this.dataEnvio = dataEnvio;
        this.dataRetorno = dataRetorno;
        this.protocoloEnvio = protocoloEnvio;
        this.numeroRecibo = numeroRecibo;
        this.caminhoArquivo = caminhoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.hashArquivo = hashArquivo;
        this.totalRegistros = totalRegistros;
        this.valorTotal = valorTotal;
        this.mensagemRetorno = mensagemRetorno;
        this.codigoErro = codigoErro;
        this.observacoes = observacoes;
        this.usuarioGeracao = usuarioGeracao;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
