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

class ComplianceClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final ComplianceClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(ComplianceClient.class);

    @Test
    void consultarRoe() {
        server.expect(requestTo("http://localhost:8080/api/compliance/cambio/roe/1"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.consultarRoe(1L);

        server.verify();
    }

    @Test
    void validarOperacao() {
        server.expect(requestTo("http://localhost:8080/api/compliance/cambio/validar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"aprovada":true,"motivo":"OK","protocolo":"PROT-001"}
                """, MediaType.APPLICATION_JSON));

        client.validarOperacao(
            new ComplianceClient.ValidarOperacaoRequest(1L, "COMPRA", new BigDecimal("10000"), "USD", "VIAGEM"));

        server.verify();
    }

    @Test
    void registrarOperacao() {
        server.expect(requestTo("http://localhost:8080/api/compliance/cambio/registrar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.registrarOperacao(
            new ComplianceClient.RegistrarOperacaoRequest(1L, 1L, "COMPRA", new BigDecimal("10000"), "USD"));

        server.verify();
    }
}
