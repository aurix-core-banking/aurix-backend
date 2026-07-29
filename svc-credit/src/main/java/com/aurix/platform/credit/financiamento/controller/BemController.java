package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.response.BemResponse;
import com.aurix.platform.credit.financiamento.repository.BemFinanciadoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financiamento/bens")
@Tag(name = "Bem")
public class BemController {

    private final BemFinanciadoRepository bemRepository;

    public BemController(BemFinanciadoRepository bemRepository) {
        this.bemRepository = bemRepository;
    }

    @GetMapping("/{contratoId}")
    public ResponseEntity<List<BemResponse>> listarBens(@PathVariable Long contratoId) {
        var bens = bemRepository.findByContratoId(contratoId).stream()
            .map(b -> new BemResponse(b.getId(), b.getTipo().name(), b.getDescricao(),
                b.getValorAvaliacao(), b.getChassi(), b.getPlaca(),
                b.getMatriculaRGI(), b.getRegistroGarantia()))
            .toList();
        return ResponseEntity.ok(bens);
    }
}
