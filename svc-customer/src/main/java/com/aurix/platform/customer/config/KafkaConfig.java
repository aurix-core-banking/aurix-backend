package com.aurix.platform.customer.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("customerKafkaConfig")
public class KafkaConfig {
    @Bean
    public NewTopic clienteCriadoTopic() {
        return TopicBuilder.name(Topics.CUSTOMER_CLIENTE_CRIADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic clienteAtualizadoTopic() {
        return TopicBuilder.name(Topics.CUSTOMER_CLIENTE_ATUALIZADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic clienteStatusAlteradoTopic() {
        return TopicBuilder.name(Topics.CUSTOMER_CLIENTE_STATUS_ALTERADO).partitions(3).replicas(1).build();
    }
}
