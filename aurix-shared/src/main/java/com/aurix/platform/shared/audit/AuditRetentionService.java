package com.aurix.platform.shared.audit;

import com.aurix.platform.shared.entity.LogAuditoria;
import com.aurix.platform.shared.repository.LogAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Job de retenção/archival de auditoria.
 * Remove registros com mais de 2 anos (padrão BACEN).
 * Migrar para tabelas particionadas (V999__particionamento.sql).
 */
@Service
public class AuditRetentionService {

    private static final Logger log = LoggerFactory.getLogger(AuditRetentionService.class);

    private final LogAuditoriaRepository repository;

    public AuditRetentionService(LogAuditoriaRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 3 * * ?") // todo dia às 3h
    @Transactional
    public void limparAuditoriaAntiga() {
        LocalDateTime limite = LocalDateTime.now().minusYears(2);
        log.info("Limpando logs de auditoria anteriores a {}", limite);

        // Selecionar e remover em lotes de 1000
        int totalRemovido = 0;
        var antigos = repository.findByDataAcaoBefore(limite);
        var lotes = partitionar(antigos, 1000);

        for (var lote : lotes) {
            repository.deleteAll(lote);
            totalRemovido += lote.size();
            log.debug("Lote removido: {} registros", lote.size());
        }

        log.info("Auditoria limpa: {} registros removidos (anteriores a {})", totalRemovido, limite);
    }

    private <T> java.util.List<java.util.List<T>> partitionar(java.util.List<T> lista, int tamanho) {
        java.util.List<java.util.List<T>> lotes = new java.util.ArrayList<>();
        for (int i = 0; i < lista.size(); i += tamanho) {
            lotes.add(lista.subList(i, Math.min(i + tamanho, lista.size())));
        }
        return lotes;
    }
}
