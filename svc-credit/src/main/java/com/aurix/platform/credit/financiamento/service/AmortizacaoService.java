package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.dto.response.LinhaTabela;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmortizacaoService {

    private static final Logger log = LoggerFactory.getLogger(AmortizacaoService.class);
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_EVEN);
    private static final int SCALE = 2;

    public List<LinhaTabela> gerarTabelaSAC(BigDecimal valor, int prazo, BigDecimal taxaMensal) {
        var tabela = new ArrayList<LinhaTabela>(prazo);
        var amortizacao = valor.divide(BigDecimal.valueOf(prazo), SCALE, RoundingMode.HALF_EVEN);
        var saldo = valor;

        for (int i = 1; i <= prazo; i++) {
            var juros = saldo.multiply(taxaMensal, MC).setScale(SCALE, RoundingMode.HALF_EVEN);
            var parcela = amortizacao.add(juros).setScale(SCALE, RoundingMode.HALF_EVEN);
            saldo = saldo.subtract(amortizacao).setScale(SCALE, RoundingMode.HALF_EVEN);
            if (i == prazo) saldo = BigDecimal.ZERO;

            tabela.add(new LinhaTabela(i, parcela, amortizacao, juros, saldo));
        }
        return tabela;
    }

    public List<LinhaTabela> gerarTabelaPrice(BigDecimal valor, int prazo, BigDecimal taxaMensal) {
        var tabela = new ArrayList<LinhaTabela>(prazo);
        var parcela = calcularParcelaPrice(valor, prazo, taxaMensal);
        var saldo = valor;

        for (int i = 1; i <= prazo; i++) {
            var juros = saldo.multiply(taxaMensal, MC).setScale(SCALE, RoundingMode.HALF_EVEN);
            var amortizacao = parcela.subtract(juros).setScale(SCALE, RoundingMode.HALF_EVEN);
            saldo = saldo.subtract(amortizacao).setScale(SCALE, RoundingMode.HALF_EVEN);
            if (i == prazo) saldo = BigDecimal.ZERO;

            tabela.add(new LinhaTabela(i, parcela, amortizacao, juros, saldo));
        }
        return tabela;
    }

    public BigDecimal calcularParcelaPrice(BigDecimal valor, int prazo, BigDecimal taxaMensal) {
        var um = BigDecimal.ONE;
        var taxaMaisUm = um.add(taxaMensal, MC);
        var fator = taxaMaisUm.pow(prazo, MC);
        var numerador = taxaMensal.multiply(fator, MC);
        var denominador = fator.subtract(um, MC);
        return valor.multiply(numerador.divide(denominador, 10, RoundingMode.HALF_EVEN))
            .setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    public List<LinhaTabela> gerarTabelaSACRE(BigDecimal valor, int prazo, BigDecimal taxaMensal) {
        var tabela = new ArrayList<LinhaTabela>(prazo);
        var saldo = valor;
        var amortizacaoBase = valor.divide(BigDecimal.valueOf(prazo), SCALE, RoundingMode.HALF_EVEN);

        for (int i = 1; i <= prazo; i++) {
            var juros = saldo.multiply(taxaMensal, MC).setScale(SCALE, RoundingMode.HALF_EVEN);
            var amortizacao = amortizacaoBase;
            if (i % 12 == 0 || i == prazo) {
                var mesesRestantes = prazo - i + 1;
                amortizacao = saldo.divide(BigDecimal.valueOf(mesesRestantes), SCALE, RoundingMode.HALF_EVEN);
            }
            var parcela = amortizacao.add(juros).setScale(SCALE, RoundingMode.HALF_EVEN);
            saldo = saldo.subtract(amortizacao).setScale(SCALE, RoundingMode.HALF_EVEN);
            if (i == prazo) saldo = BigDecimal.ZERO;

            tabela.add(new LinhaTabela(i, parcela, amortizacao, juros, saldo));
        }
        return tabela;
    }

    public BigDecimal calcularCet(BigDecimal valorParcela, int prazo, BigDecimal valorFinanciado, BigDecimal taxaCet) {
        var totalPago = valorParcela.multiply(BigDecimal.valueOf(prazo));
        var custoExtra = totalPago.multiply(taxaCet, MC);
        return totalPago.add(custoExtra).subtract(valorFinanciado).setScale(SCALE, RoundingMode.HALF_EVEN);
    }
}
