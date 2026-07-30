package com.aurix.platform.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuração do Jackson para serialização JSON.
 */
@Configuration
public class JacksonConfig {

    /** Formato de data e hora padrão. */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /** Formato de data padrão. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Cria e configura o ObjectMapper primário.
     *
     * @return ObjectMapper configurado
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Configurar módulo de tempo
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(
                        DateTimeFormatter.ofPattern(DATE_FORMAT)));

        mapper.registerModule(timeModule);

        // Configurações de serialização
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return mapper;
    }
}
