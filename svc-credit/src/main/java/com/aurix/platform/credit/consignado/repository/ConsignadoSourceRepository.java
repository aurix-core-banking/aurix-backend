package com.aurix.platform.credit.consignado.repository;

import com.aurix.platform.credit.consignado.entity.ConsignadoSource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsignadoSourceRepository extends JpaRepository<ConsignadoSource, Long> {

    List<ConsignadoSource> findByTipo(String tipo);
}
