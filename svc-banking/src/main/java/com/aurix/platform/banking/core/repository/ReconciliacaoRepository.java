package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.Reconciliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReconciliacaoRepository extends JpaRepository<Reconciliacao, Long> {
    
    Optional<Reconciliacao> findByCodigoReconciliacao(String codigoReconciliacao);
    
    List<Reconciliacao> findByTipoReconciliacao(Reconciliacao.TipoReconciliacao tipoReconciliacao);
    
    List<Reconciliacao> findByStatus(Reconciliacao.StatusReconciliacao status);
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<Reconciliacao> findReconciliacoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio,
                                                     @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.tipoReconciliacao = :tipoReconciliacao AND r.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<Reconciliacao> findReconciliacoesPorTipoEPeriodo(@Param("tipoReconciliacao") Reconciliacao.TipoReconciliacao tipoReconciliacao,
                                                          @Param("dataInicio") LocalDateTime dataInicio,
                                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.status = 'PENDENTE' AND r.processamentoAutomatico = true")
    List<Reconciliacao> findReconciliacoesPendentesParaProcessamento();
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.status = 'DIVERGENCIA'")
    List<Reconciliacao> findReconciliacoesComDivergencia();
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.codigoBacen = :codigoBacen")
    Optional<Reconciliacao> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT r FROM Reconciliacao r WHERE r.arquivoOrigem = :arquivoOrigem")
    Optional<Reconciliacao> findByArquivoOrigem(@Param("arquivoOrigem") String arquivoOrigem);
}
