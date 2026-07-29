package com.aurix.platform.cambio.config;

import com.aurix.platform.cambio.service.RelatoriosBacenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "aurix.bacen.scheduler.enabled", havingValue = "true", matchIfMissing = false)
public class RegTechSchedulerConfig {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegTechSchedulerConfig.class);
    private final RelatoriosBacenService relatoriosBacenService;

    @Scheduled(cron = "${aurix.bacen.scheduler.cron-relatorios:0 0 6 * * ?}")
    public void gerarRelatoriosPendentes() {
        log.info("Executando job de geracao de relatorios regulatorios");
        LocalDate ontem = LocalDate.now().minusDays(1);
        LocalDate mesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        try {
            relatoriosBacenService.gerarRelatorioCOSIF(ontem);
            relatoriosBacenService.gerarRelatorioPIX(ontem);
            relatoriosBacenService.gerarRelatorioBacenJud(ontem);
        } catch (Exception e) {
            log.warn("Falha relatorios diarios: {}", e.getMessage());
        }
        try {
            relatoriosBacenService.gerarRelatorioEFinanceira(mesPassado);
            relatoriosBacenService.gerarRelatorioScrCcs(mesPassado);
            relatoriosBacenService.gerarRelatorioSpedEcd(mesPassado);
            relatoriosBacenService.gerarRelatorioSpedReinf(mesPassado);
        } catch (Exception e) {
            log.warn("Falha relatorios mensais: {}", e.getMessage());
        }
        try {
            relatoriosBacenService.gerarRelatorioSpedEcf(LocalDate.now().withDayOfYear(1).minusYears(1));
        } catch (Exception e) {
            log.warn("Falha SPED ECF anual: {}", e.getMessage());
        }
        log.info("Job de relatorios concluido");
    }

    @java.lang.SuppressWarnings("all")
    public RegTechSchedulerConfig(final RelatoriosBacenService relatoriosBacenService) {
        this.relatoriosBacenService = relatoriosBacenService;
    }
}
