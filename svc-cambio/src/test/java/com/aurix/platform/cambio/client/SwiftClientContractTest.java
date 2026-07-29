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

class SwiftClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final SwiftClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(SwiftClient.class);

    @Test
    void enviarRemessa() {
        server.expect(requestTo("http://localhost:8080/api/swift/remessas/enviar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"idExterno":"SWT-001","statusSwift":"PROCESSADO","dataConfirmacao":"2026-06-26T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.enviarRemessa(
            new SwiftClient.EnviarRemessaSwiftRequest(new BigDecimal("5000"), "USD", "BANK OF AMERICA",
                "12345-6", "BOFAUS3N", "TRANSFERENCIA"));

        server.verify();
    }

    @Test
    void consultarStatus() {
        server.expect(requestTo("http://localhost:8080/api/swift/remessas/SWT-001/status"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"idExterno":"SWT-001","statusSwift":"PROCESSADO","dataConfirmacao":"2026-06-26T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.consultarStatus("SWT-001");

        server.verify();
    }
}
