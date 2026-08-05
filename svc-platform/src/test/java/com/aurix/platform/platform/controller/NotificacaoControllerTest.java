package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.entity.FilaNotificacao;
import com.aurix.platform.platform.entity.PreferenciaCliente;
import com.aurix.platform.platform.entity.TemplateNotificacao;
import com.aurix.platform.platform.service.NotificacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoControllerTest {
    @Mock private NotificacaoService service;
    @InjectMocks private NotificacaoController controller;

    @Test
    void deveEnviarNotificacaoComSucesso() {
        FilaNotificacao notificacao = new FilaNotificacao();
        notificacao.setId(1L);
        notificacao.setStatus("ENVIADA");

        when(service.enviar(eq(1L), eq("cliente.criado"), eq("joao@test.com"), any()))
                .thenReturn(notificacao);

        var response = controller.enviar(1L, "cliente.criado", "joao@test.com", Map.of("nome", "Joao"));

        assertEquals(201, response.getStatusCode().value());
        assertEquals("ENVIADA", response.getBody().getStatus());
    }

    @Test
    void deveRetornarNullQuandoNotificacaoPulada() {
        when(service.enviar(eq(1L), eq("cliente.criado"), eq("joao@test.com"), any()))
                .thenReturn(null);

        var response = controller.enviar(1L, "cliente.criado", "joao@test.com", null);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void deveListarNotificacoesPorCliente() {
        FilaNotificacao n1 = new FilaNotificacao();
        n1.setId(1L);
        when(service.listarNotificacoesPorCliente(1L)).thenReturn(List.of(n1));

        var response = controller.listarPorCliente(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deveCriarTemplate() {
        TemplateNotificacao template = new TemplateNotificacao();
        template.setCodigo("teste");
        when(service.criarTemplate(any())).thenReturn(template);

        var response = controller.criarTemplate(template);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("teste", response.getBody().getCodigo());
    }

    @Test
    void deveListarTemplates() {
        when(service.listarTemplates()).thenReturn(List.of());
        var response = controller.listarTemplates();
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void deveSalvarPreferencia() {
        PreferenciaCliente pref = new PreferenciaCliente();
        pref.setClienteId(1L);
        when(service.salvarPreferencia(any())).thenReturn(pref);

        var response = controller.salvarPreferencia(pref);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getClienteId());
    }

    @Test
    void deveBuscarPreferenciaExistente() {
        PreferenciaCliente pref = new PreferenciaCliente();
        when(service.buscarPreferencia(1L)).thenReturn(Optional.of(pref));
        var response = controller.buscarPreferencia(1L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deveRetornar404QuandoPreferenciaNaoEncontrada() {
        when(service.buscarPreferencia(99L)).thenReturn(Optional.empty());
        var response = controller.buscarPreferencia(99L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void deveRenderizarTemplate() {
        when(service.renderizar(eq("Ola {{nome}}!"), any())).thenReturn("Ola Joao!");
        var response = controller.renderizar("Ola {{nome}}!", Map.of("nome", "Joao"));
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Ola Joao!", response.getBody().get("renderizado"));
    }
}
