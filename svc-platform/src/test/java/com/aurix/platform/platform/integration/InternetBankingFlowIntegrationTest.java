package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.entity.TransacaoInternetBanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class InternetBankingFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/platform/internet-banking";
    }

    private HttpEntity<?> withSession(String sessaoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Session-ID", sessaoId);
        return new HttpEntity<>(headers);
    }

    private <T> HttpEntity<T> withSession(T body, String sessaoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Session-ID", sessaoId);
        return new HttpEntity<>(body, headers);
    }

    @Test
    void shouldLoginAndReturnSession() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-001",
            "usuario", "joao.silva",
            "senha", "senha123"
        );

        ResponseEntity<Map> response = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("sessaoId"));
        assertTrue((Boolean) response.getBody().get("success"));
    }

    @Test
    void shouldValidateSession() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-002",
            "usuario", "maria.santos",
            "senha", "senha456"
        );

        ResponseEntity<Map> loginResp = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);
        String sessaoId = (String) loginResp.getBody().get("sessaoId");

        ResponseEntity<Map> validateResp = rest.exchange(
            baseUrl + "/auth/validate-session", HttpMethod.GET,
            withSession(sessaoId), Map.class);
        assertTrue((Boolean) validateResp.getBody().get("valid"));
    }

    @Test
    void shouldLogoutAndEndSession() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-003",
            "usuario", "carlos.oliveira",
            "senha", "senha789"
        );

        ResponseEntity<Map> loginResp = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);
        String sessaoId = (String) loginResp.getBody().get("sessaoId");

        ResponseEntity<Map> logoutResp = rest.exchange(
            baseUrl + "/auth/logout", HttpMethod.POST,
            withSession(sessaoId), Map.class);
        assertEquals(HttpStatus.OK, logoutResp.getStatusCode());
        assertTrue((Boolean) logoutResp.getBody().get("success"));
    }

    @Test
    void shouldProcessTransferencia() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-004",
            "usuario", "ana.pereira",
            "senha", "senha321"
        );

        ResponseEntity<Map> loginResp = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);
        String sessaoId = (String) loginResp.getBody().get("sessaoId");

        Map<String, Object> transferRequest = Map.of(
            "clienteId", "CLI-004",
            "contaOrigem", "12345-6",
            "contaDestino", "78901-2",
            "tipo", "TRANSFERENCIA_TED",
            "valor", 1500.00,
            "descricao", "Transferencia teste"
        );

        ResponseEntity<Map> transferResp = rest.exchange(
            baseUrl + "/transacoes/transferencia", HttpMethod.POST,
            withSession(transferRequest, sessaoId), Map.class);

        assertNotEquals(HttpStatus.NOT_FOUND, transferResp.getStatusCode());
        assertNotEquals(HttpStatus.UNAUTHORIZED, transferResp.getStatusCode());
    }

    @Test
    void shouldProcessPagamento() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-005",
            "usuario", "pedro.lima",
            "senha", "senha654"
        );

        ResponseEntity<Map> loginResp = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);
        String sessaoId = (String) loginResp.getBody().get("sessaoId");

        Map<String, Object> pagamentoRequest = Map.of(
            "clienteId", "CLI-005",
            "contaOrigem", "12345-6",
            "tipo", "PAGAMENTO_BOLETO",
            "valor", 250.00,
            "descricao", "Pagamento boleto teste",
            "codigoBarras", "34191.79001 01043.510047 91020.150008 5 12345"
        );

        ResponseEntity<Map> response = rest.exchange(
            baseUrl + "/transacoes/pagamento", HttpMethod.POST,
            withSession(pagamentoRequest, sessaoId), Map.class);

        assertNotEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnDashboard() {
        Map<String, Object> loginRequest = Map.of(
            "clienteId", "CLI-006",
            "usuario", "lucia.mendes",
            "senha", "senha987"
        );

        ResponseEntity<Map> loginResp = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);
        String sessaoId = (String) loginResp.getBody().get("sessaoId");

        ResponseEntity<Map> response = rest.exchange(
            baseUrl + "/dashboard?clienteId=CLI-006", HttpMethod.GET,
            withSession(sessaoId), Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("saldoConta"));
    }
}
