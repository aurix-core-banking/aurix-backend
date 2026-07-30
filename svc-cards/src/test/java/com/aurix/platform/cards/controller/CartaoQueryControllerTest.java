package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.CardsApplication;
import com.aurix.platform.cards.config.CartoesTestConfig;
import com.aurix.platform.cards.dto.CartaoResponse;
import com.aurix.platform.cards.dto.EmitirCartaoRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(CartoesTestConfig.class)
class CartaoQueryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCartaoRepository produtoCartaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    private RestTemplate rest;
    private Long cartaoId;
    private Long contaId = 1L;

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
        emitReq.setContaId(contaId);
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
    void deveListarCartoesPorStatus() {
        var response = rest.getForEntity(url("/consultas?status=PENDENTE_ATIVACAO"), CartaoResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveBuscarCartoesPorCliente() {
        var response = rest.getForEntity(url("/consultas/cliente/" + contaId), CartaoResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveBuscarCartaoPorId() {
        var response = rest.getForEntity(url("/consultas/" + cartaoId), CartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(cartaoId);
    }
}
