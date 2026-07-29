package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.PerfilFinanceiroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PerfilFinanceiroClienteRepository extends JpaRepository<PerfilFinanceiroCliente, Long> {
    Optional<PerfilFinanceiroCliente> findByClienteId(Long clienteId);
    boolean existsByClienteId(Long clienteId);
    void deleteByClienteId(Long clienteId);
}
