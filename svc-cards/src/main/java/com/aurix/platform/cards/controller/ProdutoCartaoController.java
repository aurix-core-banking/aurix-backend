package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.ProdutoCartaoRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoResponse;
import com.aurix.platform.cards.service.ProdutoCartaoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/cards/produtos")
@Tag(name = "Produtos Cartao", description = "Cadastro e consulta de produtos de cartao")
public class ProdutoCartaoController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProdutoCartaoController.class);

    private final ProdutoCartaoService produtoCartaoService;

    public ProdutoCartaoController(ProdutoCartaoService produtoCartaoService) {
        this.produtoCartaoService = produtoCartaoService;
    }

    @PostMapping
    @Operation(summary = "Criar produto cartao")
    public ResponseEntity<ProdutoCartaoResponse> criar(@Valid @RequestBody ProdutoCartaoRequest request) {
        ProdutoCartaoResponse response = produtoCartaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar produtos cartao ativos")
    public ResponseEntity<List<ProdutoCartaoResponse>> listar() {
        return ResponseEntity.ok(produtoCartaoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto cartao por ID")
    public ResponseEntity<ProdutoCartaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoCartaoService.buscarPorId(id));
    }
}
