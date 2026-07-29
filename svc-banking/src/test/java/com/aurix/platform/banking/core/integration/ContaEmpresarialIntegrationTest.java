package com.aurix.platform.banking.core.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CoreFlowIntegrationTest.TestConfig.class)
class ContaEmpresarialIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RestTemplate rest;
    private Long pjClienteId;
    private Long pfClienteId;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        limparBanco();
        rest = new RestTemplate();
        rest.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.HttpStatusCode statusCode) {
                return false;
            }
        });
        pjClienteId = criarClientePJ();
        pfClienteId = criarClientePF();
    }

    private void limparBanco() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        for (String table : List.of("aurix.contas", "aurix.clientes")) {
            jdbcTemplate.execute("DELETE FROM " + table);
        }
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private Long criarClientePJ() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        dto.setCnpj("11222333000181");
        dto.setNomeRazaoSocial("Empresa Exemplo Ltda");
        dto.setEmail("contato@empresa.com");

        ResponseEntity<ClienteDTO> response = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        return response.getBody().getId();
    }

    private Long criarClientePF() {
        ClienteDTO dto = new ClienteDTO();
        dto.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        dto.setCpf("52998224725");
        dto.setNome("Joao Silva");
        dto.setEmail("joao@teste.com");

        ResponseEntity<ClienteDTO> response = rest.postForEntity(
            url("/api/core/clientes"), dto, ClienteDTO.class);
        return response.getBody().getId();
    }

    @Test
    void criarContaEmpresarialParaPJ_deveRetornar201() {
        ContaDTO request = new ContaDTO();
        request.setClienteId(pjClienteId);
        request.setTipoConta(Conta.TipoConta.EMPRESARIAL);

        ResponseEntity<ContaDTO> response = rest.postForEntity(
            url("/api/core/contas"), request, ContaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getTipoConta()).isEqualTo(Conta.TipoConta.EMPRESARIAL);
        assertThat(response.getBody().getClienteTipoPessoa()).isEqualTo("JURIDICA");
        assertThat(response.getBody().getNumeroConta()).isNotNull();
    }

    @Test
    void criarContaEmpresarialParaPF_deveRetornarErro() {
        ContaDTO request = new ContaDTO();
        request.setClienteId(pfClienteId);
        request.setTipoConta(Conta.TipoConta.EMPRESARIAL);

        assertThrows(RestClientException.class, () ->
            rest.postForEntity(url("/api/core/contas"), request, ContaDTO.class));
    }

    @Test
    void converterParaDTO_deveIncluirClienteTipoPessoa() {
        ContaDTO request = new ContaDTO();
        request.setClienteId(pjClienteId);
        request.setTipoConta(Conta.TipoConta.CORRENTE);

        ResponseEntity<ContaDTO> response = rest.postForEntity(
            url("/api/core/contas"), request, ContaDTO.class);

        assertThat(response.getBody().getClienteTipoPessoa()).isEqualTo("JURIDICA");
    }
}
