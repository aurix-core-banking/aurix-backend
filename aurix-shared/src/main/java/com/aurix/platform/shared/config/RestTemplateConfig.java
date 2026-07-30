package com.aurix.platform.shared.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração do RestTemplate para integração entre módulos.
 */
@Configuration
public class RestTemplateConfig {

    /** Tempo de conexão padrão: 5 segundos. */
    private static final int CONNECT_TIMEOUT = 5000;

    /** Tempo de leitura padrão: 10 segundos. */
    private static final int READ_TIMEOUT = 10000;

    /**
     * Cria o bean do RestTemplate.
     *
     * @return RestTemplate configurado
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }

    /**
     * Cria a factory de requisições HTTP.
     *
     * @return ClientHttpRequestFactory configurada
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return factory;
    }
}
