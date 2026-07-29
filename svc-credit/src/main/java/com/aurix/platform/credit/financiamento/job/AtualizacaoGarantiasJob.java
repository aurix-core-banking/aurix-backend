package com.aurix.platform.credit.financiamento.job;

import com.aurix.platform.credit.financiamento.repository.GarantiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AtualizacaoGarantiasJob {

    private static final Logger log = LoggerFactory.getLogger(AtualizacaoGarantiasJob.class);

    private final GarantiaRepository garantiaRepository;

    public AtualizacaoGarantiasJob(GarantiaRepository garantiaRepository) {
        this.garantiaRepository = garantiaRepository;
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void atualizarGarantiasPendentes() {
        log.info("Iniciando atualização de garantias...");
        // Simulated job - in production would call external services
        log.info("Atualização de garantias concluída.");
    }
}
