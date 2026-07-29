package com.aurix.platform.banking.salario.config;

import com.aurix.platform.banking.salario.client.ContaCorrenteClient;
import com.aurix.platform.shared.tenant.TenantContext;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@EnableWebSecurity
public class TestSalarioConfig {

    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, String> kafkaTemplate() {
        return Mockito.mock(KafkaTemplate.class);
    }

    @Bean
    @Primary
    public JwtDecoder jwtDecoder() {
        return Mockito.mock(JwtDecoder.class);
    }

    @Bean
    @Primary
    public ContaCorrenteClient contaCorrenteClient() {
        ContaCorrenteClient mock = Mockito.mock(ContaCorrenteClient.class);
        Mockito.when(mock.getConta(Mockito.anyLong()))
            .thenReturn(new ContaCorrenteClient.ContaCorrenteResponse(1L, "ATIVA"));
        return mock;
    }

    @Bean
    public Filter tenantContextFilter() {
        return (request, response, chain) -> {
            try {
                if (request instanceof HttpServletRequest httpRequest) {
                    String tenantId = httpRequest.getHeader(TenantContext.HEADER_TENANT_ID);
                    if (tenantId != null && !tenantId.isBlank()) {
                        TenantContext.setTenantId(tenantId);
                    }
                }
                chain.doFilter(request, response);
            } finally {
                TenantContext.clear();
            }
        };
    }
}
