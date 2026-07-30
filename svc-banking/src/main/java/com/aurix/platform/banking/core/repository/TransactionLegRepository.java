package com.aurix.platform.banking.core.repository;

import com.aurix.platform.shared.entity.TransactionLeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLegRepository extends JpaRepository<TransactionLeg, Long> {

    List<TransactionLeg> findByTransactionId(Long transactionId);

    List<TransactionLeg> findByAccountId(Long accountId);

    List<TransactionLeg> findByLegType(TransactionLeg.LegType legType);

    List<TransactionLeg> findByLegStatus(TransactionLeg.LegStatus legStatus);

    List<TransactionLeg> findByTransactionIdAndLegType(Long transactionId, TransactionLeg.LegType legType);
}
