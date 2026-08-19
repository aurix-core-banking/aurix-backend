package com.aurix.platform.cards.virtual.controller;

import com.aurix.platform.cards.virtual.service.CartaoVirtualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes/virtual")
@Tag(name = "Cartões Virtuais", description = "APIs para gestão de cartões virtuais")
@CrossOrigin(origins = "*")
public class CartaoVirtualController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoVirtualController.class);
    private final CartaoVirtualService cartaoVirtualService;

    @java.lang.SuppressWarnings("all")
    public CartaoVirtualController(final CartaoVirtualService cartaoVirtualService) {
        this.cartaoVirtualService = cartaoVirtualService;
    }

    @PostMapping
    @Operation(summary = "Criar cartão virtual vinculado a um cartão físico")
    public ResponseEntity<Map<String, Object>> criarCartaoVirtual(
            @RequestParam Long cartaoFisicoId,
            @RequestParam(defaultValue = "500.00") java.math.BigDecimal limite,
            @RequestParam(defaultValue = "12") Integer validadeMeses) {
        return ResponseEntity.ok(cartaoVirtualService.criar(cartaoFisicoId, limite, validadeMeses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar cartão virtual por ID")
    public ResponseEntity<Map<String, Object>> consultarCartaoVirtual(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoVirtualService.consultar(id));
    }

    @PutMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear cartão virtual")
    public ResponseEntity<Map<String, Object>> bloquearCartaoVirtual(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoVirtualService.bloquear(id));
    }

    @PutMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloquear cartão virtual")
    public ResponseEntity<Map<String, Object>> desbloquearCartaoVirtual(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoVirtualService.desbloquear(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar cartão virtual")
    public ResponseEntity<Map<String, Object>> cancelarCartaoVirtual(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoVirtualService.cancelar(id));
    }
}
