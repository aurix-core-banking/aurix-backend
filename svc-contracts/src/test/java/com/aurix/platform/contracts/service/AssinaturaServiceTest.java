package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.dto.AssinanteRequest;
import com.aurix.platform.contracts.dto.AssinaturaRequest;
import com.aurix.platform.contracts.dto.AssinaturaResponse;
import com.aurix.platform.contracts.entity.AssinaturaContrato;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.repository.AssinaturaContratoRepository;
import com.aurix.platform.contracts.repository.ContratoRepository;
import com.aurix.platform.shared.event.ContratoAssinadoEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.Topics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssinaturaServiceTest {

    @Mock
    private AssinaturaContratoRepository assinaturaRepository;

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private ContratoService contratoService;

    @Mock
    private EventPublisher eventPublisher;

    private AssinaturaService service;

    private final List<AssinaturaContrato> banco = new ArrayList<>();

    @BeforeEach
    void setUp() {
        service = new AssinaturaService(assinaturaRepository, contratoRepository,
            contratoService, eventPublisher);
        banco.clear();
        lenient().when(assinaturaRepository.save(any(AssinaturaContrato.class))).thenAnswer(inv -> {
            AssinaturaContrato a = inv.getArgument(0);
            banco.removeIf(b -> b.getAssinanteDocumento().equals(a.getAssinanteDocumento()));
            banco.add(a);
            return a;
        });
        lenient().when(assinaturaRepository.findByContratoId(anyLong())).thenAnswer(inv -> List.copyOf(banco));
    }

    private Contrato contrato(Contrato.StatusContrato status) {
        Contrato contrato = new Contrato();
        contrato.setId(1L);
        contrato.setNumeroContrato("CTR-2026-0001");
        contrato.setClienteId(100L);
        contrato.setTipoContrato(Contrato.TipoContrato.EMPRESTIMO);
        contrato.setStatus(status);
        return contrato;
    }

    @Test
    void iniciarFluxo_deveCriarAssinaturasEAlterarStatus() {
        when(contratoService.buscarEntidade(1L)).thenReturn(contrato(Contrato.StatusContrato.RASCUNHO));

        List<AssinaturaContrato> assinaturas = service.iniciarFluxo(1L, List.of(
            new AssinanteRequest(AssinaturaContrato.AssinanteTipo.CLIENTE, "12345678901", "Maria Silva"),
            new AssinanteRequest(AssinaturaContrato.AssinanteTipo.GARANTE, "98765432100", "João Souza")));

        assertThat(assinaturas).hasSize(2);
        assertThat(assinaturas).allMatch(a -> !a.getAssinada() && !a.getValida());
        verify(assinaturaRepository).deleteByContratoId(1L);
        verify(contratoRepository).save(any(Contrato.class));
    }

    @Test
    void iniciarFluxoEmContratoCancelado_deveBloquear() {
        when(contratoService.buscarEntidade(1L)).thenReturn(contrato(Contrato.StatusContrato.CANCELADO));

        assertThatThrownBy(() -> service.iniciarFluxo(1L, List.of(
            new AssinanteRequest(AssinaturaContrato.AssinanteTipo.CLIENTE, "12345678901", "Maria Silva"))))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("não aceita assinatura");
    }

    @Test
    void registrarAssinatura_deveConcluirFluxoQuandoTodosAssinam() {
        Contrato contrato = contrato(Contrato.StatusContrato.AGUARDANDO_ASSINATURA);
        when(contratoService.buscarEntidade(1L)).thenReturn(contrato);
        AssinaturaContrato assinatura = new AssinaturaContrato();
        assinatura.setContratoId(1L);
        assinatura.setAssinanteTipo(AssinaturaContrato.AssinanteTipo.CLIENTE);
        assinatura.setAssinanteDocumento("12345678901");
        assinatura.setAssinanteNome("Maria Silva");
        assinatura.setAssinada(false);
        assinatura.setValida(false);
        banco.add(assinatura);
        when(assinaturaRepository.findByContratoIdAndAssinanteDocumento(1L, "12345678901"))
            .thenReturn(Optional.of(assinatura));

        AssinaturaContrato registrada = service.registrarAssinatura(1L, "12345678901",
            new AssinaturaRequest("hash-123", "127.0.0.1", "chrome"));

        assertThat(registrada.getAssinada()).isTrue();
        assertThat(registrada.getValida()).isTrue();
        assertThat(contrato.getStatus()).isEqualTo(Contrato.StatusContrato.ATIVO);
        assertThat(contrato.getDataAssinatura()).isNotNull();
        verify(contratoRepository).save(contrato);
        verify(eventPublisher).publish(eq(Topics.CONTRATO_ASSINADO), any(ContratoAssinadoEvent.class));
    }

    @Test
    void registrarAssinaturaComAssinanteNaoCadastrado_deveFalhar() {
        when(contratoService.buscarEntidade(1L))
            .thenReturn(contrato(Contrato.StatusContrato.AGUARDANDO_ASSINATURA));
        when(assinaturaRepository.findByContratoIdAndAssinanteDocumento(1L, "00000000000"))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.registrarAssinatura(1L, "00000000000",
            new AssinaturaRequest("hash-1", "127.0.0.1", "chrome")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não cadastrado");
    }

    @Test
    void registrarAssinaturaEmContratoAtivo_deveBloquear() {
        when(contratoService.buscarEntidade(1L)).thenReturn(contrato(Contrato.StatusContrato.ATIVO));

        assertThatThrownBy(() -> service.registrarAssinatura(1L, "12345678901",
            new AssinaturaRequest("hash-1", "127.0.0.1", "chrome")))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("não permite assinatura");
        verify(assinaturaRepository, never()).findByContratoIdAndAssinanteDocumento(anyLong(), anyString());
    }

    @Test
    void listarAssinaturas_deveRetornarDto() {
        AssinaturaContrato assinatura = new AssinaturaContrato();
        assinatura.setContratoId(1L);
        assinatura.setAssinanteTipo(AssinaturaContrato.AssinanteTipo.CLIENTE);
        assinatura.setAssinanteDocumento("12345678901");
        assinatura.setAssinanteNome("Maria Silva");
        assinatura.setAssinada(true);
        assinatura.setValida(true);
        banco.add(assinatura);

        List<AssinaturaResponse> respostas = service.listarAssinaturas(1L);

        assertThat(respostas).hasSize(1);
        assertThat(respostas.get(0).assinanteDocumento()).isEqualTo("12345678901");
        assertThat(respostas.get(0).assinada()).isTrue();
    }
}
