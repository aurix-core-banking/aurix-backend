package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.MovimentoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentoContaRepository extends JpaRepository<MovimentoConta, Long> {
    
    Optional<MovimentoConta> findByCodigoMovimento(String codigoMovimento);
    
    List<MovimentoConta> findByContaId(Long contaId);
    
    List<MovimentoConta> findByTipoMovimento(MovimentoConta.TipoMovimento tipoMovimento);
    
    List<MovimentoConta> findByStatus(MovimentoConta.StatusMovimento status);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.conta.id = :contaId AND m.dataMovimento BETWEEN :dataInicio AND :dataFim")
    List<MovimentoConta> findMovimentosPorContaEPeriodo(@Param("contaId") Long contaId,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.conta.id = :contaId AND m.tipoMovimento = :tipoMovimento AND m.dataMovimento BETWEEN :dataInicio AND :dataFim")
    List<MovimentoConta> findMovimentosPorContaTipoEPeriodo(@Param("contaId") Long contaId,
                                                            @Param("tipoMovimento") MovimentoConta.TipoMovimento tipoMovimento,
                                                            @Param("dataInicio") LocalDateTime dataInicio,
                                                            @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT SUM(m.valorMovimento) FROM MovimentoConta m WHERE m.conta.id = :contaId AND m.tipoMovimento = :tipoMovimento AND m.status = 'CONCLUIDO' AND m.dataMovimento BETWEEN :dataInicio AND :dataFim")
    Double sumValorMovimentoPorContaTipoEPeriodo(@Param("contaId") Long contaId,
                                                 @Param("tipoMovimento") MovimentoConta.TipoMovimento tipoMovimento,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.status = 'PENDENTE' AND m.processamentoAutomatico = true")
    List<MovimentoConta> findMovimentosPendentesParaProcessamento();
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.status = 'FALHADO' AND m.reversivel = true")
    List<MovimentoConta> findMovimentosFalhadosParaRetry();
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.saldoConsistente = false")
    List<MovimentoConta> findMovimentosComSaldoInconsistente();
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.conta.id = :contaId ORDER BY m.dataMovimento DESC, m.id DESC")
    List<MovimentoConta> findUltimosMovimentosPorConta(@Param("contaId") Long contaId);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.conta.id = :contaId AND m.versaoSaldo = (SELECT MAX(m2.versaoSaldo) FROM MovimentoConta m2 WHERE m2.conta.id = :contaId)")
    List<MovimentoConta> findMovimentosUltimaVersaoPorConta(@Param("contaId") Long contaId);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.codigoBacen = :codigoBacen")
    Optional<MovimentoConta> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.codigoSPI = :codigoSPI")
    Optional<MovimentoConta> findByCodigoSPI(@Param("codigoSPI") String codigoSPI);
    
    @Query("SELECT m FROM MovimentoConta m WHERE m.codigoSTR = :codigoSTR")
    Optional<MovimentoConta> findByCodigoSTR(@Param("codigoSTR") String codigoSTR);
}
