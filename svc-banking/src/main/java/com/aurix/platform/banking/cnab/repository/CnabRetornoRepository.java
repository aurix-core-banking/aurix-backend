package com.aurix.platform.banking.cnab.repository;

import com.aurix.platform.banking.cnab.entity.CnabRetorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CnabRetornoRepository extends JpaRepository<CnabRetorno, Long> {
    List<CnabRetorno> findByTenantId(String tenantId);
    Optional<CnabRetorno> findByTenantIdAndId(String tenantId, Long id);
    List<CnabRetorno> findByRemessaId(Long remessaId);
    List<CnabRetorno> findByProcessado(Boolean processado);
}
