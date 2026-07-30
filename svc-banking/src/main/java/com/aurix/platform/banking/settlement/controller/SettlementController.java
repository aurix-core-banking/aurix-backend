package com.aurix.platform.banking.settlement.controller;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.service.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/settlement")
@Tag(name = "Settlement", description = "APIs para liquidação automática e sistema de pagamentos")
public class SettlementController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettlementController.class);
    private final SettlementService settlementService;

    @PostMapping("/processar")
    @Operation(summary = "Processar liquidação", description = "Processa liquidação de uma transação")
    public ResponseEntity<Liquidez> processarLiquidez(@RequestBody Liquidez liquidez) {
        log.info("Processando liquidação: {}", liquidez.getNumeroLiquidez());
        Liquidez liquidezProcessada = settlementService.processarLiquidez(liquidez);
        return ResponseEntity.ok(liquidezProcessada);
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Buscar liquidações pendentes", description = "Busca todas as liquidações pendentes de processamento")
    public ResponseEntity<List<Liquidez>> buscarLiquidezPendentes() {
        log.info("Buscando liquidações pendentes");
        List<Liquidez> pendentes = settlementService.buscarLiquidezPendentes();
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard de liquidação", description = "Retorna informações consolidadas sobre liquidações")
    public ResponseEntity<DashboardSettlement> obterDashboardSettlement() {
        log.info("Gerando dashboard de liquidação");
        List<Liquidez> pendentes = settlementService.buscarLiquidezPendentes();
        DashboardSettlement dashboard = DashboardSettlement.builder().liquidezPendentes(pendentes.size()).statusSistema("ATIVO").ultimaAtualizacao(LocalDate.now()).build();
        return ResponseEntity.ok(dashboard);
    }

    public static class DashboardSettlement {
        private Integer liquidezPendentes;
        private String statusSistema;
        private LocalDate ultimaAtualizacao;

        @java.lang.SuppressWarnings("all")
        DashboardSettlement() {
        }

        @java.lang.SuppressWarnings("all")
        DashboardSettlement(final Integer liquidezPendentes, final String statusSistema, final LocalDate ultimaAtualizacao) {
            this.liquidezPendentes = liquidezPendentes;
            this.statusSistema = statusSistema;
            this.ultimaAtualizacao = ultimaAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public static class DashboardSettlementBuilder {
            @java.lang.SuppressWarnings("all")
            private Integer liquidezPendentes;
            @java.lang.SuppressWarnings("all")
            private String statusSistema;
            @java.lang.SuppressWarnings("all")
            private LocalDate ultimaAtualizacao;

            @java.lang.SuppressWarnings("all")
            DashboardSettlementBuilder() {
            }

            @java.lang.SuppressWarnings("all")
            public SettlementController.DashboardSettlement.DashboardSettlementBuilder liquidezPendentes(final Integer liquidezPendentes) {
                this.liquidezPendentes = liquidezPendentes;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public SettlementController.DashboardSettlement.DashboardSettlementBuilder statusSistema(final String statusSistema) {
                this.statusSistema = statusSistema;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public SettlementController.DashboardSettlement.DashboardSettlementBuilder ultimaAtualizacao(final LocalDate ultimaAtualizacao) {
                this.ultimaAtualizacao = ultimaAtualizacao;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public SettlementController.DashboardSettlement build() {
                return new SettlementController.DashboardSettlement(this.liquidezPendentes, this.statusSistema, this.ultimaAtualizacao);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "SettlementController.DashboardSettlement.DashboardSettlementBuilder(liquidezPendentes=" + this.liquidezPendentes + ", statusSistema=" + this.statusSistema + ", ultimaAtualizacao=" + this.ultimaAtualizacao + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static SettlementController.DashboardSettlement.DashboardSettlementBuilder builder() {
            return new SettlementController.DashboardSettlement.DashboardSettlementBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public Integer getLiquidezPendentes() {
            return this.liquidezPendentes;
        }

        @java.lang.SuppressWarnings("all")
        public String getStatusSistema() {
            return this.statusSistema;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDate getUltimaAtualizacao() {
            return this.ultimaAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setLiquidezPendentes(final Integer liquidezPendentes) {
            this.liquidezPendentes = liquidezPendentes;
        }

        @java.lang.SuppressWarnings("all")
        public void setStatusSistema(final String statusSistema) {
            this.statusSistema = statusSistema;
        }

        @java.lang.SuppressWarnings("all")
        public void setUltimaAtualizacao(final LocalDate ultimaAtualizacao) {
            this.ultimaAtualizacao = ultimaAtualizacao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public SettlementController(final SettlementService settlementService) {
        this.settlementService = settlementService;
    }
}
