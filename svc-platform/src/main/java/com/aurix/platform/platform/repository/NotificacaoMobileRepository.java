package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.NotificacaoMobile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para Notificações Mobile
 */
@Repository
public interface NotificacaoMobileRepository extends JpaRepository<NotificacaoMobile, Long> {
    
    /**
     * Busca notificação por ID
     */
    NotificacaoMobile findByNotificacaoId(String notificacaoId);
    
    /**
     * Busca notificações por dispositivo
     */
    List<NotificacaoMobile> findByDispositivoId(String dispositivoId);
    
    /**
     * Busca notificações por cliente
     */
    Page<NotificacaoMobile> findByClienteId(String clienteId, Pageable pageable);
    
    /**
     * Busca notificações por tipo
     */
    List<NotificacaoMobile> findByTipoNotificacao(NotificacaoMobile.TipoNotificacao tipoNotificacao);
    
    /**
     * Busca notificações por categoria
     */
    List<NotificacaoMobile> findByCategoria(NotificacaoMobile.CategoriaNotificacao categoria);
    
    /**
     * Busca notificações por status
     */
    List<NotificacaoMobile> findByStatus(NotificacaoMobile.StatusNotificacao status);
    
    /**
     * Busca notificações por prioridade
     */
    List<NotificacaoMobile> findByPrioridade(NotificacaoMobile.PrioridadeNotificacao prioridade);
    
    /**
     * Busca notificações pendentes
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'PENDENTE'")
    List<NotificacaoMobile> findNotificacoesPendentes();
    
    /**
     * Busca notificações agendadas
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'AGENDADA' AND n.dataAgendamento <= :dataAtual")
    List<NotificacaoMobile> findNotificacoesAgendadasParaEnvio(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca notificações enviadas
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'ENVIADA'")
    List<NotificacaoMobile> findNotificacoesEnviadas();
    
    /**
     * Busca notificações entregues
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'ENTREGUE'")
    List<NotificacaoMobile> findNotificacoesEntregues();
    
    /**
     * Busca notificações lidas
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'LIDA'")
    List<NotificacaoMobile> findNotificacoesLidas();
    
    /**
     * Busca notificações que falharam
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.status = 'FALHOU'")
    List<NotificacaoMobile> findNotificacoesQueFalharam();
    
    /**
     * Busca notificações por período
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<NotificacaoMobile> findNotificacoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                                       @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca notificações por período de envio
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.dataEnvio BETWEEN :dataInicio AND :dataFim")
    List<NotificacaoMobile> findNotificacoesPorPeriodoEnvio(@Param("dataInicio") LocalDateTime dataInicio, 
                                                            @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca notificações por transação
     */
    List<NotificacaoMobile> findByTransacaoId(String transacaoId);
    
    /**
     * Busca notificações por conta
     */
    List<NotificacaoMobile> findByContaEnvolvida(String contaEnvolvida);
    
    /**
     * Busca notificações por valor
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.valor >= :valorMinimo")
    List<NotificacaoMobile> findNotificacoesPorValorMinimo(@Param("valorMinimo") java.math.BigDecimal valorMinimo);
    
    /**
     * Busca notificações críticas
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.prioridade = 'CRITICA'")
    List<NotificacaoMobile> findNotificacoesCriticas();
    
    /**
     * Busca notificações de alta prioridade
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.prioridade = 'ALTA'")
    List<NotificacaoMobile> findNotificacoesAltaPrioridade();
    
    /**
     * Busca notificações de segurança
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.categoria = 'SEGURANCA'")
    List<NotificacaoMobile> findNotificacoesSeguranca();
    
    /**
     * Busca notificações financeiras
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.categoria = 'FINANCEIRA'")
    List<NotificacaoMobile> findNotificacoesFinanceiras();
    
    /**
     * Busca notificações promocionais
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.categoria = 'PROMOCIONAL'")
    List<NotificacaoMobile> findNotificacoesPromocionais();
    
    /**
     * Busca notificações PIX
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.tipoNotificacao = 'PIX'")
    List<NotificacaoMobile> findNotificacoesPIX();
    
    /**
     * Busca notificações de transação
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.tipoNotificacao = 'TRANSACAO'")
    List<NotificacaoMobile> findNotificacoesTransacao();
    
    /**
     * Busca notificações de cartão
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.tipoNotificacao = 'CARTAO'")
    List<NotificacaoMobile> findNotificacoesCartao();
    
    /**
     * Busca notificações expiradas
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.dataExpiracao < :dataAtual AND n.status IN ('PENDENTE', 'AGENDADA')")
    List<NotificacaoMobile> findNotificacoesExpiradas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Conta notificações por cliente
     */
    @Query("SELECT COUNT(n) FROM NotificacaoMobile n WHERE n.clienteId = :clienteId")
    long countNotificacoesPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Conta notificações por dispositivo
     */
    @Query("SELECT COUNT(n) FROM NotificacaoMobile n WHERE n.dispositivoId = :dispositivoId")
    long countNotificacoesPorDispositivo(@Param("dispositivoId") String dispositivoId);
    
    /**
     * Conta notificações por status
     */
    @Query("SELECT COUNT(n) FROM NotificacaoMobile n WHERE n.status = :status")
    long countNotificacoesPorStatus(@Param("status") NotificacaoMobile.StatusNotificacao status);
    
    /**
     * Busca últimas notificações do cliente
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.clienteId = :clienteId ORDER BY n.dataCriacao DESC")
    Page<NotificacaoMobile> findUltimasNotificacoesPorCliente(@Param("clienteId") String clienteId, Pageable pageable);
    
    /**
     * Busca notificações não lidas do cliente
     */
    @Query("SELECT n FROM NotificacaoMobile n WHERE n.clienteId = :clienteId AND n.status NOT IN ('LIDA', 'CANCELADA')")
    List<NotificacaoMobile> findNotificacoesNaoLidasPorCliente(@Param("clienteId") String clienteId);
}
