package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.DeploymentProfile;
import com.aurix.platform.platform.entity.Instituicao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provider real de provisionamento por tenant.
 *
 * Ativo quando {@code aurix.provisioning.provider=real}. Para tenancy
 * MULTI_TENANT, cria o banco {@code aurix_tenant_<tenantId>} conectando-se como
 * administrador (postgres) e devolve a URL JDBC do banco criado. Para tenancy
 * SELF_HOSTED, nenhuma ação é necessária (banco único compartilhado).
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.provisioning.admin-jdbc-url} — URL do servidor Postgres (padrão localhost/postgres)</li>
 *   <li>{@code aurix.provisioning.admin-username} / {@code aurix.provisioning.admin-password} — credenciais de admin</li>
 * </ul>
 */
@Service
@ConditionalOnProperty(name = "aurix.provisioning.provider", havingValue = "real")
public class ProvisioningServiceReal implements ProvisioningService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProvisioningServiceReal.class);

    private final String adminJdbcUrl;
    private final String adminUsername;
    private final String adminPassword;

    public ProvisioningServiceReal(
            @Value("${aurix.provisioning.admin-jdbc-url:jdbc:postgresql://localhost:5432/postgres}") String adminJdbcUrl,
            @Value("${aurix.provisioning.admin-username:postgres}") String adminUsername,
            @Value("${aurix.provisioning.admin-password:}") String adminPassword) {
        this.adminJdbcUrl = adminJdbcUrl;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public ProvisioningResult provisionar(Instituicao instituicao) {
        if (instituicao.getDeploymentProfile() == null) {
            return new ProvisioningResult(false, "Deployment profile obrigatorio", null);
        }
        DeploymentProfile profile = instituicao.getDeploymentProfile();
        if (profile.getTenancy() != DeploymentProfile.TenancyType.MULTI_TENANT) {
            log.info("Self-hosted: banco unico compartilhado, nenhuma acao de provisionamento para tenant {}", instituicao.getTenantId());
            return new ProvisioningResult(true, "Self-hosted: banco unico compartilhado", null);
        }

        String dbName = "aurix_tenant_" + sanitizar(instituicao.getTenantId());
        String dbUrl = "jdbc:postgresql://localhost:5432/" + dbName;
        try (Connection conn = DriverManager.getConnection(adminJdbcUrl, adminUsername, adminPassword)) {
            if (bancoExiste(conn, dbName)) {
                log.info("Banco {} ja existe para tenant {}", dbName, instituicao.getTenantId());
                return new ProvisioningResult(true, "Banco ja provisionado", dbUrl);
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE " + dbName);
            }
            log.info("Banco {} criado para tenant {}", dbName, instituicao.getTenantId());
            return new ProvisioningResult(true, "Banco por tenant criado", dbUrl);
        } catch (Exception e) {
            log.error("Falha ao provisionar banco {} para tenant {}: {}", dbName, instituicao.getTenantId(), e.getMessage());
            return new ProvisioningResult(false, "Falha ao criar banco por tenant: " + e.getMessage(), null);
        }
    }

    private boolean bancoExiste(Connection conn, String dbName) throws SQLException {
        try (java.sql.ResultSet rs = conn.getMetaData().getCatalogs()) {
            while (rs.next()) {
                if (rs.getString("TABLE_CAT").equalsIgnoreCase(dbName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String sanitizar(String tenantId) {
        return tenantId == null ? "default" : tenantId.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}