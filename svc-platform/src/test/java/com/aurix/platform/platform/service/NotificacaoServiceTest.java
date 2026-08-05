package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.FilaNotificacao;
import com.aurix.platform.platform.entity.PreferenciaCliente;
import com.aurix.platform.platform.entity.TemplateNotificacao;
import com.aurix.platform.platform.repository.ConfirmacaoRecebimentoRepository;
import com.aurix.platform.platform.repository.FilaNotificacaoRepository;
import com.aurix.platform.platform.repository.PreferenciaClienteRepository;
import com.aurix.platform.platform.repository.TemplateNotificacaoRepository;
import com.aurix.platform.platform.service.channel.LogChannel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {
    @Mock private TemplateNotificacaoRepository templateRepository;
    @Mock private FilaNotificacaoRepository filaRepository;
    @Mock private ConfirmacaoRecebimentoRepository confirmacaoRepository;
    @Mock private PreferenciaClienteRepository preferenciaRepository;
    @Mock private NotificacaoProducer producer;
    @Mock private LogChannel logChannel;
    @InjectMocks private NotificacaoService service;

    @Test
    void deveRenderizarTemplateComVariaveis() {
        String template = "Ola {{nome}}, bem-vindo ao {{plataforma}}!";
        Map<String, String> vars = Map.of("nome", "Joao", "plataforma", "Aurix");
        String resultado = service.renderizar(template, vars);
        assertEquals("Ola Joao, bem-vindo ao Aurix!", resultado);
    }

    @Test
    void deveRenderizarTemplateSemVariaveis() {
        String template = "Mensagem estatica";
        String resultado = service.renderizar(template, null);
        assertEquals("Mensagem estatica", resultado);
    }

    @Test
    void deveTratarVariavelNulaNaRenderizacao() {
        String template = "Ola {{nome}}!";
        java.util.HashMap<String, String> vars = new java.util.HashMap<>();
        vars.put("nome", null);
        String resultado = service.renderizar(template, vars);
        assertEquals("Ola !", resultado);
    }

    @Test
    void deveEnviarNotificacaoComSucesso() {
        TemplateNotificacao template = new TemplateNotificacao();
        template.setCodigo("cliente.criado");
        template.setCanal("EMAIL");
        template.setCorpo("Bem-vindo, {{nome}}!");
        template.setAssunto("Bem-vindo!");
        template.setAtivo(true);

        when(templateRepository.findByCodigoAndAtivoTrue("cliente.criado")).thenReturn(Optional.of(template));
        when(preferenciaRepository.findByClienteId(1L)).thenReturn(Optional.empty());
        when(filaRepository.save(any())).thenAnswer(inv -> {
            FilaNotificacao saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        FilaNotificacao resultado = service.enviar(1L, "cliente.criado", "joao@test.com", Map.of("nome", "Joao"));

        assertNotNull(resultado);
        assertEquals("ENVIADA", resultado.getStatus());
        assertEquals("Bem-vindo, Joao!", resultado.getCorpoRenderizado());
        verify(logChannel).send(any());
        verify(producer).notificacaoEnviada(any());
    }

    @Test
    void deveLancarExcecaoQuandoTemplateNaoEncontrado() {
        when(templateRepository.findByCodigoAndAtivoTrue("inexistente")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> service.enviar(1L, "inexistente", "joao@test.com", Map.of()));
    }

    @Test
    void devePularNotificacaoQuandoClienteDesativado() {
        TemplateNotificacao template = new TemplateNotificacao();
        template.setCodigo("cliente.criado");
        template.setCanal("EMAIL");
        template.setAtivo(true);

        PreferenciaCliente pref = new PreferenciaCliente();
        pref.setClienteId(1L);
        pref.setAtivo(false);
        pref.setEmailAtivo(true);

        when(templateRepository.findByCodigoAndAtivoTrue("cliente.criado")).thenReturn(Optional.of(template));
        when(preferenciaRepository.findByClienteId(1L)).thenReturn(Optional.of(pref));

        FilaNotificacao resultado = service.enviar(1L, "cliente.criado", "joao@test.com", Map.of());

        assertNull(resultado);
        verify(filaRepository, never()).save(any());
    }

    @Test
    void devePularNotificacaoQuandoCanalDesativado() {
        TemplateNotificacao template = new TemplateNotificacao();
        template.setCodigo("cliente.criado");
        template.setCanal("SMS");
        template.setAtivo(true);

        PreferenciaCliente pref = new PreferenciaCliente();
        pref.setClienteId(1L);
        pref.setAtivo(true);
        pref.setEmailAtivo(true);
        pref.setSmsAtivo(false);

        when(templateRepository.findByCodigoAndAtivoTrue("cliente.criado")).thenReturn(Optional.of(template));
        when(preferenciaRepository.findByClienteId(1L)).thenReturn(Optional.of(pref));

        FilaNotificacao resultado = service.enviar(1L, "cliente.criado", "11999999999", Map.of());

        assertNull(resultado);
        verify(filaRepository, never()).save(any());
    }

    @Test
    void deveAtualizarPreferenciaExistente() {
        PreferenciaCliente existente = new PreferenciaCliente();
        existente.setClienteId(1L);
        existente.setAtivo(true);
        existente.setEmailAtivo(true);
        existente.setSmsAtivo(true);
        existente.setPushAtivo(true);

        PreferenciaCliente nova = new PreferenciaCliente();
        nova.setClienteId(1L);
        nova.setAtivo(true);
        nova.setEmailAtivo(false);
        nova.setSmsAtivo(true);
        nova.setPushAtivo(false);

        when(preferenciaRepository.findByClienteId(1L)).thenReturn(Optional.of(existente));
        when(preferenciaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PreferenciaCliente resultado = service.salvarPreferencia(nova);

        assertFalse(resultado.getEmailAtivo());
        assertFalse(resultado.getPushAtivo());
        assertTrue(resultado.getSmsAtivo());
    }
}
