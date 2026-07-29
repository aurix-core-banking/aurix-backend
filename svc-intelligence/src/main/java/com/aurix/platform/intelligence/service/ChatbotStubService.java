package com.aurix.platform.intelligence.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("!prod")
public class ChatbotStubService implements ChatbotService {

    @Override
    public Map<String, Object> processarMensagem(String texto) {
        return Map.of(
            "resposta", "Funcionalidade em implementação. Em breve teremos atendimento automatizado.",
            "intencao", "stub",
            "escalarParaHumano", false
        );
    }
}
