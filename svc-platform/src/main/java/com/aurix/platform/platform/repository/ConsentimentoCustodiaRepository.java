package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.ConsentimentoCustodia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsentimentoCustodiaRepository extends JpaRepository<ConsentimentoCustodia, Long> {

    Optional<ConsentimentoCustodia> findByTenantIdAndContaIdAndParceiroIdAndStatus(String tenantId, Long contaId, Long parceiroId, ConsentimentoCustodia.StatusConsentimento status);

    List<ConsentimentoCustodia> findByTenantIdAndParceiroIdAndStatus(String tenantId, Long parceiroId, ConsentimentoCustodia.StatusConsentimento status);
}
