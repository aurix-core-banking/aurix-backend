package com.aurix.platform.banking.core.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardDTO {

    private ResumoSettlementDTO settlement;
    private ResumoReconciliacaoDTO reconciliacao;
    private ResumoWebhookDTO webhook;
    private ResumoTransacoesDTO transacoes;

    public DashboardDTO() {}

    public DashboardDTO(ResumoSettlementDTO settlement, ResumoReconciliacaoDTO reconciliacao,
                        ResumoWebhookDTO webhook, ResumoTransacoesDTO transacoes) {
        this.settlement = settlement;
        this.reconciliacao = reconciliacao;
        this.webhook = webhook;
        this.transacoes = transacoes;
    }

    public ResumoSettlementDTO getSettlement() { return settlement; }
    public void setSettlement(ResumoSettlementDTO settlement) { this.settlement = settlement; }
    public ResumoReconciliacaoDTO getReconciliacao() { return reconciliacao; }
    public void setReconciliacao(ResumoReconciliacaoDTO reconciliacao) { this.reconciliacao = reconciliacao; }
    public ResumoWebhookDTO getWebhook() { return webhook; }
    public void setWebhook(ResumoWebhookDTO webhook) { this.webhook = webhook; }
    public ResumoTransacoesDTO getTransacoes() { return transacoes; }
    public void setTransacoes(ResumoTransacoesDTO transacoes) { this.transacoes = transacoes; }

    public static class ResumoSettlementDTO {
        private long pendentes;
        private long processados;
        private long falhas;
        private long total;

        public ResumoSettlementDTO() {}
        public ResumoSettlementDTO(long pendentes, long processados, long falhas, long total) {
            this.pendentes = pendentes; this.processados = processados; this.falhas = falhas; this.total = total;
        }
        public long getPendentes() { return pendentes; }
        public long getProcessados() { return processados; }
        public long getFalhas() { return falhas; }
        public long getTotal() { return total; }
    }

    public static class ResumoReconciliacaoDTO {
        private long conciliacoesPendentes;
        private long conciliacoesDivergentes;
        private long reconciliacoesRealizadas;
        private long totalConciliacoes;

        public ResumoReconciliacaoDTO() {}
        public ResumoReconciliacaoDTO(long pendentes, long divergentes, long realizadas, long total) {
            this.conciliacoesPendentes = pendentes; this.conciliacoesDivergentes = divergentes;
            this.reconciliacoesRealizadas = realizadas; this.totalConciliacoes = total;
        }
        public long getConciliacoesPendentes() { return conciliacoesPendentes; }
        public long getConciliacoesDivergentes() { return conciliacoesDivergentes; }
        public long getReconciliacoesRealizadas() { return reconciliacoesRealizadas; }
        public long getTotalConciliacoes() { return totalConciliacoes; }
    }

    public static class ResumoWebhookDTO {
        private long pendentes;
        private long falhas;
        private long entregues;
        private long exauridos;

        public ResumoWebhookDTO() {}
        public ResumoWebhookDTO(long pendentes, long falhas, long entregues, long exauridos) {
            this.pendentes = pendentes; this.falhas = falhas; this.entregues = entregues; this.exauridos = exauridos;
        }
        public long getPendentes() { return pendentes; }
        public long getFalhas() { return falhas; }
        public long getEntregues() { return entregues; }
        public long getExauridos() { return exauridos; }
    }

    public static class ResumoTransacoesDTO {
        private long totalHoje;
        private BigDecimal volumeHoje;
        private List<Map<String, Object>> porTipo;
        private long totalPeriodo;

        public ResumoTransacoesDTO() {}
        public ResumoTransacoesDTO(long totalHoje, BigDecimal volumeHoje, List<Map<String, Object>> porTipo, long totalPeriodo) {
            this.totalHoje = totalHoje; this.volumeHoje = volumeHoje;
            this.porTipo = porTipo; this.totalPeriodo = totalPeriodo;
        }
        public long getTotalHoje() { return totalHoje; }
        public BigDecimal getVolumeHoje() { return volumeHoje; }
        public List<Map<String, Object>> getPorTipo() { return porTipo; }
        public long getTotalPeriodo() { return totalPeriodo; }
    }
}
