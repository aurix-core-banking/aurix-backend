package com.aurix.platform.intelligence;

import org.apache.kafka.clients.producer.MockProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.intelligence",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.intelligence",
    "com.aurix.platform.shared"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.intelligence.repository",
    "com.aurix.platform.shared.eventhub"
})
@EnableScheduling
@EnableCaching
public class IntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligenceApplication.class, args);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Object> testKafkaTemplate() {
        ProducerFactory<String, Object> noopFactory = () -> new MockProducer<>();
        return new KafkaTemplate<>(noopFactory);
    }
}