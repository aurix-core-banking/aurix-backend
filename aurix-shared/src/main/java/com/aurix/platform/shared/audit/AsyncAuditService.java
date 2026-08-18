package com.aurix.platform.shared.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Audit assíncrono — escreve para Kafka ao invés de sincronizar com o DB.
 * O consumidor (svc-platform/AuditEventConsumer) persiste no ClickHouse + logs_auditoria.
 */
@Component
public class AsyncAuditService {

    private static final Logger log = LoggerFactory.getLogger(AsyncAuditService.class);
    private static final String TOPIC = "audit-log";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AsyncAuditService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void registrar(String acao, String entidade, String entidadeId,
                          Long usuarioId, String ipOrigem, String userAgent,
                          Object dadosAnteriores, Object dadosNovos) {
        try {
            Map<String, Object> evento = Map.of(
                "acao", acao,
                "entidade", entidade,
                "entidadeId", entidadeId != null ? entidadeId : "",
                "usuarioId", usuarioId != null ? usuarioId : 0,
                "ipOrigem", ipOrigem != null ? ipOrigem : "",
                "userAgent", userAgent != null ? userAgent : "",
                "dadosAnteriores", dadosAnteriores != null ? dadosAnteriores : "",
                "dadosNovos", dadosNovos != null ? dadosNovos : "",
                "dataCriacao", LocalDateTime.now().toString(),
                "resultado", "SUCESSO"
            );

            String payload = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send(TOPIC, entidade + "." + acao, payload);
        } catch (Exception e) {
            log.error("Erro ao publicar audit event assíncrono: {}", e.getMessage());
        }
    }
}
