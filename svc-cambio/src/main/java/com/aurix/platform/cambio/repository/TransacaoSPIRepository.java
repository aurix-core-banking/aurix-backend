package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.TransacaoSPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoSPIRepository extends JpaRepository<TransacaoSPI, Long> {

    Optional<TransacaoSPI> findByEndToEndId(String endToEndId);

    List<TransacaoSPI> findByStatus(TransacaoSPI.StatusSPI status);

    @Query("SELECT t FROM TransacaoSPI t WHERE t.status IN ('PENDENTE', 'ENVIANDO') AND t.tentativasEnvio < t.maxTentativas ORDER BY t.dataCriacao")
    List<TransacaoSPI> findTransacoesParaEnvio();

    @Query("SELECT t FROM TransacaoSPI t WHERE t.dataCriacao BETWEEN :inicio AND :fim")
    List<TransacaoSPI> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
