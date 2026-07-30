package com.aurix.platform.banking.settlement.repository;

import com.aurix.platform.banking.settlement.entity.Liquidez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para liquidações
 */
@Repository
public interface LiquidezRepository extends JpaRepository<Liquidez, Long> {
    
    /**
     * Busca liquidação por número
     */
    Optional<Liquidez> findByNumeroLiquidez(String numeroLiquidez);
    
    /**
     * Busca liquidações por status
     */
    List<Liquidez> findByStatus(Liquidez.StatusLiquidez status);
    
    /**
     * Busca liquidações por tipo de operação
     */
    List<Liquidez> findByTipoOperacao(Liquidez.TipoOperacao tipoOperacao);
    
    /**
     * Busca liquidações por canal
     */
    List<Liquidez> findByCanal(Liquidez.Canal canal);
    
    /**
     * Busca liquidações por conta origem
     */
    List<Liquidez> findByContaOrigem(String contaOrigem);
    
    /**
     * Busca liquidações por conta destino
     */
    List<Liquidez> findByContaDestino(String contaDestino);
    
    /**
     * Busca liquidações por data
     */
    @Query("SELECT l FROM Liquidez l WHERE DATE(l.dataLiquidez) = :data")
    List<Liquidez> findByData(@Param("data") LocalDate data);
    
    /**
     * Busca liquidações por período
     */
    @Query("SELECT l FROM Liquidez l WHERE l.dataLiquidez BETWEEN :dataInicio AND :dataFim")
    List<Liquidez> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca liquidações por valor mínimo
     */
    @Query("SELECT l FROM Liquidez l WHERE l.valor >= :valorMinimo")
    List<Liquidez> findByValorMinimo(@Param("valorMinimo") BigDecimal valorMinimo);
    
    /**
     * Busca liquidações por valor máximo
     */
    @Query("SELECT l FROM Liquidez l WHERE l.valor <= :valorMaximo")
    List<Liquidez> findByValorMaximo(@Param("valorMaximo") BigDecimal valorMaximo);
    
    /**
     * Busca liquidações por faixa de valor
     */
    @Query("SELECT l FROM Liquidez l WHERE l.valor BETWEEN :valorMinimo AND :valorMaximo")
    List<Liquidez> findByFaixaValor(@Param("valorMinimo") BigDecimal valorMinimo, @Param("valorMaximo") BigDecimal valorMaximo);
    
    /**
     * Busca liquidações com falha
     */
    @Query("SELECT l FROM Liquidez l WHERE l.status IN ('FALHA', 'REJEITADO', 'TIMEOUT')")
    List<Liquidez> findLiquidezComFalha();
    
    /**
     * Busca liquidações para retry
     */
    @Query("SELECT l FROM Liquidez l WHERE l.status = 'RETRY' AND l.proximoRetry <= :agora")
    List<Liquidez> findLiquidezParaRetry(@Param("agora") LocalDateTime agora);
    
    /**
     * Soma valor liquidado por dia
     */
    @Query("SELECT SUM(l.valor) FROM Liquidez l WHERE l.dataLiquidez = :data AND l.status = 'LIQUIDADO'")
    BigDecimal somaValorLiquidadoPorDia(@Param("data") LocalDate data);
    
    /**
     * Conta liquidações por status
     */
    long countByStatus(Liquidez.StatusLiquidez status);
    
    /**
     * Conta liquidações por tipo
     */
    long countByTipoOperacao(Liquidez.TipoOperacao tipoOperacao);
    
    /**
     * Conta liquidações por dia
     */
    @Query("SELECT COUNT(l) FROM Liquidez l WHERE l.dataLiquidez = :data")
    long countByData(@Param("data") LocalDate data);
    
    /**
     * Busca liquidações por protocolo sistema
     */
    Optional<Liquidez> findByProtocoloSistema(String protocoloSistema);
    
    /**
     * Busca liquidações por protocolo BACEN
     */
    Optional<Liquidez> findByProtocoloBacen(String protocoloBacen);
    
    /**
     * Busca liquidações por código de retorno
     */
    List<Liquidez> findByCodigoRetorno(String codigoRetorno);
    
    /**
     * Busca liquidações por banco origem
     */
    List<Liquidez> findByBancoOrigem(String bancoOrigem);
    
    /**
     * Busca liquidações por banco destino
     */
    List<Liquidez> findByBancoDestino(String bancoDestino);
    
    /**
     * Busca liquidações pendentes de confirmação
     */
    @Query("SELECT l FROM Liquidez l WHERE l.status = 'LIQUIDADO' AND l.dataConfirmacao IS NULL")
    List<Liquidez> findLiquidezPendentesConfirmacao();
    
    /**
     * Busca liquidações por período e status
     */
    @Query("SELECT l FROM Liquidez l WHERE l.dataLiquidez BETWEEN :dataInicio AND :dataFim AND l.status = :status")
    List<Liquidez> findByPeriodoAndStatus(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("status") Liquidez.StatusLiquidez status);
}
