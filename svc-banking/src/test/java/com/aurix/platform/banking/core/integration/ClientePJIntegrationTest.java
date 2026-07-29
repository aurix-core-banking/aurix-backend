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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CoreFlowIntegrationTest.TestConfig.class)
class ClientePJIntegrationTest {

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
    void deveCriarEBuscarClientePJ() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        dto.setCnpj("11222333000181");
        dto.setNomeRazaoSocial("Empresa Ltda");
        dto.setEmail("contato@empresa.com");

        ResponseEntity<ClienteDTO> postResponse = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getId()).isNotNull();

        ResponseEntity<ClienteDTO> getResponse = rest.getForEntity(
            url("/api/core/clientes/cnpj/11222333000181"), ClienteDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNomeRazaoSocial()).isEqualTo("Empresa Ltda");
        assertThat(getResponse.getBody().getTipoPessoa()).isEqualTo(Cliente.TipoPessoa.JURIDICA);
    }

    @Test
    void deveCriarEBuscarClientePJPorId() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        dto.setCnpj("91638133946209");
        dto.setNomeRazaoSocial("Comércio ABC");
        dto.setEmail("abc@comercio.com");

        ResponseEntity<ClienteDTO> postResponse = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        Long id = postResponse.getBody().getId();

        ResponseEntity<ClienteDTO> getResponse = rest.getForEntity(
            url("/api/core/clientes/" + id), ClienteDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNomeRazaoSocial()).isEqualTo("Comércio ABC");
        assertThat(getResponse.getBody().getCnpj()).isEqualTo("91638133946209");
    }
}
