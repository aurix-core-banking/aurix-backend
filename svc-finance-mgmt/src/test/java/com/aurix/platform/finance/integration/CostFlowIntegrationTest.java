package com.aurix.platform.finance.integration;

import com.aurix.platform.finance.FinanceApplication;
import com.aurix.platform.finance.entity.Custo;
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

@SpringBootTest(classes = FinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CostFlowIntegrationTest {
    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/finance/custos";
    }

    @Test
    void shouldCriarCusto() {
        Custo custo = new Custo();
        custo.setDescricao("Aluguel Matriz");
        custo.setTipoCusto(Custo.TipoCusto.FIXO);
        custo.setCategoria(Custo.CategoriaCusto.CENTRO_CUSTO);
        custo.setValor(BigDecimal.valueOf(50000.00));
        custo.setDataReferencia(LocalDate.now());
        custo.setCompetencia("2026-06");
        custo.setCentroCusto("ADMINISTRATIVO");
        custo.setProdutoId(1L);

        ResponseEntity<Custo> response = rest.postForEntity(baseUrl, custo, Custo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Aluguel Matriz", response.getBody().getDescricao());
    }

    @Test
    void shouldApurarCusto() {
        Custo custo = new Custo();
        custo.setDescricao("Energia Elétrica");
        custo.setTipoCusto(Custo.TipoCusto.VARIAVEL);
        custo.setCategoria(Custo.CategoriaCusto.CENTRO_CUSTO);
        custo.setValor(BigDecimal.valueOf(15000.00));
        custo.setDataReferencia(LocalDate.now());
        custo.setCompetencia("2026-06");
        custo.setCentroCusto("ADMINISTRATIVO");

        ResponseEntity<Custo> createResp = rest.postForEntity(baseUrl, custo, Custo.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<Custo> response = rest.postForEntity(
            baseUrl + "/" + id + "/apurar", null, Custo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Custo.StatusCusto.APURADO, response.getBody().getStatus());
    }

    @Test
    void shouldRatearCusto() {
        Custo custo = new Custo();
        custo.setDescricao("Internet Corporativa");
        custo.setTipoCusto(Custo.TipoCusto.FIXO);
        custo.setCategoria(Custo.CategoriaCusto.PRODUTO);
        custo.setValor(BigDecimal.valueOf(10000.00));
        custo.setDataReferencia(LocalDate.now());
        custo.setCompetencia("2026-06");
        custo.setCentroCusto("TI");

        ResponseEntity<Custo> createResp = rest.postForEntity(baseUrl, custo, Custo.class);
        Long id = createResp.getBody().getId();
        rest.postForEntity(baseUrl + "/" + id + "/apurar", null, Custo.class);

        ResponseEntity<Custo> response = rest.postForEntity(
            baseUrl + "/" + id + "/ratear?percentualRateio=0.50", null, Custo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, BigDecimal.valueOf(5000.00).compareTo(response.getBody().getValorRateado()));
    }

    @Test
    void shouldBuscarCustosNaoRateados() {
        Custo custo = new Custo();
        custo.setDescricao("Material Escritório");
        custo.setTipoCusto(Custo.TipoCusto.VARIAVEL);
        custo.setCategoria(Custo.CategoriaCusto.CENTRO_CUSTO);
        custo.setValor(BigDecimal.valueOf(3000.00));
        custo.setDataReferencia(LocalDate.now());
        custo.setCompetencia("2026-06");
        custo.setCentroCusto("ADMINISTRATIVO");

        rest.postForEntity(baseUrl, custo, Custo.class);

        ResponseEntity<List> response = rest.getForEntity(
            baseUrl + "/nao-rateados", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldCalcularResumoProduto() {
        ResponseEntity<Map> response = rest.getForEntity(
            baseUrl + "/resumo-produto/1", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("produtoId"));
    }

    @Test
    void shouldCalcularResumoPeriodo() {
        ResponseEntity<Map> response = rest.getForEntity(
            baseUrl + "/resumo-periodo?dataInicio=2026-01-01&dataFim=2026-12-31", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("dataInicio"));
        assertNotNull(response.getBody().get("valorTotalCustos"));
    }
}
