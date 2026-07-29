package com.aurix.platform.credit.consignado.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ConsignadoKafkaConfig {
    @Bean
    public NewTopic topicContratoAssinado() {
        return TopicBuilder.name(Topics.CONSIGNADO_CONTRATO_ASSINADO)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicParcelaDebitada() {
        return TopicBuilder.name(Topics.CONSIGNADO_PARCELA_DEBITADA)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicMargemAtualizada() {
        return TopicBuilder.name(Topics.CONSIGNADO_MARGEM_ATUALIZADA)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicContratoLiquidado() {
        return TopicBuilder.name(Topics.CONSIGNADO_CONTRATO_LIQUIDADO)
            .partitions(3)
            .replicas(1)
            .build();
    }
}
