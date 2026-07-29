package com.aurix.platform.banking.salario.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.salario.client.ContaCorrenteClient;
import com.aurix.platform.banking.salario.dto.ContaSalarioRequest;
import com.aurix.platform.banking.salario.dto.ContaSalarioResponse;
import com.aurix.platform.banking.salario.dto.ConvenioRequest;
import com.aurix.platform.banking.salario.dto.ConvenioResponse;
import com.aurix.platform.banking.salario.dto.PortabilidadeRequest;
import com.aurix.platform.banking.salario.dto.PortabilidadeResponse;
import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import com.aurix.platform.banking.salario.entity.SolicitacaoPortabilidade;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.banking.salario.repository.SolicitacaoPortabilidadeRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(SalarioFlowIntegrationTest.TestConfig.class)
class SalarioFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContaSalarioRepository contaSalarioRepository;

    @Autowired
    private ConvenioEmpresaRepository convenioRepository;

    @Autowired
    private SolicitacaoPortabilidadeRepository portabilidadeRepository;

    private RestTemplate rest;
    private Long empresaId;

    @BeforeEach
    void setUp() {
        contaSalarioRepository.deleteAll();
        convenioRepository.deleteAll();
        portabilidadeRepository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        ConvenioEmpresa empresa = new ConvenioEmpresa();
        empresa.setTenantId("test-tenant");
        empresa.setCnpj("11222333000181");
        empresa.setRazaoSocial("Empresa Teste Ltda");
        empresa.setContaCorrenteId(1L);
        empresa.setAtivo(true);
        empresa = convenioRepository.save(empresa);
        empresaId = empresa.getId();
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveCriarContaSalario() {
        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresaId);
        request.setMatriculaFuncionario("MAT001");
        request.setCpfFuncionario("12345678901");
        request.setDataAdmissao(LocalDate.of(2024, 1, 15));
        request.setValorSalarioBruto(new BigDecimal("5000.00"));
        request.setValorSalarioLiquido(new BigDecimal("4200.00"));
        request.setDiaPagamento(5);

        ResponseEntity<ContaSalarioResponse> response = rest.postForEntity(
            url("/api/salario/contas"), request, ContaSalarioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMatriculaFuncionario()).isEqualTo("MAT001");
    }

    @Test
    void deveBuscarContaSalarioPorId() {
        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresaId);
        request.setMatriculaFuncionario("MAT002");
        request.setCpfFuncionario("98765432100");
        request.setDataAdmissao(LocalDate.of(2024, 3, 10));
        request.setValorSalarioBruto(new BigDecimal("3500.00"));
        request.setValorSalarioLiquido(new BigDecimal("3000.00"));
        request.setDiaPagamento(1);
        ResponseEntity<ContaSalarioResponse> criada = rest.postForEntity(
            url("/api/salario/contas"), request, ContaSalarioResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContaSalarioResponse> response = rest.getForEntity(
            url("/api/salario/contas/" + id), ContaSalarioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void deveListarContasPorEmpresa() {
        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresaId);
        request.setMatriculaFuncionario("MAT003");
        request.setCpfFuncionario("11122233344");
        request.setDataAdmissao(LocalDate.of(2024, 6, 1));
        request.setValorSalarioBruto(new BigDecimal("2800.00"));
        request.setValorSalarioLiquido(new BigDecimal("2400.00"));
        request.setDiaPagamento(10);
        rest.postForEntity(url("/api/salario/contas"), request, ContaSalarioResponse.class);

        ResponseEntity<ContaSalarioResponse[]> response = rest.getForEntity(
            url("/api/salario/contas/empresa/" + empresaId), ContaSalarioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveBloquearContaSalario() {
        ContaSalarioRequest request = new ContaSalarioRequest();
        request.setContaCorrenteId(1L);
        request.setEmpresaId(empresaId);
        request.setMatriculaFuncionario("MAT004");
        request.setCpfFuncionario("55566677788");
        request.setDataAdmissao(LocalDate.of(2024, 2, 20));
        request.setValorSalarioBruto(new BigDecimal("4200.00"));
        request.setValorSalarioLiquido(new BigDecimal("3600.00"));
        request.setDiaPagamento(15);
        ResponseEntity<ContaSalarioResponse> criada = rest.postForEntity(
            url("/api/salario/contas"), request, ContaSalarioResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<Void> response = rest.exchange(
            url("/api/salario/contas/" + id + "/bloquear"), HttpMethod.PATCH,
            HttpEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveSolicitarPortabilidade() {
        ContaSalarioRequest csReq = new ContaSalarioRequest();
        csReq.setContaCorrenteId(1L);
        csReq.setEmpresaId(empresaId);
        csReq.setMatriculaFuncionario("MAT005");
        csReq.setCpfFuncionario("99988877766");
        csReq.setDataAdmissao(LocalDate.of(2024, 4, 5));
        csReq.setValorSalarioBruto(new BigDecimal("6000.00"));
        csReq.setValorSalarioLiquido(new BigDecimal("5100.00"));
        csReq.setDiaPagamento(20);
        ResponseEntity<ContaSalarioResponse> conta = rest.postForEntity(
            url("/api/salario/contas"), csReq, ContaSalarioResponse.class);
        Long contaId = conta.getBody().getId();

        PortabilidadeRequest request = new PortabilidadeRequest();
        request.setContaSalarioId(contaId);
        request.setCodigoBancoDestino("341");
        request.setAgenciaDestino("0001");
        request.setContaDestino("12345-6");
        request.setValorPercentual(new BigDecimal("50.00"));

        ResponseEntity<PortabilidadeResponse> response = rest.postForEntity(
            url("/api/salario/portabilidade"), request, PortabilidadeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContaSalarioId()).isEqualTo(contaId);
    }

    @Test
    void deveCancelarPortabilidade() {
        ContaSalarioRequest csReq = new ContaSalarioRequest();
        csReq.setContaCorrenteId(1L);
        csReq.setEmpresaId(empresaId);
        csReq.setMatriculaFuncionario("MAT006");
        csReq.setCpfFuncionario("44455566677");
        csReq.setDataAdmissao(LocalDate.of(2024, 7, 12));
        csReq.setValorSalarioBruto(new BigDecimal("3200.00"));
        csReq.setValorSalarioLiquido(new BigDecimal("2700.00"));
        csReq.setDiaPagamento(25);
        ResponseEntity<ContaSalarioResponse> conta = rest.postForEntity(
            url("/api/salario/contas"), csReq, ContaSalarioResponse.class);
        Long contaId = conta.getBody().getId();

        PortabilidadeRequest portReq = new PortabilidadeRequest();
        portReq.setContaSalarioId(contaId);
        portReq.setCodigoBancoDestino("237");
        portReq.setAgenciaDestino("1234");
        portReq.setContaDestino("98765-4");
        ResponseEntity<PortabilidadeResponse> criada = rest.postForEntity(
            url("/api/salario/portabilidade"), portReq, PortabilidadeResponse.class);
        Long portId = criada.getBody().getId();

        portabilidadeRepository.findById(portId).ifPresent(s -> {
            s.setTenantId("test-tenant");
            portabilidadeRepository.save(s);
        });

        rest.delete(url("/api/salario/portabilidade/" + portId));

        ResponseEntity<PortabilidadeResponse> response = rest.getForEntity(
            url("/api/salario/portabilidade/" + portId), PortabilidadeResponse.class);
        assertThat(response.getBody().getStatus()).isEqualTo(SolicitacaoPortabilidade.StatusPortabilidade.CANCELADA);
    }

    @Test
    void deveCadastrarConvenio() {
        ConvenioRequest request = new ConvenioRequest();
        request.setCnpj("99888777000155");
        request.setRazaoSocial("Empresa Convenio Ltda");
        request.setContaCorrenteId(1L);

        ResponseEntity<ConvenioResponse> response = rest.postForEntity(
            url("/api/salario/convenios"), request, ConvenioResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCnpj()).isEqualTo("99888777000155");
    }

    @Test
    void deveListarConveniosAtivos() {
        ConvenioRequest request = new ConvenioRequest();
        request.setCnpj("55666777000199");
        request.setRazaoSocial("Outra Convenio SA");
        request.setContaCorrenteId(1L);
        rest.postForEntity(url("/api/salario/convenios"), request, ConvenioResponse.class);

        ResponseEntity<ConvenioResponse[]> response = rest.getForEntity(
            url("/api/salario/convenios"), ConvenioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @TestConfiguration
    @EnableWebSecurity
    static class TestConfig {
        @Bean
        public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http.build();
        }

        @Bean
        @SuppressWarnings("unchecked")
        public KafkaTemplate<String, String> kafkaTemplate() {
            return Mockito.mock(KafkaTemplate.class);
        }

        @Bean
        @Primary
        public JwtDecoder jwtDecoder() {
            return Mockito.mock(JwtDecoder.class);
        }

        @Bean
        @Primary
        public ContaCorrenteClient contaCorrenteClient() {
            ContaCorrenteClient mock = Mockito.mock(ContaCorrenteClient.class);
            when(mock.getConta(anyLong()))
                .thenReturn(new ContaCorrenteClient.ContaCorrenteResponse(1L, "ATIVA"));
            return mock;
        }

        @Bean
        public Filter tenantFilter() {
            return (request, response, chain) -> {
                TenantContext.setTenantId("test-tenant");
                try {
                    chain.doFilter(request, response);
                } finally {
                    TenantContext.clear();
                }
            };
        }
    }
}
