package com.aurix.platform.finance.integration;

import com.aurix.platform.finance.FinanceApplication;
import com.aurix.platform.finance.entity.PerfilFinanceiroCliente;
import com.aurix.platform.shared.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class PerfilFinanceiroClienteIntegrationTest {

    
    
    static class TestEntityScanConfig {
    }

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    @PersistenceContext
    private EntityManager em;

    private String baseUrl;
    private Long clienteId;
    private static long seq = 0;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/finance/perfil";
        seq++;

        Cliente cliente = new Cliente();
        cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        cliente.setCpf("52998224725");
        cliente.setNome("Test PF");
        cliente.setEmail("test@aurix.com");
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        em.persist(cliente);
        em.flush();
        clienteId = cliente.getId();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    private String codigoCliente() {
        return "CLI-" + String.format("%03d", seq);
    }

    @Test
    void shouldCriarPerfilFinanceiro() {
        ResponseEntity<PerfilFinanceiroCliente> response = rest.postForEntity(
            baseUrl + "/" + clienteId + "?codigoCliente=" + codigoCliente(),
            null, PerfilFinanceiroCliente.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(clienteId, response.getBody().getClienteId());
        assertEquals(codigoCliente(), response.getBody().getCodigoCliente());
    }

    @Test
    void shouldBuscarPerfilPorClienteId() {
        rest.postForEntity(
            baseUrl + "/" + clienteId + "?codigoCliente=" + codigoCliente(),
            null, PerfilFinanceiroCliente.class);

        ResponseEntity<PerfilFinanceiroCliente> response = rest.getForEntity(
            baseUrl + "/" + clienteId, PerfilFinanceiroCliente.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clienteId, response.getBody().getClienteId());
        assertEquals(codigoCliente(), response.getBody().getCodigoCliente());
    }

    @Test
    void shouldAtualizarLimiteCredito() {
        rest.postForEntity(
            baseUrl + "/" + clienteId + "?codigoCliente=" + codigoCliente(),
            null, PerfilFinanceiroCliente.class);

        ResponseEntity<PerfilFinanceiroCliente> response = rest.exchange(
            baseUrl + "/" + clienteId + "/limite-credito?limiteCredito=50000",
            HttpMethod.PUT, null, PerfilFinanceiroCliente.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, BigDecimal.valueOf(50000).compareTo(response.getBody().getLimiteCredito()));
    }

    @Test
    void shouldAtualizarScore() {
        rest.postForEntity(
            baseUrl + "/" + clienteId + "?codigoCliente=" + codigoCliente(),
            null, PerfilFinanceiroCliente.class);

        ResponseEntity<PerfilFinanceiroCliente> response = rest.exchange(
            baseUrl + "/" + clienteId + "/score?scoreCredito=850",
            HttpMethod.PUT, null, PerfilFinanceiroCliente.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Integer.valueOf(850), response.getBody().getScoreCredito());
    }

    @Test
    void shouldRemoverPerfil() {
        rest.postForEntity(
            baseUrl + "/" + clienteId + "?codigoCliente=" + codigoCliente(),
            null, PerfilFinanceiroCliente.class);

        ResponseEntity<Void> deleteResponse = rest.exchange(
            baseUrl + "/" + clienteId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<PerfilFinanceiroCliente> getResponse = rest.getForEntity(
            baseUrl + "/" + clienteId, PerfilFinanceiroCliente.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
