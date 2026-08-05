package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.TransacaoInternetBanking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para Transações de Internet Banking
 */
@Repository
public interface TransacaoInternetBankingRepository extends JpaRepository<TransacaoInternetBanking, Long> {
    
    /**
     * Busca transação por ID
     */
    TransacaoInternetBanking findByTransacaoId(String transacaoId);
    
    /**
     * Busca transações por sessão
     */
    List<TransacaoInternetBanking> findBySessaoId(String sessaoId);
    
    /**
     * Busca transações por cliente
     */
    Page<TransacaoInternetBanking> findByClienteId(String clienteId, Pageable pageable);
    
    /**
     * Busca transações por conta
     */
    Page<TransacaoInternetBanking> findByContaOrigem(String contaOrigem, Pageable pageable);
    
    /**
     * Busca transações por tipo
     */
    List<TransacaoInternetBanking> findByTipoTransacao(TransacaoInternetBanking.TipoTransacao tipoTransacao);
    
    /**
     * Busca transações por status
     */
    List<TransacaoInternetBanking> findByStatus(TransacaoInternetBanking.StatusTransacao status);
    
    /**
     * Busca transações por período
     */
    @Query("SELECT t FROM TransacaoInternetBanking t WHERE t.dataTransacao BETWEEN :dataInicio AND :dataFim")
    List<TransacaoInternetBanking> findTransacoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                                            @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca transações por valor mínimo
     */
    @Query("SELECT t FROM TransacaoInternetBanking t WHERE t.valor >= :valorMinimo")
    List<TransacaoInternetBanking> findTransacoesPorValorMinimo(@Param("valorMinimo") BigDecimal valorMinimo);
    
    /**
     * Busca transações suspeitas (alto risco)
     */
    @Query("SELECT t FROM TransacaoInternetBanking t WHERE t.riscoScore >= :riscoMinimo OR t.fraudeDetectada = true")
    List<TransacaoInternetBanking> findTransacoesSuspeitas(@Param("riscoMinimo") Double riscoMinimo);
    
    /**
     * Conta transações por cliente no período
     */
    @Query("SELECT COUNT(t) FROM TransacaoInternetBanking t WHERE t.clienteId = :clienteId AND t.dataTransacao BETWEEN :dataInicio AND :dataFim")
    long countTransacoesPorClientePeriodo(@Param("clienteId") String clienteId, 
                                          @Param("dataInicio") LocalDateTime dataInicio, 
                                          @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Soma valor das transações por cliente no período
     */
    @Query("SELECT SUM(t.valor) FROM TransacaoInternetBanking t WHERE t.clienteId = :clienteId AND t.dataTransacao BETWEEN :dataInicio AND :dataFim")
    BigDecimal sumValorTransacoesPorClientePeriodo(@Param("clienteId") String clienteId, 
                                                   @Param("dataInicio") LocalDateTime dataInicio, 
                                                   @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca transações por IP
     */
    List<TransacaoInternetBanking> findByIpAddress(String ipAddress);
    
    /**
     * Busca transações por dispositivo
     */
    List<TransacaoInternetBanking> findByDeviceFingerprint(String deviceFingerprint);
    
    /**
     * Busca transações pendentes
     */
    @Query("SELECT t FROM TransacaoInternetBanking t WHERE t.status = 'PENDENTE' OR t.status = 'PROCESSANDO'")
    List<TransacaoInternetBanking> findTransacoesPendentes();
    
    /**
     * Busca transações por protocolo
     */
    TransacaoInternetBanking findByProtocolo(String protocolo);
    
    /**
     * Busca transações por número de autorização
     */
    TransacaoInternetBanking findByNumeroAutorizacao(String numeroAutorizacao);
}
