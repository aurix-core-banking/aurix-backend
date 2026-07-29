package com.aurix.platform.banking.controller;

import com.aurix.platform.banking.dto.SolicitacaoAprovacaoDTO;
import com.aurix.platform.banking.entity.SolicitacaoAprovacao;
import com.aurix.platform.banking.service.ControleAlcadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banking/alcadas")
@CrossOrigin(origins = "*")
public class ControleAlcadaController {
    
    @Autowired
    private ControleAlcadaService controleAlcadaService;
    
    @PostMapping("/solicitar")
    public ResponseEntity<SolicitacaoAprovacaoDTO> solicitarAprovacao(@RequestBody SolicitacaoAprovacaoDTO solicitacaoDTO) {
        SolicitacaoAprovacao solicitacao = controleAlcadaService.criarSolicitacaoAprovacao(solicitacaoDTO);
        return ResponseEntity.ok(new SolicitacaoAprovacaoDTO(solicitacao));
    }
    
    @GetMapping("/solicitacoes")
    public ResponseEntity<List<SolicitacaoAprovacaoDTO>> listarSolicitacoes() {
        List<SolicitacaoAprovacao> solicitacoes = controleAlcadaService.listarTodasSolicitacoes();
        List<SolicitacaoAprovacaoDTO> solicitacoesDTO = solicitacoes.stream()
                .map(SolicitacaoAprovacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(solicitacoesDTO);
    }
    
    @GetMapping("/solicitacoes/{id}")
    public ResponseEntity<SolicitacaoAprovacaoDTO> buscarSolicitacaoPorId(@PathVariable Long id) {
        SolicitacaoAprovacao solicitacao = controleAlcadaService.buscarSolicitacaoPorId(id);
        return ResponseEntity.ok(new SolicitacaoAprovacaoDTO(solicitacao));
    }
    
    @GetMapping("/solicitacoes/solicitante/{solicitanteId}")
    public ResponseEntity<List<SolicitacaoAprovacaoDTO>> buscarSolicitacoesPorSolicitante(@PathVariable Long solicitanteId) {
        List<SolicitacaoAprovacao> solicitacoes = controleAlcadaService.buscarSolicitacoesPorSolicitante(solicitanteId);
        List<SolicitacaoAprovacaoDTO> solicitacoesDTO = solicitacoes.stream()
                .map(SolicitacaoAprovacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(solicitacoesDTO);
    }
    
    @GetMapping("/solicitacoes/aprovador/{aprovadorId}")
    public ResponseEntity<List<SolicitacaoAprovacaoDTO>> buscarSolicitacoesPorAprovador(@PathVariable Long aprovadorId) {
        List<SolicitacaoAprovacao> solicitacoes = controleAlcadaService.buscarSolicitacoesPorAprovador(aprovadorId);
        List<SolicitacaoAprovacaoDTO> solicitacoesDTO = solicitacoes.stream()
                .map(SolicitacaoAprovacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(solicitacoesDTO);
    }
    
    @PostMapping("/aprovar/{solicitacaoId}")
    public ResponseEntity<SolicitacaoAprovacaoDTO> aprovarSolicitacao(
            @PathVariable Long solicitacaoId,
            @RequestParam Long aprovadorId,
            @RequestParam(required = false) String observacoes) {
        SolicitacaoAprovacao solicitacao = controleAlcadaService.aprovarSolicitacao(solicitacaoId, aprovadorId, observacoes);
        return ResponseEntity.ok(new SolicitacaoAprovacaoDTO(solicitacao));
    }
    
    @PostMapping("/rejeitar/{solicitacaoId}")
    public ResponseEntity<SolicitacaoAprovacaoDTO> rejeitarSolicitacao(
            @PathVariable Long solicitacaoId,
            @RequestParam Long aprovadorId,
            @RequestParam String motivoRejeicao,
            @RequestParam(required = false) String observacoes) {
        SolicitacaoAprovacao solicitacao = controleAlcadaService.rejeitarSolicitacao(solicitacaoId, aprovadorId, motivoRejeicao, observacoes);
        return ResponseEntity.ok(new SolicitacaoAprovacaoDTO(solicitacao));
    }
    
    @PostMapping("/cancelar/{solicitacaoId}")
    public ResponseEntity<SolicitacaoAprovacaoDTO> cancelarSolicitacao(
            @PathVariable Long solicitacaoId,
            @RequestParam Long solicitanteId,
            @RequestParam(required = false) String motivo) {
        SolicitacaoAprovacao solicitacao = controleAlcadaService.cancelarSolicitacao(solicitacaoId, solicitanteId, motivo);
        return ResponseEntity.ok(new SolicitacaoAprovacaoDTO(solicitacao));
    }
    
    @GetMapping("/dashboard/{funcionarioId}")
    public ResponseEntity<Object> obterDashboardAprovacoes(@PathVariable Long funcionarioId) {
        Object dashboard = controleAlcadaService.obterDashboardAprovacoes(funcionarioId);
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/historico/{funcionarioId}")
    public ResponseEntity<List<SolicitacaoAprovacaoDTO>> obterHistoricoAprovacoes(@PathVariable Long funcionarioId) {
        List<SolicitacaoAprovacao> solicitacoes = controleAlcadaService.obterHistoricoAprovacoes(funcionarioId);
        List<SolicitacaoAprovacaoDTO> solicitacoesDTO = solicitacoes.stream()
                .map(SolicitacaoAprovacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(solicitacoesDTO);
    }
}
