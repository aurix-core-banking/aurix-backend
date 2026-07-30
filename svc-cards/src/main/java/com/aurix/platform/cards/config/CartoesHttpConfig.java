package com.aurix.platform.cards.config;

import com.aurix.platform.cards.client.ContaCorrenteClient;
import com.aurix.platform.cards.client.EloClient;
import com.aurix.platform.cards.client.GetNetClient;
import com.aurix.platform.cards.client.MastercardClient;
import com.aurix.platform.cards.client.MlFraudClient;
import com.aurix.platform.cards.client.RedeClient;
import com.aurix.platform.cards.client.StoneClient;
import com.aurix.platform.cards.client.VisaClient;
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
@ImportHttpServices({
    ContaCorrenteClient.class, MlFraudClient.class,
    VisaClient.class, MastercardClient.class, EloClient.class,
    RedeClient.class, StoneClient.class, GetNetClient.class
})
public class CartoesHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(CartoesHttpConfig.class);

    @Value("${aurix.cartoes.base-url:http://localhost:8080}")
    private String baseUrl;

    @Bean
    public RestClient cartoesRestClient() {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
