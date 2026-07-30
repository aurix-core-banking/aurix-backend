package com.aurix.platform.banking.pricing.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.pricing.config.PricingTestConfig;
import com.aurix.platform.banking.pricing.controller.PricingController;
import com.aurix.platform.banking.core.entity.PacoteTarifas;
import com.aurix.platform.banking.pricing.entity.SimulacaoTarifas;
import com.aurix.platform.banking.pricing.repository.SimulacaoTarifasRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(PricingTestConfig.class)
class PricingFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @MockitoBean
    private SimulacaoTarifasRepository simulacaoTarifasRepository;

    @BeforeEach
    void setUp() {
        rest = new RestTemplate();
        when(simulacaoTarifasRepository.save(any(SimulacaoTarifas.class))).thenAnswer(invocation -> {
            SimulacaoTarifas s = invocation.getArgument(0);
            s.setId(1L);
            s.setStatusSimulacao("CONCLUIDA");
            s.setNumeroSimulacao("SIM-" + System.currentTimeMillis());
            return s;
        });
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testCalcularTarifa() {
        ResponseEntity<BigDecimal> response = rest.postForEntity(
            url("/pricing/calcular?operacao=PIX&clienteId=cli-001&produto=CONTA_CORRENTE&valorOperacao=1000&canal=API"),
            null, BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testSimularTarifas() {
        ResponseEntity<SimulacaoTarifas> response = rest.postForEntity(
            url("/pricing/simular?clienteId=cli-001&produto=CONTA_CORRENTE&volumeOperacoes=100&valorTotalOperacoes=10000&periodoMeses=12"),
            null, SimulacaoTarifas.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNumeroSimulacao()).isNotNull();
        assertThat(response.getBody().getStatusSimulacao()).isEqualTo("CONCLUIDA");
    }

    @Test
    void testCriarPacotePersonalizado() {
        ResponseEntity<PacoteTarifas> response = rest.postForEntity(
            url("/pricing/pacote/personalizado?clienteId=cli-001&nomePacote=MeuPacote&codigosTarifas=TARIFA001,TARIFA002&descontoPercentual=10"),
            null, PacoteTarifas.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoPacote()).isNotNull();
        assertThat(response.getBody().getNomePacote()).isEqualTo("MeuPacote");
    }

    @Test
    void testDashboard() {
        ResponseEntity<PricingController.DashboardPricing> response = rest.getForEntity(
            url("/pricing/dashboard"), PricingController.DashboardPricing.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatusMotor()).isEqualTo("ATIVO");
        assertThat(response.getBody().getTotalTarifas()).isNotNull();
    }
}
