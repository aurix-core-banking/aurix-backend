package com.aurix.platform.compliance.pep.controller;

import com.aurix.platform.compliance.pep.entity.PepCliente;
import com.aurix.platform.compliance.pep.service.PepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/pep")
@Tag(name = "PEP", description = "APIs para gestao de Pessoas Politicamente Expostas")
public class PepController {

    private final PepService pepService;

    public PepController(PepService pepService) {
        this.pepService = pepService;
    }

    @PostMapping("/verificar")
    @Operation(summary = "Verificar se cliente e PEP", description = "Verifica nas bases publicas se o cliente e PEP ou familiar de PEP")
    public ResponseEntity<PepCliente> verificarCliente(
            @RequestParam Long clienteId,
            @RequestParam String cpfCnpj,
            @RequestParam String nomeCompleto) {
        return ResponseEntity.ok(pepService.verificarCliente(clienteId, cpfCnpj, nomeCompleto));
    }

    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes PEP", description = "Lista todos os clientes classificados como PEP")
    public ResponseEntity<List<PepCliente>> listarClientesPep() {
        return ResponseEntity.ok(pepService.listarClientesPep());
    }

    @GetMapping("/clientes/todos")
    @Operation(summary = "Listar todos os registros PEP")
    public ResponseEntity<List<PepCliente>> listarTodos() {
        return ResponseEntity.ok(pepService.listarTodos());
    }

    @GetMapping("/clientes/{clienteId}")
    @Operation(summary = "Buscar registro PEP por cliente")
    public ResponseEntity<PepCliente> buscarPorCliente(@PathVariable Long clienteId) {
        return pepService.buscarPorClienteId(clienteId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clientes/risco/{nivel}")
    @Operation(summary = "Listar PEPs por nivel de risco")
    public ResponseEntity<List<PepCliente>> listarPorNivelRisco(
            @PathVariable PepCliente.NivelRiscoPep nivel) {
        return ResponseEntity.ok(pepService.listarPorNivelRisco(nivel));
    }

    @GetMapping("/clientes/alto-risco")
    @Operation(summary = "Listar PEPs de alto risco")
    public ResponseEntity<List<PepCliente>> listarAltoRisco() {
        return ResponseEntity.ok(pepService.listarAltoRisco());
    }

    @PutMapping("/clientes/{id}")
    @Operation(summary = "Atualizar classificacao PEP", description = "Atualiza a classificacao e dados de um registro PEP")
    public ResponseEntity<PepCliente> atualizarClassificacao(
            @PathVariable Long id,
            @RequestParam PepCliente.ClassificacaoPep classificacao,
            @RequestParam(required = false) String cargoFuncao,
            @RequestParam(required = false) String orgaoInstituicao) {
        return ResponseEntity.ok(pepService.atualizarClassificacao(id, classificacao, cargoFuncao, orgaoInstituicao));
    }

    @PostMapping("/atualizacao-periodica")
    @Operation(summary = "Executar atualizacao periodica", description = "Executa a verificacao periodica de todos os registros PEP nas bases publicas")
    public ResponseEntity<Map<String, String>> atualizacaoPeriodica() {
        pepService.atualizacaoPeriodica();
        return ResponseEntity.ok(Map.of(
            "status", "sucesso",
            "mensagem", "Atualizacao periodica PEP concluida"
        ));
    }
}
