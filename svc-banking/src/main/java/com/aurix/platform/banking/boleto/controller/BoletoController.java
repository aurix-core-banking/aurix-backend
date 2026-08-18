package com.aurix.platform.banking.boleto.controller;

import com.aurix.platform.banking.boleto.dto.BoletoRequest;
import com.aurix.platform.banking.boleto.dto.BoletoResponse;
import com.aurix.platform.banking.boleto.service.BoletoService;
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
@RequestMapping("/api/boletos")
public class BoletoController {

    private static final Logger log = LoggerFactory.getLogger(BoletoController.class);
    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<BoletoResponse> registrarBoleto(@Valid @RequestBody BoletoRequest request) {
        log.info("Registrando boleto: contaId={}, valor={}", request.getContaId(), request.getValor());
        BoletoResponse response = boletoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoletoResponse> consultarBoleto(@PathVariable Long id) {
        log.info("Consultando boleto: id={}", id);
        BoletoResponse response = boletoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<BoletoResponse> pagarBoleto(
            @PathVariable Long id,
            @RequestParam Long contaPagadorId) {
        log.info("Pagando boleto: id={}, contaPagador={}", id, contaPagadorId);
        BoletoResponse response = boletoService.registrarPagamento(id, contaPagadorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<BoletoResponse>> listarVencidos() {
        log.info("Listando boletos vencidos");
        List<BoletoResponse> response = boletoService.listarVencidos();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/baixar")
    public ResponseEntity<Void> baixarBoleto(@PathVariable Long id) {
        log.info("Baixando boleto: id={}", id);
        boletoService.baixarAutomaticamente(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/protestar")
    public ResponseEntity<Void> protestarBoleto(@PathVariable Long id) {
        log.info("Protestando boleto: id={}", id);
        boletoService.protestar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarBoleto(@PathVariable Long id) {
        log.info("Cancelando boleto: id={}", id);
        boletoService.cancelar(id);
        return ResponseEntity.ok().build();
    }
}
