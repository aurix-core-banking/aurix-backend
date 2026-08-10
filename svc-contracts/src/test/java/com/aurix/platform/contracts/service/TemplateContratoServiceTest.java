package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.dto.TemplateRequest;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.TemplateContrato;
import com.aurix.platform.contracts.exception.TemplateNaoEncontradoException;
import com.aurix.platform.contracts.repository.TemplateContratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateContratoServiceTest {

    @Mock
    private TemplateContratoRepository templateRepository;

    @Mock
    private ContratoService contratoService;

    private TemplateContratoService service;

    @BeforeEach
    void setUp() {
        service = new TemplateContratoService(templateRepository, contratoService);
    }

    private TemplateRequest request() {
        return new TemplateRequest("TEMPLO_EMPRESTIMO", "Contrato de Empréstimo",
            Contrato.TipoContrato.EMPRESTIMO,
            "Contrato {{numero}} no valor de R$ {{valor}} para {{tipoContrato}}");
    }

    @Test
    void criar_devePersistirTemplateAtivo() {
        when(templateRepository.existsByCodigo("TEMPLO_EMPRESTIMO")).thenReturn(false);
        when(templateRepository.save(any(TemplateContrato.class))).thenAnswer(inv -> {
            TemplateContrato t = inv.getArgument(0);
            t.setId(1L);
            return t;
        });

        TemplateContrato template = service.criar(request());

        assertThat(template.getId()).isEqualTo(1L);
        assertThat(template.getStatus()).isEqualTo(TemplateContrato.StatusTemplate.ATIVO);
        assertThat(template.getVersao()).isEqualTo(1);
    }

    @Test
    void criar_deveRejeitarCodigoDuplicado() {
        when(templateRepository.existsByCodigo("TEMPLO_EMPRESTIMO")).thenReturn(true);

        assertThatThrownBy(() -> service.criar(request()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Já existe template com o código");
        verify(templateRepository, never()).save(any(TemplateContrato.class));
    }

    @Test
    void atualizar_deveIncrementarVersao() {
        TemplateContrato template = new TemplateContrato();
        template.setId(1L);
        template.setCodigo("TEMPLO_EMPRESTIMO");
        template.setNome("Contrato de Empréstimo");
        template.setTipoContrato(Contrato.TipoContrato.EMPRESTIMO);
        template.setCorpoTexto("v1");
        template.setStatus(TemplateContrato.StatusTemplate.ATIVO);
        template.setVersao(1);
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));
        when(templateRepository.save(any(TemplateContrato.class))).thenAnswer(inv -> inv.getArgument(0));

        TemplateContrato atualizado = service.atualizar(1L, request());

        assertThat(atualizado.getVersao()).isEqualTo(2);
        assertThat(atualizado.getCorpoTexto()).contains("{{valor}}");
    }

    @Test
    void inativar_deveAlterarStatus() {
        TemplateContrato template = new TemplateContrato();
        template.setId(1L);
        template.setStatus(TemplateContrato.StatusTemplate.ATIVO);
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));
        when(templateRepository.save(any(TemplateContrato.class))).thenAnswer(inv -> inv.getArgument(0));

        TemplateContrato inativo = service.inativar(1L);

        assertThat(inativo.getStatus()).isEqualTo(TemplateContrato.StatusTemplate.INATIVO);
    }

    @Test
    void gerarDocumento_deveSubstituirVariaveis() {
        TemplateContrato template = new TemplateContrato();
        template.setId(1L);
        template.setCorpoTexto("Contrato {{numero}} do tipo {{tipoContrato}} no valor de R$ {{valor}} "
            + "em {{prazoMeses}} meses");
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));

        Contrato contrato = new Contrato();
        contrato.setId(5L);
        contrato.setNumeroContrato("CTR-2026-0001");
        contrato.setClienteId(100L);
        contrato.setClienteDocumento("12345678901");
        contrato.setTipoContrato(Contrato.TipoContrato.EMPRESTIMO);
        contrato.setValor(new BigDecimal("10000"));
        contrato.setPrazoMeses(36);
        contrato.setValorParcela(new BigDecimal("380.50"));
        contrato.setTaxaJuros(new BigDecimal("1.99"));
        when(contratoService.buscarEntidade(5L)).thenReturn(contrato);

        String documento = service.gerarDocumento(1L, 5L);

        assertThat(documento)
            .contains("CTR-2026-0001")
            .contains("EMPRESTIMO")
            .contains("R$ 10000")
            .contains("36 meses")
            .doesNotContain("{{");
    }

    @Test
    void listarPorTipo_deveFiltrar() {
        TemplateContrato template = new TemplateContrato();
        template.setId(1L);
        when(templateRepository.findByTipoContrato(Contrato.TipoContrato.EMPRESTIMO))
            .thenReturn(List.of(template));

        List<TemplateContrato> templates = service.listar(Contrato.TipoContrato.EMPRESTIMO, null);

        assertThat(templates).hasSize(1);
        assertThat(templates.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void buscarPorCodigoInexistente_deveLancarExcecao() {
        when(templateRepository.findByCodigo("NAO_EXISTE")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorCodigo("NAO_EXISTE"))
            .isInstanceOf(TemplateNaoEncontradoException.class);
    }
}
