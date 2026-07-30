package com.aurix.platform.platform.service;

import com.aurix.platform.platform.client.CoreApiClient;
import com.aurix.platform.platform.entity.ConsentimentoCustodia;
import com.aurix.platform.platform.entity.ParceiroCustodia;
import com.aurix.platform.platform.entity.SubContaCustodia;
import com.aurix.platform.platform.repository.ConsentimentoCustodiaRepository;
import com.aurix.platform.platform.repository.ParceiroCustodiaRepository;
import com.aurix.platform.platform.repository.SubContaCustodiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustodiaService {
    private final ParceiroCustodiaRepository parceiroRepository;
    private final SubContaCustodiaRepository subContaRepository;
    private final ConsentimentoCustodiaRepository consentimentoRepository;
    private final CoreApiClient coreApiClient;

    @Transactional
    public SubContaCustodia criarSubConta(String tenantId, String clientId, Long contaId, String identificadorExterno) {
        ParceiroCustodia parceiro = parceiroRepository.findByTenantIdAndClientId(tenantId, clientId).orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        if (!Boolean.TRUE.equals(parceiro.getAtivo())) throw new IllegalArgumentException("Parceiro inativo");
        if (subContaRepository.findByTenantIdAndParceiroIdAndIdentificadorExterno(tenantId, parceiro.getId(), identificadorExterno).isPresent()) {
            throw new IllegalArgumentException("Sub-conta ja existe para este identificador");
        }
        return subContaRepository.save(SubContaCustodia.builder().tenantId(tenantId).parceiro(parceiro).contaId(contaId).identificadorExterno(identificadorExterno).build());
    }

    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(String tenantId, String clientId, Long contaId) {
        ParceiroCustodia parceiro = parceiroRepository.findByTenantIdAndClientId(tenantId, clientId).orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        ConsentimentoCustodia consent = consentimentoRepository.findByTenantIdAndContaIdAndParceiroIdAndStatus(tenantId, contaId, parceiro.getId(), ConsentimentoCustodia.StatusConsentimento.ATIVO).orElseThrow(() -> new IllegalArgumentException("Consentimento ativo nao encontrado"));
        if (consent.getDataExpiracao().isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Consentimento expirado");
        if (consent.getEscopos() == null || !consent.getEscopos().contains(ConsentimentoCustodia.EscopoCustodia.CONSULTAR_SALDO)) {
            throw new IllegalArgumentException("Consentimento nao inclui CONSULTAR_SALDO");
        }
        return coreApiClient.consultarSaldo(tenantId, contaId);
    }

    @Transactional
    public String movimentarPix(String tenantId, String clientId, Long contaIdOrigem, String chaveDestino, BigDecimal valor, String idempotencyKey) {
        ParceiroCustodia parceiro = parceiroRepository.findByTenantIdAndClientId(tenantId, clientId).orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        ConsentimentoCustodia consent = consentimentoRepository.findByTenantIdAndContaIdAndParceiroIdAndStatus(tenantId, contaIdOrigem, parceiro.getId(), ConsentimentoCustodia.StatusConsentimento.ATIVO).orElseThrow(() -> new IllegalArgumentException("Consentimento ativo nao encontrado"));
        if (consent.getDataExpiracao().isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Consentimento expirado");
        if (consent.getEscopos() == null || !consent.getEscopos().contains(ConsentimentoCustodia.EscopoCustodia.MOVIMENTAR)) {
            throw new IllegalArgumentException("Consentimento nao inclui MOVIMENTAR");
        }
        return coreApiClient.movimentarPix(tenantId, contaIdOrigem, chaveDestino, valor, idempotencyKey);
    }

    @Transactional
    public ConsentimentoCustodia registrarConsentimento(String tenantId, Long contaId, Long parceiroId, List<ConsentimentoCustodia.EscopoCustodia> escopos, LocalDateTime dataExpiracao) {
        ParceiroCustodia parceiro = parceiroRepository.findById(parceiroId).orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        if (!parceiro.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Parceiro nao pertence ao tenant");
        return consentimentoRepository.save(ConsentimentoCustodia.builder().tenantId(tenantId).contaId(contaId).parceiro(parceiro).escopos(escopos).dataExpiracao(dataExpiracao).status(ConsentimentoCustodia.StatusConsentimento.ATIVO).build());
    }

    @Transactional(readOnly = true)
    public List<SubContaCustodia> listarSubContas(String tenantId, String clientId) {
        ParceiroCustodia parceiro = parceiroRepository.findByTenantIdAndClientId(tenantId, clientId).orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        return subContaRepository.findByTenantIdAndParceiroId(tenantId, parceiro.getId());
    }

    @java.lang.SuppressWarnings("all")
    public CustodiaService(final ParceiroCustodiaRepository parceiroRepository, final SubContaCustodiaRepository subContaRepository, final ConsentimentoCustodiaRepository consentimentoRepository, final CoreApiClient coreApiClient) {
        this.parceiroRepository = parceiroRepository;
        this.subContaRepository = subContaRepository;
        this.consentimentoRepository = consentimentoRepository;
        this.coreApiClient = coreApiClient;
    }
}
