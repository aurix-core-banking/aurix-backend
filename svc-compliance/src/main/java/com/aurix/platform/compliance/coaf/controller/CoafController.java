package com.aurix.platform.compliance.coaf.controller;

import com.aurix.platform.compliance.coaf.entity.CoafNotificacao;
import com.aurix.platform.compliance.coaf.entity.CoafRelatorio;
import com.aurix.platform.compliance.coaf.service.CoafService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/coaf")
@Tag(name = "COAF", description = "APIs para conformidade com COAF - Conselho de Controle de Atividades Financeiras")
public class CoafController {

    private final CoafService coafService;

    public CoafController(CoafService coafService) {
        this.coafService = coafService;
    }

    @PostMapping("/notificar")
    @Operation(summary = "Enviar notificacao COAF", description = "Registra e envia notificacao de transacao suspeita ao COAF")
    public ResponseEntity<CoafNotificacao> notificar(@RequestBody CoafNotificacao notificacao) {
        CoafNotificacao salva = coafService.notificar(notificacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/notificacoes")
    @Operation(summary = "Listar notificacoes COAF", description = "Lista todas as notificacoes registradas")
    public ResponseEntity<List<CoafNotificacao>> listarNotificacoes() {
        return ResponseEntity.ok(coafService.listarNotificacoes());
    }

    @GetMapping("/notificacoes/periodo")
    @Operation(summary = "Listar notificacoes por periodo")
    public ResponseEntity<List<CoafNotificacao>> listarNotificacoesPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(coafService.listarNotificacoesPorPeriodo(inicio, fim));
    }

    @GetMapping("/notificacoes/atrasadas")
    @Operation(summary = "Listar notificacoes atrasadas", description = "Lista notificacoes que ultrapassaram o prazo de 24h")
    public ResponseEntity<List<CoafNotificacao>> listarNotificacoesAtrasadas() {
        return ResponseEntity.ok(coafService.listarNotificacoesAtrasadas());
    }

    @PostMapping("/relatorios")
    @Operation(summary = "Gerar relatorio de suspeitas", description = "Gera relatorio consolidado de transacoes suspeitas para envio ao COAF")
    public ResponseEntity<CoafRelatorio> gerarRelatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam CoafRelatorio.TipoRelatorioCoaf tipo) {
        CoafRelatorio relatorio = coafService.gerarRelatorio(dataInicio, dataFim, tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorio);
    }

    @GetMapping("/relatorios")
    @Operation(summary = "Listar relatorios COAF")
    public ResponseEntity<List<CoafRelatorio>> listarRelatorios() {
        return ResponseEntity.ok(coafService.listarRelatorios());
    }

    @GetMapping("/relatorios/pendentes")
    @Operation(summary = "Listar relatorios pendentes de envio")
    public ResponseEntity<List<CoafRelatorio>> listarRelatoriosPendentes() {
        return ResponseEntity.ok(coafService.listarRelatoriosPendentesEnvio());
    }

    @PostMapping("/relatorios/{codigo}/enviar")
    @Operation(summary = "Enviar relatorio ao COAF")
    public ResponseEntity<CoafRelatorio> enviarRelatorio(@PathVariable String codigo) {
        return ResponseEntity.ok(coafService.enviarRelatorio(codigo));
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Obter estatisticas COAF")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        return ResponseEntity.ok(Map.of(
            "notificacoesPendentes", coafService.listarNotificacoes().stream()
                .filter(n -> n.getStatus() == CoafNotificacao.StatusNotificacaoCoaf.PENDENTE).count(),
            "notificacoesAtrasadas", coafService.listarNotificacoesAtrasadas().size(),
            "relatoriosPendentesEnvio", coafService.listarRelatoriosPendentesEnvio().size()
        ));
    }
}
