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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CoreFlowIntegrationTest.TestConfig.class)
class ClientePFIntegrationTest {

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
    void deveCriarEBuscarClientePF() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        dto.setCpf("52998224725");
        dto.setNome("João PF");
        dto.setEmail("joao@test.com");
        dto.setTelefone("11999999999");

        ResponseEntity<ClienteDTO> postResponse = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getId()).isNotNull();

        Long id = postResponse.getBody().getId();

        ResponseEntity<ClienteDTO> getResponse = rest.getForEntity(
            url("/api/core/clientes/" + id), ClienteDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNome()).isEqualTo("João PF");
        assertThat(getResponse.getBody().getTipoPessoa()).isEqualTo(Cliente.TipoPessoa.FISICA);
    }

    @Test
    void deveBuscarClientePFPorCpf() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        dto.setCpf("98765432100");
        dto.setNome("Maria PF");
        dto.setEmail("maria@test.com");
        dto.setTelefone("11988888888");

        rest.postForEntity(url("/api/core/clientes"), dto, ClienteDTO.class);

        ResponseEntity<ClienteDTO> response = rest.getForEntity(
            url("/api/core/clientes/cpf/98765432100"), ClienteDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNome()).isEqualTo("Maria PF");
        assertThat(response.getBody().getCpf()).isEqualTo("98765432100");
    }

    @Test
    void deveListarClientes() {
        ClienteDTO dto1 = new ClienteDTO();
        dto1.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        dto1.setCpf("89437367388");
        dto1.setNome("Cliente Um");
        dto1.setEmail("um@test.com");
        dto1.setTelefone("11911111111");

        ClienteDTO dto2 = new ClienteDTO();
        dto2.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        dto2.setCpf("45006046775");
        dto2.setNome("Cliente Dois");
        dto2.setEmail("dois@test.com");
        dto2.setTelefone("11922222222");

        rest.postForEntity(url("/api/core/clientes"), dto1, ClienteDTO.class);
        rest.postForEntity(url("/api/core/clientes"), dto2, ClienteDTO.class);

        ResponseEntity<ClienteDTO[]> response = rest.getForEntity(
            url("/api/core/clientes"), ClienteDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        List<ClienteDTO> clientes = Arrays.asList(response.getBody());
        assertThat(clientes).hasSize(2);
    }
}
