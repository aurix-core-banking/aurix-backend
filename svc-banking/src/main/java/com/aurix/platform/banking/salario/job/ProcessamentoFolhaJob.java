package com.aurix.platform.banking.salario.job;

import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import com.aurix.platform.banking.salario.service.FolhaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProcessamentoFolhaJob {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessamentoFolhaJob.class);

    private final FolhaService folhaService;

    public ProcessamentoFolhaJob(FolhaService folhaService) {
        this.folhaService = folhaService;
    }

    @Scheduled(cron = "0 3 * * * *")
    public void executarProcessamento() {
        log.info("Iniciando processamento de folhas pendentes...");

        List<FolhaPagamento> pendentes = folhaService.listarFolhasPendentes();

        if (pendentes.isEmpty()) {
            log.info("Nenhuma folha pendente para processar");
            return;
        }

        for (FolhaPagamento folha : pendentes) {
            try {
                folhaService.processarFolha(folha);
            } catch (Exception e) {
                log.error("Erro ao processar folha {}: {}", folha.getId(), e.getMessage());
            }
        }

        log.info("Processamento concluido: {} folhas processadas", pendentes.size());
    }
}
