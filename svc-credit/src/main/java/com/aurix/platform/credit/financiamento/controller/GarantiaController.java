package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.request.GarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.request.LiberarGarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.response.GarantiaResponse;
import com.aurix.platform.credit.financiamento.service.GarantiaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financiamento/garantias")
@Tag(name = "Garantia")
public class GarantiaController {

    private final GarantiaService garantiaService;

    public GarantiaController(GarantiaService garantiaService) {
        this.garantiaService = garantiaService;
    }

    @PostMapping
    public ResponseEntity<GarantiaResponse> registrar(@Valid @RequestBody GarantiaRequest request) {
        var response = garantiaService.registrar(request, 0L, 0L);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/liberar")
    public ResponseEntity<Void> liberar(@PathVariable Long id,
                                        @Valid @RequestBody LiberarGarantiaRequest request) {
        garantiaService.liberar(id, request);
        return ResponseEntity.noContent().build();
    }
}
