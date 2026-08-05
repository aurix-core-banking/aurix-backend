package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um log de acesso às APIs do Open Finance
 * 
 * Registra todas as requisições feitas às APIs para auditoria
 * e monitoramento de conformidade.
 */
@Entity
@Table(name = "logs_acesso_openfinance", schema = "aurix")
public class LogAcessoOpenFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "request_id", unique = true, nullable = false)
    private String requestId;
    @Column(name = "consent_id", nullable = false)
    private String consentId;
    @Column(name = "client_id", nullable = false)
    private String clientId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "endpoint", nullable = false)
    private String endpoint;
    @Column(name = "method", nullable = false)
    private String method;
    @Column(name = "http_status", nullable = false)
    private Integer httpStatus;
    @Column(name = "response_time_ms", nullable = false)
    private Long responseTimeMs;
    @Column(name = "request_size_bytes")
    private Long requestSizeBytes;
    @Column(name = "response_size_bytes")
    private Long responseSizeBytes;
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "geolocation")
    private String geolocation;
    @Column(name = "risk_score")
    private Double riskScore;
    @Column(name = "risk_level")
    private String riskLevel;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false)
    private TipoAcesso tipoAcesso;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_dados")
    private CategoriaDados categoriaDados;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_acessados", columnDefinition = "jsonb")
    private String dadosAcessados;
    @Column(name = "erro_detalhes")
    private String erroDetalhes;
    @Column(name = "rate_limit_remaining")
    private Integer rateLimitRemaining;
    @Column(name = "rate_limit_reset")
    private LocalDateTime rateLimitReset;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    @CreationTimestamp
    @Column(name = "data_acesso", nullable = false, updatable = false)
    private LocalDateTime dataAcesso;


    /**
     * Tipo de acesso
     */
    public enum TipoAcesso {
        CONTA, TRANSACAO, CARTAO_CREDITO, CARTAO_CREDITO_TRANSACAO, DADOS_PESSOAIS, DADOS_EMPRESARIAIS, FINANCIAMENTO, EMPRESTIMO, CONTA_CORRENTE_DESCOBERTA, FINANCIAMENTO_FATURA, RECURSOS;
    }


    /**
     * Categoria dos dados acessados
     */
    public enum CategoriaDados {
        IDENTIFICACAO, CONTATOS, FINANCEIRO, TRANSACIONAL, COMPORTAMENTAL, SENSIVEL;
    }


    @java.lang.SuppressWarnings("all")
    public static class LogAcessoOpenFinanceBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String requestId;
        @java.lang.SuppressWarnings("all")
        private String consentId;
        @java.lang.SuppressWarnings("all")
        private String clientId;
        @java.lang.SuppressWarnings("all")
        private Long userId;
        @java.lang.SuppressWarnings("all")
        private String endpoint;
        @java.lang.SuppressWarnings("all")
        private String method;
        @java.lang.SuppressWarnings("all")
        private Integer httpStatus;
        @java.lang.SuppressWarnings("all")
        private Long responseTimeMs;
        @java.lang.SuppressWarnings("all")
        private Long requestSizeBytes;
        @java.lang.SuppressWarnings("all")
        private Long responseSizeBytes;
        @java.lang.SuppressWarnings("all")
        private String ipAddress;
        @java.lang.SuppressWarnings("all")
        private String userAgent;
        @java.lang.SuppressWarnings("all")
        private String deviceId;
        @java.lang.SuppressWarnings("all")
        private String geolocation;
        @java.lang.SuppressWarnings("all")
        private Double riskScore;
        @java.lang.SuppressWarnings("all")
        private String riskLevel;
        @java.lang.SuppressWarnings("all")
        private TipoAcesso tipoAcesso;
        @java.lang.SuppressWarnings("all")
        private CategoriaDados categoriaDados;
        @java.lang.SuppressWarnings("all")
        private String dadosAcessados;
        @java.lang.SuppressWarnings("all")
        private String erroDetalhes;
        @java.lang.SuppressWarnings("all")
        private Integer rateLimitRemaining;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime rateLimitReset;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAcesso;

        @java.lang.SuppressWarnings("all")
        LogAcessoOpenFinanceBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder requestId(final String requestId) {
            this.requestId = requestId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder consentId(final String consentId) {
            this.consentId = consentId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder endpoint(final String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder method(final String method) {
            this.method = method;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder httpStatus(final Integer httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder responseTimeMs(final Long responseTimeMs) {
            this.responseTimeMs = responseTimeMs;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder requestSizeBytes(final Long requestSizeBytes) {
            this.requestSizeBytes = requestSizeBytes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder responseSizeBytes(final Long responseSizeBytes) {
            this.responseSizeBytes = responseSizeBytes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder deviceId(final String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder geolocation(final String geolocation) {
            this.geolocation = geolocation;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder riskScore(final Double riskScore) {
            this.riskScore = riskScore;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder riskLevel(final String riskLevel) {
            this.riskLevel = riskLevel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder tipoAcesso(final TipoAcesso tipoAcesso) {
            this.tipoAcesso = tipoAcesso;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder categoriaDados(final CategoriaDados categoriaDados) {
            this.categoriaDados = categoriaDados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder dadosAcessados(final String dadosAcessados) {
            this.dadosAcessados = dadosAcessados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder erroDetalhes(final String erroDetalhes) {
            this.erroDetalhes = erroDetalhes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder rateLimitRemaining(final Integer rateLimitRemaining) {
            this.rateLimitRemaining = rateLimitRemaining;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder rateLimitReset(final LocalDateTime rateLimitReset) {
            this.rateLimitReset = rateLimitReset;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder dataAcesso(final LocalDateTime dataAcesso) {
            this.dataAcesso = dataAcesso;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAcessoOpenFinance build() {
            return new LogAcessoOpenFinance(this.id, this.requestId, this.consentId, this.clientId, this.userId, this.endpoint, this.method, this.httpStatus, this.responseTimeMs, this.requestSizeBytes, this.responseSizeBytes, this.ipAddress, this.userAgent, this.deviceId, this.geolocation, this.riskScore, this.riskLevel, this.tipoAcesso, this.categoriaDados, this.dadosAcessados, this.erroDetalhes, this.rateLimitRemaining, this.rateLimitReset, this.metadata, this.dataAcesso);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder(id=" + this.id + ", requestId=" + this.requestId + ", consentId=" + this.consentId + ", clientId=" + this.clientId + ", userId=" + this.userId + ", endpoint=" + this.endpoint + ", method=" + this.method + ", httpStatus=" + this.httpStatus + ", responseTimeMs=" + this.responseTimeMs + ", requestSizeBytes=" + this.requestSizeBytes + ", responseSizeBytes=" + this.responseSizeBytes + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", deviceId=" + this.deviceId + ", geolocation=" + this.geolocation + ", riskScore=" + this.riskScore + ", riskLevel=" + this.riskLevel + ", tipoAcesso=" + this.tipoAcesso + ", categoriaDados=" + this.categoriaDados + ", dadosAcessados=" + this.dadosAcessados + ", erroDetalhes=" + this.erroDetalhes + ", rateLimitRemaining=" + this.rateLimitRemaining + ", rateLimitReset=" + this.rateLimitReset + ", metadata=" + this.metadata + ", dataAcesso=" + this.dataAcesso + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder builder() {
        return new LogAcessoOpenFinance.LogAcessoOpenFinanceBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getRequestId() {
        return this.requestId;
    }

    @java.lang.SuppressWarnings("all")
    public String getConsentId() {
        return this.consentId;
    }

    @java.lang.SuppressWarnings("all")
    public String getClientId() {
        return this.clientId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getUserId() {
        return this.userId;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndpoint() {
        return this.endpoint;
    }

    @java.lang.SuppressWarnings("all")
    public String getMethod() {
        return this.method;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getHttpStatus() {
        return this.httpStatus;
    }

    @java.lang.SuppressWarnings("all")
    public Long getResponseTimeMs() {
        return this.responseTimeMs;
    }

    @java.lang.SuppressWarnings("all")
    public Long getRequestSizeBytes() {
        return this.requestSizeBytes;
    }

    @java.lang.SuppressWarnings("all")
    public Long getResponseSizeBytes() {
        return this.responseSizeBytes;
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
    public String getDeviceId() {
        return this.deviceId;
    }

    @java.lang.SuppressWarnings("all")
    public String getGeolocation() {
        return this.geolocation;
    }

    @java.lang.SuppressWarnings("all")
    public Double getRiskScore() {
        return this.riskScore;
    }

    @java.lang.SuppressWarnings("all")
    public String getRiskLevel() {
        return this.riskLevel;
    }

    @java.lang.SuppressWarnings("all")
    public TipoAcesso getTipoAcesso() {
        return this.tipoAcesso;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaDados getCategoriaDados() {
        return this.categoriaDados;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAcessados() {
        return this.dadosAcessados;
    }

    @java.lang.SuppressWarnings("all")
    public String getErroDetalhes() {
        return this.erroDetalhes;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getRateLimitRemaining() {
        return this.rateLimitRemaining;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getRateLimitReset() {
        return this.rateLimitReset;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAcesso() {
        return this.dataAcesso;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    @java.lang.SuppressWarnings("all")
    public void setConsentId(final String consentId) {
        this.consentId = consentId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    @java.lang.SuppressWarnings("all")
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    @java.lang.SuppressWarnings("all")
    public void setMethod(final String method) {
        this.method = method;
    }

    @java.lang.SuppressWarnings("all")
    public void setHttpStatus(final Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponseTimeMs(final Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequestSizeBytes(final Long requestSizeBytes) {
        this.requestSizeBytes = requestSizeBytes;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponseSizeBytes(final Long responseSizeBytes) {
        this.responseSizeBytes = responseSizeBytes;
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
    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    @java.lang.SuppressWarnings("all")
    public void setGeolocation(final String geolocation) {
        this.geolocation = geolocation;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiskScore(final Double riskScore) {
        this.riskScore = riskScore;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiskLevel(final String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAcesso(final TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaDados(final CategoriaDados categoriaDados) {
        this.categoriaDados = categoriaDados;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAcessados(final String dadosAcessados) {
        this.dadosAcessados = dadosAcessados;
    }

    @java.lang.SuppressWarnings("all")
    public void setErroDetalhes(final String erroDetalhes) {
        this.erroDetalhes = erroDetalhes;
    }

    @java.lang.SuppressWarnings("all")
    public void setRateLimitRemaining(final Integer rateLimitRemaining) {
        this.rateLimitRemaining = rateLimitRemaining;
    }

    @java.lang.SuppressWarnings("all")
    public void setRateLimitReset(final LocalDateTime rateLimitReset) {
        this.rateLimitReset = rateLimitReset;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAcesso(final LocalDateTime dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LogAcessoOpenFinance)) return false;
        final LogAcessoOpenFinance other = (LogAcessoOpenFinance) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$userId = this.getUserId();
        final java.lang.Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final java.lang.Object this$httpStatus = this.getHttpStatus();
        final java.lang.Object other$httpStatus = other.getHttpStatus();
        if (this$httpStatus == null ? other$httpStatus != null : !this$httpStatus.equals(other$httpStatus)) return false;
        final java.lang.Object this$responseTimeMs = this.getResponseTimeMs();
        final java.lang.Object other$responseTimeMs = other.getResponseTimeMs();
        if (this$responseTimeMs == null ? other$responseTimeMs != null : !this$responseTimeMs.equals(other$responseTimeMs)) return false;
        final java.lang.Object this$requestSizeBytes = this.getRequestSizeBytes();
        final java.lang.Object other$requestSizeBytes = other.getRequestSizeBytes();
        if (this$requestSizeBytes == null ? other$requestSizeBytes != null : !this$requestSizeBytes.equals(other$requestSizeBytes)) return false;
        final java.lang.Object this$responseSizeBytes = this.getResponseSizeBytes();
        final java.lang.Object other$responseSizeBytes = other.getResponseSizeBytes();
        if (this$responseSizeBytes == null ? other$responseSizeBytes != null : !this$responseSizeBytes.equals(other$responseSizeBytes)) return false;
        final java.lang.Object this$riskScore = this.getRiskScore();
        final java.lang.Object other$riskScore = other.getRiskScore();
        if (this$riskScore == null ? other$riskScore != null : !this$riskScore.equals(other$riskScore)) return false;
        final java.lang.Object this$rateLimitRemaining = this.getRateLimitRemaining();
        final java.lang.Object other$rateLimitRemaining = other.getRateLimitRemaining();
        if (this$rateLimitRemaining == null ? other$rateLimitRemaining != null : !this$rateLimitRemaining.equals(other$rateLimitRemaining)) return false;
        final java.lang.Object this$requestId = this.getRequestId();
        final java.lang.Object other$requestId = other.getRequestId();
        if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) return false;
        final java.lang.Object this$consentId = this.getConsentId();
        final java.lang.Object other$consentId = other.getConsentId();
        if (this$consentId == null ? other$consentId != null : !this$consentId.equals(other$consentId)) return false;
        final java.lang.Object this$clientId = this.getClientId();
        final java.lang.Object other$clientId = other.getClientId();
        if (this$clientId == null ? other$clientId != null : !this$clientId.equals(other$clientId)) return false;
        final java.lang.Object this$endpoint = this.getEndpoint();
        final java.lang.Object other$endpoint = other.getEndpoint();
        if (this$endpoint == null ? other$endpoint != null : !this$endpoint.equals(other$endpoint)) return false;
        final java.lang.Object this$method = this.getMethod();
        final java.lang.Object other$method = other.getMethod();
        if (this$method == null ? other$method != null : !this$method.equals(other$method)) return false;
        final java.lang.Object this$ipAddress = this.getIpAddress();
        final java.lang.Object other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$deviceId = this.getDeviceId();
        final java.lang.Object other$deviceId = other.getDeviceId();
        if (this$deviceId == null ? other$deviceId != null : !this$deviceId.equals(other$deviceId)) return false;
        final java.lang.Object this$geolocation = this.getGeolocation();
        final java.lang.Object other$geolocation = other.getGeolocation();
        if (this$geolocation == null ? other$geolocation != null : !this$geolocation.equals(other$geolocation)) return false;
        final java.lang.Object this$riskLevel = this.getRiskLevel();
        final java.lang.Object other$riskLevel = other.getRiskLevel();
        if (this$riskLevel == null ? other$riskLevel != null : !this$riskLevel.equals(other$riskLevel)) return false;
        final java.lang.Object this$tipoAcesso = this.getTipoAcesso();
        final java.lang.Object other$tipoAcesso = other.getTipoAcesso();
        if (this$tipoAcesso == null ? other$tipoAcesso != null : !this$tipoAcesso.equals(other$tipoAcesso)) return false;
        final java.lang.Object this$categoriaDados = this.getCategoriaDados();
        final java.lang.Object other$categoriaDados = other.getCategoriaDados();
        if (this$categoriaDados == null ? other$categoriaDados != null : !this$categoriaDados.equals(other$categoriaDados)) return false;
        final java.lang.Object this$dadosAcessados = this.getDadosAcessados();
        final java.lang.Object other$dadosAcessados = other.getDadosAcessados();
        if (this$dadosAcessados == null ? other$dadosAcessados != null : !this$dadosAcessados.equals(other$dadosAcessados)) return false;
        final java.lang.Object this$erroDetalhes = this.getErroDetalhes();
        final java.lang.Object other$erroDetalhes = other.getErroDetalhes();
        if (this$erroDetalhes == null ? other$erroDetalhes != null : !this$erroDetalhes.equals(other$erroDetalhes)) return false;
        final java.lang.Object this$rateLimitReset = this.getRateLimitReset();
        final java.lang.Object other$rateLimitReset = other.getRateLimitReset();
        if (this$rateLimitReset == null ? other$rateLimitReset != null : !this$rateLimitReset.equals(other$rateLimitReset)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
        final java.lang.Object this$dataAcesso = this.getDataAcesso();
        final java.lang.Object other$dataAcesso = other.getDataAcesso();
        if (this$dataAcesso == null ? other$dataAcesso != null : !this$dataAcesso.equals(other$dataAcesso)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LogAcessoOpenFinance;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final java.lang.Object $httpStatus = this.getHttpStatus();
        result = result * PRIME + ($httpStatus == null ? 43 : $httpStatus.hashCode());
        final java.lang.Object $responseTimeMs = this.getResponseTimeMs();
        result = result * PRIME + ($responseTimeMs == null ? 43 : $responseTimeMs.hashCode());
        final java.lang.Object $requestSizeBytes = this.getRequestSizeBytes();
        result = result * PRIME + ($requestSizeBytes == null ? 43 : $requestSizeBytes.hashCode());
        final java.lang.Object $responseSizeBytes = this.getResponseSizeBytes();
        result = result * PRIME + ($responseSizeBytes == null ? 43 : $responseSizeBytes.hashCode());
        final java.lang.Object $riskScore = this.getRiskScore();
        result = result * PRIME + ($riskScore == null ? 43 : $riskScore.hashCode());
        final java.lang.Object $rateLimitRemaining = this.getRateLimitRemaining();
        result = result * PRIME + ($rateLimitRemaining == null ? 43 : $rateLimitRemaining.hashCode());
        final java.lang.Object $requestId = this.getRequestId();
        result = result * PRIME + ($requestId == null ? 43 : $requestId.hashCode());
        final java.lang.Object $consentId = this.getConsentId();
        result = result * PRIME + ($consentId == null ? 43 : $consentId.hashCode());
        final java.lang.Object $clientId = this.getClientId();
        result = result * PRIME + ($clientId == null ? 43 : $clientId.hashCode());
        final java.lang.Object $endpoint = this.getEndpoint();
        result = result * PRIME + ($endpoint == null ? 43 : $endpoint.hashCode());
        final java.lang.Object $method = this.getMethod();
        result = result * PRIME + ($method == null ? 43 : $method.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $deviceId = this.getDeviceId();
        result = result * PRIME + ($deviceId == null ? 43 : $deviceId.hashCode());
        final java.lang.Object $geolocation = this.getGeolocation();
        result = result * PRIME + ($geolocation == null ? 43 : $geolocation.hashCode());
        final java.lang.Object $riskLevel = this.getRiskLevel();
        result = result * PRIME + ($riskLevel == null ? 43 : $riskLevel.hashCode());
        final java.lang.Object $tipoAcesso = this.getTipoAcesso();
        result = result * PRIME + ($tipoAcesso == null ? 43 : $tipoAcesso.hashCode());
        final java.lang.Object $categoriaDados = this.getCategoriaDados();
        result = result * PRIME + ($categoriaDados == null ? 43 : $categoriaDados.hashCode());
        final java.lang.Object $dadosAcessados = this.getDadosAcessados();
        result = result * PRIME + ($dadosAcessados == null ? 43 : $dadosAcessados.hashCode());
        final java.lang.Object $erroDetalhes = this.getErroDetalhes();
        result = result * PRIME + ($erroDetalhes == null ? 43 : $erroDetalhes.hashCode());
        final java.lang.Object $rateLimitReset = this.getRateLimitReset();
        result = result * PRIME + ($rateLimitReset == null ? 43 : $rateLimitReset.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        final java.lang.Object $dataAcesso = this.getDataAcesso();
        result = result * PRIME + ($dataAcesso == null ? 43 : $dataAcesso.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LogAcessoOpenFinance(id=" + this.getId() + ", requestId=" + this.getRequestId() + ", consentId=" + this.getConsentId() + ", clientId=" + this.getClientId() + ", userId=" + this.getUserId() + ", endpoint=" + this.getEndpoint() + ", method=" + this.getMethod() + ", httpStatus=" + this.getHttpStatus() + ", responseTimeMs=" + this.getResponseTimeMs() + ", requestSizeBytes=" + this.getRequestSizeBytes() + ", responseSizeBytes=" + this.getResponseSizeBytes() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", deviceId=" + this.getDeviceId() + ", geolocation=" + this.getGeolocation() + ", riskScore=" + this.getRiskScore() + ", riskLevel=" + this.getRiskLevel() + ", tipoAcesso=" + this.getTipoAcesso() + ", categoriaDados=" + this.getCategoriaDados() + ", dadosAcessados=" + this.getDadosAcessados() + ", erroDetalhes=" + this.getErroDetalhes() + ", rateLimitRemaining=" + this.getRateLimitRemaining() + ", rateLimitReset=" + this.getRateLimitReset() + ", metadata=" + this.getMetadata() + ", dataAcesso=" + this.getDataAcesso() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LogAcessoOpenFinance() {
    }

    @java.lang.SuppressWarnings("all")
    public LogAcessoOpenFinance(final Long id, final String requestId, final String consentId, final String clientId, final Long userId, final String endpoint, final String method, final Integer httpStatus, final Long responseTimeMs, final Long requestSizeBytes, final Long responseSizeBytes, final String ipAddress, final String userAgent, final String deviceId, final String geolocation, final Double riskScore, final String riskLevel, final TipoAcesso tipoAcesso, final CategoriaDados categoriaDados, final String dadosAcessados, final String erroDetalhes, final Integer rateLimitRemaining, final LocalDateTime rateLimitReset, final String metadata, final LocalDateTime dataAcesso) {
        this.id = id;
        this.requestId = requestId;
        this.consentId = consentId;
        this.clientId = clientId;
        this.userId = userId;
        this.endpoint = endpoint;
        this.method = method;
        this.httpStatus = httpStatus;
        this.responseTimeMs = responseTimeMs;
        this.requestSizeBytes = requestSizeBytes;
        this.responseSizeBytes = responseSizeBytes;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceId = deviceId;
        this.geolocation = geolocation;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.tipoAcesso = tipoAcesso;
        this.categoriaDados = categoriaDados;
        this.dadosAcessados = dadosAcessados;
        this.erroDetalhes = erroDetalhes;
        this.rateLimitRemaining = rateLimitRemaining;
        this.rateLimitReset = rateLimitReset;
        this.metadata = metadata;
        this.dataAcesso = dataAcesso;
    }
}
