package com.aurix.platform.credit.financiamento.client;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ContaCorrenteClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final ContaCorrenteClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(ContaCorrenteClient.class);

    @Test
    void debitar() {
        server.expect(requestTo("http://localhost:8080/api/core/contas/1/debitar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.debitar(1L, new ContaCorrenteClient.DebitoRequest(new BigDecimal("100.00"), "test"));

        server.verify();
    }

    @Test
    void creditar() {
        server.expect(requestTo("http://localhost:8080/api/core/contas/1/creditar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.creditar(1L, new ContaCorrenteClient.CreditoRequest(new BigDecimal("50.00"), "test"));

        server.verify();
    }
}
