package com.aurix.platform.shared.tenant;

public final class TenantContext {

    public static final String HEADER_TENANT_ID = "X-Tenant-Id";
    public static final String DEFAULT_TENANT_ID = "default";

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId != null && !tenantId.isBlank() ? tenantId.trim() : DEFAULT_TENANT_ID);
    }

    public static String getTenantId() {
        String tenant = currentTenant.get();
        return tenant != null ? tenant : DEFAULT_TENANT_ID;
    }

    public static void clear() {
        currentTenant.remove();
    }
}
