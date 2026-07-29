package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.TransacaoCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoCartaoRepository extends JpaRepository<TransacaoCartao, Long> {

    Optional<TransacaoCartao> findByCodigoTransacao(String codigoTransacao);

    List<TransacaoCartao> findByCartaoId(Long cartaoId);

    List<TransacaoCartao> findByStatus(TransacaoCartao.StatusTransacao status);

    @Query("SELECT t FROM TransacaoCartao t WHERE t.cartaoId = :cartaoId AND t.dataTransacao BETWEEN :inicio AND :fim")
    List<TransacaoCartao> findByCartaoEPeriodo(@Param("cartaoId") Long cartaoId,
                                               @Param("inicio") LocalDateTime inicio,
                                               @Param("fim") LocalDateTime fim);
}
