package com.aurix.platform.payments.pix.config;

import com.aurix.platform.payments.pix.client.BacenPixClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(BacenPixClient.class)
public class PixHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(PixHttpConfig.class);

    @Value("${aurix.payments.spi-url}")
    private String spiUrl;

    @Bean
    public RestClient bacenRestClient() {
        return RestClient.builder()
            .baseUrl(spiUrl)
            .build();
    }

    @Bean
    public BacenPixClient bacenPixClient(RestClient bacenRestClient) {
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(bacenRestClient))
            .build()
            .createClient(BacenPixClient.class);
    }
}
