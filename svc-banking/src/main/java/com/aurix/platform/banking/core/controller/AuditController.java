package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.entity.AuditLog;
import com.aurix.platform.banking.core.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
@Tag(name = "Audit Trail", description = "Log de auditoria para rastreamento de alterações em entidades")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "Listar logs de auditoria", description = "Retorna todos os logs de auditoria")
    public ResponseEntity<List<AuditLog>> listarLogs() {
        return ResponseEntity.ok(auditService.listarTodos());
    }

    @GetMapping("/{entidade}/{entidadeId}")
    @Operation(summary = "Buscar logs por entidade", description = "Retorna logs de alteração de uma entidade específica")
    public ResponseEntity<List<AuditLog>> buscarPorEntidade(
            @PathVariable String entidade, @PathVariable String entidadeId) {
        return ResponseEntity.ok(auditService.buscarPorEntidade(entidade, entidadeId));
    }
}
