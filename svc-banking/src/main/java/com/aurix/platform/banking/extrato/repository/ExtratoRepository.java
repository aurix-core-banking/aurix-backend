package com.aurix.platform.banking.extrato.repository;

import com.aurix.platform.banking.extrato.entity.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
    List<Extrato> findByContaId(Long contaId);
    List<Extrato> findByTenantId(String tenantId);
    Optional<Extrato> findByTenantIdAndId(String tenantId, Long id);
    List<Extrato> findByContaIdAndDataInicioBetween(Long contaId, java.time.LocalDate dataInicio, java.time.LocalDate dataFim);
}
