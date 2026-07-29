package com.aurix.platform.cambio.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CambioKafkaConfig {
    @Bean
    public NewTopic topicCotacaoAtualizada() {
        return TopicBuilder.name(Topics.CAMBIO_COTACAO_ATUALIZADA)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicContratoFechado() {
        return TopicBuilder.name(Topics.CAMBIO_CONTRATO_FECHADO)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicContratoLiquidado() {
        return TopicBuilder.name(Topics.CAMBIO_CONTRATO_LIQUIDADO)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicRemessaProcessada() {
        return TopicBuilder.name(Topics.CAMBIO_REMESSA_PROCESSADA)
            .partitions(3)
            .replicas(1)
            .build();
    }
}
