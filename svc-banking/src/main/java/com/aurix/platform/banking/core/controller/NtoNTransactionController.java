package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.shared.repository.TransactionLegRepository;
import com.aurix.platform.banking.core.service.NtoNTransactionService;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.entity.TransactionLeg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes/n-n")
@Tag(name = "Transações N:N", description = "API para transações multi-perna (múltiplos débitos/créditos)")
@CrossOrigin(origins = "*")
public class NtoNTransactionController {

    private final NtoNTransactionService ntoNTransactionService;
    private final TransactionLegRepository legRepository;
    private final ControleSaldoRepository controleSaldoRepository;

    public NtoNTransactionController(NtoNTransactionService ntoNTransactionService,
                                     TransactionLegRepository legRepository,
                                     ControleSaldoRepository controleSaldoRepository) {
        this.ntoNTransactionService = ntoNTransactionService;
        this.legRepository = legRepository;
        this.controleSaldoRepository = controleSaldoRepository;
    }

    @PostMapping
    @Operation(summary = "Criar transação N:N", description = "Registra uma transação com múltiplas fontes e/ou múltiplos destinos")
    public ResponseEntity<TransacaoDTO> createNtoN(@Valid @RequestBody NtoNRequest request) {
        NtoNTransactionService.NtoNTransactionRequest.LegData[] sources = request.sources().stream()
                .map(l -> new NtoNTransactionService.NtoNTransactionRequest.LegData(
                        l.account(), l.amount(), l.currency(), l.description()))
                .toArray(NtoNTransactionService.NtoNTransactionRequest.LegData[]::new);

        NtoNTransactionService.NtoNTransactionRequest.LegData[] destinations = request.destinations().stream()
                .map(l -> new NtoNTransactionService.NtoNTransactionRequest.LegData(
                        l.account(), l.amount(), l.currency(), l.description()))
                .toArray(NtoNTransactionService.NtoNTransactionRequest.LegData[]::new);

        NtoNTransactionService.NtoNTransactionRequest svcRequest = new NtoNTransactionService.NtoNTransactionRequest(
                List.of(sources), List.of(destinations),
                request.totalAmount(), request.tipoTransacao(),
                request.description(), request.currency(), request.metadata());

        Transacao transaction = ntoNTransactionService.createNtoNTransaction(svcRequest);
        TransacaoDTO dto = TransacaoDTO.fromEntity(transaction);
        populateBalanceInfo(dto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    private void populateBalanceInfo(TransacaoDTO dto, NtoNRequest request) {
        if (!request.sources().isEmpty()) {
            Long srcId = request.sources().get(0).account().getId();
            controleSaldoRepository.findByContaId(srcId).ifPresent(cs -> {
                dto.setSaldoPosteriorOrigem(cs.getSaldoDisponivel());
                BigDecimal valor = request.totalAmount() != null ? request.totalAmount() : BigDecimal.ZERO;
                dto.setSaldoAnteriorOrigem(cs.getSaldoDisponivel().add(valor));
            });
        }
        if (!request.destinations().isEmpty()) {
            Long dstId = request.destinations().get(0).account().getId();
            controleSaldoRepository.findByContaId(dstId).ifPresent(cs -> {
                dto.setSaldoPosteriorDestino(cs.getSaldoDisponivel());
                BigDecimal valor = request.totalAmount() != null ? request.totalAmount() : BigDecimal.ZERO;
                dto.setSaldoAnteriorDestino(cs.getSaldoDisponivel().subtract(valor));
            });
        }
    }

    @GetMapping("/{transactionId}/legs")
    @Operation(summary = "Listar pernas de uma transação")
    public ResponseEntity<List<TransactionLeg>> getLegs(@PathVariable Long transactionId) {
        List<TransactionLeg> legs = legRepository.findByTransactionId(transactionId);
        return ResponseEntity.ok(legs);
    }

    public record NtoNRequest(
            @NotEmpty List<LegRequest> sources,
            @NotEmpty List<LegRequest> destinations,
            @NotNull @Positive BigDecimal totalAmount,
            @NotNull Transacao.TipoTransacao tipoTransacao,
            String description,
            String currency,
            Map<String, String> metadata
    ) {
        public NtoNRequest {
            currency = currency != null ? currency : "BRL";
        }
    }

    public record LegRequest(
            @NotNull Conta account,
            @NotNull @Positive BigDecimal amount,
            String currency,
            String description
    ) {
        public LegRequest {
            currency = currency != null ? currency : "BRL";
        }
    }
}
