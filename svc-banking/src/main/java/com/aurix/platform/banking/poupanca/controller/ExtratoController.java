package com.aurix.platform.banking.poupanca.controller;

import com.aurix.platform.banking.poupanca.service.ExtratoPdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poupanca/extrato")
@Tag(name = "Extrato Poupanca", description = "Download de extrato PDF")
public class ExtratoController {

    private final ExtratoPdfService extratoPdfService;

    public ExtratoController(ExtratoPdfService extratoPdfService) {
        this.extratoPdfService = extratoPdfService;
    }

    @GetMapping("/conta/{contaId}/pdf")
    @Operation(summary = "Download extrato PDF (12 meses)")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long contaId) {
        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusMonths(12);
        byte[] pdf = extratoPdfService.gerarPdf(contaId, inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "extrato-poupanca-" + contaId + ".txt");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
