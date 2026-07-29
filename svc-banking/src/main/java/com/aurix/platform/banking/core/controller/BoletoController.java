package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.entity.Boleto;
import com.aurix.platform.banking.core.service.BoletoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/core/boletos")
@Tag(name = "Boletos", description = "Emissao e pagamento de boletos")
public class BoletoController {
    private final BoletoService boletoService;

    @PostMapping("/emitir")
    @Operation(summary = "Emitir boleto")
    public ResponseEntity<Boleto> emitir(@RequestParam(required = false) Long contaIdPagador, @RequestParam String beneficiarioNome, @RequestParam String beneficiarioDocumento, @RequestParam(required = false) String pagadorNome, @RequestParam(required = false) String pagadorDocumento, @RequestParam BigDecimal valor, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento, @RequestParam(required = false) String descricao, @RequestParam(defaultValue = "false") boolean usarProvedorExterno) {
        Boleto b = boletoService.emitir(contaIdPagador, beneficiarioNome, beneficiarioDocumento, pagadorNome, pagadorDocumento, valor, dataVencimento, descricao, usarProvedorExterno);
        return ResponseEntity.ok(b);
    }

    @PostMapping("/{id}/registrar-pagamento")
    @Operation(summary = "Registrar pagamento de boleto (baixa em conta)")
    public ResponseEntity<Boleto> registrarPagamento(@PathVariable Long id, @RequestParam Long contaIdPagador) {
        Boleto b = boletoService.registrarPagamento(id, contaIdPagador);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar boleto por ID")
    public ResponseEntity<Boleto> buscarPorId(@PathVariable Long id) {
        return boletoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numeroBoleto}")
    @Operation(summary = "Buscar boleto por numero")
    public ResponseEntity<Boleto> buscarPorNumero(@PathVariable String numeroBoleto) {
        return boletoService.buscarPorNumero(numeroBoleto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta/{contaIdPagador}")
    @Operation(summary = "Listar boletos da conta")
    public ResponseEntity<List<Boleto>> listarPorConta(@PathVariable Long contaIdPagador) {
        return ResponseEntity.ok(boletoService.listarPorConta(contaIdPagador));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar boletos por status")
    public ResponseEntity<List<Boleto>> listarPorStatus(@PathVariable Boleto.StatusBoleto status) {
        return ResponseEntity.ok(boletoService.listarPorStatus(status));
    }

    @java.lang.SuppressWarnings("all")
    public BoletoController(final BoletoService boletoService) {
        this.boletoService = boletoService;
    }
}
