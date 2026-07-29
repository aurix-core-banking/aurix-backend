package com.aurix.platform.platform.integration;

import com.aurix.platform.shared.dto.InvestimentoDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Investimento;
import com.aurix.platform.shared.entity.Conta.TipoConta;
import com.aurix.platform.shared.entity.Conta.StatusConta;
import com.aurix.platform.platform.PlatformApplication;
import com.aurix.platform.platform.repository.InvestimentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TreasuryFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    private RestTemplate rest;
    private Long contaId;

    @BeforeEach
    void setUp() {
        rest = new RestTemplate();

        var tt = new TransactionTemplate(transactionManager);
        tt.executeWithoutResult(status -> {
            entityManager.createQuery("DELETE FROM Investimento").executeUpdate();
            entityManager.createQuery("DELETE FROM Conta").executeUpdate();
            entityManager.createQuery("DELETE FROM Cliente").executeUpdate();
            entityManager.flush();

            Cliente cliente = new Cliente();
            cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
            cliente.setCpf("12345678901");
            cliente.setNome("Test Client");
            cliente.setEmail("test@example.com");
            cliente.setTenantId("test-tenant");
            entityManager.persist(cliente);
            entityManager.flush();

            Conta conta = new Conta();
            conta.setNumeroConta("12345-6");
            conta.setCliente(entityManager.getReference(Cliente.class, cliente.getId()));
            conta.setTipoConta(TipoConta.CORRENTE);
            conta.setSaldo(BigDecimal.ZERO);
            conta.setLimiteCredito(BigDecimal.ZERO);
            conta.setLimiteUtilizado(BigDecimal.ZERO);
            conta.setStatus(StatusConta.ATIVA);
            conta.setDataAbertura(LocalDateTime.now());
            entityManager.persist(conta);
            entityManager.flush();
            contaId = conta.getId();
        });
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testPosicaoLiquidez() {
        ResponseEntity<Map> response = rest.getForEntity(url("/api/platform/tesouraria-avancada/liquidez"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKeys("totalAplicado", "totalRendimento", "valorTotalCarteira");
    }

    @Test
    void testValueAtRisk() {
        ResponseEntity<Map> response = rest.getForEntity(url("/api/platform/tesouraria-avancada/var?dias=1&nivelConfianca=95"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKeys("var", "valorCarteira", "dias");
    }

    @Test
    void testStressTest() {
        ResponseEntity<Map> response = rest.postForEntity(url("/api/platform/tesouraria-avancada/stress?cenario=taxa_sobe_1pct"), null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKeys("cenario", "valorAtual", "impactoPercentual", "impactoValor", "valorProjetado");
    }

    @Test
    void testCriarInvestimento() {
        InvestimentoDTO dto = new InvestimentoDTO();
        dto.setContaId(contaId);
        dto.setTipoInvestimento(Investimento.TipoInvestimento.CDB);
        dto.setValorInvestido(new BigDecimal("1000.00"));
        dto.setTaxaRendimento(new BigDecimal("0.10"));

        ResponseEntity<InvestimentoDTO> response = rest.postForEntity(url("/api/platform/investimentos"), dto, InvestimentoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getTipoInvestimento()).isEqualTo(Investimento.TipoInvestimento.CDB);
        assertThat(response.getBody().getValorInvestido()).isEqualByComparingTo(new BigDecimal("1000.00"));
    }

    @Test
    void testBuscarInvestimentoPorId() {
        Investimento investimento = criarInvestimentoEntity();
        Long investimentoId = investimentoRepository.save(investimento).getId();

        ResponseEntity<InvestimentoDTO> response = rest.getForEntity(url("/api/platform/investimentos/" + investimentoId), InvestimentoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(investimentoId);
    }

    @Test
    void testListarInvestimentosPorConta() {
        Investimento inv1 = criarInvestimentoEntity();
        Investimento inv2 = criarInvestimentoEntity();
        investimentoRepository.save(inv1);
        investimentoRepository.save(inv2);

        ResponseEntity<List<InvestimentoDTO>> response = rest.exchange(
            url("/api/platform/investimentos/conta/" + contaId), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<InvestimentoDTO>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testListarInvestimentosPorTipo() {
        Investimento inv = criarInvestimentoEntity();
        investimentoRepository.save(inv);

        ResponseEntity<List<InvestimentoDTO>> response = rest.exchange(
            url("/api/platform/investimentos/tipo/CDB"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<InvestimentoDTO>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testListarInvestimentosPorStatus() {
        Investimento inv = criarInvestimentoEntity();
        investimentoRepository.save(inv);

        ResponseEntity<List<InvestimentoDTO>> response = rest.exchange(
            url("/api/platform/investimentos/status/ATIVO"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<InvestimentoDTO>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testSimularInvestimento() {
        ResponseEntity<InvestimentoDTO> response = rest.postForEntity(
            url("/api/platform/investimentos/simular?tipo=CDB&valorInvestido=5000&taxaAnual=0.10&dias=30"), null, InvestimentoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getValorInvestido()).isEqualByComparingTo(new BigDecimal("5000.00"));
    }

    @Test
    void testAtualizarRendimento() {
        Investimento inv = criarInvestimentoEntity();
        Long invId = investimentoRepository.save(inv).getId();

        ResponseEntity<Void> response = rest.exchange(
            url("/api/platform/investimentos/" + invId + "/atualizar-rendimento"), HttpMethod.PUT, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testResgatarInvestimento() {
        Investimento inv = criarInvestimentoEntity();
        Long invId = investimentoRepository.save(inv).getId();

        ResponseEntity<InvestimentoDTO> response = rest.exchange(
            url("/api/platform/investimentos/" + invId + "/resgatar?resgateAntecipado=false"), HttpMethod.PUT, null, InvestimentoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo(Investimento.StatusInvestimento.RESGATADO);
    }

    @Test
    void testCalcularRendimentoTotalPorConta() {
        ResponseEntity<BigDecimal> response = rest.getForEntity(
            url("/api/platform/investimentos/conta/" + contaId + "/rendimento-total"), BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testCalcularValorTotalInvestidoPorConta() {
        ResponseEntity<BigDecimal> response = rest.getForEntity(
            url("/api/platform/investimentos/conta/" + contaId + "/valor-total"), BigDecimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private Investimento criarInvestimentoEntity() {
        Investimento inv = new Investimento();
        inv.setConta(entityManager.getReference(Conta.class, contaId));
        inv.setTipoInvestimento(Investimento.TipoInvestimento.CDB);
        inv.setValorInvestido(new BigDecimal("1000.00"));
        inv.setTaxaRendimento(new BigDecimal("0.10"));
        inv.setDataAplicacao(LocalDateTime.now());
        inv.setDataVencimento(LocalDateTime.now().plusDays(365));
        inv.setStatus(Investimento.StatusInvestimento.ATIVO);
        inv.setRendimentoAtual(BigDecimal.ZERO);
        return inv;
    }
}
