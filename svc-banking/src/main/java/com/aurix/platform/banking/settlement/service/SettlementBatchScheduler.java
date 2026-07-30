package com.aurix.platform.banking.settlement.service;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SettlementBatchScheduler {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettlementBatchScheduler.class);
    private final SettlementService settlementService;
    private final LiquidezRepository liquidezRepository;

    public SettlementBatchScheduler(final SettlementService settlementService,
                                     final LiquidezRepository liquidezRepository) {
        this.settlementService = settlementService;
        this.liquidezRepository = liquidezRepository;
    }

    @Scheduled(fixedDelayString = "${aurix.settlement.batch-interval-ms:60000}")
    public void processPendingSettlements() {
        List<Liquidez> pendentes = liquidezRepository.findByStatus(Liquidez.StatusLiquidez.PENDENTE);
        if (pendentes.isEmpty()) return;
        log.info("Batch processing {} pending settlements", pendentes.size());
        for (Liquidez l : pendentes) {
            try {
                settlementService.processarLiquidez(l);
            } catch (Exception e) {
                log.error("Batch failed for settlement {}: {}", l.getNumeroLiquidez(), e.getMessage());
            }
        }
    }

    @Scheduled(fixedDelayString = "${aurix.settlement.retry-interval-ms:120000}")
    public void retryFailedSettlements() {
        List<Liquidez> failed = liquidezRepository.findByStatus(Liquidez.StatusLiquidez.FALHA);
        LocalDateTime now = LocalDateTime.now();
        int retried = 0;
        for (Liquidez l : failed) {
            if (l.getProximoRetry() != null && l.getProximoRetry().isBefore(now)
                && l.getTentativasProcessamento() < l.getMaxTentativas()) {
                try {
                    l.setStatus(Liquidez.StatusLiquidez.PENDENTE);
                    liquidezRepository.save(l);
                    settlementService.processarLiquidez(l);
                    retried++;
                } catch (Exception e) {
                    log.error("Retry failed for settlement {}: {}", l.getNumeroLiquidez(), e.getMessage());
                }
            }
        }
        if (retried > 0) {
            log.info("Retried {} failed settlements", retried);
        }
    }
}
