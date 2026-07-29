package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.ClienteCambio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteCambioRepository extends JpaRepository<ClienteCambio, Long> {

    Optional<ClienteCambio> findByClienteId(Long clienteId);
}
