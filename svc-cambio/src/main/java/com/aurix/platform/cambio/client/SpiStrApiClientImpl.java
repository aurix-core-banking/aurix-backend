package com.aurix.platform.cambio.client;

import com.aurix.platform.cambio.config.SpiStrProperties;
import com.aurix.platform.cambio.entity.TransacaoSPI;
import com.aurix.platform.cambio.entity.TransacaoSTR;
import com.aurix.platform.cambio.service.SpiStrIntegrationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class SpiStrApiClientImpl implements SpiStrApiClient {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpiStrApiClientImpl.class);
    private final WebClient spiWebClient;
    private final WebClient strWebClient;
    private final SpiStrProperties properties;

    public SpiStrApiClientImpl(@Qualifier("spiWebClient") WebClient spiWebClient, @Qualifier("strWebClient") WebClient strWebClient, SpiStrProperties properties) {
        this.spiWebClient = spiWebClient;
        this.strWebClient = strWebClient;
        this.properties = properties;
    }

    @Override
    @Retry(name = "spi")
    @CircuitBreaker(name = "spi", fallbackMethod = "fallbackSpi")
    public SpiStrIntegrationService.SpiResult enviarPixSpi(TransacaoSPI transacao) {
        if (!properties.getSpi().isEnabled()) {
            falharSeProducao("SPI");
            return simuladoSpi(transacao);
        }
        if (isMockUrl()) {
            log.info("Usando mock BACEN para SPI PIX (mock URL: {})", properties.getSpi().getUrl());
            return WebClient.builder()
                .baseUrl(properties.getSpi().getUrl())
                .build()
                .post()
                .uri("/api/spi-str/spi/pix")
                .bodyValue(transacao)
                .retrieve()
                .bodyToMono(SpiStrIntegrationService.SpiResult.class)
                .block(Duration.ofMillis(properties.getSpi().getReadTimeoutMs()));
        }
        falharSeProducaoSemCertificado("SPI");
        log.info("Chamada SPI PIX endToEndId={} valor={} ambiente={}", transacao.getEndToEndId(), transacao.getValor(), properties.getEnvironment());
        try {
            String resposta = spiWebClient.post().uri("/api/pix/enviar").contentType(MediaType.APPLICATION_JSON).bodyValue(transacao).retrieve().bodyToMono(String.class).block(Duration.ofMillis(properties.getSpi().getReadTimeoutMs()));
            return parsearRespostaSpi(resposta, transacao.getEndToEndId());
        } catch (WebClientResponseException e) {
            log.warn("Resposta SPI HTTP {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return SpiStrIntegrationService.SpiResult.builder().sucesso(false).codigoRetorno(String.valueOf(e.getStatusCode().value())).mensagem(e.getResponseBodyAsString()).idTransacao(transacao.getEndToEndId()).dataProcessamento(LocalDateTime.now()).build();
        }
    }

    @Override
    @Retry(name = "str")
    @CircuitBreaker(name = "str", fallbackMethod = "fallbackStr")
    public SpiStrIntegrationService.StrResult enviarTedStr(TransacaoSTR transacao) {
        if (!properties.getStr().isEnabled()) {
            falharSeProducao("STR");
            return simuladoStr(transacao);
        }
        falharSeProducaoSemCertificado("STR");
        log.info("Chamada STR TED numeroControle={} valor={} ambiente={}", transacao.getNumeroControle(), transacao.getValor(), properties.getEnvironment());
        return enviarStr(transacao);
    }

    @Override
    @Retry(name = "str")
    @CircuitBreaker(name = "str", fallbackMethod = "fallbackStr")
    public SpiStrIntegrationService.StrResult enviarDocStr(TransacaoSTR transacao) {
        if (!properties.getStr().isEnabled()) {
            falharSeProducao("STR");
            return simuladoStr(transacao);
        }
        falharSeProducaoSemCertificado("STR");
        log.info("Chamada STR DOC numeroControle={} valor={} ambiente={}", transacao.getNumeroControle(), transacao.getValor(), properties.getEnvironment());
        return enviarStr(transacao);
    }

    private SpiStrIntegrationService.StrResult enviarStr(TransacaoSTR transacao) {
        try {
            String resposta = strWebClient.post().uri("/api/str/enviar").contentType(MediaType.APPLICATION_JSON).bodyValue(transacao).retrieve().bodyToMono(String.class).block(Duration.ofMillis(properties.getStr().getReadTimeoutMs()));
            return parsearRespostaStr(resposta, transacao.getNumeroControle());
        } catch (WebClientResponseException e) {
            log.warn("Resposta STR HTTP {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return SpiStrIntegrationService.StrResult.builder().sucesso(false).codigoRetorno(String.valueOf(e.getStatusCode().value())).mensagem(e.getResponseBodyAsString()).numeroControle(transacao.getNumeroControle()).dataLiquidacao(LocalDateTime.now()).build();
        }
    }

    public SpiStrIntegrationService.SpiResult fallbackSpi(TransacaoSPI transacao, Exception e) {
        log.error("Fallback SPI para endToEndId={}: {}", transacao.getEndToEndId(), e.getMessage());
        return SpiStrIntegrationService.SpiResult.builder().sucesso(false).codigoRetorno("ERR").mensagem("Indisponivel: " + e.getMessage()).idTransacao(transacao.getEndToEndId()).dataProcessamento(LocalDateTime.now()).build();
    }

    public SpiStrIntegrationService.StrResult fallbackStr(TransacaoSTR transacao, Exception e) {
        log.error("Fallback STR para numeroControle={}: {}", transacao.getNumeroControle(), e.getMessage());
        return SpiStrIntegrationService.StrResult.builder().sucesso(false).codigoRetorno("ERR").mensagem("Indisponivel: " + e.getMessage()).numeroControle(transacao.getNumeroControle()).dataLiquidacao(LocalDateTime.now()).build();
    }

    /**
     * Em produção, um cliente desabilitado não pode silenciosamente "simular
     * sucesso": isso faria uma transação real parecer liquidada sem nunca ter
     * sido enviada ao BACEN. {@code isProducao()} já existia em
     * {@link com.aurix.platform.cambio.config.SpiStrProperties} mas nunca era
     * checado aqui.
     */
    private void falharSeProducao(String canal) {
        if (properties.isProducao()) {
            throw new IllegalStateException(canal + " está desabilitado em ambiente de produção — recusando simular sucesso.");
        }
    }

    /**
     * Habilitado sem certificado mTLS configurado em produção tentaria uma
     * chamada real e falharia de forma confusa (handshake TLS / NPE) em vez de
     * um erro claro e imediato.
     */
    private void falharSeProducaoSemCertificado(String canal) {
        if (isMockUrl()) return;
        if (properties.isProducao() && !properties.isCertificadoConfigurado()) {
            throw new IllegalStateException(canal + " está habilitado em produção sem certificado mTLS configurado.");
        }
    }

    public boolean isMockUrl() {
        String url = properties.getSpi().getUrl();
        return url != null && (url.contains("localhost") || url.contains("bacen-mock"));
    }

    private SpiStrIntegrationService.SpiResult simuladoSpi(TransacaoSPI transacao) {
        return SpiStrIntegrationService.SpiResult.builder().sucesso(true).codigoRetorno("00").mensagem("Simulado - SPI desabilitado").idTransacao(transacao.getEndToEndId()).dataProcessamento(LocalDateTime.now()).build();
    }

    private SpiStrIntegrationService.StrResult simuladoStr(TransacaoSTR transacao) {
        LocalDateTime liquidacao = transacao.getDataAgendamento() != null ? transacao.getDataAgendamento() : LocalDateTime.now();
        return SpiStrIntegrationService.StrResult.builder().sucesso(true).codigoRetorno("00").mensagem("Simulado - STR desabilitado").numeroControle(transacao.getNumeroControle()).dataLiquidacao(liquidacao).build();
    }

    private SpiStrIntegrationService.SpiResult parsearRespostaSpi(String resposta, String endToEndId) {
        return SpiStrIntegrationService.SpiResult.builder().sucesso(true).codigoRetorno("00").mensagem(resposta != null && !resposta.isBlank() ? resposta : "Liquidacao realizada").idTransacao(endToEndId).dataProcessamento(LocalDateTime.now()).build();
    }

    private SpiStrIntegrationService.StrResult parsearRespostaStr(String resposta, String numeroControle) {
        return SpiStrIntegrationService.StrResult.builder().sucesso(true).codigoRetorno("00").mensagem(resposta != null && !resposta.isBlank() ? resposta : "Ordem aceita para liquidacao").numeroControle(numeroControle).dataLiquidacao(LocalDateTime.now()).build();
    }
}
