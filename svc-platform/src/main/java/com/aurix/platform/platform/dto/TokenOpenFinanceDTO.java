package com.aurix.platform.platform.dto;

import com.aurix.platform.platform.entity.TokenOpenFinance;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO para tokens do Open Finance
 * 
 * Segue o padrão OAuth2 para tokens de acesso
 */
public class TokenOpenFinanceDTO {
    @JsonProperty("access_token")
    @NotBlank(message = "Access token é obrigatório")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    @NotBlank(message = "Tipo do token é obrigatório")
    private String tokenType;
    @JsonProperty("expires_in")
    @NotNull(message = "Tempo de expiração é obrigatório")
    @Min(value = 1, message = "Tempo de expiração deve ser maior que 0")
    private Integer expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("consent_id")
    @NotBlank(message = "Consent ID é obrigatório")
    private String consentId;
    @JsonProperty("client_id")
    @NotBlank(message = "Client ID é obrigatório")
    private String clientId;
    @JsonProperty("user_id")
    @NotNull(message = "User ID é obrigatório")
    private Long userId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("data_expiracao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    @NotNull(message = "Data de expiração é obrigatória")
    @Future(message = "Data de expiração deve ser futura")
    private LocalDateTime dataExpiracao;
    @JsonProperty("data_revocacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataRevocacao;
    @JsonProperty("motivo_revocacao")
    private String motivoRevocacao;
    @JsonProperty("ip_address")
    private String ipAddress;
    @JsonProperty("user_agent")
    private String userAgent;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("geolocation")
    private String geolocation;
    @JsonProperty("rate_limit_remaining")
    @Min(value = 0, message = "Rate limit remaining deve ser maior ou igual a 0")
    private Integer rateLimitRemaining;
    @JsonProperty("rate_limit_reset")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime rateLimitReset;
    @JsonProperty("last_used")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime lastUsed;
    @JsonProperty("usage_count")
    @Min(value = 0, message = "Usage count deve ser maior ou igual a 0")
    private Long usageCount;
    @JsonProperty("metadata")
    private String metadata;
    @JsonProperty("data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataCriacao;
    @JsonProperty("data_atualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataAtualizacao;
    @JsonProperty("versao")
    private Long versao;

    /**
     * Converte entidade para DTO
     */
    public static TokenOpenFinanceDTO fromEntity(TokenOpenFinance entity) {
        if (entity == null) {
            return null;
        }
        return TokenOpenFinanceDTO.builder().accessToken(entity.getAccessToken()).refreshToken(entity.getRefreshToken()).tokenType(entity.getTokenType()).expiresIn(entity.getExpiresIn()).scope(entity.getScope()).consentId(entity.getConsentId()).clientId(entity.getClientId()).userId(entity.getUserId()).status(entity.getStatus() != null ? entity.getStatus().name() : null).dataExpiracao(entity.getDataExpiracao()).dataRevocacao(entity.getDataRevocacao()).motivoRevocacao(entity.getMotivoRevocacao()).ipAddress(entity.getIpAddress()).userAgent(entity.getUserAgent()).deviceId(entity.getDeviceId()).geolocation(entity.getGeolocation()).rateLimitRemaining(entity.getRateLimitRemaining()).rateLimitReset(entity.getRateLimitReset()).lastUsed(entity.getLastUsed()).usageCount(entity.getUsageCount()).metadata(entity.getMetadata()).dataCriacao(entity.getDataCriacao()).dataAtualizacao(entity.getDataAtualizacao()).versao(entity.getVersao()).build();
    }

    /**
     * Converte DTO para entidade
     */
    public TokenOpenFinance toEntity() {
        return TokenOpenFinance.builder().accessToken(this.accessToken).refreshToken(this.refreshToken).tokenType(this.tokenType).expiresIn(this.expiresIn).scope(this.scope).consentId(this.consentId).clientId(this.clientId).userId(this.userId).status(this.status != null ? TokenOpenFinance.StatusToken.valueOf(this.status) : null).dataExpiracao(this.dataExpiracao).dataRevocacao(this.dataRevocacao).motivoRevocacao(this.motivoRevocacao).ipAddress(this.ipAddress).userAgent(this.userAgent).deviceId(this.deviceId).geolocation(this.geolocation).rateLimitRemaining(this.rateLimitRemaining).rateLimitReset(this.rateLimitReset).lastUsed(this.lastUsed).usageCount(this.usageCount).metadata(this.metadata).build();
    }


    @java.lang.SuppressWarnings("all")
    public static class TokenOpenFinanceDTOBuilder {
        @java.lang.SuppressWarnings("all")
        private String accessToken;
        @java.lang.SuppressWarnings("all")
        private String refreshToken;
        @java.lang.SuppressWarnings("all")
        private String tokenType;
        @java.lang.SuppressWarnings("all")
        private Integer expiresIn;
        @java.lang.SuppressWarnings("all")
        private String scope;
        @java.lang.SuppressWarnings("all")
        private String consentId;
        @java.lang.SuppressWarnings("all")
        private String clientId;
        @java.lang.SuppressWarnings("all")
        private Long userId;
        @java.lang.SuppressWarnings("all")
        private String status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataRevocacao;
        @java.lang.SuppressWarnings("all")
        private String motivoRevocacao;
        @java.lang.SuppressWarnings("all")
        private String ipAddress;
        @java.lang.SuppressWarnings("all")
        private String userAgent;
        @java.lang.SuppressWarnings("all")
        private String deviceId;
        @java.lang.SuppressWarnings("all")
        private String geolocation;
        @java.lang.SuppressWarnings("all")
        private Integer rateLimitRemaining;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime rateLimitReset;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime lastUsed;
        @java.lang.SuppressWarnings("all")
        private Long usageCount;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        TokenOpenFinanceDTOBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("access_token")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder accessToken(final String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("refresh_token")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder refreshToken(final String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("token_type")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder tokenType(final String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("expires_in")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder expiresIn(final Integer expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("scope")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder scope(final String scope) {
            this.scope = scope;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("consent_id")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder consentId(final String consentId) {
            this.consentId = consentId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("client_id")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("user_id")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("status")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("data_expiracao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("data_revocacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder dataRevocacao(final LocalDateTime dataRevocacao) {
            this.dataRevocacao = dataRevocacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("motivo_revocacao")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder motivoRevocacao(final String motivoRevocacao) {
            this.motivoRevocacao = motivoRevocacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("ip_address")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("user_agent")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("device_id")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder deviceId(final String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("geolocation")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder geolocation(final String geolocation) {
            this.geolocation = geolocation;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("rate_limit_remaining")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder rateLimitRemaining(final Integer rateLimitRemaining) {
            this.rateLimitRemaining = rateLimitRemaining;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("rate_limit_reset")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder rateLimitReset(final LocalDateTime rateLimitReset) {
            this.rateLimitReset = rateLimitReset;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("last_used")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder lastUsed(final LocalDateTime lastUsed) {
            this.lastUsed = lastUsed;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("usage_count")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder usageCount(final Long usageCount) {
            this.usageCount = usageCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("metadata")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("data_criacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("data_atualizacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("versao")
        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TokenOpenFinanceDTO build() {
            return new TokenOpenFinanceDTO(this.accessToken, this.refreshToken, this.tokenType, this.expiresIn, this.scope, this.consentId, this.clientId, this.userId, this.status, this.dataExpiracao, this.dataRevocacao, this.motivoRevocacao, this.ipAddress, this.userAgent, this.deviceId, this.geolocation, this.rateLimitRemaining, this.rateLimitReset, this.lastUsed, this.usageCount, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder(accessToken=" + this.accessToken + ", refreshToken=" + this.refreshToken + ", tokenType=" + this.tokenType + ", expiresIn=" + this.expiresIn + ", scope=" + this.scope + ", consentId=" + this.consentId + ", clientId=" + this.clientId + ", userId=" + this.userId + ", status=" + this.status + ", dataExpiracao=" + this.dataExpiracao + ", dataRevocacao=" + this.dataRevocacao + ", motivoRevocacao=" + this.motivoRevocacao + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", deviceId=" + this.deviceId + ", geolocation=" + this.geolocation + ", rateLimitRemaining=" + this.rateLimitRemaining + ", rateLimitReset=" + this.rateLimitReset + ", lastUsed=" + this.lastUsed + ", usageCount=" + this.usageCount + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder builder() {
        return new TokenOpenFinanceDTO.TokenOpenFinanceDTOBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public String getAccessToken() {
        return this.accessToken;
    }

    @java.lang.SuppressWarnings("all")
    public String getRefreshToken() {
        return this.refreshToken;
    }

    @java.lang.SuppressWarnings("all")
    public String getTokenType() {
        return this.tokenType;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getExpiresIn() {
        return this.expiresIn;
    }

    @java.lang.SuppressWarnings("all")
    public String getScope() {
        return this.scope;
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
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRevocacao() {
        return this.dataRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoRevocacao() {
        return this.motivoRevocacao;
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
    public Integer getRateLimitRemaining() {
        return this.rateLimitRemaining;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getRateLimitReset() {
        return this.rateLimitReset;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getLastUsed() {
        return this.lastUsed;
    }

    @java.lang.SuppressWarnings("all")
    public Long getUsageCount() {
        return this.usageCount;
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
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    @java.lang.SuppressWarnings("all")
    public void setExpiresIn(final Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @java.lang.SuppressWarnings("all")
    public void setScope(final String scope) {
        this.scope = scope;
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
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRevocacao(final LocalDateTime dataRevocacao) {
        this.dataRevocacao = dataRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoRevocacao(final String motivoRevocacao) {
        this.motivoRevocacao = motivoRevocacao;
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
    public void setRateLimitRemaining(final Integer rateLimitRemaining) {
        this.rateLimitRemaining = rateLimitRemaining;
    }

    @java.lang.SuppressWarnings("all")
    public void setRateLimitReset(final LocalDateTime rateLimitReset) {
        this.rateLimitReset = rateLimitReset;
    }

    @java.lang.SuppressWarnings("all")
    public void setLastUsed(final LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsageCount(final Long usageCount) {
        this.usageCount = usageCount;
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
        if (!(o instanceof TokenOpenFinanceDTO)) return false;
        final TokenOpenFinanceDTO other = (TokenOpenFinanceDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$expiresIn = this.getExpiresIn();
        final java.lang.Object other$expiresIn = other.getExpiresIn();
        if (this$expiresIn == null ? other$expiresIn != null : !this$expiresIn.equals(other$expiresIn)) return false;
        final java.lang.Object this$userId = this.getUserId();
        final java.lang.Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final java.lang.Object this$rateLimitRemaining = this.getRateLimitRemaining();
        final java.lang.Object other$rateLimitRemaining = other.getRateLimitRemaining();
        if (this$rateLimitRemaining == null ? other$rateLimitRemaining != null : !this$rateLimitRemaining.equals(other$rateLimitRemaining)) return false;
        final java.lang.Object this$usageCount = this.getUsageCount();
        final java.lang.Object other$usageCount = other.getUsageCount();
        if (this$usageCount == null ? other$usageCount != null : !this$usageCount.equals(other$usageCount)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$accessToken = this.getAccessToken();
        final java.lang.Object other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken)) return false;
        final java.lang.Object this$refreshToken = this.getRefreshToken();
        final java.lang.Object other$refreshToken = other.getRefreshToken();
        if (this$refreshToken == null ? other$refreshToken != null : !this$refreshToken.equals(other$refreshToken)) return false;
        final java.lang.Object this$tokenType = this.getTokenType();
        final java.lang.Object other$tokenType = other.getTokenType();
        if (this$tokenType == null ? other$tokenType != null : !this$tokenType.equals(other$tokenType)) return false;
        final java.lang.Object this$scope = this.getScope();
        final java.lang.Object other$scope = other.getScope();
        if (this$scope == null ? other$scope != null : !this$scope.equals(other$scope)) return false;
        final java.lang.Object this$consentId = this.getConsentId();
        final java.lang.Object other$consentId = other.getConsentId();
        if (this$consentId == null ? other$consentId != null : !this$consentId.equals(other$consentId)) return false;
        final java.lang.Object this$clientId = this.getClientId();
        final java.lang.Object other$clientId = other.getClientId();
        if (this$clientId == null ? other$clientId != null : !this$clientId.equals(other$clientId)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$dataRevocacao = this.getDataRevocacao();
        final java.lang.Object other$dataRevocacao = other.getDataRevocacao();
        if (this$dataRevocacao == null ? other$dataRevocacao != null : !this$dataRevocacao.equals(other$dataRevocacao)) return false;
        final java.lang.Object this$motivoRevocacao = this.getMotivoRevocacao();
        final java.lang.Object other$motivoRevocacao = other.getMotivoRevocacao();
        if (this$motivoRevocacao == null ? other$motivoRevocacao != null : !this$motivoRevocacao.equals(other$motivoRevocacao)) return false;
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
        final java.lang.Object this$rateLimitReset = this.getRateLimitReset();
        final java.lang.Object other$rateLimitReset = other.getRateLimitReset();
        if (this$rateLimitReset == null ? other$rateLimitReset != null : !this$rateLimitReset.equals(other$rateLimitReset)) return false;
        final java.lang.Object this$lastUsed = this.getLastUsed();
        final java.lang.Object other$lastUsed = other.getLastUsed();
        if (this$lastUsed == null ? other$lastUsed != null : !this$lastUsed.equals(other$lastUsed)) return false;
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
        return other instanceof TokenOpenFinanceDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $expiresIn = this.getExpiresIn();
        result = result * PRIME + ($expiresIn == null ? 43 : $expiresIn.hashCode());
        final java.lang.Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final java.lang.Object $rateLimitRemaining = this.getRateLimitRemaining();
        result = result * PRIME + ($rateLimitRemaining == null ? 43 : $rateLimitRemaining.hashCode());
        final java.lang.Object $usageCount = this.getUsageCount();
        result = result * PRIME + ($usageCount == null ? 43 : $usageCount.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $accessToken = this.getAccessToken();
        result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
        final java.lang.Object $refreshToken = this.getRefreshToken();
        result = result * PRIME + ($refreshToken == null ? 43 : $refreshToken.hashCode());
        final java.lang.Object $tokenType = this.getTokenType();
        result = result * PRIME + ($tokenType == null ? 43 : $tokenType.hashCode());
        final java.lang.Object $scope = this.getScope();
        result = result * PRIME + ($scope == null ? 43 : $scope.hashCode());
        final java.lang.Object $consentId = this.getConsentId();
        result = result * PRIME + ($consentId == null ? 43 : $consentId.hashCode());
        final java.lang.Object $clientId = this.getClientId();
        result = result * PRIME + ($clientId == null ? 43 : $clientId.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $dataRevocacao = this.getDataRevocacao();
        result = result * PRIME + ($dataRevocacao == null ? 43 : $dataRevocacao.hashCode());
        final java.lang.Object $motivoRevocacao = this.getMotivoRevocacao();
        result = result * PRIME + ($motivoRevocacao == null ? 43 : $motivoRevocacao.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $deviceId = this.getDeviceId();
        result = result * PRIME + ($deviceId == null ? 43 : $deviceId.hashCode());
        final java.lang.Object $geolocation = this.getGeolocation();
        result = result * PRIME + ($geolocation == null ? 43 : $geolocation.hashCode());
        final java.lang.Object $rateLimitReset = this.getRateLimitReset();
        result = result * PRIME + ($rateLimitReset == null ? 43 : $rateLimitReset.hashCode());
        final java.lang.Object $lastUsed = this.getLastUsed();
        result = result * PRIME + ($lastUsed == null ? 43 : $lastUsed.hashCode());
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
        return "TokenOpenFinanceDTO(accessToken=" + this.getAccessToken() + ", refreshToken=" + this.getRefreshToken() + ", tokenType=" + this.getTokenType() + ", expiresIn=" + this.getExpiresIn() + ", scope=" + this.getScope() + ", consentId=" + this.getConsentId() + ", clientId=" + this.getClientId() + ", userId=" + this.getUserId() + ", status=" + this.getStatus() + ", dataExpiracao=" + this.getDataExpiracao() + ", dataRevocacao=" + this.getDataRevocacao() + ", motivoRevocacao=" + this.getMotivoRevocacao() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", deviceId=" + this.getDeviceId() + ", geolocation=" + this.getGeolocation() + ", rateLimitRemaining=" + this.getRateLimitRemaining() + ", rateLimitReset=" + this.getRateLimitReset() + ", lastUsed=" + this.getLastUsed() + ", usageCount=" + this.getUsageCount() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TokenOpenFinanceDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public TokenOpenFinanceDTO(final String accessToken, final String refreshToken, final String tokenType, final Integer expiresIn, final String scope, final String consentId, final String clientId, final Long userId, final String status, final LocalDateTime dataExpiracao, final LocalDateTime dataRevocacao, final String motivoRevocacao, final String ipAddress, final String userAgent, final String deviceId, final String geolocation, final Integer rateLimitRemaining, final LocalDateTime rateLimitReset, final LocalDateTime lastUsed, final Long usageCount, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.consentId = consentId;
        this.clientId = clientId;
        this.userId = userId;
        this.status = status;
        this.dataExpiracao = dataExpiracao;
        this.dataRevocacao = dataRevocacao;
        this.motivoRevocacao = motivoRevocacao;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceId = deviceId;
        this.geolocation = geolocation;
        this.rateLimitRemaining = rateLimitRemaining;
        this.rateLimitReset = rateLimitReset;
        this.lastUsed = lastUsed;
        this.usageCount = usageCount;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
