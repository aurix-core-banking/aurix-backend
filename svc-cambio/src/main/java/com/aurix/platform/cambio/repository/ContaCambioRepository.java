package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.ContaCambio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCambioRepository extends JpaRepository<ContaCambio, Long> {

    Optional<ContaCambio> findByClienteId(Long clienteId);
}
