package com.aurix.platform.payments.pix.repository;

import com.aurix.platform.shared.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para Cliente (Visão PIX)
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca cliente por CPF
     */
    Optional<Cliente> findByCpf(String cpf);
}
