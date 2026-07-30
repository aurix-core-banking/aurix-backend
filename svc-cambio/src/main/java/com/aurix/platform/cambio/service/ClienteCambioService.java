package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.dto.AtualizarLimiteRequest;
import com.aurix.platform.cambio.dto.ClienteCambioRequest;
import com.aurix.platform.cambio.dto.ClienteCambioResponse;
import com.aurix.platform.cambio.dto.LimiteCambioResponse;
import com.aurix.platform.cambio.entity.ClienteCambio;
import com.aurix.platform.cambio.entity.Remessa;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
import com.aurix.platform.cambio.repository.RemessaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteCambioService {

    private static final Logger log = LoggerFactory.getLogger(ClienteCambioService.class);

    private final ClienteCambioRepository clienteCambioRepository;
    private final RemessaRepository remessaRepository;

    public ClienteCambioService(ClienteCambioRepository clienteCambioRepository, RemessaRepository remessaRepository) {
        this.clienteCambioRepository = clienteCambioRepository;
        this.remessaRepository = remessaRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteCambioResponse> listarClientes() {
        return clienteCambioRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ClienteCambioResponse habilitarCliente(ClienteCambioRequest request) {
        ClienteCambio entity = new ClienteCambio(
            request.getClienteId(), null, request.getLimiteRemessaMensal(),
            request.getLimiteRemessaAnual(), request.getCategoriasAutorizadas(), "DEFAULT"
        );
        entity = clienteCambioRepository.save(entity);
        log.info("Cliente cambio habilitado: id={}, clienteId={}", entity.getId(), entity.getClienteId());
        return toResponse(entity);
    }

    @Transactional
    public void ajustarLimites(Long id, AtualizarLimiteRequest request) {
        ClienteCambio entity = clienteCambioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente cambio nao encontrado: " + id));
        entity.setLimiteRemessaMensal(request.getLimiteRemessaMensal());
        entity.setLimiteRemessaAnual(request.getLimiteRemessaAnual());
        clienteCambioRepository.save(entity);
        log.info("Limites ajustados: id={}, mensal={}, anual={}",
            id, request.getLimiteRemessaMensal(), request.getLimiteRemessaAnual());
    }

    @Transactional(readOnly = true)
    public LimiteCambioResponse consultarLimites(Long clienteId) {
        ClienteCambio cliente = clienteCambioRepository.findByClienteId(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente cambio nao encontrado: " + clienteId));

        LocalDate now = LocalDate.now();
        BigDecimal totalMes = BigDecimal.ZERO;
        BigDecimal totalAno = BigDecimal.ZERO;

        List<Remessa> remessas = remessaRepository.findByClienteId(clienteId);
        for (Remessa r : remessas) {
            if (r.getDataSolicitacao() != null && r.getStatus() != null && !"CANCELADA".equals(r.getStatus())) {
                LocalDate data = r.getDataSolicitacao().toLocalDate();
                if (data.getYear() == now.getYear() && data.getMonth() == now.getMonth()) {
                    totalMes = totalMes.add(r.getValor());
                }
                if (data.getYear() == now.getYear()) {
                    totalAno = totalAno.add(r.getValor());
                }
            }
        }

        BigDecimal saldoMensal = cliente.getLimiteRemessaMensal().subtract(totalMes);
        BigDecimal saldoAnual = cliente.getLimiteRemessaAnual().subtract(totalAno);

        if (saldoMensal.compareTo(BigDecimal.ZERO) < 0) saldoMensal = BigDecimal.ZERO;
        if (saldoAnual.compareTo(BigDecimal.ZERO) < 0) saldoAnual = BigDecimal.ZERO;

        return new LimiteCambioResponse(
            clienteId, cliente.getLimiteRemessaMensal(), cliente.getLimiteRemessaAnual(),
            totalMes, totalAno, saldoMensal, saldoAnual
        );
    }

    private ClienteCambioResponse toResponse(ClienteCambio e) {
        return new ClienteCambioResponse(
            e.getId(), e.getClienteId(), e.getLimiteRemessaMensal(),
            e.getLimiteRemessaAnual(), e.getCategoriasAutorizadas(), e.getDocumentacao()
        );
    }
}
