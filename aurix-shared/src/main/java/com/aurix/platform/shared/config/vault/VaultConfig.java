package com.aurix.platform.shared.config.vault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;

/**
 * Vault integration para secrets.
 *
 * Para habilitar:
 *   spring.cloud.vault.uri=http://localhost:8200
 *   spring.cloud.vault.token=<ROOT_TOKEN>
 *
 * Os secrets ficam em aurix/database/postgres, aurix/encryption, etc.
 * O Vault injeta como properties Spring normais.
 */
@Configuration
@ConditionalOnProperty(name = "spring.cloud.vault.uri")
@VaultPropertySource(value = "aurix/database/postgres", propertyNamePrefix = "aurix.vault.db.")
@VaultPropertySource(value = "aurix/encryption", propertyNamePrefix = "aurix.vault.enc.")
@VaultPropertySource(value = "aurix/keycloak", propertyNamePrefix = "aurix.vault.keycloak.")
public class VaultConfig {

    private static final Logger log = LoggerFactory.getLogger(VaultConfig.class);

    @Value("${aurix.vault.db.password:}")
    private String dbPassword;

    public void onStartup() {
        if (dbPassword != null && !dbPassword.isBlank()) {
            log.info("Vault: secrets carregados com sucesso");
        }
    }
}
