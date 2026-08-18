package com.aurix.platform.intelligence.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MlClientConfig {

    @Bean
    public RestClient mlRestClient(@Value("${aurix.ml.url:http://localhost:8000}") String mlUrl) {
        return RestClient.builder()
                .baseUrl(mlUrl)
                .build();
    }
}
