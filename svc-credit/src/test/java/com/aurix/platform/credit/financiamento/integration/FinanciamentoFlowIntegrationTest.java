package com.aurix.platform.credit.financiamento.integration;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.financiamento.config.FinanciamentoTestConfig;
import com.aurix.platform.credit.financiamento.dto.request.AtualizarTaxaRequest;
import com.aurix.platform.credit.financiamento.dto.request.BemRequest;
import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.credit.financiamento.dto.request.GarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.request.LiberarGarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.request.PagarParcelaRequest;
import com.aurix.platform.credit.financiamento.dto.request.RenegociarRequest;
import com.aurix.platform.credit.financiamento.dto.request.SimulacaoRequest;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResponse;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResumoResponse;
import com.aurix.platform.credit.financiamento.dto.response.GarantiaResponse;
import com.aurix.platform.credit.financiamento.dto.response.ParcelaResponse;
import com.aurix.platform.credit.financiamento.dto.response.SimulacaoResponse;
import com.aurix.platform.credit.financiamento.dto.response.TaxasResponse;
import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.entity.TipoFinanciamento;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(FinanciamentoTestConfig.class)
class FinanciamentoFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        rest = new RestTemplate(new JdkClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/financiamento" + path;
    }

    @Test
    void testSimularFinanciamentoSAC() {
        var request = new SimulacaoRequest(TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), 12, null, SistemaAmortizacao.SAC);

        ResponseEntity<SimulacaoResponse> response = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("SAC");
        assertThat(response.getBody().getValorParcela()).isPositive();
        assertThat(response.getBody().getTabelaSAC()).isNotNull();
        assertThat(response.getBody().getTabelaPrice()).isNotNull();
    }

    @Test
    void testSimularFinanciamentoPrice() {
        var request = new SimulacaoRequest(TipoFinanciamento.VEICULAR,
            new BigDecimal("50000"), 24, null, SistemaAmortizacao.PRICE);

        ResponseEntity<SimulacaoResponse> response = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("PRICE");
        assertThat(response.getBody().getValorParcela()).isPositive();
        assertThat(response.getBody().getTabelaPrice()).isNotNull();
        assertThat(response.getBody().getTabelaSAC()).isNotNull();
    }

    @Test
    void testSimularFinanciamentoSACRE() {
        var request = new SimulacaoRequest(TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), 24, null, SistemaAmortizacao.SACRE);

        ResponseEntity<SimulacaoResponse> response = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("SACRE");
        assertThat(response.getBody().getValorParcela()).isPositive();
        assertThat(response.getBody().getCet()).isNotNull();
    }

    @Test
    void testConsultarSimulacao() {
        var request = new SimulacaoRequest(TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), 12, null, SistemaAmortizacao.PRICE);

        ResponseEntity<SimulacaoResponse> criada = rest.postForEntity(
            url("/simulacoes"), request, SimulacaoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<SimulacaoResponse> response = rest.getForEntity(
            url("/simulacoes/" + id), SimulacaoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getValorFinanciado()).isEqualByComparingTo("100000");
    }

    @Test
    void testContratarFinanciamento() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), new BigDecimal("50000"),
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getValorFinanciado()).isEqualByComparingTo("200000");
        assertThat(response.getBody().getValorEntrada()).isEqualByComparingTo("50000");
        assertThat(response.getBody().getStatus()).isEqualTo("ATIVO");
        assertThat(response.getBody().getPrazoMeses()).isEqualTo(12);
    }

    @Test
    void testConsultarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.VEICULAR,
            new BigDecimal("80000"), BigDecimal.ZERO,
            24, SistemaAmortizacao.SAC, null, null);

        ResponseEntity<ContratoResponse> criada = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContratoResponse> response = rest.getForEntity(
            url("/contratos/" + id), ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getValorFinanciado()).isEqualByComparingTo("80000");
        assertThat(response.getBody().getSistemaAmortizacao()).isEqualTo("SAC");
        assertThat(response.getBody().getSaldoDevedor()).isNotNull();
    }

    @Test
    void testListarContratosPorCliente() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("150000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        rest.postForEntity(url("/contratos"), request, ContratoResponse.class);

        ResponseEntity<ContratoResumoResponse[]> response = rest.getForEntity(
            url("/contratos/cliente/1"), ContratoResumoResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isPositive();
        assertThat(response.getBody()[0].getId()).isPositive();
    }

    @Test
    void testListarParcelas() {
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
        assertThat(response.getBody()[0].getStatus()).isEqualTo("PENDENTE");
    }

    @Test
    void testPagarParcela() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> contrato = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long contratoId = contrato.getBody().getId();

        ResponseEntity<ParcelaResponse[]> parcelas = rest.getForEntity(
            url("/contratos/" + contratoId + "/parcelas"), ParcelaResponse[].class);
        Long parcelaId = parcelas.getBody()[0].getId();

        var pagamento = new PagarParcelaRequest(parcelaId, parcelas.getBody()[0].getValorParcela());

        ResponseEntity<Void> response = rest.postForEntity(
            url("/contratos/" + contratoId + "/parcelas/pagar"), pagamento, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testLiquidarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> criada = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<Void> response = rest.exchange(
            url("/contratos/" + id + "/liquidar"), HttpMethod.PATCH,
            HttpEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ContratoResponse> contrato = rest.getForEntity(
            url("/contratos/" + id), ContratoResponse.class);
        assertThat(contrato.getBody().getStatus()).isEqualTo("LIQUIDADO");
        assertThat(contrato.getBody().getSaldoDevedor()).isEqualByComparingTo("0");
    }

    @Test
    void testRenegociarContrato() {
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("100000"), BigDecimal.ZERO,
            12, SistemaAmortizacao.PRICE, null, null);

        ResponseEntity<ContratoResponse> criada = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long id = criada.getBody().getId();

        var renegociar = new RenegociarRequest(24, new BigDecimal("0.008"));

        ResponseEntity<ContratoResponse> response = rest.exchange(
            url("/contratos/" + id + "/renegociar"), HttpMethod.PATCH,
            new HttpEntity<>(renegociar), ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPrazoMeses()).isEqualTo(24);
    }

    @Test
    void testRegistrarGarantiaViaContrato() {
        var bem = new BemRequest("IMOVEL", "Casa residencial",
            new BigDecimal("300000"), null, null, "RGI-12345");
        var garantia = new GarantiaRequest("ALIENACAO_FIDUCIARIA",
            new BigDecimal("250000"), "CARTORIO");

        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), new BigDecimal("50000"),
            12, SistemaAmortizacao.PRICE, bem, garantia);

        ResponseEntity<ContratoResponse> response = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBens()).isNotEmpty();
        assertThat(response.getBody().getGarantias()).isNotEmpty();
        assertThat(response.getBody().getGarantias().get(0).getTipo()).isEqualTo("ALIENACAO_FIDUCIARIA");
        assertThat(response.getBody().getGarantias().get(0).getStatus()).isEqualTo("ATIVA");
    }

    @Test
    void testLiberarGarantia() {
        var bem = new BemRequest("IMOVEL", "Casa",
            new BigDecimal("300000"), null, null, "RGI-12345");
        var garantia = new GarantiaRequest("ALIENACAO_FIDUCIARIA",
            new BigDecimal("250000"), "CARTORIO");
        var request = new CriarContratoRequest(
            1L, 100L, TipoFinanciamento.IMOBILIARIO,
            new BigDecimal("200000"), new BigDecimal("50000"),
            12, SistemaAmortizacao.PRICE, bem, garantia);

        ResponseEntity<ContratoResponse> contrato = rest.postForEntity(
            url("/contratos"), request, ContratoResponse.class);
        Long garantiaId = contrato.getBody().getGarantias().get(0).getId();

        var liberar = new LiberarGarantiaRequest(java.time.LocalDate.now());

        ResponseEntity<Void> response = rest.exchange(
            url("/garantias/" + garantiaId + "/liberar"), HttpMethod.PATCH,
            new HttpEntity<>(liberar), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testListarTaxasAdmin() {
        ResponseEntity<TaxasResponse> response = rest.getForEntity(
            url("/admin/taxas"), TaxasResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTaxaSAC()).isEqualByComparingTo("0.0099");
        assertThat(response.getBody().getTaxaPrice()).isEqualByComparingTo("0.0112");
        assertThat(response.getBody().getTaxaSACRE()).isEqualByComparingTo("0.0105");
        assertThat(response.getBody().getCetTaxa()).isEqualByComparingTo("0.0025");
    }

    @Test
    void testAtualizarTaxasAdmin() {
        var request = new AtualizarTaxaRequest(SistemaAmortizacao.SAC, new BigDecimal("0.0085"));

        ResponseEntity<TaxasResponse> response = rest.exchange(
            url("/admin/taxas"), HttpMethod.PUT,
            new HttpEntity<>(request), TaxasResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
