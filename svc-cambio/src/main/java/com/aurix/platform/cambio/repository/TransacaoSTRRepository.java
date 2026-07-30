package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.TransacaoSTR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoSTRRepository extends JpaRepository<TransacaoSTR, Long> {

    Optional<TransacaoSTR> findByNumeroControle(String numeroControle);

    List<TransacaoSTR> findByStatus(TransacaoSTR.StatusSTR status);

    List<TransacaoSTR> findByTipoSTR(TransacaoSTR.TipoSTR tipoSTR);

    @Query("SELECT t FROM TransacaoSTR t WHERE t.status IN ('PENDENTE', 'AGENDADA') AND t.tentativasEnvio < t.maxTentativas AND (t.dataAgendamento IS NULL OR t.dataAgendamento <= :agora) ORDER BY t.dataAgendamento")
    List<TransacaoSTR> findTransacoesParaEnvio(@Param("agora") LocalDateTime agora);
}
