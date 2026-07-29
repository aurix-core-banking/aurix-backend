package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.RelatorioComplianceDTO;
import com.aurix.platform.platform.service.LogAuditoriaService;
import com.aurix.platform.shared.dto.LogAuditoriaDTO;
import com.aurix.platform.shared.entity.LogAuditoria;
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
 * Controller para gestão de logs de auditoria
 */
@RestController
@RequestMapping("/api/platform/logs")
@Tag(name = "Logs de Auditoria", description = "API para gestão de logs de auditoria do Aurix")
public class LogAuditoriaController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogAuditoriaController.class);
    private final LogAuditoriaService logAuditoriaService;

    /**
     * Cria um novo log de auditoria
     */
    @PostMapping
    @Operation(summary = "Criar log de auditoria", description = "Cria um novo log de auditoria")
    public ResponseEntity<LogAuditoriaDTO> criarLogAuditoria(@Valid @RequestBody LogAuditoriaDTO logAuditoriaDTO) {
        log.info("Recebida solicitação para criar log de auditoria: {}", logAuditoriaDTO.getAcao());
        LogAuditoriaDTO logCriado = logAuditoriaService.criarLogAuditoria(logAuditoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(logCriado);
    }

    /**
     * Busca log por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID", description = "Busca um log de auditoria pelo ID")
    public ResponseEntity<LogAuditoriaDTO> buscarLogPorId(@Parameter(description = "ID do log") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar log ID: {}", id);
        LogAuditoriaDTO log = logAuditoriaService.buscarLogPorId(id);
        return ResponseEntity.ok(log);
    }

    /**
     * Lista logs por usuário
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar logs por usuário", description = "Lista logs de auditoria por usuário")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsPorUsuario(@Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        log.info("Recebida solicitação para listar logs do usuário ID: {}", usuarioId);
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsPorUsuario(usuarioId);
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs por entidade
     */
    @GetMapping("/entidade/{entidade}")
    @Operation(summary = "Listar logs por entidade", description = "Lista logs de auditoria por entidade")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsPorEntidade(@Parameter(description = "Nome da entidade") @PathVariable String entidade) {
        log.info("Recebida solicitação para listar logs da entidade: {}", entidade);
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsPorEntidade(entidade);
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs por tipo de ação
     */
    @GetMapping("/tipo/{tipoAcao}")
    @Operation(summary = "Listar logs por tipo de ação", description = "Lista logs de auditoria por tipo de ação")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsPorTipoAcao(@Parameter(description = "Tipo de ação") @PathVariable LogAuditoria.TipoAcao tipoAcao) {
        log.info("Recebida solicitação para listar logs do tipo de ação: {}", tipoAcao);
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsPorTipoAcao(tipoAcao);
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs por categoria
     */
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar logs por categoria", description = "Lista logs de auditoria por categoria")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsPorCategoria(@Parameter(description = "Categoria") @PathVariable LogAuditoria.CategoriaAuditoria categoria) {
        log.info("Recebida solicitação para listar logs da categoria: {}", categoria);
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsPorCategoria(categoria);
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs por período
     */
    @GetMapping("/periodo")
    @Operation(summary = "Listar logs por período", description = "Lista logs de auditoria por período")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsPorPeriodo(@Parameter(description = "Data de início") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @Parameter(description = "Data de fim") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        log.info("Recebida solicitação para listar logs do período: {} a {}", inicio, fim);
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsPorPeriodo(inicio, fim);
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs críticos
     */
    @GetMapping("/criticos")
    @Operation(summary = "Listar logs críticos", description = "Lista logs de auditoria críticos")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsCriticos() {
        log.info("Recebida solicitação para listar logs críticos");
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsCriticos();
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs de falha
     */
    @GetMapping("/falha")
    @Operation(summary = "Listar logs de falha", description = "Lista logs de auditoria de falha")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsFalha() {
        log.info("Recebida solicitação para listar logs de falha");
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsFalha();
        return ResponseEntity.ok(logs);
    }

    /**
     * Lista logs de sucesso
     */
    @GetMapping("/sucesso")
    @Operation(summary = "Listar logs de sucesso", description = "Lista logs de auditoria de sucesso")
    public ResponseEntity<List<LogAuditoriaDTO>> listarLogsSucesso() {
        log.info("Recebida solicitação para listar logs de sucesso");
        List<LogAuditoriaDTO> logs = logAuditoriaService.listarLogsSucesso();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/relatorios/compliance")
    @Operation(summary = "Relatório de compliance", description = "Relatório consolidado para auditoria e regulador (período)")
    public ResponseEntity<RelatorioComplianceDTO> relatorioCompliance(@Parameter(description = "Data de início") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @Parameter(description = "Data de fim") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(logAuditoriaService.gerarRelatorioCompliance(inicio, fim));
    }

    @java.lang.SuppressWarnings("all")
    public LogAuditoriaController(final LogAuditoriaService logAuditoriaService) {
        this.logAuditoriaService = logAuditoriaService;
    }
}
