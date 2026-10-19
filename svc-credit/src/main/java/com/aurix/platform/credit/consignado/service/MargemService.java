package com.aurix.platform.credit.consignado.service;

import com.aurix.platform.credit.consignado.dto.MargemResponse;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import com.aurix.platform.credit.consignado.provider.MargemProvider;
import com.aurix.platform.credit.consignado.repository.MargemConsignavelRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MargemService {

    private static final Logger log = LoggerFactory.getLogger(MargemService.class);

    private static final List<String> FONTES_PADRAO =
        List.of(MargemProvider.FONTE_INSS, MargemProvider.FONTE_SIAFI, MargemProvider.FONTE_EMPRESA);

    private final MargemConsignavelRepository repository;
    private final MargemProvider margemProvider;

    public MargemService(MargemConsignavelRepository repository,
                         MargemProvider margemProvider) {
        this.repository = repository;
        this.margemProvider = margemProvider;
    }

    @Transactional
    public MargemResponse consultarMargem(Long clienteId) {
        List<MargemConsignavel> margens = repository.findByClienteId(clienteId);
        if (margens.isEmpty()) {
            margens = consultarMargemExterna(clienteId);
        }
        BigDecimal margemTotal = margens.stream()
            .map(MargemConsignavel::getMargemTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal margemDisponivel = margens.stream()
            .map(MargemConsignavel::getMargemDisponivel)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal margemUtilizada = margens.stream()
            .map(MargemConsignavel::getMargemUtilizada)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        LocalDateTime dataAtualizacao = margens.stream()
            .map(MargemConsignavel::getDataAtualizacao)
            .max(LocalDateTime::compareTo)
            .orElse(null);
        return new MargemResponse(clienteId, null, margemTotal, margemDisponivel, margemUtilizada, dataAtualizacao);
    }

    private List<MargemConsignavel> consultarMargemExterna(Long clienteId) {
        for (String fonte : FONTES_PADRAO) {
            try {
                MargemConsignavel margem = margemProvider.consultarMargem(clienteId, null, fonte);
                if (margem != null) {
                    repository.save(margem);
                    log.info("Margem externa obtida: clienteId={}, fonte={}", clienteId, fonte);
                    return List.of(margem);
                }
            } catch (Exception e) {
                log.warn("Falha ao consultar margem externa: fonte={}, clienteId={}: {}",
                    fonte, clienteId, e.getMessage());
            }
        }
        throw new IllegalArgumentException("Margem não encontrada para cliente: " + clienteId);
    }

    @Transactional
    public void atualizarMargem(Long clienteId, String fonte, BigDecimal valorUtilizado) {
        var margem = repository.findByClienteIdAndFonteMargem(clienteId, fonte)
            .orElseThrow(() -> new IllegalArgumentException(
                "Margem não encontrada: cliente=" + clienteId + ", fonte=" + fonte));
        BigDecimal novaUtilizada = margem.getMargemUtilizada().add(valorUtilizado);
        BigDecimal novaDisponivel = margem.getMargemTotal().subtract(novaUtilizada);
        margem.setMargemUtilizada(novaUtilizada);
        margem.setMargemDisponivel(novaDisponivel);
        margem.setDataAtualizacao(LocalDateTime.now());
        repository.save(margem);
        log.info("Margem atualizada: clienteId={}, fonte={}, utilizada={}, disponivel={}",
            clienteId, fonte, novaUtilizada, novaDisponivel);
    }

    @Transactional
    public void validarMargemDisponivel(Long clienteId, BigDecimal valorPretendido) {
        var margens = repository.findByClienteId(clienteId);
        if (margens.isEmpty()) {
            margens = consultarMargemExterna(clienteId);
        }
        BigDecimal disponivel = margens.stream()
            .map(MargemConsignavel::getMargemDisponivel)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (disponivel.compareTo(valorPretendido) < 0) {
            throw new IllegalStateException(
                "Margem insuficiente: disponivel=" + disponivel + ", pretendido=" + valorPretendido);
        }
    }
}
