package com.aurix.platform.credit.consignado.config;

import com.aurix.platform.credit.consignado.client.ContaSalarioClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class ConsignadoTestConfig {

    @Bean
    @Primary
    public ContaSalarioClient contaSalarioClient() {
        return mock(ContaSalarioClient.class);
    }
}
