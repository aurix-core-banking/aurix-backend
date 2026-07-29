package com.aurix.platform.platform.client;

import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "aurix.baas.core-client", havingValue = "stub", matchIfMissing = true)
public class CoreApiClientStub implements CoreApiClient {

    @Override
    public BigDecimal consultarSaldo(String tenantId, Long contaId) {
        return BigDecimal.ZERO;
    }

    @Override
    public String movimentarPix(String tenantId, Long contaIdOrigem, String chaveDestino, BigDecimal valor, String idempotencyKey) {
        return "stub-" + idempotencyKey;
    }

    @Override
    public Map<String, Object> buscarContaPorId(Long contaId, String tenantId) {
        return Map.of("id", contaId, "saldo", BigDecimal.ZERO);
    }

    @Override
    public List<Map<String, Object>> buscarTransacoesPorConta(Long contaId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public ConsentimentoOpenFinance obterConsentimento(String consentId) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getCreditCards(List<String> accountIds) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> getPersonalIdentifications(Long userId) {
        return Map.of();
    }
}
