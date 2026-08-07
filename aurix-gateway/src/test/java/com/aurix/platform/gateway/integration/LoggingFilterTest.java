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
        "aurix.gateway.rate-limit.enabled=false"
    }
)
@ActiveProfiles("test")
class LoggingFilterTest {

    @LocalServerPort
    private int port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void deveGerarRequestIdNaResposta() {
        client.get().uri("/api/gateway/sandbox")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("X-Request-Id");
    }

    @Test
    void devePreservarRequestIdInformado() {
        client.get().uri("/api/gateway/sandbox")
                .header("X-Request-Id", "request-teste-123")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("X-Request-Id", "request-teste-123");
    }
}
