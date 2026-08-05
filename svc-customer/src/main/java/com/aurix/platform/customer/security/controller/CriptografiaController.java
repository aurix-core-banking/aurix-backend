package com.aurix.platform.customer.security.controller;

import com.aurix.platform.shared.crypto.CriptografiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/security/criptografia")
@Tag(name = "Criptografia", description = "Criptografia de dados sensíveis (AES-GCM)")
public class CriptografiaController {
    private final CriptografiaService criptografiaService;

    @PostMapping("/criptografar")
    @Operation(summary = "Criptografar texto sensível")
    public ResponseEntity<Map<String, String>> criptografar(@RequestBody Map<String, String> body) {
        String texto = body.get("texto");
        if (texto == null) {
            return ResponseEntity.badRequest().build();
        }
        String cifrado = criptografiaService.criptografar(texto);
        return ResponseEntity.ok(Map.of("valorCriptografado", cifrado));
    }

    @PostMapping("/descriptografar")
    @Operation(summary = "Descriptografar valor previamente criptografado")
    public ResponseEntity<Map<String, String>> descriptografar(@RequestBody Map<String, String> body) {
        String cifrado = body.get("valorCriptografado");
        if (cifrado == null) {
            return ResponseEntity.badRequest().build();
        }
        String texto = criptografiaService.descriptografar(cifrado);
        return ResponseEntity.ok(Map.of("texto", texto != null ? texto : ""));
    }

    @java.lang.SuppressWarnings("all")
    public CriptografiaController(final CriptografiaService criptografiaService) {
        this.criptografiaService = criptografiaService;
    }
}
