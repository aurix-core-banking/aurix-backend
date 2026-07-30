package com.aurix.platform.banking.poupanca.controller;

import com.aurix.platform.banking.poupanca.service.AniversarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poupanca/aniversario")
@Tag(name = "Aniversario Poupanca", description = "Processamento de aniversario e rendimento TR")
public class AniversarioController {

    private final AniversarioService aniversarioService;

    public AniversarioController(AniversarioService aniversarioService) {
        this.aniversarioService = aniversarioService;
    }

    @PostMapping("/processar")
    @Operation(summary = "Processar aniversarios do dia")
    public ResponseEntity<Map<String, Integer>> processar() {
        int processadas = aniversarioService.processarAniversarios();
        return ResponseEntity.ok(Map.of("contasProcessadas", processadas));
    }

    @GetMapping("/proximo")
    @Operation(summary = "Estimar proximo rendimento (TR atual)")
    public ResponseEntity<Map<String, BigDecimal>> proximoRendimento() {
        BigDecimal tr = aniversarioService.estimarProximoRendimento();
        return ResponseEntity.ok(Map.of("trDiaria", tr));
    }
}
