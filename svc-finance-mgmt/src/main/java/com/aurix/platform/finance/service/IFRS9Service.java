package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.InstrumentoFinanceiro;
import com.aurix.platform.finance.entity.ExpectedCreditLoss;
import com.aurix.platform.finance.entity.HedgeAccounting;
import com.aurix.platform.finance.repository.InstrumentoFinanceiroRepository;
import com.aurix.platform.finance.repository.ExpectedCreditLossRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service principal para implementação do IFRS 9
 * 
 * Gerencia os três pilares do IFRS 9:
 * 1. Classificação e mensuração de instrumentos financeiros
 * 2. Expected Credit Loss (ECL)
 * 3. Hedge Accounting
 */
@Service
@Transactional
public class IFRS9Service {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IFRS9Service.class);
    private final InstrumentoFinanceiroRepository instrumentoRepository;
    private final ExpectedCreditLossRepository eclRepository;
    private final ECLCalculationService eclService;
    private final HedgeAccountingService hedgeService;
    private final ClassificationService classificationService;

    /**
     * Classifica um instrumento financeiro conforme IFRS 9
     */
    public InstrumentoFinanceiro classificarInstrumento(InstrumentoFinanceiro instrumento) {
        log.info("Classificando instrumento financeiro: {}", instrumento.getCodigoInstrumento());
        // Determinar categoria IFRS 9 baseada no modelo de negócio
        InstrumentoFinanceiro.CategoriaIFRS9 categoria = classificationService.determinarCategoria(instrumento);
        instrumento.setCategoriaIFRS9(categoria);
        // Determinar modelo de mensuração
        InstrumentoFinanceiro.ModeloMensuracao modelo = classificationService.determinarModeloMensuracao(instrumento);
        instrumento.setModeloMensuracao(modelo);
        // Classificar estágio inicial (Estágio 1)
        instrumento.setEstagioDeterioracao(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_1);
        // Definir status inicial
        instrumento.setStatus(InstrumentoFinanceiro.StatusInstrumento.ATIVO);
        // Salvar instrumento
        instrumento = instrumentoRepository.save(instrumento);
        log.info("Instrumento classificado: {} - Categoria: {} - Modelo: {}", instrumento.getCodigoInstrumento(), categoria, modelo);
        return instrumento;
    }

    /**
     * Calcula ECL para um instrumento financeiro
     */
    public ExpectedCreditLoss calcularECL(Long instrumentoId, LocalDate dataCalculo) {
        log.info("Calculando ECL para instrumento: {} - Data: {}", instrumentoId, dataCalculo);
        InstrumentoFinanceiro instrumento = instrumentoRepository.findById(instrumentoId).orElseThrow(() -> new RuntimeException("Instrumento não encontrado: " + instrumentoId));
        // Calcular ECL usando o service específico
        ExpectedCreditLoss ecl = eclService.calcularECL(instrumento, dataCalculo);
        // Atualizar instrumento com a perda esperada
        instrumento.setPerdaEsperada(ecl.getExpectedCreditLoss());
        instrumento.setProvisaoImparment(ecl.getProvisaoTotal());
        instrumentoRepository.save(instrumento);
        log.info("ECL calculado: {} - Valor: {}", ecl.getId(), ecl.getExpectedCreditLoss());
        return ecl;
    }

    /**
     * Reclassifica um instrumento entre estágios de deterioração
     */
    public InstrumentoFinanceiro reclassificarEstagio(Long instrumentoId, InstrumentoFinanceiro.EstagioDeterioracao novoEstagio, String motivo) {
        log.info("Reclassificando instrumento: {} - Novo estágio: {}", instrumentoId, novoEstagio);
        InstrumentoFinanceiro instrumento = instrumentoRepository.findById(instrumentoId).orElseThrow(() -> new RuntimeException("Instrumento não encontrado: " + instrumentoId));
        InstrumentoFinanceiro.EstagioDeterioracao estagioAnterior = instrumento.getEstagioDeterioracao();
        // Validar reclassificação
        if (!isReclassificacaoValida(estagioAnterior, novoEstagio)) {
            throw new RuntimeException("Reclassificação inválida: " + estagioAnterior + " -> " + novoEstagio);
        }
        // Atualizar estágio
        instrumento.setEstagioDeterioracao(novoEstagio);
        instrumento.setDataReclassificacao(LocalDateTime.now());
        // Recalcular ECL para o novo estágio
        ExpectedCreditLoss ecl = eclService.calcularECL(instrumento, LocalDate.now());
        instrumento.setPerdaEsperada(ecl.getExpectedCreditLoss());
        instrumento.setProvisaoImparment(ecl.getProvisaoTotal());
        // Atualizar status se necessário
        if (novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_3) {
            instrumento.setStatus(InstrumentoFinanceiro.StatusInstrumento.IMPAIRMENT);
        }
        instrumento = instrumentoRepository.save(instrumento);
        log.info("Instrumento reclassificado: {} - {} -> {} - Motivo: {}", instrumento.getCodigoInstrumento(), estagioAnterior, novoEstagio, motivo);
        return instrumento;
    }

    /**
     * Processa hedge accounting para um instrumento
     */
    public HedgeAccounting processarHedgeAccounting(Long instrumentoHedgeadoId, Long instrumentoHedgeId, HedgeAccounting.TipoHedge tipoHedge) {
        log.info("Processando hedge accounting: {} -> {} - Tipo: {}", instrumentoHedgeadoId, instrumentoHedgeId, tipoHedge);
        return hedgeService.criarHedge(instrumentoHedgeadoId, instrumentoHedgeId, tipoHedge);
    }

    /**
     * Avalia efetividade de hedge
     */
    public HedgeAccounting avaliarEfetividadeHedge(Long hedgeId) {
        log.info("Avaliando efetividade do hedge: {}", hedgeId);
        return hedgeService.avaliarEfetividade(hedgeId);
    }

    /**
     * Processa reclassificação de instrumentos
     */
    public void processarReclassificacao(LocalDate dataReclassificacao) {
        log.info("Processando reclassificação para data: {}", dataReclassificacao);
        List<InstrumentoFinanceiro> instrumentos = instrumentoRepository.findParaReclassificacao(dataReclassificacao.atStartOfDay());
        for (InstrumentoFinanceiro instrumento : instrumentos) {
            try {
                // Avaliar se precisa reclassificar
                InstrumentoFinanceiro.EstagioDeterioracao novoEstagio = classificationService.avaliarEstagio(instrumento, dataReclassificacao);
                if (novoEstagio != instrumento.getEstagioDeterioracao()) {
                    reclassificarEstagio(instrumento.getId(), novoEstagio, "Reclassificação automática - " + dataReclassificacao);
                }
            } catch (Exception e) {
                log.error("Erro ao reclassificar instrumento {}: {}", instrumento.getCodigoInstrumento(), e.getMessage());
            }
        }
        log.info("Reclassificação processada: {} instrumentos avaliados", instrumentos.size());
    }

    /**
     * Calcula provisionamento total por estágio
     */
    public BigDecimal calcularProvisionamentoPorEstagio(InstrumentoFinanceiro.EstagioDeterioracao estagio) {
        BigDecimal result = eclRepository.somaProvisaoPorEstagio(ExpectedCreditLoss.EstagioDeterioracao.valueOf(estagio.name()));
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * Calcula provisionamento total geral
     */
    public BigDecimal calcularProvisionamentoTotal() {
        BigDecimal result = eclRepository.somaProvisaoPorData(LocalDate.now());
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * Gera relatório consolidado IFRS 9
     */
    public RelatorioIFRS9Service.RelatorioConsolidado gerarRelatorioConsolidado(LocalDate dataReferencia) {
        log.info("Gerando relatório consolidado IFRS 9 para: {}", dataReferencia);
        RelatorioIFRS9Service.RelatorioConsolidado relatorio = new RelatorioIFRS9Service.RelatorioConsolidado();
        // Dados por categoria
        for (InstrumentoFinanceiro.CategoriaIFRS9 categoria : InstrumentoFinanceiro.CategoriaIFRS9.values()) {
            long quantidade = instrumentoRepository.countByCategoriaIFRS9(categoria);
            BigDecimal valor = instrumentoRepository.somaValorPorCategoria(categoria);
            relatorio.adicionarCategoria(categoria.name(), quantidade, valor);
        }
        // Dados por estágio
        for (InstrumentoFinanceiro.EstagioDeterioracao estagio : InstrumentoFinanceiro.EstagioDeterioracao.values()) {
            long quantidade = instrumentoRepository.countByEstagioDeterioracao(estagio);
            BigDecimal valor = instrumentoRepository.somaValorPorEstagio(estagio);
            BigDecimal provisao = eclRepository.somaProvisaoPorEstagio(ExpectedCreditLoss.EstagioDeterioracao.valueOf(estagio.name()));
            relatorio.adicionarEstagio(estagio.name(), quantidade, valor, provisao);
        }
        // Provisionamento total
        relatorio.setProvisionamentoTotal(calcularProvisionamentoTotal());
        log.info("Relatório consolidado gerado com sucesso");
        return relatorio;
    }

    /**
     * Valida se uma reclassificação é válida
     */
    private boolean isReclassificacaoValida(InstrumentoFinanceiro.EstagioDeterioracao estagioAnterior, InstrumentoFinanceiro.EstagioDeterioracao novoEstagio) {
        // Regras de reclassificação do IFRS 9
        switch (estagioAnterior) {
        case ESTAGIO_1: 
            return novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_2 || novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_3;
        case ESTAGIO_2: 
            return novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_1 || novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_3;
        case ESTAGIO_3: 
            return novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_1 || novoEstagio == InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_2;
        default: 
            return false;
        }
    }

    @java.lang.SuppressWarnings("all")
    public IFRS9Service(final InstrumentoFinanceiroRepository instrumentoRepository, final ExpectedCreditLossRepository eclRepository, final ECLCalculationService eclService, final HedgeAccountingService hedgeService, final ClassificationService classificationService) {
        this.instrumentoRepository = instrumentoRepository;
        this.eclRepository = eclRepository;
        this.eclService = eclService;
        this.hedgeService = hedgeService;
        this.classificationService = classificationService;
    }
}
