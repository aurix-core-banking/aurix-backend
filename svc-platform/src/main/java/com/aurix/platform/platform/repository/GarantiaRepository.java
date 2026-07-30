package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.Garantia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarantiaRepository extends JpaRepository<Garantia, Long> {
    List<Garantia> findByContratoId(Long contratoId);
    List<Garantia> findByClienteId(Long clienteId);
    List<Garantia> findByStatus(String status);
}
