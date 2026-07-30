package com.aurix.platform.banking.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.dto.EmpresaDTO;
import com.aurix.platform.banking.dto.FuncionarioDTO;
import com.aurix.platform.banking.dto.WorkflowDTO;
import com.aurix.platform.banking.dto.SolicitacaoAprovacaoDTO;
import com.aurix.platform.banking.entity.Workflow;
import com.aurix.platform.banking.entity.SolicitacaoAprovacao;
import com.aurix.platform.banking.integration.analytics.BIAnalyticsService;
import com.aurix.platform.banking.integration.governo.ESocialIntegrationService;
import com.aurix.platform.banking.integration.governo.ReceitaFederalService;
import com.aurix.platform.banking.integration.rh.RHIntegrationService;
import com.aurix.platform.banking.integration.social.LinkedInIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankingApplication.class)
@ActiveProfiles("test")
@Import(OrganizationFlowIntegrationTest.MockConfig.class)
class OrganizationFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    private String baseUrl;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        RHIntegrationService rhIntegrationService() {
            var mock = Mockito.mock(RHIntegrationService.class);
            when(mock.obterEstruturaOrganizacionalRH()).thenReturn(List.of(Map.of("departamento", "TI")));
            return mock;
        }

        @Bean
        @Primary
        ESocialIntegrationService eSocialIntegrationService() {
            return Mockito.mock(ESocialIntegrationService.class);
        }

        @Bean
        @Primary
        ReceitaFederalService receitaFederalService() {
            var mock = Mockito.mock(ReceitaFederalService.class);
            when(mock.validarCNPJ(anyString())).thenReturn(true);
            when(mock.obterDadosEmpresa(anyString())).thenReturn(Map.of("nome", "Teste"));
            return mock;
        }

        @Bean
        @Primary
        LinkedInIntegrationService linkedInIntegrationService() {
            return Mockito.mock(LinkedInIntegrationService.class);
        }

        @Bean
        @Primary
        BIAnalyticsService biAnalyticsService() {
            return Mockito.mock(BIAnalyticsService.class);
        }

        @Bean
        @Primary
        SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
            return http.build();
        }
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/banking";
    }

    @Test
    void shouldCreateAndRetrieveEmpresa() {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setCodigoEmpresa("EMP-001");
        dto.setNomeEmpresa("Aurix Corp");
        dto.setCnpj("11222333000181");

        ResponseEntity<EmpresaDTO> create = rest.postForEntity(baseUrl + "/empresas", dto, EmpresaDTO.class);
        assertEquals(HttpStatus.OK, create.getStatusCode());
        assertNotNull(create.getBody().getId());

        ResponseEntity<EmpresaDTO> get = rest.getForEntity(baseUrl + "/empresas/" + create.getBody().getId(), EmpresaDTO.class);
        assertEquals(HttpStatus.OK, get.getStatusCode());
        assertEquals("EMP-001", get.getBody().getCodigoEmpresa());
    }

    @Test
    void shouldListAllEmpresas() {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setCodigoEmpresa("EMP-LIST");
        dto.setNomeEmpresa("List Corp");
        dto.setCnpj("99888777000155");
        rest.postForEntity(baseUrl + "/empresas", dto, EmpresaDTO.class);

        ResponseEntity<List> list = rest.getForEntity(baseUrl + "/empresas", List.class);
        assertEquals(HttpStatus.OK, list.getStatusCode());
        assertFalse(list.getBody().isEmpty());
    }

    @Test
    void shouldFindEmpresaByCodigo() {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setCodigoEmpresa("EMP-COD");
        dto.setNomeEmpresa("Cod Corp");
        dto.setCnpj("77666555000144");
        rest.postForEntity(baseUrl + "/empresas", dto, EmpresaDTO.class);

        ResponseEntity<EmpresaDTO> get = rest.getForEntity(baseUrl + "/empresas/codigo/EMP-COD", EmpresaDTO.class);
        assertEquals(HttpStatus.OK, get.getStatusCode());
        assertEquals("Cod Corp", get.getBody().getNomeEmpresa());
    }

    @Test
    void shouldCreateAndRetrieveFuncionario() {
        EmpresaDTO emp = new EmpresaDTO();
        emp.setCodigoEmpresa("EMP-FUNC");
        emp.setNomeEmpresa("Func Corp");
        emp.setCnpj("55444333000122");
        ResponseEntity<EmpresaDTO> empResp = rest.postForEntity(baseUrl + "/empresas", emp, EmpresaDTO.class);
        Long empresaId = empResp.getBody().getId();

        FuncionarioDTO func = new FuncionarioDTO();
        func.setMatricula("F001");
        func.setNomeCompleto("João Silva");
        func.setCpf("12345678901");
        func.setEmail("joao@aurix.com");
        func.setEmpresaId(empresaId);

        ResponseEntity<FuncionarioDTO> create = rest.postForEntity(baseUrl + "/funcionarios", func, FuncionarioDTO.class);
        assertEquals(HttpStatus.OK, create.getStatusCode());
        assertNotNull(create.getBody().getId());

        ResponseEntity<FuncionarioDTO> get = rest.getForEntity(baseUrl + "/funcionarios/" + create.getBody().getId(), FuncionarioDTO.class);
        assertEquals(HttpStatus.OK, get.getStatusCode());
        assertEquals("F001", get.getBody().getMatricula());
    }

    @Test
    void shouldListFuncionarios() {
        ResponseEntity<List> list = rest.getForEntity(baseUrl + "/funcionarios", List.class);
        assertEquals(HttpStatus.OK, list.getStatusCode());
    }

    @Test
    void shouldCreateAndRetrieveSolicitacaoAprovacao() {
        EmpresaDTO emp = new EmpresaDTO();
        emp.setCodigoEmpresa("EMP-ALC");
        emp.setNomeEmpresa("Alcada Corp");
        emp.setCnpj("33222111000133");
        ResponseEntity<EmpresaDTO> empResp = rest.postForEntity(baseUrl + "/empresas", emp, EmpresaDTO.class);
        Long empresaId = empResp.getBody().getId();

        WorkflowDTO wf = new WorkflowDTO();
        wf.setCodigoWorkflow("WF-001");
        wf.setNomeWorkflow("Aprovação Padrão");
        wf.setEmpresaId(empresaId);
        wf.setTipoWorkflow(Workflow.TipoWorkflow.APROVACAO_GERAL);
        ResponseEntity<WorkflowDTO> wfResp = rest.postForEntity(baseUrl + "/workflows", wf, WorkflowDTO.class);
        Long wfId = wfResp.getBody().getId();

        FuncionarioDTO func = new FuncionarioDTO();
        func.setMatricula("F-ALC");
        func.setNomeCompleto("Maria Aprovadora");
        func.setCpf("98765432100");
        func.setEmail("maria@aurix.com");
        func.setEmpresaId(empresaId);
        ResponseEntity<FuncionarioDTO> funcResp = rest.postForEntity(baseUrl + "/funcionarios", func, FuncionarioDTO.class);
        Long funcId = funcResp.getBody().getId();

        SolicitacaoAprovacaoDTO sol = new SolicitacaoAprovacaoDTO();
        sol.setCodigoSolicitacao("SOL-001");
        sol.setWorkflowId(wfId);
        sol.setSolicitanteId(funcId);
        sol.setValorSolicitado(BigDecimal.valueOf(50000));
        sol.setTipoSolicitacao(SolicitacaoAprovacao.TipoSolicitacao.CREDITO);

        ResponseEntity<SolicitacaoAprovacaoDTO> create = rest.postForEntity(baseUrl + "/alcadas/solicitar", sol, SolicitacaoAprovacaoDTO.class);
        assertEquals(HttpStatus.OK, create.getStatusCode());
        assertNotNull(create.getBody().getId());

        ResponseEntity<SolicitacaoAprovacaoDTO> get = rest.getForEntity(baseUrl + "/alcadas/solicitacoes/" + create.getBody().getId(), SolicitacaoAprovacaoDTO.class);
        assertEquals(HttpStatus.OK, get.getStatusCode());
    }

    @Test
    void shouldListSolicitacoes() {
        ResponseEntity<List> list = rest.getForEntity(baseUrl + "/alcadas/solicitacoes", List.class);
        assertEquals(HttpStatus.OK, list.getStatusCode());
    }

    @Test
    void shouldCreateAndRetrieveWorkflow() {
        EmpresaDTO emp = new EmpresaDTO();
        emp.setCodigoEmpresa("EMP-WF");
        emp.setNomeEmpresa("WF Corp");
        emp.setCnpj("11122233300144");
        ResponseEntity<EmpresaDTO> empResp = rest.postForEntity(baseUrl + "/empresas", emp, EmpresaDTO.class);
        Long empresaId = empResp.getBody().getId();

        WorkflowDTO wf = new WorkflowDTO();
        wf.setCodigoWorkflow("WF-CREATE");
        wf.setNomeWorkflow("Workflow Teste");
        wf.setEmpresaId(empresaId);
        wf.setTipoWorkflow(Workflow.TipoWorkflow.APROVACAO_GERAL);

        ResponseEntity<WorkflowDTO> create = rest.postForEntity(baseUrl + "/workflows", wf, WorkflowDTO.class);
        assertEquals(HttpStatus.OK, create.getStatusCode());
        assertNotNull(create.getBody().getId());
    }

    @Test
    void shouldListWorkflows() {
        ResponseEntity<List> list = rest.getForEntity(baseUrl + "/workflows", List.class);
        assertEquals(HttpStatus.OK, list.getStatusCode());
    }

    @Test
    void shouldReturnIntegrationStatus() {
        ResponseEntity<Map> status = rest.getForEntity(baseUrl + "/integration/rh/estrutura-organizacional", Map.class);
        assertEquals(HttpStatus.OK, status.getStatusCode());
    }

    @Test
    void shouldReturnCNPJValidation() {
        ResponseEntity<Map> result = rest.getForEntity(baseUrl + "/integration/receita/validar-cnpj/11222333000181", Map.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
