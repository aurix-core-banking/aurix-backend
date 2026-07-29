package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.financiamento.config.FinanciamentoTestConfig;
import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResponse;
import com.aurix.platform.credit.financiamento.dto.response.ParcelaResponse;
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
class ParcelaControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + "/api/financiamento" + path;
    }

    @Test
    void deveListarParcelas() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> contrato = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long contratoId = contrato.getBody().getId();

        ResponseEntity<ParcelaResponse[]> response = rest.getForEntity(
            url("/contratos/" + contratoId + "/parcelas"), ParcelaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(12);
        assertThat(response.getBody()[0].getContratoId()).isEqualTo(contratoId);
        assertThat(response.getBody()[0].getNumero()).isEqualTo(1);
    }
}
