package com.aurix.platform.gateway.filter;

import com.aurix.platform.gateway.config.ApiKeyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Filtro de validação de API key do Gateway.
 *
 * <p>Quando habilitado, exige o header {@code X-API-Key} (se {@code required=true})
 * e valida a chave contra {@link ApiKeyProperties}. Em caso de sucesso, propaga os
 * headers de tenant e plano para os serviços downstream.</p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 40)
@ConditionalOnProperty(prefix = "aurix.gateway.api-key", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ApiKeyWebFilter implements WebFilter {

    private final ApiKeyProperties properties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public ApiKeyWebFilter(ApiKeyProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (isExempto(path)) {
            return chain.filter(exchange);
        }

        String apiKey = exchange.getRequest().getHeaders().getFirst(properties.getHeaderName());
        if (apiKey == null || apiKey.isBlank()) {
            if (properties.isRequired()) {
                return ErroResposta.escrever(exchange, HttpStatus.UNAUTHORIZED, "API key ausente");
            }
            return chain.filter(exchange);
        }

        ApiKeyProperties.ApiKeyEntry entrada = properties.getKeys().get(apiKey);
        if (entrada == null) {
            return ErroResposta.escrever(exchange, HttpStatus.UNAUTHORIZED, "API key inválida");
        }

        ServerHttpRequest requisicaoMutada = exchange.getRequest().mutate()
                .headers(headers -> {
                    headers.set(properties.getTenantHeader(), entrada.getTenantId());
                    headers.set(properties.getPlanHeader(), entrada.getPlan());
                })
                .build();
        return chain.filter(exchange.mutate().request(requisicaoMutada).build());
    }

    private boolean isExempto(String path) {
        List<String> exemptos = properties.getExemptPaths();
        if (exemptos == null) {
            return false;
        }
        return exemptos.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
