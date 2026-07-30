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

class ContaSalarioClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final ContaSalarioClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(ContaSalarioClient.class);

    @Test
    void validarVinculo() {
        server.expect(requestTo("http://localhost:8080/api/salario/vincular/validar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"valido":true,"mensagem":"Vinculo ativo"}
                """, MediaType.APPLICATION_JSON));

        client.validarVinculo(new ContaSalarioClient.ValidarVinculoRequest(1L, 1L));

        server.verify();
    }

    @Test
    void debitarParcela() {
        server.expect(requestTo("http://localhost:8080/api/salario/parcelas/debitar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess());

        client.debitarParcela(new ContaSalarioClient.DebitarParcelaRequest(1L, 1L, new BigDecimal("500.00"), "ID-001"));

        server.verify();
    }
}
