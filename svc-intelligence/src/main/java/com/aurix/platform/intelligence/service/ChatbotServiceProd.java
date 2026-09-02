package com.aurix.platform.intelligence.service;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Integração de chatbot com o serviço de ML (aurix-ml/llm). Ativo no perfil
 * "prod". Envia a mensagem para o endpoint {@code /chatbot} do serviço de ML e
 * interpreta as intenções/falhas. Em indisponibilidade, escala para humano
 * como fallback.
 */
@Service
@Profile("prod")
public class ChatbotServiceProd implements ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotServiceProd.class);

    private final RestClient mlRestClient;

    public ChatbotServiceProd(RestClient mlRestClient) {
        this.mlRestClient = mlRestClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> processarMensagem(String texto) {
        try {
            Map<String, Object> resposta = mlRestClient.post()
                    .uri("/chatbot")
                    .body(Map.of("mensagem", texto))
                    .retrieve()
                    .body(Map.class);

            String respostaTexto = resposta != null ? (String) resposta.get("resposta") : null;
            String intencao = resposta != null ? (String) resposta.getOrDefault("intencao", "desconhecida") : null;
            boolean escalar = resposta != null && Boolean.TRUE.equals(resposta.get("escalar_para_humano"));

            return Map.of(
                    "resposta", respostaTexto != null ? respostaTexto
                            : "Desculpe, não consegui processar a mensagem agora.",
                    "intencao", intencao != null ? intencao : "desconhecida",
                    "escalarParaHumano", escalar);
        } catch (Exception e) {
            log.warn("Falha ao comunicar com ML service para chatbot: {}", e.getMessage());
            return Map.of(
                    "resposta", "Estamos com dificuldades técnicas. Um atendente humano irá te ajudar em instantes.",
                    "intencao", "fallback",
                    "escalarParaHumano", true);
        }
    }
}