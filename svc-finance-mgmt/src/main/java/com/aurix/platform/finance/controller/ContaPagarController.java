package com.aurix.platform.finance.controller;

import com.aurix.platform.finance.entity.ContaPagar;
import com.aurix.platform.finance.service.ContaPagarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/finance/contas-pagar")
@Tag(name = "Contas a Pagar", description = "APIs para gestão de contas a pagar")
public class ContaPagarController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaPagarController.class);
    private final ContaPagarService contaPagarService;

    @PostMapping
    @Operation(summary = "Criar conta a pagar", description = "Cria uma nova conta a pagar")
    public ResponseEntity<ContaPagar> criarContaPagar(@RequestBody ContaPagar contaPagar) {
        log.info("Criando conta a pagar: {}", contaPagar.getNumeroDocumento());
        ContaPagar contaCriada = contaPagarService.criarContaPagar(contaPagar);
        return ResponseEntity.ok(contaCriada);
    }

    @PostMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar conta a pagar", description = "Aprova uma conta a pagar para pagamento")
    public ResponseEntity<ContaPagar> aprovarContaPagar(@Parameter(description = "ID da conta a pagar") @PathVariable Long id, @Parameter(description = "Usuário que está aprovando") @RequestParam String usuarioAprovacao) {
        log.info("Aprovando conta a pagar: {}", id);
        ContaPagar contaAprovada = contaPagarService.aprovarContaPagar(id, usuarioAprovacao);
        return ResponseEntity.ok(contaAprovada);
    }

    @PostMapping("/{id}/pagar")
    @Operation(summary = "Registrar pagamento", description = "Registra o pagamento de uma conta a pagar")
    public ResponseEntity<ContaPagar> pagarConta(@Parameter(description = "ID da conta a pagar") @PathVariable Long id, @Parameter(description = "Valor pago") @RequestParam BigDecimal valorPago, @Parameter(description = "Data do pagamento") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento) {
        log.info("Registrando pagamento da conta: {} - Valor: {}", id, valorPago);
        ContaPagar contaPaga = contaPagarService.pagarConta(id, valorPago, dataPagamento);
        return ResponseEntity.ok(contaPaga);
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar conta a pagar", description = "Cancela uma conta a pagar")
    public ResponseEntity<ContaPagar> cancelarConta(@Parameter(description = "ID da conta a pagar") @PathVariable Long id, @Parameter(description = "Motivo do cancelamento") @RequestParam String motivo) {
        log.info("Cancelando conta a pagar: {} - Motivo: {}", id, motivo);
        ContaPagar contaCancelada = contaPagarService.cancelarConta(id, motivo);
        return ResponseEntity.ok(contaCancelada);
    }

    @GetMapping("/vencidas")
    @Operation(summary = "Buscar contas vencidas", description = "Busca todas as contas a pagar vencidas")
    public ResponseEntity<List<ContaPagar>> buscarContasVencidas() {
        log.info("Buscando contas vencidas");
        List<ContaPagar> contasVencidas = contaPagarService.buscarContasVencidas();
        return ResponseEntity.ok(contasVencidas);
    }

    @GetMapping("/proximas-vencimento")
    @Operation(summary = "Buscar contas próximas do vencimento", description = "Busca contas a pagar próximas do vencimento")
    public ResponseEntity<List<ContaPagar>> buscarContasProximasVencimento(@Parameter(description = "Número de dias para considerar próximo do vencimento") @RequestParam(defaultValue = "5") int dias) {
        log.info("Buscando contas próximas do vencimento em {} dias", dias);
        List<ContaPagar> contasProximas = contaPagarService.buscarContasProximasVencimento(dias);
        return ResponseEntity.ok(contasProximas);
    }

    @GetMapping("/total-por-status/{status}")
    @Operation(summary = "Calcular total por status", description = "Calcula o valor total das contas por status")
    public ResponseEntity<BigDecimal> calcularTotalPorStatus(@Parameter(description = "Status das contas") @PathVariable ContaPagar.StatusConta status) {
        log.info("Calculando total por status: {}", status);
        BigDecimal total = contaPagarService.calcularTotalPorStatus(status);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total-por-periodo")
    @Operation(summary = "Calcular total por período", description = "Calcula o valor total das contas por período")
    public ResponseEntity<BigDecimal> calcularTotalPorPeriodo(@Parameter(description = "Data início do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, @Parameter(description = "Data fim do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        log.info("Calculando total por período: {} - {}", dataInicio, dataFim);
        BigDecimal total = contaPagarService.calcularTotalPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(total);
    }

    @java.lang.SuppressWarnings("all")
    public ContaPagarController(final ContaPagarService contaPagarService) {
        this.contaPagarService = contaPagarService;
    }
}
