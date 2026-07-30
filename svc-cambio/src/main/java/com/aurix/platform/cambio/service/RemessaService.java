package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.client.SwiftClient;
import com.aurix.platform.cambio.dto.RemessaRequest;
import com.aurix.platform.cambio.dto.RemessaResponse;
import com.aurix.platform.cambio.entity.Remessa;
import com.aurix.platform.cambio.event.RemessaProcessadaEvent;
import com.aurix.platform.cambio.repository.RemessaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemessaService {

    private static final Logger log = LoggerFactory.getLogger(RemessaService.class);

    private final RemessaRepository remessaRepository;
    private final ComplianceCambialService complianceCambialService;
    private final SwiftClient swiftClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RemessaService(RemessaRepository remessaRepository,
                          ComplianceCambialService complianceCambialService,
                          SwiftClient swiftClient,
                          KafkaTemplate<String, Object> kafkaTemplate) {
        this.remessaRepository = remessaRepository;
        this.complianceCambialService = complianceCambialService;
        this.swiftClient = swiftClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public RemessaResponse solicitarRemessa(RemessaRequest request) {
        complianceCambialService.validarLimitesCliente(request.getClienteId(), request.getValor(), request.getMoeda());
        complianceCambialService.validarFinalidade(request.getFinalidade());

        Remessa remessa = new Remessa(
            request.getContratoId(), request.getClienteId(), request.getValor(),
            request.getMoeda(), request.getBancoDestino(), request.getContaDestino(),
            request.getCodigoSwift(), request.getFinalidade(), "PENDENTE",
            LocalDateTime.now(), null, "DEFAULT"
        );
        remessa = remessaRepository.save(remessa);

        log.info("Remessa solicitada: id={}, clienteId={}, valor={}",
            remessa.getId(), remessa.getClienteId(), remessa.getValor());
        return toResponse(remessa);
    }

    @Transactional(readOnly = true)
    public RemessaResponse buscarPorId(Long id) {
        Remessa entity = remessaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Remessa nao encontrada: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<RemessaResponse> listarPorCliente(Long clienteId) {
        return remessaRepository.findByClienteId(clienteId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public void cancelar(Long id) {
        Remessa entity = remessaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Remessa nao encontrada: " + id));
        if (!"PENDENTE".equals(entity.getStatus())) {
            throw new IllegalStateException("Somente remessas pendentes podem ser canceladas");
        }
        entity.setStatus("CANCELADA");
        remessaRepository.save(entity);
        log.info("Remessa cancelada: id={}", entity.getId());
    }

    @Transactional
    public RemessaResponse processarRemessa(Long id) {
        Remessa entity = remessaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Remessa nao encontrada: " + id));

        if (!"PENDENTE".equals(entity.getStatus())) {
            return toResponse(entity);
        }

        try {
            SwiftClient.SwiftStatusResponse swiftResponse = swiftClient.enviarRemessa(
                new SwiftClient.EnviarRemessaSwiftRequest(
                    entity.getValor(), entity.getMoeda(), entity.getBancoDestino(),
                    entity.getContaDestino(), entity.getCodigoSwift(), entity.getFinalidade()
                )
            );

            entity.setStatus("ENVIADA");
            entity.setDataConfirmacao(LocalDateTime.now());
            entity = remessaRepository.save(entity);

            try {
                kafkaTemplate.send(Topics.CAMBIO_REMESSA_PROCESSADA, new RemessaProcessadaEvent(
                    entity.getId(), entity.getContratoId(), entity.getClienteId(),
                    entity.getValor(), entity.getMoeda(), entity.getStatus(),
                    entity.getCodigoSwift(), entity.getDataConfirmacao(), entity.getTenantId()));
            } catch (Exception e) {
                // fire-and-forget with try-catch
            }

            log.info("Remessa processada: id={}, swiftStatus={}", entity.getId(), swiftResponse.statusSwift());
        } catch (Exception e) {
            entity.setStatus("FALHADA");
            remessaRepository.save(entity);
            log.error("Falha ao processar remessa: id={}", entity.getId(), e);
        }

        return toResponse(entity);
    }

    @Transactional
    public void processarRemessasPendentes() {
        List<Remessa> pendentes = remessaRepository.findByStatus("PENDENTE");
        log.info("Processando {} remessas pendentes", pendentes.size());
        for (Remessa r : pendentes) {
            try {
                processarRemessa(r.getId());
            } catch (Exception e) {
                log.error("Erro ao processar remessa pendente: id={}", r.getId(), e);
            }
        }
    }

    private RemessaResponse toResponse(Remessa r) {
        String swiftMasked = r.getCodigoSwift() != null && r.getCodigoSwift().length() > 4
            ? r.getCodigoSwift().substring(0, 4) + "****"
            : r.getCodigoSwift();
        return new RemessaResponse(
            r.getId(), r.getContratoId(), r.getClienteId(), r.getValor(),
            r.getMoeda(), r.getBancoDestino(), r.getContaDestino(), swiftMasked,
            r.getFinalidade(), r.getStatus(), r.getDataSolicitacao(), r.getDataConfirmacao()
        );
    }
}
