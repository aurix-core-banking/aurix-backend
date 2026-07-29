package com.aurix.platform.banking.salario.controller;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.salario.config.TestSalarioConfig;
import com.aurix.platform.banking.salario.dto.ContaSalarioRequest;
import com.aurix.platform.banking.salario.dto.ContaSalarioResponse;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSalarioConfig.class)
class ContaSalarioControllerTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;

    @Autowired
    private ConvenioEmpresaRepository convenioEmpresaRepository;

    @Autowired
    private ContaSalarioRepository contaSalarioRepository;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        TenantContext.setTenantId("test-tenant");
        convenioEmpresaRepository.deleteAll();
        contaSalarioRepository.deleteAll();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private ConvenioEmpresa criarEmpresa() {
        ConvenioEmpresa empresa = new ConvenioEmpresa("12345678901234", "Empresa Teste", 1L);
        empresa.setTenantId("test-tenant");
        return convenioEmpresaRepository.save(empresa);
    }

    private HttpEntity<ContaSalarioRequest> criarRequestEntity(ContaSalarioRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(TenantContext.HEADER_TENANT_ID, "test-tenant");
        return new HttpEntity<>(request, headers);
    }

    @Test
    void deveCriarContaSalarioComSucesso() {
        ConvenioEmpresa empresa = criarEmpresa();

        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresa.getId());
        request.setMatriculaFuncionario("FUNC001");
        request.setCpfFuncionario("12345678901");
        request.setDataAdmissao(LocalDate.of(2026, 1, 15));
        request.setValorSalarioBruto(new BigDecimal("5000.00"));
        request.setValorSalarioLiquido(new BigDecimal("4250.00"));
        request.setDiaPagamento(5);

        ResponseEntity<ContaSalarioResponse> response = restTemplate.exchange(
            url("/api/salario/contas"), HttpMethod.POST, criarRequestEntity(request), ContaSalarioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMatriculaFuncionario()).isEqualTo("FUNC001");
        assertThat(response.getBody().getCpfFuncionario()).isEqualTo("12345678901");
        assertThat(response.getBody().getStatus()).isEqualTo(ContaSalario.StatusContaSalario.ATIVA);
    }

    @Test
    void deveBuscarContaPorId() {
        ConvenioEmpresa empresa = criarEmpresa();

        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresa.getId());
        request.setMatriculaFuncionario("FUNC002");
        request.setCpfFuncionario("12345678902");
        request.setDataAdmissao(LocalDate.of(2026, 3, 1));
        request.setValorSalarioBruto(new BigDecimal("8000.00"));
        request.setValorSalarioLiquido(new BigDecimal("6800.00"));
        request.setDiaPagamento(30);

        ResponseEntity<ContaSalarioResponse> created = restTemplate.exchange(
            url("/api/salario/contas"), HttpMethod.POST, criarRequestEntity(request), ContaSalarioResponse.class);
        Long id = created.getBody().getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set(TenantContext.HEADER_TENANT_ID, "test-tenant");
        HttpEntity<Void> getEntity = new HttpEntity<>(headers);

        ResponseEntity<ContaSalarioResponse> response = restTemplate.exchange(
            url("/api/salario/contas/" + id), HttpMethod.GET, getEntity, ContaSalarioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getMatriculaFuncionario()).isEqualTo("FUNC002");
    }

    @Test
    void deveListarContasPorEmpresa() {
        ConvenioEmpresa empresa = criarEmpresa();

        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresa.getId());
        request.setMatriculaFuncionario("FUNC003");
        request.setCpfFuncionario("12345678903");
        request.setDataAdmissao(LocalDate.of(2026, 1, 15));
        request.setValorSalarioBruto(new BigDecimal("5000.00"));
        request.setValorSalarioLiquido(new BigDecimal("4250.00"));
        request.setDiaPagamento(5);

        restTemplate.exchange(url("/api/salario/contas"), HttpMethod.POST, criarRequestEntity(request), ContaSalarioResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set(TenantContext.HEADER_TENANT_ID, "test-tenant");
        HttpEntity<Void> getEntity = new HttpEntity<>(headers);

        ResponseEntity<ContaSalarioResponse[]> response = restTemplate.exchange(
            url("/api/salario/contas/empresa/" + empresa.getId()), HttpMethod.GET, getEntity, ContaSalarioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void deveBloquearConta() {
        ConvenioEmpresa empresa = criarEmpresa();

        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresa.getId());
        request.setMatriculaFuncionario("FUNC004");
        request.setCpfFuncionario("12345678904");
        request.setDataAdmissao(LocalDate.of(2026, 1, 15));
        request.setValorSalarioBruto(new BigDecimal("5000.00"));
        request.setValorSalarioLiquido(new BigDecimal("4250.00"));
        request.setDiaPagamento(5);

        ResponseEntity<ContaSalarioResponse> created = restTemplate.exchange(
            url("/api/salario/contas"), HttpMethod.POST, criarRequestEntity(request), ContaSalarioResponse.class);
        Long id = created.getBody().getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set(TenantContext.HEADER_TENANT_ID, "test-tenant");
        HttpEntity<Void> patchEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> bloqueioResponse = restTemplate.exchange(
            url("/api/salario/contas/" + id + "/bloquear"), HttpMethod.PATCH, patchEntity, Void.class);

        assertThat(bloqueioResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId("test-tenant", id).orElseThrow();
        assertThat(conta.getStatus()).isEqualTo(ContaSalario.StatusContaSalario.BLOQUEADA);
    }

    @Test
    void deveRescindirConta() {
        ConvenioEmpresa empresa = criarEmpresa();

        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresa.getId());
        request.setMatriculaFuncionario("FUNC005");
        request.setCpfFuncionario("12345678905");
        request.setDataAdmissao(LocalDate.of(2026, 1, 15));
        request.setValorSalarioBruto(new BigDecimal("5000.00"));
        request.setValorSalarioLiquido(new BigDecimal("4250.00"));
        request.setDiaPagamento(5);

        ResponseEntity<ContaSalarioResponse> created = restTemplate.exchange(
            url("/api/salario/contas"), HttpMethod.POST, criarRequestEntity(request), ContaSalarioResponse.class);
        Long id = created.getBody().getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set(TenantContext.HEADER_TENANT_ID, "test-tenant");
        HttpEntity<Void> patchEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> rescisaoResponse = restTemplate.exchange(
            url("/api/salario/contas/" + id + "/rescindir"), HttpMethod.PATCH, patchEntity, Void.class);

        assertThat(rescisaoResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ContaSalario conta = contaSalarioRepository.findByTenantIdAndId("test-tenant", id).orElseThrow();
        assertThat(conta.getStatus()).isEqualTo(ContaSalario.StatusContaSalario.RESCINDIDA);
        assertThat(conta.getDataRescisao()).isNotNull();
    }
}
