package com.aurix.platform.banking.poupanca.controller;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.poupanca.dto.ContaPoupancaResponse;
import com.aurix.platform.banking.poupanca.dto.CriarContaRequest;
import com.aurix.platform.banking.poupanca.repository.ContaPoupancaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(ContaPoupancaControllerTest.TestConfig.class)
class ContaPoupancaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContaPoupancaRepository repository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveCriarContaPoupanca() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        request.setAniversarioDia(15);

        ResponseEntity<ContaPoupancaResponse> response = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getClienteId()).isEqualTo(1L);
        assertThat(response.getBody().getNumeroConta()).isNotBlank();
        assertThat(response.getBody().getSaldo()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void deveBuscarContaPorId() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        ResponseEntity<ContaPoupancaResponse> criada = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<ContaPoupancaResponse> response = rest.getForEntity(
            url("/api/poupanca/contas/" + id), ContaPoupancaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @TestConfiguration
    @EnableWebSecurity
    static class TestConfig {
        @Bean
        public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http.build();
        }

        @Bean
        @SuppressWarnings("unchecked")
        public KafkaTemplate<String, Object> kafkaTemplate() {
            return Mockito.mock(KafkaTemplate.class);
        }
    }
}
