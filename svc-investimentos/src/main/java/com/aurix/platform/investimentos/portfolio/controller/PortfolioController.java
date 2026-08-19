package com.aurix.platform.investimentos.portfolio.controller;

import com.aurix.platform.investimentos.portfolio.dto.PortfolioResponse;
import com.aurix.platform.investimentos.portfolio.dto.RentabilidadeResponse;
import com.aurix.platform.investimentos.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investimentos/portfolio")
@Tag(name = "Portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<PortfolioResponse> buscarPortfolio(@PathVariable Long clienteId) {
        return ResponseEntity.ok(portfolioService.buscarPortfolio(clienteId));
    }

    @GetMapping("/{clienteId}/rentabilidade")
    public ResponseEntity<RentabilidadeResponse> buscarRentabilidade(@PathVariable Long clienteId) {
        return ResponseEntity.ok(portfolioService.buscarRentabilidade(clienteId));
    }
}
