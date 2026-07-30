package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.SolicitacaoOnboarding;
import com.aurix.platform.customer.onboarding.entity.StatusOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitacaoOnboardingRepository extends JpaRepository<SolicitacaoOnboarding, Long> {

    Optional<SolicitacaoOnboarding> findByTenantIdAndId(String tenantId, Long id);

    List<SolicitacaoOnboarding> findByTenantId(String tenantId);

    List<SolicitacaoOnboarding> findByTenantIdAndStatus(String tenantId, StatusOnboarding status);

    @Query("SELECT s FROM SolicitacaoOnboarding s WHERE s.tenantId = :tenantId AND s.status IN :statusList ORDER BY s.dataCriacao DESC")
    List<SolicitacaoOnboarding> findByTenantIdAndStatusIn(@Param("tenantId") String tenantId,
                                                          @Param("statusList") List<StatusOnboarding> statusList);
}
