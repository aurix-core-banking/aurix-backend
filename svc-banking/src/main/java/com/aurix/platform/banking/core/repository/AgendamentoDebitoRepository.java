package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AgendamentoDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoDebitoRepository extends JpaRepository<AgendamentoDebito, Long> {

    List<AgendamentoDebito> findByContaId(Long contaId);

    List<AgendamentoDebito> findByStatus(AgendamentoDebito.StatusAgendamento status);

    @Query("SELECT a FROM AgendamentoDebito a WHERE a.status = 'AGENDADO' AND a.dataDebito <= :ateData ORDER BY a.dataDebito")
    List<AgendamentoDebito> findPendentesAteData(@Param("ateData") LocalDate ateData);
}
