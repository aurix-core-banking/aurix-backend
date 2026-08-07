package com.aurix.platform.gateway.ratelimit;

import reactor.core.publisher.Mono;

/**
 * Abstração de rate limiting do Gateway.
 */
public interface ApiRateLimiter {

    /**
     * Tenta adquirir uma permissão dentro da janela de 60 segundos.
     *
     * @param key            chave identificadora (api key ou endereço IP)
     * @param limitPerMinute limite de requisições por minuto
     * @return {@code true} se a requisição está dentro do limite
     */
    Mono<Boolean> tryAcquire(String key, int limitPerMinute);
}
