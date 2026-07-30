package com.aurix.platform.platform.service.channel;

import com.aurix.platform.platform.entity.FilaNotificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogChannel implements NotificacaoChannel {
    private static final Logger log = LoggerFactory.getLogger(LogChannel.class);

    @Override
    public void send(FilaNotificacao notificacao) {
        log.info("[NOTIFICATION][{}] Para: {} | Assunto: {} | Corpo: {}",
                notificacao.getCanal(), notificacao.getDestinatario(),
                notificacao.getAssunto(), notificacao.getCorpoRenderizado());
    }

    @Override
    public String getChannelName() {
        return "LOG";
    }
}
