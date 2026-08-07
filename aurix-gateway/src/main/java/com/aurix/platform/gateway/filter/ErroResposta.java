package com.aurix.platform.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utilitário para escrita de respostas de erro em JSON pelo Gateway.
 */
public final class ErroResposta {

    private static final Logger log = LoggerFactory.getLogger(ErroResposta.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ErroResposta() {
    }

    public static Mono<Void> escrever(ServerWebExchange exchange, HttpStatus status, String mensagem) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", java.time.Instant.now().toString());
        corpo.put("status", status.value());
        corpo.put("error", status.getReasonPhrase());
        corpo.put("message", mensagem);
        corpo.put("path", exchange.getRequest().getPath().value());

        byte[] bytes;
        try {
            bytes = MAPPER.writeValueAsBytes(corpo);
        } catch (JsonProcessingException e) {
            log.warn("Falha ao serializar resposta de erro: {}", e.getMessage());
            bytes = ("{\"status\":" + status.value() + ",\"message\":\"" + mensagem + "\"}").getBytes(StandardCharsets.UTF_8);
        }
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
