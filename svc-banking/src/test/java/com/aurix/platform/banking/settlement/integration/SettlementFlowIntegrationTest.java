package com.aurix.platform.banking.settlement.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.settlement.config.SettlementTestConfig;
import com.aurix.platform.banking.settlement.controller.SettlementController;
import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SettlementTestConfig.class)
class SettlementFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LiquidezRepository liquidezRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        rest = new RestTemplate();
        var tt = new TransactionTemplate(transactionManager);
        tt.executeWithoutResult(status -> {
            liquidezRepository.deleteAll();
        });
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testProcessarLiquidezPIX() {
        Liquidez request = new Liquidez();
        request.setNumeroLiquidez("LIQ-TEST-001");
        request.setDataLiquidez(LocalDate.now());
        request.setHoraLiquidez(LocalDateTime.now());
        request.setTipoOperacao(Liquidez.TipoOperacao.PIX);
        request.setCanal(Liquidez.Canal.API);
        request.setContaOrigem("12345-6");
        request.setContaDestino("67890-1");
        request.setValor(new BigDecimal("1000.00"));
        request.setStatus(Liquidez.StatusLiquidez.PENDENTE);

        ResponseEntity<Liquidez> response = rest.postForEntity(url("/settlement/processar"), request, Liquidez.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo(Liquidez.StatusLiquidez.LIQUIDADO);
        assertThat(response.getBody().getCodigoRetorno()).isEqualTo("000");
    }

    @Test
    void testProcessarLiquidezTED() {
        Liquidez request = new Liquidez();
        request.setNumeroLiquidez("LIQ-TEST-002");
        request.setDataLiquidez(LocalDate.now());
        request.setHoraLiquidez(LocalDateTime.now());
        request.setTipoOperacao(Liquidez.TipoOperacao.TED);
        request.setCanal(Liquidez.Canal.API);
        request.setContaOrigem("12345-6");
        request.setContaDestino("67890-1");
        request.setValor(new BigDecimal("500.00"));
        request.setStatus(Liquidez.StatusLiquidez.PENDENTE);

        ResponseEntity<Liquidez> response = rest.postForEntity(url("/settlement/processar"), request, Liquidez.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testBuscarLiquidezPendentes() {
        Liquidez pendente = new Liquidez();
        pendente.setNumeroLiquidez("LIQ-PEND-001");
        pendente.setDataLiquidez(LocalDate.now());
        pendente.setHoraLiquidez(LocalDateTime.now());
        pendente.setTipoOperacao(Liquidez.TipoOperacao.PIX);
        pendente.setCanal(Liquidez.Canal.API);
        pendente.setValor(new BigDecimal("100.00"));
        pendente.setStatus(Liquidez.StatusLiquidez.PENDENTE);
        liquidezRepository.save(pendente);

        ResponseEntity<List<Liquidez>> response = rest.exchange(
            url("/settlement/pendentes"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Liquidez>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getNumeroLiquidez()).isEqualTo("LIQ-PEND-001");
    }

    @Test
    void testDashboard() {
        ResponseEntity<SettlementController.DashboardSettlement> response = rest.getForEntity(
            url("/settlement/dashboard"), SettlementController.DashboardSettlement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatusSistema()).isEqualTo("ATIVO");
        assertThat(response.getBody().getLiquidezPendentes()).isNotNull();
    }
}
