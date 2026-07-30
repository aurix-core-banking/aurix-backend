package com.aurix.platform.banking.pricing.config;

import com.aurix.platform.banking.pricing.entity.SimulacaoTarifas;
import com.aurix.platform.banking.pricing.repository.SimulacaoTarifasRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class PricingTestConfig {

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConnectionFactory mockFactory = mock(RedisConnectionFactory.class);
        RedisConnection mockConn = mock(RedisConnection.class);
        when(mockFactory.getConnection()).thenReturn(mockConn);
        when(mockConn.stringCommands()).thenReturn(mock(RedisStringCommands.class));
        return mockFactory;
    }

    @Bean
    @Primary
    public SimulacaoTarifasRepository simulacaoTarifasRepository() {
        SimulacaoTarifasRepository mockRepo = mock(SimulacaoTarifasRepository.class);
        when(mockRepo.save(any(SimulacaoTarifas.class))).thenAnswer(invocation -> {
            SimulacaoTarifas s = invocation.getArgument(0);
            s.setId(1L);
            s.setStatusSimulacao("CONCLUIDA");
            s.setNumeroSimulacao("SIM-" + System.currentTimeMillis());
            return s;
        });
        return mockRepo;
    }

    @Bean
    @Primary
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
