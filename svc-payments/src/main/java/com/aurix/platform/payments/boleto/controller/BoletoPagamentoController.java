package com.aurix.platform.payments.boleto.controller;

import com.aurix.platform.payments.boleto.service.BoletoPagamentoService;
import com.aurix.platform.shared.dto.PagamentoBoletoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para gestão de boletos registrados.
 */
@RestController
@RequestMapping("/api/pagamentos/boleto")
@Tag(name = "Boleto Registrado", description = "API para registro, baixa e protesto de boletos do Aurix")
public class BoletoPagamentoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BoletoPagamentoController.class);
    private final BoletoPagamentoService boletoPagamentoService;

    /**
     * Registra um novo boleto na câmara (CEPEL).
     */
    @PostMapping
    @Operation(summary = "Registrar boleto", description = "Registra um novo boleto de cobrança na câmara de compensação")
    public ResponseEntity<PagamentoBoletoDTO> registrarBoleto(@Valid @RequestBody PagamentoBoletoDTO dto) {
        log.info("Recebida solicitação para registrar boleto para conta cedente: {}", dto.getContaCedenteId());
        PagamentoBoletoDTO boletoCriado = boletoPagamentoService.registrarBoleto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletoCriado);
    }

    /**
     * Realiza a baixa de um boleto.
     */
    @PostMapping("/{id}/baixar")
    @Operation(summary = "Baixar boleto", description = "Registra a baixa de um boleto (pagamento ou determinação do cedente)")
    public ResponseEntity<Void> baixarBoleto(
            @Parameter(description = "ID do boleto") @PathVariable Long id,
            @RequestParam @Parameter(description = "Valor pago") BigDecimal valorPago) {
        log.info("Recebida solicitação para baixar boleto ID: {}", id);
        boletoPagamentoService.baixarBoleto(id, valorPago);
        return ResponseEntity.noContent().build();
    }

    /**
     * Registra protesto de um boleto vencido.
     */
    @PostMapping("/{id}/protestar")
    @Operation(summary = "Protestar boleto", description = "Registra protesto de boleto vencido na câmara de compensação")
    public ResponseEntity<Void> protestarBoleto(
            @Parameter(description = "ID do boleto") @PathVariable Long id) {
        log.info("Recebida solicitação para protestar boleto ID: {}", id);
        boletoPagamentoService.protestarBoleto(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca boleto por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar boleto por ID", description = "Busca um boleto registrado pelo ID")
    public ResponseEntity<PagamentoBoletoDTO> buscarPorId(
            @Parameter(description = "ID do boleto") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar boleto ID: {}", id);
        PagamentoBoletoDTO boleto = boletoPagamentoService.buscarPorId(id);
        return ResponseEntity.ok(boleto);
    }

    /**
     * Lista boletos por conta cedente.
     */
    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar boletos por conta", description = "Lista todos os boletos de uma conta cedente")
    public ResponseEntity<List<PagamentoBoletoDTO>> listarPorConta(
            @Parameter(description = "ID da conta cedente") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar boletos da conta ID: {}", contaId);
        List<PagamentoBoletoDTO> boletos = boletoPagamentoService.listarPorConta(contaId);
        return ResponseEntity.ok(boletos);
    }

    /**
     * Lista boletos vencidos não pagos.
     */
    @GetMapping("/vencidos")
    @Operation(summary = "Listar boletos vencidos", description = "Lista boletos registrados que já venceram e não foram pagos")
    public ResponseEntity<List<PagamentoBoletoDTO>> listarVencidos() {
        log.info("Recebida solicitação para listar boletos vencidos");
        List<PagamentoBoletoDTO> boletos = boletoPagamentoService.listarVencidos();
        return ResponseEntity.ok(boletos);
    }

    @java.lang.SuppressWarnings("all")
    public BoletoPagamentoController(final BoletoPagamentoService boletoPagamentoService) {
        this.boletoPagamentoService = boletoPagamentoService;
    }
}
