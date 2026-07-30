package com.aurix.platform.intelligence.controller;

import com.aurix.platform.intelligence.service.MetricaService;
import com.aurix.platform.shared.dto.MetricaDTO;
import com.aurix.platform.shared.entity.Metrica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller para gestão de métricas
 */
@RestController
@RequestMapping("/api/intelligence/metricas")
@Tag(name = "Métricas", description = "API para gestão de métricas do Aurix")
public class MetricaController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MetricaController.class);
    private final MetricaService metricaService;

    /**
     * Cria uma nova métrica
     */
    @PostMapping
    @Operation(summary = "Criar métrica", description = "Cria uma nova métrica")
    public ResponseEntity<MetricaDTO> criarMetrica(@Valid @RequestBody MetricaDTO metricaDTO) {
        log.info("Recebida solicitação para criar métrica: {}", metricaDTO.getNome());
        MetricaDTO metricaCriada = metricaService.criarMetrica(metricaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(metricaCriada);
    }

    /**
     * Busca métrica por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar métrica por ID", description = "Busca uma métrica pelo ID")
    public ResponseEntity<MetricaDTO> buscarMetricaPorId(@Parameter(description = "ID da métrica") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar métrica ID: {}", id);
        MetricaDTO metrica = metricaService.buscarMetricaPorId(id);
        return ResponseEntity.ok(metrica);
    }

    /**
     * Lista todas as métricas
     */
    @GetMapping
    @Operation(summary = "Listar métricas", description = "Lista todas as métricas")
    public ResponseEntity<List<MetricaDTO>> listarMetricas() {
        log.info("Recebida solicitação para listar métricas");
        List<MetricaDTO> metricas = metricaService.listarMetricas();
        return ResponseEntity.ok(metricas);
    }

    /**
     * Lista métricas por categoria
     */
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar métricas por categoria", description = "Lista métricas por categoria")
    public ResponseEntity<List<MetricaDTO>> listarMetricasPorCategoria(@Parameter(description = "Categoria da métrica") @PathVariable Metrica.CategoriaMetrica categoria) {
        log.info("Recebida solicitação para listar métricas da categoria: {}", categoria);
        List<MetricaDTO> metricas = metricaService.listarMetricasPorCategoria(categoria);
        return ResponseEntity.ok(metricas);
    }

    /**
     * Lista métricas por tipo
     */
    @GetMapping("/tipo/{tipoMetrica}")
    @Operation(summary = "Listar métricas por tipo", description = "Lista métricas por tipo")
    public ResponseEntity<List<MetricaDTO>> listarMetricasPorTipo(@Parameter(description = "Tipo da métrica") @PathVariable Metrica.TipoMetrica tipoMetrica) {
        log.info("Recebida solicitação para listar métricas do tipo: {}", tipoMetrica);
        List<MetricaDTO> metricas = metricaService.listarMetricasPorTipo(tipoMetrica);
        return ResponseEntity.ok(metricas);
    }

    /**
     * Lista métricas por período
     */
    @GetMapping("/periodo")
    @Operation(summary = "Listar métricas por período", description = "Lista métricas por período")
    public ResponseEntity<List<MetricaDTO>> listarMetricasPorPeriodo(@Parameter(description = "Data de início") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @Parameter(description = "Data de fim") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        log.info("Recebida solicitação para listar métricas do período: {} a {}", inicio, fim);
        List<MetricaDTO> metricas = metricaService.listarMetricasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(metricas);
    }

    /**
     * Lista métricas que atingiram a meta
     */
    @GetMapping("/meta-atingida")
    @Operation(summary = "Listar métricas que atingiram a meta", description = "Lista métricas que atingiram a meta")
    public ResponseEntity<List<MetricaDTO>> listarMetricasMetaAtingida() {
        log.info("Recebida solicitação para listar métricas que atingiram a meta");
        List<MetricaDTO> metricas = metricaService.listarMetricasMetaAtingida();
        return ResponseEntity.ok(metricas);
    }

    /**
     * Lista métricas fora dos limites
     */
    @GetMapping("/fora-limites")
    @Operation(summary = "Listar métricas fora dos limites", description = "Lista métricas fora dos limites")
    public ResponseEntity<List<MetricaDTO>> listarMetricasForaLimites() {
        log.info("Recebida solicitação para listar métricas fora dos limites");
        List<MetricaDTO> metricas = metricaService.listarMetricasForaLimites();
        return ResponseEntity.ok(metricas);
    }

    /**
     * Atualiza métrica
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar métrica", description = "Atualiza uma métrica")
    public ResponseEntity<MetricaDTO> atualizarMetrica(@Parameter(description = "ID da métrica") @PathVariable Long id, @Valid @RequestBody MetricaDTO metricaDTO) {
        log.info("Recebida solicitação para atualizar métrica ID: {}", id);
        MetricaDTO metricaAtualizada = metricaService.atualizarMetrica(id, metricaDTO);
        return ResponseEntity.ok(metricaAtualizada);
    }

    @java.lang.SuppressWarnings("all")
    public MetricaController(final MetricaService metricaService) {
        this.metricaService = metricaService;
    }
}
