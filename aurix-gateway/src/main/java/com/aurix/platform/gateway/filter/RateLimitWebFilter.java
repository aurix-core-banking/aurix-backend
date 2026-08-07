package com.aurix.platform.gateway.filter;

import com.aurix.platform.gateway.config.ApiKeyProperties;
import com.aurix.platform.gateway.config.RateLimitProperties;
import com.aurix.platform.gateway.ratelimit.ApiRateLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Filtro de rate limiting do Gateway.
 *
 * <p>O limite é definido pelo plano da API key (header {@code X-Plan}) quando presente;
 * caso contrário, pelo limite global configurado. A chave do contador é a API key, ou o
 * endereço IP do cliente quando não há API key.</p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 50)
@ConditionalOnProperty(prefix = "aurix.gateway.rate-limit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RateLimitWebFilter implements WebFilter {

    private final ApiRateLimiter rateLimiter;
    private final RateLimitProperties properties;
    private final ApiKeyProperties apiKeyProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public RateLimitWebFilter(ApiRateLimiter rateLimiter,
                              RateLimitProperties properties,
                              ApiKeyProperties apiKeyProperties) {
        this.rateLimiter = rateLimiter;
        this.properties = properties;
        this.apiKeyProperties = apiKeyProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (isExempto(path)) {
            return chain.filter(exchange);
        }

        String chave = resolverChave(exchange);
        int limite = resolverLimite(exchange);

        return rateLimiter.tryAcquire(chave, limite).flatMap(permitido -> {
            if (!permitido) {
                exchange.getResponse().getHeaders().add(HttpHeaders.RETRY_AFTER, "60");
                return ErroResposta.escrever(exchange, HttpStatus.TOO_MANY_REQUESTS,
                        "Limite de requisições excedido. Tente novamente em 60 segundos.");
            }
            return chain.filter(exchange);
        });
    }

    private String resolverChave(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String apiKey = request.getHeaders().getFirst(apiKeyProperties.getHeaderName());
        if (apiKey != null && !apiKey.isBlank()) {
            return apiKey;
        }
        String forwarded = request.getHeaders().getFirst("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return "ip:" + forwarded.split(",")[0].trim();
        }
        InetSocketAddress endereco = request.getRemoteAddress();
        return endereco != null ? "ip:" + endereco.getAddress().getHostAddress() : "desconhecido";
    }

    private int resolverLimite(ServerWebExchange exchange) {
        String plano = exchange.getRequest().getHeaders().getFirst(apiKeyProperties.getPlanHeader());
        if (plano != null && !plano.isBlank()) {
            return apiKeyProperties.getLimitForPlan(plano);
        }
        return properties.getRequestsPerMinute();
    }

    private boolean isExempto(String path) {
        List<String> exemptos = apiKeyProperties.getExemptPaths();
        if (exemptos == null) {
            return false;
        }
        return exemptos.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
