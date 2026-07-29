package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.entity.AgendamentoDebito;
import com.aurix.platform.banking.core.service.AgendamentoDebitoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/core/agendamentos-debito")
@Tag(name = "Debito automatico", description = "Agendamento e execucao de debitos em conta")
public class AgendamentoDebitoController {
    private final AgendamentoDebitoService agendamentoDebitoService;

    @PostMapping
    @Operation(summary = "Agendar debito")
    public ResponseEntity<AgendamentoDebito> agendar(@RequestParam Long contaId, @RequestParam BigDecimal valor, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDebito, @RequestParam(required = false) String descricao, @RequestParam(required = false) Long boletoId, @RequestParam(required = false) Boolean recorrente, @RequestParam(required = false) String periodicidade) {
        AgendamentoDebito a = agendamentoDebitoService.agendar(contaId, valor, dataDebito, descricao, boletoId, recorrente, periodicidade);
        return ResponseEntity.ok(a);
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar agendamento")
    public ResponseEntity<AgendamentoDebito> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoDebitoService.cancelar(id));
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar agendamentos da conta")
    public ResponseEntity<List<AgendamentoDebito>> listarPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(agendamentoDebitoService.listarPorConta(contaId));
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar agendamentos pendentes")
    public ResponseEntity<List<AgendamentoDebito>> listarPendentes() {
        return ResponseEntity.ok(agendamentoDebitoService.listarPendentes());
    }

    @java.lang.SuppressWarnings("all")
    public AgendamentoDebitoController(final AgendamentoDebitoService agendamentoDebitoService) {
        this.agendamentoDebitoService = agendamentoDebitoService;
    }
}
