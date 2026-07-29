package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ControleSaldoRepository extends JpaRepository<ControleSaldo, Long> {
    
    Optional<ControleSaldo> findByContaId(Long contaId);
    
    List<ControleSaldo> findBySaldoConsistente(Boolean saldoConsistente);
    
    List<ControleSaldo> findByBloqueioOperacoes(Boolean bloqueioOperacoes);
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.saldoTotal < :valorMinimo")
    List<ControleSaldo> findContasComSaldoBaixo(@Param("valorMinimo") Double valorMinimo);
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.saldoDisponivel < :valorMinimo AND cs.bloqueioOperacoes = false")
    List<ControleSaldo> findContasSemSaldoSuficiente(@Param("valorMinimo") Double valorMinimo);
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.limiteUtilizado > (cs.limiteCredito * 0.8)")
    List<ControleSaldo> findContasComLimiteAlto();
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.saldoConsistente = false")
    List<ControleSaldo> findContasComSaldoInconsistente();
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.bloqueioOperacoes = true AND cs.dataBloqueio IS NOT NULL")
    List<ControleSaldo> findContasBloqueadas();
    
    @Query("SELECT cs FROM ControleSaldo cs WHERE cs.versaoSaldo = (SELECT MAX(cs2.versaoSaldo) FROM ControleSaldo cs2 WHERE cs2.conta.id = cs.conta.id)")
    List<ControleSaldo> findUltimasVersoesSaldos();
}
