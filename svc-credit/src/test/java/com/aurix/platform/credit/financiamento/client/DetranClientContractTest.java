package com.aurix.platform.credit.financiamento.client;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class DetranClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final DetranClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(DetranClient.class);

    @Test
    void registrarGarantia() {
        server.expect(requestTo("http://localhost:8080/api/detran/garantias"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"protocolo":"PROT-001","registro":"REG-001","status":"AGUARDANDO"}
                """, MediaType.APPLICATION_JSON));

        client.registrarGarantia(
            new DetranClient.DetranGarantiaRequest("ABC1234", "9BWZZZ377VT004251",
                new BigDecimal("80000"), "Aurix Financiamento"));

        server.verify();
    }

    @Test
    void consultarVeiculo() {
        server.expect(requestTo("http://localhost:8080/api/detran/veiculos/ABC1234"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"placa":"ABC1234","chassi":"9BWZZZ377VT004251","marca":"VW","modelo":"Gol","ano":2025,"situacao":"REGULAR"}
                """, MediaType.APPLICATION_JSON));

        client.consultarVeiculo("ABC1234");

        server.verify();
    }
}
