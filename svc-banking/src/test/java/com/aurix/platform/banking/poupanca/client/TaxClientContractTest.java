package com.aurix.platform.banking.poupanca.client;

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

class TaxClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final TaxClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(TaxClient.class);

    @Test
    void calcularIof() {
        server.expect(requestTo("http://localhost:8080/api/tax/iof/calcular"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"valorIof":15.50,"descricao":"IOF sobre resgate"}
                """, MediaType.APPLICATION_JSON));

        client.calcularIof(new TaxClient.IofRequest(1L, new BigDecimal("1000"), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 26)));

        server.verify();
    }
}
