package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.service.InvestimentoService;
import com.aurix.platform.shared.dto.InvestimentoDTO;
import com.aurix.platform.shared.entity.Investimento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controller para gestão de investimentos
 */
@RestController
@RequestMapping("/api/platform/investimentos")
@Tag(name = "Investimentos", description = "API para gestão de investimentos do Aurix")
public class InvestimentoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InvestimentoController.class);
    private final InvestimentoService investimentoService;

    /**
     * Cria um novo investimento
     */
    @PostMapping
    @Operation(summary = "Criar investimento", description = "Cria um novo investimento")
    public ResponseEntity<InvestimentoDTO> criarInvestimento(@Valid @RequestBody InvestimentoDTO investimentoDTO) {
        log.info("Recebida solicitação para criar investimento para conta ID: {}", investimentoDTO.getContaId());
        InvestimentoDTO investimentoCriado = investimentoService.criarInvestimento(investimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimentoCriado);
    }

    /**
     * Busca investimento por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar investimento por ID", description = "Busca um investimento pelo ID")
    public ResponseEntity<InvestimentoDTO> buscarInvestimentoPorId(@Parameter(description = "ID do investimento") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar investimento ID: {}", id);
        InvestimentoDTO investimento = investimentoService.buscarInvestimentoPorId(id);
        return ResponseEntity.ok(investimento);
    }

    /**
     * Lista investimentos por conta
     */
    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar investimentos por conta", description = "Lista todos os investimentos de uma conta")
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosPorConta(@Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar investimentos da conta ID: {}", contaId);
        List<InvestimentoDTO> investimentos = investimentoService.listarInvestimentosPorConta(contaId);
        return ResponseEntity.ok(investimentos);
    }

    /**
     * Lista investimentos ativos por conta
     */
    @GetMapping("/conta/{contaId}/ativos")
    @Operation(summary = "Listar investimentos ativos por conta", description = "Lista apenas investimentos ativos de uma conta")
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosAtivosPorConta(@Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar investimentos ativos da conta ID: {}", contaId);
        List<InvestimentoDTO> investimentos = investimentoService.listarInvestimentosAtivosPorConta(contaId);
        return ResponseEntity.ok(investimentos);
    }

    /**
     * Lista investimentos por tipo
     */
    @GetMapping("/tipo/{tipoInvestimento}")
    @Operation(summary = "Listar investimentos por tipo", description = "Lista investimentos por tipo")
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosPorTipo(@Parameter(description = "Tipo do investimento") @PathVariable Investimento.TipoInvestimento tipoInvestimento) {
        log.info("Recebida solicitação para listar investimentos do tipo: {}", tipoInvestimento);
        List<InvestimentoDTO> investimentos = investimentoService.listarInvestimentosPorTipo(tipoInvestimento);
        return ResponseEntity.ok(investimentos);
    }

    /**
     * Lista investimentos por status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar investimentos por status", description = "Lista investimentos por status")
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosPorStatus(@Parameter(description = "Status do investimento") @PathVariable Investimento.StatusInvestimento status) {
        log.info("Recebida solicitação para listar investimentos com status: {}", status);
        List<InvestimentoDTO> investimentos = investimentoService.listarInvestimentosPorStatus(status);
        return ResponseEntity.ok(investimentos);
    }

    /**
     * Lista investimentos vencidos
     */
    @GetMapping("/vencidos")
    @Operation(summary = "Listar investimentos vencidos", description = "Lista todos os investimentos vencidos")
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosVencidos() {
        log.info("Recebida solicitação para listar investimentos vencidos");
        List<InvestimentoDTO> investimentos = investimentoService.listarInvestimentosVencidos();
        return ResponseEntity.ok(investimentos);
    }

    /**
     * Resgata investimento com cálculo de impostos
     */
    @PutMapping("/{id}/resgatar")
    @Operation(summary = "Resgatar investimento", description = "Resgata um investimento ativo com cálculo de IOF e IR")
    public ResponseEntity<InvestimentoDTO> resgatarInvestimento(@Parameter(description = "ID do investimento") @PathVariable Long id, @Parameter(description = "Resgate antecipado") @RequestParam(defaultValue = "false") boolean resgateAntecipado) {
        log.info("Recebida solicitação para resgatar investimento ID: {}, antecipado: {}", id, resgateAntecipado);
        InvestimentoDTO investimento = investimentoService.resgatarInvestimento(id, resgateAntecipado);
        return ResponseEntity.ok(investimento);
    }

    /**
     * Atualiza rendimento do investimento automaticamente
     */
    @PutMapping("/{id}/atualizar-rendimento")
    @Operation(summary = "Atualizar rendimento", description = "Atualiza o rendimento de um investimento automaticamente")
    public ResponseEntity<Void> atualizarRendimento(@Parameter(description = "ID do investimento") @PathVariable Long id) {
        log.info("Recebida solicitação para atualizar rendimento do investimento ID: {}", id);
        investimentoService.atualizarRendimento(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Simula investimento antes de aplicar
     */
    @PostMapping("/simular")
    @Operation(summary = "Simular investimento", description = "Simula um investimento com cálculo de impostos")
    public ResponseEntity<InvestimentoDTO> simularInvestimento(@RequestParam Investimento.TipoInvestimento tipo, @RequestParam BigDecimal valorInvestido, @RequestParam BigDecimal taxaAnual, @RequestParam int dias) {
        return ResponseEntity.ok(investimentoService.simularInvestimento(tipo, valorInvestido, taxaAnual, dias));
    }

    /**
     * Atualiza rendimentos de todos os investimentos ativos
     */
    @PostMapping("/atualizar-rendimentos")
    @Operation(summary = "Atualizar todos os rendimentos", description = "Atualiza rendimentos de todos os investimentos ativos")
    public ResponseEntity<Map<String, String>> atualizarTodosRendimentos() {
        investimentoService.atualizarRendimentosAtivos();
        return ResponseEntity.ok(Map.of("mensagem", "Rendimentos atualizados com sucesso"));
    }

    /**
     * Calcula rendimento total por conta
     */
    @GetMapping("/conta/{contaId}/rendimento-total")
    @Operation(summary = "Calcular rendimento total", description = "Calcula o rendimento total de uma conta")
    public ResponseEntity<BigDecimal> calcularRendimentoTotalPorConta(@Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para calcular rendimento total da conta ID: {}", contaId);
        BigDecimal rendimentoTotal = investimentoService.calcularRendimentoTotalPorConta(contaId);
        return ResponseEntity.ok(rendimentoTotal);
    }

    /**
     * Calcula valor total investido por conta
     */
    @GetMapping("/conta/{contaId}/valor-total")
    @Operation(summary = "Calcular valor total investido", description = "Calcula o valor total investido de uma conta")
    public ResponseEntity<BigDecimal> calcularValorTotalInvestidoPorConta(@Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para calcular valor total investido da conta ID: {}", contaId);
        BigDecimal valorTotal = investimentoService.calcularValorTotalInvestidoPorConta(contaId);
        return ResponseEntity.ok(valorTotal);
    }

    @java.lang.SuppressWarnings("all")
    public InvestimentoController(final InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }
}
