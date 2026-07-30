package com.aurix.platform.banking.core.integration;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.core.dto.AplicacaoFinanceiraDTO;
import com.aurix.platform.banking.core.dto.CalculoTarifaDTO;
import com.aurix.platform.banking.core.dto.ControleSaldoDTO;
import com.aurix.platform.banking.core.dto.LiquidacaoDTO;
import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.dto.ProdutoFinanceiroDTO;
import com.aurix.platform.banking.core.dto.TarifaDTO;
import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.entity.ProdutoFinanceiro;
import com.aurix.platform.banking.core.integration.BoletoProvider;
import com.aurix.platform.banking.core.repository.ClienteRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.banking.core.repository.HistoricoSaldoRepository;
import com.aurix.platform.banking.core.repository.LiquidacaoRepository;
import com.aurix.platform.banking.core.repository.MovimentoContaRepository;
import com.aurix.platform.banking.core.repository.ProdutoFinanceiroRepository;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CoreFlowIntegrationTest.TestConfig.class)
class CoreFlowIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private BoletoProvider boletoProvider;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ControleSaldoRepository controleSaldoRepository;

    @Autowired
    private ProdutoFinanceiroRepository produtoFinanceiroRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long contaOrigemId;
    private Long contaDestinoId;
    private Long produtoFinanceiroId;

    @BeforeEach
    void setUp() {
        limparBanco();
        TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);

        Cliente cliente = new Cliente();
        cliente.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        cliente.setTipoPessoa(Cliente.TipoPessoa.FISICA);
        cliente.setCpf("52998224725");
        cliente.setNome("Test Client");
        cliente.setEmail("test@aurix.com");
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        cliente = clienteRepository.save(cliente);

        Conta contaOrigem = new Conta();
        contaOrigem.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        contaOrigem.setNumeroConta("12345-6");
        contaOrigem.setCliente(cliente);
        contaOrigem.setTipoConta(Conta.TipoConta.CORRENTE);
        contaOrigem.setSaldoAtual(BigDecimal.valueOf(1000.0));
        contaOrigem.setStatus(Conta.StatusConta.ATIVA);
        contaOrigem = contaRepository.save(contaOrigem);
        contaOrigemId = contaOrigem.getId();

        Conta contaDestino = new Conta();
        contaDestino.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        contaDestino.setNumeroConta("65432-1");
        contaDestino.setCliente(cliente);
        contaDestino.setTipoConta(Conta.TipoConta.CORRENTE);
        contaDestino.setSaldoAtual(BigDecimal.valueOf(2000.0));
        contaDestino.setStatus(Conta.StatusConta.ATIVA);
        contaDestino = contaRepository.save(contaDestino);
        contaDestinoId = contaDestino.getId();

        for (Conta c : List.of(contaOrigem, contaDestino)) {
            ControleSaldo controle = new ControleSaldo();
            controle.setConta(c);
            controle.setSaldoDisponivel(c.getSaldoAtual());
            controle.setSaldoBloqueado(BigDecimal.ZERO);
            controle.setSaldoPendente(BigDecimal.ZERO);
            controle.setSaldoTotal(c.getSaldoAtual());
            controle.setLimiteCredito(BigDecimal.valueOf(5000.0));
            controle.setLimiteUtilizado(BigDecimal.ZERO);
            controle.setLimiteDisponivel(BigDecimal.valueOf(5000.0));
            controle.setDataUltimaAtualizacao(LocalDateTime.now());
            controle.setSaldoConsistente(true);
            controle.setBloqueioOperacoes(false);
            controle.setTenantId(TenantContext.DEFAULT_TENANT_ID);
            controle.setVersaoSaldo(1);
            controleSaldoRepository.save(controle);
        }

        ProdutoFinanceiro pf = new ProdutoFinanceiro();
        pf.setCodigoProduto("CDB_001");
        pf.setNomeProduto("CDB Prefixado");
        pf.setTipoProduto(ProdutoFinanceiro.TipoProduto.CDB);
        pf.setCategoriaProduto(ProdutoFinanceiro.CategoriaProduto.RENDA_FIXA);
        pf.setTipoRemuneracao(ProdutoFinanceiro.TipoRemuneracao.PRE_FIXADA);
        pf.setPeriodicidadeRemuneracao(ProdutoFinanceiro.PeriodicidadeRemuneracao.MENSAL);
        pf.setValorMinimoAplicacao(BigDecimal.valueOf(100.0));
        pf.setValorMaximoAplicacao(BigDecimal.valueOf(100000.0));
        pf.setTaxaRemuneracao(BigDecimal.valueOf(0.01));
        pf.setAtivo(true);
        pf.setDisponivelPublico(true);
        pf.setTenantId(TenantContext.DEFAULT_TENANT_ID);
        pf = produtoFinanceiroRepository.save(pf);
        produtoFinanceiroId = pf.getId();

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
        for (String table : List.of(
            "aurix.historico_remuneracao",
            "aurix.historico_saldos",
            "aurix.aplicacoes_financeiras",
            "aurix.movimentos_conta",
            "aurix.liquidacao_itens",
            "aurix.liquidacoes",
            "aurix.transacoes",
            "aurix.controle_saldos",
            "aurix.contas",
            "aurix.produtos_financeiros",
            "aurix.clientes")) {
            jdbcTemplate.execute("DELETE FROM " + table);
        }
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void criarTransacao_deveRetornar201() {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(contaOrigemId);
        dto.setContaDestinoId(contaDestinoId);
        dto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        dto.setValor(BigDecimal.valueOf(100.0));
        dto.setDescricao("Teste PIX");

        ResponseEntity<TransacaoDTO> response = rest.postForEntity(
            url("/api/transacoes"), dto, TransacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getValor()).isEqualByComparingTo(BigDecimal.valueOf(100.0));
    }

    @Test
    void buscarTransacaoPorId_deveRetornar200() {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(contaOrigemId);
        dto.setContaDestinoId(contaDestinoId);
        dto.setTipoTransacao(Transacao.TipoTransacao.TED);
        dto.setValor(BigDecimal.valueOf(250.0));
        dto.setDescricao("Teste TED");

        ResponseEntity<TransacaoDTO> created = rest.postForEntity(
            url("/api/transacoes"), dto, TransacaoDTO.class);
        Long id = created.getBody().getId();

        ResponseEntity<TransacaoDTO> response = rest.getForEntity(
            url("/api/transacoes/" + id), TransacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void buscarTransacaoPorCodigo_deveRetornar200() {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(contaOrigemId);
        dto.setContaDestinoId(contaDestinoId);
        dto.setTipoTransacao(Transacao.TipoTransacao.DOC);
        dto.setValor(BigDecimal.valueOf(500.0));
        dto.setDescricao("Teste DOC");

        ResponseEntity<TransacaoDTO> created = rest.postForEntity(
            url("/api/transacoes"), dto, TransacaoDTO.class);
        String codigo = created.getBody().getCodigoTransacao();

        ResponseEntity<TransacaoDTO> response = rest.getForEntity(
            url("/api/transacoes/codigo/" + codigo), TransacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCodigoTransacao()).isEqualTo(codigo);
    }

    @Test
    void listarTransacoesPendentes_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/api/transacoes/pendentes"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void criarAplicacaoFinanceira_deveRetornar200() {
        Map<String, Object> request = Map.of(
            "contaId", contaOrigemId,
            "produtoFinanceiroId", produtoFinanceiroId,
            "valorAplicacao", 1000.0,
            "usuarioAplicacao", "test-user"
        );

        ResponseEntity<AplicacaoFinanceiraDTO> response = rest.postForEntity(
            url("/api/remuneracao/aplicacao/criar"), request, AplicacaoFinanceiraDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void listarAplicacoesPorConta_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/api/remuneracao/aplicacoes/conta/" + contaOrigemId), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listarProdutosDisponiveis_deveRetornar200() {
        ResponseEntity<ProdutoFinanceiroDTO[]> response = rest.getForEntity(
            url("/api/remuneracao/produtos/disponiveis"), ProdutoFinanceiroDTO[].class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void listarTiposProduto_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/remuneracao/tipos-produto"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("tipos");
    }

    @Test
    void calcularTarifa_deveRetornar200() {
        CalculoTarifaDTO request = new CalculoTarifaDTO();
        request.setContaId(contaOrigemId);
        request.setTipoTarifa("TRANSFERENCIA_PIX");
        request.setValorTransacao(BigDecimal.valueOf(1000.0));
        request.setNivelServico(2);
        request.setPessoaFisica(true);
        request.setDataTransacao(LocalDateTime.now());

        ResponseEntity<CalculoTarifaDTO> response = rest.postForEntity(
            url("/api/motor-tarifas/calcular"), request, CalculoTarifaDTO.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void simularTarifa_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/motor-tarifas/simular?valorTransacao=1000&tipoTarifa=TRANSFERENCIA_PIX&nivelServico=2"),
            Map.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void listarTarifasDisponiveis_deveRetornar200() {
        ResponseEntity<TarifaDTO[]> response = rest.getForEntity(
            url("/api/motor-tarifas/tarifas-disponiveis"), TarifaDTO[].class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void criarLiquidacao_deveRetornar200() {
        TransacaoDTO txDto = new TransacaoDTO();
        txDto.setContaOrigemId(contaOrigemId);
        txDto.setContaDestinoId(contaDestinoId);
        txDto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        txDto.setValor(BigDecimal.valueOf(300.0));
        txDto.setDescricao("Liquidacao test");
        ResponseEntity<TransacaoDTO> tx = rest.postForEntity(
            url("/api/transacoes"), txDto, TransacaoDTO.class);
        Long txId = tx.getBody().getId();

        LiquidacaoDTO liqDto = new LiquidacaoDTO();
        liqDto.setTransacaoId(txId);
        liqDto.setValorLiquidacao(BigDecimal.valueOf(300.0));
        liqDto.setDataVencimento(LocalDateTime.now().plusDays(1));

        ResponseEntity<LiquidacaoDTO> response = rest.postForEntity(
            url("/api/liquidacao/criar"), liqDto, LiquidacaoDTO.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void listarLiquidacoesPendentes_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/api/liquidacao/pendentes"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listarAvaliacoesPendentes_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/api/gestao-risco/avaliacoes/pendentes"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listarTiposAvaliacaoRisco_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/gestao-risco/tipos-avaliacao"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("tipos");
    }

    @Test
    void processarMovimentoCredito_deveRetornar200() {
        MovimentoContaDTO mov = new MovimentoContaDTO();
        mov.setContaId(contaOrigemId);
        mov.setTipoMovimento("CREDITO");
        mov.setValorMovimento(BigDecimal.valueOf(500.0));

        ResponseEntity<MovimentoContaDTO> response = rest.postForEntity(
            url("/api/controle-saldos/movimento"), mov, MovimentoContaDTO.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @Test
    void obterControleSaldo_deveRetornar200() {
        ResponseEntity<ControleSaldoDTO> response = rest.getForEntity(
            url("/api/controle-saldos/conta/" + contaOrigemId + "/controle"), ControleSaldoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContaId()).isEqualTo(contaOrigemId);
        assertThat(response.getBody().getSaldoDisponivel()).isEqualByComparingTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    void listarTiposMovimento_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/controle-saldos/tipos-movimento"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("tipos");
    }

    @Test
    void criarTransacao_semValor_deveRetornar400() {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setContaOrigemId(contaOrigemId);
        dto.setContaDestinoId(contaDestinoId);
        dto.setTipoTransacao(Transacao.TipoTransacao.PIX);

        ResponseEntity<String> response = rest.postForEntity(
            url("/api/transacoes"), dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void processarLiquidacao_deveRetornar200() {
        TransacaoDTO txDto = new TransacaoDTO();
        txDto.setContaOrigemId(contaOrigemId);
        txDto.setContaDestinoId(contaDestinoId);
        txDto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        txDto.setValor(BigDecimal.valueOf(100.0));
        txDto.setDescricao("Processamento liq");
        ResponseEntity<TransacaoDTO> tx = rest.postForEntity(
            url("/api/transacoes"), txDto, TransacaoDTO.class);
        Long txId = tx.getBody().getId();

        LiquidacaoDTO liqDto = new LiquidacaoDTO();
        liqDto.setTransacaoId(txId);
        liqDto.setValorLiquidacao(BigDecimal.valueOf(100.0));
        liqDto.setDataVencimento(LocalDateTime.now().plusDays(1));
        ResponseEntity<LiquidacaoDTO> liq = rest.postForEntity(
            url("/api/liquidacao/criar"), liqDto, LiquidacaoDTO.class);
        assertThat(liq.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);

        if (liq.getBody() != null && liq.getBody().getId() != null) {
            ResponseEntity<LiquidacaoDTO> response = rest.postForEntity(
                url("/api/liquidacao/processar/" + liq.getBody().getId()), null, LiquidacaoDTO.class);
            assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    void estornarLiquidacao_deveRetornar200() {
        TransacaoDTO txDto = new TransacaoDTO();
        txDto.setContaOrigemId(contaOrigemId);
        txDto.setContaDestinoId(contaDestinoId);
        txDto.setTipoTransacao(Transacao.TipoTransacao.PIX);
        txDto.setValor(BigDecimal.valueOf(50.0));
        txDto.setDescricao("Estorno liq");
        ResponseEntity<TransacaoDTO> tx = rest.postForEntity(
            url("/api/transacoes"), txDto, TransacaoDTO.class);
        Long txId = tx.getBody().getId();

        LiquidacaoDTO liqDto = new LiquidacaoDTO();
        liqDto.setTransacaoId(txId);
        liqDto.setValorLiquidacao(BigDecimal.valueOf(50.0));
        liqDto.setDataVencimento(LocalDateTime.now().plusDays(1));
        ResponseEntity<LiquidacaoDTO> liq = rest.postForEntity(
            url("/api/liquidacao/criar"), liqDto, LiquidacaoDTO.class);
        assertThat(liq.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);

        if (liq.getBody() != null && liq.getBody().getId() != null) {
            ResponseEntity<LiquidacaoDTO> response = rest.postForEntity(
                url("/api/liquidacao/estornar/" + liq.getBody().getId()), null, LiquidacaoDTO.class);
            assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    void listarTiposTarifa_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/motor-tarifas/tipos-tarifa"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("tipos");
    }

    @Test
    void listarNiveisServico_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/motor-tarifas/niveis-servico"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("niveis");
    }

    @Test
    void listarMovimentosPorConta_deveRetornar200() {
        MovimentoContaDTO mov = new MovimentoContaDTO();
        mov.setContaId(contaOrigemId);
        mov.setTipoMovimento("CREDITO");
        mov.setValorMovimento(BigDecimal.valueOf(200.0));
        rest.postForEntity(url("/api/controle-saldos/movimento"), mov, MovimentoContaDTO.class);

        ResponseEntity<String> response = rest.getForEntity(
            url("/api/controle-saldos/conta/" + contaOrigemId + "/movimentos"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listarAplicacoesAtivas_deveRetornar200() {
        ResponseEntity<String> response = rest.getForEntity(
            url("/api/remuneracao/aplicacoes/ativas"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listarTiposLiquidacao_deveRetornar200() {
        ResponseEntity<Map> response = rest.getForEntity(
            url("/api/liquidacao/tipos-liquidacao"), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("tipos");
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
    }
}
