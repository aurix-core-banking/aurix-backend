package com.aurix.platform.customer.onboarding.service;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.ParticipanteRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJResponse;
import com.aurix.platform.customer.onboarding.entity.*;
import com.aurix.platform.customer.onboarding.repository.*;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AurixCustomerApplication.class, properties = "spring.security.enabled=false")
@ActiveProfiles("test")
class OnboardingPJServiceTest {

    @MockitoBean
    private SolicitacaoOnboardingRepository solicitacaoOnboardingRepository;
    @MockitoBean
    private SolicitacaoPJRepository solicitacaoPJRepository;
    @MockitoBean
    private EmpresaRepository empresaRepository;
    @MockitoBean
    private ParticipanteRepository participanteRepository;
    @MockitoBean
    private DocumentoOnboardingRepository documentoRepository;
    @MockitoBean
    private HistoricoAprovacaoRepository historicoRepository;
    @MockitoBean
    private ReceitaFederalService receitaFederalService;
    @MockitoBean
    private CoreApiClient coreApiClient;
    @MockitoBean
    private WorkflowPJ workflowPJ;

    @Autowired
    private OnboardingPJService service;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);
    }

    @Test
    void iniciarOnboarding_deveCriarSolicitacao() {
        SolicitacaoPJRequest request = new SolicitacaoPJRequest();
        request.setCnpj("12345678000190");
        request.setRazaoSocial("Empresa LTDA");
        request.setNomeFantasia("Empresa");
        request.setEmail("contato@empresa.com");
        request.setTelefone("11999999999");
        request.setEndereco("{\"rua\": \"Teste\"}");

        when(solicitacaoPJRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> {
            SolicitacaoOnboarding s = i.getArgument(0);
            s.setId(1L);
            return s;
        });
        when(solicitacaoPJRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.iniciarOnboarding(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getCnpj()).isEqualTo("12345678000190");
        assertThat(response.getRazaoSocial()).isEqualTo("Empresa LTDA");
        assertThat(response.getStatus()).isEqualTo("EM_PREENCHIMENTO");
        verify(historicoRepository).save(any());
    }

    @Test
    void iniciarOnboarding_quandoCnpjDuplicado_deveLancarExcecao() {
        SolicitacaoPJRequest request = new SolicitacaoPJRequest();
        request.setCnpj("12345678000190");
        request.setRazaoSocial("Empresa LTDA");
        request.setEmail("contato@empresa.com");

        SolicitacaoPJ existingPj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").razaoSocial("Outra LTDA").build();
        when(solicitacaoPJRepository.findByCnpj(anyString())).thenReturn(Optional.of(existingPj));
        SolicitacaoOnboarding existingOnboarding = SolicitacaoOnboarding.builder()
            .id(1L).tipoPessoa(TipoPessoa.JURIDICA).status(StatusOnboarding.EM_PREENCHIMENTO).build();
        when(solicitacaoOnboardingRepository.findById(1L)).thenReturn(Optional.of(existingOnboarding));

        assertThatThrownBy(() -> service.iniciarOnboarding(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Ja existe");
    }

    @Test
    void consultarCNPJ_deveValidarETransicionar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.EM_PREENCHIMENTO).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").razaoSocial("Empresa LTDA").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(receitaFederalService.consultarCnpj(anyString()))
            .thenReturn(ReceitaFederalService.ResultadoReceita.ok("12345678000190", "Empresa LTDA", "ATIVA"));
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.consultarCNPJ(1L);

        assertThat(response.getStatus()).isEqualTo("CNPJ_CONSULTADO");
        verify(empresaRepository).save(any());
    }

    @Test
    void consultarCNPJ_quandoCnpjInativo_deveRejeitar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.EM_PREENCHIMENTO).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").razaoSocial("Empresa LTDA").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(receitaFederalService.consultarCnpj(anyString()))
            .thenReturn(ReceitaFederalService.ResultadoReceita.ok("12345678000190", "Empresa LTDA", "BAIXADA"));
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.consultarCNPJ(1L);

        assertThat(response.getStatus()).isEqualTo("REJEITADA");
    }

    @Test
    void consultarCNPJ_quandoTransicaoInvalida_deveLancarExcecao() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.CNPJ_CONSULTADO).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> service.consultarCNPJ(1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Transicao invalida");
    }

    @Test
    void adicionarParticipante_deveSalvarETransicionar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.CNPJ_CONSULTADO).build();
        ParticipanteRequest request = new ParticipanteRequest();
        request.setTipo(TipoParticipante.SOCIO);
        request.setCpf("52998224725");
        request.setNome("Joao Silva");

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.adicionarParticipante(1L, request);

        verify(participanteRepository).save(any());
        assertThat(onboarding.getStatus()).isEqualTo(StatusOnboarding.SOCIOS_VALIDADOS);
    }

    @Test
    void adicionarDocumento_deveSalvarETransicionar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.SOCIOS_VALIDADOS).build();
        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.adicionarDocumento(1L, "CONTRATO_SOCIAL", "contrato.pdf", "http://storage/contrato.pdf");

        verify(documentoRepository).save(any());
        assertThat(onboarding.getStatus()).isEqualTo(StatusOnboarding.DOCUMENTOS_ANALISADOS);
    }

    @Test
    void aprovar_deveCriarClienteETransicionar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.CONTRATO_ASSINADO).email("contato@empresa.com").telefone("11999999999")
            .endereco("{}").build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").razaoSocial("Empresa LTDA").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(empresaRepository.findBySolicitacaoId(any())).thenReturn(Optional.empty());
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(coreApiClient.criarClientePJeConta(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), any(), any(), any(), any(), any()))
            .thenReturn(new CoreApiClient.CriarClienteContaResult(100L, 200L, true));
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.aprovar(1L, "admin", "Aprovado");

        assertThat(response.getStatus()).isEqualTo("CONTA_CRIADA");
        assertThat(onboarding.getClienteIdCriado()).isEqualTo(100L);
        assertThat(onboarding.getContaIdCriada()).isEqualTo(200L);
    }

    @Test
    void aprovar_quandoContaJaCriada_deveRetornar() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.CONTA_CRIADA).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));

        SolicitacaoPJResponse response = service.aprovar(1L, "admin", "Ja aprovado");

        assertThat(response.getStatus()).isEqualTo("CONTA_CRIADA");
    }

    @Test
    void rejeitar_deveTransicionarParaRejeitada() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.EM_PREENCHIMENTO).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.rejeitar(1L, "admin", "Documentacao insuficiente");

        assertThat(response.getStatus()).isEqualTo("REJEITADA");
        verify(historicoRepository).save(any());
    }

    @Test
    void avancarStatus_deveTransicionarValido() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.DOCUMENTOS_ANALISADOS).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").razaoSocial("Empresa LTDA").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(true);
        when(solicitacaoOnboardingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SolicitacaoPJResponse response = service.avancarStatus(1L, "AML_APROVADO", "analista1", "Aprovado AML");

        assertThat(response.getStatus()).isEqualTo("AML_APROVADO");
        assertThat(onboarding.getStatus()).isEqualTo(StatusOnboarding.AML_APROVADO);
        verify(historicoRepository).save(any());
    }

    @Test
    void avancarStatus_quandoTransicaoInvalida_deveLancarExcecao() {
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .id(1L).tenantId(TenantContext.DEFAULT_TENANT_ID).tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.EM_PREENCHIMENTO).build();
        SolicitacaoPJ pj = SolicitacaoPJ.builder().solicitacaoId(1L).cnpj("12345678000190").build();

        when(solicitacaoOnboardingRepository.findByTenantIdAndId(anyString(), any()))
            .thenReturn(Optional.of(onboarding));
        when(solicitacaoPJRepository.findBySolicitacaoId(any())).thenReturn(Optional.of(pj));
        when(workflowPJ.transicaoValida(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> service.avancarStatus(1L, "CONTRATO_ASSINADO", "admin", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Transicao invalida");
    }
}
