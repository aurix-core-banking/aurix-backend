package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class SeguradoraClientTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8098");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final SeguradoraClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(SeguradoraClient.class);

    @Test
    void cotar() {
        server.expect(requestTo("http://localhost:8098/api/seguradora/cotacao"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"premio":250.00,"taxa":0.005,"seguradora":"SEGURADORA_X","aceita":true}
                """, MediaType.APPLICATION_JSON));

        client.cotar(new SeguradoraClient.CotacaoRequest(
            "VIDA", "VIDA", new BigDecimal("50000"), 40, "SP", "M", null));

        server.verify();
    }

    @Test
    void registrarApolice() {
        server.expect(requestTo("http://localhost:8098/api/seguradora/apolices"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"protocolo":"PROTO-1","status":"REGISTRADA","numeroApolice":"SEG-1"}
                """, MediaType.APPLICATION_JSON));

        client.registrarApolice(new SeguradoraClient.RegistrarApoliceRequest(
            "SEG-1", 1L, 10L, "VIDA", new BigDecimal("50000"), new BigDecimal("250")));

        server.verify();
    }
}
