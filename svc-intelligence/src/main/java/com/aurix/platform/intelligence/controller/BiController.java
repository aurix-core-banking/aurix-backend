package com.aurix.platform.intelligence.controller;

import com.aurix.platform.intelligence.service.BiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/intelligence/bi")
@Tag(name = "Business Intelligence", description = "KPIs e dashboards")
public class BiController {
    private final BiService biService;

    public BiController(BiService biService) {
        this.biService = biService;
    }

    @GetMapping("/kpis")
    @Operation(summary = "KPIs consolidados")
    public ResponseEntity<Map<String, Object>> kpis() {
        return ResponseEntity.ok(biService.obterKpis());
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Resumo dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        return ResponseEntity.ok(biService.obterDashboard());
    }
}
