package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.LogAtividadeInternetBanking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para Logs de Atividade do Internet Banking
 */
@Repository
public interface LogAtividadeInternetBankingRepository extends JpaRepository<LogAtividadeInternetBanking, Long> {
    
    /**
     * Busca log por ID
     */
    LogAtividadeInternetBanking findByLogId(String logId);
    
    /**
     * Busca logs por sessão
     */
    List<LogAtividadeInternetBanking> findBySessaoId(String sessaoId);
    
    /**
     * Busca logs por cliente
     */
    Page<LogAtividadeInternetBanking> findByClienteId(String clienteId, Pageable pageable);
    
    /**
     * Busca logs por usuário
     */
    List<LogAtividadeInternetBanking> findByUsuarioId(String usuarioId);
    
    /**
     * Busca logs por tipo de atividade
     */
    List<LogAtividadeInternetBanking> findByTipoAtividade(LogAtividadeInternetBanking.TipoAtividade tipoAtividade);
    
    /**
     * Busca logs por categoria
     */
    List<LogAtividadeInternetBanking> findByCategoria(LogAtividadeInternetBanking.CategoriaAtividade categoria);
    
    /**
     * Busca logs por status
     */
    List<LogAtividadeInternetBanking> findByStatus(String status);
    
    /**
     * Busca logs por período
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.dataAtividade BETWEEN :dataInicio AND :dataFim")
    List<LogAtividadeInternetBanking> findLogsPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                                         @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca logs por IP
     */
    List<LogAtividadeInternetBanking> findByIpAddress(String ipAddress);
    
    /**
     * Busca logs por dispositivo
     */
    List<LogAtividadeInternetBanking> findByDeviceId(String deviceId);
    
    /**
     * Busca logs de login
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.tipoAtividade = 'LOGIN'")
    List<LogAtividadeInternetBanking> findLogsLogin();
    
    /**
     * Busca logs de logout
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.tipoAtividade = 'LOGOUT'")
    List<LogAtividadeInternetBanking> findLogsLogout();
    
    /**
     * Busca logs de transações
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.tipoAtividade = 'TRANSACAO'")
    List<LogAtividadeInternetBanking> findLogsTransacoes();
    
    /**
     * Busca logs de erros
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.tipoAtividade = 'ERRO' OR l.status = 'ERROR'")
    List<LogAtividadeInternetBanking> findLogsErros();
    
    /**
     * Busca logs de atividades suspeitas
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.tipoAtividade = 'SUSPEITA'")
    List<LogAtividadeInternetBanking> findLogsAtividadesSuspeitas();
    
    /**
     * Busca logs de segurança
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.categoria = 'SEGURANCA'")
    List<LogAtividadeInternetBanking> findLogsSeguranca();
    
    /**
     * Conta logs por cliente no período
     */
    @Query("SELECT COUNT(l) FROM LogAtividadeInternetBanking l WHERE l.clienteId = :clienteId AND l.dataAtividade BETWEEN :dataInicio AND :dataFim")
    long countLogsPorClientePeriodo(@Param("clienteId") String clienteId, 
                                   @Param("dataInicio") LocalDateTime dataInicio, 
                                   @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca logs por ação específica
     */
    List<LogAtividadeInternetBanking> findByAcao(String acao);
    
    /**
     * Busca logs por resultado
     */
    List<LogAtividadeInternetBanking> findByResultado(String resultado);
    
    /**
     * Busca logs por transação
     */
    List<LogAtividadeInternetBanking> findByTransacaoId(String transacaoId);
    
    /**
     * Busca logs por conta
     */
    List<LogAtividadeInternetBanking> findByContaEnvolvida(String contaEnvolvida);
    
    /**
     * Busca últimas atividades do cliente
     */
    @Query("SELECT l FROM LogAtividadeInternetBanking l WHERE l.clienteId = :clienteId ORDER BY l.dataAtividade DESC")
    Page<LogAtividadeInternetBanking> findUltimasAtividadesPorCliente(@Param("clienteId") String clienteId, Pageable pageable);
    
    /**
     * Busca atividades por geolocalização
     */
    List<LogAtividadeInternetBanking> findByGeolocalizacao(String geolocalizacao);
}
