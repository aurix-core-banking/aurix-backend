package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.dto.ContratoRequest;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.ContratoVersao;
import com.aurix.platform.contracts.exception.ContratoNaoEncontradoException;
import com.aurix.platform.contracts.repository.ContratoRepository;
import com.aurix.platform.contracts.repository.ContratoVersaoRepository;
import com.aurix.platform.shared.event.ContratoCriadoEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.Topics;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private ContratoVersaoRepository versaoRepository;

    @Mock
    private IntegracaoContratoService integracaoService;

    @Mock
    private EventPublisher eventPublisher;

    private ContratoService service;

    @BeforeEach
    void setUp() {
        service = new ContratoService(contratoRepository, versaoRepository, integracaoService,
            eventPublisher, new ObjectMapper().findAndRegisterModules());
    }

    private ContratoRequest request() {
        return new ContratoRequest("CTR-2026-0001", null, null, 100L,
            "12345678901", Contrato.TipoContrato.EMPRESTIMO,
            new BigDecimal("10000"), 36, new BigDecimal("380.50"),
            new BigDecimal("1.99"), null, null,
            "Termos e condições do contrato", "{\"origem\":\"teste\"}");
    }

    private Contrato contratoBase() {
        Contrato contrato = new Contrato();
        contrato.setId(1L);
        contrato.setNumeroContrato("CTR-2026-0001");
        contrato.setClienteId(100L);
        contrato.setTipoContrato(Contrato.TipoContrato.EMPRESTIMO);
        contrato.setValor(new BigDecimal("10000"));
        contrato.setPrazoMeses(36);
        contrato.setStatus(Contrato.StatusContrato.RASCUNHO);
        return contrato;
    }

    @Test
    void criar_devePersistirContratoERegistrarVersao() {
        when(contratoRepository.existsByNumeroContrato("CTR-2026-0001")).thenReturn(false);
        when(contratoRepository.save(any(Contrato.class))).thenAnswer(inv -> {
            Contrato c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        Contrato contrato = service.criar(request());

        assertThat(contrato.getId()).isEqualTo(1L);
        assertThat(contrato.getStatus()).isEqualTo(Contrato.StatusContrato.RASCUNHO);
        assertThat(contrato.getClienteDocumento()).isEqualTo("12345678901");
        verify(versaoRepository).save(any(ContratoVersao.class));
        verify(eventPublisher).publish(eq(Topics.CONTRATO_CRIADO), any(ContratoCriadoEvent.class));
    }

    @Test
    void criar_deveRejeitarNumeroDuplicado() {
        when(contratoRepository.existsByNumeroContrato("CTR-2026-0001")).thenReturn(true);

        assertThatThrownBy(() -> service.criar(request()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Já existe contrato com o número");
        verify(contratoRepository, never()).save(any(Contrato.class));
    }

    @Test
    void criar_deveEnriquecerClienteQuandoDocumentoAusente() {
        ContratoRequest semDocumento = new ContratoRequest("CTR-2026-0002", null, null, 200L,
            null, Contrato.TipoContrato.EMPRESTIMO, null, null, null, null, null, null,
            null, null);
        when(contratoRepository.existsByNumeroContrato("CTR-2026-0002")).thenReturn(false);
        when(integracaoService.buscarCliente(200L))
            .thenReturn(Optional.of(new com.aurix.platform.contracts.client.ClienteClient.ClientePerfil(
                200L, "Maria Silva", "98765432100", "PF", "OURO")));
        when(contratoRepository.save(any(Contrato.class))).thenAnswer(inv -> {
            Contrato c = inv.getArgument(0);
            c.setId(2L);
            return c;
        });

        Contrato contrato = service.criar(semDocumento);

        assertThat(contrato.getClienteDocumento()).isEqualTo("98765432100");
    }

    @Test
    void atualizarContratoAtivo_deveBloquearEdicao() {
        Contrato contrato = contratoBase();
        contrato.setStatus(Contrato.StatusContrato.ATIVO);
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        assertThatThrownBy(() -> service.atualizar(1L, request(), "Tentativa"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("não pode ser editado");
    }

    @Test
    void atualizarContrato_deveAlterarNumeroNaoSerPermitido() {
        Contrato contrato = contratoBase();
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        ContratoRequest numeroDiferente = new ContratoRequest("CTR-9999", null, null, 100L,
            null, Contrato.TipoContrato.EMPRESTIMO, null, null, null, null, null, null,
            null, null);

        assertThatThrownBy(() -> service.atualizar(1L, numeroDiferente, "Troca"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não pode ser alterado");
    }

    @Test
    void atualizarContratoRascunho_deveCriarNovaVersao() {
        Contrato contrato = contratoBase();
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        when(versaoRepository.findByContratoIdOrderByNumeroVersaoDesc(1L))
            .thenReturn(List.of());
        when(contratoRepository.save(any(Contrato.class))).thenAnswer(inv -> inv.getArgument(0));

        Contrato atualizado = service.atualizar(1L, request(), "Ajuste de taxa");

        assertThat(atualizado.getValor()).isEqualTo(new BigDecimal("10000"));
        verify(versaoRepository).save(any(ContratoVersao.class));
    }

    @Test
    void liquidar_deveMarcarContratoComoLiquidado() {
        Contrato contrato = contratoBase();
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        when(contratoRepository.save(any(Contrato.class))).thenAnswer(inv -> inv.getArgument(0));

        Contrato liquidado = service.liquidar(1L);

        assertThat(liquidado.getStatus()).isEqualTo(Contrato.StatusContrato.LIQUIDADO);
        verify(eventPublisher).publish(eq(Topics.CONTRATO_LIQUIDADO), any(ContratoCriadoEvent.class));
    }

    @Test
    void liquidarContratoCancelado_deveBloquear() {
        Contrato contrato = contratoBase();
        contrato.setStatus(Contrato.StatusContrato.CANCELADO);
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        assertThatThrownBy(() -> service.liquidar(1L))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("não pode ser liquidado");
    }

    @Test
    void cancelarContratoLiquidado_deveBloquear() {
        Contrato contrato = contratoBase();
        contrato.setStatus(Contrato.StatusContrato.LIQUIDADO);
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        assertThatThrownBy(() -> service.cancelar(1L))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("não pode ser cancelado");
    }

    @Test
    void cancelar_deveMarcarContratoComoCancelado() {
        Contrato contrato = contratoBase();
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        when(contratoRepository.save(any(Contrato.class))).thenAnswer(inv -> inv.getArgument(0));

        Contrato cancelado = service.cancelar(1L);

        assertThat(cancelado.getStatus()).isEqualTo(Contrato.StatusContrato.CANCELADO);
        verify(eventPublisher).publish(eq(Topics.CONTRATO_CANCELADO), any(ContratoCriadoEvent.class));
    }

    @Test
    void buscarPorNumeroInexistente_deveLancarExcecao() {
        when(contratoRepository.findByNumeroContrato("CTR-X")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorNumero("CTR-X"))
            .isInstanceOf(ContratoNaoEncontradoException.class);
    }
}
