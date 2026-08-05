package com.aurix.platform.customer.kyc.repository;

import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitacaoKycRepository extends JpaRepository<SolicitacaoKYC, Long> {
    List<SolicitacaoKYC> findByClienteId(Long clienteId);
    List<SolicitacaoKYC> findByStatus(String status);
    Optional<SolicitacaoKYC> findTopByClienteIdOrderByDataSolicitacaoDesc(Long clienteId);
}
