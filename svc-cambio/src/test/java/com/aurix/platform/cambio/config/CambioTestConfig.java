package com.aurix.platform.cambio.config;

import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import java.util.concurrent.CompletableFuture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@EnableWebSecurity
@TestConfiguration
public class CambioTestConfig {

    @Bean
    @Primary
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        var template = mock(KafkaTemplate.class);
        when(template.send(any(ProducerRecord.class)))
            .thenReturn(CompletableFuture.completedFuture(mock(SendResult.class)));
        return template;
    }

    @Bean
    @Primary
    public BacenClient bacenClient() {
        return mock(BacenClient.class);
    }

    @Bean
    @Primary
    public SwiftClient swiftClient() {
        return mock(SwiftClient.class);
    }

    @Bean
    @Primary
    public ComplianceClient complianceClient() {
        return mock(ComplianceClient.class);
    }

    @Bean
    @Primary
    public ParceiroCambioClient parceiroCambioClient() {
        return mock(ParceiroCambioClient.class);
    }

    @Bean
    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }
}
