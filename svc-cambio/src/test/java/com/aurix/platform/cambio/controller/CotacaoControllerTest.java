package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import com.aurix.platform.cambio.dto.CotacaoRequest;
import com.aurix.platform.cambio.dto.CotacaoResponse;
import com.aurix.platform.cambio.repository.CotacaoRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CambioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CambioTestConfig.class)
class CotacaoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        cotacaoRepository.deleteAll();
        rest = new RestTemplate();
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cambio" + path;
    }

    @Test
    void testListarCotacoes() {
        ResponseEntity<CotacaoResponse[]> response = rest.getForEntity(url("/cotacoes"), CotacaoResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<CotacaoResponse> cotacoes = Arrays.asList(response.getBody());
        assertThat(cotacoes).isEmpty();
    }

    @Test
    void testObterCotacao() {
        CotacaoRequest request = new CotacaoRequest("USD", BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.2), "PROPRIO");
        rest.postForEntity(url("/cotacoes"), request, CotacaoResponse.class);

        ResponseEntity<CotacaoResponse> response = rest.getForEntity(url("/cotacoes/USD"), CotacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMoeda()).isEqualTo("USD");
        assertThat(response.getBody().getTaxaCompra()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
        assertThat(response.getBody().getTaxaVenda()).isEqualByComparingTo(BigDecimal.valueOf(5.2));
    }

    @Test
    void testAtualizarCotacao() {
        CotacaoRequest request = new CotacaoRequest("EUR", BigDecimal.valueOf(5.5), BigDecimal.valueOf(5.7), "BACEN");

        ResponseEntity<CotacaoResponse> response = rest.postForEntity(url("/cotacoes"), request, CotacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMoeda()).isEqualTo("EUR");
        assertThat(response.getBody().getTaxaCompra()).isEqualByComparingTo(BigDecimal.valueOf(5.5));
        assertThat(response.getBody().getTaxaVenda()).isEqualByComparingTo(BigDecimal.valueOf(5.7));
    }
}
