package com.aurix.platform.finance.controller;

import com.aurix.platform.finance.entity.Imposto;
import com.aurix.platform.finance.service.ImpostoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller para APIs de impostos
 * 
 * Gerencia todas as operações relacionadas a impostos e tributos
 */
@RestController
@RequestMapping("/api/finance/impostos")
@Tag(name = "Impostos", description = "APIs para gestão de impostos e tributos")
public class ImpostoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ImpostoController.class);
    private final ImpostoService impostoService;

    /**
     * Cria um novo imposto
     */
    @PostMapping
    @Operation(summary = "Criar imposto", description = "Cria um novo imposto ou tributo")
    public ResponseEntity<Imposto> criarImposto(@RequestBody Imposto imposto) {
        log.info("Criando imposto: {}", imposto.getNome());
        Imposto impostoCriado = impostoService.criarImposto(imposto);
        return ResponseEntity.ok(impostoCriado);
    }

    /**
     * Apura um imposto
     */
    @PostMapping("/{id}/apurar")
    @Operation(summary = "Apurar imposto", description = "Apura um imposto calculado")
    public ResponseEntity<Imposto> apurarImposto(@Parameter(description = "ID do imposto") @PathVariable Long id) {
        log.info("Apurando imposto: {}", id);
        Imposto impostoApurado = impostoService.apurarImposto(id);
        return ResponseEntity.ok(impostoApurado);
    }

    /**
     * Registra pagamento de imposto
     */
    @PostMapping("/{id}/pagar")
    @Operation(summary = "Registrar pagamento", description = "Registra o pagamento de um imposto")
    public ResponseEntity<Imposto> pagarImposto(@Parameter(description = "ID do imposto") @PathVariable Long id, @Parameter(description = "Data do pagamento") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento, @Parameter(description = "Número do DARF") @RequestParam String numeroDarf) {
        log.info("Registrando pagamento do imposto: {} - Data: {}", id, dataPagamento);
        Imposto impostoPago = impostoService.pagarImposto(id, dataPagamento, numeroDarf);
        return ResponseEntity.ok(impostoPago);
    }

    /**
     * Busca impostos vencidos
     */
    @GetMapping("/vencidos")
    @Operation(summary = "Buscar impostos vencidos", description = "Busca todos os impostos vencidos")
    public ResponseEntity<List<Imposto>> buscarImpostosVencidos() {
        log.info("Buscando impostos vencidos");
        List<Imposto> impostosVencidos = impostoService.buscarImpostosVencidos();
        return ResponseEntity.ok(impostosVencidos);
    }

    /**
     * Busca impostos próximos do vencimento
     */
    @GetMapping("/proximos-vencimento")
    @Operation(summary = "Buscar impostos próximos do vencimento", description = "Busca impostos próximos do vencimento")
    public ResponseEntity<List<Imposto>> buscarImpostosProximosVencimento(@Parameter(description = "Número de dias para considerar próximo do vencimento") @RequestParam(defaultValue = "5") int dias) {
        log.info("Buscando impostos próximos do vencimento em {} dias", dias);
        List<Imposto> impostosProximos = impostoService.buscarImpostosProximosVencimento(dias);
        return ResponseEntity.ok(impostosProximos);
    }

    /**
     * Calcula resumo por competência
     */
    @GetMapping("/resumo-competencia/{competencia}")
    @Operation(summary = "Calcular resumo por competência", description = "Calcula o resumo dos impostos por competência")
    public ResponseEntity<ImpostoService.ResumoCompetencia> calcularResumoCompetencia(@Parameter(description = "Competência no formato YYYYMM") @PathVariable String competencia) {
        log.info("Calculando resumo da competência: {}", competencia);
        ImpostoService.ResumoCompetencia resumo = impostoService.calcularResumoCompetencia(competencia);
        return ResponseEntity.ok(resumo);
    }

    /**
     * Calcula resumo por período
     */
    @GetMapping("/resumo-periodo")
    @Operation(summary = "Calcular resumo por período", description = "Calcula o resumo dos impostos por período")
    public ResponseEntity<ImpostoService.ResumoPeriodo> calcularResumoPeriodo(@Parameter(description = "Data início do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, @Parameter(description = "Data fim do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        log.info("Calculando resumo do período: {} - {}", dataInicio, dataFim);
        ImpostoService.ResumoPeriodo resumo = impostoService.calcularResumoPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(resumo);
    }

    @java.lang.SuppressWarnings("all")
    public ImpostoController(final ImpostoService impostoService) {
        this.impostoService = impostoService;
    }
}
