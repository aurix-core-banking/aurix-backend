package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.client.CartorioRgiClient;
import com.aurix.platform.credit.financiamento.client.ContaCorrenteClient;
import com.aurix.platform.credit.financiamento.client.DetranClient;
import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.credit.financiamento.dto.request.RenegociarRequest;
import com.aurix.platform.credit.financiamento.dto.response.BemResponse;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResponse;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResumoResponse;
import com.aurix.platform.credit.financiamento.dto.response.GarantiaResponse;
import com.aurix.platform.credit.financiamento.entity.BemFinanciado;
import com.aurix.platform.credit.financiamento.entity.ContratoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.Garantia;
import com.aurix.platform.credit.financiamento.entity.ParcelaFinanciamento;
import com.aurix.platform.credit.financiamento.entity.SistemaAmortizacao;
import com.aurix.platform.credit.financiamento.entity.StatusContrato;
import com.aurix.platform.credit.financiamento.entity.StatusGarantia;
import com.aurix.platform.credit.financiamento.entity.StatusParcela;
import com.aurix.platform.credit.financiamento.entity.TipoBem;
import com.aurix.platform.credit.financiamento.entity.TipoGarantia;
import com.aurix.platform.credit.financiamento.event.ContratoFinanciamentoAssinadoEvent;
import com.aurix.platform.credit.financiamento.event.ContratoLiquidadoEvent;
import com.aurix.platform.credit.financiamento.event.GarantiaRegistradaEvent;
import com.aurix.platform.credit.financiamento.repository.BemFinanciadoRepository;
import com.aurix.platform.credit.financiamento.repository.ContratoFinanciamentoRepository;
import com.aurix.platform.credit.financiamento.repository.GarantiaRepository;
import com.aurix.platform.credit.financiamento.repository.ParcelaFinanciamentoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoFinanciamentoService {

    private static final Logger log = LoggerFactory.getLogger(ContratoFinanciamentoService.class);

    private final ContratoFinanciamentoRepository contratoRepository;
    private final ParcelaFinanciamentoRepository parcelaRepository;
    private final BemFinanciadoRepository bemRepository;
    private final GarantiaRepository garantiaRepository;
    private final AmortizacaoService amortizacaoService;
    private final ContaCorrenteClient contaCorrenteClient;
    private final CartorioRgiClient cartorioRgiClient;
    private final DetranClient detranClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ContratoFinanciamentoService self;
    private final BigDecimal taxaSacPadrao;
    private final BigDecimal taxaPricePadrao;
    private final BigDecimal taxaSacrePadrao;

    public ContratoFinanciamentoService(ContratoFinanciamentoRepository contratoRepository,
                                        ParcelaFinanciamentoRepository parcelaRepository,
                                        BemFinanciadoRepository bemRepository,
                                        GarantiaRepository garantiaRepository,
                                        AmortizacaoService amortizacaoService,
                                        ContaCorrenteClient contaCorrenteClient,
                                        CartorioRgiClient cartorioRgiClient,
                                        DetranClient detranClient,
                                        KafkaTemplate<String, Object> kafkaTemplate,
                                        @Lazy ContratoFinanciamentoService self,
                                        @Value("${aurix.financiamento.taxa-sac:0.0099}") BigDecimal taxaSacPadrao,
                                        @Value("${aurix.financiamento.taxa-price:0.0112}") BigDecimal taxaPricePadrao,
                                        @Value("${aurix.financiamento.taxa-sacre:0.0105}") BigDecimal taxaSacrePadrao) {
        this.contratoRepository = contratoRepository;
        this.parcelaRepository = parcelaRepository;
        this.bemRepository = bemRepository;
        this.garantiaRepository = garantiaRepository;
        this.amortizacaoService = amortizacaoService;
        this.contaCorrenteClient = contaCorrenteClient;
        this.cartorioRgiClient = cartorioRgiClient;
        this.detranClient = detranClient;
        this.kafkaTemplate = kafkaTemplate;
        this.self = self;
        this.taxaSacPadrao = taxaSacPadrao;
        this.taxaPricePadrao = taxaPricePadrao;
        this.taxaSacrePadrao = taxaSacrePadrao;
    }

    @Transactional
    public ContratoResponse contratar(CriarContratoRequest request) {
        var taxaJuros = switch (request.getSistemaAmortizacao()) {
            case SAC -> taxaSacPadrao;
            case PRICE -> taxaPricePadrao;
            case SACRE -> taxaSacrePadrao;
        };

        var valor = request.getValorFinanciado();
        var prazo = request.getPrazoMeses();
        var sistema = request.getSistemaAmortizacao();

        var tabela = amortizacaoService.gerarTabelaPrice(valor, prazo, taxaJuros);
        var valorParcela = tabela.get(0).valorParcela();

        var contrato = new ContratoFinanciamento(
            "DEFAULT", request.getClienteId(), request.getTipo(), valor,
            request.getValorEntrada() != null ? request.getValorEntrada() : BigDecimal.ZERO,
            taxaJuros, prazo, sistema, valorParcela, valor,
            LocalDate.now(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(1),
            StatusContrato.PROPOSTA, request.getContaCorrenteId()
        );
        contrato = contratoRepository.save(contrato);

        var parcelas = gerarParcelas(contrato, taxaJuros);
        parcelaRepository.saveAll(parcelas);

        BemFinanciado bem = null;
        if (request.getBem() != null) {
            var bemReq = request.getBem();
            bem = new BemFinanciado(contrato.getId(), TipoBem.valueOf(bemReq.getTipo()),
                bemReq.getDescricao(), bemReq.getValorAvaliacao(),
                bemReq.getChassi(), bemReq.getPlaca(), bemReq.getMatriculaRGI(), null);
            bem = bemRepository.save(bem);
        }

        Garantia garantia = null;
        if (request.getGarantia() != null && bem != null) {
            var garReq = request.getGarantia();
            garantia = new Garantia(contrato.getId(), bem.getId(),
                TipoGarantia.valueOf(garReq.getTipo()), garReq.getValor(),
                LocalDate.now(), null, StatusGarantia.ATIVA, garReq.getOrgaoRegistro());

            if ("RGI".equals(garReq.getOrgaoRegistro()) || "CARTORIO".equals(garReq.getOrgaoRegistro())) {
                CartorioRgiClient.RegistroResponse response;
                try {
                    response = cartorioRgiClient.registrarGarantia(
                        new CartorioRgiClient.RegistroGarantiaRequest(garReq.getTipo(), garReq.getValor(), garReq.getOrgaoRegistro()));
                } catch (Exception e) {
                    log.error("Erro ao registrar garantia no cartório: {}", e.getMessage());
                    throw new IllegalStateException("Não foi possível registrar a garantia no cartório: " + e.getMessage(), e);
                }
                if (response == null
                        || (response.protocolo() == null && response.matricula() == null)) {
                    throw new IllegalStateException("Cartório não confirmou o registro da garantia");
                }
                garantia = new Garantia(contrato.getId(), bem.getId(),
                    TipoGarantia.valueOf(garReq.getTipo()), garReq.getValor(),
                    response.dataRegistro() != null ? response.dataRegistro() : LocalDate.now(),
                    null, StatusGarantia.ATIVA, garReq.getOrgaoRegistro());
                garantia.setProtocoloRegistro(
                    response.protocolo() != null ? response.protocolo() : response.matricula());
            } else if ("DETRAN".equals(garReq.getOrgaoRegistro())) {
                DetranClient.DetranResponse response;
                try {
                    response = detranClient.registrarGarantia(
                        new DetranClient.DetranGarantiaRequest(
                            bem.getPlaca(), bem.getChassi(), garReq.getValor(), "Aurix"));
                } catch (Exception e) {
                    log.error("Erro ao registrar garantia no Detran: {}", e.getMessage());
                    throw new IllegalStateException("Não foi possível registrar a garantia no Detran: " + e.getMessage(), e);
                }
                if (response == null
                        || (response.protocolo() == null && response.registro() == null)) {
                    throw new IllegalStateException("Detran não confirmou o registro da garantia");
                }
                garantia = new Garantia(contrato.getId(), bem.getId(),
                    TipoGarantia.valueOf(garReq.getTipo()), garReq.getValor(),
                    LocalDate.now(), null, StatusGarantia.ATIVA, garReq.getOrgaoRegistro());
                garantia.setProtocoloRegistro(
                    response.protocolo() != null ? response.protocolo() : response.registro());
            }
            garantia = garantiaRepository.save(garantia);
        }

        if (request.getContaCorrenteId() != null) {
            try {
                contaCorrenteClient.creditar(request.getContaCorrenteId(),
                    new ContaCorrenteClient.CreditoRequest(valor, "Crédito financiamento"));
            } catch (Exception e) {
                log.warn("Erro ao creditar valor na conta corrente: {}", e.getMessage());
            }
        }

        contrato.setStatus(StatusContrato.ASSINADO);
        contrato = contratoRepository.save(contrato);

        var finalContrato = contrato;
        try {
            kafkaTemplate.send(Topics.FINANCIAMENTO_CONTRATO_ASSINADO,
                new ContratoFinanciamentoAssinadoEvent(finalContrato.getId(), finalContrato.getClienteId(),
                    finalContrato.getContaCorrenteId(), finalContrato.getTipo().name(),
                    finalContrato.getValorFinanciado(), finalContrato.getPrazoMeses(),
                    finalContrato.getTaxaJuros(), finalContrato.getDataContratacao(), finalContrato.getTenantId()));
        } catch (Exception e) {
            log.warn("Erro ao publicar ContratoFinanciamentoAssinadoEvent: {}", e.getMessage());
        }

        if (garantia != null) {
            var finalGarantia = garantia;
            try {
                kafkaTemplate.send(Topics.FINANCIAMENTO_GARANTIA_REGISTRADA,
                    new GarantiaRegistradaEvent(finalGarantia.getId(), finalGarantia.getContratoId(),
                        finalGarantia.getBemId(), finalGarantia.getTipo().name(),
                        finalGarantia.getOrgaoRegistro(), finalGarantia.getDataRegistro(),
                        finalContrato.getTenantId()));
            } catch (Exception e) {
                log.warn("Erro ao publicar GarantiaRegistradaEvent: {}", e.getMessage());
            }
        }

        contrato.setStatus(StatusContrato.ATIVO);
        contrato = contratoRepository.save(contrato);

        log.info("Contrato criado: id={}, clienteId={}, valor={}", contrato.getId(), contrato.getClienteId(), valor);
        return toResponse(contrato);
    }

    @Transactional(readOnly = true)
    public ContratoResponse buscarPorId(Long id) {
        var entity = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ContratoResumoResponse> listarPorCliente(Long clienteId) {
        return contratoRepository.findByClienteId(clienteId).stream()
            .map(this::toResumoResponse)
            .toList();
    }

    @Transactional
    public void liquidar(Long id) {
        var contrato = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));

        if (contrato.getStatus() == StatusContrato.LIQUIDADO) {
            throw new IllegalStateException("Contrato já está liquidado: " + id);
        }

        var parcelasPendentes = parcelaRepository.findByContratoIdAndStatus(id, StatusParcela.PENDENTE);
        var parcelasAtrasadas = parcelaRepository.findByContratoIdAndStatus(id, StatusParcela.ATRASADA);

        var saldoDevedor = BigDecimal.ZERO;
        for (var p : parcelasPendentes) {
            saldoDevedor = saldoDevedor.add(p.getValorParcela());
        }
        for (var p : parcelasAtrasadas) {
            saldoDevedor = saldoDevedor.add(p.getValorParcela());
        }

        if (contrato.getContaCorrenteId() != null) {
            try {
                contaCorrenteClient.debitar(contrato.getContaCorrenteId(),
                    new ContaCorrenteClient.DebitoRequest(saldoDevedor, "Liquidação financiamento"));
            } catch (Exception e) {
                log.warn("Erro ao debitar valor da conta corrente: {}", e.getMessage());
            }
        }

        contrato.setStatus(StatusContrato.LIQUIDADO);
        contrato.setSaldoDevedor(BigDecimal.ZERO);
        contratoRepository.save(contrato);

        for (var p : parcelasPendentes) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }
        for (var p : parcelasAtrasadas) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }

        var garantias = garantiaRepository.findByContratoId(id);
        for (var g : garantias) {
            try {
                if ("RGI".equals(g.getOrgaoRegistro()) || "CARTORIO".equals(g.getOrgaoRegistro())) {
                    cartorioRgiClient.consultar(g.getProtocoloRegistro() != null
                        ? g.getProtocoloRegistro() : g.getId().toString());
                } else if ("DETRAN".equals(g.getOrgaoRegistro())) {
                    detranClient.consultarVeiculo(g.getProtocoloRegistro() != null
                        ? g.getProtocoloRegistro() : g.getId().toString());
                }
                log.info("Baixa externa confirmada para garantia {}", g.getId());
            } catch (Exception e) {
                log.error("Falha na baixa externa da garantia {} na liquidação: {}", g.getId(), e.getMessage());
                throw new IllegalStateException("Não foi possível dar baixa externa na garantia " + g.getId() + ": " + e.getMessage(), e);
            }
            g.setStatus(StatusGarantia.LIBERADA);
            g.setDataBaixa(LocalDate.now());
            garantiaRepository.save(g);
        }

        try {
            kafkaTemplate.send(Topics.FINANCIAMENTO_CONTRATO_LIQUIDADO,
                new ContratoLiquidadoEvent(contrato.getId(), contrato.getClienteId(),
                    saldoDevedor, LocalDate.now(), contrato.getTenantId()));
        } catch (Exception e) {
            log.warn("Erro ao publicar ContratoLiquidadoEvent: {}", e.getMessage());
        }

        log.info("Contrato liquidado: id={}", contrato.getId());
    }

    @Transactional
    public ContratoResponse renegociar(Long id, RenegociarRequest request) {
        var contrato = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));

        if (contrato.getStatus() == StatusContrato.LIQUIDADO) {
            throw new IllegalStateException("Contrato liquidado não pode ser renegociado: " + id);
        }

        var parcelasPendentes = parcelaRepository.findByContratoIdAndStatus(id, StatusParcela.PENDENTE);
        for (var p : parcelasPendentes) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }
        var parcelasAtrasadas = parcelaRepository.findByContratoIdAndStatus(id, StatusParcela.ATRASADA);
        for (var p : parcelasAtrasadas) {
            p.setStatus(StatusParcela.CANCELADA);
            parcelaRepository.save(p);
        }

        var novaTaxa = request.getNovaTaxa() != null ? request.getNovaTaxa() : contrato.getTaxaJuros();
        var novoPrazo = request.getNovosPrazos() > 0 ? request.getNovosPrazos() : contrato.getPrazoMeses();
        var saldoAtual = contrato.getSaldoDevedor();

        contrato.setPrazoMeses(novoPrazo);
        contrato.setTaxaJuros(novaTaxa);
        contrato.setSaldoDevedor(saldoAtual);

        var tabela = amortizacaoService.gerarTabelaPrice(saldoAtual, novoPrazo, novaTaxa);
        var novaValorParcela = tabela.get(0).valorParcela();
        contrato.setValorParcela(novaValorParcela);

        contratoRepository.save(contrato);

        for (int i = 0; i < novoPrazo; i++) {
            var linha = tabela.get(i);
            var parcela = new ParcelaFinanciamento(
                contrato.getId(), i + 1, LocalDate.now().plusMonths(i + 1),
                linha.valorParcela(), linha.amortizacao(), linha.juros(),
                linha.saldoDevedor(), null, StatusParcela.PENDENTE
            );
            parcelaRepository.save(parcela);
        }

        log.info("Contrato renegociado: id={}, novoPrazo={}, novaTaxa={}", contrato.getId(), novoPrazo, novaTaxa);
        return toResponse(contrato);
    }

    private List<ParcelaFinanciamento> gerarParcelas(ContratoFinanciamento contrato, BigDecimal taxaJuros) {
        return amortizacaoService.gerarTabelaPrice(
            contrato.getValorFinanciado(), contrato.getPrazoMeses(), taxaJuros
        ).stream().map(linha -> new ParcelaFinanciamento(
            contrato.getId(), linha.numero(),
            contrato.getDataPrimeiraParcela().plusMonths(linha.numero() - 1),
            linha.valorParcela(), linha.amortizacao(), linha.juros(),
            linha.saldoDevedor(), null, StatusParcela.PENDENTE
        )).toList();
    }

    private ContratoResponse toResponse(ContratoFinanciamento c) {
        var bens = bemRepository.findByContratoId(c.getId()).stream()
            .map(this::toBemResponse).toList();
        var garantias = garantiaRepository.findByContratoId(c.getId()).stream()
            .map(this::toGarantiaResponse).toList();
        return new ContratoResponse(c.getId(), c.getClienteId(), c.getTipo().name(),
            c.getValorFinanciado(), c.getValorEntrada(), c.getTaxaJuros(),
            c.getPrazoMeses(), c.getSistemaAmortizacao().name(), c.getValorParcela(),
            c.getSaldoDevedor(), c.getDataContratacao(), c.getDataPrimeiraParcela(),
            c.getStatus().name(), bens, garantias, c.getDataCriacao());
    }

    private ContratoResumoResponse toResumoResponse(ContratoFinanciamento c) {
        return new ContratoResumoResponse(c.getId(), c.getTipo().name(),
            c.getValorFinanciado(), c.getSaldoDevedor(), c.getStatus().name(),
            c.getDataContratacao());
    }

    private BemResponse toBemResponse(BemFinanciado b) {
        return new BemResponse(b.getId(), b.getTipo().name(), b.getDescricao(),
            b.getValorAvaliacao(), b.getChassi(), b.getPlaca(),
            b.getMatriculaRGI(), b.getRegistroGarantia());
    }

    private GarantiaResponse toGarantiaResponse(Garantia g) {
        return new GarantiaResponse(g.getId(), g.getTipo().name(), g.getValor(),
            g.getDataRegistro(), g.getDataBaixa(), g.getStatus().name(), g.getOrgaoRegistro(),
            g.getProtocoloRegistro());
    }
}
