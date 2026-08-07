package com.aurix.platform.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Segurança do Gateway.
 *
 * <p>Endpoints de health e actuator são públicos. Quando {@code aurix.security.jwt.enabled}
 * está ativo, as demais rotas exigem JWT válido (Keycloak via jwk-set-uri ou HMAC via secret).</p>
 */
@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    private static final String[] PONTOS_PUBLICOS = {
            "/actuator/health/**",
            "/actuator/info/**",
            "/health/**"
    };

    private final JwtProperties jwtProperties;

    public GatewaySecurityConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .authorizeExchange(auth -> {
                    auth.pathMatchers(PONTOS_PUBLICOS).permitAll();
                    if (jwtProperties.isEnabled()) {
                        auth.anyExchange().authenticated();
                    } else {
                        auth.anyExchange().permitAll();
                    }
                });

        if (jwtProperties.isEnabled()) {
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));
        }

        return http.build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "aurix.security.jwt", name = "enabled", havingValue = "true")
    public ReactiveJwtDecoder jwtDecoder() {
        if (jwtProperties.getJwkSetUri() != null && !jwtProperties.getJwkSetUri().isBlank()) {
            return NimbusReactiveJwtDecoder.withJwkSetUri(jwtProperties.getJwkSetUri()).build();
        }
        if (jwtProperties.getSecret() != null && !jwtProperties.getSecret().isBlank()) {
            SecretKey chave = new SecretKeySpec(
                    jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            return NimbusReactiveJwtDecoder.withSecretKey(chave).build();
        }
        throw new IllegalStateException(
                "aurix.security.jwt.enabled=true requer aurix.security.jwt.jwk-set-uri ou aurix.security.jwt.secret");
    }
}
