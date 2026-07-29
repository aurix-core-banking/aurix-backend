package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.service.ClienteService;
import com.aurix.platform.shared.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de clientes
 */
@RestController
@RequestMapping("/api/core/clientes")
@Tag(name = "Clientes", description = "API para gestão de clientes do Aurix")
public class ClienteController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    /**
     * Cria um novo cliente
     */
    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("Recebida solicitação para criar cliente: {}", clienteDTO.getCpf());
        ClienteDTO clienteCriado = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    /**
     * Busca cliente por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente pelo ID")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@Parameter(description = "ID do cliente") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar cliente ID: {}", id);
        ClienteDTO cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Busca cliente por CPF
     */
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Busca um cliente pelo CPF")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@Parameter(description = "CPF do cliente") @PathVariable String cpf) {
        log.info("Recebida solicitação para buscar cliente CPF: {}", cpf);
        ClienteDTO cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Busca cliente por CNPJ
     */
    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar cliente por CNPJ", description = "Busca um cliente pelo CNPJ")
    public ResponseEntity<ClienteDTO> buscarClientePorCnpj(@Parameter(description = "CNPJ do cliente") @PathVariable String cnpj) {
        log.info("Recebida solicitação para buscar cliente CNPJ: {}", cnpj);
        ClienteDTO cliente = clienteService.buscarClientePorCnpj(cnpj);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Lista todos os clientes
     */
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes do sistema")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        log.info("Recebida solicitação para listar clientes");
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Lista clientes ativos
     */
    @GetMapping("/ativos")
    @Operation(summary = "Listar clientes ativos", description = "Lista apenas clientes ativos")
    public ResponseEntity<List<ClienteDTO>> listarClientesAtivos() {
        log.info("Recebida solicitação para listar clientes ativos");
        List<ClienteDTO> clientes = clienteService.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Atualiza cliente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza dados de um cliente")
    public ResponseEntity<ClienteDTO> atualizarCliente(@Parameter(description = "ID do cliente") @PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("Recebida solicitação para atualizar cliente ID: {}", id);
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * Inativa cliente
     */
    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar cliente", description = "Inativa um cliente")
    public ResponseEntity<Void> inativarCliente(@Parameter(description = "ID do cliente") @PathVariable Long id) {
        log.info("Recebida solicitação para inativar cliente ID: {}", id);
        clienteService.inativarCliente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ativa cliente
     */
    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar cliente", description = "Ativa um cliente")
    public ResponseEntity<Void> ativarCliente(@Parameter(description = "ID do cliente") @PathVariable Long id) {
        log.info("Recebida solicitação para ativar cliente ID: {}", id);
        clienteService.ativarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public ClienteController(final ClienteService clienteService) {
        this.clienteService = clienteService;
    }
}
