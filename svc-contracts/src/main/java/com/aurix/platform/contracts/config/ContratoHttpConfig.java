package com.aurix.platform.contracts.config;

import com.aurix.platform.contracts.client.ClienteClient;
import com.aurix.platform.contracts.client.ProdutoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices({ClienteClient.class, ProdutoClient.class})
public class ContratoHttpConfig {

    @Value("${aurix.contracts.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient contractRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public ClienteClient clienteClient(RestClient contractRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(contractRestClient))
            .build()
            .createClient(ClienteClient.class);
    }

    @Bean
    public ProdutoClient produtoClient(RestClient contractRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(contractRestClient))
            .build()
            .createClient(ProdutoClient.class);
    }
}
