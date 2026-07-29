package com.aurix.platform.credit.consignado.repository;

import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoConsignadoRepository extends JpaRepository<ContratoConsignado, Long> {

    List<ContratoConsignado> findByClienteId(Long clienteId);

    List<ContratoConsignado> findByStatus(String status);
}
