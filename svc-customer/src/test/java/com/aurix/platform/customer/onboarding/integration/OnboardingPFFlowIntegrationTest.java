package com.aurix.platform.customer.onboarding.integration;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaResponse;
import com.aurix.platform.customer.onboarding.service.FraudService;
import com.aurix.platform.customer.onboarding.service.KycProviderService;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(
    classes = AurixCustomerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.security.enabled=false")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class OnboardingPFFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private CoreApiClient coreApiClient;

    @MockitoBean
    private KycProviderService kycProviderService;

    @MockitoBean
    private FraudService fraudService;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        when(coreApiClient.criarClientePFeConta(
            any(), any(), any(), any(),
            any(), any(), any(), anyBoolean()
        )).thenReturn(new CoreApiClient.CriarClienteContaResult(1L, 1L, true));

        when(kycProviderService.validarDocumentos(anyString(), any(), anyString()))
            .thenReturn(new KycProviderService.ResultadoKyc(true, "APROVADO", "Teste mock KYC"));

        when(fraudService.analisar(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(new FraudService.ResultadoFraude(true, "APROVADO", "Mock", 0));

        limparBanco();

        rest = new RestTemplate();
        rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatusCode statusCode) {
                return false;
            }
        });
    }

    private void limparBanco() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        for (String table : List.of(
            "aurix.historico_aprovacao_onboarding",
            "aurix.documentos_onboarding",
            "aurix.solicitacoes_pf",
            "aurix.solicitacoes_onboarding",
            "aurix.pep")) {
            jdbcTemplate.execute("DELETE FROM " + table);
        }
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String urlPF(String path) {
        return "http://localhost:" + port + "/contas/pf" + path;
    }

    private SolicitacaoContaRequest criarRequestPadrao() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Maria Silva");
        request.setEmail("maria@teste.com");
        request.setTelefone("11999999999");
        request.setDataNascimento(LocalDate.of(1990, 5, 15));
        request.setOcupacao("Analista");
        request.setRendaDeclarada(BigDecimal.valueOf(5000));
        return request;
    }

    private Long criarSolicitacao() {
        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes"), criarRequestPadrao(), SolicitacaoContaResponse.class);
        return response.getBody().getId();
    }

    @Test
    void criarSolicitacaoPF_deveRetornarStatusRECEBIDA() {
        SolicitacaoContaRequest request = criarRequestPadrao();

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("RECEBIDA");
        assertThat(response.getBody().getCpf()).isEqualTo("52998224725");
        assertThat(response.getBody().getNome()).isEqualTo("Maria Silva");
    }

    @Test
    void consultarSolicitacaoPF_deveRetornar200() {
        Long id = criarSolicitacao();

        ResponseEntity<SolicitacaoContaResponse> response = rest.getForEntity(
            urlPF("/solicitacoes/" + id), SolicitacaoContaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getStatus()).isEqualTo("RECEBIDA");
    }

    @Test
    void adicionarDocumentoPF_deveRetornar204() {
        Long id = criarSolicitacao();

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "RG",
            "nomeArquivo", "rg.pdf",
            "urlStorage", "http://storage/rg.pdf"
        );

        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/documentos"), docBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void enviarKyc_deveProcessarEAprovar() {
        Long id = criarSolicitacao();

        Map<String, Object> kycBody = Map.of(
            "documentos", List.of(),
            "selfieBase64", ""
        );

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/kyc"), kycBody, SolicitacaoContaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("KYC_APROVADO");
        assertThat(response.getBody().getResultadoKyc()).isEqualTo("APROVADO");
    }

    @Test
    void aprovarPF_deveCriarClienteEConta() {
        Long id = criarSolicitacao();

        Map<String, Object> kycBody = Map.of(
            "documentos", List.of(),
            "selfieBase64", ""
        );
        rest.postForEntity(urlPF("/solicitacoes/" + id + "/kyc"), kycBody, SolicitacaoContaResponse.class);

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/aprovar?usuarioAnalista=admin&observacao=Aprovado"),
            null, SolicitacaoContaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTA_CRIADA");
        assertThat(response.getBody().getClienteIdCriado()).isNotNull();
        assertThat(response.getBody().getContaIdCriada()).isNotNull();
    }

    @Test
    void rejeitarPF_deveRetornarREJEITADA() {
        Long id = criarSolicitacao();

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/rejeitar?usuarioAnalista=admin&observacao=Documentacao+insuficiente"),
            null, SolicitacaoContaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("REJEITADA");
    }

    @Test
    void listarSolicitacoesPF_deveRetornarLista() {
        SolicitacaoContaRequest req1 = criarRequestPadrao();
        rest.postForEntity(urlPF("/solicitacoes"), req1, SolicitacaoContaResponse.class);

        SolicitacaoContaRequest req2 = criarRequestPadrao();
        req2.setCpf("12345678901");
        rest.postForEntity(urlPF("/solicitacoes"), req2, SolicitacaoContaResponse.class);

        ResponseEntity<SolicitacaoContaResponse[]> response = rest.getForEntity(
            urlPF("/solicitacoes"), SolicitacaoContaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }
}
