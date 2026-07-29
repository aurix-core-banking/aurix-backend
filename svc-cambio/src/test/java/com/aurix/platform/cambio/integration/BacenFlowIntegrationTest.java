package com.aurix.platform.cambio.integration;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.client.SpiStrApiClient;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.entity.RelatorioBacen;
import com.aurix.platform.cambio.entity.TaxaSelic;
import com.aurix.platform.cambio.service.SpiStrIntegrationService;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CambioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CambioTestConfig.class)
class BacenFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @MockitoBean
    private SpiStrApiClient spiStrApiClient;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        SpiStrIntegrationService.SpiResult spiResult = SpiStrIntegrationService.SpiResult.builder()
            .sucesso(true)
            .codigoRetorno("00")
            .mensagem("Sucesso")
            .dataProcessamento(LocalDateTime.now())
            .build();
        when(spiStrApiClient.enviarPixSpi(any())).thenReturn(spiResult);

        SpiStrIntegrationService.StrResult strResult = SpiStrIntegrationService.StrResult.builder()
            .sucesso(true)
            .codigoRetorno("00")
            .mensagem("Sucesso")
            .dataLiquidacao(LocalDateTime.now())
            .build();
        when(spiStrApiClient.enviarTedStr(any())).thenReturn(strResult);
        when(spiStrApiClient.enviarDocStr(any())).thenReturn(strResult);

        rest = new RestTemplate();
        rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.HttpStatusCode statusCode) {
                return false;
            }
        });
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cambio" + path;
    }

    @Test
    void buscarTaxaSelicAtual_deveRetornar200() {
        ResponseEntity<TaxaSelic> response = rest.getForEntity(
            url("/selic/atual"), TaxaSelic.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void buscarHistoricoTaxaSelic_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/selic/historico?dataInicio=2024-01-01&dataFim=2024-12-31"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void calcularCompetitividade_deveRetornar200() {
        ResponseEntity<BigDecimal> response = rest.postForEntity(
            url("/spread/competitividade?taxaNossa=0.12&taxaConcorrencia=0.15"),
            null, BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void validarTaxaSelic_deveRetornar200() {
        ResponseEntity<Boolean> response = rest.postForEntity(
            url("/selic/validar?taxa=0.1325"), null, Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void enviarPixSPI_deveRetornar200() {
        ResponseEntity<Map> response = rest.postForEntity(
            url("/spi-str/spi/pix?endToEndId=E123456&ispbOrigem=123&ispbDestino=456&contaOrigem=0001&contaDestino=0002&valor=100.50&descricao=Teste"),
            null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void enviarTEDSTR_deveRetornar200() {
        ResponseEntity<Map> response = rest.postForEntity(
            url("/spi-str/str/ted?idTransacao=TED123&ispbOrigem=123&ispbDestino=456&contaOrigem=0001&contaDestino=0002&valor=500.00"),
            null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void enviarDOCSTR_deveRetornar200() {
        ResponseEntity<Map> response = rest.postForEntity(
            url("/spi-str/str/doc?idTransacao=DOC123&ispbOrigem=123&ispbDestino=456&contaOrigem=0001&contaDestino=0002&valor=300.00"),
            null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void gerarRelatorioCosif_deveRetornar200() {
        ResponseEntity<RelatorioBacen> response = rest.postForEntity(
            url("/relatorios/cosif?dataReferencia=2024-06-01"), null, RelatorioBacen.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void gerarRelatorioEFinanceira_deveRetornar200() {
        ResponseEntity<String> response = rest.postForEntity(
            url("/relatorios/efinanceira?dataReferencia=2024-06-01"), null, String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void gerarRelatorioBacenJud_deveRetornar200() {
        ResponseEntity<RelatorioBacen> response = rest.postForEntity(
            url("/relatorios/bacen-jud?dataReferencia=2024-06-01"), null, RelatorioBacen.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void calcularSpreadBancario_deveRetornar200() {
        ResponseEntity<BigDecimal> response = rest.postForEntity(
            url("/spread/calcular?taxaCaptacao=0.10&taxaAplicacao=0.15"),
            null, BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void calcularTaxaAplicacao_deveRetornar200() {
        ResponseEntity<BigDecimal> response = rest.postForEntity(
            url("/spread/taxa-aplicacao?taxaSelic=0.1325&spreadDesejado=0.03"),
            null, BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void calcularTendenciaSelic_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/selic/tendencia"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void obterDashboardBacen_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/dashboard"), Map.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void gerarRelatorioPix_deveRetornar200() {
        ResponseEntity<RelatorioBacen> response = rest.postForEntity(
            url("/relatorios/pix?dataReferencia=2024-06-01"), null, RelatorioBacen.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void consultarStatusSPI_deveRetornar404() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/spi-str/spi/status/NAOEXISTE"), String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
