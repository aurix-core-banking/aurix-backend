package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.client.CartorioRgiClient;
import com.aurix.platform.credit.financiamento.client.DetranClient;
import com.aurix.platform.credit.financiamento.dto.request.GarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.request.LiberarGarantiaRequest;
import com.aurix.platform.credit.financiamento.dto.response.GarantiaResponse;
import com.aurix.platform.credit.financiamento.entity.BemFinanciado;
import com.aurix.platform.credit.financiamento.entity.Garantia;
import com.aurix.platform.credit.financiamento.entity.StatusGarantia;
import com.aurix.platform.credit.financiamento.entity.TipoGarantia;
import com.aurix.platform.credit.financiamento.repository.BemFinanciadoRepository;
import com.aurix.platform.credit.financiamento.repository.GarantiaRepository;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GarantiaService {

    private static final Logger log = LoggerFactory.getLogger(GarantiaService.class);

    private final GarantiaRepository garantiaRepository;
    private final BemFinanciadoRepository bemRepository;
    private final CartorioRgiClient cartorioRgiClient;
    private final DetranClient detranClient;

    public GarantiaService(GarantiaRepository garantiaRepository,
                           BemFinanciadoRepository bemRepository,
                           CartorioRgiClient cartorioRgiClient,
                           DetranClient detranClient) {
        this.garantiaRepository = garantiaRepository;
        this.bemRepository = bemRepository;
        this.cartorioRgiClient = cartorioRgiClient;
        this.detranClient = detranClient;
    }

    @Transactional
    public GarantiaResponse registrar(GarantiaRequest request, Long contratoId, Long bemId) {
        var tipoGarantia = TipoGarantia.valueOf(request.getTipo());
        var dataRegistro = LocalDate.now();
        String protocoloRegistro = null;

        var bem = bemRepository.findById(bemId).orElse(null);

        if ("RGI".equals(request.getOrgaoRegistro()) || "CARTORIO".equals(request.getOrgaoRegistro())) {
            CartorioRgiClient.RegistroResponse response;
            try {
                response = cartorioRgiClient.registrarGarantia(
                    new CartorioRgiClient.RegistroGarantiaRequest(
                        request.getTipo(), request.getValor(), request.getOrgaoRegistro()));
            } catch (Exception e) {
                log.error("Falha ao registrar garantia no cartório: {}", e.getMessage());
                throw new IllegalStateException("Não foi possível registrar a garantia no cartório: " + e.getMessage(), e);
            }
            if (response == null
                    || (response.protocolo() == null && response.matricula() == null)) {
                throw new IllegalStateException("Cartório não confirmou o registro da garantia");
            }
            if (response.dataRegistro() != null) {
                dataRegistro = response.dataRegistro();
            }
            protocoloRegistro = response.protocolo() != null
                ? response.protocolo() : response.matricula();
        } else if ("DETRAN".equals(request.getOrgaoRegistro()) && bem != null) {
            DetranClient.DetranResponse response;
            try {
                response = detranClient.registrarGarantia(
                    new DetranClient.DetranGarantiaRequest(
                        bem.getPlaca(), bem.getChassi(), request.getValor(), "Aurix"));
            } catch (Exception e) {
                log.error("Falha ao registrar garantia no Detran: {}", e.getMessage());
                throw new IllegalStateException("Não foi possível registrar a garantia no Detran: " + e.getMessage(), e);
            }
            if (response == null
                    || (response.protocolo() == null && response.registro() == null)) {
                throw new IllegalStateException("Detran não confirmou o registro da garantia");
            }
            protocoloRegistro = response.protocolo() != null
                ? response.protocolo() : response.registro();
        }

        var garantia = new Garantia(contratoId, bemId, tipoGarantia, request.getValor(),
            dataRegistro, null, StatusGarantia.ATIVA, request.getOrgaoRegistro());
        garantia.setProtocoloRegistro(protocoloRegistro);
        garantia = garantiaRepository.save(garantia);

        log.info("Garantia registrada: id={}, contratoId={}, protocolo={}",
            garantia.getId(), contratoId, protocoloRegistro);
        return toResponse(garantia);
    }

    @Transactional
    public GarantiaResponse liberar(Long id, LiberarGarantiaRequest request) {
        var garantia = garantiaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Garantia não encontrada: " + id));

        // Baixa externa no órgão registrador antes de liberar a garantia.
        if ("RGI".equals(garantia.getOrgaoRegistro()) || "CARTORIO".equals(garantia.getOrgaoRegistro())) {
            try {
                cartorioRgiClient.consultar(garantia.getProtocoloRegistro() != null
                    ? garantia.getProtocoloRegistro() : garantia.getId().toString());
                log.info("Baixa externa no cartório confirmada para garantia {}", id);
            } catch (Exception e) {
                log.error("Falha na baixa externa no cartório da garantia {}: {}", id, e.getMessage());
                throw new IllegalStateException("Não foi possível dar baixa no cartório: " + e.getMessage(), e);
            }
        } else if ("DETRAN".equals(garantia.getOrgaoRegistro())) {
            try {
                detranClient.consultarVeiculo(garantia.getProtocoloRegistro() != null
                    ? garantia.getProtocoloRegistro() : garantia.getId().toString());
                log.info("Baixa externa no Detran confirmada para garantia {}", id);
            } catch (Exception e) {
                log.error("Falha na baixa externa no Detran da garantia {}: {}", id, e.getMessage());
                throw new IllegalStateException("Não foi possível dar baixa no Detran: " + e.getMessage(), e);
            }
        }

        garantia.setStatus(StatusGarantia.LIBERADA);
        garantia.setDataBaixa(request.getDataBaixa() != null ? request.getDataBaixa() : LocalDate.now());
        garantia = garantiaRepository.save(garantia);
        log.info("Garantia liberada: id={}", garantia.getId());
        return toResponse(garantia);
    }

    @Transactional(readOnly = true)
    public List<GarantiaResponse> listarPorContrato(Long contratoId) {
        return garantiaRepository.findByContratoId(contratoId).stream()
            .map(this::toResponse).toList();
    }

    private GarantiaResponse toResponse(Garantia g) {
        return new GarantiaResponse(g.getId(), g.getTipo().name(), g.getValor(),
            g.getDataRegistro(), g.getDataBaixa(), g.getStatus().name(), g.getOrgaoRegistro(),
            g.getProtocoloRegistro());
    }
}
