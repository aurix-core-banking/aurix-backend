package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.OperacaoCambio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoCambioRepository extends JpaRepository<OperacaoCambio, Long> {

    List<OperacaoCambio> findByClienteId(Long clienteId);

    List<OperacaoCambio> findByContratoId(Long contratoId);
}
