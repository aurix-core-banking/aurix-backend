package com.aurix.platform.payments;

import org.apache.kafka.clients.producer.MockProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.payments",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.payments",
    "com.aurix.platform.shared"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.payments.pix.repository",
    "com.aurix.platform.shared.eventhub",
    "com.aurix.platform.shared.repository"
})
@EnableScheduling
@EnableCaching
public class AurixPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AurixPaymentsApplication.class, args);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Object> testKafkaTemplate() {
        ProducerFactory<String, Object> noopFactory = () -> new MockProducer<>();
        return new KafkaTemplate<>(noopFactory);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, String> testKafkaTemplateString() {
        ProducerFactory<String, String> noopFactory = () -> new MockProducer<>();
        return new KafkaTemplate<>(noopFactory);
    }

    @Bean
    @Profile("test")
    public DefaultKafkaConsumerFactory<String, Object> testConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(Map.of(
            "bootstrap.servers", "localhost:1",
            "group.id", "test",
            "auto.offset.reset", "earliest"
        ));
    }
}