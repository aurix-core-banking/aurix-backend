package com.aurix.platform.shared.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class IntegrationServiceSagaTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private IntegrationService service;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        service = new IntegrationService(restTemplate);
        ReflectionTestUtils.setField(service, "financialUrl", "http://localhost:8081");
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void shouldSyncContaAndCallDesyncOnFailure() {
        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/contas"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(header("X-Idempotency-Key", org.hamcrest.Matchers.notNullValue()))
            .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/contas/CONTA-001"))
            .andExpect(method(HttpMethod.DELETE))
            .andExpect(header("X-Compensation", "true"))
            .andRespond(withStatus(HttpStatus.OK));

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
            () -> service.sincronizarContaComFinanceiro("CONTA-001", "CLI-001", BigDecimal.valueOf(1000)));

        mockServer.verify();
    }

    @Test
    void shouldSyncTransacaoAndCallDesyncOnFailure() {
        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/transacoes"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(header("X-Idempotency-Key", org.hamcrest.Matchers.notNullValue()))
            .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/transacoes/TXN-001"))
            .andExpect(method(HttpMethod.DELETE))
            .andExpect(header("X-Compensation", "true"))
            .andRespond(withStatus(HttpStatus.OK));

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
            () -> service.sincronizarTransacaoComFinanceiro("TXN-001", "CONTA-001", BigDecimal.valueOf(500), "DEBITO"));

        mockServer.verify();
    }

    @Test
    void shouldNotCallDesyncOnSuccessfulSync() {
        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/contas"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK));

        service.sincronizarContaComFinanceiro("CONTA-002", "CLI-001", BigDecimal.valueOf(2000));
        mockServer.verify();
    }

    @Test
    void shouldHandleCompensationFailureGracefully() {
        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/contas"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockServer.expect(requestTo("http://localhost:8081/api/financial/sincronizar/contas/CONTA-003"))
            .andExpect(method(HttpMethod.DELETE))
            .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
            () -> service.sincronizarContaComFinanceiro("CONTA-003", "CLI-001", BigDecimal.valueOf(3000)));

        mockServer.verify();
    }
}
