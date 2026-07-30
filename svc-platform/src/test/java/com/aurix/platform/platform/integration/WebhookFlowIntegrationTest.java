package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.entity.WebhookConfig;
import com.aurix.platform.platform.repository.WebhookConfigRepository;
import com.aurix.platform.platform.repository.WebhookLogRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WebhookFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebhookConfigRepository configRepository;

    @Autowired
    private WebhookLogRepository logRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        configRepository.deleteAll();
        logRepository.deleteAll();
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveSalvarConfigWebhook() {
        Map<String, Object> body = Map.of(
            "url", "https://hook.example.com/callback",
            "eventos", List.of("pix.recebido", "boleto.pago"),
            "ativo", true,
            "secret", "my-secret-123");

        ResponseEntity<WebhookConfig> response = rest.exchange(
            url("/api/platform/webhooks/config/tenant-wh-1"), HttpMethod.PUT,
            new HttpEntity<>(body), WebhookConfig.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUrl()).isEqualTo("https://hook.example.com/callback");
    }

    @Test
    void deveBuscarConfigWebhook() {
        Map<String, Object> body = Map.of(
            "url", "https://hook.example.com/notify",
            "eventos", List.of("pix.recebido"),
            "ativo", true);
        rest.exchange(url("/api/platform/webhooks/config/tenant-wh-2"), HttpMethod.PUT,
            new HttpEntity<>(body), WebhookConfig.class);

        ResponseEntity<WebhookConfig> response = rest.getForEntity(
            url("/api/platform/webhooks/config/tenant-wh-2"), WebhookConfig.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUrl()).isEqualTo("https://hook.example.com/notify");
    }

    @Test
    void deveRejeitarUrlEmBranco() {
        Map<String, Object> body = Map.of("url", "", "ativo", true);

        var ex = assertThrows(HttpClientErrorException.class, () ->
            rest.exchange(url("/api/platform/webhooks/config/tenant-wh-3"), HttpMethod.PUT,
                new HttpEntity<>(body), WebhookConfig.class));
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveDispararWebhook() {
        Map<String, Object> configBody = Map.of(
            "url", "https://hook.example.com/events",
            "eventos", List.of("pix.recebido"),
            "ativo", true);
        rest.exchange(url("/api/platform/webhooks/config/tenant-wh-4"), HttpMethod.PUT,
            new HttpEntity<>(configBody), WebhookConfig.class);

        Map<String, Object> dispatchBody = Map.of(
            "evento", "pix.recebido",
            "payload", Map.of("valor", 100.50, "remetente", "Joao"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant-Id", "tenant-wh-4");
        ResponseEntity<Map> response = rest.exchange(
            url("/api/platform/webhooks/dispatch"), HttpMethod.POST,
            new HttpEntity<>(dispatchBody, headers), Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).containsEntry("status", "accepted");
    }

    @Test
    void deveRejeitarDisparoEventoVazio() {
        Map<String, Object> body = Map.of("evento", "", "payload", Map.of());

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant-Id", "tenant-wh-5");
        var ex = assertThrows(HttpClientErrorException.class, () ->
            rest.exchange(url("/api/platform/webhooks/dispatch"), HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class));
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveConsultarLogs() {
        Map<String, Object> configBody = Map.of(
            "url", "https://hook.example.com/logs",
            "eventos", List.of("test.evento"),
            "ativo", true);
        rest.exchange(url("/api/platform/webhooks/config/tenant-wh-6"), HttpMethod.PUT,
            new HttpEntity<>(configBody), WebhookConfig.class);

        ResponseEntity<List> response = rest.getForEntity(
            url("/api/platform/webhooks/config/tenant-wh-6/logs?limit=10"), List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
