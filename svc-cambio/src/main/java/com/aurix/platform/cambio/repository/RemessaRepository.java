package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.Remessa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemessaRepository extends JpaRepository<Remessa, Long> {

    List<Remessa> findByClienteId(Long clienteId);

    List<Remessa> findByStatus(String status);

    List<Remessa> findByStatusIn(List<String> statuses);
}
