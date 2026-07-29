package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ContaTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContaTarifaRepository extends JpaRepository<ContaTarifa, Long> {
    
    List<ContaTarifa> findByContaIdAndAtivaTrue(Long contaId);
    
    Optional<ContaTarifa> findByContaIdAndTarifaIdAndAtivaTrue(Long contaId, Long tarifaId);
    
    List<ContaTarifa> findByContaIdAndPacoteTarifasIdAndAtivaTrue(Long contaId, Long pacoteTarifasId);
    
    @Query("SELECT ct FROM ContaTarifa ct WHERE ct.conta.id = :contaId AND ct.ativa = true AND " +
           "(:dataVigencia IS NULL OR (ct.dataInicioVigencia IS NULL OR ct.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (ct.dataFimVigencia IS NULL OR ct.dataFimVigencia >= :dataVigencia))")
    List<ContaTarifa> findTarifasVigentesPorConta(@Param("contaId") Long contaId, 
                                                 @Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT ct FROM ContaTarifa ct WHERE ct.conta.id = :contaId AND ct.tarifa.tipoTarifa = :tipoTarifa AND ct.ativa = true AND " +
           "(:dataVigencia IS NULL OR (ct.dataInicioVigencia IS NULL OR ct.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (ct.dataFimVigencia IS NULL OR ct.dataFimVigencia >= :dataVigencia))")
    List<ContaTarifa> findTarifasVigentesPorContaETipo(@Param("contaId") Long contaId, 
                                                       @Param("tipoTarifa") String tipoTarifa,
                                                       @Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT COUNT(ct) FROM ContaTarifa ct WHERE ct.conta.id = :contaId AND ct.tarifa.tipoTarifa = :tipoTarifa AND ct.ativa = true AND " +
           "(:dataVigencia IS NULL OR (ct.dataInicioVigencia IS NULL OR ct.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (ct.dataFimVigencia IS NULL OR ct.dataFimVigencia >= :dataVigencia))")
    Long countUtilizacoesTarifa(@Param("contaId") Long contaId, 
                               @Param("tipoTarifa") String tipoTarifa,
                               @Param("dataVigencia") LocalDateTime dataVigencia);
}
