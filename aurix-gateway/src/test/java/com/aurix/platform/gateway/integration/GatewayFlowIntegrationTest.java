package com.aurix.platform.gateway.integration;

import com.aurix.platform.gateway.AurixGatewayApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AurixGatewayApplication.class,
    properties = {
        "spring.main.web-application-type=reactive",
        "spring.autoconfigure.exclude=" +
        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
        "org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration," +
        "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration," +
        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
    }
)
@ActiveProfiles("test")
class GatewayFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/gateway/sandbox";
    }

    @Test
    void shouldReturnSandboxStatus() {
        ResponseEntity<Map> response = rest.getForEntity(baseUrl, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("sandbox"));
        assertNotNull(response.getBody().get("baseUrl"));
        assertNotNull(response.getBody().get("documentacao"));
        assertNotNull(response.getBody().get("headerApiKey"));
    }

    @Test
    void shouldReturnSandboxEnabledByDefault() {
        ResponseEntity<Map> response = rest.getForEntity(baseUrl, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("enabled"));
    }

    @Test
    void shouldReturnBaseUrl() {
        ResponseEntity<Map> response = rest.getForEntity(baseUrl, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String baseUrlResponse = (String) response.getBody().get("baseUrl");
        assertNotNull(baseUrlResponse);
        assertTrue(baseUrlResponse.startsWith("http://localhost:"));
    }
}
