package com.aurix.platform.cards.client;

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

class MlFraudClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final MlFraudClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(MlFraudClient.class);

    @Test
    void avaliar() {
        server.expect(requestTo("http://localhost:8080/api/ml/fraud/avaliar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"resultado":"APROVADO","score":0.95,"recomendacao":"AUTORIZAR"}
                """, MediaType.APPLICATION_JSON));

        client.avaliar(new MlFraudClient.FraudRequest(1L, new BigDecimal("500.00"), "LOJA A", "CREDITO"));

        server.verify();
    }
}
