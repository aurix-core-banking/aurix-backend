package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.CardsApplication;
import com.aurix.platform.cards.config.CartoesTestConfig;
import com.aurix.platform.cards.dto.AjustarLimiteRequest;
import com.aurix.platform.cards.dto.EmitirCartaoRequest;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.dto.ProdutoCartaoRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoResponse;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.ProdutoCartaoRepository;
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
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CartoesTestConfig.class)
class LimiteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCartaoRepository produtoCartaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    private RestTemplate rest;
    private Long cartaoId;

    @BeforeEach
    void setUp() {
        cartaoRepository.deleteAll();
        produtoCartaoRepository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate();

        var produtoReq = new ProdutoCartaoRequest();
        produtoReq.setNome("Cartao Black");
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
        var produtoId = produtoResponse.getBody().getId();

        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(1L);
        emitReq.setNomePortador("Joao Silva");
        emitReq.setTipo("CREDITO");
        var emitResponse = rest.postForEntity(url("/emissao"), emitReq, Cartao.class);
        assertThat(emitResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        cartaoId = emitResponse.getBody().getId();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cards" + path;
    }

    @Test
    void deveAjustarLimite() {
        var request = new AjustarLimiteRequest();
        request.setNovoLimite(BigDecimal.valueOf(25000));

        var response = rest.exchange(url("/limites/" + cartaoId), HttpMethod.PUT,
            new HttpEntity<>(request), LimiteCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLimiteTotal()).isEqualByComparingTo(BigDecimal.valueOf(25000));
    }

    @Test
    void deveConsultarLimite() {
        var response = rest.getForEntity(url("/limites/" + cartaoId), LimiteCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLimiteTotal()).isPositive();
    }

    @Test
    void deveBloquearLimite() {
        var response = rest.postForEntity(url("/limites/" + cartaoId + "/bloquear"), null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveDesbloquearLimite() {
        rest.postForEntity(url("/limites/" + cartaoId + "/bloquear"), null, Void.class);

        var response = rest.postForEntity(url("/limites/" + cartaoId + "/desbloquear"), null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
