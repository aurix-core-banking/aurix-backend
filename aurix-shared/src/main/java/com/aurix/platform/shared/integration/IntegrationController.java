package com.aurix.platform.shared.integration;

import com.aurix.platform.shared.cache.SharedCacheService;
import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.dto.TransacaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Controller para APIs de integração entre módulos
 * 
 * Expõe endpoints para comunicação entre módulos da plataforma
 */
@RestController
@RequestMapping("/api/integration")
@Tag(name = "Integration", description = "APIs de integração entre módulos")
public class IntegrationController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IntegrationController.class);
    /**
     * Serviço de integração entre módulos.
     */
    private final IntegrationService integrationService;
    /**
     * Serviço de cache compartilhado.
     */
    private final SharedCacheService sharedCacheService;

    // ========== BUSCA UNIFICADA ==========
    /**
     * Busca cliente unificado
     */
    @GetMapping("/clientes/{clienteId}")
    @Operation(summary = "Buscar cliente unificado", description = "Busca cliente em todos os módulos da plataforma")
    public ResponseEntity<ClienteDTO> buscarClienteUnificado(@PathVariable String clienteId) {
        log.info("Buscando cliente unificado: {}", clienteId);
        // Primeiro tenta buscar no cache
        Optional<ClienteDTO> clienteCache = sharedCacheService.buscarCliente(clienteId);
        if (clienteCache.isPresent()) {
            log.info("Cliente {} encontrado no cache", clienteId);
            return ResponseEntity.ok(clienteCache.get());
        }
        // Se não encontrou no cache, busca via integração
        Optional<ClienteDTO> cliente = integrationService.buscarClienteUnificado(clienteId);
        if (cliente.isPresent()) {
            // Salva no cache para próximas consultas
            sharedCacheService.salvarCliente(clienteId, cliente.get());
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca conta unificada
     */
    @GetMapping("/contas/{contaId}")
    @Operation(summary = "Buscar conta unificada", description = "Busca conta em todos os módulos da plataforma")
    public ResponseEntity<ContaDTO> buscarContaUnificada(@PathVariable String contaId) {
        log.info("Buscando conta unificada: {}", contaId);
        // Primeiro tenta buscar no cache
        Optional<ContaDTO> contaCache = sharedCacheService.buscarConta(contaId);
        if (contaCache.isPresent()) {
            log.info("Conta {} encontrada no cache", contaId);
            return ResponseEntity.ok(contaCache.get());
        }
        // Se não encontrou no cache, busca via integração
        Optional<ContaDTO> conta = integrationService.buscarContaUnificada(contaId);
        if (conta.isPresent()) {
            // Salva no cache para próximas consultas
            sharedCacheService.salvarConta(contaId, conta.get());
            return ResponseEntity.ok(conta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca transação unificada
     */
    @GetMapping("/transacoes/{transacaoId}")
    @Operation(summary = "Buscar transação unificada", description = "Busca transação em todos os módulos da plataforma")
    public ResponseEntity<TransacaoDTO> buscarTransacaoUnificada(@PathVariable String transacaoId) {
        log.info("Buscando transação unificada: {}", transacaoId);
        // Primeiro tenta buscar no cache
        Optional<TransacaoDTO> transacaoCache = sharedCacheService.buscarTransacao(transacaoId);
        if (transacaoCache.isPresent()) {
            log.info("Transação {} encontrada no cache", transacaoId);
            return ResponseEntity.ok(transacaoCache.get());
        }
        // Se não encontrou no cache, busca via integração
        Optional<TransacaoDTO> transacao = integrationService.buscarTransacaoUnificada(transacaoId);
        if (transacao.isPresent()) {
            // Salva no cache para próximas consultas
            sharedCacheService.salvarTransacao(transacaoId, transacao.get());
            return ResponseEntity.ok(transacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== SINCRONIZAÇÃO ==========
    /**
     * Sincroniza conta com módulo financeiro
     */
    @PostMapping("/contas/{contaId}/sincronizar")
    @Operation(summary = "Sincronizar conta", description = "Sincroniza conta com módulo financeiro")
    public ResponseEntity<String> sincronizarConta(@PathVariable String contaId, @RequestParam String clienteId, @RequestParam BigDecimal saldoInicial) {
        log.info("Sincronizando conta: Conta={}, Cliente={}, Saldo={}", contaId, clienteId, saldoInicial);
        try {
            integrationService.sincronizarContaComFinanceiro(contaId, clienteId, saldoInicial);
            // Remove do cache para forçar atualização
            sharedCacheService.removerConta(contaId);
            sharedCacheService.removerCliente(clienteId);
            return ResponseEntity.ok("Conta sincronizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao sincronizar conta {}: {}", contaId, e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao sincronizar conta: " + e.getMessage());
        }
    }

    /**
     * Sincroniza transação com módulo financeiro
     */
    @PostMapping("/transacoes/{transacaoId}/sincronizar")
    @Operation(summary = "Sincronizar transação", description = "Sincroniza transação com módulo financeiro")
    public ResponseEntity<String> sincronizarTransacao(@PathVariable String transacaoId, @RequestParam String contaId, @RequestParam BigDecimal valor, @RequestParam String tipo) {
        log.info("Sincronizando transação: Transação={}, Conta={}, Valor={}, Tipo={}", transacaoId, contaId, valor, tipo);
        try {
            integrationService.sincronizarTransacaoComFinanceiro(transacaoId, contaId, valor, tipo);
            // Remove do cache para forçar atualização
            sharedCacheService.removerTransacao(transacaoId);
            sharedCacheService.removerConta(contaId);
            return ResponseEntity.ok("Transação sincronizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao sincronizar transação {}: {}", transacaoId, e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao sincronizar transação: " + e.getMessage());
        }
    }

    // ========== CÁLCULO DE TARIFAS ==========
    /**
     * Calcula tarifa dinâmica
     */
    @PostMapping("/tarifas/calcular")
    @Operation(summary = "Calcular tarifa dinâmica", description = "Calcula tarifa dinâmica para operação")
    public ResponseEntity<Map<String, Object>> calcularTarifaDinamica(@RequestParam String operacao, @RequestParam String clienteId, @RequestParam String produto, @RequestParam BigDecimal valorOperacao) {
        log.info("Calculando tarifa dinâmica: Operação={}, Cliente={}, Produto={}, Valor={}", operacao, clienteId, produto, valorOperacao);
        try {
            // Primeiro tenta buscar no cache
            Optional<Double> tarifaCache = sharedCacheService.buscarTarifa(operacao, clienteId, produto);
            if (tarifaCache.isPresent()) {
                log.info("Tarifa encontrada no cache: {}", tarifaCache.get());
                Map<String, Object> resultado = Map.of("tarifa", tarifaCache.get(), "fonte", "cache", "timestamp", java.time.LocalDateTime.now());
                return ResponseEntity.ok(resultado);
            }
            // Se não encontrou no cache, calcula via integração
            BigDecimal tarifa = integrationService.calcularTarifaDinamica(operacao, clienteId, produto, valorOperacao);
            // Salva no cache
            sharedCacheService.salvarTarifa(operacao, clienteId, produto, tarifa.doubleValue());
            Map<String, Object> resultado = Map.of("tarifa", tarifa, "fonte", "calculado", "timestamp", java.time.LocalDateTime.now());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Erro ao calcular tarifa: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro ao calcular tarifa: " + e.getMessage()));
        }
    }

    // ========== DASHBOARD INTEGRADO ==========
    /**
     * Obtém dashboard integrado
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard integrado", description = "Retorna dashboard com dados de todos os módulos")
    public ResponseEntity<Map<String, Object>> obterDashboardIntegrado() {
        log.info("Gerando dashboard integrado");
        try {
            Map<String, Object> dashboard = integrationService.obterDashboardIntegrado();
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            log.error("Erro ao gerar dashboard integrado: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro ao gerar dashboard: " + e.getMessage()));
        }
    }

    // ========== GESTÃO DE CACHE ==========
    /**
     * Limpa cache
     */
    @PostMapping("/cache/limpar")
    @Operation(summary = "Limpar cache", description = "Limpa todo o cache compartilhado")
    public ResponseEntity<String> limparCache() {
        log.info("Limpando cache compartilhado");
        try {
            sharedCacheService.limparCache();
            return ResponseEntity.ok("Cache limpo com sucesso");
        } catch (Exception e) {
            log.error("Erro ao limpar cache: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao limpar cache: " + e.getMessage());
        }
    }

    /**
     * Limpa cache por padrão
     */
    @PostMapping("/cache/limpar/{padrao}")
    @Operation(summary = "Limpar cache por padrão", description = "Limpa cache por padrão de chave")
    public ResponseEntity<String> limparCachePorPadrao(@PathVariable String padrao) {
        log.info("Limpando cache por padrão: {}", padrao);
        try {
            sharedCacheService.limparCachePorPadrao(padrao);
            return ResponseEntity.ok("Cache limpo por padrão: " + padrao);
        } catch (Exception e) {
            log.error("Erro ao limpar cache por padrão {}: {}", padrao, e.getMessage());
            return ResponseEntity.internalServerError().body("Erro ao limpar cache: " + e.getMessage());
        }
    }

    /**
     * Creates a new {@code IntegrationController} instance.
     *
     * @param integrationService Serviço de integração entre módulos.
     * @param sharedCacheService Serviço de cache compartilhado.
     */
    @java.lang.SuppressWarnings("all")
    public IntegrationController(final IntegrationService integrationService, final SharedCacheService sharedCacheService) {
        this.integrationService = integrationService;
        this.sharedCacheService = sharedCacheService;
    }
}
