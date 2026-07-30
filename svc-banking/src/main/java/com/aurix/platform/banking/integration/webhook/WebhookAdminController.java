package com.aurix.platform.banking.integration.webhook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webhook")
@Tag(name = "Webhook Admin", description = "Administração de webhooks — listagem, reenvio, consulta de status")
public class WebhookAdminController {

    private final WebhookEventRepository webhookEventRepository;
    private final EventPipelineService eventPipelineService;

    public WebhookAdminController(WebhookEventRepository webhookEventRepository,
                                   EventPipelineService eventPipelineService) {
        this.webhookEventRepository = webhookEventRepository;
        this.eventPipelineService = eventPipelineService;
    }

    @GetMapping("/eventos")
    @Operation(summary = "Listar eventos", description = "Lista eventos de webhook com filtro opcional por status")
    public ResponseEntity<List<WebhookEvent>> listarEventos(
            @RequestParam(required = false) WebhookEvent.WebhookEventStatus status) {
        if (status != null) {
            return ResponseEntity.ok(
                webhookEventRepository.findByStatusOrderByDataCriacaoAsc(status));
        }
        return ResponseEntity.ok(webhookEventRepository.findAll());
    }

    @GetMapping("/eventos/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<WebhookEvent> buscarEvento(@PathVariable Long id) {
        return webhookEventRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/eventos/{id}/reenviar")
    @Operation(summary = "Reenviar evento", description = "Reenvia um evento de webhook manualmente")
    public ResponseEntity<WebhookEvent> reenviarEvento(@PathVariable Long id) {
        return webhookEventRepository.findById(id)
            .map(event -> {
                event.setStatus(WebhookEvent.WebhookEventStatus.PENDING);
                event.setAttempts(0);
                event.setNextRetryAt(java.time.LocalDateTime.now());
                webhookEventRepository.save(event);
                eventPipelineService.send(event);
                return ResponseEntity.ok(event);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/eventos/retry-falhos")
    @Operation(summary = "Reenviar eventos com falha", description = "Tenta reenviar todos os eventos com status FAILED")
    public ResponseEntity<Integer> reenviarFalhos() {
        int reenviados = eventPipelineService.retryFailed();
        return ResponseEntity.ok(reenviados);
    }
}
