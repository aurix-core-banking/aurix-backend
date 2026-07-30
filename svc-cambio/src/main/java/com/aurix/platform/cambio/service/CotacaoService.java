package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.dto.CotacaoRequest;
import com.aurix.platform.cambio.dto.CotacaoResponse;
import com.aurix.platform.cambio.entity.Cotacao;
import com.aurix.platform.cambio.event.CotacaoAtualizadaEvent;
import com.aurix.platform.cambio.repository.CotacaoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CotacaoService {

    private static final Logger log = LoggerFactory.getLogger(CotacaoService.class);

    private final CotacaoRepository cotacaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CotacaoService(CotacaoRepository cotacaoRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.cotacaoRepository = cotacaoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional(readOnly = true)
    public List<CotacaoResponse> listarCotacoes() {
        Map<String, Cotacao> latestByMoeda = cotacaoRepository.findAll().stream()
            .collect(Collectors.toMap(
                Cotacao::getMoeda,
                c -> c,
                (a, b) -> a.getDataCotacao().isAfter(b.getDataCotacao()) ? a : b
            ));
        return latestByMoeda.values().stream()
            .sorted(Comparator.comparing(Cotacao::getMoeda))
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CotacaoResponse obterCotacao(String moeda) {
        Cotacao cotacao = cotacaoRepository.findFirstByMoedaOrderByDataCotacaoDesc(moeda)
            .orElseThrow(() -> new IllegalArgumentException("Cotacao nao encontrada para moeda: " + moeda));
        return toResponse(cotacao);
    }

    @Transactional
    public CotacaoResponse atualizarCotacao(CotacaoRequest request) {
        String fonte = request.getFonte() != null ? request.getFonte() : "PROPRIO";
        Cotacao cotacao = new Cotacao(
            request.getMoeda(),
            request.getTaxaCompra(),
            request.getTaxaVenda(),
            LocalDateTime.now(),
            fonte,
            "DEFAULT"
        );
        cotacao = cotacaoRepository.save(cotacao);

        try {
            kafkaTemplate.send(Topics.CAMBIO_COTACAO_ATUALIZADA, new CotacaoAtualizadaEvent(
                cotacao.getId(), cotacao.getMoeda(), cotacao.getTaxaCompra(),
                cotacao.getTaxaVenda(), cotacao.getDataCotacao(), cotacao.getFonte(),
                cotacao.getTenantId()));
        } catch (Exception e) {
            // fire-and-forget with try-catch
        }

        log.info("Cotacao atualizada: moeda={}, compra={}, venda={}", cotacao.getMoeda(), cotacao.getTaxaCompra(), cotacao.getTaxaVenda());
        return toResponse(cotacao);
    }

    @Transactional
    public void atualizarCotacoesExternas() {
        // Simula busca de cotações de fontes externas (BACEN, parceiros)
        String[] moedas = {"USD", "EUR", "GBP", "ARS", "JPY"};
        for (String moeda : moedas) {
            BigDecimal taxaCompra = BigDecimal.valueOf(Math.random() * 5 + 1);
            BigDecimal taxaVenda = taxaCompra.add(BigDecimal.valueOf(Math.random() * 0.2));
            Cotacao cotacao = new Cotacao(
                moeda, taxaCompra, taxaVenda, LocalDateTime.now(), "BACEN", "DEFAULT"
            );
            cotacaoRepository.save(cotacao);

            try {
                kafkaTemplate.send(Topics.CAMBIO_COTACAO_ATUALIZADA, new CotacaoAtualizadaEvent(
                    cotacao.getId(), cotacao.getMoeda(), cotacao.getTaxaCompra(),
                    cotacao.getTaxaVenda(), cotacao.getDataCotacao(), cotacao.getFonte(),
                    cotacao.getTenantId()));
            } catch (Exception e) {
                // fire-and-forget with try-catch
            }
        }
        log.info("Cotações externas atualizadas para {} moedas", moedas.length);
    }

    private CotacaoResponse toResponse(Cotacao c) {
        return new CotacaoResponse(
            c.getId(), c.getMoeda(), c.getTaxaCompra(), c.getTaxaVenda(),
            c.getDataCotacao(), c.getFonte()
        );
    }
}
