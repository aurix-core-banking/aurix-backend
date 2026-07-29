package com.aurix.platform.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
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
    "com.aurix.platform.shared"
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
        org.apache.kafka.clients.producer.Producer<String, Object> noopProducer =
            new org.apache.kafka.clients.producer.MockProducer<>();
        ProducerFactory<String, Object> noopFactory = new ProducerFactory<>() {
            @Override
            public org.apache.kafka.clients.producer.Producer<String, Object> createProducer() {
                return noopProducer;
            }
        };
        return new KafkaTemplate<>(noopFactory);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, String> testKafkaTemplateString() {
        org.apache.kafka.clients.producer.Producer<String, String> noopProducer =
            new org.apache.kafka.clients.producer.MockProducer<>();
        ProducerFactory<String, String> noopFactory = new ProducerFactory<>() {
            @Override
            public org.apache.kafka.clients.producer.Producer<String, String> createProducer() {
                return noopProducer;
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
