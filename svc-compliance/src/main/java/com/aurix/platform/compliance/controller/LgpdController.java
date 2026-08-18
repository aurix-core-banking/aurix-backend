package com.aurix.platform.compliance.controller;

import com.aurix.platform.compliance.entity.ConsentimentoLGPD;
import com.aurix.platform.compliance.lgpd.entity.LgpdBaseLegal;
import com.aurix.platform.compliance.lgpd.entity.LgpdLogAcesso;
import com.aurix.platform.compliance.repository.LgpdExclusaoRepository;
import com.aurix.platform.compliance.service.LgpdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/lgpd")
@Tag(name = "LGPD", description = "APIs para conformidade com Lei Geral de Protecao de Dados")
public class LgpdController {

    private final LgpdService lgpdService;
    private final LgpdExclusaoRepository exclusaoRepository;

    public LgpdController(LgpdService lgpdService, LgpdExclusaoRepository exclusaoRepository) {
        this.lgpdService = lgpdService;
        this.exclusaoRepository = exclusaoRepository;
    }

    @PostMapping("/consentimentos")
    @Operation(summary = "Criar consentimento LGPD")
    public ResponseEntity<ConsentimentoLGPD> criarConsentimento(@RequestParam Long clienteId,
                                                                @RequestParam String cpfCnpj,
                                                                @RequestParam ConsentimentoLGPD.TipoConsentimento tipo,
                                                                @RequestParam String descricaoFinalidade,
                                                                @RequestParam(required = false) String finalidades,
                                                                @RequestParam(required = false) String dadosColetados,
                                                                @RequestParam(required = false) String compartilhamentos,
                                                                @RequestParam(required = false) String ipAddress,
                                                                @RequestParam(required = false) String userAgent) {
        return ResponseEntity.ok(lgpdService.criarConsentimento(
            clienteId, cpfCnpj, tipo, descricaoFinalidade,
            finalidades, dadosColetados, compartilhamentos, ipAddress, userAgent));
    }

    @PostMapping("/consentimentos/{codigo}/conceder")
    @Operation(summary = "Conceder consentimento")
    public ResponseEntity<ConsentimentoLGPD> concederConsentimento(@PathVariable String codigo,
                                                                   @RequestParam(required = false) LocalDateTime dataExpiracao) {
        return ResponseEntity.ok(lgpdService.concederConsentimento(codigo, dataExpiracao));
    }

    @PostMapping("/consentimentos/{codigo}/revogar")
    @Operation(summary = "Revogar consentimento")
    public ResponseEntity<ConsentimentoLGPD> revogarConsentimento(@PathVariable String codigo) {
        return ResponseEntity.ok(lgpdService.revogarConsentimento(codigo));
    }

    @GetMapping("/consentimentos/cliente/{clienteId}")
    @Operation(summary = "Listar consentimentos do cliente")
    public ResponseEntity<List<ConsentimentoLGPD>> listarConsentimentos(@PathVariable Long clienteId) {
        return ResponseEntity.ok(lgpdService.listarConsentimentosPorCliente(clienteId));
    }

    @GetMapping("/consentimentos/cliente/{clienteId}/ativos")
    @Operation(summary = "Listar consentimentos ativos do cliente")
    public ResponseEntity<List<ConsentimentoLGPD>> listarConsentimentosAtivos(@PathVariable Long clienteId) {
        return ResponseEntity.ok(lgpdService.listarConsentimentosAtivos(clienteId));
    }

    @PostMapping("/clientes/{clienteId}/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPLIANCE')")
    public ResponseEntity<Map<String, Object>> excluirDados(@PathVariable Long clienteId,
                                                             @RequestParam(defaultValue = "Solicitacao do titular - LGPD Art. 18") String motivo) {
        lgpdService.excluirDadosCliente(clienteId);
        return ResponseEntity.ok(Map.of(
            "status", "sucesso",
            "mensagem", "Dados do cliente excluidos conforme LGPD",
            "clienteId", clienteId
        ));
    }

    @GetMapping("/clientes/{clienteId}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPLIANCE')")
    public ResponseEntity<Map<String, Object>> verificarStatus(@PathVariable Long clienteId) {
        boolean excluido = exclusaoRepository.existsByClienteId(clienteId);
        return ResponseEntity.ok(Map.of(
            "clienteId", clienteId,
            "dadosExcluidos", excluido
        ));
    }

    @DeleteMapping("/clientes/{clienteId}/direito-esquecimento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> direitoEsquecimento(@PathVariable Long clienteId) {
        lgpdService.excluirDadosCliente(clienteId);
        return ResponseEntity.ok(Map.of(
            "status", "sucesso",
            "mensagem", "Direito ao esquecimento exercido. Dados anonimizados permanentemente.",
            "clienteId", clienteId
        ));
    }

