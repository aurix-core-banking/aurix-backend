package com.aurix.platform.customer.repository;

import com.aurix.platform.customer.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocumento(String documento);
    List<Cliente> findBySegmento(String segmento);
    List<Cliente> findByStatus(String status);
    List<Cliente> findByNomeCompletoContainingIgnoreCase(String nome);
}
