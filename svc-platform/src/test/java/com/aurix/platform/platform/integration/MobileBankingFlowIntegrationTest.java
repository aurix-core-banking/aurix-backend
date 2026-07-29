package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MobileBankingFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/platform/mobile-banking";
    }

    private HttpEntity<?> withSession(String sessaoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Session-ID", sessaoId);
        return new HttpEntity<>(headers);
    }

    @Test
    void shouldRegistrarDispositivo() {
        Map<String, Object> request = Map.ofEntries(
            Map.entry("clienteId", "CLI-001"),
            Map.entry("dispositivoId", "DEV-001"),
            Map.entry("nomeDispositivo", "iPhone 15"),
            Map.entry("marca", "Apple"),
            Map.entry("modelo", "iPhone15,3"),
            Map.entry("sistemaOperacional", "iOS"),
            Map.entry("versaoSO", "18.0"),
            Map.entry("deviceToken", "token-abc-123"),
            Map.entry("deviceFingerprint", "fp-abc-123"),
            Map.entry("imei", "351234567890123"),
            Map.entry("numeroTelefone", "+5511999999999")
        );

        ResponseEntity<Map> response = rest.postForEntity(
            baseUrl + "/dispositivos/registrar", request, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("dispositivoId"));
        assertTrue((Boolean) response.getBody().get("success"));
    }

    @Test
    void shouldHabilitarBiometrico() {
        Map<String, Object> deviceRequest = Map.ofEntries(
            Map.entry("clienteId", "CLI-002"),
            Map.entry("dispositivoId", "DEV-002"),
            Map.entry("nomeDispositivo", "Samsung Galaxy S25"),
            Map.entry("marca", "Samsung"),
            Map.entry("modelo", "SM-S25"),
            Map.entry("sistemaOperacional", "Android"),
            Map.entry("versaoSO", "16"),
            Map.entry("deviceToken", "token-def-456"),
            Map.entry("deviceFingerprint", "fp-def-456"),
            Map.entry("imei", "351234567890456"),
            Map.entry("numeroTelefone", "+5511988888888")
        );

        rest.postForEntity(baseUrl + "/dispositivos/registrar", deviceRequest, Map.class);

        ResponseEntity<Map> response = rest.postForEntity(
            baseUrl + "/dispositivos/DEV-002/biometrico?tipoBiometrico=FACE_ID", null, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("success"));
    }

    @Test
    void shouldLoginAndCreateSession() {
        Map<String, Object> deviceRequest = Map.ofEntries(
            Map.entry("clienteId", "CLI-003"),
            Map.entry("dispositivoId", "DEV-003"),
            Map.entry("nomeDispositivo", "Pixel 10"),
            Map.entry("marca", "Google"),
            Map.entry("modelo", "Pixel10"),
            Map.entry("sistemaOperacional", "Android"),
            Map.entry("versaoSO", "17"),
            Map.entry("deviceToken", "token-ghi-789"),
            Map.entry("deviceFingerprint", "fp-ghi-789"),
            Map.entry("imei", "351234567890789"),
            Map.entry("numeroTelefone", "+5511977777777")
        );

        rest.postForEntity(baseUrl + "/dispositivos/registrar", deviceRequest, Map.class);

        Map<String, Object> loginRequest = Map.ofEntries(
            Map.entry("dispositivoId", "DEV-003"),
            Map.entry("clienteId", "CLI-003"),
            Map.entry("usuario", "joao.mobile"),
            Map.entry("senha", "senha123"),
            Map.entry("deviceToken", "token-ghi-789"),
            Map.entry("appVersion", "3.2.1"),
            Map.entry("osVersion", "17"),
            Map.entry("deviceModel", "Pixel10"),
            Map.entry("deviceManufacturer", "Google"),
            Map.entry("latitude", -23.5505),
            Map.entry("longitude", -46.6333)
        );

        ResponseEntity<Map> response = rest.postForEntity(
            baseUrl + "/auth/login", loginRequest, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("sessaoId"));
        assertTrue((Boolean) response.getBody().get("success"));
    }

    @Test
    void shouldValidateMobileSession() {
        Map<String, Object> deviceRequest = Map.ofEntries(
            Map.entry("clienteId", "CLI-004"),
            Map.entry("dispositivoId", "DEV-004"),
            Map.entry("nomeDispositivo", "iPhone 14"),
            Map.entry("marca", "Apple"),
            Map.entry("modelo", "iPhone14,5"),
            Map.entry("sistemaOperacional", "iOS"),
            Map.entry("versaoSO", "17.5"),
            Map.entry("deviceToken", "token-jkl-012"),
            Map.entry("deviceFingerprint", "fp-jkl-012"),
            Map.entry("imei", "351234567890012"),
            Map.entry("numeroTelefone", "+5511966666666")
        );

        rest.postForEntity(baseUrl + "/dispositivos/registrar", deviceRequest, Map.class);

        Map<String, Object> loginRequest = Map.ofEntries(
            Map.entry("dispositivoId", "DEV-004"),
            Map.entry("clienteId", "CLI-004"),
            Map.entry("usuario", "maria.mobile"),
            Map.entry("senha", "senha456"),
            Map.entry("deviceToken", "token-jkl-012"),
            Map.entry("appVersion", "3.2.0"),
            Map.entry("osVersion", "17.5"),
            Map.entry("deviceModel", "iPhone14,5"),
            Map.entry("deviceManufacturer", "Apple"),
            Map.entry("latitude", -23.5610),
            Map.entry("longitude", -46.6560)
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
    void shouldSendPushNotification() {
        Map<String, Object> deviceRequest = Map.ofEntries(
            Map.entry("clienteId", "CLI-005"),
            Map.entry("dispositivoId", "DEV-005"),
            Map.entry("nomeDispositivo", "Xiaomi 14"),
            Map.entry("marca", "Xiaomi"),
            Map.entry("modelo", "Xiaomi14"),
            Map.entry("sistemaOperacional", "Android"),
            Map.entry("versaoSO", "15"),
            Map.entry("deviceToken", "token-mno-345"),
            Map.entry("deviceFingerprint", "fp-mno-345"),
            Map.entry("imei", "351234567890345"),
            Map.entry("numeroTelefone", "+5511955555555")
        );

        rest.postForEntity(baseUrl + "/dispositivos/registrar", deviceRequest, Map.class);

        Map<String, Object> pushRequest = Map.of(
            "dispositivoId", "DEV-005",
            "clienteId", "CLI-005",
            "tipoNotificacao", "TRANSACAO",
            "categoria", "FINANCEIRA",
            "titulo", "Compra Aprovada",
            "mensagem", "Sua compra de R$ 150,00 foi aprovada.",
            "corpo", "Estabelecimento: Loja Teste"
        );

        ResponseEntity<Map> response = rest.postForEntity(
            baseUrl + "/notificacoes/push", pushRequest, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("notificacaoId"));
    }

    @Test
    void shouldReturnMobileDashboard() {
        Map<String, Object> deviceRequest = Map.ofEntries(
            Map.entry("clienteId", "CLI-006"),
            Map.entry("dispositivoId", "DEV-006"),
            Map.entry("nomeDispositivo", "Motorola Edge 50"),
            Map.entry("marca", "Motorola"),
            Map.entry("modelo", "Edge50"),
            Map.entry("sistemaOperacional", "Android"),
            Map.entry("versaoSO", "16"),
            Map.entry("deviceToken", "token-pqr-678"),
            Map.entry("deviceFingerprint", "fp-pqr-678"),
            Map.entry("imei", "351234567890678"),
            Map.entry("numeroTelefone", "+5511944444444")
        );

        rest.postForEntity(baseUrl + "/dispositivos/registrar", deviceRequest, Map.class);

        Map<String, Object> loginRequest = Map.ofEntries(
            Map.entry("dispositivoId", "DEV-006"),
            Map.entry("clienteId", "CLI-006"),
            Map.entry("usuario", "ana.mobile"),
            Map.entry("senha", "senha789"),
            Map.entry("deviceToken", "token-pqr-678"),
            Map.entry("appVersion", "3.2.1"),
            Map.entry("osVersion", "16"),
            Map.entry("deviceModel", "Edge50"),
            Map.entry("deviceManufacturer", "Motorola"),
            Map.entry("latitude", -23.5505),
            Map.entry("longitude", -46.6333)
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
