package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    List<Metrica> findByCategoria(Metrica.CategoriaMetrica categoria);

    List<Metrica> findByTipoMetrica(Metrica.TipoMetrica tipoMetrica);

    @Query("SELECT m FROM Metrica m WHERE m.dataMedicao BETWEEN :inicio AND :fim")
    List<Metrica> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    List<Metrica> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Metrica m WHERE m.meta IS NOT NULL AND m.valor >= m.meta")
    List<Metrica> findMetricasMetaAtingida();

    @Query("SELECT m FROM Metrica m WHERE (m.limiteInferior IS NOT NULL AND m.valor < m.limiteInferior) OR (m.limiteSuperior IS NOT NULL AND m.valor > m.limiteSuperior)")
    List<Metrica> findMetricasForaLimites();

    List<Metrica> findByTendencia(String tendencia);

    @Query("SELECT m FROM Metrica m WHERE m.dataMedicao >= :dataLimite ORDER BY m.dataMedicao DESC")
    List<Metrica> findMetricasRecentes(@Param("dataLimite") LocalDateTime dataLimite);

    long countByCategoria(Metrica.CategoriaMetrica categoria);

    long countByTipoMetrica(Metrica.TipoMetrica tipoMetrica);
}
