package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.CartaoResponse;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.service.CartaoQueryService;
import com.aurix.platform.cards.service.LimiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/consultas")
@Tag(name = "Consultas Cartao", description = "Consultas de cartoes por status, cliente e ID")
public class CartaoQueryController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoQueryController.class);

    private final CartaoQueryService cartaoQueryService;
    private final LimiteService limiteService;

    public CartaoQueryController(CartaoQueryService cartaoQueryService,
                                  LimiteService limiteService) {
        this.cartaoQueryService = cartaoQueryService;
        this.limiteService = limiteService;
    }

    @GetMapping
    @Operation(summary = "Listar cartoes por status")
    public ResponseEntity<List<CartaoResponse>> listarPorStatus(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(cartaoQueryService.listarPorStatus(status));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar cartoes por cliente")
    public ResponseEntity<List<CartaoResponse>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cartaoQueryService.buscarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cartao por ID")
    public ResponseEntity<CartaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoQueryService.buscarPorId(id));
    }
}
