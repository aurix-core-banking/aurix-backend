package com.aurix.platform.cambio.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ParceiroCambioClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final ParceiroCambioClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(ParceiroCambioClient.class);

    @Test
    void consultarCotacao() {
        server.expect(requestTo("http://localhost:8080/api/parceiro-cambio/cotacoes/USD"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"moeda":"USD","taxaCompra":5.80,"taxaVenda":5.82,"dataCotacao":"2026-06-26T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.consultarCotacao("USD");

        server.verify();
    }

    @Test
    void registrarContratoParceiro() {
        server.expect(requestTo("http://localhost:8080/api/parceiro-cambio/contratos"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.registrarContratoParceiro(new Object());

        server.verify();
    }
}
