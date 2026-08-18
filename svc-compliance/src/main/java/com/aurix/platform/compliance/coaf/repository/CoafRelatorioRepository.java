package com.aurix.platform.compliance.coaf.repository;

import com.aurix.platform.compliance.coaf.entity.CoafRelatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoafRelatorioRepository extends JpaRepository<CoafRelatorio, Long> {

    Optional<CoafRelatorio> findByCodigoRelatorio(String codigoRelatorio);

    List<CoafRelatorio> findByStatus(CoafRelatorio.StatusRelatorioCoaf status);

    List<CoafRelatorio> findByTipoRelatorio(CoafRelatorio.TipoRelatorioCoaf tipo);

    @Query("SELECT r FROM CoafRelatorio r WHERE r.dataInicioPeriodo >= :inicio AND r.dataFimPeriodo <= :fim")
    List<CoafRelatorio> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT r FROM CoafRelatorio r WHERE r.status = 'GERADO' ORDER BY r.dataGeracao DESC")
    List<CoafRelatorio> findRelatoriosPendentesEnvio();
}
