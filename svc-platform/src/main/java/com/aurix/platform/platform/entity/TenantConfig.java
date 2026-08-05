package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tenant_config", schema = "aurix", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"})})
public class TenantConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, unique = true, length = 64)
    private String tenantId;
    @Column(name = "logo_url", length = 512)
    private String logoUrl;
    @Column(name = "cor_primaria", length = 20)
    private String corPrimaria;
    @Column(name = "cor_secundaria", length = 20)
    private String corSecundaria;
    @Column(name = "termos_uso_url", length = 512)
    private String termosUsoUrl;
    @Column(name = "limites", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private LimitesConfig limites;
    @Column(name = "produtos_habilitados", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> produtosHabilitados;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    public static class LimitesConfig {
        private Integer maxContasAtivas;
        private Integer maxTransacoesMes;
        private Integer maxChamadasApiMes;

        @java.lang.SuppressWarnings("all")
        public Integer getMaxContasAtivas() {
            return this.maxContasAtivas;
        }

        @java.lang.SuppressWarnings("all")
        public Integer getMaxTransacoesMes() {
            return this.maxTransacoesMes;
        }

        @java.lang.SuppressWarnings("all")
        public Integer getMaxChamadasApiMes() {
            return this.maxChamadasApiMes;
        }

        @java.lang.SuppressWarnings("all")
        public void setMaxContasAtivas(final Integer maxContasAtivas) {
            this.maxContasAtivas = maxContasAtivas;
        }

        @java.lang.SuppressWarnings("all")
        public void setMaxTransacoesMes(final Integer maxTransacoesMes) {
            this.maxTransacoesMes = maxTransacoesMes;
        }

        @java.lang.SuppressWarnings("all")
        public void setMaxChamadasApiMes(final Integer maxChamadasApiMes) {
            this.maxChamadasApiMes = maxChamadasApiMes;
        }

        @java.lang.SuppressWarnings("all")
        public LimitesConfig() {
        }

        @java.lang.SuppressWarnings("all")
        public LimitesConfig(final Integer maxContasAtivas, final Integer maxTransacoesMes, final Integer maxChamadasApiMes) {
            this.maxContasAtivas = maxContasAtivas;
            this.maxTransacoesMes = maxTransacoesMes;
            this.maxChamadasApiMes = maxChamadasApiMes;
        }
    }


    @java.lang.SuppressWarnings("all")
    public static class TenantConfigBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String logoUrl;
        @java.lang.SuppressWarnings("all")
        private String corPrimaria;
        @java.lang.SuppressWarnings("all")
        private String corSecundaria;
        @java.lang.SuppressWarnings("all")
        private String termosUsoUrl;
        @java.lang.SuppressWarnings("all")
        private LimitesConfig limites;
        @java.lang.SuppressWarnings("all")
        private List<String> produtosHabilitados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        TenantConfigBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder logoUrl(final String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder corPrimaria(final String corPrimaria) {
            this.corPrimaria = corPrimaria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder corSecundaria(final String corSecundaria) {
            this.corSecundaria = corSecundaria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder termosUsoUrl(final String termosUsoUrl) {
            this.termosUsoUrl = termosUsoUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder limites(final LimitesConfig limites) {
            this.limites = limites;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder produtosHabilitados(final List<String> produtosHabilitados) {
            this.produtosHabilitados = produtosHabilitados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantConfig.TenantConfigBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TenantConfig build() {
            return new TenantConfig(this.id, this.tenantId, this.logoUrl, this.corPrimaria, this.corSecundaria, this.termosUsoUrl, this.limites, this.produtosHabilitados, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TenantConfig.TenantConfigBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", logoUrl=" + this.logoUrl + ", corPrimaria=" + this.corPrimaria + ", corSecundaria=" + this.corSecundaria + ", termosUsoUrl=" + this.termosUsoUrl + ", limites=" + this.limites + ", produtosHabilitados=" + this.produtosHabilitados + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TenantConfig.TenantConfigBuilder builder() {
        return new TenantConfig.TenantConfigBuilder();
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
    public String getLogoUrl() {
        return this.logoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public String getCorPrimaria() {
        return this.corPrimaria;
    }

    @java.lang.SuppressWarnings("all")
    public String getCorSecundaria() {
        return this.corSecundaria;
    }

    @java.lang.SuppressWarnings("all")
    public String getTermosUsoUrl() {
        return this.termosUsoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public LimitesConfig getLimites() {
        return this.limites;
    }

    @java.lang.SuppressWarnings("all")
    public List<String> getProdutosHabilitados() {
        return this.produtosHabilitados;
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
    public void setLogoUrl(final String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public void setCorPrimaria(final String corPrimaria) {
        this.corPrimaria = corPrimaria;
    }

    @java.lang.SuppressWarnings("all")
    public void setCorSecundaria(final String corSecundaria) {
        this.corSecundaria = corSecundaria;
    }

    @java.lang.SuppressWarnings("all")
    public void setTermosUsoUrl(final String termosUsoUrl) {
        this.termosUsoUrl = termosUsoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimites(final LimitesConfig limites) {
        this.limites = limites;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutosHabilitados(final List<String> produtosHabilitados) {
        this.produtosHabilitados = produtosHabilitados;
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
    public TenantConfig() {
    }

    @java.lang.SuppressWarnings("all")
    public TenantConfig(final Long id, final String tenantId, final String logoUrl, final String corPrimaria, final String corSecundaria, final String termosUsoUrl, final LimitesConfig limites, final List<String> produtosHabilitados, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.logoUrl = logoUrl;
        this.corPrimaria = corPrimaria;
        this.corSecundaria = corSecundaria;
        this.termosUsoUrl = termosUsoUrl;
        this.limites = limites;
        this.produtosHabilitados = produtosHabilitados;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
