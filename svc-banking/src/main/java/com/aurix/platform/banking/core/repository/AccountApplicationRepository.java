package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AccountApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountApplicationRepository extends JpaRepository<AccountApplication, Long> {
    List<AccountApplication> findByClienteIdOrderByDataCriacaoDesc(Long clienteId);
    List<AccountApplication> findByStatusOrderByDataCriacaoAsc(AccountApplication.AccountApplicationStatus status);
    List<AccountApplication> findByReviewerIdOrderByDataCriacaoDesc(Long reviewerId);
    List<AccountApplication> findByCpfCnpj(String cpfCnpj);
}
