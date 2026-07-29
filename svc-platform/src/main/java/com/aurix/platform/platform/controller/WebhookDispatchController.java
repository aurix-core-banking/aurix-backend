package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.service.WebhookSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/webhooks/dispatch")
public class WebhookDispatchController {
    private final WebhookSenderService webhookSenderService;

    @PostMapping
    public ResponseEntity<Map<String, String>> dispatch(@RequestHeader("X-Tenant-Id") String tenantId, @RequestBody Map<String, Object> body) {
        String evento = (String) body.get("evento");
        Object payload = body.get("payload");
        if (evento == null || evento.isBlank()) return ResponseEntity.badRequest().build();
        webhookSenderService.dispatch(tenantId, evento, payload != null ? payload : Map.of());
        return ResponseEntity.accepted().body(Map.of("status", "accepted"));
    }

    @java.lang.SuppressWarnings("all")
    public WebhookDispatchController(final WebhookSenderService webhookSenderService) {
        this.webhookSenderService = webhookSenderService;
    }
}
