package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.financiamento.config.FinanciamentoTestConfig;
import com.aurix.platform.credit.financiamento.dto.request.SimulacaoRequest;
import com.aurix.platform.credit.financiamento.dto.response.SimulacaoResponse;
import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.entity.TipoFinanciamento;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(FinanciamentoTestConfig.class)
class SimulacaoControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + "/api/financiamento" + path;
    }

    @Test
    void deveSimularSAC() {
        var request = new SimulacaoRequest(TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), 12, null, SistemaAmortizacao.SAC);

        ResponseEntity<SimulacaoResponse> response = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("SAC");
    }

    @Test
    void deveSimularPrice() {
        var request = new SimulacaoRequest(TipoFinanciamento.VEICULAR,
            new BigDecimal("50000"), 24, null, SistemaAmortizacao.PRICE);

        ResponseEntity<SimulacaoResponse> response = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("PRICE");
        assertThat(response.getBody().getValorParcela()).isPositive();
    }

    @Test
    void deveBuscarSimulacao() {
        var request = new SimulacaoRequest(TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), 12, null, SistemaAmortizacao.PRICE);

        ResponseEntity<SimulacaoResponse> criada = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<SimulacaoResponse> response = rest.getForEntity(
            url("/simulacoes/" + id), SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }
}
