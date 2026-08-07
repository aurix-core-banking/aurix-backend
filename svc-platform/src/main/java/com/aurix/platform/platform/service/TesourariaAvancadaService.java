package com.aurix.platform.platform.service;

import com.aurix.platform.shared.entity.Investimento;
import com.aurix.platform.shared.repository.InvestimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TesourariaAvancadaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TesourariaAvancadaService.class);
    private final InvestimentoRepository investimentoRepository;
    private static final BigDecimal VOLATILIDADE_ANUAL = new BigDecimal("0.08");
    private static final BigDecimal Z_95 = new BigDecimal("1.645");
    private static final BigDecimal Z_99 = new BigDecimal("2.326");

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> posicaoLiquidez() {
        BigDecimal totalAplicado = investimentoRepository.findByStatus(Investimento.StatusInvestimento.ATIVO).stream().map(Investimento::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRendimento = investimentoRepository.findByStatus(Investimento.StatusInvestimento.ATIVO).stream().map(i -> i.getRendimentoAtual() != null ? i.getRendimentoAtual() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, BigDecimal> m = new HashMap<>();
        m.put("totalAplicado", totalAplicado != null ? totalAplicado.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
        m.put("totalRendimento", totalRendimento != null ? totalRendimento.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
        m.put("valorTotalCarteira", (totalAplicado != null ? totalAplicado : BigDecimal.ZERO).add(totalRendimento != null ? totalRendimento : BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
        return m;
    }

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> calcularVar(int dias, String nivelConfianca) {
        BigDecimal valorTotal = posicaoLiquidez().get("valorTotalCarteira");
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, BigDecimal> r = new HashMap<>();
            r.put("var", BigDecimal.ZERO);
            r.put("valorCarteira", BigDecimal.ZERO);
            r.put("dias", BigDecimal.valueOf(dias));
            return r;
        }
        BigDecimal z = "99".equals(nivelConfianca) ? Z_99 : Z_95;
        BigDecimal volatilidadePeriodo = VOLATILIDADE_ANUAL.multiply(BigDecimal.valueOf(Math.sqrt((double) dias / 365))).setScale(6, RoundingMode.HALF_UP);
        BigDecimal var = valorTotal.multiply(volatilidadePeriodo).multiply(z).setScale(2, RoundingMode.HALF_UP);
        Map<String, BigDecimal> r = new HashMap<>();
        r.put("var", var);
        r.put("valorCarteira", valorTotal);
        r.put("dias", BigDecimal.valueOf(dias));
        r.put("nivelConfianca", "99".equals(nivelConfianca) ? BigDecimal.valueOf(99) : BigDecimal.valueOf(95));
        return r;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> executarStressCenario(String cenario) {
        List<Investimento> ativos = investimentoRepository.findByStatus(Investimento.StatusInvestimento.ATIVO);
        BigDecimal valorAtual = ativos.stream().map(Investimento::getValorTotal).reduce(BigDecimal.ZERO, (a, b) -> a.add(b != null ? b : BigDecimal.ZERO));
        BigDecimal impactoPercentual = BigDecimal.ZERO;
        if ("taxa_sobe_1pct".equalsIgnoreCase(cenario)) {
            impactoPercentual = new BigDecimal("-0.01");
        } else if ("taxa_sobe_2pct".equalsIgnoreCase(cenario)) {
            impactoPercentual = new BigDecimal("-0.02");
        } else if ("taxa_desce_1pct".equalsIgnoreCase(cenario)) {
            impactoPercentual = new BigDecimal("0.01");
        } else if ("volatilidade_dobro".equalsIgnoreCase(cenario)) {
            impactoPercentual = new BigDecimal("-0.05");
        } else {
            impactoPercentual = BigDecimal.ZERO;
        }
        BigDecimal impactoValor = valorAtual.multiply(impactoPercentual).setScale(2, RoundingMode.HALF_UP);
        BigDecimal valorProjetado = valorAtual.add(impactoValor);
        Map<String, Object> r = new HashMap<>();
        r.put("cenario", cenario);
        r.put("valorAtual", valorAtual);
        r.put("impactoPercentual", impactoPercentual);
        r.put("impactoValor", impactoValor);
        r.put("valorProjetado", valorProjetado);
        r.put("dataSimulacao", LocalDateTime.now().toString());
        return r;
    }

    @java.lang.SuppressWarnings("all")
    public TesourariaAvancadaService(final InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }
}
