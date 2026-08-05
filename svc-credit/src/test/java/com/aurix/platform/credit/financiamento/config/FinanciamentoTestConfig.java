package com.aurix.platform.credit.financiamento.config;

import com.aurix.platform.credit.financiamento.client.CartorioRgiClient;
import com.aurix.platform.credit.financiamento.client.ContaCorrenteClient;
import com.aurix.platform.credit.financiamento.client.DetranClient;
import com.aurix.platform.credit.financiamento.client.BacenClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class FinanciamentoTestConfig {

    @Bean
    @Primary
    public ContaCorrenteClient contaCorrenteClient() {
        return mock(ContaCorrenteClient.class);
    }

    @Bean
    @Primary
    public CartorioRgiClient cartorioRgiClient() {
        return mock(CartorioRgiClient.class);
    }

    @Bean
    @Primary
    public DetranClient detranClient() {
        return mock(DetranClient.class);
    }

    @Bean
    @Primary
    public BacenClient bacenClient() {
        return mock(BacenClient.class);
    }
}
