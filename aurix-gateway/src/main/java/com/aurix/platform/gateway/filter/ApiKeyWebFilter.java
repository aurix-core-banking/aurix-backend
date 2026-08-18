package com.aurix.platform.gateway.filter;

import com.aurix.platform.gateway.config.ApiKeyProperties;
import com.aurix.platform.shared.entity.ApiKey;
import com.aurix.platform.shared.repository.ApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

/**
 * Filtro de validação de API key do Gateway.
 *
 * 1. Tenta validação via DB (produção) — hash SHA-256, anti-timing attack
 * 2. Fallback para YAML (dev)
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 40)
@ConditionalOnProperty(prefix = "aurix.gateway.api-key", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ApiKeyWebFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiKeyWebFilter.class);

    private final ApiKeyProperties properties;
    private final ApiKeyRepository apiKeyRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private volatile boolean dbAvailable = true;

    public ApiKeyWebFilter(ApiKeyProperties properties, ApiKeyRepository apiKeyRepository) {
        this.properties = properties;
        this.apiKeyRepository = apiKeyRepository;
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

        // Validação DB-backed com hash SHA-256
        return Mono.fromCallable(() -> validarApiKey(apiKey))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(resultado -> {
                    if (resultado == null) {
                        return ErroResposta.escrever(exchange, HttpStatus.UNAUTHORIZED, "API key inválida");
                    }
                    ServerHttpRequest req = exchange.getRequest().mutate()
                            .headers(headers -> {
                                headers.set(properties.getTenantHeader(), resultado.tenantId());
                                headers.set(properties.getPlanHeader(), resultado.plano());
                            }).build();
                    return chain.filter(exchange.mutate().request(req).build());
                });
    }

    private ApiKeyValidationResult validarApiKey(String key) {
        // 1. Tentar DB
        if (dbAvailable) {
            try {
                String hash = ApiKey.hashKey(key);
                Optional<ApiKey> opt = apiKeyRepository.findByKeyHash(hash);
                if (opt.isPresent()) {
                    ApiKey apiKey = opt.get();
                    if (apiKey.isAtivo() && !apiKey.isExpirado()) {
                        apiKey.registrarUso();
                        apiKeyRepository.save(apiKey);
                        return new ApiKeyValidationResult(apiKey.getTenantId(), apiKey.getPlano());
                    }
                    log.warn("API key inativa/expirada: prefixo={}", apiKey.getPrefixo());
                    return null;
                }
            } catch (Exception e) {
                log.warn("Tabela api_keys não disponível, usando modo YAML: {}", e.getMessage());
                dbAvailable = false;
            }
        }

        // 2. Fallback YAML
        ApiKeyProperties.ApiKeyEntry entrada = properties.getKeys().get(key);
        if (entrada == null) {
            return null;
        }
        return new ApiKeyValidationResult(entrada.getTenantId(), entrada.getPlan());
    }

    private boolean isExempto(String path) {
        List<String> exemptos = properties.getExemptPaths();
        if (exemptos == null) return false;
        return exemptos.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private record ApiKeyValidationResult(String tenantId, String plano) {}
}
