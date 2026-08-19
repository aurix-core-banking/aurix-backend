package com.aurix.platform.credit.renegociacao.service;

import com.aurix.platform.credit.financiamento.entity.ContratoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.ParcelaFinanciamento;
import com.aurix.platform.credit.financiamento.entity.StatusContrato;
import com.aurix.platform.credit.financiamento.entity.StatusParcela;
import com.aurix.platform.credit.financiamento.repository.ContratoFinanciamentoRepository;
import com.aurix.platform.credit.financiamento.repository.ParcelaFinanciamentoRepository;
import com.aurix.platform.credit.financiamento.service.AmortizacaoService;
import com.aurix.platform.credit.renegociacao.dto.request.CriarRenegociacaoRequest;
import com.aurix.platform.credit.renegociacao.dto.response.RenegociacaoParcelaResponse;
import com.aurix.platform.credit.renegociacao.dto.response.RenegociacaoResponse;
import com.aurix.platform.credit.renegociacao.entity.Renegociacao;
import com.aurix.platform.credit.renegociacao.entity.RenegociacaoParcela;
import com.aurix.platform.credit.renegociacao.entity.StatusParcelaRenegociacao;
import com.aurix.platform.credit.renegociacao.entity.StatusRenegociacao;
import com.aurix.platform.credit.renegociacao.repository.RenegociacaoParcelaRepository;
import com.aurix.platform.credit.renegociacao.repository.RenegociacaoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RenegociacaoService {

    private static final Logger log = LoggerFactory.getLogger(RenegociacaoService.class);
    private static final int DIAS_ATRASO_MAXIMO = 90;

    private final RenegociacaoRepository renegociacaoRepository;
    private final RenegociacaoParcelaRepository renegociacaoParcelaRepository;
    private final ContratoFinanciamentoRepository contratoRepository;
    private final ParcelaFinanciamentoRepository parcelaRepository;
    private final AmortizacaoService amortizacaoService;
    private final BigDecimal taxaRenegociacaoPadrao;

    public RenegociacaoService(RenegociacaoRepository renegociacaoRepository,
                               RenegociacaoParcelaRepository renegociacaoParcelaRepository,
                               ContratoFinanciamentoRepository contratoRepository,
                               ParcelaFinanciamentoRepository parcelaRepository,
                               AmortizacaoService amortizacaoService,
                               @Value("${aurix.renegociacao.taxa-padrao:0.012}") BigDecimal taxaRenegociacaoPadrao) {
        this.renegociacaoRepository = renegociacaoRepository;
        this.renegociacaoParcelaRepository = renegociacaoParcelaRepository;
        this.contratoRepository = contratoRepository;
        this.parcelaRepository = parcelaRepository;
        this.amortizacaoService = amortizacaoService;
        this.taxaRenegociacaoPadrao = taxaRenegociacaoPadrao;
    }

    @Transactional
    public RenegociacaoResponse criar(CriarRenegociacaoRequest request) {
        var contrato = contratoRepository.findById(request.getContratoOriginalId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Contrato não encontrado: " + request.getContratoOriginalId()));

        if (contrato.getStatus() != StatusContrato.ATIVO) {
            throw new IllegalStateException(
                "Contrato precisa estar ATIVO para renegociação. Status atual: " + contrato.getStatus());
        }

        var parcelasAtrasadas = parcelaRepository
            .findByContratoIdAndStatus(contrato.getId(), StatusParcela.ATRASADA);
        for (var p : parcelasAtrasadas) {
            var diasAtraso = ChronoUnit.DAYS.between(p.getDataVencimento(), LocalDate.now());
            if (diasAtraso > DIAS_ATRASO_MAXIMO) {
                throw new IllegalStateException(
                    "Parcela #" + p.getNumero() + " está com " + diasAtraso + " dias de atraso. "
                    + "Máximo permitido: " + DIAS_ATRASO_MAXIMO + " dias.");
            }
        }

        var novaTaxa = request.getNovaTaxa() != null ? request.getNovaTaxa() : taxaRenegociacaoPadrao;
        var novoPrazo = request.getNovoPrazo();
        var saldoDevedor = contrato.getSaldoDevedor();
        var sistema = request.getSistemaAmortizacao() != null
            ? request.getSistemaAmortizacao()
            : contrato.getSistemaAmortizacao().name();

        var tabela = switch (sistema) {
            case "SAC" -> amortizacaoService.gerarTabelaSAC(saldoDevedor, novoPrazo, novaTaxa);
            case "SACRE" -> amortizacaoService.gerarTabelaSACRE(saldoDevedor, novoPrazo, novaTaxa);
            default -> amortizacaoService.gerarTabelaPrice(saldoDevedor, novoPrazo, novaTaxa);
        };

        var novaParcela = tabela.get(0).valorParcela();

        var renegociacao = new Renegociacao(
            contrato.getTenantId(),
            contrato.getId(),
            contrato.getClienteId(),
            contrato.getSaldoDevedor(),
            saldoDevedor,
            contrato.getTaxaJuros(),
            novaTaxa,
            contrato.getPrazoMeses(),
            novoPrazo,
            contrato.getValorParcela(),
            novaParcela,
            sistema,
            StatusRenegociacao.PENDENTE
        );
        renegociacao.setObservacoes(request.getObservacoes());
        renegociacao = renegociacaoRepository.save(renegociacao);

        var parcelas = tabela.stream().map(linha -> new RenegociacaoParcela(
            renegociacao.getId(),
            linha.numero(),
            LocalDate.now().plusMonths(linha.numero()),
            linha.valorParcela(),
            linha.amortizacao(),
            linha.juros(),
            linha.saldoDevedor(),
            StatusParcelaRenegociacao.PENDENTE
        )).toList();
        renegociacaoParcelaRepository.saveAll(parcelas);

        log.info("Renegociação criada: id={}, contratoId={}, novoPrazo={}, novaTaxa={}",
            renegociacao.getId(), contrato.getId(), novoPrazo, novaTaxa);
        return toResponse(renegociacao, parcelas);
    }

    @Transactional(readOnly = true)
    public RenegociacaoResponse buscarPorId(Long id) {
        var renegociacao = renegociacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Renegociação não encontrada: " + id));
        var parcelas = renegociacaoParcelaRepository.findByRenegociacaoId(id);
        return toResponse(renegociacao, parcelas);
    }

    @Transactional(readOnly = true)
    public List<RenegociacaoResponse> listarPorCliente(Long clienteId) {
        return renegociacaoRepository.findByClienteId(clienteId).stream()
            .map(r -> {
                var parcelas = renegociacaoParcelaRepository.findByRenegociacaoId(r.getId());
                return toResponse(r, parcelas);
            })
            .toList();
    }

    @Transactional
    public RenegociacaoResponse aprovar(Long id) {
        var renegociacao = renegociacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Renegociação não encontrada: " + id));

        if (renegociacao.getStatus() != StatusRenegociacao.PENDENTE) {
            throw new IllegalStateException(
                "Renegociação só pode ser aprovada se estiver PENDENTE. Status atual: " + renegociacao.getStatus());
        }

        renegociacao.setStatus(StatusRenegociacao.APROVADA);
        renegociacao.setDataAprovacao(LocalDateTime.now());
        renegociacao = renegociacaoRepository.save(renegociacao);

        var parcelas = renegociacaoParcelaRepository.findByRenegociacaoId(id);
        log.info("Renegociação aprovada: id={}", id);
        return toResponse(renegociacao, parcelas);
    }

    @Transactional
    public RenegociacaoResponse contratar(Long id) {
        var renegociacao = renegociacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Renegociação não encontrada: " + id));

        if (renegociacao.getStatus() != StatusRenegociacao.APROVADA) {
            throw new IllegalStateException(
                "Renegociação só pode ser contratada se estiver APROVADA. Status atual: " + renegociacao.getStatus());
        }

        var contrato = contratoRepository.findById(renegociacao.getContratoOriginalId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Contrato original não encontrado: " + renegociacao.getContratoOriginalId()));

        var parcelasAntigasPendentes = parcelaRepository
            .findByContratoIdAndStatus(contrato.getId(), StatusParcela.PENDENTE);
        for (var p : parcelasAntigasPendentes) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }
        var parcelasAntigasAtrasadas = parcelaRepository
            .findByContratoIdAndStatus(contrato.getId(), StatusParcela.ATRASADA);
        for (var p : parcelasAntigasAtrasadas) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }

        var tabela = switch (renegociacao.getSistemaAmortizacao()) {
            case "SAC" -> amortizacaoService.gerarTabelaSAC(
                renegociacao.getSaldoDevedorRenegociado(), renegociacao.getPrazoRenegociado(),
                renegociacao.getTaxaJurosRenegociada());
            case "SACRE" -> amortizacaoService.gerarTabelaSACRE(
                renegociacao.getSaldoDevedorRenegociado(), renegociacao.getPrazoRenegociado(),
                renegociacao.getTaxaJurosRenegociada());
            default -> amortizacaoService.gerarTabelaPrice(
                renegociacao.getSaldoDevedorRenegociado(), renegociacao.getPrazoRenegociado(),
                renegociacao.getTaxaJurosRenegociada());
        };

        for (var linha : tabela) {
            var parcela = new ParcelaFinanciamento(
                contrato.getId(),
                linha.numero(),
                LocalDate.now().plusMonths(linha.numero()),
                linha.valorParcela(),
                linha.amortizacao(),
                linha.juros(),
                linha.saldoDevedor(),
                null,
                StatusParcela.PENDENTE
            );
            parcelaRepository.save(parcela);
        }

        contrato.setPrazoMeses(renegociacao.getPrazoRenegociado());
        contrato.setTaxaJuros(renegociacao.getTaxaJurosRenegociada());
        contrato.setValorParcela(renegociacao.getValorParcelaRenegociada());
        contrato.setSaldoDevedor(renegociacao.getSaldoDevedorRenegociado());
        contratoRepository.save(contrato);

        renegociacao.setStatus(StatusRenegociacao.CONTRATADA);
        renegociacao.setDataContratacao(LocalDateTime.now());
        renegociacao = renegociacaoRepository.save(renegociacao);

        var parcelas = renegociacaoParcelaRepository.findByRenegociacaoId(id);
        log.info("Renegociação contratada: id={}, contratoId={}", id, contrato.getId());
        return toResponse(renegociacao, parcelas);
    }

    private RenegociacaoResponse toResponse(Renegociacao r, List<RenegociacaoParcela> parcelas) {
        var parcelaResponses = parcelas.stream()
            .map(p -> new RenegociacaoParcelaResponse(
                p.getId(), p.getNumero(), p.getDataVencimento(),
                p.getValorParcela(), p.getValorAmortizacao(), p.getValorJuros(),
                p.getValorSaldoDevedor(), p.getDataPagamento(), p.getStatus().name()))
            .toList();
        return new RenegociacaoResponse(
            r.getId(), r.getContratoOriginalId(), r.getClienteId(),
            r.getSaldoDevedorAnterior(), r.getSaldoDevedorRenegociado(),
            r.getTaxaJurosAnterior(), r.getTaxaJurosRenegociada(),
            r.getPrazoAnterior(), r.getPrazoRenegociado(),
            r.getValorParcelaAnterior(), r.getValorParcelaRenegociada(),
            r.getSistemaAmortizacao(), r.getStatus().name(),
            r.getDataSolicitacao(), r.getDataAprovacao(), r.getDataContratacao(),
            r.getObservacoes(), parcelaResponses, r.getDataCriacao());
    }
}
