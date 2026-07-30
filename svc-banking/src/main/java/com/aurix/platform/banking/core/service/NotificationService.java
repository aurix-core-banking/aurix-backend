package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.integration.webhook.EventPipelineService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    private final EventPipelineService eventPipelineService;

    public NotificationService(EventPipelineService eventPipelineService) {
        this.eventPipelineService = eventPipelineService;
    }

    public void notificarSettlementConcluido(String numeroLiquidez, Map<String, Object> detalhes) {
        eventPipelineService.dispatch("settlement.completed", numeroLiquidez, detalhes);
    }

    public void notificarSettlementFalha(String numeroLiquidez, String erro) {
        eventPipelineService.dispatch("settlement.failed", numeroLiquidez,
            Map.of("numeroLiquidez", numeroLiquidez, "erro", erro));
    }

    public void notificarReconciliacaoDivergente(String reconciliacaoId, Map<String, Object> detalhes) {
        eventPipelineService.dispatch("reconciliation.discrepancy", reconciliacaoId, detalhes);
    }

    public void notificarReconciliacaoConcluida(String reconciliacaoId, Map<String, Object> detalhes) {
        eventPipelineService.dispatch("reconciliation.completed", reconciliacaoId, detalhes);
    }
}
