package com.aurix.platform.customer.onboarding.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoParticipanteTest {

    @Test
    void deveConterTodosOsValores() {
        TipoParticipante[] values = TipoParticipante.values();
        assertArrayEquals(new TipoParticipante[]{
                TipoParticipante.SOCIO, TipoParticipante.ADMINISTRADOR, TipoParticipante.REPRESENTANTE,
                TipoParticipante.PROCURADOR, TipoParticipante.BENEFICIARIO_FINAL
        }, values);
    }

    @Test
    void valueOfDeveFuncionar() {
        assertEquals(TipoParticipante.SOCIO, TipoParticipante.valueOf("SOCIO"));
        assertEquals(TipoParticipante.ADMINISTRADOR, TipoParticipante.valueOf("ADMINISTRADOR"));
        assertEquals(TipoParticipante.REPRESENTANTE, TipoParticipante.valueOf("REPRESENTANTE"));
        assertEquals(TipoParticipante.PROCURADOR, TipoParticipante.valueOf("PROCURADOR"));
        assertEquals(TipoParticipante.BENEFICIARIO_FINAL, TipoParticipante.valueOf("BENEFICIARIO_FINAL"));
    }
}
