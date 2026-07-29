package com.aurix.platform.credit.financiamento.config;

import com.aurix.platform.credit.financiamento.client.ContaCorrenteClient;
import com.aurix.platform.credit.financiamento.client.CartorioRgiClient;
import com.aurix.platform.credit.financiamento.client.DetranClient;
import com.aurix.platform.credit.financiamento.client.BacenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.registry.ImportHttpServices;
import org.springframework.resilience.annotation.EnableResilientMethods;

@Configuration
@EnableResilientMethods
@ImportHttpServices({ContaCorrenteClient.class, CartorioRgiClient.class, DetranClient.class, BacenClient.class})
public class FinanciamentoHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(FinanciamentoHttpConfig.class);

    @Value("${aurix.financiamento.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient financiamentoRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public ContaCorrenteClient contaCorrenteClient(RestClient financiamentoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(financiamentoRestClient))
            .build()
            .createClient(ContaCorrenteClient.class);
    }

    @Bean
    public CartorioRgiClient cartorioRgiClient(RestClient financiamentoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(financiamentoRestClient))
            .build()
            .createClient(CartorioRgiClient.class);
    }

    @Bean
    public DetranClient detranClient(RestClient financiamentoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(financiamentoRestClient))
            .build()
            .createClient(DetranClient.class);
    }

    @Bean
    public BacenClient bacenClient(RestClient financiamentoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(financiamentoRestClient))
            .build()
            .createClient(BacenClient.class);
    }
}
