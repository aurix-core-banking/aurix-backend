package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.ProcessingCodeDTO;
import com.aurix.platform.banking.core.service.ProcessingCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processing-codes")
@Tag(name = "Processing Codes", description = "Códigos de processamento para pagamentos")
@CrossOrigin(origins = "*")
public class ProcessingCodeController {

    private final ProcessingCodeService service;

    public ProcessingCodeController(final ProcessingCodeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar código de processamento")
    public ResponseEntity<ProcessingCodeDTO> criar(@Valid @RequestBody ProcessingCodeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos")
    public ResponseEntity<List<ProcessingCodeDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar ativos ordenados por prioridade")
    public ResponseEntity<List<ProcessingCodeDTO>> listarAtivos() {
        return ResponseEntity.ok(service.listarAtivos());
    }

    @GetMapping("/tipo/{paymentType}")
    @Operation(summary = "Listar por tipo de pagamento")
    public ResponseEntity<List<ProcessingCodeDTO>> listarPorTipo(@PathVariable String paymentType) {
        return ResponseEntity.ok(service.listarPorTipoPagamento(paymentType));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<ProcessingCodeDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Buscar por código")
    public ResponseEntity<ProcessingCodeDTO> buscarPorCode(@PathVariable String code) {
        return service.buscarPorCode(code)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar código de processamento")
    public ResponseEntity<ProcessingCodeDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ProcessingCodeDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover código de processamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
