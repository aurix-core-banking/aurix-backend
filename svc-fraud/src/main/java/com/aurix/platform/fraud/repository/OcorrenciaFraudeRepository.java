package com.aurix.platform.fraud.repository;

import com.aurix.platform.fraud.entity.OcorrenciaFraude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OcorrenciaFraudeRepository extends JpaRepository<OcorrenciaFraude, Long> {
    List<OcorrenciaFraude> findByClienteId(Long clienteId);
    List<OcorrenciaFraude> findByStatus(String status);
}
