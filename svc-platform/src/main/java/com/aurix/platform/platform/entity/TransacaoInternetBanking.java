package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma transação realizada via Internet Banking
 * 
 * Registra todas as transações feitas pelo cliente no canal web
 */
@Entity
@Table(name = "transacoes_internet_banking", schema = "aurix")
public class TransacaoInternetBanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transacao_id", unique = true, nullable = false, length = 100)
    private String transacaoId;
    @Column(name = "sessao_id", nullable = false, length = 100)
    private String sessaoId;
    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;
    @Column(name = "conta_origem", nullable = false, length = 50)
    private String contaOrigem;
    @Column(name = "conta_destino")
    private String contaDestino;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipoTransacao;
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;
    @Column(name = "taxa", precision = 15, scale = 2)
    private BigDecimal taxa;
    @Column(name = "valor_total", precision = 15, scale = 2)
    private BigDecimal valorTotal;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTransacao status;
    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao;
    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;
    @Column(name = "data_liquidacao")
    private LocalDateTime dataLiquidacao;
    @Column(name = "protocolo", length = 100)
    private String protocolo;
    @Column(name = "numero_autorizacao", length = 100)
    private String numeroAutorizacao;
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    @Column(name = "device_fingerprint", length = 200)
    private String deviceFingerprint;
    @Column(name = "geolocalizacao", length = 200)
    private String geolocalizacao;
    @Column(name = "mfa_verificado", nullable = false)
    private Boolean mfaVerificado;
    @Column(name = "biometrico_verificado")
    private Boolean biometricoVerificado;
    @Column(name = "risco_score")
    private Double riscoScore;
    @Column(name = "fraude_detectada")
    private Boolean fraudeDetectada;
    @Column(name = "motivo_rejeicao", length = 500)
    private String motivoRejeicao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    @Column(name = "versao_controle", nullable = false)
    @Version
    private Long versaoControle;


    /**
     * Tipo de transação
     */
    public enum TipoTransacao {
        TRANSFERENCIA_TED,  // TED
        TRANSFERENCIA_DOC,  // DOC
        TRANSFERENCIA_PIX,  // PIX
        PAGAMENTO_BOLETO,  // Pagamento de boleto
        PAGAMENTO_CONTA,  // Pagamento de conta
        PAGAMENTO_CARTAO,  // Pagamento de cartão
        INVESTIMENTO,  // Investimento
        APLICACAO,  // Aplicação
        RESGATE,  // Resgate
        EMPRESTIMO,  // Empréstimo
        FINANCIAMENTO,  // Financiamento
        RECARGA_CELULAR,  // Recarga de celular
        RECARGA_CARTAO,  // Recarga de cartão
        OUTROS // Outros
        ;
    }


    /**
     * Status da transação
     */
    public enum StatusTransacao {
        PENDENTE,  // Pendente de processamento
        PROCESSANDO,  // Em processamento
        PROCESSADA,  // Processada com sucesso
        LIQUIDADA,  // Liquidada
        REJEITADA,  // Rejeitada
        CANCELADA,  // Cancelada
        FALHOU,  // Falhou no processamento
        SUSPENSA // Suspensa para análise
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class TransacaoInternetBankingBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String transacaoId;
        @java.lang.SuppressWarnings("all")
        private String sessaoId;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String contaOrigem;
        @java.lang.SuppressWarnings("all")
        private String contaDestino;
        @java.lang.SuppressWarnings("all")
        private TipoTransacao tipoTransacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxa;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTotal;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private StatusTransacao status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataTransacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataProcessamento;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataLiquidacao;
        @java.lang.SuppressWarnings("all")
        private String protocolo;
        @java.lang.SuppressWarnings("all")
        private String numeroAutorizacao;
        @java.lang.SuppressWarnings("all")
        private String ipAddress;
        @java.lang.SuppressWarnings("all")
        private String userAgent;
        @java.lang.SuppressWarnings("all")
        private String deviceFingerprint;
        @java.lang.SuppressWarnings("all")
        private String geolocalizacao;
        @java.lang.SuppressWarnings("all")
        private Boolean mfaVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean biometricoVerificado;
        @java.lang.SuppressWarnings("all")
        private Double riscoScore;
        @java.lang.SuppressWarnings("all")
        private Boolean fraudeDetectada;
        @java.lang.SuppressWarnings("all")
        private String motivoRejeicao;
        @java.lang.SuppressWarnings("all")
        private String dadosAdicionais;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versaoControle;

        @java.lang.SuppressWarnings("all")
        TransacaoInternetBankingBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder transacaoId(final String transacaoId) {
            this.transacaoId = transacaoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder sessaoId(final String sessaoId) {
            this.sessaoId = sessaoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder contaOrigem(final String contaOrigem) {
            this.contaOrigem = contaOrigem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder contaDestino(final String contaDestino) {
            this.contaDestino = contaDestino;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder tipoTransacao(final TipoTransacao tipoTransacao) {
            this.tipoTransacao = tipoTransacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder valor(final BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder taxa(final BigDecimal taxa) {
            this.taxa = taxa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder valorTotal(final BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder status(final StatusTransacao status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dataTransacao(final LocalDateTime dataTransacao) {
            this.dataTransacao = dataTransacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dataProcessamento(final LocalDateTime dataProcessamento) {
            this.dataProcessamento = dataProcessamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dataLiquidacao(final LocalDateTime dataLiquidacao) {
            this.dataLiquidacao = dataLiquidacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder protocolo(final String protocolo) {
            this.protocolo = protocolo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder numeroAutorizacao(final String numeroAutorizacao) {
            this.numeroAutorizacao = numeroAutorizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder deviceFingerprint(final String deviceFingerprint) {
            this.deviceFingerprint = deviceFingerprint;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder geolocalizacao(final String geolocalizacao) {
            this.geolocalizacao = geolocalizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder mfaVerificado(final Boolean mfaVerificado) {
            this.mfaVerificado = mfaVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder biometricoVerificado(final Boolean biometricoVerificado) {
            this.biometricoVerificado = biometricoVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder riscoScore(final Double riscoScore) {
            this.riscoScore = riscoScore;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder fraudeDetectada(final Boolean fraudeDetectada) {
            this.fraudeDetectada = fraudeDetectada;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder motivoRejeicao(final String motivoRejeicao) {
            this.motivoRejeicao = motivoRejeicao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dadosAdicionais(final String dadosAdicionais) {
            this.dadosAdicionais = dadosAdicionais;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking.TransacaoInternetBankingBuilder versaoControle(final Long versaoControle) {
            this.versaoControle = versaoControle;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TransacaoInternetBanking build() {
            return new TransacaoInternetBanking(this.id, this.transacaoId, this.sessaoId, this.clienteId, this.contaOrigem, this.contaDestino, this.tipoTransacao, this.valor, this.taxa, this.valorTotal, this.descricao, this.observacoes, this.status, this.dataTransacao, this.dataProcessamento, this.dataLiquidacao, this.protocolo, this.numeroAutorizacao, this.ipAddress, this.userAgent, this.deviceFingerprint, this.geolocalizacao, this.mfaVerificado, this.biometricoVerificado, this.riscoScore, this.fraudeDetectada, this.motivoRejeicao, this.dadosAdicionais, this.dataCriacao, this.dataAtualizacao, this.versaoControle);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TransacaoInternetBanking.TransacaoInternetBankingBuilder(id=" + this.id + ", transacaoId=" + this.transacaoId + ", sessaoId=" + this.sessaoId + ", clienteId=" + this.clienteId + ", contaOrigem=" + this.contaOrigem + ", contaDestino=" + this.contaDestino + ", tipoTransacao=" + this.tipoTransacao + ", valor=" + this.valor + ", taxa=" + this.taxa + ", valorTotal=" + this.valorTotal + ", descricao=" + this.descricao + ", observacoes=" + this.observacoes + ", status=" + this.status + ", dataTransacao=" + this.dataTransacao + ", dataProcessamento=" + this.dataProcessamento + ", dataLiquidacao=" + this.dataLiquidacao + ", protocolo=" + this.protocolo + ", numeroAutorizacao=" + this.numeroAutorizacao + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", deviceFingerprint=" + this.deviceFingerprint + ", geolocalizacao=" + this.geolocalizacao + ", mfaVerificado=" + this.mfaVerificado + ", biometricoVerificado=" + this.biometricoVerificado + ", riscoScore=" + this.riscoScore + ", fraudeDetectada=" + this.fraudeDetectada + ", motivoRejeicao=" + this.motivoRejeicao + ", dadosAdicionais=" + this.dadosAdicionais + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versaoControle=" + this.versaoControle + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TransacaoInternetBanking.TransacaoInternetBankingBuilder builder() {
        return new TransacaoInternetBanking.TransacaoInternetBankingBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getSessaoId() {
        return this.sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaOrigem() {
        return this.contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaDestino() {
        return this.contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxa() {
        return this.taxa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public StatusTransacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacao() {
        return this.dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLiquidacao() {
        return this.dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getProtocolo() {
        return this.protocolo;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroAutorizacao() {
        return this.numeroAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getIpAddress() {
        return this.ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceFingerprint() {
        return this.deviceFingerprint;
    }

    @java.lang.SuppressWarnings("all")
    public String getGeolocalizacao() {
        return this.geolocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getMfaVerificado() {
        return this.mfaVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBiometricoVerificado() {
        return this.biometricoVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Double getRiscoScore() {
        return this.riscoScore;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getFraudeDetectada() {
        return this.fraudeDetectada;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoRejeicao() {
        return this.motivoRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
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
    public Long getVersaoControle() {
        return this.versaoControle;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final String transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSessaoId(final String sessaoId) {
        this.sessaoId = sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final String contaDestino) {
        this.contaDestino = contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoTransacao(final TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxa(final BigDecimal taxa) {
        this.taxa = taxa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusTransacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTransacao(final LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLiquidacao(final LocalDateTime dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setProtocolo(final String protocolo) {
        this.protocolo = protocolo;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroAutorizacao(final String numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceFingerprint(final String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    @java.lang.SuppressWarnings("all")
    public void setGeolocalizacao(final String geolocalizacao) {
        this.geolocalizacao = geolocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMfaVerificado(final Boolean mfaVerificado) {
        this.mfaVerificado = mfaVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setBiometricoVerificado(final Boolean biometricoVerificado) {
        this.biometricoVerificado = biometricoVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiscoScore(final Double riscoScore) {
        this.riscoScore = riscoScore;
    }

    @java.lang.SuppressWarnings("all")
    public void setFraudeDetectada(final Boolean fraudeDetectada) {
        this.fraudeDetectada = fraudeDetectada;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoRejeicao(final String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
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
    public void setVersaoControle(final Long versaoControle) {
        this.versaoControle = versaoControle;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TransacaoInternetBanking)) return false;
        final TransacaoInternetBanking other = (TransacaoInternetBanking) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$mfaVerificado = this.getMfaVerificado();
        final java.lang.Object other$mfaVerificado = other.getMfaVerificado();
        if (this$mfaVerificado == null ? other$mfaVerificado != null : !this$mfaVerificado.equals(other$mfaVerificado)) return false;
        final java.lang.Object this$biometricoVerificado = this.getBiometricoVerificado();
        final java.lang.Object other$biometricoVerificado = other.getBiometricoVerificado();
        if (this$biometricoVerificado == null ? other$biometricoVerificado != null : !this$biometricoVerificado.equals(other$biometricoVerificado)) return false;
        final java.lang.Object this$riscoScore = this.getRiscoScore();
        final java.lang.Object other$riscoScore = other.getRiscoScore();
        if (this$riscoScore == null ? other$riscoScore != null : !this$riscoScore.equals(other$riscoScore)) return false;
        final java.lang.Object this$fraudeDetectada = this.getFraudeDetectada();
        final java.lang.Object other$fraudeDetectada = other.getFraudeDetectada();
        if (this$fraudeDetectada == null ? other$fraudeDetectada != null : !this$fraudeDetectada.equals(other$fraudeDetectada)) return false;
        final java.lang.Object this$versaoControle = this.getVersaoControle();
        final java.lang.Object other$versaoControle = other.getVersaoControle();
        if (this$versaoControle == null ? other$versaoControle != null : !this$versaoControle.equals(other$versaoControle)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$sessaoId = this.getSessaoId();
        final java.lang.Object other$sessaoId = other.getSessaoId();
        if (this$sessaoId == null ? other$sessaoId != null : !this$sessaoId.equals(other$sessaoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$contaOrigem = this.getContaOrigem();
        final java.lang.Object other$contaOrigem = other.getContaOrigem();
        if (this$contaOrigem == null ? other$contaOrigem != null : !this$contaOrigem.equals(other$contaOrigem)) return false;
        final java.lang.Object this$contaDestino = this.getContaDestino();
        final java.lang.Object other$contaDestino = other.getContaDestino();
        if (this$contaDestino == null ? other$contaDestino != null : !this$contaDestino.equals(other$contaDestino)) return false;
        final java.lang.Object this$tipoTransacao = this.getTipoTransacao();
        final java.lang.Object other$tipoTransacao = other.getTipoTransacao();
        if (this$tipoTransacao == null ? other$tipoTransacao != null : !this$tipoTransacao.equals(other$tipoTransacao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$taxa = this.getTaxa();
        final java.lang.Object other$taxa = other.getTaxa();
        if (this$taxa == null ? other$taxa != null : !this$taxa.equals(other$taxa)) return false;
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataTransacao = this.getDataTransacao();
        final java.lang.Object other$dataTransacao = other.getDataTransacao();
        if (this$dataTransacao == null ? other$dataTransacao != null : !this$dataTransacao.equals(other$dataTransacao)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataLiquidacao = this.getDataLiquidacao();
        final java.lang.Object other$dataLiquidacao = other.getDataLiquidacao();
        if (this$dataLiquidacao == null ? other$dataLiquidacao != null : !this$dataLiquidacao.equals(other$dataLiquidacao)) return false;
        final java.lang.Object this$protocolo = this.getProtocolo();
        final java.lang.Object other$protocolo = other.getProtocolo();
        if (this$protocolo == null ? other$protocolo != null : !this$protocolo.equals(other$protocolo)) return false;
        final java.lang.Object this$numeroAutorizacao = this.getNumeroAutorizacao();
        final java.lang.Object other$numeroAutorizacao = other.getNumeroAutorizacao();
        if (this$numeroAutorizacao == null ? other$numeroAutorizacao != null : !this$numeroAutorizacao.equals(other$numeroAutorizacao)) return false;
        final java.lang.Object this$ipAddress = this.getIpAddress();
        final java.lang.Object other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$deviceFingerprint = this.getDeviceFingerprint();
        final java.lang.Object other$deviceFingerprint = other.getDeviceFingerprint();
        if (this$deviceFingerprint == null ? other$deviceFingerprint != null : !this$deviceFingerprint.equals(other$deviceFingerprint)) return false;
        final java.lang.Object this$geolocalizacao = this.getGeolocalizacao();
        final java.lang.Object other$geolocalizacao = other.getGeolocalizacao();
        if (this$geolocalizacao == null ? other$geolocalizacao != null : !this$geolocalizacao.equals(other$geolocalizacao)) return false;
        final java.lang.Object this$motivoRejeicao = this.getMotivoRejeicao();
        final java.lang.Object other$motivoRejeicao = other.getMotivoRejeicao();
        if (this$motivoRejeicao == null ? other$motivoRejeicao != null : !this$motivoRejeicao.equals(other$motivoRejeicao)) return false;
        final java.lang.Object this$dadosAdicionais = this.getDadosAdicionais();
        final java.lang.Object other$dadosAdicionais = other.getDadosAdicionais();
        if (this$dadosAdicionais == null ? other$dadosAdicionais != null : !this$dadosAdicionais.equals(other$dadosAdicionais)) return false;
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
        return other instanceof TransacaoInternetBanking;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $mfaVerificado = this.getMfaVerificado();
        result = result * PRIME + ($mfaVerificado == null ? 43 : $mfaVerificado.hashCode());
        final java.lang.Object $biometricoVerificado = this.getBiometricoVerificado();
        result = result * PRIME + ($biometricoVerificado == null ? 43 : $biometricoVerificado.hashCode());
        final java.lang.Object $riscoScore = this.getRiscoScore();
        result = result * PRIME + ($riscoScore == null ? 43 : $riscoScore.hashCode());
        final java.lang.Object $fraudeDetectada = this.getFraudeDetectada();
        result = result * PRIME + ($fraudeDetectada == null ? 43 : $fraudeDetectada.hashCode());
        final java.lang.Object $versaoControle = this.getVersaoControle();
        result = result * PRIME + ($versaoControle == null ? 43 : $versaoControle.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $sessaoId = this.getSessaoId();
        result = result * PRIME + ($sessaoId == null ? 43 : $sessaoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $contaOrigem = this.getContaOrigem();
        result = result * PRIME + ($contaOrigem == null ? 43 : $contaOrigem.hashCode());
        final java.lang.Object $contaDestino = this.getContaDestino();
        result = result * PRIME + ($contaDestino == null ? 43 : $contaDestino.hashCode());
        final java.lang.Object $tipoTransacao = this.getTipoTransacao();
        result = result * PRIME + ($tipoTransacao == null ? 43 : $tipoTransacao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $taxa = this.getTaxa();
        result = result * PRIME + ($taxa == null ? 43 : $taxa.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataTransacao = this.getDataTransacao();
        result = result * PRIME + ($dataTransacao == null ? 43 : $dataTransacao.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataLiquidacao = this.getDataLiquidacao();
        result = result * PRIME + ($dataLiquidacao == null ? 43 : $dataLiquidacao.hashCode());
        final java.lang.Object $protocolo = this.getProtocolo();
        result = result * PRIME + ($protocolo == null ? 43 : $protocolo.hashCode());
        final java.lang.Object $numeroAutorizacao = this.getNumeroAutorizacao();
        result = result * PRIME + ($numeroAutorizacao == null ? 43 : $numeroAutorizacao.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $deviceFingerprint = this.getDeviceFingerprint();
        result = result * PRIME + ($deviceFingerprint == null ? 43 : $deviceFingerprint.hashCode());
        final java.lang.Object $geolocalizacao = this.getGeolocalizacao();
        result = result * PRIME + ($geolocalizacao == null ? 43 : $geolocalizacao.hashCode());
        final java.lang.Object $motivoRejeicao = this.getMotivoRejeicao();
        result = result * PRIME + ($motivoRejeicao == null ? 43 : $motivoRejeicao.hashCode());
        final java.lang.Object $dadosAdicionais = this.getDadosAdicionais();
        result = result * PRIME + ($dadosAdicionais == null ? 43 : $dadosAdicionais.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TransacaoInternetBanking(id=" + this.getId() + ", transacaoId=" + this.getTransacaoId() + ", sessaoId=" + this.getSessaoId() + ", clienteId=" + this.getClienteId() + ", contaOrigem=" + this.getContaOrigem() + ", contaDestino=" + this.getContaDestino() + ", tipoTransacao=" + this.getTipoTransacao() + ", valor=" + this.getValor() + ", taxa=" + this.getTaxa() + ", valorTotal=" + this.getValorTotal() + ", descricao=" + this.getDescricao() + ", observacoes=" + this.getObservacoes() + ", status=" + this.getStatus() + ", dataTransacao=" + this.getDataTransacao() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataLiquidacao=" + this.getDataLiquidacao() + ", protocolo=" + this.getProtocolo() + ", numeroAutorizacao=" + this.getNumeroAutorizacao() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", deviceFingerprint=" + this.getDeviceFingerprint() + ", geolocalizacao=" + this.getGeolocalizacao() + ", mfaVerificado=" + this.getMfaVerificado() + ", biometricoVerificado=" + this.getBiometricoVerificado() + ", riscoScore=" + this.getRiscoScore() + ", fraudeDetectada=" + this.getFraudeDetectada() + ", motivoRejeicao=" + this.getMotivoRejeicao() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versaoControle=" + this.getVersaoControle() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoInternetBanking() {
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoInternetBanking(final Long id, final String transacaoId, final String sessaoId, final String clienteId, final String contaOrigem, final String contaDestino, final TipoTransacao tipoTransacao, final BigDecimal valor, final BigDecimal taxa, final BigDecimal valorTotal, final String descricao, final String observacoes, final StatusTransacao status, final LocalDateTime dataTransacao, final LocalDateTime dataProcessamento, final LocalDateTime dataLiquidacao, final String protocolo, final String numeroAutorizacao, final String ipAddress, final String userAgent, final String deviceFingerprint, final String geolocalizacao, final Boolean mfaVerificado, final Boolean biometricoVerificado, final Double riscoScore, final Boolean fraudeDetectada, final String motivoRejeicao, final String dadosAdicionais, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versaoControle) {
        this.id = id;
        this.transacaoId = transacaoId;
        this.sessaoId = sessaoId;
        this.clienteId = clienteId;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.taxa = taxa;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.status = status;
        this.dataTransacao = dataTransacao;
        this.dataProcessamento = dataProcessamento;
        this.dataLiquidacao = dataLiquidacao;
        this.protocolo = protocolo;
        this.numeroAutorizacao = numeroAutorizacao;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceFingerprint = deviceFingerprint;
        this.geolocalizacao = geolocalizacao;
        this.mfaVerificado = mfaVerificado;
        this.biometricoVerificado = biometricoVerificado;
        this.riscoScore = riscoScore;
        this.fraudeDetectada = fraudeDetectada;
        this.motivoRejeicao = motivoRejeicao;
        this.dadosAdicionais = dadosAdicionais;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versaoControle = versaoControle;
    }
}
