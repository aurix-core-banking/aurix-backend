package com.aurix.platform.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuração base do Aurix.
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories
@EnableKafka
@EnableAsync
@EnableScheduling
public class AurixConfig {

    // Configurações comuns do Aurix
    // Serão expandidas conforme necessário
}
