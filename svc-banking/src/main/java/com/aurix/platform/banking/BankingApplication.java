package com.aurix.platform.banking;

import org.apache.kafka.clients.producer.MockProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.banking",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.banking",
    "com.aurix.platform.shared"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.banking.repository",
    "com.aurix.platform.banking.core.repository",
    "com.aurix.platform.banking.poupanca.repository",
    "com.aurix.platform.banking.salario.repository",
    "com.aurix.platform.banking.pricing.repository",
    "com.aurix.platform.banking.settlement.repository",
    "com.aurix.platform.banking.integration.webhook",
    "com.aurix.platform.shared.eventhub"
})
@EnableScheduling
@EnableCaching
public class BankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Bean
    @Primary
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Object> testKafkaTemplate() {
        ProducerFactory<String, Object> noopFactory = new ProducerFactory<>() {
            @Override
            public org.apache.kafka.clients.producer.Producer<String, Object> createProducer() {
                return new MockProducer<>();
            }
        };
        return new KafkaTemplate<>(noopFactory);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, String> testKafkaTemplateString() {
        ProducerFactory<String, String> noopFactory = new ProducerFactory<>() {
            @Override
            public org.apache.kafka.clients.producer.Producer<String, String> createProducer() {
                return new MockProducer<>();
            }
        };
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
            new JsonDeserializer<>()
        ));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setPollTimeout(1000);
        return factory;
    }
}
