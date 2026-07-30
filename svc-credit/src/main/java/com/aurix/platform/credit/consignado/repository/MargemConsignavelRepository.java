package com.aurix.platform.credit.consignado.repository;

import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MargemConsignavelRepository extends JpaRepository<MargemConsignavel, Long> {

    Optional<MargemConsignavel> findByClienteIdAndFonteMargem(Long clienteId, String fonteMargem);

    List<MargemConsignavel> findByClienteId(Long clienteId);
}
