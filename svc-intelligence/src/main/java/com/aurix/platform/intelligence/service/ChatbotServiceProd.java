package com.aurix.platform.intelligence.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("prod")
public class ChatbotServiceProd implements ChatbotService {

    // @Value("${aurix.ml.chatbot-url:http://localhost:8000/chatbot}")
    // private String chatbotUrl;

    @Override
    public Map<String, Object> processarMensagem(String texto) {
        return Map.of(
                "resposta", "Funcionalidade em implementação",
                "escalarParaHumano", true
        );
    }
}
