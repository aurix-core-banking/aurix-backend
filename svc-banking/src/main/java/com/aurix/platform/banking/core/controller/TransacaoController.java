package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.service.TransacaoService;
import com.aurix.platform.shared.dto.TransacaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
@Tag(name = "Transações", description = "API para criação e consulta de transações bancárias")
@CrossOrigin(origins = "*")
public class TransacaoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransacaoController.class);
    private final TransacaoService transacaoService;

    @PostMapping
    @Operation(summary = "Criar transação", description = "Registra uma nova transação para posterior liquidação")
    public ResponseEntity<TransacaoDTO> criar(@Valid @RequestBody TransacaoDTO dto) {
        TransacaoDTO criada = transacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID")
    public ResponseEntity<TransacaoDTO> buscarPorId(@PathVariable Long id) {
        return transacaoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar transação por código")
    public ResponseEntity<TransacaoDTO> buscarPorCodigo(@PathVariable String codigo) {
        return transacaoService.buscarPorCodigo(codigo).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar transações da conta (paginado)")
    public ResponseEntity<Page<TransacaoDTO>> listarPorConta(@PathVariable Long contaId, @PageableDefault(size = 20, sort = "dataTransacao") Pageable pageable) {
        return ResponseEntity.ok(transacaoService.listarPorConta(contaId, pageable));
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar transações pendentes (paginado)")
    public ResponseEntity<Page<TransacaoDTO>> listarPendentes(@PageableDefault(size = 20, sort = "dataTransacao") Pageable pageable) {
        return ResponseEntity.ok(transacaoService.listarPendentes(pageable));
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoController(final TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }
}
