package com.aurix.platform.finance.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("taxKafkaConfig")
public class TaxKafkaConfig {
    @Bean
    public NewTopic impostoCalculadoTopic() {
        return TopicBuilder.name(Topics.IMPOSTO_CALCULADO).partitions(3).replicas(1).build();
    }
}
