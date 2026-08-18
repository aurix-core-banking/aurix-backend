package com.aurix.platform.cambio.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpiStrWebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(SpiStrWebClientConfig.class);

    @Bean(name = "spiWebClient")
    public WebClient spiWebClient(SpiStrProperties properties) {
        SpiStrProperties.SpiConfig spi = properties.getSpi();
        HttpClient httpClient = buildMtlsHttpClient(
                spi.getConnectTimeoutMs(), spi.getReadTimeoutMs(), properties.getCertificados(), true);
        return WebClient.builder()
                .baseUrl(spi.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean(name = "strWebClient")
    public WebClient strWebClient(SpiStrProperties properties) {
        SpiStrProperties.StrConfig str = properties.getStr();
        HttpClient httpClient = buildMtlsHttpClient(
                str.getConnectTimeoutMs(), str.getReadTimeoutMs(), properties.getCertificados(), false);
        return WebClient.builder()
                .baseUrl(str.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private HttpClient buildMtlsHttpClient(int connectTimeoutMs, int readTimeoutMs,
                                            SpiStrProperties.CertConfig certConfig,
                                            boolean isSpi) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofMillis(readTimeoutMs))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeoutMs, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(readTimeoutMs, TimeUnit.MILLISECONDS)));

        if (certConfig != null && certConfig.getKeystorePath() != null
                && !certConfig.getKeystorePath().isBlank()) {
            try {
                SslContext sslContext = buildSslContext(certConfig);
                httpClient = httpClient.secure(sslSpec -> sslSpec.sslContext(sslContext));
                log.info("mTLS {} configurado com keystore: {}", isSpi ? "SPI" : "STR",
                        certConfig.getKeystorePath());
            } catch (Exception e) {
                log.error("Erro ao configurar mTLS {}: {}", isSpi ? "SPI" : "STR", e.getMessage());
                throw new RuntimeException("Falha ao configurar mTLS BACEN", e);
            }
        } else {
            log.warn("mTLS {} NÃO configurado — keystore não definido. " +
                     "Em produção, a conexão com BACEN falhará.", isSpi ? "SPI" : "STR");
        }

        return httpClient;
    }

    private SslContext buildSslContext(SpiStrProperties.CertConfig certConfig) throws Exception {
        // Carregar keystore (certificado do participante BACEN)
        KeyStore keyStore = KeyStore.getInstance(certConfig.getKeystoreType());
        try (InputStream ksIs = new FileInputStream(certConfig.getKeystorePath())) {
            keyStore.load(ksIs, certConfig.getKeystorePassword().toCharArray());
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, certConfig.getKeystorePassword().toCharArray());

        // Carregar truststore (CAs do BACEN)
        KeyStore trustStore = KeyStore.getInstance(certConfig.getTruststoreType());
        try (InputStream tsIs = new FileInputStream(certConfig.getTruststorePath())) {
            trustStore.load(tsIs, certConfig.getTruststorePassword().toCharArray());
        }
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        return SslContextBuilder.forClient()
                .keyManager(kmf)
                .trustManager(tmf)
                .protocols("TLSv1.3", "TLSv1.2")
                .build();
    }
}
