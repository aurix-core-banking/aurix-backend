package com.aurix.platform.platform.dto;

public class TenantFeatureFlagDTO {
    private String tenantId;
    private String featureKey;
    private Boolean enabled;
    private String descricao;


    @java.lang.SuppressWarnings("all")
    public static class TenantFeatureFlagDTOBuilder {
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private String featureKey;
        @java.lang.SuppressWarnings("all")
        private Boolean enabled;
        @java.lang.SuppressWarnings("all")
        private String descricao;

        @java.lang.SuppressWarnings("all")
        TenantFeatureFlagDTOBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder featureKey(final String featureKey) {
            this.featureKey = featureKey;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder enabled(final Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public TenantFeatureFlagDTO build() {
            return new TenantFeatureFlagDTO(this.tenantId, this.featureKey, this.enabled, this.descricao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder(tenantId=" + this.tenantId + ", featureKey=" + this.featureKey + ", enabled=" + this.enabled + ", descricao=" + this.descricao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder builder() {
        return new TenantFeatureFlagDTO.TenantFeatureFlagDTOBuilder();
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TenantFeatureFlagDTO)) return false;
        final TenantFeatureFlagDTO other = (TenantFeatureFlagDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$enabled = this.getEnabled();
        final java.lang.Object other$enabled = other.getEnabled();
        if (this$enabled == null ? other$enabled != null : !this$enabled.equals(other$enabled)) return false;
        final java.lang.Object this$tenantId = this.getTenantId();
        final java.lang.Object other$tenantId = other.getTenantId();
        if (this$tenantId == null ? other$tenantId != null : !this$tenantId.equals(other$tenantId)) return false;
        final java.lang.Object this$featureKey = this.getFeatureKey();
        final java.lang.Object other$featureKey = other.getFeatureKey();
        if (this$featureKey == null ? other$featureKey != null : !this$featureKey.equals(other$featureKey)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TenantFeatureFlagDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $enabled = this.getEnabled();
        result = result * PRIME + ($enabled == null ? 43 : $enabled.hashCode());
        final java.lang.Object $tenantId = this.getTenantId();
        result = result * PRIME + ($tenantId == null ? 43 : $tenantId.hashCode());
        final java.lang.Object $featureKey = this.getFeatureKey();
        result = result * PRIME + ($featureKey == null ? 43 : $featureKey.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TenantFeatureFlagDTO(tenantId=" + this.getTenantId() + ", featureKey=" + this.getFeatureKey() + ", enabled=" + this.getEnabled() + ", descricao=" + this.getDescricao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TenantFeatureFlagDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public TenantFeatureFlagDTO(final String tenantId, final String featureKey, final Boolean enabled, final String descricao) {
        this.tenantId = tenantId;
        this.featureKey = featureKey;
        this.enabled = enabled;
        this.descricao = descricao;
    }
}
