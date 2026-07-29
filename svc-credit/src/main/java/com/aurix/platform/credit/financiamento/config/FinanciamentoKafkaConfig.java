package com.aurix.platform.credit.financiamento.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class FinanciamentoKafkaConfig {
    @Bean
    public NewTopic topicSimulacaoRealizada() {
        return TopicBuilder.name(Topics.FINANCIAMENTO_SIMULACAO_REALIZADA)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicContratoAssinado() {
        return TopicBuilder.name(Topics.FINANCIAMENTO_CONTRATO_ASSINADO)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicParcelaPaga() {
        return TopicBuilder.name(Topics.FINANCIAMENTO_PARCELA_PAGA)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicContratoLiquidado() {
        return TopicBuilder.name(Topics.FINANCIAMENTO_CONTRATO_LIQUIDADO)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicGarantiaRegistrada() {
        return TopicBuilder.name(Topics.FINANCIAMENTO_GARANTIA_REGISTRADA)
            .partitions(3)
            .replicas(1)
            .build();
    }
}
