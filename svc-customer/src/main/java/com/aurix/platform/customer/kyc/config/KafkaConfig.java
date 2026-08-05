package com.aurix.platform.customer.kyc.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("kycKafkaConfig")
public class KafkaConfig {
    @Bean
    public NewTopic kycAprovadoTopic() {
        return TopicBuilder.name(Topics.KYC_SOLICITACAO_APROVADA).partitions(3).replicas(1).build();
    }
    @Bean
    public NewTopic kycRejeitadoTopic() {
        return TopicBuilder.name(Topics.KYC_SOLICITACAO_REJEITADA).partitions(3).replicas(1).build();
    }
}
