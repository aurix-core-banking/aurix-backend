package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.financiamento.config.FinanciamentoTestConfig;
import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResumoResponse;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResponse;
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
class ContratoControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + "/api/financiamento" + path;
    }

    @Test
    void deveContratarFinanciamento() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), new BigDecimal("50000"),
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getValorFinanciado()).isEqualByComparingTo("200000");
    }

    @Test
    void deveBuscarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.VEICULAR,
            new BigDecimal("80000"), BigDecimal.ZERO,
            24, SistemaAmortizacao.SAC, null, null);

        ResponseEntity<ContratoResponse> criada = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContratoResponse> response = rest.getForEntity(
            url("/contratos/" + id), ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
    }

    @Test
    void deveListarPorCliente() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("150000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        rest.postForEntity(url("/contratos"), request, ContratoResponse.class);

        ResponseEntity<ContratoResumoResponse[]> response = rest.getForEntity(
            url("/contratos/cliente/1"), ContratoResumoResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isPositive();
        assertThat(response.getBody()[0].getId()).isPositive();
    }
}
