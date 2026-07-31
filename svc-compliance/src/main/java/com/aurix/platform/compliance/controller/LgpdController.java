package com.aurix.platform.compliance.controller;

import com.aurix.platform.compliance.repository.LgpdExclusaoRepository;
import com.aurix.platform.compliance.service.LgpdService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lgpd")
public class LgpdController {

    private final LgpdService lgpdService;
    private final LgpdExclusaoRepository exclusaoRepository;

    public LgpdController(LgpdService lgpdService, LgpdExclusaoRepository exclusaoRepository) {
        this.lgpdService = lgpdService;
        this.exclusaoRepository = exclusaoRepository;
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