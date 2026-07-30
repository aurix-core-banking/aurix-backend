package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.SolicitacaoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoAprovacaoRepository extends JpaRepository<SolicitacaoAprovacao, Long> {
    
    List<SolicitacaoAprovacao> findBySolicitanteId(Long solicitanteId);
    
    List<SolicitacaoAprovacao> findBySolicitanteIdAndStatus(Long solicitanteId, SolicitacaoAprovacao.StatusSolicitacao status);
    
    List<SolicitacaoAprovacao> findBySolicitanteIdOrderByDataCriacaoDesc(Long solicitanteId);
    
    @Query("SELECT s FROM SolicitacaoAprovacao s JOIN s.aprovacoes a WHERE a.aprovador.id = :aprovadorId")
    List<SolicitacaoAprovacao> findByAprovadorId(@Param("aprovadorId") Long aprovadorId);
    
    @Query("SELECT s FROM SolicitacaoAprovacao s JOIN s.aprovacoes a WHERE a.aprovador.id = :aprovadorId AND a.status = :status")
    List<SolicitacaoAprovacao> findByAprovadorIdAndStatus(@Param("aprovadorId") Long aprovadorId, @Param("status") String status);
    
    List<SolicitacaoAprovacao> findByStatus(SolicitacaoAprovacao.StatusSolicitacao status);
    
    List<SolicitacaoAprovacao> findByTipoSolicitacao(SolicitacaoAprovacao.TipoSolicitacao tipoSolicitacao);
    
    @Query("SELECT s FROM SolicitacaoAprovacao s WHERE s.workflow.empresa.id = :empresaId")
    List<SolicitacaoAprovacao> findByEmpresaId(@Param("empresaId") Long empresaId);
    
    @Query("SELECT COUNT(s) FROM SolicitacaoAprovacao s WHERE s.solicitante.id = :solicitanteId")
    Long countBySolicitanteId(@Param("solicitanteId") Long solicitanteId);
    
    @Query("SELECT COUNT(s) FROM SolicitacaoAprovacao s JOIN s.aprovacoes a WHERE a.aprovador.id = :aprovadorId")
    Long countByAprovadorId(@Param("aprovadorId") Long aprovadorId);
    
    @Query("SELECT s FROM SolicitacaoAprovacao s WHERE s.dataVencimento < :agora AND s.status = 'PENDENTE'")
    List<SolicitacaoAprovacao> findSolicitacoesVencidas(@Param("agora") java.time.LocalDateTime agora);
}