    @PostMapping("/consentimento")
    @Operation(summary = "Registrar consentimento LGPD", description = "Registra um novo consentimento granular (marketing, compartilhamento, analytics)")
    public ResponseEntity<ConsentimentoLGPD> registrarConsentimento(@RequestParam Long clienteId,
                                                                    @RequestParam String cpfCnpj,
                                                                    @RequestParam ConsentimentoLGPD.TipoConsentimento tipo,
                                                                    @RequestParam String descricaoFinalidade,
                                                                    @RequestParam(required = false) String finalidades,
                                                                    @RequestParam(required = false) String dadosColetados,
                                                                    @RequestParam(required = false) String compartilhamentos,
                                                                    @RequestParam(required = false) String ipAddress,
                                                                    @RequestParam(required = false) String userAgent) {
        return ResponseEntity.ok(lgpdService.criarConsentimento(
            clienteId, cpfCnpj, tipo, descricaoFinalidade,
            finalidades, dadosColetados, compartilhamentos, ipAddress, userAgent));
    }

    @GetMapping("/consentimentos/{clienteId}")
    @Operation(summary = "Listar consentimentos do cliente")
    public ResponseEntity<List<ConsentimentoLGPD>> listarConsentimentosCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(lgpdService.listarConsentimentosPorCliente(clienteId));
    }

    @DeleteMapping("/consentimentos/{id}")
    @Operation(summary = "Revogar consentimento", description = "Revoga um consentimento existente")
    public ResponseEntity<Map<String, Object>> revogarConsentimento(@PathVariable String id) {
        lgpdService.revogarConsentimento(id);
        return ResponseEntity.ok(Map.of(
            "status", "sucesso",
            "mensagem", "Consentimento revogado com sucesso",
            "codigo", id
        ));
    }

    @PostMapping("/dados/{clienteId}/exportar")
    @Operation(summary = "Exportar dados do cliente (portabilidade)", description = "Exporta todos os dados do titular conforme LGPD Art. 18-V")
    public ResponseEntity<Map<String, Object>> exportarDados(@PathVariable Long clienteId) {
        Map<String, Object> dados = lgpdService.exportarDados(clienteId);
        return ResponseEntity.ok(dados);
    }

    @PostMapping("/dados/{clienteId}/anonimizar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPLIANCE')")
    @Operation(summary = "Anonimizar/pseudonimizar dados", description = "Anonimiza ou pseudonimiza os dados do titular conforme LGPD Art. 16")
    public ResponseEntity<Map<String, Object>> anonimizarDados(@PathVariable Long clienteId) {
        lgpdService.anonimizarDados(clienteId);
        return ResponseEntity.ok(Map.of(
            "status", "sucesso",
            "mensagem", "Dados anonimizados com sucesso",
            "clienteId", clienteId
        ));
    }

    @PostMapping("/bases-legais")
    @Operation(summary = "Registrar base legal", description = "Registra uma base legal para tratamento de dados")
    public ResponseEntity<LgpdBaseLegal> registrarBaseLegal(@RequestBody LgpdBaseLegal baseLegal) {
        return ResponseEntity.ok(lgpdService.registrarBaseLegal(baseLegal));
    }

    @GetMapping("/bases-legais")
    @Operation(summary = "Listar bases legais ativas")
    public ResponseEntity<List<LgpdBaseLegal>> listarBasesLegaisAtivas() {
        return ResponseEntity.ok(lgpdService.listarBasesLegaisAtivas());
    }

    @GetMapping("/bases-legais/cliente/{clienteId}")
    @Operation(summary = "Listar bases legais do cliente")
    public ResponseEntity<List<LgpdBaseLegal>> listarBasesLegaisPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(lgpdService.listarBasesLegaisPorCliente(clienteId));
    }

    @GetMapping("/logs/{clienteId}")
    @Operation(summary = "Listar logs de acesso do cliente", description = "Historico completo de acessos e operacoes LGPD")
    public ResponseEntity<List<LgpdLogAcesso>> listarLogs(@PathVariable Long clienteId) {
        return ResponseEntity.ok(lgpdService.listarLogAcessos(clienteId));
    }

    @GetMapping("/logs/periodo")
    @Operation(summary = "Listar logs por periodo")
    public ResponseEntity<List<LgpdLogAcesso>> listarLogsPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(lgpdService.listarLogAcessosPorPeriodo(inicio, fim));
    }
}
