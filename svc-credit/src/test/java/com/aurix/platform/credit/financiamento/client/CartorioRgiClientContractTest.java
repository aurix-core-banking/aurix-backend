package com.aurix.platform.credit.financiamento.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class CartorioRgiClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final CartorioRgiClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(CartorioRgiClient.class);

    @Test
    void registrarGarantia() {
        server.expect(requestTo("http://localhost:8080/api/rgi/registro"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"protocolo":"PROT-001","matricula":"MAT-001","dataRegistro":"2026-06-26"}
                """, MediaType.APPLICATION_JSON));

        var response = client.registrarGarantia(
            new CartorioRgiClient.RegistroGarantiaRequest("alienacao", new BigDecimal("150000"), "9oRGI"));

        server.verify();
    }

    @Test
    void consultar() {
        server.expect(requestTo("http://localhost:8080/api/rgi/consulta/MAT-001"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"matricula":"MAT-001","status":"ATIVA","orgao":"9oRGI"}
                """, MediaType.APPLICATION_JSON));

        client.consultar("MAT-001");

        server.verify();
    }
}
