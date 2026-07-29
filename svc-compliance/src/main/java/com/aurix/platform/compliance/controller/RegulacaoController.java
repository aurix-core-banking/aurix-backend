package com.aurix.platform.compliance.controller;

import com.aurix.platform.compliance.service.RegulacaoService;
import com.aurix.platform.shared.dto.RegulacaoDTO;
import com.aurix.platform.shared.entity.Regulacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de regulamentações
 */
@RestController
@RequestMapping("/api/compliance/regulacoes")
@Tag(name = "Regulamentações", description = "API para gestão de regulamentações do Aurix")
public class RegulacaoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegulacaoController.class);
    private final RegulacaoService regulacaoService;

    /**
     * Cria uma nova regulamentação
     */
    @PostMapping
    @Operation(summary = "Criar regulamentação", description = "Cria uma nova regulamentação")
    public ResponseEntity<RegulacaoDTO> criarRegulacao(@Valid @RequestBody RegulacaoDTO regulacaoDTO) {
        log.info("Recebida solicitação para criar regulamentação: {}", regulacaoDTO.getNome());
        RegulacaoDTO regulacaoCriada = regulacaoService.criarRegulacao(regulacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regulacaoCriada);
    }

    /**
     * Busca regulamentação por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar regulamentação por ID", description = "Busca uma regulamentação pelo ID")
    public ResponseEntity<RegulacaoDTO> buscarRegulacaoPorId(@Parameter(description = "ID da regulamentação") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar regulamentação ID: {}", id);
        RegulacaoDTO regulacao = regulacaoService.buscarRegulacaoPorId(id);
        return ResponseEntity.ok(regulacao);
    }

    /**
     * Lista todas as regulamentações
     */
    @GetMapping
    @Operation(summary = "Listar regulamentações", description = "Lista todas as regulamentações")
    public ResponseEntity<List<RegulacaoDTO>> listarRegulacoes() {
        log.info("Recebida solicitação para listar regulamentações");
        List<RegulacaoDTO> regulacoes = regulacaoService.listarRegulacoes();
        return ResponseEntity.ok(regulacoes);
    }

    /**
     * Lista regulamentações por órgão regulador
     */
    @GetMapping("/orgao/{orgaoRegulador}")
    @Operation(summary = "Listar regulamentações por órgão", description = "Lista regulamentações por órgão regulador")
    public ResponseEntity<List<RegulacaoDTO>> listarRegulacoesPorOrgao(@Parameter(description = "Órgão regulador") @PathVariable String orgaoRegulador) {
        log.info("Recebida solicitação para listar regulamentações do órgão: {}", orgaoRegulador);
        List<RegulacaoDTO> regulacoes = regulacaoService.listarRegulacoesPorOrgao(orgaoRegulador);
        return ResponseEntity.ok(regulacoes);
    }

    /**
     * Lista regulamentações por tipo
     */
    @GetMapping("/tipo/{tipoRegulacao}")
    @Operation(summary = "Listar regulamentações por tipo", description = "Lista regulamentações por tipo")
    public ResponseEntity<List<RegulacaoDTO>> listarRegulacoesPorTipo(@Parameter(description = "Tipo da regulamentação") @PathVariable Regulacao.TipoRegulacao tipoRegulacao) {
        log.info("Recebida solicitação para listar regulamentações do tipo: {}", tipoRegulacao);
        List<RegulacaoDTO> regulacoes = regulacaoService.listarRegulacoesPorTipo(tipoRegulacao);
        return ResponseEntity.ok(regulacoes);
    }

    /**
     * Lista regulamentações ativas
     */
    @GetMapping("/ativas")
    @Operation(summary = "Listar regulamentações ativas", description = "Lista apenas regulamentações ativas")
    public ResponseEntity<List<RegulacaoDTO>> listarRegulacoesAtivas() {
        log.info("Recebida solicitação para listar regulamentações ativas");
        List<RegulacaoDTO> regulacoes = regulacaoService.listarRegulacoesAtivas();
        return ResponseEntity.ok(regulacoes);
    }

    /**
     * Lista regulamentações vencidas
     */
    @GetMapping("/vencidas")
    @Operation(summary = "Listar regulamentações vencidas", description = "Lista regulamentações vencidas")
    public ResponseEntity<List<RegulacaoDTO>> listarRegulacoesVencidas() {
        log.info("Recebida solicitação para listar regulamentações vencidas");
        List<RegulacaoDTO> regulacoes = regulacaoService.listarRegulacoesVencidas();
        return ResponseEntity.ok(regulacoes);
    }

    /**
     * Atualiza regulamentação
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regulamentação", description = "Atualiza uma regulamentação")
    public ResponseEntity<RegulacaoDTO> atualizarRegulacao(@Parameter(description = "ID da regulamentação") @PathVariable Long id, @Valid @RequestBody RegulacaoDTO regulacaoDTO) {
        log.info("Recebida solicitação para atualizar regulamentação ID: {}", id);
        RegulacaoDTO regulacaoAtualizada = regulacaoService.atualizarRegulacao(id, regulacaoDTO);
        return ResponseEntity.ok(regulacaoAtualizada);
    }

    /**
     * Ativa/desativa regulamentação
     */
    @PutMapping("/{id}/toggle-status")
    @Operation(summary = "Ativar/desativar regulamentação", description = "Altera o status de uma regulamentação")
    public ResponseEntity<Void> toggleStatusRegulacao(@Parameter(description = "ID da regulamentação") @PathVariable Long id) {
        log.info("Recebida solicitação para alterar status da regulamentação ID: {}", id);
        regulacaoService.toggleStatusRegulacao(id);
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public RegulacaoController(final RegulacaoService regulacaoService) {
        this.regulacaoService = regulacaoService;
    }
}
