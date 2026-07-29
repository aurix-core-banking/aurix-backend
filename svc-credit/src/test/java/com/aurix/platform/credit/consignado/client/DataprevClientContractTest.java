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

class DataprevClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final DataprevClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(DataprevClient.class);

    @Test
    void consultarMargemInss() {
        server.expect(requestTo("http://localhost:8080/api/dataprev/inss/margem/12345678900"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"margemTotal":6000.00,"margemDisponivel":2500.00,"margemUtilizada":3500.00,"dataBase":"2026-06-01"}
                """, MediaType.APPLICATION_JSON));

        client.consultarMargemInss("12345678900");

        server.verify();
    }
}
