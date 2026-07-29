package com.aurix.platform.cambio.job;

import com.aurix.platform.cambio.repository.ContratoCambioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FechamentoDiarioJob {

    private static final Logger log = LoggerFactory.getLogger(FechamentoDiarioJob.class);

    private final ContratoCambioRepository contratoRepository;

    public FechamentoDiarioJob(ContratoCambioRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Scheduled(cron = "0 30 23 * * *")
    public void fechamentoDiario() {
        log.info("Iniciando fechamento diário de câmbio...");
        var contratosPendentes = contratoRepository.findByStatus("CONTRATADO");
        for (var c : contratosPendentes) {
            log.info("Contrato {} pendente de liquidação", c.getId());
        }
        log.info("Fechamento diário concluído. {} contratos pendentes.", contratosPendentes.size());
    }
}
