package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.request.AtualizarTaxaRequest;
import com.aurix.platform.credit.financiamento.dto.response.TaxasResponse;
import com.aurix.platform.credit.financiamento.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financiamento/admin")
@Tag(name = "Admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/taxas")
    public ResponseEntity<TaxasResponse> listarTaxas() {
        return ResponseEntity.ok(adminService.listarTaxas());
    }

    @PutMapping("/taxas")
    public ResponseEntity<TaxasResponse> atualizarTaxas(@Valid @RequestBody AtualizarTaxaRequest request) {
        return ResponseEntity.ok(adminService.atualizarTaxas(request));
    }
}
