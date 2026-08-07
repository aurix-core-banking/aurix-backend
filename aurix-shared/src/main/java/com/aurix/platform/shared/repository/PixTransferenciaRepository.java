package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.PixTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PixTransferenciaRepository extends JpaRepository<PixTransferencia, Long> {

    Optional<PixTransferencia> findByCodigoPix(String codigoPix);

    List<PixTransferencia> findByContaOrigemId(Long contaOrigemId);

    List<PixTransferencia> findByChavePixDestino(String chavePixDestino);

    List<PixTransferencia> findByStatus(PixTransferencia.StatusPix status);

    @Query("SELECT p FROM PixTransferencia p WHERE p.dataTransferencia BETWEEN :inicio AND :fim")
    List<PixTransferencia> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT p FROM PixTransferencia p WHERE p.contaOrigem.id = :contaId AND p.dataTransferencia BETWEEN :inicio AND :fim")
    List<PixTransferencia> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT SUM(p.valor) FROM PixTransferencia p WHERE p.contaOrigem.id = :contaId AND p.dataTransferencia BETWEEN :inicio AND :fim AND p.status = 'PROCESSADA'")
    BigDecimal somarValorTransferenciasPorContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    long countByStatus(PixTransferencia.StatusPix status);

    @Query("SELECT COUNT(p) FROM PixTransferencia p WHERE p.contaOrigem.id = :contaId AND p.dataTransferencia BETWEEN :inicio AND :fim")
    long countByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT p FROM PixTransferencia p WHERE p.status = 'PENDENTE' AND p.dataTransferencia < :limite")
    List<PixTransferencia> findTransferenciasPendentesExpiradas(@Param("limite") LocalDateTime limite);
}
