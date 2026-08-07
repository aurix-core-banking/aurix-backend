package com.aurix.platform.platform;

import org.apache.kafka.clients.producer.MockProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.platform",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.platform",
    "com.aurix.platform.shared"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.platform.repository",
    "com.aurix.platform.shared.eventhub",
    "com.aurix.platform.shared.repository"
})
@EnableScheduling
@EnableCaching
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Object> testKafkaTemplate() {
        ProducerFactory<String, Object> noopFactory = () -> new MockProducer<>();
        return new KafkaTemplate<>(noopFactory);
    }

    @Bean
    @Profile("test")
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
            Map.of(
                "bootstrap.servers", "localhost:1",
                "group.id", "test",
                "auto.offset.reset", "earliest"
            ),
            new org.apache.kafka.common.serialization.StringDeserializer(),
            new org.springframework.kafka.support.serializer.JsonDeserializer<>()
        ));
        return factory;
    }
}