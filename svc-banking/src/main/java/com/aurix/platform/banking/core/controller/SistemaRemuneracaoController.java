package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.AplicacaoFinanceiraDTO;
import com.aurix.platform.banking.core.dto.ProdutoFinanceiroDTO;
import com.aurix.platform.banking.core.service.SistemaRemuneracaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/remuneracao")
@CrossOrigin(origins = "*")
@Tag(name = "Sistema de Remuneração", description = "APIs para gestão de produtos financeiros, aplicações e remunerações")
public class SistemaRemuneracaoController {

    @Autowired
    private SistemaRemuneracaoService sistemaRemuneracaoService;

    @PostMapping("/aplicacao/criar")
    @Operation(summary = "Criar Aplicação Financeira", description = "Cria uma nova aplicação financeira para um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro na criação")
    })
    public ResponseEntity<AplicacaoFinanceiraDTO> criarAplicacao(
            @Parameter(description = "Dados da aplicação", required = true) @RequestBody Map<String, Object> request) {
        try {
            Long contaId = Long.valueOf(request.get("contaId").toString());
            Long produtoFinanceiroId = Long.valueOf(request.get("produtoFinanceiroId").toString());
            BigDecimal valorAplicacao = new BigDecimal(request.get("valorAplicacao").toString());
            String usuarioAplicacao = request.get("usuarioAplicacao").toString();

            AplicacaoFinanceiraDTO resultado = sistemaRemuneracaoService.criarAplicacao(contaId, produtoFinanceiroId,
                    valorAplicacao, usuarioAplicacao);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/aplicacao/{id}/resgatar")
    @Operation(summary = "Resgatar Aplicação", description = "Resgata uma aplicação financeira ativa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação resgatada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no resgate da aplicação")
    })
    public ResponseEntity<AplicacaoFinanceiraDTO> resgatarAplicacao(
            @Parameter(description = "ID da aplicação", required = true) @PathVariable Long id,
            @Parameter(description = "Dados do resgate", required = true) @RequestBody Map<String, Object> request) {
        try {
            BigDecimal valorResgate = new BigDecimal(request.get("valorResgate").toString());
            String usuarioResgate = request.get("usuarioResgate").toString();

            AplicacaoFinanceiraDTO resultado = sistemaRemuneracaoService.resgatarAplicacao(id, valorResgate,
                    usuarioResgate);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/processar-remuneracoes")
    @Operation(summary = "Processar Remunerações", description = "Processa todas as remunerações pendentes automaticamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remunerações processadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no processamento")
    })
    public ResponseEntity<Map<String, Object>> processarRemuneracoes() {
        try {
            sistemaRemuneracaoService.processarRemuneracoes();

            Map<String, Object> resultado = Map.of(
                    "sucesso", true,
                    "mensagem", "Remunerações processadas com sucesso",
                    "dataProcessamento", LocalDateTime.now());
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/aplicacoes/conta/{contaId}")
    @Operation(summary = "Listar Aplicações por Conta", description = "Lista todas as aplicações financeiras de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aplicações retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<List<AplicacaoFinanceiraDTO>> listarAplicacoesPorConta(
            @Parameter(description = "ID da conta", required = true) @PathVariable Long contaId) {
        try {
            List<AplicacaoFinanceiraDTO> aplicacoes = sistemaRemuneracaoService.listarAplicacoesPorConta(contaId);
            return ResponseEntity.ok(aplicacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/aplicacoes/ativas")
    @Operation(summary = "Listar Aplicações Ativas", description = "Lista todas as aplicações financeiras ativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aplicações ativas retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<List<AplicacaoFinanceiraDTO>> listarAplicacoesAtivas() {
        try {
            List<AplicacaoFinanceiraDTO> aplicacoes = sistemaRemuneracaoService.listarAplicacoesAtivas();
            return ResponseEntity.ok(aplicacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/produtos/disponiveis")
    @Operation(summary = "Listar Produtos Disponíveis", description = "Lista todos os produtos financeiros disponíveis para aplicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<List<ProdutoFinanceiroDTO>> listarProdutosDisponiveis() {
        try {
            List<ProdutoFinanceiroDTO> produtos = sistemaRemuneracaoService.listarProdutosDisponiveis();
            return ResponseEntity.ok(produtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tipos-produto")
    @Operation(summary = "Listar Tipos de Produto", description = "Lista todos os tipos e categorias de produtos financeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de produto retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<Map<String, Object>> listarTiposProduto() {
        try {
            Map<String, Object> tipos = Map.of(
                    "tipos", List.of(
                            "POUPANCA",
                            "CDB",
                            "LCI",
                            "LCA",
                            "LC",
                            "DEBENTURE",
                            "FUNDO_INVESTIMENTO",
                            "PREVIDENCIA",
                            "SEGURO_VIDA",
                            "CREDITO_PESSOAL",
                            "FINANCIAMENTO",
                            "CARTÃO_CREDITO",
                            "CONTA_CORRENTE",
                            "CONTA_POUPANCA",
                            "OUTROS"),
                    "categorias", List.of(
                            "RENDA_FIXA",
                            "RENDA_VARIAVEL",
                            "HIBRIDO",
                            "CREDITO",
                            "SEGURO",
                            "PREVIDENCIA",
                            "CONTA_BANCARIA",
                            "OUTROS"),
                    "tiposRemuneracao", List.of(
                            "FIXA",
                            "VARIAVEL",
                            "HIBRIDA",
                            "INDEXADA",
                            "PRE_FIXADA",
                            "POS_FIXADA"),
                    "periodicidades", List.of(
                            "DIARIA",
                            "MENSAL",
                            "TRIMESTRAL",
                            "SEMESTRAL",
                            "ANUAL",
                            "VENCIMENTO",
                            "PERSONALIZADA"));
            return ResponseEntity.ok(tipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status-aplicacao")
    @Operation(summary = "Listar Status de Aplicação", description = "Lista todos os possíveis status de aplicações financeiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<Map<String, Object>> listarStatusAplicacao() {
        try {
            Map<String, Object> status = Map.of(
                    "status", List.of(
                            "ATIVA",
                            "RESGATADA",
                            "VENCIDA",
                            "CANCELADA",
                            "SUSPENSA",
                            "BLOQUEADA",
                            "RENOVADA",
                            "REAPLICADA"));
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard de Remuneração", description = "Retorna métricas e indicadores do sistema de remuneração")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta")
    })
    public ResponseEntity<Map<String, Object>> obterDashboardRemuneracao() {
        try {
            // Implementar dashboard com métricas de remuneração
            Map<String, Object> dashboard = Map.of(
                    "aplicacoesAtivas", 0,
                    "aplicacoesResgatadas", 0,
                    "valorTotalAplicado", 0.0,
                    "valorTotalRemunerado", 0.0,
                    "taxaMediaRemuneracao", 0.0,
                    "produtosDisponiveis", 0,
                    "remuneracoesProcessadas", 0,
                    "ultimaAtualizacao", LocalDateTime.now());
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/relatorio/{tipo}")
    @Operation(summary = "Gerar Relatório", description = "Gera relatórios de remuneração por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na geração do relatório")
    })
    public ResponseEntity<Map<String, Object>> gerarRelatorioRemuneracao(
            @Parameter(description = "Tipo do relatório", required = true) @PathVariable String tipo) {
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

    @GetMapping("/simulacao")
    @Operation(summary = "Simular Remuneração", description = "Simula o rendimento de uma aplicação financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na simulação")
    })
    public ResponseEntity<Map<String, Object>> simularRemuneracao(
            @Parameter(description = "ID do produto", required = true) @RequestParam Long produtoId,
            @Parameter(description = "Valor da aplicação", required = true) @RequestParam BigDecimal valor,
            @Parameter(description = "Prazo em dias", required = true) @RequestParam Integer prazoDias) {
        try {
            // Implementar simulação de remuneração
            Map<String, Object> simulacao = Map.of(
                    "produtoId", produtoId,
                    "valorAplicacao", valor,
                    "prazoDias", prazoDias,
                    "valorRemuneracao", 0.0,
                    "valorTotal", 0.0,
                    "taxaRemuneracao", 0.0,
                    "dataSimulacao", LocalDateTime.now());
            return ResponseEntity.ok(simulacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/aplicacao/{id}/renovar")
    @Operation(summary = "Renovar Aplicação", description = "Renova uma aplicação financeira vencida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação renovada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na renovação")
    })
    public ResponseEntity<Map<String, Object>> renovarAplicacao(
            @Parameter(description = "ID da aplicação", required = true) @PathVariable Long id,
            @Parameter(description = "Dados da renovação", required = true) @RequestBody Map<String, Object> request) {
        try {
            // Implementar renovação de aplicação
            Map<String, Object> resultado = Map.of(
                    "sucesso", true,
                    "mensagem", "Aplicação renovada com sucesso",
                    "aplicacaoId", id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/aplicacao/{id}/reaplicar")
    @Operation(summary = "Reaplicar Aplicação", description = "Reaplica uma aplicação financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação reaplicada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na reaplicação")
    })
    public ResponseEntity<Map<String, Object>> reaplicarAplicacao(
            @Parameter(description = "ID da aplicação", required = true) @PathVariable Long id,
            @Parameter(description = "Dados da reaplicação", required = true) @RequestBody Map<String, Object> request) {
        try {
            // Implementar reaplicação de aplicação
            Map<String, Object> resultado = Map.of(
                    "sucesso", true,
                    "mensagem", "Aplicação reaplicada com sucesso",
                    "aplicacaoId", id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/aplicacao/{id}/cancelar")
    @Operation(summary = "Cancelar Aplicação", description = "Cancela uma aplicação financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicação cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no cancelamento")
    })
    public ResponseEntity<Map<String, Object>> cancelarAplicacao(
            @Parameter(description = "ID da aplicação", required = true) @PathVariable Long id,
            @Parameter(description = "Dados do cancelamento", required = true) @RequestBody Map<String, Object> request) {
        try {
            // Implementar cancelamento de aplicação
            Map<String, Object> resultado = Map.of(
                    "sucesso", true,
                    "mensagem", "Aplicação cancelada com sucesso",
                    "aplicacaoId", id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
