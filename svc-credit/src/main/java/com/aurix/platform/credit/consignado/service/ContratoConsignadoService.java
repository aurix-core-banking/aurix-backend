package com.aurix.platform.credit.consignado.service;

import com.aurix.platform.credit.consignado.dto.ContratoConsignadoResponse;
import com.aurix.platform.credit.consignado.dto.CriarContratoRequest;
import com.aurix.platform.credit.consignado.dto.RenegociarRequest;
import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import com.aurix.platform.credit.consignado.event.ContratoAssinadoEvent;
import com.aurix.platform.credit.consignado.event.ContratoLiquidadoEvent;
import com.aurix.platform.credit.consignado.repository.ContratoConsignadoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoConsignadoService {

    private static final Logger log = LoggerFactory.getLogger(ContratoConsignadoService.class);

    private final ContratoConsignadoRepository repository;
    private final MargemService margemService;
    private final ParcelaService parcelaService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ContratoConsignadoService self;

    public ContratoConsignadoService(ContratoConsignadoRepository repository,
                                     MargemService margemService,
                                     ParcelaService parcelaService,
                                     KafkaTemplate<String, Object> kafkaTemplate,
                                     @Lazy ContratoConsignadoService self) {
        this.repository = repository;
        this.margemService = margemService;
        this.parcelaService = parcelaService;
        this.kafkaTemplate = kafkaTemplate;
        this.self = self;
    }

    @Transactional
    public ContratoConsignadoResponse criarContrato(CriarContratoRequest request) {
        margemService.validarMargemDisponivel(request.getClienteId(), request.getValorTotal());

        BigDecimal valorParcela = request.getValorTotal()
            .divide(BigDecimal.valueOf(request.getPrazoMeses()), 2, RoundingMode.HALF_EVEN);

        var entity = new ContratoConsignado(
            request.getClienteId(),
            request.getContaSalarioId(),
            request.getValorTotal(),
            request.getTaxaJuros(),
            request.getPrazoMeses(),
            valorParcela,
            valorParcela,
            request.getFonteMargem(),
            "PROPOSTA",
            LocalDate.now(),
            "DEFAULT"
        );
        entity = repository.save(entity);

        parcelaService.gerarParcelas(entity);

        try {
            kafkaTemplate.send(Topics.CONSIGNADO_CONTRATO_ASSINADO, new ContratoAssinadoEvent(
                entity.getId(), entity.getClienteId(), entity.getValorTotal(),
                entity.getPrazoMeses(), entity.getValorParcela(),
                entity.getFonteMargem(), entity.getDataContratacao(), entity.getTenantId()));
        } catch (Exception e) {
            // fire-and-forget with try-catch
        }

        entity.setStatus("ATIVO");
        entity = repository.save(entity);

        margemService.atualizarMargem(entity.getClienteId(), entity.getFonteMargem(), valorParcela);

        log.info("Contrato criado: id={}, clienteId={}, valor={}", entity.getId(), entity.getClienteId(), entity.getValorTotal());
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public ContratoConsignadoResponse buscarPorId(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ContratoConsignadoResponse> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ContratoConsignadoResponse liquidar(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));
        entity.setStatus("LIQUIDADO");
        entity = repository.save(entity);

        BigDecimal valorTotalPago = entity.getValorParcela()
            .multiply(BigDecimal.valueOf(entity.getPrazoMeses()));

        try {
            kafkaTemplate.send(Topics.CONSIGNADO_CONTRATO_LIQUIDADO, new ContratoLiquidadoEvent(
                entity.getId(), entity.getClienteId(), valorTotalPago,
                LocalDate.now(), entity.getTenantId()));
        } catch (Exception e) {
            // fire-and-forget with try-catch
        }

        log.info("Contrato liquidado: id={}", entity.getId());
        return toResponse(entity);
    }

    @Transactional
    public ContratoConsignadoResponse renegociar(Long id, RenegociarRequest request) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));
        entity.setValorTotal(request.getNovoValor());
        entity.setPrazoMeses(request.getNovoPrazoMeses());
        entity.setTaxaJuros(request.getNovaTaxaJuros());
        BigDecimal novaValorParcela = request.getNovoValor()
            .divide(BigDecimal.valueOf(request.getNovoPrazoMeses()), 2, RoundingMode.HALF_EVEN);
        entity.setValorParcela(novaValorParcela);
        entity.setMargemUtilizada(novaValorParcela);
        entity = repository.save(entity);
        log.info("Contrato renegociado: id={}", entity.getId());
        return toResponse(entity);
    }

    private ContratoConsignadoResponse toResponse(ContratoConsignado e) {
        return new ContratoConsignadoResponse(
            e.getId(), e.getClienteId(), e.getContaSalarioId(),
            e.getValorTotal(), e.getTaxaJuros(), e.getPrazoMeses(),
            e.getValorParcela(), e.getMargemUtilizada(), e.getFonteMargem(),
            e.getStatus(), e.getDataContratacao(), e.getTenantId(),
            e.getDataCriacao(), e.getDataAtualizacao());
    }
}
