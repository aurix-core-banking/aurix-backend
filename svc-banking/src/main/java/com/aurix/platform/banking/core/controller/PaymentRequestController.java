package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.PaymentRequestDTO;
import com.aurix.platform.banking.core.service.PaymentRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-requests")
@Tag(name = "Payment Requests", description = "Solicitações de pagamento P2P com token")
@CrossOrigin(origins = "*")
public class PaymentRequestController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRequestController.class);
    private final PaymentRequestService service;

    public PaymentRequestController(final PaymentRequestService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar solicitação de pagamento")
    public ResponseEntity<PaymentRequestDTO> criar(@Valid @RequestBody PaymentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/token/{token}")
    @Operation(summary = "Buscar por token")
    public ResponseEntity<PaymentRequestDTO> buscarPorToken(@PathVariable String token) {
        return service.buscarPorToken(token)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<PaymentRequestDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/requerente/{requesterId}")
    @Operation(summary = "Listar por requerente")
    public ResponseEntity<List<PaymentRequestDTO>> listarPorRequerente(@PathVariable Long requesterId) {
        return ResponseEntity.ok(service.listarPorRequerente(requesterId));
    }

    @GetMapping("/pagador/{payerId}")
    @Operation(summary = "Listar por pagador")
    public ResponseEntity<List<PaymentRequestDTO>> listarPorPagador(@PathVariable Long payerId) {
        return ResponseEntity.ok(service.listarPorPagador(payerId));
    }

    @PostMapping("/{token}/pagar")
    @Operation(summary = "Pagar solicitação via token")
    public ResponseEntity<PaymentRequestDTO> pagar(@PathVariable String token,
                                                    @RequestParam Long payerId,
                                                    @RequestParam String payerAccountNumber,
                                                    @RequestParam Long payerContaId) {
        return ResponseEntity.ok(service.pagarPorToken(token, payerId, payerAccountNumber, payerContaId));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar solicitação")
    public ResponseEntity<PaymentRequestDTO> cancelar(@PathVariable Long id,
                                                       @RequestParam Long requesterId) {
        return ResponseEntity.ok(service.cancelar(id, requesterId));
    }
}
