package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.integration.webhook.EventPipelineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private EventPipelineService eventPipelineService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(eventPipelineService);
    }

    @Test
    void notificarSettlementConcluido_deveDispararEvento() {
        Map<String, Object> detalhes = Map.of("numeroLiquidez", "LIQ-001", "valor", 1000);

        notificationService.notificarSettlementConcluido("LIQ-001", detalhes);

        verify(eventPipelineService).dispatch("settlement.completed", "LIQ-001", detalhes);
    }

    @Test
    void notificarSettlementFalha_deveDispararEvento() {
        notificationService.notificarSettlementFalha("LIQ-002", "Saldo insuficiente");

        verify(eventPipelineService).dispatch(eq("settlement.failed"), eq("LIQ-002"), any());
    }

    @Test
    void notificarReconciliacaoDivergente_deveDispararEvento() {
        Map<String, Object> detalhes = Map.of("reconciliacaoId", "REC-001", "diferenca", 500);

        notificationService.notificarReconciliacaoDivergente("REC-001", detalhes);

        verify(eventPipelineService).dispatch("reconciliation.discrepancy", "REC-001", detalhes);
    }

    @Test
    void notificarReconciliacaoConcluida_deveDispararEvento() {
        Map<String, Object> detalhes = Map.of("reconciliacaoId", "REC-002");

        notificationService.notificarReconciliacaoConcluida("REC-002", detalhes);

        verify(eventPipelineService).dispatch("reconciliation.completed", "REC-002", detalhes);
    }
}
