package com.aurix.platform.customer.onboarding.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SituacaoCNPJTest {

    @Test
    void deveConterTodosOsValores() {
        SituacaoCNPJ[] values = SituacaoCNPJ.values();
        assertArrayEquals(new SituacaoCNPJ[]{
                SituacaoCNPJ.ATIVA, SituacaoCNPJ.INAPTA, SituacaoCNPJ.BAIXADA,
                SituacaoCNPJ.SUSPENSA, SituacaoCNPJ.NULA
        }, values);
    }

    @Test
    void valueOfDeveFuncionar() {
        assertEquals(SituacaoCNPJ.ATIVA, SituacaoCNPJ.valueOf("ATIVA"));
        assertEquals(SituacaoCNPJ.INAPTA, SituacaoCNPJ.valueOf("INAPTA"));
        assertEquals(SituacaoCNPJ.BAIXADA, SituacaoCNPJ.valueOf("BAIXADA"));
        assertEquals(SituacaoCNPJ.SUSPENSA, SituacaoCNPJ.valueOf("SUSPENSA"));
        assertEquals(SituacaoCNPJ.NULA, SituacaoCNPJ.valueOf("NULA"));
    }
}
