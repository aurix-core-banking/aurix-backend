package com.aurix.platform.platform.dto;

import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para consentimentos do Open Finance
 * 
 * Segue o padrão definido pelo BACEN para as APIs do Open Finance
 */
public class ConsentimentoOpenFinanceDTO {
    @JsonProperty("consentId")
    private String consentId;
    @JsonProperty("clientId")
    @NotBlank(message = "Client ID é obrigatório")
    private String clientId;
    @JsonProperty("clientName")
    @NotBlank(message = "Nome do cliente é obrigatório")
    private String clientName;
    @JsonProperty("clientUri")
    private String clientUri;
    @JsonProperty("logoUri")
    private String logoUri;
    @JsonProperty("tosUri")
    private String tosUri;
    @JsonProperty("policyUri")
    private String policyUri;
    @JsonProperty("softwareId")
    private String softwareId;
    @JsonProperty("softwareVersion")
    private String softwareVersion;
    @JsonProperty("status")
    private String status;
    @JsonProperty("tipoConsentimento")
    @NotBlank(message = "Tipo de consentimento é obrigatório")
    private String tipoConsentimento;
    @JsonProperty("dataExpiracao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    @NotNull(message = "Data de expiração é obrigatória")
    @Future(message = "Data de expiração deve ser futura")
    private LocalDateTime dataExpiracao;
    @JsonProperty("dataAprovacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataAprovacao;
    @JsonProperty("dataRejeicao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataRejeicao;
    @JsonProperty("dataRevocacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataRevocacao;
    @JsonProperty("motivoRejeicao")
    private String motivoRejeicao;
    @JsonProperty("motivoRevocacao")
    private String motivoRevocacao;
    @JsonProperty("permissoes")
    @NotEmpty(message = "Permissões são obrigatórias")
    private List<String> permissoes;
    @JsonProperty("contasAutorizadas")
    private List<Long> contasAutorizadas;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("userAgent")
    private String userAgent;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("geolocation")
    private String geolocation;
    @JsonProperty("riskScore")
    @DecimalMin(value = "0.0", message = "Risk score deve ser maior ou igual a 0")
    @DecimalMax(value = "1.0", message = "Risk score deve ser menor ou igual a 1")
    private Double riskScore;
    @JsonProperty("riskLevel")
    private String riskLevel;
    @JsonProperty("metadata")
    private String metadata;
    @JsonProperty("dataCriacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataCriacao;
    @JsonProperty("dataAtualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
    private LocalDateTime dataAtualizacao;
    @JsonProperty("versao")
    private Long versao;

    /**
     * Converte entidade para DTO
     */
    public static ConsentimentoOpenFinanceDTO fromEntity(ConsentimentoOpenFinance entity) {
        if (entity == null) {
            return null;
        }
        return ConsentimentoOpenFinanceDTO.builder().consentId(entity.getConsentId()).clientId(entity.getClientId()).clientName(entity.getClientName()).clientUri(entity.getClientUri()).logoUri(entity.getLogoUri()).tosUri(entity.getTosUri()).policyUri(entity.getPolicyUri()).softwareId(entity.getSoftwareId()).softwareVersion(entity.getSoftwareVersion()).status(entity.getStatus() != null ? entity.getStatus().name() : null).tipoConsentimento(entity.getTipoConsentimento() != null ? entity.getTipoConsentimento().name() : null).dataExpiracao(entity.getDataExpiracao()).dataAprovacao(entity.getDataAprovacao()).dataRejeicao(entity.getDataRejeicao()).dataRevocacao(entity.getDataRevocacao()).motivoRejeicao(entity.getMotivoRejeicao()).motivoRevocacao(entity.getMotivoRevocacao()).permissoes(entity.getPermissoes()).contasAutorizadas(entity.getContasAutorizadas()).ipAddress(entity.getIpAddress()).userAgent(entity.getUserAgent()).deviceId(entity.getDeviceId()).geolocation(entity.getGeolocation()).riskScore(entity.getRiskScore()).riskLevel(entity.getRiskLevel()).metadata(entity.getMetadata()).dataCriacao(entity.getDataCriacao()).dataAtualizacao(entity.getDataAtualizacao()).versao(entity.getVersao()).build();
    }

    /**
     * Converte DTO para entidade
     */
    public ConsentimentoOpenFinance toEntity() {
        return ConsentimentoOpenFinance.builder().consentId(this.consentId).clientId(this.clientId).clientName(this.clientName).clientUri(this.clientUri).logoUri(this.logoUri).tosUri(this.tosUri).policyUri(this.policyUri).softwareId(this.softwareId).softwareVersion(this.softwareVersion).status(this.status != null ? ConsentimentoOpenFinance.StatusConsentimento.valueOf(this.status) : null).tipoConsentimento(this.tipoConsentimento != null ? ConsentimentoOpenFinance.TipoConsentimento.valueOf(this.tipoConsentimento) : null).dataExpiracao(this.dataExpiracao).dataAprovacao(this.dataAprovacao).dataRejeicao(this.dataRejeicao).dataRevocacao(this.dataRevocacao).motivoRejeicao(this.motivoRejeicao).motivoRevocacao(this.motivoRevocacao).permissoes(this.permissoes).contasAutorizadas(this.contasAutorizadas).ipAddress(this.ipAddress).userAgent(this.userAgent).deviceId(this.deviceId).geolocation(this.geolocation).riskScore(this.riskScore).riskLevel(this.riskLevel).metadata(this.metadata).build();
    }


    @java.lang.SuppressWarnings("all")
    public static class ConsentimentoOpenFinanceDTOBuilder {
        @java.lang.SuppressWarnings("all")
        private String consentId;
        @java.lang.SuppressWarnings("all")
        private String clientId;
        @java.lang.SuppressWarnings("all")
        private String clientName;
        @java.lang.SuppressWarnings("all")
        private String clientUri;
        @java.lang.SuppressWarnings("all")
        private String logoUri;
        @java.lang.SuppressWarnings("all")
        private String tosUri;
        @java.lang.SuppressWarnings("all")
        private String policyUri;
        @java.lang.SuppressWarnings("all")
        private String softwareId;
        @java.lang.SuppressWarnings("all")
        private String softwareVersion;
        @java.lang.SuppressWarnings("all")
        private String status;
        @java.lang.SuppressWarnings("all")
        private String tipoConsentimento;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAprovacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataRejeicao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataRevocacao;
        @java.lang.SuppressWarnings("all")
        private String motivoRejeicao;
        @java.lang.SuppressWarnings("all")
        private String motivoRevocacao;
        @java.lang.SuppressWarnings("all")
        private List<String> permissoes;
        @java.lang.SuppressWarnings("all")
        private List<Long> contasAutorizadas;
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
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        ConsentimentoOpenFinanceDTOBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("consentId")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder consentId(final String consentId) {
            this.consentId = consentId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("clientId")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("clientName")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder clientName(final String clientName) {
            this.clientName = clientName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("clientUri")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder clientUri(final String clientUri) {
            this.clientUri = clientUri;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("logoUri")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder logoUri(final String logoUri) {
            this.logoUri = logoUri;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("tosUri")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder tosUri(final String tosUri) {
            this.tosUri = tosUri;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("policyUri")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder policyUri(final String policyUri) {
            this.policyUri = policyUri;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("softwareId")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder softwareId(final String softwareId) {
            this.softwareId = softwareId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("softwareVersion")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder softwareVersion(final String softwareVersion) {
            this.softwareVersion = softwareVersion;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("status")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("tipoConsentimento")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder tipoConsentimento(final String tipoConsentimento) {
            this.tipoConsentimento = tipoConsentimento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataExpiracao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataAprovacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataAprovacao(final LocalDateTime dataAprovacao) {
            this.dataAprovacao = dataAprovacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataRejeicao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataRejeicao(final LocalDateTime dataRejeicao) {
            this.dataRejeicao = dataRejeicao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataRevocacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataRevocacao(final LocalDateTime dataRevocacao) {
            this.dataRevocacao = dataRevocacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("motivoRejeicao")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder motivoRejeicao(final String motivoRejeicao) {
            this.motivoRejeicao = motivoRejeicao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("motivoRevocacao")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder motivoRevocacao(final String motivoRevocacao) {
            this.motivoRevocacao = motivoRevocacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("permissoes")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder permissoes(final List<String> permissoes) {
            this.permissoes = permissoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("contasAutorizadas")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder contasAutorizadas(final List<Long> contasAutorizadas) {
            this.contasAutorizadas = contasAutorizadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("ipAddress")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("userAgent")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("deviceId")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder deviceId(final String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("geolocation")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder geolocation(final String geolocation) {
            this.geolocation = geolocation;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("riskScore")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder riskScore(final Double riskScore) {
            this.riskScore = riskScore;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("riskLevel")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder riskLevel(final String riskLevel) {
            this.riskLevel = riskLevel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("metadata")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataCriacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("dataAtualizacao")
        @JsonFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("versao")
        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ConsentimentoOpenFinanceDTO build() {
            return new ConsentimentoOpenFinanceDTO(this.consentId, this.clientId, this.clientName, this.clientUri, this.logoUri, this.tosUri, this.policyUri, this.softwareId, this.softwareVersion, this.status, this.tipoConsentimento, this.dataExpiracao, this.dataAprovacao, this.dataRejeicao, this.dataRevocacao, this.motivoRejeicao, this.motivoRevocacao, this.permissoes, this.contasAutorizadas, this.ipAddress, this.userAgent, this.deviceId, this.geolocation, this.riskScore, this.riskLevel, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder(consentId=" + this.consentId + ", clientId=" + this.clientId + ", clientName=" + this.clientName + ", clientUri=" + this.clientUri + ", logoUri=" + this.logoUri + ", tosUri=" + this.tosUri + ", policyUri=" + this.policyUri + ", softwareId=" + this.softwareId + ", softwareVersion=" + this.softwareVersion + ", status=" + this.status + ", tipoConsentimento=" + this.tipoConsentimento + ", dataExpiracao=" + this.dataExpiracao + ", dataAprovacao=" + this.dataAprovacao + ", dataRejeicao=" + this.dataRejeicao + ", dataRevocacao=" + this.dataRevocacao + ", motivoRejeicao=" + this.motivoRejeicao + ", motivoRevocacao=" + this.motivoRevocacao + ", permissoes=" + this.permissoes + ", contasAutorizadas=" + this.contasAutorizadas + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", deviceId=" + this.deviceId + ", geolocation=" + this.geolocation + ", riskScore=" + this.riskScore + ", riskLevel=" + this.riskLevel + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder builder() {
        return new ConsentimentoOpenFinanceDTO.ConsentimentoOpenFinanceDTOBuilder();
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
    public String getClientName() {
        return this.clientName;
    }

    @java.lang.SuppressWarnings("all")
    public String getClientUri() {
        return this.clientUri;
    }

    @java.lang.SuppressWarnings("all")
    public String getLogoUri() {
        return this.logoUri;
    }

    @java.lang.SuppressWarnings("all")
    public String getTosUri() {
        return this.tosUri;
    }

    @java.lang.SuppressWarnings("all")
    public String getPolicyUri() {
        return this.policyUri;
    }

    @java.lang.SuppressWarnings("all")
    public String getSoftwareId() {
        return this.softwareId;
    }

    @java.lang.SuppressWarnings("all")
    public String getSoftwareVersion() {
        return this.softwareVersion;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoConsentimento() {
        return this.tipoConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRejeicao() {
        return this.dataRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRevocacao() {
        return this.dataRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoRejeicao() {
        return this.motivoRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoRevocacao() {
        return this.motivoRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public List<String> getPermissoes() {
        return this.permissoes;
    }

    @java.lang.SuppressWarnings("all")
    public List<Long> getContasAutorizadas() {
        return this.contasAutorizadas;
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
    public void setConsentId(final String consentId) {
        this.consentId = consentId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    @java.lang.SuppressWarnings("all")
    public void setClientUri(final String clientUri) {
        this.clientUri = clientUri;
    }

    @java.lang.SuppressWarnings("all")
    public void setLogoUri(final String logoUri) {
        this.logoUri = logoUri;
    }

    @java.lang.SuppressWarnings("all")
    public void setTosUri(final String tosUri) {
        this.tosUri = tosUri;
    }

    @java.lang.SuppressWarnings("all")
    public void setPolicyUri(final String policyUri) {
        this.policyUri = policyUri;
    }

    @java.lang.SuppressWarnings("all")
    public void setSoftwareId(final String softwareId) {
        this.softwareId = softwareId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSoftwareVersion(final String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoConsentimento(final String tipoConsentimento) {
        this.tipoConsentimento = tipoConsentimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRejeicao(final LocalDateTime dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRevocacao(final LocalDateTime dataRevocacao) {
        this.dataRevocacao = dataRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoRejeicao(final String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoRevocacao(final String motivoRevocacao) {
        this.motivoRevocacao = motivoRevocacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermissoes(final List<String> permissoes) {
        this.permissoes = permissoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setContasAutorizadas(final List<Long> contasAutorizadas) {
        this.contasAutorizadas = contasAutorizadas;
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
        if (!(o instanceof ConsentimentoOpenFinanceDTO)) return false;
        final ConsentimentoOpenFinanceDTO other = (ConsentimentoOpenFinanceDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$riskScore = this.getRiskScore();
        final java.lang.Object other$riskScore = other.getRiskScore();
        if (this$riskScore == null ? other$riskScore != null : !this$riskScore.equals(other$riskScore)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$consentId = this.getConsentId();
        final java.lang.Object other$consentId = other.getConsentId();
        if (this$consentId == null ? other$consentId != null : !this$consentId.equals(other$consentId)) return false;
        final java.lang.Object this$clientId = this.getClientId();
        final java.lang.Object other$clientId = other.getClientId();
        if (this$clientId == null ? other$clientId != null : !this$clientId.equals(other$clientId)) return false;
        final java.lang.Object this$clientName = this.getClientName();
        final java.lang.Object other$clientName = other.getClientName();
        if (this$clientName == null ? other$clientName != null : !this$clientName.equals(other$clientName)) return false;
        final java.lang.Object this$clientUri = this.getClientUri();
        final java.lang.Object other$clientUri = other.getClientUri();
        if (this$clientUri == null ? other$clientUri != null : !this$clientUri.equals(other$clientUri)) return false;
        final java.lang.Object this$logoUri = this.getLogoUri();
        final java.lang.Object other$logoUri = other.getLogoUri();
        if (this$logoUri == null ? other$logoUri != null : !this$logoUri.equals(other$logoUri)) return false;
        final java.lang.Object this$tosUri = this.getTosUri();
        final java.lang.Object other$tosUri = other.getTosUri();
        if (this$tosUri == null ? other$tosUri != null : !this$tosUri.equals(other$tosUri)) return false;
        final java.lang.Object this$policyUri = this.getPolicyUri();
        final java.lang.Object other$policyUri = other.getPolicyUri();
        if (this$policyUri == null ? other$policyUri != null : !this$policyUri.equals(other$policyUri)) return false;
        final java.lang.Object this$softwareId = this.getSoftwareId();
        final java.lang.Object other$softwareId = other.getSoftwareId();
        if (this$softwareId == null ? other$softwareId != null : !this$softwareId.equals(other$softwareId)) return false;
        final java.lang.Object this$softwareVersion = this.getSoftwareVersion();
        final java.lang.Object other$softwareVersion = other.getSoftwareVersion();
        if (this$softwareVersion == null ? other$softwareVersion != null : !this$softwareVersion.equals(other$softwareVersion)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$tipoConsentimento = this.getTipoConsentimento();
        final java.lang.Object other$tipoConsentimento = other.getTipoConsentimento();
        if (this$tipoConsentimento == null ? other$tipoConsentimento != null : !this$tipoConsentimento.equals(other$tipoConsentimento)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        final java.lang.Object this$dataRejeicao = this.getDataRejeicao();
        final java.lang.Object other$dataRejeicao = other.getDataRejeicao();
        if (this$dataRejeicao == null ? other$dataRejeicao != null : !this$dataRejeicao.equals(other$dataRejeicao)) return false;
        final java.lang.Object this$dataRevocacao = this.getDataRevocacao();
        final java.lang.Object other$dataRevocacao = other.getDataRevocacao();
        if (this$dataRevocacao == null ? other$dataRevocacao != null : !this$dataRevocacao.equals(other$dataRevocacao)) return false;
        final java.lang.Object this$motivoRejeicao = this.getMotivoRejeicao();
        final java.lang.Object other$motivoRejeicao = other.getMotivoRejeicao();
        if (this$motivoRejeicao == null ? other$motivoRejeicao != null : !this$motivoRejeicao.equals(other$motivoRejeicao)) return false;
        final java.lang.Object this$motivoRevocacao = this.getMotivoRevocacao();
        final java.lang.Object other$motivoRevocacao = other.getMotivoRevocacao();
        if (this$motivoRevocacao == null ? other$motivoRevocacao != null : !this$motivoRevocacao.equals(other$motivoRevocacao)) return false;
        final java.lang.Object this$permissoes = this.getPermissoes();
        final java.lang.Object other$permissoes = other.getPermissoes();
        if (this$permissoes == null ? other$permissoes != null : !this$permissoes.equals(other$permissoes)) return false;
        final java.lang.Object this$contasAutorizadas = this.getContasAutorizadas();
        final java.lang.Object other$contasAutorizadas = other.getContasAutorizadas();
        if (this$contasAutorizadas == null ? other$contasAutorizadas != null : !this$contasAutorizadas.equals(other$contasAutorizadas)) return false;
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
        return other instanceof ConsentimentoOpenFinanceDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $riskScore = this.getRiskScore();
        result = result * PRIME + ($riskScore == null ? 43 : $riskScore.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $consentId = this.getConsentId();
        result = result * PRIME + ($consentId == null ? 43 : $consentId.hashCode());
        final java.lang.Object $clientId = this.getClientId();
        result = result * PRIME + ($clientId == null ? 43 : $clientId.hashCode());
        final java.lang.Object $clientName = this.getClientName();
        result = result * PRIME + ($clientName == null ? 43 : $clientName.hashCode());
        final java.lang.Object $clientUri = this.getClientUri();
        result = result * PRIME + ($clientUri == null ? 43 : $clientUri.hashCode());
        final java.lang.Object $logoUri = this.getLogoUri();
        result = result * PRIME + ($logoUri == null ? 43 : $logoUri.hashCode());
        final java.lang.Object $tosUri = this.getTosUri();
        result = result * PRIME + ($tosUri == null ? 43 : $tosUri.hashCode());
        final java.lang.Object $policyUri = this.getPolicyUri();
        result = result * PRIME + ($policyUri == null ? 43 : $policyUri.hashCode());
        final java.lang.Object $softwareId = this.getSoftwareId();
        result = result * PRIME + ($softwareId == null ? 43 : $softwareId.hashCode());
        final java.lang.Object $softwareVersion = this.getSoftwareVersion();
        result = result * PRIME + ($softwareVersion == null ? 43 : $softwareVersion.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $tipoConsentimento = this.getTipoConsentimento();
        result = result * PRIME + ($tipoConsentimento == null ? 43 : $tipoConsentimento.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        final java.lang.Object $dataRejeicao = this.getDataRejeicao();
        result = result * PRIME + ($dataRejeicao == null ? 43 : $dataRejeicao.hashCode());
        final java.lang.Object $dataRevocacao = this.getDataRevocacao();
        result = result * PRIME + ($dataRevocacao == null ? 43 : $dataRevocacao.hashCode());
        final java.lang.Object $motivoRejeicao = this.getMotivoRejeicao();
        result = result * PRIME + ($motivoRejeicao == null ? 43 : $motivoRejeicao.hashCode());
        final java.lang.Object $motivoRevocacao = this.getMotivoRevocacao();
        result = result * PRIME + ($motivoRevocacao == null ? 43 : $motivoRevocacao.hashCode());
        final java.lang.Object $permissoes = this.getPermissoes();
        result = result * PRIME + ($permissoes == null ? 43 : $permissoes.hashCode());
        final java.lang.Object $contasAutorizadas = this.getContasAutorizadas();
        result = result * PRIME + ($contasAutorizadas == null ? 43 : $contasAutorizadas.hashCode());
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
        return "ConsentimentoOpenFinanceDTO(consentId=" + this.getConsentId() + ", clientId=" + this.getClientId() + ", clientName=" + this.getClientName() + ", clientUri=" + this.getClientUri() + ", logoUri=" + this.getLogoUri() + ", tosUri=" + this.getTosUri() + ", policyUri=" + this.getPolicyUri() + ", softwareId=" + this.getSoftwareId() + ", softwareVersion=" + this.getSoftwareVersion() + ", status=" + this.getStatus() + ", tipoConsentimento=" + this.getTipoConsentimento() + ", dataExpiracao=" + this.getDataExpiracao() + ", dataAprovacao=" + this.getDataAprovacao() + ", dataRejeicao=" + this.getDataRejeicao() + ", dataRevocacao=" + this.getDataRevocacao() + ", motivoRejeicao=" + this.getMotivoRejeicao() + ", motivoRevocacao=" + this.getMotivoRevocacao() + ", permissoes=" + this.getPermissoes() + ", contasAutorizadas=" + this.getContasAutorizadas() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", deviceId=" + this.getDeviceId() + ", geolocation=" + this.getGeolocation() + ", riskScore=" + this.getRiskScore() + ", riskLevel=" + this.getRiskLevel() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoOpenFinanceDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoOpenFinanceDTO(final String consentId, final String clientId, final String clientName, final String clientUri, final String logoUri, final String tosUri, final String policyUri, final String softwareId, final String softwareVersion, final String status, final String tipoConsentimento, final LocalDateTime dataExpiracao, final LocalDateTime dataAprovacao, final LocalDateTime dataRejeicao, final LocalDateTime dataRevocacao, final String motivoRejeicao, final String motivoRevocacao, final List<String> permissoes, final List<Long> contasAutorizadas, final String ipAddress, final String userAgent, final String deviceId, final String geolocation, final Double riskScore, final String riskLevel, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.consentId = consentId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientUri = clientUri;
        this.logoUri = logoUri;
        this.tosUri = tosUri;
        this.policyUri = policyUri;
        this.softwareId = softwareId;
        this.softwareVersion = softwareVersion;
        this.status = status;
        this.tipoConsentimento = tipoConsentimento;
        this.dataExpiracao = dataExpiracao;
        this.dataAprovacao = dataAprovacao;
        this.dataRejeicao = dataRejeicao;
        this.dataRevocacao = dataRevocacao;
        this.motivoRejeicao = motivoRejeicao;
        this.motivoRevocacao = motivoRevocacao;
        this.permissoes = permissoes;
        this.contasAutorizadas = contasAutorizadas;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceId = deviceId;
        this.geolocation = geolocation;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
