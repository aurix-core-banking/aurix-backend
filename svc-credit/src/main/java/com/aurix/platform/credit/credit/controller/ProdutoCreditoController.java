package com.aurix.platform.credit.credit.controller;

import com.aurix.platform.credit.credit.entity.ProdutoCredito;
import com.aurix.platform.credit.credit.service.ProdutoCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/credit/produtos")
@Tag(name = "Produtos de Credito", description = "Produtos de credito configuráveis (taxa, prazo, tipo)")
public class ProdutoCreditoController {
    private final ProdutoCreditoService produtoCreditoService;

    @GetMapping
    @Operation(summary = "Listar produtos ativos")
    public ResponseEntity<List<ProdutoCredito>> listarAtivos() {
        return ResponseEntity.ok(produtoCreditoService.listarAtivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoCredito> buscarPorId(@PathVariable Long id) {
        return produtoCreditoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar produto por codigo")
    public ResponseEntity<ProdutoCredito> buscarPorCodigo(@PathVariable String codigo) {
        return produtoCreditoService.buscarPorCodigo(codigo).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar produtos por tipo")
    public ResponseEntity<List<ProdutoCredito>> listarPorTipo(@PathVariable ProdutoCredito.TipoCredito tipo) {
        return ResponseEntity.ok(produtoCreditoService.listarPorTipo(tipo));
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCreditoController(final ProdutoCreditoService produtoCreditoService) {
        this.produtoCreditoService = produtoCreditoService;
    }
}
