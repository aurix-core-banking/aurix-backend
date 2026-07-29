package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.service.ContaService;
import com.aurix.platform.shared.dto.ContaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de contas
 */
@RestController
@RequestMapping("/api/core/contas")
@Tag(name = "Contas", description = "API para gestão de contas do Aurix")
public class ContaController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaController.class);
    private final ContaService contaService;

    /**
     * Cria uma nova conta
     */
    @PostMapping
    @Operation(summary = "Criar conta", description = "Cria uma nova conta para um cliente")
    public ResponseEntity<ContaDTO> criarConta(@Valid @RequestBody ContaDTO contaDTO) {
        log.info("Recebida solicitação para criar conta para cliente ID: {}", contaDTO.getClienteId());
        ContaDTO contaCriada = contaService.criarConta(contaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaCriada);
    }

    /**
     * Busca conta por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Busca uma conta pelo ID")
    public ResponseEntity<ContaDTO> buscarContaPorId(@Parameter(description = "ID da conta") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar conta ID: {}", id);
        ContaDTO conta = contaService.buscarContaPorId(id);
        return ResponseEntity.ok(conta);
    }

    /**
     * Busca conta por número
     */
    @GetMapping("/numero/{numeroConta}")
    @Operation(summary = "Buscar conta por número", description = "Busca uma conta pelo número")
    public ResponseEntity<ContaDTO> buscarContaPorNumero(@Parameter(description = "Número da conta") @PathVariable String numeroConta) {
        log.info("Recebida solicitação para buscar conta número: {}", numeroConta);
        ContaDTO conta = contaService.buscarContaPorNumero(numeroConta);
        return ResponseEntity.ok(conta);
    }

    /**
     * Lista contas por cliente
     */
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contas por cliente", description = "Lista todas as contas de um cliente")
    public ResponseEntity<List<ContaDTO>> listarContasPorCliente(@Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        log.info("Recebida solicitação para listar contas do cliente ID: {}", clienteId);
        List<ContaDTO> contas = contaService.listarContasPorCliente(clienteId);
        return ResponseEntity.ok(contas);
    }

    /**
     * Lista contas ativas por cliente
     */
    @GetMapping("/cliente/{clienteId}/ativas")
    @Operation(summary = "Listar contas ativas por cliente", description = "Lista apenas contas ativas de um cliente")
    public ResponseEntity<List<ContaDTO>> listarContasAtivasPorCliente(@Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        log.info("Recebida solicitação para listar contas ativas do cliente ID: {}", clienteId);
        List<ContaDTO> contas = contaService.listarContasAtivasPorCliente(clienteId);
        return ResponseEntity.ok(contas);
    }

    /**
     * Lista todas as contas
     */
    @GetMapping
    @Operation(summary = "Listar contas", description = "Lista todas as contas do sistema")
    public ResponseEntity<List<ContaDTO>> listarContas() {
        log.info("Recebida solicitação para listar contas");
        List<ContaDTO> contas = contaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    /**
     * Atualiza conta
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta", description = "Atualiza dados de uma conta")
    public ResponseEntity<ContaDTO> atualizarConta(@Parameter(description = "ID da conta") @PathVariable Long id, @Valid @RequestBody ContaDTO contaDTO) {
        log.info("Recebida solicitação para atualizar conta ID: {}", id);
        ContaDTO contaAtualizada = contaService.atualizarConta(id, contaDTO);
        return ResponseEntity.ok(contaAtualizada);
    }

    /**
     * Fecha conta
     */
    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar conta", description = "Fecha uma conta")
    public ResponseEntity<Void> fecharConta(@Parameter(description = "ID da conta") @PathVariable Long id) {
        log.info("Recebida solicitação para fechar conta ID: {}", id);
        contaService.fecharConta(id);
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public ContaController(final ContaService contaService) {
        this.contaService = contaService;
    }
}
