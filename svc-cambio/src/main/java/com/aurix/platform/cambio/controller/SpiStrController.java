package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.entity.TransacaoSPI;
import com.aurix.platform.cambio.entity.TransacaoSTR;
import com.aurix.platform.cambio.service.SpiStrIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cambio/spi-str")
@Tag(name = "SPI/STR", description = "APIs para integração com SPI e STR do BACEN")
@CrossOrigin(origins = "*")
public class SpiStrController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpiStrController.class);
    private final SpiStrIntegrationService spiStrService;

    @PostMapping("/spi/pix")
    @Operation(summary = "Enviar transação PIX via SPI")
    public ResponseEntity<SpiStrIntegrationService.SpiResult> enviarPixSPI(@RequestParam String endToEndId, @RequestParam String ispbOrigem, @RequestParam String ispbDestino, @RequestParam String contaOrigem, @RequestParam String contaDestino, @RequestParam BigDecimal valor, @RequestParam(required = false) String descricao) {
        return ResponseEntity.ok(spiStrService.enviarPixSPI(endToEndId, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, descricao));
    }

    @PostMapping("/str/ted")
    @Operation(summary = "Enviar TED via STR")
    public ResponseEntity<SpiStrIntegrationService.StrResult> enviarTEDSTR(@RequestParam String idTransacao, @RequestParam String ispbOrigem, @RequestParam String ispbDestino, @RequestParam String contaOrigem, @RequestParam String contaDestino, @RequestParam BigDecimal valor, @RequestParam(required = false) LocalDateTime dataAgendamento) {
        return ResponseEntity.ok(spiStrService.enviarTEDSTR(idTransacao, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, dataAgendamento));
    }

    @PostMapping("/str/doc")
    @Operation(summary = "Enviar DOC via STR")
    public ResponseEntity<SpiStrIntegrationService.StrResult> enviarDOCSTR(@RequestParam String idTransacao, @RequestParam String ispbOrigem, @RequestParam String ispbDestino, @RequestParam String contaOrigem, @RequestParam String contaDestino, @RequestParam BigDecimal valor, @RequestParam(required = false) LocalDateTime dataAgendamento) {
        return ResponseEntity.ok(spiStrService.enviarDOCSTR(idTransacao, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, dataAgendamento));
    }

    @GetMapping("/spi/status/{endToEndId}")
    @Operation(summary = "Consultar status de transação SPI")
    public ResponseEntity<TransacaoSPI> consultarStatusSPI(@PathVariable String endToEndId) {
        return ResponseEntity.ok(spiStrService.consultarStatusSPI(endToEndId));
    }

    @GetMapping("/str/status/{numeroControle}")
    @Operation(summary = "Consultar status de transação STR")
    public ResponseEntity<TransacaoSTR> consultarStatusSTR(@PathVariable String numeroControle) {
        return ResponseEntity.ok(spiStrService.consultarStatusSTR(numeroControle));
    }

    @java.lang.SuppressWarnings("all")
    public SpiStrController(final SpiStrIntegrationService spiStrService) {
        this.spiStrService = spiStrService;
    }
}
