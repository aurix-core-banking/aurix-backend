package com.aurix.platform.banking.poupanca.client;

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
    void buscarTrDiaria() {
        server.expect(requestTo("http://localhost:8080/api/bacen/indicadores/tr/2026-06-26"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("0.1054", MediaType.APPLICATION_JSON));

        client.buscarTrDiaria("2026-06-26");

        server.verify();
    }
}
