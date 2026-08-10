package com.aurix.platform.contracts.integration;

import com.aurix.platform.contracts.dto.AssinaturaResponse;
import com.aurix.platform.contracts.dto.ContratoResponse;
import com.aurix.platform.contracts.dto.TemplateResponse;
import com.aurix.platform.contracts.entity.Contrato;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ContratoIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate rest;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        rest = new RestTemplate();
        rest.setRequestFactory(new org.springframework.http.client.HttpComponentsClientHttpRequestFactory());
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private ResponseEntity<ContratoResponse> criarContrato(String numero) {
        String body = """
            {
              "numeroContrato": "%s",
              "clienteId": 100,
              "clienteDocumento": "12345678901",
              "tipoContrato": "EMPRESTIMO",
              "valor": 10000.00,
              "prazoMeses": 36,
              "valorParcela": 380.50,
              "taxaJuros": 1.99,
              "termosTexto": "Termos e condições do contrato",
              "dadosJson": "{\\"origem\\":\\"integracao\\"}"
            }
            """.formatted(numero);
        return rest.exchange(url("/api/contracts/contratos"), HttpMethod.POST,
            new HttpEntity<>(body, headers), ContratoResponse.class);
    }

    @Test
    void fluxoCompletoDoContrato() {
        ResponseEntity<ContratoResponse> criado = criarContrato("CTR-2026-0001");

        assertThat(criado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ContratoResponse contrato = criado.getBody();
        assertThat(contrato).isNotNull();
        assertThat(contrato.numeroContrato()).isEqualTo("CTR-2026-0001");
        assertThat(contrato.status()).isEqualTo(Contrato.StatusContrato.RASCUNHO);
        Long id = contrato.id();

        org.springframework.web.client.HttpStatusCodeException duplicado =
            org.assertj.core.api.Assertions.catchThrowableOfType(() ->
                rest.exchange(url("/api/contracts/contratos"), HttpMethod.POST,
                    new HttpEntity<>(criarBody("CTR-2026-0001"), headers), String.class),
                org.springframework.web.client.HttpStatusCodeException.class);
        assertThat(duplicado).isNotNull();
        assertThat(duplicado.getStatusCode().isError()).isTrue();

        String assinantes = """
            [
              {"assinanteTipo": "CLIENTE", "assinanteDocumento": "12345678901", "assinanteNome": "Maria Silva"},
              {"assinanteTipo": "GARANTE", "assinanteDocumento": "98765432100", "assinanteNome": "João Souza"}
            ]
            """;
        ResponseEntity<AssinaturaResponse[]> enviadas = rest.exchange(
            url("/api/contracts/contratos/" + id + "/assinaturas/enviar"), HttpMethod.POST,
            new HttpEntity<>(assinantes, headers), AssinaturaResponse[].class);
        assertThat(enviadas.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(enviadas.getBody()).hasSize(2);

        String registro = """
            {"hashDocumento": "hash-maria", "ip": "127.0.0.1", "userAgent": "chrome"}
            """;
        ResponseEntity<AssinaturaResponse> primeira = rest.exchange(
            url("/api/contracts/contratos/" + id + "/assinaturas/12345678901/registrar"),
            HttpMethod.POST, new HttpEntity<>(registro, headers), AssinaturaResponse.class);
        assertThat(primeira.getBody().assinada()).isTrue();

        ResponseEntity<ContratoResponse> aguardando = rest.exchange(
            url("/api/contracts/contratos/" + id), HttpMethod.GET,
            new HttpEntity<>(headers), ContratoResponse.class);
        assertThat(aguardando.getBody().status())
            .isEqualTo(Contrato.StatusContrato.AGUARDANDO_ASSINATURA);

        ResponseEntity<AssinaturaResponse> segunda = rest.exchange(
            url("/api/contracts/contratos/" + id + "/assinaturas/98765432100/registrar"),
            HttpMethod.POST, new HttpEntity<>(registro, headers), AssinaturaResponse.class);
        assertThat(segunda.getBody().assinada()).isTrue();

        ResponseEntity<ContratoResponse> ativo = rest.exchange(
            url("/api/contracts/contratos/" + id), HttpMethod.GET,
            new HttpEntity<>(headers), ContratoResponse.class);
        assertThat(ativo.getBody().status()).isEqualTo(Contrato.StatusContrato.ATIVO);
        assertThat(ativo.getBody().dataAssinatura()).isNotNull();

        ResponseEntity<ContratoResponse> liquidado = rest.exchange(
            url("/api/contracts/contratos/" + id + "/liquidar"), HttpMethod.PATCH,
            new HttpEntity<>(headers), ContratoResponse.class);
        assertThat(liquidado.getBody().status()).isEqualTo(Contrato.StatusContrato.LIQUIDADO);
    }

    @Test
    void atualizarContratoAtivo_deveRetornarErro() {
        ResponseEntity<ContratoResponse> criado = criarContrato("CTR-2026-0002");
        Long id = criado.getBody().id();
        String assinantes = """
            [{"assinanteTipo": "CLIENTE", "assinanteDocumento": "12345678901", "assinanteNome": "Maria Silva"}]
            """;
        rest.exchange(url("/api/contracts/contratos/" + id + "/assinaturas/enviar"),
            HttpMethod.POST, new HttpEntity<>(assinantes, headers), AssinaturaResponse[].class);
        String registro = """
            {"hashDocumento": "hash", "ip": "127.0.0.1", "userAgent": "chrome"}
            """;
        rest.exchange(url("/api/contracts/contratos/" + id + "/assinaturas/12345678901/registrar"),
            HttpMethod.POST, new HttpEntity<>(registro, headers), AssinaturaResponse.class);

        org.springframework.web.client.HttpStatusCodeException resposta =
            org.assertj.core.api.Assertions.catchThrowableOfType(() ->
                rest.exchange(url("/api/contracts/contratos/" + id), HttpMethod.PUT,
                    new HttpEntity<>(criarBody("CTR-2026-0002"), headers), String.class),
                org.springframework.web.client.HttpStatusCodeException.class);

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatusCode().isError()).isTrue();
    }

    @Test
    void fluxoDeTemplatesEContrato() {
        String templateBody = """
            {
              "codigo": "TEMPLO_EMPRESTIMO",
              "nome": "Contrato de Empréstimo",
              "tipoContrato": "EMPRESTIMO",
              "corpoTexto": "Contrato {{numero}} do tipo {{tipoContrato}} no valor de R$ {{valor}}"
            }
            """;
        ResponseEntity<TemplateResponse> criado = rest.exchange(url("/api/contracts/templates"),
            HttpMethod.POST, new HttpEntity<>(templateBody, headers), TemplateResponse.class);
        assertThat(criado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long templateId = criado.getBody().id();

        ResponseEntity<ContratoResponse> contrato = criarContrato("CTR-2026-0003");

        ResponseEntity<String> documento = rest.exchange(
            url("/api/contracts/templates/" + templateId + "/gerar?contratoId=" + contrato.getBody().id()),
            HttpMethod.POST, new HttpEntity<>(headers), String.class);

        assertThat(documento.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(documento.getBody())
            .contains("CTR-2026-0003")
            .contains("EMPRESTIMO")
            .doesNotContain("{{");

        ResponseEntity<List> templates = rest.exchange(url("/api/contracts/templates"),
            HttpMethod.GET, new HttpEntity<>(headers), List.class);
        assertThat(templates.getBody()).hasSize(1);
    }

    @Test
    void buscarContratoInexistente_deveRetornarErro() {
        org.springframework.web.client.HttpStatusCodeException resposta =
            org.assertj.core.api.Assertions.catchThrowableOfType(() ->
                rest.exchange(url("/api/contracts/contratos/99999"), HttpMethod.GET,
                    new HttpEntity<>(headers), String.class),
                org.springframework.web.client.HttpStatusCodeException.class);

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatusCode().isError()).isTrue();
    }

    private String criarBody(String numero) {
        return """
            {
              "numeroContrato": "%s",
              "clienteId": 100,
              "tipoContrato": "EMPRESTIMO",
              "valor": 10000.00,
              "prazoMeses": 36
            }
            """.formatted(numero);
    }
}
