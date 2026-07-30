package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.ProcessingCodeDTO;
import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.banking.core.repository.ProcessingCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcessingCodeService {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessingCodeService.class);
    private final ProcessingCodeRepository repository;

    public ProcessingCodeService(final ProcessingCodeRepository repository) {
        this.repository = repository;
    }

    public ProcessingCodeDTO criar(final ProcessingCodeDTO dto) {
        if (repository.findByCode(dto.getCode()).isPresent()) {
            throw new IllegalArgumentException("Processing code already exists: " + dto.getCode());
        }
        ProcessingCode entity = dto.toEntity();
        entity = repository.save(entity);
        log.info("Processing code created: code={}, type={}", entity.getCode(), entity.getPaymentType());
        return ProcessingCodeDTO.fromEntity(entity);
    }

    public Optional<ProcessingCodeDTO> buscarPorId(final Long id) {
        return repository.findById(id).map(ProcessingCodeDTO::fromEntity);
    }

    public Optional<ProcessingCodeDTO> buscarPorCode(final String code) {
        return repository.findByCode(code).map(ProcessingCodeDTO::fromEntity);
    }

    public List<ProcessingCodeDTO> listarAtivos() {
        return repository.findByActiveTrueOrderByPriorityAsc().stream()
            .map(ProcessingCodeDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<ProcessingCodeDTO> listarTodos() {
        return repository.findAll().stream()
            .map(ProcessingCodeDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<ProcessingCodeDTO> listarPorTipoPagamento(final String paymentType) {
        return repository.findByPaymentType(paymentType).stream()
            .map(ProcessingCodeDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public ProcessingCodeDTO atualizar(final Long id, final ProcessingCodeDTO dto) {
        ProcessingCode entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Processing code not found: " + id));
        entity.setDescription(dto.getDescription());
        entity.setPaymentType(dto.getPaymentType());
        entity.setActive(dto.isActive());
        entity.setPriority(dto.getPriority());
        entity.setMinAmount(dto.getMinAmount());
        entity.setMaxAmount(dto.getMaxAmount());
        entity.setProcessingRules(dto.getProcessingRules());
        entity = repository.save(entity);
        return ProcessingCodeDTO.fromEntity(entity);
    }

    public void deletar(final Long id) {
        repository.deleteById(id);
        log.info("Processing code deleted: id={}", id);
    }
}
