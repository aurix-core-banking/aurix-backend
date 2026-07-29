package com.aurix.platform.credit.consignado.repository;

import com.aurix.platform.credit.consignado.entity.ConvenioConsignado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvenioConsignadoRepository extends JpaRepository<ConvenioConsignado, Long> {

    List<ConvenioConsignado> findByAtivoTrue();
}
