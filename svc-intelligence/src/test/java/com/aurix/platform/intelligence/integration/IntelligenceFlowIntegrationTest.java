package com.aurix.platform.intelligence.integration;

import com.aurix.platform.intelligence.IntelligenceApplication;
import com.aurix.platform.shared.dto.MetricaDTO;
import com.aurix.platform.shared.entity.Metrica;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = IntelligenceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntelligenceFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String metricasUrl;
    private String mlUrl;
    private String chatbotUrl;
    private String biUrl;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        metricasUrl = "http://localhost:" + port + "/api/intelligence/metricas";
        mlUrl = "http://localhost:" + port + "/api/intelligence/ml";
        chatbotUrl = "http://localhost:" + port + "/api/intelligence/chatbot";
        biUrl = "http://localhost:" + port + "/api/intelligence/bi";
    }

    @Test
    void shouldCreateMetrica() {
        MetricaDTO dto = new MetricaDTO();
        dto.setNome("Receita Total");
        dto.setDescricao("Receita total do banco no mês");
        dto.setValor(BigDecimal.valueOf(1500000.00));
        dto.setValorAnterior(BigDecimal.valueOf(1400000.00));
        dto.setDataMedicao(LocalDateTime.now());
        dto.setTipoMetrica(Metrica.TipoMetrica.VALOR_MONETARIO);
        dto.setCategoria(Metrica.CategoriaMetrica.FINANCEIRA);
        dto.setUnidadeMedida("BRL");

        ResponseEntity<MetricaDTO> response = rest.postForEntity(
            metricasUrl, dto, MetricaDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Receita Total", response.getBody().getNome());
    }

    @Test
    void shouldBuscarMetricaPorId() {
        MetricaDTO dto = new MetricaDTO();
        dto.setNome("Despesas Operacionais");
        dto.setDescricao("Despesas operacionais totais");
        dto.setValor(BigDecimal.valueOf(800000.00));
        dto.setDataMedicao(LocalDateTime.now());
        dto.setTipoMetrica(Metrica.TipoMetrica.VALOR_MONETARIO);
        dto.setCategoria(Metrica.CategoriaMetrica.FINANCEIRA);
        dto.setUnidadeMedida("BRL");

        ResponseEntity<MetricaDTO> createResp = rest.postForEntity(
            metricasUrl, dto, MetricaDTO.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<MetricaDTO> response = rest.getForEntity(
            metricasUrl + "/" + id, MetricaDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Despesas Operacionais", response.getBody().getNome());
    }

    @Test
    void shouldListarMetricas() {
        MetricaDTO dto1 = new MetricaDTO();
        dto1.setNome("Lucro Líquido");
        dto1.setValor(BigDecimal.valueOf(500000.00));
        dto1.setDataMedicao(LocalDateTime.now());
        dto1.setTipoMetrica(Metrica.TipoMetrica.VALOR_MONETARIO);
        dto1.setCategoria(Metrica.CategoriaMetrica.FINANCEIRA);
        dto1.setUnidadeMedida("BRL");

        rest.postForEntity(metricasUrl, dto1, MetricaDTO.class);

        ResponseEntity<List> response = rest.getForEntity(metricasUrl, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void shouldEvaluateFraudViaMl() {
        Map<String, Object> transacao = Map.of(
            "transacaoId", "TXN-001",
            "valor", 5000.00,
            "clienteId", "CLI-001",
            "tipo", "TRANSFERENCIA"
        );

        ResponseEntity<Map> response = rest.postForEntity(
            mlUrl + "/fraude/avaliar", transacao, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("riscoFraude"));
        assertTrue((Boolean) response.getBody().get("aprovado"));
    }

    @Test
    void shouldGetCreditScoreViaMl() {
        ResponseEntity<Map> response = rest.getForEntity(
            mlUrl + "/credito/score?clienteId=CLI-001", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CLI-001", response.getBody().get("clienteId"));
        assertEquals(750, response.getBody().get("score"));
    }

    @Test
    void shouldSendMessageToChatbot() {
        Map<String, String> body = Map.of("texto", "Qual meu saldo?");

        ResponseEntity<Map> response = rest.postForEntity(
            chatbotUrl + "/mensagem", body, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("resposta"));
        assertFalse((Boolean) response.getBody().get("escalarParaHumano"));
    }

    @Test
    void shouldReturnBiKpis() {
        MetricaDTO dto = new MetricaDTO();
        dto.setNome("Total Clientes");
        dto.setValor(BigDecimal.valueOf(10000));
        dto.setDataMedicao(LocalDateTime.now());
        dto.setTipoMetrica(Metrica.TipoMetrica.CONTADOR);
        dto.setCategoria(Metrica.CategoriaMetrica.CLIENTE);

        rest.postForEntity(metricasUrl, dto, MetricaDTO.class);

        ResponseEntity<Map> response = rest.getForEntity(biUrl + "/kpis", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("totalMetricas"));
        assertNotNull(response.getBody().get("status"));
    }
}
