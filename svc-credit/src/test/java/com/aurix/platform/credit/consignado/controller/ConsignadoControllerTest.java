package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.consignado.config.ConsignadoTestConfig;
import com.aurix.platform.credit.consignado.dto.ContratoConsignadoResponse;
import com.aurix.platform.credit.consignado.dto.CriarContratoRequest;
import com.aurix.platform.credit.consignado.dto.LiquidarRequest;
import com.aurix.platform.credit.consignado.dto.ParcelaResponse;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import com.aurix.platform.credit.consignado.repository.ContratoConsignadoRepository;
import com.aurix.platform.credit.consignado.repository.MargemConsignavelRepository;
import com.aurix.platform.credit.consignado.repository.ParcelaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(ConsignadoTestConfig.class)
class ConsignadoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContratoConsignadoRepository contratoRepository;

    @Autowired
    private MargemConsignavelRepository margemRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    private RestTemplate rest;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        parcelaRepository.deleteAll();
        contratoRepository.deleteAll();
        margemRepository.deleteAll();
        rest = new RestTemplate();
        seedMargem();
    }

    private void seedMargem() {
        var margem = new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT");
        margemRepository.save(margem);
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/consignado" + path;
    }

    @Test
    void criarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");

        ResponseEntity<ContratoConsignadoResponse> response = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getValorTotal()).isEqualByComparingTo("1200.00");
        assertThat(response.getBody().getStatus()).isEqualTo("ATIVO");
    }

    @Test
    void buscarContratoPorId() {
        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        ResponseEntity<ContratoConsignadoResponse> criada = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContratoConsignadoResponse> response = rest.getForEntity(
            url("/consignados/contratos/" + id), ContratoConsignadoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
    }

    @Test
    void listarParcelas() {
        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        ResponseEntity<ContratoConsignadoResponse> criada = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ParcelaResponse[]> response = rest.getForEntity(
            url("/consignados/contratos/" + id + "/parcelas"), ParcelaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(12);
        assertThat(response.getBody()[0].getContratoId()).isEqualTo(id);
    }

    @Test
    void liquidarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        ResponseEntity<ContratoConsignadoResponse> criada = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);
        Long id = criada.getBody().getId();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(new LiquidarRequest(), headers);
        ResponseEntity<ContratoConsignadoResponse> response = rest.postForEntity(
            url("/consignados/contratos/" + id + "/liquidar"), entity, ContratoConsignadoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("LIQUIDADO");
    }
}
