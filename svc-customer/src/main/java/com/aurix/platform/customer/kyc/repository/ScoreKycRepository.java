package com.aurix.platform.customer.kyc.repository;

import com.aurix.platform.customer.kyc.entity.ScoreKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ScoreKycRepository extends JpaRepository<ScoreKYC, Long> {
    Optional<ScoreKYC> findByClienteId(Long clienteId);
}
