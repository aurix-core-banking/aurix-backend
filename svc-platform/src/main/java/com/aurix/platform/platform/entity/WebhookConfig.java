package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "webhook_config", schema = "aurix", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"})})
public class WebhookConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, unique = true, length = 64)
    private String tenantId;
    @Column(name = "url", nullable = false, length = 512)
    private String url;
    @Column(name = "eventos", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> eventos;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
    @Column(name = "secret", length = 256)
    private String secret;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    public static class WebhookConfigBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String url;
        @java.lang.SuppressWarnings("all")
        private List<String> eventos;
        @java.lang.SuppressWarnings("all")
        private Boolean ativo;
        @java.lang.SuppressWarnings("all")
        private String secret;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        WebhookConfigBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder url(final String url) {
            this.url = url;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder eventos(final List<String> eventos) {
            this.eventos = eventos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder ativo(final Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder secret(final String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public WebhookConfig.WebhookConfigBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public WebhookConfig build() {
            return new WebhookConfig(this.id, this.tenantId, this.url, this.eventos, this.ativo, this.secret, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "WebhookConfig.WebhookConfigBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", url=" + this.url + ", eventos=" + this.eventos + ", ativo=" + this.ativo + ", secret=" + this.secret + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static WebhookConfig.WebhookConfigBuilder builder() {
        return new WebhookConfig.WebhookConfigBuilder();
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
    public String getUrl() {
        return this.url;
    }

    @java.lang.SuppressWarnings("all")
    public List<String> getEventos() {
        return this.eventos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public String getSecret() {
        return this.secret;
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setUrl(final String url) {
        this.url = url;
    }

    @java.lang.SuppressWarnings("all")
    public void setEventos(final List<String> eventos) {
        this.eventos = eventos;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSecret(final String secret) {
        this.secret = secret;
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
    public WebhookConfig() {
    }

    @java.lang.SuppressWarnings("all")
    public WebhookConfig(final Long id, final String tenantId, final String url, final List<String> eventos, final Boolean ativo, final String secret, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.url = url;
        this.eventos = eventos;
        this.ativo = ativo;
        this.secret = secret;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
