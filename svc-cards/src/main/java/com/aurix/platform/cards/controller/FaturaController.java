package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.FaturaDetalhadaResponse;
import com.aurix.platform.cards.dto.FaturaResponse;
import com.aurix.platform.cards.dto.PagarFaturaRequest;
import com.aurix.platform.cards.service.FaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/faturas")
@Tag(name = "Faturas Cartao", description = "Fechamento, pagamento e consulta de faturas")
public class FaturaController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FaturaController.class);

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @PostMapping("/fechar")
    @Operation(summary = "Fechar fatura")
    public ResponseEntity<FaturaResponse> fecharFatura(@RequestParam Long cartaoId, @RequestParam Integer mes, @RequestParam Integer ano) {
        return ResponseEntity.ok(faturaService.fecharFatura(cartaoId, mes, ano));
    }

    @PostMapping("/{id}/pagar")
    @Operation(summary = "Pagar fatura")
    public ResponseEntity<FaturaResponse> pagarFatura(@PathVariable Long id, @Valid @RequestBody PagarFaturaRequest request) {
        return ResponseEntity.ok(faturaService.pagarFatura(id, request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar fatura detalhada")
    public ResponseEntity<FaturaDetalhadaResponse> consultarFatura(@PathVariable Long id) {
        return ResponseEntity.ok(faturaService.consultarFatura(id));
    }

    @GetMapping
    @Operation(summary = "Listar faturas de um cartao")
    public ResponseEntity<List<FaturaResponse>> listarFaturas(@RequestParam Long cartaoId) {
        return ResponseEntity.ok(faturaService.listarFaturas(cartaoId));
    }
}
