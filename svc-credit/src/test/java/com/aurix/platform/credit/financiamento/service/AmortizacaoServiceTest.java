package com.aurix.platform.credit.financiamento.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class AmortizacaoServiceTest {

    private final AmortizacaoService service = new AmortizacaoService();

    @Test
    void calcularSAC_amortizacaoConstante() {
        var tabela = service.gerarTabelaSAC(
            BigDecimal.valueOf(100000), 12, new BigDecimal("0.01"));
        assertEquals(0, tabela.get(0).amortizacao().compareTo(tabela.get(1).amortizacao()));
        assertTrue(tabela.get(0).juros().compareTo(tabela.get(1).juros()) > 0);
        assertEquals(0, BigDecimal.ZERO.compareTo(tabela.get(tabela.size() - 1).saldoDevedor()));
    }

    @Test
    void calcularPrice_parcelaConstante() {
        var tabela = service.gerarTabelaPrice(
            BigDecimal.valueOf(100000), 12, new BigDecimal("0.01"));
        assertEquals(0, tabela.get(0).valorParcela().compareTo(tabela.get(1).valorParcela()));
        assertTrue(tabela.get(0).amortizacao().compareTo(tabela.get(1).amortizacao()) < 0);
        assertEquals(0, BigDecimal.ZERO.compareTo(tabela.get(tabela.size() - 1).saldoDevedor()));
    }

    @Test
    void calcularPrice_valorParcelaConhecido() {
        var tabela = service.gerarTabelaPrice(
            BigDecimal.valueOf(100000), 12, new BigDecimal("0.01"));
        // PMT = 100000 * [0.01*(1.01^12)] / [(1.01^12)-1] ≈ 8,884.88
        assertEquals(0, new BigDecimal("8884.88").compareTo(tabela.get(0).valorParcela()));
    }
}
