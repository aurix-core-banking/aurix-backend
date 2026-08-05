package com.aurix.platform.customer.security.config;

import com.aurix.platform.shared.crypto.CriptografiaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CriptografiaConfig {

    @Bean
    public CriptografiaService criptografiaService(
            @Value("${aurix.security.encryption.key-base64:}") String keyBase64) {
        if (keyBase64 == null || keyBase64.isBlank()) {
            throw new IllegalStateException("aurix.security.encryption.key-base64 deve ser definido (32 bytes em Base64)");
        }
        byte[] key = CriptografiaService.chaveDeBase64(keyBase64.trim());
        return new CriptografiaService(key);
    }
}
