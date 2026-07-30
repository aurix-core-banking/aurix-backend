package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.CardsApplication;
import com.aurix.platform.cards.config.CartoesTestConfig;
import com.aurix.platform.cards.dto.BloquearCartaoRequest;
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
class EmissaoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCartaoRepository produtoCartaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    private RestTemplate rest;
    private Long produtoId;

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

        var response = rest.postForEntity(url("/produtos"), produtoReq, ProdutoCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        produtoId = response.getBody().getId();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cards" + path;
    }

    @Test
    void deveEmitirCartao() {
        var request = new EmitirCartaoRequest();
        request.setProdutoId(produtoId);
        request.setContaId(1L);
        request.setNomePortador("Joao Silva");
        request.setTipo("CREDITO");

        var response = rest.postForEntity(url("/emissao"), request, Cartao.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
    }

    @Test
    void deveBloquearCartao() {
        var emitReq = new EmitirCartaoRequest();
        emitReq.setProdutoId(produtoId);
        emitReq.setContaId(1L);
        emitReq.setNomePortador("Joao Silva");
        emitReq.setTipo("CREDITO");
        var emitResponse = rest.postForEntity(url("/emissao"), emitReq, Cartao.class);
        assertThat(emitResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var cartaoId = emitResponse.getBody().getId();

        var bloqueioReq = new BloquearCartaoRequest();
        bloqueioReq.setMotivo("Perda");

        var response = rest.postForEntity(url("/emissao/" + cartaoId + "/bloquear"), bloqueioReq, Cartao.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus().name()).isEqualTo("BLOQUEADO");
    }

  :35:28.735) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.737) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.737) Debug :  File:() Line : (0) ThreadID: (8528) queryRootNode count =  1
(2026-07-29 21:35:28.737) Debug :  File:() Line : (0) ThreadID: (8528) queryRootNode rootName =  "volume"
(2026-07-29 21:35:28.741) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.743) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.746) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.748) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.750) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.751) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.751) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.753) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.753) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.755) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:28.757) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-2