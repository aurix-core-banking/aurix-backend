package com.aurix.platform.banking.settlement.service;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatchJobService {

    private final SettlementService settlementService;
    private final LiquidezRepository liquidezRepository;

    public BatchJobService(SettlementService settlementService, LiquidezRepository liquidezRepository) {
        this.settlementService = settlementService;
        this.liquidezRepository = liquidezRepository;
    }

    @Transactional
    public int reprocessarPendentes() {
        List<Liquidez> pendentes = liquidezRepository.findByStatus(Liquidez.StatusLiquidez.PENDENTE);
        int processados = 0;
        for (Liquidez l : pendentes) {
            try {
                settlementService.processarLiquidez(l);
                processados++;
            } catch (Exception e) {
                l.setStatus(Liquidez.StatusLiquidez.FALHA);
                liquidezRepository.save(l);
            }
        }
        return processados;
    }

    @Transactional
    public int reprocessarFalhas() {
        List<Liquidez> falhas = liquidezRepository.findByStatus(Liquidez.StatusLiquidez.FALHA);
        int processados = 0;
        for (Liquidez l : falhas) {
            try {
                l.setStatus(Liquidez.StatusLiquidez.PENDENTE);
                l.setProximoRetry(LocalDateTime.now());
                liquidezRepository.save(l);
                settlementService.processarLiquidez(l);
                processados++;
            } catch (Exception e) {
                l.setProximoRetry(LocalDateTime.now().plusMinutes(5));
                liquidezRepository.save(l);
            }
        }
        return processados;
    }

    @Transactional(readOnly = true)
    public long contarPendentes() {
        return liquidezRepository.countByStatus(Liquidez.StatusLiquidez.PENDENTE);
    }

    @Transactional(readOnly = true)
    public long contarFalhas() {
        return liquidezRepository.countByStatus(Liquidez.StatusLiquidez.FALHA);
    }
}
