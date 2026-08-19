package com.aurix.platform.settlement.controller;

import com.aurix.platform.settlement.dto.LiquidacaoRequest;
import com.aurix.platform.settlement.dto.LiquidacaoResponse;
import com.aurix.platform.settlement.service.LiquidacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/liquidacao")
@Tag(name = "Liquidacao", description = "Liquidar transacoes D+0, D+1")
public class LiquidacaoController {

    private final LiquidacaoService liquidacaoService;

    public LiquidacaoController(LiquidacaoService liquidacaoService) {
        this.liquidacaoService = liquidacaoService;
    }

    @PostMapping
    @Operation(summary = "Criar liquidacao")
    public ResponseEntity<LiquidacaoResponse> criarLiquidacao(
            @Valid @RequestBody LiquidacaoRequest request) {
        LiquidacaoResponse response = liquidacaoService.criarLiquidacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar liquidacao por ID")
    public ResponseEntity<LiquidacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(liquidacaoService.buscarPorId(id));
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar liquidacoes pendentes")
    public ResponseEntity<List<LiquidacaoResponse>> listarPendentes() {
        return ResponseEntity.ok(liquidacaoService.listarPendentes());
    }

    @PutMapping("/{id}/processar")
    @Operation(summary = "Processar liquidacao")
    public ResponseEntity<LiquidacaoResponse> processar(@PathVariable Long id) {
        return ResponseEntity.ok(liquidacaoService.processar(id));
    }

    @PutMapping("/{id}/estornar")
    @Operation(summary = "Estornar liquidacao")
    public ResponseEntity<LiquidacaoResponse> estornar(@PathVariable Long id) {
        return ResponseEntity.ok(liquidacaoService.estornar(id));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar liquidacoes por tipo")
    public ResponseEntity<List<LiquidacaoResponse>> listarPorTipo(
            @PathVariable String tipo) {
        return ResponseEntity.ok(liquidacaoService.listarPorTipo(tipo));
    }
}
