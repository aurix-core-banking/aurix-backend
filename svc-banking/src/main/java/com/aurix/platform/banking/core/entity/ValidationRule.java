package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "validation_rules", schema = "aurix")
public class ValidationRule extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "spel_expression", nullable = false, length = 1000)
    private String spelExpression;

    @NotBlank
    @Size(max = 20)
    @Column(name = "error_code", nullable = false, length = 20)
    private String errorCode;

    @Size(max = 500)
    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, length = 20)
    private RuleScope scope = RuleScope.TRANSACAO;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "priority", nullable = false)
    private Integer priority = 0;

    @Size(max = 50)
    @Column(name = "rule_category", length = 50)
    private String ruleCategory;

    public ValidationRule() {}

    @java.lang.SuppressWarnings("all")
    public String getName() { return this.name; }

    @java.lang.SuppressWarnings("all")
    public String getDescription() { return this.description; }

    @java.lang.SuppressWarnings("all")
    public String getSpelExpression() { return this.spelExpression; }

    @java.lang.SuppressWarnings("all")
    public String getErrorCode() { return this.errorCode; }

    @java.lang.SuppressWarnings("all")
    public String getErrorMessage() { return this.errorMessage; }

    @java.lang.SuppressWarnings("all")
    public RuleScope getScope() { return this.scope; }

    @java.lang.SuppressWarnings("all")
    public Boolean getActive() { return this.active; }

    @java.lang.SuppressWarnings("all")
    public Integer getPriority() { return this.priority; }

    @java.lang.SuppressWarnings("all")
    public String getRuleCategory() { return this.ruleCategory; }

    @java.lang.SuppressWarnings("all")
    public void setName(final String name) { this.name = name; }

    @java.lang.SuppressWarnings("all")
    public void setDescription(final String description) { this.description = description; }

    @java.lang.SuppressWarnings("all")
    public void setSpelExpression(final String spelExpression) { this.spelExpression = spelExpression; }

    @java.lang.SuppressWarnings("all")
    public void setErrorCode(final String errorCode) { this.errorCode = errorCode; }

    @java.lang.SuppressWarnings("all")
    public void setErrorMessage(final String errorMessage) { this.errorMessage = errorMessage; }

    @java.lang.SuppressWarnings("all")
    public void setScope(final RuleScope scope) { this.scope = scope; }

    @java.lang.SuppressWarnings("all")
    public void setActive(final Boolean active) { this.active = active; }

    @java.lang.SuppressWarnings("all")
    public void setPriority(final Integer priority) { this.priority = priority; }

    @java.lang.SuppressWarnings("all")
    public void setRuleCategory(final String ruleCategory) { this.ruleCategory = ruleCategory; }

    public enum RuleScope {
        TRANSACAO, CONTA, CLIENTE
    }
}
