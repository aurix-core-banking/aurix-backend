package com.aurix.platform.fraud.repository;

import com.aurix.platform.fraud.entity.BloqueioPreventivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloqueioPreventivoRepository extends JpaRepository<BloqueioPreventivo, Long> {
    List<BloqueioPreventivo> findByClienteId(Long clienteId);
    List<BloqueioPreventivo> findByClienteIdAndAtivoTrue(Long clienteId);
    Optional<BloqueioPreventivo> findByClienteIdAndAtivoTrueAndTipo(Long clienteId, String tipo);
}
