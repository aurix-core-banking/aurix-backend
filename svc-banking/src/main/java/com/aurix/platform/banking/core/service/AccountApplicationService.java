package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.AccountApplicationDTO;
import com.aurix.platform.banking.core.entity.AccountApplication;
import com.aurix.platform.banking.core.repository.AccountApplicationRepository;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.entity.Conta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountApplicationService {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AccountApplicationService.class);
    private final AccountApplicationRepository repository;
    private final ContaService contaService;

    public AccountApplicationService(final AccountApplicationRepository repository,
                                      final ContaService contaService) {
        this.repository = repository;
        this.contaService = contaService;
    }

    public AccountApplicationDTO criar(final AccountApplicationDTO dto) {
        AccountApplication entity = dto.toEntity();
        entity.setStatus(AccountApplication.AccountApplicationStatus.DRAFT);
        entity = repository.save(entity);
        log.info("Account application created: id={}, clienteId={}", entity.getId(), entity.getClienteId());
        return AccountApplicationDTO.fromEntity(entity);
    }

    public Optional<AccountApplicationDTO> buscarPorId(final Long id) {
        return repository.findById(id).map(AccountApplicationDTO::fromEntity);
    }

    public List<AccountApplicationDTO> listarPorCliente(final Long clienteId) {
        return repository.findByClienteIdOrderByDataCriacaoDesc(clienteId).stream()
            .map(AccountApplicationDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<AccountApplicationDTO> listarPorStatus(final AccountApplication.AccountApplicationStatus status) {
        return repository.findByStatusOrderByDataCriacaoAsc(status).stream()
            .map(AccountApplicationDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public AccountApplicationDTO submit(final Long id) {
        AccountApplication entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account application not found: " + id));
        if (entity.getStatus() != AccountApplication.AccountApplicationStatus.DRAFT
            && entity.getStatus() != AccountApplication.AccountApplicationStatus.PENDING_DOCUMENTS) {
            throw new IllegalStateException("Cannot submit application with status: " + entity.getStatus());
        }
        entity.setStatus(AccountApplication.AccountApplicationStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());
        entity = repository.save(entity);
        log.info("Account application submitted: id={}", id);
        return AccountApplicationDTO.fromEntity(entity);
    }

    public AccountApplicationDTO requestDocuments(final Long id, final String notes) {
        AccountApplication entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account application not found: " + id));
        if (entity.getStatus() != AccountApplication.AccountApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Can only request documents on SUBMITTED applications");
        }
        entity.setStatus(AccountApplication.AccountApplicationStatus.PENDING_DOCUMENTS);
        entity.setReviewNotes(notes);
        entity = repository.save(entity);
        log.info("Documents requested for application: id={}", id);
        return AccountApplicationDTO.fromEntity(entity);
    }

    public AccountApplicationDTO startReview(final Long id, final Long reviewerId) {
        AccountApplication entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account application not found: " + id));
        if (entity.getStatus() != AccountApplication.AccountApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Can only review SUBMITTED applications");
        }
        entity.setStatus(AccountApplication.AccountApplicationStatus.UNDER_REVIEW);
        entity.setReviewerId(reviewerId);
        entity = repository.save(entity);
        log.info("Review started for application: id={}, reviewerId={}", id, reviewerId);
        return AccountApplicationDTO.fromEntity(entity);
    }

    public AccountApplicationDTO approve(final Long id, final String notes, final Long reviewerId) {
        AccountApplication entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account application not found: " + id));
        if (entity.getStatus() != AccountApplication.AccountApplicationStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Can only approve UNDER_REVIEW applications");
        }

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setClienteId(entity.getClienteId());
        contaDTO.setTipoConta(Conta.TipoConta.valueOf(entity.getAccountType()));

        ContaDTO createdConta = contaService.criarConta(contaDTO);

        entity.setStatus(AccountApplication.AccountApplicationStatus.APPROVED);
        entity.setReviewNotes(notes);
        entity.setReviewerId(reviewerId);
        entity.setReviewedAt(LocalDateTime.now());
        entity.setAccountId(createdConta.getId());
        entity = repository.save(entity);
        log.info("Account application approved: id={}, accountId={}", id, createdConta.getId());
        return AccountApplicationDTO.fromEntity(entity);
    }

    public AccountApplicationDTO reject(final Long id, final String reason, final Long reviewerId) {
        AccountApplication entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account application not found: " + id));
        if (entity.getStatus() != AccountApplication.AccountApplicationStatus.UNDER_REVIEW
            && entity.getStatus() != AccountApplication.AccountApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Cannot reject application with status: " + entity.getStatus());
        }
        entity.setStatus(AccountApplication.AccountApplicationStatus.REJECTED);
        entity.setReviewNotes(reason);
        entity.setReviewerId(reviewerId);
        entity.setReviewedAt(LocalDateTime.now());
        entity = repository.save(entity);
        log.info("Account application rejected: id={}, reason={}", id, reason);
        return AccountApplicationDTO.fromEntity(entity);
    }
}
