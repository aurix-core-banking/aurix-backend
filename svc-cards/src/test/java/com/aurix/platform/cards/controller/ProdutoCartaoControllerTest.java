package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.CardsApplication;
import com.aurix.platform.cards.config.CartoesTestConfig;
import com.aurix.platform.cards.dto.ProdutoCartaoRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoResponse;
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
class ProdutoCartaoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoCartaoRepository produtoCartaoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        produtoCartaoRepository.deleteAll();
        TenantContext.setTenantId("test-tenant");
        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/cards" + path;
    }

    @Test
    void deveCriarProdutoCartao() {
        var request = new ProdutoCartaoRequest();
        request.setNome("Cartao Black");
        request.setBandeira("VISA");
        request.setAdquirente("REDE");
        request.setAnuidade(BigDecimal.valueOf(300));
        request.setTaxaJuros(BigDecimal.valueOf(0.15));
        request.setTaxaMora(BigDecimal.valueOf(0.02));
        request.setLimiteMinimo(BigDecimal.valueOf(1000));
        request.setLimiteMaximo(BigDecimal.valueOf(50000));
        request.setProgramaPontos("Multiplus");
        request.setAtivo(true);

        var response = rest.postForEntity(url("/produtos"), request, ProdutoCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isPositive();
        assertThat(response.getBody().getNome()).isEqualTo("Cartao Black");
    }

    @Test
    void deveListarProdutosAtivos() {
        var request = new ProdutoCartaoRequest();
        request.setNome("Cartao Black");
        request.setBandeira("VISA");
        request.setAdquirente("REDE");
        request.setAnuidade(BigDecimal.valueOf(300));
        request.setTaxaJuros(BigDecimal.valueOf(0.15));
        request.setTaxaMora(BigDecimal.valueOf(0.02));
        request.setLimiteMinimo(BigDecimal.valueOf(1000));
        request.setLimiteMaximo(BigDecimal.valueOf(50000));
        request.setProgramaPontos("Multiplus");
        request.setAtivo(true);
        rest.postForEntity(url("/produtos"), request, ProdutoCartaoResponse.class);

        var response = rest.getForEntity(url("/produtos"), ProdutoCartaoResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void deveBuscarProdutoPorId() {
        var request = new ProdutoCartaoRequest();
        request.setNome("Cartao Black");
        request.setBandeira("VISA");
        request.setAdquirente("REDE");
        request.setAnuidade(BigDecimal.valueOf(300));
        request.setTaxaJuros(BigDecimal.valueOf(0.15));
        request.setTaxaMora(BigDecimal.valueOf(0.02));
        request.setLimiteMinimo(BigDecimal.valueOf(1000));
        request.setLimiteMaximo(BigDecimal.valueOf(50000));
        request.setProgramaPontos("Multiplus");
        request.setAtivo(true);
        var created = rest.postForEntity(url("/produtos"), request, ProdutoCartaoResponse.class);
        var id = created.getBody().getId();

        var response = rest.getForEntity(url("/produtos/" + id), ProdutoCartaoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
    }
}
