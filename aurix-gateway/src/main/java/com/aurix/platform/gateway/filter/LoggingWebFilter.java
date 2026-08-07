package com.aurix.platform.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Filtro de log estruturado do Gateway.
 *
 * <p>Gera o header {@code X-Request-Id} quando ausente e registra method, path,
 * status e duração de cada requisição em formato estruturado (chave=valor).</p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class LoggingWebFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingWebFilter.class);

    private static final String REQUEST_ID = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long inicio = System.nanoTime();
        String requestIdHeader = exchange.getRequest().getHeaders().getFirst(REQUEST_ID);
        final String requestId;
        if (requestIdHeader == null || requestIdHeader.isBlank()) {
            requestId = UUID.randomUUID().toString();
        } else {
            requestId = requestIdHeader;
        }

        ServerHttpRequest requisicao = exchange.getRequest().mutate()
                .header(REQUEST_ID, requestId)
                .build();
        exchange.getResponse().getHeaders().set(REQUEST_ID, requestId);

        ServerWebExchange exchangeMutado = exchange.mutate().request(requisicao).build();

        return chain.filter(exchangeMutado).doFinally(sinal -> {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000L;
            HttpStatusCode status = exchangeMutado.getResponse().getStatusCode();
            log.info("request_id={} method={} path={} status={} duration_ms={}",
                    requestId,
                    exchangeMutado.getRequest().getMethod(),
                    exchangeMutado.getRequest().getPath().value(),
                    status == null ? "-" : status.value(),
                    duracaoMs);
        });
    }
}
