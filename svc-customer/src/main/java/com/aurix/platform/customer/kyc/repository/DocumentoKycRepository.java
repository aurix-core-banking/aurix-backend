package com.aurix.platform.customer.kyc.repository;

import com.aurix.platform.customer.kyc.entity.DocumentoKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentoKycRepository extends JpaRepository<DocumentoKYC, Long> {
    List<DocumentoKYC> findBySolicitacaoId(Long solicitacaoId);
}
