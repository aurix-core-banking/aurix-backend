package com.aurix.platform.cambio.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("cambioBacenKafkaConfig")
public class BacenKafkaConfig {
    @Bean
    public NewTopic relatorioGeradoTopic() {
        return TopicBuilder.name(Topics.RELATORIO_GERADO).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic relatorioEnviadoTopic() {
        return TopicBuilder.name(Topics.RELATORIO_ENVIADO).partitions(3).replicas(1).build();
    }
}
