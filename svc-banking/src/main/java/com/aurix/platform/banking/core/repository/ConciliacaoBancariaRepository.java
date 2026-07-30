package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ConciliacaoBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConciliacaoBancariaRepository extends JpaRepository<ConciliacaoBancaria, Long> {
    
    Optional<ConciliacaoBancaria> findByCodigoConciliacao(String codigoConciliacao);
    
    List<ConciliacaoBancaria> findByTipoConciliacao(ConciliacaoBancaria.TipoConciliacao tipoConciliacao);
    
    List<ConciliacaoBancaria> findByStatus(ConciliacaoBancaria.StatusConciliacao status);
    
    List<ConciliacaoBancaria> findByContaId(Long contaId);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<ConciliacaoBancaria> findConciliacoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio,
                                                         @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.tipoConciliacao = :tipoConciliacao AND c.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<ConciliacaoBancaria> findConciliacoesPorTipoEPeriodo(@Param("tipoConciliacao") ConciliacaoBancaria.TipoConciliacao tipoConciliacao,
                                                              @Param("dataInicio") LocalDateTime dataInicio,
                                                              @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.status = 'PENDENTE' AND c.processamentoAutomatico = true")
    List<ConciliacaoBancaria> findConciliacoesPendentesParaProcessamento();
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.status = 'DIVERGENCIA'")
    List<ConciliacaoBancaria> findConciliacoesComDivergencia();
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.conta.id = :contaId AND c.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<ConciliacaoBancaria> findConciliacoesPorContaEPeriodo(@Param("contaId") Long contaId,
                                                               @Param("dataInicio") LocalDateTime dataInicio,
                                                               @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.codigoBacen = :codigoBacen")
    Optional<ConciliacaoBancaria> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.codigoSPI = :codigoSPI")
    Optional<ConciliacaoBancaria> findByCodigoSPI(@Param("codigoSPI") String codigoSPI);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.codigoSTR = :codigoSTR")
    Optional<ConciliacaoBancaria> findByCodigoSTR(@Param("codigoSTR") String codigoSTR);
    
    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.arquivoExtrato = :arquivoExtrato")
    Optional<ConciliacaoBancaria> findByArquivoExtrato(@Param("arquivoExtrato") String arquivoExtrato);
}
