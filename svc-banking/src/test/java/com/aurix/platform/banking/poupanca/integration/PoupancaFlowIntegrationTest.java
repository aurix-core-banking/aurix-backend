package com.aurix.platform.banking.poupanca.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.poupanca.client.BacenClient;
import com.aurix.platform.banking.poupanca.client.ContaCorrenteClient;
import com.aurix.platform.banking.poupanca.client.TaxClient;
import com.aurix.platform.banking.poupanca.dto.ContaPoupancaResponse;
import com.aurix.platform.banking.poupanca.dto.CriarContaRequest;
import com.aurix.platform.banking.poupanca.dto.DepositoRequest;
import com.aurix.platform.banking.poupanca.dto.ExtratoResponse;
import com.aurix.platform.banking.poupanca.dto.SaqueRequest;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca;
import com.aurix.platform.banking.poupanca.repository.ContaPoupancaRepository;
import com.aurix.platform.banking.poupanca.repository.MovimentacaoPoupancaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(PoupancaFlowIntegrationTest.TestConfig.class)
class PoupancaFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContaPoupancaRepository contaPoupancaRepository;

    @Autowired
    private MovimentacaoPoupancaRepository movimentacaoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        contaPoupancaRepository.deleteAll();
        movimentacaoRepository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
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

    @Test
    void deveListarContasPorCliente() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(10L);
        request.setContaCorrenteId(1L);
        rest.postForEntity(url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);

        ResponseEntity<ContaPoupancaResponse[]> response = rest.getForEntity(
            url("/api/poupanca/contas/cliente/10"), ContaPoupancaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveBloquearConta() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        ResponseEntity<ContaPoupancaResponse> criada = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);
        Long id = criada.getBody().getId();

        ResponseEntity<Void> response = rest.exchange(
            url("/api/poupanca/contas/" + id + "/bloquear"), HttpMethod.PATCH,
            HttpEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveDepositar() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        ResponseEntity<ContaPoupancaResponse> criada = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);
        Long id = criada.getBody().getId();

        DepositoRequest deposito = new DepositoRequest();
        deposito.setContaPoupancaId(id);
        deposito.setValor(new BigDecimal("500.00"));

        ResponseEntity<Void> response = rest.postForEntity(
            url("/api/poupanca/movimentacoes/deposito"), deposito, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveSacar() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        ResponseEntity<ContaPoupancaResponse> criada = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);
        Long id = criada.getBody().getId();

        DepositoRequest deposito = new DepositoRequest();
        deposito.setContaPoupancaId(id);
        deposito.setValor(new BigDecimal("1000.00"));
        rest.postForEntity(url("/api/poupanca/movimentacoes/deposito"), deposito, Void.class);

        ContaPoupanca conta = contaPoupancaRepository.findById(id).orElseThrow();
        if (conta.getUltimoAniversario() == null) {
            conta.setUltimoAniversario(LocalDate.now().minusDays(31));
            contaPoupancaRepository.save(conta);
        }

        SaqueRequest saque = new SaqueRequest();
        saque.setContaPoupancaId(id);
        saque.setValor(new BigDecimal("300.00"));

        ResponseEntity<Void> response = rest.postForEntity(
            url("/api/poupanca/movimentacoes/saque"), saque, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveGerarExtrato() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        ResponseEntity<ContaPoupancaResponse> criada = rest.postForEntity(
            url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);
        Long id = criada.getBody().getId();

        DepositoRequest deposito = new DepositoRequest();
        deposito.setContaPoupancaId(id);
        deposito.setValor(new BigDecimal("1000.00"));
        rest.postForEntity(url("/api/poupanca/movimentacoes/deposito"), deposito, Void.class);

        ResponseEntity<ExtratoResponse> response = rest.getForEntity(
            url("/api/poupanca/movimentacoes/conta/" + id), ExtratoResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMovimentacoes()).isNotEmpty();
    }

    @Test
    void deveProcessarAniversario() {
        CriarContaRequest request = new CriarContaRequest();
        request.setClienteId(1L);
        request.setContaCorrenteId(1L);
        rest.postForEntity(url("/api/poupanca/contas"), request, ContaPoupancaResponse.class);

        ResponseEntity<Map> response = rest.postForEntity(
            url("/api/poupanca/aniversario/processar"), null, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("contasProcessadas");
    }

    @Test
    void deveEstimarProximoRendimento() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/poupanca/aniversario/proximo"), Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("trDiaria");
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

        @Bean
        @Primary
        public ContaCorrenteClient contaCorrenteClient() {
            return Mockito.mock(ContaCorrenteClient.class);
        }

        @Bean
        @Primary
        public TaxClient taxClient() {
            TaxClient mock = Mockito.mock(TaxClient.class);
            when(mock.calcularIof(any())).thenReturn(new TaxClient.IofResponse(BigDecimal.ZERO, ""));
            return mock;
        }

        @Bean
        @Primary
        public BacenClient bacenClient() {
            BacenClient mock = Mockito.mock(BacenClient.class);
            when(mock.buscarTrDiaria(any())).thenReturn(BigDecimal.ZERO);
            return mock;
        }
    }
}
