package com.aurix.platform.compliance;

import org.apache.kafka.clients.producer.MockProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Clock;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aurix.platform.compliance", "com.aurix.platform.shared"})
@EntityScan(basePackages = {
    "com.aurix.platform.compliance.entity",
    "com.aurix.platform.shared.entity",
    "com.aurix.platform.shared.eventhub"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.compliance.repository",
    "com.aurix.platform.shared.eventhub"
})
@EnableKafka
@EnableScheduling
@EnableCaching
public class ComplianceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComplianceApplication.class, args);
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

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }
}
