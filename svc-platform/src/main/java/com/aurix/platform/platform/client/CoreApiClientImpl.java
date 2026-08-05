package com.aurix.platform.platform.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "aurix.baas.core-client", havingValue = "real")
public class CoreApiClientImpl implements CoreApiClient {

    private final WebClient webClient;
    private final String tenantIdHeader;

    public CoreApiClientImpl(
            WebClient.Builder builder,
            @Value("${aurix.openfinance.core.base-url:http://localhost:8081}") String baseUrl,
            @Value("${aurix.openfinance.core.tenant-id:}") String tenantIdHeader) {
        this.tenantIdHeader = (tenantIdHeader != null && !tenantIdHeader.isBlank()) ? tenantIdHeader : null;
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Map<String, Object> buscarContaPorId(Long contaId, String tenantId) {
        try {
            var spec = webClient.get().uri("/api/core/contas/{id}", contaId);
            if (tenantId != null && !tenantId.isBlank()) spec = spec.header("X-Tenant-Id", tenantId);
            else if (tenantIdHeader != null) spec = spec.header("X-Tenant-Id", tenantIdHeader);
            return spec.retrieve().bodyToMono(Map.class).block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> buscarTransacoesPorConta(Long contaId, String tenantId) {
        try {
            var spec = webClient.get().uri("/api/core/api/transacoes/conta/{contaId}?size=500", contaId);
            if (tenantId != null && !tenantId.isBlank()) spec = spec.header("X-Tenant-Id", tenantId);
            else if (tenantIdHeader != null) spec = spec.header("X-Tenant-Id", tenantIdHeader);
            Map<String, Object> body = spec.retrieve().bodyToMono(Map.class).block();
            if (body == null) return Collections.emptyList();
            Object content = body.get("content");
            if (content instanceof List) return (List<Map<String, Object>>) content;
            return Collections.emptyList();
        } catch (WebClientResponseException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public ConsentimentoOpenFinance obterConsentimento(String consentId) {
        try {
            return webClient.get().uri("/api/core/consentimentos/{id}", consentId)
                .retrieve().bodyToMono(ConsentimentoOpenFinance.class).block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getCreditCards(List<String> accountIds) {
        try {
            return webClient.post().uri("/api/core/credit-cards")
                .bodyValue(Map.of("accountIds", accountIds))
                .retrieve().bodyToMono(List.class).block();
        } catch (WebClientResponseException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> getPersonalIdentifications(Long userId) {
        try {
            return webClient.get().uri("/api/core/personal-identifications/{userId}", userId)
                .retrieve().bodyToMono(Map.class).block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    @Override
    public BigDecimal consultarSaldo(String tenantId, Long contaId) {
        try {
            var spec = webClient.get().uri("/api/core/contas/{id}/saldo", contaId);
            if (tenantId != null && !tenantId.isBlank()) spec = spec.header("X-Tenant-Id", tenantId);
            else if (tenantIdHeader != null) spec = spec.header("X-Tenant-Id", tenantIdHeader);
            Map<String, Object> body = spec.retrieve().bodyToMono(Map.class).block();
            if (body != null && body.get("saldo") instanceof Number) {
                return new BigDecimal(body.get("saldo").toString());
            }
            return BigDecimal.ZERO;
        } catch (WebClientResponseException e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public String movimentarPix(String tenantId, Long contaIdOrigem, String chaveDestino, BigDecimal valor, String idempotencyKey) {
        try {
            var spec = webClient.post().uri("/api/core/pix/transfers")
                .bodyValue(Map.of(
                    "contaOrigem", contaIdOrigem,
                    "chaveDestino", chaveDestino,
                    "valor", valor,
                    "idempotencyKey", idempotencyKey));
            if (tenantId != null && !tenantId.isBlank()) spec = spec.header("X-Tenant-Id", tenantId);
            else if (tenantIdHeader != null) spec = spec.header("X-Tenant-Id", tenantIdHeader);
            Map<String, Object> body = spec.retrieve().bodyToMono(Map.class).block();
            if (body != null) return (String) body.get("transactionId");
            return null;
        } catch (WebClientResponseException e) {
            return null;
        }
    }
}
