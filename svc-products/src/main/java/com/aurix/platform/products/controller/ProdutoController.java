package com.aurix.platform.products.controller;

import com.aurix.platform.products.dto.AvaliacaoElegibilidadeResponse;
import com.aurix.platform.products.dto.PerfilClienteRequest;
import com.aurix.platform.products.dto.ProdutoRequest;
import com.aurix.platform.products.dto.ProdutoResponse;
import com.aurix.platform.products.dto.RegraElegibilidadeRequest;
import com.aurix.platform.products.dto.RegraElegibilidadeResponse;
import com.aurix.platform.products.dto.TarifaProdutoRequest;
import com.aurix.platform.products.dto.TarifaProdutoResponse;
import com.aurix.platform.products.dto.VersaoProdutoResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.service.ElegibilidadeService;
import com.aurix.platform.products.service.ProdutoService;
import com.aurix.platform.products.service.TarifaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "products", description = "Catálogo de produtos financeiros (svc-products)")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ElegibilidadeService elegibilidadeService;
    private final TarifaProdutoService tarifaService;

    public ProdutoController(ProdutoService produtoService,
                             ElegibilidadeService elegibilidadeService,
                             TarifaProdutoService tarifaService) {
        this.produtoService = produtoService;
        this.elegibilidadeService = elegibilidadeService;
        this.tarifaService = tarifaService;
    }

    @PostMapping("/produtos")
    @Operation(summary = "Criar produto financeiro no catálogo")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ProdutoResponse.de(produtoService.criar(request)));
    }

    @GetMapping("/produtos")
    @Operation(summary = "Listar produtos do catálogo")
    public ResponseEntity<List<ProdutoResponse>> listar(
            @RequestParam(required = false) Produto.TipoProduto tipo,
            @RequestParam(required = false) Produto.StatusProduto status,
            @RequestParam(required = false) Boolean ativo) {
        return ResponseEntity.ok(produtoService.listar(tipo, status, ativo).stream()
            .map(ProdutoResponse::de)
            .toList());
    }

    @GetMapping("/produtos/{id}")
    @Operation(summary = "Buscar produto por id")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ProdutoResponse.de(produtoService.buscarEntidade(id)));
    }

    @GetMapping("/produtos/codigo/{codigo}")
    @Operation(summary = "Buscar produto por código")
    public ResponseEntity<ProdutoResponse> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(ProdutoResponse.de(produtoService.buscarPorCodigo(codigo)));
    }

    @PutMapping("/produtos/{id}")
    @Operation(summary = "Atualizar produto criando nova versão")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ProdutoRequest request,
                                                     @RequestParam(required = false) String autor,
                                                     @RequestParam(required = false) String changelog) {
        return ResponseEntity.ok(ProdutoResponse.de(
            produtoService.atualizar(id, request, autor, changelog)));
    }

    @PostMapping("/produtos/{id}/publicar")
    @Operation(summary = "Publicar produto (status ATIVO)")
    public ResponseEntity<ProdutoResponse> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(ProdutoResponse.de(produtoService.publicar(id)));
    }

    @DeleteMapping("/produtos/{id}")
    @Operation(summary = "Descontinuar produto")
    public ResponseEntity<Void> descontinuar(@PathVariable Long id) {
        produtoService.descontinuar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/produtos/{id}/versoes")
    @Operation(summary = "Listar histórico de versões do produto")
    public ResponseEntity<List<VersaoProdutoResponse>> versoes(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarVersoes(id));
    }

    @PostMapping("/produtos/{id}/elegibilidade")
    @Operation(summary = "Adicionar regra de elegibilidade ao produto")
    public ResponseEntity<RegraElegibilidadeResponse> adicionarRegra(@PathVariable Long id,
                                                                     @Valid @RequestBody RegraElegibilidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            RegraElegibilidadeResponse.de(elegibilidadeService.adicionarRegra(id, request)));
    }

    @GetMapping("/produtos/{id}/elegibilidade")
    @Operation(summary = "Listar regras de elegibilidade do produto")
    public ResponseEntity<List<RegraElegibilidadeResponse>> listarRegras(@PathVariable Long id) {
        return ResponseEntity.ok(elegibilidadeService.listarRegras(id));
    }

    @PostMapping("/produtos/{id}/elegibilidade/avaliar")
    @Operation(summary = "Avaliar elegibilidade do cliente para o produto")
    public ResponseEntity<AvaliacaoElegibilidadeResponse> avaliarElegibilidade(@PathVariable Long id,
                                                                               @RequestBody PerfilClienteRequest perfil) {
        return ResponseEntity.ok(elegibilidadeService.avaliar(id, perfil));
    }

    @DeleteMapping("/produtos/{produtoId}/elegibilidade/{regraId}")
    @Operation(summary = "Remover regra de elegibilidade do produto")
    public ResponseEntity<Void> removerRegra(@PathVariable Long produtoId, @PathVariable Long regraId) {
        elegibilidadeService.removerRegra(produtoId, regraId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/produtos/{id}/tarifas")
    @Operation(summary = "Adicionar tarifa ao produto")
    public ResponseEntity<TarifaProdutoResponse> adicionarTarifa(@PathVariable Long id,
                                                                 @Valid @RequestBody TarifaProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            TarifaProdutoResponse.de(tarifaService.adicionarTarifa(id, request)));
    }

    @GetMapping("/produtos/{id}/tarifas")
    @Operation(summary = "Listar tarifas do produto")
    public ResponseEntity<List<TarifaProdutoResponse>> listarTarifas(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.listarTarifas(id));
    }

    @DeleteMapping("/produtos/{produtoId}/tarifas/{tarifaId}")
    @Operation(summary = "Remover tarifa do produto")
    public ResponseEntity<Void> removerTarifa(@PathVariable Long produtoId, @PathVariable Long tarifaId) {
        tarifaService.removerTarifa(produtoId, tarifaId);
        return ResponseEntity.noContent().build();
    }
}
