package com.aurix.platform.banking.pricing.controller;

import com.aurix.platform.banking.core.entity.PacoteTarifas;
import com.aurix.platform.banking.pricing.entity.SimulacaoTarifas;
import com.aurix.platform.banking.pricing.service.PricingEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para APIs do motor de tarifas
 * 
 * Gerencia todas as operações relacionadas ao motor de tarifas dinâmico
 */
@RestController
@RequestMapping("/pricing")
@Tag(name = "Pricing Engine", description = "APIs para motor de tarifas dinâmico")
public class PricingController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PricingController.class);
    private final PricingEngineService pricingEngineService;

    /**
     * Calcula tarifa para uma operação
     */
    @PostMapping("/calcular")
    @Operation(summary = "Calcular tarifa", description = "Calcula tarifa dinâmica para uma operação específica")
    public ResponseEntity<BigDecimal> calcularTarifa(@Parameter(description = "Tipo de operação") @RequestParam String operacao, @Parameter(description = "ID do cliente") @RequestParam String clienteId, @Parameter(description = "Produto") @RequestParam String produto, @Parameter(description = "Valor da operação") @RequestParam BigDecimal valorOperacao, @Parameter(description = "Canal da operação") @RequestParam(defaultValue = "INTERNET_BANKING") String canal) {
        log.info("Calculando tarifa: Operação={}, Cliente={}, Produto={}, Valor={}, Canal={}", operacao, clienteId, produto, valorOperacao, canal);
        BigDecimal tarifa = pricingEngineService.calcularTarifa(operacao, clienteId, produto, valorOperacao, canal);
        return ResponseEntity.ok(tarifa);
    }

    /**
     * Simula tarifas para diferentes cenários
     */
    @PostMapping("/simular")
    @Operation(summary = "Simular tarifas", description = "Simula tarifas para diferentes cenários e volumes")
    public ResponseEntity<SimulacaoTarifas> simularTarifas(@Parameter(description = "ID do cliente") @RequestParam String clienteId, @Parameter(description = "Produto") @RequestParam String produto, @Parameter(description = "Volume de operações") @RequestParam Integer volumeOperacoes, @Parameter(description = "Valor total das operações") @RequestParam BigDecimal valorTotalOperacoes, @Parameter(description = "Período em meses") @RequestParam(defaultValue = "12") Integer periodoMeses) {
        log.info("Simulando tarifas: Cliente={}, Produto={}, Volume={}, Valor={}, Período={} meses", clienteId, produto, volumeOperacoes, valorTotalOperacoes, periodoMeses);
        SimulacaoTarifas simulacao = pricingEngineService.simularTarifas(clienteId, produto, volumeOperacoes, valorTotalOperacoes, periodoMeses);
        if (simulacao != null) {
            return ResponseEntity.ok(simulacao);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Cria pacote personalizado de tarifas
     */
    @PostMapping("/pacote/personalizado")
    @Operation(summary = "Criar pacote personalizado", description = "Cria um pacote personalizado de tarifas para um cliente")
    public ResponseEntity<PacoteTarifas> criarPacotePersonalizado(@Parameter(description = "ID do cliente") @RequestParam String clienteId, @Parameter(description = "Nome do pacote") @RequestParam String nomePacote, @Parameter(description = "Códigos das tarifas") @RequestParam List<String> codigosTarifas, @Parameter(description = "Percentual de desconto") @RequestParam BigDecimal descontoPercentual) {
        log.info("Criando pacote personalizado: Cliente={}, Nome={}, Desconto={}%", clienteId, nomePacote, descontoPercentual);
        PacoteTarifas pacote = pricingEngineService.criarPacotePersonalizado(clienteId, nomePacote, codigosTarifas, descontoPercentual);
        if (pacote != null) {
            return ResponseEntity.ok(pacote);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Dashboard do motor de tarifas
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard de tarifas", description = "Retorna informações consolidadas sobre o motor de tarifas")
    public ResponseEntity<DashboardPricing> obterDashboardPricing() {
        log.info("Gerando dashboard de tarifas");
        DashboardPricing dashboard = DashboardPricing.builder().statusMotor("ATIVO").totalTarifas(150).totalPacotes(25).simulacoesHoje(12).economiaTotalMes(BigDecimal.valueOf(50000.0)).ultimaAtualizacao(java.time.LocalDate.now()).build();
        return ResponseEntity.ok(dashboard);
    }


    /**
     * Classe para dashboard de tarifas
     */
    public static class DashboardPricing {
        private String statusMotor;
        private Integer totalTarifas;
        private Integer totalPacotes;
        private Integer simulacoesHoje;
        private BigDecimal economiaTotalMes;
        private java.time.LocalDate ultimaAtualizacao;

        @java.lang.SuppressWarnings("all")
        DashboardPricing() {
        }

        @java.lang.SuppressWarnings("all")
        DashboardPricing(final String statusMotor, final Integer totalTarifas, final Integer totalPacotes, final Integer simulacoesHoje, final BigDecimal economiaTotalMes, final java.time.LocalDate ultimaAtualizacao) {
            this.statusMotor = statusMotor;
            this.totalTarifas = totalTarifas;
            this.totalPacotes = totalPacotes;
            this.simulacoesHoje = simulacoesHoje;
            this.economiaTotalMes = economiaTotalMes;
            this.ultimaAtualizacao = ultimaAtualizacao;
        }


        @java.lang.SuppressWarnings("all")
        public static class DashboardPricingBuilder {
            @java.lang.SuppressWarnings("all")
            private String statusMotor;
            @java.lang.SuppressWarnings("all")
            private Integer totalTarifas;
            @java.lang.SuppressWarnings("all")
            private Integer totalPacotes;
            @java.lang.SuppressWarnings("all")
            private Integer simulacoesHoje;
            @java.lang.SuppressWarnings("all")
            private BigDecimal economiaTotalMes;
            @java.lang.SuppressWarnings("all")
            private java.time.LocalDate ultimaAtualizacao;

            @java.lang.SuppressWarnings("all")
            DashboardPricingBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder statusMotor(final String statusMotor) {
                this.statusMotor = statusMotor;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder totalTarifas(final Integer totalTarifas) {
                this.totalTarifas = totalTarifas;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder totalPacotes(final Integer totalPacotes) {
                this.totalPacotes = totalPacotes;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder simulacoesHoje(final Integer simulacoesHoje) {
                this.simulacoesHoje = simulacoesHoje;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder economiaTotalMes(final BigDecimal economiaTotalMes) {
                this.economiaTotalMes = economiaTotalMes;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing.DashboardPricingBuilder ultimaAtualizacao(final java.time.LocalDate ultimaAtualizacao) {
                this.ultimaAtualizacao = ultimaAtualizacao;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public PricingController.DashboardPricing build() {
                return new PricingController.DashboardPricing(this.statusMotor, this.totalTarifas, this.totalPacotes, this.simulacoesHoje, this.economiaTotalMes, this.ultimaAtualizacao);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "PricingController.DashboardPricing.DashboardPricingBuilder(statusMotor=" + this.statusMotor + ", totalTarifas=" + this.totalTarifas + ", totalPacotes=" + this.totalPacotes + ", simulacoesHoje=" + this.simulacoesHoje + ", economiaTotalMes=" + this.economiaTotalMes + ", ultimaAtualizacao=" + this.ultimaAtualizacao + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static PricingController.DashboardPricing.DashboardPricingBuilder builder() {
            return new PricingController.DashboardPricing.DashboardPricingBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public String getStatusMotor() {
            return this.statusMotor;
        }

        @java.lang.SuppressWarnings("all")
        public Integer getTotalTarifas() {
            return this.totalTarifas;
        }

        @java.lang.SuppressWarnings("all")
        public Integer getTotalPacotes() {
            return this.totalPacotes;
        }

        @java.lang.SuppressWarnings("all")
        public Integer getSimulacoesHoje() {
            return this.simulacoesHoje;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getEconomiaTotalMes() {
            return this.economiaTotalMes;
        }

        @java.lang.SuppressWarnings("all")
        public java.time.LocalDate getUltimaAtualizacao() {
            return this.ultimaAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setStatusMotor(final String statusMotor) {
            this.statusMotor = statusMotor;
        }

        @java.lang.SuppressWarnings("all")
        public void setTotalTarifas(final Integer totalTarifas) {
            this.totalTarifas = totalTarifas;
        }

        @java.lang.SuppressWarnings("all")
        public void setTotalPacotes(final Integer totalPacotes) {
            this.totalPacotes = totalPacotes;
        }

        @java.lang.SuppressWarnings("all")
        public void setSimulacoesHoje(final Integer simulacoesHoje) {
            this.simulacoesHoje = simulacoesHoje;
        }

        @java.lang.SuppressWarnings("all")
        public void setEconomiaTotalMes(final BigDecimal economiaTotalMes) {
            this.economiaTotalMes = economiaTotalMes;
        }

        @java.lang.SuppressWarnings("all")
        public void setUltimaAtualizacao(final java.time.LocalDate ultimaAtualizacao) {
            this.ultimaAtualizacao = ultimaAtualizacao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof PricingController.DashboardPricing)) return false;
            final PricingController.DashboardPricing other = (PricingController.DashboardPricing) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$totalTarifas = this.getTotalTarifas();
            final java.lang.Object other$totalTarifas = other.getTotalTarifas();
            if (this$totalTarifas == null ? other$totalTarifas != null : !this$totalTarifas.equals(other$totalTarifas)) return false;
            final java.lang.Object this$totalPacotes = this.getTotalPacotes();
            final java.lang.Object other$totalPacotes = other.getTotalPacotes();
            if (this$totalPacotes == null ? other$totalPacotes != null : !this$totalPacotes.equals(other$totalPacotes)) return false;
            final java.lang.Object this$simulacoesHoje = this.getSimulacoesHoje();
            final java.lang.Object other$simulacoesHoje = other.getSimulacoesHoje();
            if (this$simulacoesHoje == null ? other$simulacoesHoje != null : !this$simulacoesHoje.equals(other$simulacoesHoje)) return false;
            final java.lang.Object this$statusMotor = this.getStatusMotor();
            final java.lang.Object other$statusMotor = other.getStatusMotor();
            if (this$statusMotor == null ? other$statusMotor != null : !this$statusMotor.equals(other$statusMotor)) return false;
            final java.lang.Object this$economiaTotalMes = this.getEconomiaTotalMes();
            final java.lang.Object other$economiaTotalMes = other.getEconomiaTotalMes();
            if (this$economiaTotalMes == null ? other$economiaTotalMes != null : !this$economiaTotalMes.equals(other$economiaTotalMes)) return false;
            final java.lang.Object this$ultimaAtualizacao = this.getUltimaAtualizacao();
            final java.lang.Object other$ultimaAtualizacao = other.getUltimaAtualizacao();
            if (this$ultimaAtualizacao == null ? other$ultimaAtualizacao != null : !this$ultimaAtualizacao.equals(other$ultimaAtualizacao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof PricingController.DashboardPricing;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $totalTarifas = this.getTotalTarifas();
            result = result * PRIME + ($totalTarifas == null ? 43 : $totalTarifas.hashCode());
            final java.lang.Object $totalPacotes = this.getTotalPacotes();
            result = result * PRIME + ($totalPacotes == null ? 43 : $totalPacotes.hashCode());
            final java.lang.Object $simulacoesHoje = this.getSimulacoesHoje();
            result = result * PRIME + ($simulacoesHoje == null ? 43 : $simulacoesHoje.hashCode());
            final java.lang.Object $statusMotor = this.getStatusMotor();
            result = result * PRIME + ($statusMotor == null ? 43 : $statusMotor.hashCode());
            final java.lang.Object $economiaTotalMes = this.getEconomiaTotalMes();
            result = result * PRIME + ($economiaTotalMes == null ? 43 : $economiaTotalMes.hashCode());
            final java.lang.Object $ultimaAtualizacao = this.getUltimaAtualizacao();
            result = result * PRIME + ($ultimaAtualizacao == null ? 43 : $ultimaAtualizacao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "PricingController.DashboardPricing(statusMotor=" + this.getStatusMotor() + ", totalTarifas=" + this.getTotalTarifas() + ", totalPacotes=" + this.getTotalPacotes() + ", simulacoesHoje=" + this.getSimulacoesHoje() + ", economiaTotalMes=" + this.getEconomiaTotalMes() + ", ultimaAtualizacao=" + this.getUltimaAtualizacao() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public PricingController(final PricingEngineService pricingEngineService) {
        this.pricingEngineService = pricingEngineService;
    }
}
