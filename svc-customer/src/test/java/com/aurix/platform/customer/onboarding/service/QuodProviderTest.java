package com.aurix.platform.customer.onboarding.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 9999)
class QuodProviderTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void deveConsultarScoreComSucesso() {
        stubFor(post(urlEqualTo("/bureau/quod"))
            .withRequestBody(containing("52998224725"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {"score": 620, "situacao": "REGULAR", "mensagem": "OK"}
                    """)));

        QuodProvider provider = new QuodProvider(restTemplate,
            "http://localhost:9999/bureau/quod", "test-key");

        BureauService.ResultadoBureau result = provider.consultar("52998224725");

        assertThat(result.score()).isEqualTo(620);
        assertThat(result.situacao()).isEqualTo("REGULAR");
    }

    @Test
    void deveLancarExcecaoQuandoApiRetornaErro() {
        stubFor(post(urlEqualTo("/bureau/quod"))
            .willReturn(aResponse().withStatus(500)));

        QuodProvider provider = new QuodProvider(restTemplate,
            "http://localhost:9999/bureau/quod", "test-key");

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
            () -> provider.consultar("52998224725"));
    }
}
