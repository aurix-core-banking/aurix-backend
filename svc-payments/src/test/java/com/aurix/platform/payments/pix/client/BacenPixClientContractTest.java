package com.aurix.platform.payments.pix.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class BacenPixClientContractTest {

    private final RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8080");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final RestClient restClient = builder.build();
    private final BacenPixClient client = HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(BacenPixClient.class);

    @Test
    void consultarChave() {
        server.expect(requestTo("http://localhost:8080/pix/v2/chaves/teste%40email.com"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""
                {"chave":"teste@email.com","tipo":"EMAIL","instituicao":"Aurix","nomeTitular":"Joao","documento":"12345678900","dataCriacao":"2026-01-01T10:00:00"}
                """, MediaType.APPLICATION_JSON));

        client.consultarChave("teste@email.com");

        server.verify();
    }

    @Test
    void registrarSpb() {
        server.expect(requestTo("http://localhost:8080/pix/v2/spb/registrar"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"id":"SPB-001","status":"REGISTRADO","protocolo":"PROT-001"}
                """, MediaType.APPLICATION_JSON));

        client.registrarSpb(new BacenPixClient.RegistrarSpbRequest(
            "teste@email.com", "CORRENTE", "123", "0001", "12345-6", "12345678900"));

        server.verify();
    }

    @Test
    void devolverPix() {
        server.expect(requestTo("http://localhost:8080/pix/v2/spb/devolver"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess("""
                {"id":"DEV-001","status":"DEVOLVIDO","motivo":"DEVOLUCAO"}
                """, MediaType.APPLICATION_JSON));

        client.devolverPix(new BacenPixClient.DevolverPixRequest("E001", "100.00", "ORIGINAL", "BE03"));

        server.verify();
    }
}
