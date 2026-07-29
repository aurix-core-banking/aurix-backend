package com.aurix.platform.banking.salario.client;

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

class ContaCorrenteClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final ContaCorrenteClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(ContaCorrenteClient.class);

    @Test
    void getConta() {
        server.expect(requestTo("http://localhost:8080/api/core/contas/1"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"id":1,"status":"ATIVA"}
                """, MediaType.APPLICATION_JSON));

        client.getConta(1L);

        server.verify();
    }

    @Test
    void creditar() {
        server.expect(requestTo("http://localhost:8080/api/core/contas/1/creditar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.creditar(1L, new ContaCorrenteClient.CreditoRequest(new BigDecimal("5000.00"), "Salario"));

        server.verify();
    }

    @Test
    void debitar() {
        server.expect(requestTo("http://localhost:8080/api/core/contas/1/debitar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.debitar(1L, new ContaCorrenteClient.DebitoRequest(new BigDecimal("200.00"), "Debito"));

        server.verify();
    }
}
