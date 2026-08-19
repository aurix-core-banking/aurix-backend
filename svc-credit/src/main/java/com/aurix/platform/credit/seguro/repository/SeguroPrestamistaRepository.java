package com.aurix.platform.credit.seguro.repository;

import com.aurix.platform.credit.seguro.entity.SeguroPrestamista;
import com.aurix.platform.credit.seguro.entity.StatusSeguro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroPrestamistaRepository extends JpaRepository<SeguroPrestamista, Long> {

    List<SeguroPrestamista> findByContratoId(Long contratoId);

    Optional<SeguroPrestamista> findByContratoIdAndStatus(Long contratoId, StatusSeguro status);

    List<SeguroPrestamista> findByClienteId(Long clienteId);

    List<SeguroPrestamista> findByTenantIdAndStatus(String tenantId, StatusSeguro status);
}
