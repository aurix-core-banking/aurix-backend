package com.aurix.platform.credit.credit.integration;

import com.aurix.platform.credit.CreditApplication;
import com.aurix.platform.credit.credit.entity.ProdutoCredito;
import com.aurix.platform.credit.credit.repository.ClienteRepository;
import com.aurix.platform.credit.credit.repository.ProdutoCreditoRepository;
import com.aurix.platform.credit.credit.repository.SolicitacaoCreditoRepository;
import com.aurix.platform.shared.dto.SolicitacaoCreditoDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.SolicitacaoCredito;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CreditApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CreditFlowIntegrationTest.TestConfig.class)
class CreditFlowIntegrationTest {

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCreditoRepository produtoCreditoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SolicitacaoCreditoRepository solicitacaoCreditoRepository;

    private RestTemplate rest;
    private Cliente clienteTeste;
    private ProdutoCredito produtoTeste;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        solicitacaoCreditoRepository.deleteAll();
        produtoCreditoRepository.deleteAll();
        clienteRepository.deleteAll();

        clienteTeste = new Cliente();
        clienteTeste.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        clienteTeste.setCpf("52998224725");
        clienteTeste.setNome("Jackson Wendel");
        clienteTeste.setEmail("jackson@aurix.com");
        clienteTeste.setStatus(Cliente.StatusCliente.ATIVO);
        clienteTeste = clienteRepository.save(clienteTeste);

