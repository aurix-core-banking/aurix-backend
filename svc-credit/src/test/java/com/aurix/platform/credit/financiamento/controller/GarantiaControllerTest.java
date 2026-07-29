package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.financiamento.config.FinanciamentoTestConfig;
import com.aurix.platform.credit.financiamento.dto.request.BemRequest;
import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.credit.financiamento.dto.request.GarantiaRequest;
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
class GarantiaControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + "/api/financiamento" + path;
    }

    @Test
    void deveRegistrarEGarantia() {
        var bem = new BemRequest("IMOVEL", "Casa residencial",
            new BigDecimal("300000"), null, null, "RGI-12345");
        var garantia = new GarantiaRequest("ALIENACAO_FIDUCIARIA",
            new BigDecimal("250000"), "CARTORIO");

        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), new BigDecimal("50000"),
            12, SistemaAmortizacao.PRICE, bem, garantia);

        ResponseEntity<ContratoResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getGarantias()).isNotEmpty();
    }
}
