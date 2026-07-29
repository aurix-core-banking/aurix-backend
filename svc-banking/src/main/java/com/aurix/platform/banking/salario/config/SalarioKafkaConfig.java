package com.aurix.platform.banking.salario.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SalarioKafkaConfig {
    @Bean
    public NewTopic topicContaCriada() {
        return TopicBuilder.name(Topics.SALARIO_CONTA_CRIADA).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicCredito() {
        return TopicBuilder.name(Topics.SALARIO_CREDITADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicPortabilidade() {
        return TopicBuilder.name(Topics.SALARIO_PORTABILIDADE_SOLICITADA).partitions(3).replicas(1).build();
    }
}
