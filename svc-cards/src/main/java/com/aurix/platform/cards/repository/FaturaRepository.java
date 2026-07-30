package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    Optional<Fatura> findByCodigoFatura(String codigoFatura);

    List<Fatura> findByCartaoId(Long cartaoId);

    @Query("SELECT f FROM Fatura f WHERE f.cartaoId = :cartaoId AND f.mesReferencia = :mes AND f.anoReferencia = :ano")
    Optional<Fatura> findByCartaoMesAno(@Param("cartaoId") Long cartaoId, 
                                        @Param("mes") Integer mes, 
                                        @Param("ano") Integer ano);

    @Query("SELECT f FROM Fatura f WHERE f.status = 'VENCIDA' AND f.dataVencimento < :hoje")
    List<Fatura> findFaturasVencidas(@Param("hoje") LocalDate hoje);

    @Query("SELECT f FROM Fatura f WHERE f.status IN ('ABERTA', 'FECHADA') AND f.dataVencimento < :hoje")
    List<Fatura> findFaturasAVencer(@Param("hoje") LocalDate hoje);
}
