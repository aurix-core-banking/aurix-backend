package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Aprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AprovacaoRepository extends JpaRepository<Aprovacao, Long> {
    
    List<Aprovacao> findBySolicitacaoId(Long solicitacaoId);
    
    List<Aprovacao> findByAprovadorId(Long aprovadorId);
    
    List<Aprovacao> findByAprovadorIdAndStatus(Long aprovadorId, Aprovacao.StatusAprovacao status);
    
    @Query("SELECT a FROM Aprovacao a WHERE a.solicitacao.id = :solicitacaoId AND a.etapaWorkflow.ordemEtapa = :ordemEtapa")
    List<Aprovacao> findBySolicitacaoIdAndOrdemEtapa(@Param("solicitacaoId") Long solicitacaoId, @Param("ordemEtapa") Integer ordemEtapa);
    
    @Query("SELECT a FROM Aprovacao a WHERE a.solicitacao.id = :solicitacaoId AND a.aprovador.id = :aprovadorId AND a.status = :status")
    Optional<Aprovacao> findBySolicitacaoIdAndAprovadorIdAndStatus(@Param("solicitacaoId") Long solicitacaoId, @Param("aprovadorId") Long aprovadorId, @Param("status") Aprovacao.StatusAprovacao status);
    
    @Query("SELECT COUNT(a) FROM Aprovacao a WHERE a.aprovador.id = :aprovadorId")
    Long countByAprovadorId(@Param("aprovadorId") Long aprovadorId);
    
    @Query("SELECT COUNT(a) FROM Aprovacao a WHERE a.aprovador.id = :aprovadorId AND a.status = :status")
    Long countByAprovadorIdAndStatus(@Param("aprovadorId") Long aprovadorId, @Param("status") Aprovacao.StatusAprovacao status);
    
    @Query("SELECT a FROM Aprovacao a WHERE a.solicitacao.id = :solicitacaoId ORDER BY a.etapaWorkflow.ordemEtapa, a.dataCriacao")
    List<Aprovacao> findBySolicitacaoIdOrderByOrdemEtapa(@Param("solicitacaoId") Long solicitacaoId);
    
    @Query("SELECT a FROM Aprovacao a WHERE a.etapaWorkflow.id = :etapaId")
    List<Aprovacao> findByEtapaWorkflowId(@Param("etapaId") Long etapaId);
    
    @Query("SELECT a FROM Aprovacao a WHERE a.solicitacao.workflow.id = :workflowId")
    List<Aprovacao> findByWorkflowId(@Param("workflowId") Long workflowId);
}
