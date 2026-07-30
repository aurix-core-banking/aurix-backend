package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.entity.FilaNotificacao;
import com.aurix.platform.platform.entity.PreferenciaCliente;
import com.aurix.platform.platform.entity.TemplateNotificacao;
import com.aurix.platform.platform.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/notificacoes")
@Tag(name = "Notification", description = "Multi-channel notification management")
public class NotificacaoController {
    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @PostMapping("/enviar")
    @Operation(summary = "Enviar notificacao para um cliente")
    public ResponseEntity<FilaNotificacao> enviar(@RequestParam Long clienteId,
            @RequestParam String templateCodigo, @RequestParam String destinatario,
            @RequestBody(required = false) Map<String, String> variaveis) {
        FilaNotificacao resultado = service.enviar(clienteId, templateCodigo, destinatario,
                variaveis != null ? variaveis : Map.of());
        if (resultado == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar notificacoes de um cliente")
    public ResponseEntity<List<FilaNotificacao>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarNotificacoesPorCliente(clienteId));
    }

    @PostMapping("/templates")
    @Operation(summary = "Criar template de notificacao")
    public ResponseEntity<TemplateNotificacao> criarTemplate(@RequestBody TemplateNotificacao template) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTemplate(template));
    }

    @GetMapping("/templates")
    @Operation(summary = "Listar todos os templates")
    public ResponseEntity<List<TemplateNotificacao>> listarTemplates() {
        return ResponseEntity.ok(service.listarTemplates());
    }

    @PostMapping("/preferencias")
    @Operation(summary = "Salvar preferencias de notificacao do cliente")
    public ResponseEntity<PreferenciaCliente> salvarPreferencia(
            @RequestBody PreferenciaCliente preferencia) {
        return ResponseEntity.ok(service.salvarPreferencia(preferencia));
    }

    @GetMapping("/preferencias/{clienteId}")
    @Operation(summary = "Buscar preferencias de notificacao do cliente")
    public ResponseEntity<PreferenciaCliente> buscarPreferencia(@PathVariable Long clienteId) {
        return service.buscarPreferencia(clienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/renderizar")
    @Operation(summary = "Renderizar template com variaveis (preview)")
    public ResponseEntity<Map<String, String>> renderizar(@RequestParam String template,
            @RequestBody Map<String, String> variaveis) {
        String resultado = service.renderizar(template, variaveis);
        return ResponseEntity.ok(Map.of("renderizado", resultado));
    }
}
