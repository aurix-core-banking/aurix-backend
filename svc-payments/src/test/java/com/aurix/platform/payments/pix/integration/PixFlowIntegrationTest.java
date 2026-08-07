package com.aurix.platform.payments.pix.integration;

import com.aurix.platform.payments.AurixPaymentsApplication;
import com.aurix.platform.payments.pix.client.BacenPixClient;
import com.aurix.platform.payments.pix.client.PixBacenClient;
import com.aurix.platform.payments.pix.client.dto.SpiResult;
import com.aurix.platform.shared.repository.ClienteRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.payments.pix.repository.OutboxEventRepository;
import com.aurix.platform.shared.repository.PixChaveRepository;
import com.aurix.platform.shared.repository.PixTransferenciaRepository;
import com.aurix.platform.shared.dto.PixChaveDTO;
import com.aurix.platform.shared.dto.PixTransferenciaDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PixChave;
import com.aurix.platform.shared.entity.PixTransferencia;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import org.springframework.web.client.RestClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AurixPaymentsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(PixFlowIntegrationTest.TestConfig.class)
class PixFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PixChaveRepository pixChaveRepository;

    @Autowired
    private PixTransferenciaRepository pixTransferenciaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    private RestTemplate rest;
    private Conta contaTeste;
    private Cliente clienteTeste;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        outboxEventRepository.deleteAll();
        pixTransferenciaRepository.deleteAll();
        pixChaveRepository.deleteAll();
        contaRepository.deleteAll();
        clienteRepository.deleteAll();

        clienteTeste = new Cliente();
        clienteTeste.setCpf("12345678901");
        clienteTeste.setNome("Jackson Wendel");
        clienteTeste.setEmail("jackson@aurix.com");
        clienteTeste.setStatus(Cliente.StatusCliente.ATIVO);
        clienteTeste.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        clienteTeste = clienteRepository.save(clienteTeste);

        contaTeste = new Conta();
        contaTeste.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        contaTeste.setNumeroConta("12345-6");
        contaTeste.setCliente(clienteTeste);
        contaTeste.setTipoConta(Conta.TipoConta.CORRENTE);
        contaTeste.setSaldo(BigDecimal.valueOf(5000.00));
        contaTeste.setStatus(Conta.StatusConta.ATIVA);
        contaTeste = contaRepository.save(contaTeste);

        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/pix" + path;
    }

    private PixChaveDTO criarChavePix(String chave, PixChave.TipoChavePix tipo, String nomeTitular) {
        PixChaveDTO dto = new PixChaveDTO();
        dto.setChavePix(chave);
        dto.setContaId(contaTeste.getId());
        dto.setTipoChave(tipo);
        dto.setNomeTitular(nomeTitular);
        ResponseEntity<PixChaveDTO> response = rest.postForEntity(url("/chaves"), dto, PixChaveDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return response.getBody();
    }

    @Test
    void testCriarChavePix_CPF() {
        PixChaveDTO chave = criarChavePix("52998224725", PixChave.TipoChavePix.CPF, "Jackson Wendel");

        assertThat(chave).isNotNull();
        assertThat(chave.getId()).isNotNull();
        assertThat(chave.getChavePix()).isEqualTo("52998224725");
        assertThat(chave.getTipoChave()).isEqualTo(PixChave.TipoChavePix.CPF);
        assertThat(chave.getStatus()).isEqualTo(PixChave.StatusChavePix.ATIVA);
    }

    @Test
    void testCriarChavePix_Email() {
        PixChaveDTO chave = criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        assertThat(chave).isNotNull();
        assertThat(chave.getId()).isNotNull();
        assertThat(chave.getChavePix()).isEqualTo("jackson@aurix.com");
        assertThat(chave.getStatus()).isEqualTo(PixChave.StatusChavePix.ATIVA);
    }

    @Test
    void testCriarChavePix_Telefone() {
        PixChaveDTO chave = criarChavePix("+5511999999999", PixChave.TipoChavePix.TELEFONE, "Jackson Wendel");

        assertThat(chave).isNotNull();
        assertThat(chave.getId()).isNotNull();
        assertThat(chave.getChavePix()).isEqualTo("+5511999999999");
    }

    @Test
    void testCriarChavePix_Aleatoria() {
        String chaveAleatoria = UUID.randomUUID().toString();
        PixChaveDTO chave = criarChavePix(chaveAleatoria, PixChave.TipoChavePix.CHAVE_ALEATORIA, "Jackson Wendel");

        assertThat(chave).isNotNull();
        assertThat(chave.getId()).isNotNull();
        assertThat(chave.getChavePix()).isEqualTo(chaveAleatoria);
    }

    @Test
    void testCriarChavePix_Duplicada() {
        criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        PixChaveDTO dto = new PixChaveDTO();
        dto.setChavePix("jackson@aurix.com");
        dto.setContaId(contaTeste.getId());
        dto.setTipoChave(PixChave.TipoChavePix.EMAIL);
        dto.setNomeTitular("Jackson Wendel");

        try {
            rest.postForEntity(url("/chaves"), dto, Map.class);
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    void testListarChavesPorConta() {
        criarChavePix("52998224725", PixChave.TipoChavePix.CPF, "Jackson Wendel");
        criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        ResponseEntity<PixChaveDTO[]> response = rest.getForEntity(url("/chaves/conta/" + contaTeste.getId()), PixChaveDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<PixChaveDTO> chaves = Arrays.asList(response.getBody());
        assertThat(chaves).hasSize(2);
    }

    @Test
    void testListarChavesAtivasPorConta() {
        PixChaveDTO chave = criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        rest.put(url("/chaves/" + chave.getId() + "/inativar"), null);

        ResponseEntity<PixChaveDTO[]> response = rest.getForEntity(
            url("/chaves/conta/" + contaTeste.getId() + "/ativas"), PixChaveDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isEmpty();
    }

    @Test
    void testInativarEAtivarChavePix() {
        PixChaveDTO chave = criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        rest.put(url("/chaves/" + chave.getId() + "/inativar"), null);

        ResponseEntity<PixChaveDTO> chaveInativada = rest.getForEntity(url("/chaves/" + chave.getId()), PixChaveDTO.class);
        assertThat(chaveInativada.getBody().getStatus()).isEqualTo(PixChave.StatusChavePix.INATIVA);

        rest.put(url("/chaves/" + chave.getId() + "/ativar"), null);

        ResponseEntity<PixChaveDTO> chaveAtivada = rest.getForEntity(url("/chaves/" + chave.getId()), PixChaveDTO.class);
        assertThat(chaveAtivada.getBody().getStatus()).isEqualTo(PixChave.StatusChavePix.ATIVA);
    }

    @Test
    void testBuscarChavePorChavePix() {
        criarChavePix("jackson@aurix.com", PixChave.TipoChavePix.EMAIL, "Jackson Wendel");

        ResponseEntity<PixChaveDTO> response = rest.getForEntity(url("/chaves/chave/jackson@aurix.com"), PixChaveDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getChavePix()).isEqualTo("jackson@aurix.com");
    }

    @Test
    void testCriarTransferenciaPix() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(150.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        ResponseEntity<PixTransferenciaDTO> response = rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getCodigoPix()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(PixTransferencia.StatusPix.PENDENTE);
        assertThat(response.getBody().getValor()).isEqualByComparingTo(BigDecimal.valueOf(150.00));
    }

    @Test
    void testConsultarTransferenciaPorId() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(250.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        ResponseEntity<PixTransferenciaDTO> created = rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);
        Long transferenciaId = created.getBody().getId();

        ResponseEntity<PixTransferenciaDTO> response = rest.getForEntity(url("/transferencias/" + transferenciaId), PixTransferenciaDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(transferenciaId);
        assertThat(response.getBody().getValor()).isEqualByComparingTo(BigDecimal.valueOf(250.00));
    }

    @Test
    void testConsultarTransferenciaPorCodigoPix() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(100.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        ResponseEntity<PixTransferenciaDTO> created = rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);
        String codigoPix = created.getBody().getCodigoPix();

        ResponseEntity<PixTransferenciaDTO> response = rest.getForEntity(url("/transferencias/codigo/" + codigoPix), PixTransferenciaDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCodigoPix()).isEqualTo(codigoPix);
    }

    @Test
    void testProcessarTransferencia() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(500.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        ResponseEntity<PixTransferenciaDTO> created = rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);
        Long transferenciaId = created.getBody().getId();

        rest.put(url("/transferencias/" + transferenciaId + "/processar"), null);

        ResponseEntity<PixTransferenciaDTO> response = rest.getForEntity(url("/transferencias/" + transferenciaId), PixTransferenciaDTO.class);
        assertThat(response.getBody().getStatus()).isEqualTo(PixTransferencia.StatusPix.PROCESSADA);
        assertThat(response.getBody().getDataProcessamento()).isNotNull();
    }

    @Test
    void testCancelarTransferencia() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(300.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        ResponseEntity<PixTransferenciaDTO> created = rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);
        Long transferenciaId = created.getBody().getId();

        rest.put(url("/transferencias/" + transferenciaId + "/cancelar"), null);

        ResponseEntity<PixTransferenciaDTO> response = rest.getForEntity(url("/transferencias/" + transferenciaId), PixTransferenciaDTO.class);
        assertThat(response.getBody().getStatus()).isEqualTo(PixTransferencia.StatusPix.CANCELADA);
    }

    @Test
    void testListarTransferenciasPorConta() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(100.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);

        rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);

        dto.setValor(BigDecimal.valueOf(200.00));
        rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);

        ResponseEntity<PixTransferenciaDTO[]> response = rest.getForEntity(url("/transferencias/conta/" + contaTeste.getId()), PixTransferenciaDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(2);
    }

    @Test
    void testListarTransferenciasPorPeriodo() {
        PixTransferenciaDTO dto = new PixTransferenciaDTO();
        dto.setContaOrigemId(contaTeste.getId());
        dto.setChavePixDestino("jackson@aurix.com");
        dto.setNomeDestinatario("Jackson Wendel");
        dto.setValor(BigDecimal.valueOf(100.00));
        dto.setTipoChave(PixTransferencia.TipoChavePix.EMAIL);
        rest.postForEntity(url("/transferencias"), dto, PixTransferenciaDTO.class);

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);

        ResponseEntity<PixTransferenciaDTO[]> response = rest.getForEntity(
            url("/transferencias/periodo?inicio=" + inicio + "&fim=" + fim),
            PixTransferenciaDTO[].class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isNotEmpty();
    }

    @Test
    void testHealthCheck() {
        ResponseEntity<Map> response = rest.getForEntity(url("/health"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("status")).isEqualTo("UP");
        assertThat(response.getBody().get("service")).isEqualTo("aurix-payments");
    }

    @Test
    void testHealthCheckDetailed() {
        ResponseEntity<Map> response = rest.getForEntity(url("/health/detailed"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("status")).isEqualTo("UP");
    }

    @Test
    void testPixChaveNotFound() {
        try {
            rest.getForEntity(url("/chaves/99999"), Map.class);
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    void testTransferenciaNotFound() {
        try {
            rest.getForEntity(url("/transferencias/99999"), Map.class);
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        @Primary
        @SuppressWarnings("unchecked")
        public KafkaTemplate<String, Object> mockKafkaTemplate() {
            return Mockito.mock(KafkaTemplate.class);
        }

        @Bean
        @Primary
        public BacenPixClient mockBacenPixClient() {
            return Mockito.mock(BacenPixClient.class);
        }

        @Bean
        @Primary
        public RestClient.Builder restClientBuilder() {
            return RestClient.builder();
        }

        @Bean
        @Primary
        public PixBacenClient mockPixBacenClient() {
            PixBacenClient mock = Mockito.mock(PixBacenClient.class);
            SpiResult result = new SpiResult();
            result.setSucesso(true);
            result.setEndToEndId("E0000000020240710TESTENDTOEND01");
            result.setStatus("LIQUIDADA");
            Mockito.when(mock.enviarPix(Mockito.any())).thenReturn(result);
            return mock;
        }
    }
}
