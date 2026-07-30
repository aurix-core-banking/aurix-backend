package com.aurix.platform.customer.onboarding.integration;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.ParticipanteRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJResponse;
import com.aurix.platform.customer.onboarding.entity.TipoParticipante;
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
class OnboardingPJFlowIntegrationTest {

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

        when(coreApiClient.criarClientePJeConta(
            anyString(), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyBoolean(),
            any(), any(), any(), any(), any()
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
            "aurix.participantes",
            "aurix.empresas",
            "aurix.solicitacoes_pj",
            "aurix.solicitacoes_pf",
            "aurix.solicitacoes_onboarding",
            "aurix.pep")) {
            jdbcTemplate.execute("DELETE FROM " + table);
        }
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String urlPJ(String path) {
        return "http://localhost:" + port + "/contas/pj" + path;
    }

    private SolicitacaoPJRequest criarRequestPadrao() {
        SolicitacaoPJRequest request = new SolicitacaoPJRequest();
        request.setCnpj("12345678000190");
        request.setRazaoSocial("Empresa Exemplo Ltda");
        request.setEmail("contato@empresa.com");
        request.setTelefone("11999999999");
        request.setEndereco("{\"rua\": \"Teste\", \"cidade\": \"SP\"}");
        return request;
    }

    private Long criarSolicitacao() {
        ResponseEntity<SolicitacaoPJResponse> response = rest.postForEntity(
            urlPJ(""), criarRequestPadrao(), SolicitacaoPJResponse.class);
        return response.getBody().getId();
    }

    @Test
    void iniciarOnboardingPJ_deveRetornar201() {
        SolicitacaoPJRequest request = criarRequestPadrao();

        ResponseEntity<SolicitacaoPJResponse> response = rest.postForEntity(
            urlPJ(""), request, SolicitacaoPJResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("EM_PREENCHIMENTO");
    }

    @Test
    void consultarStatusPJ_deveRetornar200() {
        Long id = criarSolicitacao();

        ResponseEntity<SolicitacaoPJResponse> response = rest.getForEntity(
            urlPJ("/" + id), SolicitacaoPJResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void validarCNPJ_deveTransicionarParaCNPJ_CONSULTADO() {
        Long id = criarSolicitacao();

        ResponseEntity<SolicitacaoPJResponse> response = rest.postForEntity(
            urlPJ("/" + id + "/validar-cnpj"), null, SolicitacaoPJResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("CNPJ_CONSULTADO");
    }

    @Test
    void adicionarSocio_deveRetornar204() {
        Long id = criarSolicitacao();

        ParticipanteRequest socio = new ParticipanteRequest();
        socio.setTipo(TipoParticipante.SOCIO);
        socio.setCpf("52998224725");
        socio.setNome("João Silva");
        socio.setEmail("joao@empresa.com");
        socio.setTelefone("11988888888");
        socio.setDataNascimento(LocalDate.of(1985, 5, 15));
        socio.setPercentualParticipacao(BigDecimal.valueOf(50.0));

        ResponseEntity<String> response = rest.postForEntity(
            urlPJ("/" + id + "/socios"), socio, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void adicionarDocumento_deveRetornar204() {
        Long id = criarSolicitacao();

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "CONTRATO_SOCIAL",
            "nomeArquivo", "contrato_social.pdf",
            "urlStorage", "http://storage/contrato.pdf"
        );

        ResponseEntity<String> response = rest.postForEntity(
            urlPJ("/" + id + "/documentos"), docBody, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void aprovarPJ_deveCriarClienteEConta() {
        Long id = criarSolicitacao();
        rest.postForEntity(urlPJ("/" + id + "/validar-cnpj"), null, SolicitacaoPJResponse.class);

        ParticipanteRequest socio = new ParticipanteRequest();
        socio.setTipo(TipoParticipante.SOCIO);
        socio.setCpf("52998224725");
        socio.setNome("João Silva");
        rest.postForEntity(urlPJ("/" + id + "/socios"), socio, String.class);

        Map<String, String> docBody = Map.of(
            "tipoDocumento", "CONTRATO_SOCIAL",
            "nomeArquivo", "contrato_social.pdf",
            "urlStorage", "http://storage/contrato.pdf"
        );
        rest.postForEntity(urlPJ("/" + id + "/documentos"), docBody, String.class);

        ResponseEntity<SolicitacaoPJResponse> amlResponse = rest.postForEntity(
            urlPJ("/" + id + "/aml-aprovar"), null, SolicitacaoPJResponse.class);
        assertThat(amlResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(amlResponse.getBody().getStatus()).isEqualTo("AML_APROVADO");

        ResponseEntity<SolicitacaoPJResponse> complianceResponse = rest.postForEntity(
            urlPJ("/" + id + "/compliance-aprovar"), null, SolicitacaoPJResponse.class);
        assertThat(complianceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(complianceResponse.getBody().getStatus()).isEqualTo("COMPLIANCE_APROVADO");

        ResponseEntity<SolicitacaoPJResponse> assinaturaResponse = rest.postForEntity(
            urlPJ("/" + id + "/assinatura-solicitar"),
            Map.of("tipoAssinatura", "ELETRONICA"),
            SolicitacaoPJResponse.class);
        assertThat(assinaturaResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(assinaturaResponse.getBody().getStatus()).isEqualTo("EM_ASSINATURA");

        ResponseEntity<SolicitacaoPJResponse> confirmacaoResponse = rest.postForEntity(
            urlPJ("/" + id + "/assinatura-confirmar"), null, SolicitacaoPJResponse.class);
        assertThat(confirmacaoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(confirmacaoResponse.getBody().getStatus()).isEqualTo("CONTRATO_ASSINADO");

        ResponseEntity<SolicitacaoPJResponse> response = rest.postForEntity(
            urlPJ("/" + id + "/aprovar?usuarioAnalista=admin&observacao=Aprovado"),
            null, SolicitacaoPJResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("CONTA_CRIADA");
        assertThat(response.getBody().getClienteIdCriado()).isNotNull();
    }

    @Test
    void rejeitarPJ_deveRetornar200() {
        Long id = criarSolicitacao();

        ResponseEntity<SolicitacaoPJResponse> response = rest.postForEntity(
            urlPJ("/" + id + "/rejeitar?usuarioAnalista=admin&observacao=Documentacao+insuficiente"),
            null, SolicitacaoPJResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo("REJEITADA");
    }
}
