package com.aurix.platform.shared.service;

import com.aurix.platform.shared.entity.ApiKey;
import com.aurix.platform.shared.repository.ApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApiKeyService {

    private static final Logger log = LoggerFactory.getLogger(ApiKeyService.class);

    private final ApiKeyRepository repository;

    public ApiKeyService(ApiKeyRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria nova API key.
     * @return Map com prefixo, key (plaintext — retornar apenas uma vez), e metadata
     */
    @Transactional
    public Map<String, Object> criar(String nome, String tenantId, String plano,
                                      Integer diasExpiracao) {
        String key = ApiKey.gerarKey();
        ApiKey apiKey = new ApiKey();
        apiKey.setPrefixo(ApiKey.extrairPrefixo(key));
        apiKey.setKeyHash(ApiKey.hashKey(key));
        apiKey.setNome(nome);
        apiKey.setTenantId(tenantId);
        apiKey.setPlano(plano);
        apiKey.setAtivo(true);
        if (diasExpiracao != null) {
            apiKey.setDataExpiracao(LocalDateTime.now().plusDays(diasExpiracao));
        }
        repository.save(apiKey);

        log.info("API key criada: prefixo={}, tenant={}, plano={}", apiKey.getPrefixo(), tenantId, plano);

        return Map.of(
            "key", key, // ÚNICA VEZ que a key plaintext é retornada
            "prefixo", apiKey.getPrefixo(),
            "tenantId", tenantId,
            "plano", plano,
            "rateLimitRpm", apiKey.getRateLimitRpm()
        );
    }

    /**
     * Valida API key recebida.
     * @return true se key é válida, ativa e não expirada
     */
    @Transactional
    public boolean validar(String key) {
        if (key == null || key.isBlank()) return false;

        String hash = ApiKey.hashKey(key);
        Optional<ApiKey> opt = repository.findByKeyHash(hash);

        if (opt.isEmpty()) return false;

        ApiKey apiKey = opt.get();
        if (!apiKey.isAtivo()) {
            log.warn("API key inativa: prefixo={}", apiKey.getPrefixo());
            return false;
        }
        if (apiKey.isExpirado()) {
            log.warn("API key expirada: prefixo={}", apiKey.getPrefixo());
            return false;
        }

        apiKey.registrarUso();
        repository.save(apiKey);
        return true;
    }

    /**
     * Obtém tenant ID a partir da API key.
     */
    public Optional<String> obterTenantId(String key) {
        if (key == null || key.isBlank()) return Optional.empty();
        String hash = ApiKey.hashKey(key);
        return repository.findByKeyHash(hash)
                .filter(ApiKey::isAtivo)
                .map(ApiKey::getTenantId);
    }

    /**
     * Rotação de API key — gera nova e invalida a antiga.
     */
    @Transactional
    public Map<String, String> rotacionar(Long apiKeyId) {
        ApiKey apiKey = repository.findById(apiKeyId)
                .orElseThrow(() -> new RuntimeException("API key não encontrada: " + apiKeyId));

        String novaKey = ApiKey.gerarKey();
        apiKey.rotacionar(novaKey);
        repository.save(apiKey);

        log.info("API key rotacionada: prefixo={}", apiKey.getPrefixo());
        return Map.of("novaKey", novaKey, "prefixo", apiKey.getPrefixo());
    }

    /**
     * Revogar API key.
     */
    @Transactional
    public void revogar(Long apiKeyId) {
        ApiKey apiKey = repository.findById(apiKeyId)
                .orElseThrow(() -> new RuntimeException("API key não encontrada: " + apiKeyId));
        apiKey.revogar();
        repository.save(apiKey);
        log.info("API key revogada: prefixo={}", apiKey.getPrefixo());
    }

    public List<ApiKey> listarTodas() {
        return repository.findAll();
    }

    public Optional<ApiKey> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
