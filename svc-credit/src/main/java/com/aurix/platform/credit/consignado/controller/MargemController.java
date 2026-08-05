package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.consignado.dto.MargemResponse;
import com.aurix.platform.credit.consignado.service.MargemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consignado/margem")
@Tag(name = "Margem Consignavel")
public class MargemController {

    private final MargemService margemService;

    public MargemController(MargemService margemService) {
        this.margemService = margemService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<MargemResponse> consultarMargem(@PathVariable Long clienteId) {
        return ResponseEntity.ok(margemService.consultarMargem(clienteId));
    }
}
