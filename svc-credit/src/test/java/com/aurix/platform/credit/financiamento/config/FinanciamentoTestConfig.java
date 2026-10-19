package com.aurix.platform.credit.financiamento.config;

import com.aurix.platform.credit.financiamento.client.CartorioRgiClient;
import com.aurix.platform.credit.financiamento.client.ContaCorrenteClient;
import com.aurix.platform.credit.financiamento.client.DetranClient;
import com.aurix.platform.credit.financiamento.client.BacenClient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class FinanciamentoTestConfig {

    @Bean
    @Primary
    public ContaCorrenteClient mockContaCorrenteClient() {
        return mock(ContaCorrenteClient.class);
    }

    @Bean
    @Primary
    public CartorioRgiClient mockCartorioRgiClient() {
        CartorioRgiClient client = mock(CartorioRgiClient.class);
        when(client.registrarGarantia(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new CartorioRgiClient.RegistroResponse(
                "PROTO-1", "MAT-1", LocalDate.now()));
        when(client.consultar(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(new CartorioRgiClient.SituacaoRegistro("MAT-1", "BAIXADA", "CARTORIO"));
        return client;
    }

    @Bean
    @Primary
    public DetranClient mockDetranClient() {
        DetranClient client = mock(DetranClient.class);
        when(client.registrarGarantia(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new DetranClient.DetranResponse("PROTO-DETRAN", "REGI-1", "ATIVO"));
        when(client.consultarVeiculo(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(new DetranClient.DadosVeiculo(
                "ABC-1234", "CHASSI-1", "FIAT", "ARGO", 2024, "ALIENADO"));
        return client;
    }

    @Bean
    @Primary
    public BacenClient mockBacenClient() {
        return mock(BacenClient.class);
    }

    @Bean
    @Primary
    @SuppressWarnings("unchecked")
    public org.springframework.kafka.core.KafkaTemplate<String, String> kafkaTemplate() {
        return mock(org.springframework.kafka.core.KafkaTemplate.class);
    }
}
