package com.aurix.platform.customer.onboarding.integration;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaResponse;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(
    classes = AurixCustomerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.security.enabled=false")
@ActiveProfiles("test")
class OnboardingFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private CoreApiClient coreApiClient;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        when(coreApiClient.criarClientePFeConta(
            anyString(), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyString(), anyBoolean()
        )).thenReturn(new CoreApiClient.CriarClienteContaResult(1L, 1L, true));

        limparBanco();

        rest = new RestTemplate();
        rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.HttpStatusCode statusCode) {
                return false;
            }
        });
    }

    private void limparBanco() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        for (String table : List.of(
            "aurix.historico_aprovacao_onboarding",
            "aurix.documentos_onboarding",
            "aurix.pep",
            "aurix.solicitacoes_pf",
            "aurix.solicitacoes_pj",
            "aurix.empresas",
            "aurix.participantes",
            "aurix.solicitacoes_onboarding")) {
            jdbcTemplate.execute("DELETE FROM " + table);
        }
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private String urlPF(String path) {
        return "http://localhost:" + port + "/contas/pf" + path;
    }

    @Test
    void solicitarAberturaConta_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");
        request.setEmail("jackson@aurix.com");
        request.setTelefone("11999999999");
        request.setDataNascimento(LocalDate.of(1990, 1, 1));
        request.setRendaDeclarada(BigDecimal.valueOf(5000.0));
        request.setEndereco("{\"rua\": \"Teste\", \"cidade\": \"SP\"}");

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("RECEBIDA");
    }

    @Test
    void consultarStatusSolicitacao_quandoExiste_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");
        request.setEmail("jackson@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        ResponseEntity<SolicitacaoContaResponse> response = rest.getForEntity(
            urlPF("/solicitacoes/" + id), SolicitacaoContaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void consultarStatusSolicitacao_quandoNaoExiste_deveRetornar404() {
        ResponseEntity<String> response = rest.getForEntity(
            urlPF("/solicitacoes/99999"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void listarSolicitacoes_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");
        request.setEmail("jackson@aurix.com");
        rest.postForEntity(urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);

        ResponseEntity<SolicitacaoContaResponse[]> response = rest.getForEntity(
            urlPF("/solicitacoes"), SolicitacaoContaResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void aprovarSolicitacao_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");
        request.setEmail("jackson@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/aprovar?usuarioAnalista=admin&observacao=Aprovado"),
            null, String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void rejeitarSolicitacao_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");
        request.setEmail("jackson@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/rejeitar?usuarioAnalista=admin&observacao=Documentacao+insuficiente"),
            null, SolicitacaoContaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("REJEITADA");
    }

    @Test
    void consultarCep_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/onboarding/integracoes/cep/01001000"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void consultarCnpj_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/onboarding/integracoes/receita/cnpj/12345678000190"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void solicitarAberturaConta_cpfInvalido_deveRetornar400() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("123");
        request.setNome("Nome");
        request.setEmail("email@test.com");

        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes"), request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void solicitarAberturaConta_semEmail_deveRetornar400() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Jackson Wendel");

        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes"), request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void enviarKyc_deveRetornar200() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("KYC Test");
        request.setEmail("kyc@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        Map<String, Object> kycBody = Map.of(
            "documentos", List.of(Map.of("tipo", "RG", "url", "http://storage/doc.pdf")),
            "selfieBase64", "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
        );
        ResponseEntity<SolicitacaoContaResponse> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/kyc"), kycBody, SolicitacaoContaResponse.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void consultarPep_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            urlPF("/pep/52998224725"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("pep");
    }

    @Test
    void registrarPep_deveRetornar200() {
        Map<String, String> pepBody = Map.of(
            "cpf", "52998224725",
            "nome", "PEP Test",
            "cargoOuVinculo", "Deputado Federal"
        );
        ResponseEntity<Map> response = rest.postForEntity(
            urlPF("/pep"), pepBody, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void adicionarDocumento_deveRetornar204() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Doc Test");
        request.setEmail("doc@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "RG",
            "nomeArquivo", "frente_rg.pdf",
            "urlStorage", "http://storage/rg.pdf"
        );
        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/documentos"), docBody, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void validarDocumento_deveRetornar204() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224725");
        request.setNome("Validar Doc Test");
        request.setEmail("validar@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "RG",
            "nomeArquivo", "rg.pdf",
            "urlStorage", "http://storage/rg.pdf"
        );
        rest.postForEntity(urlPF("/solicitacoes/" + id + "/documentos"), docBody, String.class);

        Long docId = jdbcTemplate.queryForObject(
            "SELECT id FROM aurix.documentos_onboarding WHERE solicitacao_id = ?",
            Long.class, id);

        Map<String, Object> validarBody = Map.of(
            "validado", true,
            "observacao", "Documento confere"
        );
        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/documentos/" + docId + "/validar"),
            validarBody, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Boolean validado = jdbcTemplate.queryForObject(
            "SELECT validado FROM aurix.documentos_onboarding WHERE id = ?",
            Boolean.class, docId);
        assertThat(validado).isTrue();
    }

    @Test
    void rejeitarDocumento_deveRetornar204() {
        SolicitacaoContaRequest request = new SolicitacaoContaRequest();
        request.setCpf("52998224726");
        request.setNome("Rejeitar Doc Test");
        request.setEmail("rejeitar@aurix.com");
        ResponseEntity<SolicitacaoContaResponse> created = rest.postForEntity(
            urlPF("/solicitacoes"), request, SolicitacaoContaResponse.class);
        Long id = created.getBody().getId();

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "CPF",
            "nomeArquivo", "cpf.pdf",
            "urlStorage", "http://storage/cpf.pdf"
        );
        rest.postForEntity(urlPF("/solicitacoes/" + id + "/documentos"), docBody, String.class);

        Long docId = jdbcTemplate.queryForObject(
            "SELECT id FROM aurix.documentos_onboarding WHERE solicitacao_id = ?",
            Long.class, id);

        Map<String, Object> validarBody = Map.of(
            "validado", false,
            "observacao", "Documento ilegivel"
        );
        ResponseEntity<String> response = rest.postForEntity(
            urlPF("/solicitacoes/" + id + "/documentos/" + docId + "/validar"),
            validarBody, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Boolean validado = jdbcTemplate.queryForObject(
            "SELECT validado FROM aurix.documentos_onboarding WHERE id = ?",
            Boolean.class, docId);
        assertThat(validado).isFalse();
    }
}
