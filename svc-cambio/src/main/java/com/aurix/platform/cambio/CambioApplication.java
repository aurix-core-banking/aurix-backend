package com.aurix.platform.cambio;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableKafka
@EntityScan(basePackages = {"com.aurix.platform.cambio.entity", "com.aurix.platform.shared.entity"})
@OpenAPIDefinition(
    info = @Info(
        title = "Aurix Cambio API",
        version = "1.0.0",
        description = "API para Cambio - Cotacoes, Contratos, Remessas, Integracao BACEN/SPI/STR",
        contact = @Contact(
            name = "Aurix Platform Team",
            email = "dev@aurix.platform",
            url = "https://aurix.platform"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8093/api/cambio", description = "Servidor de Desenvolvimento"),
        @Server(url = "https://api.aurix.platform/cambio", description = "Servidor de Producao")
    }
)
public class CambioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CambioApplication.class, args);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(new ProducerFactory<>() {
            @Override
            public org.apache.kafka.clients.producer.Producer<String, Object> createProducer() {
                return createNoOpProducer();
            }

            @Override
            public org.apache.kafka.clients.producer.Producer<String, Object> createProducer(String txId) {
                return createNoOpProducer();
            }

            @SuppressWarnings("unchecked")
            private org.apache.kafka.clients.producer.Producer<String, Object> createNoOpProducer() {
                return (org.apache.kafka.clients.producer.Producer<String, Object>)
                    java.lang.reflect.Proxy.newProxyInstance(
                        org.apache.kafka.clients.producer.Producer.class.getClassLoader(),
                        new Class<?>[]{org.apache.kafka.clients.producer.Producer.class},
                        (proxy, method, args) -> {
                            if (method.getReturnType() == java.util.concurrent.Future.class) {
                                return java.util.concurrent.CompletableFuture.completedFuture(null);
                            }
                            if (method.getName().equals("flush")) return null;
                            if (method.getName().equals("close")) return null;
                            if (method.getName().equals("initTransactions")) return null;
                            if (method.getName().equals("beginTransaction")) return null;
                            if (method.getName().equals("commitTransaction")) return null;
                            if (method.getName().equals("abortTransaction")) return null;
                            if (method.getName().equals("sendOffsetsToTransaction")) return null;
                            if (method.getName().equals("groupMetadata")) return null;
                            return null;
                        });
            }
        });
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
