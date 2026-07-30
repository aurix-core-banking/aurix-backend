package com.aurix.platform.credit.credit.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("creditKafkaConfig")
public class CreditKafkaConfig {
    @Bean
    public NewTopic solicitacaoCreditoCriadaTopic() {
        return TopicBuilder.name(Topics.CREDIT_SOLICITACAO_CRIADA)
            .partitions(3).replicas(1).build();
    }
}
