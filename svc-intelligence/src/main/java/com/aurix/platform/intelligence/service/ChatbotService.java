package com.aurix.platform.intelligence.service;

import java.util.Map;

public interface ChatbotService {
    Map<String, Object> processarMensagem(String texto);
}
