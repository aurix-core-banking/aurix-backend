package com.aurix.platform.compliance.controller;

import com.aurix.platform.compliance.entity.ConsentimentoLGPD;
import com.aurix.platform.compliance.repository.LgpdExclusaoRepository;
import com.aurix.platform.compliance.service.LgpdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/api/lgpd")
@Tag(name = "LGPD", description = "APIs para conformidade com Lei Geral de Proteção de Dados")
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
}
