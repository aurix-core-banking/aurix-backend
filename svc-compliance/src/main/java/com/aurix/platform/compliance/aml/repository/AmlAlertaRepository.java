package com.aurix.platform.compliance.aml.repository;

import com.aurix.platform.compliance.aml.entity.AmlAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AmlAlertaRepository extends JpaRepository<AmlAlerta, Long> {

    Optional<AmlAlerta> findByCodigoAlerta(String codigoAlerta);

    List<AmlAlerta> findByClienteId(Long clienteId);

    List<AmlAlerta> findByCpfCnpj(String cpfCnpj);

    List<AmlAlerta> findByStatus(AmlAlerta.StatusAlertaAml status);

    List<AmlAlerta> findByTipoAlerta(AmlAlerta.TipoAlertaAml tipo);

    @Query("SELECT a FROM AmlAlerta a WHERE a.scoreRisco >= :scoreMinimo ORDER BY a.scoreRisco DESC")
    List<AmlAlerta> findAlertasPorScoreMinimo(@Param("scoreMinimo") Integer scoreMinimo);

    @Query("SELECT a FROM AmlAlerta a WHERE a.status IN ('DETECTADO', 'EM_INVESTIGACAO') ORDER BY a.scoreRisco DESC")
    List<AmlAlerta> findAlertasAbertos();

    @Query("SELECT a FROM AmlAlerta a WHERE a.dataDeteccao BETWEEN :inicio AND :fim")
    List<AmlAlerta> findByPeriodoDeteccao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(a) FROM AmlAlerta a WHERE a.status IN ('DETECTADO', 'EM_INVESTIGACAO')")
    long countAlertasAbertos();

    @Query("SELECT a FROM AmlAlerta a WHERE a.clienteId = :clienteId AND a.status != 'ARQUIVADO'")
    List<AmlAlerta> findAlertasNaoArquivadosPorCliente(@Param("clienteId") Long clienteId);
}
