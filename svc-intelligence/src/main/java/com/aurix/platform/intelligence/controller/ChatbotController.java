package com.aurix.platform.intelligence.controller;

import com.aurix.platform.intelligence.service.ChatbotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/intelligence/chatbot")
@Tag(name = "Chatbot", description = "Assistente virtual")
public class ChatbotController {
    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/mensagem")
    @Operation(summary = "Enviar mensagem ao chatbot")
    public ResponseEntity<Map<String, Object>> enviar(@RequestBody Map<String, String> body) {
        String texto = body != null ? body.get("texto") : null;
        return ResponseEntity.ok(chatbotService.processarMensagem(texto));
    }
}
