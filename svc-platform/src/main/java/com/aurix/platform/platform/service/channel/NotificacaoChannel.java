package com.aurix.platform.platform.service.channel;

import com.aurix.platform.platform.entity.FilaNotificacao;

public interface NotificacaoChannel {
    void send(FilaNotificacao notificacao);
    String getChannelName();
}
