package com.aurix.platform.credit.consignado.config;

import com.aurix.platform.credit.consignado.client.ContaSalarioClient;
import com.aurix.platform.credit.consignado.client.SrccClient;
import com.aurix.platform.credit.consignado.client.DataprevClient;
import com.aurix.platform.credit.consignado.client.SiafiClient;
import com.aurix.platform.credit.consignado.client.ESocialClient;
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
@ImportHttpServices({ContaSalarioClient.class, SrccClient.class, DataprevClient.class, SiafiClient.class, ESocialClient.class})
public class ConsignadoHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(ConsignadoHttpConfig.class);

    @Value("${aurix.consignado.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient consignadoRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public ContaSalarioClient contaSalarioClient(RestClient consignadoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(consignadoRestClient))
            .build()
            .createClient(ContaSalarioClient.class);
    }

    @Bean
    public SrccClient srccClient(RestClient consignadoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(consignadoRestClient))
            .build()
            .createClient(SrccClient.class);
    }

    @Bean
    public DataprevClient dataprevClient(RestClient consignadoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(consignadoRestClient))
            .build()
            .createClient(DataprevClient.class);
    }

    @Bean
    public SiafiClient siafiClient(RestClient consignadoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(consignadoRestClient))
            .build()
            .createClient(SiafiClient.class);
    }

    @Bean
    public ESocialClient eSocialClient(RestClient consignadoRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(consignadoRestClient))
            .build()
            .createClient(ESocialClient.class);
    }
}
