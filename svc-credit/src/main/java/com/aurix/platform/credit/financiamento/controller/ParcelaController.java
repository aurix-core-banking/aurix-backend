package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.request.PagarParcelaRequest;
import com.aurix.platform.credit.financiamento.dto.response.ParcelaResponse;
import com.aurix.platform.credit.financiamento.service.ParcelaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financiamento/contratos/{contratoId}/parcelas")
@Tag(name = "Parcela")
public class ParcelaController {

    private final ParcelaService parcelaService;

    public ParcelaController(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @GetMapping
    public ResponseEntity<List<ParcelaResponse>> listarParcelas(@PathVariable Long contratoId) {
        return ResponseEntity.ok(parcelaService.listarParcelas(contratoId));
    }

    @PostMapping("/pagar")
    public ResponseEntity<Void> pagarParcela(@PathVariable Long contratoId,
                                             @Valid @RequestBody PagarParcelaRequest request) {
        parcelaService.pagarParcela(contratoId, request);
        return ResponseEntity.ok().build();
    }
}
