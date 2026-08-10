package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.dto.AssinanteRequest;
import com.aurix.platform.contracts.dto.AssinaturaRequest;
import com.aurix.platform.contracts.dto.AssinaturaResponse;
import com.aurix.platform.contracts.entity.AssinaturaContrato;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.repository.AssinaturaContratoRepository;
import com.aurix.platform.contracts.repository.ContratoRepository;
import com.aurix.platform.shared.event.ContratoAssinadoEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.Topics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssinaturaService {

    private final AssinaturaContratoRepository assinaturaRepository;
    private final ContratoRepository contratoRepository;
    private final ContratoService contratoService;
    private final EventPublisher eventPublisher;

    public AssinaturaService(AssinaturaContratoRepository assinaturaRepository,
                             ContratoRepository contratoRepository,
                             ContratoService contratoService,
                             EventPublisher eventPublisher) {
        this.assinaturaRepository = assinaturaRepository;
        this.contratoRepository = contratoRepository;
        this.contratoService = contratoService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public List<AssinaturaContrato> iniciarFluxo(Long contratoId, List<AssinanteRequest> assinantes) {
        Contrato contrato = contratoService.buscarEntidade(contratoId);
        if (contrato.getStatus() == Contrato.StatusContrato.LIQUIDADO
            || contrato.getStatus() == Contrato.StatusContrato.CANCELADO) {
            throw new IllegalStateException("Contrato " + contrato.getStatus().name().toLowerCase() + " não aceita assinatura");
        }
        assinaturaRepository.deleteByContratoId(contratoId);
        for (AssinanteRequest assinante : assinantes) {
            AssinaturaContrato assinatura = new AssinaturaContrato();
            assinatura.setContratoId(contratoId);
            assinatura.setAssinanteTipo(assinante.assinanteTipo());
            assinatura.setAssinanteDocumento(assinante.assinanteDocumento());
            assinatura.setAssinanteNome(assinante.assinanteNome());
            assinatura.setAssinada(false);
            assinatura.setValida(false);
            assinaturaRepository.save(assinatura);
        }
        contrato.setStatus(Contrato.StatusContrato.AGUARDANDO_ASSINATURA);
        contratoRepository.save(contrato);
        return assinaturaRepository.findByContratoId(contratoId);
    }

    @Transactional
    public AssinaturaContrato registrarAssinatura(Long contratoId, String assinanteDocumento, AssinaturaRequest request) {
        Contrato contrato = contratoService.buscarEntidade(contratoId);
        if (contrato.getStatus() != Contrato.StatusContrato.AGUARDANDO_ASSINATURA
            && contrato.getStatus() != Contrato.StatusContrato.RASCUNHO) {
            throw new IllegalStateException("Contrato em status " + contrato.getStatus().name() + " não permite assinatura");
        }
        AssinaturaContrato assinatura = assinaturaRepository
            .findByContratoIdAndAssinanteDocumento(contratoId, assinanteDocumento)
            .orElseThrow(() -> new IllegalArgumentException(
                "Assinante não cadastrado para este contrato: " + assinanteDocumento));

        assinatura.setHashDocumento(request.hashDocumento());
        assinatura.setIp(request.ip());
        assinatura.setUserAgent(request.userAgent());
        assinatura.setDataAssinatura(LocalDateTime.now());
        assinatura.setAssinada(true);
        assinatura.setValida(true);
        assinatura = assinaturaRepository.save(assinatura);

        eventPublisher.publish(Topics.CONTRATO_ASSINADO, ContratoAssinadoEvent.assinado(
            contratoId, contrato.getNumeroContrato(), assinanteDocumento, assinatura.getDataAssinatura()));

        verificarConclusao(contrato);
        return assinatura;
    }

    @Transactional(readOnly = true)
    public List<AssinaturaResponse> listarAssinaturas(Long contratoId) {
        contratoService.buscarEntidade(contratoId);
        return assinaturaRepository.findByContratoId(contratoId).stream()
            .map(AssinaturaResponse::de)
            .toList();
    }

    private void verificarConclusao(Contrato contrato) {
        List<AssinaturaContrato> assinaturas = assinaturaRepository.findByContratoId(contrato.getId());
        boolean todasAssinadas = !assinaturas.isEmpty() && assinaturas.stream()
            .allMatch(a -> Boolean.TRUE.equals(a.getAssinada()) && Boolean.TRUE.equals(a.getValida()));
        if (todasAssinadas) {
            contrato.setStatus(Contrato.StatusContrato.ATIVO);
            contrato.setDataAssinatura(LocalDateTime.now());
            contratoRepository.save(contrato);
        }
    }
}
