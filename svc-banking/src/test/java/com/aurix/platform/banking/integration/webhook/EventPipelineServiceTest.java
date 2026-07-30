package com.aurix.platform.banking.integration.webhook;

import com.aurix.platform.banking.integration.webhook.WebhookEvent.WebhookEventStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventPipelineServiceTest {

    @Mock
    private WebhookEventRepository repository;

    @Mock
    private WebhookEndpoint webhookEndpoint;

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private EventPipelineService service;

    private WebhookEndpoint.Endpoint enabledEndpoint;
    private WebhookEndpoint.Endpoint disabledEndpoint;
    private WebhookEndpoint.Endpoint filteredEndpoint;

    @BeforeEach
    void setUp() {
        service = new EventPipelineService(repository, webhookEndpoint, restTemplate, objectMapper);

        enabledEndpoint = new WebhookEndpoint.Endpoint();
        enabledEndpoint.setUrl("https://hooks.example.com/event");
        enabledEndpoint.setApiKey("test-key");
        enabledEndpoint.setEnabled(true);

        disabledEndpoint = new WebhookEndpoint.Endpoint();
        disabledEndpoint.setUrl("https://hooks.example.com/disabled");
        disabledEndpoint.setEnabled(false);

        filteredEndpoint = new WebhookEndpoint.Endpoint();
        filteredEndpoint.setUrl("https://hooks.example.com/filtered");
        filteredEndpoint.setEnabled(true);
        filteredEndpoint.setEventos(List.of("transacao.criada"));
    }

    @Test
    void dispatch_deveEnviarParaEndpointsHabilitados() {
        when(webhookEndpoint.getEndpoints()).thenReturn(List.of(enabledEndpoint, disabledEndpoint));
        when(repository.save(any(WebhookEvent.class))).thenAnswer(i -> {
            WebhookEvent e = i.getArgument(0);
            e.setId(1L);
            return e;
        });
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenReturn(ResponseEntity.ok("{\"status\":\"ok\"}"));

        service.dispatch("transacao.criada", "svc-banking", Map.of("id", 123));

        verify(repository, times(2)).save(any(WebhookEvent.class));
        verify(restTemplate).exchange(eq("https://hooks.example.com/event"), any(), any(), eq(String.class));
    }

    @Test
    void dispatch_deveFiltrarPorEvento() {
        when(webhookEndpoint.getEndpoints()).thenReturn(List.of(filteredEndpoint));

        service.dispatch("outro.evento", "svc-banking", Map.of("id", 456));

        verifyNoInteractions(repository, restTemplate);
    }

    @Test
    void send_comSucesso_deveMarcarDelivered() {
        WebhookEvent event = new WebhookEvent();
        event.setId(10L);
        event.setTargetUrl("https://hooks.example.com/ok");
        event.setPayload("{\"data\":\"test\"}");
        event.setStatus(WebhookEventStatus.PENDING);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenReturn(ResponseEntity.ok("{\"status\":\"received\"}"));

        service.send(event);

        assertEquals(WebhookEventStatus.DELIVERED, event.getStatus());
        assertEquals(200, event.getResponseCode());
        assertNotNull(event.getDispatchedAt());
        verify(repository).save(event);
    }

    @Test
    void send_comFalha_deveMarcarFailedComBackoff() {
        WebhookEvent event = new WebhookEvent();
        event.setId(11L);
        event.setTargetUrl("https://hooks.example.com/fail");
        event.setPayload("{}");
        event.setMaxAttempts(5);
        event.setAttempts(0);
        event.setStatus(WebhookEventStatus.PENDING);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenThrow(new RuntimeException("Connection refused"));

        service.send(event);

        assertEquals(WebhookEventStatus.FAILED, event.getStatus());
        assertEquals(1, event.getAttempts());
        assertNotNull(event.getNextRetryAt());
        assertTrue(event.getNextRetryAt().isAfter(LocalDateTime.now()));
        verify(repository).save(event);
    }

    @Test
    void send_comFalhaExaustiva_deveMarcarExhausted() {
        WebhookEvent event = new WebhookEvent();
        event.setId(12L);
        event.setTargetUrl("https://hooks.example.com/exhaust");
        event.setPayload("{}");
        event.setMaxAttempts(3);
        event.setAttempts(3);
        event.setStatus(WebhookEventStatus.FAILED);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenThrow(new RuntimeException("Timeout"));

        service.send(event);

        assertEquals(WebhookEventStatus.EXHAUSTED, event.getStatus());
        assertEquals(4, event.getAttempts());
        verify(repository).save(event);
    }

    @Test
    void retryFailed_deveReprocessarEventosVencidos() {
        WebhookEvent failed1 = new WebhookEvent();
        failed1.setId(20L);
        failed1.setTargetUrl("https://hooks.example.com/retry1");
        failed1.setPayload("{}");
        failed1.setStatus(WebhookEventStatus.FAILED);
        failed1.setNextRetryAt(LocalDateTime.now().minusMinutes(5));

        when(repository.findByStatusAndNextRetryAtBeforeOrderByNextRetryAtAsc(
            eq(WebhookEventStatus.FAILED), any())).thenReturn(List.of(failed1));
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenReturn(ResponseEntity.ok("ok"));

        int count = service.retryFailed();

        assertEquals(1, count);
        verify(repository, times(2)).save(any(WebhookEvent.class));
    }

    @Test
    void dispatch_semEndpoints_naoFazNada() {
        when(webhookEndpoint.getEndpoints()).thenReturn(null);
        service.dispatch("test.event", "test", Map.of());
        verifyNoInteractions(repository, restTemplate);
    }

    @Test
    void countPending_deveRetornarContagem() {
        when(repository.countByStatus(WebhookEventStatus.PENDING)).thenReturn(5L);
        assertEquals(5L, service.countPending());
    }

    @Test
    void countFailed_deveRetornarContagem() {
        when(repository.countByStatus(WebhookEventStatus.FAILED)).thenReturn(3L);
        assertEquals(3L, service.countFailed());
    }
}
