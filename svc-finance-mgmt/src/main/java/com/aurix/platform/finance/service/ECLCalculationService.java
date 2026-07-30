package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.InstrumentoFinanceiro;
import com.aurix.platform.finance.entity.ExpectedCreditLoss;
import com.aurix.platform.finance.repository.ExpectedCreditLossRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service para cálculo de Expected Credit Loss (ECL) conforme IFRS 9
 * 
 * Implementa o modelo de três estágios e calcula ECL = PD × LGD × EAD
 */
@Service
@Transactional
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class ECLCalculationService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ECLCalculationService.class);
    private final ExpectedCreditLossRepository eclRepository;
    private final RiskAssessmentService riskService;

    /**
     * Calcula ECL para um instrumento financeiro
     */
    public ExpectedCreditLoss calcularECL(InstrumentoFinanceiro instrumento, LocalDate dataCalculo) {
        log.info("Calculando ECL para instrumento: {} - Estágio: {}", instrumento.getCodigoInstrumento(), instrumento.getEstagioDeterioracao());
        // Determinar EAD (Exposure at Default)
        BigDecimal ead = calcularEAD(instrumento);
        // Calcular PD (Probability of Default)
        BigDecimal pd = calcularPD(instrumento, dataCalculo);
        // Calcular LGD (Loss Given Default)
        BigDecimal lgd = calcularLGD(instrumento);
        // Calcular ECL = PD × LGD × EAD
        BigDecimal ecl = pd.multiply(lgd).multiply(ead).setScale(2, RoundingMode.HALF_UP);
        // Determinar provisão baseada no estágio
        BigDecimal provisao12Meses = BigDecimal.ZERO;
        BigDecimal provisaoVidaUtil = BigDecimal.ZERO;
        switch (instrumento.getEstagioDeterioracao()) {
        case ESTAGIO_1: 
            provisao12Meses = calcularECL12Meses(instrumento, pd, lgd, ead);
            provisaoVidaUtil = ecl;
            break;
        case ESTAGIO_2: 
        case ESTAGIO_3: 
            provisaoVidaUtil = ecl;
            break;
        }
        BigDecimal provisaoTotal = provisao12Meses.add(provisaoVidaUtil);
        // Criar registro de ECL
        ExpectedCreditLoss eclRecord = ExpectedCreditLoss.builder().instrumento(instrumento).dataCalculo(dataCalculo).estagio(ExpectedCreditLoss.EstagioDeterioracao.valueOf(instrumento.getEstagioDeterioracao().name())).probabilityDefault(pd).lossGivenDefault(lgd).exposureAtDefault(ead).expectedCreditLoss(ecl).provisao12Meses(provisao12Meses).provisaoVidaUtil(provisaoVidaUtil).provisaoTotal(provisaoTotal).metodologiaCalculo("IFRS 9 - Modelo Padrão").modeloUtilizado("PD × LGD × EAD").cenarioBase("Base").dataUltimaAtualizacao(LocalDateTime.now()).usuarioCalculo("SISTEMA").build();
        eclRecord = eclRepository.save(eclRecord);
        log.info("ECL calculado: PD={}, LGD={}, EAD={}, ECL={}", pd, lgd, ead, ecl);
        return eclRecord;
    }

    /**
     * Calcula EAD (Exposure at Default) - Exposição em caso de inadimplência
     */
    private BigDecimal calcularEAD(InstrumentoFinanceiro instrumento) {
        // Para empréstimos e financiamentos, EAD é o valor nominal
        BigDecimal ead = instrumento.getValorNominal();
        // Ajustar para instrumentos com amortização
        if (instrumento.getTipoInstrumento() == InstrumentoFinanceiro.TipoInstrumento.EMPRESTIMO || instrumento.getTipoInstrumento() == InstrumentoFinanceiro.TipoInstrumento.FINANCIAMENTO) {
            // Calcular saldo devedor atual
            ead = calcularSaldoDevedor(instrumento);
        }
        // Considerar garantias
        if (instrumento.getMetadata() != null && instrumento.getMetadata().contains("garantia")) {
            // Reduzir EAD pelo valor das garantias
            BigDecimal valorGarantia = extrairValorGarantia(instrumento.getMetadata());
            ead = ead.subtract(valorGarantia).max(BigDecimal.ZERO);
        }
        return ead.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula PD (Probability of Default) - Probabilidade de inadimplência
     */
    private BigDecimal calcularPD(InstrumentoFinanceiro instrumento, LocalDate dataCalculo) {
        BigDecimal pd = BigDecimal.ZERO;
        // PD baseada no estágio de deterioração
        switch (instrumento.getEstagioDeterioracao()) {
        case ESTAGIO_1: 
            pd = calcularPD12Meses(instrumento);
            break;
        case ESTAGIO_2: 
            pd = calcularPDVidaUtil(instrumento);
            break;
        case ESTAGIO_3: 
            pd = calcularPDDeteriorado(instrumento);
            break;
        }
        // Ajustar PD baseada em indicadores específicos
        pd = ajustarPDPorIndicadores(instrumento, pd);
        // Garantir que PD está entre 0 e 1
        pd = pd.max(BigDecimal.ZERO).min(BigDecimal.ONE);
        return pd.setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula LGD (Loss Given Default) - Perda em caso de inadimplência
     */
    private BigDecimal calcularLGD(InstrumentoFinanceiro instrumento) {
        BigDecimal lgd = BigDecimal.valueOf(0.45); // LGD padrão 45%
        // Ajustar LGD baseada no tipo de instrumento
        switch (instrumento.getTipoInstrumento()) {
        case EMPRESTIMO: 
            lgd = BigDecimal.valueOf(0.5); // 50% para empréstimos
            break;
        case FINANCIAMENTO: 
            lgd = BigDecimal.valueOf(0.4); // 40% para financiamentos
            break;
        case TITULO_PUBLICO: 
            lgd = BigDecimal.valueOf(0.1); // 10% para títulos públicos
            break;
        case CDB: 
            lgd = BigDecimal.valueOf(0.2); // 20% para CDB
            break;
        case LCI: 
        case LCA: 
            lgd = BigDecimal.valueOf(0.15); // 15% para LCI/LCA
            break;
        case DEBENTURE: 
            lgd = BigDecimal.valueOf(0.6); // 60% para debêntures
            break;
        case INVESTIMENTO: 
        case FUNDOS: 
            lgd = BigDecimal.valueOf(0.3); // 30% para investimentos variados
            break;
        case ACOES: 
        case DERIVATIVOS: 
            lgd = BigDecimal.valueOf(0.8); // 80% para ativos de alto risco
            break;
        case HEDGE: 
            lgd = BigDecimal.valueOf(0.25); // 25% para instrumentos de hedge
            break;
        case GARANTIA: 
            lgd = BigDecimal.valueOf(1.0); // 100% (garantia é o próprio colateral)
            break;
        case OUTROS: 
        default: 
            lgd = BigDecimal.valueOf(0.45); // 45% valor padrão final
            break;
        }
        // Reduzir LGD se há garantias
        if (instrumento.getMetadata() != null && instrumento.getMetadata().contains("garantia")) {
            BigDecimal valorGarantia = extrairValorGarantia(instrumento.getMetadata());
            BigDecimal valorInstrumento = instrumento.getValorNominal();
            if (valorGarantia.compareTo(valorInstrumento) >= 0) {
                lgd = BigDecimal.valueOf(0.05); // LGD muito baixo com garantia total
            } else {
                BigDecimal coberturaGarantia = valorGarantia.divide(valorInstrumento, 4, RoundingMode.HALF_UP);
                lgd = lgd.multiply(BigDecimal.ONE.subtract(coberturaGarantia));
            }
        }
        // Garantir que LGD está entre 0 e 1
        lgd = lgd.max(BigDecimal.ZERO).min(BigDecimal.ONE);
        return lgd.setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula ECL para 12 meses (Estágio 1)
     */
    private BigDecimal calcularECL12Meses(InstrumentoFinanceiro instrumento, BigDecimal pd, BigDecimal lgd, BigDecimal ead) {
        // Para Estágio 1, ECL é calculado apenas para 12 meses
        BigDecimal pd12Meses = pd.multiply(BigDecimal.valueOf(0.3)); // Ajuste para 12 meses
        return pd12Meses.multiply(lgd).multiply(ead).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula PD para 12 meses
     */
    private BigDecimal calc                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                