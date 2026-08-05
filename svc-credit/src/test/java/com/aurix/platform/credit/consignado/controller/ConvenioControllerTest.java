package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.consignado.config.ConsignadoTestConfig;
import com.aurix.platform.credit.consignado.dto.ConvenioRequest;
import com.aurix.platform.credit.consignado.dto.ConvenioResponse;
import com.aurix.platform.credit.consignado.repository.ConvenioConsignadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(ConsignadoTestConfig.class)
class ConvenioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ConvenioConsignadoRepository repository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/consignado" + path;
    }

    @Test
    void criarConvenio() {
        var request = new ConvenioRequest("INSS Convenio", "INSS", "INSS001", true);

        ResponseEntity<ConvenioResponse> response = rest.postForEntity(
            url("/convenios"), request, ConvenioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getNome()).isEqualTo("INSS Convenio");
        assertThat(response.getBody().getTipo()).isEqualTo("INSS");
        assertThat(response.getBody().isAtivo()).isTrue();
    }

    @Test
    void listarConvenios() {
        var request = new ConvenioRequest("SIAFI Convenio", "SIAFI", "SIAFI001", true);
        rest.postForEntity(url("/convenios"), request, ConvenioResponse.class);

        ResponseEntity<ConvenioResponse[]> response = rest.getForEntity(
            url("/convenios"), ConvenioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getNome()).isEqualTo("SIAFI Convenio");
    }
}
