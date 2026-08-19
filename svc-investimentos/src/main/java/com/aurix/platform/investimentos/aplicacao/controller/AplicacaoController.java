package com.aurix.platform.investimentos.aplicacao.controller;

import com.aurix.platform.investimentos.aplicacao.dto.AplicacaoRequest;
import com.aurix.platform.investimentos.aplicacao.dto.AplicacaoResponse;
import com.aurix.platform.investimentos.aplicacao.service.AplicacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investimentos/aplicacoes")
@Tag(name = "Aplicação")
public class AplicacaoController {

    private final AplicacaoService aplicacaoService;

    public AplicacaoController(AplicacaoService aplicacaoService) {
        this.aplicacaoService = aplicacaoService;
    }

    @PostMapping
    public ResponseEntity<AplicacaoResponse> aplicar(@Valid @RequestBody AplicacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aplicacaoService.aplicar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(aplicacaoService.buscarPorId(id));
    }

    @PostMapping("/{id}/resgatar")
    public ResponseEntity<AplicacaoResponse> resgatar(@PathVariable Long id) {
        return ResponseEntity.ok(aplicacaoService.resgatar(id));
    }
}
