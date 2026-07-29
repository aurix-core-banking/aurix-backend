package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.DocumentoOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoOnboardingRepository extends JpaRepository<DocumentoOnboarding, Long> {

    List<DocumentoOnboarding> findBySolicitacaoId(Long solicitacaoId);
}
