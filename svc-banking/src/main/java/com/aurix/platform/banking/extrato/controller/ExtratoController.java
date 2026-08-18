package com.aurix.platform.banking.extrato.controller;

import com.aurix.platform.banking.extrato.dto.ExtratoRequest;
import com.aurix.platform.banking.extrato.dto.ExtratoResponse;
import com.aurix.platform.banking.extrato.service.ExtratoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/extrato")
public class ExtratoController {

    private static final Logger log = LoggerFactory.getLogger(ExtratoController.class);
    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

    @GetMapping("/{contaId}")
    public ResponseEntity<ExtratoResponse> gerarExtrato(
            @PathVariable Long contaId,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam(required = false) String tipoMovimentacao,
            @RequestParam(required = false) String descricao) {
        log.info("Gerando extrato: contaId={}, de {} a {}", contaId, dataInicio, dataFim);

        ExtratoRequest request = new ExtratoRequest();
        request.setContaId(contaId);
        request.setDataInicio(dataInicio);
        request.setDataFim(dataFim);
        request.setTipoMovimentacao(tipoMovimentacao);
        request.setDescricao(descricao);

        ExtratoResponse response = extratoService.gerarExtrato(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExtratoResponse> gerarExtratoPost(@Valid @RequestBody ExtratoRequest request) {
        log.info("Gerando extrato via POST: contaId={}, de {} a {}",
            request.getContaId(), request.getDataInicio(), request.getDataFim());
        ExtratoResponse response = extratoService.gerarExtrato(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contaId}/pdf")
    public ResponseEntity<byte[]> gerarPdf(
            @PathVariable Long contaId,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        log.info("Gerando PDF do extrato: contaId={}, de {} a {}", contaId, dataInicio, dataFim);

        byte[] pdf = extratoService.gerarPdf(contaId, dataInicio, dataFim);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=extrato_" + contaId + ".txt")
            .contentType(MediaType.TEXT_PLAIN)
            .contentLength(pdf.length)
            .body(pdf);
    }

    @GetMapping("/{contaId}/extratos")
    public ResponseEntity<ExtratoResponse> buscarExtratoPorId(@PathVariable Long contaId) {
        log.info("Buscando extrato por ID: {}", contaId);
        ExtratoResponse response = extratoService.buscarPorId(contaId);
        return ResponseEntity.ok(response);
    }
}
