package com.aurix.platform.contracts.repository;

import com.aurix.platform.contracts.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    Optional<Contrato> findByNumeroContrato(String numeroContrato);

    List<Contrato> findByClienteId(Long clienteId);

    List<Contrato> findByStatus(Contrato.StatusContrato status);

    boolean existsByNumeroContrato(String numeroContrato);
}
