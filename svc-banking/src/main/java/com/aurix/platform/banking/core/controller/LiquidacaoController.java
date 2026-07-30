package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.LiquidacaoDTO;
import com.aurix.platform.banking.core.service.LiquidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/liquidacao")
@CrossOrigin(origins = "*")
public class LiquidacaoController {

    @Autowired
    private LiquidacaoService liquidacaoService;

    @PostMapping("/criar")
    public ResponseEntity<LiquidacaoDTO> criarLiquidacao(@RequestBody LiquidacaoDTO liquidacaoDTO) {
        try {
            LiquidacaoDTO resultado = liquidacaoService.criarLiquidacao(liquidacaoDTO);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/processar/{id}")
    public ResponseEntity<LiquidacaoDTO> processarLiquidacao(@PathVariable Long id) {
        try {
            LiquidacaoDTO resultado = liquidacaoService.processarLiquidacao(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/estornar/{id}")
    public ResponseEntity<LiquidacaoDTO> estornarLiquidacao(@PathVariable Long id) {
        try {
            LiquidacaoDTO resultado = liquidacaoService.estornarLiquidacao(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<LiquidacaoDTO>> listarLiquidacoesPendentes() {
        try {
            List<LiquidacaoDTO> liquidacoes = liquidacaoService.listarLiquidacoesPendentes();
            return ResponseEntity.ok(liquidacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tipos-liquidacao")
    public ResponseEntity<Map<String, Object>> listarTiposLiquidacao() {
        try {
            Map<String, Object> tipos = Map.of(
                    "tipos", List.of(
                            "PIX_INSTANTANEO",
                            "PIX_AGENDADO",
                            "TED_IMEDIATA",
                            "TED_AGENDADA",
                            "DOC_IMEDIATA",
                            "DOC_AGENDADA",
                            "SAQUE_ATM",
                            "DEPOSITO_ESPECIE",
                            "DEPOSITO_CHEQUE",
                            "TRANSFERENCIA_INTERNA",
                            "APLICACAO_INVESTIMENTO",
                            "RESGATE_INVESTIMENTO",
                            "PAGAMENTO_BOLETO",
                            "PAGAMENTO_CARTAO",
                            "OUTROS"),
                    "status", List.of(
                            "PENDENTE",
                            "PROCESSANDO",
                            "LIQUIDADA",
                            "FALHADA",
                            "CANCELADA",
                            "ESTORNADA",
                            "REVERSADA",
                            "AGUARDANDO_APROVACAO",
                            "REJEITADA"));
            return ResponseEntity.ok(tipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> obterDashboardLiquidacao() {
        try {
            // Implementar dashboard com métricas de liquidação
            Map<String, Object> dashboard = Map.of(
                    "liquidacoesPendentes", 0,
                    "liquidacoesProcessando", 0,
                    "liquidacoesLiquidadas", 0,
                    "liquidacoesFalhadas", 0,
                    "valorTotalLiquidado", 0.0,
                    "taxaSucesso", 0.0,
                    "tempoMedioProcessamento", 0.0,
                    "ultimaAtualizacao", LocalDateTime.now());
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/relatorio/{tipo}")
    public ResponseEntity<Map<String, Object>> gerarRelatorioLiquidacao(@PathVariable String tipo) {
        try {
            // Implementar geração de relatórios
            Map<String, Object> relatorio = Map.of(
                    "tipo", tipo,
                    "dataGeracao", LocalDateTime.now(),
                    "dados", List.of(),
                    "totalRegistros", 0,
                    "valorTotal", 0.0);
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
