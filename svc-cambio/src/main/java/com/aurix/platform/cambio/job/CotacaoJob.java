package com.aurix.platform.cambio.job;

import com.aurix.platform.cambio.service.CotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CotacaoJob {

    private static final Logger log = LoggerFactory.getLogger(CotacaoJob.class);

    private final CotacaoService cotacaoService;

    public CotacaoJob(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @Scheduled(cron = "0 0 6,12,18 * * *")
    public void atualizarCotacoes() {
        log.info("Iniciando atualização de cotações...");
        try {
            cotacaoService.atualizarCotacoesExternas();
            log.info("Cotações atualizadas com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao atualizar cotações", e);
        }
    }
}
