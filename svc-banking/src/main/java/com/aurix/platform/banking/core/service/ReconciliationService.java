package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.ConciliacaoBancaria;
import com.aurix.platform.banking.core.entity.ItemConciliacao;
import com.aurix.platform.banking.core.entity.Liquidacao;
import com.aurix.platform.banking.core.entity.Reconciliacao;
import com.aurix.platform.banking.core.repository.ConciliacaoBancariaRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.ItemConciliacaoRepository;
import com.aurix.platform.banking.core.repository.LiquidacaoRepository;
import com.aurix.platform.banking.core.repository.ReconciliacaoRepository;
import com.aurix.platform.shared.entity.Conta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReconciliationService {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReconciliationService.class);
    private final ReconciliacaoRepository reconciliacaoRepository;
    private final ConciliacaoBancariaRepository conciliacaoRepository;
    private final ItemConciliacaoRepository itemRepository;
    private final LiquidacaoRepository liquidacaoRepository;
    private final ContaRepository contaRepository;

    public ReconciliationService(final ReconciliacaoRepository reconciliacaoRepository,
                                  final ConciliacaoBancariaRepository conciliacaoRepository,
                                  final ItemConciliacaoRepository itemRepository,
                                  final LiquidacaoRepository liquidacaoRepository,
                                  final ContaRepository contaRepository) {
        this.reconciliacaoRepository = reconciliacaoRepository;
        this.conciliacaoRepository = conciliacaoRepository;
        this.itemRepository = itemRepository;
        this.liquidacaoRepository = liquidacaoRepository;
        this.contaRepository = contaRepository;
    }

    public ConciliacaoBancaria criarConciliacao(Long contaId, String tipo, String extratoRef) {
        Conta conta = contaRepository.findById(contaId)
            .orElseThrow(() -> new IllegalArgumentException("Conta not found: " + contaId));

        ConciliacaoBancaria c = new ConciliacaoBancaria();
        c.setCodigoConciliacao("CONC-" + System.currentTimeMillis());
        c.setConta(conta);
        c.setTipoConciliacao(ConciliacaoBancaria.TipoConciliacao.valueOf(tipo));
        c.setStatus(ConciliacaoBancaria.StatusConciliacao.PENDENTE);
        c.setDataReferencia(LocalDateTime.now());
        c.setArquivoExtrato(extratoRef);
        c = conciliacaoRepository.save(c);
        log.info("Reconciliation created: id={}, tipo={}", c.getId(), tipo);
        return c;
    }

    public ConciliacaoBancaria processarConciliacao(Long conciliacaoId) {
        ConciliacaoBancaria c = conciliacaoRepository.findById(conciliacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Reconciliation not found: " + conciliacaoId));

        c.setStatus(ConciliacaoBancaria.StatusConciliacao.PROCESSANDO);
        c.setDataInicioProcessamento(LocalDateTime.now());

        List<Liquidacao> liquidacoes = liquidacaoRepository
            .findByStatus(Liquidacao.StatusLiquidacao.LIQUIDADA);
        int conciliados = 0;
        int divergencias = 0;

        for (Liquidacao l : liquidacoes) {
            List<ItemConciliacao> existentes = itemRepository.findByLiquidacaoId(l.getId());
            if (!existentes.isEmpty()) continue;

            ItemConciliacao item = new ItemConciliacao();
            item.setConciliacao(c);
            item.setLiquidacao(l);
            item.setOrigemItem(ItemConciliacao.OrigemItem.SISTEMA);
            item.setStatus(ItemConciliacao.StatusConciliacao.CONCILIADO);
            item.setValorItem(l.getValorLiquidacao());
            item.setDataItem(l.getDataLiquidacao() != null
                ? l.getDataLiquidacao() : LocalDateTime.now());
            item.setDescricaoItem("Liquidação: " + l.getCodigoLiquidacao());
            item.setProcessado(true);
            item.setDataProcessamento(LocalDateTime.now());
            itemRepository.save(item);
            conciliados++;
        }

        c.setQuantidadeMovimentosSistema(liquidacoes.size());
        c.setQuantidadeConciliados(conciliados);
        c.setQuantidadeDivergencias(divergencias);
        c.setDataFimProcessamento(LocalDateTime.now());

        if (divergencias > 0) {
            c.setStatus(ConciliacaoBancaria.StatusConciliacao.DIVERGENCIA);
        } else {
            c.setStatus(ConciliacaoBancaria.StatusConciliacao.CONCLUIDA);
        }

        c = conciliacaoRepository.save(c);
        log.info("Reconciliation processed: id={}, conciliados={}, divergencias={}",
            conciliacaoId, conciliados, divergencias);
        return c;
    }

    public Reconciliacao criarReconciliacaoGeral(String tipo) {
        Reconciliacao r = new Reconciliacao();
        r.setCodigoReconciliacao("RECONC-" + System.currentTimeMillis());
        r.setTipoReconciliacao(Reconciliacao.TipoReconciliacao.valueOf(tipo));
        r.setStatus(Reconciliacao.StatusReconciliacao.PENDENTE);
        r.setDataReferencia(LocalDateTime.now());
        r.setProcessamentoAutomatico(true);
        r = reconciliacaoRepository.save(r);
        log.info("General reconciliation created: id={}, tipo={}", r.getId(), tipo);
        return r;
    }

    public Reconciliacao processarReconciliacaoGeral(Long reconciliacaoId) {
        Reconciliacao r = reconciliacaoRepository.findById(reconciliacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Reconciliation not found: " + reconciliacaoId));

        r.setStatus(Reconciliacao.StatusReconciliacao.PROCESSANDO);
        r.setDataInicioProcessamento(LocalDateTime.now());

        List<ConciliacaoBancaria> conciliacoes = conciliacaoRepository
            .findConciliacoesPorPeriodo(r.getDataReferencia().minusDays(1), r.getDataReferencia());

        int totalTransacoes = 0;
        int totalSucesso = 0;
        int totalFalha = 0;
        int totalPendente = 0;
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ConciliacaoBancaria c : conciliacoes) {
            totalTransacoes += c.getQuantidadeMovimentosSistema() != null
                ? c.getQuantidadeMovimentosSistema() : 0;
            totalSucesso += c.getQuantidadeConciliados() != null
                ? c.getQuantidadeConciliados() : 0;
            totalFalha += c.getQuantidadeDivergencias() != null
                ? c.getQuantidadeDivergencias() : 0;
            if (c.getStatus() == ConciliacaoBancaria.StatusConciliacao.PENDENTE
                || c.getStatus() == ConciliacaoBancaria.StatusConciliacao.PROCESSANDO) {
                totalPendente++;
            }
        }

        r.setQuantidadeTransacoes(totalTransacoes);
        r.setQuantidadeSucesso(totalSucesso);
        r.setQuantidadeFalha(totalFalha);
        r.setQuantidadePendente(totalPendente);
        r.setValorTotalProcessado(valorTotal);
        r.setDataFimProcessamento(LocalDateTime.now());

        if (totalFalha > 0) {
            r.setStatus(Reconciliacao.StatusReconciliacao.DIVERGENCIA);
        } else if (totalPendente > 0) {
            r.setStatus(Reconciliacao.StatusReconciliacao.PENDENTE);
        } else {
            r.setStatus(Reconciliacao.StatusReconciliacao.CONCLUIDA);
        }

        r = reconciliacaoRepository.save(r);
        return r;
    }

    public List<ConciliacaoBancaria> listarConciliacoesPendentes() {
        return conciliacaoRepository.findConciliacoesPendentesParaProcessamento();
    }

    public List<ConciliacaoBancaria> listarConciliacoesComDivergencia() {
        return conciliacaoRepository.findConciliacoesComDivergencia();
    }

    public Optional<ConciliacaoBancaria> buscarConciliacaoPorId(Long id) {
        return conciliacaoRepository.findById(id);
    }

    public Optional<Reconciliacao> buscarReconciliacaoPorId(Long id) {
        return reconciliacaoRepository.findById(id);
    }
}
