package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

    List<Investimento> findByContaId(Long contaId);

    List<Investimento> findByTipoInvestimento(Investimento.TipoInvestimento tipoInvestimento);

    List<Investimento> findByStatus(Investimento.StatusInvestimento status);

    @Query("SELECT i FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosAtivosByContaId(@Param("contaId") Long contaId);

    @Query("SELECT i FROM Investimento i WHERE i.dataVencimento < :dataAtual AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosVencidos(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT i FROM Investimento i WHERE i.dataAplicacao BETWEEN :inicio AND :fim")
    List<Investimento> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT i FROM Investimento i WHERE i.conta.id = :contaId AND i.dataAplicacao BETWEEN :inicio AND :fim")
    List<Investimento> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT SUM(i.valorInvestido + i.rendimentoAtual) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarValorTotalPorConta(@Param("contaId") Long contaId);

    @Query("SELECT SUM(i.valorInvestido) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarValorInvestidoPorConta(@Param("contaId") Long contaId);

    @Query("SELECT SUM(i.rendimentoAtual) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarRendimentoAtualPorConta(@Param("contaId") Long contaId);

    long countByStatus(Investimento.StatusInvestimento status);

    long countByTipoInvestimento(Investimento.TipoInvestimento tipoInvestimento);

    long countByContaId(Long contaId);

    @Query("SELECT i FROM Investimento i WHERE i.dataVencimento BETWEEN :inicio AND :fim AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosProximosVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
