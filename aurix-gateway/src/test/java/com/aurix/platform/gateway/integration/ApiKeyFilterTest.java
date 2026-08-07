package com.aurix.platform.gateway.integration;

import com.aurix.platform.gateway.AurixGatewayApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AurixGatewayApplication.class,
    properties = {
        "spring.main.web-application-type=reactive",
        "spring.autoconfigure.exclude=" +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration," +
            "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration," +
            "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        "aurix.gateway.api-key.enabled=true",
        "aurix.gateway.api-key.required=true",
        "aurix.gateway.api-key.keys.chaveTeste1.plan=sandbox",
        "aurix.gateway.api-key.keys.chaveTeste1.tenantId=tenant-a",
        "aurix.gateway.rate-limit.enabled=false"
    }
)
@ActiveProfiles("test")
class ApiKeyFilterTest {

    @LocalServerPort
    private int port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void deveRejeitarRequisicaoSemApiKey() {
        client.get().uri("/api/gateway/sandbox")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.message").isEqualTo("API key ausente");
    }

    @Test
    void deveRejeitarApiKeyInvalida() {
        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chave-invalida")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.message").isEqualTo("API key inválida");
    }

    @Test
    void deveAceitarApiKeyValida() {
        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveTeste1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sandbox").isEqualTo(true);
    }
}
