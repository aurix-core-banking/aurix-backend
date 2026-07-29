package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.CobrancaTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CobrancaTarifaRepository extends JpaRepository<CobrancaTarifa, Long> {
    
    List<CobrancaTarifa> findByContaId(Long contaId);
    
    List<CobrancaTarifa> findByContaIdAndStatus(Long contaId, CobrancaTarifa.StatusCobranca status);
    
    List<CobrancaTarifa> findByStatus(CobrancaTarifa.StatusCobranca status);
    
    @Query("SELECT ct FROM CobrancaTarifa ct WHERE ct.conta.id = :contaId AND " +
           "ct.dataCobranca BETWEEN :dataInicio AND :dataFim")
    List<CobrancaTarifa> findCobrancasPorPeriodo(@Param("contaId") Long contaId, 
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT SUM(ct.valorCobrado) FROM CobrancaTarifa ct WHERE ct.conta.id = :contaId AND " +
           "ct.status = 'COBRADA' AND ct.dataCobranca BETWEEN :dataInicio AND :dataFim")
    Double sumValorCobradoPorPeriodo(@Param("contaId") Long contaId, 
                                    @Param("dataInicio") LocalDateTime dataInicio,
                                    @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT ct FROM CobrancaTarifa ct WHERE ct.status = 'PENDENTE' AND " +
           "ct.dataVencimento < :dataAtual")
    List<CobrancaTarifa> findCobrancasVencidas(@Param("dataAtual") LocalDateTime dataAtual);
}
