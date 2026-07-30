package com.aurix.platform.banking.core.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BusinessMetricsService {

    private final Counter transactionCounter;
    private final Counter lockConflictCounter;
    private final MeterRegistry meterRegistry;

    public BusinessMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        this.transactionCounter = Counter.builder("aurix.transactions.total")
                .description("Total de transações processadas")
                .tag("status", "success")
                .register(meterRegistry);

        this.lockConflictCounter = Counter.builder("aurix.lock.conflicts.total")
                .description("Total de conflitos de concorrência (Optimistic Lock)")
                .register(meterRegistry);
    }

    public void registrarSucessoTransacao() {
        transactionCounter.increment();
    }

    public void registrarConflitoLock() {
        lockConflictCounter.increment();
    }

    public void registrarVolumeFinanceiro(String tipo, BigDecimal valor) {
        Counter.builder("aurix.finance.volume.total")
                .description("Volume financeiro total processado")
                .tag("tipo", tipo)
                .register(meterRegistry)
                .increment(valor.doubleValue());
    }
}
