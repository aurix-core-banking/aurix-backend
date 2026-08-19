package com.aurix.platform.investimentos.portfolio.service;

import com.aurix.platform.investimentos.aplicacao.entity.Aplicacao;
import com.aurix.platform.investimentos.aplicacao.entity.StatusAplicacao;
import com.aurix.platform.investimentos.aplicacao.repository.AplicacaoRepository;
import com.aurix.platform.investimentos.portfolio.dto.PortfolioResponse;
import com.aurix.platform.investimentos.portfolio.dto.RentabilidadeResponse;
import com.aurix.platform.investimentos.produto.repository.ProdutoInvestimentoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

    private final AplicacaoRepository aplicacaoRepository;
    private final ProdutoInvestimentoRepository produtoRepository;

    public PortfolioService(AplicacaoRepository aplicacaoRepository,
                            ProdutoInvestimentoRepository produtoRepository) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public PortfolioResponse buscarPortfolio(Long clienteId) {
        var aplicacoes = aplicacaoRepository.findByClienteIdAndStatus(clienteId, StatusAplicacao.APLICADA);

        var itens = new ArrayList<PortfolioResponse.ItemPortfolio>();
        var valorTotalAplicado = BigDecimal.ZERO;
        var valorTotalBruto = BigDecimal.ZERO;
        var valorTotalLiquido = BigDecimal.ZERO;

        for (var a : aplicacoes) {
            var produto = produtoRepository.findById(a.getProdutoId()).orElse(null);
            var nomeProduto = produto != null ? produto.getNome() : "N/A";

            itens.add(new PortfolioResponse.ItemPortfolio(
                a.getId(), a.getProdutoId(), nomeProduto, a.getProdutoTipo(),
                a.getValorAplicado(), a.getValorBruto(), a.getValorLiquido(),
                a.getStatus().name(), a.getDataAplicacao(), a.getDataVencimento()
            ));

            valorTotalAplicado = valorTotalAplicado.add(a.getValorAplicado());
            valorTotalBruto = valorTotalBruto.add(a.getValorBruto());
            valorTotalLiquido = valorTotalLiquido.add(a.getValorLiquido());
        }

        var rendimentoTotal = valorTotalBruto.subtract(valorTotalAplicado);

        return new PortfolioResponse(
            clienteId, valorTotalAplicado, valorTotalBruto, valorTotalLiquido,
            rendimentoTotal, aplicacoes.size(), itens
        );
    }

    @Transactional(readOnly = true)
    public RentabilidadeResponse buscarRentabilidade(Long clienteId) {
        var aplicacoes = aplicacaoRepository.findByClienteIdAndStatus(clienteId, StatusAplicacao.APLICADA);

        var iofTotal = BigDecimal.ZERO;
        var irTotal = BigDecimal.ZERO;
        var rendimentoBrutoTotal = BigDecimal.ZERO;

        for (var a : aplicacoes) {
            var rendimentoBruto = a.getValorBruto().subtract(a.getValorAplicado());
            rendimentoBrutoTotal = rendimentoBrutoTotal.add(rendimentoBruto);
            iofTotal = iofTotal.add(a.getIof() != null ? a.getIof() : BigDecimal.ZERO);
            irTotal = irTotal.add(a.getIr() != null ? a.getIr() : BigDecimal.ZERO);
        }

        var rendimentoLiquidoTotal = rendimentoBrutoTotal.subtract(iofTotal).subtract(irTotal);

        var valorTotalAplicado = aplicacoes.stream()
            .map(Aplicacao::getValorAplicado)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        var rentabilidadeAnual = BigDecimal.ZERO;
        if (valorTotalAplicado.compareTo(BigDecimal.ZERO) > 0) {
            var menorData = aplicacoes.stream()
                .map(Aplicacao::getDataAplicacao)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());
            long dias = ChronoUnit.DAYS.between(menorData, LocalDate.now());
            if (dias > 0) {
                rentabilidadeAnual = rendimentoLiquidoTotal
                    .divide(valorTotalAplicado, 6, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(dias), 6, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(365))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
            }
        }

        return new RentabilidadeResponse(
            clienteId, rendimentoBrutoTotal, iofTotal, irTotal,
            rendimentoLiquidoTotal, rentabilidadeAnual, LocalDate.now()
        );
    }
}
