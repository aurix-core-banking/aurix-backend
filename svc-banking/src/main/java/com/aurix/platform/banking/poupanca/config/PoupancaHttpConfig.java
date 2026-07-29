package com.aurix.platform.banking.poupanca.config;

import com.aurix.platform.banking.poupanca.client.BacenClient;
import com.aurix.platform.banking.poupanca.client.ContaCorrenteClient;
import com.aurix.platform.banking.poupanca.client.TaxClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@EnableResilientMethods
@ImportHttpServices({ContaCorrenteClient.class, TaxClient.class, BacenClient.class})
public class PoupancaHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(PoupancaHttpConfig.class);

    @Value("${aurix.poupanca.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient poupancaRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
