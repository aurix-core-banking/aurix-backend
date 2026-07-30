package com.aurix.platform.shared.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CNPJUtilTest {

    @Test
    void deveValidarCNPJValido() {
        assertTrue(CNPJUtil.isValid("11222333000181"));
    }

    @Test
    void deveRejeitarCNPJInvalido() {
        assertFalse(CNPJUtil.isValid("11222333000182"));
    }

    @Test
    void deveRejeitarCNPJComDigitosIguais() {
        assertFalse(CNPJUtil.isValid("11111111111111"));
    }

    @Test
    void deveRejeitarCNPJNulo() {
        assertFalse(CNPJUtil.isValid(null));
    }

    @Test
    void deveRejeitarCNPJComLetras() {
        assertFalse(CNPJUtil.isValid("11.222.333/0001-8A"));
    }

    @Test
    void deveFormatarCNPJ() {
        assertEquals("11.222.333/0001-81", CNPJUtil.format("11222333000181"));
    }

    @Test
    void deveRetornarOriginalSeTamanhoInvalidoAoFormatar() {
        assertEquals("123", CNPJUtil.format("123"));
    }

    @Test
    void deveRemoverFormatacao() {
        assertEquals("11222333000181", CNPJUtil.unformat("11.222.333/0001-81"));
    }

    @Test
    void deveRetornarNullSeNullAoUnformat() {
        assertNull(CNPJUtil.unformat(null));
    }

    @Test
    void deveMascararCNPJ() {
        assertEquals("**.222.333/0001-81", CNPJUtil.mask("11222333000181"));
    }
}
