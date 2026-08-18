package com.aurix.platform.compliance.aml.repository;

import com.aurix.platform.compliance.aml.entity.AmlRegra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AmlRegraRepository extends JpaRepository<AmlRegra, Long> {

    Optional<AmlRegra> findByCodigoRegra(String codigoRegra);

    List<AmlRegra> findByStatus(AmlRegra.StatusRegraAml status);

    List<AmlRegra> findByTipoRegra(AmlRegra.TipoRegraAml tipo);

    @Query("SELECT r FROM AmlRegra r WHERE r.status = 'ATIVA' AND (r.dataVigenciaInicio IS NULL OR r.dataVigenciaInicio <= :agora) AND (r.dataVigenciaFim IS NULL OR r.dataVigenciaFim >= :agora)")
    List<AmlRegra> findRegrasAtivas(@Param("agora") LocalDateTime agora);

    @Query("SELECT r FROM AmlRegra r WHERE r.executarAutomaticamente = true AND r.status = 'ATIVA'")
    List<AmlRegra> findRegrasExecutaveis();
}
