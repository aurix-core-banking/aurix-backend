package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.client.ParceiroCambioClient;
import com.aurix.platform.cambio.client.SwiftClient;
import com.aurix.platform.cambio.dto.AtualizarLimiteRequest;
import com.aurix.platform.cambio.dto.ClienteCambioRequest;
import com.aurix.platform.cambio.dto.ClienteCambioResponse;
import com.aurix.platform.cambio.dto.LimiteCambioResponse;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
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
import org.springframework.http.HttpEntity;
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
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteCambioRepository clienteCambioRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        clienteCambioRepository.deleteAll();
        rest = new RestTemplate();
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cambio" + path;
    }

    private Long habilitarCliente() {
        ClienteCambioRequest request = new ClienteCambioRequest(1L, BigDecimal.valueOf(10000), BigDecimal.valueOf(30000), "TURISMO");
        ResponseEntity<ClienteCambioResponse> response = rest.postForEntity(
            url("/clientes"), request, ClienteCambioResponse.class);
        return response.getBody().getId();
    }

    @Test
    void testListarClientes() {
        ResponseEntity<ClienteCambioResponse[]> response = rest.getForEntity(
            url("/clientes"), ClienteCambioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ClienteCambioResponse> clientes = Arrays.asList(response.getBody());
        assertThat(clientes).isEmpty();
    }

    @Test
    void testHabilitarCliente() {
        ClienteCambioRequest request = new ClienteCambioRequest(1L, BigDecimal.valueOf(10000), BigDecimal.valueOf(30000), "TURISMO");

        ResponseEntity<ClienteCambioResponse> response = rest.postForEntity(
            url("/clientes"), request, ClienteCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getLimiteRemessaMensal()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        assertThat(response.getBody().getLimiteRemessaAnual()).isEqualByComparingTo(BigDecimal.valueOf(30000));
    }

    @Test
    void testConsultarLimites() {
        habilitarCliente();

        ResponseEntity<LimiteCambioResponse> response = rest.getForEntity(
            url("/clientes/cliente/1/limites"), LimiteCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getLimiteRemessaMensal()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        assertThat(response.getBody().getLimiteRemessaAnual()).isEqualByComparingTo(BigDecimal.valueOf(30000));
    }

    @Test
    void testAjustarLimites() {
        Long id = habilitarCliente();
        AtualizarLimiteRequest request = new AtualizarLimiteRequest(BigDecimal.valueOf(20000), BigDecimal.valueOf(50000));

        ResponseEntity<Void> response = rest.exchange(
            url("/clientes/" + id + "/limites"),
            HttpMethod.PUT, new HttpEntity<>(request), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
