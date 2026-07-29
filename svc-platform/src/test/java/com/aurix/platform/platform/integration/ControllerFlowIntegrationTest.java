package com.aurix.platform.platform.integration;

import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.entity.Orcamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ControllerFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/platform/orcamentos";
    }

    @Test
    void shouldCriarOrcamento() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento Controladoria 2026");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.of(2026, 1, 1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(8000000.00));
        orcamento.setCentroCusto("CONTROLADORIA");
        orcamento.setResponsavel("Controller");

        ResponseEntity<Orcamento> response = rest.postForEntity(baseUrl, orcamento, Orcamento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Orçamento Controladoria 2026", response.getBody().getNome());
    }

    @Test
    void shouldAprovarOrcamento() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento Auditoria 2026");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.of(2026, 1, 1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(2000000.00));
        orcamento.setCentroCusto("AUDITORIA");
        orcamento.setResponsavel("Auditor Chefe");

        ResponseEntity<Orcamento> createResp = rest.postForEntity(baseUrl, orcamento, Orcamento.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<Orcamento> response = rest.postForEntity(
            baseUrl + "/" + id + "/aprovar?aprovadoPor=Diretor", null, Orcamento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Orcamento.StatusOrcamento.APROVADO, response.getBody().getStatus());
    }

    @Test
    void shouldIniciarExecucao() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento Compliance 2026");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.now().minusDays(1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(1500000.00));
        orcamento.setCentroCusto("COMPLIANCE");
        orcamento.setResponsavel("CCO");

        ResponseEntity<Orcamento> createResp = rest.postForEntity(baseUrl, orcamento, Orcamento.class);
        Long id = createResp.getBody().getId();
        rest.postForEntity(baseUrl + "/" + id + "/aprovar?aprovadoPor=Diretor", null, Orcamento.class);

        ResponseEntity<Orcamento> response = rest.postForEntity(
            baseUrl + "/" + id + "/iniciar-execucao", null, Orcamento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Orcamento.StatusOrcamento.EXECUTANDO, response.getBody().getStatus());
    }

    @Test
    void shouldAtualizarValoresRealizados() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento Jurídico 2026");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.now().minusDays(1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(2500000.00));
        orcamento.setCentroCusto("JURIDICO");
        orcamento.setResponsavel("Diretor Jurídico");

        ResponseEntity<Orcamento> createResp = rest.postForEntity(baseUrl, orcamento, Orcamento.class);
        Long id = createResp.getBody().getId();
        rest.postForEntity(baseUrl + "/" + id + "/aprovar?aprovadoPor=Diretor", null, Orcamento.class);
        rest.postForEntity(baseUrl + "/" + id + "/iniciar-execucao", null, Orcamento.class);

        ResponseEntity<Orcamento> response = rest.postForEntity(
            baseUrl + "/" + id + "/atualizar-realizado?valorRealizado=300000.00", null, Orcamento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, BigDecimal.valueOf(300000.00).compareTo(response.getBody().getValorTotalRealizado()));
    }

    @Test
    void shouldFecharOrcamento() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento Projetos 2026");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.now().minusDays(1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(1000000.00));
        orcamento.setCentroCusto("PROJETOS");
        orcamento.setResponsavel("Gerente Projetos");

        ResponseEntity<Orcamento> createResp = rest.postForEntity(baseUrl, orcamento, Orcamento.class);
        Long id = createResp.getBody().getId();
        rest.postForEntity(baseUrl + "/" + id + "/aprovar?aprovadoPor=Diretor", null, Orcamento.class);
        rest.postForEntity(baseUrl + "/" + id + "/iniciar-execucao", null, Orcamento.class);

        ResponseEntity<Orcamento> response = rest.postForEntity(
            baseUrl + "/" + id + "/fechar", null, Orcamento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Orcamento.StatusOrcamento.FECHADO, response.getBody().getStatus());
    }

    @Test
    void shouldBuscarPorAno() {
        Orcamento orcamento = new Orcamento();
        orcamento.setNome("Orçamento 2026 Controller Teste");
        orcamento.setAno(2026);
        orcamento.setTipoOrcamento(Orcamento.TipoOrcamento.ANUAL);
        orcamento.setDataInicio(LocalDate.of(2026, 1, 1));
        orcamento.setDataFim(LocalDate.of(2026, 12, 31));
        orcamento.setValorTotalOrcado(BigDecimal.valueOf(500000.00));
        orcamento.setCentroCusto("TESTE");
        orcamento.setResponsavel("Teste");

        rest.postForEntity(baseUrl, orcamento, Orcamento.class);

        ResponseEntity<List> response = rest.getForEntity(
            baseUrl + "/ano/2026", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
