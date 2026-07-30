package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    Optional<PaymentRequest> findByToken(String token);
    List<PaymentRequest> findByRequesterIdOrderByDataCriacaoDesc(Long requesterId);
    List<PaymentRequest> findByPayerIdOrderByDataCriacaoDesc(Long payerId);
    List<PaymentRequest> findByStatusAndExpiresAtBefore(PaymentRequest.PaymentRequestStatus status, LocalDateTime now);
}
