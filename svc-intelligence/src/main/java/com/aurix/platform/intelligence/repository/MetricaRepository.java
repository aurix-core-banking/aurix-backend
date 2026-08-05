package com.aurix.platform.intelligence.repository;

import com.aurix.platform.shared.entity.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Metrica
 */
@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {
    
    /**
     * Busca métricas por categoria
     */
    List<Metrica> findByCategoria(Metrica.CategoriaMetrica categoria);
    
    /**
     * Busca métricas por tipo
     */
    List<Metrica> findByTipoMetrica(Metrica.TipoMetrica tipoMetrica);
    
    /**
     * Busca métricas por período
     */
    @Query("SELECT m FROM Metrica m WHERE m.dataMedicao BETWEEN :inicio AND :fim")
    List<Metrica> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca métricas por nome
     */
    List<Metrica> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca métricas que atingiram a meta
     */
    @Query("SELECT m FROM Metrica m WHERE m.meta IS NOT NULL AND m.valor >= m.meta")
    List<Metrica> findMetricasMetaAtingida();
    
    /**
     * Busca métricas fora dos limites
     */
    @Query("SELECT m FROM Metrica m WHERE (m.limiteInferior IS NOT NULL AND m.valor < m.limiteInferior) OR (m.limiteSuperior IS NOT NULL AND m.valor > m.limiteSuperior)")
    List<Metrica> findMetricasForaLimites();
    
    /**
     * Busca métricas por tendência
     */
    List<Metrica> findByTendencia(String tendencia);
    
    /**
     * Busca métricas recentes
     */
    @Query("SELECT m FROM Metrica m WHERE m.dataMedicao >= :dataLimite ORDER BY m.dataMedicao DESC")
    List<Metrica> findMetricasRecentes(@Param("dataLimite") LocalDateTime dataLimite);
    
    /**
     * Conta métricas por categoria
     */
    long countByCategoria(Metrica.CategoriaMetrica categoria);
    
    /**
     * Conta métricas por tipo
     */
    long countByTipoMetrica(Metrica.TipoMetrica tipoMetrica);
}

