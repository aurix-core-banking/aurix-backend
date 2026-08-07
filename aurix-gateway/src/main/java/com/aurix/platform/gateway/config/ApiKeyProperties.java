package com.aurix.platform.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "aurix.gateway.api-key")
public class ApiKeyProperties {
    private boolean enabled = false;
    private boolean required = false;
    private String headerName = "X-API-Key";
    private String tenantHeader = "X-Tenant-Id";
    private String planHeader = "X-Plan";
    private List<String> exemptPaths = new ArrayList<>();
    private Map<String, ApiKeyEntry> keys = new HashMap<>();
    private Map<String, Integer> planLimits = defaultPlanLimits();


    public static class ApiKeyEntry {
        private String plan = "free";
        private String tenantId;

        @java.lang.SuppressWarnings("all")
        public ApiKeyEntry() {
        }

        @java.lang.SuppressWarnings("all")
        public String getPlan() {
            return this.plan;
        }

        @java.lang.SuppressWarnings("all")
        public String getTenantId() {
            return this.tenantId;
        }

        @java.lang.SuppressWarnings("all")
        public void setPlan(final String plan) {
            this.plan = plan;
        }

        @java.lang.SuppressWarnings("all")
        public void setTenantId(final String tenantId) {
            this.tenantId = tenantId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof ApiKeyProperties.ApiKeyEntry)) return false;
            final ApiKeyProperties.ApiKeyEntry other = (ApiKeyProperties.ApiKeyEntry) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$plan = this.getPlan();
            final java.lang.Object other$plan = other.getPlan();
            if (this$plan == null ? other$plan != null : !this$plan.equals(other$plan)) return false;
            final java.lang.Object this$tenantId = this.getTenantId();
            final java.lang.Object other$tenantId = other.getTenantId();
            if (this$tenantId == null ? other$tenantId != null : !this$tenantId.equals(other$tenantId)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof ApiKeyProperties.ApiKeyEntry;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $plan = this.getPlan();
            result = result * PRIME + ($plan == null ? 43 : $plan.hashCode());
            final java.lang.Object $tenantId = this.getTenantId();
            result = result * PRIME + ($tenantId == null ? 43 : $tenantId.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ApiKeyProperties.ApiKeyEntry(plan=" + this.getPlan() + ", tenantId=" + this.getTenantId() + ")";
        }
    }

    private static Map<String, Integer> defaultPlanLimits() {
        Map<String, Integer> limits = new HashMap<>();
        limits.put("free", 10);
        limits.put("starter", 60);
        limits.put("growth", 300);
        limits.put("enterprise", 1000);
        limits.put("sandbox", 30);
        return limits;
    }

    public int getLimitForPlan(String plan) {
        if (plan == null) plan = "free";
        return planLimits.getOrDefault(plan, 10);
    }

    @java.lang.SuppressWarnings("all")
    public ApiKeyProperties() {
    }

    @java.lang.SuppressWarnings("all")
    public boolean isEnabled() {
        return this.enabled;
    }

    @java.lang.SuppressWarnings("all")
    public boolean isRequired() {
        return this.required;
    }

    @java.lang.SuppressWarnings("all")
    public String getHeaderName() {
        return this.headerName;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantHeader() {
        return this.tenantHeader;
    }

    @java.lang.SuppressWarnings("all")
    public String getPlanHeader() {
        return this.planHeader;
    }

    @java.lang.SuppressWarnings("all")
    public List<String> getExemptPaths() {
        return this.exemptPaths;
    }

    @java.lang.SuppressWarnings("all")
    public Map<String, ApiKeyEntry> getKeys() {
        return this.keys;
    }

    @java.lang.SuppressWarnings("all")
    public Map<String, Integer> getPlanLimits() {
        return this.planLimits;
    }

    @java.lang.SuppressWarnings("all")
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequired(final boolean required) {
        this.required = required;
    }

    @java.lang.SuppressWarnings("all")
    public void setHeaderName(final String headerName) {
        this.headerName = headerName;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantHeader(final String tenantHeader) {
        this.tenantHeader = tenantHeader;
    }

    @java.lang.SuppressWarnings("all")
    public void setPlanHeader(final String planHeader) {
        this.planHeader = planHeader;
    }

    @java.lang.SuppressWarnings("all")
    public void setExemptPaths(final List<String> exemptPaths) {
        this.exemptPaths = exemptPaths;
    }

    @java.lang.SuppressWarnings("all")
    public void setKeys(final Map<String, ApiKeyEntry> keys) {
        this.keys = keys;
    }

    @java.lang.SuppressWarnings("all")
    public void setPlanLimits(final Map<String, Integer> planLimits) {
        this.planLimits = planLimits;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiKeyProperties)) return false;
        final ApiKeyProperties other = (ApiKeyProperties) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isEnabled() != other.isEnabled()) return false;
        if (this.isRequired() != other.isRequired()) return false;
        final java.lang.Object this$keys = this.getKeys();
        final java.lang.Object other$keys = other.getKeys();
        if (this$keys == null ? other$keys != null : !this$keys.equals(other$keys)) return false;
        final java.lang.Object this$planLimits = this.getPlanLimits();
        final java.lang.Object other$planLimits = other.getPlanLimits();
        if (this$planLimits == null ? other$planLimits != null : !this$planLimits.equals(other$planLimits)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ApiKeyProperties;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isEnabled() ? 79 : 97);
        result = result * PRIME + (this.isRequired() ? 79 : 97);
        final java.lang.Object $keys = this.getKeys();
        result = result * PRIME + ($keys == null ? 43 : $keys.hashCode());
        final java.lang.Object $planLimits = this.getPlanLimits();
        result = result * PRIME + ($planLimits == null ? 43 : $planLimits.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ApiKeyProperties(enabled=" + this.isEnabled() + ", required=" + this.isRequired() + ", keys=" + this.getKeys() + ", planLimits=" + this.getPlanLimits() + ")";
    }
}
