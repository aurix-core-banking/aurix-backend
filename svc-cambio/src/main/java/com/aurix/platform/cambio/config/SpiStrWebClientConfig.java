package com.aurix.platform.cambio.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpiStrWebClientConfig {

    @Bean(name = "spiWebClient")
    public WebClient spiWebClient(SpiStrProperties properties) {
        SpiStrProperties.SpiConfig spi = properties.getSpi();
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, spi.getConnectTimeoutMs())
                .responseTimeout(Duration.ofMillis(spi.getReadTimeoutMs()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(spi.getReadTimeoutMs(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(spi.getReadTimeoutMs(), TimeUnit.MILLISECONDS)));
        return WebClient.builder()
                .baseUrl(spi.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean(name = "strWebClient")
    public WebClient strWebClient(SpiStrProperties properties) {
        SpiStrProperties.StrConfig str = properties.getStr();
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, str.getConnectTimeoutMs())
                .responseTimeout(Duration.ofMillis(str.getReadTimeoutMs()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(str.getReadTimeoutMs(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(str.getReadTimeoutMs(), TimeUnit.MILLISECONDS)));
        return WebClient.builder()
                .baseUrl(str.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
