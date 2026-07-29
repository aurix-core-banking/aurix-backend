package com.aurix.platform.banking.core.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CoreFlowIntegrationTest.TestConfig.class)
class ClientePJFinancialFieldsTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        limparBanco();
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        rest = new RestTemplate();
        rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.HttpStatusCode statusCode) {
                return false;
            }
        });
    }

    private void limparBanco() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("DELETE FROM aurix.clientes");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveCriarClientePJComCamposFinanceiros() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        dto.setCnpj("55667788000186");
        dto.setNomeRazaoSocial("Financeira Teste Ltda");
        dto.setEmail("financeiro@teste.com");
        dto.setFaturamentoMensal(BigDecimal.valueOf(1500000));
        dto.setCapitalSocial(BigDecimal.valueOf(500000));
        dto.setCnaePrincipal("64.62-0");
        dto.setPorte("EPP");
        dto.setDataConstituicao(LocalDate.of(2019, 6, 15));

        ResponseEntity<ClienteDTO> postResponse = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        ClienteDTO criado = postResponse.getBody();
        assertThat(criado.getFaturamentoMensal()).isEqualByComparingTo(BigDecimal.valueOf(1500000));
        assertThat(criado.getCapitalSocial()).isEqualByComparingTo(BigDecimal.valueOf(500000));
        assertThat(criado.getCnaePrincipal()).isEqualTo("64.62-0");
        assertThat(criado.getPorte()).isEqualTo("EPP");
        assertThat(criado.getDataConstituicao()).isEqualTo(LocalDate.of(2019, 6, 15));
    }

    @Test
    void deveCriarClientePJSemCamposFinanceiros() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        dto.setCnpj("99887766000105");
        dto.setNomeRazaoSocial("Sem Financeiro Ltda");
        dto.setEmail("semfinanceiro@teste.com");

        ResponseEntity<ClienteDTO> postResponse = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        ClienteDTO criado = postResponse.getBody();
        assertThat(criado.getFaturamentoMensal()).isNull();
        assertThat(criado.getCapitalSocial()).isNull();
        assertThat(criado.getCnaePrincipal()).isNull();
        assertThat(criado.getPorte()).isNull();
        assertThat(criado.getDataConstituicao()).isNull();
    }
}
