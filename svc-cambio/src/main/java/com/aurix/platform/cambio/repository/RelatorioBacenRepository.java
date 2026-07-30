package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.RelatorioBacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RelatorioBacenRepository extends JpaRepository<RelatorioBacen, Long> {

    Optional<RelatorioBacen> findByCodigoRelatorioAndDataReferencia(String codigoRelatorio, LocalDate dataReferencia);

    List<RelatorioBacen> findByDataReferencia(LocalDate dataReferencia);

    List<RelatorioBacen> findByTipoRelatorio(RelatorioBacen.TipoRelatorio tipoRelatorio);

    List<RelatorioBacen> findByCategoria(RelatorioBacen.CategoriaRelatorio categoria);

    List<RelatorioBacen> findByStatus(RelatorioBacen.StatusRelatorio status);

    @Query("SELECT r FROM RelatorioBacen r WHERE r.dataReferencia BETWEEN :inicio AND :fim ORDER BY r.dataReferencia DESC, r.codigoRelatorio")
    List<RelatorioBacen> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT r FROM RelatorioBacen r WHERE r.status = 'PENDENTE' AND r.dataVencimento <= :hoje ORDER BY r.dataVencimento")
    List<RelatorioBacen> findPendentesComVencimentoProximo(@Param("hoje") LocalDate hoje);
}
