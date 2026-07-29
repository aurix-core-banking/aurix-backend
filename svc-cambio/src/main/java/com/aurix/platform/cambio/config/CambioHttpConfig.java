package com.aurix.platform.cambio.config;

import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@EnableResilientMethods
@Profile("!test")
@ImportHttpServices({BacenClient.class, SwiftClient.class, ComplianceClient.class, ParceiroCambioClient.class})
public class CambioHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(CambioHttpConfig.class);

    @Value("${aurix.cambio.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient cambioRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public BacenClient bacenClient(RestClient cambioRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(cambioRestClient))
            .build()
            .createClient(BacenClient.class);
    }

    @Bean
    public SwiftClient swiftClient(RestClient cambioRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(cambioRestClient))
            .build()
            .createClient(SwiftClient.class);
    }

    @Bean
    public ComplianceClient complianceClient(RestClient cambioRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(cambioRestClient))
            .build()
            .createClient(ComplianceClient.class);
    }

    @Bean
    public ParceiroCambioClient parceiroCambioClient(RestClient cambioRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(cambioRestClient))
            .build()
            .createClient(ParceiroCambioClient.class);
    }
}
