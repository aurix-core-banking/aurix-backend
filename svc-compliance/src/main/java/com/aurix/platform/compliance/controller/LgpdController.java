package com.aurix.platform.compliance.controller;

import com.aurix.platform.compliance.entity.*;
import com.aurix.platform.compliance.service.LgpdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/compliance/api/lgpd")
@Tag(name = "LGPD", description = "APIs para conformidade com Lei Geral de Proteção de Dados")
@CrossOrigin(origins = "*")
public class LgpdController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LgpdController.class);
    private final LgpdService lgpdService;

    @PostMapping("/consentimentos")
    @Operation(summary = "Criar consentimento LGPD")
    public ResponseEntity<ConsentimentoLGPD> criarConsentimento(@RequestParam Long clienteId, @RequestParam String cpfCnpj, @RequestParam ConsentimentoLGPD.TipoConsentimento tipo, @RequestParam String descricaoFinalidade, @RequestParam(required = false) String finalidades, @RequestParam(required = false) String dadosColetados, @RequestParam(required = false) String compartilhamentos, @RequestParam(required = false) String ipAddress, @RequestParam(required = false) String userAgent) {
        return ResponseEntity.ok(lgpdService.criarConsentimento(clienteId, cpfCnpj, tipo, descricaoFinalidade, finalidades, dadosColetados, compartilhamentos, ipAddress, userAgent));
    }

    @PostMapping("/consentimentos/{codigo}/conceder")
    @Operation(summary = "Conceder consentimento")
    public ResponseEntity<ConsentimentoLGPD> concederConsentimento(@PathVariable String codigo, @RequestParam(required = false) LocalDateTime dataExpiracao) {
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

    @PostMapping("/direito-esquecimento")
    @Operation(summary = "Solicitar direito ao esquecimento")
    public ResponseEntity<DireitoEsquecimento> solicitarDireitoEsquecimento(@RequestParam Long clienteId, @RequestParam String cpfCnpj, @RequestParam DireitoEsquecimento.TipoDireito tipoDireito, @RequestParam String justificativa) {
        return ResponseEntity.ok(lgpdService.solicitarDireitoEsquecimento(clienteId, cpfCnpj, tipoDireito, justificativa));
    }

    @PostMapping("/direito-esquecimento/{codigo}/processar")
    @Operation(summary = "Processar direito ao esquecimento")
    public ResponseEntity<DireitoEsquecimento> processarDireitoEsquecimento(@PathVariable String codigo) {
        return ResponseEntity.ok(lgpdService.processarDireitoEsquecimento(codigo));
    }

    @PostMapping("/portabilidade")
    @Operation(summary = "Solicitar portabilidade de dados")
    public ResponseEntity<PortabilidadeDados> solicitarPortabilidade(@RequestParam Long clienteId, @RequestParam String cpfCnpj, @RequestParam PortabilidadeDados.TipoPortabilidade tipo, @RequestParam(required = false) String tiposDados) {
        return ResponseEntity.ok(lgpdService.solicitarPortabilidade(clienteId, cpfCnpj, tipo, tiposDados));
    }

    @PostMapping("/portabilidade/{codigo}/gerar")
    @Operation(summary = "Gerar arquivo de portabilidade")
    public ResponseEntity<PortabilidadeDados> gerarPortabilidade(@PathVariable String codigo) {
        return ResponseEntity.ok(lgpdService.gerarPortabilidade(codigo));
    }

    @PostMapping("/anonimizacao")
    @Operation(summary = "Solicitar anonimização de dados")
    public ResponseEntity<AnonimizacaoDados> solicitarAnonimizacao(@RequestParam Long clienteId, @RequestParam String cpfCnpj, @RequestParam AnonimizacaoDados.TipoAnonimizacao tipo, @RequestParam(required = false) String tabelasAfetadas, @RequestParam(required = false) String camposAnonimizados) {
        return ResponseEntity.ok(lgpdService.solicitarAnonimizacao(clienteId, cpfCnpj, tipo, tabelasAfetadas, camposAnonimizados));
    }

    @PostMapping("/anonimizacao/{codigo}/processar")
    @Operation(summary = "Processar anonimização")
    public ResponseEntity<AnonimizacaoDados> processarAnonimizacao(@PathVariable String codigo) {
        return ResponseEntity.ok(lgpdService.processarAnonimizacao(codigo));
    }

    @java.lang.SuppressWarnings("all")
    public LgpdController(final LgpdService lgpdService) {
        this.lgpdService = lgpdService;
    }
}
