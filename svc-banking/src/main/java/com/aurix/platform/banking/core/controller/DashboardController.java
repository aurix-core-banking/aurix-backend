package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.DashboardDTO;
import com.aurix.platform.banking.core.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Dashboard operacional com métricas de settlement, reconciliação e webhooks")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Obter dashboard", description = "Retorna métricas consolidadas do sistema")
    public ResponseEntity<DashboardDTO> obterDashboard() {
        return ResponseEntity.ok(dashboardService.obterDashboard());
    }
}
