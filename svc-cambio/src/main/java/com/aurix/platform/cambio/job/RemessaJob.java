package com.aurix.platform.cambio.job;

import com.aurix.platform.cambio.service.RemessaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemessaJob {

    private static final Logger log = LoggerFactory.getLogger(RemessaJob.class);

    private final RemessaService remessaService;

    public RemessaJob(RemessaService remessaService) {
        this.remessaService = remessaService;
    }

    @Scheduled(fixedDelay = 60000)
    public void processarRemessas() {
        try {
            remessaService.processarRemessasPendentes();
        } catch (Exception e) {
            log.error("Erro no processamento de remessas", e);
        }
    }
}
