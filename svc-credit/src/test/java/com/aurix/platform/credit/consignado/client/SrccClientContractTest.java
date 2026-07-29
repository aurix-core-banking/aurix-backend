package com.aurix.platform.credit.consignado.client;

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

class SrccClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final SrccClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(SrccClient.class);

    @Test
    void consultarMargem() {
        server.expect(requestTo("http://localhost:8080/api/srcc/margem/12345678900"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"margemTotal":5000.00,"margemDisponivel":2000.00,"margemUtilizada":3000.00,"fonte":"INSS","dataReferencia":"2026-06-26T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.consultarMargem("12345678900");

        server.verify();
    }

    @Test
    void registrarContrato() {
        server.expect(requestTo("http://localhost:8080/api/srcc/contratos"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"id":1,"protocolo":"PROT-001","status":"APROVADO"}
                """, MediaType.APPLICATION_JSON));

        client.registrarContrato(
            new SrccClient.ContratoRequest(1L, new BigDecimal("10000"), 24, new BigDecimal("500"), "INSS"));

        server.verify();
    }
}
