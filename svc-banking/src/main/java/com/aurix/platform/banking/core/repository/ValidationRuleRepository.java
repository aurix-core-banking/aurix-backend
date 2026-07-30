package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {
    List<ValidationRule> findByActiveTrueAndScopeOrderByPriorityAsc(ValidationRule.RuleScope scope);
    List<ValidationRule> findByRuleCategoryOrderByPriorityAsc(String category);
    List<ValidationRule> findByNameContainingIgnoreCase(String name);
}
