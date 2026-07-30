package com.aurix.platform.shared.config;

import com.aurix.platform.shared.event.BaseEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuração do Kafka para integração entre módulos.
 */
@Configuration
@EnableKafka
@Profile("!test")
public class KafkaConfig {

        /** Servidores bootstrap do Kafka. */
        @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
        private String bootstrapServers;

        /** ID do grupo de consumidores. */
        @Value("${spring.kafka.consumer.group-id:aurix-integration}")
        private String groupId;

        /** Número de tentativas de reenvio. */
        private static final int KAFKA_RETRIES = 3;

        /** Tamanho do lote de mensagens. */
        private static final int KAFKA_BATCH_SIZE = 16384;

        /** Memória total do buffer. */
        private static final int KAFKA_BUFFER_MEMORY = 33554432;

        /** Máximo de registros por poll. */
        private static final int KAFKA_MAX_POLL_RECORDS = 500;

        /** Tempo limite da sessão em ms. */
        private static final int KAFKA_SESSION_TIMEOUT = 30000;

        /** Intervalo de heartbeat em ms. */
        private static final int KAFKA_HEARTBEAT_INTERVAL = 3000;

        /** Número de threads concorrentes. */
        private static final int KAFKA_CONCURRENCY = 3;

        /** Tempo limite de poll em ms. */
        private static final int KAFKA_POLL_TIMEOUT = 3000;

        // ========== PRODUCER CONFIG ==========

        /**
         * Cria a factory de produtores Kafka.
         *
         * @return ProducerFactory configurada
         */
        @Bean
        public ProducerFactory<String, Object> producerFactory() {
                Map<String, Object> configProps = new HashMap<>();
                configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                bootstrapServers);
                configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                StringSerializer.class);
                configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                JsonSerializer.class);
                configProps.put(ProducerConfig.ACKS_CONFIG, "all");
                configProps.put(ProducerConfig.RETRIES_CONFIG, KAFKA_RETRIES);
                configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, KAFKA_BATCH_SIZE);
                configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
                configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG,
                                KAFKA_BUFFER_MEMORY);

                return new DefaultKafkaProducerFactory<>(configProps);
        }

        /**
         * Cria o template do Kafka para envio de mensagens.
         *
         * @return KafkaTemplate configurado
         */
        @Bean
        public KafkaTemplate<String, Object> kafkaTemplate() {
                return new KafkaTemplate<>(producerFactory());
        }

        // ========== CONSUMER CONFIG ==========

        /**
         * Cria a factory de consumidores Kafka.
         *
         * @return ConsumerFactory configurada
         */
        @Bean
        public ConsumerFactory<String, BaseEvent> consumerFactory() {
                Map<String, Object> props = new HashMap<>();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
                props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                StringDeserializer.class);
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                JsonDeserializer.class);
                props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
                props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                                KAFKA_MAX_POLL_RECORDS);
                props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
                                KAFKA_SESSION_TIMEOUT);
                props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,
                                KAFKA_HEARTBEAT_INTERVAL);

                JsonDeserializer<BaseEvent> deserializer = new JsonDeserializer<>();
                deserializer.addTrustedPackages("com.aurix.platform.shared.event");

                return new DefaultKafkaConsumerFactory<>(props,
                                new StringDeserializer(), deserializer);
        }

        /**
         * Cria a factory de containers de listeners do Kafka.
         *
         * @return ConcurrentKafkaListenerContainerFactory configurado
         */
        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, BaseEvent> kafkaListenerContainerFactory() {
                ConcurrentKafkaListenerContainerFactory<String, BaseEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(consumerFactory());
                factory.setConcurrency(KAFKA_CONCURRENCY);
                factory.getContainerProperties().setAckMode(
                                ContainerProperties.AckMode.MANUAL_IMMEDIATE);
                factory.getContainerProperties().setPollTimeout(KAFKA_POLL_TIMEOUT);

                return factory;
        }

        // ========== TOPIC CONFIG ==========

        /**
         * Cria o administrador do Kafka.
         *
         * @return KafkaAdmin configurado
         */
        @Bean
        @Profile("!test")
        public KafkaAdmin kafkaAdmin() {
                Map<String, Object> configs = new HashMap<>();
                configs.put("bootstrap.servers", bootstrapServers);
                return new KafkaAdmin(configs);
        }
}
