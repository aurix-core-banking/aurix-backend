package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.ValidationRuleDTO;
import com.aurix.platform.banking.core.entity.ValidationRule;
import com.aurix.platform.banking.core.repository.ValidationRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ValidationRuleService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidationRuleService.class);
    private final ValidationRuleRepository repository;

    public ValidationRuleService(final ValidationRuleRepository repository) {
        this.repository = repository;
    }

    public ValidationRuleDTO criar(final ValidationRuleDTO dto) {
        ValidationRule entity = dto.toEntity();
        entity = repository.save(entity);
        log.info("Regra de validação criada: id={}, name={}", entity.getId(), entity.getName());
        return ValidationRuleDTO.fromEntity(entity);
    }

    public ValidationRuleDTO atualizar(final Long id, final ValidationRuleDTO dto) {
        ValidationRule entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Regra de validação não encontrada: " + id));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSpelExpression(dto.getSpelExpression());
        entity.setErrorCode(dto.getErrorCode());
        entity.setErrorMessage(dto.getErrorMessage());
        entity.setScope(ValidationRule.RuleScope.valueOf(dto.getScope()));
        entity.setActive(dto.getActive());
        entity.setPriority(dto.getPriority());
        entity.setRuleCategory(dto.getRuleCategory());
        entity = repository.save(entity);
        log.info("Regra de validação atualizada: id={}, name={}", entity.getId(), entity.getName());
        return ValidationRuleDTO.fromEntity(entity);
    }

    public Optional<ValidationRuleDTO> buscarPorId(final Long id) {
        return repository.findById(id).map(ValidationRuleDTO::fromEntity);
    }

    public List<ValidationRuleDTO> listarTodas() {
        return repository.findAll().stream()
            .map(ValidationRuleDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<ValidationRuleDTO> listarPorCategoria(final String category) {
        return repository.findByRuleCategoryOrderByPriorityAsc(category).stream()
            .map(ValidationRuleDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<ValidationRule> listarAtivasPorScope(final ValidationRule.RuleScope scope) {
        return repository.findByActiveTrueAndScopeOrderByPriorityAsc(scope);
    }

    public void deletar(final Long id) {
        repository.deleteById(id);
        log.info("Regra de validação deletada: id={}", id);
    }

    public ValidationRuleDTO toggleActive(final Long id) {
        ValidationRule entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Regra de validação não encontrada: " + id));
        entity.setActive(!entity.getActive());
        entity = repository.save(entity);
        return ValidationRuleDTO.fromEntity(entity);
    }
}
