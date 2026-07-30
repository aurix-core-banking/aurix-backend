package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.ControleSaldoDTO;
import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.service.ControleSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/controle-saldos")
@CrossOrigin(origins = "*")
public class ControleSaldoController {

    @Autowired
    private ControleSaldoService controleSaldoService;

    @PostMapping("/movimento")
    public ResponseEntity<MovimentoContaDTO> processarMovimento(@RequestBody MovimentoContaDTO movimentoDTO) {
        try {
            MovimentoContaDTO resultado = controleSaldoService.processarMovimento(movimentoDTO);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/movimento/{id}/processar")
    public ResponseEntity<MovimentoContaDTO> processarMovimento(@PathVariable Long id) {
        try {
            MovimentoContaDTO resultado = controleSaldoService.processarMovimento(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/movimento/{id}/estornar")
    public ResponseEntity<MovimentoContaDTO> estornarMovimento(@PathVariable Long id) {
        try {
            MovimentoContaDTO resultado = controleSaldoService.estornarMovimento(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/conta/{contaId}/movimentos")
    public ResponseEntity<List<MovimentoContaDTO>> listarMovimentosPorConta(@PathVariable Long contaId) {
        try {
            List<MovimentoContaDTO> movimentos = controleSaldoService.listarMovimentosPorConta(contaId);
            return ResponseEntity.ok(movimentos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/movimentos/pendentes")
    public ResponseEntity<List<MovimentoContaDTO>> listarMovimentosPendentes() {
        try {
            List<MovimentoContaDTO> movimentos = controleSaldoService.listarMovimentosPendentes();
            return ResponseEntity.ok(movimentos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/conta/{contaId}/controle")
    public ResponseEntity<ControleSaldoDTO> obterControleSaldo(@PathVariable Long contaId) {
        try {
            ControleSaldoDTO controle = controleSaldoService.obterControleSaldo(contaId);
            return ResponseEntity.ok(controle);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/inconsistentes")
    public ResponseEntity<List<ControleSaldoDTO>> listarContasComSaldoInconsistente() {
        try {
            List<ControleSaldoDTO> controles = controleSaldoService.listarContasComSaldoInconsistente();
            return ResponseEntity.ok(controles);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/bloqueadas")
    public ResponseEntity<List<ControleSaldoDTO>> listarContasBloqueadas() {
        try {
            List<ControleSaldoDTO> controles = controleSaldoService.listarContasBloqueadas();
            return ResponseEntity.ok(controles);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tipos-movimento")
    public ResponseEntity<Map<String, Object>> listarTiposMovimento() {
        try {
            Map<String, Object> tipos = Map.of(
                    "tipos", List.of(
                            "DEBITO",
                            "CREDITO",
                            "BLOQUEIO",
                            "DESBLOQUEIO",
                            "RESERVA",
                            "LIBERACAO_RESERVA",
                            "AJUSTE_CREDITO",
                            "AJUSTE_DEBITO",
                            "TARIFA",
                            "JUROS",
                            "RENDIMENTO",
                            "CORRECAO_MONETARIA",
                            "IOF",
                            "IR",
                            "OUTROS"),
                    "status", List.of(
                            "PENDENTE",
                            "PROCESSANDO",
                            "CONCLUIDO",
                            "FALHADO",
                            "CANCELADO",
                            "ESTORNADO",
                            "REVERSADO",
                            "AGUARDANDO_APROVACAO",
                            "REJEITADO"));
            return ResponseEntity.ok(tipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> obterDashboardControleSaldos() {
        try {
            // Implementar dashboard com métricas de controle de saldos
            Map<String, Object> dashboard = Map.of(
                    "movimentosPendentes", 0,
                    "movimentosProcessando", 0,
                    "movimentosConcluidos", 0,
                    "movimentosFalhados", 0,
                    "contasInconsistentes", 0,
                    "contasBloqueadas", 0,
                    "valorTotalMovimentado", 0.0,
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
    public ResponseEntity<Map<String, Object>> gerarRelatorioControleSaldos(@PathVariable String tipo) {
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

    @PostMapping("/conta/{contaId}/bloquear")
    public ResponseEntity<ControleSaldoDTO> bloquearConta(@PathVariable Long contaId,
            @RequestBody Map<String, String> request) {
        try {
            // Implementar bloqueio de conta

            // Lógica de bloqueio aqui

            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/conta/{contaId}/desbloquear")
    public ResponseEntity<ControleSaldoDTO> desbloquearConta(@PathVariable Long contaId,
            @RequestBody Map<String, String> request) {
        try {
            // Implementar desbloqueio de conta

            // Lógica de desbloqueio aqui

            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
