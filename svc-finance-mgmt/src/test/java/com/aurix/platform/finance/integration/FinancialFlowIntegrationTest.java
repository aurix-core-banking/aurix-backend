package com.aurix.platform.finance.integration;

import com.aurix.platform.finance.FinanceApplication;
import com.aurix.platform.finance.entity.ContaPagar;
import com.aurix.platform.finance.entity.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class FinancialFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    @PersistenceContext
    private EntityManager em;

    private String baseUrl;
    private Fornecedor fornecedor;

    private static long fornecedorSeq = 0;

    @BeforeEach
    void setUp() {
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        baseUrl = "http://localhost:" + port + "/api/finance/contas-pagar";
        fornecedorSeq++;
        fornecedor = new Fornecedor();
        fornecedor.setCodigoFornecedor("FORN-" + fornecedorSeq);
        fornecedor.setNomeRazaoSocial("Fornecedor Teste Ltda");
        fornecedor.setNomeFantasia("Fornecedor Teste");
        fornecedor.setTipoPessoa(Fornecedor.TipoPessoa.JURIDICA);
        fornecedor.setCidade("São Paulo");
        fornecedor.setEstado("SP");
        fornecedor.setStatus(Fornecedor.StatusFornecedor.ATIVO);
        em.persist(fornecedor);
        em.flush();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    void shouldCriarContaPagar() {
        ContaPagar contaPagar = new ContaPagar();
        contaPagar.setNumeroDocumento("NF-001");
        contaPagar.setDescricao("Serviço de Consultoria");
        contaPagar.setFornecedor(fornecedor);
        contaPagar.setValorOriginal(BigDecimal.valueOf(15000.00));
        contaPagar.setValorTotal(BigDecimal.valueOf(15000.00));
        contaPagar.setDataVencimento(LocalDate.now().plusDays(30));
        contaPagar.setMoeda("BRL");
        contaPagar.setCategoria("SERVICOS");
        contaPagar.setCentroCusto("ADMINISTRATIVO");
        contaPagar.setUsuarioCriacao("admin");
        contaPagar.setDataEmissao(LocalDate.now());
        contaPagar.setStatus(ContaPagar.StatusConta.PENDENTE);

        ResponseEntity<ContaPagar> response = rest.postForEntity(baseUrl, contaPagar, ContaPagar.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("NF-001", response.getBody().getNumeroDocumento());
    }

    @Test
    void shouldAprovarContaPagar() {
        ContaPagar contaPagar = new ContaPagar();
        contaPagar.setNumeroDocumento("NF-002");
        contaPagar.setDescricao("Material de Escritório");
        contaPagar.setFornecedor(fornecedor);
        contaPagar.setValorOriginal(BigDecimal.valueOf(5000.00));
        contaPagar.setValorTotal(BigDecimal.valueOf(5000.00));
        contaPagar.setDataVencimento(LocalDate.now().plusDays(30));
        contaPagar.setMoeda("BRL");
        contaPagar.setCategoria("MATERIAL");
        contaPagar.setCentroCusto("ADMINISTRATIVO");
        contaPagar.setUsuarioCriacao("admin");
        contaPagar.setDataEmissao(LocalDate.now());

        ResponseEntity<ContaPagar> createResp = rest.postForEntity(baseUrl, contaPagar, ContaPagar.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<ContaPagar> response = rest.postForEntity(
            baseUrl + "/" + id + "/aprovar?usuarioAprovacao=Gerente", null, ContaPagar.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ContaPagar.StatusConta.APROVADA, response.getBody().getStatus());
    }

    @Test
    void shouldPagarConta() {
        ContaPagar contaPagar = new ContaPagar();
        contaPagar.setNumeroDocumento("NF-003");
        contaPagar.setDescricao("Serviço de Limpeza");
        contaPagar.setFornecedor(fornecedor);
        contaPagar.setValorOriginal(BigDecimal.valueOf(3000.00));
        contaPagar.setValorTotal(BigDecimal.valueOf(3000.00));
        contaPagar.setDataVencimento(LocalDate.now().minusDays(5));
        contaPagar.setMoeda("BRL");
        contaPagar.setCategoria("SERVICOS");
        contaPagar.setCentroCusto("ADMINISTRATIVO");
        contaPagar.setUsuarioCriacao("admin");
        contaPagar.setDataEmissao(LocalDate.now());

        ResponseEntity<ContaPagar> createResp = rest.postForEntity(baseUrl, contaPagar, ContaPagar.class);
        Long id = createResp.getBody().getId();
        rest.postForEntity(baseUrl + "/" + id + "/aprovar?usuarioAprovacao=Gerente", null, ContaPagar.class);

        ResponseEntity<ContaPagar> response = rest.postForEntity(
            baseUrl + "/" + id + "/pagar?valorPago=3000.00&dataPagamento=" + LocalDate.now(),
            null, ContaPagar.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ContaPagar.StatusConta.PAGA, response.getBody().getStatus());
    }

    @Test
    void shouldCancelarConta() {
        ContaPagar contaPagar = new ContaPagar();
        contaPagar.setNumeroDocumento("NF-004");
        contaPagar.setDescricao("Serviço Cancelado");
        contaPagar.setFornecedor(fornecedor);
        contaPagar.setValorOriginal(BigDecimal.valueOf(2000.00));
        contaPagar.setValorTotal(BigDecimal.valueOf(2000.00));
        contaPagar.setDataVencimento(LocalDate.now().plusDays(10));
        contaPagar.setMoeda("BRL");
        contaPagar.setCategoria("SERVICOS");
        contaPagar.setCentroCusto("ADMINISTRATIVO");
        contaPagar.setUsuarioCriacao("admin");
        contaPagar.setDataEmissao(LocalDate.now());

        ResponseEntity<ContaPagar> createResp = rest.postForEntity(baseUrl, contaPagar, ContaPagar.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<ContaPagar> response = rest.postForEntity(
            baseUrl + "/" + id + "/cancelar?motivo=Pedido%20cancelado%20pelo%20fornecedor",
            null, ContaPagar.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ContaPagar.StatusConta.CANCELADA, response.getBody().getStatus());
    }

    @Test
    void shouldBuscarContasVencidas() {
        ResponseEntity<List> response = rest.getForEntity(
            baseUrl + "/vencidas", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldCalcularTotalPorStatus() {
        ResponseEntity<BigDecimal> response = rest.getForEntity(
            baseUrl + "/total-por-status/PENDENTE", BigDecimal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
