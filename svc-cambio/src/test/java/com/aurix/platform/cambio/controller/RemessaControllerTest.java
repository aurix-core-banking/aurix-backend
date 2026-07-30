package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import com.aurix.platform.cambio.dto.RemessaRequest;
import com.aurix.platform.cambio.dto.RemessaResponse;
import com.aurix.platform.cambio.entity.ClienteCambio;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
import com.aurix.platform.cambio.repository.RemessaRepository;
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
import org.springframework.http.HttpMethod;
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
class RemessaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private ClienteCambioRepository clienteCambioRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        remessaRepository.deleteAll();
        clienteCambioRepository.deleteAll();
        rest = new RestTemplate();
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cambio" + path;
    }

    private void seedCliente() {
        ClienteCambio cliente = new ClienteCambio(1L, null, BigDecimal.valueOf(100000), BigDecimal.valueOf(300000), "TURISMO", "DEFAULT");
        clienteCambioRepository.save(cliente);
    }

    private Long criarRemessa() {
        seedCliente();
        RemessaRequest request = new RemessaRequest(1L, 1L, BigDecimal.valueOf(5000), "USD",
            "Banco do Brasil", "12345-6", "BBBRBRRJ", "turismo");
        ResponseEntity<RemessaResponse> response = rest.postForEntity(
            url("/remessas"), request, RemessaResponse.class);
        return response.getBody().getId();
    }

    @Test
    void testSolicitarRemessa() {
        seedCliente();
        RemessaRequest request = new RemessaRequest(1L, 1L, BigDecimal.valueOf(5000), "USD",
            "Banco do Brasil", "12345-6", "BBBRBRRJ", "turismo");

        ResponseEntity<RemessaResponse> response = rest.postForEntity(
            url("/remessas"), request, RemessaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getValor()).isEqualByComparingTo(BigDecimal.valueOf(5000));
        assertThat(response.getBody().getStatus()).isEqualTo("PENDENTE");
    }

    @Test
    void testBuscarRemessa() {
        Long id = criarRemessa();

        ResponseEntity<RemessaResponse> response = rest.getForEntity(
            url("/remessas/" + id), RemessaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void testListarPorCliente() {
        criarRemessa();

        ResponseEntity<RemessaResponse[]> response = rest.getForEntity(
            url("/remessas/cliente/1"), RemessaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<RemessaResponse> remessas = Arrays.asList(response.getBody());
        assertThat(remessas).isNotEmpty();
        assertThat(remessas.get(0).getClienteId()).isEqualTo(1L);
    }

    @Test
    void testCancelarRemessa() {
        Long id = criarRemessa();

        ResponseEntity<Void> response = rest.exchange(
            url("/remessas/" + id + "/cancelar"),
            HttpMethod.PATCH, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
