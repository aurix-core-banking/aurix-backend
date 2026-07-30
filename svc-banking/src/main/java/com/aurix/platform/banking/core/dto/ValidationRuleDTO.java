package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.ValidationRule;

public class ValidationRuleDTO {
    private Long id;
    private String name;
    private String description;
    private String spelExpression;
    private String errorCode;
    private String errorMessage;
    private String scope;
    private Boolean active;
    private Integer priority;
    private String ruleCategory;

    public static ValidationRuleDTO fromEntity(ValidationRule entity) {
        ValidationRuleDTO dto = new ValidationRuleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSpelExpression(entity.getSpelExpression());
        dto.setErrorCode(entity.getErrorCode());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setScope(entity.getScope().name());
        dto.setActive(entity.getActive());
        dto.setPriority(entity.getPriority());
        dto.setRuleCategory(entity.getRuleCategory());
        return dto;
    }

    public ValidationRule toEntity() {
        ValidationRule entity = new ValidationRule();
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setSpelExpression(this.spelExpression);
        entity.setErrorCode(this.errorCode);
        entity.setErrorMessage(this.errorMessage);
        entity.setScope(ValidationRule.RuleScope.valueOf(this.scope));
        entity.setActive(this.active != null ? this.active : true);
        entity.setPriority(this.priority != null ? this.priority : 0);
        entity.setRuleCategory(this.ruleCategory);
        return entity;
    }

    @java.lang.SuppressWarnings("all")
    public ValidationRuleDTO() {}

    @java.lang.SuppressWarnings("all")
    public Long getId() { return this.id; }

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
    public String getScope() { return this.scope; }

    @java.lang.SuppressWarnings("all")
    public Boolean getActive() { return this.active; }

    @java.lang.SuppressWarnings("all")
    public Integer getPriority() { return this.priority; }

    @java.lang.SuppressWarnings("all")
    public String getRuleCategory() { return this.ruleCategory; }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) { this.id = id; }

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
    public void setScope(final String scope) { this.scope = scope; }

    @java.lang.SuppressWarnings("all")
    public void setActive(final Boolean active) { this.active = active; }

    @java.lang.SuppressWarnings("all")
    public void setPriority(final Integer priority) { this.priority = priority; }

    @java.lang.SuppressWarnings("all")
    public void setRuleCategory(final String ruleCategory) { this.ruleCategory = ruleCategory; }
}
