package com.aurix.platform.customer.onboarding.controller;

import com.aurix.platform.customer.onboarding.service.CepService;
import com.aurix.platform.customer.onboarding.service.ReceitaFederalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding/integracoes")
@Tag(name = "Integração Terceiros", description = "CEP (Correios), Receita Federal - stubs")
public class IntegracaoTerceirosController {
    private final CepService cepService;
    private final ReceitaFederalService receitaFederalService;

    @GetMapping("/cep/{cep}")
    @Operation(summary = "Consultar CEP")
    public ResponseEntity<CepService.ResultadoCep> consultarCep(@PathVariable String cep) {
        return ResponseEntity.ok(cepService.consultar(cep));
    }

    @GetMapping("/receita/cnpj/{cnpj}")
    @Operation(summary = "Consultar CNPJ na Receita Federal")
    public ResponseEntity<ReceitaFederalService.ResultadoReceita> consultarCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(receitaFederalService.consultarCnpj(cnpj));
    }

    @java.lang.SuppressWarnings("all")
    public IntegracaoTerceirosController(final CepService cepService, final ReceitaFederalService receitaFederalService) {
        this.cepService = cepService;
        this.receitaFederalService = receitaFederalService;
    }
}
