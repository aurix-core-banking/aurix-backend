package com.aurix.platform.cambio.client;

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

class BacenClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final BacenClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(BacenClient.class);

    @Test
    void consultarTaxa() {
        server.expect(requestTo("http://localhost:8080/api/bacen/cambio/taxas/USD"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"moeda":"USD","taxaCompra":5.85,"taxaVenda":5.87,"dataReferencia":"2026-06-26T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.consultarTaxa("USD");

        server.verify();
    }

    @Test
    void registrarContrato() {
        server.expect(requestTo("http://localhost:8080/api/bacen/cambio/contratos"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.registrarContrato(
            new BacenClient.RegistrarContratoBacenRequest(1L, new BigDecimal("10000"), "USD", "12345678900", "COMPRA"));

        server.verify();
    }
}
