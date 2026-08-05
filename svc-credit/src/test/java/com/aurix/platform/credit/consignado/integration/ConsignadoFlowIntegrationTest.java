package com.aurix.platform.credit.consignado.integration;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.consignado.config.ConsignadoTestConfig;
import com.aurix.platform.credit.consignado.dto.ContratoConsignadoResponse;
import com.aurix.platform.credit.consignado.dto.ConvenioRequest;
import com.aurix.platform.credit.consignado.dto.ConvenioResponse;
import com.aurix.platform.credit.consignado.dto.CriarContratoRequest;
import com.aurix.platform.credit.consignado.dto.LiquidarRequest;
import com.aurix.platform.credit.consignado.dto.MargemResponse;
import com.aurix.platform.credit.consignado.dto.ParcelaResponse;
import com.aurix.platform.credit.consignado.dto.RenegociarRequest;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import com.aurix.platform.credit.consignado.repository.MargemConsignavelRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(ConsignadoTestConfig.class)
class ConsignadoFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MargemConsignavelRepository margemRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        margemRepository.deleteAll();
        rest = new RestTemplate(new JdkClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/consignado" + path;
    }

    private void seedMargemInss() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("3500.00"), new BigDecimal("1500.00"),
            LocalDateTime.now(), "DEFAULT"));
    }

    @Test
    void testCriarConvenio() {
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
    void testListarConvenios() {
        var request = new ConvenioRequest("SIAFI Convenio", "SIAFI", "SIAFI001", true);
        rest.postForEntity(url("/convenios"), request, ConvenioResponse.class);

        ResponseEntity<ConvenioResponse[]> response = rest.getForEntity(
            url("/convenios"), ConvenioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testConsultarMargemINSS() {
        seedMargemInss();

        ResponseEntity<MargemResponse> response = rest.getForEntity(
            url("/margem/1"), MargemResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getMargemTotal()).isEqualByComparingTo("5000.00");
        assertThat(response.getBody().getMargemDisponivel()).isEqualByComparingTo("3500.00");
        assertThat(response.getBody().getMargemUtilizada()).isEqualByComparingTo("1500.00");
    }

    @Test
    void testConsultarMargemMultiplasFontes() {
        margemRepository.save(new MargemConsignavel(
            2L, "INSS", new BigDecimal("3000.00"),
            new BigDecimal("2000.00"), new BigDecimal("1000.00"),
            LocalDateTime.now(), "DEFAULT"));
        margemRepository.save(new MargemConsignavel(
            2L, "SIAFI", new BigDecimal("2000.00"),
            new BigDecimal("1500.00"), new BigDecimal("500.00"),
            LocalDateTime.now(), "DEFAULT"));
        margemRepository.save(new MargemConsignavel(
            2L, "EMPRESA", new BigDecimal("1000.00"),
            new BigDecimal("1000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

        ResponseEntity<MargemResponse> response = rest.getForEntity(
            url("/margem/2"), MargemResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMargemTotal()).isEqualByComparingTo("6000.00");
        assertThat(response.getBody().getMargemDisponivel()).isEqualByComparingTo("4500.00");
        assertThat(response.getBody().getMargemUtilizada()).isEqualByComparingTo("1500.00");
    }

    @Test
    void testMargemInsuficiente() {
        margemRepository.save(new MargemConsignavel(
            3L, "INSS", new BigDecimal("1000.00"),
            new BigDecimal("100.00"), new BigDecimal("900.00"),
            LocalDateTime.now(), "DEFAULT"));

        var request = new CriarContratoRequest(
            3L, 100L, new BigDecimal("500.00"),
            new BigDecimal("0.0199"), 12, "INSS");

        assertThatThrownBy(() ->
            rest.postForEntity(url("/consignados/contratos"), request, ContratoConsignadoResponse.class)
        ).isInstanceOf(HttpServerErrorException.class);
    }

    @Test
    void testContratarConsignado() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

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
        assertThat(response.getBody().getValorParcela()).isEqualByComparingTo("100.00");
    }

    @Test
    void testConsultarContrato() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

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
    void testListarContratosPorServidor() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        rest.postForEntity(url("/consignados/contratos"), request, ContratoConsignadoResponse.class);

        ResponseEntity<ContratoConsignadoResponse[]> response = rest.getForEntity(
            url("/consignados/contratos/cliente/1"), ContratoConsignadoResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isPositive();
        assertThat(response.getBody()[0].getClienteId()).isEqualTo(1L);
    }

    @Test
    void testListarParcelas() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

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
        assertThat(response.getBody()[0].getNumero()).isEqualTo(1);
        assertThat(response.getBody()[0].getStatus()).isEqualTo("PENDENTE");
    }

    @Test
    void testLiquidarConsignado() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        ResponseEntity<ContratoConsignadoResponse> criada = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContratoConsignadoResponse> response = rest.postForEntity(
            url("/consignados/contratos/" + id + "/liquidar"),
            new LiquidarRequest(), ContratoConsignadoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("LIQUIDADO");
    }

    @Test
    void testRenegociarConsignado() {
        margemRepository.save(new MargemConsignavel(
            1L, "INSS", new BigDecimal("5000.00"),
            new BigDecimal("5000.00"), BigDecimal.ZERO,
            LocalDateTime.now(), "DEFAULT"));

        var request = new CriarContratoRequest(
            1L, 100L, new BigDecimal("1200.00"),
            new BigDecimal("0.0199"), 12, "INSS");
        ResponseEntity<ContratoConsignadoResponse> criada = rest.postForEntity(
            url("/consignados/contratos"), request, ContratoConsignadoResponse.class);
        Long id = criada.getBody().getId();

        var renegociar = new RenegociarRequest(new BigDecimal("2400.00"), 24, new BigDecimal("0.015"));

        ResponseEntity<ContratoConsignadoResponse> response = rest.exchange(
            url("/consignados/contratos/" + id + "/renegociar"), HttpMethod.PATCH,
            new HttpEntity<>(renegociar), ContratoConsignadoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getValorTotal()).isEqualByComparingTo("2400.00");
        assertThat(response.getBody().getPrazoMeses()).isEqualTo(24);
    }
}
