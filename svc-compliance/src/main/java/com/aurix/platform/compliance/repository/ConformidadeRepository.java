package com.aurix.platform.compliance.repository;

import com.aurix.platform.shared.entity.Conformidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Conformidade
 */
@Repository
public interface ConformidadeRepository extends JpaRepository<Conformidade, Long> {
    
    /**
     * Busca conformidades por regulamentação
     */
    List<Conformidade> findByRegulacaoId(Long regulacaoId);
    
    /**
     * Busca conformidades por entidade
     */
    List<Conformidade> findByEntidadeIdAndTipoEntidade(Long entidadeId, String tipoEntidade);
    
    /**
     * Busca conformidades por status
     */
    List<Conformidade> findByStatus(Conformidade.StatusConformidade status);
    
    /**
     * Busca conformidades vencidas
     */
    @Query("SELECT c FROM Conformidade c WHERE c.dataProximaVerificacao < :dataAtual")
    List<Conformidade> findConformidadesVencidas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca conformidades próximas do vencimento
     */
    @Query("SELECT c FROM Conformidade c WHERE c.dataProximaVerificacao BETWEEN :inicio AND :fim")
    List<Conformidade> findConformidadesProximasVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca conformidades por período
     */
    @Query("SELECT c FROM Conformidade c WHERE c.dataVerificacao BETWEEN :inicio AND :fim")
    List<Conformidade> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca conformidades não conformes
     */
    @Query("SELECT c FROM Conformidade c WHERE c.status IN ('NAO_CONFORME', 'NAO_CONFORME_CRITICO')")
    List<Conformidade> findConformidadesNaoConformes();
    
    /**
     * Busca conformidades críticas
     */
    @Query("SELECT c FROM Conformidade c WHERE c.status = 'NAO_CONFORME_CRITICO'")
    List<Conformidade> findConformidadesCriticas();
    
    /**
     * Conta conformidades por status
     */
    long countByStatus(Conformidade.StatusConformidade status);
    
    /**
     * Conta conformidades por entidade
     */
    long countByEntidadeIdAndTipoEntidade(Long entidadeId, String tipoEntidade);
    
    /**
     * Conta conformidades por regulamentação
     */
    long countByRegulacaoId(Long regulacaoId);
}

