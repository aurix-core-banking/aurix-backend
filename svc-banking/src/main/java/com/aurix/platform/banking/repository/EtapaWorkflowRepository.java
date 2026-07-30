package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.EtapaWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapaWorkflowRepository extends JpaRepository<EtapaWorkflow, Long> {
    
    List<EtapaWorkflow> findByWorkflowId(Long workflowId);
    
    List<EtapaWorkflow> findByWorkflowIdOrderByOrdemEtapa(Long workflowId);
    
    List<EtapaWorkflow> findByStatus(EtapaWorkflow.StatusEtapa status);
    
    List<EtapaWorkflow> findByCargoAprovadorId(Long cargoId);
    
    List<EtapaWorkflow> findByDepartamentoAprovadorId(Long departamentoId);
    
    @Query("SELECT e FROM EtapaWorkflow e WHERE e.workflow.id = :workflowId AND e.status = 'ATIVA' ORDER BY e.ordemEtapa")
    List<EtapaWorkflow> findEtapasAtivasByWorkflow(@Param("workflowId") Long workflowId);
    
    @Query("SELECT e FROM EtapaWorkflow e WHERE e.workflow.id = :workflowId AND e.ordemEtapa = :ordemEtapa")
    List<EtapaWorkflow> findByWorkflowIdAndOrdemEtapa(@Param("workflowId") Long workflowId, @Param("ordemEtapa") Integer ordemEtapa);
    
    @Query("SELECT e FROM EtapaWorkflow e WHERE e.workflow.id = :workflowId AND e.valorMinimo <= :valor AND e.valorMaximo >= :valor")
    List<EtapaWorkflow> findEtapasPorValor(@Param("workflowId") Long workflowId, @Param("valor") java.math.BigDecimal valor);
    
    @Query("SELECT e FROM EtapaWorkflow e WHERE e.workflow.id = :workflowId AND e.nivelHierarquicoNecessario <= :nivelHierarquico ORDER BY e.ordemEtapa")
    List<EtapaWorkflow> findEtapasPorNivelHierarquico(@Param("workflowId") Long workflowId, @Param("nivelHierarquico") Integer nivelHierarquico);
}
