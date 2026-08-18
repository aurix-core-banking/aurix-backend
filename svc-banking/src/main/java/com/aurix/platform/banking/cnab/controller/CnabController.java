package com.aurix.platform.banking.cnab.controller;

import com.aurix.platform.banking.cnab.dto.CnabRemessaRequest;
import com.aurix.platform.banking.cnab.dto.CnabRemessaResponse;
import com.aurix.platform.banking.cnab.service.CnabService;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cnab")
public class CnabController {

    private static final Logger log = LoggerFactory.getLogger(CnabController.class);
    private final CnabService cnabService;

    public CnabController(CnabService cnabService) {
        this.cnabService = cnabService;
    }

    @PostMapping("/remessa")
    public ResponseEntity<CnabRemessaResponse> gerarRemessa(@Valid @RequestBody CnabRemessaRequest request) {
        log.info("Gerando remessa CNAB: tipo={}", request.getTipo());
        CnabRemessaResponse response = cnabService.gerarRemessa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/retorno")
    public ResponseEntity<CnabRemessaResponse> processarRetorno(
            @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        log.info("Processando retorno CNAB: {}", arquivo.getOriginalFilename());
        CnabRemessaResponse response = cnabService.processarRetorno(
            arquivo.getBytes(), arquivo.getOriginalFilename());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remessas")
    public ResponseEntity<List<CnabRemessaResponse>> listarRemessas() {
        log.info("Listando remessas CNAB");
        List<CnabRemessaResponse> response = cnabService.listarRemessas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remessas/{id}")
    public ResponseEntity<CnabRemessaResponse> buscarRemessa(@PathVariable Long id) {
        log.info("Buscando remessa CNAB: id={}", id);
        CnabRemessaResponse response = cnabService.buscarRemessaPorId(id);
        return ResponseEntity.ok(response);
    }
}
