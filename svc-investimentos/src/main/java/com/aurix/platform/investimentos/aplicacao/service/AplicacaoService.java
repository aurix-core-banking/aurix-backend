package com.aurix.platform.investimentos.aplicacao.service;

import com.aurix.platform.investimentos.aplicacao.dto.AplicacaoRequest;
import com.aurix.platform.investimentos.aplicacao.dto.AplicacaoResponse;
import com.aurix.platform.investimentos.aplicacao.entity.Aplicacao;
import com.aurix.platform.investimentos.aplicacao.entity.StatusAplicacao;
import com.aurix.platform.investimentos.aplicacao.repository.AplicacaoRepository;
import com.aurix.platform.investimentos.indice.entity.TipoIndice;
import com.aurix.platform.investimentos.indice.service.IndiceMercadoService;
import com.aurix.platform.investimentos.produto.entity.ProdutoInvestimento;
import com.aurix.platform.investimentos.produto.entity.TipoRenda;
import com.aurix.platform.investimentos.produto.repository.ProdutoInvestimentoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AplicacaoService {

    private static final Logger log = LoggerFactory.getLogger(AplicacaoService.class);

    private static final BigDecimal TAXA_IOF_MAXIMA = new BigDecimal("0.96");
    private static final int IOF_DIAS_SEM_IOF = 30;

    private static final BigDecimal IR_22_5 = new BigDecimal("0.225");
    private static final BigDecimal IR_20_0 = new BigDecimal("0.200");
    private static final BigDecimal IR_17_5 = new BigDecimal("0.175");
    private static final BigDecimal IR_15_0 = new BigDecimal("0.150");
    private static final int DIAS_IR_22_5 = 180;
    private static final int DIAS_IR_20_0 = 360;
    private static final int DIAS_IR_17_5 = 720;

    private final AplicacaoRepository aplicacaoRepository;
    private final ProdutoInvestimentoRepository produtoRepository;
    private final IndiceMercadoService indiceMercadoService;

    public AplicacaoService(AplicacaoRepository aplicacaoRepository,
                            ProdutoInvestimentoRepository produtoRepository,
                            IndiceMercadoService indiceMercadoService) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.produtoRepository = produtoRepository;
        this.indiceMercadoService = indiceMercadoService;
    }

    @Transactional
    public AplicacaoResponse aplicar(AplicacaoRequest request) {
        var produto = produtoRepository.findById(request.produtoId())
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + request.produtoId()));

        if (!produto.getAtivo()) {
            throw new IllegalStateException("Produto não está ativo: " + request.produtoId());
        }

        if (request.valor().compareTo(produto.getValorMinimo()) < 0) {
            throw new IllegalArgumentException(
                "Valor mínimo para aplicação: " + produto.getValorMinimo());
        }

        var taxaParaRendimento = resolverTaxaRendimento(produto);

        var dataAplicacao = LocalDate.now();
        var dataVencimento = produto.getDataVencimento() != null
            ? produto.getDataVencimento()
            : dataAplicacao.plusDays(produto.getPrazoMinimoDias());

        var aplicacao = new Aplicacao(
            request.tenantId(), request.clienteId(), request.produtoId(),
            produto.getTipo().name(), request.valor(), taxaParaRendimento,
            dataAplicacao, dataVencimento, request.contaCorrenteId()
        );

        var rendimentoBruto = calcularRendimentoBruto(request.valor(), taxaParaRendimento,
            dataAplicacao, dataVencimento);
        aplicacao.setValorBruto(request.valor().add(rendimentoBruto));

        var iof = calcularIOF(rendimentoBruto, dataAplicacao, dataAplicacao);
        aplicacao.setIof(iof);

        var ir = calcularIR(rendimentoBruto, dataAplicacao, dataAplicacao);
        aplicacao.setIr(ir);

        var valorLiquido = aplicacao.getValorBruto().subtract(iof).subtract(ir);
        aplicacao.setValorLiquido(valorLiquido);

        aplicacao = aplicacaoRepository.save(aplicacao);

        log.info("Aplicação realizada: id={}, produto={}, valor={}", aplicacao.getId(),
            produto.getTipo().name(), request.valor());

        return toResponse(aplicacao);
    }

    @Transactional(readOnly = true)
    public AplicacaoResponse buscarPorId(Long id) {
        var entity = aplicacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Aplicação não encontrada: " + id));
        return toResponse(entity);
    }

    @Transactional
    public AplicacaoResponse resgatar(Long id) {
        var aplicacao = aplicacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Aplicação não encontrada: " + id));

        if (aplicacao.getStatus() == StatusAplicacao.RESGATADA
                || aplicacao.getStatus() == StatusAplicacao.CANCELADA) {
            throw new IllegalStateException("Aplicação já foi resgatada ou cancelada: " + id);
        }

        var produto = produtoRepository.findById(aplicacao.getProdutoId())
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + aplicacao.getProdutoId()));

        if (produto.getCarenciaDias() != null) {
            var diasDesdeAplicacao = ChronoUnit.DAYS.between(aplicacao.getDataAplicacao(), LocalDate.now());
            if (diasDesdeAplicacao < produto.getCarenciaDias()) {
                throw new IllegalStateException(
                    "Carência de " + produto.getCarenciaDias() + " dias não atingida. "
                    + "Dias desde aplicação: " + diasDesdeAplicacao);
            }
        }

        var dataResgate = LocalDate.now();
        var rendimentoBruto = calcularRendimentoBruto(
            aplicacao.getValorAplicado(), aplicacao.getTaxaRendimento(),
            aplicacao.getDataAplicacao(), dataResgate);
        aplicacao.setValorBruto(aplicacao.getValorAplicado().add(rendimentoBruto));

        var iof = calcularIOF(rendimentoBruto, aplicacao.getDataAplicacao(), dataResgate);
        aplicacao.setIof(iof);

        var ir = calcularIR(rendimentoBruto, aplicacao.getDataAplicacao(), dataResgate);
        aplicacao.setIr(ir);

        var valorLiquido = aplicacao.getValorBruto().subtract(iof).subtract(ir);
        aplicacao.setValorLiquido(valorLiquido);
        aplicacao.setDataResgate(dataResgate);
        aplicacao.setStatus(StatusAplicacao.RESGATADA);

        var aplicacaoSalva = aplicacaoRepository.save(aplicacao);

        log.info("Resgate realizado: id={}, valorBruto={}, iof={}, ir={}, liquido={}",
            aplicacaoSalva.getId(), aplicacaoSalva.getValorBruto(), iof, ir, valorLiquido);

        return toResponse(aplicacaoSalva);
    }

    private BigDecimal calcularRendimentoBruto(BigDecimal valor, BigDecimal taxaAnual,
                                               LocalDate dataInicio, LocalDate dataFim) {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        if (dias <= 0) {
            return BigDecimal.ZERO;
        }
        return valor.multiply(taxaAnual)
            .multiply(BigDecimal.valueOf(dias))
            .divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularIOF(BigDecimal rendimentoBruto, LocalDate dataInicio, LocalDate dataFim) {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        if (dias >= IOF_DIAS_SEM_IOF) {
            return BigDecimal.ZERO;
        }
        var aliquota = TAXA_IOF_MAXIMA.multiply(BigDecimal.valueOf(IOF_DIAS_SEM_IOF - dias))
            .divide(BigDecimal.valueOf(IOF_DIAS_SEM_IOF), 6, RoundingMode.HALF_UP);
        return rendimentoBruto.multiply(aliquota).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularIR(BigDecimal rendimentoBruto, LocalDate dataInicio, LocalDate dataFim) {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        BigDecimal aliquota;
        if (dias < DIAS_IR_22_5) {
            aliquota = IR_22_5;
        } else if (dias < DIAS_IR_20_0) {
            aliquota = IR_20_0;
        } else if (dias < DIAS_IR_17_5) {
            aliquota = IR_17_5;
        } else {
            aliquota = IR_15_0;
        }
        return rendimentoBruto.multiply(aliquota).setScale(2, RoundingMode.HALF_UP);
    }

    private AplicacaoResponse toResponse(Aplicacao a) {
        return new AplicacaoResponse(
            a.getId(), a.getTenantId(), a.getClienteId(), a.getProdutoId(),
            a.getProdutoTipo(), a.getValorAplicado(), a.getValorBruto(),
            a.getValorLiquido(), a.getIof(), a.getIr(), a.getTaxaRendimento(),
            a.getDataAplicacao(), a.getDataVencimento(), a.getDataResgate(),
            a.getStatus().name(), a.getDataCriacao()
        );
    }

    /**
     * Resolve a taxa anual (fração decimal, ex.: 0.1265) usada no cálculo de
     * rendimento. Quando o produto é atrelado a um indexador (SELIC/CDI/IMAB),
     * usa a taxa anual projetada do índice obtida da API do BACEN, aplicando o
     * percentual do indexador. Caso contrário, usa a taxa fixa cadastrada.
     */
    private BigDecimal resolverTaxaRendimento(ProdutoInvestimento produto) {
        TipoIndice indexador = produto.getIndexador();
        if (indexador != null) {
            BigDecimal taxaProjetada = indiceMercadoService.buscarTaxaProjetadaAnual(
                indexador, produto.getPercentualIndexador());
            if (taxaProjetada != null) {
                log.info("Produto {} indexado a {}: taxa anual projetada {}",
                    produto.getId(), indexador, taxaProjetada);
                return taxaProjetada;
            }
            log.warn("Índice {} indisponível para o produto {}, usando taxa fixa {}",
                indexador, produto.getId(), produto.getTaxaRendimento());
        }
        return produto.getTaxaRendimento();
    }
}
