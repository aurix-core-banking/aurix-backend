package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.dto.ContratoCambioResponse;
import com.aurix.platform.cambio.dto.FecharContratoRequest;
import com.aurix.platform.cambio.dto.LiquidarContratoRequest;
import com.aurix.platform.cambio.entity.ContratoCambio;
import com.aurix.platform.cambio.entity.OperacaoCambio;
import com.aurix.platform.cambio.event.ContratoFechadoEvent;
import com.aurix.platform.cambio.event.ContratoLiquidadoEvent;
import com.aurix.platform.cambio.repository.ContratoCambioRepository;
import com.aurix.platform.cambio.repository.OperacaoCambioRepository;
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
public class ContratoCambioService {

    private static final Logger log = LoggerFactory.getLogger(ContratoCambioService.class);

    private final ContratoCambioRepository contratoRepository;
    private final OperacaoCambioRepository operacaoRepository;
    private final ComplianceCambialService complianceCambialService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ContratoCambioService self;

    public ContratoCambioService(ContratoCambioRepository contratoRepository,
                                 OperacaoCambioRepository operacaoRepository,
                                 ComplianceCambialService complianceCambialService,
                                 KafkaTemplate<String, Object> kafkaTemplate,
                                 @Lazy ContratoCambioService self) {
        this.contratoRepository = contratoRepository;
        this.operacaoRepository = operacaoRepository;
        this.complianceCambialService = complianceCambialService;
        this.kafkaTemplate = kafkaTemplate;
        this.self = self;
    }

    @Transactional
    public ContratoCambioResponse fecharContrato(FecharContratoRequest request) {
        BigDecimal valorDestino = request.getValorOrigem()
            .multiply(request.getTaxaCambio())
            .setScale(6, RoundingMode.HALF_EVEN);

        ContratoCambio contrato = new ContratoCambio(
            request.getClienteId(),
            request.getTipo(),
            request.getMoedaOrigem(),
            request.getMoedaDestino(),
            request.getValorOrigem(),
            valorDestino,
            request.getTaxaCambio(),
            LocalDate.now(),
            null,
            request.getFinalidade() != null ? request.getFinalidade() : "turismo",
            "COTADO",
            null,
            "DEFAULT"
        );
        contrato = contratoRepository.save(contrato);

        complianceCambialService.validarLimitesCliente(
            contrato.getClienteId(), contrato.getValorDestino(), contrato.getMoedaDestino());

        contrato.setStatus("CONTRATADO");
        contrato = contratoRepository.save(contrato);

        complianceCambialService.registrarOperacaoBacen(contrato);

        OperacaoCambio operacao = new OperacaoCambio(
            contrato.getId(), contrato.getClienteId(), contrato.getTipo(),
            contrato.getValorOrigem(), contrato.getValorDestino(),
            contrato.getTaxaCambio(), LocalDateTime.now(), null, "DEFAULT"
        );
        operacaoRepository.save(operacao);

        try {
            kafkaTemplate.send(Topics.CAMBIO_CONTRATO_FECHADO, new ContratoFechadoEvent(
                contrato.getId(), contrato.getClienteId(), contrato.getTipo(),
                contrato.getMoedaOrigem(), contrato.getMoedaDestino(),
                contrato.getValorOrigem(), contrato.getValorDestino(),
                contrato.getTaxaCambio(), contrato.getRegistroBACEN(),
                contrato.getDataContratacao(), contrato.getTenantId()));
        } catch (Exception e) {
            // fire-and-forget with try-catch
        }

        log.info("Contrato fechado: id={}, clienteId={}, valor={}",
            contrato.getId(), contrato.getClienteId(), contrato.getValorOrigem());
        return toResponse(contrato);
    }

    @Transactional(readOnly = true)
    public ContratoCambioResponse buscarPorId(Long id) {
        ContratoCambio entity = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato nao encontrado: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ContratoCambioResponse> listarPorCliente(Long clienteId) {
        return contratoRepository.findByClienteId(clienteId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ContratoCambioResponse liquidar(Long id, LiquidarContratoRequest request) {
        ContratoCambio entity = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato nao encontrado: " + id));
        entity.setStatus("LIQUIDADO");
        entity.setDataLiquidacao(LocalDate.now());
        entity = contratoRepository.save(entity);

        try {
            kafkaTemplate.send(Topics.CAMBIO_CONTRATO_LIQUIDADO, new ContratoLiquidadoEvent(
                entity.getId(), entity.getClienteId(), entity.getValorDestino(),
                entity.getDataLiquidacao(), entity.getTenantId()));
        } catch (Exception e) {
            // fire-and-forget with try-catch
        }

        log.info("Contrato liquidado: id={}", entity.getId());
        return toResponse(entity);
    }

    @Transactional
    public void cancelar(Long id) {
        ContratoCambio entity = contratoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Contrato nao encontrado: " + id));
        entity.setStatus("CANCELADO");
        contratoRepository.save(entity);
        log.info("Contrato cancelado: id={}", entity.getId());
    }

    private ContratoCambioResponse toResponse(ContratoCambio e) {
        return new ContratoCambioResponse(
            e.getId(), e.getClienteId(), e.getTipo(), e.getMoedaOrigem(),
            e.getMoedaDestino(), e.getValorOrigem(), e.getValorDestino(),
            e.getTaxaCambio(), e.getDataContratacao(), e.getDataLiquidacao(),
            e.getFinalidade(), e.getStatus(), e.getRegistroBACEN(), e.getDataCriacao()
        );
    }
}
