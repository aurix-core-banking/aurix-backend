package com.aurix.platform.platform.config;

import com.aurix.platform.shared.event.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration("guaranteeKafkaConfig")
public class GuaranteeKafkaConfig {
    @Bean
    public NewTopic garantiaRegistradaTopic() {
        return TopicBuilder.name(Topics.GUARANTEE_GARANTIA_REGISTRADA)
            .partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic garantiaLiberadaTopic() {
        return TopicBuilder.name(Topics.GUARANTEE_GARANTIA_LIBERADA)
            .partitions(3).replicas(1).build();
    }
}
