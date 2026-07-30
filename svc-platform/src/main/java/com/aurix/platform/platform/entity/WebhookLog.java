package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_log", schema = "aurix")
public class WebhookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, length = 64)
    private String tenantId;
    @Column(name = "evento", nullable = false, length = 64)
    private String evento;
    @Column(name = "payload", columnDefinition = "text")
    private String payload;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEnvio status;
    @Column(name = "tentativas")
    private Integer tentativas;
    @Column(name = "response_code")
    private Integer responseCode;
    @Column(name = "response_body", length = 2000)
    private String responseBody;
    @Column(name = "proxima_tentativa")
    private LocalDateTime proximaTentativa;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;


    public enum StatusEnvio {
        PENDENTE, ENVIADO, FALHA, EXCEDIDO;
    }


    @java.lang.SuppressWarnings("all")
    public static class WebhookLogBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String evento;
        @java.lang.SuppressWarnings("all")
        private String payload;
        @java.lang.SuppressWarnings("all")
        private StatusEnvio status;
        @java.lang.SuppressWarnings("all")
        private Integer tentativas;
        @java.lang.SuppressWarnings("all")
        private Integer responseCode;
        @java.lang.SuppressWarnings("all")
        private String responseBody;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime proximaTentativa;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        WebhookLogBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder evento(final String evento) {
            this.evento = evento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder payload(final String payload) {
            this.payload = payload;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder status(final StatusEnvio status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder tentativas(final Integer tentativas) {
            this.tentativas = tentativas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder responseCode(final Integer responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder responseBody(final String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder proximaTentativa(final LocalDateTime proximaTentativa) {
            this.proximaTentativa = proximaTentativa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookLog.WebhookLogBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public WebhookLog build() {
            return new WebhookLog(this.id, this.tenantId, this.evento, this.payload, this.status, this.tentativas, this.responseCode, this.responseBody, this.proximaTentativa, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "WebhookLog.WebhookLogBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", evento=" + this.evento + ", payload=" + this.payload + ", status=" + this.status + ", tentativas=" + this.tentativas + ", responseCode=" + this.responseCode + ", responseBody=" + this.responseBody + ", proximaTentativa=" + this.proximaTentativa + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static WebhookLog.WebhookLogBuilder builder() {
        return new WebhookLog.WebhookLogBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public String getEvento() {
        return this.evento;
    }

    @java.lang.SuppressWarnings("all")
    public String getPayload() {
        return this.payload;
    }

    @java.lang.SuppressWarnings("all")
    public StatusEnvio getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativas() {
        return this.tentativas;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getResponseCode() {
        return this.responseCode;
    }

    @java.lang.SuppressWarnings("all")
    public String getResponseBody() {
        return this.responseBody;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getProximaTentativa() {
        return this.proximaTentativa;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setEvento(final String evento) {
        this.evento = evento;
    }

    @java.lang.SuppressWarnings("all")
    public void setPayload(final String payload) {
        this.payload = payload;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusEnvio status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativas(final Integer tentativas) {
        this.tentativas = tentativas;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponseCode(final Integer responseCode) {
        this.responseCode = responseCode;
    }

    @java.lang.SuppressWarnings("all")
    public void setResponseBody(final String responseBody) {
        this.responseBody = responseBody;
    }

    @java.lang.SuppressWarnings("all")
    public void setProximaTentativa(final LocalDateTime proximaTentativa) {
        this.proximaTentativa = proximaTentativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final Loc                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   