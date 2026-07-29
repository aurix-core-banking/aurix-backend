package com.aurix.platform.shared.integration;

import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço de integração entre módulos Aurix
 * 
 * Gerencia comunicação síncrona entre todos os módulos da plataforma
 */
@Service
public class IntegrationService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IntegrationService.class);
    /**
     * REST template for HTTP communication.
     */
    private final RestTemplate restTemplate;
    /**
     * URL base do módulo Core.
     */
    @Value("${aurix.integration.core.url:http://localhost:8080}")
    private String coreUrl;
    /**
     * URL base do módulo Financial.
     */
    @Value("${aurix.integration.financial.url:http://localhost:8081}")
    private String financialUrl;
    /**
     * URL base do módulo Controller.
     */
    @Value("${aurix.integration.controller.url:http://localhost:8082}")
    private String controllerUrl;
    /**
     * URL base do módulo Tax.
     */
    @Value("${aurix.integration.tax.url:http://localhost:8083}")
    private String taxUrl;
    /**
     * URL base do módulo Accounting.
     */
    @Value("${aurix.integration.accounting.url:http://localhost:8084}")
    private String accountingUrl;
    /**
     * URL base do módulo Pricing.
     */
    @Value("${aurix.integration.pricing.url:http://localhost:8105}")
    private String pricingUrl;

    // ========== INTEGRAÇÃO CORE → FINANCIAL ==========
    /**
     * Sincroniza conta criada no core com o módulo financeiro
     */
    public void sincronizarContaComFinanceiro(final String contaId, String clienteId, BigDecimal saldoInicial) {
        log.info("SAGA: sincronizando conta {} com modulo financeiro", contaId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("contaId", contaId);
        payload.put("clienteId", clienteId);
        payload.put("saldoInicial", saldoInicial);
        payload.put("dataCriacao", LocalDateTime.now());
        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, buildHeaders(contaId));
            ResponseEntity<Map> response = restTemplate.exchange(
                financialUrl + "/api/financial/sincronizar/contas", HttpMethod.POST, entity, Map.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Falha ao sincronizar conta " + contaId + ": HTTP " + response.getStatusCode());
            }
            log.info("SAGA: conta {} sincronizada com sucesso", contaId);
        } catch (Exception e) {
            log.error("SAGA: erro ao sincronizar conta {}, executando compensacao: {}", contaId, e.getMessage());
            compensarSyncConta(contaId);
            throw new RuntimeException("Saga falhou ao sincronizar conta " + contaId, e);
        }
    }

    /**
     * Sincroniza transação realizada no core com o módulo financeiro
     */
    public void sincronizarTransacaoComFinanceiro(final String transacaoId, String contaId, BigDecimal valor, String tipo) {
        log.info("SAGA: sincronizando transacao {} com modulo financeiro", transacaoId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("transacaoId", transacaoId);
        payload.put("contaId", contaId);
        payload.put("valor", valor);
        payload.put("tipo", tipo);
        payload.put("dataTransacao", LocalDateTime.now());
        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, buildHeaders(transacaoId));
            ResponseEntity<Map> response = restTemplate.exchange(
                financialUrl + "/api/financial/sincronizar/transacoes", HttpMethod.POST, entity, Map.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Falha ao sincronizar transacao " + transacaoId + ": HTTP " + response.getStatusCode());
            }
            log.info("SAGA: transacao {} sincronizada com sucesso", transacaoId);
        } catch (Exception e) {
            log.error("SAGA: erro ao sincronizar transacao {}, executando compensacao: {}", transacaoId, e.getMessage());
            compensarSyncTransacao(transacaoId);
            throw new RuntimeException("Saga falhou ao sincronizar transacao " + transacaoId, e);
        }
    }

    private void compensarSyncConta(String contaId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Compensation", "true");
            restTemplate.exchange(financialUrl + "/api/financial/sincronizar/contas/" + contaId,
                HttpMethod.DELETE, new HttpEntity<>(headers), Map.class);
            log.info("SAGA: compensacao da conta {} executada", contaId);
        } catch (Exception e) {
            log.error("SAGA: compensacao da conta {} tambem falhou: {}", contaId, e.getMessage());
        }
    }

    private void compensarSyncTransacao(String transacaoId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Compensation", "true");
            restTemplate.exchange(financialUrl + "/api/financial/sincronizar/transacoes/" + transacaoId,
                HttpMethod.DELETE, new HttpEntity<>(headers), Map.class);
            log.info("SAGA: compensacao da transacao {} executada", transacaoId);
        } catch (Exception e) {
            log.error("SAGA: compensacao da transacao {} tambem falhou: {}", transacaoId, e.getMessage());
        }
    }

    private HttpHeaders buildHeaders(String idempotencyKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Idempotency-Key", "saga-" + idempotencyKey);
        return headers;
    }

    // ========== INTEGRAÇÃO FINANCIAL → CONTROLLER ==========
    /**
     * Envia dados para controladoria
     */
    public void enviarDadosControladoria(final String transacaoId, String centroCusto, BigDecimal valor, String categoria) {
        log.info("Enviando dados para controladoria: Transação={}, Centro={}, Valor={}", transacaoId, centroCusto, valor);
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("transacaoId", transacaoId);
            payload.put("centroCusto", centroCusto);
            payload.put("valor", valor);
            payload.put("categoria", categoria);
            payload.put("dataProcessamento", LocalDateTime.now());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.exchange(controllerUrl + "/api/controller/transacoes/processar", HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Dados enviados com sucesso para controladoria: {}", transacaoId);
            } else {
                log.error("Erro ao enviar dados para controladoria: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao enviar dados para controladoria: {}", e.getMessage());
        }
    }

    // ========== INTEGRAÇÃO CONTROLLER → TAX ==========
    /**
     * Calcula impostos para lançamento contábil
     */
    public BigDecimal calcularImpostos(String lancamentoId, BigDecimal valorBase, String tipoOperacao) {
        log.info("Calculando impostos para lançamento {}: Valor={}, Tipo={}", lancamentoId, valorBase, tipoOperacao);
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("lancamentoId", lancamentoId);
            payload.put("valorBase", valorBase);
            payload.put("tipoOperacao", tipoOperacao);
            payload.put("dataCalculo", LocalDateTime.now());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.exchange(taxUrl + "/api/tax/impostos/calcular", HttpMethod.POST, entity, Map.class);
            Map<String, Object> resultado = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && resultado != null) {
                Object valorObj = resultado.get("valorImposto");
                if (valorObj != null) {
                    BigDecimal valorImposto = new BigDecimal(valorObj.toString());
                    log.info("Impostos calculados para lançamento {}: {}", lancamentoId, valorImposto);
                    return valorImposto;
                }
                log.warn("Valor de imposto não encontrado na resposta para lançamento {}", lancamentoId);
                return BigDecimal.ZERO;
            } else {
                log.error("Erro ao calcular impostos: {}", response.getStatusCode());
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            log.error("Erro ao calcular impostos: {}", e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    // ========== INTEGRAÇÃO TAX → ACCOUNTING ==========
    /**
     * Registra imposto no módulo contábil
     */
    public void registrarImpostoContabil(final String impostoId, String contaContabil, BigDecimal valorImposto, String tipoImposto) {
        log.info("Registrando imposto {} no módulo contábil: Conta={}, Valor={}", impostoId, contaContabil, valorImposto);
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("impostoId", impostoId);
            payload.put("contaContabil", contaContabil);
            payload.put("valorImposto", valorImposto);
            payload.put("tipoImposto", tipoImposto);
            payload.put("dataRegistro", LocalDateTime.now());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.exchange(accountingUrl + "/api/accounting/impostos/registrar", HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Imposto {} registrado com sucesso no módulo contábil", impostoId);
            } else {
                log.error("Erro ao registrar imposto {} no módulo contábil: {}", impostoId, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao registrar imposto {} no módulo contábil: {}", impostoId, e.getMessage());
        }
    }

    // ========== INTEGRAÇÃO PRICING ==========
    /**
     * Calcula tarifa dinâmica
     */
    public BigDecimal calcularTarifaDinamica(String operacao, String clienteId, String produto, BigDecimal valorOperacao) {
        log.info("Calculando tarifa dinâmica: Operação={}, Cliente={}, Produto={}, Valor={}", operacao, clienteId, produto, valorOperacao);
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("operacao", operacao);
            payload.put("clienteId", clienteId);
            payload.put("produto", produto);
            payload.put("valorOperacao", valorOperacao);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.exchange(pricingUrl + "/api/pricing/calcular", HttpMethod.POST, entity, Map.class);
            Map<String, Object> resultado = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && resultado != null) {
                Object tarifaObj = resultado.get("tarifa");
                if (tarifaObj != null) {
                    BigDecimal tarifa = new BigDecimal(tarifaObj.toString());
                    log.info("Tarifa calculada: {}", tarifa);
                    return tarifa;
                }
                log.warn("Tarifa não encontrada na resposta");
                return BigDecimal.ZERO;
            } else {
                log.error("Erro ao calcular tarifa: {}", response.getStatusCode());
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            log.error("Erro ao calcular tarifa: {}", e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    // ========== BUSCA UNIFICADA ==========
    /**
     * Busca cliente unificado
     */
    public Optional<ClienteDTO> buscarClienteUnificado(String clienteId) {
        log.info("Buscando cliente unificado: {}", clienteId);
        try {
            ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(coreUrl + "/api/core/clientes/" + clienteId, ClienteDTO.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            } else {
                log.warn("Cliente {} não encontrado", clienteId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar cliente {}: {}", clienteId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Busca conta unificada
     */
    public Optional<ContaDTO> buscarContaUnificada(String contaId) {
        log.info("Buscando conta unificada: {}", contaId);
        try {
            ResponseEntity<ContaDTO> response = restTemplate.getForEntity(coreUrl + "/api/core/contas/" + contaId, ContaDTO.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            } else {
                log.warn("Conta {} não encontrada", contaId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar conta {}: {}", contaId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Busca transação unificada
     */
    public Optional<TransacaoDTO> buscarTransacaoUnificada(String transacaoId) {
        log.info("Buscando transação unificada: {}", transacaoId);
        try {
            ResponseEntity<TransacaoDTO> response = restTemplate.getForEntity(coreUrl + "/api/core/transacoes/" + transacaoId, TransacaoDTO.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            } else {
                log.warn("Transação {} não encontrada", transacaoId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar transação {}: {}", transacaoId, e.getMessage());
            return Optional.empty();
        }
    }

    // ========== DASHBOARD INTEGRADO ==========
    /**
     * Obtém dados para dashboard integrado
     */
    public Map<String, Object> obterDashboardIntegrado() {
        log.info("Gerando dashboard integrado");
        Map<String, Object> dashboard = new HashMap<>();
        try {
            // Dados do Core
            ResponseEntity<Map> coreResponse = restTemplate.getForEntity(coreUrl + "/api/core/dashboard", Map.class);
            if (coreResponse.getStatusCode().is2xxSuccessful() && coreResponse.getBody() != null) {
                dashboard.put("core", coreResponse.getBody());
            }
            // Dados do Financial
            ResponseEntity<Map> financialResponse = restTemplate.getForEntity(financialUrl + "/api/financial/dashboard", Map.class);
            if (financialResponse.getStatusCode().is2xxSuccessful() && financialResponse.getBody() != null) {
                dashboard.put("financial", financialResponse.getBody());
            }
            // Dados do Accounting
            ResponseEntity<Map> accountingResponse = restTemplate.getForEntity(accountingUrl + "/api/accounting/dashboard", Map.class);
            if (accountingResponse.getStatusCode().is2xxSuccessful() && accountingResponse.getBody() != null) {
                dashboard.put("accounting", accountingResponse.getBody());
            }
            // Dados do Pricing
            ResponseEntity<Map> pricingResponse = restTemplate.getForEntity(pricingUrl + "/api/pricing/dashboard", Map.class);
            if (pricingResponse.getStatusCode().is2xxSuccessful() && pricingResponse.getBody() != null) {
                dashboard.put("pricing", pricingResponse.getBody());
            }
            dashboard.put("timestamp", LocalDateTime.now());
            dashboard.put("status", "SUCCESS");
        } catch (Exception e) {
            log.error("Erro ao gerar dashboard integrado: {}", e.getMessage());
            dashboard.put("status", "ERROR");
            dashboard.put("error", e.getMessage());
        }
        return dashboard;
    }

    /**
     * Creates a new {@code IntegrationService} instance.
     *
     * @param restTemplate REST template for HTTP communication.
     */
    @java.lang.SuppressWarnings("all")
    public IntegrationService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
