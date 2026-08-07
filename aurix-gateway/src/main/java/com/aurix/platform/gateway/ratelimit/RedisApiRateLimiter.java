package com.aurix.platform.gateway.ratelimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Implementação baseada em Redis do rate limiter (produto), usando INCR + EXPIRE
 * em janela deslizante de 60 segundos por chave.
 */
@Component
@ConditionalOnProperty(prefix = "aurix.gateway.rate-limit", name = "redis-enabled", havingValue = "true")
public class RedisApiRateLimiter implements ApiRateLimiter {

    private static final Logger log = LoggerFactory.getLogger(RedisApiRateLimiter.class);

    private static final Duration JANELA = Duration.ofSeconds(60);
    private static final String PREFIXO_CHAVE = "aurix:ratelimit:";

    private final ReactiveStringRedisTemplate redis;

    public RedisApiRateLimiter(ReactiveStringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
    public Mono<Boolean> tryAcquire(String key, int limitPerMinute) {
        String chave = PREFIXO_CHAVE + key;
        return redis.opsForValue().increment(chave)
                .flatMap(valor -> {
                    if (valor.longValue() == 1L) {
                        return redis.expire(chave, JANELA).thenReturn(true);
                    }
                    return Mono.just(valor.longValue() <= limitPerMinute);
                })
                .onErrorResume(ex -> {
                    log.warn("Falha no rate limit via Redis: {}", ex.getMessage());
                    return Mono.just(true);
                });
    }
}
