package com.aurix.platform.banking.poupanca.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PoupancaKafkaConfig {
    @Bean
    public NewTopic topicoContaCriada() {
        return TopicBuilder.name(Topics.POUPANCA_CONTA_CRIADA).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicoDeposito() {
        return TopicBuilder.name(Topics.POUPANCA_DEPOSITO_REALIZADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicoSaque() {
        return TopicBuilder.name(Topics.POUPANCA_SAQUE_REALIZADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicoRendimento() {
        return TopicBuilder.name(Topics.POUPANCA_RENDIMENTO_CREDITADO).partitions(3).replicas(1).build();
    }
}
