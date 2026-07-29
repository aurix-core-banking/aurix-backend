package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.Pep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PepRepository extends JpaRepository<Pep, Long> {

    Optional<Pep> findByTenantIdAndCpf(String tenantId, String cpf);

    boolean existsByTenantIdAndCpf(String tenantId, String cpf);
}
