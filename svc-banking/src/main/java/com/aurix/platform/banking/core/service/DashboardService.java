package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.DashboardDTO;
import com.aurix.platform.banking.core.dto.DashboardDTO.ResumoReconciliacaoDTO;
import com.aurix.platform.banking.core.dto.DashboardDTO.ResumoSettlementDTO;
import com.aurix.platform.banking.core.dto.DashboardDTO.ResumoTransacoesDTO;
import com.aurix.platform.banking.core.dto.DashboardDTO.ResumoWebhookDTO;
import com.aurix.platform.banking.core.entity.MovimentoConta;
import com.aurix.platform.banking.core.repository.MovimentoContaRepository;
import com.aurix.platform.banking.core.repository.ReconciliacaoRepository;
import com.aurix.platform.banking.integration.webhook.WebhookEvent;
import com.aurix.platform.banking.integration.webhook.WebhookEventRepository;
import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final LiquidezRepository liquidezRepository;
    private final ReconciliacaoRepository reconciliacaoRepository;
    private final WebhookEventRepository webhookEventRepository;
    private final MovimentoContaRepository movimentoContaRepository;

    public DashboardService(LiquidezRepository liquidezRepository,
                            ReconciliacaoRepository reconciliacaoRepository,
                            WebhookEventRepository webhookEventRepository,
                            MovimentoContaRepository movimentoContaRepository) {
        this.liquidezRepository = liquidezRepository;
        this.reconciliacaoRepository = reconciliacaoRepository;
        this.webhookEventRepository = webhookEventRepository;
        this.movimentoContaRepository = movimentoContaRepository;
    }

    public DashboardDTO obterDashboard() {
        return new DashboardDTO(
            obterResumoSettlement(),
            obterResumoReconciliacao(),
            obterResumoWebhook(),
            obterResumoTransacoes()
        );
    }

    private ResumoSettlementDTO obterResumoSettlement() {
        return new ResumoSettlementDTO(
            liquidezRepository.countByStatus(Liquidez.StatusLiquidez.PENDENTE),
            liquidezRepository.countByStatus(Liquidez.StatusLiquidez.LIQUIDADO),
            liquidezRepository.countByStatus(Liquidez.StatusLiquidez.FALHA),
            liquidezRepository.count()
        );
    }

    private ResumoReconciliacaoDTO obterResumoReconciliacao() {
        return new ResumoReconciliacaoDTO(0, 0, 0, reconciliacaoRepository.count());
    }

    private ResumoWebhookDTO obterResumoWebhook() {
        return new ResumoWebhookDTO(
            webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.PENDING),
            webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.FAILED),
            webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.DELIVERED),
            webhookEventRepository.countByStatus(WebhookEvent.WebhookEventStatus.EXHAUSTED)
        );
    }

    private ResumoTransacoesDTO obterResumoTransacoes() {
        List<MovimentoConta> movimentosHoje = movimentoContaRepository.findAll().stream()
            .filter(m -> m.getDataMovimento() != null
                && m.getDataMovimento().toLocalDate().equals(LocalDate.now()))
            .toList();
        long totalHoje = movimentosHoje.size();
        BigDecimal volumeHoje = movimentosHoje.stream()
            .map(MovimentoConta::getValorMovimento)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Long> porTipo = movimentosHoje.stream()
            .filter(m -> m.getTipoMovimento() != null)
            .collect(Collectors.groupingBy(
                m -> m.getTipoMovimento().name(), Collectors.counting()));
        List<Map<String, Object>> porTipoList = porTipo.entrySet().stream()
            .map(e -> Map.<String, Object>of("tipo", e.getKey(), "total", e.getValue()))
            .toList();
        long totalPeriodo = movimentoContaRepository.count();
        return new ResumoTransacoesDTO(totalHoje, volumeHoje, porTipoList, totalPeriodo);
    }
}
