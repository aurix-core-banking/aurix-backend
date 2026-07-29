package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.Liquidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiquidacaoRepository extends JpaRepository<Liquidacao, Long> {
    
    Optional<Liquidacao> findByCodigoLiquidacao(String codigoLiquidacao);
    
    List<Liquidacao> findByStatus(Liquidacao.StatusLiquidacao status);
    
    List<Liquidacao> findByTipoLiquidacao(Liquidacao.TipoLiquidacao tipoLiquidacao);
    
    List<Liquidacao> findByTransacaoId(Long transacaoId);
    
    @Query("SELECT l FROM Liquidacao l WHERE l.status = :status AND l.dataLiquidacao BETWEEN :dataInicio AND :dataFim")
    List<Liquidacao> findLiquidacoesPorStatusEPeriodo(@Param("status") Liquidacao.StatusLiquidacao status,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT l FROM Liquidacao l WHERE l.status = 'PENDENTE' AND l.processamentoAutomatico = true AND l.tentativasLiquidacao < l.maxTentativas")
    List<Liquidacao> findLiquidacoesPendentesParaProcessamento();
    
    @Query("SELECT l FROM Liquidacao l WHERE l.status = 'FALHADA' AND l.tentativasLiquidacao < l.maxTentativas")
    List<Liquidacao> findLiquidacoesFalhadasParaRetry();
    
    @Query("SELECT l FROM Liquidacao l WHERE l.status = 'PROCESSANDO' AND l.dataProcessamento < :dataLimite")
    List<Liquidacao> findLiquidacoesProcessandoExpiradas(@Param("dataLimite") LocalDateTime dataLimite);
    
    @Query("SELECT SUM(l.valorLiquidacao) FROM Liquidacao l WHERE l.status = 'LIQUIDADA' AND l.dataLiquidacao BETWEEN :dataInicio AND :dataFim")
    Double sumValorLiquidadoPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio,
                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT COUNT(l) FROM Liquidacao l WHERE l.status = :status AND l.dataLiquidacao BETWEEN :dataInicio AND :dataFim")
    Long countLiquidacoesPorStatusEPeriodo(@Param("status") Liquidacao.StatusLiquidacao status,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT l FROM Liquidacao l WHERE l.codigoBacen = :codigoBacen")
    Optional<Liquidacao> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT l FROM Liquidacao l WHERE l.codigoSPI = :codigoSPI")
    Optional<Liquidacao> findByCodigoSPI(@Param("codigoSPI") String codigoSPI);
    
    @Query("SELECT l FROM Liquidacao l WHERE l.codigoSTR = :codigoSTR")
    Optional<Liquidacao> findByCodigoSTR(@Param("codigoSTR") String codigoSTR);
}
