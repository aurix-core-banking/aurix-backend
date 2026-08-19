package com.aurix.platform.credit.seguro.service;

import com.aurix.platform.credit.financiamento.entity.ContratoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.StatusContrato;
import com.aurix.platform.credit.financiamento.repository.ContratoFinanciamentoRepository;
import com.aurix.platform.credit.seguro.dto.request.AbrirSinistroRequest;
import com.aurix.platform.credit.seguro.dto.request.ContratarSeguroRequest;
import com.aurix.platform.credit.seguro.dto.response.CoberturaResponse;
import com.aurix.platform.credit.seguro.dto.response.SeguroPrestamistaResponse;
import com.aurix.platform.credit.seguro.dto.response.SinistroResponse;
import com.aurix.platform.credit.seguro.entity.SeguroPrestamista;
import com.aurix.platform.credit.seguro.entity.Sinistro;
import com.aurix.platform.credit.seguro.entity.StatusSeguro;
import com.aurix.platform.credit.seguro.entity.StatusSinistro;
import com.aurix.platform.credit.seguro.entity.TipoCobertura;
import com.aurix.platform.credit.seguro.entity.TipoSinistro;
import com.aurix.platform.credit.seguro.repository.SeguroPrestamistaRepository;
import com.aurix.platform.credit.seguro.repository.SinistroRepository;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroPrestamistaService {

    private static final Logger log = LoggerFactory.getLogger(SeguroPrestamistaService.class);
    private static final int DIAS_CARENCIA_PADRAO = 30;
    private static final int DIAS_PRAZO_SINISTRO = 30;
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_EVEN);
    private static final int SCALE = 2;

    private final SeguroPrestamistaRepository seguroRepository;
    private final SinistroRepository sinistroRepository;
    private final ContratoFinanciamentoRepository contratoRepository;
    private final BigDecimal taxaMensalPadrao;

    public SeguroPrestamistaService(SeguroPrestamistaRepository seguroRepository,
                                    SinistroRepository sinistroRepository,
                                    ContratoFinanciamentoRepository contratoRepository,
                                    @Value("${aurix.seguro.taxa-mensal:0.005}") BigDecimal taxaMensalPadrao) {
        this.seguroRepository = seguroRepository;
        this.sinistroRepository = sinistroRepository;
        this.contratoRepository = contratoRepository;
        this.taxaMensalPadrao = taxaMensalPadrao;
    }

    @Transactional
    public SeguroPrestamistaResponse contratar(ContratarSeguroRequest request) {
        var contrato = contratoRepository.findById(request.getContratoId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Contrato não encontrado: " + request.getContratoId()));

        if (contrato.getStatus() != StatusContrato.ATIVO) {
            throw new IllegalStateException(
                "Contrato precisa estar ATIVO para contratar seguro. Status: " + contrato.getStatus());
        }

        var seguroExistente = seguroRepository.findByContratoIdAndStatus(
            request.getContratoId(), StatusSeguro.ATIVO);
        if (seguroExistente.isPresent()) {
            throw new IllegalStateException(
                "Já existe seguro ativo para o contrato: " + request.getContratoId());
        }

        var saldoDevedor = contrato.getSaldoDevedor();
        var valorPremio = saldoDevedor.multiply(taxaMensalPadrao, MC)
            .setScale(SCALE, RoundingMode.HALF_EVEN);

        var coberturas = request.getCoberturas() != null && request.getCoberturas().length > 0
            ? request.getCoberturas()
            : new String[]{TipoCobertura.MORTE.name(), TipoCobertura.INVALIDEZ_PERMANENTE.name(),
                           TipoCobertura.DESEMPREGO.name()};

        var seguro = new SeguroPrestamista(
            contrato.getTenantId(),
            request.getContratoId(),
            contrato.getClienteId(),
            saldoDevedor,
            taxaMensalPadrao,
            valorPremio,
            DIAS_CARENCIA_PADRAO,
            LocalDate.now(),
            StatusSeguro.ATIVO
        );
        seguro = seguroRepository.save(seguro);

        log.info("Seguro prestamista contratado: id={}, contratoId={}, premioMensal={}",
            seguro.getId(), request.getContratoId(), valorPremio);
        return toResponse(seguro, coberturas);
    }

    @Transactional(readOnly = true)
    public SeguroPrestamistaResponse buscarPorId(Long id) {
        var seguro = seguroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado: " + id));
        return toResponse(seguro, null);
    }

    @Transactional(readOnly = true)
    public List<SeguroPrestamistaResponse> listarPorContrato(Long contratoId) {
        return seguroRepository.findByContratoId(contratoId).stream()
            .map(s -> toResponse(s, null))
            .toList();
    }

    @Transactional
    public SeguroPrestamistaResponse cancelar(Long id, String motivo) {
        var seguro = seguroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado: " + id));

        if (seguro.getStatus() != StatusSeguro.ATIVO && seguro.getStatus() != StatusSeguro.SUSPENSO) {
            throw new IllegalStateException(
                "Seguro em status " + seguro.getStatus() + " não pode ser cancelado.");
        }

        seguro.setStatus(StatusSeguro.CANCELADO);
        seguro.setDataCancelamento(LocalDate.now());
        seguro.setMotivoCancelamento(motivo);
        seguro = seguroRepository.save(seguro);

        log.info("Seguro prestamista cancelado: id={}, motivo={}", id, motivo);
        return toResponse(seguro, null);
    }

    @Transactional(readOnly = true)
    public CoberturaResponse verificarCobertura(Long contratoId) {
        var seguroOpt = seguroRepository.findByContratoIdAndStatus(contratoId, StatusSeguro.ATIVO);
        if (seguroOpt.isEmpty()) {
            return new CoberturaResponse(null, contratoId, "INATIVO",
                List.of(), null, null, 0, false, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        var seguro = seguroOpt.get();
        var contrato = contratoRepository.findById(contratoId)
            .orElse(null);
        var saldoAtual = contrato != null ? contrato.getSaldoDevedor() : seguro.getSaldoDevedorInicial();

        var diasDesdeInicio = ChronoUnit.DAYS.between(seguro.getDataInicio(), LocalDate.now());
        var dentroCarencia = diasDesdeInicio < seguro.getCarenciaDias();

        var coberturas = Arrays.asList(
            TipoCobertura.MORTE.name(),
            TipoCobertura.INVALIDEZ_PERMANENTE.name(),
            TipoCobertura.DESEMPREGO.name()
        );

        return new CoberturaResponse(
            seguro.getId(),
            contratoId,
            seguro.getStatus().name(),
            coberturas,
            seguro.getDataInicio(),
            seguro.getDataFim(),
            seguro.getCarenciaDias(),
            dentroCarencia,
            saldoAtual,
            seguro.getTaxaMensal()
        );
    }

    @Transactional
    public SinistroResponse abrirSinistro(Long seguroId, AbrirSinistroRequest request) {
        var seguro = seguroRepository.findById(seguroId)
            .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado: " + seguroId));

        if (seguro.getStatus() != StatusSeguro.ATIVO) {
            throw new IllegalStateException(
                "Seguro precisa estar ATIVO para abrir sinistro. Status: " + seguro.getStatus());
        }

        var diasDesdeOcorrencia = ChronoUnit.DAYS.between(request.getDataOcorrencia(), LocalDate.now());
        if (diasDesdeOcorrencia > DIAS_PRAZO_SINISTRO) {
            throw new IllegalStateException(
                "Prazo para abertura de sinistro expirado. Máximo: " + DIAS_PRAZO_SINISTRO
                + " dias. Dias desde ocorrência: " + diasDesdeOcorrencia);
        }

        var diasDesdeInicio = ChronoUnit.DAYS.between(seguro.getDataInicio(), LocalDate.now());
        if (diasDesdeInicio < seguro.getCarenciaDias()) {
            throw new IllegalStateException(
                "Seguro ainda em período de carência. Dias restantes: "
                + (seguro.getCarenciaDias() - diasDesdeInicio));
        }

        var sinistro = new Sinistro(
            seguroId,
            TipoSinistro.valueOf(request.getTipoSinistro()),
            request.getDescricao(),
            request.getDataOcorrencia(),
            StatusSinistro.ABERTO
        );
        sinistro.setDocumentos(request.getDocumentos());
        sinistro = sinistroRepository.save(sinistro);

        log.info("Sinistro aberto: id={}, seguroId={}, tipo={}", sinistro.getId(), seguroId,
            request.getTipoSinistro());
        return toSinistroResponse(sinistro);
    }

    @Transactional(readOnly = true)
    public List<SinistroResponse> listarSinistros(Long seguroId) {
        return sinistroRepository.findBySeguroId(seguroId).stream()
            .map(this::toSinistroResponse)
            .toList();
    }

    private SeguroPrestamistaResponse toResponse(SeguroPrestamista s, String[] coberturas) {
        var coberturasList = coberturas != null
            ? Arrays.asList(coberturas)
            : List.of(TipoCobertura.MORTE.name(), TipoCobertura.INVALIDEZ_PERMANENTE.name(),
                      TipoCobertura.DESEMPREGO.name());
        return new SeguroPrestamistaResponse(
            s.getId(), s.getContratoId(), s.getClienteId(),
            s.getSaldoDevedorInicial(), s.getTaxaMensal(), s.getValorPremioMensal(),
            s.getCarenciaDias(), coberturasList,
            s.getDataInicio(), s.getDataFim(), s.getDataCancelamento(),
            s.getMotivoCancelamento(), s.getStatus().name(), s.getDataCriacao());
    }

    private SinistroResponse toSinistroResponse(Sinistro s) {
        return new SinistroResponse(
            s.getId(), s.getSeguroId(), s.getTipoSinistro().name(),
            s.getDescricao(), s.getDataOcorrencia(), s.getDataAbertura(),
            s.getDataAnalise(), s.getDataResolucao(), s.getValorIndenizacao(),
            s.getStatus().name(), s.getDocumentos(), s.getDataCriacao());
    }
}
