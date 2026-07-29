package com.aurix.platform.banking.settlement.service;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.LiquidezEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private LiquidezRepository liquidezRepository;

    @Mock
    private EventPublisher eventPublisher;

    private SettlementService settlementService;

    @BeforeEach
    void setUp() {
        // Tuesday 2025-07-15 10:00 AM — within TED business hours
        Clock fixedClock = Clock.fixed(
            LocalDateTime.of(2025, 7, 15, 10, 0).atZone(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        );
        settlementService = new SettlementService(liquidezRepository, eventPublisher, fixedClock);
        org.mockito.Mockito.lenient().when(liquidezRepository.save(any(Liquidez.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testProcessarLiquidezPIX() {
        Liquidez liquidez = new Liquidez();
        liquidez.setNumeroLiquidez("LIQ-001");
        liquidez.setTipoOperacao(Liquidez.TipoOperacao.PIX);
        liquidez.setValor(new BigDecimal("1000.00"));
        liquidez.setContaOrigem("12345-6");
        liquidez.setContaDestino("67890-1");
        liquidez.setStatus(Liquidez.StatusLiquidez.PENDENTE);

        Liquidez resultado = settlementService.processarLiquidez(liquidez);

        verify(eventPublisher).publicarLiquidezProcessada(any(LiquidezEvent.class));
    }

    @Test
    void testProcessarLiquidezTEDDentroHorario() {
        Liquidez liquidez = new Liquidez();
        liquidez.setNumeroLiquidez("LIQ-002");
        liquidez.setTipoOperacao(Liquidez.TipoOperacao.TED);
        liquidez.setValor(new BigDecimal("500.00"));
        liquidez.setContaOrigem("12345-6");
        liquidez.setContaDestino("67890-1");
        liquidez.setStatus(Liquidez.StatusLiquidez.PENDENTE);

        Liquidez resultado = settlementService.processarLiquidez(liquidez);

        verify(eventPublisher).publicarLiquidezProcessada(any(LiquidezEvent.class));
    }

    @Test
    void testBuscarLiquidezPendentes() {
        settlementService.buscarLiquidezPendentes();
        verify(liquidezRepository).findByStatus(Liquidez.StatusLiquidez.PENDENTE);
    }
}
