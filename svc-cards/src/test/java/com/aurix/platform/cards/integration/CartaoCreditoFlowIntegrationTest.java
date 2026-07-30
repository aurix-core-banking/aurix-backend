package com.aurix.platform.cards.integration;

import com.aurix.platform.cards.CardsApplication;
import com.aurix.platform.cards.config.CartoesTestConfig;
import com.aurix.platform.cards.dto.AjustarLimiteRequest;
import com.aurix.platform.cards.dto.AutorizarTransacaoRequest;
import com.aurix.platform.cards.dto.BloquearCartaoRequest;
import com.aurix.platform.cards.dto.EmitirCartaoRequest;
import com.aurix.platform.cards.dto.FaturaDetalhadaResponse;
import com.aurix.platform.cards.dto.FaturaResponse;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.dto.PagarFaturaRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoResponse;
import com.aurix.platform.cards.dto.CartaoResponse;
import com.aurix.platform.cards.dto.TransacaoResponse;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Fatura;
import com.aurix.platform.cards.entity.TransacaoCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.FaturaRepository;
import com.aurix.platform.cards.repository.LancamentoFaturaRepository;
import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import com.aurix.platform.cards.repository.ProdutoCartaoRepository;
import com.aurix.platform.cards.repository.TransacaoCartaoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CartoesTestConfig.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.hbm2ddl.create_namespaces=true"
})
class CartaoCreditoFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCartaoRepository produtoCartaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private LimiteCartaoRepository limiteCartaoRepository;
    @Autowired
    private TransacaoCartaoRepository transacaoCartaoRepository;
    @Autowired
    private FaturaRepository faturaRepository;
    @Autowired
    private LancamentoFaturaRepository lancamentoFaturaRepository;

    private RestTemplate rest;
    private Long cartaoId;
    private Long produtoId;
    private static final Long CONTA_ID = 1L;

    @BeforeEach
    void setUp() {
        transacaoCartaoRepository.deleteAll();
        lancamentoFaturaRepository.deleteAll();
        faturaRepository.deleteAll();
        limiteCartaoRepository.deleteAll();
        cartaoRepository.deleteAll();
        produtoCartaoRepository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate();

        var produtoReq = new ProdutoCartaoRequest();
        produtoReq.setNome("Cartao Black Visa");
        produtoReq.setBandeira("VISA");
        produtoReq.setAdquirente("REDE");
        produtoReq.setAnuidade(BigDecimal.valueOf(300));
        produtoReq.setTaxaJuros(BigDecimal.valueOf(0.15));
        produtoReq.setTaxaMora(BigDecimal.valueOf(0.02));
        produtoReq.setLimiteMinimo(BigDecimal.valueOf(1000));
        produtoReq.setLimiteMaximo(BigDecimal.valueOf(50000));
        produtoReq.setProgramaPontos("Multiplus");
        produtoReq.setAtivo(true);
        var produtoResponse = rest.postForEntity(url("/produtos"), produtoReq, ProdutoCartaoResponse.class);
        assertThat(produtoResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        produtoId = Objects.requireNonNull(produtoResponse.getBody()).getId();

        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(CONTA_ID);
        emitReq.setNomePortador("Joao Silva");
        emitReq.setTipo("CREDITO");
        var emitResponse = rest.postForEntity(url("/emissao"), emitReq, Cartao.class);
        assertThat(emitResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        cartaoId = Objects.requireNonNull(emitResponse.getBody()).getId();

        rest.postForEntity(url("/emissao/" + cartaoId + "/ativar"), null, Cartao.class);
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cards" + path;
    }

    @Test
    void testCriarCartao() {
        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(2L);
        emitReq.setNomePortador("Maria Santos");
        emitReq.setTipo("CREDITO");

        var response = rest.postForEntity(url("/emissao"), emitReq, Cartao.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.PENDENTE_ATIVACAO);

        Cartao saved = cartaoRepository.findById(response.getBody().getId()).orElseThrow();
        assertThat(saved.getNumeroCartao()).isNotBlank();
        assertThat(saved.getNumeroCartaoMascarado()).startsWith("****");
        assertThat(saved.getCvv()).isNotBlank();
    }

    @Test
    void testConsultarCartao() {
        var response = rest.getForEntity(url("/consultas/" + cartaoId), CartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(cartaoId);
        assertThat(response.getBody().getNomePortador()).isEqualTo("Joao Silva");
        assertThat(response.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.ATIVO.name());
        assertThat(response.getBody().getNumeroCartaoMascarado()).startsWith("****");
    }

    @Test
    void testListarCartoes() {
        var response = rest.getForEntity(url("/consultas/cliente/" + CONTA_ID), CartaoResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);

        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(CONTA_ID);
        emitReq.setNomePortador("Segundo Cartao");
        emitReq.setTipo("CREDITO");
        rest.postForEntity(url("/emissao"), emitReq, Cartao.class);

        var response2 = rest.getForEntity(url("/consultas/cliente/" + CONTA_ID), CartaoResponse[].class);
        assertThat(response2.getBody()).hasSize(2);
    }

    @Test
    void testBloquearDesbloquearCartao() {
        var bloqueioReq = new BloquearCartaoRequest();
        bloqueioReq.setMotivo("Roubo");

        var blockResponse = rest.postForEntity(url("/emissao/" + cartaoId + "/bloquear"), bloqueioReq, Cartao.class);
        assertThat(blockResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(blockResponse.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.BLOQUEADO);
        assertThat(blockResponse.getBody().getMotivoBloqueio()).isEqualTo("Roubo");

        var unblockResponse = rest.postForEntity(url("/emissao/" + cartaoId + "/ativar"), null, Cartao.class);
        assertThat(unblockResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(unblockResponse.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.ATIVO);
    }

    @Test
    void testAlterarLimite() {
        var request = new AjustarLimiteRequest();
        request.setNovoLimite(BigDecimal.valueOf(25000));

        var response = rest.exchange(url("/limites/" + cartaoId), HttpMethod.PUT,
            new HttpEntity<>(request), LimiteCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLimiteTotal()).isEqualByComparingTo(BigDecimal.valueOf(25000));
        assertThat(response.getBody().getLimiteDisponivel()).isEqualByComparingTo(BigDecimal.valueOf(25000));
    }

    @Test
    void testCartaoInexistente() {
        assertThrows(HttpServerErrorException.class, () ->
            rest.getForEntity(url("/consultas/99999"), CartaoResponse.class));
    }

    @Test
    void testFecharFatura() {
        var response = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoFatura()).isNotBlank();
        assertThat(response.getBody().getStatus()).isEqualTo(Fatura.StatusFatura.FECHADA.name());
    }

    @Test
    void testConsultarFaturaAberta() {
        var fecharResponse = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        var faturaId = Objects.requireNonNull(fecharResponse.getBody()).getId();

        var response = rest.getForEntity(url("/faturas/" + faturaId), FaturaDetalhadaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoFatura()).isNotBlank();
        assertThat(response.getBody().getLancamentos()).isNotEmpty();
    }

    @Test
    void testListarFaturas() {
        rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);

        var response = rest.getForEntity(url("/faturas?cartaoId=" + cartaoId), FaturaResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void testPagarFatura() {
        var authReq = new AutorizarTransacaoRequest();
        authReq.setCartaoId(cartaoId);
        authReq.setValor(BigDecimal.valueOf(500));
        authReq.setEstabelecimento("Loja Teste");
        authReq.setModo("CREDITO");
        var authResponse = rest.postForEntity(url("/transacoes/autorizar"), authReq, TransacaoResponse.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        var fecharResponse = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        var faturaId = Objects.requireNonNull(fecharResponse.getBody()).getId();

        var pagamentoReq = new PagarFaturaRequest();
        pagamentoReq.setValorPagamento(BigDecimal.valueOf(500));

        var response = rest.postForEntity(url("/faturas/" + faturaId + "/pagar"), pagamentoReq, FaturaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getValorPago()).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(response.getBody().getValorPendente()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.getBody().getStatus()).isEqualTo(Fatura.StatusFatura.PAGA.name());
    }

    @Test
    void testPagarFaturaParcial() {
        var authReq = new AutorizarTransacaoRequest();
        authReq.setCartaoId(cartaoId);
        authReq.setValor(BigDecimal.valueOf(1000));
        authReq.setEstabelecimento("Loja Teste");
        authReq.setModo("CREDITO");
        rest.postForEntity(url("/transacoes/autorizar"), authReq, TransacaoResponse.class);

        var fecharResponse = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        var faturaId = Objects.requireNonNull(fecharResponse.getBody()).getId();

        var pagamentoReq = new PagarFaturaRequest();
        pagamentoReq.setValorPagamento(BigDecimal.valueOf(100));

        var response = rest.postForEntity(url("/faturas/" + faturaId + "/pagar"), pagamentoReq, FaturaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getValorPago()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(response.getBody().getValorPendente()).isEqualByComparingTo(BigDecimal.valueOf(900));
        assertThat(response.getBody().getStatus()).isEqualTo(Fatura.StatusFatura.FECHADA.name());
    }

    @Test
    void testAutorizarTransacao() {
        var request = new AutorizarTransacaoRequest();
        request.setCartaoId(cartaoId);
        request.setValor(BigDecimal.valueOf(150));
        request.setEstabelecimento("Supermercado");
        request.setModo("CREDITO");

        var response = rest.postForEntity(url("/transacoes/autorizar"), request, TransacaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("AUTORIZADA");
        assertThat(response.getBody().getCodigoTransacao()).startsWith("TXN-");
    }

    @Test
    void testConsultarTransacao() {
        var authReq = new AutorizarTransacaoRequest();
        authReq.setCartaoId(cartaoId);
        authReq.setValor(BigDecimal.valueOf(200));
        authReq.setEstabelecimento("Posto de Gasolina");
        authReq.setModo("CREDITO");
        var authResponse = rest.postForEntity(url("/transacoes/autorizar"), authReq, TransacaoResponse.class);
        var transacaoId = Objects.requireNonNull(authResponse.getBody()).getId();

        var saved = transacaoCartaoRepository.findById(transacaoId).orElseThrow();
        assertThat(saved.getId()).isEqualTo(transacaoId);
        assertThat(saved.getStatus()).isEqualTo(TransacaoCartao.StatusTransacao.AUTORIZADA);
        assertThat(saved.getValor()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void testEstornarTransacao() {
        var authReq = new AutorizarTransacaoRequest();
        authReq.setCartaoId(cartaoId);
        authReq.setValor(BigDecimal.valueOf(75));
        authReq.setEstabelecimento("Restaurante");
        authReq.setModo("CREDITO");
        var authResponse = rest.postForEntity(url("/transacoes/autorizar"), authReq, TransacaoResponse.class);
        var transacaoId = Objects.requireNonNull(authResponse.getBody()).getId();

        var estornoResponse = rest.postForEntity(url("/transacoes/" + transacaoId + "/estornar"), null, TransacaoResponse.class);
        assertThat(estornoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(estornoResponse.getBody().getStatus()).isEqualTo("ESTORNADA");

        var saved = transacaoCartaoRepository.findById(transacaoId).orElseThrow();
        assertThat(saved.getStatus()).isEqualTo(TransacaoCartao.StatusTransacao.ESTORNADA);
    }

    @Test
    void testListarExtrato() {
        var authReq1 = new AutorizarTransacaoRequest();
        authReq1.setCartaoId(cartaoId);
        authReq1.setValor(BigDecimal.valueOf(50));
        authReq1.setEstabelecimento("Loja A");
        authReq1.setModo("CREDITO");
        rest.postForEntity(url("/transacoes/autorizar"), authReq1, TransacaoResponse.class);

        var authReq2 = new AutorizarTransacaoRequest();
        authReq2.setCartaoId(cartaoId);
        authReq2.setValor(BigDecimal.valueOf(100));
        authReq2.setEstabelecimento("Loja B");
        authReq2.setModo("CREDITO");
        rest.postForEntity(url("/transacoes/autorizar"), authReq2, TransacaoResponse.class);

        var transacoes = transacaoCartaoRepository.findByCartaoId(cartaoId);
        assertThat(transacoes).hasSize(2);
    }

    @Test
    void testCompraParcelada() {
        var response = rest.postForEntity(
            url("/" + cartaoId + "/transacao?valor=1200&estabelecimento=Loja Parcelada&tipo=PARCELAMENTO"),
            null, TransacaoCartao.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(TransacaoCartao.StatusTransacao.AUTORIZADA);
        assertThat(response.getBody().getTipoTransacao()).isEqualTo(TransacaoCartao.TipoTransacao.PARCELAMENTO);
    }

    @Test
    void testCalcularParcelas() {
        var response = rest.postForEntity(
            url("/" + cartaoId + "/transacao?valor=600&estabelecimento=Loja Parcelas&tipo=COMPRA_CREDITO"),
            null, TransacaoCartao.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTipoTransacao()).isEqualTo(TransacaoCartao.TipoTransacao.COMPRA_CREDITO);
        assertThat(response.getBody().getValor()).isEqualByComparingTo(BigDecimal.valueOf(600));
    }

    @Test
    void testFlowCompleto() {
        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(3L);
        emitReq.setNomePortador("Flow Test");
        emitReq.setTipo("CREDITO");
        var emitResp = rest.postForEntity(url("/emissao"), emitReq, Cartao.class);
        Long novoCartaoId = Objects.requireNonNull(emitResp.getBody()).getId();
        assertThat(emitResp.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.PENDENTE_ATIVACAO);

        rest.postForEntity(url("/emissao/" + novoCartaoId + "/ativar"), null, Cartao.class);

        var consulta = rest.getForEntity(url("/consultas/" + novoCartaoId), CartaoResponse.class);
        assertThat(consulta.getBody().getStatus()).isEqualTo(Cartao.StatusCartao.ATIVO.name());

        var authReq = new AutorizarTransacaoRequest();
        authReq.setCartaoId(novoCartaoId);
        authReq.setValor(BigDecimal.valueOf(300));
        authReq.setEstabelecimento("Compra Flow");
        authReq.setModo("CREDITO");
        var authResp = rest.postForEntity(url("/transacoes/autorizar"), authReq, TransacaoResponse.class);
        Long transacaoId = Objects.requireNonNull(authResp.getBody()).getId();
        assertThat(authResp.getBody().getStatus()).isEqualTo("AUTORIZADA");

        var fecharResp = rest.postForEntity(url("/faturas/fechar?cartaoId=" + novoCartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        Long faturaId = Objects.requireNonNull(fecharResp.getBody()).getId();
        assertThat(fecharResp.getBody().getStatus()).isEqualTo(Fatura.StatusFatura.FECHADA.name());

        var faturaDet = rest.getForEntity(url("/faturas/" + faturaId), FaturaDetalhadaResponse.class);
        assertThat(faturaDet.getBody().getLancamentos()).isNotEmpty();

        var pagamentoReq = new PagarFaturaRequest();
        pagamentoReq.setValorPagamento(BigDecimal.valueOf(300));
        var pagResp = rest.postForEntity(url("/faturas/" + faturaId + "/pagar"), pagamentoReq, FaturaResponse.class);
        assertThat(pagResp.getBody().getStatus()).isEqualTo(Fatura.StatusFatura.PAGA.name());

        var estornoResp = rest.postForEntity(url("/transacoes/" + transacaoId + "/estornar"), null, TransacaoResponse.class);
        assertThat(estornoResp.getBody().getStatus()).isEqualTo("ESTORNADA");
    }
}
