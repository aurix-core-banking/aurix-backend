package com.aurix.platform.cards.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CartoesKafkaConfig {
    @Bean
    public NewTopic cartaoEmitidoTopic() { return TopicBuilder.name(Topics.CARTOES_CARTAO_EMITIDO).partitions(3).replicas(1).build(); }
    @Bean
    public NewTopic transacaoAutorizadaTopic() { return TopicBuilder.name(Topics.CARTOES_TRANSACAO_AUTORIZADA).partitions(3).replicas(1).build(); }
    @Bean
    public NewTopic transacaoEstornadaTopic() { return TopicBuilder.name(Topics.CARTOES_TRANSACAO_ESTORNADA).partitions(3).replicas(1).build(); }
    @Bean
    public NewTopic faturaFechadaTopic() { return TopicBuilder.name(Topics.CARTOES_FATURA_FECHADA).partitions(3).replicas(1).build(); }
    @Bean
    public NewTopic faturaPagaTopic() { return TopicBuilder.name(Topics.CARTOES_FATURA_PAGA).partitions(3).replicas(1).build(); }
}
