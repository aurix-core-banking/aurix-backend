package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import com.aurix.platform.cambio.dto.ContratoCambioResponse;
import com.aurix.platform.cambio.dto.FecharContratoRequest;
import com.aurix.platform.cambio.entity.ClienteCambio;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
import com.aurix.platform.cambio.repository.ContratoCambioRepository;
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
class ContratoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContratoCambioRepository contratoRepository;

    @Autowired
    private ClienteCambioRepository clienteCambioRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        contratoRepository.deleteAll();
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

    private Long criarContrato() {
        seedCliente();
        FecharContratoRequest request = new FecharContratoRequest(1L, "COMPRA", "BRL", "USD",
            BigDecimal.valueOf(1000), BigDecimal.valueOf(5.0), "turismo");
        ResponseEntity<ContratoCambioResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoCambioResponse.class);
        return response.getBody().getId();
    }

    @Test
    void testFecharContrato() {
        seedCliente();
        FecharContratoRequest request = new FecharContratoRequest(1L, "COMPRA", "BRL", "USD",
            BigDecimal.valueOf(1000), BigDecimal.valueOf(5.0), "turismo");

        ResponseEntity<ContratoCambioResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTRATADO");
        assertThat(response.getBody().getMoedaOrigem()).isEqualTo("BRL");
        assertThat(response.getBody().getMoedaDestino()).isEqualTo("USD");
    }

    @Test
    void testBuscarContrato() {
        Long id = criarContrato();

        ResponseEntity<ContratoCambioResponse> response = rest.getForEntity(
            url("/contratos/" + id), ContratoCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void testLiquidarContrato() {
        Long id = criarContrato();

        ResponseEntity<ContratoCambioResponse> response = rest.exchange(
            url("/contratos/" + id + "/liquidar"),
            HttpMethod.PATCH, null, ContratoCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("LIQUIDADO");
    }

    @Test
    void testCancelarContrato() {
        Long id = criarContrato();

        ResponseEntity<Void> response = rest.exchange(
            url("/contratos/" + id + "/cancelar"),
            HttpMethod.PATCH, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testListarPorCliente() {
        criarContrato();

        ResponseEntity<ContratoCambioResponse[]> response = rest.getForEntity(
            url("/contratos/cliente/1"), ContratoCambioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ContratoCambioResponse> contratos = Arrays.asList(response.getBody());
        assertThat(contratos).isNotEmpty();
        assertThat(contratos.get(0).getClienteId()).isEqualTo(1L);
    }
}
