package com.aurix.platform.products.integration;

import com.aurix.platform.products.dto.AvaliacaoElegibilidadeResponse;
import com.aurix.platform.products.dto.ProdutoResponse;
import com.aurix.platform.products.dto.RegraElegibilidadeResponse;
import com.aurix.platform.products.dto.TarifaProdutoResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.RegraElegibilidade;
import com.aurix.platform.products.entity.TarifaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProdutoIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        rest = new RestTemplate();
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private ResponseEntity<ProdutoResponse> criarProduto(String codigo) {
        String body = """
            {
              "codigo": "%s",
              "nome": "Consignado Auxílio",
              "descricao": "Empréstimo consignado em folha",
              "tipoProduto": "CONSIGNADO",
              "publicoAlvo": "Servidores públicos",
              "exigenciaMinima": "Vínculo ativo com a fonte pagadora",
              "vigenciaInicio": "%s"
            }
            """.formatted(codigo, LocalDate.now());
        return rest.exchange(url("/api/products/produtos"), HttpMethod.POST,
            new HttpEntity<>(body, headers), ProdutoResponse.class);
    }

    @Test
    void fluxoCompletoDoCatalogo() {
        ResponseEntity<ProdutoResponse> criado = criarProduto("CONSIGNADO_AUX");

        assertThat(criado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ProdutoResponse produto = criado.getBody();
        assertThat(produto).isNotNull();
        assertThat(produto.id()).isNotNull();
        assertThat(produto.codigo()).isEqualTo("CONSIGNADO_AUX");
        assertThat(produto.status()).isEqualTo(Produto.StatusProduto.RASCUNHO);
        assertThat(produto.numeroVersao()).isEqualTo(1);
        Long id = produto.id();

        String tarifaBody = """
            {
              "codigo": "TARIFA_MANUTENCAO",
              "descricao": "Tarifa de manutenção mensal",
              "tipoTarifa": "MANUTENCAO",
              "periodicidade": "MENSAL",
              "valorFixo": 25.00,
              "obrigatoria": true
            }
            """;
        ResponseEntity<TarifaProdutoResponse> tarifa = rest.exchange(
            url("/api/products/produtos/" + id + "/tarifas"), HttpMethod.POST,
            new HttpEntity<>(tarifaBody, headers), TarifaProdutoResponse.class);
        assertThat(tarifa.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(tarifa.getBody().tipoTarifa()).isEqualTo(TarifaProduto.TipoTarifa.MANUTENCAO);

        ResponseEntity<List> tarifas = rest.exchange(
            url("/api/products/produtos/" + id + "/tarifas"), HttpMethod.GET,
            new HttpEntity<>(headers), List.class);
        assertThat(tarifas.getBody()).hasSize(1);

        String regraBody = """
            {
              "tipoRegra": "RENDA_MINIMA",
              "comparador": "MAIOR_IGUAL",
              "valorNumerico": 2000.00,
              "descricao": "Renda mensal mínima de R$ 2.000"
            }
            """;
        ResponseEntity<RegraElegibilidadeResponse> regra = rest.exchange(
            url("/api/products/produtos/" + id + "/elegibilidade"), HttpMethod.POST,
            new HttpEntity<>(regraBody, headers), RegraElegibilidadeResponse.class);
        assertThat(regra.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(regra.getBody().tipoRegra())
            .isEqualTo(RegraElegibilidade.TipoRegra.RENDA_MINIMA);

        String perfilBody = """
            {
              "rendaMensal": 3500.00,
              "idade": 30,
              "score": 750,
              "segmento": "OURO",
              "tipoPessoa": "PF",
              "negativado": false
            }
            """;
        ResponseEntity<AvaliacaoElegibilidadeResponse> avaliacao = rest.exchange(
            url("/api/products/produtos/" + id + "/elegibilidade/avaliar"), HttpMethod.POST,
            new HttpEntity<>(perfilBody, headers), AvaliacaoElegibilidadeResponse.class);
        assertThat(avaliacao.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(avaliacao.getBody().apto()).isTrue();

        ResponseEntity<ProdutoResponse> publicado = rest.exchange(
            url("/api/products/produtos/" + id + "/publicar"), HttpMethod.POST,
            new HttpEntity<>(headers), ProdutoResponse.class);
        assertThat(publicado.getBody().status()).isEqualTo(Produto.StatusProduto.ATIVO);

        ResponseEntity<ProdutoResponse> buscado = rest.exchange(
            url("/api/products/produtos/" + id), HttpMethod.GET,
            new HttpEntity<>(headers), ProdutoResponse.class);
        assertThat(buscado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(buscado.getBody().ativo()).isTrue();

        ResponseEntity<List> lista = rest.exchange(url("/api/products/produtos"), HttpMethod.GET,
            new HttpEntity<>(headers), List.class);
        assertThat(lista.getBody().toString()).contains("CONSIGNADO_AUX");
    }

    @Test
    void criarProdutoDuplicado_deveRetornarErro() {
        criarProduto("CONSIGNADO_DUP");

        org.springframework.web.client.HttpStatusCodeException excecao =
            org.assertj.core.api.Assertions.catchThrowableOfType(() ->
                rest.exchange(url("/api/products/produtos"), HttpMethod.POST,
                    new HttpEntity<>(criarCorpo("CONSIGNADO_DUP"), headers), String.class),
                org.springframework.web.client.HttpStatusCodeException.class);

        assertThat(excecao).isNotNull();
        assertThat(excecao.getStatusCode().value()).isEqualTo(500);
    }

    @Test
    void buscarProdutoInexistente_deveRetornarErro() {
        org.springframework.web.client.HttpStatusCodeException excecao =
            org.assertj.core.api.Assertions.catchThrowableOfType(() ->
                rest.exchange(url("/api/products/produtos/99999"), HttpMethod.GET,
                    new HttpEntity<>(headers), String.class),
                org.springframework.web.client.HttpStatusCodeException.class);

        assertThat(excecao).isNotNull();
        assertThat(excecao.getStatusCode().isError()).isTrue();
    }

    @Test
    void atualizarProduto_deveGerarNovaVersao() {
        ResponseEntity<ProdutoResponse> criado = criarProduto("CONSIGNADO_VERSAO");
        Long id = criado.getBody().id();

        String body = """
            {
              "codigo": "CONSIGNADO_VERSAO",
              "nome": "Consignado Auxílio Premium",
              "descricao": "Com taxas reduzidas",
              "tipoProduto": "CONSIGNADO",
              "publicoAlvo": "Servidores públicos",
              "exigenciaMinima": "Vínculo ativo com a fonte pagadora"
            }
            """;
        ResponseEntity<ProdutoResponse> atualizado = rest.exchange(
            url("/api/products/produtos/" + id + "?autor=tester&changelog=Ajuste"),
            HttpMethod.PUT, new HttpEntity<>(body, headers), ProdutoResponse.class);

        assertThat(atualizado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(atualizado.getBody().numeroVersao()).isEqualTo(2);

        ResponseEntity<List> versoes = rest.exchange(
            url("/api/products/produtos/" + id + "/versoes"), HttpMethod.GET,
            new HttpEntity<>(headers), List.class);
        assertThat(versoes.getBody()).hasSize(2);
    }

    private String criarCorpo(String codigo) {
        return """
            {
              "codigo": "%s",
              "nome": "Consignado Auxílio",
              "descricao": "Empréstimo consignado em folha",
              "tipoProduto": "CONSIGNADO",
              "publicoAlvo": "Servidores públicos",
              "exigenciaMinima": "Vínculo ativo com a fonte pagadora"
            }
            """.formatted(codigo);
    }
}
