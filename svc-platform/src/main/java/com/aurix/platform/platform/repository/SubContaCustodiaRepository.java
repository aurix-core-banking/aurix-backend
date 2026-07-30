package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.SubContaCustodia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubContaCustodiaRepository extends JpaRepository<SubContaCustodia, Long> {

    Optional<SubContaCustodia> findByTenantIdAndParceiroIdAndIdentificadorExterno(String tenantId, Long parceiroId, String identificadorExterno);

    List<SubContaCustodia> findByTenantIdAndParceiroId(String tenantId, Long parceiroId);
}
