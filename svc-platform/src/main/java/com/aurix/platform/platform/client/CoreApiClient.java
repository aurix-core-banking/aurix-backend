package com.aurix.platform.platform.client;

import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CoreApiClient {

    Map<String, Object> buscarContaPorId(Long contaId, String tenantId);

    List<Map<String, Object>> buscarTransacoesPorConta(Long contaId, String tenantId);

    ConsentimentoOpenFinance obterConsentimento(String consentId);

    List<Map<String, Object>> getCreditCards(List<String> accountIds);

    Map<String, Object> getPersonalIdentifications(Long userId);

    BigDecimal consultarSaldo(String tenantId, Long contaId);

    String movimentarPix(String tenantId, Long contaIdOrigem, String chaveDestino, BigDecimal valor, String idempotencyKey);
}
