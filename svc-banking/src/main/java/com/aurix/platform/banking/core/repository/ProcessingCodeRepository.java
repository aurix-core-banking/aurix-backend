package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessingCodeRepository extends JpaRepository<ProcessingCode, Long> {
    Optional<ProcessingCode> findByCode(String code);
    List<ProcessingCode> findByActiveTrueOrderByPriorityAsc();
    List<ProcessingCode> findByPaymentType(String paymentType);
    List<ProcessingCode> findByPaymentTypeAndActiveTrueOrderByPriorityAsc(String paymentType);
}
