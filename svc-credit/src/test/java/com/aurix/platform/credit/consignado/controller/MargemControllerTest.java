package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.consignado.config.ConsignadoTestConfig;
import com.aurix.platform.credit.consignado.dto.MargemResponse;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import com.aurix.platform.credit.consignado.repository.MargemConsignavelRepository;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(ConsignadoTestConfig.class)
class MargemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MargemConsignavelRepository repository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        rest = new RestTemplate();
        seedMargem();
    }

    private void seedMargem() {
        var margem = new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("3500.00"), new BigDecimal("1500.00"),
            LocalDateTime.now(), "DEFAULT");
        repository.save(margem);
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/consignado" + path;
    }

    @Test
    void consultarMargem() {
        ResponseEntity<MargemResponse> response = rest.getForEntity(
            url("/margem/1"), MargemResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getMargemTotal()).isEqualByComparingTo("5000.00");
        assertThat(response.getBody().getMargemDisponivel()).isEqualByComparingTo("3500.00");
        assertThat(response.getBody().getMargemUtilizada()).isEqualByComparingTo("1500.00");
    }
}
