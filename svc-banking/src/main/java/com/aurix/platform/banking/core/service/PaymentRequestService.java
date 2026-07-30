package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.PaymentRequestDTO;
import com.aurix.platform.banking.core.entity.PaymentRequest;
import com.aurix.platform.banking.core.repository.PaymentRequestRepository;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentRequestService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRequestService.class);
    private final PaymentRequestRepository repository;
    private final TransacaoService transacaoService;

    public PaymentRequestService(final PaymentRequestRepository repository,
                                  final TransacaoService transacaoService) {
        this.repository = repository;
        this.transacaoService = transacaoService;
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private static final int DEFAULT_EXPIRATION_HOURS = 24;

    public PaymentRequestDTO criar(final PaymentRequestDTO dto) {
        PaymentRequest entity = dto.toEntity();
        entity.setToken(generateToken());
        entity.setStatus(PaymentRequest.PaymentRequestStatus.PENDING);
        if (entity.getExpiresAt() == null) {
            entity.setExpiresAt(LocalDateTime.now().plusHours(DEFAULT_EXPIRATION_HOURS));
        }
        entity = repository.save(entity);
        log.info("Payment request created: id={}, token={}, amount={}", entity.getId(), entity.getToken(), entity.getAmount());
        return PaymentRequestDTO.fromEntity(entity);
    }

    public Optional<PaymentRequestDTO> buscarPorToken(final String token) {
        return repository.findByToken(token).map(PaymentRequestDTO::fromEntity);
    }

    public Optional<PaymentRequestDTO> buscarPorId(final Long id) {
        return repository.findById(id).map(PaymentRequestDTO::fromEntity);
    }

    public List<PaymentRequestDTO> listarPorRequerente(final Long requesterId) {
        return repository.findByRequesterIdOrderByDataCriacaoDesc(requesterId).stream()
            .map(PaymentRequestDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<PaymentRequestDTO> listarPorPagador(final Long payerId) {
        return repository.findByPayerIdOrderByDataCriacaoDesc(payerId).stream()
            .map(PaymentRequestDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public PaymentRequestDTO pagarPorToken(final String token, final Long payerId,
                                            final String payerAccountNumber,
                                            final Long payerContaId) {
        PaymentRequest entity = repository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Payment request not found: " + token));

        if (entity.getStatus() != PaymentRequest.PaymentRequestStatus.PENDING) {
            throw new IllegalStateException("Payment request is not PENDING: " + entity.getStatus());
        }
        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            entity.setStatus(PaymentRequest.PaymentRequestStatus.EXPIRED);
            repository.save(entity);
            throw new IllegalStateException("Payment request expired at " + entity.getExpiresAt());
        }

        TransacaoDTO txDto = new TransacaoDTO();
        txDto.setContaOrigemId(payerContaId);
        txDto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        txDto.setValor(entity.getAmount());
        txDto.setDescricao("Payment request: " + entity.getToken());

        TransacaoDTO created = transacaoService.criar(txDto);

        entity.setPayerId(payerId);
        entity.setPayerAccountNumber(payerAccountNumber);
        entity.setStatus(PaymentRequest.PaymentRequestStatus.PAID);
        entity.setPaidAt(LocalDateTime.now());
        entity.setTransactionId(created.getId());
        entity = repository.save(entity);

        log.info("Payment request paid: id={}, token={}, txId={}", entity.getId(), entity.getToken(), created.getId());
        return PaymentRequestDTO.fromEntity(entity);
    }

    public PaymentRequestDTO cancelar(final Long id, final Long requesterId) {
        PaymentRequest entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Payment request not found: " + id));
        if (!entity.getRequesterId().equals(requesterId)) {
            throw new IllegalArgumentException("Only the requester can cancel the payment request");
        }
        if (entity.getStatus() != PaymentRequest.PaymentRequestStatus.PENDING) {
            throw new IllegalStateException("Cannot cancel a payment request with status: " + entity.getStatus());
        }
        entity.setStatus(PaymentRequest.PaymentRequestStatus.CANCELLED);
        entity = repository.save(entity);
        log.info("Payment request cancelled: id={}, token={}", entity.getId(), entity.getToken());
        return PaymentRequestDTO.fromEntity(entity);
    }

    public int expirarVencidas() {
        List<PaymentRequest> expired = repository.findByStatusAndExpiresAtBefore(
            PaymentRequest.PaymentRequestStatus.PENDING, LocalDateTime.now());
        for (PaymentRequest entity : expired) {
            entity.setStatus(PaymentRequest.PaymentRequestStatus.EXPIRED);
            repository.save(entity);
        }
        if (!expired.isEmpty()) {
            log.info("Expired {} payment requests", expired.size());
        }
        return expired.size();
    }
}
