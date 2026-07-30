package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.DashboardDTO;
import com.aurix.platform.banking.core.entity.MovimentoConta;
import com.aurix.platform.banking.core.repository.MovimentoContaRepository;
import com.aurix.platform.banking.core.repository.ReconciliacaoRepository;
import com.aurix.platform.banking.integration.webhook.WebhookEvent;
import com.aurix.platform.banking.integration.webhook.WebhookEventRepository;
import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private LiquidezRepository liquidezRepository;
    @Mock
    private ReconciliacaoRepository reconciliacaoRepository;
    @Mock
    private WebhookEventRepository webhookEventRepository;
    @Mock
    private MovimentoContaRepository movimentoContaRepository;

    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        dashboardService = new DashboardService(liquidezRepository, reconciliacaoRepository,
            webhookEventRepository, movimentoContaRepository);
    }

    @Test
    void obterDashboard_deveRetornarTodasAsSecoes() {
        when(liquidezRepository.countByStatus(Liquidez.StatusLiquidez.PENDENTE)).thenReturn(3L);
        when(liquidezRepository.countByStatus(Liquidez.StatusLiquidez.LIQUIDADO)).thenReturn(10L);
        when(liquidezRepository.countByStatus(Liquidez.StatusLiquidez.FALHA)).thenReturn(1L);
        when(liquidezRepository.count()).thenReturn(14L);
        when(reconciliacaoRepository.count()).thenReturn(5L);

        when(webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.PENDING)).thenReturn(2L);
        when(webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.FAILED)).thenReturn(1L);
        when(webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.DELIVERED)).thenReturn(8L);
        when(webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.EXHAUSTED)).thenReturn(0L);

        MovimentoConta mov = new MovimentoConta();
        mov.setValorMovimento(new BigDecimal("500.00"));
        mov.setTipoMovimento(MovimentoConta.TipoMovimento.CREDITO);
        mov.setDataMovimento(LocalDateTime.now());
        when(movimentoContaRepository.findAll()).thenReturn(List.of(mov));
        when(movimentoContaRepository.count()).thenReturn(100L);

        DashboardDTO dto = dashboardService.obterDashboard();

        assertNotNull(dto);
        assertEquals(3, dto.getSettlement().getPendentes());
        assertEquals(10, dto.getSettlement().getProcessados());
        assertEquals(1, dto.getSettlement().getFalhas());
        assertEquals(14, dto.getSettlement().getTotal());

        assertEquals(2, dto.getWebhook().getPendentes());
        assertEquals(1, dto.getWebhook().getFalhas());
        assertEquals(8, dto.getWebhook().getEntregues());
        assertEquals(0, dto.getWebhook().getExauridos());

        assertEquals(5, dto.getReconciliacao().getTotalConciliacoes());
        assertEquals(100, dto.getTransacoes().getTotalPeriodo());
        assertEquals(1, dto.getTransacoes().getTotalHoje());
        assertEquals(0, new BigDecimal("500.00").compareTo(dto.getTransacoes().getVolumeHoje()));
    }
}
