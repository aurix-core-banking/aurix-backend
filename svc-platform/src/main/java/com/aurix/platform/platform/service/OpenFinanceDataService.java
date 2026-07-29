package com.aurix.platform.platform.service;

import com.aurix.platform.platform.client.CoreApiClient;
import com.aurix.platform.platform.dto.TokenOpenFinanceDTO;
import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import com.aurix.platform.platform.entity.TokenOpenFinance;
import com.aurix.platform.platform.repository.ConsentimentoOpenFinanceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"PMD.UselessParentheses"})
public class OpenFinanceDataService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OpenFinanceDataService.class);
    private final ConsentimentoOpenFinanceRepository consentimentoRepository;
    private final CoreApiClient coreApiClient;
    @Value("${aurix.openfinance.core.tenant-id:default}")
    private String defaultTenantId;

    public List<Map<String, Object>> listarContasPorToken(TokenOpenFinanceDTO token) {
        ConsentimentoOpenFinance consent = consentimentoRepository.findByConsentId(token.getConsentId()).orElse(null);
        if (consent == null || consent.getStatus() != ConsentimentoOpenFinance.StatusConsentimento.APROVADO) return Collections.emptyList();
        if (consent.getDataExpiracao().isBefore(LocalDateTime.now())) return Collections.emptyList();
        List<Long> contaIds = consent.getContasAutorizadas();
        if (contaIds == null || contaIds.isEmpty()) return Collections.emptyList();
        String tenantId = defaultTenantId;
        List<Map<String, Object>> accounts = new ArrayList<>();
        for (Long contaId : contaIds) {
            Map<String, Object> conta = coreApiClient.buscarContaPorId(contaId, tenantId);
            if (conta != null) accounts.add(toAccountFormat(conta));
        }
        return accounts;
    }

    public List<Map<String, Object>> listarTransacoesPorConta(String accountId, TokenOpenFinanceDTO token) {
        ConsentimentoOpenFinance consent = consentimentoRepository.findByConsentId(token.getConsentId()).orElse(null);
        if (consent == null || consent.getStatus() != ConsentimentoOpenFinance.StatusConsentimento.APROVADO) return Collections.emptyList();
        if (consent.getDataExpiracao().isBefore(LocalDateTime.now())) return Collections.emptyList();
        List<Long> contaIds = consent.getContasAutorizadas();
        if (contaIds == null) return Collections.emptyList();
        Long contaIdLong;
        try {
            contaIdLong = Long.parseLong(accountId);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
        if (!contaIds.contains(contaIdLong)) return Collections.emptyList();
        String tenantId = defaultTenantId;
        List<Map<String, Object>> transacoes = coreApiClient.buscarTransacoesPorConta(contaIdLong, tenantId);
        return transacoes.stream().map(this::toTransactionFormat).collect(Collectors.toList());
    }

    public List<Map<String, Object>> listarCartoesCreditoPorToken(TokenOpenFinance token) {
        ConsentimentoOpenFinance consent = coreApiClient.obterConsentimento(token.getConsentId());
        if (consent == null) {
            log.warn("Consentimento não encontrado para token: {}", token.getConsentId());
            return List.of();
        }
        if (!consent.getPermissoes().contains(ConsentimentoOpenFinance.TipoConsentimento.CREDIT_CARDS_ACCOUNTS.name())) {
            log.warn("Consentimento {} não possui permissão para cartões de crédito", token.getConsentId());
            return List.of();
        }
        List<Long> contasAutorizadasLong = consent.getContasAutorizadas();
        if (contasAutorizadasLong == null || contasAutorizadasLong.isEmpty()) return List.of();
        List<String> contasAutorizadas = contasAutorizadasLong.stream().map(String::valueOf).toList();
        return coreApiClient.getCreditCards(contasAutorizadas);
    }

    public List<Map<String, Object>> listarIdentificacoesPessoaisPorToken(TokenOpenFinance token) {
        ConsentimentoOpenFinance consentimento = coreApiClient.obterConsentimento(token.getConsentId());
        if (consentimento == null) {
            log.warn("Consentimento não encontrado para token: {}", token.getConsentId());
            return List.of();
        }
        if (!consentimento.getPermissoes().contains(ConsentimentoOpenFinance.TipoConsentimento.CUSTOMERS_PERSONAL_IDENTIFICATIONS.name())) {
            log.warn("Consentimento {} não possui permissão para identificação pessoal", token.getConsentId());
            return List.of();
        }
        Map<String, Object> dados = coreApiClient.getPersonalIdentifications(token.getUserId());
        return List.of(Objects.requireNonNullElse(dados, Map.of()));
    }

    private Map<String, Object> toAccountFormat(Map<String, Object> conta) {
        Object id = conta.get("id");
        Object saldo = conta.get("saldo");
        return Map.of("accountId", id != null ? String.valueOf(id) : "", "number", conta.get("numeroConta") != null ? String.valueOf(conta.get("numeroConta")) : "", "type", conta.get("tipoConta") != null ? String.valueOf(conta.get("tipoConta")) : "CONTA_DEPOSITO_A_VISTA", "balance", saldo != null ? saldo : 0, "currency", "BRL", "brand", Map.of("name", "Aurix"));
    }

    private Map<String, Object> toTransactionFormat(Map<String, Object> t) {
        Object id = t.get("id");
        Object contaOrigem = t.get("contaOrigemId");
        Object contaDestino = t.get("contaDestinoId");
        Object valor = t.get("valor");
        Object dataTransacao = t.get("dataTransacao");
        Object dataProcessamento = t.get("dataProcessamento");
        Object dataCri = t.get("dataCriacao");
        Object dataResolvida = dataTransacao != null ? dataTransacao : (dataProcessamento != null ? dataProcessamento : dataCri);
        Object tipo = t.get("tipoTransacao");
        return Map.of("transactionId", id != null ? String.valueOf(id) : "", "accountId", contaOrigem != null ? String.valueOf(contaOrigem) : (contaDestino != null ? String.valueOf(contaDestino) : ""), "amount", valor != null ? valor : 0, "type", tipo != null ? String.valueOf(tipo) : "PIX", "completedAuthorised", dataResolvida != null ? dataResolvida : "", "creditDebitType", "CREDIT");
    }

    @java.lang.SuppressWarnings("all")
    public OpenFinanceDataService(final ConsentimentoOpenFinanceRepository consentimentoRepository, final CoreApiClient coreApiClient) {
        this.consentimentoRepository = consentimentoRepository;
        this.coreApiClient = coreApiClient;
    }
}
