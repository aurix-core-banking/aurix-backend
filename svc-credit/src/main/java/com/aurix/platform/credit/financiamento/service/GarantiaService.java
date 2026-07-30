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

        var bem = bemRepository.findById(bemId).orElse(null);

        if ("RGI".equals(request.getOrgaoRegistro()) || "CARTORIO".equals(request.getOrgaoRegistro())) {
            try {
                var response = cartorioRgiClient.registrarGarantia(
                    new CartorioRgiClient.RegistroGarantiaRequest(request.getTipo(), request.getValor(), request.getOrgaoRegistro()));
                dataRegistro = response.dataRegistro();
            } catch (Exception e) {
                log.warn("Erro ao registrar garantia no cartório: {}", e.getMessage());
            }
        } else if ("DETRAN".equals(request.getOrgaoRegistro()) && bem != null) {
            try {
                detranClient.registrarGarantia(
                    new DetranClient.DetranGarantiaRequest(bem.getPlaca(), bem.getChassi(), request.getValor(), "Aurix"));
            } catch (Exception e) {
                log.warn("Erro ao registrar garantia no Detran: {}", e.getMessage());
            }
        }

        var garantia = new Garantia(contratoId, bemId, tipoGarantia, request.getValor(),
            dataRegistro, null, StatusGarantia.ATIVA, request.getOrgaoRegistro());
        garantia = garantiaRepository.save(garantia);

        log.info("Garantia registrada: id={}, contratoId={}", garantia.getId(), contratoId);
        return toResponse(garantia);
    }

    @Transactional
    public GarantiaResponse liberar(Long id, LiberarGarantiaRequest request) {
        var garantia = garantiaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Garantia não encontrada: " + id));
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
            g.getDataRegistro(), g.getDataBaixa(), g.getStatus().name(), g.getOrgaoRegistro());
    }
}
