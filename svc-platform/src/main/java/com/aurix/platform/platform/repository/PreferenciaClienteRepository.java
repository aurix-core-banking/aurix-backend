package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.PreferenciaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PreferenciaClienteRepository extends JpaRepository<PreferenciaCliente, Long> {
    Optional<PreferenciaCliente> findByClienteId(Long clienteId);
}
