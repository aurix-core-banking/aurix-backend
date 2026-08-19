package com.aurix.platform.investimentos.produto.controller;

import com.aurix.platform.investimentos.produto.dto.ProdutoInvestimentoRequest;
import com.aurix.platform.investimentos.produto.dto.ProdutoInvestimentoResponse;
import com.aurix.platform.investimentos.produto.entity.TipoProdutoInvestimento;
import com.aurix.platform.investimentos.produto.service.ProdutoInvestimentoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investimentos/produtos")
@Tag(name = "Produto Investimento")
public class ProdutoInvestimentoController {

    private final ProdutoInvestimentoService produtoService;

    public ProdutoInvestimentoController(ProdutoInvestimentoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoInvestimentoResponse> criar(@Valid @RequestBody ProdutoInvestimentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoInvestimentoResponse>> listar(
            @RequestParam(required = false) TipoProdutoInvestimento tipo) {
        if (tipo != null) {
            return ResponseEntity.ok(produtoService.listarPorTipo(tipo));
        }
        return ResponseEntity.ok(produtoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoInvestimentoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
}
