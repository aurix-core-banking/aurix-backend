package com.aurix.platform.seguros.produto.controller;

import com.aurix.platform.seguros.produto.dto.ProdutoSeguroRequest;
import com.aurix.platform.seguros.produto.dto.ProdutoSeguroResponse;
import com.aurix.platform.seguros.produto.entity.TipoSeguro;
import com.aurix.platform.seguros.produto.service.ProdutoSeguroService;
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
@RequestMapping("/api/seguros/produtos")
@Tag(name = "Produto Seguro")
public class ProdutoSeguroController {

    private final ProdutoSeguroService produtoService;

    public ProdutoSeguroController(ProdutoSeguroService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoSeguroResponse> criar(@Valid @RequestBody ProdutoSeguroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoSeguroResponse>> listar(
            @RequestParam(required = false) TipoSeguro tipo) {
        if (tipo != null) {
            return ResponseEntity.ok(produtoService.listarPorTipo(tipo));
        }
        return ResponseEntity.ok(produtoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoSeguroResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
}
