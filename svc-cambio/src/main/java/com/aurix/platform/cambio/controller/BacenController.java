package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.entity.RelatorioBacen;
import com.aurix.platform.cambio.entity.TaxaSelic;
import com.aurix.platform.cambio.service.BacenIntegrationService;
import com.aurix.platform.cambio.service.RelatoriosBacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller para APIs de integração BACEN
 * 
 * Gerencia todas as operações relacionadas ao Banco Central
 */
@RestController
@RequestMapping("/api/cambio")
@Tag(name = "BACEN Integration", description = "APIs para integração com Banco Central do Brasil")
public class BacenController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BacenController.class);
    private final BacenIntegrationService bacenIntegrationService;
    private final RelatoriosBacenService relatoriosBacenService;

    /**
     * Busca taxa SELIC atual
     */
    @GetMapping("/selic/atual")
    @Operation(summary = "Buscar taxa SELIC atual", description = "Retorna a taxa SELIC mais recente")
    public ResponseEntity<TaxaSelic> buscarTaxaSelicAtual() {
        log.info("Buscando taxa SELIC atual");
        TaxaSelic taxaAtual = bacenIntegrationService.buscarTaxaSelicAtual();
        if (taxaAtual != null) {
            return ResponseEntity.ok(taxaAtual);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza taxa SELIC do BACEN
     */
    @PostMapping("/selic/atualizar")
    @Operation(summary = "Atualizar taxa SELIC", description = "Força atualização da taxa SELIC com dados do BACEN")
    public ResponseEntity<TaxaSelic> atualizarTaxaSelic() {
        log.info("Atualizando taxa SELIC do BACEN");
        TaxaSelic taxaAtualizada = bacenIntegrationService.atualizarTaxaSelicDoBacen();
        if (taxaAtualizada != null) {
            return ResponseEntity.ok(taxaAtualizada);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Busca histórico de taxa SELIC
     */
    @GetMapping("/selic/historico")
    @Operation(summary = "Buscar histórico de taxa SELIC", description = "Retorna histórico de taxas SELIC por período")
    public ResponseEntity<List<TaxaSelic>> buscarHistoricoTaxaSelic(@Parameter(description = "Data de início (yyyy-MM-dd)") @RequestParam LocalDate dataInicio, @Parameter(description = "Data de fim (yyyy-MM-dd)") @RequestParam LocalDate dataFim) {
        log.info("Buscando histórico de taxa SELIC: {} a {}", dataInicio, dataFim);
        List<TaxaSelic> historico = bacenIntegrationService.buscarHistoricoTaxaSelic(dataInicio, dataFim);
        return ResponseEntity.ok(historico);
    }

    /**
     * Calcula spread bancário
     */
    @PostMapping("/spread/calcular")
    @Operation(summary = "Calcular spread bancário", description = "Calcula o spread entre taxa de captação e aplicação")
    public ResponseEntity<BigDecimal> calcularSpreadBancario(@Parameter(description = "Taxa de captação") @RequestParam BigDecimal taxaCaptacao, @Parameter(description = "Taxa de aplicação") @RequestParam BigDecimal taxaAplicacao) {
        log.info("Calculando spread bancário: Captação={}, Aplicação={}", taxaCaptacao, taxaAplicacao);
        BigDecimal spread = bacenIntegrationService.calcularSpreadBancario(taxaCaptacao, taxaAplicacao);
        return ResponseEntity.ok(spread);
    }

    /**
     * Calcula taxa de aplicação baseada no spread
     */
    @PostMapping("/spread/taxa-aplicacao")
    @Operation(summary = "Calcular taxa de aplicação", description = "Calcula taxa de aplicação baseada na SELIC e spread desejado")
    public ResponseEntity<BigDecimal> calcularTaxaAplicacao(@Parameter(description = "Taxa SELIC atual") @RequestParam BigDecimal taxaSelic, @Parameter(description = "Spread desejado") @RequestParam BigDecimal spreadDesejado) {
        log.info("Calculando taxa de aplicação: SELIC={}, Spread={}", taxaSelic, spreadDesejado);
        BigDecimal taxaAplicacao = bacenIntegrationService.calcularTaxaAplicacao(taxaSelic, spreadDesejado);
        return ResponseEntity.ok(taxaAplicacao);
    }

    /**
     * Calcula competitividade da taxa
     */
    @PostMapping("/spread/competitividade")
    @Operation(summary = "Calcular competitividade", description = "Calcula competitividade em relação à concorrência")
    public ResponseEntity<BigDecimal> calcularCompetitividade(@Parameter(description = "Nossa taxa") @RequestParam BigDecimal taxaNossa, @Parameter(description = "Taxa da concorrência") @RequestParam BigDecimal taxaConcorrencia) {
        log.info("Calculando competitividade: Nossa={}, Concorrência={}", taxaNossa, taxaConcorrencia);
        BigDecimal competitividade = bacenIntegrationService.calcularCompetitividade(taxaNossa, taxaConcorrencia);
        return ResponseEntity.ok(competitividade);
    }

    /**
     * Valida taxa SELIC
     */
    @PostMapping("/selic/validar")
    @Operation(summary = "Validar taxa SELIC", description = "Valida se a taxa está dentro dos limites aceitáveis")
    public ResponseEntity<Boolean> validarTaxaSelic(@Parameter(description = "Taxa SELIC para validar") @RequestParam BigDecimal taxa) {
        log.info("Validando taxa SELIC: {}", taxa);
        boolean valida = bacenIntegrationService.validarTaxaSelic(taxa);
        return ResponseEntity.ok(valida);
    }

    /**
     * Calcula tendência da taxa SELIC
     */
    @GetMapping("/selic/tendencia")
    @Operation(summary = "Calcular tendência da taxa SELIC", description = "Calcula se a taxa está em alta, baixa ou estável")
    public ResponseEntity<String> calcularTendenciaTaxaSelic() {
        log.info("Calculando tendência da taxa SELIC");
        String tendencia = bacenIntegrationService.calcularTendenciaTaxaSelic();
        return ResponseEntity.ok(tendencia);
    }

    /**
     * Dashboard BACEN
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard BACEN", description = "Retorna informações consolidadas do BACEN")
    public ResponseEntity<DashboardBacen> obterDashboardBacen() {
        log.info("Gerando dashboard BACEN");
        TaxaSelic taxaAtual = bacenIntegrationService.buscarTaxaSelicAtual();
        String tendencia = bacenIntegrationService.calcularTendenciaTaxaSelic();
        DashboardBacen dashboard = DashboardBacen.builder().taxaSelicAtual(taxaAtual != null ? taxaAtual.getValorTaxa() : BigDecimal.ZERO).dataAtualizacao(taxaAtual != null ? taxaAtual.getDataReferencia() : LocalDate.now()).tendencia(tendencia).statusIntegracao("ATIVA").ultimaAtualizacao(LocalDate.now()).build();
        return ResponseEntity.ok(dashboard);
    }

    @PostMapping("/relatorios/cosif")
    @Operation(summary = "Gerar relatório COSIF", description = "Gera o relatório COSIF para a data de referência")
    public ResponseEntity<RelatorioBacen> gerarRelatorioCOSIF(@Parameter(description = "Data de referência (yyyy-MM-dd)") @RequestParam LocalDate dataReferencia) {
        RelatorioBacen rel = relatoriosBacenService.gerarRelatorioCOSIF(dataReferencia);
        return ResponseEntity.ok(rel);
    }

    @PostMapping("/relatorios/pix")
    @Operation(summary = "Gerar relatório PIX", description = "Gera o relatório regulatório de operações PIX")
    public ResponseEntity<RelatorioBacen> gerarRelatorioPIX(@Parameter(description = "Data de referência (yyyy-MM-dd)") @RequestParam LocalDate dataReferencia) {
        RelatorioBacen rel = relatoriosBacenService.gerarRelatorioPIX(dataReferencia);
        return ResponseEntity.ok(rel);
    }

    @PostMapping("/relatorios/credito")
    @Operation(summary = "Gerar relatório de crédito", description = "Gera o relatório de operações de crédito")
    public ResponseEntity<RelatorioBacen> gerarRelatorioCredito(@Parameter(description = "Data de referência (yyyy-MM-dd)") @RequestParam LocalDate dataReferencia) {
        RelatorioBacen rel = relatoriosBacenService.gerarRelatorioCredito(dataReferencia);
        return ResponseEntity.ok(rel);
    }

    @GetMapping("/relatorios/pendentes")
    @Operation(summary = "Listar relatórios pendentes", description = "Lista relatórios com vencimento próximo")
    public ResponseEntity<List<RelatorioBacen>> listarRelatoriosPendentes() {
        return ResponseEntity.ok(relatoriosBacenService.listarPendentes());
    }

    @GetMapping("/relatorios")
    @Operation(summary = "Listar relatórios por período")
    public ResponseEntity<List<RelatorioBacen>> listarRelatorios(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return ResponseEntity.ok(relatoriosBacenService.listarPorPeriodo(inicio, fim));
    }

    @PostMapping("/relatorios/{id}/enviado")
    @Operation(summary = "Marcar relatório como enviado ao BACEN")
    public ResponseEntity<RelatorioBacen> marcarRelatorioEnviado(@PathVariable Long id, @RequestParam String protocoloBacen) {
        return ResponseEntity.ok(relatoriosBacenService.marcarComoEnviado(id, protocoloBacen));
    }

    @PostMapping("/relatorios/efinanceira")
    @Operation(summary = "Gerar relatório E-Financeira")
    public ResponseEntity<RelatorioBacen> gerarRelatorioEFinanceira(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioEFinanceira(dataReferencia));
    }

    @PostMapping("/relatorios/scr-ccs")
    @Operation(summary = "Gerar relatório SCR/CCS")
    public ResponseEntity<RelatorioBacen> gerarRelatorioScrCcs(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioScrCcs(dataReferencia));
    }

    @PostMapping("/relatorios/sped-ecd")
    @Operation(summary = "Gerar SPED ECD")
    public ResponseEntity<RelatorioBacen> gerarRelatorioSpedEcd(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioSpedEcd(dataReferencia));
    }

    @PostMapping("/relatorios/sped-ecf")
    @Operation(summary = "Gerar SPED ECF")
    public ResponseEntity<RelatorioBacen> gerarRelatorioSpedEcf(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioSpedEcf(dataReferencia));
    }

    @PostMapping("/relatorios/sped-reinf")
    @Operation(summary = "Gerar SPED EFD-Reinf")
    public ResponseEntity<RelatorioBacen> gerarRelatorioSpedReinf(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioSpedReinf(dataReferencia));
    }

    @PostMapping("/relatorios/bacen-jud")
    @Operation(summary = "Gerar relatório BACEN Jud")
    public ResponseEntity<RelatorioBacen> gerarRelatorioBacenJud(@RequestParam LocalDate dataReferencia) {
        return ResponseEntity.ok(relatoriosBacenService.gerarRelatorioBacenJud(dataReferencia));
    }

    @GetMapping("/relatorios/status")
    @Operation(summary = "Dashboard de status regulatório")
    public ResponseEntity<java.util.Map<String, Object>> dashboardStatusRegulatorio() {
        return ResponseEntity.ok(relatoriosBacenService.obterDashboardStatus());
    }


    /**
     * Classe para dashboard BACEN
     */
    public static class DashboardBacen {
        private BigDecimal taxaSelicAtual;
        private LocalDate dataAtualizacao;
        private String tendencia;
        private String statusIntegracao;
        private LocalDate ultimaAtualizacao;

        @java.lang.SuppressWarnings("all")
        DashboardBacen(final BigDecimal taxaSelicAtual, final LocalDate dataAtualizacao, final String tendencia, final String statusIntegracao, final LocalDate ultimaAtualizacao) {
            this.taxaSelicAtual = taxaSelicAtual;
            this.dataAtualizacao = dataAtualizacao;
            this.tendencia = tendencia;
            this.statusIntegracao = statusIntegracao;
            this.ultimaAtualizacao = ultimaAtualizacao;
        }


        @java.lang.SuppressWarnings("all")
        public static class DashboardBacenBuilder {
            @java.lang.SuppressWarnings("all")
            private BigDecimal taxaSelicAtual;
            @java.lang.SuppressWarnings("all")
            private LocalDate dataAtualizacao;
            @java.lang.SuppressWarnings("all")
            private String tendencia;
            @java.lang.SuppressWarnings("all")
            private String statusIntegracao;
            @java.lang.SuppressWarnings("all")
            private LocalDate ultimaAtualizacao;

            @java.lang.SuppressWarnings("all")
            DashboardBacenBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen.DashboardBacenBuilder taxaSelicAtual(final BigDecimal taxaSelicAtual) {
                this.taxaSelicAtual = taxaSelicAtual;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen.DashboardBacenBuilder dataAtualizacao(final LocalDate dataAtualizacao) {
                this.dataAtualizacao = dataAtualizacao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen.DashboardBacenBuilder tendencia(final String tendencia) {
                this.tendencia = tendencia;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen.DashboardBacenBuilder statusIntegracao(final String statusIntegracao) {
                this.statusIntegracao = statusIntegracao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen.DashboardBacenBuilder ultimaAtualizacao(final LocalDate ultimaAtualizacao) {
                this.ultimaAtualizacao = ultimaAtualizacao;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public BacenController.DashboardBacen build() {
                return new BacenController.DashboardBacen(this.taxaSelicAtual, this.dataAtualizacao, this.tendencia, this.statusIntegracao, this.ultimaAtualizacao);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "BacenController.DashboardBacen.DashboardBacenBuilder(taxaSelicAtual=" + this.taxaSelicAtual + ", dataAtualizacao=" + this.dataAtualizacao + ", tendencia=" + this.tendencia + ", statusIntegracao=" + this.statusIntegracao + ", ultimaAtualizacao=" + this.ultimaAtualizacao + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static BacenController.DashboardBacen.DashboardBacenBuilder builder() {
            return new BacenController.DashboardBacen.DashboardBacenBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getTaxaSelicAtual() {
            return this.taxaSelicAtual;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDate getDataAtualizacao() {
            return this.dataAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public String getTendencia() {
            return this.tendencia;
        }

        @java.lang.SuppressWarnings("all")
        public String getStatusIntegracao() {
            return this.statusIntegracao;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDate getUltimaAtualizacao() {
            return this.ultimaAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setTaxaSelicAtual(final BigDecimal taxaSelicAtual) {
            this.taxaSelicAtual = taxaSelicAtual;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataAtualizacao(final LocalDate dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setTendencia(final String tendencia) {
            this.tendencia = tendencia;
        }

        @java.lang.SuppressWarnings("all")
        public void setStatusIntegracao(final String statusIntegracao) {
            this.statusIntegracao = statusIntegracao;
        }

        @java.lang.SuppressWarnings("all")
        public void setUltimaAtualizacao(final LocalDate ultimaAtualizacao) {
            this.ultimaAtualizacao = ultimaAtualizacao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof BacenController.DashboardBacen)) return false;
            final BacenController.DashboardBacen other = (BacenController.DashboardBacen) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$taxaSelicAtual = this.getTaxaSelicAtual();
            final java.lang.Object other$taxaSelicAtual = other.getTaxaSelicAtual();
            if (this$taxaSelicAtual == null ? other$taxaSelicAtual != null : !this$taxaSelicAtual.equals(other$taxaSelicAtual)) return false;
            final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
            final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
            if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
            final java.lang.Object this$tendencia = this.getTendencia();
            final java.lang.Object other$tendencia = other.getTendencia();
            if (this$tendencia == null ? other$tendencia != null : !this$tendencia.equals(other$tendencia)) return false;
            final java.lang.Object this$statusIntegracao = this.getStatusIntegracao();
            final java.lang.Object other$statusIntegracao = other.getStatusIntegracao();
            if (this$statusIntegracao == null ? other$statusIntegracao != null : !this$statusIntegracao.equals(other$statusIntegracao)) return false;
            final java.lang.Object this$ultimaAtualizacao = this.getUltimaAtualizacao();
            final java.lang.Object other$ultimaAtualizacao = other.getUltimaAtualizacao();
            if (this$ultimaAtualizacao == null ? other$ultimaAtualizacao != null : !this$ultimaAtualizacao.equals(other$ultimaAtualizacao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof BacenController.DashboardBacen;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $taxaSelicAtual = this.getTaxaSelicAtual();
            result = result * PRIME + ($taxaSelicAtual == null ? 43 : $taxaSelicAtual.hashCode());
            final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
            result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
            final java.lang.Object $tendencia = this.getTendencia();
            result = result * PRIME + ($tendencia == null ? 43 : $tendencia.hashCode());
            final java.lang.Object $statusIntegracao = this.getStatusIntegracao();
            result = result * PRIME + ($statusIntegracao == null ? 43 : $statusIntegracao.hashCode());
            final java.lang.Object $ultimaAtualizacao = this.getUltimaAtualizacao();
            result = result * PRIME + ($ultimaAtualizacao == null ? 43 : $ultimaAtualizacao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "BacenController.DashboardBacen(taxaSelicAtual=" + this.getTaxaSelicAtual() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", tendencia=" + this.getTendencia() + ", statusIntegracao=" + this.getStatusIntegracao() + ", ultimaAtualizacao=" + this.getUltimaAtualizacao() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public BacenController(final BacenIntegrationService bacenIntegrationService, final RelatoriosBacenService relatoriosBacenService) {
        this.bacenIntegrationService = bacenIntegrationService;
        this.relatoriosBacenService = relatoriosBacenService;
    }
}
