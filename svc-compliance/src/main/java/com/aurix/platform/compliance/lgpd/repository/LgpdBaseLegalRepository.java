package com.aurix.platform.compliance.lgpd.repository;

import com.aurix.platform.compliance.lgpd.entity.LgpdBaseLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LgpdBaseLegalRepository extends JpaRepository<LgpdBaseLegal, Long> {

    Optional<LgpdBaseLegal> findByCodigoBaseLegal(String codigoBaseLegal);

    List<LgpdBaseLegal> findByClienteId(Long clienteId);

    List<LgpdBaseLegal> findByStatus(LgpdBaseLegal.StatusBaseLegal status);

    List<LgpdBaseLegal> findByTipoBaseLegal(LgpdBaseLegal.TipoBaseLegal tipo);

    @Query("SELECT b FROM LgpdBaseLegal b WHERE b.status = 'ATIVA' AND (b.dataFimVigencia IS NULL OR b.dataFimVigencia >= :agora)")
    List<LgpdBaseLegal> findBasesLegaisAtivas(@Param("agora") LocalDateTime agora);

    @Query("SELECT b FROM LgpdBaseLegal b WHERE b.clienteId = :clienteId AND b.status = 'ATIVA'")
    List<LgpdBaseLegal> findBasesLegaisAtivasPorCliente(@Param("clienteId") Long clienteId);
}
