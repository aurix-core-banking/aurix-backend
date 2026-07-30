package com.aurix.platform.banking.settlement.service;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchJobServiceTest {

    @Mock
    private SettlementService settlementService;
    @Mock
    private LiquidezRepository liquidezRepository;

    private BatchJobService batchJobService;

    @BeforeEach
    void setUp() {
        batchJobService = new BatchJobService(settlementService, liquidezRepository);
        lenient().when(liquidezRepository.save(any(Liquidez.class)))
            .thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void reprocessarPendentes_deveProcessarTodos() {
        Liquidez l1 = new Liquidez();
        l1.setNumeroLiquidez("LIQ-001");
        l1.setStatus(Liquidez.StatusLiquidez.PENDENTE);
        Liquidez l2 = new Liquidez();
        l2.setNumeroLiquidez("LIQ-002");
        l2.setStatus(Liquidez.StatusLiquidez.PENDENTE);
        when(liquidezRepository.findByStatus(Liquidez.StatusLiquidez.PENDENTE))
            .thenReturn(List.of(l1, l2));

        int processados = batchJobService.reprocessarPendentes();

        assertEquals(2, processados);
        verify(settlementService, times(2)).processarLiquidez(any(Liquidez.class));
    }

    @Test
    void reprocessarFalhas_deveResetarEProcessar() {
        Liquidez l1 = new Liquidez();
        l1.setNumeroLiquidez("LIQ-003");
        l1.setStatus(Liquidez.StatusLiquidez.FALHA);
        when(liquidezRepository.findByStatus(Liquidez.StatusLiquidez.FALHA))
            .thenReturn(List.of(l1));

        int processados = batchJobService.reprocessarFalhas();

        assertEquals(1, processados);
        verify(settlementService).processarLiquidez(l1);
    }

    @Test
    void contarPendentes_deveRetornarQuantidade() {
        when(liquidezRepository.countByStatus(Liquidez.StatusLiquidez.PENDENTE)).thenReturn(5L);

        assertEquals(5, batchJobService.contarPendentes());
    }

    @Test
    void contarFalhas_deveRetornarQuantidade() {
        when(liquidezRepository.countByStatus(Liquidez.StatusLiquidez.FALHA)).thenReturn(3L);

        assertEquals(3, batchJobService.contarFalhas());
    }
}
