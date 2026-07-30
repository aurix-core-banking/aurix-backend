package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.ValidationRuleDTO;
import com.aurix.platform.banking.core.service.ValidationRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/validation-rules")
@Tag(name = "Regras de Validação", description = "CRUD de regras dinâmicas com SpEL para Flex Controls")
@CrossOrigin(origins = "*")
public class ValidationRuleController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidationRuleController.class);
    private final ValidationRuleService service;

    public ValidationRuleController(final ValidationRuleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar regra de validação")
    public ResponseEntity<ValidationRuleDTO> criar(@Valid @RequestBody ValidationRuleDTO dto) {
        ValidationRuleDTO criada = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regra de validação")
    public ResponseEntity<ValidationRuleDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ValidationRuleDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar regra por ID")
    public ResponseEntity<ValidationRuleDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as regras")
    public ResponseEntity<List<ValidationRuleDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/categoria/{category}")
    @Operation(summary = "Listar regras por categoria")
    public ResponseEntity<List<ValidationRuleDTO>> listarPorCategoria(@PathVariable String category) {
        return ResponseEntity.ok(service.listarPorCategoria(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar regra de validação")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Ativar/desativar regra")
    public ResponseEntity<ValidationRuleDTO> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleActive(id));
    }
}
