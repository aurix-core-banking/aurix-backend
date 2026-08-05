package com.aurix.platform.platform.service.channel;

import com.aurix.platform.platform.entity.FilaNotificacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogChannelTest {
    @Test
    void deveRetornarNomeCanalLog() {
        LogChannel channel = new LogChannel();
        assertEquals("LOG", channel.getChannelName());
    }

    @Test
    void deveEnviarNotificacaoSemExcecao() {
        LogChannel channel = new LogChannel();
        FilaNotificacao notificacao = new FilaNotificacao();
        notificacao.setCanal("EMAIL");
        notificacao.setDestinatario("test@test.com");
        notificacao.setAssunto("Teste");
        notificacao.setCorpoRenderizado("Corpo da mensagem");
        assertDoesNotThrow(() -> channel.send(notificacao));
    }
}
