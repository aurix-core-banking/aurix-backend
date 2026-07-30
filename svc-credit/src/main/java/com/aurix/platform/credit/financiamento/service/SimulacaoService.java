package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.dto.request.SimulacaoRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.credit.financiamento.dto.response.LinhaTabela;
import com.aurix.platform.credit.financiamento.dto.response.SimulacaoResponse;
import com.aurix.platform.credit.financiamento.entity.SimulacaoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.event.SimulacaoRealizadaEvent;
import com.aurix.platform.credit.financiamento.repository.SimulacaoFinanciamentoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulacaoService {

    private static final Logger log = LoggerFactory.getLogger(SimulacaoService.class);

    private final SimulacaoFinanciamentoRepository simulacaoRepository;
    private final AmortizacaoService amortizacaoService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BigDecimal taxaSacPadrao;
    private final BigDecimal taxaPricePadrao;
    private final BigDecimal taxaSacrePadrao;
    private final BigDecimal cetTaxa;

    public SimulacaoService(SimulacaoFinanciamentoRepository simulacaoRepository,
                            AmortizacaoService amortizacaoService,
                            KafkaTemplate<String, Object> kafkaTemplate,
                            @Value("${aurix.financiamento.taxa-sac:0.0099}") BigDecimal taxaSacPadrao,
                            @Value("${aurix.financiamento.taxa-price:0.0112}") BigDecimal taxaPricePadrao,
                            @Value("${aurix.financiamento.taxa-sacre:0.0105}") BigDecimal taxaSacrePadrao,
                            @Value("${aurix.financiamento.cet-taxa:0.0025}") BigDecimal cetTaxa) {
        this.simulacaoRepository = simulacaoRepository;
        this.amortizacaoService = amortizacaoService;
        this.kafkaTemplate = kafkaTemplate;
        this.taxaSacPadrao = taxaSacPadrao;
        this.taxaPricePadrao = taxaPricePadrao;
        this.taxaSacrePadrao = taxaSacrePadrao;
        this.cetTaxa = cetTaxa;
    }

    @Transactional
    public SimulacaoResponse simular(SimulacaoRequest request) {
        var sistema = request.getSistemaAmortizacao() != null
            ? request.getSistemaAmortizacao() : SistemaAmortizacao.PRICE;

        var taxaJuros = request.getTaxaJuros();
        if (taxaJuros == null) {
            taxaJuros = switch (sistema) {
                case SAC -> taxaSacPadrao;
                case PRICE -> taxaPricePadrao;
                case SACRE -> taxaSacrePadrao;
            };
        }

        var valor = request.getValorFinanciado();
        var prazo = request.getPrazoMeses();

        List<LinhaTabela> tabelaSAC = null;
        List<LinhaTabela> tabelaPrice = null;
        BigDecimal valorParcela;

        switch (sistema) {
            case SAC -> {
                tabelaSAC = amortizacaoService.gerarTabelaSAC(valor, prazo, taxaJuros);
                valorParcela = tabelaSAC.get(0).valorParcela();
                tabelaPrice = amortizacaoService.gerarTabelaPrice(valor, prazo, taxaJuros);
            }
            case PRICE -> {
                tabelaPrice = amortizacaoService.gerarTabelaPrice(valor, prazo, taxaJuros);
                valorParcela = tabelaPrice.get(0).valorParcela();
                tabelaSAC = amortizacaoService.gerarTabelaSAC(valor, prazo, taxaJuros);
            }
            case SACRE -> {
                var tabelaSACRE = amortizacaoService.gerarTabelaSACRE(valor, prazo, taxaJuros);
                valorParcela = tabelaSACRE.get(0).valorParcela();
                tabelaPrice = amortizacaoService.gerarTabelaPrice(valor, prazo, taxaJuros);
            }
            default -> throw new IllegalStateException("Sistema não suportado: " + sistema);
        }

        var cet = amortizacaoService.calcularCet(valorParcela, prazo, valor, cetTaxa);

        var entity = new SimulacaoFinanciamento(
            "DEFAULT", 0L, request.getTipo(), valor, prazo, taxaJuros,
            sistema, valorParcela, null, null, LocalDateTime.now()
        );
        entity = simulacaoRepository.save(entity);

        try {
            kafkaTemplate.send(Topics.FINANCIAMENTO_SIMULACAO_REALIZADA,
                new SimulacaoRealizadaEvent(entity.getId(), 0L, request.getTipo().name(),
                    valor, prazo, sistema.name(), entity.getDataSimulacao(), "DEFAULT"));
        } catch (Exception e) {
            log.warn("Erro ao publicar SimulacaoRealizadaEvent: {}", e.getMessage());
        }

        log.info("Simulação realizada: id={}, tipo={}, valor={}", entity.getId(), request.getTipo(), valor);

        return new SimulacaoResponse(entity.getId(), request.getTipo().name(), valor, prazo,
            taxaJuros, sistema.name(), valorParcela, cet, tabelaSAC, tabelaPrice, entity.getDataSimulacao());
    }

    @Transactional(readOnly = true)
    public SimulacaoResponse buscarPorId(Long id) {
        var entity = simulacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Simulação não encontrada: " + id));
        return new SimulacaoResponse(entity.getId(), entity.getTipo().name(),
            entity.getValorFinanciado(), entity.getPrazoMeses(), entity.getTaxaJuros(),
            entity.getSistemaAmortizacao().name(), entity.getValorParcela(), null,
            null, null, entity.getDataSimulacao());
    }
}
