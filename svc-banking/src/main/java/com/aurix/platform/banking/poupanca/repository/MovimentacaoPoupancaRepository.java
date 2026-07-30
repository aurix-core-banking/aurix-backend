package com.aurix.platform.banking.poupanca.repository;

import com.aurix.platform.banking.poupanca.entity.MovimentacaoPoupanca;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimentacaoPoupancaRepository extends JpaRepository<MovimentacaoPoupanca, Long> {

    List<MovimentacaoPoupanca> findByContaPoupancaIdOrderByDataMovimentacaoDesc(Long contaPoupancaId);

    @Query("SELECT m FROM MovimentacaoPoupanca m WHERE m.contaPoupancaId = :contaId AND m.dataMovimentacao BETWEEN :inicio AND :fim ORDER BY m.dataMovimentacao DESC")
    List<MovimentacaoPoupanca> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
