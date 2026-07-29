package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    
    Optional<Workflow> findByCodigoWorkflow(String codigoWorkflow);
    
    List<Workflow> findByEmpresaIdAndStatus(Long empresaId, Workflow.StatusWorkflow status);
    
    List<Workflow> findByTipoWorkflowAndStatus(Workflow.TipoWorkflow tipoWorkflow, Workflow.StatusWorkflow status);
    
    @Query("SELECT w FROM Workflow w WHERE w.empresa.id = :empresaId AND w.status = 'ATIVO' ORDER BY w.nomeWorkflow")
    List<Workflow> findWorkflowsAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT w FROM Workflow w WHERE w.tipoWorkflow = :tipo AND w.status = 'ATIVO' ORDER BY w.nomeWorkflow")
    List<Workflow> findWorkflowsAtivosByTipo(@Param("tipo") Workflow.TipoWorkflow tipo);
    
    boolean existsByCodigoWorkflow(String codigoWorkflow);
    
    @Query("SELECT COUNT(w) FROM Workflow w WHERE w.empresa.id = :empresaId AND w.status = 'ATIVO'")
    Long countWorkflowsAtivosByEmpresa(@Param("empresaId") Long empresaId);
}
