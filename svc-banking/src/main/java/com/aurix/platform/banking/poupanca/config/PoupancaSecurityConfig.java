package com.aurix.platform.banking.poupanca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class PoupancaSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/contas/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/contas/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/contas/**").authenticated()
                .requestMatchers("/movimentacoes/**").authenticated()
                .requestMatchers("/aniversario/**").authenticated()
                .requestMatchers("/extrato/**").authenticated()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
