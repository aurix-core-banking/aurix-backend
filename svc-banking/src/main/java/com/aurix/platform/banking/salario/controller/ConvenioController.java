package com.aurix.platform.banking.salario.controller;

import com.aurix.platform.banking.salario.dto.ConvenioRequest;
import com.aurix.platform.banking.salario.dto.ConvenioResponse;
import com.aurix.platform.banking.salario.service.ConvenioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salario/convenios")
public class ConvenioController {

    private final ConvenioService convenioService;

    public ConvenioController(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @PostMapping
    public ResponseEntity<ConvenioResponse> cadastrar(@Valid @RequestBody ConvenioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convenioService.cadastrar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvenioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(convenioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvenioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ConvenioRequest request) {
        return ResponseEntity.ok(convenioService.atualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ConvenioResponse>> listarAtivos() {
        return ResponseEntity.ok(convenioService.listarAtivos());
    }
}
