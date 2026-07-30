package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Fatura;
import com.aurix.platform.cards.entity.TransacaoCartao;
import com.aurix.platform.cards.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@Tag(name = "Cartões", description = "APIs para gestão de cartões de crédito e débito")
@CrossOrigin(origins = "*")
public class CartaoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoController.class);
    private final CartaoService cartaoService;

    @PostMapping("/emitir")
    @Operation(summary = "Emitir novo cartão")
    public ResponseEntity<Cartao> emitirCartao(@RequestParam Long contaId, @RequestParam Cartao.TipoCartao tipoCartao, @RequestParam Cartao.BandeiraCartao bandeira, @RequestParam String nomePortador, @RequestParam BigDecimal limiteCredito) {
        return ResponseEntity.ok(cartaoService.emitirCartao(contaId, tipoCartao, bandeira, nomePortador, limiteCredito));
    }

    @PostMapping("/{id}/ativar")
    @Operation(summary = "Ativar cartão")
    public ResponseEntity<Cartao> ativarCartao(@PathVariable Long id, @RequestParam String cvv) {
        return ResponseEntity.ok(cartaoService.ativarCartao(id, cvv));
    }

    @PostMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear cartão")
    public ResponseEntity<Cartao> bloquearCartao(@PathVariable Long id, @RequestParam String motivo) {
        return ResponseEntity.ok(cartaoService.bloquearCartao(id, motivo));
    }

    @PostMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloquear cartão")
    public ResponseEntity<Cartao> desbloquearCartao(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.desbloquearCartao(id));
    }

    @PatchMapping("/{id}/limite")
    @Operation(summary = "Alterar limite do cartão")
    public ResponseEntity<Cartao> alterarLimite(@PathVariable Long id, @RequestParam BigDecimal limite) {
        return ResponseEntity.ok(cartaoService.alterarLimite(id, limite));
    }

    @PostMapping("/{id}/transacao")
    @Operation(summary = "Processar transação no cartão")
    public ResponseEntity<TransacaoCartao> processarTransacao(@PathVariable Long id, @RequestParam BigDecimal valor, @RequestParam String estabelecimento, @RequestParam TransacaoCartao.TipoTransacao tipo) {
        return ResponseEntity.ok(cartaoService.processarTransacao(id, valor, estabelecimento, tipo));
    }

    @PostMapping("/{id}/fatura")
    @Operation(summary = "Gerar fatura do cartão")
    public ResponseEntity<Fatura> gerarFatura(@PathVariable Long id, @RequestParam Integer mes, @RequestParam Integer ano) {
        return ResponseEntity.ok(cartaoService.gerarFatura(id, mes, ano));
    }

    @PostMapping("/fatura/{faturaId}/pagar")
    @Operation(summary = "Pagar fatura")
    public ResponseEntity<Fatura> pagarFatura(@PathVariable Long faturaId, @RequestParam BigDecimal valorPagamento) {
        return ResponseEntity.ok(cartaoService.pagarFatura(faturaId, valorPagamento));
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar cartões da conta")
    public ResponseEntity<List<Cartao>> listarCartoesPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(cartaoService.listarCartoesPorConta(contaId));
    }

    @java.lang.SuppressWarnings("all")
    public CartaoController(final CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }
}
