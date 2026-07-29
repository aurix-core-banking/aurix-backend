package com.aurix.platform.credit.consignado.job;

import com.aurix.platform.credit.consignado.service.ParcelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("processamentoParcelasConsignadoJob")
public class ProcessamentoParcelasJob {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoParcelasJob.class);

    private final ParcelaService parcelaService;

    public ProcessamentoParcelasJob(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void processarParcelasVencidas() {
        log.info("Iniciando processamento de parcelas vencidas...");
        try {
            parcelaService.processarParcelasVencidas();
            log.info("Processamento de parcelas vencidas concluído.");
        } catch (Exception e) {
            log.error("Erro no processamento de parcelas vencidas", e);
        }
    }
}
