package com.aurix.platform.shared.config;

import com.aurix.platform.shared.event.BaseEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.security.authenticator.LoginCallbackHandler;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Suporta SASL/SCRAM para produção (autenticação broker-client).
 */
@Configuration
@EnableKafka
@Profile("!test")
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:aurix-integration}")
    private String groupId;

    // SASL/SSL — habilitado quando `aurix.kafka.sasl.enabled=true`
    @Value("${aurix.kafka.sasl.enabled:false}")
    private boolean saslEnabled;

    @Value("${aurix.kafka.sasl.mechanism:SCRAM-SHA-512}")
    private String saslMechanism;

    @Value("${aurix.kafka.sasl.jaas.config:}")
    private String saslJaasConfig;

    @Value("${aurix.kafka.security.protocol:SASL_SSL}")
    private String securityProtocol;

    @Value("${aurix.kafka.ssl.truststore.location:}")
    private String sslTruststoreLocation;

    @Value("${aurix.kafka.ssl.truststore.password:}")
    private String sslTruststorePassword;

    private static final int KAFKA_RETRIES = 3;
    private static final int KAFKA_MAX_BLOCK_MS = 5000;
    private static final int KAFKA_BATCH_SIZE = 16384;
    private static final int KAFKA_BUFFER_MEMORY = 33554432;
    private static final int KAFKA_MAX_POLL_RECORDS = 500;
    private static final int KAFKA_SESSION_TIMEOUT = 30000;
    private static final int KAFKA_HEARTBEAT_INTERVAL = 3000;
    private static final int KAFKA_CONCURRENCY = 3;
    private static final int KAFKA_POLL_TIMEOUT = 3000;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, KAFKA_RETRIES);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, KAFKA_BATCH_SIZE);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, KAFKA_MAX_BLOCK_MS);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, KAFKA_BUFFER_MEMORY);

        if (saslEnabled) {
            configProps.putAll(buildSaslConfig());
            log.info("Kafka producer configurado com SASL/SSL: {}", securityProtocol);
        }

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, BaseEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KAFKA_MAX_POLL_RECORDS);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, KAFKA_SESSION_TIMEOUT);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, KAFKA_HEARTBEAT_INTERVAL);

        if (saslEnabled) {
            props.putAll(buildSaslConfig());
            log.info("Kafka consumer configurado com SASL/SSL: {}", securityProtocol);
        }

        JsonDeserializer<BaseEvent> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("com.aurix.platform.shared.event");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BaseEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BaseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(KAFKA_CONCURRENCY);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setPollTimeout(KAFKA_POLL_TIMEOUT);
        return factory;
    }

    @Bean
    @Profile("!test")
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", bootstrapServers);
        if (saslEnabled) {
            configs.putAll(buildSaslConfig());
        }
        return new KafkaAdmin(configs);
    }

    /**
     * Constrói configuração SASL/SCRAM para producer, consumer e admin.
     * Em dev/test, SASL está desabilitado (plaintext).
     */
    private Map<String, Object> buildSaslConfig() {
        Map<String, Object> saslProps = new HashMap<>();
        saslProps.put("security.protocol", securityProtocol);
        saslProps.put("sasl.mechanism", saslMechanism);

        if (saslJaasConfig != null && !saslJaasConfig.isBlank()) {
            saslProps.put("sasl.jaas.config", saslJaasConfig);
        } else {
            // Fallback: construct JAAS config from env vars
            String username = System.getenv("KAFKA_SASL_USERNAME");
            String password = System.getenv("KAFKA_SASL_PASSWORD");
            if (username != null && password != null) {
                saslProps.put("sasl.jaas.config",
                    "org.apache.kafka.common.security.scram.ScramLoginModule required "
                    + "username=\"" + username + "\" password=\"" + password + "\";");
            }
        }

        if (sslTruststoreLocation != null && !sslTruststoreLocation.isBlank()) {
            saslProps.put("ssl.truststore.location", sslTruststoreLocation);
            saslProps.put("ssl.truststore.password", sslTruststorePassword);
        } else {
            String truststorePath = System.getenv("KAFKA_SSL_TRUSTSTORE_LOCATION");
            String truststorePwd = System.getenv("KAFKA_SSL_TRUSTSTORE_PASSWORD");
            if (truststorePath != null) {
                saslProps.put("ssl.truststore.location", truststorePath);
                saslProps.put("ssl.truststore.password", truststorePwd != null ? truststorePwd : "");
            }
        }

        return saslProps;
    }
}
