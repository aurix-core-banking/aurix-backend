package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.consignado.dto.ConvenioRequest;
import com.aurix.platform.credit.consignado.dto.ConvenioResponse;
import com.aurix.platform.credit.consignado.service.ConvenioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consignado/convenios")
@Tag(name = "Convenios Consignado")
public class ConvenioController {

    private final ConvenioService convenioService;

    public ConvenioController(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @GetMapping
    public ResponseEntity<List<ConvenioResponse>> listarConvenios() {
        return ResponseEntity.ok(convenioService.listarConvenios());
    }

    @PostMapping
    public ResponseEntity<ConvenioResponse> criarConvenio(@Valid @RequestBody ConvenioRequest request) {
        var response = convenioService.criarConvenio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
