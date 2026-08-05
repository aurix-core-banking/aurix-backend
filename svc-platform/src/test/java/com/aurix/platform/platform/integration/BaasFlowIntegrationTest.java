package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.entity.ConsentimentoCustodia;
import com.aurix.platform.platform.entity.ParceiroCustodia;
import com.aurix.platform.platform.repository.ConsentimentoCustodiaRepository;
import com.aurix.platform.platform.repository.ParceiroCustodiaRepository;
import com.aurix.platform.platform.repository.SubContaCustodiaRepository;
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
class BaasFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ParceiroCustodiaRepository parceiroRepository;

    @Autowired
    private SubContaCustodiaRepository subContaRepository;

    @Autowired
    private ConsentimentoCustodiaRepository consentimentoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        consentimentoRepository.deleteAll();
        subContaRepository.deleteAll();
        parceiroRepository.deleteAll();
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private HttpHeaders tenantHeaders(String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant-Id", tenantId);
        return headers;
    }

    private HttpHeaders clientHeaders(String tenantId, String clientId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant-Id", tenantId);
        headers.set("X-Client-Id", clientId);
        return headers;
    }

    @Test
    void deveCriarParceiro() {
        Map<String, String> body = Map.of("clientId", "client-001", "nome", "Parceiro Teste");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, tenantHeaders("tenant-baas-1"));

        ResponseEntity<ParceiroCustodia> response = rest.exchange(
            url("/api/platform/parceiros"), HttpMethod.POST, entity, ParceiroCustodia.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClientId()).isEqualTo("client-001");
    }

    @Test
    void deveRejeitarParceiroDuplicado() {
        Map<String, String> body = Map.of("clientId", "client-001", "nome", "Primeiro");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, tenantHeaders("tenant-baas-1"));
        rest.exchange(url("/api/platform/parceiros"), HttpMethod.POST, entity, ParceiroCustodia.class);

        var ex = assertThrows(HttpClientErrorException.class, () ->
            rest.exchange(url("/api/platform/parceiros"), HttpMethod.POST, entity, ParceiroCustodia.class));
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void deveListarParceiros() {
        HttpEntity<Map<String, String>> entity1 = new HttpEntity<>(
            Map.of("clientId", "c1", "nome", "Parceiro A"), tenantHeaders("tenant-baas-2"));
        HttpEntity<Map<String, String>> entity2 = new HttpEntity<>(
            Map.of("clientId", "c2", "nome", "Parceiro B"), tenantHeaders("tenant-baas-2"));
        rest.exchange(url("/api/platform/parceiros"), HttpMethod.POST, entity1, ParceiroCustodia.class);
        rest.exchange(url("/api/platform/parceiros"), HttpMethod.POST, entity2, ParceiroCustodia.class);

        HttpEntity<Void> getEntity = new HttpEntity<>(tenantHeaders("tenant-baas-2"));
        ResponseEntity<ParceiroCustodia[]> response = rest.exchange(
            url("/api/platform/parceiros"), HttpMethod.GET, getEntity, ParceiroCustodia[].class);

        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveCriarSubContaCustodia() {
        HttpEntity<Map<String, String>> parceiroEntity = new HttpEntity<>(
            Map.of("clientId", "client-sc", "nome", "Parceiro SC"), tenantHeaders("tenant-baas-3"));
        rest.exchange(url("/api/platform/parceiros"), HttpMethod.POST, parceiroEntity, ParceiroCustodia.class);

        Map<String, Object> subContaBody = Map.of(
            "contaId", 100L,
            "identificadorExterno", "ext-001");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
            subContaBody, clientHeaders("tenant-baas-3", "client-sc"));

        ResponseEntity<Map> response = rest.exchange(
            url("/api/platform/custodia/subcontas"), HttpMethod.POST, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void deveConsultarSaldoCustodia() {
        HttpEntity<Map<String, String>> parceiroEntity = new HttpEntity<>(
            Map.of("clientId", "client-saldo", "nome", "Parceiro Saldo"), tenantHeaders("tenant-baas-4"));
        ResponseEntity<ParceiroCustodia> parceiroResp = rest.exchange(
            url("/api/platform/parceiros"), HttpMethod.POST, parceiroEntity, ParceiroCustodia.class);

        Map<String, Object> consentBody = Map.of(
            "contaId", 1L,
            "parceiroId", parceiroResp.getBody().getId(),
            "escopos", List.of("CONSULTAR_SALDO", "MOVIMENTAR"),
            "dataExpiracao", "2027-12-31T23:59:59");
        HttpEntity<Map<String, Object>> consentEntity = new HttpEntity<>(
            consentBody, tenantHeaders("tenant-baas-4"));
        rest.exchange(url("/api/platform/custodia/consentimentos"), HttpMethod.POST, consentEntity, ConsentimentoCustodia.class);

        HttpEntity<Void> entity = new HttpEntity<>(clientHeaders("tenant-baas-4", "client-saldo"));
        ResponseEntity<Map> response = rest.exchange(
            url("/api/platform/custodia/saldo?contaId=1"), HttpMethod.GET, entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("saldo");
    }
}
