package com.aurix.platform.shared.cache;

import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.dto.TransacaoDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Serviço de cache compartilhado entre módulos.
 *
 * Gerencia cache Redis para dados compartilhados.
 */
@Service
public class SharedCacheService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SharedCacheService.class);
    /**
     * TTL padrão para clientes: 1 hora.
     */
    private static final int TTL_CLIENTE_HORAS = 1;
    /**
     * TTL padrão para contas: 30 minutos.
     */
    private static final int TTL_CONTA_MINUTOS = 30;
    /**
     * TTL padrão para transações: 15 minutos.
     */
    private static final int TTL_TRANSACAO_MINUTOS = 15;
    /**
     * TTL padrão para configurações: 24 horas.
     */
    private static final int TTL_CONFIG_HORAS = 24;
    /**
     * TTL padrão para tarifas: 1 hora.
     */
    private static final int TTL_TARIFA_HORAS = 1;
    /**
     * Template do Redis para operações de cache.
     */
    private final RedisTemplate<String, Object> redisTemplate;

    // ========== CACHE DE CLIENTES ==========
    /**
     * Busca cliente no cache.
     *
     * @param clienteId ID do cliente
     * @return Optional com o cliente se encontrado
     */
    @Cacheable(value = "clientes", key = "#clienteId")
    public Optional<ClienteDTO> buscarCliente(final String clienteId) {
        log.debug("Buscando cliente no cache: {}", clienteId);
        try {
            ClienteDTO cliente = (ClienteDTO) redisTemplate.opsForValue().get("cliente:" + clienteId);
            if (cliente != null) {
                log.debug("Cliente {} encontrado no cache", clienteId);
                return Optional.of(cliente);
            } else {
                log.debug("Cliente {} não encontrado no cache", clienteId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar cliente {} no cache: {}", clienteId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Salva cliente no cache.
     *
     * @param clienteId ID do cliente
     * @param cliente   DTO do cliente
     */
    public void salvarCliente(final String clienteId, final ClienteDTO cliente) {
        log.debug("Salvando cliente no cache: {}", clienteId);
        try {
            redisTemplate.opsForValue().set("cliente:" + clienteId, cliente, Duration.ofHours(TTL_CLIENTE_HORAS));
            log.debug("Cliente {} salvo no cache", clienteId);
        } catch (Exception e) {
            log.error("Erro ao salvar cliente {} no cache: {}", clienteId, e.getMessage());
        }
    }

    /**
     * Remove cliente do cache.
     *
     * @param clienteId ID do cliente
     */
    @CacheEvict(value = "clientes", key = "#clienteId")
    public void removerCliente(final String clienteId) {
        log.debug("Removendo cliente do cache: {}", clienteId);
        try {
            redisTemplate.delete("cliente:" + clienteId);
            log.debug("Cliente {} removido do cache", clienteId);
        } catch (Exception e) {
            log.error("Erro ao remover cliente {} do cache: {}", clienteId, e.getMessage());
        }
    }

    // ========== CACHE DE CONTAS ==========
    /**
     * Busca conta no cache.
     *
     * @param contaId ID da conta
     * @return Optional com a conta se encontrada
     */
    @Cacheable(value = "contas", key = "#contaId")
    public Optional<ContaDTO> buscarConta(final String contaId) {
        log.debug("Buscando conta no cache: {}", contaId);
        try {
            ContaDTO conta = (ContaDTO) redisTemplate.opsForValue().get("conta:" + contaId);
            if (conta != null) {
                log.debug("Conta {} encontrada no cache", contaId);
                return Optional.of(conta);
            } else {
                log.debug("Conta {} não encontrada no cache", contaId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar conta {} no cache: {}", contaId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Salva conta no cache.
     *
     * @param contaId ID da conta
     * @param conta   DTO da conta
     */
    public void salvarConta(final String contaId, final ContaDTO conta) {
        log.debug("Salvando conta no cache: {}", contaId);
        try {
            redisTemplate.opsForValue().set("conta:" + contaId, conta, Duration.ofMinutes(TTL_CONTA_MINUTOS));
            log.debug("Conta {} salva no cache", contaId);
        } catch (Exception e) {
            log.error("Erro ao salvar conta {} no cache: {}", contaId, e.getMessage());
        }
    }

    /**
     * Remove conta do cache.
     *
     * @param contaId ID da conta
     */
    @CacheEvict(value = "contas", key = "#contaId")
    public void removerConta(final String contaId) {
        log.debug("Removendo conta do cache: {}", contaId);
        try {
            redisTemplate.delete("conta:" + contaId);
            log.debug("Conta {} removida do cache", contaId);
        } catch (Exception e) {
            log.error("Erro ao remover conta {} do cache: {}", contaId, e.getMessage());
        }
    }

    // ========== CACHE DE TRANSAÇÕES ==========
    /**
     * Busca transação no cache.
     *
     * @param transacaoId ID da transação
     * @return Optional com a transação se encontrada
     */
    @Cacheable(value = "transacoes", key = "#transacaoId")
    public Optional<TransacaoDTO> buscarTransacao(final String transacaoId) {
        log.debug("Buscando transação no cache: {}", transacaoId);
        try {
            TransacaoDTO transacao = (TransacaoDTO) redisTemplate.opsForValue().get("transacao:" + transacaoId);
            if (transacao != null) {
                log.debug("Transação {} encontrada no cache", transacaoId);
                return Optional.of(transacao);
            } else {
                log.debug("Transação {} não encontrada no cache", transacaoId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar transação {} no cache: {}", transacaoId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Salva transação no cache.
     *
     * @param transacaoId ID da transação
     * @param transacao   DTO da transação
     */
    public void salvarTransacao(final String transacaoId, final TransacaoDTO transacao) {
        log.debug("Salvando transação no cache: {}", transacaoId);
        try {
            redisTemplate.opsForValue().set("transacao:" + transacaoId, transacao, Duration.ofMinutes(TTL_TRANSACAO_MINUTOS));
            log.debug("Transação {} salva no cache", transacaoId);
        } catch (Exception e) {
            log.error("Erro ao salvar transação {} no cache: {}", transacaoId, e.getMessage());
        }
    }

    /**
     * Remove transação do cache.
     *
     * @param transacaoId ID da transação
     */
    @CacheEvict(value = "transacoes", key = "#transacaoId")
    public void removerTransacao(final String transacaoId) {
        log.debug("Removendo transação do cache: {}", transacaoId);
        try {
            redisTemplate.delete("transacao:" + transacaoId);
            log.debug("Transação {} removida do cache", transacaoId);
        } catch (Exception e) {
            log.error("Erro ao remover transação {} do cache: {}", transacaoId, e.getMessage());
        }
    }

    // ========== CACHE DE CONFIGURAÇÕES ==========
    /**
     * Busca configuração no cache.
     *
     * @param chave Chave da configuração
     * @return Optional com o valor se encontrado
     */
    @Cacheable(value = "configuracoes", key = "#chave")
    public Optional<String> buscarConfiguracao(final String chave) {
        log.debug("Buscando configuração no cache: {}", chave);
        try {
            String valor = (String) redisTemplate.opsForValue().get("config:" + chave);
            if (valor != null) {
                log.debug("Configuração {} encontrada no cache", chave);
                return Optional.of(valor);
            } else {
                log.debug("Configuração {} não encontrada no cache", chave);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar configuração {} no cache: {}", chave, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Salva configuração no cache.
     *
     * @param chave Chave da configuração
     * @param valor Valor da configuração
     */
    public void salvarConfiguracao(final String chave, final String valor) {
        log.debug("Salvando configuração no cache: {} = {}", chave, valor);
        try {
            redisTemplate.opsForValue().set("config:" + chave, valor, Duration.ofHours(TTL_CONFIG_HORAS));
            log.debug("Configuração {} salva no cache", chave);
        } catch (Exception e) {
            log.error("Erro ao salvar configuração {} no cache: {}", chave, e.getMessage());
        }
    }

    // ========== CACHE DE TARIFAS ==========
    /**
     * Busca tarifa no cache.
     *
     * @param operacao  Nome da operação
     * @param clienteId ID do cliente
     * @param produto   Nome do produto
     * @return Optional com a tarifa se encontrada
     */
    @Cacheable(value = "tarifas", key = "#operacao + \'_\' + #clienteId + \'_\' + #produto")
    public Optional<Double> buscarTarifa(final String operacao, final String clienteId, final String produto) {
        log.debug("Buscando tarifa no cache: " + "Operação={}, Cliente={}, Produto={}", operacao, clienteId, produto);
        try {
            String chave = String.format("tarifa:%s:%s:%s", operacao, clienteId, produto);
            Double tarifa = (Double) redisTemplate.opsForValue().get(chave);
            if (tarifa != null) {
                log.debug("Tarifa encontrada no cache: {}", tarifa);
                return Optional.of(tarifa);
            } else {
                log.debug("Tarifa não encontrada no cache");
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar tarifa no cache: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Salva tarifa no cache.
     *
     * @param operacao  Nome da operação
     * @param clienteId ID do cliente
     * @param produto   Nome do produto
     * @param tarifa    Valor da tarifa
     */
    public void salvarTarifa(final String operacao, final String clienteId, final String produto, final Double tarifa) {
        log.debug("Salvando tarifa no cache: " + "Operação={}, Cliente={}, Produto={}, Tarifa={}", operacao, clienteId, produto, tarifa);
        try {
            String chave = String.format("tarifa:%s:%s:%s", operacao, clienteId, produto);
            redisTemplate.opsForValue().set(chave, tarifa, Duration.ofHours(TTL_TARIFA_HORAS));
            log.debug("Tarifa salva no cache");
        } catch (Exception e) {
            log.error("Erro ao salvar tarifa no cache: {}", e.getMessage());
        }
    }

    // ========== MÉTODOS UTILITÁRIOS ==========
    /**
     * Limpa todo o cache.
     */
    public void limparCache() {
        log.info("Limpando todo o cache");
        if (redisTemplate == null) {
            log.warn("Redis não disponível, cache não foi limpo");
            return;
        }
        try {
            var connectionFactory = redisTemplate.getConnectionFactory();
            if (connectionFactory != null) {
                var connection = connectionFactory.getConnection();
                connection.flushAll();
                log.info("Cache limpo com sucesso");
            }
        } catch (Exception e) {
            log.error("Erro ao limpar cache: {}", e.getMessage());
        }
    }

    /**
     * Limpa cache por padrão.
     *
     * @param padrao Padrão de chaves para limpar
     */
    public void limparCachePorPadrao(final String padrao) {
        log.info("Limpando cache por padrão: {}", padrao);
        try {
            var keys = redisTemplate.keys(padrao);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Cache limpo por padrão: {}", padrao);
            }
        } catch (Exception e) {
            log.error("Erro ao limpar cache por padrão {}: {}", padrao, e.getMessage());
        }
    }

    /**
     * Verifica se chave existe no cache.
     *
     * @param chave Chave a verificar
     * @return true se a chave existe
     */
    public boolean existeChave(final String chave) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(chave));
        } catch (Exception e) {
            log.error("Erro ao verificar chave {} no cache: {}", chave, e.getMessage());
            return false;
        }
    }

    /**
     * Define TTL para chave.
     *
     * @param chave    Chave para expirar
     * @param segundos Tempo em segundos
     */
    public void definirTTL(final String chave, final long segundos) {
        try {
            redisTemplate.expire(chave, segundos, TimeUnit.SECONDS);
            log.debug("TTL definido para chave {}: {} segundos", chave, segundos);
        } catch (Exception e) {
            log.error("Erro ao definir TTL para chave {}: {}", chave, e.getMessage());
        }
    }

    /**
     * Creates a new {@code SharedCacheService} instance.
     *
     * @param redisTemplate Template do Redis para operações de cache.
     */
    @java.lang.SuppressWarnings("all")
    public SharedCacheService(final @org.springframework.beans.factory.annotation.Autowired(required = false) RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        if (redisTemplate == null) {
            log.warn("RedisTemplate not available - SharedCacheService will operate in degraded mode");
        }
    }
}
