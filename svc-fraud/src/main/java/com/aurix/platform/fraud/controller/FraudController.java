package com.aurix.platform.fraud.controller;

import com.aurix.platform.fraud.entity.BloqueioPreventivo;
import com.aurix.platform.fraud.entity.OcorrenciaFraude;
import com.aurix.platform.fraud.entity.RegraFraude;
import com.aurix.platform.fraud.entity.ScoreTransacao;
import com.aurix.platform.fraud.service.FraudScoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fraud/fraudes")
@Tag(name = "Fraud", description = "Anti-fraud scoring, rule evaluation, and transaction monitoring")
public class FraudController {
    private final FraudScoringService service;

    public FraudController(FraudScoringService service) {
        this.service = service;
    }

    @PostMapping("/regras")
    @Operation(summary = "Criar regra de fraude")
    public ResponseEntity<RegraFraude> criarRegra(@RequestBody RegraFraude regra) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarRegra(regra));
    }

    @GetMapping("/regras")
    @Operation(summary = "Listar todas as regras")
    public ResponseEntity<List<RegraFraude>> listarRegras() {
        return ResponseEntity.ok(service.listarRegras());
    }

    @GetMapping("/regras/{id}")
    @Operation(summary = "Buscar regra por ID")
    public ResponseEntity<RegraFraude> buscarRegra(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarRegra(id));
    }

    @PutMapping("/regras/{id}")
    @Operation(summary = "Atualizar regra")
    public ResponseEntity<RegraFraude> atualizarRegra(@PathVariable Long id, @RequestBody RegraFraude regra) {
        return ResponseEntity.ok(service.atualizarRegra(id, regra));
    }

    @DeleteMapping("/regras/{id}")
    @Operation(summary = "Excluir regra")
    public ResponseEntity<Void> excluirRegra(@PathVariable Long id) {
        service.excluirRegra(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/avaliar")
    @Operation(summary = "Avaliar transacao contra regras de fraude")
    public ResponseEntity<ScoreTransacao> avaliarTransacao(
            @RequestParam Long clienteId,
            @RequestParam String transacaoRef) {
        return ResponseEntity.ok(service.avaliarTransacao(clienteId, transacaoRef));
    }

    @GetMapping("/ocorrencias")
    @Operation(summary = "Listar ocorrencias de fraude")
    public ResponseEntity<List<OcorrenciaFraude>> listarOcorrencias(
            @RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return ResponseEntity.ok(service.listarOcorrenciasPorCliente(clienteId));
        }
        return ResponseEntity.ok(service.listarOcorrencias());
    }

    @GetMapping("/ocorrencias/{id}")
    @Operation(summary = "Buscar ocorrencia por ID")
    public ResponseEntity<OcorrenciaFraude> buscarOcorrencia(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarOcorrencia(id));
    }

    @PutMapping("/ocorrencias/{id}/status")
    @Operation(summary = "Atualizar status da ocorrencia")
    public ResponseEntity<OcorrenciaFraude> atualizarStatusOcorrencia(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.atualizarStatusOcorrencia(id, status));
    }

    @PostMapping("/bloqueios")
    @Operation(summary = "Criar bloqueio preventivo")
    public ResponseEntity<BloqueioPreventivo> criarBloqueio(@RequestBody BloqueioPreventivo bloqueio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarBloqueio(bloqueio));
    }

    @GetMapping("/bloqueios/cliente/{clienteId}")
    @Operation(summary = "Listar bloqueios do cliente")
    public ResponseEntity<List<BloqueioPreventivo>> listarBloqueios(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarBloqueios(clienteId));
    }

    @PutMapping("/bloqueios/{id}/desativar")
    @Operation(summary = "Desativar bloqueio preventivo")
    public ResponseEntity<BloqueioPreventivo> desativarBloqueio(@PathVariable Long id) {
        return ResponseEntity.ok(service.desativarBloqueio(id));
    }
}
