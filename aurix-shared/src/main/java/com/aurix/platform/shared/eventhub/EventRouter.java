package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.event.ImpostoEvent;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Roteador inteligente de eventos.
 *
 * Determina as rotas corretas para cada evento baseado em regras de negócio.
 */
@Component
public class EventRouter {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventRouter.class);
    private final Map<String, List<String>> routingRules = new ConcurrentHashMap<>();
    private final AtomicLong totalEventsProcessed = new AtomicLong(0);
    private final AtomicLong totalRoutes = new AtomicLong(0);
    /**
     * Valor limite para monitoramento de compliance.
     */
    private static final int COMPLIANCE_THRESHOLD = 1000;

    public EventRouter() {
        initializeRoutingRules();
    }

    /**
     * Inicializa regras de roteamento
     */
    private void initializeRoutingRules() {
        log.info("Inicializando regras de roteamento do Event Hub");
        // Regras para eventos de conta
        routingRules.put("CONTA_CRIADA", Arrays.asList("conta-criada", "financial-sync", "audit-log", "analytics-tracking"));
        routingRules.put("CONTA_ATUALIZADA", Arrays.asList("conta-atualizada", "financial-sync", "audit-log"));
        routingRules.put("CONTA_BLOQUEADA", Arrays.asList("conta-bloqueada", "financial-sync", "audit-log", "compliance-alert"));
        // Regras para eventos de transação
        routingRules.put("TRANSACAO_REALIZADA", Arrays.asList("transacao-realizada", "financial-sync", "audit-log", "analytics-tracking", "compliance-monitoring"));
        routingRules.put("TRANSACAO_LIQUIDADA", Arrays.asList("transacao-liquidada", "financial-sync", "audit-log", "settlement-sync"));
        routingRules.put("TRANSACAO_CONCILIADA", Arrays.asList("transacao-conciliada", "financial-sync", "audit-log", "accounting-sync"));
        // Regras para eventos de imposto
        routingRules.put("IMPOSTO_CALCULADO", Arrays.asList("imposto-calculado", "tax-sync", "audit-log"));
        routingRules.put("IMPOSTO_REGISTRADO", Arrays.asList("imposto-registrado", "accounting-sync", "audit-log", "compliance-reporting"));
        // Regras para eventos de tarifa
        routingRules.put("TARIFA_CALCULADA", Arrays.asList("tarifa-calculada", "pricing-sync", "audit-log", "analytics-tracking"));
        // Regras para eventos de crédito
        routingRules.put("CREDITO_APROVADO", Arrays.asList("credito-aprovado", "credit-sync", "audit-log", "compliance-monitoring"));
        routingRules.put("CREDITO_REJEITADO", Arrays.asList("credito-rejeitado", "credit-sync", "audit-log", "analytics-tracking"));
        // Regras para eventos de tesouraria
        routingRules.put("INVESTIMENTO_REALIZADO", Arrays.asList("investimento-realizado", "treasury-sync", "audit-log", "analytics-tracking"));
        // Regras para eventos de compliance
        routingRules.put("COMPLIANCE_ALERT", Arrays.asList("compliance-alert", "audit-log", "compliance-monitoring", "risk-management"));
        // Regras para eventos de auditoria
        routingRules.put("AUDIT_LOG", Arrays.asList("audit-log", "analytics-tracking"));
        log.info("Regras de roteamento inicializadas: {} tipos de evento", routingRules.size());
    }

    /**
     * Determina as rotas para um evento.
     *
     * @param event Evento a ser roteado
     * @return Array de tópicos/rotas
     */
    public String[] getRoutes(final BaseEvent event) {
        totalEventsProcessed.incrementAndGet();
        String eventType = event.getEventType();
        List<String> routes = routingRules.get(eventType);
        if (routes == null || routes.isEmpty()) {
            log.warn("Event Router: Nenhuma rota encontrada para evento tipo: {}", eventType);
            return new String[] {"default-route"};
        }
        // Aplicar regras de negócio específicas
        List<String> finalRoutes = applyBusinessRules(event, new ArrayList<>(routes));
        totalRoutes.addAndGet(finalRoutes.size());
        log.debug("Event Router: Evento {} roteado para {} rotas: {}", event.getEventId(), finalRoutes.size(), finalRoutes);
        return finalRoutes.toArray(new String[0]);
    }

    /**
     * Aplica regras de negócio específicas.
     *
     * @param event  Evento original
     * @param routes Lista inicial de rotas
     * @return Lista filtrada de rotas
     */
    private List<String> applyBusinessRules(final BaseEvent event, final List<String> routes) {
        // Remover rotas baseado em condições específicas
        // Se evento é de conta bloqueada, não enviar para analytics
        if (event instanceof ContaEvent && "CONTA_BLOQUEADA".equals(event.getEventType())) {
            routes.remove("analytics-tracking");
        }
        // Se evento é de transação com valor baixo, não enviar para compliance
        if (event instanceof TransacaoEvent) {
            TransacaoEvent transacaoEvent = (TransacaoEvent) event;
            if (transacaoEvent.getValor().compareTo(java.math.BigDecimal.valueOf(COMPLIANCE_THRESHOLD)) < 0) {
                routes.remove("compliance-monitoring");
            }
        }
        // Se evento é de imposto com valor zero, não processar
        if (event instanceof ImpostoEvent) {
            ImpostoEvent impostoEvent = (ImpostoEvent) event;
            if (impostoEvent.getValorImposto().compareTo(java.math.BigDecimal.ZERO) == 0) {
                routes.remove("tax-sync");
                routes.remove("accounting-sync");
            }
        }
        // Aplicar filtros baseados em metadados
        Map<String, Object> metadata = event.getMetadata();
        if (metadata != null) {
            // Se evento tem flag de teste, não enviar para produção
            if (Boolean.TRUE.equals(metadata.get("testEvent"))) {
                routes.removeIf(route -> !route.contains("test"));
            }
            // Se evento tem prioridade baixa, remover rotas não críticas
            if ("LOW".equals(metadata.get("priority"))) {
                routes.remove("compliance-monitoring");
                routes.remove("risk-management");
            }
        }
        return routes;
    }

    /**
     * Adiciona nova regra de roteamento.
     *
     * @param eventType Tipo do evento
     * @param routes    Lista de rotas
     */
    public void addRoutingRule(final String eventType, final List<String> routes) {
        log.info("Adicionando regra de roteamento: {} -> {}", eventType, routes);
        routingRules.put(eventType, new ArrayList<>(routes));
    }

    /**
     * Remove regra de roteamento
     */
    public void removeRoutingRule(final String eventType) {
        log.info("Removendo regra de roteamento: {}", eventType);
        routingRules.remove(eventType);
    }

    /**
     * Obtém todas as regras de roteamento
     */
    public Map<String, List<String>> getAllRoutingRules() {
        return new HashMap<>(routingRules);
    }

    /**
     * Obtém rotas para um tipo de evento.
     *
     * @param eventType Tipo do evento
     * @return Lista de rotas configuradas
     */
    public List<String> getRoutesForEventType(final String eventType) {
        return routingRules.getOrDefault(eventType, Collections.emptyList());
    }

    /**
     * Verifica se existe rota para um tipo de evento.
     *
     * @param eventType Tipo do evento
     * @return true se existe configuração
     */
    public boolean hasRouteForEventType(final String eventType) {
        return routingRules.containsKey(eventType);
    }

    /**
     * Obtém total de eventos processados
     */
    public long getTotalEventsProcessed() {
        return totalEventsProcessed.get();
    }

    /**
     * Obtém total de rotas
     */
    public long getTotalRoutes() {
        return totalRoutes.get();
    }

    /**
     * Obtém estatísticas de roteamento
     */
    public Map<String, Object> getRoutingStatistics() {
        return Map.of("totalEventTypes", routingRules.size(), "totalEventsProcessed", totalEventsProcessed.get(), "totalRoutes", totalRoutes.get(), "averageRoutesPerEvent", totalEventsProcessed.get() > 0 ? (double) totalRoutes.get() / totalEventsProcessed.get() : 0.0);
    }
}
