package com.aurix.platform.transactions.service;

import com.aurix.platform.transactions.dto.TransacaoRequest;
import com.aurix.platform.transactions.dto.TransacaoResponse;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransacaoService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
    private static final int UUID_SUBSTRING_END = 8;

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final EventPublisher eventPublisher;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            ContaRepository contaRepository,
                            EventPublisher eventPublisher) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.eventPublisher = eventPublisher;
    }

    public TransacaoResponse criar(TransacaoRequest request) {
        log.info("Criando transacao: valor={}", request.getValor());
        String tenantId = TenantContext.getTenantId();

        Conta contaOrigem = null;
        if (request.getContaOrigemId() != null) {
            contaOrigem = contaRepository.findByTenantIdAndId(tenantId, request.getContaOrigemId())
                .orElseThrow(() -> new RuntimeException("Conta origem nao encontrada"));
        }
        Conta contaDestino = null;
        if (request.getContaDestinoId() != null) {
            contaDestino = contaRepository.findByTenantIdAndId(tenantId, request.getContaDestinoId())
                .orElseThrow(() -> new RuntimeException("Conta destino nao encontrada"));
        }

        Transacao t = new Transacao();
        t.setTenantId(tenantId);
        t.setContaOrigem(contaOrigem);
        t.setContaDestino(contaDestino);
        t.setTipoTransacao(request.getTipoTransacao() != null
            ? Transacao.TipoTransacao.valueOf(request.getTipoTransacao())
            : Transacao.TipoTransacao.TRANSFERENCIA_INTERNA);
        t.setValor(request.getValor());
        t.setDescricao(request.getDescricao());
        t.setStatus(Transacao.StatusTransacao.PENDENTE);
        t.setCodigoTransacao("TXN-" + UUID.randomUUID().toString()
            .substring(0, UUID_SUBSTRING_END).toUpperCase(java.util.Locale.ROOT));
        t.setDataTransacao(LocalDateTime.now());
        t.setDadosPix(request.getDadosPix());
        t.setDadosTed(request.getDadosTed());

        t = transacaoRepository.save(t);

        try {
            String contaId = t.getContaOrigem() != null ? String.valueOf(t.getContaOrigem().getId()) : null;
            boolean hasCliente = t.getContaOrigem() != null && t.getContaOrigem().getCliente() != null;
            String clienteId = hasCliente ? String.valueOf(t.getContaOrigem().getCliente().getId()) : null;
            String tipo = t.getTipoTransacao() != null ? t.getTipoTransacao().name() : "TRANSFERENCIA_INTERNA";
            eventPublisher.publicarTransacaoRealizada(TransacaoEvent.transacaoRealizada(
                String.valueOf(t.getId()), contaId, clienteId, t.getValor(), tipo, t.getDescricao()));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento transacao-realizada: {}", e.getMessage());
        }

        return toResponse(t);
    }

    @Transactional(readOnly = true)
    public TransacaoResponse buscarPorId(Long id) {
        String tenantId = TenantContext.getTenantId();
        return transacaoRepository.findByTenantIdAndId(tenantId, id)
            .map(this::toResponse)
            .orElseThrow(() -> new RuntimeException("Transacao nao encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public TransacaoResponse buscarPorCodigo(String codigoTransacao) {
        String tenantId = TenantContext.getTenantId();
        return transacaoRepository.findByTenantIdAndCodigoTransacao(tenantId, codigoTransacao)
            .map(this::toResponse)
            .orElseThrow(() -> new RuntimeException("Transacao nao encontrada: " + codigoTransacao));
    }

    @Transactional(readOnly = true)
    public List<TransacaoResponse> listarPorConta(Long contaId) {
        String tenantId = TenantContext.getTenantId();
        return transacaoRepository.findByTenantIdAndContaIdOrderByDataTransacaoDesc(
                tenantId, contaId, Pageable.unpaged()).getContent().stream()
            .map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<TransacaoResponse> listarPorContaPaginado(Long contaId, Pageable pageable) {
        String tenantId = TenantContext.getTenantId();
        return transacaoRepository.findByTenantIdAndContaIdOrderByDataTransacaoDesc(
            tenantId, contaId, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TransacaoResponse> listarPendentes(Pageable pageable) {
        String tenantId = TenantContext.getTenantId();
        return transacaoRepository.findTransacoesPendentesByTenantId(tenantId, pageable)
            .map(this::toResponse);
    }

    public TransacaoResponse cancelar(Long id) {
        log.info("Cancelando transacao ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Transacao t = transacaoRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new RuntimeException("Transacao nao encontrada: " + id));

        if (t.getStatus() == Transacao.StatusTransacao.CONCLUIDA) {
            throw new IllegalStateException("Nao e possivel cancelar transacao ja concluida");
        }

        t.setStatus(Transacao.StatusTransacao.CANCELADA);
        t = transacaoRepository.save(t);
        log.info("Transacao {} cancelada", t.getCodigoTransacao());
        return toResponse(t);
    }

    public TransacaoResponse processar(Long id) {
        log.info("Processando transacao ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Transacao t = transacaoRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new RuntimeException("Transacao nao encontrada: " + id));

        if (t.getStatus() != Transacao.StatusTransacao.PENDENTE) {
            throw new IllegalStateException("Apenas transacoes pendentes podem ser processadas");
        }

        t.setStatus(Transacao.StatusTransacao.CONCLUIDA);
        t.setDataProcessamento(LocalDateTime.now());
        t = transacaoRepository.save(t);
        log.info("Transacao {} processada", t.getCodigoTransacao());
        return toResponse(t);
    }

    private TransacaoResponse toResponse(Transacao t) {
        TransacaoResponse resp = new TransacaoResponse();
        resp.setId(t.getId());
        resp.setCodigoTransacao(t.getCodigoTransacao());
        resp.setContaOrigemId(t.getContaOrigem() != null ? t.getContaOrigem().getId() : null);
        resp.setContaOrigemNumero(t.getContaOrigem() != null ? t.getContaOrigem().getNumeroConta() : null);
        resp.setContaDestinoId(t.getContaDestino() != null ? t.getContaDestino().getId() : null);
        resp.setContaDestinoNumero(t.getContaDestino() != null ? t.getContaDestino().getNumeroConta() : null);
        resp.setTipoTransacao(t.getTipoTransacao() != null ? t.getTipoTransacao().name() : null);
        resp.setValor(t.getValor());
        resp.setDescricao(t.getDescricao());
        resp.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        resp.setDataTransacao(t.getDataTransacao());
        resp.setDataProcessamento(t.getDataProcessamento());
        return resp;
    }
}
