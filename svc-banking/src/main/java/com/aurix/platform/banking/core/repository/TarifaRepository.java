package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    
    Optional<Tarifa> findByCodigoTarifa(String codigoTarifa);
    
    List<Tarifa> findByTipoTarifaAndAtivaTrue(Tarifa.TipoTarifa tipoTarifa);
    
    List<Tarifa> findByCategoriaTarifaAndAtivaTrue(Tarifa.CategoriaTarifa categoriaTarifa);
    
    List<Tarifa> findByNivelServicoAndAtivaTrue(Integer nivelServico);
    
    List<Tarifa> findByAtivaTrue();
    
    @Query("SELECT t FROM Tarifa t WHERE t.ativa = true AND " +
           "(:dataVigencia IS NULL OR (t.dataInicioVigencia IS NULL OR t.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (t.dataFimVigencia IS NULL OR t.dataFimVigencia >= :dataVigencia))")
    List<Tarifa> findTarifasVigentes(@Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT t FROM Tarifa t WHERE t.ativa = true AND t.tipoTarifa = :tipoTarifa AND " +
           "(:dataVigencia IS NULL OR (t.dataInicioVigencia IS NULL OR t.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (t.dataFimVigencia IS NULL OR t.dataFimVigencia >= :dataVigencia))")
    List<Tarifa> findTarifasVigentesPorTipo(@Param("tipoTarifa") Tarifa.TipoTarifa tipoTarifa, 
                                           @Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT t FROM Tarifa t WHERE t.ativa = true AND t.empresa.id = :empresaId AND " +
           "(:dataVigencia IS NULL OR (t.dataInicioVigencia IS NULL OR t.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (t.dataFimVigencia IS NULL OR t.dataFimVigencia >= :dataVigencia))")
    List<Tarifa> findTarifasVigentesPorEmpresa(@Param("empresaId") Long empresaId, 
                                              @Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT t FROM Tarifa t WHERE t.ativa = true AND t.tipoTarifa = :tipoTarifa AND " +
           "t.nivelServico <= :nivelServico AND " +
           "(:dataVigencia IS NULL OR (t.dataInicioVigencia IS NULL OR t.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (t.dataFimVigencia IS NULL OR t.dataFimVigencia >= :dataVigencia)) " +
           "ORDER BY t.nivelServico ASC, t.valorBase ASC")
    List<Tarifa> findMelhorTarifa(@Param("tipoTarifa") Tarifa.TipoTarifa tipoTarifa, 
                                 @Param("nivelServico") Integer nivelServico,
                                 @Param("dataVigencia") LocalDateTime dataVigencia);
}
