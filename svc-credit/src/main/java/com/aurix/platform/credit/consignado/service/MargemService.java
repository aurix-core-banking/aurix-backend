package com.aurix.platform.credit.consignado.service;

import com.aurix.platform.credit.consignado.dto.MargemResponse;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
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

    private final MargemConsignavelRepository repository;

    public MargemService(MargemConsignavelRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public MargemResponse consultarMargem(Long clienteId) {
        List<MargemConsignavel> margens = repository.findByClienteId(clienteId);
        if (margens.isEmpty()) {
            throw new IllegalArgumentException("Margem não encontrada para cliente: " + clienteId);
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

    @Transactional(readOnly = true)
    public void validarMargemDisponivel(Long clienteId, BigDecimal valorPretendido) {
        var margens = repository.findByClienteId(clienteId);
        BigDecimal disponivel = margens.stream()
            .map(MargemConsignavel::getMargemDisponivel)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (disponivel.compareTo(valorPretendido) < 0) {
            throw new IllegalStateException(
                "Margem insuficiente: disponivel=" + disponivel + ", pretendido=" + valorPretendido);
        }
    }
}
