package com.aurix.platform.banking.salario.config;

import com.aurix.platform.banking.salario.client.ContaCorrenteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(ContaCorrenteClient.class)
@EnableResilientMethods
public class SalarioHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(SalarioHttpConfig.class);

    @Value("${aurix.salario.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient salarioRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
