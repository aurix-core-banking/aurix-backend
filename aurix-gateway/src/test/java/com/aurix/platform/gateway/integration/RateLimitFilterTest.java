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
        "aurix.gateway.api-key.keys.chaveRateA.plan=sandbox",
        "aurix.gateway.api-key.keys.chaveRateA.tenantId=tenant-b",
        "aurix.gateway.api-key.keys.chaveRateB.plan=sandbox",
        "aurix.gateway.api-key.keys.chaveRateB.tenantId=tenant-b",
        "aurix.gateway.api-key.plan-limits.sandbox=2",
        "aurix.gateway.rate-limit.enabled=true",
        "aurix.gateway.rate-limit.redis-enabled=false"
    }
)
@ActiveProfiles("test")
class RateLimitFilterTest {

    @LocalServerPort
    private int port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void devePermitirDentroDoLimiteDoPlano() {
        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveRateA")
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveRateA")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deveBloquearAoExcederLimiteDoPlano() {
        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveRateB")
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveRateB")
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/api/gateway/sandbox")
                .header("X-API-Key", "chaveRateB")
                .exchange()
                .expectStatus().isEqualTo(429)
                .expectHeader().valueEquals("Retry-After", "60")
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("Limite de requisições excedido. Tente novamente em 60 segundos.");
    }
}
