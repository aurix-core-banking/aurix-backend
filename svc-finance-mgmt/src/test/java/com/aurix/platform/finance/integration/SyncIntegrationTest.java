package com.aurix.platform.finance.integration;

import com.aurix.platform.finance.FinanceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SyncIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();
    private String baseUrl;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/finance/sincronizar";
    }

    @Test
    void shouldSyncAndDesyncConta() {
        var payload = Map.of("contaId", "CONTA-001", "clienteId", "CLI-001",
            "saldoInicial", 1000.00, "dataCriacao", LocalDateTime.now().toString());
        ResponseEntity<Map> sync = rest.postForEntity(baseUrl + "/contas", payload, Map.class);
        assertEquals(HttpStatus.OK, sync.getStatusCode());
        assertEquals("CONTA-001", sync.getBody().get("contaId"));
        assertEquals("ATIVO", sync.getBody().get("status"));

        ResponseEntity<Map> desync = rest.exchange(baseUrl + "/contas/CONTA-001",
            HttpMethod.DELETE, null, Map.class);
        assertEquals(HttpStatus.OK, desync.getStatusCode());
        assertEquals("compensated", desync.getBody().get("status"));
    }

    @Test
    void shouldSyncAndDesyncTransacao() {
        var payload = Map.of("transacaoId", "TXN-001", "contaId", "CONTA-001",
            "valor", 500.00, "tipo", "DEBITO", "dataTransacao", LocalDateTime.now().toString());
        ResponseEntity<Map> sync = rest.postForEntity(baseUrl + "/transacoes", payload, Map.class);
        assertEquals(HttpStatus.OK, sync.getStatusCode());
        assertEquals("TXN-001", sync.getBody().get("transacaoId"));
        assertEquals("REGISTRADA", sync.getBody().get("status"));

        ResponseEntity<Map> desync = rest.exchange(baseUrl + "/transacoes/TXN-001",
            HttpMethod.DELETE, null, Map.class);
        assertEquals(HttpStatus.OK, desync.getStatusCode());
        assertEquals("compensated", desync.getBody().get("status"));
    }

    @Test
    void shouldBeIdempotentOnRepeatedSync() {
        var payload = Map.of("contaId", "CONTA-002", "clienteId", "CLI-001",
            "saldoInicial", 2000.00, "dataCriacao", LocalDateTime.now().toString());
        ResponseEntity<Map> first = rest.postForEntity(baseUrl + "/contas", payload, Map.class);
        assertEquals(HttpStatus.OK, first.getStatusCode());
        ResponseEntity<Map> second = rest.postForEntity(baseUrl + "/contas", payload, Map.class);
        assertEquals(HttpStatus.OK, second.getStatusCode());
        assertEquals(first.getBody().get("contaId"), second.getBody().get("contaId"));
    }

    @Test
    void shouldReturnErrorOnInvalidPayload() {
        var resp = rest.postForEntity(baseUrl + "/contas", Map.of("contaId", "CONTA-003"), Map.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
    }

    @Test
    void shouldDesyncNonExistentContaGracefully() {
        ResponseEntity<Map> resp = rest.exchange(baseUrl + "/contas/NAO-EXISTE",
            HttpMethod.DELETE, null, Map.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("compensated", resp.getBody().get("status"));
    }
}