        produtoTeste = new ProdutoCredito();
        produtoTeste.setCodigo("PESSOAL_001");
        produtoTeste.setNome("Credito Pessoal");
        produtoTeste.setTipoCredito(ProdutoCredito.TipoCredito.PESSOAL);
        produtoTeste.setTaxaJurosMin(BigDecimal.valueOf(1.5));
        produtoTeste.setTaxaJurosMax(BigDecimal.valueOf(5.0));
        produtoTeste.setPrazoMinMeses(6);
        produtoTeste.setPrazoMaxMeses(60);
        produtoTeste.setValorMin(BigDecimal.valueOf(1000));
        produtoTeste.setValorMax(BigDecimal.valueOf(50000));
        produtoTeste.setExigeGarantia(false);
        produtoTeste.setAtivo(true);
        produtoTeste = produtoCreditoRepository.save(produtoTeste);

        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/credit" + path;
    }

    @Test
    void testListarProdutosAtivos() {
        ResponseEntity<ProdutoCredito[]> response = rest.getForEntity(url("/produtos"), ProdutoCredito[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ProdutoCredito> produtos = Arrays.asList(response.getBody());
        assertThat(produtos).isNotEmpty();
        assertThat(produtos).allMatch(ProdutoCredito::getAtivo);
    }

    @Test
    void testBuscarProdutoPorId() {
        ResponseEntity<ProdutoCredito> response = rest.getForEntity(url("/produtos/" + produtoTeste.getId()), ProdutoCredito.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(produtoTeste.getId());
        assertThat(response.getBody().getCodigo()).isEqualTo("PESSOAL_001");
    }

    @Test
    void testListarProdutosPorTipo() {
        ProdutoCredito consignado = new ProdutoCredito();
        consignado.setCodigo("CONSIG_001");
        consignado.setNome("Credito Consignado");
        consignado.setTipoCredito(ProdutoCredito.TipoCredito.CONSIGNADO);
        consignado.setTaxaJurosMin(BigDecimal.valueOf(1.0));
        consignado.setTaxaJurosMax(BigDecimal.valueOf(3.0));
        consignado.setPrazoMinMeses(6);
        consignado.setPrazoMaxMeses(72);
        consignado.setValorMin(BigDecimal.valueOf(500));
        consignado.setValorMax(BigDecimal.valueOf(100000));
        consignado.setExigeGarantia(true);
        consignado.setAtivo(true);
        produtoCreditoRepository.save(consignado);

        ResponseEntity<ProdutoCredito[]> response = rest.getForEntity(
            url("/produtos/tipo/" + ProdutoCredito.TipoCredito.PESSOAL), ProdutoCredito[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ProdutoCredito> produtos = Arrays.asList(response.getBody());
        assertThat(produtos).hasSize(1);
        assertThat(produtos.get(0).getTipoCredito()).isEqualTo(ProdutoCredito.TipoCredito.PESSOAL);
    }

    @Test
    void testSimularCredito() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/simulador?valor=10000&prazoMeses=12&taxaJurosAoMes=1.5"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("parcelas")).isNotNull();
        assertThat(response.getBody().get("valorTotal")).isNotNull();
        assertThat(response.getBody().get("totalJuros")).isNotNull();
    }

    @Test
    void testCriarSolicitacaoCredito() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> response = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(SolicitacaoCredito.StatusSolicitacao.PENDENTE);
        assertThat(response.getBody().getValorSolicitado()).isEqualByComparingTo(BigDecimal.valueOf(5000));
    }

    @Test
    void testBuscarSolicitacaoPorId() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> created = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        Long id = created.getBody().getId();

        ResponseEntity<SolicitacaoCreditoDTO> response = rest.getForEntity(
            url("/solicitacoes/" + id), SolicitacaoCreditoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getValorSolicitado()).isEqualByComparingTo(BigDecimal.valueOf(5000));
    }

    @Test
    void testListarSolicitacoesPorCliente() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));
        rest.postForEntity(url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);

        dto.setValorSolicitado(BigDecimal.valueOf(10000));
        rest.postForEntity(url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);

        ResponseEntity<SolicitacaoCreditoDTO[]> response = rest.getForEntity(
            url("/solicitacoes/cliente/" + clienteTeste.getId()), SolicitacaoCreditoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(2);
    }

    @Test
    void testAprovarSolicitacao() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> created = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        Long id = created.getBody().getId();

        rest.put(url("/solicitacoes/" + id + "/aprovar?valorAprovado=5000&prazoAprovado=12&taxaAprovada=2.5"), null);

        ResponseEntity<SolicitacaoCreditoDTO> response = rest.getForEntity(
            url("/solicitacoes/" + id), SolicitacaoCreditoDTO.class);
        assertThat(response.getBody().getStatus()).isEqualTo(SolicitacaoCredito.StatusSolicitacao.APROVADA);
        assertThat(response.getBody().getValorAprovado()).isEqualByComparingTo(BigDecimal.valueOf(5000));
    }

    @Test
    void testRejeitarSolicitacao() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> created = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        Long id = created.getBody().getId();

        rest.put(url("/solicitacoes/" + id + "/rejeitar?observacoes=Score+baixo"), null);

        ResponseEntity<SolicitacaoCreditoDTO> response = rest.getForEntity(
            url("/solicitacoes/" + id), SolicitacaoCreditoDTO.class);
        assertThat(response.getBody().getStatus()).isEqualTo(SolicitacaoCredito.StatusSolicitacao.REJEITADA);
    }

    @Test
    void testListarSolicitacoesPendentes() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));
        rest.postForEntity(url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);

        ResponseEntity<SolicitacaoCreditoDTO[]> response = rest.getForEntity(
            url("/solicitacoes/pendentes"), SolicitacaoCreditoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isNotEmpty();
    }

    @Test
    void testListarSolicitacoesAprovadas() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> created = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        Long id = created.getBody().getId();
        rest.put(url("/solicitacoes/" + id + "/aprovar?valorAprovado=5000&prazoAprovado=12&taxaAprovada=2.5"), null);

        ResponseEntity<SolicitacaoCreditoDTO[]> response = rest.getForEntity(
            url("/solicitacoes/aprovadas"), SolicitacaoCreditoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isNotEmpty();
    }

    @Test
    void testObterDecisaoCredito() {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setClienteId(clienteTeste.getId());
        dto.setValorSolicitado(BigDecimal.valueOf(5000));
        dto.setPrazoMeses(12);
        dto.setTaxaJuros(BigDecimal.valueOf(2.5));

        ResponseEntity<SolicitacaoCreditoDTO> created = rest.postForEntity(
            url("/solicitacoes"), dto, SolicitacaoCreditoDTO.class);
        Long id = created.getBody().getId();

        ResponseEntity<Map> response = rest.postForEntity(
            url("/decisao/" + id), null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("decisao")).isIn("APPROVE", "DECLINE", "REFER");
    }

    @TestConfiguration
    @EnableWebSecurity
    static class TestConfig {
    }
}
