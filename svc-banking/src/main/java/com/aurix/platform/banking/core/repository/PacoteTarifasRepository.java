package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.PacoteTarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacoteTarifasRepository extends JpaRepository<PacoteTarifas, Long> {
    
    Optional<PacoteTarifas> findByCodigoPacote(String codigoPacote);
    
    List<PacoteTarifas> findByTipoPacoteAndAtivoTrue(PacoteTarifas.TipoPacote tipoPacote);
    
    List<PacoteTarifas> findByNivelServicoAndAtivoTrue(Integer nivelServico);
    
    List<PacoteTarifas> findByAtivoTrue();
    
    @Query("SELECT p FROM PacoteTarifas p WHERE p.ativo = true AND " +
           "(:dataVigencia IS NULL OR (p.dataInicioVigencia IS NULL OR p.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (p.dataFimVigencia IS NULL OR p.dataFimVigencia >= :dataVigencia))")
    List<PacoteTarifas> findPacotesVigentes(@Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT p FROM PacoteTarifas p WHERE p.ativo = true AND p.empresa.id = :empresaId AND " +
           "(:dataVigencia IS NULL OR (p.dataInicioVigencia IS NULL OR p.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (p.dataFimVigencia IS NULL OR p.dataFimVigencia >= :dataVigencia))")
    List<PacoteTarifas> findPacotesVigentesPorEmpresa(@Param("empresaId") Long empresaId, 
                                                     @Param("dataVigencia") LocalDateTime dataVigencia);
    
    @Query("SELECT p FROM PacoteTarifas p WHERE p.ativo = true AND p.nivelServico <= :nivelServico AND " +
           "(:dataVigencia IS NULL OR (p.dataInicioVigencia IS NULL OR p.dataInicioVigencia <= :dataVigencia)) AND " +
           "(:dataVigencia IS NULL OR (p.dataFimVigencia IS NULL OR p.dataFimVigencia >= :dataVigencia)) " +
           "ORDER BY p.nivelServico ASC, p.valorMensalidade ASC")
    List<PacoteTarifas> findMelhorPacote(@Param("nivelServico") Integer nivelServico,
                                        @Param("dataVigencia") LocalDateTime dataVigencia);
}
