package com.aurix.platform.finance.controller;

import com.aurix.platform.finance.entity.InstrumentoFinanceiro;
import com.aurix.platform.finance.entity.ExpectedCreditLoss;
import com.aurix.platform.finance.entity.HedgeAccounting;
import com.aurix.platform.finance.entity.RelatorioIFRS9;
import com.aurix.platform.finance.service.IFRS9Service;
import com.aurix.platform.finance.service.RelatorioIFRS9Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Controller para APIs do IFRS 9
 * 
 * Expõe endpoints para classificação, ECL e hedge accounting
 */
@RestController
@RequestMapping("/api/finance/ifrs9")
@Tag(name = "IFRS 9", description = "APIs para implementação do IFRS 9")
public class IFRS9Controller {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IFRS9Controller.class);
    private final IFRS9Service ifrs9Service;
    private final RelatorioIFRS9Service relatorioService;

    /**
     * Classifica um instrumento financeiro conforme IFRS 9
     */
    @PostMapping("/instrumentos/classificar")
    @Operation(summary = "Classificar instrumento financeiro", description = "Classifica um instrumento financeiro conforme critérios do IFRS 9")
    public ResponseEntity<InstrumentoFinanceiro> classificarInstrumento(@RequestBody InstrumentoFinanceiro instrumento) {
        log.info("Classificando instrumento: {}", instrumento.getCodigoInstrumento());
        InstrumentoFinanceiro instrumentoClassificado = ifrs9Service.classificarInstrumento(instrumento);
        return ResponseEntity.ok(instrumentoClassificado);
    }

    /**
     * Calcula ECL para um instrumento financeiro
     */
    @PostMapping("/ecl/calcular/{instrumentoId}")
    @Operation(summary = "Calcular ECL", description = "Calcula Expected Credit Loss para um instrumento financeiro")
    public ResponseEntity<ExpectedCreditLoss> calcularECL(@Parameter(description = "ID do instrumento financeiro") @PathVariable Long instrumentoId, @Parameter(description = "Data de cálculo do ECL") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCalculo) {
        log.info("Calculando ECL para instrumento: {} - Data: {}", instrumentoId, dataCalculo);
        ExpectedCreditLoss ecl = ifrs9Service.calcularECL(instrumentoId, dataCalculo);
        return ResponseEntity.ok(ecl);
    }

    /**
     * Reclassifica um instrumento entre estágios de deterioração
     */
    @PostMapping("/instrumentos/{instrumentoId}/reclassificar")
    @Operation(summary = "Reclassificar estágio", description = "Reclassifica um instrumento entre estágios de deterioração")
    public ResponseEntity<InstrumentoFinanceiro> reclassificarEstagio(@Parameter(description = "ID do instrumento financeiro") @PathVariable Long instrumentoId, @Parameter(description = "Novo estágio de deterioração") @RequestParam InstrumentoFinanceiro.EstagioDeterioracao novoEstagio, @Parameter(description = "Motivo da reclassificação") @RequestParam String motivo) {
        log.info("Reclassificando instrumento: {} - Novo estágio: {}", instrumentoId, novoEstagio);
        InstrumentoFinanceiro instrumento = ifrs9Service.reclassificarEstagio(instrumentoId, novoEstagio, motivo);
        return ResponseEntity.ok(instrumento);
    }

    /**
     * Processa hedge accounting
     */
    @PostMapping("/hedge")
    @Operation(summary = "Criar hedge", description = "Cria um relacionamento de hedge accounting")
    public ResponseEntity<HedgeAccounting> criarHedge(@Parameter(description = "ID do instrumento hedgeado") @RequestParam Long instrumentoHedgeadoId, @Parameter(description = "ID do instrumento hedge") @RequestParam Long instrumentoHedgeId, @Parameter(description = "Tipo de hedge") @RequestParam HedgeAccounting.TipoHedge tipoHedge) {
        log.info("Criando hedge: {} -> {} - Tipo: {}", instrumentoHedgeadoId, instrumentoHedgeId, tipoHedge);
        HedgeAccounting hedge = ifrs9Service.processarHedgeAccounting(instrumentoHedgeadoId, instrumentoHedgeId, tipoHedge);
        return ResponseEntity.ok(hedge);
    }

    /**
     * Avalia efetividade de hedge
     */
    @PostMapping("/hedge/{hedgeId}/avaliar-efetividade")
    @Operation(summary = "Avaliar efetividade", description = "Avalia a efetividade de um hedge")
    public ResponseEntity<HedgeAccounting> avaliarEfetividadeHedge(@Parameter(description = "ID do hedge") @PathVariable Long hedgeId) {
        log.info("Avaliando efetividade do hedge: {}", hedgeId);
        HedgeAccounting hedge = ifrs9Service.avaliarEfetividadeHedge(hedgeId);
        return ResponseEntity.ok(hedge);
    }

    /**
     * Processa reclassificação automática
     */
    @PostMapping("/processar-reclassificacao")
    @Operation(summary = "Processar reclassificação", description = "Processa reclassificação automática de instrumentos")
    public ResponseEntity<Void> processarReclassificacao(@Parameter(description = "Data de reclassificação") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReclassificacao) {
        log.info("Processando reclassificação para: {}", dataReclassificacao);
        ifrs9Service.processarReclassificacao(dataReclassificacao);
        return ResponseEntity.ok().build();
    }

    /**
     * Calcula provisionamento por estágio
     */
    @GetMapping("/provisionamento/estagio/{estagio}")
    @Operation(summary = "Calcular provisionamento por estágio", description = "Calcula o provisionamento total para um estágio específico")
    public ResponseEntity<Double> calcularProvisionamentoPorEstagio(@Parameter(description = "Estágio de deterioração") @PathVariable InstrumentoFinanceiro.EstagioDeterioracao estagio) {
        log.info("Calculando provisionamento para estágio: {}", estagio);
        var provisionamento = ifrs9Service.calcularProvisionamentoPorEstagio(estagio);
        return ResponseEntity.ok(provisionamento.doubleValue());
    }

    /**
     * Calcula provisionamento total
     */
    @GetMapping("/provisionamento/total")
    @Operation(summary = "Calcular provisionamento total", description = "Calcula o provisionamento total geral")
    public ResponseEntity<Double> calcularProvisionamentoTotal() {
        log.info("Calculando provisionamento total");
        var provisionamento = ifrs9Service.calcularProvisionamentoTotal();
        return ResponseEntity.ok(provisionamento.doubleValue());
    }

    /**
     * Gera relatório consolidado IFRS 9
     */
    @PostMapping("/relatorios/consolidado")
    @Operation(summary = "Gerar relatório consolidado", description = "Gera relatório consolidado do IFRS 9")
    public ResponseEntity<RelatorioIFRS9Service.RelatorioConsolidado> gerarRelatorioConsolidado(@Parameter(description = "Data de referência") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        log.info("Gerando relatório consolidado para: {}", dataReferencia);
        RelatorioIFRS9Service.RelatorioConsolidado relatorio = ifrs9Service.gerarRelatorioConsolidado(dataReferencia);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * Gera relatório de classificação
     */
    @PostMapping("/relatorios/classificacao")
    @Operation(summary = "Gerar relatório de classificação", description = "Gera relatório de classificação e mensuração")
    public ResponseEntity<RelatorioIFRS9> gerarRelatorioClassificacao(@Parameter(description = "Data início do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoInicio, @Parameter(description = "Data fim do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoFim) {
        log.info("Gerando relatório de classificação: {} - {}", periodoInicio, periodoFim);
        RelatorioIFRS9 relatorio = relatorioService.gerarRelatorioClassificacao(periodoInicio, periodoFim);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * Gera relatório ECL detalhado
     */
    @PostMapping("/relatorios/ecl-detalhado")
    @Operation(summary = "Gerar relatório ECL detalhado", description = "Gera relatório detalhado de Expected Credit Loss")
    public ResponseEntity<RelatorioIFRS9> gerarRelatorioECLDetalhado(@Parameter(description = "Data de cálculo") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCalculo) {
        log.info("Gerando relatório ECL detalhado para: {}", dataCalculo);
        RelatorioIFRS9 relatorio = relatorioService.gerarRelatorioECLDetalhado(dataCalculo);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * Gera relatório de hedge accounting
     */
    @PostMapping("/relatorios/hedge")
    @Operation(summary = "Gerar relatório hedge accounting", description = "Gera relatório de hedge accounting")
    public ResponseEntity<RelatorioIFRS9> gerarRelatorioHedgeAccounting(@Parameter(description = "Data início do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoInicio, @Parameter(description = "Data fim do período") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoFim) {
        log.info("Gerando relatório hedge accounting: {} - {}", periodoInicio, periodoFim);
        RelatorioIFRS9 relatorio = relatorioService.gerarRelatorioHedgeAccounting(periodoInicio, periodoFim);
        return ResponseEntity.ok(relatorio);
    }

    @java.lang.SuppressWarnings("all")
    public IFRS9Controller(final IFRS9Service ifrs9Service, final RelatorioIFRS9Service relatorioService) {
        this.ifrs9Service = ifrs9Service;
        this.relatorioService = relatorioService;
    }
}
