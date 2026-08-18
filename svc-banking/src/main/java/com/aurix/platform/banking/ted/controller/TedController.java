package com.aurix.platform.banking.ted.controller;

import com.aurix.platform.banking.ted.dto.TedRequest;
import com.aurix.platform.banking.ted.dto.TedResponse;
import com.aurix.platform.banking.ted.service.TedService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/ted")
public class TedController {

    private static final Logger log = LoggerFactory.getLogger(TedController.class);
    private final TedService tedService;

    public TedController(TedService tedService) {
        this.tedService = tedService;
    }

    @PostMapping
    public ResponseEntity<TedResponse> criarTed(@Valid @RequestBody TedRequest request) {
        log.info("Requisicao de TED: contaOrigem={}, valor={}", request.getContaOrigemId(), request.getValor());
        TedResponse response = tedService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TedResponse> consultarTed(@PathVariable Long id) {
        log.info("Consultando TED: id={}", id);
        TedResponse response = tedService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transferencias")
    public ResponseEntity<List<TedResponse>> listarTransferencias(
            @RequestParam(required = false) Long contaOrigemId) {
        log.info("Listando transferencias TED: contaOrigem={}", contaOrigemId);
        List<TedResponse> response = tedService.listarTransferencias(contaOrigemId);
        return ResponseEntity.ok(response);
    }
}
