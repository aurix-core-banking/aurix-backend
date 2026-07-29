package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.dto.CotacaoRequest;
import com.aurix.platform.cambio.dto.CotacaoResponse;
import com.aurix.platform.cambio.service.CotacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cambio/cotacoes")
@Tag(name = "Cotacoes Cambio")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @GetMapping
    public ResponseEntity<List<CotacaoResponse>> listarCotacoes() {
        return ResponseEntity.ok(cotacaoService.listarCotacoes());
    }

    @GetMapping("/{moeda}")
    public ResponseEntity<CotacaoResponse> obterCotacao(@PathVariable String moeda) {
        return ResponseEntity.ok(cotacaoService.obterCotacao(moeda));
    }

    @PostMapping
    public ResponseEntity<CotacaoResponse> atualizarCotacao(@Valid @RequestBody CotacaoRequest request) {
        var response = cotacaoService.atualizarCotacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
