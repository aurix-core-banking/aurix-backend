package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenant_feature_flags", schema = "aurix", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id", "feature_key"})})
public class TenantFeatureFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, length = 64)
    private String tenantId;
    @Column(name = "feature_key", nullable = false, length = 128)
    private String featureKey;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
    @Column(name = "descricao", length = 512)
    private String descricao;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    public static class TenantFeatureFlagBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String featureKey;
        @java.lang.SuppressWarnings("all")
        private Boolean enabled;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        TenantFeatureFlagBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder featureKey(final String featureKey) {
            this.featureKey = featureKey;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder enabled(final Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag.TenantFeatureFlagBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlag build() {
            return new TenantFeatureFlag(this.id, this.tenantId, this.featureKey, this.enabled, this.descricao, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TenantFeatureFlag.TenantFeatureFlagBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", featureKey=" + this.featureKey + ", enabled=" + this.enabled + ", descricao=" + this.descricao + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TenantFeatureFlag.TenantFeatureFlagBuilder builder() {
        return new TenantFeatureFlag.TenantFeatureFlagBuilder();
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
    public String getFeatureKey() {
        return this.featureKey;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getEnabled() {
        return this.enabled;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
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
    public void setFeatureKey(final String featureKey) {
        this.featureKey = featureKey;
    }

    @java.lang.SuppressWarnings("all")
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
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
    public TenantFeatureFlag() {
    }

    @java.lang.SuppressWarnings("all")
    public TenantFeatureFlag(final Long id, final String tenantId, final String featureKey, final Boolean enabled, final String descricao, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.featureKey = featureKey;
        this.enabled = enabled;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
