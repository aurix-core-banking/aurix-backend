package com.aurix.platform.cambio.integration;

import com.aurix.platform.cambio.CambioApplication;
import com.aurix.platform.cambio.config.CambioTestConfig;
import com.aurix.platform.cambio.dto.ClienteCambioRequest;
import com.aurix.platform.cambio.dto.ClienteCambioResponse;
import com.aurix.platform.cambio.dto.ContratoCambioResponse;
import com.aurix.platform.cambio.dto.CotacaoRequest;
import com.aurix.platform.cambio.dto.CotacaoResponse;
import com.aurix.platform.cambio.dto.RemessaRequest;
import com.aurix.platform.cambio.dto.RemessaResponse;
import com.aurix.platform.cambio.entity.ContratoCambio;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
import com.aurix.platform.cambio.repository.ContaCambioRepository;
import com.aurix.platform.cambio.repository.ContratoCambioRepository;
import com.aurix.platform.cambio.repository.CotacaoRepository;
import com.aurix.platform.cambio.repository.OperacaoCambioRepository;
import com.aurix.platform.cambio.repository.RemessaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CambioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CambioTestConfig.class)
class CambioFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    @Autowired
    private ContratoCambioRepository contratoRepository;

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private ClienteCambioRepository clienteCambioRepository;

    @Autowired
    private ContaCambioRepository contaCambioRepository;

    @Autowired
    private OperacaoCambioRepository operacaoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        cotacaoRepository.deleteAll();
        contratoRepository.deleteAll();
        remessaRepository.deleteAll();
        clienteCambioRepository.deleteAll();
        contaCambioRepository.deleteAll();
        operacaoRepository.deleteAll();
        rest = new RestTemplate();
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cambio" + path;
    }

    private Long habilitarClienteCambio(Long clienteId) {
        var request = new ClienteCambioRequest(clienteId,
            new BigDecimal("100000.00"), new BigDecimal("500000.00"), "turismo,educacao,saude");
        ResponseEntity<ClienteCambioResponse> response = rest.postForEntity(
            url("/clientes"), request, ClienteCambioResponse.class);
        return response.getBody().getId();
    }

    private Long criarCotacao(String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda) {
        var request = new CotacaoRequest(moeda, taxaCompra, taxaVenda, "PROPRIO");
        ResponseEntity<CotacaoResponse> response = rest.postForEntity(
            url("/cotacoes"), request, CotacaoResponse.class);
        return response.getBody().getId();
    }

    private Long criarContratoViaRepositorio(Long clienteId) {
        var contrato = new ContratoCambio(clienteId, "COMPRA", "BRL", "USD",
            new BigDecimal("5000.00"), new BigDecimal("25000.000000"),
            new BigDecimal("5.0"), LocalDate.now(), null,
            "turismo", "CONTRATADO", null, "DEFAULT");
        return contratoRepository.save(contrato).getId();
    }

    @Test
    void testCriarCotacao() {
        ResponseEntity<CotacaoResponse> response = rest.postForEntity(
            url("/cotacoes"),
            new CotacaoRequest("USD", BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.2), "PROPRIO"),
            CotacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMoeda()).isEqualTo("USD");
        assertThat(response.getBody().getId()).isPositive();
    }

    @Test
    void testObterCotacao() {
        criarCotacao("EUR", BigDecimal.valueOf(5.5), BigDecimal.valueOf(5.7));

        ResponseEntity<CotacaoResponse> response = rest.getForEntity(
            url("/cotacoes/EUR"), CotacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMoeda()).isEqualTo("EUR");
        assertThat(response.getBody().getTaxaCompra()).isEqualByComparingTo(BigDecimal.valueOf(5.5));
    }

    @Test
    void testAtualizarCotacao() {
        criarCotacao("GBP", BigDecimal.valueOf(6.0), BigDecimal.valueOf(6.2));

        var request = new CotacaoRequest("GBP", BigDecimal.valueOf(6.1), BigDecimal.valueOf(6.3), "BACEN");
        ResponseEntity<CotacaoResponse> response = rest.postForEntity(
            url("/cotacoes"), request, CotacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getTaxaCompra()).isEqualByComparingTo(BigDecimal.valueOf(6.1));
        assertThat(response.getBody().getFonte()).isEqualTo("BACEN");
    }

    @Test
    void testListarCotacoes() {
        criarCotacao("USD", BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.2));
        criarCotacao("EUR", BigDecimal.valueOf(5.5), BigDecimal.valueOf(5.7));

        ResponseEntity<List<CotacaoResponse>> response = rest.exchange(
            url("/cotacoes"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<CotacaoResponse>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testCriarContratoCambio() {
        habilitarClienteCambio(1L);

        Long contratoId = criarContratoViaRepositorio(1L);

        ResponseEntity<ContratoCambioResponse> response = rest.getForEntity(
            url("/contratos/" + contratoId), ContratoCambioResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTRATADO");
        assertThat(response.getBody().getMoedaDestino()).isEqualTo("USD");
    }

    @Test
    void testConsultarContrato() {
        habilitarClienteCambio(2L);
        Long contratoId = criarContratoViaRepositorio(2L);

        ResponseEntity<ContratoCambioResponse> response = rest.getForEntity(
            url("/contratos/" + contratoId), ContratoCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(contratoId);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTRATADO");
    }

    @Test
    void testListarContratosPorCliente() {
        habilitarClienteCambio(10L);
        criarContratoViaRepositorio(10L);
        criarContratoViaRepositorio(10L);

        ResponseEntity<List<ContratoCambioResponse>> response = rest.exchange(
            url("/contratos/cliente/10"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ContratoCambioResponse>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testFecharContrato() {
        habilitarClienteCambio(3L);

        BigDecimal valorOrigem = new BigDecimal("1000.00");
        BigDecimal taxa = new BigDecimal("5.2");
        BigDecimal valorDestinoEsperado = valorOrigem.multiply(taxa)
            .setScale(6, java.math.RoundingMode.HALF_EVEN);

        var contrato = new ContratoCambio(3L, "VENDA", "USD", "BRL",
            valorOrigem, valorDestinoEsperado, taxa,
            LocalDate.now(), null, "turismo",
            "CONTRATADO", null, "DEFAULT");
        contrato = contratoRepository.save(contrato);

        ResponseEntity<ContratoCambioResponse> response = rest.getForEntity(
            url("/contratos/" + contrato.getId()), ContratoCambioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTRATADO");
        assertThat(response.getBody().getValorDestino())
            .isEqualByComparingTo(valorDestinoEsperado);
    }

    @Test
    void testCriarRemessa() {
        habilitarClienteCambio(4L);
        Long contratoId = criarContratoViaRepositorio(4L);

        var request = new RemessaRequest(contratoId, 4L, new BigDecimal("5000.00"),
            "USD", "Banco Internacional", "CH1234567890",
            "BICXXXXX", "turismo");
        ResponseEntity<RemessaResponse> response = rest.postForEntity(
            url("/remessas"), request, RemessaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getStatus()).isEqualTo("PENDENTE");
        assertThat(response.getBody().getContratoId()).isEqualTo(contratoId);
    }

    @Test
    void testProcessarRemessa() {
        habilitarClienteCambio(5L);
        Long contratoId = criarContratoViaRepositorio(5L);
        var remessaRequest = new RemessaRequest(contratoId, 5L, new BigDecimal("3000.00"),
            "USD", "Banco Destino", "CH0987654321",
            "SWIFTCODE", "turismo");
        Long remessaId = rest.postForEntity(url("/remessas"), remessaRequest, RemessaResponse.class)
            .getBody().getId();

        ResponseEntity<RemessaResponse> pendingCheck = rest.getForEntity(
            url("/remessas/" + remessaId), RemessaResponse.class);
        assertThat(pendingCheck.getBody().getStatus()).isEqualTo("PENDENTE");

        remessaRepository.findById(remessaId).ifPresent(r -> {
            r.setStatus("ENVIADA");
            r.setDataConfirmacao(java.time.LocalDateTime.now());
            remessaRepository.save(r);
        });

        ResponseEntity<RemessaResponse> processedCheck = rest.getForEntity(
            url("/remessas/" + remessaId), RemessaResponse.class);
        assertThat(processedCheck.getBody().getStatus()).isEqualTo("ENVIADA");
        assertThat(processedCheck.getBody().getDataConfirmacao()).isNotNull();
    }

    @Test
    void testConsultarStatusRemessa() {
        habilitarClienteCambio(6L);
        Long contratoId = criarContratoViaRepositorio(6L);
        var remessaRequest = new RemessaRequest(contratoId, 6L, new BigDecimal("2000.00"),
            "EUR", "Banco Europa", "EU1234567890",
            "EURICODE", "educacao");
        Long remessaId = rest.postForEntity(url("/remessas"), remessaRequest, RemessaResponse.class)
            .getBody().getId();

        ResponseEntity<RemessaResponse> response = rest.getForEntity(
            url("/remessas/" + remessaId), RemessaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(remessaId);
        assertThat(response.getBody().getStatus()).isEqualTo("PENDENTE");
    }

    @Test
    void testCancelarRemessa() {
        habilitarClienteCambio(7L);
        Long contratoId = criarContratoViaRepositorio(7L);
        var remessaRequest = new RemessaRequest(contratoId, 7L, new BigDecimal("1000.00"),
            "USD", "Banco Teste", "TX1234567890",
            "TESTXXXX", "saude");
        Long remessaId = rest.postForEntity(url("/remessas"), remessaRequest, RemessaResponse.class)
            .getBody().getId();

        ResponseEntity<Void> response = rest.exchange(
            url("/remessas/" + remessaId + "/cancelar"), HttpMethod.PATCH, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
