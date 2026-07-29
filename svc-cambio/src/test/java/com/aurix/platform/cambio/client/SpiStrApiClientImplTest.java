package com.aurix.platform.cambio.client;

import com.aurix.platform.cambio.config.SpiStrProperties;
import com.aurix.platform.cambio.entity.TransacaoSPI;
import com.aurix.platform.cambio.entity.TransacaoSTR;
import com.aurix.platform.cambio.service.SpiStrIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SpiStrApiClientImplTest {

    @Mock
    private WebClient spiWebClient;

    @Mock
    private WebClient strWebClient;

    private SpiStrProperties properties;
    private SpiStrApiClientImpl client;

    @BeforeEach
    void setUp() {
        properties = new SpiStrProperties();
        client = new SpiStrApiClientImpl(spiWebClient, strWebClient, properties);
    }

    @Test
    void enviarPixSpiSimulaComSucessoQuandoDesabilitadoForaDeProducao() {
        properties.setEnvironment("homologacao");
        properties.getSpi().setEnabled(false);

        SpiStrIntegrationService.SpiResult resultado = client.enviarPixSpi(new TransacaoSPI());

        assertTrue(resultado.isSucesso());
    }

    @Test
    void enviarPixSpiFalhaRapidoQuandoDesabilitadoEmProducao() {
        // SPI desabilitado em produção não pode silenciosamente "simular sucesso":
        // isso faria uma transação PIX real parecer liquidada sem nunca ter sido
        // enviada ao BACEN. isProducao()/isCertificadoConfigurado() já existiam em
        // SpiStrProperties mas nunca eram checados aqui.
        properties.setEnvironment("producao");
        properties.getSpi().setEnabled(false);

        assertThrows(IllegalStateException.class, () -> client.enviarPixSpi(new TransacaoSPI()));
    }

    @Test
    void enviarTedStrSimulaComSucessoQuandoDesabilitadoForaDeProducao() {
        properties.setEnvironment("homologacao");
        properties.getStr().setEnabled(false);

        SpiStrIntegrationService.StrResult resultado = client.enviarTedStr(new TransacaoSTR());

        assertTrue(resultado.isSucesso());
    }

    @Test
    void enviarTedStrFalhaRapidoQuandoDesabilitadoEmProducao() {
        properties.setEnvironment("producao");
        properties.getStr().setEnabled(false);

        assertThrows(IllegalStateException.class, () -> client.enviarTedStr(new TransacaoSTR()));
    }

    @Test
    void enviarPixSpiFalhaRapidoQuandoHabilitadoEmProducaoSemCertificadoConfigurado() {
        // Habilitado sem certificado em produção tentaria uma chamada mTLS real e
        // falharia de forma confusa (handshake TLS) em vez de um erro claro.
        properties.setEnvironment("producao");
        properties.getSpi().setEnabled(true);

        assertThrows(IllegalStateException.class, () -> client.enviarPixSpi(new TransacaoSPI()));
    }

    @Test
    void shouldDetectMockUrlForLocalhost() {
        SpiStrProperties props = new SpiStrProperties();
        props.getSpi().setUrl("http://localhost:8095");
        SpiStrApiClientImpl c = new SpiStrApiClientImpl(null, null, props);
        assertThat(c.isMockUrl()).isTrue();
    }

    @Test
    void shouldDetectMockUrlForBacenMock() {
        SpiStrProperties props = new SpiStrProperties();
        props.getSpi().setUrl("http://bacen-mock:8095");
        SpiStrApiClientImpl c = new SpiStrApiClientImpl(null, null, props);
        assertThat(c.isMockUrl()).isTrue();
    }

    @Test
    void shouldDetectNonMockUrl() {
        SpiStrProperties props = new SpiStrProperties();
        props.getSpi().setUrl("https://spi-homologacao.bcb.gov.br");
        SpiStrApiClientImpl c = new SpiStrApiClientImpl(null, null, props);
        assertThat(c.isMockUrl()).isFalse();
    }
}
