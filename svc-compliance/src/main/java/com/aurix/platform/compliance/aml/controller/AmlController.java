package com.aurix.platform.compliance.aml.controller;

import com.aurix.platform.compliance.aml.entity.AmlAlerta;
import com.aurix.platform.compliance.aml.entity.AmlInvestigacao;
import com.aurix.platform.compliance.aml.entity.AmlRegra;
import com.aurix.platform.compliance.aml.service.AmlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/aml")
@Tag(name = "PLD/AML", description = "APIs para Prevencao a Lavagem de Dinheiro e Combate ao Financiamento do Terrorismo")
public class AmlController {

    private final AmlService amlService;

    public AmlController(AmlService amlService) {
        this.amlService = amlService;
    }

    @PostMapping("/regras")
    @Operation(summary = "Criar regra de deteccao AML", description = "Cria uma nova regra automatica de deteccao de lavagem de dinheiro")
    public ResponseEntity<AmlRegra> criarRegra(@RequestBody AmlRegra regra) {
        AmlRegra salva = amlService.criarRegra(regra);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/regras")
    @Operation(summary = "Listar regras AML")
    public ResponseEntity<List<AmlRegra>> listarRegras() {
        return ResponseEntity.ok(amlService.listarRegras());
    }

    @GetMapping("/regras/ativas")
    @Operation(summary = "Listar regras ativas")
    public ResponseEntity<List<AmlRegra>> listarRegrasAtivas() {
        return ResponseEntity.ok(amlService.listarRegrasAtivas());
    }

    @PutMapping("/regras/{id}")
    @Operation(summary = "Atualizar regra AML")
    public ResponseEntity<AmlRegra> atualizarRegra(@PathVariable Long id, @RequestBody AmlRegra regra) {
        return ResponseEntity.ok(amlService.atualizarRegra(id, regra));
    }

    @PutMapping("/regras/{id}/toggle")
    @Operation(summary = "Ativar/desativar regra AML")
    public ResponseEntity<Void> toggleRegra(@PathVariable Long id) {
        amlService.toggleRegra(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas")
    @Operation(summary = "Listar alertas AML", description = "Lista todos os alertas gerados pelo sistema")
    public ResponseEntity<List<AmlAlerta>> listarAlertas() {
        return ResponseEntity.ok(amlService.listarAlertas());
    }

    @GetMapping("/alertas/abertos")
    @Operation(summary = "Listar alertas abertos")
    public ResponseEntity<List<AmlAlerta>> listarAlertasAbertos() {
        return ResponseEntity.ok(amlService.listarAlertasAbertos());
    }

    @GetMapping("/alertas/cliente/{clienteId}")
    @Operation(summary = "Listar alertas por cliente")
    public ResponseEntity<List<AmlAlerta>> listarAlertasPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(amlService.listarAlertasPorCliente(clienteId));
    }

    @GetMapping("/alertas/score/{scoreMinimo}")
    @Operation(summary = "Listar alertas por score minimo")
    public ResponseEntity<List<AmlAlerta>> listarAlertasPorScore(@PathVariable Integer scoreMinimo) {
        return ResponseEntity.ok(amlService.listarAlertasPorScoreMinimo(scoreMinimo));
    }

    @PostMapping("/alertas/{id}/investigar")
    @Operation(summary = "Iniciar investigacao de alerta", description = "Inicia o workflow de investigacao para um alerta detectado")
    public ResponseEntity<AmlAlerta> iniciarInvestigacao(
            @PathVariable Long id,
            @RequestParam String investigador) {
        return ResponseEntity.ok(amlService.iniciarInvestigacao(id, investigador));
    }

    @PostMapping("/alertas/{id}/resolver")
    @Operation(summary = "Resolver alerta AML", description = "Resolve um alerta com decisao e parecer da investigacao")
    public ResponseEntity<AmlAlerta> resolverAlerta(
            @PathVariable Long id,
            @RequestParam AmlInvestigacao.DecisaoInvestigacao decisao,
            @RequestParam String parecer,
            @RequestParam String motivoResolucao) {
        return ResponseEntity.ok(amlService.resolverAlerta(id, decisao, parecer, motivoResolucao));
    }

    @PostMapping("/alertas/{id}/arquivar")
    @Operation(summary = "Arquivar alerta")
    public ResponseEntity<AmlAlerta> arquivarAlerta(
            @PathVariable Long id,
            @RequestParam String justificativa) {
        return ResponseEntity.ok(amlService.arquivarAlerta(id, justificativa));
    }

    @GetMapping("/investigacoes")
    @Operation(summary = "Listar investigacoes AML")
    public ResponseEntity<List<AmlInvestigacao>> listarInvestigacoes() {
        return ResponseEntity.ok(amlService.listarInvestigacoes());
    }

    @GetMapping("/investigacoes/abertas")
    @Operation(summary = "Listar investigacoes abertas")
    public ResponseEntity<List<AmlInvestigacao>> listarInvestigacoesAbertas() {
        return ResponseEntity.ok(amlService.listarInvestigacoesAbertas());
    }

    @PostMapping("/executar-regras")
    @Operation(summary = "Executar regras automaticas", description = "Executa todas as regras automaticas de deteccao AML")
    public ResponseEntity<List<AmlAlerta>> executarRegras() {
        List<AmlAlerta> novosAlertas = amlService.executarRegrasAutomaticas();
        return ResponseEntity.ok(novosAlertas);
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Obter estatisticas AML")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        return ResponseEntity.ok(Map.of(
            "alertasAbertos", amlService.contarAlertasAbertos(),
            "investigacoesAbertas", amlService.contarInvestigacoesAbertas(),
            "totalRegrasAtivas", amlService.listarRegrasAtivas().size()
        ));
    }
}
