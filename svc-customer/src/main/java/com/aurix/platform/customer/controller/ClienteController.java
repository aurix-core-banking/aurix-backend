package com.aurix.platform.customer.controller;

import com.aurix.platform.customer.entity.Cliente;
import com.aurix.platform.customer.entity.Contato;
import com.aurix.platform.customer.entity.Endereco;
import com.aurix.platform.customer.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Criar cliente")
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criar(cliente));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @GetMapping("/documento/{documento}")
    @Operation(summary = "Buscar cliente por CPF/CNPJ")
    public ResponseEntity<Cliente> buscarPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(clienteService.buscarPorDocumento(documento));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.atualizar(id, cliente));
    }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<Cliente>> listar(
            @RequestParam(required = false) String segmento,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(clienteService.listar(segmento, status));
    }

    @GetMapping("/{id}/enderecos")
    @Operation(summary = "Listar enderecos do cliente")
    public ResponseEntity<List<Endereco>> listarEnderecos(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.listarEnderecos(id));
    }

    @PostMapping("/{id}/enderecos")
    @Operation(summary = "Adicionar endereco")
    public ResponseEntity<Endereco> adicionarEndereco(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.adicionarEndereco(id, endereco));
    }

    @GetMapping("/{id}/contatos")
    @Operation(summary = "Listar contatos do cliente")
    public ResponseEntity<List<Contato>> listarContatos(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.listarContatos(id));
    }

    @PostMapping("/{id}/contatos")
    @Operation(summary = "Adicionar contato")
    public ResponseEntity<Contato> adicionarContato(@PathVariable Long id, @Valid @RequestBody Contato contato) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.adicionarContato(id, contato));
    }
}
