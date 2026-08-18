package com.aurix.platform.banking.cnab.repository;

import com.aurix.platform.banking.cnab.entity.CnabRemessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CnabRemessaRepository extends JpaRepository<CnabRemessa, Long> {
    List<CnabRemessa> findByTenantId(String tenantId);
    List<CnabRemessa> findByTenantIdAndTipo(String tenantId, CnabRemessa.TipoCnab tipo);
    Optional<CnabRemessa> findByTenantIdAndId(String tenantId, Long id);
    List<CnabRemessa> findByStatus(CnabRemessa.StatusRemessa status);
}
