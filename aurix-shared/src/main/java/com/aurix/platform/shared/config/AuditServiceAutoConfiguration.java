package com.aurix.platform.shared.config;

import com.aurix.platform.shared.repository.LogAuditoriaRepository;
import com.aurix.platform.shared.service.AuditService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnBean(LogAuditoriaRepository.class)
public class AuditServiceAutoConfiguration {

    @Bean(name = "auditService")
    @ConditionalOnMissingBean(name = "auditService")
    public AuditService auditServiceCompartilhado(LogAuditoriaRepository logAuditoriaRepository) {
        return new AuditService(logAuditoriaRepository);
    }
}
