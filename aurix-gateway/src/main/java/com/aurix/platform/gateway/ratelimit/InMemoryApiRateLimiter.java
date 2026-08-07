package com.aurix.platform.gateway.ratelimit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementação em memória do rate limiter, usada em desenvolvimento e testes
 * quando o Redis não está disponível (rate-limit.redis-enabled=false).
 */
@Component
@ConditionalOnProperty(prefix = "aurix.gateway.rate-limit", name = "redis-enabled", havingValue = "false", matchIfMissing = true)
public class InMemoryApiRateLimiter implements ApiRateLimiter {

    private static final long JANELA_MS = 60_000L;

    private final ConcurrentMap<String, Janela> janelas = new ConcurrentHashMap<>();

    @Override
    public Mono<Boolean> tryAcquire(String key, int limitPerMinute) {
        long agora = System.currentTimeMillis();
        Janela janela = janelas.compute(key, (k, atual) -> {
            if (atual == null || agora - atual.inicio >= JANELA_MS) {
                return new Janela(agora, 1);
            }
            atual.contagem++;
            return atual;
        });
        return Mono.just(janela.contagem <= limitPerMinute);
    }

    private static final class Janela {
        private final long inicio;
        private int contagem;

        private Janela(long inicio, int contagem) {
            this.inicio = inicio;
            this.contagem = contagem;
        }
    }
}
